package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wrh on 2018/5/29.
 */
@ApiModel
public class OrderConTractListForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "合同面签更新合同号list")
  private List<OrderContractReqForm> list;

  public List<OrderContractReqForm> getList() {
    return list;
  }

  public void setList(List<OrderContractReqForm> list) {
    this.list = list;
  }
}
