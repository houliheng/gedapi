package com.resoft.credit.gedApplyRegister.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.gedApplyRegister.dao.GedApplyRegisterDao;
import com.resoft.credit.gedApplyRegister.entity.GedApplyRegister;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 冠e贷申请客户登记信息表Service
 * 
 * @author wangguodong
 * @version 2017-02-14
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class GedApplyRegisterService extends CrudService<GedApplyRegisterDao, GedApplyRegister> {

	@Autowired
	private ApplyRegisterService applyRegisterService;

	public GedApplyRegister get(String id) {
		return super.get(id);
	}

	public List<GedApplyRegister> findList(GedApplyRegister gedApplyRegister) {
		return super.findList(gedApplyRegister);
	}

	public Page<GedApplyRegister> findPage(Page<GedApplyRegister> page, GedApplyRegister gedApplyRegister) {
		return super.findPage(page, gedApplyRegister);
	}

	public List<GedApplyRegister> findgedGedApplyRegisters(GedApplyRegister gedApplyRegister ){
		return this.dao.findgedGedApplyRegisters(gedApplyRegister);
	}
	
	@Transactional(value = "CRE", readOnly = false)
	public void save(GedApplyRegister gedApplyRegister) {
		super.save(gedApplyRegister);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(GedApplyRegister gedApplyRegister) {
		super.delete(gedApplyRegister);
	}

	/**
	 * 查询指定角色下的所有用户
	 */
	@Transactional(value = "CRE", readOnly = false)
	public List<Map<String, Object>> getUserToAllot(Map<String, Object> params) {
		return this.dao.getUserToAllot(params);
	}

	/**
	 * 更改分配状态
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void updateAllotStatus(Map<String, Object> params) {
		this.dao.updateAllotStatus(params);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void allotTask(GedApplyRegister gedApplyRegister, String loginName, String userId, String companyId) {
		gedApplyRegister.setAllotStatus("2");
		save(gedApplyRegister);
		ApplyRegister applyRegister = new ApplyRegister();
		applyRegister.setApplyNo("");
		applyRegister.setCreateId(userId);
		applyRegister.setUpdateId(userId);
		applyRegister.setIftApplyId(gedApplyRegister.getApplyId());
		applyRegister.setCustName(gedApplyRegister.getCustName());
		applyRegister.setApplyAmount(gedApplyRegister.getApplyAmount());
		applyRegister.setIdType(gedApplyRegister.getIdType());
		applyRegister.setIdNum(gedApplyRegister.getIdNum());
		applyRegister.setApplyStatus(Constants.APPLY_STATUS_SAVE);
		applyRegister.setMobileNum(gedApplyRegister.getMobileNum());
		applyRegister.setRegisterDate(gedApplyRegister.getRegisterDate());
		applyRegister.setRegisterName(loginName);
		applyRegister.setChannelSourceType(gedApplyRegister.getChannelSourceType());
		Office office = new Office();
		office.setId(companyId);
		applyRegister.setCompany(office);
		applyRegister.preInsert();
		applyRegisterService.saveApplyRegisterFromGedApplyRegister(applyRegister);
	}

	/**
	 * 根据applyId查询applyRegister
	 */
	public ApplyRegister getApplyRegisterByIFTApplyId(String iftApplyId){
		return this.dao.getApplyRegisterByIFTApplyId(iftApplyId);
	}
	
	
	/**
	 * 根据applyId查询GEDapplyRegister
	 */
	public GedApplyRegister getGedApplyRegisterByApplyId(String iftApplyId){
		return this.dao.getGedApplyRegisterByApplyId(iftApplyId);
	}
	
}