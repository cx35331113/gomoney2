package com.cloud.oauth.gomoney.oauth.common.security.support.password;

import com.cloud.oauth.gomoney.oauth.common.security.core.CustomeOAuth2RefreshTokenGenerator;
import com.cloud.oauth.gomoney.oauth.common.security.support.base.OAuth2ResourceBaseAuthenticationProider;
import com.cloud.oauth.gomoney.security.service.CaptchaService;
import com.cloud.oauth.gomoney.security.service.PasswordUserDetailsService;
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
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;

import java.util.Map;


public class CaptchaOAuth2AuthenticationProvider extends OAuth2ResourceBaseAuthenticationProider<CaptchaOAuth2AuthenticationToken> {

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final PasswordUserDetailsService captchaUserDetailsService;
    private final CaptchaService captchaService;
    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private static final Logger LOGGER = LogManager.getLogger(CaptchaOAuth2AuthenticationProvider.class);

    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-4.1.2.1";

    private OAuth2AuthorizationService authorizationService;

    private OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    private AuthenticationManager authenticationManager;

    private CustomeOAuth2RefreshTokenGenerator customeOAuth2RefreshTokenGenerator;

    public CaptchaOAuth2AuthenticationProvider(PasswordUserDetailsService captchaUserDetailsService,
                                               CaptchaService captchaService,
                                               OAuth2AuthorizationService authorizationService,
                                               OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
                                               AuthenticationManager authenticationManager,
                                               CustomeOAuth2RefreshTokenGenerator customeOAuth2RefreshTokenGenerator
                                                ) {
        super(authenticationManager,authorizationService,tokenGenerator);
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.captchaUserDetailsService = captchaUserDetailsService;
        this.captchaService = captchaService;
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
        this.customeOAuth2RefreshTokenGenerator = customeOAuth2RefreshTokenGenerator;
    }

    public UsernamePasswordAuthenticationToken buildToken(Map<String, Object> reqParameters) {
        //校验验证码
        String captcha = reqParameters.get("captcha").toString();
        String uuid = reqParameters.get("uuid").toString();
        if(captchaService.verifyCaptcha(uuid,captcha)){
            String username = (String) reqParameters.get(OAuth2ParameterNames.USERNAME);
            String password = (String) reqParameters.get(OAuth2ParameterNames.PASSWORD);
            return new UsernamePasswordAuthenticationToken(username, password);
        } else {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The verification code does not match.", ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CaptchaOAuth2AuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(captchaUserDetailsService, "captchaUserDetailsService must not be null");
        Assert.notNull(captchaService, "captchaService must not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public void checkClient(RegisteredClient registeredClient) {
        assert registeredClient != null;
        if (!registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.PASSWORD)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }
    }
}
