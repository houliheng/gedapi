package com.resoft.credit.checkPhone.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.checkPhone.entity.CheckPhone;
import com.resoft.credit.checkPhone.dao.CheckPhoneDao;

/**
 * 电话核查Service
 * @author yanwanmei
 * @version 2016-02-27
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CheckPhoneService extends CrudService<CheckPhoneDao, CheckPhone> {

	public CheckPhone get(String id) {
		return super.get(id);
	}
	
	public List<CheckPhone> findList(CheckPhone checkPhone) {
		return super.findList(checkPhone);
	}
	
	public Page<CheckPhone> findPage(Page<CheckPhone> page, CheckPhone checkPhone) {
		return super.findPage(page, checkPhone);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(CheckPhone checkPhone) {
		super.save(checkPhone);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(CheckPhone checkPhone) {
		super.delete(checkPhone);
	}
	
	public List<CheckPhone> findListByAppNo(String appNo){
		return super.dao.findListByAppNo(appNo);
	}
	
	public String getCheckPhoneCount(String applyNo,String custId,String roleType) {
		Map<String,String> param = new HashMap<String, String>();
		param.put("applyNo", applyNo);
		param.put("custId", custId);
		param.put("roleType", roleType);
		return super.dao.getCheckPhoneCount(param);
	}
	
	public List<CheckPhone> getCheckPhoneByApplyNo(String applyNo){
		return super.dao.getCheckPhoneByApplyNo(applyNo);
	}
}