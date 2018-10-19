package com.resoft.credit.applyInfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyInfo.dao.ApplyInfoDao;
import com.resoft.credit.applyInfo.entity.ApplyInfo;
import com.resoft.credit.applyRelation.dao.ApplyRelationDao;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterRequest;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterResponse;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 申请借款信息表Service
 * 
 * @author chenshaojia
 * @version 2016-03-01
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class ApplyInfoService extends CrudService<ApplyInfoDao, ApplyInfo> {

	@Autowired
	private ApplyRelationDao applyRelationDao;// 关系表dao

	public ApplyInfo get(String id) {
		return super.get(id);
	}

	public List<ApplyInfo> findList(ApplyInfo applyInfo) {
		return super.findList(applyInfo);
	}

	public Page<ApplyInfo> findPage(Page<ApplyInfo> page, ApplyInfo applyInfo) {
		return super.findPage(page, applyInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(ApplyInfo applyInfo) {
		List <ApplyInfo> checkApplyInfo = checkDoubleInfo(applyInfo);
		if(checkApplyInfo.size()==0){
			applyInfo.preInsert();
			dao.insert(applyInfo);
		}else{
			applyInfo.preUpdate();
			dao.update(applyInfo);
		}
	}

	private List<ApplyInfo> checkDoubleInfo(ApplyInfo applyInfo) {
		return dao.checkDoubleInfo(applyInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void saveApplyInfo(ApplyInfo applyInfo) {
		save(applyInfo);
		if("0".equals(applyInfo.getIsHaveAssure())){
			Map<String, String> params = Maps.newHashMap();
			params.put("applyNo", applyInfo.getApplyNo());
			params.put("roleType", Constants.ROLE_TYPE_MATE);
			ApplyRelation applyRelation = applyRelationDao.findApplyRelationByApplyNo(params);
			if (!(StringUtils.isNull(applyRelation))) {
				params.put("custId", applyRelation.getCustId());
				params.put("roleType", "");
				params.put("delFlag", "1");
				params.put("mateToGuarantor", "0");
				applyRelationDao.updateApplyRelationByApplyNoAndCustId(params);
			}
		}
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(ApplyInfo applyInfo) {
		super.delete(applyInfo);
	}

	public ApplyInfo findApplyInfoByApplyNo(String applyNo) {
		return super.dao.findApplyInfoByApplyNo(applyNo);
	}

	/**
	 * 批复信息保存后，更新借款申请信息中的合同金额
	 * 
	 * @param applyInfo
	 * @throws Exception
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void updateContractAmount(ApplyInfo applyInfo) throws Exception {
		super.dao.updateContractAmount(applyInfo);
	}

	public Map<String, String> findGEDNeedCom(Map<String, String> param) {
		return super.dao.findGEDNeedCom(param);
	}

	public Map<String, String> findGEDNeedInfo(Map<String, String> param) {
		return super.dao.findGEDNeedInfo(param);
	}
	@Transactional(value = "CRE", readOnly = false)
	public String checkIsRegisterGed(String applyNo) {
		 Map<String, String> param = Maps.newConcurrentMap();
		   param.put("applyNo",applyNo);
		   Map<String, String> resultCom = findGEDNeedCom(param);
		   Map<String, String> resultInfo =findGEDNeedInfo(param);
		   if(resultCom==null){
			   return "请填写主借人和主借企业！";
		   }else{
			    String unSocCreditNo = resultCom.get("unSocCreditNo");
			    FindIsRegisterRequest findIsRegister=new FindIsRegisterRequest("1",unSocCreditNo);
			    findIsRegister.setMobile(resultInfo.get("mobileNum"));
				findIsRegister.setUserRole("0");//借款人
			    FindIsRegisterResponse findIsRegisterResponse = Facade.facade.findGedRegisterInterface(findIsRegister, applyNo);
			    if(findIsRegisterResponse==null){
			    	return "同冠易贷连接异常";
			    }
			    String code = findIsRegisterResponse.getCode();
			    if("0".equals(code)){//冠易贷返回异常信息，没有注册
			    	return "请先注册冠易贷账号！！";
			    }else {
			    	return "";
			    }
		   }
	}
}