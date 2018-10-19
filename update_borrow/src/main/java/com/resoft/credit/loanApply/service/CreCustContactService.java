/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.resoft.credit.loanApply.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.loanApply.entity.CreCustContact;
import com.resoft.credit.loanApply.dao.CreCustContactDao;

/**
 * 联系人信息表Service
 * @author lirongchao
 * @version 2015-10-22
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CreCustContactService extends CrudService<CreCustContactDao, CreCustContact> {

	public CreCustContact get(String id) {
		return super.get(id);
	}
	/**
	 * @reqno: H1510080107
	 * @date-designer:20151022-lirongchao
	 * @date-author:20151022-lirongchao: 需求-联系人列表数据项：复选择框、姓名、人员类型、移动电话、是否知晓本次贷款、住宅电话、单位名称
											列表排序：创建时间降序
											表头按钮：新增、修改、删除、详情
										当前环节-根据申请id查询联系人列表，将结果传给前台
	 */
	public List<CreCustContact> findList(CreCustContact creCustContact) {
		return super.findList(creCustContact);
	}
	
	public Page<CreCustContact> findPage(Page<CreCustContact> page, CreCustContact creCustContact) {
		return super.findPage(page, creCustContact);
	}
	/**
	 * @reqno: H1510080108
	 * @date-designer:20151021-lirongchao
	 * @date-author:20151021-lirongchao: 需求-1.新增：
												   弹出新增窗体，窗体名称：新增联系人信息；新增数据项包括：【姓名、人员类型、移动电话】、【是否知晓本次贷款、住宅电话、住宅地址】、【单位名称、单位地址、部门名称】、【职位名称】；
												   操作按钮：保存、关闭；
												   保存时前台会对每个字段进行非空、长度校验。
												   姓名、人员类型、移动电话、是否知晓本次贷款为必填项
											2.备注：新增保存后，提示保存成功，关闭保存窗体，同时页面刷新联系人列表
										当前环节-新增保存操作
	 */		
	@Transactional(value="CRE",readOnly = false)
	public void save(CreCustContact creCustContact) {
		super.save(creCustContact);
	}
	/**
	 * @reqno: H1510080109
	 * @date-designer:20151023-lirongchao
	 * @date-author:20151023-lirongchao: 需求-1.修改：
   弹出修改窗体，窗体名称：修改联系人信息；修改数据项包括：【姓名、人员类型、移动电话】、【是否知晓本次贷款、住宅电话、住宅地址】、【单位名称、单位地址、部门名称】、【职位名称】；
   操作按钮：保存、关闭；
   保存时前台会对每个字段进行非空、长度校验。
   姓名、人员类型、移动电话、是否知晓本次贷款为必填项
2. 详情：
    弹出详情窗体，窗体名称：查看联系人信息；详情数据项包括：【姓名、人员类型、移动电话】、【是否知晓本次贷款、住宅电话、住宅地址】、【单位名称、单位地址、部门名称】、【职位名称】；
   操作按钮：关闭；
3.删除：
  在查询页面表格，选择1个及1个以上记录，点击删除，进行删除操作；
4. 备注：修改、删除后，页面刷新联系人列表
										当前环节-联系人信息删除操作，根据id删除联系人信息
	 */	
	@Transactional(value="CRE",readOnly = false)
	public void delete(CreCustContact creCustContact) {
		super.delete(creCustContact);
	}
	
}