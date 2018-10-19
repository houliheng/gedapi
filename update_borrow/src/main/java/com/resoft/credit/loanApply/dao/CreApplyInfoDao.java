package com.resoft.credit.loanApply.dao;

import java.util.List;
import java.util.Map;

import com.resoft.credit.loanApply.entity.CreApplyInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * @reqno:H1509130076
 * @date-designer:2015年10月12日-songmin
 * @date-author:2015年10月12日-songmin:贷款申请信息表Dao
 */
@MyBatisDao
public interface CreApplyInfoDao extends CrudDao<CreApplyInfo>{

	/**
	 * @reqno:H1509130076
	 * @date-designer:2015年10月12日-songmin
	 * @date-author:2015年10月12日-songmin:根据用户申请ID获取用户贷款申请的产品信息
	 * @see findLoanInfoByApplyId
	 */
	@Deprecated
	public CreApplyInfo findLoadProductInfoByApplyId(String applyId);
	
	/**
	 * @reqno:H1509130075
	 * @date-designer:2015年10月13日-songmin
	 * @date-author:2015年10月13日-songmin:CRE_信贷审批_贷款初审_结论信息_查询
	 * 		根据申请ID，查询客户的贷款申请记录信息
	 */
	public CreApplyInfo findLoanInfoByApplyId(String applyId);
	
	/**
	 * @reqno:H1509230044
	 * @date-designer:2015年10月13日-songmin
	 * @date-author:2015年10月13日-songmin:CRE_信贷审批_申请录入_产品类型选择_查询
	 * 		判断是否登记过申请贷款的产品类型-大于0表示登记过产品类型，反之表示没有 
	 */
	public long checkExists(String applyId);
	/**
	 * @reqno:H1509230043
	 * @date-designer:2015年10月14日-songmin
	 * @date-author:2015年10月14日-songmin:CRE_信贷审批_申请录入_产品类型选择_查询
	 * 		登记具体贷款信息之前先登记客户申请贷款产品类型信息
	 */
	/**
	 * @reqno:H1512160007
	 * @date-designer:2015年12月24日-songmin
	 * @date-author:2015年12月24日-songmin:业务变更代码不再使用
	 */
	
	/**
	 * @reqno:H1512160007
	 * @date-designer:2015年12月17日-songmin
	 * @date-author:2015年12月17日-songmin:CRE_信贷审批_进件管理_企业客户登记_同步业务数据
	 * 		根据申请ID修改贷款申请表中的产品类型、产品类型名称、产品名称、产品ID
	 * 		该操作废弃，不再使用
	 */
	
	/**
	 * @reqno:H1510080095
	 * @date-designer:2015年10月20日-songmin
	 * @date-author:2015年10月20日-songmin:CRE_信贷审批_申请录入_贷款申请信息_更新、保存_贷款申请信息数据
	 * 		贷款申请信息录入（实际做修改操作，新增功能已在选择产品类型时录入），这里只录入固定属性的贷款申请信息，其他动态表单数据由动态表单功能完成
	 */
	public void loanApplyRecord(CreApplyInfo creApplyInfo);
	/**
	 * @reqno: H1510280019
	 * @date-designer:20151105-lirongchao
	 * @db-z :sys_user :login_name,name,id
	 * @db-j :sys_office :name
	 * @date-author:20151105-lirongchao: 1.申请录入页面：
 页面布局：上下布局，上面为工具栏（包括：影像上传、影像查阅、关闭）；下面为tab页签（包括：贷款申请信息、客户信息、录入结论）；
2.本需求是在“影像查阅”后面添加：“转办” 按钮；
3.点击“转办”按钮，弹出窗口，窗口名称“转办人员选择”；
4.窗口页面内容：上下布局，分别为：
  表头按钮：转办、关闭；
  人员列表，数据项：单选框按钮、序号、登陆名、姓名、归属部门、归属机构；
5.人员列表，加载与当前用户同一机构下的用户，供选择；列表要做成分布显示
6.选择一个用户，点击“转办”按钮，进行转办，前台提示转办成功，关闭窗口，同时刷新待办任务列表；
7.点击“关闭”按钮，关闭窗口；
	 当前环节-获取相同机构下的人员，不包括自己,跳转到转办页面	
	*/	
	public List<User> finduserList(Map<String, Object> params);
}
