package com.resoft.credit.companyInfo.service;

import com.resoft.credit.applyRelation.dao.ApplyRelationDao;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.companyInfo.dao.CompanyInfoDao;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.credit.guaranteeRelation.entity.GuaranteeRelation;
import com.resoft.credit.guranteeCompanyRelation.dao.CreApplyCompanyRelationDao;
import com.resoft.credit.guranteeCompanyRelation.entity.CreApplyCompanyRelation;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterRequest;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterResponse;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业基本信息Service
 *
 * @author caoyinglong
 * @version 2016-02-27
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class CompanyInfoService extends CrudService<CompanyInfoDao, CompanyInfo> {

    @Autowired
    private ApplyRelationService applyRelationService;

    @Autowired
    private ApplyRelationDao applyRelationDao;


    @Autowired
    private CustInfoService custInfoService;

    @Autowired
    private CreApplyCompanyRelationDao creApplyCompanyRelationDao;

    public CompanyInfo get(String id) {
        return super.get(id);
    }

    public List<CompanyInfo> findList(CompanyInfo companyInfo) {
        return super.findList(companyInfo);
    }

    public Page<CompanyInfo> findPage(Page<CompanyInfo> page, CompanyInfo companyInfo) {
        return super.findPage(page, companyInfo);
    }

    @Transactional(value = "CRE", readOnly = false)
    public void save(CompanyInfo companyInfo) {
        super.save(companyInfo);
    }

    @Transactional(value = "CRE", readOnly = false)
    public void delete(CompanyInfo companyInfo) {
        super.delete(companyInfo);
    }

    public CompanyInfo getByApplyNo(String applyNo) {
        return super.dao.getByApplyNo(applyNo);
    }

    @Transactional(value = "CRE", readOnly = false)
    public void update(CompanyInfo companyInfo) {
        super.dao.update(companyInfo);
        //其他操作，后期补上
    }

    public List<CompanyInfo> findListByParams(Map<String, Object> params) {
        return super.dao.findListByParams(params);
    }

    public List<CompanyInfo> findGedListByParams(Map<String, Object> params) {
        return super.dao.findGedListByParams(params);
    }

    @Transactional(value = "CRE", readOnly = false)
    public void saveCompanyInfo(CompanyInfo companyInfo, String roleType) throws Exception {
        String sv_flag = companyInfo.getSvFlag();
        if (companyInfo != null && StringUtils.isNotEmpty(companyInfo.getId())) {
            companyInfo.preUpdate();
            super.dao.update(companyInfo);
        } else {
            companyInfo.preInsert();
            super.dao.insert(companyInfo);
        }
        String applyNo = companyInfo.getCurrApplyNo();
        String custId = companyInfo.getId();
        String custName = companyInfo.getBusiRegName();
        ApplyRelation applyRelation = new ApplyRelation();
        applyRelation.setApplyNo(applyNo);
        applyRelation.setRoleType(roleType);
        applyRelation.setCustId(companyInfo.getId());
        List<ApplyRelation> applyRelationList = applyRelationService.findList(applyRelation);
        if (applyRelationList != null && applyRelationList.size() == 1) {
            applyRelation = applyRelationList.get(0);
            applyRelation.setApplyNo(applyNo);
            applyRelation.setCompanyInfo(companyInfo);
            applyRelation.setCustId(custId);
            applyRelation.setCustName(custName);
            applyRelation.setRoleType(roleType);
            applyRelation.setSvFlag(sv_flag);
            applyRelation.preUpdate();
            applyRelationDao.update(applyRelation);
        } else {
            applyRelation = new ApplyRelation();
            applyRelation.setApplyNo(applyNo);
            applyRelation.setCompanyInfo(companyInfo);
            applyRelation.setCustId(custId);
            applyRelation.setCustName(custName);
            applyRelation.setRoleType(roleType);
            applyRelation.setSvFlag(sv_flag);
            applyRelation.preInsert();
            applyRelationDao.insert(applyRelation);
        }
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

    /**
     * 关联关系表 根据applyNo 查询某个企业的信息
     *
     * @param param1
     * @return
     */
    public CompanyInfo getCompanyInfoByApplyNoAndCustId(Map<String, String> param1) {
        return this.dao.getCompanyInfoByApplyNoAndCustId(param1);
    }

    @Transactional(value = "CRE", readOnly = false)
    public List<CompanyInfo> findGEDRegisterAccount(List<CompanyInfo> companyInfoList, String applyNo) {
        for (CompanyInfo companyInfo : companyInfoList) {
            //0借款人(需要社会统一代码)1自有担保人2自有担保企业3合作企业
			/*String roleType = companyInfo.getRoleType();
			String userRole = null;
			if("4".equals(roleType)||"3".equals(roleType)||"7".equals(roleType)) {
				userRole = "1";
			}else {//对象都是企业
*/
            String userRole = "2";
            //}
            FindIsRegisterRequest findIsRegister = new FindIsRegisterRequest("1", companyInfo.getUnSocCreditNo());
            findIsRegister.setUserRole(userRole);
            findIsRegister.setMobile(companyInfo.getCorporationMobile());
            FindIsRegisterResponse findIsRegisterResponse = Facade.facade.findGedRegisterInterface(findIsRegister, applyNo);
            if (findIsRegisterResponse != null) {
                //String data =findIsRegisterResponse.getData().get//电话号码
                String data = null;
                if (findIsRegisterResponse.getData() != null) {
                    data = findIsRegisterResponse.getData().getMobile();
                    companyInfo.setGedAccount(data);
                }
            }
        }
        return companyInfoList;
    }

    @Transactional(value = "CRE", readOnly = false)
    public List<GuaranteeRelation> guaranteeGEDRegisterAccount(List<GuaranteeRelation> guaranteeRelations, String applyNo, String flag) {
        for (GuaranteeRelation guaranteeRelation : guaranteeRelations) {
            FindIsRegisterRequest findIsRegister = null;
            //0借款人(需要社会统一代码)1自有担保人2自有担保企业3合作企业
            if ("1".equals(guaranteeRelation.getRoleType())) {//人
                CustInfo custInfo = custInfoService.get(guaranteeRelation.getCustId());
                String roleType = applyRelationService.getRoleType(applyNo, custInfo.getIdNum());
                findIsRegister = new FindIsRegisterRequest();
                findIsRegister.setMobile(custInfo.getMobileNum());
                if ("3".equals(roleType)) {//主借人的担保人
                    findIsRegister.setUserRole("1");
                } else if ("1".equals(roleType)) {//主借人
                    findIsRegister.setUserRole("0");
                    findIsRegister.setType("1");
                    String credit = dao.getCredit(applyNo, "5");
                    findIsRegister.setCode(credit);
                } else {//为空就是批量借款企业的担保人
                    findIsRegister.setUserRole("1");
                }
            } else if ("2".equals(guaranteeRelation.getRoleType())) {
                CompanyInfo companyInfo = dao.get(guaranteeRelation.getCustId());
                findIsRegister = new FindIsRegisterRequest("1", guaranteeRelation.getIdNum());
                findIsRegister.setMobile(companyInfo.getCorporationMobile());
                findIsRegister.setUserRole("2");
            }
            //担保公司不显示
            FindIsRegisterResponse findIsRegisterResponse = Facade.facade.findGedRegisterInterface(findIsRegister, applyNo);
            if (findIsRegisterResponse != null) {
                String data = null;
                if (findIsRegisterResponse.getData() != null) {
                    data = findIsRegisterResponse.getData().getMobile();
                    guaranteeRelation.setGedAccount(data);
                }
            }
        }
        return guaranteeRelations;
    }

    public String getRoleIsMainOrBatch(String applyNo, String companyId) {
        // TODO Auto-generated method stub
        return dao.getRoleIsMainOrBatch(applyNo, companyId);
    }

    public List<CompanyInfo> findBatchGuarantee(String applyNo, String companyId) {
        return dao.findBatchGuarantee(applyNo, companyId);
    }

    public List<CompanyInfo> findGedDanBaoListByParams(Map<String, Object> params) {
        return super.dao.findGedDanBaoListByParams(params);
    }

    public String getMainCodeByApply(String applyNo) {
        return dao.getMainCodeByApply(applyNo);
    }

    public List<CompanyInfo> findGuaranteeListByParams(Map<String, Object> params) {
        return super.dao.findGuaranteeListByParams(params);
    }

    /*
     * 批量借款企业的担保企业保存
     * */
    @Transactional(value = "CRE", readOnly = false)
    public void saveBatchCompanyInfo(CompanyInfo companyInfo, String guaranteeCompanyId) {
        String sv_flag = companyInfo.getSvFlag();
        if (companyInfo != null && StringUtils.isNotEmpty(companyInfo.getId())) {
            companyInfo.preUpdate();
            super.dao.update(companyInfo);
        } else {
            companyInfo.preInsert();
            super.dao.insert(companyInfo);
        }
        String applyNo = companyInfo.getCurrApplyNo();
        String custId = companyInfo.getId();

        List<CreApplyCompanyRelation> applyCompanyRelationList = creApplyCompanyRelationDao.findHasRelation(applyNo, guaranteeCompanyId, custId);
        if (applyCompanyRelationList.size() == 0) {
            CreApplyCompanyRelation creApplyCompanyRelation = new CreApplyCompanyRelation();
            creApplyCompanyRelation.setApplyNo(applyNo);
            creApplyCompanyRelation.setCompanyId(guaranteeCompanyId);
            creApplyCompanyRelation.setCustId(custId);
            creApplyCompanyRelation.setRoleType("2");
            creApplyCompanyRelation.preInsert();
            creApplyCompanyRelationDao.insert(creApplyCompanyRelation);
        }
    }

    @Transactional(value = "CRE", readOnly = false)
    public void banchGuaranteeDelete(List<String> idList, String applyNo, String guaranteeCompanyId) {
        if (null != idList && idList.size() > 0) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("idList", idList);
            param.put("applyNo", applyNo);
            param.put("guaranteeCompanyId", guaranteeCompanyId);
            super.dao.banchGuaranteeDelete(param);
        }

    }
	/*@Transactional(value="CRE",readOnly=false)
	public void saveRegisterType(String flag, String id) {
		dao.saveRegisterType(flag,id);
	}*/

	public CompanyInfo getCompanyNameByApproId(String approId) {
		return dao.getCompanyNameByApproId(approId);
	}

	public CompanyInfo getByApplyNoAndRoleType(Map<String, Object> params) {
	    return dao.getByApplyNoAndRoleType(params);
    }

	//根据主键添加企业的社会统一新员工代码
    @Transactional(value = "CRE",readOnly = false)
    public void InsertunSocCreditNo(CompanyInfo companyInfo){
      dao.InsertunSocCreditNo(companyInfo);
    }

}