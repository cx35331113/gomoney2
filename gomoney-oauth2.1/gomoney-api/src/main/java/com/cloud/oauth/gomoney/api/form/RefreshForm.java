package com.cloud.oauth.gomoney.api.form;

import lombok.Data;

@Data
public class RefreshForm {

    private String refreshToken;

    private String clientId;
}
