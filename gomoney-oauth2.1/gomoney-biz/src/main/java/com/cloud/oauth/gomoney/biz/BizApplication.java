package com.cloud.oauth.gomoney.biz;


import com.cloud.oauth.gomoney.feign.annotation.EnableGomoneyFeignClients;
import com.cloud.oauth.gomoney.security.config.ResourceServerAutoConfiguration;
import com.cloud.oauth.gomoney.security.config.ResourceServerConfig;
import com.cloud.oauth.swagger.annotation.EnableGoMoneyDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
@EnableGoMoneyDoc(value = "admin")
@EnableMethodSecurity
@EnableGomoneyFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@Import({ ResourceServerAutoConfiguration.class, ResourceServerConfig.class})
public class BizApplication {

    public static void main(String[] args) {
        SpringApplication.run(BizApplication.class, args);
    }
}
