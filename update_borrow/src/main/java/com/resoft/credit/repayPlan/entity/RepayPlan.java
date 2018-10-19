package com.resoft.credit.repayPlan.entity;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 还款计划Entity
 * @author wuxi01
 * @version 2016-03-01
 */
public class RepayPlan extends DataEntity<RepayPlan> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String contractNo;		// 合同编号
	private String duebillId;		// 借据ID
	private String periodNum;		// 期数
	private BigDecimal repayAmount;		// 当月本息总额
	private BigDecimal interestAmount;		// 其中利息
	private BigDecimal serviceFee;		// 还款金额-服务费
	private BigDecimal managementFee;		// 还款金额-账户管理费(差额还款金额-合计)
	private BigDecimal capitalAmount;		// 还款金额-本金
	private Date deductDate;		// 计划扣款日期
	private BigDecimal overduePenalty;		// 逾期违约金
	
	//新增字段
	private BigDecimal bidCapitalAmount;		//标的还款金额-本金
	private BigDecimal bidInterestAmount;	//标的还款金额-利息
	private BigDecimal bidRepayAmount;		//标的还款金额-合计
	private BigDecimal difCapitalAmount;		//差额还款金额-本金
	private BigDecimal difInterestAmount;	//差额还款金额-利息
	
	private String orgLevel2; //非本表数据：向acc_repay_plan表中插入数据时使用
	private String orgLevel3;//非本表数据：向acc_repay_plan表中插入数据时使用
	private String orgLevel4;//非本表数据：向acc_repay_plan表中插入数据时使用
	private String custName;//非本表数据：向acc_repay_plan表中插入数据时使用
	private String capitalTerraceNo;//非本表数据：向acc_repay_plan表中插入数据时使用
	private int periodValue;//非本表数据：向acc_repay_plan表中插入数据时使用
	private String deductDateStr;//非本表数据：向acc_repay_plan表中插入数据时使用
	private String createDateStr;//非本表数据：向acc_repay_plan表中插入数据时使用
	
	private String taskDefKey;//流程id
	
	public RepayPlan() {
		super();
	}

	public RepayPlan(String id){
		super(id);
	}

	@Length(min=0, max=32, message="申请编号长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=0, max=32, message="合同编号长度必须介于 0 和 32 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=32, message="借据ID长度必须介于 0 和 32 之间")
	public String getDuebillId() {
		return duebillId;
	}

	public void setDuebillId(String duebillId) {
		this.duebillId = duebillId;
	}
	
	@Length(min=0, max=10, message="期数长度必须介于 0 和 10 之间")
	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDeductDate() {
		return deductDate;
	}

	public void setDeductDate(Date deductDate) {
		this.deductDate = deductDate;
	}

	public BigDecimal getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}

	public BigDecimal getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(BigDecimal interestAmount) {
		this.interestAmount = interestAmount;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	
	public BigDecimal getManagementFee() {
		return managementFee;
	}

	public void setManagementFee(BigDecimal managementFee) {
		this.managementFee = managementFee;
	}

	public BigDecimal getCapitalAmount() {
		return capitalAmount;
	}

	public void setCapitalAmount(BigDecimal capitalAmount) {
		this.capitalAmount = capitalAmount;
	}

	public BigDecimal getOverduePenalty() {
		return overduePenalty;
	}

	public void setOverduePenalty(BigDecimal overduePenalty) {
		this.overduePenalty = overduePenalty;
	}

	public String getOrgLevel2() {
		return orgLevel2;
	}

	public void setOrgLevel2(String orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}

	public String getOrgLevel3() {
		return orgLevel3;
	}

	public void setOrgLevel3(String orgLevel3) {
		this.orgLevel3 = orgLevel3;
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

	public String getCapitalTerraceNo() {
		return capitalTerraceNo;
	}

	public void setCapitalTerraceNo(String capitalTerraceNo) {
		this.capitalTerraceNo = capitalTerraceNo;
	}

	public int getPeriodValue() {
		return periodValue;
	}

	public void setPeriodValue(int periodValue) {
		this.periodValue = periodValue;
	}

	public String getDeductDateStr() {
		return deductDateStr;
	}

	public void setDeductDateStr(String deductDateStr) {
		this.deductDateStr = deductDateStr;
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	
	
	//**********新增字段的set和get方法**********//
	
	public BigDecimal getBidCapitalAmount() {
		return bidCapitalAmount;
	}

	public void setBidCapitalAmount(BigDecimal bidCapitalAmount) {
		this.bidCapitalAmount = bidCapitalAmount;
	}

	public BigDecimal getBidInterestAmount() {
		return bidInterestAmount;
	}

	public void setBidInterestAmount(BigDecimal bidInterestAmount) {
		this.bidInterestAmount = bidInterestAmount;
	}

	public BigDecimal getBidRepayAmount() {
		return bidRepayAmount;
	}

	public void setBidRepayAmount(BigDecimal bidRepayAmount) {
		this.bidRepayAmount = bidRepayAmount;
	}

	public BigDecimal getDifCapitalAmount() {
		return difCapitalAmount;
	}

	public void setDifCapitalAmount(BigDecimal difCapitalAmount) {
		this.difCapitalAmount = difCapitalAmount;
	}

	public BigDecimal getDifInterestAmount() {
		return difInterestAmount;
	}

	public void setDifInterestAmount(BigDecimal difInterestAmount) {
		this.difInterestAmount = difInterestAmount;
	}
	//*******************************//
}