package com.duck.code.commons.constant;

/**
 * @program: codeduck-blog-serve
 * @description: 响应状态码
 * @author: Code Duck
 * @create: 2020-10-24 13:57
 */
public class ResCode {

    /**
     * 操作成功返回的状态码
     */
    public static final int OPERATION_SUCCESS = 1000;

    /**
     * 操作失败返回的状态码
     */
    public static final int OPERATION_FAIL = 1001;

    /**
     * 拒绝本次操作返回的状态码
     */
    public static final int OPERATION_REJECT = 1002;

    /**
     * token异常返回状态码
     */
    public static final int TOKEN_ERROR = 5000;

    /**
     * token过期返回状态码
     */
    public static final int TOKEN_EXPIRED = 5001;

    /**
     * 服务器异常返回状态码
     */
    public static final int SERVICE_ERROR = 6000;


}
