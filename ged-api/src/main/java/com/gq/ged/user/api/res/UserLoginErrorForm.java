package com.gq.ged.user.api.res;

import java.io.Serializable;

/**
 * Created by wyq_tomorrow on 2017/9/23.
 */
public class UserLoginErrorForm implements Serializable {
  private String mobile;
  private Integer count;
  private boolean timeFlag;

  public boolean isTimeFlag() {
    return timeFlag;
  }

  public void setTimeFlag(boolean timeFlag) {
    this.timeFlag = timeFlag;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }
}
