package com.resoft.multds.credit.plApplyRegister.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.resoft.multds.credit.plApplyRegister.entity.PLApplyRegister;
import com.resoft.multds.credit.plCustinfo.entity.PLCustInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 个人客户登记DAO接口
 * 
 * @author wuxi01
 * @version 2016-06-17
 */
@MyBatisDao
public interface PLApplyRegisterDao extends CrudDao<PLApplyRegister> {

	/**
	 * 根据合同号查询进件信息
	 * 
	 * @param contractNo
	 * @return
	 */
	public List<PLApplyRegister> findApplyRegisterByContractNo(String contractNo);

	/**
	 * 根据证件号查询客户信息
	 * 
	 * @param contractNo
	 * @return
	 */
	public List<PLCustInfo> findPLCustInfoByIdCard(HashMap<String, String> params);

	/**
	 * 根据证件类型、证件号和手机号查询信息登记状态
	 * 
	 * @param map
	 * @return
	 */
	public List<String> statusResult(Map<String, String> map);
}