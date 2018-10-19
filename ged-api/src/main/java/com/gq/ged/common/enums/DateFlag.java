package com.gq.ged.common.enums;

/**
 * Created by wrh on 2018/3/17.
 */
public enum DateFlag {
  LESS_NEWDATE(1, "小于当前日期"), MORE_NEWDATE(2, "大于当前日期");
  private int code;
  private String value;

  DateFlag(int code, String value) {
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
