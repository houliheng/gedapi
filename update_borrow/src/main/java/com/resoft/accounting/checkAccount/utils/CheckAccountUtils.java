package com.resoft.accounting.checkAccount.utils;

import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountDCResponse;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.TransferWithHoldEntity;
import com.resoft.accounting.bankAccountStatement.entity.CheckAccountStatement;
import com.resoft.accounting.bankAccountStatement.entity.StatementStatusEnum;
import com.resoft.accounting.checkAccount.entity.ApplyStatusEnum;
import com.resoft.accounting.checkAccount.entity.CheckAccount;
import com.resoft.accounting.checkAccount.entity.CheckAccountApply;
import com.resoft.accounting.checkAccount.entity.CheckAccountContentEnum;
import com.resoft.accounting.checkAccount.entity.CheckAccountHistory;
import com.resoft.accounting.checkAccount.entity.CheckAccountNodeEnum;
import com.resoft.accounting.checkAccount.entity.CheckAccountVerify;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.staRepayPlanStatus.entity.StaRepayPlanStatus;
import com.resoft.common.utils.DateUtils;
import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.credit.repayPlan.entity.RepayPlan;
import com.resoft.outinterface.rest.ged.entity.GedRepayment;
import com.thinkgem.jeesite.common.config.Global;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 查账工具类.
 *
 * @author SeppSong
 */
public class CheckAccountUtils {

    private static final Logger logger = LoggerFactory.getLogger(CheckAccountUtils.class);

    public static CheckAccountHistory initSubmitApply(CheckAccountApply apply) {
        CheckAccountHistory checkAccountHistory = initCheckAccountHis(apply);
        checkAccountHistory.setNodeName(CheckAccountNodeEnum.CHECK_ACCOUNT_APPLY.getValue());
        checkAccountHistory.setContent(CheckAccountContentEnum.SUBMIT.getValue());
        checkAccountHistory.setRemark("");
        return checkAccountHistory;
    }

    public static CheckAccountHistory initSubmitMatch(CheckAccountApply apply, String suggestion) {
        CheckAccountHistory checkAccountHistory = initCheckAccountHis(apply);
        checkAccountHistory.setNodeName(CheckAccountNodeEnum.WAIT_FOR_CHECK_ACCOUNT.getValue());
        checkAccountHistory.setContent(CheckAccountContentEnum.ACCOUNT_ENTRY.getValue());
        checkAccountHistory.setRemark(suggestion);
        return checkAccountHistory;
    }

    public static CheckAccountHistory initRefuseMatch(CheckAccountApply apply, String suggestion) {
        CheckAccountHistory checkAccountHistory = initCheckAccountHis(apply);
        checkAccountHistory.setNodeName(CheckAccountNodeEnum.WAIT_FOR_CHECK_ACCOUNT.getValue());
        checkAccountHistory.setContent(CheckAccountContentEnum.REFUSE.getValue());
        checkAccountHistory.setRemark(suggestion);
        return checkAccountHistory;
    }

    private static CheckAccountHistory initCheckAccountHis(CheckAccountApply apply) {
        CheckAccountHistory checkAccountHistory = new CheckAccountHistory();
        checkAccountHistory.setContractNo(apply.getContractNumber());
        checkAccountHistory.setApplyId(apply.getId());
        checkAccountHistory.preInsert();
        return checkAccountHistory;
    }

    public static List<String> extractContractNo(List<CheckAccountVerify> list) {
        List<String> contractNoList = new ArrayList<>();
        for (CheckAccountVerify verify : list) {
            contractNoList.add(verify.getContractNo());
        }
        return contractNoList;
    }

    /**
     * 填充最近还款日及最近还款金额
     * 未匹配到不填充 报错
     * @param verifyList 审核列表
     * @param statusList 还款状态列表
     * @param repayPlanList 还款计划列表
     */
    public static void fillCheckAccountVerify(List<CheckAccountVerify> verifyList, List<StaRepayPlanStatus> statusList,
        List<RepayPlan> repayPlanList) {
        Map<String, Map<String, RepayPlan>> repayPlanMap = new HashMap<>(32);
        for (RepayPlan repayPlan : repayPlanList) {
            String contractNo = repayPlan.getContractNo();
            String periodNum = repayPlan.getPeriodNum();
            Map<String, RepayPlan> innerMap = repayPlanMap.get(contractNo);
            if (innerMap != null) {
                innerMap.put(periodNum, repayPlan);
            } else {
                innerMap = new HashMap<>(16);
                innerMap.put(periodNum, repayPlan);
                repayPlanMap.put(contractNo, innerMap);
            }
        }
        Map<String, StaRepayPlanStatus> statusMap = new HashMap<>(32);
        for (StaRepayPlanStatus status : statusList) {
            String contractNo = status.getContractNo();
            StaRepayPlanStatus innerStatus = statusMap.get(contractNo);
            if (innerStatus != null) {
                innerStatus = status.getPeriodNum().compareTo(innerStatus.getPeriodNum()) > 0 ? status : innerStatus;
                statusMap.put(contractNo, innerStatus);
            } else {
                statusMap.put(status.getContractNo(), status);
            }
        }

        for (CheckAccountVerify verify : verifyList) {
            String contractNo = verify.getContractNo();
            StaRepayPlanStatus status = statusMap.get(contractNo);
            /*
              默认(没有实还)第一期
              如果还款中或逾期取当期
              其余情况取下一期
             */
            String periodNum = "1";
            if (status != null) {
                boolean flag = StringUtils.isNotBlank(status.getRepayPeriodStatus()) && (
                    Constants.PERIOD_STATE_YQ.equals(status.getRepayPeriodStatus()) ||
                        Constants.PERIOD_STATE_DHK.equals(status.getRepayPeriodStatus()));
                if (flag) {
                    periodNum = String.valueOf(Integer.valueOf(status.getPeriodNum() + 1));
                } else {
                    periodNum = status.getPeriodNum();
                }
            }
            if (repayPlanMap.containsKey(contractNo) && repayPlanMap.get(contractNo).containsKey(periodNum)) {
                RepayPlan repayPlan = repayPlanMap.get(contractNo).get(periodNum);
                verify.setRepayDate(repayPlan.getDeductDate());
                verify.setRepayAmount(repayPlan.getRepayAmount());
            } else {
                logger.error("审核列表 -> 合同未匹配到当前还款计划");
            }
        }
    }

