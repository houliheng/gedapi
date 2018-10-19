package com.resoft.credit.stockOperateDetail.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 股权操作轨迹Entity
 * @author jml
 * @version 2017-12-15
 */
public class StockOperateDetail extends DataEntity<StockOperateDetail> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String grade;		// 记录此时在工作流中的节点
	private String operate;		// 操作，(0.分单1.转办3.保存)
	private String receiver;		// 转办人
	
	public StockOperateDetail() {
		super();
	}

	public StockOperateDetail(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=0, max=1, message="记录此时在工作流中的节点长度必须介于 0 和 1 之间")
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}
	
	@Length(min=0, max=250, message="转办人长度必须介于 0 和 250 之间")
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
}