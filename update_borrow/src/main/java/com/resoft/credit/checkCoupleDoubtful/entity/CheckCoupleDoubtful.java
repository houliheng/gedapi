package com.resoft.credit.checkCoupleDoubtful.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 两人外访Entity
 * @author yanwanmei
 * @version 2016-02-27
 */
public class CheckCoupleDoubtful extends DataEntity<CheckCoupleDoubtful> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String roleType;		// 外访对象
	private String custId;		// 姓名
	private String custName;		// 姓名
	private String checkType;		// 外访类型
	private Date checkDate;		// 外访时间
	private String checkUserId;		// 外访人ID
	private String checkUserName;		// 外访人
	private String checkUserDept;		// 外访人岗位
	private String checkAddress;		// 外访地点
	private String isCheckFee;		// 是否收取外访费
	private String checkKm;		// 公里数
	private String description;		// 外访意见
	
	public CheckCoupleDoubtful() {
		super();
	}

	public CheckCoupleDoubtful(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=0, max=4, message="外访对象长度必须介于 0 和 4 之间")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	@Length(min=0, max=32, message="姓名长度必须介于 0 和 32 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@Length(min=0, max=4, message="外访类型长度必须介于 0 和 4 之间")
	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	@Length(min=0, max=32, message="外访人ID长度必须介于 0 和 32 之间")
	public String getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}
	
	@Length(min=0, max=20, message="外访人长度必须介于 0 和 20 之间")
	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}
	
	@Length(min=0, max=10, message="外访人岗位长度必须介于 0 和 10 之间")
	public String getCheckUserDept() {
		return checkUserDept;
	}

	public void setCheckUserDept(String checkUserDept) {
		this.checkUserDept = checkUserDept;
	}
	
	@Length(min=0, max=300, message="外访地点长度必须介于 0 和 300 之间")
	public String getCheckAddress() {
		return checkAddress;
	}

	public void setCheckAddress(String checkAddress) {
		this.checkAddress = checkAddress;
	}
	
	@Length(min=0, max=1, message="是否收取外访费长度必须介于 0 和 1 之间")
	public String getIsCheckFee() {
		return isCheckFee;
	}

	public void setIsCheckFee(String isCheckFee) {
		this.isCheckFee = isCheckFee;
	}
	
	public String getCheckKm() {
		return checkKm;
	}

	public void setCheckKm(String checkKm) {
		this.checkKm = checkKm;
	}
	
	@Length(min=0, max=1000, message="外访意见长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	
}