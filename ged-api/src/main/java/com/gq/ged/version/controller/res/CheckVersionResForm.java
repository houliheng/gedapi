package com.gq.ged.version.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wrh on 2017/10/19.
 */
@ApiModel
public class CheckVersionResForm implements Serializable{
  @ApiModelProperty(value = "状态")
  private Integer status;

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
