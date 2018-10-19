package com.gq.ged.common.exception.business;

/**
 * Created by wyq_tomorrow on 2017/12/5.
 */
public class CommonException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public CommonException() {
    super();
  }

  public CommonException(String msg, Throwable t) {
    super(msg, t);
  }

  public CommonException(String msg) {
    super(msg);
  }

  public CommonException(Throwable cause) {
    super(cause);
  }

  protected CommonException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
