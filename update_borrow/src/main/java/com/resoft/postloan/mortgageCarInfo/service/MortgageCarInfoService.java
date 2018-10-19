package com.resoft.postloan.mortgageCarInfo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.postloan.mortgageCarEvaluateInfo.entity.MortgageCarEvaluateInfo;
import com.resoft.postloan.mortgageCarInfo.dao.MortgageCarInfoDao;
import com.resoft.postloan.mortgageCarInfo.entity.MortgageCarInfo;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 车辆抵质押物信息Service
 * @author yanwanmei
 * @version 2016-02-29
 */
@Service("plMortgageCarInfoService")
@DbType("pl.dbType")
@Transactional(value="PL",readOnly = true)
public class MortgageCarInfoService extends CrudService<MortgageCarInfoDao, MortgageCarInfo> {

	public MortgageCarInfo get(String id) {
		return super.get(id);
	}
	
	public List<MortgageCarInfo> findList(MortgageCarInfo mortgageCarInfo) {
		return super.findList(mortgageCarInfo);
	}
	
	public Page<MortgageCarInfo> findPage(Page<MortgageCarInfo> page, MortgageCarInfo mortgageCarInfo) {
		return super.findPage(page, mortgageCarInfo);
	}
	
	@Transactional(value="PL",readOnly = false)
	public void save(MortgageCarInfo mortgageCarInfo) {
		super.save(mortgageCarInfo);
	}
	
	@Transactional(value="PL",readOnly = false)
	public void delete(MortgageCarInfo mortgageCarInfo) {
		super.delete(mortgageCarInfo);
	}
	
	@Transactional(value="PL",readOnly=false)
	public void batchDelete(List<String> idList){
		if(null!=idList && idList.size()>0){
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("idList", idList);
			super.dao.batchDelete(param);
		}
	}
	
	@Transactional(value="PL",readOnly=false)
	public MortgageCarEvaluateInfo selectData(String id){
			return super.dao.selectData(id);
	}
	
	public List<MortgageCarInfo> getPageByApplyNo(String applyNo){
		return super.dao.getPageByApplyNo(applyNo);
	}
	
	
}