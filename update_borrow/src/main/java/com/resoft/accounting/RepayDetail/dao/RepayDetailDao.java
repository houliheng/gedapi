package com.resoft.accounting.RepayDetail.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.accounting.RepayDetail.entity.RepayDetail;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface RepayDetailDao extends CrudDao<RepayDetail>{
	public List<RepayDetail> findPeriondByDeducyApplyNo(@Param("deductApplyNo") String deductApplyNo);
}
