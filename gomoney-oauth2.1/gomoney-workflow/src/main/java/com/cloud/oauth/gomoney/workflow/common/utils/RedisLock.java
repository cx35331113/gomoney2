package com.cloud.oauth.gomoney.workflow.common.utils;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisLock {
    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     *
     * @param key
     * @param value 当前事件+超时事件
     * @return
     */
    public boolean lock(String key, String value) {
        //加锁成功
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        //假如currentValue=A先占用了锁  其他两个线程的value都是B,保证其中一个线程拿到锁
        String currentValue = redisTemplate.opsForValue().get(key);
        //锁过期  防止出现死锁
        if (!StrUtil.isEmpty(currentValue) &&
                Long.parseLong(currentValue) < System.currentTimeMillis()) {
            //获取上一步锁的时间
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            if (!StrUtil.isEmpty(oldValue) &&
                    oldValue.equals(currentValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     *
     * @param key
     * @param value
     */
    public void unlock(String key, String value) {
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StrUtil.isEmpty(currentValue) &&
                    currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            logger.error("【redis分布式锁】 解锁异常，{}", e);
        }
    }
}
