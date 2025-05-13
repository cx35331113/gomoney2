package com.cloud.oauth.gomoney.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface SMSUserDetailsService {
    boolean checkMobileAndCode(String mobile, String code) throws UsernameNotFoundException;
}
