package com.gq.ged.user.api.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author yxh by 2018/5/31
 */
public class ContractNoReqForm implements Serializable {
    @ApiModelProperty(value = "合同号")
    private String contractNo;

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }
}
