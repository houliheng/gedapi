package com.resoft.accounting.deductApply.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 划扣查询 Entity
 * @author wangguodong
 * @version 2016-03-21
 */
public class DeductApplyVO extends DataEntity<DeductApplyVO> {
	
	private static final long serialVersionUID = 1L;
	private String custName;		// 客户名称
	private String contractNo;		// 合同编号
	private String periodNum;		// 期数
	private String streamNo;        //流水号
	private String applyDeductAmount;		// 申请划扣金额
	private String deductAmountResult;		// 成功划扣金额
	private Office orgLevel4;		// 登记门店
	private Office orgLevel3;		// 区域
	private Office orgLevel2;		// 大区
	private String orgLevel4Id;		// 登记门店Id
	private String orgLevel3Id;		// 区域Id
	private String orgLevel2Id;		// 大区Id
	private String deductResult;		// 划扣状态
	private String decuctTime;  //划扣时间
	private String approPeriodValue;  //合同期数
	private String repayCapitalAmount; // 还款本金    REPAY_CAPITAL_AMOUNT
	private String repayInterestAmount; // 还款利息
	private String repayServiceFee;// 服务费
	private String repayManagementFee;// 账户管理费
	private String repayPenaltyAmount;//违约金
	private String repayFineAmount;// 罚息
	private String deductCustId;//划扣人Id
	private String deductCustName;//划扣人Name
	private Date streamTime;//流水时间
	private String streamTimeStr;//流水时间
	private String repayPeriodStatus;//期状态
	private Date startTime ;//开始时间
	private Date endTime;//结束时间
	private Date streamStartTime ;//流水开始时间
	private Date streamEndTime;//流水结束时间
	private Office company;
	private String repayBreakAmount; //REPAY_BREAK_AMOUNT  提前还款费用
	private String streamType;//流水类型
	private String deductType;//划扣类型
	private String capitalTerraceNo;		// 资金平台账号
	private String sysSeqNo;//系统交易号
	private String deductApplyNo;//扣款申请序列号
	private String description; // 备注
	private String dataDt; //数据日期
	private String flag; //非表字段  单纯用于标记 划扣结果查询(0) 和流水查询(1) 在入账明细中(筛选入账失败的数据)
	private String repayAllAmount;//入账金额
	private String repayAddAmount; // 营业外收入
	private Date repayDate;//计划扣款日 REPAY_DATE
	private String backAmount;
	private Date operateTime;//操作时间
	private String strikeFlag; //冲账标记STRIKE_FLG
	private String remark;
	private String isLock;//锁定标记
	private String transferDeductResult;  // 转账结果 TRANSFER_DEDUCT_RESULT
	private String deductMsg; //DEDUCT_MSG 划扣返回信息
	public String getDeductMsg() {
		return deductMsg;
	}
	public void setDeductMsg(String deductMsg) {
		this.deductMsg = deductMsg;
	}
	public String getTransferDeductResult() {
		return transferDeductResult;
	}
	public void setTransferDeductResult(String transferDeductResult) {
		this.transferDeductResult = transferDeductResult;
	}
	public String getDeductCustName() {
		return deductCustName;
	}
	public void setDeductCustName(String deductCustName) {
		this.deductCustName = deductCustName;
	}
	public String getIsLock() {
		return isLock;
	}
	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}
	public String getStrikeFlag() {
		return strikeFlag;
	}
	public void setStrikeFlag(String strikeFlag) {
		this.strikeFlag = strikeFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getBackAmount() {
		return backAmount;
	}
	public void setBackAmount(String backAmount) {
		this.backAmount = backAmount;
	}
	public String getOrgLevel4Id() {
		return orgLevel4Id;
	}
	public void setOrgLevel4Id(String orgLevel4Id) {
		this.orgLevel4Id = orgLevel4Id;
	}
	public String getOrgLevel3Id() {
		return orgLevel3Id;
	}
	public void setOrgLevel3Id(String orgLevel3Id) {
		this.orgLevel3Id = orgLevel3Id;
	}
	public String getOrgLevel2Id() {
		return orgLevel2Id;
	}
	public void setOrgLevel2Id(String orgLevel2Id) {
		this.orgLevel2Id = orgLevel2Id;
	}
	public Date getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}
	public String getStreamTimeStr() {
		return streamTimeStr;
	}
	public void setStreamTimeStr(String streamTimeStr) {
		this.streamTimeStr = streamTimeStr;
	}
	public String getRepayAddAmount() {
		return repayAddAmount;
	}
	public void setRepayAddAmount(String repayAddAmount) {
		this.repayAddAmount = repayAddAmount;
	}
	public String getRepayAllAmount() {
		return repayAllAmount;
	}
	public void setRepayAllAmount(String repayAllAmount) {
		this.repayAllAmount = repayAllAmount;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCapitalTerraceNo() {
		return capitalTerraceNo;
	}
	public void setCapitalTerraceNo(String capitalTerraceNo) {
		this.capitalTerraceNo = capitalTerraceNo;
	}
	public String getSysSeqNo() {
		return sysSeqNo;
	}
	public void setSysSeqNo(String sysSeqNo) {
		this.sysSeqNo = sysSeqNo;
	}
	public String getDeductApplyNo() {
		return deductApplyNo;
	}
	public void setDeductApplyNo(String deductApplyNo) {
		this.deductApplyNo = deductApplyNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDataDt() {
		return dataDt;
	}
	public void setDataDt(String dataDt) {
		this.dataDt = dataDt;
	}
	public String getStreamType() {
		return streamType;
	}
	public void setStreamType(String streamType) {
		this.streamType = streamType;
	}
	public String getDeductType() {
		return deductType;
	}
	public void setDeductType(String deductType) {
		this.deductType = deductType;
	}
	
	public Date getStreamStartTime() {
		return streamStartTime;
	}
	public void setStreamStartTime(Date streamStartTime) {
		this.streamStartTime = streamStartTime;
	}
	public Date getStreamEndTime() {
		return streamEndTime;
	}
	public void setStreamEndTime(Date streamEndTime) {
		this.streamEndTime = streamEndTime;
	}
	public String getRepayBreakAmount() {
		return repayBreakAmount;
	}
	public void setRepayBreakAmount(String repayBreakAmount) {
		this.repayBreakAmount = repayBreakAmount;
	}
	public Office getCompany() {
		return company;
	}
	public void setCompany(Office company) {
		this.company = company;
	}
	public String getStreamNo() {
		return streamNo;
	}
	public void setStreamNo(String streamNo) {
		this.streamNo = streamNo;
	}
	public String getRepayPeriodStatus() {
		return repayPeriodStatus;
	}
	public void setRepayPeriodStatus(String repayPeriodStatus) {
		this.repayPeriodStatus = repayPeriodStatus;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getPeriodNum() {
		return periodNum;
	}
	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}
	public String getApplyDeductAmount() {
		return applyDeductAmount;
	}
	public void setApplyDeductAmount(String applyDeductAmount) {
		this.applyDeductAmount = applyDeductAmount;
	}
	public String getDeductAmountResult() {
		return deductAmountResult;
	}
	public void setDeductAmountResult(String deductAmountResult) {
		this.deductAmountResult = deductAmountResult;
	}
	public Office getOrgLevel4() {
		return orgLevel4;
	}
	public void setOrgLevel4(Office orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}
	public Office getOrgLevel3() {
		return orgLevel3;
	}
	public void setOrgLevel3(Office orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}
	public Office getOrgLevel2() {
		return orgLevel2;
	}
	public void setOrgLevel2(Office orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}
	public String getDeductResult() {
		return deductResult;
	}
	public void setDeductResult(String deductResult) {
		this.deductResult = deductResult;
	}
	public String getDecuctTime() {
		return decuctTime;
	}
	public void setDecuctTime(String decuctTime) {
		this.decuctTime = decuctTime;
	}
	public String getApproPeriodValue() {
		return approPeriodValue;
	}
	public void setApproPeriodValue(String approPeriodValue) {
		this.approPeriodValue = approPeriodValue;
	}
	public String getRepayCapitalAmount() {
		return repayCapitalAmount;
	}
	public void setRepayCapitalAmount(String repayCapitalAmount) {
		this.repayCapitalAmount = repayCapitalAmount;
	}
	public String getRepayInterestAmount() {
		return repayInterestAmount;
	}
	public void setRepayInterestAmount(String repayInterestAmount) {
		this.repayInterestAmount = repayInterestAmount;
	}
	public String getRepayServiceFee() {
		return repayServiceFee;
	}
	public void setRepayServiceFee(String repayServiceFee) {
		this.repayServiceFee = repayServiceFee;
	}
	public String getRepayManagementFee() {
		return repayManagementFee;
	}
	public void setRepayManagementFee(String repayManagementFee) {
		this.repayManagementFee = repayManagementFee;
	}
	public String getRepayPenaltyAmount() {
		return repayPenaltyAmount;
	}
	public void setRepayPenaltyAmount(String repayPenaltyAmount) {
		this.repayPenaltyAmount = repayPenaltyAmount;
	}
	public String getRepayFineAmount() {
		return repayFineAmount;
	}
	public void setRepayFineAmount(String repayFineAmount) {
		this.repayFineAmount = repayFineAmount;
	}
	public String getDeductCustId() {
		return deductCustId;
	}
	public void setDeductCustId(String deductCustId) {
		this.deductCustId = deductCustId;
	}
	public Date getStreamTime() {
		return streamTime;
	}
	public void setStreamTime(Date streamTime) {
		this.streamTime = streamTime;
	}
	
	
}