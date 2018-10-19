package com.gq.ged.order.pc.res;


import java.io.Serializable;

public class Repayment implements Serializable {

  /**
   * 合同编号
   */
  private String contractNo;
  /**
   * 期数
   */
  private Integer period;

  public String getContractNo() {
    return contractNo;
  }

  public void setContractNo(String contractNo) {
    this.contractNo = contractNo;
  }

  public Integer getPeriod() {
    return period;
  }

  public void setPeriod(Integer period) {
    this.period = period;
  }
}
