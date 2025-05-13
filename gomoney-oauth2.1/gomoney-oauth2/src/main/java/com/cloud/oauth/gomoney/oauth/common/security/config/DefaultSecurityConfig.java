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
package com.cloud.oauth.gomoney.oauth.common.security.config;

import com.cloud.oauth.gomoney.security.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Joe Grandja
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class DefaultSecurityConfig {

	private final UserDetailsServiceImpl userDetailsService;

	// @formatter:off
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		AntPathRequestMatcher[] requestMatchers = new AntPathRequestMatcher[] {
				AntPathRequestMatcher.antMatcher("/oauth2/token"),
				AntPathRequestMatcher.antMatcher("/sys/authentication/**"),
				AntPathRequestMatcher.antMatcher("/v2/api-docs"),
				AntPathRequestMatcher.antMatcher("/v3/api-docs"),
				AntPathRequestMatcher.antMatcher("/instances"),
				AntPathRequestMatcher.antMatcher("/instances/**"),
				AntPathRequestMatcher.antMatcher("/actuator/**"),
				AntPathRequestMatcher.antMatcher("/token/**"),
				AntPathRequestMatcher.antMatcher("/swagger-ui/**") };
		http.userDetailsService(userDetailsService)
			.authorizeHttpRequests(authorizeRequests ->
				authorizeRequests.requestMatchers(requestMatchers)
						.permitAll().anyRequest().authenticated()
			)
				.formLogin((formLogin) ->
						formLogin
								.usernameParameter("username")
								.passwordParameter("password")
								.loginPage("/authentication/login")
								.failureUrl("/authentication/login?failed")
								.loginProcessingUrl("/authentication/login/process")
				);
				//.loginPage("/token/login")
		;
		//http.authenticationProvider(new GomoneyAuthenticationProvider());
		return http.build();
	}

	/**
	 * 暴露静态资源
	 *
	 * https://github.com/spring-projects/spring-security/issues/10938
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Order(0)
	SecurityFilterChain resources(HttpSecurity http) throws Exception {
		AntPathRequestMatcher[] requestMatchers = new AntPathRequestMatcher[] {
				AntPathRequestMatcher.antMatcher("/actuator/**"),
				AntPathRequestMatcher.antMatcher("/css/**"),
				AntPathRequestMatcher.antMatcher("/error")};
		http.securityMatchers((matchers) -> matchers.requestMatchers(requestMatchers))
				.authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll())
				.requestCache(RequestCacheConfigurer::disable)
				.securityContext(SecurityContextConfigurer::disable)
				.sessionManagement(SessionManagementConfigurer::disable);
		return http.build();
	}
	// @formatter:on

	// @formatter:off
/*	@Bean
	UserDetailsService users() {
		var inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
		UserDetails user = User
				.withUsername("admin")
				.password(passwordEncoder().encode("123"))
				.roles("USER")
				.authorities("message.read")
				.build();
		inMemoryUserDetailsManager.createUser(user);
		return inMemoryUserDetailsManager;
	}*/
	// @formatter:on
}
