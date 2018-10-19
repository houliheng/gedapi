package com.gq.ged.account.controller.req.v2;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class AcccountReqForm implements Serializable{
  /*@ApiModelProperty("法人姓名")
  private String name;
  @ApiModelProperty("法人身份证号")
  private String cretNO;*/
  @ApiModelProperty("手机号")
  private String mobile;
 /* @ApiModelProperty("银行卡号")
  private String bankNo;*/

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

}
