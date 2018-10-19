package com.resoft.credit.checkPhone.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.checkPhone.entity.CheckPhone;

/**
 * 电话核查DAO接口
 * @author yanwanmei
 * @version 2016-02-27
 */
@MyBatisDao
public interface CheckPhoneDao extends CrudDao<CheckPhone> {
	public List<CheckPhone> findListByAppNo(String appNo);
	
	public String getCheckPhoneCount(Map<String,String> param);
	
	public List<CheckPhone> getCheckPhoneByApplyNo(@Param("applyNo")String applyNo);
}