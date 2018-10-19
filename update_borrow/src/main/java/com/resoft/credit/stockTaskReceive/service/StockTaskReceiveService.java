package com.resoft.credit.stockTaskReceive.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.common.utils.Constants;
import com.resoft.credit.stockTaskReceive.dao.StockTaskReceiveDao;
import com.resoft.credit.stockTaskReceive.entity.StockTaskReceive;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 股权任务接收表Service
 * @author jml
 * @version 2017-12-11
 */
@Service
@Transactional(readOnly = true)
public class StockTaskReceiveService extends CrudService<StockTaskReceiveDao, StockTaskReceive> {
	
	@Autowired
	private StockTaskReceiveDao stockTaskReceiveDao;
	
	public StockTaskReceive get(String id) {
		return super.get(id);
	}
	
	public List<StockTaskReceive> findList(StockTaskReceive stockTaskReceive) {
		return super.findList(stockTaskReceive);
	}
	
	public Page<StockTaskReceive> findPage(Page<StockTaskReceive> page, StockTaskReceive stockTaskReceive) {
		return super.findPage(page, stockTaskReceive);
	}
	
	@Transactional(readOnly = false)
	public void save(StockTaskReceive stockTaskReceive) {
		super.save(stockTaskReceive);
	}
	
	@Transactional(readOnly = false)
	public void delete(StockTaskReceive stockTaskReceive) {
		super.delete(stockTaskReceive);
	}

	public StockTaskReceive getReceiveByApplyNoAndGrade(String applyNo, String grade) {
		return dao.getReceiveByApplyNoAndGrade(applyNo,grade);
	}
	
	/**
	 * 查询股权分配表中记录的状态
	 * @param param
	 * @return
	 */
	public StockTaskReceive findCreStockOpinionByParam(Map<String, String> param){
		if("utask_fgsfksh".equalsIgnoreCase(param.get("taskDefKeyV"))){
			param.put("grade","4");
		}
		if("utask_qyfksh".equalsIgnoreCase(param.get("taskDefKeyV"))){
			param.put("grade","3");
		}
		if("utask_dqfkzysh".equalsIgnoreCase(param.get("taskDefKeyV"))){
			param.put("grade","2");
		}
		if("utask_zgsfksh".equalsIgnoreCase(param.get("taskDefKeyV"))){
			param.put("grade","1");
		}
		
		return stockTaskReceiveDao.findStockTaskReciveByParam(param);
	}
	
	/**
	 * 更新股权分配表状态
	 * @param param
	 */
	@Transactional(readOnly = false)
	public void updateStockTaskReciveState(Map<String,String> param){
		
			if("utask_fgsfksh".equalsIgnoreCase(param.get("taskDefKey")) || Constants.UTASK_FGSJLSH.equals(param.get("taskDefKey"))){
				if("back".equals(param.get("passFlag")) || "yes".equalsIgnoreCase(param.get("passFlag"))){
					param.put("grade","'4'");
				}	
			}
			if("utask_qyfksh".equalsIgnoreCase(param.get("taskDefKey")) || Constants.UTASK_QYJLSH.equals(param.get("taskDefKey"))){
				if("back".equals(param.get("passFlag")) || "yes".equalsIgnoreCase(param.get("passFlag"))){
					param.put("grade","'3'");
				}
			}
			if("utask_dqfkzysh".equalsIgnoreCase(param.get("taskDefKey")) || Constants.UTASK_DQFKJLSH.equals(param.get("taskDefKey"))){
				if("back".equals(param.get("passFlag")) || "yes".equalsIgnoreCase(param.get("passFlag"))){
					param.put("grade","'2'");
				}
			}
			if("utask_zgsfksh".equalsIgnoreCase(param.get("taskDefKey")) || Constants.UTASK_ZGSJLSH.equals(param.get("taskDefKey"))){
				if("back".equals(param.get("passFlag")) || "yes".equalsIgnoreCase(param.get("passFlag")) || "no".equalsIgnoreCase(param.get("passFlag")) || "black".equalsIgnoreCase(param.get("passFlag")) || "finish".equalsIgnoreCase(param.get("passFlag"))){
					param.put("grade","'1'");
				}
			}
		if("backToSQLR".equals(param.get("passFlag"))){
			if(Constants.UTASK_QYJLSH.equals(param.get("taskDefKey")) || "utask_qyfksh".equalsIgnoreCase(param.get("taskDefKey"))){
				param.put("grade","'4','3'");
			}
			if(Constants.UTASK_FGSJLSH.equals(param.get("taskDefKey")) || "utask_fgsfksh".equalsIgnoreCase(param.get("taskDefKey"))){
				param.put("grade","'4'");
			}
			if(Constants.UTASK_DQFKJLSH.equals(param.get("taskDefKey")) || "utask_dqfkzysh".equalsIgnoreCase(param.get("taskDefKey"))){
				param.put("grade","'4','3','2'");
			}
			if(Constants.UTASK_ZGSJLSH.equals(param.get("taskDefKey")) || Constants.UTASK_FGSSX.equals(param.get("taskDefKey")) || "utask_zgsfksh".equalsIgnoreCase(param.get("taskDefKey")) || Constants.UTASK_ZGSZJLSH.equals(param.get("taskDefKey"))){
				param.put("grade","'4','3','2','1'");
			}
		}
		if((Constants.UTASK_SQLR.equals(param.get("taskDefKey")) || Constants.UTASK_MS.equalsIgnoreCase(param.get("taskDefKey")) || Constants.UTASK_ZGSJLSH.equals(param.get("taskDefKey")) || Constants.UTASK_FGSSX.equals(param.get("taskDefKey")) || "utask_zgsfksh".equalsIgnoreCase(param.get("taskDefKey"))) && ("no".equalsIgnoreCase(param.get("passFlag")) || "black".equalsIgnoreCase(param.get("passFlag")) || "finish".equalsIgnoreCase(param.get("passFlag")))){
			param.put("grade","'4','3','2','1'");
		}
		if(StringUtils.isNotBlank(param.get("grade"))){
			stockTaskReceiveDao.updateStockTaskReciveState(param);
		}
	}

	public void deleteStockInfoByApplyGrade(String applyNo, String grade) {
		dao.deleteStockInfoByApplyGrade(applyNo,grade);
	}

	public StockTaskReceive getById(String stockTaskReceiveId) {
		return dao.getById(stockTaskReceiveId);
	}
}