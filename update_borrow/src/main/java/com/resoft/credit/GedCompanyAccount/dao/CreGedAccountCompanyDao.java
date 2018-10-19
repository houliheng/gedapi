package com.resoft.credit.GedCompanyAccount.dao;

import com.resoft.credit.GedCompanyAccount.entity.CreGedAccountCompany;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 冠E贷企业账户信息DAO接口
 * @author gsh
 * @version 2018-03-31
 */
@MyBatisDao
public interface CreGedAccountCompanyDao extends CrudDao<CreGedAccountCompany> {
	public List<CreGedAccountCompany> findCompanyAccountBySocialCreditNo(@Param("unSocCreditNo") String unSocCreditNo);

	public List<String> checkCompanyAccount(@Param("custId")String custId, @Param("applyNo")String applyNo);

	public List<String> checkBatchIsOpenAccount(@Param("applyNo")String applyNo);

	public List<String> checkDanBaoIsOpenAccount(@Param("applyNo")String applyNo);

	public CreGedAccountCompany getCompanyAccount(@Param("approId")String approId);


	/**
	 * 查询开户审核列表
	 * */
	public List<MyMap> findAccountCompanyList(Map<String, Object> paramMap);

	/**
	 * 查询开户已办列表
	 * */
	public List<MyMap> findAccountCompanyDoneList(Map<String, Object> paramMap);

	/**
	 * 更新状态
	 * @param id
	 * @param status
	 */
	public void updateSaveAccount(@Param("status")String status, @Param("id")String id,@Param("createTime")String createTime,@Param("reason")String reason);

	/**
	 * 查询企业开户信息
	 * @param id
	 * @return
	 */
	CreGedAccountCompany selectCompanyAccountById(@Param("id")String id);

    CreGedAccountCompany findCompanyAccountByApproId(@Param("approId")String approId);
    public String getCustByCompany(String contractNo);
    CreGedAccountCompany getCompanyAccountByCustId(@Param("custId")String custId);

    void deleteByCustId(@Param("custId")String custId);
	public CreGedAccountCompany getByApplyNo(@Param("applyNo")String applyNo);
}