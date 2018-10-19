package com.resoft.accounting.staContractStatus.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.resoft.outinterface.rest.ged.entity.GedAdvanceRepayDetail;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.resoft.accounting.contract.web.ExcelUtil;
import com.resoft.accounting.staContractStatus.dao.StaContractStatusDao;
import com.resoft.accounting.staContractStatus.entity.DeductResultTemp;
import com.resoft.accounting.staContractStatus.entity.StaContractStatus;
import com.resoft.accounting.staContractStatus.entity.StaContractStatusImport;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 合同还款明细查询Service
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@Service
@DbType("acc.dbType")
@Transactional(value = "ACC", readOnly = true)
public class StaContractStatusService extends CrudService<StaContractStatusDao, StaContractStatus> {

	private static final Logger logger = Logger.getLogger(StaContractStatusService.class);

	public StaContractStatus get(String id) {
		return super.get(id);
	}

	public List<StaContractStatus> findList(StaContractStatus staContractStatus) {
		return super.findList(staContractStatus);
	}

	public Page<StaContractStatus> findPage(Page<StaContractStatus> page, StaContractStatus staContractStatus) {
		return super.findPage(page, staContractStatus);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void save(StaContractStatus staContractStatus) {
		super.save(staContractStatus);
	}

	// @Transactional(value = "ACC", readOnly = false)
	// public void delete(StaContractStatus staContractStatus) {
	// super.delete(staContractStatus);
	// }

	@Transactional(value = "ACC", readOnly = false)
	public List<DeductResultTemp> queryDeductResult(String contractNo,String periodNum) {
		return this.dao.queryDeductResult(contractNo,periodNum);
	}
	
	@Transactional(value = "ACC", readOnly = false)
	public List<DeductResultTemp> queryDeductResultByParams(String contractNo
				,Date startDate,Date endDate,String repayPeriodStatus) {
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(contractNo)){
			params.put("contractNo", contractNo);
		}
		if(startDate != null){
			params.put("startDate", startDate);
		}
		if(endDate != null){
			params.put("endDate", endDate);
		}
		if(StringUtils.isNotEmpty(repayPeriodStatus)){
			String[]repayPeriodStatusList = repayPeriodStatus.split(",");
//			List<String>repayPeriodStatusList = new ArrayList<String>();
//			for(String str : status){
//				repayPeriodStatusList.add(str);
//			}
			params.put("repayPeriodStatusList", repayPeriodStatusList);
		}
		return this.dao.queryDeductResultByParams(params);
	}

	public String getOfficeLevel(String id) {
		return this.dao.getOfficeLevel(id);

	}

	@Transactional(value = "ACC", readOnly = false)
	public String importData(MultipartFile file) {
		String errerMessage = null;
		InputStream is = null;
		try {
			is = file.getInputStream();
		} catch (IOException e) {
			logger.error("获取文件输入流失败", e);
			errerMessage = e.getMessage();
			return errerMessage;
		}
		String fileName = file.getOriginalFilename();
		ExcelUtil excelUtil = new ExcelUtil();
		StaContractStatusImport staContractStatusImport = new StaContractStatusImport();
		List<Object> staContractStatusImports = excelUtil.importDataFromExcel(staContractStatusImport, is, fileName,"staContractStatusImport");
		if (staContractStatusImports != null && staContractStatusImports.size() != 0) {
			for (int i = 0; i < staContractStatusImports.size(); i++) {
				StaContractStatusImport tmp = (StaContractStatusImport) staContractStatusImports.get(i);
				try {
					boolean flag = dealStaContractStatusData(tmp);
					if (flag) {
						importStaContractStatus(tmp);
					}else{
						updateimportedStaContractStatus(tmp);
					}
				} catch (Exception e) {
					logger.error("合同还款统计汇总导入数据库失败", e);
					errerMessage = "合同号为" + tmp.getContractNo() + "的数据，出现问题,已导入成功"+i+"条";
					return errerMessage;
				}
			}
		} else {
			errerMessage = "导入出现问题，请仔细核对规范，若再次出现请联系管理员";
		}
		return errerMessage;
	}
	

	boolean dealStaContractStatusData(StaContractStatusImport tmp) {
		boolean flag = false;
		StaContractStatus staContractStatus = getStaContractStatusByContractNo(tmp.getContractNo());
		if (staContractStatus == null) {
			flag = true;
		}
		return flag;
	}
	

	/**
	 * 还款计划导入
	 */
	public void importStaContractStatus(StaContractStatusImport staContractStatusImport) {
		this.dao.importStaContractStatus(staContractStatusImport);
	}
	/**
	 * 还款计划更新
	 */
	public void updateimportedStaContractStatus(StaContractStatusImport staContractStatusImport){
		this.dao.updateimportedStaContractStatus(staContractStatusImport);
	}
	/**
	 * 根据合同号查询统计数据
	 */
	public StaContractStatus getStaContractStatusByContractNo(String contractNo){
		return this.dao.getStaContractStatusByContractNo(contractNo);
	}

    public List<GedAdvanceRepayDetail> getAdvanceDetailGED(String contractNo) {
		return dao.getAdvanceDetailGED(contractNo);
    }
    
    @Transactional(value = "ACC", readOnly = false)
    public void updateContractStatus(String contractNo){
    	this.dao.updateContractStatus(contractNo);
    }
    
    public StaContractStatus getContractByContractNo(String contractNo){
    	return dao.getContractByContractNo(contractNo);
    }
}