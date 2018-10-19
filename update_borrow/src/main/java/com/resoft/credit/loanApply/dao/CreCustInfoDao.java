package com.resoft.credit.loanApply.dao;

import com.resoft.credit.loanApply.entity.CreCustInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * @reqno:H1509230043
 * @date-designer:2015年10月14日-songmin
 * @date-author:2015年10月14日-songmin:CRE_信贷审批_申请录入_产品类型选择_下一步（保存）、页面跳转
 */
@MyBatisDao
public interface CreCustInfoDao extends CrudDao<CreCustInfo>{

	/**
	 * @reqno:H1509230043
	 * @date-designer:2015年10月14日-songmin
	 * @date-author:2015年10月14日-songmin:CRE_信贷审批_申请录入_产品类型选择_下一步（保存）、页面跳转
	 * 		把客户名称、证件类型、证件号从CRE_APPLY_REGISTER表中拷贝过来
	 */
	/**
	 * @reqno:H1512210023
	 * @date-designer:2015年12月30日-songmin
	 * @date-author:2015年12月30日-songmin:CRE_信贷审批_进件管理_个人客户登记_功能重构
	 * 该功能因需求变更删除
	 */
	
	/**
	 * @reqno:H1510080106
	 * @date-designer:2015年10月23日-songmin
	 * @date-author:2015年10月23日-songmin:CRE_信贷审批_申请录入_客户信息_更新、保存_基本信息、房产信息、车辆信息、工作信息数据
	 * 		根据ID、修改客户基本信息
	 * 		修改数据项：出生日期、性别、移动电话、婚姻状况、子女数量、供养人数、每月家庭支出、最高学历、住宅电话的区号、住宅电话、现住宅地居住时间、
	 *		 	来本市时间、电子邮箱、微信号码、QQ号码、微博号码、户籍地址省、户籍地址市、户籍地址区、户籍地址详细、是否本地户籍、现居地与户籍地址是否一致、
	 *		 	现居地与户籍地址是否一致、通讯地址省-通讯地址即现住宅地址、通讯地址市、通讯地址区、通讯地址详细
	 */
	public void updateCustInfo(CreCustInfo creCustInfo);
}
