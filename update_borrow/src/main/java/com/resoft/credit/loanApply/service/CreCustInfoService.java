package com.resoft.credit.loanApply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.loanApply.dao.CreCustInfoDao;
import com.resoft.credit.loanApply.entity.CreCustCarInfo;
import com.resoft.credit.loanApply.entity.CreCustCompanyInfo;
import com.resoft.credit.loanApply.entity.CreCustHousingInfo;
import com.resoft.credit.loanApply.entity.CreCustInfo;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * @reqno:H1510080096
 * @date-designer:2015年10月21日-songmin
 * @date-author:2015年10月21日-songmin:CRE_信贷审批_申请录入_客户信息_基本信息【默认数据项】展现
 * 	客户信息表Service  默认方法：get()
 */
@Service("CreCustInfoService") @DbType("cre.dbType")
@Transactional(value="CRE",readOnly=true)
public class CreCustInfoService extends CrudService<CreCustInfoDao, CreCustInfo>{
	@Autowired
	private CreCustHousingInfoService creCustHousingInfoService;
	@Autowired
	private CreCustCarInfoService creCustCarInfoService;
	@Autowired
	private CreCustCompanyInfoService creCustCompanyInfoService;
	/**
	 * @reqno:H1510080106
	 * @date-designer:2015年10月23日-songmin
	 * @date-author:2015年10月23日-songmin:CRE_信贷审批_申请录入_客户信息_更新、保存_基本信息、房产信息、车辆信息、工作信息数据
	 * 		根据ID、修改客户基本信息
	 * 		修改数据项：出生日期、性别、移动电话、婚姻状况、子女数量、供养人数、每月家庭支出、最高学历、住宅电话的区号、住宅电话、现住宅地居住时间、
	 *		 	来本市时间、电子邮箱、微信号码、QQ号码、微博号码、户籍地址省、户籍地址市、户籍地址区、户籍地址详细、是否本地户籍、现居地与户籍地址是否一致、
	 *		 	现居地与户籍地址是否一致、通讯地址省-通讯地址即现住宅地址、通讯地址市、通讯地址区、通讯地址详细
	 */
	@Transactional(value="CRE",readOnly=false)
	public void updateCustInfo(CreCustInfo creCustInfo){
		super.dao.updateCustInfo(creCustInfo);
	}
	
	/**
	 * @reqno:H1510080106
	 * @date-designer:2015年10月26日-songmin
	 * @date-author:2015年10月26日-songmin:CRE_信贷审批_申请录入_客户信息_更新、保存_基本信息、房产信息、车辆信息、工作信息数据
	 *		集中对基本信息、房产信息、车辆信息、工作信息数据进行保存、修改操作
	 */
	@Transactional(value="CRE",readOnly=false)
	public void updateCustInfo(CreCustInfo creCustInfo,CreCustHousingInfo creCustHousingInfo,
		CreCustCompanyInfo creCustCompanyInfo,CreCustCarInfo creCustCarInfo){
		//客户基本信息只会有修改操作
		super.dao.updateCustInfo(creCustInfo);
		creCustHousingInfoService.save(creCustHousingInfo);
		creCustCompanyInfoService.save(creCustCompanyInfo);
		creCustCarInfoService.save(creCustCarInfo);
	}
}
