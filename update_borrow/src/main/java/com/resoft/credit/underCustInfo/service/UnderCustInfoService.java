package com.resoft.credit.underCustInfo.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.opensaml.xmlsec.signature.J;
import org.springframework.beans.factory.annotation.Autowired;
import com.resoft.credit.underCompanyInfo.entity.UnderCompanyInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.underCustInfo.entity.UnderCustInfo;
import com.resoft.credit.underCustInfo.dao.UnderCustInfoDao;

/**
 * 线下借款-借款人基本信息Service
 * @author jml
 * @version 2018-06-26
 */
@Service
@Transactional(readOnly = false)
public class UnderCustInfoService extends CrudService<UnderCustInfoDao, UnderCustInfo> {
	@Autowired
	private UnderCustInfoDao underCustInfoDao;

	public UnderCustInfo get(String id) {
		return super.get(id);
	}
	
	public List<UnderCustInfo> findList(UnderCustInfo underCustInfo) {
		return super.findList(underCustInfo);
	}
	
	public Page<UnderCustInfo> findPage(Page<UnderCustInfo> page, UnderCustInfo underCustInfo) {
		return super.findPage(page, underCustInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(UnderCustInfo underCustInfo) {
		super.save(underCustInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(UnderCustInfo underCustInfo) {
		super.delete(underCustInfo);
	}

	public UnderCustInfo getByApplyNo(String applyNo){
		return underCustInfoDao.getByApplyNo(applyNo);
	}

	/**
	 * 根据applyNo判断是插入还是更新数据
	 * @param underCustInfo
	 * @return
	 */
	@Transactional(readOnly = false)
	public int insertOrUpdate(UnderCustInfo underCustInfo){
		if (underCustInfo == null || underCustInfo.getApplyNo() == null){
			return 0;
		}else {
			UnderCustInfo underCustInfo1 = underCustInfoDao.getByApplyNo(underCustInfo.getApplyNo());
			System.out.println("==================================================================" + JSONObject.toJSONString(underCustInfo1));
			if (underCustInfo1 == null){
				underCustInfo.preInsert();
				return underCustInfoDao.insert(underCustInfo);
			}else {
				underCustInfo.preUpdate();
				underCustInfoDao.updateByApplyNo(underCustInfo);
			}
		}
		return 0;
	}

}