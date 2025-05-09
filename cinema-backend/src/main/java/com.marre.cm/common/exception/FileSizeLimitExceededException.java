package com.marre.cm.common.exception;

/**
 * @author :marRE
 * @Description:文件大小超限异常
 * @Date :2024/3/26
 */

public class FileSizeLimitExceededException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException() {
    }

    public FileSizeLimitExceededException(String message) {
        super(message);
    }

}
