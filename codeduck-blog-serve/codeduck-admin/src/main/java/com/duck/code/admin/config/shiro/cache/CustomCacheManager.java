package com.duck.code.admin.config.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @program: codeduck-auth
 * @description:
 * @author: Code Duck
 * @create: 2020-10-01 19:02
 */
public class CustomCacheManager implements CacheManager {

    private RedisTemplate<String, Object> redisTemplate;

    public CustomCacheManager(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new CustomCache<K, V>(redisTemplate);
    }
}
