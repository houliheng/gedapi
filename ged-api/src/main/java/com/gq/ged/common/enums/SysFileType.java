package com.gq.ged.common.enums;

/**
 * Created by wyq_tomorrow on 2017/9/8.
 */
public enum SysFileType {
  FILE_IMG("1", "图片"), FILE_DOC("2", "文档");
  private String code;
  private String desc;

  SysFileType(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
