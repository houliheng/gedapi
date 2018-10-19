package com.gq.ged.common.enums;


/**
 * Created by wyq_tomorrow on 2018/1/30.
 */
public enum RechargeType {
  SERVICEFEE(1, "服务费充值"), REPAYMENT(2, "还款充值");
  private int key;
  private String value;

  RechargeType(int key, String value) {
    this.key = key;
    this.value = value;
  }

  public static RechargeType resolve(int code) {
    switch (code) {
      case 1:
        return SERVICEFEE;
      case 2:
        return REPAYMENT;
      default:
        return SERVICEFEE;
    }
  }

  public int getKey() {
    return key;
  }

  public void setKey(int key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
