package com.resoft.credit.applyRegister.entity;


import java.util.Date;
import java.util.List;

import com.resoft.credit.loanApply.entity.CreApplyInfo;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

public class Customer extends DataEntity<Customer> {
		private static final long serialVersionUID = 1L;
		private String cusName;	//客户名称
		private String applyNo;	//申请编号
		private String idType;		//证件类型，1-身份证;2-户口薄;3-护照;4-军官证;5-士兵证;6-港澳居民来往内地通行证;7-台湾同胞来往内地通行证;8-临时身份证;9-外国人居留证;10-警官证;11-其他个人证件;
		private String idNum;		//证件号
		private String idNumConfirm;		//证件号确认
		private String mobileNum;		//移动电话
		private String channelOtherDesc;		//渠道说明
		private String channelSourceType;		//客户来源，0-主动拜访;1-客户推荐;2-资料宣传;3-市场活动;4-车商推荐;5-同行介绍
		private Date registerDate;		//登记日期
		private String applyStatus;		//当前状态:是否提交
		private String post;	//提交状态判断
		/**
		 * @reqno:H1512160006
		 * @date-designer:2015年12月16日-songmin
		 * @date-author:2015年12月16日-songmin:CRE_信贷审批_进件管理_企业客户登记_新增、修改
		 * 		增加客户类型字段  1：个人客户 2：公司客户 3：集团客户
		 */
		private String custType;	//客户类型 1：个人客户 2：公司客户 3：集团客户
		private String contactsName;//LINKMAN_NAME;//联系人（专用于企业）名称
		private String contactsCardIdNo;//LINKMAN_INDENTITY_ID;//联系身份证号（专用于企业）名称
		private String contactsMobile;//LINKMAN_MOBILE;//联系手机号（专用于企业）名称
		/**
		 * @reqno:H1512160005
		 * @date-designer:2015年12月16日-songmin
		 * @date-author:2015年12月16日-songmin:字典表对应的名称
		 */
		private String idTypeLabel;//证件类型字典Label
		private String statusLabel;//当前状态字典Label
		private String channelSourceTypeLabel;//客户来源Label
		
		/**
		 * @reqno: H1510210068
		 * @date-designer:20151023-lirongchao
		 * @date-author:20151023-lirongchao:  需求-1.在客户登记列表的新增或修改页面，
		 * 										当用户点击“提交”按钮，后台在提交前按规则生成申请编号、并保存；
												2.生成申请编号的规则：YYYYMMDD+00001+4位随机数；其中“00001”为序号，
												每个公司每天从00001开始递增
					当前操作-增加申请编号生成所需的两个字段
						serialNumber,		序号（5位，每天从00001开始）
						companyId			公司ID
		 */
		private String serialNumber; //序号（5位，每天从00001开始）
		/**
		 * @reqno:AH1509140009
		 * @date-designer:2015年9月28日-songmin
		 * @date-author:2015年9月28日-songmin:内匹配信息追加的2个字段：机构名称、贷款申请额度
		 * 									机构ID-客户登记时未保存机构ID，顺带在这里补上
		 */
		private String registerOrgId;//机构ID-客户登记时未保存机构ID，顺带在这里补上
		private String orgName;//机构名称
		private String applyAmount;//申请金额
		/**
		 * @reqno:H1509230047
		 * @date-designer:20151010-huangxuecheng
		 * @date-author:20151010-huangxuecheng:增加字段满足代办任务需求,增加的查询字段说明createTime创建时间查询
		 * 									   createStartTime创建开始时间查询，createEndTime创建结束时间查询
		 */
		private Date createTime;
		private Date createStartTime;
		private Date createEndTime;
		/**
		 * @reqno:H1509230065
		 * @date-designer:20151010-huangxuecheng
		 * @date-author:20151010-huangxuecheng:增加字段满足已办任务需求,增加的查询字段说明doneTime创建时间查询
		 * 									   doneStartTime创建开始时间查询，doneEndTime创建结束时间查询
		 */
		private Date doneTime;
		private Date doneStartTime;
		private Date doneEndTime;
		
