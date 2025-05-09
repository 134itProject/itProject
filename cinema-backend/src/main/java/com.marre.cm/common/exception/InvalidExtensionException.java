package com.marre.cm.common.exception;

/**
 * @author :marRE
 * @Description:文件后缀异常
 * @Date :2024/3/26
 */

public class InvalidExtensionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidExtensionException() {
    }

    public InvalidExtensionException(String message) {
        super(message);
    }
}
