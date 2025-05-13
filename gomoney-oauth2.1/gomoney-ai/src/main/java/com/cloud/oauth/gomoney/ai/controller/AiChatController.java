package com.cloud.oauth.gomoney.ai.controller;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.cloud.oauth.gomoney.ai.entity.ChatMessage;
import com.cloud.oauth.gomoney.ai.service.impl.AIChatServiceImpl;
import com.cloud.oauth.gomoney.api.annotation.Resubmit;
import com.cloud.oauth.gomoney.core.utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ai/chat")
public class AiChatController {

    private final AIChatServiceImpl aiChatService;

    @Resubmit
    @PostMapping("/chatResult")
    public R chatResult(@RequestBody ChatMessage message) throws NoApiKeyException, InputRequiredException {
        ChatMessage messages = aiChatService.aiChatList(message.getMessage());
        return R.ok().put("data",messages);
    }
}
