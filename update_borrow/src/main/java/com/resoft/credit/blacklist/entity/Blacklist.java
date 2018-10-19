/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.resoft.credit.blacklist.entity;


import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.resoft.common.utils.Constants;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 黑名单表Entity
 * @author lirongchao
 * @version 2015-12-22
 */
public class Blacklist extends DataEntity<Blacklist> {
	/**
	 * @reqno: H1512210035
	 * @date-designer:2015年12月24日-lirongchao
	 * @date-author:2015年12月24日-lirongchao:1.点击“个人黑名单管理”页面列表中的“详情”链接，弹出窗体，窗体名称“加黑详情”；
											2.“加黑详情”页面布局：上下布局，依次为：工具栏、加黑详情列表；
											3. 工具栏:关闭按钮，点击按钮，关闭当前窗体；
											4.加黑详情列表数据项：操作人、操作时间、操作类型（加黑、刷白）、设置说明；
											   列表排序：操作时间升序；
											   表格分页显示；
											   鼠标放到【操作说明】列时，以tip显示具体内容，以避免内容过多无法查看；
											   当前环节-黑名单表VO
	 */	
	private static final long serialVersionUID = 1L;
	private String custType;		// 客户类型（字典类型：CUST_TYPE）
	private String custName;		// 客户名称
	private String idType;		// 证件类型(个人字典类型：CUSTOMER_P_ID_TYPE,对公字典类型：CUSTOMER_C_ID_TYPE)
	private String idNum;		// 证件号
	private String mobile;		// 移动电话
	private String listStatus;		// 黑名单状态(字典类型：BLACKLIST_STATUS)
	
	private Date createStartTime;
	private Date createEndTime;
	
	private List<BlacklistDetail> blacklistDetailList = Lists.newArrayList();
	
	public Blacklist() {
		super();
	}
	public Blacklist(String id){
		super(id);
	}

	
	public List<BlacklistDetail> getBlacklistDetailList() {
		return blacklistDetailList;
	}
	public void setBlacklistDetailList(List<BlacklistDetail> blacklistDetailList) {
		this.blacklistDetailList = blacklistDetailList;
	}
	
	@Length(min=0, max=10, message="客户类型（字典类型：CUST_TYPE）长度必须介于 0 和 10 之间")
	public String getCustType() {
		return custType;
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
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	@Length(min=0, max=200, message="客户名称长度必须介于 0 和 200 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@Length(min=0, max=10, message="证件类型(个人字典类型：CUSTOMER_P_ID_TYPE,对公字典类型：CUSTOMER_C_ID_TYPE)长度必须介于 0 和 10 之间")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdTypeLabel() {
		if("1".equals(custType)){
			
			return DictUtils.getDictLabel(idType, Constants.CUSTOMER_P_ID_TYPE, null);
		}
		else {
			return DictUtils.getDictLabel(idType, Constants.CUSTOMER_C_ID_TYPE, null);
		}
	}	
	@Length(min=0, max=200, message="证件号长度必须介于 0 和 200 之间")
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	
	@Length(min=0, max=255, message="移动电话长度必须介于 0 和 255 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=2, message="黑名单状态(字典类型：BLACKLIST_STATUS)长度必须介于 0 和 2 之间")
	public String getListStatus() {
		return listStatus;
	}

	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}
	public String getListStatusLabel() {
		return DictUtils.getDictLabel(listStatus, Constants.BLACKLIST_STATUS, null);
	}
	
}