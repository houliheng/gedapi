/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.resoft.credit.blacklist.entity;


import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 黑名单详情表Entity
 * @author lirongchao
 * @version 2016-01-07
 */
public class BlacklistDetail extends DataEntity<BlacklistDetail> {
	/**
	 * @reqno: H1512210033
	 * @date-designer:2015年12月24日-lirongchao
	 * @date-author:2015年12月24日-lirongchao:1.查询条件-【客户名称（模糊查询）、状态】、【证件类型、证件号码（模糊查询）】、【创建日期（开始结束时间）】；
											查询表单按钮-查询、重置；
											2.列表数据项-单选框按钮、客户名称、状态（黑名单、白名单）、证件类型、证件号、创建人、创建日期、操作（详情）
											3.列表排序-创建日期降序
											4.表头按钮-加黑、刷白
											当前环节-黑名单详情Entity
	 */		
	private static final long serialVersionUID = 1L;
	private String blacklistId;		// 黑名单ID
	private String listStatus;		// 黑名单状态(字典类型：BLACKLIST_STATUS)
	private String remarks;		// 加黑/刷白说明
	
	public BlacklistDetail() {
		super();
	}

	public BlacklistDetail(String id){
		super(id);
	}

	@Length(min=0, max=32, message="黑名单ID长度必须介于 0 和 32 之间")
	public String getBlacklistId() {
		return blacklistId;
	}

	public void setBlacklistId(String blacklistId) {
		this.blacklistId = blacklistId;
	}
	
	@Length(min=0, max=2, message="黑名单状态(字典类型：BLACKLIST_STATUS)长度必须介于 0 和 2 之间")
	public String getListStatus() {
		return listStatus;
	}

	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}
	
	@Length(min=0, max=255, message="加黑/刷白说明长度必须介于 0 和 255 之间")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}