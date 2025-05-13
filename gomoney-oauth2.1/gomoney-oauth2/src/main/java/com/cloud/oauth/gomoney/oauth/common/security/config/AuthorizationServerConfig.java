/*
 * Copyright 2020-2022 the original author or authors.
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

import com.cloud.oauth.gomoney.core.constant.SecurityConstants;
import com.cloud.oauth.gomoney.oauth.common.security.CustomeOAuth2AccessTokenGenerator;
import com.cloud.oauth.gomoney.oauth.common.security.core.CustomeOAuth2RefreshTokenGenerator;
import com.cloud.oauth.gomoney.oauth.common.security.core.CustomeOAuth2TokenCustomizer;
import com.cloud.oauth.gomoney.oauth.common.security.core.FormIdentityLoginConfigurer;
import com.cloud.oauth.gomoney.oauth.common.security.core.GomoneyAuthenticationProvider;
import com.cloud.oauth.gomoney.oauth.common.security.support.handler.OAurh2LoginAuthenticationSuccessHandler;
import com.cloud.oauth.gomoney.oauth.common.security.support.handler.OAuth2AuthenticationFailureEventHandler;
import com.cloud.oauth.gomoney.oauth.common.security.support.handler.OAuth2AuthenticationSuccessEventHandler;
import com.cloud.oauth.gomoney.oauth.common.security.support.password.CaptchaAuthenticationConverter;
import com.cloud.oauth.gomoney.oauth.common.security.support.password.CaptchaOAuth2AuthenticationProvider;
import com.cloud.oauth.gomoney.oauth.common.security.support.sms.SMSAuthenticationConverter;
import com.cloud.oauth.gomoney.oauth.common.security.support.sms.SMSOAuth2AuthenticationProvider;
import com.cloud.oauth.gomoney.security.service.SMSUserDetailsService;
import com.cloud.oauth.gomoney.security.service.impl.CaptchaServiceImpl;
import com.cloud.oauth.gomoney.security.service.impl.PasswordUserDetailsServiceImpl;
import com.cloud.oauth.gomoney.security.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.*;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.authentication.DelegatingAuthenticationConverter;

import java.util.Arrays;

/**
 * @author Joe Grandja
 * @author Daniel Garnier-Moiroux
 */
