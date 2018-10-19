package com.resoft.credit.creditAndLine.service.creditLineBank;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.resoft.outinterface.SV.client2.PersonalWaterField;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.creditAndLine.dao.creditLineBank.CreditLineBankDao;
import com.resoft.credit.creditAndLine.entity.creditLineBank.CreditLineBank;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 流水信息列表Service
 * @author wuxi01
 * @version 2016-02-26
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CreditLineBankService extends CrudService<CreditLineBankDao, CreditLineBank> {

	public CreditLineBank get(String id) {
		CreditLineBank creditLineBank = super.get(id);
		return creditLineBank;
	}
	
	public List<CreditLineBank> findList(CreditLineBank creditLineBank) {
		return super.findList(creditLineBank);
	}
	
	public Page<CreditLineBank> findPage(Page<CreditLineBank> page, CreditLineBank creditLineBank) {
		return super.findPage(page, creditLineBank);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(CreditLineBank creditLineBank) {
		super.save(creditLineBank);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(CreditLineBank creditLineBank) {
		super.delete(creditLineBank);
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
	 * 更新进项总额、出项总额、月均有效流水
	 * @param creditLineBank
	 */
	@Transactional(value="CRE",readOnly = false)
	public void updateSumAndAvg(CreditLineBank creditLineBank){
		super.dao.updateSumAndAvg(creditLineBank);
	};

	public List<PersonalWaterField> getLineBankAndDetail(String applyNo) {
		return dao.getLineBankAndDetail(applyNo);
	}

}