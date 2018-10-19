package com.resoft.credit.mappingInfo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.mappingInfo.entity.MappingInfo;
import com.resoft.credit.mappingInfo.dao.MappingInfoDao;

/**
 * 合同号映射表Service
 * @author 合同号映射表
 * @version 2016-07-29
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class MappingInfoService extends CrudService<MappingInfoDao, MappingInfo> {

	public MappingInfo get(String id) {
		return super.get(id);
	}
	
	public List<MappingInfo> findList(MappingInfo mappingInfo) {
		return super.findList(mappingInfo);
	}
	
	public Page<MappingInfo> findPage(Page<MappingInfo> page, MappingInfo mappingInfo) {
		return super.findPage(page, mappingInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(MappingInfo mappingInfo) {
		super.save(mappingInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(MappingInfo mappingInfo) {
		super.delete(mappingInfo);
	}
}