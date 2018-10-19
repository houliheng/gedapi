package com.resoft.credit.creditAndLine.service.creditCust;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.creditAndLine.entity.creditCust.CreditCustLoan;
import com.resoft.credit.creditAndLine.dao.creditCust.CreditCustLoanDao;

/**
 * 征信个人贷款明细Service
 * @author wuxi01
 * @version 2016-02-27
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CreditCustLoanService extends CrudService<CreditCustLoanDao, CreditCustLoan> {

	
	
	public CreditCustLoan get(String id) {
		CreditCustLoan creditCustLoan = super.get(id);
		return creditCustLoan;
	}
	
	public List<CreditCustLoan> findList(CreditCustLoan creditCustLoan) {
		return super.findList(creditCustLoan);
	}
	
	public Page<CreditCustLoan> findPage(Page<CreditCustLoan> page, CreditCustLoan creditCustLoan) {
		return super.findPage(page, creditCustLoan);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(CreditCustLoan creditCustLoan) {
		super.save(creditCustLoan);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(CreditCustLoan creditCustLoan) {
		super.delete(creditCustLoan);
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
	 * 计算个人征信中贷款总额度、贷款总余额、贷款逾期总额
	 * @param creditCustLoanId
	 * @return
	 */
	public Map<String, Object> getSumElements(String creditCustId){
		return super.dao.getSumElements(creditCustId);
	};
	
}