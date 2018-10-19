package com.resoft.credit.checkWeb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.checkWeb.entity.CheckWeb;
import com.resoft.credit.checkWeb.dao.CheckWebDao;

/**
 * 网查Service
 * @author yanwanmei
 * @version 2016-02-27
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CheckWebService extends CrudService<CheckWebDao, CheckWeb> {

	public CheckWeb get(String id) {
		return super.get(id);
	}
	
	public List<CheckWeb> findList(CheckWeb checkWeb) {
		return super.findList(checkWeb);
	}
	
	public Page<CheckWeb> findPage(Page<CheckWeb> page, CheckWeb checkWeb) {
		return super.findPage(page, checkWeb);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(CheckWeb checkWeb) {
		super.save(checkWeb);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(CheckWeb checkWeb) {
		super.delete(checkWeb);
	}
	
	public String getCheckWebCount(String applyNo,String custId,String roleType) {
		Map<String,String> param = new HashMap<String, String>();
		param.put("applyNo", applyNo);
		param.put("custId", custId);
		param.put("roleType", roleType);
		return super.dao.getCheckWebCount(param);
	}
	
	public List<CheckWeb> getCheckWebByApplyNo(String applyNo){
		return super.dao.getCheckWebByApplyNo(applyNo);
	}
	
}