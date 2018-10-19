package com.gq.ged.user.api.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

/**
 * 担保函.
 *
 * @author SeppSong
 */
@ApiModel
public class GuarantorLetterResForm implements Serializable {

    private static final long serialVersionUID = 8479888015922224018L;

    @ApiModelProperty(value = "担保人")
    private String guarantor;
    @ApiModelProperty(value = "法定代表人")
    private String legalPerson;
    @ApiModelProperty(value = "联系地址")
    private String address;
    @ApiModelProperty(value = "身份证号码")
    private String identityNo;
    @ApiModelProperty("社会统一信用代码")
    private String socialCreditCode;
    @ApiModelProperty("借款人")
    private String borrower;
    @ApiModelProperty(value = "日期")
    private String date;
    @ApiModelProperty(value = "合同编号")
    private String contractNo;
    @ApiModelProperty(value = "借款金额")
    private String loanAmount;
    @ApiModelProperty(value = "借款金额--大写")
    private String loanAmountUpperCase;

    public String getGuarantor() {
        return guarantor;
    }

    public void setGuarantor(String guarantor) {
        this.guarantor = guarantor;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanAmountUpperCase() {
        return loanAmountUpperCase;
    }

    public void setLoanAmountUpperCase(String loanAmountUpperCase) {
        this.loanAmountUpperCase = loanAmountUpperCase;
    }
}
