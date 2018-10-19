package com.resoft.accounting.applyChangeBankcard.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 银行卡变更申请Entity
 * @author wuxi01
 * @version 2016-03-03
 */
public class ApplyChangeBankcard extends DataEntity<ApplyChangeBankcard> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private String newRepBankcardNo;		// 新还款银行卡号
	private String newRepBankcardName;		// 新还款银行卡账户名称
	private String newRepBank;		// 新还款银行（字典类型：BANKS）
	private String newRepBankName;		// 新还款银行网点名称
	private String newRepBankMobile;		// 新收款移动电话
	private String newRepBankIdNum;		// 新收款人身份证号
	private String newRepBankProvince;		// 新还款银行地址//省Province
	private String newRepBankCity;		// 新还款银行地址//市city
	private String newRepBankDistinct;		// 新还款银行地址//区district
	private String newRepBankDetail;		// 新还款银行地址//详细
	private String pictureAddress;		// 证件图片地址路径
	private String flowState;		// 申请流程状态（字典类型：FLOW_STATE）
	private Date replyTime;		// 审核时间
	private String replyDesc;		// 审核意见
	private Date createTime;		// 创建时间
	private String creatorId;		// 创建人ID
	private String orgId;		// 创建人机构ID
	private String newRepBankcardNoo;//比较用字段 确认银行卡号
	
	public String getNewRepBankcardNoo() {
		return newRepBankcardNoo;
	}

	public void setNewRepBankcardNoo(String newRepBankcardNoo) {
		this.newRepBankcardNoo = newRepBankcardNoo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ApplyChangeBankcard() {
		super();
	}

	public ApplyChangeBankcard(String id){
		super(id);
	}

	@Length(min=0, max=50, message="合同编号长度必须介于 0 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=30, message="新还款银行卡号长度必须介于 0 和 30 之间")
	public String getNewRepBankcardNo() {
		return newRepBankcardNo;
	}

	public void setNewRepBankcardNo(String newRepBankcardNo) {
		this.newRepBankcardNo = newRepBankcardNo;
	}
	
	@Length(min=0, max=20, message="新还款银行卡账户名称长度必须介于 0 和 20 之间")
	public String getNewRepBankcardName() {
		return newRepBankcardName;
	}

	public void setNewRepBankcardName(String newRepBankcardName) {
		this.newRepBankcardName = newRepBankcardName;
	}
	
	@Length(min=0, max=10, message="新还款银行（字典类型：BANKS）长度必须介于 0 和 10 之间")
	public String getNewRepBank() {
		return newRepBank;
	}

	public void setNewRepBank(String newRepBank) {
		this.newRepBank = newRepBank;
	}
	
	@Length(min=0, max=20, message="新还款银行网点名称长度必须介于 0 和 20 之间")
	public String getNewRepBankName() {
		return newRepBankName;
	}

	public void setNewRepBankName(String newRepBankName) {
		this.newRepBankName = newRepBankName;
	}
	
	@Length(min=0, max=15, message="新收款移动电话长度必须介于 0 和 15 之间")
	public String getNewRepBankMobile() {
		return newRepBankMobile;
	}

	public void setNewRepBankMobile(String newRepBankMobile) {
		this.newRepBankMobile = newRepBankMobile;
	}
	
	@Length(min=0, max=32, message="新收款人身份证号长度必须介于 0 和 32 之间")
	public String getNewRepBankIdNum() {
		return newRepBankIdNum;
	}

	public void setNewRepBankIdNum(String newRepBankIdNum) {
		this.newRepBankIdNum = newRepBankIdNum;
	}
	
	@Length(min=0, max=10, message="新还款银行地址//省Province长度必须介于 0 和 10 之间")
	public String getNewRepBankProvince() {
		return newRepBankProvince;
	}

	public void setNewRepBankProvince(String newRepBankProvince) {
		this.newRepBankProvince = newRepBankProvince;
	}
	
	@Length(min=0, max=10, message="新还款银行地址//市city长度必须介于 0 和 10 之间")
	public String getNewRepBankCity() {
		return newRepBankCity;
	}

	public void setNewRepBankCity(String newRepBankCity) {
		this.newRepBankCity = newRepBankCity;
	}
	
	@Length(min=0, max=10, message="新还款银行地址//区district长度必须介于 0 和 10 之间")
	public String getNewRepBankDistinct() {
		return newRepBankDistinct;
	}

	public void setNewRepBankDistinct(String newRepBankDistinct) {
		this.newRepBankDistinct = newRepBankDistinct;
	}
	
	@Length(min=0, max=300, message="新还款银行地址//详细长度必须介于 0 和 300 之间")
	public String getNewRepBankDetail() {
		return newRepBankDetail;
	}

	public void setNewRepBankDetail(String newRepBankDetail) {
		this.newRepBankDetail = newRepBankDetail;
	}
	
	@Length(min=0, max=1000, message="证件图片地址路径长度必须介于 0 和 1000 之间")
	public String getPictureAddress() {
		return pictureAddress;
	}

	public void setPictureAddress(String pictureAddress) {
		this.pictureAddress = pictureAddress;
	}
	
	@Length(min=0, max=4, message="申请流程状态（字典类型：FLOW_STATE）长度必须介于 0 和 4 之间")
	public String getFlowState() {
		return flowState;
	}

	public void setFlowState(String flowState) {
		this.flowState = flowState;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	
	@Length(min=0, max=1000, message="审核意见长度必须介于 0 和 1000 之间")
	public String getReplyDesc() {
		return replyDesc;
	}

	public void setReplyDesc(String replyDesc) {
		this.replyDesc = replyDesc;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=32, message="创建人ID长度必须介于 0 和 32 之间")
	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	
	@Length(min=0, max=32, message="创建人机构ID长度必须介于 0 和 32 之间")
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}