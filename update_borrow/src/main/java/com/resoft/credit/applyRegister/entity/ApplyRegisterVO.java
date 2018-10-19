package com.resoft.credit.applyRegister.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 综合查询模块所需Entity 在ApplyRegister基础上增加合同金额用于前台页面显示
 * 
 * @version 2016-03-01
 */
public class ApplyRegisterVO extends DataEntity<ApplyRegisterVO> {

	private static final long serialVersionUID = 1L;
	private String applyNo; // 申请编号
	private String custType; // 客户类型（个人，企业）
	private String custName; // 客户名称
	private String idType; // 证件类型
	private String idNum; // 证件号
	private String mobileNum; // 手机号
	private String productCode; // 产品编码
	private String applyAmount; // 申请金额
	private String channelSourceType; // 客户来源（字典类型：CHANNEL_SOURCE_TYPE）
	private String channelOtherDesc; // 渠道说明
	private String registerName; // 登记人
	private String registerDate; // 登记日期
	private String orgId;
	private Office company;// 机构
	private String applyStatus; // 客户申请状态（字典类型：APPLY_STATUS）
	private String procInsId; // 流程实例ID
	private String serialNumber; // 序号（5位，每天从00001开始）
	private String contactsName; // 联系人名称
	private String contactsCardIdNo; // 联系人身份证号
	private String contactsMobile; // 联系人手机号
	private String contractAmount;// 合同金额
	private String contractNo;	// 合同编号
	private String roleType;// 关联关系

	public ApplyRegisterVO() {
		super();
	}

	public ApplyRegisterVO(String id) {
		super(id);
	}

	@Length(min = 1, max = 50, message = "申请编号长度必须介于 1 和 50 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	@Length(min = 0, max = 1, message = "客户类型（个人，企业）长度必须介于 0 和 1 之间")
	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	@Length(min = 0, max = 20, message = "客户名称长度必须介于 0 和 20 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Length(min = 0, max = 10, message = "证件类型长度必须介于 0 和 10 之间")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	@Length(min = 0, max = 18, message = "证件号长度必须介于 0 和 18 之间")
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	@Length(min = 0, max = 15, message = "手机号长度必须介于 0 和 15 之间")
	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	@Length(min = 0, max = 10, message = "产品编码长度必须介于 0 和 10 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(String applyAmount) {
		this.applyAmount = applyAmount;
	}

	@Length(min = 0, max = 4, message = "客户来源（字典类型：CHANNEL_SOURCE_TYPE）长度必须介于 0 和 4 之间")
	public String getChannelSourceType() {
		return channelSourceType;
	}

	public void setChannelSourceType(String channelSourceType) {
		this.channelSourceType = channelSourceType;
	}

	@Length(min = 0, max = 1000, message = "渠道说明长度必须介于 0 和 1000 之间")
	public String getChannelOtherDesc() {
		return channelOtherDesc;
	}

	public void setChannelOtherDesc(String channelOtherDesc) {
		this.channelOtherDesc = channelOtherDesc;
	}

	@Length(min = 0, max = 20, message = "登记人长度必须介于 0 和 20 之间")
	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	@Length(min = 0, max = 4, message = "客户申请状态（字典类型：APPLY_STATUS）长度必须介于 0 和 4 之间")
	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	@Length(min = 0, max = 32, message = "流程实例ID长度必须介于 0 和 32 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	@Length(min = 0, max = 5, message = "序号（5位，每天从00001开始）长度必须介于 0 和 5 之间")
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Length(min = 0, max = 20, message = "联系人名称长度必须介于 0 和 20 之间")
	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	@Length(min = 0, max = 18, message = "联系人身份证号长度必须介于 0 和 18 之间")
	public String getContactsCardIdNo() {
		return contactsCardIdNo;
	}

	public void setContactsCardIdNo(String contactsCardIdNo) {
		this.contactsCardIdNo = contactsCardIdNo;
	}

	@Length(min = 0, max = 15, message = "联系人手机号长度必须介于 0 和 15 之间")
	public String getContactsMobile() {
		return contactsMobile;
	}

	public void setContactsMobile(String contactsMobile) {
		this.contactsMobile = contactsMobile;
	}

	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
}