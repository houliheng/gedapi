package com.resoft.credit.creditAndLine.service.creditCompany;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.creditAndLine.entity.creditCompany.CreditCompanyLoan;
import com.resoft.credit.creditAndLine.dao.creditCompany.CreditCompanyLoanDao;

/**
 * 征信企业贷款明细Service
 * @author wuxi01
 * @version 2016-02-27
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CreditCompanyLoanService extends CrudService<CreditCompanyLoanDao, CreditCompanyLoan> {

	
	
	public CreditCompanyLoan get(String id) {
		CreditCompanyLoan creditCompanyLoan = super.get(id);
		return creditCompanyLoan;
	}
	
	public List<CreditCompanyLoan> findList(CreditCompanyLoan creditCompanyLoan) {
		return super.findList(creditCompanyLoan);
	}
	
	public Page<CreditCompanyLoan> findPage(Page<CreditCompanyLoan> page, CreditCompanyLoan creditCompanyLoan) {
		return super.findPage(page, creditCompanyLoan);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(CreditCompanyLoan creditCompanyLoan) {
		super.save(creditCompanyLoan);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(CreditCompanyLoan creditCompanyLoan) {
		super.delete(creditCompanyLoan);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@Transactional(value="CRE",readOnly = false)
	public void banchDelete(String ids){
		if (ids != null) {
			String idArray[] = ids.split(",");
			List<String> idList = Arrays.asList(idArray);
			Map<String, Object> param = new HashMap<String, Object>();
			if (idList != null && idList.size() > 0) {
				param.put("idList", idList);
				super.dao.banchDelete(param);
			}
			
		}
	}
	
	
	
	/**
	 * 计算企业征信中贷款总额度、贷款总余额、贷款逾期总额
	 * @param creditCustLoanId
	 * @return
	 */
	public Map<String, Object> getSumElements(String creditCustId){
		return super.dao.getSumElements(creditCustId);
	};
	
	
}