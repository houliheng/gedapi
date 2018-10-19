package com.resoft.credit.gqgetComInfo.dao;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.gqgetComInfo.entity.GqgetComInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 冠E通DAO接口
 * @author yanwanmei
 * @version 2016-08-08
 */
@MyBatisDao
public interface GqgetComInfoDao extends CrudDao<GqgetComInfo> {
	
	public GqgetComInfo getGqgetComInfoByApplyNo(@Param("applyNo")String applyNo);

	public void updateByApplyNo(GqgetComInfo gqgetComInfo);

    void saveRepaySource(GqgetComInfo source);

	void updateRepaySource(GqgetComInfo source);
	void updateZichan(GqgetComInfo zichan);
}