package com.cloud.oauth.gomoney.biz.common.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.security.core.context.SecurityContextHolder;


public class UserLogConfig extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if (username != null) {
                return username;
            } else {
                return null;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }
}