		/**
		 * @reqno:H1510230032
		 * @date-designer:20151026-huangxuecheng
		 * @date-author:20151026-huangxuecheng:增加字段满足CRE_信贷审批_合同复核_批量提交待办任务列表_查询
		 */
		private Integer appAmount;
		private String appProductName;
		private String appPeriodValue;
		private Integer appYearRate;
		private String recAccount;
		private String recAccountName;
		private String recBank;
		private String recBankNumber;
		private String recBankValue;
		private Integer contractAmount;
		private String contractNo;
		private Date occurDate;
		/**
		 * @reqno:H1510230033
		 * @date-designer:20151026-huangxuecheng
		 * @date-author:20151026-huangxuecheng:增加字段满足CRE_信贷审批_合同复核_批量提交功能的文本域接收
		 */
		private String textAreaComment;
		/**
		 * @reqno:H1510230034
		 * @date-designer:20151026-huangxuecheng
		 * @date-author:20151026-huangxuecheng:增加字段满足CRE_信贷审批_合同复核_导出，导出文件的流程定义id接收
		 */
		private String procInsId;
		private List<String> procInsIdList;
		
		/**
		 * @reqno:H1512160007
		 * @date-designer:2015年12月17日-songmin
		 * @date-author:2015年12月17日-songmin:CRE_信贷审批_进件管理_企业客户登记_同步业务数据
		 * 		在保存企业客户记录时同步贷款申请信息表数据：申请产品类型、类型名、产品ID、产品名
		 */
		private CreApplyInfo applyInfo;//贷款申请信息
		
		//产品类型
		private String applyProductTypeCode;
		
		//登机门店
		private Office company;
		
		private String loanModel;//借款模式
		
		/**
		 * @return the applyInfo
		 */
		public CreApplyInfo getApplyInfo() {
			return applyInfo;
		}
		/**
		 * @param applyInfo the applyInfo to set
		 */
		public void setApplyInfo(CreApplyInfo applyInfo) {
			this.applyInfo = applyInfo;
		}
		/**
		 * @return the linkManName
		 */
		/**
		 * @return the custType
		 */
		public String getCustType() {
			return custType;
		}
		/**
		 * @param custType the custType to set
		 */
		public void setCustType(String custType) {
			this.custType = custType;
		}
		/**
		 * @return the channelSourceTypeLabel
		 */
		public String getChannelSourceTypeLabel() {
			return channelSourceTypeLabel;
		}
		/**
		 * @param channelSourceTypeLabel the channelSourceTypeLabel to set
		 */
		public void setChannelSourceTypeLabel(String channelSourceTypeLabel) {
			this.channelSourceTypeLabel = channelSourceTypeLabel;
		}
		public String getIdTypeLabel() {
			return idTypeLabel;
		}
		public void setIdTypeLabel(String idTypeLabel) {
			this.idTypeLabel = idTypeLabel;
		}
		/**
		 * @return the statusLabel
		 */
		public String getStatusLabel() {
			return statusLabel;
		}
		
		public String getApplyNo() {
			return applyNo;
		}
		public void setApplyNo(String applyNo) {
			this.applyNo = applyNo;
		}
		public String getMobileNum() {
			return mobileNum;
		}
		public void setMobileNum(String mobileNum) {
			this.mobileNum = mobileNum;
		}
		public Date getRegisterDate() {
			return registerDate;
		}
		public void setRegisterDate(Date registerDate) {
			this.registerDate = registerDate;
		}
		public String getApplyStatus() {
			return applyStatus;
		}
		public void setApplyStatus(String applyStatus) {
			this.applyStatus = applyStatus;
		}
		public String getContactsName() {
			return contactsName;
		}
		public void setContactsName(String contactsName) {
			this.contactsName = contactsName;
		}
		public String getContactsCardIdNo() {
			return contactsCardIdNo;
		}
		public void setContactsCardIdNo(String contactsCardIdNo) {
			this.contactsCardIdNo = contactsCardIdNo;
		}
		public String getContactsMobile() {
			return contactsMobile;
		}
		public void setContactsMobile(String contactsMobile) {
			this.contactsMobile = contactsMobile;
		}
		public String getRegisterOrgId() {
			return registerOrgId;
		}
		public void setRegisterOrgId(String registerOrgId) {
			this.registerOrgId = registerOrgId;
		}
		/**
		 * @param statusLabel the statusLabel to set
		 */
		public void setStatusLabel(String statusLabel) {
			this.statusLabel = statusLabel;
		}
		public String getPost() {
			return post;
		}
		public void setPost(String post) {
			this.post = post;
		}
		
