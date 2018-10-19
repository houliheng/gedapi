package com.resoft.credit.mortageEquipmentUnion.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.mortageEquipmentUnion.dao.MortageEquipmentUnionDao;
import com.resoft.credit.mortageEquipmentUnion.entity.MortageEquipmentUnion;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 联合授信设备抵押Service
 * @author wangguodong
 * @version 2016-12-26
 */
@Service
@Transactional(readOnly = true)
public class MortageEquipmentUnionService extends CrudService<MortageEquipmentUnionDao, MortageEquipmentUnion> {

	public MortageEquipmentUnion get(String id) {
		return super.get(id);
	}
	
	public List<MortageEquipmentUnion> findList(MortageEquipmentUnion mortageEquipmentUnion) {
		return super.findList(mortageEquipmentUnion);
	}
	
	public Page<MortageEquipmentUnion> findPage(Page<MortageEquipmentUnion> page, MortageEquipmentUnion mortageEquipmentUnion) {
		return super.findPage(page, mortageEquipmentUnion);
	}
	
	@Transactional(readOnly = false)
	public void save(MortageEquipmentUnion mortageEquipmentUnion) {
		super.save(mortageEquipmentUnion);
	}
	
	@Transactional(readOnly = false)
	public void delete(MortageEquipmentUnion mortageEquipmentUnion) {
		super.delete(mortageEquipmentUnion);
	}
	
	public List<MortageEquipmentUnion> getPageByApplyNo(Map<String,String> params){
		return super.dao.getPageByApplyNo(params);
	}
}