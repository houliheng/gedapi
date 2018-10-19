package com.resoft.credit.mortgagedperson.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.mortgagedperson.dao.MortgagedPersonDao;
import com.resoft.credit.mortgagedperson.entity.MortgagedPerson;
import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * @reqno:H1601220056
 * @date-designer:2016年1月25日-songmin
 * @date-author:2016年1月25日-songmin:CRE_信贷审批_客户信息管理_抵押权人信息管理
 * 	抵押权人Service
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class MortgagedPersonService extends CrudService<MortgagedPersonDao, MortgagedPerson> {

	/**
	 * @reqno:H1601220056
	 * @date-designer:2016年1月25日-songmin
	 * @date-author:2016年1月25日-songmin:CRE_信贷审批_客户信息管理_抵押权人信息管理
	 * 	分页查询抵押权人信息
	 */
	public Page<MortgagedPerson> findPage(Page<MortgagedPerson> page, MortgagedPerson creMortgagedPerson) {
		return super.findPage(page, creMortgagedPerson);
	}
	
	/**
	 * @reqno:H1601250005
	 * @date-designer:2016年1月27日-songmin
	 * @date-author:2016年1月27日-songmin:CRE_信贷审批_客户信息管理_抵押权人信息管理7788_修改、删除
	 * 一次性删除多条抵押权人信息
	 */
	@Transactional(value="CRE",readOnly=false)
	public void deleteList(List<String> idList){
		if(null!=idList && idList.size()>0){
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("idList", idList);
			param.put("DEL_FLAG_DELETE", MortgagedPerson.DEL_FLAG_DELETE);
			super.dao.deleteList(param);
		}
	}
	
	public List<MortgagedPerson> getMortgagedPerson(){
		return super.dao.getMortgagedPerson();
	}
	
	/**
	 * 中间人信息
	 */
	public List<MortgagedPerson> getMiddlePerson(){
		return this.dao.getMiddlePerson();
	}
	
	/**
	 * 根据Id获取中间人信息
	 */
	public MortgagedPerson getMiddlePersonById(String id){
		return this.dao.getMiddlePersonById(id);
	}
	
	public MortgagedPerson getMortgagedPersonById(String id){
		return super.dao.getMortgagedPersonById(id);
	}
}