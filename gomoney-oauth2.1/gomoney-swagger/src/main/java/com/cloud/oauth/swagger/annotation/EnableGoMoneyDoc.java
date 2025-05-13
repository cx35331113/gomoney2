package com.cloud.oauth.swagger.annotation;

import com.cloud.oauth.gomoney.core.factory.YamlPropertySourceFactory;
import com.cloud.oauth.swagger.support.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import com.cloud.oauth.swagger.config.OpenAPIDefinitionImportSelector;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableConfigurationProperties(SwaggerProperties.class)
@Import(OpenAPIDefinitionImportSelector.class)
@PropertySource(value = "classpath:openapi-config.yaml", factory = YamlPropertySourceFactory.class)
public @interface EnableGoMoneyDoc {

    /**
     * 网关路由前缀
     * @return String
     */
    String value();

    /**
     * 是否是微服务架构
     * @return true
     */
    boolean isMicro() default true;
}
