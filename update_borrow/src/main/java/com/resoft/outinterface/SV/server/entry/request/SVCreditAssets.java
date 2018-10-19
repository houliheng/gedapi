package com.resoft.outinterface.SV.server.entry.request;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.thinkgem.jeesite.common.utils.IdGen;

/**
 * SV接口信审意见书资产清单Entity
 * 
 * @author wuxi01
 * @version 2016-04-21
 */
@XmlAccessorType(XmlAccessType.NONE)
public class SVCreditAssets implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String applyNo; // 申请编号
	private Date createDate; // 创建日期
	private Date updateDate; // 更新日期
	private String delFlag; // 删除标记（0：正常；1：删除；2：审核）
	private CustInfo custInfo;// 客户基本信息
	private CompanyInfo companyInfo;// 企业基本信息
	@XmlElement(required = false, name = "ROLE_TYPE")
	private String roleType; // 角色类型(主借人，共借人，担保人，主借企业，担保企业)
	@XmlElement(required = false, name = "ID_TYPE")
	private String assetsOwnerIdTyoe; // 权属人证件类型(企业法人证件类型)
	@XmlElement(required = false, name = "ID_NUM")
	private String assetsOwnerIdNum; // 权属人证件号(企业法人证件号)
	@XmlElement(required = false, name = "ASSETS_NAME")
	private String assetsName; // 资产名称
	@XmlElement(required = false, name = "MARKET_VALUE")
	private String marketValue; // 市值
	@XmlElement(required = false, name = "IS_MORTGAGE")
	private String isMortgage; // 是否已抵押
	@XmlElement(required = false, name = "IS_CHECK")
	private String isCheck; // 是否外访
	@XmlElement(required = false, name = "DETAIL_COMMENT")
	private String detailComment; // 详细情况

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getAssetsOwnerIdTyoe() {
		return assetsOwnerIdTyoe;
	}

	public void setAssetsOwnerIdTyoe(String assetsOwnerIdTyoe) {
		this.assetsOwnerIdTyoe = assetsOwnerIdTyoe;
	}

	public String getAssetsOwnerIdNum() {
		return assetsOwnerIdNum;
	}

	public void setAssetsOwnerIdNum(String assetsOwnerIdNum) {
		this.assetsOwnerIdNum = assetsOwnerIdNum;
	}

	public String getAssetsName() {
		return assetsName;
	}

	public void setAssetsName(String assetsName) {
		this.assetsName = assetsName;
	}

	public String getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}

	public String getIsMortgage() {
		return isMortgage;
	}

	public void setIsMortgage(String isMortgage) {
		this.isMortgage = isMortgage;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getDetailComment() {
		return detailComment;
	}

	public void setDetailComment(String detailComment) {
		this.detailComment = detailComment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public void preInsert() {
		setId(IdGen.uuid());
		this.createDate = new Date();
	}

	public CustInfo getCustInfo() {
		return custInfo;
	}

	public void setCustInfo(CustInfo custInfo) {
		this.custInfo = custInfo;
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}
}