package com.resoft.credit.loanApply.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.loanApply.dao.CreApplyRegisterDao;
import com.resoft.credit.loanApply.entity.CreApplyRegister;
import com.resoft.credit.taskCenter.entity.ActHiComment;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly=true)
public class CreApplyRegisterService extends CrudService<CreApplyRegisterDao, CreApplyRegister>{

	/**
	 * @reqno:H1509230044
	 * @date-designer:2015年10月13日-songmin
	 * @date-author:2015年10月13日-songmin:CRE_信贷审批_申请录入_产品类型选择
	 * 		根据申请ID，查询客户的贷款申请登记信息
	 */
	public CreApplyRegister findApplyRegisterInfoById(String applyId){
		return super.dao.findApplyRegisterInfoById(applyId);
	}
	/**
	 * @reqno:H1510290045
	 * @date-designer:2015年11月5日-songmin
	 * @date-author:2015年11月5日-songmin:CRE_信贷审批_取消审批_审批结论
	 * 	根据ID，修改客户申请注册信息的状态位
	 * @param ID
	 * @param status
	 */
	/**
	 * @reqno:H1510290046
	 * @date-designer:2015年11月5日-songmin
	 * @date-author:2015年11月5日-songmin:事务支持
	 */
	@Transactional(value="CRE",readOnly=false)
	public void updateStatus(CreApplyRegister creApplyRegister){
		super.dao.updateStatus(creApplyRegister);
	}
	
	/**
	 * @reqno:H1511100082
	 * @date-designer:2015年11月13日-songmin
	 * @date-author:2015年11月13日-songmin:根据证件类型、证件号码获取最近一次的客户登记信息（这里只获取了ID）
	 */
	public List<CreApplyRegister> findApplyRegisterByIdCard(CreApplyRegister creApplyRegister){
		return super.dao.findApplyRegisterByIdCard(creApplyRegister);
	}
	/**
	 * @reqno:H1511100082
	 * @date-designer:2015年10月13日-songmin
	 * @date-author:2015年10月13日-songmin:根据申请ID，获取证件类型、证件号码、客户名称、ID
	 */
	public CreApplyRegister findSimpleApplyRegisterInfoById(String applyId){
		return super.dao.findSimpleApplyRegisterInfoById(applyId);
	}
	/**
	 * @reqno:H1511100047
	 * @date-designer:20151117-huangxuecheng
	 * @date-author:20151117-huangxuecheng:CRE_信贷审批_进件管理_个人客户登记列表_流程图、流程轨迹.开发说明：废弃时插入comment					        
	 */
    @Transactional(value="CRE",readOnly = false)
    public void addDepositComment(ActHiComment actHiComment){
    	 super.dao.insertComment(actHiComment);
    }

	/**
	 *根据申请编号 求出'流程实例ID',
	 */
     public CreApplyRegister queryApplyRegisterByapplyNO(String applyNo){
         return super.dao.selectApplyRegisterByApplyNo(applyNo);
	 }

}
