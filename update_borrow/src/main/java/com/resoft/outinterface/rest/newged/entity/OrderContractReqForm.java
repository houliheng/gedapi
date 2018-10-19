package com.resoft.outinterface.rest.newged.entity;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wrh on 2018/5/28.
 */
public class OrderContractReqForm implements Serializable {
    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "订单编号")
    public String orderNo;
    //@ApiModelProperty(value = "合同号")
    private String contractNo;
    //@ApiModelProperty(value = "子订单编号")
    private String applyIdChild;
    
    private String accountManageFee;
    
    private String serviceFee;
    
    private String contractProvince ;
    private String contractCity   ;
    private String contractDistinct;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getApplyIdChild() {
        return applyIdChild;
    }

    public void setApplyIdChild(String applyIdChild) {
        this.applyIdChild = applyIdChild;
    }

    public String getAccountManageFee() {
        return accountManageFee;
    }

    public void setAccountManageFee(String accountManageFee) {
        this.accountManageFee = accountManageFee;
    }

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getContractProvince() {
		return contractProvince;
	}

	public void setContractProvince(String contractProvince) {
		this.contractProvince = contractProvince;
	}

	public String getContractCity() {
		return contractCity;
	}

	public void setContractCity(String contractCity) {
		this.contractCity = contractCity;
	}

	public String getContractDistinct() {
		return contractDistinct;
	}

	public void setContractDistinct(String contractDistinct) {
		this.contractDistinct = contractDistinct;
	}


}