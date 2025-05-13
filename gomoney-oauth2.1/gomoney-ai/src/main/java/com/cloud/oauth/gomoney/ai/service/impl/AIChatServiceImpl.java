package com.cloud.oauth.gomoney.ai.service.impl;

import com.alibaba.dashscope.app.ApplicationResult;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.cloud.oauth.gomoney.ai.config.ApplicationParamConfig;
import com.cloud.oauth.gomoney.ai.entity.ChatMessage;
import com.cloud.oauth.gomoney.ai.service.AIChatService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AIChatServiceImpl implements AIChatService {

    public ChatMessage aiChatList(String message) throws NoApiKeyException, InputRequiredException {
        ApplicationResult result = new ApplicationParamConfig().applicationResult(message);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender("ai");
        chatMessage.setResultId(result.getRequestId());
        chatMessage.setMessage(result.getOutput().getText());
        chatMessage.setFinishReason(result.getOutput().getFinishReason());
        return chatMessage;
    }
}
