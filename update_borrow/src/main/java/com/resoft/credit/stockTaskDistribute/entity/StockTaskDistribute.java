package com.resoft.credit.stockTaskDistribute.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 股权估值服务列表Entity
 * @author jml
 * @version 2017-09-06
 */
public class StockTaskDistribute extends DataEntity<StockTaskDistribute> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// apply_no
	private String status;		// 标志位（0.待分配1.已分配）
	private String operateBy;		// 估值岗、中心的服务专员
	private Date operateDate;		// 服务操作的时间
	private String receive;		// 估值岗、中心专员
	private String type;		// 类型（1.估值岗2.估值中心）
	private String code;
	
	public StockTaskDistribute() {
		super();
	}

	public StockTaskDistribute(String id){
		super(id);
	}

	@Length(min=1, max=32, message="apply_no长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=32, message="估值岗、中心的服务专员长度必须介于 0 和 32 之间")
	public String getOperateBy() {
		return operateBy;
	}

	public void setOperateBy(String operateBy) {
		this.operateBy = operateBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	
	@Length(min=0, max=32, message="估值岗、中心专员长度必须介于 0 和 32 之间")
	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}