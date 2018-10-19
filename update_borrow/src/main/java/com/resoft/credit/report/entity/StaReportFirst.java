package com.resoft.credit.report.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 统计报表1Entity
 * @author lixing
 * @version 2016-12-09
 */
public class StaReportFirst extends DataEntity<StaReportFirst> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String dataMonth;		// 月份
	private String productTypeCode;//产品类型编码
	private String orgId;//机构id
	private String orgName;//机构名称
	private String aveContractAmount;//平均借款额度
	private String currContractAmount;//借款余额
	private String serviceFee;//服务费总额
	private String loanAmount;//放款总金额
	private String applyAmount;//申请额度
	private String repayOntimerate;//准时还款率
	private String companyAmount;//企业个数
	private String registerAmount;//进件量
	private Office company;		//机构
	private String dataMonthStart;//查询用 开始时间
	private String dataMonthEnd;//查询用 结束时间
	
	public String getDataMonthStart() {
		return dataMonthStart;
	}

	public void setDataMonthStart(String dataMonthStart) {
		this.dataMonthStart = dataMonthStart;
	}

	public String getDataMonthEnd() {
		return dataMonthEnd;
	}

	public void setDataMonthEnd(String dataMonthEnd) {
		this.dataMonthEnd = dataMonthEnd;
	}

	public StaReportFirst() {
		super();
	}

	public StaReportFirst(String id){
		super(id);
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getDataMonth() {
		return dataMonth;
	}

	public void setDataMonth(String dataMonth) {
		this.dataMonth = dataMonth;
	}

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getAveContractAmount() {
		return aveContractAmount;
	}

	public void setAveContractAmount(String aveContractAmount) {
		this.aveContractAmount = aveContractAmount;
	}

	public String getCurrContractAmount() {
		return currContractAmount;
	}

	public void setCurrContractAmount(String currContractAmount) {
		this.currContractAmount = currContractAmount;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(String applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getRepayOntimerate() {
		return repayOntimerate;
	}

	public void setRepayOntimerate(String repayOntimerate) {
		this.repayOntimerate = repayOntimerate;
	}

	public String getCompanyAmount() {
		return companyAmount;
	}

	public void setCompanyAmount(String companyAmount) {
		this.companyAmount = companyAmount;
	}

	public String getRegisterAmount() {
		return registerAmount;
	}

	public void setRegisterAmount(String registerAmount) {
		this.registerAmount = registerAmount;
	}

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}
	
}