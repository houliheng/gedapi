package com.resoft.common.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.resoft.credit.applyRegister.dao.ApplyRegisterDao;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRelation.dao.ApplyRelationDao;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.checkApprove.dao.CheckApproveDao;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApproveUnion.dao.CheckApproveUnionDao;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.checkApproveUnion.service.CheckApproveUnionService;
import com.resoft.credit.checkFee.dao.CheckFeeDao;
import com.resoft.credit.checkFee.entity.CheckFee;
import com.resoft.credit.contract.dao.ContractDao;
import com.resoft.credit.contract.entity.RepayPlanData;
import com.resoft.credit.creditAndLine.dao.creditAnalysis.CreditAnalysisDao;
import com.resoft.credit.creditAndLine.entity.creditAnalysis.CreditAnalysis;
import com.resoft.credit.gqgetAssertHouse.dao.GqgetAssetHouseDao;
import com.resoft.credit.gqgetAssertHouse.entity.GqgetAssetHouse;
import com.resoft.credit.gqgetAssetCar.dao.GqgetAssetCarDao;
import com.resoft.credit.gqgetAssetCar.entity.GqgetAssetCar;
import com.resoft.credit.gqgetAssetCarUnion.dao.GqgetAssetCarUnionDao;
import com.resoft.credit.gqgetAssetHouseUnion.dao.GqgetAssetHouseUnionDao;
import com.resoft.credit.gqgetComInfo.dao.BankLoanDao;
import com.resoft.credit.gqgetComInfo.dao.GqgetComInfoDao;
import com.resoft.credit.gqgetComInfo.dao.MortageEquipmentDao;
import com.resoft.credit.gqgetComInfo.dao.WarehouseGoodsDao;
import com.resoft.credit.gqgetComInfo.entity.BankLoan;
import com.resoft.credit.gqgetComInfo.entity.GqgetComInfo;
import com.resoft.credit.gqgetComInfo.entity.MortageEquipment;
import com.resoft.credit.gqgetComInfo.entity.WarehouseGoods;
import com.resoft.credit.gqgetComInfoUnion.entity.GqgetComInfoUnion;
import com.resoft.credit.gqgetComInfoUnion.service.GqgetComInfoUnionService;
import com.resoft.credit.gqgetGuarantorCompany.dao.GqgetGuarantorCompanyDao;
import com.resoft.credit.gqgetGuarantorCompany.entity.GqgetGuarantorCompany;
import com.resoft.credit.mortageEquipmentUnion.dao.MortageEquipmentUnionDao;
import com.resoft.credit.mortgagedCompanyUnion.dao.MortgagedCompanyUnionDao;
import com.resoft.credit.rateCapital.entity.RateCapital;
import com.resoft.credit.repayPlan.dao.RepayPlanDao;
import com.resoft.credit.repayPlanUnion.dao.RepayPlanUnionDao;
import com.resoft.credit.repayPlanUnion.entity.RepayPlanUnion;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.utils.StringUtils;

