package com.resoft.Accoutinterface.gqget.server.entity;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.DataEntity;
@SuppressWarnings("serial")
public class GqgetCustInfo extends DataEntity<GqgetCustInfo> {

	private String gqgetCustId;  //冠E通客户ID
	private String gqgetCustName;//冠E通客户名称
	private String idNum;        //身份证号
	private String custType;
	private String phoneNo;
	private List<BankInfo> bankInfo;
	public GqgetCustInfo() {
		super();
	}
	public String getGqgetCustId() {
		return gqgetCustId;
	}
	public void setGqgetCustId(String gqgetCustId) {
		this.gqgetCustId = gqgetCustId;
	}
	public String getGqgetCustName() {
		return gqgetCustName;
	}
	public void setGqgetCustName(String gqgetCustName) {
		this.gqgetCustName = gqgetCustName;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public List<BankInfo> getBankInfo() {
		return bankInfo;
	}
	public void setBankInfo(List<BankInfo> bankInfo) {
		this.bankInfo = bankInfo;
	}

}
