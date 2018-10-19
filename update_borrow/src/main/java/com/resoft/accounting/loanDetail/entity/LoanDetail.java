package com.resoft.accounting.loanDetail.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 放款明细Entity
 * 
 * @author wangguodong
 * @version 2016-08-15
 */
public class LoanDetail extends DataEntity<LoanDetail> {

	private static final long serialVersionUID = 1L;
	private String dataDt; // 数据日期
	private Office orgLevel2; // 登记门店
	private Office orgLevel3; // 区域
	private Office orgLevel4; // 大区
	private String contractNo; // 合同编号
	private String custName; // 客户名称
	private Date loanDate; // 放款日期
	private String contractAmount; // 合同金额
	private String loanAmount;// 放款金额
	private String serviceFee;// 服务费
	private String marginAmount;// 保证金
	private String specialServiceFee;// 特殊服务费
	private Date startTime;// 开始时间
	private Date endTime;// 结束时间
	private Office company;
	private String approProductTypeName;// 产品类型
	private BigDecimal factGuaranteeFee;//实收担保费
	private BigDecimal factGuaranteeGold;//实收担保金
	private BigDecimal factServiceFee;//实收服务费
	private String loanStatus;//状态
	public String getApproProductTypeName() {
		return approProductTypeName;
	}

	public void setApproProductTypeName(String approProductTypeName) {
		this.approProductTypeName = approProductTypeName;
	}

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDataDt() {
		return dataDt;
	}

	public void setDataDt(String dataDt) {
		this.dataDt = dataDt;
	}

	public Office getOrgLevel2() {
		return orgLevel2;
	}

	public void setOrgLevel2(Office orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}

	public Office getOrgLevel3() {
		return orgLevel3;
	}

	public void setOrgLevel3(Office orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}

	public Office getOrgLevel4() {
		return orgLevel4;
	}

	public void setOrgLevel4(Office orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getMarginAmount() {
		return marginAmount;
	}

	public void setMarginAmount(String marginAmount) {
		this.marginAmount = marginAmount;
	}

	public String getSpecialServiceFee() {
		return specialServiceFee;
	}

	public void setSpecialServiceFee(String specialServiceFee) {
		this.specialServiceFee = specialServiceFee;
	}

	public LoanDetail() {
		super();
	}

	public LoanDetail(String id) {
		super(id);
	}

	public BigDecimal getFactGuaranteeFee() {
		return factGuaranteeFee;
	}

	public void setFactGuaranteeFee(BigDecimal factGuaranteeFee) {
		this.factGuaranteeFee = factGuaranteeFee;
	}

	public BigDecimal getFactGuaranteeGold() {
		return factGuaranteeGold;
	}

	public void setFactGuaranteeGold(BigDecimal factGuaranteeGold) {
		this.factGuaranteeGold = factGuaranteeGold;
	}

	public BigDecimal getFactServiceFee() {
		return factServiceFee;
	}

	public void setFactServiceFee(BigDecimal factServiceFee) {
		this.factServiceFee = factServiceFee;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

}