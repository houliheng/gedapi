package com.gq.ged.account.controller.req.v2;

import java.io.Serializable;

import com.gq.ged.account.dao.model.Account;
import com.gq.ged.account.dao.model.AccountCompany;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wyq_tomorrow on 2018/3/14.
 */
@ApiModel
public class AccountFlagNewResForm implements Serializable {
  @ApiModelProperty("开户标识")
  private Integer accountFlag;
  @ApiModelProperty("个人账户信息")
  private Account account;
  @ApiModelProperty("企业账户信息")
  private AccountCompany accountCompany;
  @ApiModelProperty("是否绑定手机号标识")
  private Integer mobileFlag;

  private Integer status;

  public Integer getAccountFlag() {
    return accountFlag;
  }

  public void setAccountFlag(Integer accountFlag) {
    this.accountFlag = accountFlag;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public AccountCompany getAccountCompany() {
    return accountCompany;
  }

  public void setAccountCompany(AccountCompany accountCompany) {
    this.accountCompany = accountCompany;
  }

  public Integer getMobileFlag() {
    return mobileFlag;
  }

  public void setMobileFlag(Integer mobileFlag) {
    this.mobileFlag = mobileFlag;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
