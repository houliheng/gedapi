package com.gq.ged.message.api.req;

import java.io.Serializable;

/**
 * Created by wyq_tomorrow on 2017/8/23.
 */
public class SysCodeReqForm implements Serializable {

  private String sysCode;

  public String getSysCode() {
    return sysCode;
  }

  public void setSysCode(String sysCode) {
    this.sysCode = sysCode;
  }
}
