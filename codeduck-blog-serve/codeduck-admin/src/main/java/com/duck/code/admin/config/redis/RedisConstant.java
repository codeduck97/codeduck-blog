package com.duck.code.admin.config.redis;

/**
 * @program: code duck-auth
 * @description: Redis常量枚举类
 * @author: Code Duck
 * @create: 2020-09-27 15:57
 **/
public enum RedisConstant {

    /**
     * redis-key-前缀-shiro:cache:
     */
    PREFIX_SHIRO_CACHE("shiro:cache:"),

    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    PREFIX_SHIRO_REFRESH_TOKEN("shiro:refresh_token:");

    private String key;

    RedisConstant(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
