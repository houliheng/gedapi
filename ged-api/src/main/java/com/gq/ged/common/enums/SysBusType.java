package com.gq.ged.common.enums;

/**
 * Created by wyq_tomorrow on 2017/9/8.
 */
public enum SysBusType {
  USER("1", "用户"), ORDER("2", "订单");
  private String code;
  private String desc;

  SysBusType(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
