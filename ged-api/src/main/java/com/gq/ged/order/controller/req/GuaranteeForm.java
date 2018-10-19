package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wrh on 2018/4/21.
 */
@ApiModel
public class GuaranteeForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "list")
  private List<PushGuarantRecordReqForm> list;

  public List<PushGuarantRecordReqForm> getList() {
    return list;
  }

  public void setList(List<PushGuarantRecordReqForm> list) {
    this.list = list;
  }
}
