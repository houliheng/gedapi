package com.resoft.credit.stockInfo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.stockInfo.dao.StockInfoDao;
import com.resoft.credit.stockInfo.entity.StockInfo;
import com.resoft.credit.stockOperateDetail.dao.StockOperateDetailDao;
import com.resoft.credit.stockOperateDetail.entity.StockOperateDetail;
import com.resoft.credit.stockOpinion.entity.CreStockOpinion;
import com.resoft.credit.stockOpinion.service.CreStockOpinionService;
import com.resoft.credit.stockTaskReceive.entity.StockTaskReceive;
import com.resoft.credit.stockTaskReceive.service.StockTaskReceiveService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 股权尽调业务表Service
 * @author jml
 * @version 2017-12-04
 */
@Service
@Transactional(readOnly = false)
public class StockInfoService extends CrudService<StockInfoDao, StockInfo> {

	@Autowired
	private CreStockOpinionService creStockOpinionService;
	@Autowired
	private StockTaskReceiveService stockTaskReceiveService;
	@Autowired
	private StockOperateDetailDao stockOperateDetailDao;
	
	public StockInfo get(String id) {
		return super.get(id);
	}
	
	public List<StockInfo> findList(StockInfo stockInfo) {
		return super.findList(stockInfo);
	}
	
	public Page<StockInfo> findPage(Page<StockInfo> page, StockInfo stockInfo) {
		return super.findPage(page, stockInfo);
	}
	
	@Transactional(readOnly = false)
	public void saveStockInfoOnlyApplyNo(StockInfo stockInfo) {
		this.dao.saveOnlyApplyNo(stockInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(StockInfo stockInfo) {
		super.save(stockInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(StockInfo stockInfo) {
		super.delete(stockInfo);
	}
	
	@Transactional(readOnly = false)
	public void saveStockInfo(StockInfo stockInfo,String grade) throws Exception {
		StockInfo stockInfoOld = getStockInfoByApplyGrade(stockInfo.getApplyNo(),grade);
		stockInfo.setId(stockInfoOld.getId());
		stockInfo.setGrade(grade);
		stockInfo.preUpdate();
		dao.update(stockInfo);
		CreStockOpinion creStockOpinion=creStockOpinionService.getByOfficeGrade(stockInfo.getApplyNo(),grade);
		if("4".equals(grade)){
			if(creStockOpinion==null){
				creStockOpinion=new CreStockOpinion();
			}
			creStockOpinion.setApplyNo(stockInfo.getApplyNo());
			creStockOpinion.setOfficeFlag(grade);
			creStockOpinion.setEmployeeName(stockInfo.getBranchName());
			creStockOpinion.setEmployeeNo(stockInfo.getBranchNo());
			creStockOpinion.setOfficeOpinion(stockInfo.getSuggestionBranch());
			creStockOpinionService.save(creStockOpinion);
		}
		if("3".equals(grade)){
			if(creStockOpinion==null){
				creStockOpinion=new CreStockOpinion();
			}
			creStockOpinion.setApplyNo(stockInfo.getApplyNo());
			creStockOpinion.setOfficeFlag(grade);
			creStockOpinion.setEmployeeName(stockInfo.getAreaName());
			creStockOpinion.setEmployeeNo(stockInfo.getAreaNo());
			creStockOpinion.setOfficeOpinion(stockInfo.getSuggestionArea());
			creStockOpinionService.save(creStockOpinion);
		}
		if("2".equals(grade)){
			if(creStockOpinion==null){
				creStockOpinion=new CreStockOpinion();
			}
			creStockOpinion.setApplyNo(stockInfo.getApplyNo());
			creStockOpinion.setOfficeFlag(grade);
			creStockOpinion.setEmployeeName(stockInfo.getLargeAreaName());
			creStockOpinion.setEmployeeNo(stockInfo.getLargeAreaNo());
			creStockOpinion.setOfficeOpinion(stockInfo.getSuggestionLargeArea());
			creStockOpinionService.save(creStockOpinion);
		}
		if("1".equals(grade)){
			if(creStockOpinion==null){
				creStockOpinion=new CreStockOpinion();
			}
			creStockOpinion.setApplyNo(stockInfo.getApplyNo());
			creStockOpinion.setOfficeFlag(grade);
			creStockOpinion.setEmployeeName(stockInfo.getHeadName());
			creStockOpinion.setEmployeeNo(stockInfo.getHeadNo());
			creStockOpinion.setOfficeOpinion(stockInfo.getSuggestionHead());
			creStockOpinionService.save(creStockOpinion);
		}
		
		//更新服务表，和分单表的状态
		//将分配表的状态更改为完成
		StockTaskReceive stockTaskReceive = stockTaskReceiveService.getReceiveByApplyNoAndGrade(stockInfo.getApplyNo(),grade);
		if(stockTaskReceive==null){
			throw new Exception();
		}else{
			stockTaskReceive.setEndTime(new Date());
			stockTaskReceive.setStatus("0");
			stockTaskReceiveService.save(stockTaskReceive);
			//流程轨迹
			StockOperateDetail stockOperateDetail=new StockOperateDetail();
			stockOperateDetail.setGrade(stockTaskReceive.getGrade());
			stockOperateDetail.setApplyNo(stockTaskReceive.getApplyNo());
			stockOperateDetail.setOperate("2");
			stockOperateDetail.preInsert();
			stockOperateDetailDao.insert(stockOperateDetail);
		}
	}

	public StockInfo getStockInfoByApplyGrade(String applyNo, String grade) {
		return dao.getStockInfoByApplyGrade(applyNo,grade);
	}

	public StockInfo getStockInfoByApplyNo(String applyNo) {
		return dao.getStockInfoByApplyNo(applyNo);
	}

	public String selectActRuTasKDefKeyByInstId(String procInsId) {
		return dao.selectActRuTasKDefKeyByInstId(procInsId);
	}

	public void deleteStockInfoByApplyGrade(String applyNo, String grade) {
		dao.deleteStockInfoByApplyGrade(applyNo,grade);
	}
}