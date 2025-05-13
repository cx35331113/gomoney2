package com.cloud.oauth.gomoney.ai.aspect;


import com.alibaba.fastjson.JSONObject;
import com.cloud.oauth.gomoney.core.utils.MyLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.cloud.oauth.gomoney.ai.common.utils.*;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@RequiredArgsConstructor
public class RepeatSubmitAspect {

    protected static final MyLog _log = MyLog.getLog(RepeatSubmitAspect.class);

    /**
     * 自定义错误信息
     *
     * @return JSONObject
     * @author chenx
     */
    private JSONObject buildFailureMsg(String errMsg) {
        final JSONObject json = new JSONObject();
        json.put("code", "405");
        json.put("msg", errMsg);
        return json;
    }

    private final RedisUtil redisUtils;

    private final RedisLock redisLock;

    /**
     * 重复提交拦截
     *
     * @return Object
     * @author chenx
     */
    @Around(value = "@annotation(com.cloud.oauth.gomoney.api.annotation.Resubmit)")
    public Object arround(ProceedingJoinPoint pjp) {
        Object obj = null;
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            HttpServletResponse response = attributes.getResponse();
            String token = request.getHeader("Authorization");
            String key = token + "-" + request.getServletPath();
            // 如果缓存中有这个url视为重复提交
            long time = System.currentTimeMillis() + 5000;
            String lockKey = "weddingExpo:repeatSubmit:" + key;
            if (redisUtils.get(key) == null&&redisLock.lock(lockKey, String.valueOf(time))) {
                obj = pjp.proceed();
                redisUtils.set(key, 0, 5, TimeUnit.SECONDS);
                redisLock.unlock(lockKey, String.valueOf(time));
                return obj;
            } else {
                _log.info("msg={}", "repeat commit error");
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                response.setContentType("application/json;charset=UTF-8");
                response.getOutputStream().write(buildFailureMsg("repeat commit error").toString().getBytes("utf-8"));
                return obj;
            }
        } catch (final Throwable e) {
            _log.info("msg={}", "repeat commit error");
            return buildFailureMsg("repeat commit error").toJSONString();
        }
    }
}
