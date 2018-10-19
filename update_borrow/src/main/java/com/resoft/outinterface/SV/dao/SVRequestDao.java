package com.resoft.outinterface.SV.dao;

import java.util.List;
import java.util.Map;

import com.resoft.outinterface.SV.server.entry.request.SVCreditAssets;
import com.resoft.outinterface.SV.server.entry.request.SVCreditCompany;
import com.resoft.outinterface.SV.server.entry.request.SVCreditCust;
import com.resoft.outinterface.SV.server.entry.request.SVLineBank;
import com.resoft.outinterface.SV.server.entry.request.SVRequestHead;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * SV接口DAO
 * 
 * @author wuxi01
 * @date 2016-04-18
 */
@MyBatisDao
public interface SVRequestDao {

	/**
	 * 根据applyNo查询请求头信息（合同号、序列号）
	 * 
	 * @param applyNo
	 * @return
	 */
	public SVRequestHead getSVrequestHeadByApplyNo(String applyNo);

	/**
	 * 查询指定角色的企业征信列表
	 * 
	 * @param params
	 * @return
	 */
	public List<Map<String, String>> findSVCompanyList(Map<String, String> params);

	/**
	 * 查询指定角色的个人征信列表
	 * 
	 * @param params
	 * @return
	 */
	public List<Map<String, String>> findSVPersonList(Map<String, String> params);

	/**
	 * 根据applyNo查询银行卡流水信息列表
	 * 
	 * @return
	 */
	public List<SVLineBank> findSVLineBankList(Map<String, String> params);

	/**
	 * 根据applyNo查询企业证信息
	 * 
	 * @param params
	 * @return
	 */
	public SVCreditCompany getCompanyCredit(Map<String, String> params);

	/**
	 * 根据applyNo查询个人证信息
	 * 
	 * @param params
	 * @return
	 */
	public SVCreditCust getPersonCredit(Map<String, String> params);

	/**
	 * 根据applyNo查询资产列表
	 * 
	 * @param params
	 * @return
	 */
	public List<SVCreditAssets> findAssetsList(Map<String, String> params);

	/**
	 * 批量插入SVRequestBaseData
	 * 
	 * @param svRequestBaseDataList
	 */
	public void saveSVRequestBaseData(Map<String, Object> baseInfo);
	
	/**
	 * 保存个人征信信息
	 * @param baseInfo
	 * @param creditCustList
	 */
	public void saveCreditCust(Map<String, Object> baseInfo);
	
	/**
	 * 保存企业征信信息
	 * @param baseInfo
	 * @param creditCompanyList
	 */
	public void saveCreditCompany(Map<String, Object> baseInfo);
	
	/**
	 * 保存银行卡信息
	 * @param baseInfo
	 * @param lineBankList
	 */
	public void saveLineBank(Map<String, Object> baseInfo);
	/**
	 * 保存银行卡明细信息
	 * @param baseInfo
	 */
	public void saveLineBankDetail(Map<String, Object> baseInfo);
	
	/**
	 * 保存资产信息
	 * @param baseInfo
	 * @param lineBankList
	 */
	public void saveAssets(Map<String, Object> baseInfo);

}
