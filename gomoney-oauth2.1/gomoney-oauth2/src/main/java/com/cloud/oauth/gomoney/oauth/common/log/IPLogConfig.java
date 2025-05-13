package com.cloud.oauth.gomoney.oauth.common.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class IPLogConfig extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent event) {
        try {
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            //return IPKit.getIpAddrByRequest(sra.getRequest());
            return sra.getRequest().getHeader("ip");
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }
}
