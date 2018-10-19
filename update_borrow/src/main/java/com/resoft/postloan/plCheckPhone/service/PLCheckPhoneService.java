package com.resoft.postloan.plCheckPhone.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.postloan.plCheckPhone.dao.PLCheckPhoneDao;
import com.resoft.postloan.plCheckPhone.entity.PLApplyRelation;
import com.resoft.postloan.plCheckPhone.entity.PLCheckPhone;
import com.resoft.postloan.plCheckPhone.entity.PLContactInfo;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 借后电话外访Service
 * 
 * @author wangguodong
 * @version 2016-09-02
 */
@Service
@Transactional(readOnly = true)
public class PLCheckPhoneService extends CrudService<PLCheckPhoneDao, PLCheckPhone> {

	public PLCheckPhone get(String id) {
		return super.get(id);
	}

	public List<PLCheckPhone> findList(PLCheckPhone checkPhone) {
		return super.findList(checkPhone);
	}

	public Page<PLCheckPhone> findPage(Page<PLCheckPhone> page, PLCheckPhone checkPhone) {
		return super.findPage(page, checkPhone);
	}

	@Transactional(readOnly = false)
	public void save(PLCheckPhone checkPhone) {
		super.save(checkPhone);
	}

	@Transactional(readOnly = false)
	public void delete(PLCheckPhone checkPhone) {
		super.delete(checkPhone);
	}

	public List<PLCheckPhone> getCheckPhoneByAllocateId(String allocateId) {
		return this.dao.getCheckPhoneByAllocateId(allocateId);
	}

	public List<PLApplyRelation> findByApplyNoAndRoleTypeList(Map<String, Object> params) {
		return this.dao.findByApplyNoAndRoleTypeList(params);
	}

	public String getCheckPhoneCount(String allocateId,String custId,String roleType) {
		Map<String,String> param = new HashMap<String, String>();
		param.put("allocateId", allocateId);
		param.put("custId", custId);
		param.put("roleType", roleType);
		return this.dao.getCheckPhoneCount(param);
	}
	public List<PLContactInfo> findContactInfoByApplyNo(Map<String, Object> params){
		return this.dao.findContactInfoByApplyNo(params);
	}
	
	public String getCheckFaceByApplyNo(String applyNo){
		return this.dao.getCheckFaceByApplyNo(applyNo);
	}
}

