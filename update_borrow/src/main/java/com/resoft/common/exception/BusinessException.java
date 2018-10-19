package com.resoft.common.exception;

/**
 * 业务异常.
 *
 * @author SeppSong
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -8258238579469255845L;

    private String errorMsg;

    public BusinessException(String message) {
        super(message);
        this.errorMsg = message;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.errorMsg = message;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
