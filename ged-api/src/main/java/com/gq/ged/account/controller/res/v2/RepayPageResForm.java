package com.gq.ged.account.controller.res.v2;

import java.io.Serializable;

public class RepayPageResForm implements Serializable {
  private String redirectUrl;// 充值页面链接（为空串时，直连扣费；不为空串时，页面验密扣费）
  private String deducturl;// 缴费页面链接

  public String getRedirectUrl() {
    return redirectUrl;
  }

  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }

  public String getDeducturl() {
    return deducturl;
  }

  public void setDeducturl(String deducturl) {
    this.deducturl = deducturl;
  }
}
