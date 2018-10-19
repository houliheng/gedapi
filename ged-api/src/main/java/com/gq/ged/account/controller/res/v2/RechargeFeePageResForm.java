package com.gq.ged.account.controller.res.v2;

import java.io.Serializable;

public class RechargeFeePageResForm implements Serializable {
  private String redirectUrl;// 提现跳转的页面
  private Integer rechargeFlag;// 0未充值过 1已充值过

  public String getRedirectUrl() {
    return redirectUrl;
  }

  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }

  public Integer getRechargeFlag() {
    return rechargeFlag;
  }

  public void setRechargeFlag(Integer rechargeFlag) {
    this.rechargeFlag = rechargeFlag;
  }
}
