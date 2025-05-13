package com.cloud.oauth.gomoney.gateway.springdoc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import java.util.*;

//@Component
public class SpringDocResourcesConfig {

    private static final String SWAGGER2URL = "/v2/api-docs";

    //@Value("${spring.application.name}")
    private String self;

    //@Bean
 /*   public CommandLineRunner openApiGroups(RouteDefinitionLocator locator,
                                           SwaggerUiConfigParameters swaggerUiParameters) {

*//*        Set<String> routeHosts = new HashSet<>();

        // 获取所有可用的host：serviceId
        locator.getRoutes().filter(route -> route.getUri().getHost() != null)
                .filter(route -> !self.equals(route.getUri().getHost()))
                .subscribe(route -> routeHosts.add(route.getUri().getHost()));

        // 记录已经添加过的server
        return routeHosts.stream().map(instance ->
            "/" + instance.toLowerCase() + SWAGGER2URL
        ).forEach(swaggerUiParameters::addGroup);*//*
        return args -> locator
                .getRouteDefinitions().collectList().block()
                .stream()
                .map(RouteDefinition::getId)
                .filter(id -> !id.matches(".*-service"))
                .forEach(swaggerUiParameters::addGroup);
    }*/

}

