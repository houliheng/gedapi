package com.resoft.accounting.contract.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class ContractTemp extends DataEntity<ContractTemp> {

	private static final long serialVersionUID = 1L;
	private String addressId; // id
	private String addressValue; // value
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getAddressValue() {
		return addressValue;
	}
	public void setAddressValue(String addressValue) {
		this.addressValue = addressValue;
	}
	
}
