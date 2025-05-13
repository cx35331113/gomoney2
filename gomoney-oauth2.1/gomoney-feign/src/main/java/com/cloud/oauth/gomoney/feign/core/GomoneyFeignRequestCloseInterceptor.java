package com.cloud.oauth.gomoney.feign.core;

import feign.RequestInterceptor;
import org.springframework.http.HttpHeaders;

public class GomoneyFeignRequestCloseInterceptor implements RequestInterceptor {

    @Override
    public void apply(feign.RequestTemplate template) {
        template.header(HttpHeaders.CONNECTION, "close");
    }


}
