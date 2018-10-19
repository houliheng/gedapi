package com.resoft.accounting.deductApply.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.accounting.deductApply.entity.DeductApplyVO;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 划扣查询DAO接口
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@MyBatisDao
public interface DeductApplyDetailVODao extends CrudDao<DeductApplyVO> {

	/**
	 * 入账成功查询
	 */
	public List<DeductApplyVO> getDeductApplyVOForSuccess(DeductApplyVO deductApplyVO);

	/**
	 * 入账成功查询Count
	 */
	public long getDeductApplyVOForSuccessCount(DeductApplyVO deductApplyVO);

	/**
	 * 入账失败查询
	 */
	public List<DeductApplyVO> getDeductApplyVOForFail(DeductApplyVO deductApplyVO);

	/**
	 * 入账失败查询Count
	 */
	public long getDeductApplyVOForFailCount(DeductApplyVO deductApplyVO);

	/**
	 * 根据流水号查询总体扣款金额list
	 * 
	 * @param deductApplyVO
	 * @return
	 */
	public List<DeductApplyVO> getDeductAmountList(DeductApplyVO deductApplyVO);

	/**
	 * 获取指定还款明细数据
	 */
	public List<Map<String, Object>> getRepayDetailData(Map<String, Object> params);

	/**
	 * 插入明细表
	 */
	public void saveDataInRepayDetail(@Param("deductApplyVOList") List<DeductApplyVO> deductApplyVOs);

	/**
	 * 根据deductapplyNo查询选中数据的总金额
	 */
	public List<String> getDeductAmountByDeductAppyNo(String deductApplyNo);
}