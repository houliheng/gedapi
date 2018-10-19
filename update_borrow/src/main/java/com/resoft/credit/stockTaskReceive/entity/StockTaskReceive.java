package com.resoft.credit.stockTaskReceive.entity;


import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 股权任务接收表Entity
 * @author jml
 * @version 2017-12-11
 */
public class StockTaskReceive extends DataEntity<StockTaskReceive> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String stockInfoId;		// 业务表的id
	private String code;		// 机构编码
	private String receiver;		// 接单人
	private String status;		// 估值状态
	private Date endTime;		// 专员完成的时间
	private String grade;		// 类型
	//非表字段
	private String procInsId;
	private String procDefId;
	private String executionId;
	private String taskDefKey;
	private String ruId;		//当前运行点id 
	private String idNum;		//证件号
	private String idType; 		//证件类型
	private String custName; 	//客户名称 
	private BigDecimal applyAmount;	//申请金额
	private String orgName;		//登记门店
	private String isDone;		//是否是已办任务 "0"不是，只查询未完成的   "1"是，只查询完成的
	private Integer isStockPost; //是否是估值岗接收， 1是 0否
	private String gradeDesc;//机构描述
	private String processStatus;//流程状态
	

	public StockTaskReceive() {
		super();
	}

	public StockTaskReceive(String id){
		super(id);
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	
	public String getStockInfoId() {
		return stockInfoId;
	}

	public void setStockInfoId(String stockInfoId) {
		this.stockInfoId = stockInfoId;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	


	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getIsDone() {
		return isDone;
	}

	public void setIsDone(String isDone) {
		this.isDone = isDone;
	}
	
	public String getRuId() {
		return ruId;
	}

	public void setRuId(String ruId) {
		this.ruId = ruId;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Integer getIsStockPost() {
		return isStockPost;
	}

	public void setIsStockPost(Integer isStockPost) {
		this.isStockPost = isStockPost;
	}

	public String getGradeDesc() {
		return gradeDesc;
	}

	public void setGradeDesc(String gradeDesc) {
		this.gradeDesc = gradeDesc;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	
	
}