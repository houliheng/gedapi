package com.resoft.postloan.collateralDisposal.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.resoft.postloan.collateralDisposal.entity.CollateralDisposal;
import com.resoft.postloan.collateralDisposal.entity.TaskTempEntity;

/**
 * 借后抵押物处置DAO接口
 * 
 * @author wangguodong
 * @version 2017-01-04
 */
@MyBatisDao
public interface CollateralDisposalDao extends CrudDao<CollateralDisposal> {
	/**
	 * 根据流程id查询合同信息和逾期数据
	 */
	public List<TaskTempEntity> findContractNoAndOverdueDataByProcInsId(String procInsId);

	/**
	 * 总部下发已办列表
	 */
	public List<CollateralDisposal> findToDoneList(CollateralDisposal collateralDisposal);

	/**
	 * 查询指定群组和制定机构下的人
	 */
	public List<Map<String, Object>> findOrgUserList(Map<String, Object> params);

	/**
	 * 根据合同编号和期数修改抵押物处置状态
	 */
	public void updateDisposalStatusByContractNoAndPeriodNum(Map<String, Object> params);
	
	/**
	 * 查询逾期未处理的任务
	 */
	public List<Map<String, Object>> findOverDueList(Map<String, Object> params);
	
}