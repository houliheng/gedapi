package com.resoft.credit.checkApprove.dao;

import java.util.List;
import java.util.Map;

import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

/**
 * 批复信息DAO接口
 * @author wuxi01
 * @version 2016-02-29
 */
@MyBatisDao
public interface CheckApproveDao extends CrudDao<CheckApprove> {
	public List<CheckApprove> getCheckApproveByApplyNo(Map<String,String> param);
	
	public void deleteChekApproveBackUp(String applyNo);
	
	public void insertCheckApproveBackUp(String applyNo);
	
	public CheckApprove getCheckApproveBackUp(String applyNo);

    CheckApprove getByApplyNo(@Param("applyNo")String applyNo,@Param("taskDefKey")String taskDefKey);
	//under_contract_no
    CheckApprove getByUnderContract(@Param("underContractNo")String underContractNo);
}