@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfig {
    private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";

    private final OAuth2AuthorizationService authorizationService;

    private final CaptchaServiceImpl captchService;

    private final PasswordUserDetailsServiceImpl passwordUserDetailsService;

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
 /*       OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        http.with(authorizationServerConfigurer.tokenEndpoint((tokenEndpoint) -> {// 个性化认证授权端点
                    tokenEndpoint.accessTokenRequestConverter(accessTokenRequestConverter()) // 注入自定义的授权认证Converter
                            .accessTokenResponseHandler(new OAurh2LoginAuthenticationSuccessHandler()) // 登录成功处理器
                            .errorResponseHandler(new OAuth2AuthenticationFailureEventHandler());// 登录失败处理器
                }).clientAuthentication(oAuth2ClientAuthenticationConfigurer -> // 个性化客户端认证
                        oAuth2ClientAuthenticationConfigurer.errorResponseHandler(new OAuth2AuthenticationFailureEventHandler()))// 处理客户端认证异常
                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint// 授权码端点个性化confirm页面
                        .consentPage(CUSTOM_CONSENT_PAGE_URI)), Customizer.withDefaults());

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

        RequestMatcher endpointsMatcher = authorizationServerConfigurer
                .getEndpointsMatcher();
        http.userDetailsService(userDetailsService)
                .authorizeHttpRequests(
                        authorizeRequests ->{
                                authorizeRequests.requestMatchers(requestMatchers).permitAll();
                            authorizeRequests.anyRequest().authenticated();
                        })
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .with(authorizationServerConfigurer
                        .authorizationService(authorizationService)// redis存储token的实现
                        .authorizationServerSettings(AuthorizationServerSettings.builder()
                                .issuer(SecurityConstants.PROJECT_LICENSE)
                                .build()), Customizer.withDefaults());
        DefaultSecurityFilterChain securityFilterChain = http.build();
*/
        // 配置授权服务器的安全策略，只有/oauth2/**的请求才会走如下的配置
        http.securityMatcher("/oauth2/**");
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

        http.userDetailsService(userDetailsService).with(authorizationServerConfigurer.tokenEndpoint((tokenEndpoint) -> {// 个性化认证授权端点
                            tokenEndpoint.accessTokenRequestConverter(accessTokenRequestConverter()) // 注入自定义的授权认证Converter
                                    .accessTokenResponseHandler(new OAuth2AuthenticationSuccessEventHandler()) // 登录成功处理器
                                    .errorResponseHandler(new OAuth2AuthenticationFailureEventHandler());// 登录失败处理器
                        }).clientAuthentication(oAuth2ClientAuthenticationConfigurer -> // 个性化客户端认证
                                oAuth2ClientAuthenticationConfigurer.errorResponseHandler(new OAuth2AuthenticationFailureEventHandler()))// 处理客户端认证异常
                        .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint// 授权码端点个性化confirm页面
                                .consentPage(SecurityConstants.CUSTOM_CONSENT_PAGE_URI)), Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated());

        // 设置 Token 存储的策略
        http.with(authorizationServerConfigurer.authorizationService(authorizationService)// redis存储token的实现
                        .authorizationServerSettings(
                                AuthorizationServerSettings.builder().issuer(SecurityConstants.PROJECT_LICENSE).build()),
                Customizer.withDefaults());

        // 设置授权码模式登录页面
        http.with(new FormIdentityLoginConfigurer(), Customizer.withDefaults());
        DefaultSecurityFilterChain securityFilterChain = http.build();
                // 注入自定义授权模式实现
        addCustomOAuth2GrantAuthenticationProvider(http);

        return securityFilterChain;
    }



    /**
     * request -> xToken 注入请求转换器
     * @return DelegatingAuthenticationConverter
     */
    private AuthenticationConverter accessTokenRequestConverter() {
        return new DelegatingAuthenticationConverter(Arrays.asList(
                new CaptchaAuthenticationConverter(),
                new SMSAuthenticationConverter(),
                new OAuth2RefreshTokenAuthenticationConverter(),
                new OAuth2ClientCredentialsAuthenticationConverter(),
                new OAuth2AuthorizationCodeAuthenticationConverter(),
                new OAuth2AuthorizationCodeRequestAuthenticationConverter()));
    }


    private final SMSUserDetailsService smsUserDetailsService;
    /**
     * 注入授权模式实现提供方
     *
     * 1. 密码模式 </br>
     * 2. 短信登录 </br>
     *
     */
    @SuppressWarnings("unchecked")
    private void addCustomOAuth2GrantAuthenticationProvider(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);

        CustomeOAuth2AccessTokenGenerator customeOAuth2AccessTokenGenerator = new CustomeOAuth2AccessTokenGenerator();

        customeOAuth2AccessTokenGenerator.setAccessTokenCustomizer(new CustomeOAuth2TokenCustomizer());

        CustomeOAuth2RefreshTokenGenerator customeOAuth2RefreshTokenGenerator=new CustomeOAuth2RefreshTokenGenerator();

        DelegatingOAuth2TokenGenerator delegatingOAuth2TokenGenerator = new DelegatingOAuth2TokenGenerator(customeOAuth2AccessTokenGenerator, new OAuth2RefreshTokenGenerator());

        http.authenticationProvider(
                new GomoneyAuthenticationProvider());

        http.authenticationProvider(
                new CaptchaOAuth2AuthenticationProvider(
                passwordUserDetailsService,
                captchService,
                authorizationService,
                delegatingOAuth2TokenGenerator,
                authenticationManager,
                customeOAuth2RefreshTokenGenerator));

        http.authenticationProvider(
                new SMSOAuth2AuthenticationProvider(
                        smsUserDetailsService,
                        authorizationService,
                        delegatingOAuth2TokenGenerator,
                        authenticationManager,
                        customeOAuth2RefreshTokenGenerator));
    }

    @Bean
    public OAuth2TokenGenerator oAuth2TokenGenerator() {
        CustomeOAuth2AccessTokenGenerator accessTokenGenerator = new CustomeOAuth2AccessTokenGenerator();
        // 注入Token 增加关联用户信息
        accessTokenGenerator.setAccessTokenCustomizer(new CustomeOAuth2TokenCustomizer());
        return new DelegatingOAuth2TokenGenerator(accessTokenGenerator, new OAuth2RefreshTokenGenerator());
    }

}
