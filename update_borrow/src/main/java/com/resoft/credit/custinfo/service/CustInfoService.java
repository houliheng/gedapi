package com.resoft.credit.custinfo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.resoft.credit.guranteeCompanyRelation.entity.CreApplyCompanyRelation;
import com.resoft.credit.guranteeCompanyRelation.service.CreApplyCompanyRelationService;
import com.resoft.outinterface.SV.client2.ResidenceField;
import com.resoft.outinterface.SV.client2.WorkField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.custWorkInfo.dao.CustWorkInfoDao;
import com.resoft.credit.custWorkInfo.entity.CustWorkInfo;
import com.resoft.credit.custinfo.dao.CustInfoDao;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * @reqno:H1601220021
 * @date-designer:2016年1月28日-gaofeng
 * @date-author:2016年1月28日-gaofeng:客户基本信息Service
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class CustInfoService extends CrudService<CustInfoDao, CustInfo> {

	@Autowired
	private CustWorkInfoDao custWorkInfoDao;

	@Autowired
	private ApplyRelationService applyRelationService;

	@Autowired
	private CustInfoDao custInfoDao;

	@Autowired
	private CreApplyCompanyRelationService creApplyCompanyRelationService;
	public CustInfo get(String id) {
		return super.get(id);
	}

	public List<CustInfo> findList(CustInfo custInfo) {
		return super.findList(custInfo);
	}

	public Page<CustInfo> findPage(Page<CustInfo> page, CustInfo custInfo) {
		return super.findPage(page, custInfo);
	}

	/**
	 * 保存主借人相关人员信息
	 */
	@Transactional(value = "CRE", readOnly = false)
	public String saveMainCust(CustInfo custInfo, String currApplyNo, AjaxView ajaxView) throws Exception {
		if("".equals(custInfo.getPersonIdEndDate())) {
			custInfo.setPersonIdEndDate(null);
		}
		super.save(custInfo);
		// 同时新增关系表数据
		custInfo.setSvFlag("1");
		String relationId = saveRelation(custInfo, currApplyNo, null, Constants.ROLE_TYPE_ZJR, null);
		if (!(Constants.WED_STATUS_YH.equals(custInfo.getWedStatus()))) {
			Map<String, String> params = Maps.newHashMap();
			params.put("applyNo", currApplyNo);
			params.put("roleType", Constants.ROLE_TYPE_MATE);
			ApplyRelation applyRelation = applyRelationService.findApplyRelationByApplyNo(params);
			if (!(StringUtils.isNull(applyRelation))) {
				params.put("custId", applyRelation.getCustId());
				params.put("roleType", "");
				params.put("delFlag", "1");
				params.put("mateToGuarantor", "0");
				applyRelationService.updateApplyRelationByApplyNoAndCustId(params);
			}
		}
		return relationId;
	}

	/**
	 * 保存除主借人配偶相关人员信息
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void saveMateCust(CustInfo custInfo, String currApplyNo, String relationForApply, String roleType) throws Exception {
		// 根据身份证号判断是否已存在该客户信息，存在时传入id以更新客户信息，避免多次保存
		CustInfo oldCustInfo = getIdNum(custInfo.getIdNum());
		if (null != oldCustInfo) {
			custInfo.setId(oldCustInfo.getId());
		}
		if("".equals(custInfo.getPersonIdEndDate())) {
			custInfo.setPersonIdEndDate(null);
		}
		super.save(custInfo);
		saveRelation(custInfo, currApplyNo, relationForApply, roleType, null);
		if ("1".equalsIgnoreCase(custInfo.getMateToGuarantor())) {
			custInfo.setMateToGuarantor(null);
			roleType = Constants.ROLE_TYPE_DBR;
			saveRelation(custInfo, currApplyNo, relationForApply, roleType, null);
		} else {
			Map<String, String> params = Maps.newHashMap();
			params.put("applyNo", currApplyNo);
			params.put("custId", custInfo.getId());
			params.put("roleType", Constants.ROLE_TYPE_DBR);
			params.put("delFlag", "1");
			applyRelationService.updateApplyRelationByApplyNoAndCustId(params);
			applyRelationService.updateIsPush(currApplyNo);
		}
	}

	/**
	 * 根据指定合同编号，查询主借人 ，配偶，担保人的身份证号码
	 *
	 * @param applyNo
	 * @return
	 */
	public List<Map<String, String>> getAllCardNoByApplyNo(String applyNo) {
		return this.dao.getAllCardNoByApplyNo(applyNo);
	}

	/**
	 * 根据指定合同编号，查询联系人的所有电话号码
	 *
	 * @param applyNo
	 * @return
	 */
	public List<Map<String, String>> getAllMobileByApplyNo(String applyNo) {
		return this.dao.getAllMobileByApplyNo(applyNo);
	}

	/**
	 * 保存关系
	 */
	public String saveRelation(CustInfo custInfo, String applyNo, String relationForApply, String roleType, String oldRoleType) throws Exception {
		Map<String, String> map = Maps.newConcurrentMap();
		map.put("custId", custInfo.getId());
		map.put("applyNo", applyNo);
		map.put("roleType", roleType);
		ApplyRelation applyRelation = applyRelationService.findApplyRelationByApplyNo(map);
		if (applyRelation == null) {
			applyRelation = new ApplyRelation();
		}
		applyRelation.setApplyNo(applyNo);
		applyRelation.setCustId(custInfo.getId());
		applyRelation.setCustName(custInfo.getCustName());
		applyRelation.setCustInfo(custInfo);
		applyRelation.setRelationForApply(relationForApply);
		applyRelation.setRoleType(roleType);
		applyRelation.setMateToGuarantor(custInfo.getMateToGuarantor());
		applyRelation.setSvFlag(custInfo.getSvFlag());
		applyRelationService.save(applyRelation);

		return applyRelation.getId();
	}

	/**
	 * 保存共借人信息、担保人信息
	 *
	 * @param custInfo
	 * @param currApplyNo
	 * @return
	 * @throws Exception
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void saveCoCustInfo(CustInfo custInfo, String currApplyNo, String relationForApply, String roleType, CustWorkInfo coCustWorkInfo) throws Exception {
		// 根据身份证号判断是否已存在该客户信息，存在时传入id以更新客户信息，避免多次保存
		CustInfo oldCustInfo = getIdNum(custInfo.getIdNum());
		if (null != oldCustInfo) {
			custInfo.setId(oldCustInfo.getId());
			coCustWorkInfo.setCustId(oldCustInfo.getId());
		}
		if (coCustWorkInfo != null && StringUtils.isNotEmpty(coCustWorkInfo.getCustId())) {
			custInfo.setId(coCustWorkInfo.getCustId());
			custInfo.preUpdate();
			if("".equals(custInfo.getPersonIdEndDate())) {
				custInfo.setPersonIdEndDate(null);
			}
			super.dao.update(custInfo);
		} else {
			custInfo.preInsert();
			super.dao.insert(custInfo);

		}


		// 操作关系表
		saveRelation(custInfo, currApplyNo, relationForApply, roleType, null);
		// 插入担保人工作信息
		// 根据custId判断工作信息是否存在，存在时传入id以更新工作信息，避免多次保存
		if (null != oldCustInfo) {
			Map<String, String> map = Maps.newConcurrentMap();
			map.put("custId", oldCustInfo.getId());
			CustWorkInfo oldCustWorkInfo = custWorkInfoDao.findCustWorkInfoByCustId(map);
			if (null != oldCustWorkInfo) {
				coCustWorkInfo.setId(oldCustWorkInfo.getId());
			}
		}
		if ((coCustWorkInfo != null && StringUtils.isNotEmpty(coCustWorkInfo.getId()))) {
			coCustWorkInfo.preUpdate();
			custWorkInfoDao.update(coCustWorkInfo);
		} else {
			coCustWorkInfo.preInsert();
			custWorkInfoDao.insert(coCustWorkInfo);
		}

		/*
		 * if (coCustWorkInfo != null &&
		 * StringUtils.isNotEmpty(coCustWorkInfo.getCustId())) { Map<String,
		 * String> map = Maps.newConcurrentMap(); map.put("custId",
		 * coCustWorkInfo.getCustId()); CustWorkInfo custWorkInfo =
		 * custWorkInfoDao.findCustWorkInfoByCustId(map); String custWorkInfoId
		 * = custWorkInfo.getId(); coCustWorkInfo.setId(custWorkInfoId);
		 * coCustWorkInfo.preUpdate(); custWorkInfoDao.update(coCustWorkInfo); }
		 * else { coCustWorkInfo.preInsert();
		 * custWorkInfoDao.insert(coCustWorkInfo); }
		 */
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(CustInfo custInfo) {
		super.delete(custInfo);
	}

	/**
	 * 根据身份证号查询客户基本信息
	 *
	 * @return CustInfo
	 */
	public CustInfo getIdNum(String idNum) {
		return super.dao.getIdNum(idNum);
	}

	/**
	 * 根据证件类型、证件号、手机号、角色类型查询客户信息（关联relation表查询）
	 *
	 * @param map
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> findCustInfoList(Map<String, String> map) {
		return super.dao.findCustInfoList(map);
	};

	/**
	 * 根据证件类型、证件号查询客户信息（单表查询）
	 *
	 * @param map
	 * @return List<Map<String, String>>
	 */
	public CustInfo findCustInfoByIdCard(Map<String, String> map) {
		return super.dao.findCustInfoByIdCard(map);
	};

	/**
	 * 根据客户登记信息更新客户基本信息表信息
	 *
	 * @param custInfo
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void updateByApplyRegister(CustInfo custInfo) {
		super.dao.updateByApplyRegister(custInfo);
	};

	/**
	 * 根据证件类型、证件号、手机号确定客户是否为给定角色类型
	 *
	 * @param map
	 * @return boolean
	 */
	public boolean isTheRoleType(Map<String, String> map) {
		List<Map<String, String>> custInfoList = this.findCustInfoList(map);
		if (custInfoList != null && custInfoList.size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 根据主借人查询共借人列表
	 *
	 * @param custInfo
	 * @return
	 */
	public List<CustInfo> findCoCustInfoList(CustInfo custInfo) {
		return super.dao.findCoCustInfoList(custInfo);
	}

	/**
	 * 批量删除客户信息
	 *
	 * @param idList
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void banchDelete(List<String> idList, String applyNo, String roleType) {
		if (null != idList && idList.size() > 0) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("idList", idList);
			param.put("applyNo", applyNo);
			param.put("roleType", roleType);
			super.dao.banchDelete(param);
		}
	}
	
	@Transactional(value = "CRE", readOnly = false)
	public void newBanchDelete(List<String> idList, String applyNo, String roleType) {
		if (null != idList && idList.size() > 0) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("idList", idList);
			param.put("applyNo", applyNo);
			param.put("roleType", roleType);
			super.dao.newBanchDelete(param);
		}
	}

	public List<CustInfo> findMainBorrowerByApplyNo(String applyNo) {
		return super.dao.findMainBorrowerByApplyNo(applyNo);
	}

	/**
	 * 根据身份证号查询客户信息
	 *
	 * @param card
	 * @return
	 */
	public CustInfo getInfoByCard(String card) {
		return custInfoDao.getInfoByCard(card);
	}

	public List<CustInfo> findGedGuaranteeInfo(Map<String, Object> params) {
		return dao.findGedGuaranteeInfo(params);
	}

	/**
	 * 保存担保人 增加一条批量借款企业与担保人的关联关系表
	 */
	@Transactional(value = "CRE", readOnly = false)
	 public void saveCustInfoAndApplyCompanyRelation(CustInfo custInfo, String applyNo,String piliId){
         super.save(custInfo);
		CreApplyCompanyRelation relation = new CreApplyCompanyRelation();
		relation.setApplyNo(applyNo);
		relation.setCompanyId(piliId); // 这是批量借款企业id
		relation.setCustId(custInfo.getId());  // 这是担保** 的id
		relation.setRoleType("1"); //担保公司
		creApplyCompanyRelationService.save(relation);   //保存批量借款企业与担保人的关系
	}




	public List<CustInfo> findThisCompanyGuarantor(String applyNo, String companyId) {
		return dao.findThisCompanyGuarantor(applyNo,companyId);
	}

	/**
	 * 保存共借人信息、担保人信息
	 *
	 * @param custInfo
	 * @param currApplyNo
	 * @return
	 * @throws Exception
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void saveCustInfo(CustInfo custInfo, String currApplyNo, String relationForApply, String roleType, CustWorkInfo coCustWorkInfo) throws Exception {
		// 根据身份证号判断是否已存在该客户信息，存在时传入id以更新客户信息，避免多次保存
		CustInfo oldCustInfo = getIdNum(custInfo.getIdNum());
		if (null != oldCustInfo) {
			custInfo.setId(oldCustInfo.getId());
			coCustWorkInfo.setCustId(oldCustInfo.getId());
		}
		if (coCustWorkInfo != null && StringUtils.isNotEmpty(coCustWorkInfo.getCustId())) {
			custInfo.setId(coCustWorkInfo.getCustId());
			custInfo.preUpdate();
			super.dao.update(custInfo);
		} else {
			custInfo.preInsert();
			super.dao.insert(custInfo);
		}
	}

	public CustInfo getCustByRoleAndApplyNo(String applyNo, String roleType) {
		return dao.getCustByRoleAndApplyNo(applyNo,roleType);
	}

    public List<WorkField> getMainWork(String applyNo) {
		return dao.getMainWork(applyNo);
    }
    public List<WorkField> getBatchWork(String applyNo) {
		return dao.getBatchWork(applyNo);
    }

	public List<ResidenceField> getMainRealAddress(String applyNo) {
		return dao.getMainRealAddress(applyNo);
	}
	/*@Transactional(value = "CRE", readOnly = false)
	public void saveRegisterType(String custId, String registerType) {
		dao.saveRegisterType(custId,registerType);
	}*/

}