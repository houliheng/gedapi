package com.resoft.multds.credit.evaluateAsset.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 根绝角色类型查询人员姓名DAO接口
 * @author zhaohuakui
 * @version 2016-05-25
 */
@MyBatisDao
public interface MortgageFindCustNameDao {
	/**
	 * 根据角色类型在当前流程中查询客户名称
	 * @param Map<String, String>
	 * @return List<Map<String, String>>
	 */
	List<Map<String, String>> findCustNameByRoleType(Map<String, String> params);
	List<Map<String, String>> getContactInfoByApplyNo(Map<String, String> params);
	
}