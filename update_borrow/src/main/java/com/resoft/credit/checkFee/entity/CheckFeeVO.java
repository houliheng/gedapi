package com.resoft.credit.checkFee.entity;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 外访费返还Entity
 * 
 * @author yanwanmei
 * @version 2016-02-29
 */
public class CheckFeeVO extends DataEntity<CheckFeeVO> {

	private static final long serialVersionUID = 1L;
	private String applyNo; // 申请编号
	private String isCollect; // 是否收取
	private String checkUserId; // 核查人ID
	private String checkUserName; // 收取人
	private String checkDate; // 收取时间
	private String checkFee; // 收取金额
	private String description; // 备注
	private String returnCheckFee; // 返还金额
	private String returnTime; // 返还日期
	private String returnUserId; // 返还操作人ID
	private String returnUserName; // 返还操作人名称
	private String descriptionReturn; // 返还备注
	private String custName;// 客户名称
	private String contractNo;// 合同编号
	private String contractAmount; // 合同金额
	private String applyStatus;// 申请状态
	private String loginId;
	
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public CheckFeeVO() {
		super();
	}

	public CheckFeeVO(String id) {
		super(id);
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}

	@Length(min = 1, max = 32, message = "申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	@Length(min = 0, max = 1, message = "是否收取长度必须介于 0 和 1 之间")
	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}

	@Length(min = 0, max = 32, message = "核查人ID长度必须介于 0 和 32 之间")
	public String getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}

	@Length(min = 0, max = 20, message = "收取人长度必须介于 0 和 20 之间")
	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

	public String getCheckFee() {
		return checkFee;
	}

	public void setCheckFee(String checkFee) {
		this.checkFee = checkFee;
	}

	@Length(min = 0, max = 1000, message = "备注长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReturnCheckFee() {
		return returnCheckFee;
	}

	public void setReturnCheckFee(String returnCheckFee) {
		this.returnCheckFee = returnCheckFee;
	}

	@Length(min = 0, max = 32, message = "返还操作人ID长度必须介于 0 和 32 之间")
	public String getReturnUserId() {
		return returnUserId;
	}

	public void setReturnUserId(String returnUserId) {
		this.returnUserId = returnUserId;
	}

	@Length(min = 0, max = 20, message = "返还操作人名称长度必须介于 0 和 20 之间")
	public String getReturnUserName() {
		return returnUserName;
	}

	public void setReturnUserName(String returnUserName) {
		this.returnUserName = returnUserName;
	}

	@Length(min = 0, max = 1000, message = "返还备注长度必须介于 0 和 1000 之间")
	public String getDescriptionReturn() {
		return descriptionReturn;
	}

	public void setDescriptionReturn(String descriptionReturn) {
		this.descriptionReturn = descriptionReturn;
	}

}