package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wrh on 2018/4/23.
 */
@ApiModel
public class PushOrderListForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "推送订单list")
  private List<GedOrderloanPushReqForm> list;

  public List<GedOrderloanPushReqForm> getList() {
    return list;
  }

  public void setList(List<GedOrderloanPushReqForm> list) {
    this.list = list;
  }
}
