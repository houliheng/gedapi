package com.resoft.credit.gqgetComInfo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.gqgetComInfo.dao.MortageEquipmentDao;
import com.resoft.credit.gqgetComInfo.entity.MortageEquipment;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;


/**
 * 冠E通Service
 * @author yanwanmei
 * @version 2016-08-08
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class MortageEquipmentService extends CrudService<MortageEquipmentDao, MortageEquipment> {

	public MortageEquipment get(String id) {
		return super.get(id);
	}
	
	public List<MortageEquipment> findList(MortageEquipment mortageEquipment) {
		return super.findList(mortageEquipment);
	}
	
	public Page<MortageEquipment> findPage(Page<MortageEquipment> page, MortageEquipment mortageEquipment) {
		return super.findPage(page, mortageEquipment);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(MortageEquipment mortageEquipment) {
		super.save(mortageEquipment);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void updateByApplyNo(MortageEquipment mortageEquipment) {
		super.dao.updateByApplyNo(mortageEquipment);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(MortageEquipment mortageEquipment) {
		super.delete(mortageEquipment);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void insert(MortageEquipment mortageEquipment) {
		super.dao.insert(mortageEquipment);
	}
	
	public List<MortageEquipment> getPageByApplyNo(String applyNo){
		return super.dao.getPageByApplyNo(applyNo);
	}
}