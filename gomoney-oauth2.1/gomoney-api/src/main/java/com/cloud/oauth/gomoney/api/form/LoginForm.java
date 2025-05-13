package com.cloud.oauth.gomoney.api.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "中心登录表单")
public class LoginForm {


    @Schema(name = "登录账号", required = true)
    private String username;

    @Schema(name = "密码", required = true)
    private String password;

    @Schema(name = "验证码", required = true)
    private String captcha;

    @Schema(name = "授权码", required = true)
    private String uuid;
}
