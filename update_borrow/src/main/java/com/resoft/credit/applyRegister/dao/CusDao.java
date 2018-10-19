package com.resoft.credit.applyRegister.dao;

import java.util.HashMap;
import java.util.List;

import com.resoft.credit.applyRegister.entity.Customer;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * @reqno: H1509110039
 * @date-designer:20150918-gengchang
 * @date-author:20150918-gengchang:CRE_信贷审批_进件管理_客户登记列表_查询
 * 									用到的方法有：
 * 												查询列表数据--findList(),
 *												获得分页总数--getCount(),
 *												新增记录--insert(),
 *												获得单条数据--get(),
 *												删除记录--delete() 									
 */
@MyBatisDao
public interface CusDao extends CrudDao<Customer> {
	
	/**
	 * @reqno:AH1509140009
	 * @date-designer:2015年9月28日-songmin
	 * @date-author:2015年9月28日-songmin:CRE_信贷审批_贷款初审_内匹配信息
	 * 	根据证件号码、证件类型查询指定客户的历史贷款申请记录（完全匹配）
	 * 	查询参数：customer-idNum、customer-idType
	 */
	public List<Customer> findApplyHis(Customer customer);
	/**
	 * @reqno: H1510210068
	 * @date-designer:20151023-lirongchao
	 * @date-author:20151023-lirongchao: 需求-1.在客户登记列表的新增或修改页面，
	 * 										当用户点击“提交”按钮，后台在提交前按规则生成申请编号、并保存；
											2.生成申请编号的规则：YYYYMMDD+00001+4位随机数；其中“00001”为序号，
											每个公司每天从00001开始递增
										当前环节-查询日期为当天的，当前公司登记的用户.
	 */
	public List<Customer> findserial(Customer customer);
	
	/**
	 * @reqno: H1510210067
	 * @date-designer:20151028-gengchang
	 * @date-author:20151028-gengchang: CRE_信贷审批_进件管理_客户登记列表_新增、修改_提交_做客户唯一性校验
	 *				statusResult():查询数据库中证件类型和证件号，在客户申请信息表中查询出status
  */
	public List<HashMap<String, String>> statusResult(HashMap<String, String> map);
	/**
	 * @reqno:H1512160005
	 * @date-designer:2015年12月16日-songmin
	 * @date-author:2015年12月16日-songmin:CRE_信贷审批_进件管理_企业客户登记_查询
	 * 		根据客户名称（模糊）、证件类型、证件号、状态分页查询企业客户数
	 */
	public List<Customer> findEnList(Customer customer);
	/**
	 * @reqno:H1512160005
	 * @date-designer:2015年12月16日-songmin
	 * @date-author:2015年12月16日-songmin:CRE_信贷审批_进件管理_企业客户登记_查询
	 * 		根据客户名称（模糊）、证件类型、证件号、状态查询企业客户总数
	 */
	public Long findEnListCount(Customer customer);
}
