package com.marre.cm.common.constant;

/**
 * @author :marRE
 * @Description:HTTP 状态码
 * @Date :2024/3/24
 */

public class HttpStatus {

    /**
     * 成功
     */
    public static final int SUCCESS = 200;

    /**
     * 参数异常等
     */
    public static final int BAD_REQUEST = 400;

    /**
     * 访问受限，授权过期
     */
    public static final int FORBIDDEN = 403;

    /**
     * 资源，服务未找到
     */
    public static final int NOT_FOUND = 404;

    /**
     * 系统内部错误
     */
    public static final int ERROR = 500;
}
