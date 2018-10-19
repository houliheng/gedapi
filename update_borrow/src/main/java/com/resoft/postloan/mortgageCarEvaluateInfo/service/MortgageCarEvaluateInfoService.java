package com.resoft.postloan.mortgageCarEvaluateInfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.postloan.mortgageCarEvaluateInfo.dao.MortgageCarEvaluateInfoDao;
import com.resoft.postloan.mortgageCarEvaluateInfo.entity.MortgageCarEvaluateInfo;
import com.resoft.postloan.mortgageCarInfo.dao.MortgageCarInfoDao;
import com.resoft.postloan.mortgageCarInfo.entity.MortgageCarInfo;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 抵质押物信息Service
 * @author yanwanmei
 * @version 2016-03-01
 */

	
@Service("plMortgageCarEvaluateInfoService")
@DbType("pl.dbType")
@Transactional(value="PL",readOnly = true)
public class MortgageCarEvaluateInfoService extends CrudService<MortgageCarEvaluateInfoDao, MortgageCarEvaluateInfo> {

	public MortgageCarEvaluateInfo get(String id) {
		return super.get(id);
	}
	@Autowired
	private   MortgageCarInfoDao  mortgageCarInfoDao;
	
	public List<MortgageCarEvaluateInfo> findList(MortgageCarEvaluateInfo mortgageCarEvaluateInfo) {
		return super.findList(mortgageCarEvaluateInfo);
	}
	
	public Page<MortgageCarEvaluateInfo> findPage(Page<MortgageCarEvaluateInfo> page, MortgageCarEvaluateInfo mortgageCarEvaluateInfo) {
		return super.findPage(page, mortgageCarEvaluateInfo);
	}
	
	@Transactional(value="PL",readOnly = false)
	public void save(MortgageCarEvaluateInfo mortgageCarEvaluateInfo,MortgageCarInfo mortgageCarInfo) {
		//保存车辆信息
		mortgageCarInfoDao.insert(mortgageCarInfo);
		//保存车辆评估信息
		super.save(mortgageCarEvaluateInfo);
	}
	
	@Transactional(value="PL",readOnly = false)
	public void delete(MortgageCarInfo mortgageCarInfo) {
		mortgageCarInfoDao.delete(mortgageCarInfo);
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
	@Transactional(value="PL",readOnly = false)
	public void updateByCarId(MortgageCarEvaluateInfo mortgageCarEvaluateInfo,MortgageCarInfo mortgageCarInfo) {
		 this.dao.updateByCarId(mortgageCarEvaluateInfo);
		//更新车辆信息
		 mortgageCarInfoDao.update(mortgageCarInfo);
	}
}