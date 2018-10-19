package com.resoft.credit.GedAccount.service;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.resoft.credit.underCustInfo.entity.UnderCustInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.credit.GedAccount.dao.GedAccountDao;
import com.resoft.credit.GedAccount.entity.GedAccount;
import com.resoft.credit.GedCompanyAccount.service.CreGedAccountCompanyService;
import com.resoft.credit.checkApproveUnion.service.CheckApproveUnionService;
import com.resoft.credit.guaranteeRelation.service.GuaranteeRelationService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.service.AreaService;


/**
 * 冠E贷账户信息Service
 * @author gsh
 * @version 2018-01-24
 */
@Service
@Transactional(readOnly = true)
public class GedAccountService extends CrudService<GedAccountDao, GedAccount> {
	
	@Autowired
	private GedAccountDao gedAccountDao;
	@Autowired
	private AreaService areaService;// 区域地质service
	@Autowired
	private CheckApproveUnionService checkApproveUnionService;
	@Autowired
	private CreGedAccountCompanyService creGedAccountCompanyService;
	
	public GedAccount get(String id) {
		return super.get(id);
	}
	
	public List<GedAccount> findList(GedAccount gedAccount) {
		return super.findList(gedAccount);
	}
	
	public Page<GedAccount> findPage(Page<GedAccount> page, GedAccount gedAccount) {
		return super.findPage(page, gedAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(GedAccount gedAccount) {
		// 获取省级下拉
		LinkedHashMap<String, String> gedProvinceMap = loadAreaData("1");// 这里的1表示全国
		// 获取市级下拉
		LinkedHashMap<String, String> gedCityMap = loadAreaData(gedAccount.getProvinceName());
		gedAccount.setProvinceName(gedProvinceMap.get(gedAccount.getProvinceName()));
		gedAccount.setCityName(gedCityMap.get(gedAccount.getCityName()));
		dao.deleteByCustId(gedAccount.getCustId());
		gedAccount.preInsert();
		dao.insert(gedAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(GedAccount gedAccount) {
		super.delete(gedAccount);
	}
		
	public GedAccount findGedAccountByComIdNum(String soclalCreditCode) throws Exception{
		return gedAccountDao.findGedAccountByComIdNum(soclalCreditCode);
	}
	public GedAccount findGedAccountByIdNum(String IdNum) throws Exception{
		return gedAccountDao.findGedAccountByIdNum(IdNum);
	}
	
	//判断该对象是否
    public static boolean isAllFieldNull(Object obj) throws Exception{
    	Class stuCla = (Class) obj.getClass();
        Field[] fs = stuCla.getDeclaredFields();
        boolean flag = true;
        for (Field f : fs) {
            f.setAccessible(true); 
            Object val = f.get(obj);
            System.out.println("companyBankBranchName".equals(f.getName()));
            if(!"companyBankBranchName".equals(f.getName()) && !"comIdNum".equals(f.getName()) && !"companyName".equals(f.getName()) && (val ==null || (StringUtils.isEmpty(String.valueOf(val))))) {
                flag = false;
                break;
            }
        }
        return flag;
    }
    
 // 省市级联数据加载
 	private LinkedHashMap<String, String> loadAreaData(String areaCode) {
 		Map<String, String> param = Maps.newConcurrentMap();
 		LinkedHashMap<String, String> areaMap = new LinkedHashMap<String, String>();
 		if (StringUtils.isNotEmpty(areaCode)) {
 			param.put("parentId", areaCode);// 根据市级ID获取区县数据信息
 			List<Map<String, String>> regDistinctList = this.areaService.getTreeNode(param);
 			if (null != regDistinctList && regDistinctList.size() > 0) {
 				for (Map<String, String> mp : regDistinctList) {
 					areaMap.put(mp.get("id"), mp.get("name"));
 				}
 			}
 		}
 		return areaMap;
 	}

	public String getCustIdByContract(String contractNo) {
		return dao.getCustIdByContract(contractNo);
	}

	public String checkIsOpenAccount(String applyNo) {
		List<String> checkApproveUnionIds = checkApproveUnionService.getCustIdByApplyNo(applyNo);//担保人
		for (String custId : checkApproveUnionIds) {
			int i=0;
			//人  有合同的担保人开户
			List<String> infoList = dao.checkInfoAccount(custId,applyNo);
			for (String string : infoList) {
				if(StringUtils.isNoneBlank(string)) {
					i=1;
					break;
				}
			}
			//企业 有合同的担保企业开户
			List<String> companyList = creGedAccountCompanyService.checkCompanyAccount(custId,applyNo);
			for (String string : companyList) {
				if(StringUtils.isNoneBlank(string)) {
					i=1;
					break;
				}
			}
			if(i==0) {
				return "担保人未开户，请保证每个借款企业至少有一个担保自然人已开户";
			}
			
		}
		//有合同的企业开户
		List<String> batchList = creGedAccountCompanyService.checkBatchIsOpenAccount(applyNo);//批量借款企业
		//List<String> danBaoCompanyList = creGedAccountCompanyService.checkDanBaoIsOpenAccount(applyNo);//担保公司
		//batchList.addAll(danBaoCompanyList);
		for (String string : batchList) {
			if(StringUtils.isBlank(string)) {
				return "借款企业未开户，请尽快通知借款企业开通资金存管账户";
			}
		}
		return null;
	}

	public GedAccount getByCustID(String custId) {
		return dao.getByCustID(custId);
	}

	public GedAccount getSingleByCustID(String custId) {
		return dao.getSingleByCustID(custId);
	}

	public String getLHCustIdByContract(String contractNo) {
		return dao.getLHCustIdByContract(contractNo);
	}

	public GedAccount getFarenByCustID(String custId) {
		return dao.getFarenByCustID(custId);
	}

	public String getByBankNo(String recBankcardNo) {
		return dao.getByBankNo(recBankcardNo);
	}

    public GedAccount getByApplyNo(String applyNo) {
		return  dao.getByApplyNo(applyNo);
    }

	public String getUnderCustIdByContract(String contractNo) {
		return dao.getUnderCustIdByContract(contractNo);
	}

	public GedAccount getByPhone(String mobileNum) {
		return dao.getByPhone(mobileNum);
	}

	public GedAccount getByCustPhoneID(String approId) {
		return dao.getByCustPhoneID(approId);
	}

	public GedAccount getByComCredit(String applyNo) {
		return dao.getByComCredit(applyNo);
	}

	public GedAccount queryByBankNo(String recBankcardNo) {
		return dao.queryByBankNo(recBankcardNo);
	}

	public GedAccount findGedAccountByPhoneNum(String corporationMobile) {
		return dao.findGedAccountByPhoneNum(corporationMobile);
	}

	public String getCustByCompany(String contractNo){
		return dao.getCustByCompany(contractNo);
	}


}