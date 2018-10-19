package com.resoft.credit.stockTaskDistribute.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.common.utils.Constants;
import com.resoft.credit.stockAssesseTarget.service.StockAssesseTargetService;
import com.resoft.credit.stockInfo.entity.StockInfo;
import com.resoft.credit.stockInfo.service.StockInfoService;
import com.resoft.credit.stockOperateDetail.dao.StockOperateDetailDao;
import com.resoft.credit.stockOperateDetail.entity.StockOperateDetail;
import com.resoft.credit.stockOpinion.service.CreStockOpinionService;
import com.resoft.credit.stockTaskDistribute.dao.StockTaskDistributeDao;
import com.resoft.credit.stockTaskDistribute.entity.StockTaskDistribute;
import com.resoft.credit.stockTaskReceive.entity.StockTaskReceive;
import com.resoft.credit.stockTaskReceive.service.StockTaskReceiveService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 股权估值服务列表Service
 * @author jml
 * @version 2017-09-06
 */
@Service
@Transactional(readOnly = true)
public class StockTaskDistributeService extends CrudService<StockTaskDistributeDao, StockTaskDistribute> {

	@Autowired
	private StockTaskReceiveService stockTaskReceiveService;
	@Autowired
	private StockInfoService stockInfoService;
	@Autowired
	private CreStockOpinionService stockOpinionService;
	@Autowired
	private StockOperateDetailDao stockOperateDetailDao;
	@Autowired
	private StockAssesseTargetService stockAssesseTargetService;
	
	@Transactional(readOnly = false)
	public void saveStockReceive(StockTaskReceive stockTaskReceive ,String taskDefKey,String stockType,String stockTaskReceiveId){
		StockInfo stockInfo = new StockInfo();
		stockInfo.setApplyNo(stockTaskReceive.getApplyNo());
		if(Constants.UTASK_FGSFKSH.equalsIgnoreCase(taskDefKey)){
			stockTaskReceive.setGrade("4");
		}else if(Constants.UTASK_QYFKSH.equalsIgnoreCase(taskDefKey)){
			stockTaskReceive.setGrade("3");
		}else if(Constants.UTASK_DQFKSH.equalsIgnoreCase(taskDefKey)){
			stockTaskReceive.setGrade("2");
		}else if(Constants.UTASK_ZGSFKSH.equalsIgnoreCase(taskDefKey)){
			stockTaskReceive.setGrade("1");
		}
		if("1".equals(stockType)){//转办
			StockTaskReceive oldStockTaskReceive = stockTaskReceiveService.getById(stockTaskReceiveId);
			stockInfo.setGrade(oldStockTaskReceive.getGrade());
		}else{//直接分配
			stockInfo.setGrade(stockTaskReceive.getGrade());
		}
		stockInfoService.deleteStockInfoByApplyGrade(stockTaskReceive.getApplyNo(), stockTaskReceive.getGrade());
		stockTaskReceiveService.deleteStockInfoByApplyGrade(stockTaskReceive.getApplyNo(), stockTaskReceive.getGrade());
		stockOpinionService.deleteStockInfoByApplyGrade(stockTaskReceive.getApplyNo(), stockTaskReceive.getGrade());
		stockAssesseTargetService.deleteStockInfoByApplyGrade(stockTaskReceive.getApplyNo(), stockTaskReceive.getGrade());
		stockInfo.setId(IdGen.uuid());
		stockInfo.preInsert();
		stockInfoService.saveStockInfoOnlyApplyNo(stockInfo);
		stockTaskReceive.setStockInfoId(stockInfo.getId());
		stockTaskReceiveService.save(stockTaskReceive);
		//流程轨迹
		StockOperateDetail stockOperateDetail=new StockOperateDetail();
		stockOperateDetail.setGrade(stockTaskReceive.getGrade());
		stockOperateDetail.setApplyNo(stockTaskReceive.getApplyNo());
		stockOperateDetail.setReceiver(stockTaskReceive.getReceiver());
		if("1".equals(stockType)){//转办
			stockOperateDetail.setOperate("1");
		}else{
			stockOperateDetail.setOperate("0");
		}
		stockOperateDetail.preInsert();
		stockOperateDetailDao.insert(stockOperateDetail);
	}
	
	public StockTaskDistribute get(String id) {
		return super.get(id);
	}
	
	public List<User> findUserAndOrleList(Map<String, Object> params){
		return super.dao.findUserAndOrleList(params);
	}
	
	public List<StockTaskDistribute> findList(StockTaskDistribute stockTaskDistribute) {
		return super.findList(stockTaskDistribute);
	}
	
	public Page<StockTaskDistribute> findPage(Page<StockTaskDistribute> page, StockTaskDistribute stockTaskDistribute) {
		return super.findPage(page, stockTaskDistribute);
	}
	
	@Transactional(readOnly = false)
	public void save(StockTaskDistribute stockTaskDistribute) {
		super.save(stockTaskDistribute);
	}
	
	@Transactional(readOnly = false)
	public void delete(StockTaskDistribute stockTaskDistribute) {
		super.delete(stockTaskDistribute);
	}
	
}