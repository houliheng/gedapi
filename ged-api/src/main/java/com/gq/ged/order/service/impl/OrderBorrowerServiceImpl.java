package com.gq.ged.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gq.ged.common.Constant;
import com.gq.ged.common.Errors;
import com.gq.ged.common.enums.JointCreditFlag;
import com.gq.ged.common.enums.OrderStatus;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.common.http.HttpUtils;
import com.gq.ged.dictionary.dao.model.SystemDictionaryItem;
import com.gq.ged.dictionary.service.DictionaryService;
import com.gq.ged.order.controller.res.DeductResult;
import com.gq.ged.order.dao.model.GedOrder;
import com.gq.ged.order.dao.model.GedRechargeRecord;
import com.gq.ged.order.dao.model.OrderLog;
import com.gq.ged.order.pc.req.ContractRepayPlanDetail;
import com.gq.ged.order.pc.req.RepayPalnDetail;
import com.gq.ged.order.pc.res.*;
import com.gq.ged.order.service.*;
import com.gq.ged.user.dao.model.User;
import com.gq.ged.user.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Levi on 2018/4/18.
 */
@Service
public class OrderBorrowerServiceImpl implements OrderBorrowerService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    OrderService orderService;

    @Resource
    UserService userService;

    @Resource
    OrderLogService orderLogService;

    @Resource
    RechargeRecordService rechargeRecordService;

    @Resource
    OrderGuarantLoanService orderGuarantLoanService;

    @Resource
    DictionaryService dictionaryService;

    @Value("${gq.borrow.url}")
    String gqBorrowUrl;

    @Override
    public BorrowerIndexResForm index(User user) throws Exception {
        if (user == null) {
            throw new BusinessException(Errors.BORROWER_EXCEPTION, "借款人首页user查询失败");
        }
        user = userService.getUserById(user.getId());
        BorrowerIndexResForm res = new BorrowerIndexResForm();

        res.setUserName(user.getUsername());
        // res.setEmail(user.get);?
        res.setMobile(nullUtil(user.getMobile()));
        res.setSocialCreditCode(nullUtil(user.getSocialCreditCode()));
        res.setIsOpenAccount(isOpenAccount(user));

        //// 第二版面
        Integer orderCount = 0;
        BigDecimal orderingAmount = new BigDecimal(0);
        GedOrder condition = new GedOrder();
        condition.setUserId(user.getId());
        // condition.setStatus(OrderStatus.FKCG.getCode());
        List<GedOrder> orders = orderService.getOrdersByCondition(condition);
        if (orders == null || orders.size() == 0) {
            res.setOrders(new ArrayList<>());
        } else {
            List<LoanRecordResForm> datas = new ArrayList<>();
            for (GedOrder order : orders) {
                if (order.getStatus() >= OrderStatus.FKCG.getCode()
                        && order.getStatus() < OrderStatus.YHQ.getCode()) {
                    orderingAmount = orderingAmount.add(order.getLoanAmount());
                    //   if (order.getStatus() == OrderStatus.ZLWSZ.getCode()) {
                    LoanRecordResForm resLoan = setLoanRecordResForm(order, user);
                    datas.add(resLoan);
                    //     }
                    orderCount++;
                }
            }
            res.setOrderingLoanAmount(orderingAmount.setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
            res.setOrders(datas);
        }
        res.setOrderCount(orderCount + "");
        JSONObject object = this.borrowConditonByUserId(user.getId());
        if (object != null && Constant.RES_CODE_SUCCESS.equals(object.get("code"))) {
            // res.setOrderingLoanAmount(object.getString("loanTotalMoney"));
            res.setNowRepayAmount(object.getString("contractStayMoney"));

        }
        // 获取账户余额
        BigDecimal accountBalance = orderService.accountBalance(null, user.getId());
        res.setAccountBalance(accountBalance.setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");

        return res;
    }

    @Override
    public List<LoanRecordResForm> getLoanRecordsByUserId(Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new BusinessException(Errors.BORROWER_EXCEPTION, "借款记录user查询失败");
        }
        List<LoanRecordResForm> res = new ArrayList<>();
        GedOrder condition = new GedOrder();
        condition.setUserId(user.getId());
        List<GedOrder> orders = orderService.getOrdersByCondition(condition);
        if (orders == null || orders.size() == 0) {
            return res;
        } else {
            for (GedOrder order : orders) {
                LoanRecordResForm resLoan = setLoanRecordResForm(order, user);
                res.add(resLoan);
            }
        }
        return res;
    }

    @Override
    public PageInfo<LoanRecordResForm> getLoanRecordsPageByUserId(Long userId, Integer pageNum,
                                                                  Integer pageSize) {
        pageNum = pageNum == null ? Constant.PAGE_NUM : pageNum;
        pageSize = pageSize == null ? Constant.PAGE_SIZE : pageSize;
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new BusinessException(Errors.BORROWER_EXCEPTION, "借款记录user查询失败");
        }
        List<LoanRecordResForm> res = new ArrayList<>();
        GedOrder condition = new GedOrder();
        condition.setUserId(user.getId());
        PageHelper.startPage(pageNum, pageSize);
        List<GedOrder> orders = orderService.getOrdersByCondition(condition);
        PageInfo pageInfo = new PageInfo(orders);
        if (orders == null || orders.size() == 0) {
            return pageInfo;
        } else {
            for (GedOrder order : orders) {
                LoanRecordResForm resLoan = setLoanRecordResForm(order, user);
                res.add(resLoan);
            }
            pageInfo.setList(res);
        }
        return pageInfo;
    }

    @Override
    public LoanRecordResForm getJoinLoanRecordsPageByUserId(Long userId, String masterCode) {
        User user = userService.getUserById(userId);
        if (user == null)
            throw new BusinessException(Errors.BORROWER_EXCEPTION, "借款记录user查询失败");
        List<LoanRecordResForm> res = new ArrayList<>();
        GedOrder condition = new GedOrder();
        condition.setUserId(user.getId());
        condition.setMasterOrderCode(masterCode);
        List<GedOrder> orders = orderService.getOrdersByCondition(condition);
        if (orders != null && orders.size() >= 0){
            return setLoanRecordResForm(orders.get(0), user);
        }
        return null;
    }

    @Override
    public OrderDetailResForm orderDetail(String orderNo) throws Exception {
        // 1 查询订单基本信息
        GedOrder order = orderService.selectOrderByOrderNo(orderNo);
        if (order == null) {
            throw new BusinessException(Errors.BORROWER_EXCEPTION, "借款详情订单编号查询失败");
        }
        OrderDetailResForm res = new OrderDetailResForm();
        res.setIsFiveDays("0");
        res.setOrderNo(orderNo);
        copyOrderMianInfos(res, order);

        if (order.getStatus() >= OrderStatus.FKCG.getCode()
                && order.getStatus() <= OrderStatus.YQYH.getCode()) {
            String url = orderService.getContractUrlByContractNo(order.getContractCode());
            res.setContractUrl(url);

            // 2 查询还款计划列表
            Map<String, String> param = new HashMap<>();
            param.put("dateFlag", "");
            param.put("repayPeriodStatus", "");
            param.put("orderNo", order.getOrderCode());
            param.put("jointCredit", JointCreditFlag.NOT_JOINTCREDIT.getCode() + "");

            List<RepaymentItem> datas = new ArrayList<>();
            List<DeductResult> list = null;
            Map<String, Object> map = orderService.LoanSystem(param);
            if (map != null) {
                list = (List<DeductResult>) map.get("planList");
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Integer count = 0;
            Integer overdueNum = -1; // 逾期记录位置
            Integer nomalNum = -1; // 正常还款记录位置
            if (list != null && list.size() != 0) {
                for (DeductResult deductResult : list) {
                    if ("0200".equals(deductResult.getRepayPeriodStatus())
                            || "0300".equals(deductResult.getRepayPeriodStatus())) {
                        // 查找逾期记录首条
                        if (overdueNum < 0) {
                            if ("0300".equals(deductResult.getRepayPeriodStatus())) {
                                overdueNum = count;
                            }
                        }
                        // 查找未逾期的下一期记录
                        if (nomalNum < 0) {
                            if ("0200".equals(deductResult.getRepayPeriodStatus()) && simpleDateFormat
                                    .parse(deductResult.getDeductDate()).getTime() >= simpleDateFormat
                                    .parse(simpleDateFormat.format(new Date(System.currentTimeMillis())))
                                    .getTime()) {
                                nomalNum = count;
                            }
                        }
                    }

                    RepaymentItem repaymentItem = new RepaymentItem();
                    repayCopyItem(deductResult, repaymentItem);
                    datas.add(repaymentItem);
                    count++;
                }
                Integer thisPerisNum = -1;
                if (nomalNum >= 0) {
                    thisPerisNum = nomalNum;
                }
                if (thisPerisNum != -1) {
                    if (thisPerisNum == 0) {
                        if ("0200".equals(list.get(thisPerisNum).getRepayPeriodStatus())) {
                            datas.get(thisPerisNum).setRepayPeriodStatus("待还款");
                        }
                    } else if (thisPerisNum > 0) {
                        if (!"0200".equals(list.get(thisPerisNum - 1).getRepayPeriodStatus())
                                && "0200".equals(list.get(thisPerisNum).getRepayPeriodStatus())
                                && simpleDateFormat.parse(list.get(thisPerisNum - 1).getDeductDate())
                                .getTime() >= simpleDateFormat
                                .parse(simpleDateFormat.format(new Date(System.currentTimeMillis())))
                                .getTime()) {
                        } else {
                            datas.get(thisPerisNum).setRepayPeriodStatus("待还款");
                        }
                    }
                }
            }
            //计算日期间隔天数  nimalgbsb的要求
            boolean flag = true;
            for (RepaymentItem repaymentItem: datas) {
                if (repaymentItem.getRepayPeriodStatus().equals("待还款")){
                    flag = false;
                    String dbtime1 = repaymentItem.getDeductDate();// "2017-02-23";  //第二个日期
                    String dbtime2 = simpleDateFormat.format(new Date()) ;//new Date();//"2017-02-22";  //第一个日期
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date1 = format.parse(dbtime1);
                    Date date2 = format.parse(dbtime2);
                    int a = (int) ((date1.getTime() - date2.getTime()) / (1000*3600*24));
                    if (a>3){
                        res.setIsFiveDays("1");
                    }
                    break;

                }
            }
            if (flag)
                res.setIsFiveDays("1");
            res.setRepaymentPlans(datas);
            orderGuarantLoanService.isCanpay(list, datas);

        }



        res.setIsCanRepay(order.getRepaymentLockFlag() == null ? "1" :order.getRepaymentLockFlag()+ "");
        return res;
    }

    @Override
    public List<ContractRepayPlanDetail> myRepayments(Long userId) throws Exception {
        List<Map<String, String>> orderNosAndContractNos = new ArrayList<>();
        // data.put("jsonRes",this.planDetail(datas));
        // data.put("nos",map);
        Map<String, Object> res = this.planDetailByUserId(userId);
        JSONObject data = (JSONObject) res.get("jsonRes");
        Map<String, String> nos = (Map<String, String>) res.get("nos");
        List<ContractRepayPlanDetail> datas = this.contractRepayPlanDetails(data);
        if (datas != null) {
            for (ContractRepayPlanDetail c : datas) {
                c.setOrderNo(nos.get(c.getContractNo()));
                List<RepayPalnDetail> rs = c.getRepayPalnDetails();
                if (rs != null && rs.size() > 0) {
                    for (RepayPalnDetail d : rs) {

                        if ("0200".equals(d.getRepayStatus())) {
                            d.setRepayStatus("待还款");
                        } else {
                            d.setRepayStatus(getStatusInfo(d.getRepayStatus()));
                        }
                    }
                }
            }
        }
        return datas;
    }

    @Override
    public ServiceFeeDetailResForm serviceFeeDetail(String orderNo) {

        // 1 查询订单基本信息
        GedOrder order = orderService.selectOrderByOrderNo(orderNo);
        if (order == null) {
            throw new BusinessException(Errors.BORROWER_EXCEPTION, "服务费详情订单编号查询失败");
        }
        ServiceFeeDetailResForm res = new ServiceFeeDetailResForm();
        copyOrderMianInfos(res, order);
        res.setOrderNo(order.getOrderCode());
        res.setServiceFee(order.getServiceFee().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
        if ((order.getStatus() == OrderStatus.FKCG.getCode()
                || order.getStatus() == OrderStatus.YCTX.getCode())) {
            GedRechargeRecord rechargeRecord =
                    rechargeRecordService.getRechargeRecordByOrderNo(order.getOrderCode());
            res.setIsPay(isCanPay(order, rechargeRecord) + "");
        } else {
            res.setIsPay("-1");
        }
        return res;
    }

    @Override
    public WithdrawDetailResForm withdrawDetail(String orderNo) throws Exception {
        GedOrder order = orderService.selectOrderByOrderNo(orderNo);
        if (order == null) {
            throw new BusinessException(Errors.BORROWER_EXCEPTION, "提箱详情订单编号查询失败");
        }
        WithdrawDetailResForm res = new WithdrawDetailResForm();
        copyOrderMianInfos(res, order);
        WithDrawViewResForm beforeWithDr = orderService.beforeWithDr(orderNo, order.getUserId());
        res.setOrderNo(orderNo);
        // @ApiModelProperty("提现到银行卡")
        // private String bankInfo;
        res.setLoanAmount(order.getLoanAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
        res.setAccountAmt(beforeWithDr.getAccountAmt());
        res.setActualAmt(beforeWithDr.getActualAmt());
        if (order.getLoanAmount() != null) {
            res.setCanWithdrawAmt(order.getLoanAmount().subtract(order.getWithdrawAmount() == null ? new BigDecimal(0) : order.getWithdrawAmount()).setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
        }
        res.setBankInfo(beforeWithDr.getBankName() + "( " + beforeWithDr.getBankNum() + " )");
        return res;
    }

    /**
     * 复制订单主题信息
     */
    private void copyOrderMianInfos(BorrowOrderBaseInfo res, GedOrder order) {
        res.setLoanAmount(order.getLoanAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
        res.setPeriod(order.getLoanTerm() + "");
        res.setRate(order.getRateDay().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "%");
        if (order.getLoanType() != null) {
            SystemDictionaryItem item =
                    dictionaryService.getDictionaryByParam("LOAN_TYPE", order.getLoanType() + "");
            res.setLoanType(item == null ? "" : item.getValue());
        }

        // 查询还款日
        OrderLog orderLog =
                orderLogService.getOrderLogByOrderId(order.getId(), OrderStatus.FKCG.getCode());
        if (orderLog != null) {
            // 计算结束日
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(orderLog.getCreateTime());
            c.add(Calendar.MONTH, order.getLoanTerm());
            res.setDate(
                    f.format(orderLog.getCreateTime()) + " 至 " + f.format(new Date(c.getTimeInMillis())));
        }
        if (order.getStockFlag() == 1) {
            res.setStatus(OrderStatus.resove(order.getStatus()).getFeStatusDesc());
        } else {
            if (order.getSignContractFlag() == 1) {
                res.setStatus(OrderStatus.resove(order.getStatus()).getFeStatusDesc());
            } else if (order.getSignContractFlag() == 0) {
                res.setStatus("待签署合同");
            }
            if (StringUtils.isBlank(order.getContractCode())) {
                res.setStatus(OrderStatus.resove(order.getStatus()).getFeStatusDesc());
            }
        }
        res.setLoanPurpose(order.getLoanPurpose() + "");
        if (order.getLoanPurpose() != null) {
            SystemDictionaryItem item =
                    dictionaryService.getDictionaryByParam("LOAN_PURPOST", order.getLoanPurpose() + "");
            res.setLoanPurpose(item == null ? "" : item.getValue());
        }

  }

    @Override
    public void repayCopyItem(DeductResult deduct, RepaymentItem repaymentItem) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        repaymentItem.setDeductDate(deduct.getDeductDate());
        repaymentItem.setRepayPeriodStatus(getStatusInfo(deduct.getRepayPeriodStatus()));
        repaymentItem.setPeriodNum(deduct.getPeriodNum());
        repaymentItem.setRepayAmount(deduct.getRepayAmount());
        repaymentItem.setCompensatoryAmount(deduct.getCompensatoryAmount());
        repaymentItem.setStayMoney(deduct.getStayMoney());
        repaymentItem.setFactAmount(
                calcualterFactPayAmount(deduct).setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
        if ("0100".equals(deduct.getRepayPeriodStatus())
                || "0300".equals(deduct.getRepayPeriodStatus())) {
            repaymentItem.setFineAmount(deduct.getFineAmount());
            repaymentItem.setPenaltyAmount(deduct.getPenaltyAmount());
        } else {
            repaymentItem.setFineAmount("0.00");
            repaymentItem.setPenaltyAmount("0.00");
        }

    }


    /**
     * 计算实际还款金额
     *
     * @param deduct
     * @return
     */
    public BigDecimal calcualterFactPayAmount(DeductResult deduct) {
        if (deduct == null) {
            return new BigDecimal(0);
        }
        makeToero(deduct);
        return new BigDecimal(deduct.getFactServiceFee())
                .add(new BigDecimal(deduct.getFactManagementFee()))
                .add(new BigDecimal(deduct.getFactInterestAmount()))
                .add(new BigDecimal(deduct.getFactOverdueCapialAmount()))
                .add(new BigDecimal(deduct.getFactPenaltyAmount()))
                .add(new BigDecimal(deduct.getFactFineAmount()));
    }

    public void makeToero(DeductResult plan) {
        if (plan != null) {
            BigDecimal factServiceFee = StringUtils.isBlank(plan.getFactServiceFee()) ? new BigDecimal(0)
                    : new BigDecimal(plan.getFactServiceFee());// 实还服务费
            BigDecimal factManagermentFee = StringUtils.isBlank(plan.getFactManagementFee())
                    ? new BigDecimal(0) : new BigDecimal(plan.getFactManagementFee());// 实还账户管理费
            BigDecimal factInterestAmount = StringUtils.isBlank(plan.getFactInterestAmount())
                    ? new BigDecimal(0) : new BigDecimal(plan.getFactInterestAmount());// 实还利息
            BigDecimal factOverdueCapialAmount = StringUtils.isBlank(plan.getFactOverdueCapialAmount())
                    ? new BigDecimal(0) : new BigDecimal(plan.getFactOverdueCapialAmount());// 实还本金
            BigDecimal factPenaltyAmount = StringUtils.isBlank(plan.getFactPenaltyAmount())
                    ? new BigDecimal(0) : new BigDecimal(plan.getFactPenaltyAmount());// 实还违约金
            BigDecimal factFineAmount = StringUtils.isBlank(plan.getFactFineAmount()) ? new BigDecimal(0)
                    : new BigDecimal(plan.getFactFineAmount());// 实还违约金
            plan.setFactFineAmount(factFineAmount.setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
            plan.setFactManagementFee(factManagermentFee.setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
            plan.setFactOverdueCapialAmount(
                    factOverdueCapialAmount.setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
            plan.setFactServiceFee(factServiceFee.setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
            plan.setFactInterestAmount(factInterestAmount.setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
            plan.setFactPenaltyAmount(factPenaltyAmount.setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
        }
    }

    public String getStatusInfo(String code) {
        if ("0100".equals(code)) {
            return "逾期结清";
        } else if ("0200".equals(code)) {
            return "未到期";
        } else if ("0300".equals(code)) {
            return "逾期";
        } else if ("0400".equals(code)) {
            return "正常结清";
        } else if ("0500".equals(code)) {
            return "提前还款";
        } else if ("0600".equals(code)) {
            return "展期结清";
        }
        return "";
    }

    private LoanRecordResForm setLoanRecordResForm(GedOrder order, User user) {
        LoanRecordResForm loan = new LoanRecordResForm();
        loan.setOrderNo(order.getOrderCode());
        loan.setIsSignContract(order.getSignContractFlag() + "");
        loan.setContractNo(order.getContractCode());
        loan.setLoanAmount(order.getLoanAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
        loan.setPeriod(order.getLoanTerm() + "");
        loan.setRate(order.getRateDay() == null ? ""
                : order.getRateDay().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "%");
        loan.setRepayWay(nullUtil(order.getRepaymentStyle()));
        // 查询还款日
        OrderLog orderLog =
                orderLogService.getOrderLogByOrderId(order.getId(), OrderStatus.FKCG.getCode());
        if (orderLog != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(orderLog.getCreateTime());
            loan.setRepayDate(getRepaymentDay(calendar) + "/月");
        }
        loan.setStatus(OrderStatus.resove(order.getStatus()).getFeStatusDesc());
        loan.setStatusCode(OrderStatus.resove(order.getStatus()).getCode() + "");
        loan.setIsStockFlag(order.getStockFlag() + "");
        // 是否存在支付按钮
        // if ((order.getStatus() == OrderStatus.FKCG.getCode()
        // || order.getStatus() == OrderStatus.YCTX.getCode())) {
        GedRechargeRecord rechargeRecord =
                rechargeRecordService.getRechargeRecordByOrderNo(order.getOrderCode());
        loan.setIsCanServiceFee(isCanPay(order, rechargeRecord) + "");
        // }
        // 是否存在提现按钮
        loan.setIsCanWithdraw(isWithdraw(order) + "");
        // 是否存在还款按钮
        if (order.getStatus() >= OrderStatus.FKCG.getCode()
                && order.getStatus() < OrderStatus.YHQ.getCode()) {
            loan.setIsCanRepay("1");
        } else {
            loan.setIsCanRepay("0");
        }

    return loan;
  }

    /**
     * 是否能够提现
     *
     * @param order
     * @return
     */
    private int isWithdraw(GedOrder order) {
        if (order.getStatus() >= OrderStatus.FKCG.getCode()
                && order.getStatus() <= OrderStatus.YQYH.getCode()) {
            if (order.getWithdrawFlag() == null || order.getWithdrawFlag() == 2 // 可提现
                    || order.getWithdrawFlag() == 5) {
                return 1;
            } else if (order.getWithdrawFlag() == 1 || order.getWithdrawFlag() == 3) { // 提现处理中
                return -1;
            }
        } else {
            return 0;
        }
        return 0; // 提现完成
    }

    /**
     * 判断缴费按钮是否出现
     *
     * @param order
     * @param rechargeRecord
     * @return
     */
    private Integer isCanPay(GedOrder order, GedRechargeRecord rechargeRecord) {
        // 订单处于放款成功 和结清之间的状态 才有可能显示缴费
        if (order.getStatus() >= OrderStatus.FKCG.getCode()
                && order.getStatus() <= OrderStatus.YQYH.getCode()) {
            // 债股结合无服务费
            if (order != null && order.getLoanType() != null && order.getLoanType() == 6)
                return -1;
            //服务费摊入还款计划  不需要独立缴纳服务费
            if (order != null && order.getServiceFeeWay() != null && order.getServiceFeeWay() == 2)
                return -1;

            if (rechargeRecord == null) {
                if (order.getServiceFeeFlag() == 1) {
                    return 3;
                } else {
                    return order.getServiceFeeFlag();
                }
            } else {
                return rechargeRecord.getStatus();
            }
        } else {
            return -1;
        }
    }

    /**
     * 判断还款日
     *
     * @param calendar
     * @return
     */
    @Override
    public int getRepaymentDay(Calendar calendar) {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day > 1) {
            return day - 1;
        } else {
            calendar.add(Calendar.MONTH, -1);
            int dayNum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            if (dayNum == 31) {
                return dayNum - 1;
            } else {
                return dayNum;
            }
        }
    }

    /**
     * 判断是否开户
     *
     * @param user
     * @return
     */
    @Override
    public String isOpenAccount(User user) {
        if (user.getUserRole() != null) {
            if (user.getUserRole() == 0) {
                // 借款人 开企业户 + 个人户
                if (user.getCheckAccountFlag() != null && user.getCompanyAccountFlag() != null
                        && user.getCheckAccountFlag() == 4 && user.getCompanyAccountFlag() == 1) {
                    return "1";
                } else {
                    return "0";
                }
            } else if (user.getUserRole() == 1) {
                // 担保人 只开个人户
                if (user.getCheckAccountFlag() != null && user.getCheckAccountFlag() == 4) {
                    return "1";
                } else {
                    return "0";
                }
            } else if (user.getUserRole() == 2 || (user.getUserRole() == 3)) {
                // 只开企业户
                if (user.getCompanyAccountFlag() != null && user.getCompanyAccountFlag() == 1) {
                    return "1";
                } else {
                    return "0";
                }
            }
        } else {
            return "0";
        }
        return "0";
    }

    @Override
    public JSONObject planDetail(List<String> contractNos) throws Exception {
        Map<String, Object> map = new HashedMap();
        map.put("contractNo", contractNos);
        logger.info("（1）planDetail 调用借款系统 输入的合同编号list为：" + JSONObject.toJSONString(contractNos));
        JSONObject jsonObject =
                HttpUtils.doPost(gqBorrowUrl + "/api/planDetail", JSONObject.toJSONString(map));
        logger.info("（2）planDetail 调用借款系统获取结果为：" + jsonObject);
        return (jsonObject);
    }

    @Override
    public Map<String, Object> planDetailByUserId(Long userId) throws Exception {
        List<GedOrder> listorder = orderService.selectGedOrder(userId);
        if (listorder == null || listorder.size() == 0) {
            return null;
        }
        List<String> datas = new ArrayList<>();
        Map<String, String> map = new HashedMap();
        for (GedOrder gedOrder : listorder) {
            if (gedOrder.getStatus() != 0 && gedOrder.getStatus() < 180) {
                if (gedOrder.getContractCode() != null) {
                    datas.add(gedOrder.getContractCode());
                }

                map.put(gedOrder.getContractCode(), gedOrder.getOrderCode());
            }
        }
        Map<String, Object> data = new HashedMap();
        data.put("jsonRes", this.planDetail(datas));
        data.put("nos", map);
        return data;
    }

    @Override
    public JSONObject borrowConditon(List<String> contractNos) throws Exception {
        Map<String, Object> map = new HashedMap();
        map.put("contractNo", contractNos);
        logger.info("（1）borrowConditon 调用借款系统 输入的合同编号list为：" + JSONObject.toJSONString(contractNos));
        JSONObject jsonObject =
                HttpUtils.doPost(gqBorrowUrl + "/api/borrowConditon", JSONObject.toJSONString(map));
        logger.info("（2）borrowConditon 调用借款系统获取结果为：" + jsonObject);
        return (jsonObject);
    }

    @Override
    public JSONObject borrowConditonByUserId(Long userId) throws Exception {
        List<GedOrder> listorder = orderService.selectGedOrder(userId);
        if (listorder == null || listorder.size() == 0) {
            return null;
        }
        List<String> datas = new ArrayList<>();
        for (GedOrder gedOrder : listorder) {
            if (gedOrder.getStatus() != 0 && gedOrder.getStatus() < 180) {
                if (gedOrder.getContractCode() != null) {
                    datas.add(gedOrder.getContractCode());
                }
            }
        }
        return this.borrowConditon(datas);
    }

    @Override
    public List<ContractRepayPlanDetail> contractRepayPlanDetails(JSONObject json) {
        if (json != null && Constant.RES_CODE_SUCCESS.equals(json.get("code"))) {
            String msg = (String) json.getJSONObject("repayPlanInfo").getString("data");
            List<ContractRepayPlanDetail> data =
                    JSONObject.parseArray(msg, ContractRepayPlanDetail.class);
            return data;
        }
        return null;
    }


    private String nullUtil(String msg) {
        return msg == null ? "" : msg;
    }
}
