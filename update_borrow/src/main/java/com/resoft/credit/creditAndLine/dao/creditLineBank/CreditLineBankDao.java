package com.resoft.credit.creditAndLine.dao.creditLineBank;

import java.util.List;
import java.util.Map;

import com.resoft.outinterface.SV.client2.PersonalWaterField;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.creditAndLine.entity.creditLineBank.CreditLineBank;
import org.apache.ibatis.annotations.Param;

/**
 * 流水信息列表DAO接口
 * @author wuxi01
 * @version 2016-02-26
 */
@MyBatisDao
public interface CreditLineBankDao extends CrudDao<CreditLineBank> {
	
	/**
	 * 批量删除
	 * @param params
	 */
	public void banchDelete(Map<String, Object> params);
	
	/**
	 * 更新进项总额、出项总额、月均有效流水
	 * @param creditLineBank
	 */
	public void updateSumAndAvg(CreditLineBank creditLineBank);

    List<PersonalWaterField> getLineBankAndDetail(@Param("applyNo")String applyNo);
}