/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cloud.oauth.gomoney.security.config;

import com.cloud.oauth.gomoney.security.component.GomoneyBearerTokenExtractor;
import com.cloud.oauth.gomoney.security.component.PermitAllUrlProperties;
import com.cloud.oauth.gomoney.security.component.ResourceAuthExceptionEntryPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Joe Grandja
 * @since 0.0.1
 */
@Slf4j
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class ResourceServerConfig {

	private final OpaqueTokenIntrospector customOpaqueTokenIntrospector;

	private final ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;

	private final GomoneyBearerTokenExtractor gomoneyBearerTokenExtractor;

	private final PermitAllUrlProperties permitAllUrl;

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		AntPathRequestMatcher[] requestMatchers = permitAllUrl.getUrls()
				.stream()
				.map(AntPathRequestMatcher::new)
				.toList()
				.toArray(new AntPathRequestMatcher[] {});

		http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
						.requestMatchers(requestMatchers)
						.permitAll().anyRequest()
						.authenticated())
				.oauth2ResourceServer(
						oauth2 ->
								oauth2.opaqueToken(token ->
												token.introspector(customOpaqueTokenIntrospector))
										.authenticationEntryPoint(resourceAuthExceptionEntryPoint)
										.bearerTokenResolver(gomoneyBearerTokenExtractor))
				.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
				.csrf(AbstractHttpConfigurer::disable);
		return http.build();
	}

}
