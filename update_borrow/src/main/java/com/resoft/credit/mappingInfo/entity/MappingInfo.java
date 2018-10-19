package com.resoft.credit.mappingInfo.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 合同号映射表Entity
 * @author 合同号映射表
 * @version 2016-07-29
 */
public class MappingInfo extends DataEntity<MappingInfo> {
	
	private static final long serialVersionUID = 1L;
	private String contractNoFirst;		// 合同编号第一个标志位
	private String contractNoSecond;		// 合同编号第二个标志位
	
	public MappingInfo() {
		super();
	}

	public MappingInfo(String id){
		super(id);
	}

	@Length(min=0, max=10, message="合同编号第一个标志位长度必须介于 0 和 10 之间")
	public String getContractNoFirst() {
		return contractNoFirst;
	}

	public void setContractNoFirst(String contractNoFirst) {
		this.contractNoFirst = contractNoFirst;
	}
	
	@Length(min=0, max=10, message="合同编号第二个标志位长度必须介于 0 和 10 之间")
	public String getContractNoSecond() {
		return contractNoSecond;
	}

	public void setContractNoSecond(String contractNoSecond) {
		this.contractNoSecond = contractNoSecond;
	}
	
}