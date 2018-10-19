package com.resoft.accounting.applyMarginRepay.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 保证金退还申请查询列表VO
 * 
 * @author wuxi01
 * @version 2016-03-04
 */
public class ApplyMarginRepayVO extends DataEntity<ApplyMarginRepayVO> {

	private static final long serialVersionUID = 1L;

	private String custName;// 客户名称
	private String contractNo; // 合同编号
	private String contractAmount; // 合同金额
	private String settleDate; // 结清日期
	private String operateId; // 经办人ID
	private String operateOrgId; // 经办机构ID（合同归属公司）
	private Office orgLevel2; // 大区
	private Office orgLevel3; // 区域
	private Office orgLevel4; // 登记门店
	private String marginAmount; // 缴纳保证金金额
	private String approMarginAmount;// 退还保证金金额
	private String marginAmountStatus;// 保证金退还状态
	private Office company;
	private Date startTime;
	private Date endTime;
	private Date marginAmountTime;//退款时间
	public Date getMarginAmountTime() {
		return marginAmountTime;
	}

	public void setMarginAmountTime(Date marginAmountTime) {
		this.marginAmountTime = marginAmountTime;
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

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public ApplyMarginRepayVO() {
		super();
	}

	public ApplyMarginRepayVO(String id) {
		super(id);
	}

	@Length(min = 0, max = 32, message = "2级公司长度必须介于 0 和 32 之间")
	public Office getOrgLevel2() {
		return orgLevel2;
	}

	public void setOrgLevel2(Office orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}

	@Length(min = 0, max = 32, message = "3级公司长度必须介于 0 和 32 之间")
	public Office getOrgLevel3() {
		return orgLevel3;
	}

	public void setOrgLevel3(Office orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}

	@Length(min = 0, max = 32, message = "4级公司长度必须介于 0 和 32 之间")
	public Office getOrgLevel4() {
		return orgLevel4;
	}

	public void setOrgLevel4(Office orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}

	@Length(min = 0, max = 32, message = "经办人ID长度必须介于 0 和 32 之间")
	public String getOperateId() {
		return operateId;
	}

	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}

	@Length(min = 0, max = 32, message = "经办机构ID（合同归属公司）长度必须介于 0 和 32 之间")
	public String getOperateOrgId() {
		return operateOrgId;
	}

	public void setOperateOrgId(String operateOrgId) {
		this.operateOrgId = operateOrgId;
	}

	@Length(min = 0, max = 32, message = "合同编号长度必须介于 0 和 32 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getMarginAmount() {
		return marginAmount;
	}

	public void setMarginAmount(String marginAmount) {
		this.marginAmount = marginAmount;
	}

	public String getApproMarginAmount() {
		return approMarginAmount;
	}

	public void setApproMarginAmount(String approMarginAmount) {
		this.approMarginAmount = approMarginAmount;
	}

	@Length(min = 0, max = 4, message = "保证金退还状态长度必须介于 0 和 4 之间")
	public String getMarginAmountStatus() {
		return marginAmountStatus;
	}

	public void setMarginAmountStatus(String marginAmountStatus) {
		this.marginAmountStatus = marginAmountStatus;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

}