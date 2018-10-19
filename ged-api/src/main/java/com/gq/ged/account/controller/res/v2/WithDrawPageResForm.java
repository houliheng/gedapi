package com.gq.ged.account.controller.res.v2;

import java.io.Serializable;

public class WithDrawPageResForm implements Serializable {
  private String redirectUrl;//提现跳转的页面
  private Integer withDrawFlag;// 0账户正常，可提现 1账户余额不足，不能提现

  public String getRedirectUrl() {
    return redirectUrl;
  }

  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }

  public Integer getWithDrawFlag() {
    return withDrawFlag;
  }

  public void setWithDrawFlag(Integer withDrawFlag) {
    this.withDrawFlag = withDrawFlag;
  }
}
