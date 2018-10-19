package com.resoft.credit.companyInfo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 企业基本信息DAO接口
 * @author caoyinglong
 * @version 2016-02-27
 */
@MyBatisDao
public interface CompanyInfoDao extends CrudDao<CompanyInfo> {
	public CompanyInfo getByApplyNo(String applyNo);

	public List<CompanyInfo> findListByParams(Map<String, Object> params);

	/**
	 * 批量删除客户信息
	 * @param
	 *
	 * @param idList
	 */
	public void banchDelete(Map<String, Object> param);

	/**
	 * 关联关系表 根据applyNo 查询某个企业的信息
	 *
	 * @param param1
	 * @return
	 */
	public CompanyInfo getCompanyInfoByApplyNoAndCustId(Map<String, String> param1);

	public List<CompanyInfo> findGedListByParams(Map<String, Object> params);

	public String getCredit(@Param("applyNo") String applyNo, @Param("roleType") String roleType);

	public String getRoleIsMainOrBatch(@Param("applyNo") String applyNo,@Param("companyId")  String companyId);

	public List<CompanyInfo> findBatchGuarantee(@Param("applyNo") String applyNo,@Param("companyId")  String companyId);

	public List<CompanyInfo> findGedDanBaoListByParams(Map<String, Object> params);

	public String getMainCodeByApply(@Param("applyNo") String applyNo);

	public List<CompanyInfo> findGuaranteeListByParams(Map<String, Object> params);

	public void banchGuaranteeDelete(Map<String, Object> param);

	public CompanyInfo getCompanyNameByApproId(@Param("approId")String approId);

	//public void saveRegisterType(@Param("flag")String flag, @Param("id")String id);
	public void InsertunSocCreditNo(CompanyInfo companyInfo);


	CompanyInfo getByApplyNoAndRoleType(Map<String, Object> params);

}