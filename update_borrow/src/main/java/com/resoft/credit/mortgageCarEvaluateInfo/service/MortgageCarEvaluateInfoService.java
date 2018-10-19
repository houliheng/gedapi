package com.resoft.credit.mortgageCarEvaluateInfo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.mortgageCarEvaluateInfo.entity.MortgageCarEvaluateInfo;
import com.resoft.credit.mortgageCarEvaluateInfo.dao.MortgageCarEvaluateInfoDao;
import com.resoft.credit.mortgageCarInfo.entity.MortgageCarInfo;

/**
 * 分公司风控审核-抵质押物信息Service
 * @author yanwanmei
 * @version 2016-03-01
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class MortgageCarEvaluateInfoService extends CrudService<MortgageCarEvaluateInfoDao, MortgageCarEvaluateInfo> {

	public MortgageCarEvaluateInfo get(String id) {
		return super.get(id);
	}
	
	public List<MortgageCarEvaluateInfo> findList(MortgageCarEvaluateInfo mortgageCarEvaluateInfo) {
		return super.findList(mortgageCarEvaluateInfo);
	}
	
	public Page<MortgageCarEvaluateInfo> findPage(Page<MortgageCarEvaluateInfo> page, MortgageCarEvaluateInfo mortgageCarEvaluateInfo) {
		return super.findPage(page, mortgageCarEvaluateInfo);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(MortgageCarEvaluateInfo mortgageCarEvaluateInfo) {
		super.save(mortgageCarEvaluateInfo);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(MortgageCarEvaluateInfo mortgageCarEvaluateInfo) {
		super.delete(mortgageCarEvaluateInfo);
	}
	
	//根据carId查找车辆抵质押物的数据
	public MortgageCarInfo findMortgageCarByCarId(String carId){
		return super.dao.findMortgageCarByCarId(carId);
     }
	
	//根据carId查找数据
	public MortgageCarEvaluateInfo findListByCarId(String carId) {
		return this.dao.findListByCarId(carId);
	}
	//更新抵质押物评估表数据
	@Transactional(value="CRE",readOnly = false)
	public void updateByCarId(MortgageCarEvaluateInfo mortgageCarEvaluateInfo) {
		 this.dao.updateByCarId(mortgageCarEvaluateInfo);
	}
	
}