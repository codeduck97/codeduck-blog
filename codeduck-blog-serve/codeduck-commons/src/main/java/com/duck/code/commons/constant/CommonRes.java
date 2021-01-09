package com.duck.code.commons.constant;

import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.commons.constant.ResCode;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2020-12-03 10:46
 */
public class CommonRes {

    public static R success(Object o) {
        return R.ok(o).setCode(ResCode.OPERATION_SUCCESS);
    }

    public static R success(String msg) {
        return R.ok(null).setCode(ResCode.OPERATION_SUCCESS).setMsg(msg);
    }

    public static R fail(String msg) {
        return R.failed(msg).setCode(ResCode.OPERATION_FAIL);
    }
}
