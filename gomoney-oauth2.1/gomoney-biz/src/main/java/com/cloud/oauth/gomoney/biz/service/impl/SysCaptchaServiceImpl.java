package com.cloud.oauth.gomoney.biz.service.impl;

import com.cloud.oauth.gomoney.api.entity.sys.SysCaptcha;
import com.cloud.oauth.gomoney.biz.common.utils.RRException;
import com.cloud.oauth.gomoney.biz.common.utils.RedisUtil;
import com.cloud.oauth.gomoney.biz.mapper.SysCaptchaMapper;
import com.cloud.oauth.gomoney.biz.service.SysCaptchaService;
import com.google.code.kaptcha.Producer;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;

@Service
@Transactional
@RequiredArgsConstructor
public class SysCaptchaServiceImpl implements SysCaptchaService {

    private final Producer producer;

    private final SysCaptchaMapper sysCaptchaMapper;

    private final RedisUtil redisUtil;

    @Override
    public void smsCode(String mobile) {
        String code = producer.createText();
        System.out.println("**************");
        System.out.println("**************");
        System.out.println("**************");
        System.out.println(code);
        System.out.println("**************");
        System.out.println("**************");
        System.out.println("**************");
        redisUtil.set(mobile,code,300);
    }

    public String sendCode(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            throw new RRException("uuid不能为空");
        }
        String code = producer.createText();
        redisUtil.set(uuid,code,300);
        return code;
    }

    public BufferedImage getCaptcha(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            throw new RRException("uuid不能为空");
        }
        //生成文字验证码
        String code = producer.createText();
        redisUtil.set(uuid,code,300);
        System.out.println("**************");
        System.out.println("**************");
        System.out.println("**************");
        System.out.println(code);
        System.out.println("**************");
        System.out.println("**************");
        System.out.println("**************");
        return producer.createImage(code);
    }

    public boolean validate(String uuid, String code) {
        if(redisUtil.get(uuid).toString().equalsIgnoreCase(code)){
            redisUtil.del(uuid);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public SysCaptcha findSysCaptchaById(String id) {
        return sysCaptchaMapper.selectById(id);
    }
}
