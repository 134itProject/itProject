package com.marre.cm.common.exception;

/**
 * @author :marRE
 * @Description:数据未找到异常
 * @Date :2024/3/26
 */

public class DataNotFoundException extends RuntimeException {

    static final long serialVersionUID = -7034897190745456227L;

    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(String message) {
        super(message);
    }

}
