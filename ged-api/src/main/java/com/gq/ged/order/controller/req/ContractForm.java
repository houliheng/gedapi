package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wyq_tomorrow on 2018/3/7.
 */
@ApiModel
public class ContractForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "合同编号")
  private String contractNo;
  @ApiModelProperty(value = "分期list")
  private List<StagesForm> list;

  public String getContractNo() {
    return contractNo;
  }

  public void setContractNo(String contractNo) {
    this.contractNo = contractNo;
  }

  public List<StagesForm> getList() {
    return list;
  }

  public void setList(List<StagesForm> list) {
    this.list = list;
  }
}
