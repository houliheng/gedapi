/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.resoft.credit.blacklist.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.blacklist.dao.BlacklistDao;
import com.resoft.credit.blacklist.dao.BlacklistDetailDao;
import com.resoft.credit.blacklist.entity.Blacklist;
import com.resoft.credit.blacklist.entity.BlacklistDetail;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 黑名单表Service
 * @author lirongchao
 * @version 2015-12-22
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class BlacklistService extends CrudService<BlacklistDao, Blacklist> {
	@Autowired
	private ApplyRegisterService applyRegisterService;
	
	@Autowired
	private BlacklistDetailDao blacklistDetailDao;
	
	public Blacklist get(String id) {
		return super.get(id);
	}
	
	public List<Blacklist> findList(Blacklist blacklist) {
		return super.findList(blacklist);
	}

	public Page<Blacklist> findPage(Page<Blacklist> page, Blacklist blacklist) {
		if(StringUtils.isEmpty(blacklist.getCustType())){
			blacklist.setCustType(Constants.CUST_TYPE_PERSON);
		}
		if(blacklist.getListStatus() == null){
			blacklist.setListStatus(Constants.BLACKLIST_STATUS_HMD);
		}		
		return super.findPage(page, blacklist);
	}
	
	public Page<Blacklist> findBusinessPage(Page<Blacklist> page, Blacklist blacklist) {
		blacklist.setCustType("2");
		if(blacklist.getListStatus() == null){
			blacklist.setListStatus(Constants.BLACKLIST_STATUS_HMD);
		}
		return super.findPage(page, blacklist);
	}
	
	public Page<BlacklistDetail> findDetailsPage(Page<BlacklistDetail> page, BlacklistDetail blacklistDetail) {
		blacklistDetail.setPage(page);
		page.setList(blacklistDetailDao.findList(blacklistDetail));
		return page;
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(Blacklist blacklist) {
		super.save(blacklist);
	}
	/**
	 *  在“个人黑名单管理”页面，选择1条上记录，点击加黑(刷白)，判断所选择记录是否是白名单(白名单)，如果不是，给出提示；如果是，则进行加黑操作，弹出加黑窗体，窗体名称“加黑设置”，窗口数据项：【客户名称、移动电话】、【证件类型、证件号】、【设置说明】；
												  窗体按钮：保存、关闭；
												  【客户名称、移动电话】、【证件类型、证件号】为只读；【设置说明】必填项，大文本显示，最大输入500汉字；能输入普通字符，如句号、逗号等；
												  点击保存按钮，更新黑名单表状态为黑名单；同时在加黑详情表添加一条记录明细；保存成功后，提示“保存成功！”、关闭窗体、刷新列表数据；
												  点击关闭按钮，关闭窗体；
												  当前环节-保存黑名单管理信息,同时在加黑详情表添加一条记录明细
	 */	
	@Transactional(value="CRE",readOnly = false)
	public String revampSave(BlacklistDetail blacklistDetail) throws Exception
	{
		String returnValue ="";
		Blacklist  blacklist = this.get(blacklistDetail.getBlacklistId());
		blacklist.setListStatus(blacklistDetail.getListStatus());
		this.save(blacklist);
		blacklistDetail.preInsert();
		blacklistDetailDao.insert(blacklistDetail);
		returnValue = "success";
		return returnValue;
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(Blacklist blacklist) {
		super.delete(blacklist);
	}
	
	/**
	 * 判断当前客户是否存在黑名单中
	 */
	public boolean isBlackName(Map<String,String> params){
		params.put("DEL_FLAG_NORMAL", "0");
		List<Blacklist> list =  super.dao.findBlacklistByIdCard(params);
		if(list.isEmpty()){
			return false;
		}else{
			String listStatus = list.get(0).getListStatus();
			return Constants.BLACKLIST_STATUS_HMD.equals(listStatus);
		}
	}
	/**
	 * 在加入黑单前先根据证件类型、证件号，判断是否已经存在相应黑名单记录，如不存在，直接添加；如已存在，则更新相应记录状态为“黑名单”，同时在黑名单对应的加黑详情列表添加一条记录
	 * @param blacklist idType、idNum、mobile、listStatus、remark、、、、、、、、、
	 */
	@Transactional(value="CRE",readOnly=false)
	public void joinBalckAndDetails(String applyNo,String remarks){
		//根据applyId查找登记信息-从中获取客户类型等信息
		ApplyRegister  applyRegister = new ApplyRegister();
		applyRegister.setApplyNo(applyNo);
		List<ApplyRegister> applyRegisterList = applyRegisterService.findList(applyRegister);
		if (!applyRegisterList.isEmpty()) {
			applyRegister = applyRegisterList.get(0);
		}
		Blacklist blacklist = new Blacklist();
		blacklist.setCustType(applyRegister.getCustType());
		blacklist.setCustName(applyRegister.getCustName());
		blacklist.setIdType(applyRegister.getIdType());
		blacklist.setIdNum(applyRegister.getIdNum());
		blacklist.setMobile(applyRegister.getMobileNum());
		//查询当前客户的黑名单信息
		Map<String,String> params = Maps.newConcurrentMap();
		params.put("idType", blacklist.getIdType());
		params.put("idNum", blacklist.getIdNum());
		params.put("DEL_FLAG_NORMAL", "0");
		List<Blacklist> list =  super.dao.findBlacklistByIdCard(params);
		if(list.isEmpty()){//黑名单表中不存在记录
			blacklist.setListStatus(Constants.BLACKLIST_STATUS_HMD); //1黑名 单  2白名单
			this.save(blacklist);
		}else{
			blacklist = list.get(0);
			blacklist.setListStatus(Constants.BLACKLIST_STATUS_HMD); //不管原本记录时黑名单还是白名单，这里直接设置为黑名单    1黑名 单  2白名单
			this.save(blacklist);
		}
		//黑名单详情表，添加一条记录
		BlacklistDetail blacklistDetail = new BlacklistDetail();
		blacklistDetail.setBlacklistId(blacklist.getId());
		blacklistDetail.setListStatus(blacklist.getListStatus());
		blacklistDetail.setRemarks(remarks);
		blacklistDetail.preInsert();
		blacklistDetailDao.insert(blacklistDetail);
		applyRegisterService.updateApplyStatus(applyNo, Constants.APPLY_STATUS_REFUSED);
	}
	
}