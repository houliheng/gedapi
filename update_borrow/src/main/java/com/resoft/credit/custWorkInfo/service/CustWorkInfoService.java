package com.resoft.credit.custWorkInfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.google.common.collect.Maps;
import com.resoft.credit.custWorkInfo.entity.CustWorkInfo;
import com.resoft.credit.custWorkInfo.dao.CustWorkInfoDao;

/**
 * 客户工作信息表Service
 * @author gaofeng
 * @version 2016-02-25
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CustWorkInfoService extends CrudService<CustWorkInfoDao, CustWorkInfo> {

	public CustWorkInfo get(String id) {
		return super.get(id);
	}
	
	public List<CustWorkInfo> findList(CustWorkInfo custWorkInfo) {
		return super.findList(custWorkInfo);
	}
	
	public Page<CustWorkInfo> findPage(Page<CustWorkInfo> page, CustWorkInfo custWorkInfo) {
		return super.findPage(page, custWorkInfo);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void saveCustWorkInfo(CustWorkInfo custWorkInfo) throws Exception {
		//根据custId判断工作信息是否存在，存在时传入id以更新工作信息，避免多次保存
		Map<String, String> map = Maps.newConcurrentMap();
		map.put("custId", custWorkInfo.getCustId());
		CustWorkInfo oldCustWorkInfo=findCustWorkInfoByCustId(map);
		if(null != oldCustWorkInfo){
			custWorkInfo.setId(oldCustWorkInfo.getId());
		}
		super.save(custWorkInfo);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(CustWorkInfo custWorkInfo) {
		super.delete(custWorkInfo);
	}
	/**
	 * 根据客户ID查询客户工作信息
	 * @return
	 */
	public CustWorkInfo findCustWorkInfoByCustId(Map<String, String> map) {
		return super.dao.findCustWorkInfoByCustId(map);
	}
	
}