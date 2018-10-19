package com.resoft.credit.GedAccount.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.GedAccount.entity.GedAccount;

/**
 * 冠E贷账户信息DAO接口
 * @author gsh
 * @version 2018-01-24
 */
@MyBatisDao
public interface GedAccountDao extends CrudDao<GedAccount> {
	public GedAccount findGetAccountByCustId(@Param("custId") String custId);
	
	public GedAccount findGedAccountByComIdNum(@Param("soclalCreditCode") String soclalCreditCode);

	public GedAccount findGedAccountByIdNum(@Param("idNum") String idNum);

	public String getCustIdByContract(@Param("contractNo") String contractNo);

	public List<String> checkDanBaoInfoIsOpenAccount(@Param("applyNo")String applyNo);

	public List<String> checkInfoAccount(@Param("custId")String custId,@Param("applyNo")String applyNo);

	public GedAccount getByCustID(@Param("custId")String custId);

	public GedAccount getSingleByCustID(@Param("custId")String custId);

	public String getLHCustIdByContract(@Param("contractNo")String contractNo);

	public GedAccount getFarenByCustID(@Param("custId")String custId);

	public String getByBankNo(@Param("recBankcardNo")String recBankcardNo);


	GedAccount getByLegalPerPhone(@Param("legalPerPhone")String legalPerPhone);

	void updateByLegalPerPhone(GedAccount newGedAccount);

    GedAccount findGedAccountByPhoneNum(@Param("corporationMobile")String corporationMobile);

    public String getCustByCompany(String contractNo);

    void deleteByCustId(@Param("custId")String custId);
    GedAccount getByApplyNo(@Param("applyNo")String applyNo);

    String getUnderCustIdByContract(@Param("contractNo")String contractNo);

    GedAccount getByPhone(@Param("mobileNum")String mobileNum);

	GedAccount getByCustPhoneID(@Param("custId")String approId);

	GedAccount getByComCredit(@Param("applyNo")String applyNo);

	GedAccount queryByBankNo(@Param("recBankcardNo")String recBankcardNo);

}