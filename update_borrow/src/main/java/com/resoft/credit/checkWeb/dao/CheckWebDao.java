package com.resoft.credit.checkWeb.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.checkWeb.entity.CheckWeb;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 网查DAO接口
 * @author yanwanmei
 * @version 2016-02-27
 */
@MyBatisDao
public interface CheckWebDao extends CrudDao<CheckWeb> {
	public String getCheckWebCount(Map<String,String> param);
	
    public List<CheckWeb> getCheckWebByApplyNo(@Param("applyNo")String applyNo);
}