package com.cloud.oauth.gomoney.security.component;

import cn.hutool.extra.spring.SpringUtil;
import com.cloud.oauth.gomoney.security.model.GoUser;
import com.cloud.oauth.gomoney.security.service.GoUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.security.Principal;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class GomoneyCustomOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final OAuth2AuthorizationService authorizationService;

    public OAuth2AuthenticatedPrincipal introspect(String token) {
        OAuth2Authorization oldAuthorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        if (Objects.isNull(oldAuthorization)) {
            throw new InvalidBearerTokenException(token);
        }
        Map<String, GoUserDetailsService> userDetailsServiceMap = SpringUtil
                .getBeansOfType(GoUserDetailsService.class);

        Optional<GoUserDetailsService> optional = userDetailsServiceMap.values().stream()
                .filter(service -> service.support(Objects.requireNonNull(oldAuthorization).getRegisteredClientId(),
                        oldAuthorization.getAuthorizationGrantType().getValue()))
                .max(Comparator.comparingInt(Ordered::getOrder));
        UserDetails userDetails = null;
        try {
            Object principal = Objects.requireNonNull(oldAuthorization).getAttributes().get(Principal.class.getName());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
            Object tokenPrincipal = usernamePasswordAuthenticationToken.getPrincipal();
            userDetails = optional.get().loadUserByUser((GoUser) tokenPrincipal);
        }
        catch (UsernameNotFoundException notFoundException) {
            log.warn("用户不不存在 {}", notFoundException.getLocalizedMessage());
            throw notFoundException;
        }
        catch (Exception ex) {
            log.error("资源服务器 introspect Token error {}", ex.getLocalizedMessage());
        }
        return (GoUser) userDetails;
    }
}
