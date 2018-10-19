package com.resoft.accounting.repayPlan.entity;


import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 应还款查询Entity
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
public class RepayPlanImport  {

	

	private String importId;
	private String orgLevel2; // 大区
	private String orgLevel3; // 区域
	private String orgLevel4; // 登记门店
	private String custName; // 客户名称
	private String capitalTerraceNo;// 资金平台账号
	private String contractNo; // 合同编号
	private String periodValue; // 期限值
	private String periodNum; // 当期期数
	private String deductDate; // 应还款日期
	private String repayAmount; // 还款金额-本息合计
	private String serviceFee; // 还款金额-服务费
	private String mangementFee; // 还款金额-账户管理费
	private String capitalAmount; // 还款金额-本金
	private String interestAmount; // 其中利息
	private String penaltyAmount; // 违约金
	private String createTime;


	public String getImportId() {
		return importId;
	}

	public void setImportId(String importId) {
		this.importId = importId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCapitalTerraceNo() {
		return capitalTerraceNo;
	}

	public void setCapitalTerraceNo(String capitalTerraceNo) {
		this.capitalTerraceNo = capitalTerraceNo;
	}

	public String getPenaltyAmount() {
		return penaltyAmount;
	}

	public void setPenaltyAmount(String penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}

	public RepayPlanImport() {
		super();
	}

	public String getOrgLevel2() {
		return orgLevel2;
	}

	public void setOrgLevel2(String orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}

	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}

	public String getOrgLevel3() {
		return orgLevel3;
	}

	public void setOrgLevel3(String orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}

	public String getDeductDate() {
		return deductDate;
	}

	public void setDeductDate(String deductDate) {
		this.deductDate = deductDate;
	}

	public String getOrgLevel4() {
		return orgLevel4;
	}

	public void setOrgLevel4(String orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(String repayAmount) {
		this.repayAmount = repayAmount;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getPeriodValue() {
		return periodValue;
	}

	public void setPeriodValue(String periodValue) {
		this.periodValue = periodValue;
	}

	public String getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(String interestAmount) {
		this.interestAmount = interestAmount;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		if(StringUtils.isEmpty(serviceFee)){
			this.serviceFee = "0.00";
		}else{
			this.serviceFee = serviceFee;
		}
	}

	public String getMangementFee() {
		return mangementFee;
	}

	public void setMangementFee(String mangementFee) {
		if(StringUtils.isEmpty(mangementFee)){
			this.mangementFee = "0.00";
		}else{
			this.mangementFee = mangementFee;
		}
	}

	public String getCapitalAmount() {
		return capitalAmount;
	}

	public void setCapitalAmount(String capitalAmount) {
		if(StringUtils.isEmpty(capitalAmount)){
			this.capitalAmount = "0.00";
		}else{
			this.capitalAmount = capitalAmount;
		}
	}


}