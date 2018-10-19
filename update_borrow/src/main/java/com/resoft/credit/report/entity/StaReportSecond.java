package com.resoft.credit.report.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 统计报表2Entity
 * @author lixing
 * @version 2016-12-09
 */
public class StaReportSecond extends DataEntity<StaReportSecond> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String custName;		// 客户姓名
	private String dataMonth;		// 月份
	private String orgLevel4;		// 进件分公司ID
	private String orgLevel3;		// 区域ID
	private String orgLevel2;		// 大区ID
	private String appTimeDq;		// 大区批复时间
	private String appStatusDq;		// 大区审批结果(1:通过 0:拒绝)
	private String appTimeZb;		// 总部大区批复时间
	private String appStatusZb;		// 总部审批结果(1:通过 0:拒绝)
	private Office company;		//机构
	private String taskDefKey;	//非表字段
	private String dataMonthStart;//查询用 开始时间
	private String dataMonthEnd;//查询用 结束时间
	
	public String getDataMonthStart() {
		return dataMonthStart;
	}

	public void setDataMonthStart(String dataMonthStart) {
		this.dataMonthStart = dataMonthStart;
	}

	public String getDataMonthEnd() {
		return dataMonthEnd;
	}

	public void setDataMonthEnd(String dataMonthEnd) {
		this.dataMonthEnd = dataMonthEnd;
	}

	public StaReportSecond() {
		super();
	}

	public StaReportSecond(String id){
		super(id);
	}

	@Length(min=0, max=32, message="申请编号长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=0, max=20, message="客户姓名长度必须介于 0 和 20 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@Length(min=0, max=10, message="月份长度必须介于 0 和 10 之间")
	public String getDataMonth() {
		return dataMonth;
	}

	public void setDataMonth(String dataMonth) {
		this.dataMonth = dataMonth;
	}
	
	@Length(min=0, max=32, message="进件分公司ID长度必须介于 0 和 32 之间")
	public String getOrgLevel4() {
		return orgLevel4;
	}

	public void setOrgLevel4(String orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}
	
	@Length(min=0, max=32, message="区域ID长度必须介于 0 和 32 之间")
	public String getOrgLevel3() {
		return orgLevel3;
	}

	public void setOrgLevel3(String orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}
	
	@Length(min=0, max=32, message="大区ID长度必须介于 0 和 32 之间")
	public String getOrgLevel2() {
		return orgLevel2;
	}

	public void setOrgLevel2(String orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}
	
	@Length(min=0, max=30, message="大区批复时间长度必须介于 0 和 30 之间")
	public String getAppTimeDq() {
		return appTimeDq;
	}

	public void setAppTimeDq(String appTimeDq) {
		this.appTimeDq = appTimeDq;
	}
	
	@Length(min=0, max=30, message="大区审批结果(1:通过 0:拒绝)长度必须介于 0 和 30 之间")
	public String getAppStatusDq() {
		return appStatusDq;
	}

	public void setAppStatusDq(String appStatusDq) {
		this.appStatusDq = appStatusDq;
	}
	
	@Length(min=0, max=30, message="总部大区批复时间长度必须介于 0 和 30 之间")
	public String getAppTimeZb() {
		return appTimeZb;
	}

	public void setAppTimeZb(String appTimeZb) {
		this.appTimeZb = appTimeZb;
	}
	
	@Length(min=0, max=30, message="总部审批结果(1:通过 0:拒绝)长度必须介于 0 和 30 之间")
	public String getAppStatusZb() {
		return appStatusZb;
	}

	public void setAppStatusZb(String appStatusZb) {
		this.appStatusZb = appStatusZb;
	}

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	
}