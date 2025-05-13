package com.cloud.oauth.gomoney.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.util.Assert;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class GomoneyRedisOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

	private RedisTemplate<String, Object> redisTemplate;

	private final static Long TIMEOUT = 10L;

	@Autowired
	public void setRedisTemplate(RedisTemplate redisTemplate){
		RedisSerializer serializer=new StringRedisSerializer();
		redisTemplate.setKeySerializer(serializer);
		redisTemplate.setValueSerializer(RedisSerializer.java());
		redisTemplate.setHashKeySerializer(serializer);
		redisTemplate.setHashValueSerializer(RedisSerializer.java());
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void save(OAuth2AuthorizationConsent authorizationConsent) {
		Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
		if (redisTemplate.keys("*") != null) {
			Set<String> keys = redisTemplate.keys("*");
			for (String k : keys) {
				try {
					OAuth2AuthorizationConsent auth2AuthorizationConsent = (OAuth2AuthorizationConsent)redisTemplate.opsForValue().get(k);
					if (auth2AuthorizationConsent.getPrincipalName().equals(authorizationConsent.getPrincipalName())) {
						redisTemplate.delete(k);
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		redisTemplate.opsForValue().set(buildKey(authorizationConsent), authorizationConsent, TIMEOUT,
				TimeUnit.MINUTES);

	}

	@Override
	public void remove(OAuth2AuthorizationConsent authorizationConsent) {
		Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
		redisTemplate.delete(buildKey(authorizationConsent));
	}

	@Override
	public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
		Assert.hasText(registeredClientId, "registeredClientId cannot be empty");
		Assert.hasText(principalName, "principalName cannot be empty");
		return (OAuth2AuthorizationConsent) redisTemplate.opsForValue().get(buildKey(registeredClientId, principalName));
	}

	private static String buildKey(String registeredClientId, String principalName) {
		return "token:consent:" + registeredClientId + ":" + principalName;
	}

	private static String buildKey(OAuth2AuthorizationConsent authorizationConsent) {
		return buildKey(authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
	}

}
