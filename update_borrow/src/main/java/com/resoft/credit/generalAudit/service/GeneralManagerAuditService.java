package com.resoft.credit.generalAudit.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.generalAudit.dao.GeneralManagerAuditDao;
import com.resoft.credit.generalAudit.entity.GeneralManagerAudit;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 总公司总经理审核条件配置Service
 * @author chenshaojia
 * @version 2016-04-08
 */
@Service
@DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class GeneralManagerAuditService extends CrudService<GeneralManagerAuditDao, GeneralManagerAudit> {

	private Logger logger = LoggerFactory.getLogger(GeneralManagerAuditService.class);
	
	public GeneralManagerAudit get(String id) {
		return super.get(id);
	}
	
	public List<GeneralManagerAudit> findList(GeneralManagerAudit generalManagerAudit) {
		return super.findList(generalManagerAudit);
	}
	
	public Page<GeneralManagerAudit> findPage(Page<GeneralManagerAudit> page, GeneralManagerAudit generalManagerAudit) {
		return super.findPage(page, generalManagerAudit);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(GeneralManagerAudit generalManagerAudit) {
		super.save(generalManagerAudit);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(GeneralManagerAudit generalManagerAudit) {
		super.delete(generalManagerAudit);
	}
	/**
	 * 判断是否需要总公司总经理审核
	 * */
	public boolean isGeneralManagerAudit(String contractAmountString,String checkApproveProductType, String applyNo){
		boolean flag = false;
		User currentUser = UserUtils.getUser();
		String officeId = null;
		if (currentUser.getOffice() != null) {
			officeId = currentUser.getOffice().getId();
		}
		List<GeneralManagerAudit> conditionList = super.dao.findConfigCondition(officeId, checkApproveProductType,applyNo);
		if (!conditionList.isEmpty()) {
			GeneralManagerAudit condition = conditionList.get(0);
			BigDecimal contractAmount = new BigDecimal(contractAmountString);
			if(logger.isDebugEnabled()){
				logger.debug("合同金额：" + contractAmount + ";配置金额：" + condition.getContractAmount());
			}
			if (contractAmount.compareTo(condition.getContractAmount()) >= 0) {
				flag = true;
			}
		}
		return flag;
	}
	
}