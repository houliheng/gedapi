package com.resoft.outinterface.rest.sendGET.entry;

import java.io.Serializable;

public class SendGETRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	private String contractNo;
	private String period;
	private String compensatoryCustId;
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getCompensatoryCustId() {
		return compensatoryCustId;
	}
	public void setCompensatoryCustId(String compensatoryCustId) {
		this.compensatoryCustId = compensatoryCustId;
	}



}
