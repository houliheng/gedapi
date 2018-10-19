package com.gq.ged.common.enums;

/**
 * Created by wyq_tomorrow on 2018/4/8.
 */
public enum ChanelStyle {
  MOBILE(0, "MOBILE"), PC(1, "PC");
  private int code;
  private String value;

  ChanelStyle(int code, String value) {
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
