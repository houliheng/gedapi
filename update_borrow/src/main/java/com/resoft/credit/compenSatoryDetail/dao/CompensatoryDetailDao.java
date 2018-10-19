package com.resoft.credit.compenSatoryDetail.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.compenSatoryDetail.entity.CompensatoryDetail;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 代偿详情表DAO接口
 * @author jml
 * @version 2018-03-16
 */
@MyBatisDao
public interface CompensatoryDetailDao extends CrudDao<CompensatoryDetail> {
	/**
	 * 查询代扣流水
	 * @param param
	 * @return
	 */
	public List<CompensatoryDetail> findCompenSatorByConAndPer(@Param("contractNo") String contractNo,@Param("periodNum") String periodNum);
	
	/**
	 * 更新是否已还代偿人账户
	 * @param pamram
	 */
	public void updateSendZjStatus(@Param("contractNo") String contractNo,@Param("periodNum") String periodNum,@Param("surplusAmount") BigDecimal surplusAmount);

	public String getAmount(@Param("contractNo")String contractNo, @Param("period")String period);
	
	/**
	 * 
	 * 查询代偿金额
	 * @param contractNo
	 * @return
	 */
	public CompensatoryDetail queryCompenMoneyByContractNo(@Param("contractNo") String contractNo);
	public CompensatoryDetail getCompenyByContractAndPerNum(@Param("contractNo")String contractNo, @Param("period")String period);
	
	/**
	 * 更新代偿状态
	 * @param seqNum
	 */
	public void updateCompensatoryStatusBySeqNum(String seqNum);
	/**
	 * 通过流水号查询代偿信息
	 * @param seqNum
	 * @return
	 */
	public CompensatoryDetail queryCompenMoneyBySeqNo(@Param("seqNum")String seqNum,@Param("flag") String flag);
	
	public void updateCompensatoryInfoBySeqNum(@Param("seqNum")String seqNum, @Param("seqNo")String seqNo);
}