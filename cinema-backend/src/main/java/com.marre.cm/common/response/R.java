package com.marre.cm.common.response;

import com.marre.cm.common.constant.HttpStatus;

import java.util.HashMap;
/**
 * @author :marRE
 * @Description:响应结果
 * @Date :2024/3/24
 */

public class R extends HashMap<String, Object>{


    private static final long serialVersionUID = 1L;

    //状态码的key
    private static final String CODE = "code";

    //操作结果信息key
    private static final String MESSAGE = "msg";

    //返回数据的key
    private static final String DATA = "data";

    /**
     * 空的响应消息结果
     */
    public R() {
    }

    /**
     * 不包含数据的响应消息
     *
     * @param code
     * @param msg
     */
    public R(int code, String msg) {
        super.put(CODE, code);
        super.put(MESSAGE, msg);
    }

    /**
     * 构造响应消息
     *
     * @param code 状态码
     * @param msg  响应消息
     * @param data 响应数据
     */
    public R(int code, String msg, Object data) {
        super.put(CODE, code);
        super.put(MESSAGE, msg);
        if (data != null) {
            super.put(DATA, data);
        }
    }

    /* 返回成功消息 */
    public static R success() {
        return success("操作成功");
    }

    public static R success(String msg) {
        return success(msg, null);
    }

    public static R success(Object data) {
        return success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg  响应消息
     * @param data 响应数据
     * @return 响应消息对象
     */
    public static R success(String msg, Object data) {
        return new R(HttpStatus.SUCCESS, msg, data);
    }


    /* 返回错误消息 */
    public static R error() {
        return error("操作失败");
    }

    public static R error(String msg) {
        return error(msg, null);
    }

    /**
     * 指定返回错误状态码的错误消息
     *
     * @param code
     * @param msg
     * @return
     */
    public static R error(int code, String msg) {
        return new R(code, msg, null);
    }

    /**
     * 默认类型错误消息(500)
     *
     * @param msg
     * @param data
     * @return
     */
    public static R error(String msg, Object data) {
        return new R(HttpStatus.ERROR, msg, data);
    }

}
