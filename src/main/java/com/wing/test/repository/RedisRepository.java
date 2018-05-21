package com.wing.test.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisRepository {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setExpire(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, 180l, TimeUnit.SECONDS);
    }

    public void setExpire(String key, Object value, long duration, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, duration, timeUnit);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
