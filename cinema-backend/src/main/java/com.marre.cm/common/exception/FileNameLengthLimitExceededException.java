package com.marre.cm.common.exception;

/**
 * @author :marRE
 * @Description:文件名过长
 * @Date :2024/3/26
 */

public class FileNameLengthLimitExceededException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException() {
    }

    public FileNameLengthLimitExceededException(String message) {
        super(message);
    }

}
