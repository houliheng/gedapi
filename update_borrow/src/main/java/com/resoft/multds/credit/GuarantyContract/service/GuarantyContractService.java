package com.resoft.multds.credit.GuarantyContract.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.multds.credit.GuarantyContract.dao.GuarantyContractDao;
import com.resoft.multds.credit.GuarantyContract.entity.GuarantyContract;
import com.resoft.postloan.checkTwentyFiveAllocate.service.CheckTwentyFiveAllocateService;
import com.resoft.postloan.common.utils.Constants;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 根绝角色类型查询人员姓名ListService
 * @author zhaohuakui
 * @version 2016-05-12
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class GuarantyContractService extends CrudService<GuarantyContractDao, GuarantyContract> {
    @Autowired
    private CheckTwentyFiveAllocateService checkTwentyFiveAllocateService;
	public GuarantyContract get(String id) {
		return super.get(id);
	}
	
	public List<GuarantyContract> findList(GuarantyContract guarantyContract) {
		return super.findList(guarantyContract);
	}
	
	public Page<GuarantyContract> findPage(Page<GuarantyContract> page, GuarantyContract guarantyContract) {
		Map<String,String> param = new HashMap<String,String>();
		param.put("contractNo", guarantyContract.getContractNo());
		param.put("checkedType", Constants.SIGN_GUARANTY_CONTRACT);
		checkTwentyFiveAllocateService.updateCheckedTypeByContractNo(param);
		return super.findPage(page, guarantyContract);
	}
	

}