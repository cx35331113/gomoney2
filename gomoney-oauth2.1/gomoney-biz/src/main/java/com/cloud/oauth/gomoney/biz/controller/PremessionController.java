package com.cloud.oauth.gomoney.biz.controller;

import com.cloud.oauth.gomoney.api.entity.sys.SysOAuthClientDetails;
import com.cloud.oauth.gomoney.api.entity.sys.SysUser;
import com.cloud.oauth.gomoney.api.form.LoginForm;
import com.cloud.oauth.gomoney.api.form.RefreshForm;
import com.cloud.oauth.gomoney.biz.common.utils.RedisUtil;
import com.cloud.oauth.gomoney.biz.service.impl.SysCaptchaServiceImpl;
import com.cloud.oauth.gomoney.biz.service.impl.SysOAuthClientDetailsServiceImpl;
import com.cloud.oauth.gomoney.biz.service.impl.SysUserServiceImpl;
import com.cloud.oauth.gomoney.core.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;

import jakarta.servlet.ServletOutputStream;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * Created by admin on 2018/6/25.
 */
@RestController
@RequestMapping("/sys/authentication")
@Tag(name = "PremessionController", description = "用户授权相关")
@RequiredArgsConstructor
public class PremessionController extends AbstractController {

    private final RedisUtil redisUtil;

    private final SysUserServiceImpl userService;

    private final SysCaptchaServiceImpl sysCaptchaService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/sendCode/{mobile}")
    public R sendCode(@PathVariable String mobile) {
        sysCaptchaService.smsCode(mobile);
        return R.ok();
    }

    @Operation(summary = "验证码", description = "验证码")
    @GetMapping("captcha.jpg")
    public void captcha(String uuid) throws IOException {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        sra.getResponse().setHeader("Cache-Control", "no-store, no-cache");
        sra.getResponse().setContentType("image/jpeg");
        BufferedImage image = sysCaptchaService.getCaptcha(uuid);
        ServletOutputStream out = sra.getResponse().getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.close(out);
    }

    @Operation(summary = "登陆", description = "登陆")
    @PostMapping("/require")
    public R login(@RequestBody LoginForm user) {
        boolean captcha = sysCaptchaService.validate(user.getUuid(), user.getCaptcha());
        if (!captcha) {
            return R.error("验证码不正确");
        }
        SysUser sysUser = userService.login(user.getUsername());
        if (sysUser != null && sysUser.getState() == 0) {
            return R.error("账号被冻结");
        } else if (sysUser != null && sysUser.getState() == -1) {
            return R.error("账号被注销");
        } else if (sysUser != null && passwordEncoder.matches(user.getPassword(), sysUser.getUserPass())) {
            return userService.createToken(sysUser.getUserId());
        } else {
            return R.error("账号或密码不正确");
        }
    }

    @Operation(summary = "登出", description = "登出")
    @PostMapping("/logout")
    public R logout() {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        redisUtil.del(token);
        token = "token::access_token::" + token.replaceAll("bearer ", "");
        redisUtil.del(token);
        token = "token::refresh_token::" + token.replaceAll("bearer ", "");
        redisUtil.del(token);
        return R.ok();
    }

    @Operation(summary = "获取刷新token", description = "获取刷新token")
    @PostMapping("/refreshToken")
    public R refreshToken() {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String refresh_token = null;
        if (redisUtil.get(token) != null) {
            refresh_token = redisUtil.get(token).toString();
        }
        return R.ok().put("data", refresh_token);
    }
}
