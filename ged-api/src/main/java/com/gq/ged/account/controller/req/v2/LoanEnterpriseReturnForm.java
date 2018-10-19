package com.gq.ged.account.controller.req.v2;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: zhaozb
 * @Date: 2018/6/28 12:53
 * @Description:
 */
public class LoanEnterpriseReturnForm {
  @ApiModelProperty(value = "统一社会信用代码")
  private String socialCreditCode;
  @ApiModelProperty(value = "用户ID")
  private Long userId;
  @ApiModelProperty(value = "企业开户状态")
  private Integer status;
  @ApiModelProperty(value = "退回原因")
  private String returnReason;

  public String getSocialCreditCode() {
    return socialCreditCode;
  }

  public void setSocialCreditCode(String socialCreditCode) {
    this.socialCreditCode = socialCreditCode;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getReturnReason() {
    return returnReason;
  }

  public void setReturnReason(String returnReason) {
    this.returnReason = returnReason;
  }
}
