package com.gq.ged.common.enums;

/**
 * Created by wrh on 2018/3/17.
 */
public enum JointCreditFlag {
  NOT_JOINTCREDIT(0, "不联合授信"), JOINTCRED(1, "联合授信");
  private int code;
  private String value;

  JointCreditFlag(int code, String value) {
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
