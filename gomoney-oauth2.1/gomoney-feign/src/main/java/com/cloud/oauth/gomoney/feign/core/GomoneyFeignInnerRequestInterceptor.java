package com.cloud.oauth.gomoney.feign.core;

import com.cloud.oauth.gomoney.core.constant.SecurityConstants;
import com.cloud.oauth.gomoney.feign.annotation.NoToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;

public class GomoneyFeignInnerRequestInterceptor implements RequestInterceptor, Ordered {

    /**
     * Called for every request. Add data using methods on the supplied
     * {@link RequestTemplate}.
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        Method method = template.methodMetadata().method();
        NoToken noToken = method.getAnnotation(NoToken.class);
        if (noToken != null) {
            template.header(SecurityConstants.FROM, SecurityConstants.FROM_IN);
        }
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
