package com.resoft.accounting.discountList.dao;



import com.resoft.accounting.discountList.entity.Discount;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
* @author guoshaohua
* @version 2018年5月17日 下午2:22:47
* 
*/
@MyBatisDao
public interface DiscountDao extends CrudDao<Discount>{
	public Discount finPalnRepayDetailsByContractNo(String contractNo);
	public Integer isImport(String contractNo);
	
}
