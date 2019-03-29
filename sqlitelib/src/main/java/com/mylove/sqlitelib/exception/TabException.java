package com.mylove.sqlitelib.exception;

/**
 * @author YanYi
 * @date 2019/3/29 15:11
 * @email ben@yanyi.red
 * @overview
 */
public class TabException extends RuntimeException {

    private static final long serialVersionUID = 3652654932830298674L;

    public TabException() {
    }

    public TabException(String message) {
        super(message);
    }
}
