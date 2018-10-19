package com.resoft.credit.financialStateImport.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.common.utils.Constants;
import com.resoft.credit.financialStateImport.dao.FinancialStateImportDao;
import com.resoft.credit.financialStateImport.dao.ThemisReportBodyDao;
import com.resoft.credit.financialStateImport.dao.ThemisReportHeadDao;
import com.resoft.credit.financialStateImport.entity.FinancialStateImport;
import com.resoft.credit.financialStateImport.entity.ReportInfo4Excel;
import com.resoft.credit.financialStateImport.entity.ThemisReportHead;
import com.resoft.credit.financialStateImport.entity.ThemisReportInfo;
import com.resoft.outinterface.themis.dao.ThemisRequestDao;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 财报导入Service
 * 
 * @author caoyinglong
 * @version 2016-03-10
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class FinancialStateImportService extends CrudService<FinancialStateImportDao, FinancialStateImport> {

	
	@Autowired
	private ThemisReportHeadDao themisReportHeadDao;

	@Autowired
	private ThemisReportBodyDao themisReportBodyDao;
	
	@Autowired
	private ThemisRequestDao themisRequestDao;

	public FinancialStateImport get(String id) {
		return super.get(id);
	}

	/**
	 * 保存表头
	 * 
	 * @param themisReportHead
	 */
	private void saveThemisReportHead(ThemisReportHead themisReportHead, String applyNo) {
		themisReportHeadDao.deleltByApplyNo(applyNo);
		themisReportHeadDao.insert(themisReportHead);
	}
	
	
	public ThemisReportHead findReportHeadByApplyNo(String applyNo) {
		ThemisReportHead trh = themisReportBodyDao.findThemisReportHead(applyNo);
		return trh;
	}
	
	public List<ThemisReportInfo> findThemisReportInfoByApplyNo(String applyNo,String reportTable){
		List<ThemisReportInfo> ltri = new ArrayList<ThemisReportInfo>();
		List<String>  yearMonths= themisReportBodyDao.findYearMonthByApply(reportTable, applyNo);
		for(int i = 0;i<yearMonths.size();i++){
			ThemisReportInfo tri = new ThemisReportInfo();
			String yearMonth=yearMonths.get(i);
			List<ReportInfo4Excel>  content= themisReportBodyDao.findFinancialReport(reportTable, yearMonth, applyNo);
			tri.setCompanycode("");
			tri.setReportYearMonth(yearMonth);
			tri.setReportTable(reportTable);
			tri.setReportInfo(content);
			ltri.add(tri);
		}
		return ltri;
	}
	private void saveThemisReportBody(ThemisReportInfo body,String applyNo,String companycode,String reportTable) {
			themisReportBodyDao.batchInsert(applyNo,companycode,body.getReportYearMonth(),reportTable,body.getReportInfo());
	}

	/**
	 * 保存页
	 * 
	 * @param triList
	 * @param reportTable
	 * @param applyNo
	 */
	private void saveThemisReportInfoList(List<ThemisReportInfo> triList, String companycode, String reportTable, String applyNo) {
		themisReportBodyDao.deleteByApplyNo(applyNo,reportTable);
		for (int i = 0; i < triList.size(); i++) {
			this.saveThemisReportBody(triList.get(i),applyNo,companycode,reportTable);
		}
	}


	/**
	 * 导入EXCEL
	 * 
	 * @param themisReportHead
	 * @param triList1
	 * @param triList2
	 */
	@Transactional(value="CRE",readOnly = false)
	public void saveExcel(ThemisReportHead themisReportHead, List<ThemisReportInfo> triList1, List<ThemisReportInfo> triList2, String applyNo) {
		// 1.公司信息
		User user = UserUtils.getUser();
		themisReportHead.setUser(user);
		themisReportHead.setApplyNo(applyNo);
		this.saveThemisReportHead(themisReportHead, applyNo);
		// 2.新会计准则资产负债表
		this.saveThemisReportInfoList(triList1, themisReportHead.getCompanycode(), Constants.THEMIS_NEW_FINANCE_BS, applyNo);
		// 3.新会计准则利润表
		this.saveThemisReportInfoList(triList2, themisReportHead.getCompanycode(), Constants.THEMIS_NEW_FINANCE_PL, applyNo);
	}
	
	/**
	 * 保存外访系统推送财报
	 * 
	 * @param themisReportHead
	 * @param triList1
	 * @param triList2
	 */
	@Transactional(value="CRE",readOnly = false)
	public void saveSVExcel(ThemisReportHead themisReportHead, List<ThemisReportInfo> triList1, List<ThemisReportInfo> triList2, String applyNo) {
		// 1.公司信息
		themisReportHead.setApplyNo(applyNo);
		this.saveThemisReportHead(themisReportHead, applyNo);
		// 2.新会计准则资产负债表
		this.saveThemisReportInfoList(triList1, themisReportHead.getCompanycode(), Constants.THEMIS_NEW_FINANCE_BS, applyNo);
		// 3.新会计准则利润表
		this.saveThemisReportInfoList(triList2, themisReportHead.getCompanycode(), Constants.THEMIS_NEW_FINANCE_PL, applyNo);
	}
	
	@Transactional(value="CRE",readOnly = false)
   public void deleteSVFinancial(String applyNo){
		themisReportHeadDao.deleltByApplyNo(applyNo);
		themisReportBodyDao.deleteByApplyNo(applyNo,Constants.THEMIS_NEW_FINANCE_BS);
		themisReportBodyDao.deleteByApplyNo(applyNo,Constants.THEMIS_NEW_FINANCE_PL);
   }	
	
	@Transactional(value="CRE",readOnly = false)
	public void deleteSVAndThemis(String applyNo){
		themisRequestDao.deleteThemisReturnHead(applyNo);
		themisRequestDao.deleteThemisReview(applyNo);
		themisRequestDao.deleteThemisScore(applyNo);
		themisReportHeadDao.deleltByApplyNo(applyNo);
		themisReportBodyDao.deleteByApplyNo(applyNo,Constants.THEMIS_NEW_FINANCE_BS);
		themisReportBodyDao.deleteByApplyNo(applyNo,Constants.THEMIS_NEW_FINANCE_PL);
	}
	
}
