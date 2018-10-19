package com.resoft.postloan.plCheckPhone.dao;

import java.util.List;
import java.util.Map;

import com.resoft.postloan.plCheckPhone.entity.PLApplyRelation;
import com.resoft.postloan.plCheckPhone.entity.PLCheckPhone;
import com.resoft.postloan.plCheckPhone.entity.PLContactInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 借后电话外访DAO接口
 * 
 * @author wangguodong
 * @version 2016-09-02
 */
@MyBatisDao
public interface PLCheckPhoneDao extends CrudDao<PLCheckPhone> {

	public List<PLCheckPhone> getCheckPhoneByAllocateId(String allocateId);

	public String getCheckPhoneCount(Map<String, String> param);

	/**
	 * 根据申请编号和角色类型List查询关系表
	 * 
	 * @param params
	 * @return
	 */
	public List<PLApplyRelation> findByApplyNoAndRoleTypeList(Map<String, Object> params);

	/**
	 * 根据申请编号连表查询待电话核查的联系人（参数中roleType为主借人）
	 * 
	 * @param params
	 * @return
	 */
	public List<PLContactInfo> findContactInfoByApplyNo(Map<String, Object> params);

	/**
	 * 查询面审信息
	 * @param applyNo
	 * @return
	 */
	public String getCheckFaceByApplyNo(String applyNo);
}