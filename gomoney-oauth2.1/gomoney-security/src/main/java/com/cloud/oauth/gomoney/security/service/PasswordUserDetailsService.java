package com.cloud.oauth.gomoney.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface PasswordUserDetailsService {

    /**
     * load user by phone
     *
     * @param username username
     * @return userDetails
     * @throws UsernameNotFoundException not found user
     */
    UserDetails loadUserByUserName(String username, String password) throws UsernameNotFoundException;
}
