package com.resoft.credit.mortgagedperson.dao;

import java.util.List;
import java.util.Map;

import com.resoft.credit.mortgagedperson.entity.MortgagedPerson;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * @reqno:H1601220056
 * @date-designer:2016年1月25日-songmin
 * @date-author:2016年1月25日-songmin:CRE_信贷审批_客户信息管理_抵押权人信息管理
 * 	抵押权人DAO
 */
@MyBatisDao
public interface MortgagedPersonDao extends CrudDao<MortgagedPerson> {
	/**
	 * @reqno:H1601250005
	 * @date-designer:2016年1月27日-songmin
	 * @date-author:2016年1月27日-songmin:CRE_信贷审批_客户信息管理_抵押权人信息管理7788_修改、删除
	 * 一次性删除多条抵押权人信息
	 */
	public void deleteList(Map<String,Object> param);
	
	public List<MortgagedPerson> getMortgagedPerson();
	
	public MortgagedPerson getMortgagedPersonById(String id);
	
	/**
	 * 中间人信息
	 */
	public List<MortgagedPerson> getMiddlePerson();
	
	
	/**
	 * 根据Id中间人信息
	 */
	public MortgagedPerson getMiddlePersonById(String id);
	
}