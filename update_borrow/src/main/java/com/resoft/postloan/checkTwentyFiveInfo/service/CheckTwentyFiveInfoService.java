package com.resoft.postloan.checkTwentyFiveInfo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.postloan.check25.service.CheckTwentyFiveService;
import com.resoft.postloan.checkTwentyFiveAllocate.service.CheckTwentyFiveAllocateService;
import com.resoft.postloan.checkTwentyFiveInfo.dao.CheckTwentyFiveInfoDao;
import com.resoft.postloan.checkTwentyFiveInfo.entity.CheckTwentyFiveInfo;
import com.resoft.postloan.checkTwentyFiveInfo.entity.CheckTwentyFiveInfoVO;
import com.resoft.postloan.common.utils.Constants;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 25日复核信息Service
 * 
 * @author admin
 * @version 2016-05-25
 */
@Service
@DbType("pl.dbType")
@Transactional(value = "PL", readOnly = true)
public class CheckTwentyFiveInfoService extends CrudService<CheckTwentyFiveInfoDao, CheckTwentyFiveInfo> {
	private static final Logger logger = LoggerFactory.getLogger(CheckTwentyFiveInfoService.class);

	@Autowired
	private CheckTwentyFiveService checkTwentyFiveService;
	@Autowired
	private CheckTwentyFiveAllocateService checkTwentyFiveAllocateService;

	public CheckTwentyFiveInfo get(String id) {
		return super.get(id);
	}

	public List<CheckTwentyFiveInfo> findList(CheckTwentyFiveInfo checkTwentyFiveInfo) {
		return super.findList(checkTwentyFiveInfo);
	}

	public Page<CheckTwentyFiveInfo> findPage(Page<CheckTwentyFiveInfo> page, CheckTwentyFiveInfo checkTwentyFiveInfo) {
		return super.findPage(page, checkTwentyFiveInfo);
	}

	@Transactional(value = "PL", readOnly = false)
	public void save(CheckTwentyFiveInfo checkTwentyFiveInfo) {
		super.save(checkTwentyFiveInfo);
	}

	@Transactional(value = "PL", readOnly = false)
	public void delete(CheckTwentyFiveInfo checkTwentyFiveInfo) {
		super.delete(checkTwentyFiveInfo);
	}

	public List<CheckTwentyFiveInfo> getCheckTwentyFiveInfoByContractNo(String contractNo) {
		return super.dao.getCheckTwentyFiveInfoByContractNo(contractNo);
	}

	@Transactional(value = "PL", readOnly = false)
	public void saveList(List<CheckTwentyFiveInfo> checkTwentyFiveInfoList) {
		super.dao.saveList(checkTwentyFiveInfoList);
	}

