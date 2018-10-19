package com.resoft.credit.contactInfo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.contactInfo.entity.ContactInfo;

/**
 * 联系人信息DAO接口
 * 
 * @author chenshaojia
 * @version 2016-03-11
 */
@MyBatisDao
public interface ContactInfoDao extends CrudDao<ContactInfo> {
	public void batchDelete(@Param("idList") List<String> idList);

	public long getContactCount(@Param("applyNo") String applyNo);

	public List<ContactInfo> getContactListByRelation(ContactInfo contactInfo);

	/**
	 * 根据申请编号连表查询待电话核查的联系人（参数中roleType为主借人）
	 * 
	 * @param params
	 * @return
	 */
	public List<ContactInfo> findContactInfoByApplyNo(Map<String, Object> params);
	
	public List<Map<String, String>> getContactInfoByApplyNo(Map<String, String> params);
}