package com.resoft.postloan.check25.dao;

import java.util.List;

import com.resoft.postloan.check25.entity.CheckTwentyFive;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 25日复核DAO接口
 * @author yanwanmei
 * @version 2016-05-25
 */
@MyBatisDao
public interface CheckTwentyFiveDao extends CrudDao<CheckTwentyFive> {
	public List<MyMap> getCheckUserInfo(MyMap paramMap);
	public CheckTwentyFive getTwentyFiveByAllocateId(String allocateId);
	
	
}