package com.cloud.oauth.gomoney.gateway.common.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.alibaba.fastjson.JSONObject;
import com.cloud.oauth.gomoney.core.utils.MyLog;
import com.cloud.oauth.gomoney.gateway.common.util.RedisUtil;
import com.cloud.oauth.gomoney.gateway.swagger.handle.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;
import reactor.core.publisher.Mono;

import java.util.*;

@Configuration
public class GatewayConfigurer {

    @Autowired
    private RedisUtil redisUtil;

    private MyLog _log = MyLog.getLog(GatewayConfigurer.class);

    public static final String OAUTH_PREFIX = "oauth:jwt:";// pc端前缀

    public static final String OAUTH_PREFIX_REFRESH = "oauth:jwt:refresh:";// pc端刷新token前缀

    //@Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes().route("gomoney-oauth2", r -> r.path("/gomoney-oauth2/oauth2/**")
                .filters(f -> f.modifyResponseBody(String.class, String.class, (exchange, s) -> {
                    String path = exchange.getRequest().getURI().getPath();
                    if ("/gomoney-oauth2/oauth2/token".equals(path)) {
                        JSONObject jsonObject = JSONObject.parseObject(s);
                        _log.info("***************************************");
                        _log.info("***************************************");
                        _log.info("***************************************");
                        _log.info(jsonObject.getString("access_token"));
                        String t = "";//JwtTokenUtil.parseToken(jsonObject.getString("access_token"));
                        _log.info(t);
                        _log.info("***************************************");
                        _log.info("***************************************");
                        _log.info("***************************************");
                        JSONObject sysUser = jsonObject.parseObject(jsonObject.parseObject(jsonObject.getString("info")).get("user").toString());
                        String userId = sysUser.get("userId").toString();
                        Map<String, Object> user = new HashMap<>();
                        user.put("userId", sysUser.get("userId"));
                        user.put("userName", sysUser.get("userName"));
                        user.put("mobile", sysUser.get("mobile"));
                        user.put("state", sysUser.get("state"));
                        user.put("realname", sysUser.get("realname"));
                        user.put("userEmail", sysUser.get("userEmail"));
                        user.put("permsSet", sysUser.get("permsSet"));
                        user.put("optrdate", sysUser.get("optrdate"));
                        user.put("roleIdList", sysUser.get("roleIdList"));
                        String access_token = OAUTH_PREFIX + jsonObject.getString("access_token");
                        if (redisUtil.getAll("*") != null) {
                            Set<Object> keys = redisUtil.getAll("*");
                            for (Object k : keys) {
                                try {
                                    if (redisUtil.get(k.toString()).toString().equals(userId)) {
                                        String access = k.toString();
                                        redisUtil.del(access);
                                        String refresh_token = OAUTH_PREFIX_REFRESH + access.replaceAll(OAUTH_PREFIX, "");
                                        redisUtil.del(refresh_token);
                                        break;
                                    }
                                } catch (Exception e) {
                                    continue;
                                }
                            }
                        }
                        Long expires_in = jsonObject.getLong("expires_in") != null ? jsonObject.getLong("expires_in") : 0;
                        String refresh_token = jsonObject.getString("refresh_token");
                        System.out.println(jsonObject);
                        System.out.println("expires_in" + expires_in);
                        redisUtil.set(access_token, userId,
                                expires_in);
                        redisUtil.set(userId, user,
                                expires_in);
                        redisUtil.set(OAUTH_PREFIX_REFRESH + access_token.replaceAll(OAUTH_PREFIX, ""), refresh_token, expires_in + 1800);
                    }
                    return Mono.just(s);
                })).uri("lb://gomoney-oauth2")).build();
    }

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayConfigurer(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                             ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }
    @Bean
    public GlobalExceptionHandler globalExceptionHandler(ObjectMapper objectMapper) {
        return new GlobalExceptionHandler(objectMapper);
    }

    @Bean
    @Order(-1)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }
    @PostConstruct
    public void doInit() {
        initGatewayRules();
    }


    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        rules.add(new GatewayFlowRule("gomoney-auth2")
                .setCount(10) // 限流阈值
                .setIntervalSec(1) // 统计时间窗口，单位是秒，默认是 1 秒
        );
        rules.add(new GatewayFlowRule("gomoney-biz")
                .setCount(10) // 限流阈值
                .setIntervalSec(1) // 统计时间窗口，单位是秒，默认是 1 秒
        );
        rules.add(new GatewayFlowRule("gomoney-file")
                .setCount(10) // 限流阈值
                .setIntervalSec(1) // 统计时间窗口，单位是秒，默认是 1 秒
        );
        GatewayRuleManager.loadRules(rules);
    }
}
