package com.cloud.oauth.gomoney.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /*@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("gomoney-oauth2", r -> r.path("/gomoney-oauth2/**")
                        .uri("lb://127.0.0.1"))
                .route("gomoney-biz", r -> r.path("/gomoney-biz/**")
                        .uri("lb://127.0.0.1"))
                .route("gomoney-file", r -> r.path("/gomoney-file/**")
                        .uri("lb://127.0.0.1"))
                .route("gomoney-workflow", r -> r.path("/gomoney-workflow/**")
                        .uri("lb://127.0.0.1"))
                .route("monitor", r -> r.path("/monitor/**")
                        .uri("lb://127.0.0.1"))
                .build();
    }*/
}
