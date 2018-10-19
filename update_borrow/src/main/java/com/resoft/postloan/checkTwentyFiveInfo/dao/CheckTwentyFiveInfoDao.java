package com.resoft.postloan.checkTwentyFiveInfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.postloan.checkTwentyFiveInfo.entity.CheckTwentyFiveInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 25日复核信息DAO接口
 * @author admin
 * @version 2016-05-25
 */
@MyBatisDao
public interface CheckTwentyFiveInfoDao extends CrudDao<CheckTwentyFiveInfo> {
	public List<CheckTwentyFiveInfo> getCheckTwentyFiveInfoByContractNo(String contractNo);
	public void saveList(@Param("checkTwentyFiveInfoList")List<CheckTwentyFiveInfo> checkTwentyFiveInfoList);
	
}