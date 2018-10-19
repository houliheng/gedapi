package com.resoft.credit.creditAndLine.service.creditAnalysisMostExtend;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.creditAndLine.dao.creditAnalysisMostExtend.CreditAnalysisMostExtendDao;
import com.resoft.credit.creditAndLine.entity.creditAnalysisMostExtend.CreditAnalysisMostExtend;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CreditAnalysisMostExtendService extends CrudService<CreditAnalysisMostExtendDao, CreditAnalysisMostExtend> {

	
	public CreditAnalysisMostExtend getCreditAnalysisMostExtendByApplyNo(String applyNo) {
		return super.dao.getCreditAnalysisMostExtendByApplyNo(applyNo);
	}

	public void updateByApplyNo(CreditAnalysisMostExtend creditAnalysisMostExtend) {
		super.dao.updateByApplyNo(creditAnalysisMostExtend);
	}

	public void delete(CreditAnalysisMostExtend creditAnalysisMostExtend) {
		super.delete(creditAnalysisMostExtend);
		
	}
}
