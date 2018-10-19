package com.resoft.credit.gedApplyRegister.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.gedApplyRegister.entity.GedApplyRegister;

/**
 * 冠e贷申请客户登记信息表DAO接口
 * 
 * @author wangguodong
 * @version 2017-02-14
 */
@MyBatisDao
public interface GedApplyRegisterDao extends CrudDao<GedApplyRegister> {
	/**
	 * 查询指定角色下的所有用户
	 */
	public List<Map<String, Object>> getUserToAllot(Map<String, Object> params);

	/**
	 * 更改分配状态
	 */
	public void updateAllotStatus(Map<String, Object> params);

	/**
	 * 根据applyId查询applyRegister
	 */
	public ApplyRegister getApplyRegisterByIFTApplyId(String iftApplyId);

	public GedApplyRegister getGedApplyRegisterByApplyId(String applyId);
	
	/**
	 * 查询数据 不分页
	 */
	public List<GedApplyRegister> findgedGedApplyRegisters(GedApplyRegister gedApplyRegister );
	
}