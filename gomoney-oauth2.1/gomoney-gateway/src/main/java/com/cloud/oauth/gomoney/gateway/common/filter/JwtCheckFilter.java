package com.cloud.oauth.gomoney.gateway.common.filter;

import cn.hutool.core.util.StrUtil;
import com.cloud.oauth.gomoney.gateway.common.util.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component
public class JwtCheckFilter
        //implements GlobalFilter, Ordered
{

   /* //@Autowired
    private RedisUtil redisUtil;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    //放行的路径
    private static List<String> passUrl(){
        List<String> urls=new ArrayList<>();
        urls.add("/gomoney-oauth2/oauth2/**");
        urls.add("/gomoney-auth/sys/authentication/**");
        urls.add("/gomoney-auth/instances");
        urls.add("/gomoney-auth/instances/**");
        urls.add("/gomoney-auth/actuator/**");
        urls.add("/gomoney-auth/login");
        urls.add("/gomoney-auth/v2/api-docs");
        urls.add("/gomoney-auth/v3/api-docs");
        urls.add("/gomoney-biz/v2/api-docs");
        urls.add("/gomoney-biz/v3/api-docs");
        //urls.add("/gomoney-biz/sys/biz/insert");
        urls.add("/gomoney-biz/instances");
        urls.add("/gomoney-biz/instances/**");
        urls.add("/gomoney-biz/actuator/**");
        //urls.add("/gomoney-file/center/file/**");
        urls.add("/gomoney-file/instances");
        urls.add("/gomoney-file/instances/**");
        urls.add("/gomoney-file/actuator/**");
        urls.add("/gomoney-file/v2/api-docs");
        urls.add("/gomoney-file/v3/api-docs");
        urls.add("/monitor/**");
        return urls;
    }

    public static final List<String> ALLOW_PATH = passUrl();

    public static final String AUTHORIZATION = "Authorization";

    *//*** 描述: 检验请求是否携带 token
     * ** @param exchange:
     * * @param chain:
     * * @return reactor.core.publisher.Mono<java.lang.Void> *//*
    //@SneakyThrows
   // @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        boolean match = false;
        for (String pattern : ALLOW_PATH) {
            if (antPathMatcher.isPattern(pattern)) {
                match = antPathMatcher.match(pattern, path);
            } else {
                match = path.equals(pattern);
            }
            if(match){
                break;
            }
        }
        if (match) {
            return chain.filter(exchange);
        }//验证有没有 token
        List<String> list = request.getHeaders().get(AUTHORIZATION);
        if (!ObjectUtils.isEmpty(list)) {
            String authorization = list.get(0);
            String token = authorization.replaceAll("Bearer ","");
            if (!StrUtil.isEmpty(token) && redisUtil.hasKey("oauth:jwt:" + token)) {
                return chain.filter(exchange);
            }
        }
        ServerHttpResponse response = exchange.getResponse();
        //如果没有 token 或者 redis 里面没有 token，就返回 401
        Map<String, Object> map = new HashMap<>();
        map.put("code", HttpStatus.UNAUTHORIZED.value());
        map.put("msg", "非法访问");
        response.getHeaders().add("content-Type", "application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(map);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }*/
}