package com.gq.ged.account.controller.res.v2;

/**
 * @Auther: zhaozb
 * @Date: 2018/6/14 15:27
 * @Description:
 */
public class UserStatusResForm {
  private Integer status;// 2未激活 1已激活
  private Integer sysFlag;// 0借款系统1自主

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getSysFlag() {
    return sysFlag;
  }

  public void setSysFlag(Integer sysFlag) {
    this.sysFlag = sysFlag;
  }
}
