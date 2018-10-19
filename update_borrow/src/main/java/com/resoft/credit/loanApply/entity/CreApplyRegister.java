package com.resoft.credit.loanApply.entity;

import java.util.Date;

import com.resoft.common.utils.Constants;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * @reqno:H1509230044
 * @date-designer:2015年10月13日-songmin
 * @date-author:2015年10月13日-songmin:CRE_信贷审批_申请录入_产品类型选择
 * 		客户申请信息表实体类     Table：CRE_APPLY_REGISTER
 */
public class CreApplyRegister extends DataEntity<CreApplyRegister>{
	private static final long serialVersionUID = -2101199448612021517L;
	
	private String id;			// varchar(32) default null comment '申请id',
	private String applyNo;	// varchar(50) default null comment '申请编号',
	private String custName;	// varchar(200) default null comment '客户名称',
	private String custType;	// varchar(10) default null comment '客户类型（字典类型：cust_type）',
	private String companyId;	// varchar(32) default null comment '公司id（保留字段）',
	private String idType;		// varchar(10) default null comment '证件类型（字典类型：customer_p_id_type）',
	private String idNum;		// varchar(200) default null comment '证件号',
	private String mobile;		// varchar(1020) default null comment '移动电话',
	private String channelType;	// varchar(10) default null comment '申请渠道（字典类型：channel_type）',
	private String channelSourceType;// varchar(10) default null comment '客户来源（字典类型：channel_source_type）',
	private String otherChannelSource;// varchar(1020) default null comment '其他渠道',
	private String channelDesc;	// varchar(300) default null comment '渠道来源说明 当来源选择“其他”这一项时，需要真写来源说明',
	private Date bizDate;		// timestamp null default null comment '登记日期',
	private String orgId;		// varchar(32) default null comment '登记机构',
	private String status;		// varchar(10) default null comment '状态，是否提交（字典类型：yes_no）',
	private String creator;		// varchar(200) default null comment '创建人',
	private String creatorId;	// varchar(32) default null comment '创建人id',
	private String procInsId;	// varchar(64) default null comment '流程实例id'
	private String orgName;		//机构名称
	/**
	 * @reqno:H1512210025
	 * @date-designer:2015年12月31日-songmin
	 * @date-author:2015年12月31日-songmin:CRE_信贷审批_个人_申请录入_页面重构
	 * 增加字典属性Lable属性，添加企业客户专属查询字段
	 */
	private String idTypeLable;//证件类型Lable
	private String channelSourceTypeLable;//客户来源Lable
	private String channelTypeLable;//申请渠道Lable
	private String linkManName;//LINKMAN_NAME;//联系人（专用于企业）名称
	private String linkManIndentityId;//LINKMAN_INDENTITY_ID;//联系身份证号（专用于企业）名称
	private String linkManMobile;//LINKMAN_MOBILE;//联系手机号（专用于企业）名称
	
	public String getLinkManName() {
		return linkManName;
	}
	
	public void setLinkManName(String linkManName) {
		this.linkManName = linkManName;
	}
	
	public String getLinkManIndentityId() {
		return linkManIndentityId;
	}
	
	public void setLinkManIndentityId(String linkManIndentityId) {
		this.linkManIndentityId = linkManIndentityId;
	}
	
	public String getLinkManMobile() {
		return linkManMobile;
	}
	
	public void setLinkManMobile(String linkManMobile) {
		this.linkManMobile = linkManMobile;
	}
	
	public String getChannelSourceTypeLable() {
		return DictUtils.getDictLabel(channelSourceType, Constants.CHANNEL_SOURCE_TYPE, null);
	}
	
	public void setChannelSourceTypeLable(String channelSourceTypeLable) {
		this.channelSourceTypeLable = channelSourceTypeLable;
	}
	
	public String getChannelTypeLable() {
		return DictUtils.getDictLabel(channelType, Constants.CHANNEL_TYPE, null);
	}
	
	public void setChannelTypeLable(String channelTypeLable) {
		this.channelTypeLable = channelTypeLable;
	}
	
	public String getIdTypeLable() {
		if("1".equals(custType)){//个人客户
			return DictUtils.getDictLabel(idType, Constants.CUSTOMER_P_ID_TYPE, null);
		}else if("2".equals(custType)){//2企业客户
			return DictUtils.getDictLabel(idType, Constants.CUSTOMER_C_ID_TYPE, null);
		}else{
			return idTypeLable;
		}
	}
	
	public void setIdTypeLable(String idTypeLable) {
		this.idTypeLable = idTypeLable;
	}
	
	public String getOrgName() {
		return orgName;
	}
	
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	
	public String getCustName() {
		return custName;
	}
	
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	public String getCustType() {
		return custType;
	}
	
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getChannelType() {
		return channelType;
	}
	
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	
	public String getChannelSourceType() {
		return channelSourceType;
	}
	
	public void setChannelSourceType(String channelSourceType) {
		this.channelSourceType = channelSourceType;
	}
	
	public String getOtherChannelSource() {
		return otherChannelSource;
	}
	
	public void setOtherChannelSource(String otherChannelSource) {
		this.otherChannelSource = otherChannelSource;
	}
	
	public String getChannelDesc() {
		return channelDesc;
	}
	
	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}
	
	public Date getBizDate() {
		return bizDate;
	}
	
	public void setBizDate(Date bizDate) {
		this.bizDate = bizDate;
	}
	
	public String getOrgId() {
		return orgId;
	}
	
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public String getCreatorId() {
		return creatorId;
	}
	
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	
	public String getProcInsId() {
		return procInsId;
	}
	
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
}
