package com.resoft.credit.creditAndLine.dao.creditCust;

import java.util.List;
import java.util.Map;

import com.resoft.outinterface.SV.client2.PersonalCreditField;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.creditAndLine.entity.creditCust.CreditCust;
import org.apache.ibatis.annotations.Param;

/**
 * 个人征信列表DAO接口
 * @author wuxi01
 * @version 2016-03-17
 */
@MyBatisDao
public interface CreditCustDao extends CrudDao<CreditCust> {
	/**
	 * 批量删除
	 * @param params
	 */
	public void banchDelete(Map<String, Object> params);
	/**
	 * 更新个人征信信息中的贷款总额、贷款余额、贷款逾期总额
	 * @param creditCust
	 */
	public void updateSumElements(CreditCust creditCust);

    List<PersonalCreditField> getCreditByApply(@Param("applyNo")String applyNo);
}