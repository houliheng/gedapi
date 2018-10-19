package com.resoft.postloan.mortgageHouseInfo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.postloan.mortgageHouseInfo.dao.MortgageHouseInfoDao;
import com.resoft.postloan.mortgageHouseInfo.entity.MortgageHouseInfo;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 房产抵质押物Service
 * @author yanwanmei
 * @version 2016-02-29
 */
@Service("plMortgageHouseInfoService")
@DbType("pl.dbType")
@Transactional(value="PL",readOnly = true)
public class MortgageHouseInfoService extends CrudService<MortgageHouseInfoDao, MortgageHouseInfo> {

	public MortgageHouseInfo get(String id) {
		return super.get(id);
	}
	
	public List<MortgageHouseInfo> findList(MortgageHouseInfo mortgageHouseInfo) {
		return super.findList(mortgageHouseInfo);
	}
	
	public Page<MortgageHouseInfo> findPage(Page<MortgageHouseInfo> page, MortgageHouseInfo mortgageHouseInfo) {
		return super.findPage(page, mortgageHouseInfo);
	}
	
	@Transactional(value="PL",readOnly = false)
	public void save(MortgageHouseInfo mortgageHouseInfo) {
		super.save(mortgageHouseInfo);
	}
	
	@Transactional(value="PL",readOnly = false)
	public void delete(MortgageHouseInfo mortgageHouseInfo) {
		super.delete(mortgageHouseInfo);
	}
	
	@Transactional(value="PL",readOnly=false)
	public void batchDelete(List<String> idList){
		if(null!=idList && idList.size()>0){
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("idList", idList);
			super.dao.batchDelete(param);
		}
	}
	
	public List<MortgageHouseInfo> getPageByApplyNo(String applyNo){
		return super.dao.getPageByApplyNo(applyNo);
	}
}