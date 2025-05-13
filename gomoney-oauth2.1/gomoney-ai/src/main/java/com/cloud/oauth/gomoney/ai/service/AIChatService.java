package com.cloud.oauth.gomoney.ai.service;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.cloud.oauth.gomoney.ai.entity.ChatMessage;

import java.util.List;

public interface AIChatService {

    ChatMessage aiChatList(String message) throws NoApiKeyException, InputRequiredException;
}
