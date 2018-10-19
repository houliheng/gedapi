package com.gq.ged.common.enums;

/**
 * Created by wyq_tomorrow on 2018/1/29.
 */
public enum LoanPurpose {
  BUY_CAR(1, "购车"), LIVING_CONSUME(2, "日常生活消费"), RENOVATION(3, "装修"), EDU(4, "教育支出"), MEDICAL(5,
      "医疗"), PAY(6, "支付员工工资"), MANAGEMENT(7,
          "扩大生产/经营"), EQUIPMENT(8, "购买货物/原材料/设备"), CAPITAL(9, "资金周转"), OTHER(99, "其他");
  private int key;
  private String value;

  LoanPurpose(int key, String value) {
    this.key = key;
    this.value = value;
  }

  public static LoanPurpose resove(Integer code) {
    switch (code) {
      case 1:
        return BUY_CAR;
      case 2:
        return LIVING_CONSUME;
      case 3:
        return RENOVATION;
      case 4:
        return EDU;
      case 5:
        return MEDICAL;
      case 6:
        return PAY;
      case 7:
        return MANAGEMENT;
      case 8:
        return EQUIPMENT;
      case 9:
        return CAPITAL;
      case 99:
        return OTHER;
      default:
        return OTHER;
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
