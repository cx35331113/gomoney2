package com.cloud.oauth.gomoney.biz.service;


import com.cloud.oauth.gomoney.api.entity.sys.SysCaptcha;

import java.awt.image.BufferedImage;

public interface SysCaptchaService {

    void smsCode(String mobile);

    String sendCode(String uuid);

    BufferedImage getCaptcha(String uuid);

    boolean validate(String uuid, String code);

    SysCaptcha findSysCaptchaById(String id);
}
