package com.resoft.credit.applyRegister.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.resoft.common.utils.Constants;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 个人客户登记Entity
 * 
 * @author wuxi01
 * @version 2016-03-05
 */
public class ApplyRegister extends DataEntity<ApplyRegister> {

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
	private String procDefKey;	//流程标识
	private String iftApplyId;//冠e贷申请id
	private String createId;//ged推数据用
	private String updateId;//ged推数据用
	private String companyName;//企业名称
	private String comIdType;//企业证件类型
	private String comIdNum;//企业证件号
	
	private String isSameManager;//上笔与本笔业务经理是否一致(0:否,1:是)
	private String managerDifferReason;//上笔与本笔业务经理不一致的原因
	private String isSamePlace;//登记门店与企业经营地在同一地级市(0:否,1:是)
	private String placeDifferReason;//登记门店与企业经营地不在同一地级市的原因
	
	private String participant;//参与者
	private String productCategory;//产品分类
	private String sendGED;//发送ged标志
	private String newOld;//区分新老数据
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getComIdType() {
		return comIdType;
	}

	public String getComIdTypeLabel() {
		return DictUtils.getDictLabel(comIdType, Constants.COM_ID_TYPE, "");
	}
	
	public void setComIdType(String comIdType) {
		this.comIdType = comIdType;
	}

	public String getComIdNum() {
		return comIdNum;
	}

	public void setComIdNum(String comIdNum) {
		this.comIdNum = comIdNum;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getIftApplyId() {
		return iftApplyId;
	}

	public void setIftApplyId(String iftApplyId) {
		this.iftApplyId = iftApplyId;
	}

	public ApplyRegister() {
		super();
	}

	public ApplyRegister(String id) {
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
	
	public String getIdTypeLabel() {
		return DictUtils.getDictLabel(idType, Constants.CUSTOMER_P_ID_TYPE, "");
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

	@Length(min = 0, max = 10, message = "产品类型编码长度必须介于 0 和 10 之间")
	public String getApplyProductTypeCode() {
		return applyProductTypeCode;
	}

	public void setApplyProductTypeCode(String applyProductTypeCode) {
		this.applyProductTypeCode = applyProductTypeCode;
	}
	
	public String getApplyProductTypeLabel() {
		return DictUtils.getDictLabel(applyProductTypeCode, Constants.PRODUCT_TYPE, "");
	}

	@Length(min = 0, max = 32, message = "申请产品ID长度必须介于 0 和 32 之间")
	public String getApplyProductId() {
		return applyProductId;
	}

	public void setApplyProductId(String applyProductId) {
		this.applyProductId = applyProductId;
	}

	@Length(min = 0, max = 4, message = "客户来源（字典类型：CHANNEL_SOURCE_TYPE）长度必须介于 0 和 4 之间")
	public String getChannelSourceType() {
		return channelSourceType;
	}

	public void setChannelSourceType(String channelSourceType) {
		this.channelSourceType = channelSourceType;
	}

	public String getChannelSourceTypeLabel() {
		return DictUtils.getDictLabel(channelSourceType, Constants.CHANNEL_SOURCE_TYPE, "");
	}
	
	@Length(min = 0, max = 30, message = "申请产品名称长度必须介于 0 和 30 之间")
	public String getApplyProductName() {
		return applyProductName;
	}

	public void setApplyProductName(String applyProductName) {
		this.applyProductName = applyProductName;
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

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getProcDefKey() {
		return procDefKey;
	}

	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}

	public String getIsSameManager() {
		return isSameManager;
	}

	public void setIsSameManager(String isSameManager) {
		this.isSameManager = isSameManager;
	}

	public String getManagerDifferReason() {
		return managerDifferReason;
	}

	public void setManagerDifferReason(String managerDifferReason) {
		this.managerDifferReason = managerDifferReason;
	}

	public String getIsSamePlace() {
		return isSamePlace;
	}

	public void setIsSamePlace(String isSamePlace) {
		this.isSamePlace = isSamePlace;
	}

	public String getPlaceDifferReason() {
		return placeDifferReason;
	}

	public void setPlaceDifferReason(String placeDifferReason) {
		this.placeDifferReason = placeDifferReason;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getSendGED() {
		return sendGED;
	}

	public void setSendGED(String sendGED) {
		this.sendGED = sendGED;
	}

	public String getNewOld() {
		return newOld;
	}

	public void setNewOld(String newOld) {
		this.newOld = newOld;
	}

	
	
}