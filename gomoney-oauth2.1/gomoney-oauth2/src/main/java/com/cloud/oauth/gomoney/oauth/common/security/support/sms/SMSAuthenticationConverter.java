package com.cloud.oauth.gomoney.oauth.common.security.support.sms;

import com.cloud.oauth.gomoney.core.constant.SecurityConstants;
import com.cloud.oauth.gomoney.oauth.common.security.support.base.OAuth2ResourceBaseAuthenticationConverter;
import com.cloud.oauth.gomoney.oauth.common.security.support.password.CaptchaOAuth2AuthenticationToken;
import com.cloud.oauth.gomoney.security.utils.OAuth2EndpointUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

public class SMSAuthenticationConverter extends OAuth2ResourceBaseAuthenticationConverter
        <SMSOAuth2AuthenticationToken> {

    /**
     * 校验扩展参数 密码模式密码必须不为空
     *
     * @param request 参数列表
     */
    @Override
    public void checkParams(HttpServletRequest request) {
        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);
        // username (REQUIRED)
        String mobile = parameters.getFirst(SecurityConstants.SMS_PARAMETER_NAME);
        if (!StringUtils.hasText(mobile) || parameters.get(SecurityConstants.SMS_PARAMETER_NAME).size() != 1) {
            OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST,
                    SecurityConstants.SMS_PARAMETER_NAME,
                    OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        // password (REQUIRED)
        String code = parameters.getFirst(SecurityConstants.SMS_PARAMETER_CODE);
        if (!StringUtils.hasText(code) || parameters.get(SecurityConstants.SMS_PARAMETER_CODE).size() != 1) {
            OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST,
                    SecurityConstants.SMS_PARAMETER_CODE,
                    OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }
    }

    @Override
    public SMSOAuth2AuthenticationToken buildToken(Authentication clientPrincipal,
                                                   Set requestedScopes,
                                                   Map additionalParameters) {
        return new SMSOAuth2AuthenticationToken(
                new AuthorizationGrantType(SecurityConstants.MOBILE),
                clientPrincipal,
                requestedScopes,
                additionalParameters);
    }

    /**
     * 支持短信模式
     *
     * @param grantType 授权类型
     */
    @Override
    public boolean support(String grantType) {
        return SecurityConstants.MOBILE.equals(grantType);
    }
}
