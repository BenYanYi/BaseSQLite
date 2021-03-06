package com.benyanyi.sqlitelib.exception;

/**
 * @author YanYi
 * @date 2019/3/29 15:11
 * @email ben@yanyi.red
 * @overview 异常返回
 */
public class TableException extends RuntimeException {

    private static final long serialVersionUID = 3652654932830298674L;

    public TableException() {
    }

    public TableException(String message) {
        super(message);
    }
}
