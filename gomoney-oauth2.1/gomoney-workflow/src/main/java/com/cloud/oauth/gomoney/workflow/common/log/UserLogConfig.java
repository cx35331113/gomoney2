package com.cloud.oauth.gomoney.workflow.common.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.Jedis;


public class UserLogConfig extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        try {
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            if (sra.getRequest().getHeader("token") != null) {
                String token = sra.getRequest().getHeader("token");
                Jedis jedis = new Jedis("pig-redis", 6379);
                return jedis.get("\"" + token + "\"");
            } else {
                return null;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }
}
