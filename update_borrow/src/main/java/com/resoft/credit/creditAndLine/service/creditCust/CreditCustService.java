package com.resoft.credit.creditAndLine.service.creditCust;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.resoft.outinterface.SV.client2.PersonalCreditField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.creditAndLine.dao.creditCust.CreditCustDao;
import com.resoft.credit.creditAndLine.entity.creditCust.CreditCust;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 个人征信列表Service
 * @author wuxi01
 * @version 2016-02-26
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CreditCustService extends CrudService<CreditCustDao, CreditCust> {

	@Autowired
	private CreditCustLoanService creditCustLoanService;
	
	public CreditCust get(String id) {
		CreditCust creditCust = super.get(id);
		return creditCust;
	}
	
	public List<CreditCust> findList(CreditCust creditCust) {
		return super.findList(creditCust);
	}
	
	public Page<CreditCust> findPage(Page<CreditCust> page, CreditCust creditCust) {
		return super.findPage(page, creditCust);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(CreditCust creditCust) {
		super.save(creditCust);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(CreditCust creditCust) {
		super.delete(creditCust);
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
	 * 更新个人征信信息中的贷款总额、贷款余额、贷款逾期总额
	 * @param creditCust
	 */
	@Transactional(value="CRE",readOnly = false)
	public void updateSumElements(String creditCustId){
		//更新个人征信中贷款总额度、贷款总余额、贷款逾期总额
		Map<String, Object> resultMap = creditCustLoanService.getSumElements(creditCustId);
		BigDecimal sumLoanAmount = null;
		BigDecimal sumBalanceAmount = null;
		BigDecimal sumOverdueAmount = null;
		if(resultMap != null){
			sumLoanAmount = (BigDecimal) resultMap.get("sumLoanAmount");
			sumBalanceAmount = (BigDecimal) resultMap.get("sumBalanceAmount");
			sumOverdueAmount = (BigDecimal) resultMap.get("sumOverdueAmount");
		}
		CreditCust creditCust = new CreditCust();
		creditCust.preUpdate();
		creditCust.setId(creditCustId);
		creditCust.setSumBalanceAmount(sumBalanceAmount);
		creditCust.setSumLoanAmount(sumLoanAmount);
		creditCust.setSumOverdueAmount(sumOverdueAmount);
		super.dao.updateSumElements(creditCust);
	}

	public List<PersonalCreditField> getCreditByApply(String applyNo) {
		return dao.getCreditByApply(applyNo);
	}
}