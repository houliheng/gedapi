package com.gq.ged.dictionary.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wyq_tomorrow on 2018/1/28.
 */
@ApiModel("借钱list")
public class LoanListResForm implements Serializable {
  @ApiModelProperty("id")
  private String id;
  @ApiModelProperty("产品名称")
  private String productName;
  @ApiModelProperty("产品描述")
  private String desc;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
