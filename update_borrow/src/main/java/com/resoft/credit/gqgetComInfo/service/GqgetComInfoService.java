package com.resoft.credit.gqgetComInfo.service;

import com.thinkgem.jeesite.common.utils.IdGen;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.gqgetComInfo.entity.GqgetComInfo;
import com.resoft.credit.gqgetComInfo.dao.GqgetComInfoDao;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;

/**
 * 冠E通Service
 * 
 * @author yanwanmei
 * @version 2016-08-08
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class GqgetComInfoService extends CrudService<GqgetComInfoDao, GqgetComInfo> {
	
	@Autowired
	private BankLoanService bankLoanService;
	
	public GqgetComInfo get(String id) {
		return super.get(id);
	}

	public List<GqgetComInfo> findList(GqgetComInfo gqgetComInfo) {
		return super.findList(gqgetComInfo);
	}

	public Page<GqgetComInfo> findPage(Page<GqgetComInfo> page, GqgetComInfo gqgetComInfo) {
		return super.findPage(page, gqgetComInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(GqgetComInfo gqgetComInfo) {
		super.save(gqgetComInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void updateByApplyNo(GqgetComInfo gqgetComInfo) {
		super.dao.updateByApplyNo(gqgetComInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(GqgetComInfo gqgetComInfo) {
		super.delete(gqgetComInfo);
	}

	public GqgetComInfo getGqgetComInfoByApplyNo(String applyNo) {
		return super.dao.getGqgetComInfoByApplyNo(applyNo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void insert(GqgetComInfo gqgetComInfo) {
		super.dao.insert(gqgetComInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void saveGqgetData(ActTaskParam actTaskParam, GqgetComInfo gqgetComInfo) {
		gqgetComInfo.getBankLoan().setApplyNo(actTaskParam.getApplyNo());
		bankLoanService.save(gqgetComInfo.getBankLoan());
		GqgetComInfo hasQqgetComInfo = getGqgetComInfoByApplyNo(actTaskParam.getApplyNo());
		if (hasQqgetComInfo != null) {
			gqgetComInfo.preUpdate();
			updateByApplyNo(gqgetComInfo);
		} else {
			gqgetComInfo.preInsert();
			insert(gqgetComInfo);
		}
	}

	@Transactional(value = "CRE", readOnly = false)
    public void saveRepaySource(GqgetComInfo repaySource) {
		if (StringUtils.isBlank(repaySource.getId())) {
			repaySource.preInsert();
			dao.saveRepaySource(repaySource);
		} else {
			repaySource.preUpdate();
			dao.updateRepaySource(repaySource);
		}
    }


	@Transactional(value = "CRE", readOnly = false)
	public void saveZichan(ActTaskParam actTaskParam, GqgetComInfo gqgetComInfo) {
//		gqgetComInfo.getBankLoan().setApplyNo(actTaskParam.getApplyNo());
		GqgetComInfo hasQqgetComInfo = getGqgetComInfoByApplyNo(gqgetComInfo.getApplyNo());
		if (hasQqgetComInfo != null) {
			gqgetComInfo.preUpdate();
			dao.updateZichan(gqgetComInfo);
		} else {
			gqgetComInfo.preInsert();
			insert(gqgetComInfo);
		}
	}
}