package com.resoft.outinterface.cn.com.experian.entry.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExperianResponseHeader {

    @XmlElement(name = "SOSWebserviceVersion", required = true, defaultValue = "1.0")
    protected String sosWebserviceVersion;
    @XmlElement(name = "LoanId", required = true)
    protected String loanId;
    @XmlElement(name = "RequestNumber", required = true)
    protected String requestNumber;
    @XmlElement(name = "BorrowerName", required = true)
    protected String borrowerName;
    @XmlElement(name = "SendDate", required = true)
    @XmlSchemaType(name = "date")
    protected String sendDate;
    @XmlElement(name = "SendTime", required = true)
    protected String sendTime;
    @XmlElement(name = "LoginUserName", required = true)
    protected String loginUserName;
    @XmlElement(name = "SOSRandomNumber", required = true)
    protected String sosRandomNumber;
    @XmlElement(name = "ResultCode", required = true)
    protected String resultCode;
    @XmlElement(name = "ResultInfo", required = true)
    protected String resultInfo;
    
    protected String id;
    
    
	public String getSosWebserviceVersion() {
		return sosWebserviceVersion;
	}
	public void setSosWebserviceVersion(String sosWebserviceVersion) {
		this.sosWebserviceVersion = sosWebserviceVersion;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	public String getBorrowerName() {
		return borrowerName;
	}
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getLoginUserName() {
		return loginUserName;
	}
	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}
	public String getSosRandomNumber() {
		return sosRandomNumber;
	}
	public void setSosRandomNumber(String sosRandomNumber) {
		this.sosRandomNumber = sosRandomNumber;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultInfo() {
		return resultInfo;
	}
	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
