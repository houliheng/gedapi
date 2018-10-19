package com.resoft.credit.creditAndLine.dao.creditAnalysisExtend;

import com.resoft.credit.creditAndLine.entity.creditAnalysisExtend.CreditAnalysisExtend;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface CreditAnalysisExtendDao extends CrudDao<CreditAnalysisExtend> {

	CreditAnalysisExtend getCreditAnalysisExtendByApplyNo(String applyNo);

	void updateByApplyNo(CreditAnalysisExtend creditAnalysisExtend);

}
