package com.resoft.accounting.repayPlan.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.resoft.accounting.staOverdueStatus.entity.StaOverdueStatus;
import com.resoft.accounting.staRepayPlanStatus.entity.StaRepayPlanStatus;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 应还款查询Entity
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
public class AccRepayPlan extends DataEntity<AccRepayPlan> {

	private static final long serialVersionUID = 1L;
	private Office orgLevel2; // 大区
	private String periodNum; // 当期期数
	private Office orgLevel3; // 区域
	private String deductDate; // 应还款日期
	private Office orgLevel4; // 登记门店
	private String custName; // 客户名称
	private String repayAmount; // 还款金额-本息合计
	private String contractNo; // 合同编号
	private String periodValue; // 期限值
	private String interestAmount; // 其中利息
	private String serviceFee; // 还款金额-服务费
	private String mangementFee; // 还款金额-账户管理费
	private String capitalAmount; // 还款金额-本金
	private Date createTime; // 创建时间
	private String penaltyAmount; // 违约金
	private String repayPeriodStatus; // 期状态（临时） 单做拼表用
	private StaRepayPlanStatus staRepayPlanStatus;
	private StaOverdueStatus staOverdueStatus;
	private Date startTime; // 做查询用 起始时间
	private Date endTime; // 做查询用 结束时间
	private String fineAmount;// 应还罚息(财务调整用)
	private Office company;
	private String contractAmount;// 合同金额
	private String couponAmount; //应还本息总额 : 本金总额 + 利息总额
	private String mangementFeeAmount; //账户管理费总额


	public String getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(String couponAmount) {
		this.couponAmount = couponAmount;
	}

	public String getMangementFeeAmount() {
		return mangementFeeAmount;
	}

	public void setMangementFeeAmount(String mangementFeeAmount) {
		this.mangementFeeAmount = mangementFeeAmount;
	}

	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public String getFineAmount() {
		return fineAmount;
	}

	public void setFineAmount(String fineAmount) {
		this.fineAmount = fineAmount;
	}

	public void setStaOverdueStatus(StaOverdueStatus staOverdueStatus) {
		this.staOverdueStatus = staOverdueStatus;
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

	public String getRepayPeriodStatus() {
		return repayPeriodStatus;
	}

	public void setRepayPeriodStatus(String repayPeriodStatus) {
		this.repayPeriodStatus = repayPeriodStatus;
	}

	public String getPenaltyAmount() {
		return penaltyAmount;
	}

	public void setPenaltyAmount(String penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public AccRepayPlan() {
		super();
	}

	public AccRepayPlan(String id) {
		super(id);
	}

	@Length(min = 0, max = 32, message = "大区长度必须介于 0 和 32 之间")
	public Office getOrgLevel2() {
		return orgLevel2;
	}

	public void setOrgLevel2(Office orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}

	@Length(min = 0, max = 10, message = "当期期数长度必须介于 0 和 10 之间")
	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}

	@Length(min = 0, max = 32, message = "区域长度必须介于 0 和 32 之间")
	public Office getOrgLevel3() {
		return orgLevel3;
	}

	public void setOrgLevel3(Office orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}

	@Length(min = 0, max = 10, message = "应还款日期长度必须介于 0 和 10 之间")
	public String getDeductDate() {
		return deductDate;
	}

	public void setDeductDate(String deductDate) {
		this.deductDate = deductDate;
	}

	@Length(min = 0, max = 32, message = "登记门店长度必须介于 0 和 32 之间")
	public Office getOrgLevel4() {
		return orgLevel4;
	}

	public void setOrgLevel4(Office orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}

	@Length(min = 0, max = 20, message = "客户名称长度必须介于 0 和 20 之间")
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

	@Length(min = 0, max = 50, message = "合同编号长度必须介于 0 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Length(min = 0, max = 4, message = "期限值长度必须介于 0 和 4 之间")
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
		this.serviceFee = serviceFee;
	}

	public String getMangementFee() {
		return mangementFee;
	}

	public void setMangementFee(String mangementFee) {
		this.mangementFee = mangementFee;
	}

	public String getCapitalAmount() {
		return capitalAmount;
	}

	public void setCapitalAmount(String capitalAmount) {
		this.capitalAmount = capitalAmount;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public StaRepayPlanStatus getStaRepayPlanStatus() {
		return staRepayPlanStatus;
	}

	public void setStaRepayPlanStatus(StaRepayPlanStatus staRepayPlanStatus) {
		this.staRepayPlanStatus = staRepayPlanStatus;
	}

	public StaOverdueStatus getStaOverdueStatus() {
		return staOverdueStatus;
	}

}