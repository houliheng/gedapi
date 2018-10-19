package com.resoft.credit.creditAndLine.service.creditAnalysisMostExtends;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.creditAndLine.dao.creditAnalysisMostExtends.CreditAnalysisMostExtendsDao;
import com.resoft.credit.creditAndLine.entity.creditAnalysisMostExtends.CreditAnalysisMostExtends;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CreditAnalysisMostExtendsService extends CrudService<CreditAnalysisMostExtendsDao, CreditAnalysisMostExtends> {

	public CreditAnalysisMostExtends getCreditAnalysisMostExtendsByApplyNo(String applyNo) {
		return super.dao.getCreditAnalysisMostExtendsByApplyNo(applyNo);
	}

	public void updateByApplyNo(CreditAnalysisMostExtends creditAnalysisMostExtends) {
		super.dao.updateByApplyNo(creditAnalysisMostExtends);
	}

	public void delete(CreditAnalysisMostExtends creditAnalysisMostExtends) {
		super.delete(creditAnalysisMostExtends);
		
	}
}