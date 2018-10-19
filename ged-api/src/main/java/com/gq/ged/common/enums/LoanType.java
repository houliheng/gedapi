package com.gq.ged.common.enums;


/**
 * Created by wyq_tomorrow on 2018/1/30.
 */
public enum LoanType {
  CREDIT(1, "信用"), HYPOTHECATION(2, "抵质押"), BLEND(4, "混合"), BEDT(6, "债股结合"), PURCHASE(7, "采购贷");
  private int key;
  private String value;

  LoanType(int key, String value) {
    this.key = key;
    this.value = value;
  }

  public static LoanType resolve(int code) {
    switch (code) {
      case 1:
        return CREDIT;
      case 2:
        return HYPOTHECATION;
      case 4:
        return BLEND;
      case 6:
        return BEDT;
      case 7:
        return PURCHASE;
      default:
        return CREDIT;
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
