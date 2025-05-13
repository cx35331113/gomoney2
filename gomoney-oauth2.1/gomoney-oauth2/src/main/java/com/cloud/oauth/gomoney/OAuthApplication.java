package com.cloud.oauth.gomoney;

import com.cloud.oauth.gomoney.feign.annotation.EnableGomoneyFeignClients;
import com.cloud.oauth.swagger.annotation.EnableGoMoneyDoc;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableGoMoneyDoc(value = "admin")
@EnableGomoneyFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class OAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthApplication.class, args);
    }
}
