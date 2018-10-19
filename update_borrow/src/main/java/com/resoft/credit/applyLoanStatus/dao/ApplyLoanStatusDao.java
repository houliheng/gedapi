package com.resoft.credit.applyLoanStatus.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.applyLoanStatus.entity.ApplyLoanStatus;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 财务放款DAO接口
 * @author wuxi01
 * @version 2016-03-03
 */
@MyBatisDao
public interface ApplyLoanStatusDao extends CrudDao<ApplyLoanStatus> {
	/**
	 * 根据证件类型+证件号或手机号查询合同状态
	 * @return
	 */
	public List<String> findContractStatusByIdCardOrMobile(Map<String, String> map);
	
	/**
	 * 批量删除方法
	 * @param idList
	 */
	public void banchDelete(List<String> idList);
	
	/**
	 * 根据合同号查询记录
	 */
	public List<ApplyLoanStatus> getApplyLoanStatusByApplyNo(String applyNo);

	/**
	 * 审核通过后新增数据
	 */
	void saveApplyLoanStatus(ApplyLoanStatus applyLoanStatus);
	
	public void updateContractState(String contractNo);
	// 查询联合授信信息  
	public List<CheckApproveUnion> findUnionCust(Map<String, String> unionMap);
	
	/**
	 * 更新状态为已推送待提现
	 */
	public void updateLoanStatus(ApplyLoanStatus applyLoanStatus);
	/**
	 * 查询状态是否都为已提现
	 */
	public List<CheckApproveUnion> getLoanStatusNew(String applyNo);
	/**
	 * 根据申请编号取得 放款状态
	 */
	public String getLoanStatusByApplyNo(String applyNo);
	
	/**
	 * 根据applyNo删除放款提现信息
	 */
	public void deleteLoanStatusByApplyNo(String applyNo);

	/**
	 * 更改合同号
	 */
	public void updateContractNo(ApplyLoanStatus applyLoanStatus);
	
	/**
	 * 冠易贷订单状态反馈(一次提现、二次提现)状态更新
	 * @param applyNo
	 * @param loanStatus
	 */
	public boolean updateDrawStatus(String applyNo , String loanStatus);
	
	/**
	 * 服务收取状态更新
	 * @param applyNo
	 */
	public void updateServiceFeeByApplyNo(Map<String,String> param);
	
	/**
	 * 查询是否存在对应放款信息
	 * @param applyNo
	 * @return
	 */
	public List<ApplyLoanStatus> finApplyLoanStatus(Map<String,String> param);
	
	
	/**
	 * 首次提现更新状态
	 * @param param
	 */
	public void updateLoanStatusByApplyNoAndContractNo(Map<String,String> param);
	
	/**
	 * 更新已推送冠E贷解冻状态
	 * @param param
	 */
	public void updateSendGEDJd(Map<String,String> param);
	
	
	public ApplyLoanStatus queryContractLoanStatus(@Param("contractNo") String contractNo);
	
}