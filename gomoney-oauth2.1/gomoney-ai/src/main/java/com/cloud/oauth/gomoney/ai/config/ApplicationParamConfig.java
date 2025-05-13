package com.cloud.oauth.gomoney.ai.config;

import com.alibaba.dashscope.app.Application;
import com.alibaba.dashscope.app.ApplicationParam;
import com.alibaba.dashscope.app.ApplicationResult;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ApplicationParamConfig {

    public ApplicationResult applicationResult(String message) throws NoApiKeyException, InputRequiredException {
        ApplicationParam param = ApplicationParam.builder()
                .apiKey("sk-d1d9294b811f451d86f15499ece82a81")
                .appId("a25e01f9954c4d3d8458b116938dcda1")
                .prompt(message)
                .build();

        Application application = new Application();
        return application.call(param);
    }

}
