package com.resoft.credit.creditViewBook.dao.creditOtherInfo;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.creditViewBook.entity.creditOtherInfo.CreditOtherInfo;

/**
 * 征信意见书其他信息DAO接口
 * @author wuxi01
 * @version 2016-02-29
 */
@MyBatisDao
public interface CreditOtherInfoDao extends CrudDao<CreditOtherInfo> {
	
	public CreditOtherInfo getCreditOtherInfoByApplyNo(@Param("applyNo")String applyNo);
	
	public void updateByApplyNo(CreditOtherInfo creditOtherInfo);
}