@Service("JointCreditService")
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class JointCredit {

	@Autowired
	private CheckApproveDao checkApproveDao;
	@Autowired
	private CheckFeeDao checkFeeDao;
	@Autowired
	private BankLoanDao bankLoanDao;
	@Autowired
	private RepayPlanDao repayPlanDao;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private RepayPlanUnionDao repayPlanUnionDao;
	@Autowired
	private CheckApproveUnionService checkApproveUnionService;
	@Autowired
	private GqgetComInfoUnionService gqgetComInfoUnionService;
	@Autowired
	private ApplyRelationDao applyRelationDao;
	@Autowired
	private GqgetComInfoDao gqgetComInfoDao;
	@Autowired
	private GqgetAssetCarDao gqgetAssetCarDao;
	@Autowired
	private GqgetAssetCarUnionDao gqgetAssetCarUnionDao;
	@Autowired
	private GqgetAssetHouseDao gqgetAssetHouseDao;
	@Autowired
	private GqgetAssetHouseUnionDao gqgetAssetHouseUnionDao;
	@Autowired
	private MortageEquipmentDao mortageEquipmentDao;
	@Autowired
	private MortageEquipmentUnionDao mortageEquipmentUnionDao;
	@Autowired
	private GqgetGuarantorCompanyDao gqgetGuarantorCompanyDao;
	@Autowired
	private MortgagedCompanyUnionDao mortgagedCompanyUnionDao;
	@Autowired
	private WarehouseGoodsDao warehouseGoodsDao;
	@Autowired
	private CheckApproveUnionDao checkApproveUnionDao;
	@Autowired
	private CreditAnalysisDao creditDao;
	@Autowired
	private ApplyRegisterDao applyRegisterDao;
	
	private static final Logger logger = LoggerFactory.getLogger(JointCredit.class);
	@Transactional(value = "CRE", readOnly = false)
	public void resoluteContract(ActTaskParam actTaskParam, String deleteFlag) throws Exception {
		logger.info("-------------------------------------------进入拆分开始"+actTaskParam.getApplyNo());
		String applyNo = actTaskParam.getApplyNo();
		int count = 0;
		// 取出总公司批复信息
		Map<String, String> param = Maps.newHashMap();
		param.put("applyNo", applyNo);
		List<CheckApprove> checkApproves = checkApproveDao.getCheckApproveByApplyNo(param);
		if (checkApproves != null && checkApproves.size() != 0) {
			logger.info("-------------------------------------------开始拆分"+actTaskParam.getApplyNo());
			CheckApprove checkApprove = checkApproves.get(0);
			if ("no".equalsIgnoreCase(deleteFlag)) {
				dealPartData(applyNo, checkApprove);
			} else {
				BigDecimal allContractAmount = checkApprove.getContractAmount();
				logger.info("-------------------------------------------拆分总金额"+allContractAmount);
				BigDecimal allCount = allContractAmount.divide(new BigDecimal(1000000), 0, BigDecimal.ROUND_UP);
				// 主借企业，担保企业列表
				List<ApplyRelation> applyRelationCompanies = applyRelationDao.getCompanyListByApplyNo(applyNo);
				// 保证钱在100w以上
				// if (allCount.intValue() > 1) {
				// 保证关系企业的数量不小于（钱/1000000）的数量
				if (allCount.compareTo(new BigDecimal(applyRelationCompanies.size())) != 1) {
					logger.info("-------------------------------------------进行拆分出数量"+allCount+"-"+applyRelationCompanies.size());
					// 冠e通总信息
					GqgetComInfo gqgetComInfo = gqgetComInfoDao.getGqgetComInfoByApplyNo(applyNo);
					BankLoan bankLoan = bankLoanDao.getBankLoanByApplyNo(gqgetComInfo.getApplyNo());
					List<GqgetAssetCar> gqgetAssetCars = Lists.newArrayList();
					List<GqgetAssetHouse> gqgetAssetHouses = Lists.newArrayList();
					List<MortageEquipment> mortageEquipments = Lists.newArrayList();
					List<GqgetGuarantorCompany> gqgetGuarantorCompanies = Lists.newArrayList();
					if (Constants.PRODUCT_TYPE_XY.equals(gqgetComInfo.getProductType())
							|| Constants.PRODUCT_TYPE_CG.equals(gqgetComInfo.getProductType())) {
						gqgetAssetCars = gqgetAssetCarDao.findGqgetAssetCarDataByApplyNo(applyNo);
						gqgetAssetHouses = gqgetAssetHouseDao.findGqgetAssetHouseDataByApplyNo(applyNo);
					} else {
						mortageEquipments = mortageEquipmentDao.getPageByApplyNo(applyNo);
						if (Constants.PRODUCT_TYPE_HH.equals(gqgetComInfo.getProductType())) {
							gqgetGuarantorCompanies = gqgetGuarantorCompanyDao
									.findGqgetGuarantorCompanyDataByApplyNo(applyNo);
						}
					}
					List<WarehouseGoods> warehouseGoodsLists = Lists.newArrayList();
					if (Constants.PRODUCT_TYPE_CG.equals(checkApprove.getApproProductTypeCode())) {
						gqgetAssetCars = gqgetAssetCarDao.findGqgetAssetCarDataByApplyNo(applyNo);
						gqgetAssetHouses = gqgetAssetHouseDao.findGqgetAssetHouseDataByApplyNo(applyNo);
						warehouseGoodsLists = warehouseGoodsDao.getPageByApplyNo(applyNo);
					}

					List<RepayPlanUnion> repayPlanUnions = Lists.newArrayList();
					// 循环插入数据
					logger.info("-------------------------------------------循环拆分开始");
					for (int i = 0; i < allCount.intValue(); i++) {
						CheckApproveUnion checkApproveUnion = new CheckApproveUnion();
						if (allContractAmount.compareTo(new BigDecimal(1000000)) == -1) {
							// 不足100w
							logger.info("-------------------------------------------不足100w拆分开始");
							if (count == 0) {
								checkApproveUnion = calculateCountLessThanMillion(allContractAmount,
										applyRelationCompanies.get(0).getCustId(), checkApprove);
								count++;
								logger.info("-------------------------------------------已拆分数量"+count);
							} else {
								checkApproveUnion = calculateCountLessThanMillion(allContractAmount, null,
										checkApprove);
								logger.info("-------------------------------------------已拆分成功");
							}
							logger.info("-------------------------------------------不足100w拆分结束");
						} else {
							// 超过100w
							logger.info("-------------------------------------------超过100w拆分开始");
							if (count == 0) {
								checkApproveUnion = calculateCountMoreThanMillion(allContractAmount,
										applyRelationCompanies.get(0).getCustId(), checkApprove);
								count++;
							} else {
								checkApproveUnion = calculateCountMoreThanMillion(allContractAmount, null,
										checkApprove);
								logger.info("-------------------------------------------已拆分成功");
							}
							logger.info("-------------------------------------------超过100w拆分结束");
							allContractAmount = allContractAmount.subtract(new BigDecimal(1000000));
							logger.info("-------------------------------------------减去拆分的结果"+allContractAmount);
						}
						logger.info("冠E通信息生成开始------------------------------------");
						// 生成冠e通信息
						GqgetComInfoUnion gqgetComInfoUnion = new GqgetComInfoUnion();
						gqgetComInfoUnion.setApplyNo(gqgetComInfo.getApplyNo());
						gqgetComInfoUnion.setApproveId(checkApproveUnion.getId());
						gqgetComInfoUnion.setOverallRanking(gqgetComInfo.getOverallRanking());
						gqgetComInfoUnion.setIntroductionOfMainborrower(gqgetComInfo.getIntrOfMainborrower());
						gqgetComInfoUnion.setIntroductionOfCompany(gqgetComInfo.getIntrOfCompany());
						gqgetComInfoUnion.setMixLoanUsage(gqgetComInfo.getMixLoanUsage());
						gqgetComInfoUnion.setOther(gqgetComInfo.getOther());
						gqgetComInfoUnion.setSourceOfPepayment1(gqgetComInfo.getSourceOfDepayment1());
						gqgetComInfoUnion.setSourceOfPepayment2(gqgetComInfo.getSourceOfDepayment2());
						gqgetComInfoUnion.setSourceOfPepayment3(gqgetComInfo.getSourceOfDepayment3());
						gqgetComInfoUnion.setSourceOfPepayment4(gqgetComInfo.getSourceOfDepayment4());
						gqgetComInfoUnion.setAuditOpintion(gqgetComInfo.getAuditOpintion());
						gqgetComInfoUnion.setIntroductionOfComProduction(gqgetComInfo.getIntrOfComProduction());
						gqgetComInfoUnion.setOperateActuality(gqgetComInfo.getOperateActuality());
						gqgetComInfoUnion.setPropertyHouse(gqgetComInfo.getPropertyHouse());
						gqgetComInfoUnion.setPropertyCar(gqgetComInfo.getPropertyHouse());
						gqgetComInfoUnion.setPropertyOther(gqgetComInfo.getPropertyOther());
						gqgetComInfoUnion.setProductType(gqgetComInfo.getProductType());
						gqgetComInfoUnion.setIsLoan(bankLoan.getIsLoan());
						gqgetComInfoUnion.setLoanRecode(bankLoan.getLoanRecode());
						gqgetComInfoUnion.setIsOverdue(bankLoan.getIsOverdue());
						gqgetComInfoUnion.setSourceOfCreditRegistries(bankLoan.getSourceOfCreRegist());
						gqgetComInfoUnion.setBorrowAndMatePunish(gqgetComInfo.getBorrowAndMatePunish());
						gqgetComInfoUnion.setBorrowInvolveInfo(gqgetComInfo.getBorrowInvolveInfo());
						gqgetComInfoUnion.setBorrowCrimaAdminPunish(gqgetComInfo.getBorrowCrimaAdminPunish());
						gqgetComInfoUnion.setBorrowNewLoanBlance(gqgetComInfo.getBorrowNewLoanBlance());
						gqgetComInfoUnion.setPlatformOverdueMoney(gqgetComInfo.getPlatformOverdueMoney());
						gqgetComInfoUnion.setPlatformOverdueTime(gqgetComInfo.getPlatformOverdueTime());
						CreditAnalysis creditAnalysis = creditDao.getCreditAnalysisByApplyNo(applyNo);
						if (creditAnalysis != null && applyRelationCompanies.size() > 0) {
							for(ApplyRelation applyRelation :applyRelationCompanies){
								if (StringUtils.isNotBlank(checkApproveUnion.getCustId()) && StringUtils.isNotBlank(applyRelation.getRoleType()) && StringUtils.isNotBlank(applyRelation.getCustId()) && "5".equals(applyRelation.getRoleType()) && checkApproveUnion.getCustId().equals(applyRelation.getCustId())) {
									gqgetComInfoUnion.setCreditCompany(creditAnalysis.getCreditCompany());
									break;
								}
							}
						}
						gqgetComInfoUnionService.save(gqgetComInfoUnion);
						logger.info("冠E通信息生成结束------------------------------------");
						if (Constants.PRODUCT_TYPE_XY.equals(gqgetComInfo.getProductType())
								|| Constants.PRODUCT_TYPE_CG.equals(gqgetComInfo.getProductType())) {
							if (gqgetAssetCars != null && gqgetAssetCars.size() != 0) {
								gqgetAssetCarUnionDao.insertGqgetAssetCarUnionData(gqgetAssetCars,
										checkApproveUnion.getId());
							}
							if (gqgetAssetHouses != null && gqgetAssetHouses.size() != 0) {
								gqgetAssetHouseUnionDao.insertGqgetAssetHouseUnionData(gqgetAssetHouses,
										checkApproveUnion.getId());
							}
						} else if (Constants.PRODUCT_TYPE_DY.equals(gqgetComInfo.getProductType())) {
							if (mortageEquipments != null && mortageEquipments.size() != 0) {
								mortageEquipmentUnionDao.insertMortageEquipmentUnionData(mortageEquipments,
										checkApproveUnion.getId());
							}
						} else if (Constants.PRODUCT_TYPE_HH.equals(gqgetComInfo.getProductType())) {
							if (mortageEquipments != null && mortageEquipments.size() != 0) {
								mortageEquipmentUnionDao.insertMortageEquipmentUnionData(mortageEquipments,
										checkApproveUnion.getId());
							}
							if (gqgetGuarantorCompanies != null && gqgetGuarantorCompanies.size() != 0) {
								mortgagedCompanyUnionDao.insertMortgagedCompanyUnionData(gqgetGuarantorCompanies,
										checkApproveUnion.getId());
							}
						}

						// 库存
						if (warehouseGoodsLists.size() != 0) {
							for (WarehouseGoods goods : warehouseGoodsLists) {
								goods.preInsert();
								goods.setApproId(checkApproveUnion.getId());
							}
							warehouseGoodsDao.saveWarehouseGoodsTogether(warehouseGoodsLists);
						}
						logger.info("还款计划进行生成开始------------------------------------");
						// 生成还款计划
						RepayPlanData repayPlanData = new RepayPlanData();
						repayPlanData.setApproProductTypeCode(checkApproveUnion.getApproProductTypeCode());
						repayPlanData.setApplyNo(checkApproveUnion.getApplyNo());
						repayPlanData.setContractNo("");
						repayPlanData.setApproLoanRepayType(checkApproveUnion.getApproLoanRepayType());
						repayPlanData.setApproPeriodValue(checkApproveUnion.getApproPeriodValue());
						repayPlanData.setContractAmount(checkApproveUnion.getContractAmount());
						repayPlanData.setServiceFeeType(checkApproveUnion.getServiceFeeType());
						repayPlanData.setServiceFeeRate(checkApproveUnion.getServiceFeeRate());
						repayPlanData.setDeductDate(null);
						repayPlanData.setTaskDefKey(Constants.UTASK_FGSSX);
						// 查询利率
						BigDecimal interest = checkApproveUnion.getApproYearRate();
						if (interest != null) {
							interest = interest.multiply(new BigDecimal("0.01"));
						}
						repayPlanData.setInterest(interest);
						List<RepayPlanUnion> repayPlanUnionList = Lists.newArrayList();
						List<RepayPlanUnion> repayPlanUnionListTmp = Lists.newArrayList();
						repayPlanUnionListTmp = calculateRepayPlan(repayPlanData);
						// 判断 如果是一次性付清本息 则进行数据处理
						if (Constants.LOAN_REPAY_TYPE_YCXHBFX.equals(checkApproveUnion.getApproLoanRepayType())) {
							repayPlanUnionList = recountData(repayPlanUnionListTmp,
									checkApproveUnion.getApproPeriodValue());
						} else {
							repayPlanUnionList = repayPlanUnionListTmp;
						}
						for (RepayPlanUnion planUnion : repayPlanUnionList) {
							planUnion.setApproveId(checkApproveUnion.getId());
							repayPlanUnions.add(planUnion);
						}
						logger.info("还款计划进行生成结束------------------------------------");
					}
					logger.info("保存还款计划开始------------------------------------");
					repayPlanUnionDao.saveRepayPlanUnionList(repayPlanUnions);
					logger.info("保存还款计划结束------------------------------------");
					logger.info("-------------------------------------------进入拆分结束"+actTaskParam.getApplyNo());
				}
			}
		}
	}

	@Transactional(value = "CRE", readOnly = false)
	public CheckApproveUnion calculateCountLessThanMillion(BigDecimal allContractAmount, String custId,
			CheckApprove checkApprove) {
		logger.info("-----------------------拆分各项费用计算开始-------------------"+allContractAmount);
		CheckApproveUnion checkApproveUnion = new CheckApproveUnion();
		// 合同金额
		BigDecimal contractAmount = allContractAmount;
		// 计算保证金 保证金=合同金额*保证金比例
		BigDecimal marginRate = checkApprove.getMarginRate();
		BigDecimal marginAmount = contractAmount.multiply(marginRate.multiply(new BigDecimal(0.01)));
		// 计算质量服务保证金
		BigDecimal qualityServiceMarginRate = checkApprove.getQualityServiceMarginRate();
		if(StringUtils.isNull(qualityServiceMarginRate)){
			qualityServiceMarginRate = new BigDecimal(0);
		}
		BigDecimal qualityServiceMarginAmount = contractAmount
				.multiply(qualityServiceMarginRate.multiply(new BigDecimal(0.01)));
		// 计算服务费 服务费=合同金额*服务费率*期限
		BigDecimal serviceFeeRate = checkApprove.getServiceFeeRate();
		String approPeriodId = checkApprove.getApproPeriodId();
		BigDecimal serviceFee = contractAmount
				.multiply(serviceFeeRate.multiply(new BigDecimal(approPeriodId).multiply(new BigDecimal(0.01))));
		// 特殊服务费 ， 特殊服务费 = 合同金额*特殊服务费率
		BigDecimal specialServiceFeeRate = checkApprove.getSpecialServiceFeeRate();
		BigDecimal specialServiceFee = null;
		if (specialServiceFeeRate != null) {
			specialServiceFee = contractAmount.multiply(specialServiceFeeRate.multiply(new BigDecimal(0.01)));
		} else {
			specialServiceFee = new BigDecimal(0);
		}
		// 服务费合计，服务费合计 = 服务费 + 特殊服务费
		BigDecimal allServiceFee = null;
		// 放款金额，放款金额=合同金额-服务费-保证金-特殊服务费
		BigDecimal loanAmount = null;
		if(Constants.PRODUCT_TYPE_ZGJH.equals(checkApprove.getApproProductTypeCode())){
			loanAmount = contractAmount.subtract(marginAmount);
			allServiceFee = checkApprove.getRealityServiceFee().add(checkApprove.getSpecialServiceFee());
		}else{
			if ("1".equals(checkApprove.getServiceFeeType())) {
				if (specialServiceFee != null) {
					loanAmount = (((contractAmount.subtract(serviceFee)).subtract(marginAmount))
							.subtract(specialServiceFee)).subtract(qualityServiceMarginAmount);
				} else {
					loanAmount = ((contractAmount.subtract(serviceFee)).subtract(marginAmount))
							.subtract(qualityServiceMarginAmount);
				}
			} else {
				loanAmount = (contractAmount.subtract(marginAmount)).subtract(qualityServiceMarginAmount);
			}
			if (specialServiceFee != null) {
				allServiceFee = serviceFee.add(specialServiceFee);
			} else {
				allServiceFee = serviceFee;
			}
		}
		logger.info("----------------------------------拆分的中间过程");
		//计算利息月息差
		BigDecimal interestMonthlySpread = JointCreditUtils.calcInterestMonthlySpread(checkApprove, loanAmount);
		//计算保证金月息差
		BigDecimal interestRateDiff = JointCreditUtils.calcInterestRateDiff(checkApprove, marginRate, marginAmount);
		
		checkApproveUnion.setApplyNo(checkApprove.getApplyNo());
		if (StringUtils.isNotEmpty(custId)) {
			checkApproveUnion.setCustId(custId);
			// 只有主借人有外访费
			CheckFee checkFee = checkFeeDao.findByApplyNo(checkApprove.getApplyNo());
			if (checkFee == null) {
				checkFee = new CheckFee();
			}
			checkApproveUnion.setCheckFee(checkFee.getCheckFee());
		}
		checkApproveUnion.setQualityServiceMarginRate(checkApprove.getQualityServiceMarginRate());
		checkApproveUnion.setQualityServiceMarginAmount(qualityServiceMarginAmount);
		checkApproveUnion.setPricedRisk(checkApprove.getPricedRisk());
		checkApproveUnion.setApproProductTypeCode(checkApprove.getApproProductTypeCode());
		checkApproveUnion.setApproProductTypeName(checkApprove.getApproProductTypeName());
		checkApproveUnion.setApproProductId(checkApprove.getApproProductId());
		checkApproveUnion.setApproProductName(checkApprove.getApproProductName());
		checkApproveUnion.setContractAmount(contractAmount);
		checkApproveUnion.setApproAmount(checkApprove.getApproAmount());
		checkApproveUnion.setLoanAmount(loanAmount);
		checkApproveUnion.setApproYearRate(checkApprove.getApproYearRate());
		checkApproveUnion.setGuanetongRate(checkApprove.getGuanetongRate());
		checkApproveUnion.setServiceFeeRate(checkApprove.getServiceFeeRate());
		checkApproveUnion.setSpecialServiceFeeRate(checkApprove.getSpecialServiceFeeRate());
		checkApproveUnion.setServiceFeeType(checkApprove.getServiceFeeType());
		checkApproveUnion.setServiceFee(serviceFee);
		checkApproveUnion.setSpecialServiceFee(specialServiceFee);
		checkApproveUnion.setAllServiceFee(allServiceFee);
		checkApproveUnion.setApproPeriodId(checkApprove.getApproPeriodId());
		checkApproveUnion.setApproPeriodValue(checkApprove.getApproPeriodValue());
		checkApproveUnion.setApproLoanRepayType(checkApprove.getApproLoanRepayType());
		checkApproveUnion.setMarginRate(checkApprove.getMarginRate());
		checkApproveUnion.setMarginAmount(marginAmount);
		checkApproveUnion.setLoanModel(checkApprove.getLoanModel());
		checkApproveUnion.setIsUrgent(checkApprove.getIsUrgent());
		checkApproveUnion.setContractType(checkApprove.getContractType());
		checkApproveUnion.setApproDate(checkApprove.getApproDate());
		checkApproveUnion.setTaskDefKey(Constants.UTASK_FGSSX);
		checkApproveUnion.setProcessSequence(checkApprove.getProcessSequence());
		checkApproveUnion.setRemark(checkApprove.getRemark());
		checkApproveUnion.setProductCategory(checkApprove.getProductCategory());
		//----采购贷新增字段
		checkApproveUnion.setTopShopName(checkApprove.getTopShopName());
		checkApproveUnion.setDownShopName(checkApprove.getDownShopName());
		checkApproveUnion.setTopShopBackMoney(checkApprove.getTopShopBackMoney());
		checkApproveUnion.setTopShopBackRate(checkApprove.getTopShopBackRate());
		checkApproveUnion.setTopShopMonthRate(checkApprove.getTopShopMonthRate());
		checkApproveUnion.setMediacyServiceFee(checkApprove.getMediacyServiceFee());
		checkApproveUnion.setInterestRateDiff(interestRateDiff);
		checkApproveUnion.setRealityServiceFee(checkApprove.getRealityServiceFee());
		checkApproveUnion.setAddFundPeriod(checkApprove.getAddFundPeriod());
		checkApproveUnion.setDiscountInterestRate(checkApprove.getDiscountInterestRate());
		checkApproveUnion.setInterestMonthlySpread(interestMonthlySpread);
		if(StringUtils.isEmpty(checkApproveUnion.getId())) {
			checkApproveUnion.preInsert();
			String checkId = checkApproveUnion.getId().substring(0,14);
			String randomString= (int)((Math.random()*9+1)*100000)+"";
			checkApproveUnion.setId(checkId+randomString);
			checkApproveUnionDao.insert(checkApproveUnion);
			logger.info("-------------------------------拆分插入数据--------------");
		}else {
			checkApproveUnionService.save(checkApproveUnion);
			logger.info("--------------拆分更新数据-------------------------");
		}
		try {
			logger.info("-----------------------拆分各项费用计算结束-------------------"+checkApproveUnion.getContractAmount());
		} catch (Exception e) {
			logger.error("-----------------------拆分各项费用计算失败-------------------",e.getMessage());
		}
		
		return checkApproveUnion;
	}

	@Transactional(value = "CRE", readOnly = false)
	public CheckApproveUnion calculateCountMoreThanMillion(BigDecimal allContractAmount, String custId,
			CheckApprove checkApprove) {
		CheckApproveUnion checkApproveUnion = new CheckApproveUnion();
		// 合同金额
		BigDecimal contractAmount = new BigDecimal(1000000);
		// 计算保证金 保证金=合同金额*保证金比例
		BigDecimal marginRate = checkApprove.getMarginRate();
		BigDecimal marginAmount = contractAmount.multiply(marginRate.multiply(new BigDecimal(0.01)));
		// 计算质量服务保证金
		//BigDecimal qualityServiceMarginRate = new BigDecimal(0); 
		BigDecimal qualityServiceMarginRate = checkApprove.getQualityServiceMarginRate();
		if(StringUtils.isNull(qualityServiceMarginRate)){
			qualityServiceMarginRate = new BigDecimal(0);
		}
		BigDecimal qualityServiceMarginAmount = contractAmount
				.multiply(qualityServiceMarginRate.multiply(new BigDecimal(0.01)));
		// 计算服务费 服务费=合同金额*服务费率*期限
		BigDecimal serviceFeeRate = checkApprove.getServiceFeeRate();
		String approPeriodId = checkApprove.getApproPeriodId();
		BigDecimal serviceFee = contractAmount
				.multiply(serviceFeeRate.multiply(new BigDecimal(approPeriodId).multiply(new BigDecimal(0.01))));
		// 特殊服务费 ， 特殊服务费 = 合同金额*特殊服务费率
		BigDecimal specialServiceFeeRate = checkApprove.getSpecialServiceFeeRate();
		BigDecimal specialServiceFee = null;
		if (specialServiceFeeRate != null) {
			specialServiceFee = contractAmount.multiply(specialServiceFeeRate.multiply(new BigDecimal(0.01)));
		} else {
			specialServiceFee = new BigDecimal(0);
		}
		
		// 放款金额，放款金额=合同金额-服务费-保证金-特殊服务费
		BigDecimal loanAmount = null;
		// 服务费合计，服务费合计 = 服务费 + 特殊服务费
		BigDecimal allServiceFee = null;
		if(Constants.PRODUCT_TYPE_ZGJH.equals(checkApprove.getApproProductTypeCode())){
			loanAmount = contractAmount.subtract(marginAmount);
			allServiceFee = checkApprove.getRealityServiceFee().add(checkApprove.getSpecialServiceFee());
		}else{
			if ("1".equals(checkApprove.getServiceFeeType())) {
				if (specialServiceFee != null) {
					loanAmount = (((contractAmount.subtract(serviceFee)).subtract(marginAmount))
							.subtract(specialServiceFee)).subtract(qualityServiceMarginAmount);
				} else {
					loanAmount = ((contractAmount.subtract(serviceFee)).subtract(marginAmount))
							.subtract(qualityServiceMarginAmount);
				}
			} else {
				loanAmount = (contractAmount.subtract(marginAmount)).subtract(qualityServiceMarginAmount);
			}
			
			
			if (specialServiceFee != null) {
				allServiceFee = serviceFee.add(specialServiceFee);
			} else {
				allServiceFee = serviceFee;
			}
		}
		//计算利息月息差
		BigDecimal interestMonthlySpread = JointCreditUtils.calcInterestMonthlySpread(checkApprove, loanAmount);
		//计算保证金月息差
		BigDecimal interestRateDiff = JointCreditUtils.calcInterestRateDiff(checkApprove, marginRate, marginAmount);

		checkApproveUnion.setApplyNo(checkApprove.getApplyNo());
		if (StringUtils.isNotEmpty(custId)) {
			checkApproveUnion.setCustId(custId);
			// 只有主借人有外访费
			CheckFee checkFee = checkFeeDao.findByApplyNo(checkApprove.getApplyNo());
			if (checkFee == null) {
				checkFee = new CheckFee();
			}
			checkApproveUnion.setCheckFee(checkFee.getCheckFee());
		}
		checkApproveUnion.setQualityServiceMarginRate(checkApprove.getQualityServiceMarginRate());
		checkApproveUnion.setQualityServiceMarginAmount(qualityServiceMarginAmount);
		checkApproveUnion.setPricedRisk(checkApprove.getPricedRisk());
		checkApproveUnion.setApproProductTypeCode(checkApprove.getApproProductTypeCode());
		checkApproveUnion.setApproProductTypeName(checkApprove.getApproProductTypeName());
		checkApproveUnion.setApproProductId(checkApprove.getApproProductId());
		checkApproveUnion.setApproProductName(checkApprove.getApproProductName());
		checkApproveUnion.setContractAmount(contractAmount);
		checkApproveUnion.setApproAmount(checkApprove.getApproAmount());
		checkApproveUnion.setLoanAmount(loanAmount);
		checkApproveUnion.setApproYearRate(checkApprove.getApproYearRate());
		checkApproveUnion.setGuanetongRate(checkApprove.getGuanetongRate());
		checkApproveUnion.setServiceFeeRate(checkApprove.getServiceFeeRate());
		checkApproveUnion.setSpecialServiceFeeRate(checkApprove.getSpecialServiceFeeRate());
		checkApproveUnion.setServiceFeeType(checkApprove.getServiceFeeType());
		checkApproveUnion.setServiceFee(serviceFee);
		checkApproveUnion.setSpecialServiceFee(specialServiceFee);
		checkApproveUnion.setAllServiceFee(allServiceFee);
		checkApproveUnion.setApproPeriodId(checkApprove.getApproPeriodId());
		checkApproveUnion.setApproPeriodValue(checkApprove.getApproPeriodValue());
		checkApproveUnion.setApproLoanRepayType(checkApprove.getApproLoanRepayType());
		checkApproveUnion.setMarginRate(checkApprove.getMarginRate());
		checkApproveUnion.setMarginAmount(marginAmount);
		checkApproveUnion.setLoanModel(checkApprove.getLoanModel());
		checkApproveUnion.setIsUrgent(checkApprove.getIsUrgent());
		checkApproveUnion.setContractType(checkApprove.getContractType());
		checkApproveUnion.setApproDate(checkApprove.getApproDate());
		checkApproveUnion.setTaskDefKey(Constants.UTASK_FGSSX);
		checkApproveUnion.setProcessSequence(checkApprove.getProcessSequence());
		checkApproveUnion.setRemark(checkApprove.getRemark());
		checkApproveUnion.setProductCategory(checkApprove.getProductCategory());
		//----采购贷新增字段
		checkApproveUnion.setTopShopName(checkApprove.getTopShopName());
		checkApproveUnion.setDownShopName(checkApprove.getDownShopName());
		checkApproveUnion.setTopShopBackMoney(checkApprove.getTopShopBackMoney());
		checkApproveUnion.setTopShopBackRate(checkApprove.getTopShopBackRate());
		checkApproveUnion.setTopShopMonthRate(checkApprove.getTopShopMonthRate());
		checkApproveUnion.setMediacyServiceFee(checkApprove.getMediacyServiceFee());
		checkApproveUnion.setInterestRateDiff(interestRateDiff);
		checkApproveUnion.setRealityServiceFee(checkApprove.getRealityServiceFee());
		checkApproveUnion.setAddFundPeriod(checkApprove.getAddFundPeriod());
		checkApproveUnion.setDiscountInterestRate(checkApprove.getDiscountInterestRate());
		checkApproveUnion.setInterestMonthlySpread(interestMonthlySpread);
		if(StringUtils.isEmpty(checkApproveUnion.getId())) {
			checkApproveUnion.preInsert();
			String checkId = checkApproveUnion.getId().substring(0,14);
			String randomString= (int)((Math.random()*9+1)*100000)+"";
			checkApproveUnion.setId(checkId+randomString);
			checkApproveUnionDao.insert(checkApproveUnion);
		}else {
			checkApproveUnionService.save(checkApproveUnion);
		}
		return checkApproveUnion;
	}

	// 生成还款计划表中的数据
	@Transactional(value = "CRE", readOnly = false)
	public List<RepayPlanUnion> calculateRepayPlan(RepayPlanData repayPlanData) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<RepayPlanUnion> repayPlanList = new ArrayList<RepayPlanUnion>();

		Map<String, String> params = Maps.newConcurrentMap();
		params.put("loanRepayType", repayPlanData.getApproLoanRepayType());
		params.put("periodValue", repayPlanData.getApproPeriodValue());
		params.put("approProductTypeCode", repayPlanData.getApproProductTypeCode());

		List<RateCapital> rateCapitalList = contractDao.getRateCapitalCurr(params);

		// 合同金额
		BigDecimal contractAmount = repayPlanData.getContractAmount();
		// 期数
		int approPeriodValue = Integer.parseInt(repayPlanData.getApproPeriodValue());
		// 当期本金
		BigDecimal capitalAmount = new BigDecimal("0");

		// *************************************************************//
		// 调用工具类计算还款方式
		BigDecimal contractRate = repayPlanData.getInterest();
		List<Map<String, BigDecimal>> exportRepayPlan = new ArrayList<Map<String, BigDecimal>>();

		if ("5".equals(repayPlanData.getApproLoanRepayType())) {
			exportRepayPlan = CalculatorFormulasUtils.getACPIMonthPaymentAmount(contractAmount,
					new BigDecimal(rateCapitalList.get(0).getRateInterest().getRateInterest()), approPeriodValue,
					contractRate);
		} else {
			exportRepayPlan = CalculatorFormulasUtils.getLadderMonthPaymentAmount(contractAmount,
					new BigDecimal(rateCapitalList.get(0).getRateInterest().getRateInterest()), approPeriodValue,
					contractRate, rateCapitalList);
		}

		// ************************************************************//
		Date firstDeductDate = null;
		for (int i = 0; i < approPeriodValue; i++) {
			RepayPlanUnion repayPlanUnion = new RepayPlanUnion();
			repayPlanUnion.preInsert();
			repayPlanUnion.setApplyNo(repayPlanData.getApplyNo());
			repayPlanUnion.setContractNo(repayPlanData.getContractNo());

			// ******************//
			// 增加合同还款本金、利息、合计以及差额本金、利息
			repayPlanUnion.setBidCapitalAmount(exportRepayPlan.get(i).get("htMonthBaseAmount"));
			repayPlanUnion.setBidInterestAmount(exportRepayPlan.get(i).get("htMonthIrrAmount"));
			repayPlanUnion.setBidRepayAmount(exportRepayPlan.get(i).get("htMonthSumAmount"));
			repayPlanUnion.setDifCapitalAmount(exportRepayPlan.get(i).get("ceMonthBaseAmount"));
			repayPlanUnion.setDifInterestAmount(exportRepayPlan.get(i).get("ceMonthIrrAmount"));
			// ******************//

			if (StringUtils.isEmpty(repayPlanData.getIsAcc()) && repayPlanData.getDeductDate() != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(repayPlanData.getDeductDate());
				if (cal.get(Calendar.MONTH) == 2 && cal.get(Calendar.DAY_OF_MONTH) == 1) {// 特殊情况：如果是3月1号提现回盘，第一期还款应该是3月30，2月没有30号，Calendar日期减一会导致所有期数还款日为28号或29号
					if (i == 0) {
						cal.add(Calendar.MONTH, 1);
						cal.add(Calendar.DATE, -2);// 04-01改到03-30,减2天
						Date deductDate = cal.getTime();
						repayPlanUnion.setDeductDate(deductDate);
						firstDeductDate = deductDate;
					} else {
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(firstDeductDate);
						cal2.add(Calendar.DATE, 0);// 肯定不是31号
						cal2.add(Calendar.MONTH, i);
						Date deductDate = cal2.getTime();
						repayPlanUnion.setDeductDate(deductDate);
					}
				} else {
					cal.add(Calendar.DATE, -1);
					if (cal.get(Calendar.DAY_OF_MONTH) == 31) {// 如果还款日是31号，则改为30号
						cal.add(Calendar.DATE, -1);
					}
					cal.add(Calendar.MONTH, i + 1);
					Date deductDate = cal.getTime();
					repayPlanUnion.setDeductDate(deductDate);
				}
			}

			if (StringUtils.isNotEmpty(repayPlanData.getIsAcc())
					&& StringUtils.isNotEmpty(repayPlanData.getDeductDateStr())) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(repayPlanData.getDeductDate());
				if (cal.get(Calendar.MONTH) == 2 && cal.get(Calendar.DAY_OF_MONTH) == 1) {
					if (i == 0) {
						cal.add(Calendar.MONTH, 1);
						cal.add(Calendar.DATE, -2);// 04-01改到03-30,减2天
						Date deductDate = cal.getTime();
						repayPlanUnion.setDeductDateStr(sdf.format(deductDate));
						firstDeductDate = deductDate;
						repayPlanUnion.setDeductDate(deductDate);
					} else {
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(firstDeductDate);
						cal2.add(Calendar.DATE, 0);// 肯定不是31号
						cal2.add(Calendar.MONTH, i);
						Date deductDate = cal2.getTime();
						repayPlanUnion.setDeductDateStr(sdf.format(deductDate));
						repayPlanUnion.setDeductDate(deductDate);
					}
				} else {
					cal.add(Calendar.DATE, -1);
					if (cal.get(Calendar.DAY_OF_MONTH) == 31) {// 如果还款日是31号，则改为30号
						cal.add(Calendar.DATE, -1);
					}
					cal.add(Calendar.MONTH, i + 1);
					Date deductDate = cal.getTime();
					repayPlanUnion.setDeductDate(deductDate);
					repayPlanUnion.setDeductDateStr(sdf.format(deductDate));
				}

			}

			repayPlanUnion.setPeriodNum(rateCapitalList.get(i).getPeriodNum());

			// 计算当期本金
			// 运用工具类计算的当期本金
			repayPlanUnion.setCapitalAmount(exportRepayPlan.get(i).get("monthBaseAmount"));

			// 计算当期利息
			// 参数表中当期剩余应还本金比例
			// 运用工具类计算的标的利息
			repayPlanUnion.setInterestAmount(exportRepayPlan.get(i).get("monthIrrAmount"));

			// 计算当期服务费
			BigDecimal serviceFee = new BigDecimal("0");
			// 放款时扣除服务费
			BigDecimal serviceFeeRate = repayPlanData.getServiceFeeRate().divide(new BigDecimal("100"));// 服务费率
			if (Constants.SERVICE_FEE_TYPE_FKSKCFWF.equals(repayPlanData.getServiceFeeType())) {
				serviceFee = new BigDecimal("0");
			} else if (Constants.SERVICE_FEE_TYPE_HKZJTDMQ.equals(repayPlanData.getServiceFeeType())) {// 还款中均摊到每期扣除服务费
				// 合同金额*服务费率
				serviceFee = contractAmount.multiply(serviceFeeRate);
			}
			repayPlanUnion.setServiceFee(serviceFee);
			
			// 计算账户管理费
			BigDecimal managementFee = new BigDecimal("0");

			// 运用工具类计算账户管理费（差额本息）
			repayPlanUnion.setManagementFee(exportRepayPlan.get(i).get("ceMonthSumAmount"));

			// 计算逾期违约金费用
			BigDecimal overduePenalty = new BigDecimal("0");
			List<Map<String, String>> overduePenaltyList = repayPlanDao.getOverduePenalty();
			overduePenalty = serviceFee.add(managementFee).add(capitalAmount);
			if (overduePenalty.compareTo(new BigDecimal(overduePenaltyList.get(0).get("label"))) <= 0) {
				overduePenalty = overduePenalty.multiply(new BigDecimal(overduePenaltyList.get(0).get("value")));
			} else if (overduePenalty.compareTo(
					new BigDecimal(overduePenaltyList.get(overduePenaltyList.size() - 2).get("label"))) > 0) {
				overduePenalty = overduePenalty
						.multiply(new BigDecimal(overduePenaltyList.get(overduePenaltyList.size() - 1).get("value")));
			} else {
				for (int j = 0; j < overduePenaltyList.size(); j++) {
					if (overduePenalty.compareTo(new BigDecimal(overduePenaltyList.get(j).get("label"))) > 0
							&& overduePenalty
									.compareTo(new BigDecimal(overduePenaltyList.get(j + 1).get("label"))) <= 0) {
						overduePenalty = overduePenalty
								.multiply(new BigDecimal(overduePenaltyList.get(j + 1).get("value")));
						break;
					}
				}
			}
			
			
			//新增数据还款计划违约金为0
			if (StringUtils.isNotBlank(repayPlanUnion.getApplyNo())) {
				ApplyRegister ap = applyRegisterDao.getApplyRegisterByApplyNo(repayPlanUnion.getApplyNo());
				if ("0".equals(ap.getNewOld())) {
					repayPlanUnion.setOverduePenalty(new BigDecimal("0"));
				}else{
					repayPlanUnion.setOverduePenalty(serviceFee);
				}
			}else{
				repayPlanUnion.setOverduePenalty(overduePenalty);
			}
			
			// 计算月还款
			BigDecimal repayAmount = new BigDecimal("0");

			// （运用工具类计算合同还款本息）+计算当期服务费

			repayAmount = serviceFee.add(exportRepayPlan.get(i).get("htMonthSumAmount"));

			repayPlanUnion.setRepayAmount(repayAmount);

			if (StringUtils.isNotEmpty(repayPlanData.getIsAcc())) {
				// 机构号
				repayPlanUnion.setOrgLevel2(repayPlanData.getOrgLevel2());
				repayPlanUnion.setOrgLevel3(repayPlanData.getOrgLevel3());
				repayPlanUnion.setOrgLevel4(repayPlanData.getOrgLevel4());

				// 总期数
				repayPlanUnion.setPeriodValue(Integer.parseInt(rateCapitalList.get(i).getPeriodValue()));

				// 客户名称
				repayPlanUnion.setCustName(repayPlanData.getCustName());
				// 生成日期
				repayPlanUnion.setCreateDateStr(sdf.format(new Date()));
				// 资金平台账号
				repayPlanUnion.setCapitalTerraceNo(repayPlanData.getCapitalTerraceNo());
			}

			if (StringUtils.isEmpty(repayPlanData.getIsAcc())) {
				// 流程ID
				repayPlanUnion.setTaskDefKey(repayPlanData.getTaskDefKey());
			}

			repayPlanList.add(i, repayPlanUnion);
		}
		return repayPlanList;
	}

	public List<RepayPlanUnion> recountData(List<RepayPlanUnion> repayPlanList, String periodNum) {
		BigDecimal repayAmount = new BigDecimal(0);
		BigDecimal interestAmount = new BigDecimal(0);
		BigDecimal serviceFee = new BigDecimal(0);
		BigDecimal managementFee = new BigDecimal(0);
		BigDecimal capitalAmount = new BigDecimal(0);
		BigDecimal overduePenalty = new BigDecimal(0);
		BigDecimal bidCapitalAmount = new BigDecimal(0);
		BigDecimal bidInterestAmount = new BigDecimal(0);
		BigDecimal bidRepayAmount = new BigDecimal(0);
		BigDecimal difCapitalAmount = new BigDecimal(0);
		BigDecimal difInterestAmount = new BigDecimal(0);
		for (int i = 0; i < repayPlanList.size(); i++) {
			repayAmount = repayAmount.add(repayPlanList.get(i).getRepayAmount());
			interestAmount = interestAmount.add(repayPlanList.get(i).getInterestAmount());
			serviceFee = serviceFee.add(repayPlanList.get(i).getServiceFee());
			managementFee = managementFee.add(repayPlanList.get(i).getManagementFee());
			capitalAmount = capitalAmount.add(repayPlanList.get(i).getCapitalAmount());
			overduePenalty = overduePenalty.add(repayPlanList.get(i).getOverduePenalty());
			bidCapitalAmount = bidCapitalAmount.add(repayPlanList.get(i).getBidCapitalAmount());
			bidInterestAmount = bidInterestAmount.add(repayPlanList.get(i).getBidInterestAmount());
			bidRepayAmount = bidRepayAmount.add(repayPlanList.get(i).getBidRepayAmount());
			difCapitalAmount = difCapitalAmount.add(repayPlanList.get(i).getDifCapitalAmount());
			difInterestAmount = difInterestAmount.add(repayPlanList.get(i).getDifInterestAmount());
			repayPlanList.get(i).setRepayAmount(new BigDecimal(0));
			repayPlanList.get(i).setInterestAmount(new BigDecimal(0));
			repayPlanList.get(i).setServiceFee(new BigDecimal(0));
			repayPlanList.get(i).setManagementFee(new BigDecimal(0));
			repayPlanList.get(i).setCapitalAmount(new BigDecimal(0));
			repayPlanList.get(i).setOverduePenalty(new BigDecimal(0));
			repayPlanList.get(i).setBidCapitalAmount(new BigDecimal(0));
			repayPlanList.get(i).setBidInterestAmount(new BigDecimal(0));
			repayPlanList.get(i).setBidRepayAmount(new BigDecimal(0));
			repayPlanList.get(i).setDifCapitalAmount(new BigDecimal(0));
			repayPlanList.get(i).setDifInterestAmount(new BigDecimal(0));
		}
		for (int i = 0; i < repayPlanList.size(); i++) {
			int value = Integer.valueOf(repayPlanList.get(i).getPeriodNum());
			int num = Integer.valueOf(periodNum);
			if (num == value) {
				repayPlanList.get(i).setRepayAmount(repayAmount);
				repayPlanList.get(i).setInterestAmount(interestAmount);
				repayPlanList.get(i).setServiceFee(serviceFee);
				repayPlanList.get(i).setManagementFee(managementFee);
				repayPlanList.get(i).setCapitalAmount(capitalAmount);
				repayPlanList.get(i).setOverduePenalty(overduePenalty);
				repayPlanList.get(i).setBidCapitalAmount(bidCapitalAmount);
				repayPlanList.get(i).setBidInterestAmount(bidInterestAmount);
				repayPlanList.get(i).setBidRepayAmount(bidRepayAmount);
				repayPlanList.get(i).setDifCapitalAmount(difCapitalAmount);
				repayPlanList.get(i).setDifInterestAmount(difInterestAmount);
			}
		}
		return repayPlanList;
	}

	void dealPartData(String applyNo, CheckApprove checkApprove) {
		List<CheckApproveUnion> approveUnions = checkApproveUnionService.getCheckApproveUnionByApplyNo(applyNo);
		GqgetComInfo gqgetComInfo = gqgetComInfoDao.getGqgetComInfoByApplyNo(applyNo);
		BankLoan bankLoan = bankLoanDao.getBankLoanByApplyNo(gqgetComInfo.getApplyNo());
		List<GqgetAssetCar> gqgetAssetCars = Lists.newArrayList();
		List<GqgetAssetHouse> gqgetAssetHouses = Lists.newArrayList();
		List<MortageEquipment> mortageEquipments = Lists.newArrayList();
		List<GqgetGuarantorCompany> gqgetGuarantorCompanies = Lists.newArrayList();
		if (Constants.PRODUCT_TYPE_XY.equals(gqgetComInfo.getProductType())
				|| Constants.PRODUCT_TYPE_CG.equals(gqgetComInfo.getProductType())) {
			gqgetAssetCars = gqgetAssetCarDao.findGqgetAssetCarDataByApplyNo(applyNo);
			gqgetAssetHouses = gqgetAssetHouseDao.findGqgetAssetHouseDataByApplyNo(applyNo);
		} else {
			mortageEquipments = mortageEquipmentDao.getPageByApplyNo(applyNo);
			if (Constants.PRODUCT_TYPE_HH.equals(gqgetComInfo.getProductType())) {
				gqgetGuarantorCompanies = gqgetGuarantorCompanyDao.findGqgetGuarantorCompanyDataByApplyNo(applyNo);
			}
		}
		List<WarehouseGoods> warehouseGoodsLists = Lists.newArrayList();
		if (Constants.PRODUCT_TYPE_CG.equals(gqgetComInfo.getProductType())) {
			gqgetAssetCars = gqgetAssetCarDao.findGqgetAssetCarDataByApplyNo(applyNo);
			gqgetAssetHouses = gqgetAssetHouseDao.findGqgetAssetHouseDataByApplyNo(applyNo);
			warehouseGoodsLists = warehouseGoodsDao.getPageByApplyNo(applyNo);
		}

		if (approveUnions != null && approveUnions.size() > 0) {
			// 循环更新数据
			for (CheckApproveUnion checkApproveUnion : approveUnions) {
				// 更新批复数据
				checkApproveUnion.setIsUrgent(checkApprove.getIsUrgent());
				checkApproveUnion.setRemark(checkApprove.getRemark());
				if(StringUtils.isEmpty(checkApproveUnion.getId())) {
					checkApproveUnion.preInsert();
					String checkId = checkApproveUnion.getId().substring(0,14);
					String randomString= (int)((Math.random()*9+1)*100000)+"";
					checkApproveUnion.setId(checkId+randomString);
					checkApproveUnionDao.insert(checkApproveUnion);
				}else {
					checkApproveUnionService.save(checkApproveUnion);
				}

				// 生成冠e通信息
				GqgetComInfoUnion gqgetComInfoUnion = new GqgetComInfoUnion();
				gqgetComInfoUnion.setApplyNo(gqgetComInfo.getApplyNo());
				gqgetComInfoUnion.setApproveId(checkApproveUnion.getId());
				gqgetComInfoUnion.setOverallRanking(gqgetComInfo.getOverallRanking());
				gqgetComInfoUnion.setIntroductionOfMainborrower(gqgetComInfo.getIntrOfMainborrower());
				gqgetComInfoUnion.setIntroductionOfCompany(gqgetComInfo.getIntrOfCompany());
				gqgetComInfoUnion.setMixLoanUsage(gqgetComInfo.getMixLoanUsage());
				gqgetComInfoUnion.setOther(gqgetComInfo.getOther());
				gqgetComInfoUnion.setSourceOfPepayment1(gqgetComInfo.getSourceOfDepayment1());
				gqgetComInfoUnion.setSourceOfPepayment2(gqgetComInfo.getSourceOfDepayment2());
				gqgetComInfoUnion.setSourceOfPepayment3(gqgetComInfo.getSourceOfDepayment3());
				gqgetComInfoUnion.setSourceOfPepayment4(gqgetComInfo.getSourceOfDepayment4());
				gqgetComInfoUnion.setAuditOpintion(gqgetComInfo.getAuditOpintion());
				gqgetComInfoUnion.setIntroductionOfComProduction(gqgetComInfo.getIntrOfComProduction());
				gqgetComInfoUnion.setOperateActuality(gqgetComInfo.getOperateActuality());
				gqgetComInfoUnion.setPropertyHouse(gqgetComInfo.getPropertyHouse());
				gqgetComInfoUnion.setPropertyCar(gqgetComInfo.getPropertyHouse());
				gqgetComInfoUnion.setPropertyOther(gqgetComInfo.getPropertyOther());
				gqgetComInfoUnion.setProductType(gqgetComInfo.getProductType());
				gqgetComInfoUnion.setIsLoan(bankLoan.getIsLoan());
				gqgetComInfoUnion.setLoanRecode(bankLoan.getLoanRecode());
				gqgetComInfoUnion.setIsOverdue(bankLoan.getIsOverdue());
				gqgetComInfoUnion.setSourceOfCreditRegistries(bankLoan.getSourceOfCreRegist());
				gqgetComInfoUnionService.save(gqgetComInfoUnion);
				if (Constants.PRODUCT_TYPE_XY.equals(gqgetComInfo.getProductType())
						|| Constants.PRODUCT_TYPE_CG.equals(gqgetComInfo.getProductType())) {
					if (gqgetAssetCars != null && gqgetAssetCars.size() != 0) {
						gqgetAssetCarUnionDao.insertGqgetAssetCarUnionData(gqgetAssetCars, checkApproveUnion.getId());
					}
					if (gqgetAssetHouses != null && gqgetAssetHouses.size() != 0) {
						gqgetAssetHouseUnionDao.insertGqgetAssetHouseUnionData(gqgetAssetHouses,
								checkApproveUnion.getId());
					}
				} else if (Constants.PRODUCT_TYPE_DY.equals(gqgetComInfo.getProductType())) {
					if (mortageEquipments != null && mortageEquipments.size() != 0) {
						mortageEquipmentUnionDao.insertMortageEquipmentUnionData(mortageEquipments,
								checkApproveUnion.getId());
					}
				} else if (Constants.PRODUCT_TYPE_HH.equals(gqgetComInfo.getProductType())) {
					if (mortageEquipments != null && mortageEquipments.size() != 0) {
						mortageEquipmentUnionDao.insertMortageEquipmentUnionData(mortageEquipments,
								checkApproveUnion.getId());
					}
					if (gqgetGuarantorCompanies != null && gqgetGuarantorCompanies.size() != 0) {
						mortgagedCompanyUnionDao.insertMortgagedCompanyUnionData(gqgetGuarantorCompanies,
								checkApproveUnion.getId());
					}
				}

				// 库存
				if (warehouseGoodsLists.size() != 0) {
					for (WarehouseGoods goods : warehouseGoodsLists) {
						goods.preInsert();
						goods.setApproId(checkApproveUnion.getId());
					}
					warehouseGoodsDao.saveWarehouseGoodsTogether(warehouseGoodsLists);
				}
			}
		}
	}

}
