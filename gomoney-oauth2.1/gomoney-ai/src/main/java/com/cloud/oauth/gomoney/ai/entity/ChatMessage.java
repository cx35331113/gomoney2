package com.cloud.oauth.gomoney.ai.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ChatMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 193488572593271383L;

    private String resultId;

    private String sender;

    private String message;

    private String finishReason;
}
