package com.resoft.credit.creditAndLine.dao.creditAnalysisMostExtends;

import com.resoft.credit.creditAndLine.entity.creditAnalysisMostExtends.CreditAnalysisMostExtends;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface CreditAnalysisMostExtendsDao extends CrudDao<CreditAnalysisMostExtends> {

	CreditAnalysisMostExtends getCreditAnalysisMostExtendsByApplyNo(String applyNo);

	void updateByApplyNo(CreditAnalysisMostExtends creditAnalysisMostExtends);

}
