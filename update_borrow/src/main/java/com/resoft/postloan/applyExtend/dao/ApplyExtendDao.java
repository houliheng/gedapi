package com.resoft.postloan.applyExtend.dao;

import java.util.Map;

import com.resoft.postloan.applyExtend.entity.ApplyExtend;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 合同展期申请DAO接口
 * 
 * @author wangguodong
 * @version 2016-05-23
 */
@MyBatisDao
public interface ApplyExtendDao extends CrudDao<ApplyExtend> {
	/**
	 * 根据合同号查询展期申请
	 * 
	 * @param contractNo
	 *            合同号
	 * @return ApplyExtend
	 */
	public ApplyExtend findApplyExtendByContractNo(String contractNo);

	/**
	 * 验证指定合同号是否已进行过展期
	 */
	public ApplyExtend validateApplyExtend(String contractNo);

	/**
	 * 修改申请状态
	 * 
	 * @param applyExtend
	 */
	public void updateApplyStatus(Map<String, Object> map);
	
	/**
	 * 启动流程后修改业务表中流程实例ID
	 * 
	 * @param params
	 */
	public void updateProcInsIdByBusinessId(Map<String, Object> params);

}