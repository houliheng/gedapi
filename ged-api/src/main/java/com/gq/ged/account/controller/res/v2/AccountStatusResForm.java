package com.gq.ged.account.controller.res.v2;

/**
 * @Auther: zhaozb
 * @Date: 2018/5/31 10:01
 * @Description:
 */
public class AccountStatusResForm {
  private Integer status;
  private String verifyInfo;

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getVerifyInfo() {
    return verifyInfo;
  }

  public void setVerifyInfo(String verifyInfo) {
    this.verifyInfo = verifyInfo;
  }
}
