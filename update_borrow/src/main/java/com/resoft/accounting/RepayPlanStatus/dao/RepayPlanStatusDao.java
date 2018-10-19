package com.resoft.accounting.RepayPlanStatus.dao;

import org.apache.ibatis.annotations.Param;

import com.resoft.accounting.RepayPlanStatus.entity.RepayPlanStatus;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface RepayPlanStatusDao extends CrudDao<RepayPlanStatus>{
	public RepayPlanStatus findStatusByConNoandPerNo(@Param("contractNo") String contractNo,@Param("periodNum") Integer perNo);
}
