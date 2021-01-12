package com.duck.code.commons.constant;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-09 15:04
 */
public class SysConstants {

    /**
     * token令牌
     */
    public static final String TOKEN = "token";

    /**
     * 资源不可使用
     */
    public static final Integer DISABLED = 0;

    /**
     * 资源可使用
     */
    public static final Integer ENABLE = 1;

    /**
     * 资源已冻结
     */
    public static final Integer FREEZE = 2;


    /**
     * 普通用户角色ID
     */
    public static final Integer GENERAL_USER = 1;

    /**
     * 一分钟
     */
    public static final Long ONE_MINUTE = 60L;

    /**
     * 半小时
     */
    public static final Long HALF_HOURS = 180L;

    /**
     * 一小时
     */
    public static final Long ONE_HOURS = 3600L;

    /**
     * 一天
     */
    public static final Long ONE_DAY = 86400L;

    /**
     * 一周
     */
    public static final Long ONE_WEEK = 604800L;

    /**
     * 评论发送邮件相关
     */
    public static final String COMMENT_LIST = "comment_list";
    public static final String REPLY_LIST = "reply_list";
    public static final String RECEIVER = "receiver";
    public static final String EMAIL = "email";
    public static final String TEXT = "text";
    public static final String TO_TEXT = "to_text";
    public static final String NICKNAME = "nickname";
    public static final String TO_NICKNAME = "to_nickname";
    public static final String USER_UID = "user_id";
    public static final String URL = "url";
    public static final String ACCOUNT_ACTIVATED = "账号已激活,欢迎使用CodeDuck Blog! 现在可以去登录啦! ~^_^~";
    public static final String USER_INFO = "userInfo";
}
