package com.resoft.credit.custinfo.dao;

import java.util.List;
import java.util.Map;

import com.resoft.outinterface.SV.client2.ResidenceField;
import com.resoft.outinterface.SV.client2.WorkField;
import org.apache.ibatis.annotations.Param;

import com.resoft.credit.custinfo.entity.CustInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 *客户基本信息DAO接口
 */
  
@MyBatisDao
public interface CustInfoDao extends CrudDao<CustInfo> {
	/**
	 * 根据身份证号查询客户基本信息
	 * */
	public CustInfo getIdNum(String idNum) ;
	
	/**
	 * 根据证件类型、证件号、手机号查询客户信息（关联relation表查询）
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> findCustInfoList(Map<String, String> map);
	
	/**
	 * 根据证件类型、证件号查询客户信息（单表查询）
	 * @param map
	 * @return List<Map<String, String>>
	 */
	public CustInfo findCustInfoByIdCard(Map<String, String> map);
	
	/**
	 * 根据手机号、证件号查询客户信息（单表查询）
	 * @param map
	 * @return List<Map<String, String>>
	 */
	public CustInfo findCustInfoByIdCardAndMobileNum(Map<String, String> map);
	
	/**
	 * 根据客户登记信息更新客户基本信息表信息
	 * @param custInfo
	 */
	public void updateByApplyRegister(CustInfo custInfo);
	
	/**
	 * 根据主借人信息查询共借人列表
	 * @param custInfo
	 * @return List<CustInfo>
	 */
	public List<CustInfo> findCoCustInfoList(CustInfo custInfo);
	
	/**
	 * 批量删除客户信息
	 * @param idList
	 */
	public void banchDelete(Map<String,Object> param);
	public void newBanchDelete(Map<String,Object> param);
	/**
	 * 根据申请编号查询客户信息
	 * @param applyNo
	 * @return List<CustInfo>
	 */
	public List<CustInfo> findMainBorrowerByApplyNo(String applyNo);

	/**
	 * 根据指定合同编号，查询主借人 ，配偶，担保人的身份证号码
	 * @param applyNo
	 * @return
	 */
	public List<Map<String, String>> getAllCardNoByApplyNo(String applyNo);

	
	/**
	 * 根据指定合同编号，查询联系人的所有电话号码
	 * @param applyNo
	 * @return
	 */
	public List<Map<String, String>> getAllMobileByApplyNo(String applyNo);
	
	/**
	 * 根据身份证卡号查询客户信息
	 * @param card
	 * @return
	 */
	public CustInfo getInfoByCard(String card);

	public List<CustInfo> findGedGuaranteeInfo(Map<String, Object> params);

	public List<CustInfo> findThisCompanyGuarantor(@Param("applyNo")String applyNo, @Param("companyId")String companyId);

	public CustInfo getCustByRoleAndApplyNo(@Param("applyNo")String applyNo, @Param("roleType")String roleType);

    List<WorkField> getMainWork(@Param("applyNo")String applyNo);

    List<WorkField> getBatchWork(@Param("applyNo")String applyNo);

	List<ResidenceField> getMainRealAddress(@Param("applyNo")String applyNo);

	//Custpublic void saveRegisterType(@Param("custId")String custId, @Param("registerType")String registerType);
}