package com.gq.ged.user.api.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 网络借贷平台借款情况披露及承诺书 请求.
 *
 * @author SeppSong
 */
@ApiModel
public class LoanGuaranteeProtocolReqForm implements Serializable{

    private static final long serialVersionUID = -4534701611441812884L;
    @ApiModelProperty
    private String otherPlatformLoanAmount;
    @ApiModelProperty
    private String orderNo;

    public String getOtherPlatformLoanAmount() {
        return otherPlatformLoanAmount;
    }

    public void setOtherPlatformLoanAmount(String otherPlatformLoanAmount) {
        this.otherPlatformLoanAmount = otherPlatformLoanAmount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
