package com.resoft.credit.checkFee.dao;

import java.util.List;
import java.util.Map;

import com.resoft.credit.checkFee.entity.CheckFee;
import com.resoft.credit.checkFee.entity.CheckFeeVO;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 外访费登记DAO接口
 * 
 * @author yanwanmei
 * @version 2016-02-29
 */
@MyBatisDao
public interface CheckFeeDao extends CrudDao<CheckFee> {
	public CheckFee findByApplyNo(String applyNo);
	/**
	 * 查询外访费返还列表
	 * @param params
	 * @return
	 */
	public List<CheckFeeVO> findCheckFeeVOList(Map<String, String> params);
}