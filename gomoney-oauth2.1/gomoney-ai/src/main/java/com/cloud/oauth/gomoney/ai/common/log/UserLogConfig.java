package com.cloud.oauth.gomoney.ai.common.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.Jedis;


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
