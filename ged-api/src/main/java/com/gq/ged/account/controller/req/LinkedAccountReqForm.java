package com.gq.ged.account.controller.req;


import java.io.Serializable;

/**
 * @Auther: zhaozb
 * @Date: 2018/5/18 10:19
 * @Description: 冠e贷存量用户关联账户（个人账户）
 */
public class LinkedAccountReqForm implements Serializable{

  private String corporationPhone;// 法人电话

  // private String socialCreditCode;// 统一社会信用代码

  public String getCorporationPhone() {
    return corporationPhone;
  }

  public void setCorporationPhone(String corporationPhone) {
    this.corporationPhone = corporationPhone;
  }

}
