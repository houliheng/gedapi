package com.gq.ged.account.controller.req.v2;

import java.io.Serializable;

public class EnterpriseAccountReq  implements Serializable {
  private String custId;// 法人客户ID
  private String mobile;// 法人客户手机号
  private String remark;// 备用字段

  public String getCustId() {
    return custId;
  }

  public void setCustId(String custId) {
    this.custId = custId;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
