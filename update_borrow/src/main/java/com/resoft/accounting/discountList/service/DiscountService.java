package com.resoft.accounting.discountList.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.resoft.accounting.discountList.dao.DiscountDao;
import com.resoft.accounting.discountList.entity.Discount;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
* @author guoshaohua
* @version 2018年5月17日 下午2:22:09
* 
*/
@Service @DbType("acc.dbType")
@Transactional(value="ACC",readOnly = true)
public class DiscountService  extends CrudService<DiscountDao, Discount> {
	public Page<Discount> findPage(Page<Discount> page, Discount discount) {
		return super.findPage(page, discount);
	}
	
	public Discount get(String id) {
		return super.get(id);
	}
	
	public Discount finPalnRepayDetailsByContractNo(String contractNo){
		return this.dao.finPalnRepayDetailsByContractNo(contractNo);
	}
	
	public Integer isImport(String contractNo){
		return this.dao.isImport(contractNo);
	}
}
