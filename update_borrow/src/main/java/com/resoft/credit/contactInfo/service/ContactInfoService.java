package com.resoft.credit.contactInfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.contactInfo.entity.ContactInfo;
import com.resoft.credit.contactInfo.dao.ContactInfoDao;

/**
 * 联系人信息Service
 * 
 * @author chenshaojia
 * @version 2016-03-11
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class ContactInfoService extends CrudService<ContactInfoDao, ContactInfo> {

	public ContactInfo get(String id) {
		return super.get(id);
	}

	public List<ContactInfo> findList(ContactInfo contactInfo) {
		return super.findList(contactInfo);
	}

	public Page<ContactInfo> findPage(Page<ContactInfo> page, ContactInfo contactInfo) {
		return super.findPage(page, contactInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(ContactInfo contactInfo) {
		super.save(contactInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(ContactInfo contactInfo) {
		super.delete(contactInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void batchDelete(List<String> idList) {
		super.dao.batchDelete(idList);
	}

	public List<ContactInfo> getContactListByRelation(ContactInfo contactInfo) {
		return super.dao.getContactListByRelation(contactInfo);
	}

	/**
	 * 根据申请编号连表查询待电话核查的联系人（参数中roleType为主借人）
	 * 
	 * @param params
	 * @return
	 */
	public List<ContactInfo> findContactInfoByApplyNo(Map<String, Object> params) {
		return super.dao.findContactInfoByApplyNo(params);
	}
	//新增流水银行卡中根据applyNo查找联系人
	public List<Map<String, String>> getContactInfoByApplyNo(Map<String, String> params) {
		return super.dao.getContactInfoByApplyNo(params);
	}

}