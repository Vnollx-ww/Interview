package com.example.interview.utils;

import com.example.interview.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

@Component
public class RedisUtils {
    private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    public Object getValue(String redisKey) {
        if (redisKey == null || redisKey.isEmpty()) {
            logger.error("Redis 键不能为空");
            throw new IllegalArgumentException("Redis 键不能为空");
        }
        try {
            ValueOperations<String, Serializable> valueOps = redisTemplate.opsForValue();
            return valueOps.get(redisKey);
        } catch (Exception e) {
            logger.error("获取 Redis 值失败，键: {}, 错误信息: {}", redisKey, e.getMessage(), e);
            throw new RuntimeException("获取 Redis 值失败: " + e.getMessage(), e);
        }
    }

    public long getValueAsLong(String redisKey) {
        Object value = getValue(redisKey);
        if (value == null) {
            logger.warn("Redis 键 {} 不存在，返回默认值 0", redisKey);
            return 0;
        }
        try {
            if (value instanceof String) {
                return Long.parseLong((String) value);
            } else if (value instanceof Number) {
                return ((Number) value).longValue();
            } else {
                throw new IllegalArgumentException("不支持的类型: " + value.getClass());
            }
        } catch (NumberFormatException e) {
            logger.error("Redis 键 {} 的值无法转换为 long: {}", redisKey, value);
            throw new RuntimeException("值转换失败: " + value, e);
        }
    }
}