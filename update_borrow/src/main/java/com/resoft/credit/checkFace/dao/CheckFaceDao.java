package com.resoft.credit.checkFace.dao;

import com.resoft.credit.checkFace.entity.CheckFace;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 面审信息DAO接口
 * @author yanwanmei
 * @version 2016-02-25
 */
@MyBatisDao
public interface CheckFaceDao extends CrudDao<CheckFace> {
	public CheckFace getCheckFaceByApplyNo(String applyNo);
}