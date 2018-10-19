package com.resoft.credit.creditAndLine.service.creditAnalysisExtend;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.creditAndLine.dao.creditAnalysisExtend.CreditAnalysisExtendDao;
import com.resoft.credit.creditAndLine.entity.creditAnalysis.CreditAnalysis;
import com.resoft.credit.creditAndLine.entity.creditAnalysisExtend.CreditAnalysisExtend;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CreditAnalysisExtendService extends CrudService<CreditAnalysisExtendDao, CreditAnalysisExtend> {


	
	public CreditAnalysisExtend getCreditAnalysisExtendByApplyNo(String applyNo) {
		return super.dao.getCreditAnalysisExtendByApplyNo(applyNo);
	}

	public void updateByApplyNo(CreditAnalysisExtend creditAnalysisExtend) {
		super.dao.updateByApplyNo(creditAnalysisExtend);
	}

	public void delete(CreditAnalysisExtend creditAnalysisExtend) {
		super.delete(creditAnalysisExtend);
		
	}


}
