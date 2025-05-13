package com.cloud.oauth.gomoney.oauth.common.security.support.sms;

import com.cloud.oauth.gomoney.core.constant.SecurityConstants;
import com.cloud.oauth.gomoney.oauth.common.security.core.CustomeOAuth2RefreshTokenGenerator;
import com.cloud.oauth.gomoney.oauth.common.security.support.base.OAuth2ResourceBaseAuthenticationProider;
import com.cloud.oauth.gomoney.oauth.common.security.support.password.CaptchaOAuth2AuthenticationProvider;
import com.cloud.oauth.gomoney.oauth.common.security.support.password.CaptchaOAuth2AuthenticationToken;
import com.cloud.oauth.gomoney.security.service.SMSUserDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;

import java.util.Map;

public class SMSOAuth2AuthenticationProvider extends OAuth2ResourceBaseAuthenticationProider<SMSOAuth2AuthenticationToken> {

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    private final SMSUserDetailsService smsUserDetailsService;

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private static final Logger LOGGER = LogManager.getLogger(CaptchaOAuth2AuthenticationProvider.class);

    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-4.1.2.1";

    private OAuth2AuthorizationService authorizationService;

    private OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    private AuthenticationManager authenticationManager;

    private CustomeOAuth2RefreshTokenGenerator customeOAuth2RefreshTokenGenerator;

    public SMSOAuth2AuthenticationProvider(SMSUserDetailsService smsUserDetailsService,
                                               OAuth2AuthorizationService authorizationService,
                                               OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
                                               AuthenticationManager authenticationManager,
                                               CustomeOAuth2RefreshTokenGenerator customeOAuth2RefreshTokenGenerator
    ) {
        super(authenticationManager,authorizationService,tokenGenerator);
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.smsUserDetailsService = smsUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
        this.customeOAuth2RefreshTokenGenerator = customeOAuth2RefreshTokenGenerator;
    }

    public UsernamePasswordAuthenticationToken buildToken(Map<String, Object> reqParameters) {
        String mobile = (String) reqParameters.get(SecurityConstants.SMS_PARAMETER_NAME);
        String code = (String) reqParameters.get(SecurityConstants.SMS_PARAMETER_CODE);
        if (smsUserDetailsService.checkMobileAndCode(mobile, code)){
            return new UsernamePasswordAuthenticationToken(mobile, code);
        } else {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The mobile verification code does not match.", ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SMSOAuth2AuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(smsUserDetailsService, "smsUserDetailsService must not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public void checkClient(RegisteredClient registeredClient) {
        assert registeredClient != null;
        if (!registeredClient.getAuthorizationGrantTypes().contains(new AuthorizationGrantType(SecurityConstants.MOBILE))) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }
    }
}
