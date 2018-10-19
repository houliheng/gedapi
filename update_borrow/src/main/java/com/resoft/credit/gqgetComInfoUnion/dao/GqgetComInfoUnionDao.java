package com.resoft.credit.gqgetComInfoUnion.dao;

import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.gqgetComInfoUnion.entity.GqgetComInfoUnion;

/**
 * 联合授信冠e通信息DAO接口
 * @author lixing
 * @version 2016-12-26
 */
@MyBatisDao
public interface GqgetComInfoUnionDao extends CrudDao<GqgetComInfoUnion> {
	/**
	 * 根据applyno删除数据
	 */
	void deleteGqgetComInfoUnionData(String applyNo);
	public GqgetComInfoUnion getGqgetComInfoByParam(Map<String, String> params);
}