package com.resoft.outinterface.SV.client.entry.request;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "workOrderDto", propOrder = {
    "appNo",
    "autoIssue",
    "bsFlag",
    "closingDate",
    "commission",
    "commissionType",
    "contractNo",
    "customerNo",
    "includeWeekend",
    "inverstigatorCount",
    "inverstigatorLevel",
    "loanAmount",
    "loanType",
    "orderDiscount",
    "orderNo",
    "orderPrice",
    "orgNo",
    "productNo",
    "remarks",
    "surveyInfoDto",
    "userCode"
})
public class WorkOrderDto {
	// ���
	@XmlElement(required = true, name = "USER_CODE")
	private String userCode;
	// ���
	@XmlElement(required = true, name = "ORG_NO")
	private String orgNo;
	// ������
	@XmlElement(required = true, name = "APP_NO")
	private String appNo;
	// ί�е���
	@XmlElement(name = "ORDER_NO")
	private String orderNo;
	// ί�з��ͻ�
	@XmlElement(name = "CUSTOMER_NO")
	private String customerNo;
	// ��ͬ���
	@XmlElement(name = "CONTRACT_NO")
	private String contractNo;
	// �Ƿ��� Ĭ�� 0-��
	@XmlElement(defaultValue = "0", name = "BS_FLAG")
	private String bsFlag;
	// ������Ա����
	@XmlElement(name = "INVERSTIGATOR_LEVEL")
	private String inverstigatorLevel;
	// ��������
	@XmlElement(required = true, name = "CLOSING_DATE")
	private String closingDate;
	// �Ƿ��˫����
	@XmlElement(name = "INCLUDE_WEEKEND")
	private String includeWeekend;
	// ��������
	@XmlElement(name = "INVERSTIGATOR_COUNT")
	private String inverstigatorCount;
	// �����ܼ�
	@XmlElement(name = "ORDER_PRICE")
	private String orderPrice;
	// �����ۿ�
	@XmlElement(name = "ORDER_DISCOUNT")
	private String orderDiscount;
	// �������
	@XmlElement(name = "COMMISSION_TYPE")
	private String commissionType;
	// ���
	@XmlElement(name = "COMMISSION")
	private String commission;
	// �Զ��ɷ�
	@XmlElement(name = "AUTO_ISSUE")
	private String autoIssue;
	// �����
	@XmlElement(required = true, name = "LOAN_AMOUNT")
	private String loanAmount;
	// �������
	@XmlElement(required = true, name = "LOAN_TYPE")
	private String loanType;

	@XmlElement(name = "REMARKS")
	private String remarks;
	
	@XmlElement(name = "surveyInfoDto")
	private List<SurveyInfoDto> surveyInfoDto;

	@XmlElement(name = "PRODUCT_NO")
	private String productNo;


	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getBsFlag() {
		return bsFlag;
	}

	public void setBsFlag(String bsFlag) {
		this.bsFlag = bsFlag;
	}

	public String getInverstigatorLevel() {
		return inverstigatorLevel;
	}

	public void setInverstigatorLevel(String inverstigatorLevel) {
		this.inverstigatorLevel = inverstigatorLevel;
	}

	public String getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}

	public String getIncludeWeekend() {
		return includeWeekend;
	}

	public void setIncludeWeekend(String includeWeekend) {
		this.includeWeekend = includeWeekend;
	}

	public String getInverstigatorCount() {
		return inverstigatorCount;
	}

	public void setInverstigatorCount(String inverstigatorCount) {
		this.inverstigatorCount = inverstigatorCount;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getOrderDiscount() {
		return orderDiscount;
	}

	public void setOrderDiscount(String orderDiscount) {
		this.orderDiscount = orderDiscount;
	}

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getAutoIssue() {
		return autoIssue;
	}

	public void setAutoIssue(String autoIssue) {
		this.autoIssue = autoIssue;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<SurveyInfoDto> getSurveyInfoDto() {
		return surveyInfoDto;
	}

	public void setSurveyInfoDto(List<SurveyInfoDto> surveyInfoDto) {
		this.surveyInfoDto = surveyInfoDto;
	}

	@Override
	public String toString() {
		return "WorkOrderDto [userCode=" + userCode + ", appNo=" + appNo
				+ ", orderNo=" + orderNo + ", customerNo=" + customerNo
				+ ", contractNo=" + contractNo + ", bsFlag=" + bsFlag
				+ ", inverstigatorLevel=" + inverstigatorLevel
				+ ", closingDate=" + closingDate + ", includeWeekend="
				+ includeWeekend + ", inverstigatorCount=" + inverstigatorCount
				+ ", orderPrice=" + orderPrice + ", orderDiscount="
				+ orderDiscount + ", commissionType=" + commissionType
				+ ", commission=" + commission + ", autoIssue=" + autoIssue
				+ ", loanAmount=" + loanAmount + ", loanType=" + loanType
				+ ", remarks=" + remarks + ", surveyInfoDto=" + surveyInfoDto.toString()
				+ ", productNo=" + productNo + "]";
	}
}
