package com.gq.ged.message.api.res;

import java.util.Date;

/**
 * Created by wyq_tomorrow on 2017/8/25.
 */
public class MsgRedisEntity {
  /**
   * 验证码
   */
  private String[] param;
  /**
   * 手机号
   */
  private String mobile;
  /**
   * 访问次数
   */
  private int count;

  /**
   * 当前日期
   */
  private Date currentDate;

  public String[] getParam() {
    return param;
  }

  public void setParam(String[] param) {
    this.param = param;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public Date getCurrentDate() {
    return currentDate;
  }

  public void setCurrentDate(Date currentDate) {
    this.currentDate = currentDate;
  }
}
