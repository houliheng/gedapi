package com.resoft.credit.gedApplyRegister.entity;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 冠e贷申请客户登记信息表Entity
 * 
 * @author wangguodong
 * @version 2017-02-14
 */
public class GedApplyRegister extends DataEntity<GedApplyRegister> {

	private static final long serialVersionUID = 1L;
	private String applyId; // 申请Id
	private String custName; // 客户名称
	private String idType; // 证件类型
	private String idNum; // 证件号
	private String mobileNum; // 手机号
	private BigDecimal applyAmount; // 申请金额
	private String channelSourceType; // 客户来源（字典类型：CHANNEL_SOURCE_TYPE）
	private Date registerDate; // 登记日期
	private String allotStatus; // 客户分配状态（字典类型：ALLOT_STATUS）
	private String locationCity; // 所在城市
	private String gedApplyStatus;// 冠易贷申请状态

	@ExcelField(title = "申请状态", align = 2, sort = 100, dictType = "GED_APPLY_STATUS")
	public String getGedApplyStatus() {
		return gedApplyStatus;
	}

	public void setGedApplyStatus(String gedApplyStatus) {
		this.gedApplyStatus = gedApplyStatus;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public GedApplyRegister() {
		super();
	}

	public GedApplyRegister(String id) {
		super(id);
	}

	@Length(min = 0, max = 32, message = "申请Id长度必须介于 0 和 32 之间")
	@ExcelField(title = "申请流水号", align = 2, sort = 20)
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	@Length(min = 0, max = 20, message = "客户名称长度必须介于 0 和 20 之间")
	@ExcelField(title = "客户名称", align = 2, sort = 10)
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Length(min = 0, max = 10, message = "证件类型长度必须介于 0 和 10 之间")
	@ExcelField(title = "证件类型", align = 2, sort = 30, dictType = "CUSTOMER_P_ID_TYPE")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	@Length(min = 0, max = 18, message = "证件号长度必须介于 0 和 18 之间")
	@ExcelField(title = "证件号", align = 2, sort = 40)
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	@Length(min = 0, max = 15, message = "手机号长度必须介于 0 和 15 之间")
	@ExcelField(title = "手机号", align = 2, sort = 50)
	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	@Length(min = 0, max = 4, message = "客户来源（字典类型：CHANNEL_SOURCE_TYPE）长度必须介于 0 和 4 之间")
	@ExcelField(title = "客户来源", align = 2, sort = 60, dictType = "CHANNEL_SOURCE_TYPE")
	public String getChannelSourceType() {
		return channelSourceType;
	}

	public void setChannelSourceType(String channelSourceType) {
		this.channelSourceType = channelSourceType;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title = "登记日期", align = 2, sort = 80)
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	@Length(min = 0, max = 4, message = "客户分配状态（字典类型：ALLOT_STATUS）长度必须介于 0 和 4 之间")
	@ExcelField(title = "客户分配状态", align = 2, sort = 90, dictType = "ALLOT_STATUS")
	public String getAllotStatus() {
		return allotStatus;
	}

	public void setAllotStatus(String allotStatus) {
		this.allotStatus = allotStatus;
	}

	@Length(min = 0, max = 20, message = "所在城市长度必须介于 0 和 20 之间")
	@ExcelField(title = "所在城市", align = 2, sort = 70)
	public String getLocationCity() {
		return locationCity;
	}

	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}

}