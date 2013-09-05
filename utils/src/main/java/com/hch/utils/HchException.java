package com.hch.utils;

/**
 * Created with IntelliJ IDEA.
 * User: huaiwang
 * Date: 13-9-5
 * Time: 上午10:48
 * To change this template use File | Settings | File Templates.
 */
public class HchException extends RuntimeException {
    public HchException() {
        super();
    }

    public HchException(String message) {
        super(message);
    }

    public HchException(String message, Throwable cause) {
        super(message, cause);
    }

    public HchException(Throwable cause) {
        super(cause);
    }
}
