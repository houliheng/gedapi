package com.gq.ged.common.enums;

/**
 * Created by wrh on 2018/3/16.
 */
public enum WithDrawFlag {
  NOT_WITHDEAW(0, "未提现"), fIRST_WITHDRAWING(1, "一次提现中"), FIRST_WITHDRAWFINISH(2,
      "一次提现完成"), SECOND_WITHDRAWING(3,
          "二次提现中"), SECOND_WITHDEAWFINISH(4, "二次提现完成"), FAIL(5, "提现失败");
  private int key;
  private String value;

  WithDrawFlag(int key, String value) {
    this.key = key;
    this.value = value;
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
