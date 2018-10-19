package com.resoft.accounting.repayPlan.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountDCResponse;
import com.resoft.Accoutinterface.utils.AccFacade;
import com.resoft.Accoutinterface.utils.AccFinancialPlatformUtils;
import com.resoft.accounting.advanceRepayGET.entity.AdvanceRepayGet;
import com.resoft.accounting.common.utils.DateUtils;
import com.resoft.accounting.contract.dao.AccContractDao;
import com.resoft.accounting.contract.entity.Contract;
import com.resoft.accounting.contract.web.ExcelUtil;
import com.resoft.accounting.repayPlan.dao.AccRepayPlanDao;
import com.resoft.accounting.repayPlan.entity.AccRepayPlan;
import com.resoft.accounting.repayPlan.entity.RepayPlanImport;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.JsonUtil;
/**
 * 应还款查询Service
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@Service
@DbType("acc.dbType")
@Transactional(value = "ACC", readOnly = true)
public class AccRepayPlanService extends CrudService<AccRepayPlanDao, AccRepayPlan> {

	@Autowired
	private AccContractDao accContractDao;

	private static final Logger logger = Logger.getLogger(AccRepayPlanService.class);

	public AccRepayPlan get(String id) {
		return super.get(id);
	}

	public List<AccRepayPlan> findList(AccRepayPlan repayPlan) {
		return super.findList(repayPlan);
	}

	public Page<AccRepayPlan> findPage(Page<AccRepayPlan> page, AccRepayPlan repayPlan) {
		return super.findPage(page, repayPlan);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void save(AccRepayPlan repayPlan) {
		super.save(repayPlan);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void delete(AccRepayPlan repayPlan) {
		super.delete(repayPlan);
	}

	@Transactional(value = "ACC", readOnly = false)
	public List<AccRepayPlan> findRepayPlanByContractNo(String contractNo) {
		return this.dao.findRepayPlanByContractNo(contractNo);
	}

	@Transactional(value = "ACC", readOnly = false)
	public List<AccRepayPlan> getRepayPlanByContractNo(String contractNo) {
		return this.dao.getRepayPlanByContractNo(contractNo);
	}

	@Transactional(value = "ACC", readOnly = false)
	public List<AccRepayPlan> getDataInQueryRepayPlan(Map<String, String> params) {
		return this.dao.getDataInQueryRepayPlan(params);
	}

	@Transactional(value = "ACC", readOnly = false)
	public List<Map<String, String>> findRepayPlanToStaRepayPlanStatusByContractNo(String contractNo) {
		return this.dao.findRepayPlanToStaRepayPlanStatusByContractNo(contractNo);
	}

	/**
	 * 还款计划导入
	 */
	@Transactional(value = "ACC", readOnly = false)
	public void importRepayPlan(RepayPlanImport repayPlanImport) {
		this.dao.importRepayPlan(repayPlanImport);
	}

	/**
	 * 还款计划更新
	 */
	@Transactional(value = "ACC", readOnly = false)
	public void updateimportedRepayPlan(RepayPlanImport repayPlanImport){
		this.dao.updateimportedRepayPlan(repayPlanImport);
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
		RepayPlanImport repayPlanImport = new RepayPlanImport();
		List<Object> repayPlanImports = excelUtil.importDataFromExcel(repayPlanImport, is, fileName,"repayPlanImport");
		if (repayPlanImports != null && repayPlanImports.size() != 0) {
			for (int i = 0; i < repayPlanImports.size(); i++) {
				RepayPlanImport tmp = (RepayPlanImport) repayPlanImports.get(i);
				try {
					tmp.setCreateTime(DateUtils.formatDateTime(new Date()));
					boolean flag = dealRepayPlanData(tmp);
					if (flag) {
						importRepayPlan(tmp);
					}else{
						updateimportedRepayPlan(tmp);
					}
				} catch (Exception e) {
					logger.error("合同还款计划导入数据库失败", e);
					errerMessage = "合同号为" + tmp.getContractNo() + "，期数为" + tmp.getPeriodNum() + "的数据出现问题,已导入成功"+i+"条";
					return errerMessage;
				}
			}
		} else {
			errerMessage = "导入出现问题，请仔细核对规范，若再次出现请联系管理员";
		}
		return errerMessage;
	}
	
	boolean dealRepayPlanData(RepayPlanImport tmp){
		boolean flag = false;
		Map<String, String> param = Maps.newHashMap();
		param.put("contractNo", tmp.getContractNo());
		param.put("periodNum", tmp.getPeriodNum());
		List<AccRepayPlan> accRepayPlans = getDataInQueryRepayPlan(param);
		if (accRepayPlans.size() == 0) {
			flag = true;
		}
		return flag;
	}
	
	public List<String> getDataInQueryRepayPlanByContractAndPeriod(Map<String,String> pamram,BigDecimal amount){
		List<AccRepayPlan> accRepayPlanList = this.dao.getDataInQueryRepayPlanByContractAndPeriod(pamram);
		List<String> period = new ArrayList<String>();
		if(accRepayPlanList.size() > 0 ){
			for(int i= 0; i<accRepayPlanList.size();i++){
				if(amount.compareTo(new BigDecimal(accRepayPlanList.get(i).getRepayAmount())) == 1 || amount.compareTo(new BigDecimal(accRepayPlanList.get(i).getRepayAmount())) == 0){
					amount = amount.subtract(new BigDecimal(accRepayPlanList.get(i).getRepayAmount()));
					period.add(i, accRepayPlanList.get(i).getPeriodNum());
				}
				if (amount.compareTo(new BigDecimal(accRepayPlanList.get(i).getRepayAmount())) == -1 || amount.compareTo(new BigDecimal("0.00")) == 0) {
					break;
				}
			}
		}
		return period;
	}
	
	
	
	public AccRepayPlan findAccRepayByConAndNumber(String contractNo,String periodNum){
		return this.dao.findAccRepayByConAndNumber(contractNo,periodNum);
	}

	/**
	 *  查询本息总额 还有 管理费总额
	 * @param repayPlan
	 * @return
	 */
	public AccRepayPlan selectAccRepayPlanByXinxi(AccRepayPlan repayPlan){
		return super.dao.selectAccRepayPlanByXinxi(repayPlan);

	}

    public AccRepayPlan getCurrentRepayPlan(Date date,String contractNo) {
		return dao.getCurrentRepayPlan(date,contractNo);
    }

	/**
	 * 根据合同编号查询用户实换的管理费金额
	 */
	public BigDecimal queryFactServiceFee(String ContractNo){
		return super.dao.queryFactServiceFee(ContractNo);
	}
	/**
	 * 充值服务费
	 */
	public void compensation(Map<String,Object> paramMap){
		String contractNo = (String)paramMap.get("contractNo");
		String custId = (String)paramMap.get("cust_id");
		BigDecimal oldfactServiceFee = (BigDecimal) paramMap.get("oldfactServiceFee");//之前服务费
		BigDecimal nowFactServiceFee = super.dao.queryFactServiceFee(contractNo); //现在服务费
		logger.info("之前服务费==========="+oldfactServiceFee);
		logger.info("现在服务费=================>"+nowFactServiceFee);
			if(!nowFactServiceFee.equals(oldfactServiceFee)) {  //现在服务费 > 之前服务费 要给资金平台充值
			Contract accContract = accContractDao.queryOrglevel(contractNo);
			BigDecimal serviceFee = nowFactServiceFee.subtract(oldfactServiceFee);
				logger.info("用户充值的服务费============>" + serviceFee);
/*	 			//增加征信服务费分润 服务费按6:4 比例进行拆分 6是冠君征信,4是资金平台账务
				BigDecimal subServiceFee = serviceFee.divide(new BigDecimal("10"));
				BigDecimal guanServiceFee = subServiceFee.multiply(new BigDecimal("6"));//6是冠君征信
				BigDecimal ziJinServiceFee = subServiceFee.multiply(new BigDecimal("4"));//4是资金平台账务
				logger.info("冠君征信入账======>" + guanServiceFee);
				logger.info("资金平台入账=======>" + ziJinServiceFee);*/
			Map<String, String> map = new ConcurrentHashMap<>();
			if (accContract != null) {
				if (accContract.getOrgLevel4() != null) {
					map.put("filiale", accContract.getOrgLevel4().getName());//所属分公司
				}
				if (accContract.getOrgLevel2() != null) {
					map.put("region", accContract.getOrgLevel2().getName());//所属大区
				}
			}
			if ("10040001".equals(accContract.getLoanPlatform()) || "10040002".equals(accContract.getLoanPlatform()) || "10040003".equals(accContract.getLoanPlatform())) {
				//北京 上海 天津
				map.put("platform", accContract.getLoanPlatform());//所属平台
			} else {
				map.put("platform", "10040099");//所属平台
			}
			map.put("amt", String.valueOf(serviceFee));//交易金额
			map.put("cust_no", custId);//客户编号

			map.put("mchn", Global.getConfig("FPMchn")); //商户号
			map.put("seq_no", AccFinancialPlatformUtils.makeSeqNo());//交易流水号 19位
			map.put("trade_type", "11060003");//交易类型
			map.put("busi_type", "1");//账户类型 1 代表借款人
			map.put("accounts_type", "21992101");//账务类型    21992101是服务费
			String json = null;
			AccAccountDCResponse response = null;
				try {
				json = JsonUtil.getJSONString(map);
				logger.info("充值服务费给资金平台:----" + json);
				response = AccFacade.facade.repaymentCompensation(json, contractNo);
				//resp_code  0000成功,其他见返回码表
				if (response != null) {
					if (response.getResp_code().equals("0000")) {
						/*//在给冠君征信充值
						map.put("amt",String.valueOf(guanServiceFee)); //拆分后的冠君征信的金额
						map.put("trade_type","11060010"); // 交易类型冠君征信服务费
						map.put("seq_no", AccFinancialPlatformUtils.makeSeqNo());//交易流水号 19位
						json = JsonUtil.getJSONString(map);
						logger.info("充值服务费给冠君征信:---"+json);
						response = 	AccFacade.facade.repaymentCompensation(json, contractNo);
						logger.info("充值服务费给冠君证信返回信息----"+response.getResp_msg());*/
						logger.info("成功");
					} else {
						logger.info("失败---------------------------------");
						logger.info(response.getResp_msg());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<AccRepayPlan> getAdvanceRepayPlan(String contractNo,String periodNum,String date){
    	return this.dao.getAdvanceRepayPlan(contractNo,periodNum,date);

    }
    
    public AdvanceRepayGet queryStayMoney(String contractNo,String date){
    	return this.dao.queryStayMoney(contractNo,date);
    }
    
    public List<AccRepayPlan> queryAccRepayPlan(String contractNo,String periodNum){
    	return this.dao.queryAccRepayPlan(contractNo,periodNum);
    }

}