    public static boolean matchAmount(CheckAccountApply apply, List<CheckAccountStatement> list) {
        BigDecimal flowAmount = new BigDecimal("0");
        for (CheckAccountStatement flow : list) {
            flowAmount = flowAmount.add(flow.getUnAccountAmount());
        }
        return apply.getCheckAmount().compareTo(flowAmount) <= 0;
    }

    public static List<CheckAccountStatement> updateBankFlow(CheckAccountApply apply, List<CheckAccountStatement> list) {
        BigDecimal checkAmount = apply.getCheckAmount();
        List<CheckAccountStatement> updateList = new ArrayList<>();
        for (CheckAccountStatement flow : list) {
            if (checkAmount.equals(new BigDecimal(0))) {
                break;
            }
            BigDecimal unAccountAmount = flow.getUnAccountAmount();
            if (checkAmount.compareTo(unAccountAmount) >= 0) {
                flow.setEnterAccountAmount(flow.getEnterAccountAmount().add(unAccountAmount));
                flow.setUnAccountAmount(new BigDecimal(0));
                flow.setStatus(StatementStatusEnum.ENTER.getValue());
                checkAmount = checkAmount.subtract(unAccountAmount);
            } else {
                flow.setEnterAccountAmount(flow.getEnterAccountAmount().add(checkAmount));
                flow.setUnAccountAmount(unAccountAmount.subtract(checkAmount));
                checkAmount = checkAmount.subtract(checkAmount);
            }
            updateList.add(flow);
        }
        return updateList;
    }

    public static void updateCheckAccount(List<CheckAccount> list) {
        for (CheckAccount checkAccount : list) {
            Integer applyStatus = Integer.valueOf(checkAccount.getApplyStatus());
            checkAccount.setApplyStatus(ApplyStatusEnum.valueOf(applyStatus).getName());
        }
    }

    public static void initGedRepayment(GedRepayment gedRepayment, List<StaRepayPlanStatus> statusList,
        TransferWithHoldEntity data, AccAccountDCResponse response) throws IOException {
        gedRepayment.setSeqNo(response.getSeq_no());
        gedRepayment.setContractNo(data.getContractNo());
        gedRepayment.setCustId(data.getCustId());
        gedRepayment.setCapitalTerraceNo(response.getCust_Id());
        gedRepayment.setDeductType("11030043");
        gedRepayment.setDeductId(Global.getConfig("deductCustId"));
        gedRepayment.setMobileNum(data.getMobile());
        gedRepayment.setAccountNo(response.getSeq_no());
        if (!statusList.isEmpty()) {
            for (StaRepayPlanStatus status : statusList) {
                if (status != null) {
                    if (StringUtils.isNotBlank(status.getRepayPeriodStatus()) &&
                        (Constants.PERIOD_STATE_YQ.equals(status.getRepayPeriodStatus()) ||
                            Constants.PERIOD_STATE_DHK.equals(status.getRepayPeriodStatus()))) {
                        gedRepayment.setPeriodNum(status.getPeriodNum());
                    }else{
                        int perNum = Integer.parseInt(status.getPeriodNum()) + 1;
                        gedRepayment.setPeriodNum(String.valueOf(perNum));
                    }
                }else{
                    gedRepayment.setPeriodNum("1");
                }
            }
        } else{
            gedRepayment.setPeriodNum("1");
        }
        Date date = new Date();
        gedRepayment.setDataDt(DateUtils.formatDate(date, "yyyy-MM-dd"));
        gedRepayment.setDeductTime(DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss"));
        gedRepayment.setDeductAmount(new BigDecimal(data.getAmount()));
        Map<String, String> innerMap = JsonTransferUtils.json2Bean(data.getRemark(), HashMap.class);
        gedRepayment.setDeductCustName(innerMap.get("deductCustName"));
        gedRepayment.setBank(innerMap.get("bank"));
        gedRepayment.setBankcardNo(innerMap.get("bankCardNo"));
    }
}
