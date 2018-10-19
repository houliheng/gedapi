package com.resoft.credit.interfaceinfo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.dao.InterfaceInfoDao;

/**
 * 接口日志记录Service
 * @author wanghong
 * @version 2016-10-31
 */
@Service
@Transactional(readOnly = true)
public class InterfaceInfoService extends CrudService<InterfaceInfoDao, InterfaceInfo> {

	public InterfaceInfo get(String id) {
		return super.get(id);
	}
	
	public List<InterfaceInfo> findList(InterfaceInfo interfaceInfo) {
		return super.findList(interfaceInfo);
	}
	
	public Page<InterfaceInfo> findPage(Page<InterfaceInfo> page, InterfaceInfo interfaceInfo) {
		return super.findPage(page, interfaceInfo);
	}
	/**
	 * 保存或者更新接口调用日志信息
	 */
	@Transactional(readOnly = false)
	public void save(InterfaceInfo interfaceInfo) {
		super.save(interfaceInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(InterfaceInfo interfaceInfo) {
		super.delete(interfaceInfo);
	}

	public InterfaceInfo findSVtoThemisResult(InterfaceInfo interfaceInfo) {
		return dao.findSVtoThemisResult(interfaceInfo);
	}

	
}