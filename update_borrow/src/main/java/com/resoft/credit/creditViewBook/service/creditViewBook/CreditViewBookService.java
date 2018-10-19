package com.resoft.credit.creditViewBook.service.creditViewBook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.creditAndLine.entity.creditAnalysis.CreditAnalysis;
import com.resoft.credit.creditAndLine.entity.creditAnalysisExtend.CreditAnalysisExtend;
import com.resoft.credit.creditAndLine.entity.creditAnalysisMostExtend.CreditAnalysisMostExtend;
import com.resoft.credit.creditAndLine.entity.creditAnalysisMostExtends.CreditAnalysisMostExtends;
import com.resoft.credit.creditAndLine.service.creditAnalysis.CreditAnalysisService;
import com.resoft.credit.creditAndLine.service.creditAnalysisExtend.CreditAnalysisExtendService;
import com.resoft.credit.creditAndLine.service.creditAnalysisMostExtend.CreditAnalysisMostExtendService;
import com.resoft.credit.creditAndLine.service.creditAnalysisMostExtends.CreditAnalysisMostExtendsService;
import com.resoft.credit.creditViewBook.entity.CreditViewBook.CreditViewBook;
import com.resoft.credit.creditViewBook.entity.creditOtherInfo.CreditOtherInfo;
import com.resoft.credit.creditViewBook.service.creditOtherInfo.CreditOtherInfoService;
import com.resoft.credit.creditViewBook.dao.creditViewBook.CreditViewBookDao;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.credit.fdfs.util.StringUtils;

