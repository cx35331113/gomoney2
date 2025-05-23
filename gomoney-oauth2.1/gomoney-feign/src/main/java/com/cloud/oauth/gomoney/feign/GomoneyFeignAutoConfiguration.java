/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloud.oauth.gomoney.feign;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.cloud.oauth.gomoney.feign.core.GomoneyFeignInnerRequestInterceptor;
import com.cloud.oauth.gomoney.feign.core.GomoneyFeignRequestCloseInterceptor;
import com.cloud.oauth.gomoney.feign.sentinel.ext.GomoneySentinelFeign;
import com.cloud.oauth.gomoney.feign.sentinel.handle.GomoneyUrlBlockHandler;
import com.cloud.oauth.gomoney.feign.sentinel.parser.GomoneyHeaderRequestOriginParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * sentinel 配置
 *
 * @author lengleng
 * @date 2020-02-12
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(SentinelFeignAutoConfiguration.class)
public class GomoneyFeignAutoConfiguration {

	@Bean
	@Scope("prototype")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(name = "feign.sentinel.enabled")
	public Feign.Builder feignSentinelBuilder() {
		return GomoneySentinelFeign.builder();
	}

	@Bean
	public GomoneyFeignInnerRequestInterceptor gomoneyFeignInnerRequestInterceptor(){
		return new GomoneyFeignInnerRequestInterceptor();
	}

	@Bean
	public GomoneyFeignRequestCloseInterceptor gomoneyFeignRequestCloseInterceptor(){
		return new GomoneyFeignRequestCloseInterceptor();
	}

}
