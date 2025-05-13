package com.cloud.oauth.gomoney.security.service;

/**
 * @author n1
 */
public interface CaptchaService {

    boolean verifyCaptcha(String uuid, String rawCode);
}
