package com.cloud.oauth.gomoney.oauth.common.security.support.sms;

import com.cloud.oauth.gomoney.oauth.common.security.support.base.OAuth2ResourceBaseAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.*;

public class SMSOAuth2AuthenticationToken extends OAuth2ResourceBaseAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";

    public static final String SPRING_SECURITY_FORM_CODE_KEY = "code";

    private AuthorizationGrantType authorizationGrantType;

    private Authentication authentication;

    private Set<String> scopes;

    private Map<String, Object> params;

    private String mobile;

    private String code;

    public SMSOAuth2AuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                        Authentication authentication,
                                        Set<String> scopes,
                                        Map<String, Object> params) {
        super(authorizationGrantType,authentication,scopes,params);

        this.authorizationGrantType = authorizationGrantType;
        this.authentication = authentication;
        this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
        this.mobile = params.get(SPRING_SECURITY_FORM_MOBILE_KEY).toString();
        this.code = params.get(SPRING_SECURITY_FORM_CODE_KEY).toString();
        this.params = params;
        setAuthenticated(false);
    }

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public SMSOAuth2AuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                        Authentication authentication,
                                        Set<String> scopes,
                                        Map<String, Object> params,
                                        Collection<? extends GrantedAuthority> authorities) {
        super(authorizationGrantType,authentication,scopes,params);
        this.authorizationGrantType = authorizationGrantType;
        this.authentication = authentication;
        this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
        this.params = params;
        this.mobile = params.get(SPRING_SECURITY_FORM_MOBILE_KEY).toString();
        this.code = params.get(SPRING_SECURITY_FORM_CODE_KEY).toString();
        super.setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return this.authentication;
    }

    public Object getMobile() {
        return this.mobile;
    }

    public Object getCode() {
        return this.code;
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
    }
}
