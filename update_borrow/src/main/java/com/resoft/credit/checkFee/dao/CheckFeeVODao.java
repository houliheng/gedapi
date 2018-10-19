package com.resoft.credit.checkFee.dao;

import java.util.List;
import java.util.Map;

import com.resoft.credit.checkFee.entity.CheckFeeVO;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 外访费返还DAO接口
 * 
 * @author yanwanmei
 * @version 2016-02-29
 */
@MyBatisDao
public interface CheckFeeVODao extends CrudDao<CheckFeeVO> {
	public List<MyMap> findAllUserList(Map<String, Object> paramMap);

}