package com.resoft.credit.applyRelation.entity;

import org.hibernate.validator.constraints.Length;

import com.resoft.credit.applyLoanStatus.entity.ApplyLoanStatus;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 申请客户关联信息表Entity
 * @author caoyinglong
 * @version 2016-02-29
 */
public class ApplyRelation extends DataEntity<ApplyRelation> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String roleType;		// 角色类型(主借人，共借人，担保人，配偶，主借企业，担保企业)
	private String relationForApply;		// 与主借人关系(亲属，朋友，同事，其他)
	private String custId;
	private String custName;
	private CustInfo custInfo;		// 客户ID(主借人，共借人，担保人，配偶，主借企业，担保企业)
	private CompanyInfo companyInfo;//
	private String description;		// 备注
	private String visitCount;//访问次数
	private ApplyLoanStatus applyLoanStatus;
	private String matchRoleType;//匹配的角色类型
	private String matchCustName;//匹配的客户名称
	private String matchApplyNo;//匹配的申请编号
	private String mateToGuarantor;//是否添加至担保人
	//是否推送数据到外访系统  0不推送  1推送
	private String svFlag;
	private String isConfirm;

	/** cre_guarantee_relation 中的 isConfirm */
	private String guaranteeConfirm;

	public ApplyRelation() {
		super();
	}

	public ApplyRelation(String id){
		super(id);
	}

	public String getMateToGuarantor() {
		return mateToGuarantor;
	}

	public void setMateToGuarantor(String mateToGuarantor) {
		this.mateToGuarantor = mateToGuarantor;
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getCustId(){
		return custId;
	}
	
	public void setCustId(String custId){
		this.custId=custId;
	}
	
	public CompanyInfo getCompanyInfo(){
		return companyInfo;
	}
	
	public void setCompanyInfo(CompanyInfo companyInfo){
		this.companyInfo=companyInfo;
	}
	
	@Length(min=0, max=2, message="角色类型(主借人，共借人，担保人，配偶，主借企业，担保企业)长度必须介于 0 和 2 之间")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	@Length(min=0, max=4, message="与主借人关系(亲属，朋友，同事，其他)长度必须介于 0 和 4 之间")
	public String getRelationForApply() {
		return relationForApply;
	}

	public void setRelationForApply(String relationForApply) {
		this.relationForApply = relationForApply;
	}
	
	public CustInfo getCustInfo() {
		return custInfo;
	}

	public void setCustInfo(CustInfo custInfo) {
		this.custInfo = custInfo;
	}
	
	@Length(min=0, max=1000, message="备注长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(String visitCount) {
		this.visitCount = visitCount;
	}

	public ApplyLoanStatus getApplyLoanStatus() {
		return applyLoanStatus;
	}

	public void setApplyLoanStatus(ApplyLoanStatus applyLoanStatus) {
		this.applyLoanStatus = applyLoanStatus;
	}

	public String getMatchRoleType() {
		return matchRoleType;
	}

	public void setMatchRoleType(String matchRoleType) {
		this.matchRoleType = matchRoleType;
	}

	public String getMatchCustName() {
		return matchCustName;
	}

	public void setMatchCustName(String matchCustName) {
		this.matchCustName = matchCustName;
	}

	public String getMatchApplyNo() {
		return matchApplyNo;
	}

	public void setMatchApplyNo(String matchApplyNo) {
		this.matchApplyNo = matchApplyNo;
	}

	public String getSvFlag() {
		return svFlag;
	}

	public void setSvFlag(String svFlag) {
		this.svFlag = svFlag;
	}

	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}

	public String getGuaranteeConfirm() {
		return guaranteeConfirm;
	}

	public void setGuaranteeConfirm(String guaranteeConfirm) {
		this.guaranteeConfirm = guaranteeConfirm;
	}
}