		public List<String> getProcInsIdList() {
			return procInsIdList;
		}
		public void setProcInsIdList(List<String> procInsIdList) {
			this.procInsIdList = procInsIdList;
		}
		public String getProcInsId() {
			return procInsId;
		}
		public void setProcInsId(String procInsId) {
			this.procInsId = procInsId;
		}
		public Integer getAppAmount() {
			return appAmount;
		}
		public void setAppAmount(Integer appAmount) {
			this.appAmount = appAmount;
		}
		public String getAppProductName() {
			return appProductName;
		}
		public void setAppProductName(String appProductName) {
			this.appProductName = appProductName;
		}
		public String getAppPeriodValue() {
			return appPeriodValue;
		}
		public void setAppPeriodValue(String appPeriodValue) {
			this.appPeriodValue = appPeriodValue;
		}
		public Integer getAppYearRate() {
			return appYearRate;
		}
		public void setAppYearRate(Integer appYearRate) {
			this.appYearRate = appYearRate;
		}
		public String getRecAccount() {
			return recAccount;
		}
		public void setRecAccount(String recAccount) {
			this.recAccount = recAccount;
		}
		public String getRecAccountName() {
			return recAccountName;
		}
		public void setRecAccountName(String recAccountName) {
			this.recAccountName = recAccountName;
		}
		public String getRecBank() {
			return recBank;
		}
		public void setRecBank(String recBank) {
			this.recBank = recBank;
		}
		public String getRecBankNumber() {
			return recBankNumber;
		}
		public void setRecBankNumber(String recBankNumber) {
			this.recBankNumber = recBankNumber;
		}
		public String getRecBankValue() {
			return recBankValue;
		}
		public void setRecBankValue(String recBankValue) {
			this.recBankValue = recBankValue;
		}
		public Integer getContractAmount() {
			return contractAmount;
		}
		public void setContractAmount(Integer contractAmount) {
			this.contractAmount = contractAmount;
		}
		public String getContractNo() {
			return contractNo;
		}
		public void setContractNo(String contractNo) {
			this.contractNo = contractNo;
		}
		public Date getOccurDate() {
			return occurDate;
		}
		public void setOccurDate(Date occurDate) {
			this.occurDate = occurDate;
		}
		public String getTextAreaComment() {
			return textAreaComment;
		}
		public void setTextAreaComment(String textAreaComment) {
			this.textAreaComment = textAreaComment;
		}
		public String getSerialNumber() {
			return serialNumber;
		}
		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}
		public Date getDoneTime() {
			return doneTime;
		}
		public void setDoneTime(Date doneTime) {
			this.doneTime = doneTime;
		}
		public Date getDoneStartTime() {
			return doneStartTime;
		}
		public void setDoneStartTime(Date doneStartTime) {
			this.doneStartTime = doneStartTime;
		}
		public Date getDoneEndTime() {
			return doneEndTime;
		}
		public void setDoneEndTime(Date doneEndTime) {
			this.doneEndTime = doneEndTime;
		}
		public Date getCreateStartTime() {
			return createStartTime;
		}
		public void setCreateStartTime(Date createStartTime) {
			this.createStartTime = createStartTime;
		}
		public Date getCreateEndTime() {
			return createEndTime;
		}
		public void setCreateEndTime(Date createEndTime) {
			this.createEndTime = createEndTime;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		/**
		 * @return the orgName
		 */
		public String getOrgName() {
			return orgName;
		}
		/**
		 * @param orgName the orgName to set
		 */
		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}
		/**
		 * @return the applyAmount
		 */
		public String getApplyAmount() {
			return applyAmount;
		}
		/**
		 * @param applyAmount the applyAmount to set
		 */
		public void setApplyAmount(String applyAmount) {
			this.applyAmount = applyAmount;
		}
		public String getCusName() {
			return cusName;
		}
		public void setCusName(String cusName) {
			this.cusName = cusName;
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
		
		public String getIdNumConfirm() {
			return idNumConfirm;
		}
		public void setIdNumConfirm(String idNumConfirm) {
			this.idNumConfirm = idNumConfirm;
		}
		
		public String getChannelOtherDesc() {
			return channelOtherDesc;
		}
		public void setChannelOtherDesc(String channelOtherDesc) {
			this.channelOtherDesc = channelOtherDesc;
		}
		public String getChannelSourceType() {
			return channelSourceType;
		}
		public void setChannelSourceType(String channelSourceType) {
			this.channelSourceType = channelSourceType;
		}
		public String getApplyProductTypeCode() {
			return applyProductTypeCode;
		}
		public void setApplyProductTypeCode(String applyProductTypeCode) {
			this.applyProductTypeCode = applyProductTypeCode;
		}
		public Office getCompany() {
			return company;
		}
		public void setCompany(Office company) {
			this.company = company;
		}
		public String getLoanModel() {
			return loanModel;
		}
		public void setLoanModel(String loanModel) {
			this.loanModel = loanModel;
		}
		
		
}