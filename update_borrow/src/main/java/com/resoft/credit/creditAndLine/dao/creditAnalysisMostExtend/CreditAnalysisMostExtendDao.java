package com.resoft.credit.creditAndLine.dao.creditAnalysisMostExtend;

import com.resoft.credit.creditAndLine.entity.creditAnalysisMostExtend.CreditAnalysisMostExtend;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface CreditAnalysisMostExtendDao extends CrudDao<CreditAnalysisMostExtend> {

	CreditAnalysisMostExtend getCreditAnalysisMostExtendByApplyNo(String applyNo);

	void updateByApplyNo(CreditAnalysisMostExtend creditAnalysisMostExtend);

}
