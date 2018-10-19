package com.resoft.outinterface.rest.ged.entity.request;

import java.io.Serializable;

/**
 * 更新合同签署信息请求.
 *
 * @author SeppSong
 */
public class ContractSignFlagRequest implements Serializable{

    private static final long serialVersionUID = -8571024906748135173L;

    private String contractNo;

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    @Override
    public String toString() {
        return "ContractSignFlagRequest{" + "contractNo='" + contractNo + '\'' + '}';
    }
}
