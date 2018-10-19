package com.gq.ged.common.enums;

/**
 * Created by wrh on 2018/3/19.
 */
public enum RepayFlag {
  HAHREPAYMENT(1, "已还"), OVERDUE(2, "已逾期"),NOTREPAYMENT_OVERDUE(3,"未还款或逾期");
  private int code;
  private String value;

  RepayFlag(int code, String value) {
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
