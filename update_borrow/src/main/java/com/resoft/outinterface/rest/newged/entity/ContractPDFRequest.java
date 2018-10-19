package com.resoft.outinterface.rest.newged.entity;

public class ContractPDFRequest {
	private String contractNo;

	
	public ContractPDFRequest(String contractNo) {
		super();
		this.contractNo = contractNo;
	}

	public ContractPDFRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
}
