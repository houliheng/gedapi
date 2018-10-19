package com.resoft.credit.applyRegister.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.applyRegister.entity.ApplyRegister;

/**
 * 个人客户登记DAO接口
 * @author wuxi01
 * @version 2016-03-05
 */
@MyBatisDao
public interface ApplyRegisterDao extends CrudDao<ApplyRegister> {




	/**
	 * 根据证件类型、证件号和手机号查询信息登记状态
	 * @param map
	 * @return
	 */
	public List<String> statusResult(Map<String, String> map);
	
	/**
	 * 批量删除方法
	 * @param param
	 */
	public void banchDelete(Map<String,Object> param);
	

	public ApplyRegister getApplyRegisterByApplyNo(@Param("applyNo")String applyNo);
	
	public String getRegisterDateByApplyNo(@Param("applyNo")String applyNo);
	
	public void updateApplyStatus(@Param("applyNo")String applyNo,@Param("applyStatus") String applyStatus);
	/**
	 * 启动流程后更新对应的流程实例ID
	 */
	public void updateProcInsIdById(ApplyRegister applyRegister);

	public void saveApplyRegisterFromGedApplyRegister(ApplyRegister applyRegister);
	
	/**
	 * 根据企业标志（4选1）判断该企业是否存在
	 */
	public List<String> existCompany(Map<String, Object> params);

	public ApplyRegister checkDouble(ApplyRegister applyRegister);
	
	/**
	 * 获取申请人信息
	 * @param applyNo
	 * @return
	 */
	public ApplyRegister getApplyRegisterByApplyNoAndGed(@Param("applyNo")String applyNo);

	/**
	 * 根据身份证号码查询是否存在对应申请信息
	 * @param idNum
	 * @return
	 */
	public List<ApplyRegister> findApplyListsByIdNum(@Param("idNum")String idNum);

	public void updateSendGEDStatus(ApplyRegister applyRegister);

	public String findGEDByContractNo(@Param("contractNo") String contractNo);


	/**
	 * 根据申请编号查询产品类型 : 联合授信产品 还是非联合授信产品
	 */
	public String selectRegisterByApplyNo(String applyNo);

	public ApplyRegister getApplyRegisterByContractNo(@Param("contractNo")String contractNo);


	// 老数据推送冠易贷的时候 修改sengGed 为 2
   public int updateRegisterByApplyNo(ApplyRegister applyRegister);
   
   
   public void updateUnderByApplyNo(ApplyRegister applyRegister);
   
   public ApplyRegister getApplyRegisterByAccontractNo(String contractNo);

}