/**
 * 征信意见书其他信息Service
 * 
 * @author wuxi01
 * @version 2016-02-29
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CreditViewBookService extends CrudService<CreditViewBookDao, CreditViewBook> {

	@Autowired
	private ApplyRegisterService applyRegisterService;
	
	@Autowired
	private CreditOtherInfoService creditOtherInfoService;

	@Autowired
	private CreditAnalysisService creditAnalysisService;
	
	@Autowired
	private CreditAnalysisExtendService creditAnalysisExtendService;
	
	@Autowired
	private CreditAnalysisMostExtendsService creditAnalysisMostExtendsService;
	
	@Autowired
	private CreditAnalysisMostExtendService creditAnalysisMostExtendService;

	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;

	public CreditViewBook get(String id) {
		return super.get(id);
	}

	@Transactional(value="CRE",readOnly = false)
	public void save(CreditViewBook creditViewBook) { 

		ProcessSuggestionInfo processSuggestionInfo = new ProcessSuggestionInfo();
		ProcessSuggestionInfo hasProcessSuggestionInfo = new ProcessSuggestionInfo();
        CreditOtherInfo  hasCreditOtherInfo = new CreditOtherInfo();
        CreditAnalysis  hasCreditAnalysis = new CreditAnalysis();
		// 分公司风控审核
		if (Constants.UTASK_FGSFKSH.equalsIgnoreCase(creditViewBook.getTaskDefKey())) {
			
			creditViewBook.getCreditOtherInfo().setApplyNo(creditViewBook.getApplyNo());
			//申请人在用款企业的出资年限，向下取整，比如2.8年，取2年
			String capitalContributionPeriod = creditViewBook.getCreditOtherInfo().getCapitalContributionPeriod();
			double capitalContributionPeriodD = Math.floor(Float.parseFloat(capitalContributionPeriod));
			creditViewBook.getCreditOtherInfo().setCapitalContributionPeriod(capitalContributionPeriodD + "");
			hasCreditOtherInfo = creditOtherInfoService.getCreditOtherInfoByApplyNo(creditViewBook.getApplyNo());
			if(hasCreditOtherInfo!=null){
				creditViewBook.getCreditOtherInfo().preUpdate();
				creditOtherInfoService.updateByApplyNo(creditViewBook.getCreditOtherInfo());
			}else{
				creditOtherInfoService.save(creditViewBook.getCreditOtherInfo());
			}
			
			
			creditViewBook.getCreditAnalysis().setApplyNo(creditViewBook.getApplyNo());
			hasCreditAnalysis = creditAnalysisService.getCreditAnalysisByApplyNo(creditViewBook.getApplyNo());
			//引号、破折号转义
			CreditAnalysis creditAnalysis = creditViewBook.getCreditAnalysis();
			//行业状况分析
			creditAnalysis.setCategoryDesc(StringUtils.getTransferString(creditAnalysis.getCategoryDesc()));
			//------------------
			
			try { 
				ApplyRegister applyRegister= applyRegisterService.getApplyRegisterByApplyNo(creditViewBook.getApplyNo());
				Date createDate = applyRegister.getRegisterDate();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			    Date dateFlag = sdf.parse("2017-8-30 00:00:00"); 
			    boolean isbeforer = createDate.before(dateFlag);
				if(!isbeforer){
					creditViewBook.getCreditAnalysisExtend().setApplyNo(creditViewBook.getApplyNo());
					CreditAnalysisExtend creditAnalysisExtend = creditViewBook.getCreditAnalysisExtend();
					CreditAnalysisExtend hascreditAnalysisExtend = new CreditAnalysisExtend();
					hascreditAnalysisExtend = creditAnalysisExtendService.getCreditAnalysisExtendByApplyNo(creditViewBook.getApplyNo());
					creditAnalysisExtend=removeTrim(creditAnalysisExtend);
					creditViewBook.setCreditAnalysisExtend(creditAnalysisExtend);
					if(hascreditAnalysisExtend!=null){
						creditViewBook.getCreditAnalysisExtend().preUpdate();
						creditAnalysisExtendService.updateByApplyNo(creditViewBook.getCreditAnalysisExtend());
					}else{
						creditAnalysisExtendService.save(creditViewBook.getCreditAnalysisExtend());
					}
					
					creditViewBook.getCreditAnalysisMostExtend().setApplyNo(creditViewBook.getApplyNo());
					CreditAnalysisMostExtend creditAnalysisMostExtend = creditViewBook.getCreditAnalysisMostExtend();
					CreditAnalysisMostExtend hascreditAnalysisMostExtend = new CreditAnalysisMostExtend();
					hascreditAnalysisMostExtend = creditAnalysisMostExtendService.getCreditAnalysisMostExtendByApplyNo(creditViewBook.getApplyNo());
					creditAnalysisMostExtend=removeTrim(creditAnalysisMostExtend);
					creditViewBook.setCreditAnalysisMostExtend(creditAnalysisMostExtend);
					if(hascreditAnalysisMostExtend!=null){
						creditViewBook.getCreditAnalysisMostExtend().preUpdate();
						creditAnalysisMostExtendService.updateByApplyNo(creditViewBook.getCreditAnalysisMostExtend());
					}else{
						creditAnalysisMostExtendService.save(creditViewBook.getCreditAnalysisMostExtend());
					}
					
					
					creditViewBook.getCreditAnalysisMostExtends().setApplyNo(creditViewBook.getApplyNo());
					CreditAnalysisMostExtends creditAnalysisMostExtends = creditViewBook.getCreditAnalysisMostExtends();
					CreditAnalysisMostExtends hascreditAnalysisMostExtends = new CreditAnalysisMostExtends();
					hascreditAnalysisMostExtends = creditAnalysisMostExtendsService.getCreditAnalysisMostExtendsByApplyNo(creditViewBook.getApplyNo());
					creditAnalysisMostExtends=removeTrim(creditAnalysisMostExtends);
					creditViewBook.setCreditAnalysisMostExtends(creditAnalysisMostExtends);
					if(hascreditAnalysisMostExtends!=null){
						creditViewBook.getCreditAnalysisMostExtends().preUpdate();
						creditAnalysisMostExtendsService.updateByApplyNo(creditViewBook.getCreditAnalysisMostExtends());
					}else{
						creditAnalysisMostExtendsService.save(creditViewBook.getCreditAnalysisMostExtends());
					}
					
					
				}else{
					//借款用途说明
					creditAnalysis.setLoanPurposeDesc(StringUtils.getTransferString(creditAnalysis.getLoanPurposeDesc()));
					//借款企业情况分析
					creditAnalysis.setCompanyDesc(StringUtils.getTransferString(creditAnalysis.getCompanyDesc()));
					//还款来源分析
					creditAnalysis.setLoanRepayDesc(StringUtils.getTransferString(creditAnalysis.getLoanRepayDesc()));
					//担保情况分析
					creditAnalysis.setGuaranteeDesc(StringUtils.getTransferString(creditAnalysis.getGuaranteeDesc()));
					//风险点
					creditAnalysis.setRiskPoint(StringUtils.getTransferString(creditAnalysis.getRiskPoint()));
					//优势
					creditAnalysis.setEdgeComment(StringUtils.getTransferString(creditAnalysis.getEdgeComment()));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			//----------------------
			creditViewBook.setCreditAnalysis(creditAnalysis);
			if(hasCreditAnalysis!=null){
				creditViewBook.getCreditAnalysis().preUpdate();
				creditAnalysisService.updateByApplyNo(creditViewBook.getCreditAnalysis());
			}else{
				creditAnalysisService.save(creditViewBook.getCreditAnalysis());
			}
			processSuggestionInfo.setCreditDesc(creditViewBook.getSuggestionBranch());
		}

		// 区域风控审核
		if (Constants.UTASK_QYFKSH.equalsIgnoreCase(creditViewBook.getTaskDefKey())) {
			processSuggestionInfo.setCreditDesc(creditViewBook.getSuggestionArea());
		}

		// 大区风控审核
		if (Constants.UTASK_DQFKSH.equalsIgnoreCase(creditViewBook.getTaskDefKey())) {
			processSuggestionInfo.setCreditDesc(creditViewBook.getSuggestionLargeArea());
		}

		// 总公司风控审核
		if (Constants.UTASK_ZGSFKSH.equalsIgnoreCase(creditViewBook.getTaskDefKey())) {
			processSuggestionInfo.setCreditDesc(creditViewBook.getSuggestionHead());
		}

		processSuggestionInfo.setApplyNo(creditViewBook.getApplyNo());
		processSuggestionInfo.setTaskDefKey(creditViewBook.getTaskDefKey());

		Map<String, String> params = Maps.newHashMap();
		params.put("applyNo", creditViewBook.getApplyNo());
		params.put("taskDefKey", creditViewBook.getTaskDefKey());
		hasProcessSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
		if(hasProcessSuggestionInfo!=null){
			processSuggestionInfoService.updateByApplyNo(processSuggestionInfo);
		}else{
			processSuggestionInfoService.save(processSuggestionInfo);
		}
		
	}  

	private CreditAnalysisMostExtends removeTrim(CreditAnalysisMostExtends creditAnalysisMostExtends) {
		creditAnalysisMostExtends.setCoreValue(StringUtils.getTransferString(creditAnalysisMostExtends.getCoreValue()));
		creditAnalysisMostExtends.setCoreAdvantage(StringUtils.getTransferString(creditAnalysisMostExtends.getCoreAdvantage()));
		creditAnalysisMostExtends.setCoreGuarantee(StringUtils.getTransferString(creditAnalysisMostExtends.getCoreGuarantee()));
		creditAnalysisMostExtends.setCoreMeasures(StringUtils.getTransferString(creditAnalysisMostExtends.getCoreMeasures()));
		creditAnalysisMostExtends.setPolicyRiskAnalysis(StringUtils.getTransferString(creditAnalysisMostExtends.getPolicyRiskAnalysis()));
		creditAnalysisMostExtends.setOperateRiskAnalysis(StringUtils.getTransferString(creditAnalysisMostExtends.getOperateRiskAnalysis()));
		creditAnalysisMostExtends.setCreditRiskAnalysis(StringUtils.getTransferString(creditAnalysisMostExtends.getCreditRiskAnalysis()));
		creditAnalysisMostExtends.setUnexpectedRiskAnalysis(StringUtils.getTransferString(creditAnalysisMostExtends.getUnexpectedRiskAnalysis()));
		return creditAnalysisMostExtends;
	}

	private CreditAnalysisMostExtend removeTrim(CreditAnalysisMostExtend creditAnalysisMostExtend) {
		creditAnalysisMostExtend.setApplicationDetails(StringUtils.getTransferString(creditAnalysisMostExtend.getApplicationDetails()));
		creditAnalysisMostExtend.setVerifyMethod(StringUtils.getTransferString(creditAnalysisMostExtend.getVerifyMethod()));
		creditAnalysisMostExtend.setVerifyBasis(StringUtils.getTransferString(creditAnalysisMostExtend.getVerifyBasis()));
		creditAnalysisMostExtend.setRepaymentSourceBasis(StringUtils.getTransferString(creditAnalysisMostExtend.getRepaymentSourceBasis()));
		creditAnalysisMostExtend.setSecondRepaymentSource(StringUtils.getTransferString(creditAnalysisMostExtend.getSecondRepaymentSource()));
		creditAnalysisMostExtend.setOtherRepaymentSource(StringUtils.getTransferString(creditAnalysisMostExtend.getOtherRepaymentSource()));
		creditAnalysisMostExtend.setGuaranteeDetail(StringUtils.getTransferString(creditAnalysisMostExtend.getGuaranteeDetail()));
		creditAnalysisMostExtend.setGuaranteeCorporation(StringUtils.getTransferString(creditAnalysisMostExtend.getGuaranteeCorporation()));
		return creditAnalysisMostExtend;
	}

	private CreditAnalysisExtend removeTrim(CreditAnalysisExtend creditAnalysisExtend) {
		creditAnalysisExtend.setTurnoverProfit(StringUtils.getTransferString(creditAnalysisExtend.getTurnoverProfit()));
		creditAnalysisExtend.setTfBasis(StringUtils.getTransferString(creditAnalysisExtend.getTfBasis()));
		creditAnalysisExtend.setStaffNumberDesc(StringUtils.getTransferString(creditAnalysisExtend.getStaffNumberDesc()));
		creditAnalysisExtend.setSndBasis(StringUtils.getTransferString(creditAnalysisExtend.getSndBasis()));
		creditAnalysisExtend.setSaleProfitDesc(StringUtils.getTransferString(creditAnalysisExtend.getSaleProfitDesc()));
		creditAnalysisExtend.setSpdBasis(StringUtils.getTransferString(creditAnalysisExtend.getSpdBasis()));
		creditAnalysisExtend.setCompanyDebt(StringUtils.getTransferString(creditAnalysisExtend.getCompanyDebt()));
		creditAnalysisExtend.setCdBasis(StringUtils.getTransferString(creditAnalysisExtend.getCdBasis()));
		return creditAnalysisExtend;
	}
}