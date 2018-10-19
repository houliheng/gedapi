package com.gq.ged.user.api.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wyq_tomorrow on 2018/2/1.
 */
@ApiModel
public class UserTitleResForm implements Serializable {
  /**
   * @Fields serialVersionUID : TODO
   */
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "用户姓名")
  private String userName;
  @ApiModelProperty(value = "公司名称")
  private String companyName;
  /*@ApiModelProperty(value = "是否开户个人")
  private Integer isAccount;*/
  @ApiModelProperty(value = "是否开户")
  private Integer isCompanyAccount;
  @ApiModelProperty(value = "是否实名")
  private Integer isRealName;

  public Integer getIsCompanyAccount() {
    return isCompanyAccount;
  }

  public void setIsCompanyAccount(Integer isCompanyAccount) {
    this.isCompanyAccount = isCompanyAccount;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public Integer getIsRealName() {
    return isRealName;
  }

  public void setIsRealName(Integer isRealName) {
    this.isRealName = isRealName;
  }
}
