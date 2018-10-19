package com.resoft.credit.repayPlanUnion.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 还款计划授信Entity
 * @author wangguodong
 * @version 2016-12-14
 */
public class RepayPlanUnion extends DataEntity<RepayPlanUnion> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String approveId;		// 批复ID
	private String contractNo;		// 合同编号
	private String duebillId;		// 借据ID
	private String periodNum;		// 期数
	private Date deductDate;		// 计划扣款日期
	private BigDecimal repayAmount;		// 还款金额-合计
	private BigDecimal serviceFee;		// 还款金额-服务费
	private BigDecimal managementFee;		// 标的还款金额（本息差额）-账户管理费
	private BigDecimal capitalAmount;		// 标的还款金额-本金
	private BigDecimal interestAmount;		// 标的还款金额-利息
	private BigDecimal bidCapitalAmount;		// 合同还款金额-本金
	private BigDecimal bidInterestAmount;		// 合同还款金额-利息
	private BigDecimal bidRepayAmount;		// 合同还款金额-本息合计
	private BigDecimal difCapitalAmount;		// 差额还款金额-本金
	private BigDecimal difInterestAmount;		// 差额还款金额-利息
	private BigDecimal overduePenalty;		// 逾期违约金
	private String taskDefKey;		// 流程ID
	private String orgLevel2; //非本表数据：向acc_repay_plan表中插入数据时使用
	private String orgLevel3;//非本表数据：向acc_repay_plan表中插入数据时使用
	private String orgLevel4;//非本表数据：向acc_repay_plan表中插入数据时使用
	private String custName;//非本表数据：向acc_repay_plan表中插入数据时使用
	private String capitalTerraceNo;//非本表数据：向acc_repay_plan表中插入数据时使用
	private int periodValue;//非本表数据：向acc_repay_plan表中插入数据时使用
	private String deductDateStr;//非本表数据：向acc_repay_plan表中插入数据时使用
	private String createDateStr;//非本表数据：向acc_repay_plan表中插入数据时使用
	
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

	public RepayPlanUnion() {
		super();
	}

	public RepayPlanUnion(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=1, max=32, message="批复ID长度必须介于 1 和 32 之间")
	public String getApproveId() {
		return approveId;
	}

	public void setApproveId(String approveId) {
		this.approveId = approveId;
	}
	
	@Length(min=1, max=32, message="合同编号长度必须介于 1 和 32 之间")
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
	
	public BigDecimal getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(BigDecimal interestAmount) {
		this.interestAmount = interestAmount;
	}
	
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
	
	public BigDecimal getOverduePenalty() {
		return overduePenalty;
	}

	public void setOverduePenalty(BigDecimal overduePenalty) {
		this.overduePenalty = overduePenalty;
	}
	
	@Length(min=0, max=50, message="流程ID长度必须介于 0 和 50 之间")
	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	
}