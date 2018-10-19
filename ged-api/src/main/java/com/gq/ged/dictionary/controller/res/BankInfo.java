package com.gq.ged.dictionary.controller.res;

import java.io.Serializable;

/**
 * Created by Levi on 2018/3/9.
 */
public class BankInfo implements Serializable {
  private static final long serialVersionUID = 1L;

  private String bankName;

  //1.0 支付用
  private String fuyouCode;
  //开户用
  private String code;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getFuyouCode() {
    return fuyouCode;
  }

  public void setFuyouCode(String fuyouCode) {
    this.fuyouCode = fuyouCode;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }
}
