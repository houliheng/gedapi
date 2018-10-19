package com.resoft.credit.creditViewBook.entity.creditAssets;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 信审意见书资产清单Entity
 * @author wuxi01
 * @version 2016-02-29
 */
public class CreditAssets extends DataEntity<CreditAssets> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String roleType;		// 角色类型(主借人，共借人，担保人，主借企业，担保企业)
	private String assetsOwnerId;		// 权属人ID
	private String assetsOwnerName;		// 权属人名称
	private String assetsName;		// 资产名称
	private String marketValue;		// 市值
	private String isMortgage;		// 是否已抵押
	private String isCheck;		// 是否外访
	private String detailComment;		// 详细情况
	
	public CreditAssets() {
		super();
	}

	public CreditAssets(String id){
		super(id);
	}

	@Length(min=0, max=32, message="申请编号长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=0, max=4, message="角色类型(主借人，共借人，担保人，主借企业，担保企业)长度必须介于 0 和 4 之间")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	@Length(min=0, max=32, message="权属人ID长度必须介于 0 和 32 之间")
	public String getAssetsOwnerId() {
		return assetsOwnerId;
	}

	public void setAssetsOwnerId(String assetsOwnerId) {
		this.assetsOwnerId = assetsOwnerId;
	}
	
	@Length(min=0, max=20, message="权属人名称长度必须介于 0 和 20 之间")
	public String getAssetsOwnerName() {
		return assetsOwnerName;
	}

	public void setAssetsOwnerName(String assetsOwnerName) {
		this.assetsOwnerName = assetsOwnerName;
	}
	
	@Length(min=0, max=20, message="资产名称长度必须介于 0 和 20 之间")
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
	
	@Length(min=0, max=1, message="是否已抵押长度必须介于 0 和 1 之间")
	public String getIsMortgage() {
		return isMortgage;
	}

	public void setIsMortgage(String isMortgage) {
		this.isMortgage = isMortgage;
	}
	
	@Length(min=0, max=1, message="是否外访长度必须介于 0 和 1 之间")
	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	
	@Length(min=0, max=1000, message="详细情况长度必须介于 0 和 1000 之间")
	public String getDetailComment() {
		return detailComment;
	}

	public void setDetailComment(String detailComment) {
		this.detailComment = detailComment;
	}
	
}