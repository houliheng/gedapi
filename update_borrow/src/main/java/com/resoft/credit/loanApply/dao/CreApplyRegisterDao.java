package com.resoft.credit.loanApply.dao;

import java.util.List;

import com.resoft.credit.loanApply.entity.CreApplyRegister;
import com.resoft.credit.taskCenter.entity.ActHiComment;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * @reqno:H1509230044
 * @date-designer:2015年10月13日-songmin
 * @date-author:2015年10月13日-songmin:客户申请信息表Dao
 */
@MyBatisDao
public interface CreApplyRegisterDao extends CrudDao<CreApplyRegister>{

	/**
	 * @reqno:H1509230044
	 * @date-designer:2015年10月13日-songmin
	 * @date-author:2015年10月13日-songmin:CRE_信贷审批_申请录入_产品类型选择
	 * 		根据申请ID，查询客户的贷款申请登记信息
	 */
	public CreApplyRegister findApplyRegisterInfoById(String applyId);
	/**
	 * @reqno:H1509230043
	 * @date-designer:2015年10月13日-songmin
	 * @date-author:2015年10月13日-songmin:CRE_信贷审批_申请录入_产品类型选择_下一步（保存）、页面跳转
	 * 		根据申请ID，查询客户的贷款申请登记信息（只取简要信息，不予其他表进行关联）
	 */
	public CreApplyRegister findSimpleApplyRegisterInfoById(String applyId);
	/**
	 * @reqno:H1510290045
	 * @date-designer:2015年11月5日-songmin
	 * @date-author:2015年11月5日-songmin:CRE_信贷审批_取消审批_审批结论
	 * 	根据ID，修改客户申请注册信息的状态位
	 */
	public void updateStatus(CreApplyRegister creApplyRegister);
	/**
	 * @reqno:H1511100082
	 * @date-designer:2015年11月13日-songmin
	 * @date-author:2015年11月13日-songmin:根据证件类型、证件号码获取最近一次的客户登记信息（这里只获取了ID）
	 * @param creApplyRegister
	 * @return
	 */
	public List<CreApplyRegister> findApplyRegisterByIdCard(CreApplyRegister creApplyRegister);
	/**
	 * @reqno:H1511100047
	 * @date-designer:20151117-huangxuecheng
	 * @date-author:20151117-huangxuecheng:CRE_信贷审批_进件管理_个人客户登记列表_流程图、流程轨迹.开发说明：废弃时插入comment					        
	 */
	public void insertComment(ActHiComment actHiComment);
	/**
	 * 根据申请编号查询 流程实例id
	 */
    public CreApplyRegister selectApplyRegisterByApplyNo(String applyNo);
}
