package com.cloud.oauth.gomoney.oauth.common.security.support.password;

import com.cloud.oauth.gomoney.oauth.common.security.support.base.OAuth2ResourceBaseAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.*;

public class CaptchaOAuth2AuthenticationToken extends OAuth2ResourceBaseAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";

    public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";

    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    public static final String SPRING_SECURITY_FORM_UUID_KEY = "uuid";

    private AuthorizationGrantType authorizationGrantType;

    private Authentication authentication;

    private Set<String> scopes;

    private Map<String, Object> params;

    private String phone;
    private String pwd;
    private String uuid;
    private String captcha;

    public CaptchaOAuth2AuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                            Authentication authentication,
                                            Set<String> scopes,
                                            Map<String, Object> params) {
        super(authorizationGrantType,authentication,scopes,params);

        this.authorizationGrantType = authorizationGrantType;
        this.authentication = authentication;
        this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
        this.phone = params.get(SPRING_SECURITY_FORM_USERNAME_KEY).toString();
        this.pwd = params.get(SPRING_SECURITY_FORM_PASSWORD_KEY).toString();
        this.uuid = params.get(SPRING_SECURITY_FORM_UUID_KEY).toString();
        this.captcha = params.get(SPRING_SECURITY_FORM_CAPTCHA_KEY).toString();
        this.params = params;
        setAuthenticated(false);
    }

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public CaptchaOAuth2AuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                            Authentication authentication,
                                            Set<String> scopes,
                                            Map<String, Object> params,
                                            Collection<? extends GrantedAuthority> authorities) {
        super(authorizationGrantType,authentication,scopes,params);
        this.authorizationGrantType = authorizationGrantType;
        this.authentication = authentication;
        this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
        this.params = params;
        this.phone = params.get(SPRING_SECURITY_FORM_USERNAME_KEY).toString();
        this.pwd = params.get(SPRING_SECURITY_FORM_PASSWORD_KEY).toString();
        this.uuid = params.get(SPRING_SECURITY_FORM_UUID_KEY).toString();
        this.captcha = params.get(SPRING_SECURITY_FORM_CAPTCHA_KEY).toString();
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.captcha;
    }

    @Override
    public Object getPrincipal() {
        return this.authentication;
    }

    public Object getPhone() {
        return this.phone;
    }

    public Object getPwd() {
        return this.pwd;
    }

    public Object getUUID() {
        return this.uuid;
    }

    public Set<String> getScopes() {
        return this.scopes;
    }

    public AuthorizationGrantType getAuthorizationGrantType() {
        return this.authorizationGrantType;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        captcha = null;
    }
}
