package com.duck.code.web;

import com.duck.code.web.config.redis.RedisClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-10 16:39
 */
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisClient redisClient;

    @Test
    public void setKey() {
        String key = "test:redis";
        String value = "123123";
        redisClient.set(key,value   );
        System.out.println(redisClient.get(key));
    }
}
