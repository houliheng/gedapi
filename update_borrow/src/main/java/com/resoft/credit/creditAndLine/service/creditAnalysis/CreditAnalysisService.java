package com.resoft.credit.creditAndLine.service.creditAnalysis;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.creditAndLine.dao.creditAnalysis.CreditAnalysisDao;
import com.resoft.credit.creditAndLine.entity.creditAnalysis.CreditAnalysis;
import com.resoft.credit.fdfs.util.StringUtils;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 征信分析信息详细Service
 * @author wuxi01
 * @version 2016-02-26
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CreditAnalysisService extends CrudService<CreditAnalysisDao, CreditAnalysis> {

	public CreditAnalysis get(String id) {
		return super.get(id);
	}
	
	public List<CreditAnalysis> findList(CreditAnalysis creditAnalysis) {
		return super.findList(creditAnalysis);
	}
	
	public Page<CreditAnalysis> findPage(Page<CreditAnalysis> page, CreditAnalysis creditAnalysis) {
		return super.findPage(page, creditAnalysis);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(CreditAnalysis creditAnalysis) {
		super.save(creditAnalysis);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void saveData(CreditAnalysis creditAnalysis){
		CreditAnalysis creditAnalysis1 = getCreditAnalysisByApplyNo(creditAnalysis.getApplyNo());
		creditAnalysis.setLineDesc(StringUtils.getTransferString(creditAnalysis.getLineDesc()));	//流水分析
		creditAnalysis.setCreditDesc(StringUtils.getTransferString(creditAnalysis.getCreditDesc()));	//征信分析
		creditAnalysis.setCreditCompany(StringUtils.getTransferString(creditAnalysis.getCreditCompany()));//企业法人征信分析
		if(creditAnalysis1!=null){
			creditAnalysis.preUpdate();
			updateByApplyNo(creditAnalysis);
		}else{
			save(creditAnalysis);
		}
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(CreditAnalysis creditAnalysis) {
		super.delete(creditAnalysis);
	}
	
	public CreditAnalysis getCreditAnalysisByApplyNo(String applyNo){
		return super.dao.getCreditAnalysisByApplyNo(applyNo);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void updateByApplyNo(CreditAnalysis creditAnalysis) {
		 super.dao.updateByApplyNo(creditAnalysis);
	}
	
}