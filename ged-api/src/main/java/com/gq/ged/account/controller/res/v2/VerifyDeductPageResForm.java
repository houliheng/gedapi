package com.gq.ged.account.controller.res.v2;

import java.io.Serializable;

public class VerifyDeductPageResForm implements Serializable {
  private String redirectUrl;// 提现跳转的页面
  private Integer verifyDeductFlag;// 0未交费 1已缴费

  public String getRedirectUrl() {
    return redirectUrl;
  }

  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }

  public Integer getVerifyDeductFlag() {
    return verifyDeductFlag;
  }

  public void setVerifyDeductFlag(Integer verifyDeductFlag) {
    this.verifyDeductFlag = verifyDeductFlag;
  }
}
