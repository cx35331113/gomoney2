package com.cloud.oauth.gomoney.core.servlet;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class RepeatBodyRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] bodyByteArray;
    private final Map<String, String[]> parameterMap;

    public RepeatBodyRequestWrapper(HttpServletRequest request) {
        super(request);
        this.bodyByteArray = getByteBody(request);
        // 使用 HashMap 以便后续可以修改
        this.parameterMap = new HashMap<>(request.getParameterMap());
    }

    @Override
    public BufferedReader getReader() {
        return ObjectUtils.isEmpty(this.bodyByteArray) ? null
                : new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.bodyByteArray);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true; // 可以读取
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                // doNothing
            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    private static byte[] getByteBody(HttpServletRequest request) {
        byte[] body = new byte[0];
        try {
            body = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            log.error("解析流中数据异常", e);
        }
        return body;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.parameterMap; // 返回可变的 parameterMap
    }

    /**
     * 设置新的参数映射
     * @param parameterMap 新的参数映射
     */
    public void setParameterMap(Map<String, String[]> parameterMap) {
        this.parameterMap.clear();
        this.parameterMap.putAll(parameterMap);
    }

    @Override
    public String getParameter(String name) {
        String[] values = parameterMap.get(name);
        return (values != null && values.length > 0) ? values[0] : null;
    }

    @Override
    public String[] getParameterValues(String name) {
        return parameterMap.get(name);
    }
}



