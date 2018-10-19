package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * Created by Levi on 2018/4/23.
 */
public class ConfirmGuaranteeListResForm extends GuaranteeBaseForm {
  // 借款用途 产品类型loan_type loan_purpose
  @ApiModelProperty("借款用途")
  private String loanType;
  @ApiModelProperty("产品类型loan_type")
  private String loanPurpose;

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }
}
