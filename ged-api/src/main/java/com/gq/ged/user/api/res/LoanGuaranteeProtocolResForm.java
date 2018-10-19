package com.gq.ged.user.api.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 网络借贷平台借款情况披露及承诺书.
 *
 * @author SeppSong
 */
@ApiModel
public class LoanGuaranteeProtocolResForm implements Serializable{

    private static final long serialVersionUID = 1542445710881363154L;

    @ApiModelProperty(value = "其他平台借款金额")
    private String otherPlatformLoanAmount;
    @ApiModelProperty(value = "借款金额")
    private String loanAmount;
    @ApiModelProperty(value = "自然人姓名")
    private String name;
    @ApiModelProperty(value = "法人姓名")
    private String legalPerson;
    @ApiModelProperty(value = "时间")
    private String date;

    public String getOtherPlatformLoanAmount() {
        return otherPlatformLoanAmount;
    }

    public void setOtherPlatformLoanAmount(String otherPlatformLoanAmount) {
        this.otherPlatformLoanAmount = otherPlatformLoanAmount;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
