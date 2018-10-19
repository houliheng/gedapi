package com.resoft.multds.credit.plApplyRegister.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 个人客户登记Entity
 * 
 * @author wuxi01
 * @version 2016-06-17
 */
public class PLApplyRegister extends DataEntity<PLApplyRegister> {

	private static final long serialVersionUID = 1L;
	private String procInsId; // 流程实例ID
	private String serialNumber; // 序号（5位，每天从00001开始）
	private String applyNo; // 申请编号
	private String custType; // 客户类型（个人，企业）---保留字段
	private String custName; // 客户名称
	private String idType; // 证件类型
	private String idNum; // 证件号
	private String mobileNum; // 手机号
	private String channelSourceType; // 客户来源（字典类型：CHANNEL_SOURCE_TYPE）
	private String channelOtherDesc; // 渠道说明
	private String applyProductId; // 申请产品ID
	private String applyProductName; // 申请产品名称
	private String applyProductTypeCode; // 产品类型编码
	private BigDecimal applyAmount; // 申请金额
	private String registerName; // 登记人
	private Date registerDate; // 登记日期
	private Office company; // 登记机构
	private String applyStatus; // 客户申请状态（字典类型：APPLY_STATUS）
	private String orgId; // 登记机构(=合同表经办机构)
	private String plBorrowNewFlag; // 借后借新还旧标记（1：是借新还旧）
	private String productCategory;//产品分类

	public PLApplyRegister() {
		super();
	}

	public PLApplyRegister(String id) {
		super(id);
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getChannelSourceType() {
		return channelSourceType;
	}

	public void setChannelSourceType(String channelSourceType) {
		this.channelSourceType = channelSourceType;
	}

	public String getChannelOtherDesc() {
		return channelOtherDesc;
	}

	public void setChannelOtherDesc(String channelOtherDesc) {
		this.channelOtherDesc = channelOtherDesc;
	}

	public String getApplyProductId() {
		return applyProductId;
	}

	public void setApplyProductId(String applyProductId) {
		this.applyProductId = applyProductId;
	}

	public String getApplyProductName() {
		return applyProductName;
	}

	public void setApplyProductName(String applyProductName) {
		this.applyProductName = applyProductName;
	}

	public String getApplyProductTypeCode() {
		return applyProductTypeCode;
	}

	public void setApplyProductTypeCode(String applyProductTypeCode) {
		this.applyProductTypeCode = applyProductTypeCode;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getPlBorrowNewFlag() {
		return plBorrowNewFlag;
	}

	public void setPlBorrowNewFlag(String plBorrowNewFlag) {
		this.plBorrowNewFlag = plBorrowNewFlag;
	}
	
	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

}