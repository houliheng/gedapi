package com.resoft.credit.underCompanyInfo.service;

import java.util.List;

import com.resoft.credit.GedAccount.entity.GedAccount;
import com.resoft.credit.GedAccount.service.GedAccountService;
import com.resoft.credit.GedCompanyAccount.entity.CreGedAccountCompany;
import com.resoft.credit.GedCompanyAccount.service.CreGedAccountCompanyService;
import com.resoft.outinterface.rest.newged.entity.AddOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.underCompanyInfo.entity.UnderCompanyInfo;
import com.resoft.credit.underCompanyInfo.dao.UnderCompanyInfoDao;

/**
 * 线下借款-企业信息Service
 * @author jml
 * @version 2018-06-26
 */
@Service
@Transactional(readOnly = false)
public class UnderCompanyInfoService extends CrudService<UnderCompanyInfoDao, UnderCompanyInfo> {

	//冠e贷个人账户接口
	@Autowired
	private GedAccountService gedAccountService;
	//冠e贷企业账户接口
	@Autowired
	private CreGedAccountCompanyService gedAccountCompanyService;

	@Autowired
	private UnderCompanyInfoDao underCompanyInfoDao;

	public UnderCompanyInfo get(String id) {
		return super.get(id);
	}
	
	public List<UnderCompanyInfo> findList(UnderCompanyInfo underCompanyInfo) {
		return super.findList(underCompanyInfo);
	}
	
	public Page<UnderCompanyInfo> findPage(Page<UnderCompanyInfo> page, UnderCompanyInfo underCompanyInfo) {
		return super.findPage(page, underCompanyInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(UnderCompanyInfo underCompanyInfo) {
		super.save(underCompanyInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(UnderCompanyInfo underCompanyInfo) {
		super.delete(underCompanyInfo);
	}

	@Transactional(readOnly = false)
    public void updateStatus(String status, String applyNo) {
		dao.updayeStatus(status,applyNo);
    }

	/***
	 * lwiei  获取账户信息
	 * @param idNum
	 */
	public GedAccount getAccountInfo(String idNum){
		try {
			GedAccount account = gedAccountService.findGedAccountByIdNum(idNum);
			return account;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * liwei  根据统一社会信用代码获取企业账户信息
	 * @param socialCreditNo
	 * @return
	 */
	public CreGedAccountCompany getCompanyAccountInfo(String socialCreditNo){
		try {
			List<CreGedAccountCompany> accounts = gedAccountCompanyService.findCompanyAccountBySocialCreditNo(socialCreditNo);
			if (accounts!=null && accounts.size() ==1)
				return accounts.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Integer update(UnderCompanyInfo underCompanyInfo){
//		if("".equals(underCompanyInfo.getPersonIdEndDate())) {
//			underCompanyInfo.setPersonIdEndDate(null);
//		}
//		super.save(underCompanyInfo);
//		if (underCompanyInfo.getApplyNo() != null){
//			return underCompanyInfoDao.updateByApplyNo(underCompanyInfo);
//		}
		return  null;
	}

	public Integer updateByApplyNo(UnderCompanyInfo underCompanyInfo){
		if (underCompanyInfo.getApplyNo() != null){
			return underCompanyInfoDao.updateByApplyNo(underCompanyInfo);
		}
		return  null;
	}

	@Transactional(value = "CRE", readOnly = false)
	public void updateOrgInfoByApplyNo(UnderCompanyInfo underCompanyInfo) {
		if (underCompanyInfo.getApplyNo() != null) {
			underCompanyInfoDao.updateOrgInfoByApplyNo(underCompanyInfo);
		}
	}

	public UnderCompanyInfo getByApplyNo(String applyNo){
		return underCompanyInfoDao.getByApplyNo(applyNo);
	}


    public AddOrderRequest pushOrder(String applyNo) {
		return dao.pushOrder(applyNo);
    }

    public void updateGedAccount(String corporationMobile,String applyNo) {
		dao.updateGedAccount(corporationMobile,applyNo);
    }
}