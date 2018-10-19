package com.resoft.credit.gedApiUser.service;

import java.util.List;

import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.gedApiUser.entity.CreGedapiUser;
import com.resoft.credit.gedApiUser.dao.CreGedapiUserDao;

/**
 * 冠E贷账号Service
 * @author lb
 * @version 2018-05-23
 */
@Service
@Transactional(readOnly = true)
public class CreGedapiUserService extends CrudService<CreGedapiUserDao, CreGedapiUser> {

	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private CustInfoService custInfoService;

	public CreGedapiUser get(String id) {
		return super.get(id);
	}
	
	public List<CreGedapiUser> findList(CreGedapiUser creGedapiUser) {
		return super.findList(creGedapiUser);
	}
	
	public Page<CreGedapiUser> findPage(Page<CreGedapiUser> page, CreGedapiUser creGedapiUser) {
		return super.findPage(page, creGedapiUser);
	}
	
	@Transactional(readOnly = false)
	public void save(CreGedapiUser creGedapiUser) {
		super.save(creGedapiUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(CreGedapiUser creGedapiUser) {
		super.delete(creGedapiUser);
	}

	public    CreGedapiUser  queryGedUSerByCustName(String custName){
       return super.dao.queryGedApiUserByCustName(custName);

	}
	@Transactional(value="CRE",readOnly = false)
	public int deleteGedapiUser(String gedNumber){

		return super.dao.deleteGedapiUserByGedNumber(gedNumber);
	}


	@Transactional(readOnly = false)
    public void saveRegisterInfo(String custId, String type,String gedAccount) {
		CreGedapiUser creGedapiUser = new CreGedapiUser();
		creGedapiUser.setCustId(custId);
		creGedapiUser.setRoleType(type);
		creGedapiUser.setGedNumber(gedAccount);
		if("1".equalsIgnoreCase(type)){//个人
			CustInfo custInfo = custInfoService.get(custId);
			creGedapiUser.setCustName(custInfo.getCustName());
			creGedapiUser.setPhone(custInfo.getMobileNum());
			creGedapiUser.setIdNum(custInfo.getIdNum());
		}
		if("2".equalsIgnoreCase(type)){//企业
			CompanyInfo companyInfo = companyInfoService.get(custId);
			creGedapiUser.setCustName(companyInfo.getBusiRegName());
			creGedapiUser.setPhone(companyInfo.getCorporationMobile());
			creGedapiUser.setIdNum(companyInfo.getUnSocCreditNo());
		}
		creGedapiUser.preInsert();
		dao.insert(creGedapiUser);
    }
}