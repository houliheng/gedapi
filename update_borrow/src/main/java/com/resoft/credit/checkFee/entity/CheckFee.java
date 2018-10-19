package com.resoft.credit.checkFee.entity;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 外访费登记Entity
 * @author yanwanmei
 * @version 2016-02-29
 */
public class CheckFee extends DataEntity<CheckFee> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String isCollect;		// 是否收取
	private String checkUserId;		// 核查人ID
	private String checkUserName;		// 收取人
	private Date checkDate;		// 收取时间
	private BigDecimal checkFee;		// 收取金额
	private String description;		// 备注
	private String returnCheckFee;		// 返还金额
	private Date returnTime;		// 返还日期
	private String returnUserId;		// 返还操作人ID
	private String returnUserName;		// 返还操作人名称
	private String descriptionReturn;		// 返还备注
	
	public CheckFee() {
		super();
	}

	public CheckFee(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=0, max=1, message="是否收取长度必须介于 0 和 1 之间")
	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}
	
	@Length(min=0, max=32, message="核查人ID长度必须介于 0 和 32 之间")
	public String getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}
	
	@Length(min=0, max=20, message="收取人长度必须介于 0 和 20 之间")
	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	
	public BigDecimal getCheckFee() {
		return checkFee;
	}

	public void setCheckFee(BigDecimal checkFee) {
		this.checkFee = checkFee;
	}

	@Length(min=0, max=1000, message="备注长度必须介于 0 和 1000 之间")
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	
	@Length(min=0, max=32, message="返还操作人ID长度必须介于 0 和 32 之间")
	public String getReturnUserId() {
		return returnUserId;
	}

	public void setReturnUserId(String returnUserId) {
		this.returnUserId = returnUserId;
	}
	
	@Length(min=0, max=20, message="返还操作人名称长度必须介于 0 和 20 之间")
	public String getReturnUserName() {
		return returnUserName;
	}

	public void setReturnUserName(String returnUserName) {
		this.returnUserName = returnUserName;
	}
	
	@Length(min=0, max=1000, message="返还备注长度必须介于 0 和 1000 之间")
	public String getDescriptionReturn() {
		return descriptionReturn;
	}

	public void setDescriptionReturn(String descriptionReturn) {
		this.descriptionReturn = descriptionReturn;
	}
	
}