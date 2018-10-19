package com.gq.ged.common.enums;

/**
 * Created by wrh on 2018/3/17.
 */
public enum FirstWithDeposit {
  ALREADY_PRESENTED(22, "已提现,还款中"), FIRST_WITHCASH(26, "首次提现"), FAIL(7, "提现失败");

  private int code;
  private String value;

  FirstWithDeposit(int code, String value) {
    this.code = code;
    this.value = value;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
