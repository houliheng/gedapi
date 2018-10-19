package com.resoft.postloan.plCheckPhone.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 借后电话外访Entity
 * 
 * @author wangguodong
 * @version 2016-09-02
 */
public class PLCheckPhone extends DataEntity<PLCheckPhone> {

	private static final long serialVersionUID = 1L;
	private String contractNo; // 合同编号
	private String applyNo; // 申请编号
	private String allocateId; // 分配id
	private String roleType; // 核查对象
	private String custId; // 客户ID（主借人ID,共借人ID,担保人ID，配偶ID等）
	private String custName; // 姓名
	private String mobileNum; // 移动电话
	private Date dialTime; // 拨打时间
	private String phoneSrc; // 电话来源（字典类型：PHONE_SRC）
	private String dialResc; // 拨打情况
	private String listenerType; // 接听者身份
	private String riskPoint; // 异常风险点
	private String is114Check; // 企业电话是否经过114网查
	private String description; // 电核详情
	private String checkUserId; // 检查人ID
	private String checkUserName; // 拨打人

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public PLCheckPhone() {
		super();
	}

	public PLCheckPhone(String id) {
		super(id);
	}

	public String getAllocateId() {
		return allocateId;
	}

	public void setAllocateId(String allocateId) {
		this.allocateId = allocateId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public Date getDialTime() {
		return dialTime;
	}

	public void setDialTime(Date dialTime) {
		this.dialTime = dialTime;
	}

	public String getPhoneSrc() {
		return phoneSrc;
	}

	public void setPhoneSrc(String phoneSrc) {
		this.phoneSrc = phoneSrc;
	}

	public String getDialResc() {
		return dialResc;
	}

	public void setDialResc(String dialResc) {
		this.dialResc = dialResc;
	}

	public String getListenerType() {
		return listenerType;
	}

	public void setListenerType(String listenerType) {
		this.listenerType = listenerType;
	}

	public String getRiskPoint() {
		return riskPoint;
	}

	public void setRiskPoint(String riskPoint) {
		this.riskPoint = riskPoint;
	}

	public String getIs114Check() {
		return is114Check;
	}

	public void setIs114Check(String is114Check) {
		this.is114Check = is114Check;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

}