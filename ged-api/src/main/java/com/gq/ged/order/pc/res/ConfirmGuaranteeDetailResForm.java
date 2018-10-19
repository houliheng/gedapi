package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Levi on 2018/4/23.
 */
public class ConfirmGuaranteeDetailResForm extends GuaranteeBaseForm {
  @ApiModelProperty("借款用途")
  private String loanType;
  @ApiModelProperty("产品类型loan_type")
  private String loanPurpose;
  @ApiModelProperty("保证金")
  private String cashDeposit;
  @ApiModelProperty("担保服务费")
  private String guaranteeFee;
  @ApiModelProperty("担保状态")
  private String guaranteeStatus;

  @ApiModelProperty("企业信息")
  private String infoCompanyInfo;
  @ApiModelProperty("借款用途")
  private String infoLoanPurpose;
  @ApiModelProperty("产品信息")
  private String infoCompanyProductInfo;
  @ApiModelProperty("借款人行政处罚")
  private String infoBorrowerSanction;
  @ApiModelProperty("借款人涉诉情况")
  private String infoBorrowerLitigation;
  @ApiModelProperty("借款人行事处罚")
  private String infoBorrowerActInfo;
  @ApiModelProperty("借款人在其他平台共债情况")
  private String infoBorrowerDebtInfo;
  @ApiModelProperty("银行贷款情况")
  private String infoBankLoanInfo;
  @ApiModelProperty("逾期次数")
  private Integer infoOverdueNumber;
  @ApiModelProperty("资产信息")
  private String infoAssetsInfo;
  @ApiModelProperty("还款来源")
  private String infoRepayChanel;


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

    public String getCashDeposit() {
        return cashDeposit;
    }

    public void setCashDeposit(String cashDeposit) {
        this.cashDeposit = cashDeposit;
    }

    public String getGuaranteeFee() {
        return guaranteeFee;
    }

    public void setGuaranteeFee(String guaranteeFee) {
        this.guaranteeFee = guaranteeFee;
    }

    public String getGuaranteeStatus() {
        return guaranteeStatus;
    }

    public void setGuaranteeStatus(String guaranteeStatus) {
        this.guaranteeStatus = guaranteeStatus;
    }

    public String getInfoCompanyInfo() {
        return infoCompanyInfo;
    }

    public void setInfoCompanyInfo(String infoCompanyInfo) {
        this.infoCompanyInfo = infoCompanyInfo;
    }

    public String getInfoLoanPurpose() {
        return infoLoanPurpose;
    }

    public void setInfoLoanPurpose(String infoLoanPurpose) {
        this.infoLoanPurpose = infoLoanPurpose;
    }

    public String getInfoCompanyProductInfo() {
        return infoCompanyProductInfo;
    }

    public void setInfoCompanyProductInfo(String infoCompanyProductInfo) {
        this.infoCompanyProductInfo = infoCompanyProductInfo;
    }

    public String getInfoBorrowerSanction() {
        return infoBorrowerSanction;
    }

    public void setInfoBorrowerSanction(String infoBorrowerSanction) {
        this.infoBorrowerSanction = infoBorrowerSanction;
    }

    public String getInfoBorrowerLitigation() {
        return infoBorrowerLitigation;
    }

    public void setInfoBorrowerLitigation(String infoBorrowerLitigation) {
        this.infoBorrowerLitigation = infoBorrowerLitigation;
    }

    public String getInfoBorrowerActInfo() {
        return infoBorrowerActInfo;
    }

    public void setInfoBorrowerActInfo(String infoBorrowerActInfo) {
        this.infoBorrowerActInfo = infoBorrowerActInfo;
    }

    public String getInfoBorrowerDebtInfo() {
        return infoBorrowerDebtInfo;
    }

    public void setInfoBorrowerDebtInfo(String infoBorrowerDebtInfo) {
        this.infoBorrowerDebtInfo = infoBorrowerDebtInfo;
    }

    public String getInfoBankLoanInfo() {
        return infoBankLoanInfo;
    }

    public void setInfoBankLoanInfo(String infoBankLoanInfo) {
        this.infoBankLoanInfo = infoBankLoanInfo;
    }

    public Integer getInfoOverdueNumber() {
        return infoOverdueNumber;
    }

    public void setInfoOverdueNumber(Integer infoOverdueNumber) {
        this.infoOverdueNumber = infoOverdueNumber;
    }

    public String getInfoAssetsInfo() {
        return infoAssetsInfo;
    }

    public void setInfoAssetsInfo(String infoAssetsInfo) {
        this.infoAssetsInfo = infoAssetsInfo;
    }

    public String getInfoRepayChanel() {
        return infoRepayChanel;
    }

    public void setInfoRepayChanel(String infoRepayChanel) {
        this.infoRepayChanel = infoRepayChanel;
    }
}
