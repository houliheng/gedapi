package com.resoft.credit.mortgageCarInfo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.resoft.credit.underCustInfo.entity.UnderCustInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.mortgageCarEvaluateInfo.entity.MortgageCarEvaluateInfo;
import com.resoft.credit.mortgageCarInfo.entity.MortgageCarInfo;
import com.resoft.credit.mortgageCarInfo.entity.gqGetMortgageCarInfo;
import com.resoft.credit.mortgageCarInfo.dao.MortgageCarInfoDao;
import com.resoft.credit.mortgagedperson.entity.MortgagedPerson;

/**
 * 车辆抵质押物信息Service
 * @author yanwanmei
 * @version 2016-02-29
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
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
	
	@Transactional(value="CRE",readOnly = false)
	public void save(MortgageCarInfo mortgageCarInfo) {
		super.save(mortgageCarInfo);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(MortgageCarInfo mortgageCarInfo) {
		super.delete(mortgageCarInfo);
	}
	
	@Transactional(value="CRE",readOnly=false)
	public void batchDelete(List<String> idList){
		if(null!=idList && idList.size()>0){
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("idList", idList);
			super.dao.batchDelete(param);
		}
	}
	
	@Transactional(value="CRE",readOnly=false)
	public MortgageCarEvaluateInfo selectData(String id){
			return super.dao.selectData(id);
	}
	
	public List<MortgageCarInfo> getPageByApplyNo(String applyNo){
		return super.dao.getPageByApplyNo(applyNo);
	}
	
	public List<gqGetMortgageCarInfo> getGqPageByApplyNo(String applyNo){
		return super.dao.getGqPageByApplyNo(applyNo);
	}

	public List<MortgageCarInfo> getCarData(String applyNo) {
		return super.dao.getCarData(applyNo);
	}


	public List<gqGetMortgageCarInfo> getByApplyNo(String applyNo) {

		return dao.getByApplyNo(applyNo);
	}
}