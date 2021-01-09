package com.duck.code.web.service.impl;

import com.duck.code.web.config.redis.RedisClient;
import com.duck.code.web.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-09 15:57
 */
@Slf4j
@Service
public class CacheServiceImpl implements CacheService {

    @Resource
    private RedisClient redisClient;

    @Override
    public void testConnect() {

        redisClient.set("test_redis", "success");
        String test1 = (String) redisClient.get("test_redis");
        log.info("测试获取Redis{{}}",test1 );
        redisClient.del("test_redis");
        String test2 = (String) redisClient.get("test_redis");
        if (StringUtils.isEmpty(test2)) {
            log.info("redis连接成功");
        } else {
            log.error("redis连接失败");
        }
    }
}