	@Transactional(value = "PL", readOnly = false)
	public String saveCheckTwentyFiveInfo(CheckTwentyFiveInfoVO checkTwentyFiveInfoVO, String allocateId, String contractNo, String procedure) {
		try {
			checkTwentyFiveInfoVO.getCheckTwentyFive().setAllocateId(allocateId);
			checkTwentyFiveInfoVO.getCheckTwentyFive().setContractNo(contractNo);
			checkTwentyFiveInfoVO.getCheckTwentyFive().setCheckedBy(UserUtils.getUser().getName());
			checkTwentyFiveService.save(checkTwentyFiveInfoVO.getCheckTwentyFive());

			List<CheckTwentyFiveInfo> checkTwentyFiveInfoList = new ArrayList<CheckTwentyFiveInfo>();
			// 主借人
			if (checkTwentyFiveInfoVO.getMainBorrower() != null) {
				checkTwentyFiveInfoVO.getMainBorrower().getCheckTwentyFiveInfo().setId(IdGen.uuid());
				checkTwentyFiveInfoVO.getMainBorrower().getCheckTwentyFiveInfo().setAllocateId(allocateId);
				checkTwentyFiveInfoVO.getMainBorrower().getCheckTwentyFiveInfo().setContractNo(contractNo);
				checkTwentyFiveInfoVO.getMainBorrower().getCheckTwentyFiveInfo().setRoleType(Constants.ROLE_TYPE_ZJR);
				checkTwentyFiveInfoList.add(checkTwentyFiveInfoVO.getMainBorrower().getCheckTwentyFiveInfo());
			}
			// 担保人
			if (checkTwentyFiveInfoVO.getGuarantor() != null) {
				checkTwentyFiveInfoVO.getGuarantor().getCheckTwentyFiveInfo().setId(IdGen.uuid());
				checkTwentyFiveInfoVO.getGuarantor().getCheckTwentyFiveInfo().setAllocateId(allocateId);
				checkTwentyFiveInfoVO.getGuarantor().getCheckTwentyFiveInfo().setContractNo(contractNo);
				checkTwentyFiveInfoVO.getGuarantor().getCheckTwentyFiveInfo().setRoleType(Constants.ROLE_TYPE_DBR);
				checkTwentyFiveInfoList.add(checkTwentyFiveInfoVO.getGuarantor().getCheckTwentyFiveInfo());
			}
			// 主借企业
			if (checkTwentyFiveInfoVO.getMainBorrowerCompany() != null) {
				checkTwentyFiveInfoVO.getMainBorrowerCompany().getCheckTwentyFiveInfo().setId(IdGen.uuid());
				checkTwentyFiveInfoVO.getMainBorrowerCompany().getCheckTwentyFiveInfo().setAllocateId(allocateId);
				checkTwentyFiveInfoVO.getMainBorrowerCompany().getCheckTwentyFiveInfo().setContractNo(contractNo);
				checkTwentyFiveInfoVO.getMainBorrowerCompany().getCheckTwentyFiveInfo().setRoleType(Constants.ROLE_TYPE_ZJQY);
				checkTwentyFiveInfoList.add(checkTwentyFiveInfoVO.getMainBorrowerCompany().getCheckTwentyFiveInfo());
			}
			// 担保企业
			if (checkTwentyFiveInfoVO.getGuarantorCompany() != null) {
				checkTwentyFiveInfoVO.getGuarantorCompany().getCheckTwentyFiveInfo().setId(IdGen.uuid());
				checkTwentyFiveInfoVO.getGuarantorCompany().getCheckTwentyFiveInfo().setAllocateId(allocateId);
				checkTwentyFiveInfoVO.getGuarantorCompany().getCheckTwentyFiveInfo().setContractNo(contractNo);
				checkTwentyFiveInfoVO.getGuarantorCompany().getCheckTwentyFiveInfo().setRoleType(Constants.ROLE_TYPE_DBQY);
				checkTwentyFiveInfoList.add(checkTwentyFiveInfoVO.getGuarantorCompany().getCheckTwentyFiveInfo());
			}
			// 合规性
			if (checkTwentyFiveInfoVO.getCompliance() != null) {
				checkTwentyFiveInfoVO.getCompliance().getCheckTwentyFiveInfo().setId(IdGen.uuid());
				checkTwentyFiveInfoVO.getCompliance().getCheckTwentyFiveInfo().setAllocateId(allocateId);
				checkTwentyFiveInfoVO.getCompliance().getCheckTwentyFiveInfo().setContractNo(contractNo);
				checkTwentyFiveInfoVO.getCompliance().getCheckTwentyFiveInfo().setRoleType(Constants.ROLE_TYPE_HGX);
				checkTwentyFiveInfoList.add(checkTwentyFiveInfoVO.getCompliance().getCheckTwentyFiveInfo());
			}
			if (checkTwentyFiveInfoList != null && checkTwentyFiveInfoList.size() > 0) {
				saveList(checkTwentyFiveInfoList);
			}

			if (StringUtils.isEmpty(procedure)) {
				// 修改pl_check_25_allocate的状态由待复核改为已复核
				Map<String, String> param = new HashMap<String, String>();
				param.put("checkedType", Constants.HAS_BEEN_ALLOCATED);
				param.put("contractNo", contractNo);
				checkTwentyFiveAllocateService.updateCheckedTypeByContractNo(param);
			}

			return "true";
		} catch (Exception e) {
			logger.error("保存25日复核信息失败", e);
			return "false";
		}

	}

}