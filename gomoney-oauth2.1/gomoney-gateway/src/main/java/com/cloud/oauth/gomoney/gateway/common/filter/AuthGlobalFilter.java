package com.cloud.oauth.gomoney.gateway.common.filter;

import com.cloud.oauth.gomoney.gateway.common.auth.AuthService;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private AuthService authService;

    public AuthGlobalFilter(AuthService authService) {
        this.authService = authService;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String reqPath = exchange.getRequest().getURI().getPath();
        String token = exchange.getRequest().getHeaders().getFirst("token");
        if (!authService.verifyToken(reqPath, token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange.mutate().request(exchange.getRequest()).build());
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
