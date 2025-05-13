package com.cloud.oauth.gomoney.gateway.springdoc.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lengleng
 * @date 2022/3/26
 * <p>
 * swagger 3.0 展示
 */
@OpenAPIDefinition(
		info = @Info(
				title = "API Documentation",
				version = "1.0",
				description = "API documentation with Springdoc-openapi",
				contact = @Contact(name = "Developer Name", email = "developer@example.com")
		)
)
@Configuration
public class SpringDocConfiguration {


}
