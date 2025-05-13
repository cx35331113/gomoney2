package com.cloud.oauth.gomoney.ai;

import com.cloud.oauth.gomoney.feign.annotation.EnableGomoneyFeignClients;
import com.cloud.oauth.gomoney.security.config.ResourceServerAutoConfiguration;
import com.cloud.oauth.gomoney.security.config.ResourceServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@EnableGomoneyFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@Import({ResourceServerAutoConfiguration.class, ResourceServerConfig.class})
public class AIApplication {
    public static void main(String[] args) {
        SpringApplication.run(AIApplication.class, args);
    }
}
