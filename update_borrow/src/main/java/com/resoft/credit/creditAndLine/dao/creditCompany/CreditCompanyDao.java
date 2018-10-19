package com.resoft.credit.creditAndLine.dao.creditCompany;

import java.util.List;
import java.util.Map;

import com.resoft.outinterface.SV.client2.CompanyCreditField;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.creditAndLine.entity.creditCompany.CreditCompany;

import org.apache.ibatis.annotations.Param;
/**
 * 企业征信列表DAO接口
 * 
 * @author wuxi01
 * @version 2016-03-18
 */
@MyBatisDao
public interface CreditCompanyDao extends CrudDao<CreditCompany> {

	/**
	 * 批量删除
	 * @param params
	 */
	public void banchDelete(Map<String, Object> params);
	
	/**
	 * 更新企业征信信息中的贷款总额、贷款余额、贷款逾期总额
	 * @param creditCust
	 */
	public void updateSumElements(CreditCompany creditCompany);

    List<CompanyCreditField> getByapplyNo(@Param("applyNo")String applyNo);
}