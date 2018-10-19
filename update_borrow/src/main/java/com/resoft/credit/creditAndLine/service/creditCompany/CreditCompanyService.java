package com.resoft.credit.creditAndLine.service.creditCompany;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.resoft.outinterface.SV.client2.CompanyCreditField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.creditAndLine.dao.creditCompany.CreditCompanyDao;
import com.resoft.credit.creditAndLine.entity.creditCompany.CreditCompany;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 企业征信列表Service
 * 
 * @author wuxi01
 * @version 2016-02-26
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CreditCompanyService extends CrudService<CreditCompanyDao, CreditCompany> {

	@Autowired
	private CreditCompanyLoanService creditCompanyLoanService;
	

	public CreditCompany get(String id) {
		CreditCompany creditCompany = super.get(id);
		return creditCompany;
	}

	public List<CreditCompany> findList(CreditCompany creditCompany) {
		return super.findList(creditCompany);
	}

	public Page<CreditCompany> findPage(Page<CreditCompany> page, CreditCompany creditCompany) {
		return super.findPage(page, creditCompany);
	}

	@Transactional(value="CRE",readOnly = false)
	public void save(CreditCompany creditCompany) {
		super.save(creditCompany);
	}

	@Transactional(value="CRE",readOnly = false)
	public void delete(CreditCompany creditCompany) {
		super.delete(creditCompany);
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
	 * 更新企业征信信息中的贷款总额、贷款余额、贷款逾期总额
	 * @param creditCompany
	 */
	@Transactional(value="CRE",readOnly = false)
	public void updateSumElements(String creditCompanyId){
		//更新企业征信中贷款总额度、贷款总余额、贷款逾期总额
		Map<String, Object> resultMap = creditCompanyLoanService.getSumElements(creditCompanyId);
		
		String sumLoanAmount = null;
		String sumBalanceAmount = null;
		String sumOverdueAmount = null;
		if(resultMap !=null){
			sumLoanAmount = resultMap.get("sumLoanAmount").toString();
			sumBalanceAmount = resultMap.get("sumBalanceAmount").toString();
			sumOverdueAmount = resultMap.get("sumOverdueAmount").toString();
		}
		CreditCompany creditCompany = new CreditCompany();
		creditCompany.preUpdate();
		creditCompany.setId(creditCompanyId);
		creditCompany.setSumBalanceAmount(sumBalanceAmount);
		creditCompany.setSumLoanAmount(sumLoanAmount);
		creditCompany.setSumOverdueAmount(sumOverdueAmount);
		super.dao.updateSumElements(creditCompany);
	}

    public List<CompanyCreditField> getByapplyNo(String applyNo) {
		return dao.getByapplyNo(applyNo);
    }
}