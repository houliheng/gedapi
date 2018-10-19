package com.gq.ged.user.api.req;

import java.io.Serializable;

/**
 * Created by wyq_tomorrow on 2018/3/14.
 */
public class UserLoginReqForm implements Serializable {
  public String mobile;
  public String password;

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
