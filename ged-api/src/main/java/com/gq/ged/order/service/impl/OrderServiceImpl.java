package com.gq.ged.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gq.ged.account.dao.model.Account;
import com.gq.ged.account.dao.model.AccountCompany;
import com.gq.ged.account.service.AccountService;
import com.gq.ged.activemq.service.JmsProvider;
import com.gq.ged.common.Constant;
import com.gq.ged.common.Errors;
import com.gq.ged.common.enums.*;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.common.http.HttpUtils;
import com.gq.ged.common.utils.RandomUtil;
import com.gq.ged.common.utils.SecurityUtils;
import com.gq.ged.common.utils.copy.BeanCopyTools;
import com.gq.ged.common.utils.date.DateFormatUtils;
import com.gq.ged.dictionary.dao.model.SystemDictionaryItem;
import com.gq.ged.dictionary.service.DictionaryService;
import com.gq.ged.message.service.MsgService;
import com.gq.ged.order.controller.req.*;
import com.gq.ged.order.controller.res.*;
import com.gq.ged.order.dao.mapper.GedOrderMapper;
import com.gq.ged.order.dao.mapper.GedOrderRepaymentPlanMapper;
import com.gq.ged.order.dao.mapper.GedOrderTagsMapper;
import com.gq.ged.order.dao.mapper.GedRepaymentRecordMapper;
import com.gq.ged.order.dao.model.*;
import com.gq.ged.order.pc.req.*;
import com.gq.ged.order.pc.res.*;
import com.gq.ged.order.service.*;
import com.gq.ged.user.dao.mapper.UserMapper;
import com.gq.ged.user.dao.model.User;
import com.gq.ged.user.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by wrh on 2018/1/18.
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT,
    rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

  Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

  @Resource
  GedOrderMapper gedOrderMapper;
  @Resource
  GedOrderTagsMapper gedOrderTagsMapper;
  @Resource
  AccountService accountService;
  @Resource
  RedissonClient redissonClient;
  @Resource
  GedOrderRepaymentPlanMapper gedOrderRepaymentPlanMapper;
  @Resource
  UserMapper userMapper;
  @Resource
  GedOrderRepaymentPlanMapper planMapper;
  @Resource
  DictionaryService dictionaryService;
  @Resource
  OrderLogService orderLogService;
  @Resource
  UserService userService;
  @Resource
  MsgService msgService;
  @Resource
  JmsProvider jmsProvider;

  @Resource
  GedRepaymentRecordMapper gedRepaymentRecordMapper;
  @Resource
  OrderGuarantLoanService orderGuarantLoanService;
  @Resource
  RechargeRecordService rechargeRecordService;

  @Resource
  RechargeRepaymentRecordService repaymentRecordService;

  @Value("${gq.borrow.url}")
  String borrowUrl;

  @Value("${gq.ged.call.capital.url}")
  String capitalUrl;

  @Value("${gq.ged.get.mchn}")
  String mchn;
  @Value("${gq.ged.get.rechargeTradeType}")
  String rechargeTradeType;
  @Value("${gq.ged.get.compensationTradeType}")
  String compensationTradeType;
  @Value("${gq.ged.get.compensaTradeType}")
  String compensaTradeType;
  @Value("${gq.ged.get.rechargeCallBackTradeType}")
  String rechargeCallBackTradeType;
  @Value("${gq.ged.get.accountsType}")
  String accountsType;

  /*
   * @Value("${gq.ged.get.back_notify_url}") String backNotifyUrl;
   */
  /*
   * @Value("${gq.ged.get.page_notify_url}") String pageNotifyUrl;
   */
  @Value("${gq.ged.withDraw.tradeType}")
  String withDrawTradeType;
  @Value("${gq.ged.withDraw.applyType}")
  String withDrawApplyType;
  @Value("${gq.ged.accountBalance.tradeType}")
  String accountBalanceTradeType;
  /*
   * @Value("${gq.ged.gedUpdateOrderStatus.url}") String gedUpdateOrderUrl;
   */
  /*
   * @Value("${gq.ged.ziChanDuan.serviceFee.url}") String serviceFeeUrl;
   */
  /*
   * @Value("${gq.ged.firstWithDeposit.url}") String firstWithDepositUrl;
   */
  /*
   * @Value("${gq.ged.gedRepaymentPlan.url}") String gedRepaymentPlanUrl;
   */
  @Value("${ged.pay.mchn}")
  String gedPayMchn;
  @Value("${gq.ged.h5.url}")
  String gedh5url;
  /*
   * @Value("${gq.ged.repayment.borrow.url}") String repaymentBorrowUrl;
   */
  @Value("${gq.ged.api.url}")
  String gedapiUrl;
  @Value("${gq.ged.get.url}")
  String gedGETBackUrl;
  @Value("${gq.ged.fastDFS.url}")
  String getFastDFSurl;



  @Override
  public GedOrderIdResForm createOrder(GedOrderReqForm reqForm, User userInfo) throws Exception {
    GedOrderIdResForm gedOrderIdResForm = new GedOrderIdResForm();
    // 身份证实名标识
    User user = userMapper.selectByPrimaryKey(userInfo.getId());
    if (user == null) {
      throw new BusinessException(Errors.USER_MOBILE_NOT_EXISTS_ERROR);
    }
    logger.info("api传递的参数:" + JSONObject.parseObject(JSON.toJSONString(reqForm)));
    // Integer idCardFlag = user.getIdCardFlag();
    // logger.info("身份证实名标识:=" + idCardFlag);
    // if (idCardFlag == null || idCardFlag == 0) {
    // gedOrderIdResForm.setIdCardFlag(0);
    // return gedOrderIdResForm;
    // }
    // 查询最新的一笔订单 是否有申请中的进件
    List<GedOrder> listorder = selectGedOrder(userInfo.getId());
    if (listorder != null && listorder.size() != 0) {
      GedOrder ged = listorder.get(0);
      if (ged.getStatus() != 0 && ged.getStatus() < 180) {
        throw new BusinessException(Errors.NOTE_UNCOMMIT_ERROR);
      }
    }
    // gedOrderIdResForm.setIdCardFlag(idCardFlag);
    GedOrder gedOrder = new GedOrder();
    BeanCopyTools.copyProperties(reqForm, gedOrder);
    gedOrder.setCreateTime(new Date());
    gedOrder.setModifyTime(new Date());
    gedOrder.setUserId(userInfo.getId());
    gedOrder.setRecommendCode(user.getRecommendCode());
    gedOrder.setStatus(OrderStatus.ZLWSZ.getCode());
    gedOrder.setReadFlag(0);
    gedOrder.setSysFlag(1);// 自主
    gedOrder.setLoanAmount(reqForm.getLoanAmount().multiply(new BigDecimal(10000)));
    gedOrder.setPersonCardNum(user.getIdCardNum());
    gedOrder.setCompanyCardNum(reqForm.getSocialCreditCode());
    gedOrder.setPersonCardNum(user.getIdCardNum());
    String orderCode = timeCode();
    gedOrder.setOrderCode(orderCode);
    gedOrderMapper.insertSelective(gedOrder);
    long gedId = gedOrder.getId();
    logger.info("主键ID=====" + gedId);
    // 保存标签表
    GedTag(reqForm, gedId);
    // 更新用户表中的字段信息
    updateOrderUser(reqForm, userInfo);
    // 新增日志表
    createGedOrderLog(userInfo, gedId);
    GedOrder ged = gedOrderMapper.selectByPrimaryKey(gedId);
    // 推到借款系统
    pushLoan(gedOrder, ged.getOrderCode(), reqForm, user.getIdCardNum(), user.getUsername());
    gedOrderIdResForm.setOrderNo(ged.getOrderCode());// 返回订单编号
    return gedOrderIdResForm;
  }

  @Override
  public Integer insertOrder(GedOrder order) {
    return gedOrderMapper.insertSelective(order);
  }


  public void GedTag(GedOrderReqForm reqForm, long gedId) {
    GedOrderTags gedOrderTags = new GedOrderTags();
    gedOrderTags.setRefId(gedId);
    gedOrderTags.setRefType(1);// 订单
    gedOrderTags.setCreateTime(new Date());
    String tags = reqForm.getOrderTags();
    if (StringUtils.isNotBlank(tags)) {
      String[] orderTags = tags.split(",");
      if (orderTags != null && orderTags.length != 0) {
        logger.info("标签集合==========" + orderTags.length);
        for (int i = 0; i < orderTags.length; i++) {
          gedOrderTags.setTagKey(Integer.parseInt(orderTags[i]));
          TagValue(Integer.parseInt(orderTags[i]), gedOrderTags);
          gedOrderTagsMapper.insertSelective(gedOrderTags);
        }
      }
    }
  }

  public void updateOrderUser(GedOrderReqForm reqForm, User userInfo) {
    User user = new User();
    user.setId(userInfo.getId());
    user.setBusinessLicence(reqForm.getBusinessLicence());// 营业执照编号
    user.setSocialCreditCode(reqForm.getSocialCreditCode());// 社会信用统一代码
    user.setCompanyCardCode(reqForm.getSocialCreditCode());
    user.setTaxCode(reqForm.getTaxCode());// 税务登记号
    user.setOrganizationCode(reqForm.getOrganizationCode());// 组织机构代码
    user.setCompanyName(reqForm.getCompanyName());// 企业名称
    user.setCompanyCardType(reqForm.getCompanyType());// 企业类型
    userMapper.updateByPrimaryKeySelective(user);
  }

  // 新增日志表
  public void createGedOrderLog(User userInfo, long orderId) {
    OrderLog log = new OrderLog();
    log.setOrderId(orderId);
    log.setCreateId(userInfo.getId());
    log.setSourceStatus(0);
    log.setTargetStatus(100);
    log.setCreateTime(new Date());
    orderLogService.createOrderlog(log);
  }


  public void pushLoan(GedOrder gedOrder, String orderNo, GedOrderReqForm reqForm, String idCardNum,
      String userName) throws Exception {
    Map<String, String> param = new HashMap<String, String>();
    param.put("custName", userName);// 客户名称
    param.put("idNum", idCardNum);// 证件号码
    param.put("applyDate",
        DateFormatUtils.formatDate(gedOrder.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));// 申请日期
    param.put("productType", "9");// 产品类型
    param.put("productName", "冠e贷");// 产品名称
    param.put("custType", "2");// 客户类型----企业
    param.put("phoneNo", gedOrder.getContactPhone());// 移动电话
    param.put("applyAmount", gedOrder.getLoanAmount().toString());// 申请金额 .divide(new
                                                                  // BigDecimal(10000)) 推送数据单位是元
    param.put("applyPeriod", gedOrder.getLoanTerm().toString());// 申请期限
    param.put("companyName", gedOrder.getCompanyName());// 企业名称
    param.put("companyCardType", gedOrder.getCompanyType().toString());// 企业证件类型
    param.put("companyCardNum", reqForm.getSocialCreditCode());// 企业证件号--（统一社会信用代码）
    logger.info("getBusinessLicence====" + reqForm.getBusinessLicence());
    param.put("businessLicence", reqForm.getBusinessLicence());// 营业执照
    param.put("organizationCode", reqForm.getOrganizationCode());// 组织结构代码
    param.put("taxCode", reqForm.getTaxCode());// 税务号
    param.put("province", gedOrder.getProvinceId().toString());// 省
    param.put("city", gedOrder.getCityId().toString());// 市
    param.put("district", gedOrder.getAreaId().toString());// 区
    param.put("contCity", gedOrder.getManagementAddr().toString());// 所在城市
    param.put("applyId", orderNo);// 申请ID
    param.put("loanPurpost", gedOrder.getLoanPurpose().toString());// 借款用途
    logger.info("推借款系统数据参数:" + JSONObject.parseObject(JSON.toJSONString(param)));
    JSONObject json =
        HttpUtils.doPost(borrowUrl + "/api/gedApplyRegister", JSONObject.toJSONString(param));
    logger.info("返回的参数值===============" + json.get("code") + "");
    if (!Constant.RES_CODE_SUCCESS.equals(json.getString("code"))) {
      throw new BusinessException(Errors.PUSH_ERROR);
    }

  }


  public String timeCode() {
    Date currentDate = new Date();
    String data = DateFormatUtils.formatDate(currentDate, "yyyyMMdd");
    String dataTime = DateFormatUtils.formatDate(currentDate, "yyyyMMdd");
    RLock lock = redissonClient.getLock(dataTime);
    lock.lock();
    RBucket rBucket = redissonClient.getBucket("RequestCode" + data);
    Long count = 1L;
    if (rBucket.isExists()) {
      count = (Long) rBucket.get() + 1;
      rBucket.set(count, 1, TimeUnit.DAYS);
    } else {
      redissonClient.getBucket("RequestCode" + data).set(count, 1, TimeUnit.DAYS);
    }
    String newSeq = String.format("%07d", count);
    lock.unlock();
    return dataTime + newSeq;
  }

  public void TagValue(int key, GedOrderTags gedOrderTags) {
    switch (key) {
      case 1:
        gedOrderTags.setTagValue(OrderTags.HOUSE.getValue());
        break;
      case 2:
        gedOrderTags.setTagValue(OrderTags.CAR.getValue());
        break;
      case 3:
        gedOrderTags.setTagValue(OrderTags.COMPANY.getValue());
        break;
      case 4:
        gedOrderTags.setTagValue(OrderTags.OTHER.getValue());
        break;
    }
  }

  @Override
  public RepayPlanTotleResForm selectOrderStatus(long userId) throws Exception {
    RepayPlanTotleResForm resForm = new RepayPlanTotleResForm();
    List<DeductResult> repaymentList = new ArrayList<>();
    logger.info("订单状态用户id=" + userId);
    List<GedOrder> list = selectGedOrder(userId);
    String accStatus = selectAccStatus(userId);
    logger.info("开户状态==" + accStatus);
    List<Account> accountList = accountService.getAccountInfo(userId);
    AccountCompany accountCompany = accountService.queryCompanyAccountInfoById(userId);
    if (accountList != null && accountList.size() != 0) {
      resForm.setCompanyName(accountList.get(0).getCompanyName());// 开户公司名称
    }
    int account = 0;
    if (accountCompany != null) {
      if (StringUtils.isNotBlank(accountCompany.getIdCardFaceUrl())
          && StringUtils.isNotBlank(accountCompany.getIdCardBackUrl())
          && StringUtils.isNotBlank(accountCompany.getIdCardHoldUrl())
          && StringUtils.isNotBlank(accountCompany.getBusinessLicenceUrl())
          && StringUtils.isNotBlank(accountCompany.getAccountsPermitsUrl())) {
        if (StringUtils.isNotBlank(accStatus)) {
          account = Integer.parseInt(accStatus);
        }
      }
    }
    // 开户成功未签约成功 开户失败 签约失败 则为为开户
    if (account == 2 || account == 3 || account == 5) {
      account = 0;
    } else if (account == 4)// 签约成功
      account = 2;
    resForm.setAccStatus(account);// 开户状态
    if (list != null && list.size() != 0) {
      GedOrder gedOrder = list.get(0);
      // 待还款和已逾期
      if (gedOrder.getStatus() == OrderStatus.HKING.getCode()
          || gedOrder.getStatus() == OrderStatus.YYQ.getCode()) {
        repaymentList = overDueList(gedOrder.getOrderCode());
        if (repaymentList != null && repaymentList.size() != 0) {// 有逾期
          // 更新订单状态
          gedOrder.setStatus(OrderStatus.YYQ.getCode());
        } else {
          gedOrder.setStatus(OrderStatus.HKING.getCode());
        }
        gedOrderMapper.updateByPrimaryKeySelective(gedOrder);
      }
      int status = gedOrder.getStatus();
      logger.info("数据库状态:" + status);
      String feStatus = OrderStatus.resove(status).getFeStatus();
      int readFlag = gedOrder.getReadFlag() == null ? 0 : gedOrder.getReadFlag();
      logger.info("返回页面状态==========" + feStatus);
      if (StringUtils.isNotBlank(feStatus)) {
        switch (feStatus) {
          case "S0":// 材料收集中
            reloanAmTe(gedOrder, resForm, feStatus);
            break;
          case "S10":// 审核中
            reloanAmTe(gedOrder, resForm, feStatus);
            resForm.setAccStatus(account);// 开户状态
            if (StringUtils.isBlank(gedOrder.getContractCode())) {
              resForm.setSignContractFlag(2);
            } else {
              resForm.setSignContractFlag(gedOrder.getSignContractFlag());
            }
            break;
          case "S20":// 筹标中
            reloanAmTe(gedOrder, resForm, feStatus);
            resForm.setAccStatus(account);// 开户状态
            break;
          case "S30":// 审核通过
            reloanAmTe(gedOrder, resForm, feStatus);
            OrderLog orLog =
                orderLogService.getOrderLogByOrderId(gedOrder.getId(), OrderStatus.SHTG.getCode());
            resForm.setLoanDate(DateFormatUtils.formatDate(orLog.getCreateTime(), "yyyy-MM-dd"));// 审核通过时间
            break;
          case "S40":// 提现处理中 放款成功
            // reloanAmTe(gedOrder, resForm, feStatus);
            Integer withDrawFlag =
                gedOrder.getWithdrawFlag() == null ? 0 : gedOrder.getWithdrawFlag();
            // 判断提现按钮是否置灰
            if (withDrawFlag == 3) {
              resForm.setWithDrawFlag(1);// 二次提现中 按钮置灰
            } else {
              resForm.setWithDrawFlag(0);
            }
            resForm.setLoanAmount(gedOrder.getCreditAmount());// 放款额度
            resForm.setLoanTerm(gedOrder.getReplyTerm());// 批复期限
            resForm.setStatus(feStatus);
            resForm.setOrderNo(gedOrder.getOrderCode());
            OrderLog orderLog =
                orderLogService.getOrderLogByOrderId(gedOrder.getId(), OrderStatus.FKCG.getCode());
            resForm.setLoanDate(DateFormatUtils.formatDate(orderLog.getCreateTime(), "yyyy-MM-dd"));// 放款成功时间
            // 账号余额
            resForm.setAccountBalance(accountBalance(gedOrder.getOrderCode(), userId));
            break;
          case "S50":// 还款中
            resForm.setOrderNo(gedOrder.getOrderCode());
            resForm.setStatus(feStatus);
            loanPlan(repaymentList, gedOrder.getOrderCode(), userId, resForm);
            resForm.setLoanAmount(gedOrder.getLoanAmount());
            resForm.setLoanDate(DateFormatUtils.formatDate(gedOrder.getCreateTime(), "yyyy-MM-dd"));// 借钱时间
            break;
          case "S90":// 已还清/首页
            resForm.setStatus(feStatus);
            resForm.setPortalAmount(dictionaryService.getPortalAmount());
            resForm.setOrderNo(gedOrder.getOrderCode());
            break;
          case "S91":// 审核拒绝/未通过
            if (String.valueOf(readFlag).equals(String.valueOf(NoteFlagSource.RFT.getCode()))) {
              resForm.setStatus(OrderStatus.YE.getFeStatus());
              resForm.setPortalAmount(dictionaryService.getPortalAmount());
              resForm.setOrderNo(gedOrder.getOrderCode());
            } else {
              reloanAmTe(gedOrder, resForm, feStatus);
            }
            break;
          case "S92":// 游标
            if (String.valueOf(readFlag).equals(String.valueOf(NoteFlagSource.RFT.getCode()))) {
              resForm.setStatus(OrderStatus.YE.getFeStatus());
              resForm.setPortalAmount(dictionaryService.getPortalAmount());
              resForm.setOrderNo(gedOrder.getOrderCode());
            } else {
              reloanAmTe(gedOrder, resForm, feStatus);
            }
            break;
          case "S94":// 已逾期
            resForm.setStatus(feStatus);
            loanPlan(repaymentList, gedOrder.getOrderCode(), userId, resForm);
            resForm.setLoanAmount(gedOrder.getLoanAmount());
            resForm.setLoanDate(DateFormatUtils.formatDate(gedOrder.getCreateTime(), "yyyy-MM-dd"));// 借钱时间
            resForm.setOrderNo(gedOrder.getOrderCode());
            break;
        }
      }
    } else {
      logger.info("=========================" + OrderStatus.YE.getFeStatus());
      resForm.setStatus(OrderStatus.YE.getFeStatus());
      resForm.setPortalAmount(dictionaryService.getPortalAmount());
    }
    return resForm;
  }

  // 查询订单表
  @Override
  public List<GedOrder> selectGedOrder(long userId) {
    GedOrderExample example = new GedOrderExample();
    example.setOrderByClause("modify_time desc");
    GedOrderExample.Criteria criteria = example.createCriteria();
    criteria.andUserIdEqualTo(userId);
    List<GedOrder> list = gedOrderMapper.selectByExample(example);
    return list;
  }

  // 金额 期限 状态
  public RepayPlanTotleResForm reloanAmTe(GedOrder gedOrder, RepayPlanTotleResForm resForm,
      String feStatus) throws Exception {
    resForm.setLoanAmount(gedOrder.getLoanAmount());
    resForm.setLoanTerm(gedOrder.getLoanTerm());
    resForm.setStatus(feStatus);
    resForm.setOrderNo(gedOrder.getOrderCode());
    return resForm;
  }


  public String selectAccStatus(Long userId) {
    return accountService.selectAccStatus(userId);
  }

  public List<DeductResult> selectGedOrderRepaymentPlan(String orderNo, String dateFlag,
      String repayFlag) throws Exception {
    Map<String, String> param = new HashMap<String, String>();
    param.put("dateFlag", dateFlag);
    param.put("repayPeriodStatus", repayFlag);
    param.put("orderNo", orderNo);
    param.put("jointCredit", JointCreditFlag.NOT_JOINTCREDIT.getCode() + "");
    List<DeductResult> repaymentList = LoanSystemRepay(param);// 查询借款系统 返回list集合
    logger.info("=====" + JSONObject.toJSONString(repaymentList));
    return repaymentList;
  }

  @Override
  public void updateLoanTeAmo(GedOrderUpdateReqForm reqForm) throws Exception {
    GedOrder gedOrder = new GedOrder();
    gedOrder.setOrderCode(reqForm.getOrderCode());
    gedOrder.setCreditAmount(reqForm.getLoanAmount());// 放款额度
    gedOrder.setReplyTerm(reqForm.getLoanTerm());// 批复期限
    gedOrder.setReplyAmount(reqForm.getLoanAmount());// 批复金额
    gedOrder.setStatus(reqForm.getStatus());
    gedOrder.setModifyTime(new Date());
    logger.info("放款额度" + reqForm.getLoanAmount() + "放款期限" + reqForm.getLoanTerm() + "状态"
        + gedOrder.getStatus());
    gedOrderMapper.updateByOrderCodeSelective(gedOrder);
    // 增加放款成功日志表
    OrderLog log = new OrderLog();
    // 根据订单编号查询订单信息
    GedOrder ged = selectOrderByOrderNo(reqForm.getOrderCode());
    if (ged != null) {
      log.setCreateId(ged.getUserId());
      log.setSourceStatus(ged.getStatus());
      log.setOrderId(ged.getId());
    }
    log.setTargetStatus(reqForm.getStatus());
    log.setCreateTime(new Date());
    orderLogService.createOrderlog(log);
  }

  @Override
  public List<GedLoanRecordResForm> selectLoanRecord(User userInfo) throws Exception {
    List<GedLoanRecordResForm> resList = new ArrayList<GedLoanRecordResForm>();
    GedOrderExample example = new GedOrderExample();
    GedOrderExample.Criteria criteria = example.createCriteria();
    criteria.andUserIdEqualTo(userInfo.getId());
    List<GedOrder> list = gedOrderMapper.selectByExample(example);
    if (list == null && list.size() == 0) {
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR);
    }
    for (GedOrder gedOrder : list) {
      GedLoanRecordResForm resForm = new GedLoanRecordResForm();
      if (gedOrder.getStockFlag() == 1) {// 存量
        resForm.setStatus(OrderStatus.resove(gedOrder.getStatus()).getFeStatus());
      } else if (gedOrder.getSignContractFlag() == 0) {
        resForm.setStatus("待签署合同");
      } else {
        resForm.setStatus(OrderStatus.resove(gedOrder.getStatus()).getFeStatus());
      }
      if (StringUtils.isBlank(gedOrder.getContractCode())) {
        resForm.setStatus(OrderStatus.resove(gedOrder.getStatus()).getFeStatus());
      }
      resForm.setLoanAmount(gedOrder.getLoanAmount());
      resForm.setOrderNo(gedOrder.getOrderCode());
      resForm.setDate(DateFormatUtils.formatDate(gedOrder.getModifyTime(), "YYYY-MM-dd"));
      resList.add(resForm);
    }
    return resList;
  }

  @Override
  public void OrderStateFeedBack(GedOrderStatusReqForm reqForm) throws Exception {
    logger.info("订单反馈接口传过来的参数=" + JSONObject.toJSONString(reqForm));
    GedOrder gedOrder = new GedOrder();
    String applyIdChild = reqForm.getApplyIdChild();// 子订单编号
    if (StringUtils.isNotBlank(applyIdChild)) {// 联合授信
      gedOrder.setOrderCode(reqForm.getApplyIdChild());
    } else {
      gedOrder.setOrderCode(reqForm.getOrderNo());
    }
    gedOrder.setStatus(reqForm.getStatus());
    gedOrder.setModifyTime(new Date());
    gedOrder.setServiceFee(reqForm.getServiceFee());// 服务费
    gedOrder.setServiceFeeWay(reqForm.getServiceFeeWay());// 服务费收取方式
    if(reqForm.getServiceFeeWay() != null && 2==reqForm.getServiceFeeWay()) {
      gedOrder.setServiceFeeFlag(1);
    }
    // gedOrder.setCashDeposit(reqForm.getCashDeposit());// 保证金
    // gedOrder.setGuaranteeFee(reqForm.getGuaranteeFee());// 担保费
    gedOrder.setServiceProvinceId(reqForm.getServiceProvinceId());// 服务方所在地
    gedOrder.setRepaymentStyle(reqForm.getRepaymentStyle());// 还款方式
    gedOrder.setContractCode(reqForm.getContractNo());// 合同号
    gedOrder.setCreditAmount(reqForm.getContractAmount());// 合同额度
    gedOrder.setReplyTerm(reqForm.getLoanTerm());// 批复期限
    gedOrder.setReplyAmount(reqForm.getContractAmount());// 批复金额
    logger.info("订单编号:====" + gedOrder.getOrderCode());
    logger.info("传过来的状态" + gedOrder.getStatus());
    if (reqForm.getStatus() == OrderStatus.FKCG.getCode()) {
      logger.info("放款额度" + reqForm.getContractAmount() + "放款期限" + reqForm.getLoanTerm() + "合同号"
          + reqForm.getContractNo());
    }
    gedOrderMapper.updateByOrderCodeSelective(gedOrder);
    // 增加日志表
    OrderLog log = new OrderLog();
    // 根据订单编号查询订单信息
    if (StringUtils.isNotBlank(applyIdChild)) {// 联合授信
      GedOrder ged = selectOrderByOrderNo(reqForm.getApplyIdChild());
      if (ged != null) {
        log.setCreateId(ged.getUserId());
        log.setSourceStatus(ged.getStatus());
        log.setOrderId(ged.getId());
      }
    } else {
      GedOrder ged = selectOrderByOrderNo(reqForm.getOrderNo());
      if (ged != null) {
        log.setCreateId(ged.getUserId());
        log.setSourceStatus(ged.getStatus());
        log.setOrderId(ged.getId());
      }
    }
    log.setTargetStatus(reqForm.getStatus());
    log.setCreateTime(new Date());
    orderLogService.createOrderlog(log);
    if (reqForm.getStatus() == OrderStatus.SHWTG.getCode()) {
      logger.info("审核未通过发送短信====");
      if (StringUtils.isNotBlank(applyIdChild)) {// 联合授信
        GedOrder ger = selectOrderByOrderNo(reqForm.getApplyIdChild());
        msgService.sendMessageByLoanSysApplyRefuse(ger.getContactPhone(),
            ger.getLoanAmount().toString());
      } else {
        GedOrder ger = selectOrderByOrderNo(reqForm.getOrderNo());
        msgService.sendMessageByLoanSysApplyRefuse(ger.getContactPhone(),
            ger.getLoanAmount().toString());
      }
    }
  }

  @Override
  public void updateContract(OrderConTractListForm contractreqForm) throws Exception {
    logger.info("合同面签传过来的list集合" + JSONObject.toJSONString(contractreqForm));
    List<OrderContractReqForm> list = contractreqForm.getList();
    if (list != null && list.size() != 0) {
      for (OrderContractReqForm reqForm : list) {
        GedOrder gedOrder = new GedOrder();
        String applyIdChild = reqForm.getApplyIdChild();// 子订单编号
        if (StringUtils.isNotBlank(applyIdChild)) {// 联合授信
          gedOrder.setOrderCode(reqForm.getApplyIdChild());
        } else {
          gedOrder.setOrderCode(reqForm.getOrderNo());
        }
        gedOrder.setContractCode(reqForm.getContractNo());// 合同号
        gedOrder.setAccountManageFee(reqForm.getAccountManageFee());// 账户管理费
        gedOrder.setServiceFee(reqForm.getServiceFee());// 服务费
        gedOrder.setContractSignProvince(reqForm.getContractProvince());// 合同签署省
        gedOrder.setContractSignCity(reqForm.getContractCity());// 合同签署市
        gedOrder.setContractSignArea(reqForm.getContractDistinct());// 合同签署区
        logger.info("==账户管理费 服务费 合同号*****===" + JSONObject.toJSONString(gedOrder));
        gedOrderMapper.updateByOrderCodeSelective(gedOrder);
      }
    }
  }


  @Override
  public List<OrderLoanProgramResForm> loanProgram(Long userId) throws Exception {
    List<OrderLoanProgramResForm> resList = new ArrayList<OrderLoanProgramResForm>();
    List<GedOrder> gedOrderList = selectGedOrder(userId);
    if (gedOrderList != null && gedOrderList.size() > 0) {
      for (GedOrder gedOrder : gedOrderList) {
        OrderLoanProgramResForm resForm = new OrderLoanProgramResForm();
        resLoanGragram(gedOrder, resForm);
        resList.add(resForm);
      }
    }
    return resList;
  }

  public void resLoanGragram(GedOrder gedOrder, OrderLoanProgramResForm resForm) {
    resForm.setLoanAmount(gedOrder.getLoanAmount());// 借款金额
    resForm.setLoanTerm(gedOrder.getLoanTerm() + "个月");// 借款期限
    if (gedOrder.getRateDay() != null) {
      resForm.setRateDay(gedOrder.getRateDay() + "%");// 月利率
    }
    resForm.setCompanyName(gedOrder.getCompanyName());// 借款人
    resForm.setStatus(OrderStatus.resove(gedOrder.getStatus()).getFeStatus());// 状态
    resForm.setOrderCode(gedOrder.getOrderCode());// 订单编号
  }


  @Override
  public void OrderStateNotBack(GedOrderNotStatusReqForm reqForm) throws Exception {
    logger.info("借款系统打回传过来的参数=" + JSONObject.toJSONString(reqForm));
    String flag = reqForm.getFlag();// 0非联1联合
    if (StringUtils.isNotBlank(flag)) {
      if ("0".equals(flag)) {// 非联合授信
        GedOrder gedOrder = selectOrderByOrderNo(reqForm.getOrderNo());
        if (gedOrder != null) {
          gedOrder.setStatus(reqForm.getStatus());
          gedOrderMapper.updateByPrimaryKey(gedOrder);
        }
      } else if ("1".equals(flag)) {// 联合授信
        List<GedOrder> orderList = selectOrderByMasterOrderNo(reqForm.getOrderNo());
        if (orderList != null && orderList.size() != 0) {
          for (GedOrder geo : orderList) {
            geo.setStatus(reqForm.getStatus());
            gedOrderMapper.updateByPrimaryKey(geo);
          }
        }
      }
    }
  }

  // 根据订单编号返回订单信息
  @Override
  public GedOrder selectOrderByOrderNo(String orderNo) {
    GedOrderExample example = new GedOrderExample();
    GedOrderExample.Criteria criteria = example.createCriteria();
    criteria.andOrderCodeEqualTo(orderNo);
    List<GedOrder> gedOrderList = gedOrderMapper.selectByExample(example);
    if (gedOrderList != null && gedOrderList.size() != 0) {
      return gedOrderList.get(0);
    }
    return null;
  }

  // 根据父订单编号返回订单集合
  @Override
  public List<GedOrder> selectOrderByMasterOrderNo(String masterOrderNo) {
    GedOrderExample example = new GedOrderExample();
    GedOrderExample.Criteria criteria = example.createCriteria();
    criteria.andMasterOrderCodeEqualTo(masterOrderNo);
    List<GedOrder> gedOrderList = gedOrderMapper.selectByExample(example);
    return gedOrderList;
  }

  @Override
  public void pushLoanCreateOrder(PushOrderListForm listreqForm) throws Exception {
    logger.info("推送订单的参数=" + JSONObject.toJSONString(listreqForm));
    List<GedOrderloanPushReqForm> list = listreqForm.getList();
    if (list != null && list.size() != 0) {
      for (GedOrderloanPushReqForm reqForm : list) {
        String applyIdChlid = reqForm.getApplyIdChild();
        if (StringUtils.isBlank(applyIdChlid)) {// 非联合授信
          // 根据申请单id查询是否有信息
          GedOrder ged = selectOrderByOrderNo(reqForm.getApplyId());// orderNo
          if (ged != null) {
            logger.info("要删除的进件编号=" + ged.getOrderCode() + "主键id" + ged.getId());
            orderGuarantLoanService.detetetionGuar(ged.getId());// 删除担保关系
            // 说明有信息 则删除
            gedOrderMapper.deleteByPrimaryKey(ged.getId());
          }
        } else {// 联合授信
          String applyId = listreqForm.getList().get(0).getApplyId();
          // 级联删除担保关系
          orderGuarantLoanService.cascadingDeletion(applyId);
          // 根据父订单编号删除订单
          GedOrderExample example = new GedOrderExample();
          example.createCriteria().andMasterOrderCodeEqualTo(applyId);
          gedOrderMapper.deleteByExample(example);
          break;
        }
      }
      for (GedOrderloanPushReqForm reqForm : list) {
        GedOrder gedOrder = new GedOrder();
        String applyIdChlid = reqForm.getApplyIdChild();
        if (StringUtils.isBlank(applyIdChlid)) {// 非联合授信
          gedOrder.setOrderCode(reqForm.getApplyId());
        } else {// 联合授信
          gedOrder.setOrderCode(applyIdChlid);
          gedOrder.setMasterOrderCode(reqForm.getApplyId());
        }
        // 客户名称
        gedOrder.setPersonCardNum(reqForm.getIdNum());// 证件号码
        gedOrder.setCreateTime(
            DateFormatUtils.parseDateStr(reqForm.getApplyDate(), "yyyy-MM-dd HH:mm:ss"));// 申请日期
        // 产品类型 产品名称 //客户类型
        gedOrder.setContactPhone(reqForm.getPhoneNo());// 联系电话
        gedOrder.setLoanAmount(reqForm.getApplyAmount());// 申请金额
        gedOrder.setLoanTerm(reqForm.getApplyPeriod());// 申请期限
        gedOrder.setCompanyName(reqForm.getCompanyName());// 企业名称
        gedOrder.setCompanyType(reqForm.getCompanyCardType());// 企业证件类型
        gedOrder.setCompanyCardNum(reqForm.getCompanyCardNum());// 企业证件号码
        // 根据企业证件类型 查询用户id
        User user = null;
        user =
            userService.selectUserBytype(reqForm.getCompanyCardType(), reqForm.getCompanyCardNum());
        if (user == null) {
          user = userService.userByMobile(reqForm.getPhoneNo());// 通过手机号查询
        }
        logger.info("推过来的查询用户信息===" + user);
        if (user != null) {
          gedOrder.setUserId(user.getId());
          // 更新用户信息表中的 3证合一字段
          user.setId(user.getId());
          user.setUsername(reqForm.getCustName());// 客户名称
          user.setBusinessLicence(reqForm.getBusinessLicence());
          user.setOrganizationCode(reqForm.getOrganizationCode());
          user.setTaxCode(reqForm.getTaxCode());
          user.setCompanyName(reqForm.getCompanyName());
          user.setLegalName(reqForm.getLegalName());
          user.setLegalMobile(reqForm.getLegalMobile());
          user.setLegalCardNumber(reqForm.getLegalCardNumber());
          user.setLegalCardType(1);
          userMapper.updateByPrimaryKeySelective(user);
        }
        gedOrder.setLoanPurpose(reqForm.getLoanPurpost());// 借款用途
        gedOrder.setRateDay(reqForm.getApproveMonthRate());// 月利率
        gedOrder.setLoanType(reqForm.getProductType());// 借款类型
        gedOrder.setPersonCardType(1);// 默认身份证
        // 客户来源
        gedOrder.setProvinceId(
            reqForm.getProvince() != null ? Long.valueOf(reqForm.getProvince()) : null);// 省
        // //传的是码值
        gedOrder.setCityId(reqForm.getCity() != null ? Long.valueOf(reqForm.getCity()) : null);// 市
        gedOrder
            .setAreaId(reqForm.getDistrict() != null ? Long.valueOf(reqForm.getDistrict()) : null);// 区
        gedOrder.setManagementAddr(reqForm.getContCity());// 所在城市
        // gedOrder.setOrderCode(reqForm.getApplyId());// 申请单号
        gedOrder.setRepaymentStyle(reqForm.getRepaymentStyle());// 还款方式
        gedOrder.setReceivableCashDeposit(reqForm.getReceivableCashDeposit());// 应收保证金
        gedOrder.setReceivableGuaranteeFee(reqForm.getReceivableGuaranteeFee());// 应收担保费
        gedOrder.setCreateTime(new Date());
        gedOrder.setModifyTime(new Date());
        gedOrder.setSysFlag(0);// 借款系统推过来的订单
        gedOrder.setStatus(110);// 默认状态审核中
        logger.info("推过来的订单信息" + JSONObject.parseObject(JSON.toJSONString(gedOrder)));
        gedOrderMapper.insertSelective(gedOrder);
      }
    }
  }

  @Override
  public RepayPlanTotleResForm selectRepayment(User userInfo, GedOrderNoReqForm reqForm)
      throws Exception {
    // 查询用户信息
    User user = userService.getUserById(userInfo.getId());
    if (user == null) {
      throw new BusinessException(Errors.USER_MOBILE_NOT_EXISTS_ERROR);
    }
    String custId = user.getGetCustId();// 客户id
    int readyCountTerm = 0;
    BigDecimal readyCountAmount = new BigDecimal(0);
    BigDecimal notCountAmount = new BigDecimal(0);
    long startTime = System.currentTimeMillis();
    RepayPlanTotleResForm repayPlanTotleResForm = new RepayPlanTotleResForm();
    GedOrder gedOrder = selectOrderByOrderNo(reqForm.getOrderNo());
    if (gedOrder == null) {
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR);
    }
    String orderNo = gedOrder.getOrderCode();
    Map<String, String> param = new HashMap<String, String>();
    param.put("dateFlag", "");
    param.put("repayPeriodStatus", "");
    param.put("orderNo", orderNo);
    param.put("jointCredit", JointCreditFlag.NOT_JOINTCREDIT.getCode() + "");
    List<DeductResult> repaymentList = LoanSystemRepay(param);// 查询借款系统 返回list集合
    halfPlan(repaymentList, repayPlanTotleResForm);// 还款计划上部分
    repayPlanTotleResForm.setLoanAmount(gedOrder.getLoanAmount());// 借钱
    OrderLog orderLog =
        orderLogService.getOrderLogByOrderId(gedOrder.getId(), OrderStatus.FKCG.getCode());
    // 更多信息上面的时间
    repayPlanTotleResForm
        .setLoanDate(DateFormatUtils.formatDate(orderLog.getCreateTime(), "yyyy-MM-dd"));
    if (repaymentList != null && repaymentList.size() != 0) {
      List repaidList = new ArrayList<>();
      List notYetList = new ArrayList<>();
      for (DeductResult plan : repaymentList) {
        String periodStatus =
            plan.getRepayPeriodStatus() == null ? "0200" : plan.getRepayPeriodStatus();
        if (periodStatus.equals(RepayMentFlag.FIRST.getLoanStatus())
            || periodStatus.equals(RepayMentFlag.FOUR.getLoanStatus())
            || periodStatus.equals(RepayMentFlag.FIVE.getLoanStatus())
            || periodStatus.equals(RepayMentFlag.SIX.getLoanStatus())) {// 已还
          RepayMentPlanResForm resForm = new RepayMentPlanResForm();
          repayList(resForm, plan);// 期限 金额 日期;
          ++readyCountTerm;
          readyCountAmount = readyCountAmount.add(resForm.getRepaymentAmount());
          // 代偿 没有匹配的 或者没有传过来custId 则默认不显示 为0
          if (StringUtils.isNotBlank(plan.getCustId())) {
            if (plan.getCustId().equals(custId)) {
              resForm.setCompensatory(
                  plan.getCompensatoryAmount() == null ? "0" : plan.getCompensatoryAmount());
            } else {
              resForm.setCompensatory("0");
            }
          } else {
            resForm.setCompensatory("0");
          }
          repaidList.add(resForm);
        } else if (periodStatus.equals(RepayMentFlag.ZERO.getLoanStatus())) {// 待还
          RepayMentPlanResForm resForm = new RepayMentPlanResForm();
          repayList(resForm, plan);
          notCountAmount = notCountAmount.add(resForm.getRepaymentAmount());
          notYetList.add(resForm);
        } else if (periodStatus.equals(RepayMentFlag.SECOND.getLoanStatus())) {// 逾期
          RepayMentPlanResForm resForm = new RepayMentPlanResForm();
          repayList(resForm, plan);
          notCountAmount = notCountAmount.add(resForm.getRepaymentAmount());
          resForm.setWhethRepayFlag(1);
          // 代偿
          if (StringUtils.isNotBlank(plan.getCustId())) {
            if (plan.getCustId().equals(custId)) {
              resForm.setCompensatory(
                  plan.getCompensatoryAmount() == null ? "0" : plan.getCompensatoryAmount());
            } else {
              resForm.setCompensatory("0");
            }
          } else {
            resForm.setCompensatory("0");
          }
          notYetList.add(resForm);
        }
      }
      repayPlanTotleResForm.setRepaidList(repaidList);
      repayPlanTotleResForm.setNotYetList(notYetList);
      repayPlanTotleResForm.setReadyCountTerm(readyCountTerm);
      repayPlanTotleResForm.setNotCountTerm(repaymentList.size() - readyCountTerm);
      repayPlanTotleResForm.setReadyCountAmount(readyCountAmount);
      repayPlanTotleResForm.setNotCountAmount(notCountAmount);
      logger.info("还款计划返回的数据集合=" + JSONObject.toJSONString(repayPlanTotleResForm));
      long endTime = System.currentTimeMillis();
      logger.info("耗时" + (float) (endTime - startTime) / 1000);
    }
    return repayPlanTotleResForm;
  }

  // 设置还款计划list集合
  public RepayMentPlanResForm repayList(RepayMentPlanResForm resForm, DeductResult plan)
      throws Exception {
    resForm.setOrderTerm(Integer.parseInt(plan.getPeriodNum()));
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
    String repayPeriodStatus =
        plan.getRepayPeriodStatus() == null ? "0200" : plan.getRepayPeriodStatus();
    if (repayPeriodStatus.equals(RepayMentFlag.SECOND.getLoanStatus())
        || repayPeriodStatus.equals(RepayMentFlag.ZERO.getLoanStatus())) {// 等于逾期
      resForm.setRepaymentAmount(
          (new BigDecimal(plan.getRepayAmount()).subtract(calcualterFactPayAmount(plan)))
              .setScale(2, BigDecimal.ROUND_HALF_DOWN));
    } else {
      resForm.setRepaymentAmount(factServiceFee.add(factManagermentFee).add(factInterestAmount)
          .add(factOverdueCapialAmount).add(factPenaltyAmount).add(factFineAmount));
    }
    resForm.setRepaymentTime(plan.getDeductDate());
    return resForm;
  }


  public RepayPlanTotleResForm halfPlan(List<DeductResult> list, RepayPlanTotleResForm resForm)
      throws Exception {
    BigDecimal totleAmount = new BigDecimal(0);
    BigDecimal overdueInterest = new BigDecimal(0);
    int count = 0;
    Boolean flag = false;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    if (list != null && list.size() != 0) {
      for (DeductResult plan : list) {
        // && simpleDateFormat.parse(plan.getDeductDate()).getTime() < System.currentTimeMillis()
        if ("0300".equals(plan.getRepayPeriodStatus())) {// 逾期
          BigDecimal fineAmount = StringUtils.isBlank(plan.getFineAmount()) ? new BigDecimal(0)
              : new BigDecimal(plan.getFineAmount());
          // 应还-实还
          totleAmount = totleAmount
              .add((new BigDecimal(plan.getRepayAmount()).subtract(calcualterFactPayAmount(plan)))
                  .setScale(2, BigDecimal.ROUND_HALF_DOWN));
          BigDecimal oweInterestAmount = StringUtils.isBlank(plan.getOweInterestAmount())
              ? new BigDecimal(0) : new BigDecimal(plan.getOweInterestAmount());// 逾期利息
          BigDecimal penaltyAmount = StringUtils.isBlank(plan.getPenaltyAmount())
              ? new BigDecimal(0) : new BigDecimal(plan.getPenaltyAmount());// 违约金
          overdueInterest = overdueInterest.add(fineAmount).add(oweInterestAmount)
              .add(penaltyAmount).setScale(2, BigDecimal.ROUND_HALF_DOWN);// 违约费用
          count = count + 1;
          flag = true;
        }
      }
      if (flag == true) {// 有逾期
        int repayDay = 0;
        for (DeductResult plan : list) {
          if ("0300".equals(plan.getRepayPeriodStatus())) {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(plan.getDeductDate());
            String now = new SimpleDateFormat("yyyy年MM月dd日").format(date);
            resForm.setRepaymentTime(now);// 最早期逾期的时间
            repayDay = DateFormatUtils.daysBetween(date, new Date());
            break;
          }
        }
        for (DeductResult plan : list) {
          // 查询是否有下一期的 金额和期限
          if ("0200".equals(plan.getRepayPeriodStatus()) && simpleDateFormat
              .parse(plan.getDeductDate()).getTime() > System.currentTimeMillis()) {
            totleAmount = totleAmount
                .add(new BigDecimal(plan.getRepayAmount()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
            resForm.setRepaymentAmount(
                (new BigDecimal(plan.getRepayAmount()).subtract(calcualterFactPayAmount(plan)))
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN));// 本期待还金额
            count = count + 1;
            resForm.setCurrentFlag(0);
            break;
          } else {
            resForm.setCurrentFlag(1);
          }
        }
        resForm.setCount(count);// 共几期
        resForm.setRepayDay(repayDay);// 逾期天数
        resForm.setTotleAmount(totleAmount);// 总共金额
        resForm.setOverDueDefaultInterest(overdueInterest);// 逾期罚息 违约费用
        resForm.setRepaymentFlag(RepayMentFlag.resove("0300").getCode());// 还款标识为逾期
      } else {// 无逾期
        logger.info("===============无逾期数据===================");
        for (DeductResult plan : list) {
          if ("0200".equals(plan.getRepayPeriodStatus()) && simpleDateFormat
              .parse(plan.getDeductDate()).getTime() > System.currentTimeMillis()) {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(plan.getDeductDate());
            String now = new SimpleDateFormat("yyyy年MM月dd日").format(date);
            resForm.setRepaymentTime(now);// 当前还款日
            resForm.setOrderTerm(Integer.parseInt(plan.getPeriodNum()));// 第几期
            resForm.setRepaymentAmount(
                (new BigDecimal(plan.getRepayAmount()).subtract(calcualterFactPayAmount(plan)))
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN));
            String repayPeriodStatus =
                plan.getRepayPeriodStatus() == null ? "0200" : plan.getRepayPeriodStatus();
            resForm.setRepaymentFlag(RepayMentFlag.resove(repayPeriodStatus).getCode());// 还款标识
            break;
          }
        }
      }
    }
    resForm.setOverdueFlag(flag);
    logger.info("上半部分" + JSONObject.toJSONString(resForm) + "========");
    return resForm;
  }

  /**
   * 还款计划
   *
   * @param orderNo
   * @param userId
   * @return
   */
  public RepayPlanTotleResForm loanPlan(List<DeductResult> repaymentList, String orderNo,
      long userId, RepayPlanTotleResForm resForm) throws Exception {
    if (repaymentList != null && repaymentList.size() != 0) {// 说明有逾期
      BigDecimal totleAmount = new BigDecimal(0);
      BigDecimal overdueInterest = new BigDecimal(0);
      int count = 0;
      for (DeductResult plan : repaymentList) {
        BigDecimal fineAmount = StringUtils.isBlank(plan.getFineAmount()) ? new BigDecimal(0)
            : new BigDecimal(plan.getFineAmount());// 罚息
        BigDecimal oweInterestAmount = StringUtils.isBlank(plan.getOweInterestAmount())
            ? new BigDecimal(0) : new BigDecimal(plan.getOweInterestAmount());// 逾期利息
        BigDecimal penaltyAmount = StringUtils.isBlank(plan.getPenaltyAmount()) ? new BigDecimal(0)
            : new BigDecimal(plan.getPenaltyAmount());// 违约金
        totleAmount = totleAmount
            .add((new BigDecimal(plan.getRepayAmount()).subtract(calcualterFactPayAmount(plan)))
                .setScale(2, BigDecimal.ROUND_HALF_DOWN));
        overdueInterest = overdueInterest.add(fineAmount).add(oweInterestAmount).add(penaltyAmount)
            .setScale(2, BigDecimal.ROUND_HALF_DOWN);// 违约费用
        count = count + 1;
      }
      Date date = new SimpleDateFormat("yyyy-MM-dd").parse(repaymentList.get(0).getDeductDate());
      String now = new SimpleDateFormat("yyyy年MM月dd日").format(date);
      resForm.setRepaymentTime(now);// 最早期逾期的时间

      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Map<String, String> param = new HashMap<String, String>();
      param.put("dateFlag", "");
      param.put("repayPeriodStatus", "");
      param.put("orderNo", orderNo);
      param.put("jointCredit", JointCreditFlag.NOT_JOINTCREDIT.getCode() + "");
      List<DeductResult> list = LoanSystemRepay(param);// 查询借款系统 返回list集合
      for (DeductResult plan : list) {
        if ("0200".equals(plan.getRepayPeriodStatus()) && simpleDateFormat
            .parse(plan.getDeductDate()).getTime() > System.currentTimeMillis()) {
          totleAmount = totleAmount
              .add(new BigDecimal(plan.getRepayAmount()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
          resForm.setRepaymentAmount(
              (new BigDecimal(plan.getRepayAmount()).subtract(calcualterFactPayAmount(plan)))
                  .setScale(2, BigDecimal.ROUND_HALF_DOWN));// 本期待还金额
          count = count + 1;
          resForm.setCurrentFlag(0);
          break;
        } else {
          resForm.setCurrentFlag(1);
        }
      }
      int repayDay = DateFormatUtils.daysBetween(date, new Date());
      resForm.setCount(count);// 共几期
      resForm.setRepayDay(repayDay);// 逾期天数
      resForm.setTotleAmount(totleAmount);// 总共金额
      resForm.setOverDueDefaultInterest(overdueInterest);// 逾期罚息
      resForm.setRepaymentFlag(RepayMentFlag.resove("0300").getCode());// 还款标识为逾期
      logger.info("逾期共几期" + count + "==共还金额" + totleAmount + "==逾期天数" + repayDay);
    } else {// 还款完成
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Map<String, String> param = new HashMap<String, String>();
      param.put("dateFlag", "");
      param.put("repayPeriodStatus", "");
      param.put("orderNo", orderNo);
      param.put("jointCredit", JointCreditFlag.NOT_JOINTCREDIT.getCode() + "");
      List<DeductResult> list = LoanSystemRepay(param);// 查询借款系统 返回list集合
      for (DeductResult plan : list) {
        if ("0200".equals(plan.getRepayPeriodStatus()) && simpleDateFormat
            .parse(plan.getDeductDate()).getTime() > System.currentTimeMillis()) {
          Date date = new SimpleDateFormat("yyyy-MM-dd").parse(plan.getDeductDate());
          String now = new SimpleDateFormat("yyyy年MM月dd日").format(date);
          resForm.setRepaymentTime(now);// 当前还款日
          resForm.setOrderTerm(Integer.parseInt(plan.getPeriodNum()));// 第几期
          resForm.setRepaymentAmount(
              (new BigDecimal(plan.getRepayAmount()).subtract(calcualterFactPayAmount(plan)))
                  .setScale(2, BigDecimal.ROUND_HALF_DOWN));
          String repayPeriodStatus =
              plan.getRepayPeriodStatus() == null ? "0200" : plan.getRepayPeriodStatus();
          resForm.setRepaymentFlag(RepayMentFlag.resove(repayPeriodStatus).getCode());// 还款标识
          break;
        }
      }
    }
    logger.info("===============" + JSONObject.toJSONString(resForm) + "========");
    return resForm;
  }

  // 查询还款计划是否有逾期数据
  public List<DeductResult> overDueList(String orderNo) throws Exception {
    Map<String, String> param = new HashMap<String, String>();
    param.put("dateFlag", "");// 小于当前日期的 DateFlag.LESS_NEWDATE.getCode()
    param.put("repayPeriodStatus", RepayMentFlag.SECOND.getLoanStatus());// 逾期标识
    param.put("orderNo", orderNo);
    param.put("jointCredit", JointCreditFlag.NOT_JOINTCREDIT.getCode() + "");
    List<DeductResult> repaymentList = LoanSystemRepay(param);// 查询借款系统 返回list集合
    return repaymentList;
  }

  public DeductResult loanGreaThanPlan(String orderNo, long userId) throws Exception {
    Map<String, String> param = new HashMap<String, String>();
    param.put("dateFlag", DateFlag.MORE_NEWDATE.getCode() + "");// 大于等于当前日期的
    param.put("repayPeriodStatus", RepayMentFlag.ZERO.getLoanStatus());// 查询未还款的 这个可能提前还款
    param.put("orderNo", orderNo);
    param.put("jointCredit", JointCreditFlag.NOT_JOINTCREDIT.getCode() + "");
    List<DeductResult> repaymentGrThanList = LoanSystemRepay(param);// 查询借款系统 返回list集合
    if (repaymentGrThanList != null && repaymentGrThanList.size() != 0) {
      DeductResult plan = repaymentGrThanList.get(0);
      // 显示第一个大于当前日期的还款日期
      return plan;
    }
    return null;
  }

  // 调用借款系统的还款计划
  public List<DeductResult> LoanSystemRepay(Map<String, String> param) throws Exception {
    logger.info("还款计划传的参数为=" + JSONObject.toJSONString(param));
    JSONObject json =
        HttpUtils.doPost(borrowUrl + "/api/gedRepaymentPlan", JSONObject.toJSONString(param));
    // JSONObject json =
    // HttpUtils.doPost("http://10.100.160.121:9082/update_borrow_000/f/rest/api/gedRepaymentPlan",
    // JSONObject.toJSONString(param));
    if (!Constant.RES_CODE_SUCCESS.equals(json.get("code"))) {
      throw new BusinessException(Errors.LOANSYSTEM_ERROR);
    }
    String msg = (String) json.get("msg");
    List<DeductResult> plan = JSONObject.parseArray(msg, DeductResult.class);
    logger.info("============" + plan.size());
    return plan;
  }

  // 调用借款系统的还款计划
  @Override
  public Map LoanSystem(Map<String, String> param) throws Exception {
    Map<String, Object> loanSy = new HashMap<String, Object>();
    logger.info("还款计划传的参数为=" + JSONObject.toJSONString(param));
    JSONObject json =
        HttpUtils.doPost(borrowUrl + "/api/gedRepaymentPlan", JSONObject.toJSONString(param));
    if (json == null) {
      return null;
    }
    if (!Constant.RES_CODE_SUCCESS.equals(json.get("code"))) {
      throw new BusinessException(Errors.LOANSYSTEM_ERROR);
    }
    String msg = (String) json.get("msg");
    String loanType = (String) json.getJSONObject("mapValue").get("loanRepayTypeName");
    List<DeductResult> plan = JSONObject.parseArray(msg, DeductResult.class);
    loanSy.put("planList", plan);
    loanSy.put("loanType", loanType);
    logger.info("============" + plan.size());
    return loanSy;
  }

  @Override
  public AccountWheResForm selectWheAccount(User userInfo) {
    AccountWheResForm resForm = new AccountWheResForm();
    // 查询最新的一笔订单 是否有申请中的进件
    List<GedOrder> listorder = selectGedOrder(userInfo.getId());
    if (listorder != null && listorder.size() != 0) {
      GedOrder gedOrder = listorder.get(0);
      if (gedOrder.getStatus() != 0 && gedOrder.getStatus() < 180) {
        throw new BusinessException(Errors.NOTE_UNCOMMIT_ERROR);
      }
    }
    // 查询企业是否开户
    AccountCompany accountCompany = accountService.queryCompanyAccountInfoById(userInfo.getId());
    if (accountCompany == null) {
      resForm.setFlag(0);
      return resForm;
    }
    resForm.setBusinessLicence(accountCompany.getBusinessLicence());
    resForm.setCompanyCardType(accountCompany.getCompanyCardType());
    resForm.setCompanyName(accountCompany.getCompanyName());
    resForm.setOrganizationCode(accountCompany.getOrganizationCode());
    resForm.setTaxCode(accountCompany.getTaxCode());
    resForm.setSocialCreditCode(accountCompany.getSocialCreditCode());
    resForm.setFlag(1);
    return resForm;
  }

  @Override
  public void auditFail(User userInfo, GedOrderNoReqForm reqForm) {
    GedOrder gedOrder = new GedOrder();
    gedOrder.setReadFlag(NoteFlagSource.RFT.getCode());
    gedOrder.setModifyTime(new Date());
    gedOrder.setOrderCode(reqForm.getOrderNo());
    gedOrderMapper.updateByOrderCodeSelective(gedOrder);
  }

  @Override
  public void updateCertification(User userInfo, CertificationReqForm reqForm) {
    User user = userMapper.selectByPrimaryKey(userInfo.getId());
    if (user == null) {
      throw new BusinessException(Errors.SYSTEM_ERROR);
    }
    user.setId(userInfo.getId());
    user.setLegalName(reqForm.getUsername());
    user.setLegalCardNumber(reqForm.getIdCardNum());
    user.setUsername(reqForm.getUsername());
    user.setIdCardNum(reqForm.getIdCardNum());
    user.setIdCardFlag(1);
    userMapper.updateByPrimaryKeySelective(user);
  }

  @Override
  public WithDrawResForm withDraw(User userInfo, String orderNo) throws Exception {
    WithDrawResForm resForm = new WithDrawResForm();
    BigDecimal accountBalan = accountBalance(orderNo, userInfo.getId());
    BigDecimal withDraw = new BigDecimal(0.0);
    resForm.setAccountBalance(accountBalan);// 账户余额
    GedOrder gedOrder = selectOrderByOrderNo(orderNo);
    if (gedOrder == null) {
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR);
    }
    List<Account> accountList = accountService.getAccountInfo(userInfo.getId());
    if (accountList != null && accountList.size() != 0) {
      Account account = accountList.get(0);
      resForm.setCorporationName(account.getCompanyName());// 个人公司名称
      resForm.setCorporationAccount(account.getCompanyAccount());// 个人对公账户
      resForm.setCorporationBankOfDeposit(account.getCompanyBankOfDeposit());// 个人开户行
    }
    Integer serviceFeeFlag = gedOrder.getServiceFeeFlag();// 服务费标识 0未交费 1已缴费
    logger.info("服务费标识=" + serviceFeeFlag);
    withDraw = withDrawAmount(orderNo);// 返回提现金额
    if (gedOrder.getWithdrawFlag() != null) {
      if (gedOrder.getWithdrawFlag() == WithDrawFlag.SECOND_WITHDRAWING.getKey()) {// 如果是二次提现处理中 置为0
        withDraw = new BigDecimal(0.0);
      }
    }
    logger.info("放款额度=" + gedOrder.getCreditAmount() + "提现金额" + withDraw + "订单编号" + orderNo);
    resForm.setWithDraw(withDraw);// 提现金额
    if (serviceFeeFlag == null || serviceFeeFlag == 0) {// 页面提示标识
      serviceFeeFlag = 0;
    }
    resForm.setServiceFeeFlag(serviceFeeFlag);// 服务费标识
    resForm.setServiceFee(gedOrder.getServiceFee());// 服务费
    return resForm;
  }

  @Override
  public BigDecimal withDrawAmount(String orderNo) {
    BigDecimal withDraw = new BigDecimal(0.0);
    GedOrder gedOrder = selectOrderByOrderNo(orderNo);
    if (gedOrder == null) {
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR);
    }
    Integer withDrawFlag = gedOrder.getWithdrawFlag() == null ? 0 : gedOrder.getWithdrawFlag();
    Integer serviceFeeFlag = gedOrder.getServiceFeeFlag();// 服务费标识
    Integer guaranteeFeeFlag = gedOrder.getGuaranteeFeeFlag();// 担保费标识
    if (serviceFeeFlag == null || serviceFeeFlag == 0 || guaranteeFeeFlag == null
        || guaranteeFeeFlag == 0) {// 服务费 保障金未缴费 提现60%
      if (withDrawFlag == WithDrawFlag.fIRST_WITHDRAWING.getKey()
          || withDrawFlag == WithDrawFlag.FIRST_WITHDRAWFINISH.getKey()) {// 提现中 但是没还有回调回来或者回调回来 //
                                                                          // 提现40%
        withDraw = gedOrder.getCreditAmount().multiply(new BigDecimal(0.4)).setScale(2,
            BigDecimal.ROUND_HALF_DOWN);
      } else if (gedOrder.getWithdrawAmount() != null) {
        withDraw = gedOrder.getCreditAmount().subtract(gedOrder.getWithdrawAmount()).setScale(2,
            BigDecimal.ROUND_HALF_DOWN);
      } else {
        withDraw = gedOrder.getCreditAmount().multiply(new BigDecimal(0.6)).setScale(2,
            BigDecimal.ROUND_HALF_DOWN);
      }
    } else if (serviceFeeFlag == 1 && guaranteeFeeFlag == 1) {// 服务费和担保费都已缴费
      if (gedOrder.getWithdrawAmount() != null) {// 交了服务费 回调回来 提现剩下的40%
        withDraw = gedOrder.getCreditAmount().subtract(gedOrder.getWithdrawAmount()).setScale(2,
            BigDecimal.ROUND_HALF_DOWN);
      } else {
        if (withDrawFlag == WithDrawFlag.fIRST_WITHDRAWING.getKey()) {// 交了服务费 但是没还有回调回来 2
          withDraw = gedOrder.getCreditAmount().multiply(new BigDecimal(0.4)).setScale(2,
              BigDecimal.ROUND_HALF_DOWN);
        } else {
          withDraw = gedOrder.getCreditAmount();// 已交服务费 全额提现
        }
      }
    }
    return withDraw;
  }

  @Override
  public String getContractUrlByOrderNo(String ordrNo) throws Exception {
    GedOrder order = this.selectOrderByOrderNo(ordrNo);
    return this.getContractUrlByContractNo(order.getContractCode());
  }

  @Override
  public GedOrder selectByPrimy(Long id) {
    return gedOrderMapper.selectByPrimaryKey(id);
  }



  @Override
  public String getContractUrlByContractNo(String contractNo) throws Exception {
    if (StringUtils.isBlank(contractNo)) {
      return null;
    }
    Map<String, String> paras = new HashedMap();
    paras.put("contractNo", contractNo);
    logger.info("-----------------------请求合同地址--------------------------contractNo=" + contractNo);
    String result = HttpUtils.doPostForm(gedGETBackUrl + "/api/gedLoan/queryLoanContract", paras);
    logger.info("-----------------------请求合同地址--------------------------结果：");
    logger.info(result);
    JSONObject res = JSON.parseObject(result);
    if (res == null || !Constant.RES_CODE_SUCCESS.equals(res.getString("code"))) {
      logger.info("请求合同url信息错误，原因：" + res.getString("msg"));
      return null;
      // throw new BusinessException(Errors.LOANSYSTEM_ERROR ,
      // "请求合同url信息错误，原因："+res.getString("msg"));
    }
    Map<String, Object> data = (Map<String, Object>) res.get("data");
    if (data == null || data.get("loanContractUrl") == null) {
      return "";
    }

    return getFastDFSurl + "/" + (String) data.get("loanContractUrl");
  }

  @Override
  public PageInfo<GedOrder> getOrdersPageByCondition(GedOrder gedOrder, Integer pageNum,
      Integer pageSize) {
    pageNum = pageNum == null ? 1 : pageNum;
    pageSize = pageSize == null ? 10 : pageSize;
    GedOrderExample example = new GedOrderExample();
    GedOrderExample.Criteria criteria = example.createCriteria();
    copyOrderUtil(gedOrder, criteria);
    example.setOrderByClause("create_time desc");
    PageHelper.startPage(pageNum, pageSize);
    List<GedOrder> list = this.gedOrderMapper.selectByExample(example);
    PageInfo pageInfo = new PageInfo(list);
    // Page page = (Page) list;
    return pageInfo;
  }

  @Override
  public List<GedOrder> getOrdersByCondition(GedOrder gedOrder) {
    GedOrderExample example = new GedOrderExample();
    GedOrderExample.Criteria criteria = example.createCriteria();
    copyOrderUtil(gedOrder, criteria);
    example.setOrderByClause("create_time desc");
    List<GedOrder> list = this.gedOrderMapper.selectByExample(example);
    return list;
  }

  @Override
  public List<GedOrder> getOrderingByCondition(GedOrder gedOrder, Integer type) {
    GedOrderExample example = new GedOrderExample();
    GedOrderExample.Criteria criteria = example.createCriteria();
    copyOrderUtil(gedOrder, criteria);
    example.setOrderByClause("create_time desc");
    if (gedOrder.getUserId() != null) {
      criteria.andUserIdEqualTo(gedOrder.getUserId());
    }
    if (gedOrder.getContractCode() != null) {
      criteria.andContractCodeEqualTo(gedOrder.getContractCode());
    }
    if (gedOrder.getOrderCode() != null) {
      criteria.andOrderCodeEqualTo(gedOrder.getOrderCode());
    }
    if (gedOrder.getServiceFeeFlag() != null) {
      criteria.andServiceFeeFlagEqualTo(gedOrder.getServiceFeeFlag());
    }
    if (gedOrder.getGuaranteeFeeFlag() != null) {
      criteria.andGuaranteeFeeFlagEqualTo(gedOrder.getGuaranteeFeeFlag());
    }
    if (gedOrder.getGuaranteeFlag() != null) {
      criteria.andGuaranteeFlagEqualTo(gedOrder.getGuaranteeFlag());
    }
    if (gedOrder.getReadFlag() != null) {
      criteria.andReadFlagEqualTo(gedOrder.getReadFlag());
    }
    // if (gedOrder.getStatus() != null){criteria.andStatusEqualTo(gedOrder.getStatus());}
    if (gedOrder.getWithdrawFlag() != null) {
      criteria.andWithdrawFlagEqualTo(gedOrder.getWithdrawFlag());
    }
    if (gedOrder.getRepaymentLockFlag() != null) {
      criteria.andRepaymentLockFlagEqualTo(gedOrder.getRepaymentLockFlag());
    }
    if (gedOrder.getMasterOrderCode() != null) {
      criteria.andMasterOrderCodeEqualTo(gedOrder.getMasterOrderCode());
    }
    if (type != null && type == 1) {
      // criteria.andStatusBetween(139 , 170);
      criteria.andStatusLessThan(180);
    } else {
      if (gedOrder.getStatus() != null) {
        criteria.andStatusEqualTo(gedOrder.getStatus());
      }
    }
    List<GedOrder> list = this.gedOrderMapper.selectByExample(example);
    return list;
  }


  @Override
  public GedOrder getOneOrderingByCondition(GedOrder gedOrder, Integer type) {
    GedOrderExample example = new GedOrderExample();
    GedOrderExample.Criteria criteria = example.createCriteria();
    copyOrderUtil(gedOrder, criteria);
    example.setOrderByClause("create_time desc");
    if (gedOrder.getUserId() != null) {
      criteria.andUserIdEqualTo(gedOrder.getUserId());
    }
    if (gedOrder.getId() != null) {
      criteria.andIdEqualTo(gedOrder.getId());
    }
    if (gedOrder.getContractCode() != null) {
      criteria.andContractCodeEqualTo(gedOrder.getContractCode());
    }
    if (gedOrder.getOrderCode() != null) {
      criteria.andOrderCodeEqualTo(gedOrder.getOrderCode());
    }
    if (gedOrder.getServiceFeeFlag() != null) {
      criteria.andServiceFeeFlagEqualTo(gedOrder.getServiceFeeFlag());
    }
    if (gedOrder.getGuaranteeFeeFlag() != null) {
      criteria.andGuaranteeFeeFlagEqualTo(gedOrder.getGuaranteeFeeFlag());
    }
    if (gedOrder.getGuaranteeFlag() != null) {
      criteria.andGuaranteeFlagEqualTo(gedOrder.getGuaranteeFlag());
    }
    if (gedOrder.getReadFlag() != null) {
      criteria.andReadFlagEqualTo(gedOrder.getReadFlag());
    }
    // if (gedOrder.getStatus() != null){criteria.andStatusEqualTo(gedOrder.getStatus());}
    if (gedOrder.getWithdrawFlag() != null) {
      criteria.andWithdrawFlagEqualTo(gedOrder.getWithdrawFlag());
    }
    if (gedOrder.getRepaymentLockFlag() != null) {
      criteria.andRepaymentLockFlagEqualTo(gedOrder.getRepaymentLockFlag());
    }
    if (type != null && type == 1) {
      // criteria.andStatusBetween(139 , 170);
      criteria.andStatusLessThan(180);
    } else {
      if (gedOrder.getStatus() != null) {
        criteria.andStatusEqualTo(gedOrder.getStatus());
      }
    }
    List<GedOrder> list = this.gedOrderMapper.selectByExample(example);
    return (list == null || list.size() == 0) ? null : list.get(0);
  }

  private void copyOrderUtil(GedOrder gedOrder, GedOrderExample.Criteria criteria) {
    if (gedOrder != null) {
      if (gedOrder.getUserId() != null) {
        criteria.andUserIdEqualTo(gedOrder.getUserId());
      }
      if (gedOrder.getContractCode() != null) {
        criteria.andContractCodeEqualTo(gedOrder.getContractCode());
      }
      if (gedOrder.getOrderCode() != null) {
        criteria.andOrderCodeEqualTo(gedOrder.getOrderCode());
      }
      if (gedOrder.getServiceFeeFlag() != null) {
        criteria.andServiceFeeFlagEqualTo(gedOrder.getServiceFeeFlag());
      }
      if (gedOrder.getGuaranteeFeeFlag() != null) {
        criteria.andGuaranteeFeeFlagEqualTo(gedOrder.getGuaranteeFeeFlag());
      }
      if (gedOrder.getGuaranteeFlag() != null) {
        criteria.andGuaranteeFlagEqualTo(gedOrder.getGuaranteeFlag());
      }
      if (gedOrder.getReadFlag() != null) {
        criteria.andReadFlagEqualTo(gedOrder.getReadFlag());
      }
      if (gedOrder.getStatus() != null) {
        criteria.andStatusEqualTo(gedOrder.getStatus());
      }
      if (gedOrder.getWithdrawFlag() != null) {
        criteria.andWithdrawFlagEqualTo(gedOrder.getWithdrawFlag());
      }
      if (gedOrder.getRepaymentLockFlag() != null) {
        criteria.andRepaymentLockFlagEqualTo(gedOrder.getRepaymentLockFlag());
      }
      if (gedOrder.getMasterOrderCode() != null) {
        criteria.andMasterOrderCodeEqualTo(gedOrder.getMasterOrderCode());
      }
    }
  }

  @Override
  public GedServiceGuarFlagResForm confirWithDrawCash(User userInfo, String orderNo)
      throws Exception {
    GedServiceGuarFlagResForm flagResForm = new GedServiceGuarFlagResForm();
    WithDrawCapitalResForm resForm = withDr(userInfo.getId(), orderNo);
    logger.info("确认提现订单编号:" + orderNo);

    Account account = accountService.getAccountByUserId(userInfo.getId());
    if (account != null) {
      flagResForm.setAmt(resForm.getWithDrawAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
      flagResForm.setCardNum(account.getCompanyAccount());
    }

    GedOrder gedOrder = selectOrderByOrderNo(orderNo);
    flagResForm.setGuaranteeFeeFlag(gedOrder.getGuaranteeFeeFlag());// 保证金标识
    flagResForm.setServiceFeeFlag(gedOrder.getServiceFeeFlag());// 服务费标识
    // 若提现成功 更新数据库状态字段和修改日期
    GedOrder ged = new GedOrder();
    ged.setId(gedOrder.getId());
    ged.setModifyTime(new Date());
    ged.setWithdrawFlag(resForm.getWithDrawFlag());
    gedOrderMapper.updateByPrimaryKeySelective(ged);
    return flagResForm;
  }

  @Override
  public WithDrawCapitalResForm withDr(long userId, String orderNo) throws Exception {
    WithDrawCapitalResForm resForm = new WithDrawCapitalResForm();
    WithDrForm para = new WithDrForm();
    Date currentDate = new Date();
    String dataTime = DateFormatUtils.formatDate(currentDate, "HHmmssSSS");
    BigDecimal withDrawAmount = new BigDecimal(0);
    BigDecimal accountBalan = accountBalance(orderNo, userId);
    if (accountBalan.compareTo(BigDecimal.ZERO) == 0) {// 如果账户余额为0
      throw new BusinessException(Errors.WITHDRAWING_ERROR);
    }
    Integer status = null;
    GedOrder gedOrder = selectOrderByOrderNo(orderNo);
    if (gedOrder == null) {
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR);
    }
    Integer withDrawFlag = gedOrder.getWithdrawFlag() == null ? 0 : gedOrder.getWithdrawFlag();
    if (withDrawFlag == WithDrawFlag.SECOND_WITHDRAWING.getKey()
        || withDrawFlag == WithDrawFlag.SECOND_WITHDEAWFINISH.getKey()) {
      throw new BusinessException(Errors.WITHDRAWING_ERROR);
    }
    User user = userService.getUserById(userId);
    Integer serviceFeeFlag = gedOrder.getServiceFeeFlag();// 服务费标识
    Integer guaranteeFeeFlag = gedOrder.getGuaranteeFeeFlag();// 担保费标识
    Integer withdrawFlag = gedOrder.getWithdrawFlag() == null ? 0 : gedOrder.getWithdrawFlag();// 提现标识
    logger.info("服务费标识=" + serviceFeeFlag);
    if (serviceFeeFlag == null || serviceFeeFlag == 0) {// 服务费 未缴费 提现60%
      if (withdrawFlag == WithDrawFlag.fIRST_WITHDRAWING.getKey()) { // 没交服务费 没有回调 谈出异常1
        throw new BusinessException(Errors.WITHDRAWING_ERROR);
      }
      if (gedOrder.getWithdrawAmount() != null) {// 没交服务费 但已经提现60%
        throw new BusinessException(Errors.NOTSERVICE_ERROR);
      }
      withDrawAmount = gedOrder.getCreditAmount().multiply(new BigDecimal(0.6)).setScale(2,
          BigDecimal.ROUND_HALF_DOWN);
      withdrawFlag = WithDrawFlag.fIRST_WITHDRAWING.getKey();
    } else if (serviceFeeFlag == 1) {// 服务费已缴费
      if (gedOrder.getWithdrawAmount() != null) {// 已回调回来 提现剩下的40%
        if (guaranteeFeeFlag == null || guaranteeFeeFlag == 0) { // 但没交保障金
          throw new BusinessException(Errors.GUARANT_ERROR);
        }
        withDrawAmount = gedOrder.getCreditAmount().subtract(gedOrder.getWithdrawAmount())
            .setScale(2, BigDecimal.ROUND_HALF_DOWN);
        withdrawFlag = WithDrawFlag.SECOND_WITHDRAWING.getKey();// 3
      } else {
        if (withDrawFlag == WithDrawFlag.fIRST_WITHDRAWING.getKey()) {// 已交服务费 但是没还有回调回来 1
          throw new BusinessException(Errors.WITHDRAWING_ERROR);
        } else if (guaranteeFeeFlag == null || guaranteeFeeFlag == 0) {// 没交保证金 提现60%
          withDrawAmount = gedOrder.getCreditAmount().multiply(new BigDecimal(0.6)).setScale(2,
              BigDecimal.ROUND_HALF_DOWN);
          withdrawFlag = WithDrawFlag.fIRST_WITHDRAWING.getKey();// 1
        } else {
          withDrawAmount = gedOrder.getCreditAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN);// 已交服务费
          // 全额提现
          withdrawFlag = WithDrawFlag.SECOND_WITHDRAWING.getKey();// 3
        }
      }
    }
    logger.info("确认提现的提现金额:" + withDrawAmount);
    status = OrderStatus.YCTX.getCode();
    para.setMchn(mchn);// 系统代码88721657SUKQ
    para.setSeqNo(orderNo + dataTime);;// 交易流水号
    para.setTradeType(withDrawTradeType);// 交易类型11091004
    // todo 测试完成后放开
    para.setCustNo(user.getGetCustId());// 客户编号 测试6666
    // para.setCustNo("6666");// 客户编号 测试6666
    para.setBusiNo(gedOrder.getContractCode());// 合同ID
    para.setCustType("1");// 客户类型
    para.setApplyType(withDrawApplyType);// 申请类型1104
    para.setContractNo(gedOrder.getContractCode());;// 合同编号
    para.setAmt(String.valueOf(withDrawAmount));// 提现金额
    para.setSettleType("0");// 提现时效
    para.setSignature("");// 签名
    JSONObject json = withDra(para);
    if (!Constant.RES_CODE_SUCCESS.equals(json.getString("resp_code"))) {
      throw new BusinessException(Errors.PUSH_ERROR);
    }
    resForm.setData(json.toJSONString());
    resForm.setStatus(status);
    resForm.setWithDrawAmount(withDrawAmount);
    resForm.setWithDrawFlag(withdrawFlag);
    return resForm;
  }

  public GedOrder selectByContract(String contractNo) {
    GedOrderExample example = new GedOrderExample();
    GedOrderExample.Criteria criteria = example.createCriteria();
    criteria.andContractCodeEqualTo(contractNo);
    List<GedOrder> gedOrderList = gedOrderMapper.selectByExample(example);
    if (gedOrderList != null && gedOrderList.size() != 0) {
      return gedOrderList.get(0);
    }
    return null;
  }

  public JSONObject withDra(WithDrForm drForm) throws Exception {
    Map<String, String> param = new HashMap<String, String>();
    param.put("mchn", drForm.getMchn());// 系统代码88721657SUKQ
    param.put("seq_no", drForm.getSeqNo());// 交易流水号
    param.put("trade_type", drForm.getTradeType());// 交易类型11091004
    param.put("cust_no", drForm.getCustNo());// 客户编号 测试6666
    param.put("busi_no", drForm.getBusiNo());// 合同ID
    param.put("cust_type", drForm.getCustType());// 客户类型
    param.put("apply_type", drForm.getApplyType());// 申请类型1104
    param.put("contract_no", drForm.getContractNo());// 合同编号
    param.put("amt", drForm.getAmt());// 提现金额
    param.put("settle_type", drForm.getSettleType());// 提现时效
    param.put("signature", drForm.getSignature());// 签名
    logger.info("参数为=" + JSONObject.toJSONString(param));
    JSONObject json =
        HttpUtils.doPost(capitalUrl + "/api/careateWithDrawApply", JSONObject.toJSONString(param));
    return json;
  }

  @Override
  public Map<String, Object> callBackwithDraw(CallBackWithDrawReqForm reqForm) throws Exception {
    logger.info("提现回调回参数=" + JSONObject.toJSONString(reqForm));
    Map<String, Object> resParam = new HashMap<String, Object>();
    resParam.put("resp_code", Constant.RES_CODE_SUCCESS);
    Map<String, String> loanParam = new HashMap<String, String>();
    Map<String, String> withCashParam = new HashMap<String, String>();
    String busiNo = reqForm.getBusi_no();
    String seq_no = reqForm.getSeq_no();
    logger.info("合同编号:" + busiNo + "===" + "回调回来的交易流水号" + seq_no);
    GedOrder gedOrder = selectByContract(busiNo);
    Integer status = OrderStatus.YCTX.getCode();
    Integer loanStauts = FirstWithDeposit.FIRST_WITHCASH.getCode();
    if (Constant.RES_CODE_SUCCESS.equals(reqForm.getResp_code())) {// 提现成功
      BigDecimal reaTradeAmount = reqForm.getReal_trade_amount();// 回调回来的提现金额
      if (gedOrder != null) {
        OrderLog orderLog = selectOrderLog(gedOrder);// 查询日志
        if (orderLog != null) {
          logger.info("日志查出回调流水号========" + orderLog.getRemark());
          if (seq_no.equals(orderLog.getRemark()))// 防止重复回调
          {
            return resParam;
          }
        }
        BigDecimal creditAmount = gedOrder.getCreditAmount();// 放款额度
        BigDecimal withDrawAmount = gedOrder.getWithdrawAmount();// 已提现金额
        logger.info("回调回来的放款额度" + reaTradeAmount + "======" + "放款额度" + creditAmount + "==="
            + "数据库存放的已提现金额" + withDrawAmount);
        if (reaTradeAmount.equals(creditAmount)) {// 第一次交服务费全额提现
          logger.info("===========直接全部提现=============");
          loanStauts = FirstWithDeposit.ALREADY_PRESENTED.getCode();
          status = OrderStatus.HKING.getCode();
          gedOrder.setStatus(status);
          gedOrder.setWithdrawFlag(WithDrawFlag.SECOND_WITHDEAWFINISH.getKey());// 4
          gedOrder.setWithdrawAmount(reaTradeAmount);
          gedOrderMapper.updateByPrimaryKeySelective(gedOrder);
          createOrderLog(gedOrder, status, seq_no);// 新增日志
        } else {
          if (withDrawAmount == null) {// 一次提现
            logger.info("==========是一次提现啊============");
            gedOrder.setWithdrawAmount(reaTradeAmount);
            gedOrder.setWithdrawFlag(WithDrawFlag.FIRST_WITHDRAWFINISH.getKey());// 一次提现完成
            gedOrder.setStatus(status);
            gedOrderMapper.updateByPrimaryKeySelective(gedOrder);
            createOrderLog(gedOrder, status, seq_no);// 新增日志
          } else {// 二次提现处理成功
            logger.info("==========是二次提现啊=============");
            gedOrder.setWithdrawAmount(gedOrder.getWithdrawAmount().add(reaTradeAmount).setScale(2,
                BigDecimal.ROUND_HALF_DOWN));
            status = OrderStatus.HKING.getCode();
            loanStauts = FirstWithDeposit.ALREADY_PRESENTED.getCode();
            gedOrder.setStatus(status);
            gedOrder.setWithdrawFlag(WithDrawFlag.SECOND_WITHDEAWFINISH.getKey());// 4 二次提现完成
            gedOrder.setWithdrawAmount(
                withDrawAmount.add(reaTradeAmount).setScale(2, BigDecimal.ROUND_HALF_DOWN));
            gedOrderMapper.updateByPrimaryKeySelective(gedOrder);
            createOrderLog(gedOrder, status, seq_no);// 新增日志
          }
        }
        // 并把状态推送到借款系统
        loanParam.put("orderNo", gedOrder.getOrderCode());
        loanParam.put("status", String.valueOf(status));
        JSONObject loanJson = HttpUtils.doPost(borrowUrl + "/api/gedUpdateOrderStatus",
            JSONObject.toJSONString(loanParam));
        logger.info("推送借款系统更新状态返回的参数值===============" + loanJson.get("code") + "");
        // if (!loanJson.getString("code").equals(Constant.RES_CODE_SUCCESS)) {
        // throw new BusinessException(Errors.PUSH_ERROR);
        // }

        withCashParam.put("loanStatus", String.valueOf(loanStauts));// 状态码
        withCashParam.put("contractNo", busiNo);// 合同号
        if (StringUtils.isNotBlank(gedOrder.getMasterOrderCode())) {
          withCashParam.put("applyNo", gedOrder.getMasterOrderCode());// 父订单编号
        } else {
          withCashParam.put("applyNo", gedOrder.getOrderCode());// 订单编号
        }
        // ===========================暂时注释掉 调通完要开===============================
        withCashParam.put("withdrawAmount", reaTradeAmount + "");// 提现金额
        withCashParam.put("seqNo", seq_no);// 交易流水号
        withCashParam.put("mchn", reqForm.getMchn());// 系统代码
        JSONObject loanJs = HttpUtils.doPost(borrowUrl + "/api/firstWithDeposit",
            JSONObject.toJSONString(withCashParam));
        logger.info("推送借款系统提现反馈返回的参数值===============" + loanJs.get("code") + "");
        // if (!loanJs.getString("code").equals("0")) {
        // throw new BusinessException(Errors.PUSH_ERROR);
        // }
      }
    } else {
      logger.info("=========提现失败======");
      // 更新提现按钮标识为可提现
      if (gedOrder != null) {
        gedOrder.setWithdrawFlag(WithDrawFlag.FAIL.getKey());// 提现失败 5
        gedOrderMapper.updateByPrimaryKey(gedOrder);

        withCashParam.put("loanStatus", String.valueOf(FirstWithDeposit.FAIL.getCode()));
        withCashParam.put("contractNo", busiNo);
        if (StringUtils.isNotBlank(gedOrder.getMasterOrderCode())) {
          withCashParam.put("applyNo", gedOrder.getMasterOrderCode());// 父订单编号
        } else {
          withCashParam.put("applyNo", gedOrder.getOrderCode());// 订单编号
        }
        JSONObject loanJs = HttpUtils.doPost(borrowUrl + "/api/firstWithDeposit",
            JSONObject.toJSONString(withCashParam));
        logger.info("推送借款系统返回的参数值===============" + loanJs.get("code") + "");
        // if (!loanJs.getString("code").equals("0")) {
        // throw new BusinessException(Errors.PUSH_ERROR);
        // }
      }
    }
    return resParam;
  }

  public void createOrderLog(GedOrder gedOrder, Integer targetStatus, String seqNo) {
    // 新增日志信息
    OrderLog log = new OrderLog();
    log.setCreateId(gedOrder.getUserId());
    log.setSourceStatus(gedOrder.getStatus());
    log.setOrderId(gedOrder.getId());
    log.setTargetStatus(targetStatus);
    log.setCreateTime(new Date());
    log.setRemark(seqNo);// 交易流水号
    orderLogService.createOrderlog(log);
  }

  public OrderLog selectOrderLog(GedOrder gedOrder) {
    return orderLogService.getOrderLogByOrderId(gedOrder.getId(), gedOrder.getStatus());
  }

  @Override
  public WithDrawViewResForm beforeWithDr(String orderNo, Long userId) throws Exception {
    GedOrder order = this.selectOrderByOrderNo(orderNo);
    if (order == null) {
      throw new BusinessException(Errors.WITH_DRAW_ERROR, "显示提现详细信息，查询订单失败");// 提现失败
    }
    if (!(order.getStatus() == OrderStatus.FKCG.getCode()
        || order.getStatus() == OrderStatus.YCTX.getCode())) {
      throw new BusinessException(Errors.WITH_DRAW_ERROR, "该订单状态为非提现状态，无法展示提现信息");// 提现失败
    }

    Account account = accountService.getAccountByUserId(userId);

    WithDrawViewResForm res = new WithDrawViewResForm();

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    res.setAccountAmt(
        this.accountBalance(orderNo, userId).setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    if (order.getLoanAmount() != null && order.getWithdrawAmount() != null) {
      res.setCanWithdrawAmt(order.getLoanAmount().subtract(order.getWithdrawAmount()).setScale(2,
          BigDecimal.ROUND_HALF_DOWN) + "");
    }

    // 根据字典表查询出银行
    SystemDictionaryItem item = null;
    if (account.getCompanyBankOfDeposit() != null) {
      item = dictionaryService.getDictionaryByParam("BANK_TYPE", account.getCompanyBankOfDeposit());
    }
    res.setBankName(item == null ? "" : item.getValue());
    res.setBankNum(account.getCompanyAccount() == null ? ""
        : account.getCompanyAccount().substring(account.getCompanyAccount().length() - 4));
    res.setCorporationName(account.getCorporationName());
    res.setOrderNo(orderNo);
    res.setSource("冠易贷");
    BigDecimal withDrawAmount = withDrawAmount(orderNo);
    res.setWithDrawAmt(withDrawAmount.setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");// 提现金额
    res.setActualAmt(withDrawAmount.setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");// 实际到账金额
    res.setWithDrawDate(this.getNextDay());
    return res;
  }

  /**
   * 计算后一天
   *
   * @return
   */
  public static String getNextDay() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    Date date = calendar.getTime();
    SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
    return myFmt.format(date);
  }


  public GedOrderRepaymentPlan selectGedOrderPlan(String orderNo) {
    GedOrderRepaymentPlanExample example = new GedOrderRepaymentPlanExample();
    GedOrderRepaymentPlanExample.Criteria criteria = example.createCriteria();
    criteria.andOrderNoEqualTo(orderNo);
    List<GedOrderRepaymentPlan> planList = gedOrderRepaymentPlanMapper.selectByExample(example);
    if (planList != null && planList.size() != 0) {
      return planList.get(0);
    } else {
      return null;
    }
  }

  @Override
  public JSONObject recharge(RechargeForm paraForm) throws Exception {
    Map<String, String> map = new HashedMap();
    map.put("amt", paraForm.getAmt()); // 充值金额
    map.put("seq_no", paraForm.getSeq_no()); // 交易流水号
    map.put("cust_no", paraForm.getCust_no()); // 客户编号
    map.put("bank_id", paraForm.getBank_id()); // 银行类型
    // map.put("signature", paraForm.getSignature()); //签名
    // map.put("user_no", paraForm.getUser_no()); //用户编号
    map.put("trade_type", paraForm.getTrade_type()); // 交易类型
    map.put("mchn", paraForm.getMchn()); // 系统代码

    // logger.info("======="+rechargeUrl+"====");
    String jsonObject = HttpUtils.doPostForm(capitalUrl + "/api/rechargeOrder", map);
    // logger.info("充值接口返回结果:"+jsonObject);
    return JSONObject.parseObject(jsonObject);
  }

  @Override
  public JSONObject rechargeCallBack(RechargeCallBackForm paraForm) throws Exception {
    Map<String, String> map = new HashedMap();
    logger.info("调用入账接口");
    // map.put("signature", paraForm.getSignature()); //签名
    map.put("mchn", mchn); // 系统代码
    map.put("seq_no", paraForm.getSeq_no()); // 交易流水号
    map.put("trade_type", paraForm.getTrade_type()); // 交易类型
    map.put("cust_no", paraForm.getCust_no()); // 客户编号
    // map.put("user_no", paraForm.getUser_no()); //用户编号
    map.put("order_no", paraForm.getOrder_no()); // 第三方交易流水号
    map.put("tradeDate", paraForm.getTradeDate()); // 第三方交易日期
    map.put("respCode", paraForm.getRespCode()); // 第三方交易返回码
    map.put("mobile_no", paraForm.getMobile_no()); // 第三方交易返回手机号
    map.put("amt", paraForm.getAmt()); // 充值金额
    map.put("bank_id", paraForm.getBank_id()); // 银行类型

    String jsonObject = HttpUtils.doPostForm(capitalUrl + "/api/rechargeCallback", map);
    // logger.info("是否充值成功接口返回结果:"+jsonObject);
    return JSONObject.parseObject(jsonObject);
  }

  @Override
  public JSONObject compensation(ChargeAmountForm paraForm) throws Exception {
    Map<String, String> map = new HashedMap();
    map.put("amt", paraForm.getAmt()); // 收费金额
    map.put("seq_no", paraForm.getSeq_no()); // 交易流水号
    map.put("cust_no", paraForm.getCust_no()); // 客户编号
    // map.put("trade_no", paraForm.getTrade_no()); //交易编号
    map.put("filiale", paraForm.getFiliale()); // 所属分公司
    map.put("region", paraForm.getRegion()); // 所属大区
    // map.put("signature", paraForm.getSignature()); //签名
    // map.put("memo", paraForm.getMemo()); //备注
    map.put("busi_type", paraForm.getBusi_type()); // 账户类型
    // map.put("user_no", paraForm.getUser_no()); //用户编号
    map.put("trade_type", paraForm.getTrade_type()); // 交易类型
    map.put("platform", paraForm.getPlatform()); // 所属平台
    map.put("accounts_type", paraForm.getAccounts_type()); // 账务类型
    map.put("mchn", paraForm.getMchn()); // 系统代码
    // map.put("busi_no", paraForm.getBusi_no()); //业务编号

    JSONObject jsonObject =
        HttpUtils.doPost(capitalUrl + "/api/compensation", JSONObject.toJSONString(map));
    logger.info("缴费接口返结果:" + jsonObject);
    return jsonObject;
  }

  @Override
  public RechargeReForm userRecharge(Long userId, String bankId) throws Exception {
    logger.info("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓调用userRecharge接口开始↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
    User userInfo = userService.getUserById(userId);
    GedOrder order = this.getNewOrderByUserId(userId);

    GedRechargeRecord rechargeRecord =
        this.rechargeRecordService.getRechargeRecordByOrderNo(order.getOrderCode());
    if (rechargeRecord != null && rechargeRecord.getStatus() >= 1
        && rechargeRecord.getStatus() != 2) {
      throw new BusinessException(Errors.SYSTEM_ERROR, "充值缴费正在处理中，禁止操作");
    }

    RechargeForm paras = new RechargeForm();
    // 计算充值总金额
    String amt = order.getServiceFee().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "";
    paras.setAmt(amt);
    paras.setBank_id(bankId);
    paras.setCust_no(userInfo.getGetCustId());
    paras.setMchn(mchn);
    SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
    paras.setSeq_no(order.getOrderCode() + dateFormat.format(new Date()));
    // paras.setSignature(); 充值没有验签
    paras.setTrade_type(rechargeTradeType);
    // paras.setUser_no();
    logger.info(
        "************************************调用userRcharge接口时传入的参数：************************************");
    logger.info(JSONObject.toJSONString(paras));

    JSONObject res = this.recharge(paras);
    logger.info("************************************调用userRcharge接后返回的数据" + userId
        + "  结果为：************************************");
    logger.info(res.toJSONString());

    logger.info("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑调用userrecharge接口结束↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
    if (res != null && Constant.RES_CODE_SUCCESS.equals(res.getString("resp_code"))) {
      RechargeReForm r = new RechargeReForm();
      r.setUserName(res.getString("username"));
      r.setOrderNo(res.getString("order_no"));
      r.setSeqNum(res.getString("seq_no"));
      saverRechargeRecordItem(order, r.getUserName(), r.getOrderNo(), amt, bankId, 0);
      return r;
    }
    throw new BusinessException(Errors.SYSTEM_ERROR,
        "充值缴费前获取流水号失败  原因" + res.getString("resp_code"));
  }

  @Override
  public RechargeReForm userRechargeForRepayment(Long userId, String bankId, String amt,
      String period, Integer type) throws Exception {
    if (type == null || type != 2)
      logger.info("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓还款充值调get开始↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓userid=" + userId);
    else
      logger.info("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓【提前结清】还款充值调get开始↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓userid=" + userId);
    logger.info("userRechargeForRepayment请求参数为bankId=" + bankId + "------amt=" + amt);
    User userInfo = userService.getUserById(userId);
    GedOrder order = this.getNewOrderByUserId(userId);
    if (order == null) {
      throw new BusinessException(Errors.SYSTEM_ERROR, "还款充值前操作订单查询失败 userId=" + userId);
    }
    /*
     * RBucket rBucket = redissonClient.getBucket(Constant.CONTRACT_NO + ":" +
     * order.getContractCode()); if (!rBucket.isExists()) { rBucket.set(0); }else { int flag = (int)
     * rBucket.get(); if (flag == 0) throw new BusinessException(Errors.SYSTEM_ERROR , "您的还款正在处理中");
     * else rBucket.set(0); }
     */
    if (order.getRepaymentLockFlag() == null || order.getRepaymentLockFlag() != 0) {
      /*
       * GedOrder updater = new GedOrder(); updater.setId(order.getId());
       * updater.setRepaymentLockFlag(0); this.gedOrderMapper.updateByPrimaryKeySelective(updater);
       */
    } else {
      throw new BusinessException(Errors.SYSTEM_ERROR, "您的还款正在处理中");
    }

    RechargeForm paras = new RechargeForm();
    paras.setAmt(amt);
    paras.setBank_id(bankId);
    paras.setCust_no(userInfo.getGetCustId());
    paras.setMchn(mchn);
    SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmssSSS");
    paras.setSeq_no(order.getOrderCode() + dateFormat.format(new Date()));
    paras.setTrade_type(rechargeTradeType);

    JSONObject res = this.recharge(paras);
    logger.info("充值操作==========用户id=" + userId + "  结果为：");
    logger.info(res.toJSONString());
    if (type == null || type != 2)
      logger.info("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑还款充值调get结束↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
    else
      logger.info("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑【提前结清】还款充值调get结束↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
    if (res != null && Constant.RES_CODE_SUCCESS.equals(res.getString("resp_code"))) {
      RechargeReForm r = new RechargeReForm();
      r.setUserName(res.getString("username"));
      r.setOrderNo(res.getString("order_no"));
      r.setSeqNum(res.getString("seq_no"));

      GedRepaymentRecord record = new GedRepaymentRecord();
      if (type != null)
        record.setType(type);
      record.setOrderNo(r.getOrderNo());
      record.setRepaymentAmount(new BigDecimal(amt));
      record.setSeqNo(order.getOrderCode());
      record.setCreateTime(new Date());
      record.setModifyTime(new Date());
      record.setCustId(userInfo.getGetCustId());
      record.setContractNo(order.getContractCode());
      record.setPeriodNum(Integer.parseInt(period));
      // record.setDeductCustName();
      // record.setDeductApplyNo();
      record.setMobileNum(userInfo.getMobile());
      record.setBank(bankId);
      // record.setAccountBalance();
      record.setCreateId(userId);

      repaymentRecordService.insertOrUpdate(record);
      return r;
    }
    if (type == null || type != 2)
      throw new BusinessException(Errors.SYSTEM_ERROR,
          "还款充值前获取流水号失败 原因：" + res.getString("resp_code"));
    else
      throw new BusinessException(Errors.SYSTEM_ERROR,
          "【提前结清】还款充值前获取流水号失败 原因：" + res.getString("resp_code"));
  }

  public void saverRechargeRecordItem(GedOrder order, String username, String order_no, String amt,
      String bankId, Integer status) {

    GedRechargeRecord rechargeRecord = new GedRechargeRecord();
    rechargeRecord.setStatus(status);
    rechargeRecord.setSeqNo(order.getOrderCode());
    // rechargeRecord.setCreateTime(new Date());
    // rechargeRecord.setCustNo();
    rechargeRecord.setMobileNo(username);
    rechargeRecord.setOrderNo(order_no);
    rechargeRecord.setBankId(bankId);
    rechargeRecord.setRechargeAmount(new BigDecimal(amt));
    rechargeRecord.setRespCode(Constant.RES_CODE_SUCCESS);
    rechargeRecord.setTradeDate(new Date());
    rechargeRecord.setType(1);
    logger.info("===========================userRecharge返回的订单号=================================："
        + order_no);
    GedRechargeRecord record =
        rechargeRecordService.getRechargeRecordByOrderNo(order.getOrderCode());
    if (record == null) {
      rechargeRecordService.insert(rechargeRecord);
    } else {
      rechargeRecord.setId(record.getId());
      rechargeRecordService.updateById(rechargeRecord);
    }
  }

  @Override
  public void userRechargeCallBack(Long userId) throws Exception {
    logger.info("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓充值成功后回调get开始↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓userid=" + userId);
    User userInfo = userService.getUserById(userId);
    GedOrder order = this.getNewOrderByUserId(userId);
    // Account account = accountService.getAccountByUserId(userId);
    // String amt =
    // order.getServiceFee().add(order.getCashDeposit().add(order.getGuaranteeFee())).setScale(2,BigDecimal.ROUND_HALF_DOWN)
    // +"";
    RechargeCallBackForm rechargeCallBackForm = new RechargeCallBackForm();
    rechargeCallBackForm.setTrade_type(rechargeCallBackTradeType);// 交易类型
    SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
    GedRechargeRecord rechargeRecord =
        rechargeRecordService.getRechargeRecordByOrderNo(order.getOrderCode());

    rechargeCallBackForm.setSeq_no(rechargeRecord.getSeqNo() + dateFormat.format(new Date()));// 交易流水号

    rechargeCallBackForm.setCust_no(userInfo.getGetCustId());// 客户编号
    rechargeCallBackForm.setOrder_no(rechargeRecord.getOrderNo());// 第三方交易流水号
    // rechargeCallBackForm.setTradeDate(rechargeRecord.getTradeDate().getTime() + "");// 第三方交易日期
    rechargeCallBackForm.setRespCode(rechargeRecord.getRespCode());// 第三方交易返回码
    rechargeCallBackForm.setMobile_no(rechargeRecord.getMobileNo());// 第三方交易返回手机号
    rechargeCallBackForm.setAmt(order.getServiceFee().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");// 充值金额
    rechargeCallBackForm.setBank_id(rechargeRecord.getBankId());// 银行类型

    // 更新充值记录
    updateRechageRecord(rechargeRecord.getId(), 1);

    logger.info("******************************充值完成后调get入账的参数******************************");
    logger.info(JSONObject.toJSONString(rechargeCallBackForm));
    JSONObject res = this.rechargeCallBack(rechargeCallBackForm);
    logger.info("******************************充值成功回调操get作结果为：");
    logger.info(res.toJSONString());
    Map<String, String> data = new HashedMap();
    data.put("userId", userId.intValue() + "");
    // 缴费
    jmsProvider.sendMessage(Constant.MQ_GED_COMPENSATION, JSONObject.toJSONString(data));
    logger.info("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑充值成功后回调get结束↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
  }

  private void updateRechageRecord(Long reId, Integer status) {
    GedRechargeRecord updater = new GedRechargeRecord();
    updater.setId(reId);
    updater.setStatus(status);
    updater.setModifyTime(new Date());
  }

  @Override
  public void userRechargeCallBackForRepayment(String ssn, String amt,
      PayCallBackReqForm payCallBackReqForm) throws Exception {
    if (!Constant.RES_CODE_SUCCESS.equals(payCallBackReqForm.getResp_code())) {
      logger.info("还款充值失败:" + JSONObject.toJSONString(payCallBackReqForm));
      throw new BusinessException(Errors.SYSTEM_ERROR, "还款充值前获取流水号失败");
    }
    // 1、获取还款充值流水记录
    GedRepaymentRecord record = repaymentRecordService.getGedRepaymentRecordByZjStreamNo(ssn);
    // 2、获取订单信息
    GedOrder order = this.selectOrderByOrderNo(record.getSeqNo());
    // 3、获取用户信息
    User userInfo = userService.getUserById(order.getUserId());
    RechargeCallBackForm rechargeCallBackForm = new RechargeCallBackForm();
    rechargeCallBackForm.setTrade_type(rechargeCallBackTradeType);// 交易类型
    rechargeCallBackForm.setSeq_no(order.getOrderCode());// 交易流水号

    rechargeCallBackForm.setCust_no(userInfo.getGetCustId());// 客户编号
    rechargeCallBackForm.setOrder_no(ssn);// 第三方交易流水号
    // rechargeCallBackForm.setTradeDate(rechargeRecord.getTradeDate().getTime() + "");// 第三方交易日期
    rechargeCallBackForm.setRespCode(payCallBackReqForm.getResp_code());// 第三方交易返回码
    rechargeCallBackForm.setMobile_no(record.getMobileNum());// 第三方交易返回手机号
    rechargeCallBackForm.setAmt(new BigDecimal(amt).setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");// 充值金额
    rechargeCallBackForm.setBank_id(record.getBank());// 银行类型

    // 5、资金入账
    logger.info("调用资金入账接口开始");
    logger.info(JSONObject.toJSONString(rechargeCallBackForm));
    JSONObject res = this.rechargeCallBack(rechargeCallBackForm);
    logger.info("调用资金入账接口开始结束");
    logger.info(res.toJSONString());
    // 4、锁定还款状态（等待借款系统处理成功回调）
    GedOrder updater = new GedOrder();
    updater.setId(order.getId());
    updater.setRepaymentLockFlag(0);
    this.gedOrderMapper.updateByPrimaryKeySelective(updater);
    Map<String, String> data = new HashedMap();
    data.put("orderNo", ssn);
    data.put("amt", rechargeCallBackForm.getAmt());
    // 5、调借款端进行划扣
    jmsProvider.sendMessage(Constant.MQ_GED_REPAYMENT_BORROW, JSONObject.toJSONString(data));
    GedRepaymentRecord recorde = new GedRepaymentRecord();
    recorde.setStatus(1);
    recorde.setOrderNo(ssn);
    recorde.setModifyTime(new Date());
    recorde.setSeqNo(order.getOrderCode());
    recorde.setDeductCustName(userInfo.getUsername()); // 扣款人Name 扣款人姓名
    recorde.setAccountBalance(accountBalance(order.getOrderCode(), order.getUserId())); // 账户余额
    // 6、更新还款记录表
    repaymentRecordService.insertOrUpdate(recorde);
    logger.info("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑还款充值成功后回调get结束↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
  }

  @Override
  public CompensationResForm userCompensation(Long userId) throws Exception {
    GedOrder order = this.getNewOrderByUserId(userId);
    // 修改缴费状态
    GedRechargeRecord record =
        rechargeRecordService.getRechargeRecordByOrderNo(order.getOrderCode());
    // record.setModifyTime(new Date());
    // record.setStatus(6);
    // rechargeRecordService.updateById(record);

    CompensationResForm resForm = new CompensationResForm();
    logger.info("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓缴费接口开始     id=" + userId + "↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
    User user = userService.getUserById(userId);


    ChargeAmountForm paraForm = new ChargeAmountForm();
    paraForm.setTrade_type(compensationTradeType);
    // String amt = order.getServiceFee().add(order.getCashDeposit().add(order.getGuaranteeFee()))
    // .setScale(2, BigDecimal.ROUND_HALF_DOWN) + "";
    String amt =
        order.getServiceFee().multiply(new BigDecimal(0.4)).setScale(2, BigDecimal.ROUND_HALF_DOWN)
            + "";
    paraForm.setAmt(amt);
    // SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
    // paraForm.setSeq_no(order.getOrderCode() + dateFormat.format(new Date()));
    paraForm.setSeq_no(order.getOrderCode() + RandomUtil.generateString(6));
    paraForm.setCust_no(user.getGetCustId());
    // paraForm.setTrade_no();
    paraForm.setFiliale(order.getBranchOffice());
    paraForm.setRegion(order.getRegionOffice());
    // paraForm.setMemo(); // 备注
    paraForm.setBusi_type("1");
    if (order.getProvinceId() == 10040001 || order.getProvinceId() == 10040002
        || order.getProvinceId() == 10040003) {
      paraForm.setPlatform(order.getProvinceId() + "");
    } else {
      paraForm.setPlatform("10040099");
    }
    paraForm.setAccounts_type(accountsType);
    paraForm.setMchn(mchn);
    // paraForm.setBusi_no();
    JSONObject ressix = null;
    JSONObject res = null;// 40%
    // 冠君征信：冠群驰骋=6:4
    // 0未缴费1冠君缴费失败2冠群缴费失败3全部失败4成功
    if (order.getServiceFeeResult() == 3) {
      ressix = this.compensation(compensaTrade(order, user));// 60%
      res = this.compensation(paraForm);// 40%
    }
    if (order.getServiceFeeResult() == 0 || order.getServiceFeeResult() == 2) {
      res = this.compensation(paraForm);// 40%
    }
    if (order.getServiceFeeResult() == 0 || order.getServiceFeeResult() == 1) {
      ressix = this.compensation(compensaTrade(order, user));// 60%
    }
    logger.info(
        "*********************************************缴费操作后返回的结果为*********************************************");
    if (res != null)
      logger.info("冠群驰骋回调的结果=" + JSONObject.toJSONString(res));
    if (ressix != null)
      logger.info("冠君征信回调的结果=" + JSONObject.toJSONString(ressix));

    if (order.getServiceFeeResult() == 0 || order.getServiceFeeResult() == 3) {
      if (res == null || ressix == null
          || (!Constant.RES_CODE_SUCCESS.equals(res.getString("resp_code"))
              && !Constant.RES_CODE_SUCCESS.equals(ressix.getString("resp_code")))) {
        updateStatus(3, order, record);// 全部失败
        return resForm;
      }
      if (res == null || !Constant.RES_CODE_SUCCESS.equals(res.getString("resp_code"))) {
        updateStatus(2, order, record);
        return resForm;
      }
      if (ressix == null || !Constant.RES_CODE_SUCCESS.equals(ressix.getString("resp_code"))) {
        updateStatus(1, order, record);
        return resForm;
      }
    } else if (order.getServiceFeeResult() == 1) {
      if (ressix == null || !Constant.RES_CODE_SUCCESS.equals(ressix.getString("resp_code"))) {
        updateStatus(1, order, record);
        return resForm;
      }
    } else if (order.getServiceFeeResult() == 2) {
      if (res == null || !Constant.RES_CODE_SUCCESS.equals(res.getString("resp_code"))) {
        updateStatus(2, order, record);
        return resForm;
      }
    }
    // 缴费成功
    GedOrder o = new GedOrder();
    o.setServiceFeeFlag(1);
    o.setId(order.getId());
    o.setServiceFeeResult(4);
    this.gedOrderMapper.updateByPrimaryKeySelective(o);

    if (record != null) {
      record.setStatus(3);
      rechargeRecordService.updateById(record);
      logger.info("缴费成功后更新的的rechargeRecord" + JSONObject.toJSONString(record));
    } else {
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR,
          "充值成功记录查询失败 orderNo=" + order.getOrderCode());
    }
    // 推送借款端
    Map<String, String> data = new HashedMap();
    data.put("orderCode", order.getOrderCode());
    data.put("sysFlag", order.getSysFlag() + "");
    data.put("contractCode", order.getContractCode() + "");// 合同编号
    jmsProvider.sendMessage(Constant.MQ_GED_PUSH_TO_BORROW, JSONObject.toJSONString(data));
    resForm.setAmt(order.getServiceFee().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    resForm.setOrderCode(order.getOrderCode());
    logger.info("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑缴费接口结束    id=" + userId + "↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ");
    return resForm;
  }

  public void updateStatus(int status, GedOrder order, GedRechargeRecord record) {
    record.setStatus(6);
    record.setModifyTime(new Date());
    rechargeRecordService.updateById(record);
    order.setServiceFeeResult(status);
    gedOrderMapper.updateByPrimaryKey(order);
  }


  public ChargeAmountForm compensaTrade(GedOrder order, User user) {
    ChargeAmountForm paraForm = new ChargeAmountForm();
    paraForm.setTrade_type(compensaTradeType);
    String amt =
        order.getServiceFee().multiply(new BigDecimal(0.6)).setScale(2, BigDecimal.ROUND_HALF_DOWN)
            + "";
    paraForm.setAmt(amt);
    paraForm.setSeq_no(order.getOrderCode() + RandomUtil.generateString(6));
    paraForm.setCust_no(user.getGetCustId());
    paraForm.setFiliale(order.getBranchOffice());
    paraForm.setRegion(order.getRegionOffice());
    paraForm.setBusi_type("1");
    if (order.getProvinceId() == 10040001 || order.getProvinceId() == 10040002
        || order.getProvinceId() == 10040003) {
      paraForm.setPlatform(order.getProvinceId() + "");
    } else {
      paraForm.setPlatform("10040099");
    }
    paraForm.setAccounts_type(accountsType);
    paraForm.setMchn(mchn);
    return paraForm;
  }


  @Override
  public GeneratePayParas generatePayParas(String seqNum, String loginName, String orderNo,
      Long userId, String orderPayType, String issinscd) {
    GedOrder order = this.selectOrderByOrderNo(seqNum.substring(0, seqNum.length() - 6));
    if (order == null) {
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR,
          "生成第三方支付参数失败，原因订单查询失败orderNo=" + seqNum.substring(0, seqNum.length() - 6));
    }
    // User user = userService.getUserById(userId);
    Account account = accountService.getAccountByUserId(userId);
    if (account == null) {
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR,
          "生成第三方支付参数失败，原因账户查询失败userId=" + userId);
    }

    GeneratePayParas payParas = new GeneratePayParas();
    payParas.setAmt(order.getServiceFee().multiply(new BigDecimal(100)).intValue() + "");
    payParas.setLogin_id(loginName);
    payParas.setMchnt_cd(gedPayMchn);
    // SimpleDateFormat df = new SimpleDateFormat("HHmmss");
    payParas.setMchnt_txn_ssn(orderNo);
    payParas.setBack_notify_url(gedapiUrl + "/pc/page/saveResParasAfterPay");
    payParas.setPage_notify_url(gedapiUrl + "/pc/page/payCallBack");
    payParas.setIss_ins_cd(issinscd);
    payParas.setOrder_pay_type(orderPayType);

    // 签名数据明文：amt + "|" + back_notify_url + "|" + iss_ins_cd + "|" + login_id + "|" + mchnt_cd + "|"
    // + mchnt_txn_ssn + "|" + order_pay_type + "|" + page_notify_url
    String si = payParas.getAmt() + "|" + payParas.getBack_notify_url() + "|" + issinscd + "|"
        + payParas.getLogin_id() + "|" + payParas.getMchnt_cd() + "|" + payParas.getMchnt_txn_ssn()
        + "|" + orderPayType + "|" + payParas.getPage_notify_url();
    String ssiInfo = SecurityUtils.sign(si);
    logger.info("generatePayParas明文签名为：" + si);
    // logger.info("秘钥：" + SecurityUtils.privateKey);
    // logger.info("路径：" + SecurityUtils.getClassPath());
    logger.info("generatePayParas生成的签名为：" + ssiInfo);
    payParas.setSignature(ssiInfo);

    return payParas;
  }

  @Override
  public GeneratePayParas generatePayParasForRepayment(String seqNum, String loginName,
      String orderNo, Long userId, String orderPayType, String issinscd, String amt) {
    GedOrder order = this.selectOrderByOrderNo(seqNum.substring(0, seqNum.length() - 9));
    if (order == null) {
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR,
          "生成第三方支付参数失败，原因订单查询失败orderNo=" + seqNum.substring(0, seqNum.length() - 9));
    }
    // User user = userService.getUserById(userId);
    Account account = accountService.getAccountByUserId(userId);
    if (account == null) {
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR,
          "生成第三方支付参数失败，原因账户查询失败userId=" + userId);
    }

    GeneratePayParas payParas = new GeneratePayParas();
    // todo 测试完成后放开
    // RBucket rBucket = redissonClient.getBucket(Constant.PAYMENT+order.getOrderCode() );
    // if (!rBucket.isExists()) {
    // throw new BusinessException(Errors.USER_VALIDE_CODE_NOT_EXISTS_ERROR ,"应还款金额查询失败");
    // }
    // MsgRedisEntity mr = (MsgRedisEntity) rBucket.get();
    // String repayValue = mr.getParam()[0];
    // if(Integer.parseInt(repayValue) < Integer.parseInt(amt))
    // throw new BusinessException(Errors.USER_VALIDE_CODE_NOT_EXISTS_ERROR ,"还款金额不能大于应还款金额");
    payParas.setAmt(new BigDecimal(amt).multiply(new BigDecimal(100)).intValue() + "");
    payParas.setLogin_id(loginName);
    payParas.setMchnt_cd(gedPayMchn);
    // SimpleDateFormat df = new SimpleDateFormat("HHmmssSSS");
    // payParas.setMchnt_txn_ssn(orderNo + df.format(new Date()));
    payParas.setMchnt_txn_ssn(orderNo);
    payParas.setBack_notify_url(gedapiUrl + "/pc/page/saveResParasAfterPayRepayment");
    payParas.setPage_notify_url(gedapiUrl + "/pc/page/payCallBackForPayment");
    payParas.setIss_ins_cd(issinscd);
    payParas.setOrder_pay_type(orderPayType);

    // 签名数据明文：amt + "|" + back_notify_url + "|" + iss_ins_cd + "|" + login_id + "|" + mchnt_cd + "|"
    // + mchnt_txn_ssn + "|" + order_pay_type + "|" + page_notify_url
    String si = payParas.getAmt() + "|" + payParas.getBack_notify_url() + "|" + issinscd + "|"
        + payParas.getLogin_id() + "|" + payParas.getMchnt_cd() + "|" + payParas.getMchnt_txn_ssn()
        + "|" + orderPayType + "|" + payParas.getPage_notify_url();
    String ssiInfo = SecurityUtils.sign(si);
    logger.info("generatePayParasForRepayment明文签名为：" + si);
    logger.info("generatePayParasForRepayment入参amt：" + amt);
    // logger.info("秘钥：" + SecurityUtils.privateKey);
    // logger.info("路径：" + SecurityUtils.getClassPath());
    logger.info("generatePayParasForRepayment生成的签名为：" + ssiInfo);
    payParas.setSignature(ssiInfo);

    return payParas;
  }

  @Override
  public Integer saveResParasAfterPay(PayCallBackReqForm res) throws Exception {
    logger.info("=======================================支付回调开始=================================");
    logger.info(JSONObject.toJSONString(res));
    // 第三方支付成功
    // 缴费6位
    String ssn = res.getMchnt_txn_ssn();
    GedRechargeRecord record = rechargeRecordService.getRechargeRecordByStreamNo(ssn);
    GedOrder order = this.selectOrderByOrderNo(record.getSeqNo());
    if (order == null) {
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR,
          "第三方会掉失败，原因查询订单失败  回传orderNo=" + res.getMchnt_txn_ssn());
    }
    User user = userService.getUserById(order.getUserId());;
    if (user == null) {
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR,
          "第三方回调失败，原因查询用户失败  回传orderNo=" + res.getMchnt_txn_ssn());
    }
    if (res != null && res.getResp_code() != null
        && Constant.RES_CODE_SUCCESS.equals(res.getResp_code().trim())) {
      rechargeRecordHelper(res, user, 1);
      logger.info("充值111成功");
      // 更新订单费用状态
      Map<String, String> map = new HashedMap();
      map.put("userId", "" + order.getUserId().intValue());
      // 推送get
      jmsProvider.sendMessage(Constant.MQ_GED_CALL_BACK_GET, JSONObject.toJSONString(map));
      return 1;
    } else {
      // 第三方支付失败
      logger.info("充值失败");
      rechargeRecordHelper(res, user, 2);
    }
    logger.info("=======================================支付回调结束=================================");
    return 0;
  }

  private void rechargeRecordHelper(PayCallBackReqForm res, User user, Integer status) {
    GedRechargeRecord rechargeRecord = new GedRechargeRecord();
    // rechargeRecord.setStatus(1);
    rechargeRecord
        .setSeqNo(res.getMchnt_txn_ssn().substring(0, res.getMchnt_txn_ssn().length() - 9));
    rechargeRecord.setCustNo(user.getGetCustId());
    rechargeRecord.setMobileNo(res.getLogin_id());
    // rechargeRecord.setOrderNo(res.getMchnt_txn_ssn());
    // rechargeRecord.setRechargeAmount(new BigDecimal(res.getAmt()));
    rechargeRecord.setRespCode(res.getResp_code());
    // rechargeRecord.setSeqNo();
    rechargeRecord.setTradeDate(new Date());
    rechargeRecord.setType(1);
    rechargeRecord.setStatus(status);

    GedRechargeRecord records =
        rechargeRecordService.getRechargeRecordByOrderNo(rechargeRecord.getSeqNo());
    if (records == null) {
      rechargeRecord.setCreateTime(new Date());
      rechargeRecordService.insert(rechargeRecord);
    } else {
      rechargeRecord.setId(records.getId());
      rechargeRecordService.updateById(rechargeRecord);
    }

  }

  @Override
  public Integer saveBankId(String orderNo, String bankId, Long userId) {
    GedRechargeRecord record = rechargeRecordService.getRechargeRecordByOrderNo(orderNo);
    GedRechargeRecord rechargeRecord = new GedRechargeRecord();
    rechargeRecord.setBankId(bankId);

    if (record == null) {
      // 新增操作
      User userInfo = userService.getUserById(userId);
      // rechargeRecord.setOrderNo(orderNo);
      rechargeRecord.setSeqNo(orderNo);
      rechargeRecord.setCustNo(userInfo.getGetCustId());
      return rechargeRecordService.insert(rechargeRecord);
    } else {
      // 更新操作
      rechargeRecord.setId(record.getId());
      return rechargeRecordService.updateById(rechargeRecord);
    }
  }

  @Override
  public BigDecimal accountBalance(String orderNo, Long userId) throws Exception {

    Date currentDate = new Date();
    User user = userService.getUserById(userId);
    String dataTime = DateFormatUtils.formatDate(currentDate, "yyyyMMddHHmmssSSS");
    logger.info("账户余额交易流水号===========================" + dataTime);
    Map<String, String> param = new HashMap<String, String>();
    param.put("cust_no", user.getGetCustId());// 测试6666
    // param.put("cust_no", "6666");么
    param.put("busi_type", "1");
    param.put("mchn", mchn);// 系统代码88721657SUKQ
    param.put("seq_no", dataTime);// 交易流水号
    param.put("trade_type", accountBalanceTradeType);// 交易类型11110003
    // 账户余额
    String loanJson = HttpUtils.doPostForm(capitalUrl + "/api/getAccountBanlance", param);
    logger.info("账户余额返回的对象" + loanJson);
    JSONObject jsonObject = JSONObject.parseObject(loanJson);
    if (Constant.RES_CODE_SUCCESS.equals(jsonObject.getString("resp_code"))) {
      logger.info("账户余额：" + jsonObject.getBigDecimal("amount"));
      return jsonObject.getBigDecimal("amount");
    }
    return new BigDecimal(0);
  }

  @Override
  public List<GedOrder> getOrdersByUserId(Long userId) {
    GedOrderExample example = new GedOrderExample();
    example.setOrderByClause("create_time desc");
    GedOrderExample.Criteria criteria = example.createCriteria();
    criteria.andUserIdEqualTo(userId);
    return gedOrderMapper.selectByExample(example);
  }

  @Override
  public GedOrder getNewOrderByUserId(Long userId) {
    List<GedOrder> datas = this.getOrdersByUserId(userId);
    return (datas == null || datas.size() == 0) ? null : datas.get(0);
  }

  @Override
  public OrderFeesDetailReqForm orderManage(Long userId) throws Exception {
    // 1 获取该用户的最新订单 如果为null 转向无此订单页面
    GedOrder newOrder = this.getNewOrderByUserId(userId);
    GedRechargeRecord rechargeRecord = null;
    OrderFeesDetailReqForm res = new OrderFeesDetailReqForm();
    if (newOrder == null) {
      res.setPageNum(2);
      // }else if ((newOrder.getStatus() == OrderStatus.FKCG.getCode() || newOrder.getStatus() ==
      // OrderStatus.YCTX.getCode()) && (null == newOrder.getServiceFeeFlag() ||
      // newOrder.getServiceFeeFlag() == 0)){
    } else if ((newOrder.getStatus() == OrderStatus.FKCG.getCode()
        || newOrder.getStatus() == OrderStatus.YCTX.getCode())) {
      rechargeRecord = rechargeRecordService.getRechargeRecordByOrderNo(newOrder.getOrderCode());
      res = this.payFeeInfos(userId);
      res.setPageNum(1);
      // res.setIsPay(newOrder.getServiceFeeFlag() + "");
      res.setIsPay(createIsPay4Manager(newOrder, rechargeRecord) + "");
      res.setIsWithdraw(isWithdraw(newOrder) + "");
      if (newOrder.getServiceFeeFlag() == 1 && newOrder.getGuaranteeFeeFlag() == 1) {
        res.setWithdrawPercent("2");
      } else {
        res.setWithdrawPercent("1");
      }
    } else {
      rechargeRecord = rechargeRecordService.getRechargeRecordByOrderNo(newOrder.getOrderCode());
      // res.setPageNum(3);
      OrderViewsReqForm re = this.orderView(userId);
      copyUtil(res, re);
      res.setPageNum(3);
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
      // GedRechargeRecord rechargeRecord =
      // rechargeRecordService.getRechargeRecordByOrderNo(newOrder.getOrderCode());
      if (rechargeRecord != null && rechargeRecord.getStatus() == 3) {
        res.setFeePaidAmount(newOrder.getServiceFee().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
        res.setFeePaidDate(sf.format(rechargeRecord.getModifyTime()));
      } else {
        res.setFeePaidAmount("");
        res.setFeePaidDate("");
      }
      if (newOrder.getStatus() >= OrderStatus.YCTX.getCode()
          && newOrder.getStatus() <= OrderStatus.YQYH.getCode()) {
        res.setFeeAwithdrawAmount(newOrder.getWithdrawAmount() == null ? ""
            : newOrder.getWithdrawAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
        OrderLog orderLog =
            orderLogService.getOrderLogByOrderId(newOrder.getId(), OrderStatus.HKING.getCode());
        if (orderLog != null) {
          res.setFeeAwithdrawDate(sf.format(orderLog.getCreateTime()));
        }
      }
    }
    if (newOrder != null && newOrder.getContractCode() != null) {
      res.setContractUrl(this.getContractUrlByContractNo(newOrder.getContractCode()));
    }
    return res;
  }

  private Integer createIsPay4Manager(GedOrder order, GedRechargeRecord rechargeRecord) {
    if (rechargeRecord == null) {
      if (order.getServiceFeeFlag() == 1) {
        return 3;
      } else {
        return order.getServiceFeeFlag();
      }
    } else {
      return rechargeRecord.getStatus();
    }
  }
  /*
   * private Integer createIsPay(GedOrder order, GedRechargeRecord rechargeRecord) { // if
   * (order.getServiceFeeFlag() == null || order.getServiceFeeFlag() == 0) // return 0; if
   * (order.getServiceFeeFlag() != null && order.getServiceFeeFlag() == 1) return 1;
   * 
   * if (rechargeRecord == null) return 0; else return rechargeRecord.getStatus(); }
   */

  private int isWithdraw(GedOrder order) {
    if (order.getWithdrawFlag() == null || order.getWithdrawFlag() == 2
        || order.getWithdrawFlag() == 5) {
      return 1;
    }
    return 0;
  }

  private void copyUtil(OrderFeesDetailReqForm res, OrderViewsReqForm re) {
    res.setStatus(re.getStatus());
    res.setOrderCode(re.getOrderCode());
    res.setCompanyBankOfDeposit(re.getCompanyBankOfDeposit());
    res.setReplyAmount(re.getLoanAmount());
    res.setCompanyName(re.getCompanyName());
    res.setLoanTerm(re.getLoanTerm());
    res.setCompanyAccount(re.getCompanyAccount());
    res.setIsOpenAccount(re.getIsOpenAccount());
    // res.setServiceFee(re.getServiceFee());
    // res.setGuaranteeFee(re.getGuaranteeFee());
    // res.setCashDeposit(re.getCashDeposit());
    // res.setAllPayAmount(re.getAllPayAmount());
    // res.setLendingDate(re.getLendingDate());
    // res.setLendingAmount(re.getLendingAmount());
    // res.setMoneyAccountBalance(re.getMoneyAccountBalance());
    // res.setUsefullBalance(re.getUsefullBalance());
  }

  @Override
  public OrderFeesDetailReqForm payFeeInfos(Long userId) throws Exception {
    OrderFeesDetailReqForm o = new OrderFeesDetailReqForm();
    // 1 获取该用户的最新订单
    GedOrder order = this.getNewOrderByUserId(userId);
    if (order == null) {
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR);
    }

    // 2 获取用户的账户信息
    Account account = accountService.getAccountByUserId(userId);
    if (account == null) {
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR, "payFeeInfos接口查询用户账户失败");
    }


    // 第一版面
    o.setOrderCode(order.getOrderCode());
    SystemDictionaryItem systemDictionaryItem =
        dictionaryService.getBankInfo(account.getCompanyBankOfDeposit());
    o.setCompanyBankOfDeposit(systemDictionaryItem.getValue());
    o.setReplyAmount(order.getReplyAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    o.setCompanyName(account.getCorporationName());
    o.setLoanTerm(order.getLoanTerm() + "");
    o.setCompanyAccount(account.getCompanyAccount());
    o.setStatus(OrderStatus.resove(order.getStatus()).getBackStatusDesc());

    // 第二版面
    o.setServiceFee(order.getServiceFee() == null ? "0.00"
        : order.getServiceFee().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    // o.setGuaranteeFee(order.getGuaranteeFee() == null ? "0.00"
    // : order.getGuaranteeFee().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    // o.setCashDeposit(order.getCashDeposit() == null ? "0.00"
    // : order.getCashDeposit().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");


    if (order.getServiceFeeFlag() == null || order.getServiceFeeFlag() == 0) {
      o.setAllPayAmount(
          // order.getServiceFee().add(order.getGuaranteeFee().add(order.getCashDeposit())).setScale(2,BigDecimal.ROUND_HALF_DOWN)
          // + "");
          order.getServiceFee().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    }
    // 如果已支付服务费则 应支付总金额=0
    else {
      o.setAllPayAmount("0.00");
    }

    // 第三版面
    OrderLog orderLog =
        orderLogService.getOrderLogByOrderId(order.getId(), OrderStatus.FKCG.getCode());
    if (orderLog == null) {
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR, "资金管理-费用支付 订单日志查询失败");
    }

    BigDecimal result = this.accountBalance(order.getOrderCode(), userId);
    o.setLendingAmount(order.getReplyAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    o.setLendingDate(orderLog.getCreateTime());
    o.setMoneyAccountBalance(
        result == null ? "0.00" : result.setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    // 如果已支付就可以全额提现 否则 60%
    // if (order.getServiceFeeFlag() == null || order.getServiceFeeFlag() == 0)// wei支付
    // o.setUsefullBalance(order.getReplyAmount().multiply(new BigDecimal(0.6)).setScale(2,
    // BigDecimal.ROUND_HALF_DOWN) + "");
    // else//
    // o.setUsefullBalance(order.getReplyAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN) +
    // "");withDrawAmount(String orderNo)
    o.setUsefullBalance(
        withDrawAmount(order.getOrderCode()).setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    // 第一版面的提示信息
    o.setAlertInfo(getDateAfterString(orderLog.getCreateTime(), 5));
    return o;
  }

  private static Date getDateAfter(Date d, int day) {
    Calendar now = Calendar.getInstance();
    now.setTime(d);
    now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
    return now.getTime();
  }

  private static String getDateAfterString(Date d, int day) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
    return sdf.format(getDateAfter(d, day));
  }

  @Override
  public OrderViewsReqForm orderView(Long userId) {
    OrderViewsReqForm o = new OrderViewsReqForm();
    // 1 获取该用户的最新订单
    GedOrder order = this.getNewOrderByUserId(userId);
    if (order == null) {
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR);
    }
    o.setIsOpenAccount("0");
    o.setCompanyBankOfDeposit("");
    o.setCompanyName("");
    o.setCompanyAccount("");
    // 2 获取用户的账户信息
    Account account = accountService.getAccountByUserId(userId);
    if (account != null) {
      SystemDictionaryItem systemDictionaryItem =
          dictionaryService.getBankInfo(account.getCompanyBankOfDeposit());
      o.setCompanyBankOfDeposit(systemDictionaryItem.getValue());
      o.setCompanyName(account.getCorporationName());
      o.setCompanyAccount(account.getCompanyAccount());
      o.setIsOpenAccount("1");
    }

    o.setOrderCode(order.getOrderCode());
    o.setLoanTerm(order.getLoanTerm() + "");
    o.setStatus(OrderStatus.resove(order.getStatus()).getBackStatusDesc());
    // 如果订单状态 status < 139 或者status >= 200 LoanAmount为申请金额 139 《= status = 200 为批复金额
    if (139 <= order.getStatus() && order.getStatus() < 200) {
      o.setLoanAmount(order.getReplyAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    } else {
      o.setLoanAmount(order.getLoanAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    }
    return o;
  }

  @Override
  public RepayPlanResForm repaymentPlan(Long userId) throws Exception {
    RepayPlanResForm res = new RepayPlanResForm();
    // 获取订单
    GedOrder order = this.getNewOrderByUserId(userId);
    if (order == null) {
      res.setIsCanRepay("-1");
      return res;
    }
    // throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR, "还款计划查询订单失败 userID=" + userId);

    res.setOrderNo(order.getOrderCode());
    res.setLoanAmount(order.getReplyAmount() == null ? ""
        : (order.getReplyAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN) + ""));
    res.setLoanTerm(order.getLoanTerm() == null ? "" : (order.getLoanTerm() + ""));
    res.setStatus(OrderStatus.resove(order.getStatus()).getBackStatusDesc());

    if (order.getStatus() < OrderStatus.FKCG.getCode()) {
      res.setIsCanRepay("0");
      return res;
    }

    // 获取账户信息
    Account account = accountService.getAccountByUserId(userId);
    if (account == null) {
      res.setIsCanRepay("0");
      return res;
    }

    // throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR, "还款计划查询账户信息失败 userId=" + userId);
    SystemDictionaryItem systemDictionaryItem =
        dictionaryService.getBankInfo(account.getCompanyBankOfDeposit());
    res.setCompanyBankOfDeposit(
        systemDictionaryItem == null ? "" : String.valueOf(systemDictionaryItem.getValue()));
    res.setCorporationName(account.getCorporationName());
    res.setCompanyAccount(account.getCompanyAccount());

    // 获取每期还款计划
    Map<String, String> param = new HashMap<>();
    param.put("dateFlag", "");
    param.put("repayPeriodStatus", "");
    param.put("orderNo", order.getOrderCode());
    param.put("jointCredit", JointCreditFlag.NOT_JOINTCREDIT.getCode() + "");

    Map<String, Object> map = this.LoanSystem(param);
    List<DeductResult> list = (List<DeductResult>) map.get("planList");
    res.setIsPay("0");
    // 未还总数
    Integer count = 0;
    Integer noRepayCount = 0; // 未还记录数
    Integer overdueNum = -1; // 逾期记录位置
    Integer nomalNum = -1; // 正常还款记录位置
    BigDecimal ovdueSumAmount = new BigDecimal(0);
    BigDecimal dueAmount = new BigDecimal(0);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    if (list != null && list.size() > 0) {
      for (DeductResult deductResult : list) {
        if ("0200".equals(deductResult.getRepayPeriodStatus())
            || "0300".equals(deductResult.getRepayPeriodStatus())) {
          dueAmount = dueAmount.add(new BigDecimal(deductResult.getRepayAmount()));
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
          ++noRepayCount;
          if ("0300".equals(deductResult.getRepayPeriodStatus())) {
            // 计算逾期所有期数应还总金额
            ovdueSumAmount = ovdueSumAmount.add(new BigDecimal(deductResult.getRepayAmount()));
          }
        }
        ++count;
      }

      if ("0200".equals(list.get(list.size() - 1).getRepayPeriodStatus())
          || "0300".equals(list.get(list.size() - 1).getRepayPeriodStatus())) {
        res.setIsPay("1");
      }
    }

    // 计算下一期还款是逾期还是正常还款
    DeductResult deductResult = null;
    Integer thisPerisNum = -1;
    /*
     * if (overdueNum >= 0) { // thisPerisNum = overdueNum; deductResult = list.get(overdueNum); }
     * else
     */
    if (nomalNum >= 0) {
      thisPerisNum = nomalNum;
      deductResult = list.get(nomalNum);
    }

    int num = order.getReplyTerm() == null ? 0 : order.getReplyTerm() - count;
    // 已还期数
    res.setRepaymentPeriod(
        count - noRepayCount + "/" + (order.getReplyTerm() == null ? 0 : order.getReplyTerm()));
    if (deductResult != null) {
      // 应还日期
      res.setDueDate(deductResult == null ? null : deductResult.getDeductDate());
      // 应还金额 = 应还金额 - 实金额 + 逾期应还总金额ovdueSumAmount
      res.setDueAmount((new BigDecimal(deductResult.getRepayAmount()).add(ovdueSumAmount)
          .subtract(calcualterFactPayAmount(deductResult))).setScale(2, BigDecimal.ROUND_HALF_DOWN)
          + "");

      res.setRemainDueAmount(dueAmount.setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    } else if (deductResult == null && overdueNum > -1) {
      // 应还日期
      DeductResult last = list.get(list.size() - 1);
      res.setDueDate(last == null ? null : last.getDeductDate());
      // 应还金额 = 应还金额 - 实金额 + 逾期应还总金额ovdueSumAmount
      res.setDueAmount(((ovdueSumAmount).subtract(calcualterFactPayAmount(deductResult)))
          .setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    }

    // 将应还金额保存到redis
    RBucket rBucket = redissonClient.getBucket(Constant.PAYMENT + order.getOrderCode());
    rBucket.set(res.getDueAmount());

    res.setRepayWay((String) map.get("loanType"));

    // res.setRepayWay();//还款方式
    res.setDueRepayTotal(res.getDueAmount()); // 待还款总额
    OrderLog orLog =
        orderLogService.getOrderLogByOrderId(order.getId(), OrderStatus.FKCG.getCode());
    if (orLog != null) {
      res.setLoanDay(simpleDateFormat.format(orLog.getCreateTime()));// 放款日
    }

    BigDecimal factSum = new BigDecimal(0);

    List<RepaymentItem> datas = new ArrayList<>();
    // 计算还款计划列表
    for (DeductResult deduct : list) {
      RepaymentItem repaymentItem = new RepaymentItem();
      repayCopyItem(deduct, repaymentItem);
      datas.add(repaymentItem);
      factSum = factSum.add(new BigDecimal(repaymentItem.getFactAmount()));
    }

    if (thisPerisNum != -1) {
      // 判断当期 是否到达还款日期
      if (thisPerisNum > 0 && !"0200".equals(list.get(thisPerisNum - 1).getRepayPeriodStatus())
      // && !list.get(thisPerisNum - 1).getRepayPeriodStatus().equals("0300")
          && simpleDateFormat.parse(list.get(thisPerisNum - 1).getDeductDate())
              .getTime() >= simpleDateFormat
                  .parse(simpleDateFormat.format(new Date(System.currentTimeMillis()))).getTime())
      // && simpleDateFormat.parse(list.get(thisPerisNum).getDeductDate()).getTime() >
      // System.currentTimeMillis())
      {
        // res.setDueAmount((new BigDecimal(list.get(thisPerisNum -
        // 1).getRepayAmount()).add(ovdueSumAmount)
        // .subtract(calcualterFactPayAmount(list.get(thisPerisNum - 1)))).setScale(2,
        // BigDecimal.ROUND_HALF_DOWN)
        // + "");
        res.setDueAmount("0.00");
      } else {
        datas.get(thisPerisNum).setRepayPeriodStatus("待还款");
      }
    }

    res.setActualRepayTotal(factSum.setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");// 实还总额
    res.setRepaymentPlanList(datas);
    return res;
  }

  private void repayCopyItem(DeductResult deduct, RepaymentItem repaymentItem) {
    repaymentItem.setDeductDate(deduct.getDeductDate());
    repaymentItem.setRepayPeriodStatus(getStatusInfo(deduct.getRepayPeriodStatus()));
    repaymentItem.setPeriodNum(deduct.getPeriodNum());
    repaymentItem.setRepayAmount(deduct.getRepayAmount());
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

  @Override
  public Integer saveRepaymentAfterPay(ParasAfterPayReqForm res) throws Exception {
    // 第三方支付成功
    if (res != null && res.getResp_code() != null
        && Constant.RES_CODE_SUCCESS.equals(res.getResp_code().trim())) {
      GedRechargeRecord rechargeRecord = new GedRechargeRecord();
      rechargeRecord.setStatus(1);
      // rechargeRecord.setCreateTime(new Date());
      // rechargeRecord.setCustNo();
      rechargeRecord.setMobileNo(res.getLogin_id());
      // rechargeRecord.setOrderNo(res.getMchnt_txn_ssn());
      // rechargeRecord.setRechargeAmount(new BigDecimal(res.getAmt()));
      rechargeRecord.setRespCode(res.getResp_code());
      // rechargeRecord.setSeqNo();
      rechargeRecord.setTradeDate(new Date());
      rechargeRecord.setType(RechargeType.REPAYMENT.getKey());// 还款充值

      GedRechargeRecord record =
          rechargeRecordService.getRechargeRecordByOrderNo(res.getMchnt_txn_ssn());
      if (record == null) {
        return rechargeRecordService.insert(rechargeRecord);
      }
      rechargeRecord.setId(record.getId());
      return rechargeRecordService.updateById(rechargeRecord);
    } else {
      // 第三方支付失败
    }

    Repayment repayment =
        (Repayment) redissonClient.getBucket(Constant.REPAYMENT_CONTRACTNO_PERIOD).get();
    Map<String, String> params = new HashMap<String, String>();
    params.put("contractNo", repayment.getContractNo());
    params.put("period", String.valueOf(repayment.getPeriod()));
    logger.info("用户充值成功后，还款通知 冠e通  请求参数为：" + JSON.toJSONString(params));
    String jsonObject =
        HttpUtils.doPostForm(borrowUrl + "/funds/service/repaymentWithholding", params);
    logger.info("用户充值成功后，还款通知 冠e通  返回信息为：" + jsonObject);

    return 0;
  }

  @Override
  public Integer updateServiceFlagByOrderNo(RpdateServiceFlagByOrderNoReqForm req) {
    String orderNo = req.getOrderNo();
    GedOrder order = this.selectOrderByOrderNo(orderNo);
    if (order == null) {
      throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "资产端回调接口查询订单失败 orderNo=" + orderNo);
    }
    GedOrder updateOrder = new GedOrder();
    updateOrder.setId(order.getId());
    // updateOrder.setServiceFeeFlag(1);
    updateOrder.setGuaranteeFeeFlag(1);
    if (StringUtils.isNotBlank(req.getFactGuaranteeGold())) {
      updateOrder.setCashDeposit(new BigDecimal(req.getFactGuaranteeGold()));// 保证金
    }
    if (StringUtils.isNotBlank(req.getFactGuaranteeFee())) {
      updateOrder.setGuaranteeFee(new BigDecimal(req.getFactGuaranteeFee()));// 担保费
    }
    if (StringUtils.isNotBlank(req.getFactServiceFee())
        && !req.getFactServiceFee().equals("0.00")) {
      updateOrder.setServiceFee(new BigDecimal(req.getFactServiceFee()));// 服务费
      updateOrder.setServiceFeeFlag(1);// 服务费表示
    }
    logger.info(JSONObject.toJSONString(req)
        + "===================================借款端回调==============================orderNo="
        + orderNo);
    return gedOrderMapper.updateByPrimaryKeySelective(updateOrder);
  }

  @Override
  public Integer pushToBrrow(String orderNo, Integer sysFlag, String contractCode)
      throws Exception {
    Map<String, String> map = new HashedMap();
    map.put("applyNo", orderNo); //
    map.put("flag", sysFlag + ""); //
    map.put("contractNo", contractCode); //
    JSONObject jsonObject =
        HttpUtils.doPost(borrowUrl + "/api/serviceFee", JSONObject.toJSONString(map));
    logger.info("借款系统serviceFee接口返结果:" + jsonObject);
    return null;
  }

  @Override
  public CheckServiceFeeResForm CheckServiceFeeStatus(Long userId) {
    CheckServiceFeeResForm resForm = new CheckServiceFeeResForm();
    GedOrder newOrder = this.getNewOrderByUserId(userId);
    GedRechargeRecord rechargeRecord =
        rechargeRecordService.getRechargeRecordByOrderNo(newOrder.getOrderCode());
    resForm.setStatus(createIsPay4Manager(newOrder, rechargeRecord) + "");
    resForm.setOrderNo(newOrder.getOrderCode());
    resForm.setAmt(newOrder.getServiceFee().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    return resForm;
  }

  @Override
  public void paying(Long userId, String orderNo) {

    GedOrder order = this.selectOrderByOrderNo(orderNo);

    GedRechargeRecord rechargeRecord = new GedRechargeRecord();
    // rechargeRecord.setSeqNo(res.getMchnt_txn_ssn().substring(0,res.getMchnt_txn_ssn().length()-9));
    // rechargeRecord.setCustNo(user.getGetCustId());
    // rechargeRecord.setMobileNo(res.getLogin_id());
    // rechargeRecord.setOrderNo(res.getMchnt_txn_ssn());
    // rechargeRecord.setRechargeAmount(order.getServiceFee());

    // rechargeRecord.setRespCode(res.getResp_code());
    rechargeRecord.setSeqNo(orderNo);

    rechargeRecord.setType(1);
    // rechargeRecord.setStatus(5);
    rechargeRecord.setTradeDate(new Date());
    GedRechargeRecord records =
        rechargeRecordService.getRechargeRecordByOrderNo(rechargeRecord.getSeqNo());
    if (records == null) {
      rechargeRecord.setCreateTime(new Date());
      rechargeRecordService.insert(rechargeRecord);
    } else {
      rechargeRecord.setModifyTime(new Date());
      rechargeRecord.setId(records.getId());
      rechargeRecordService.updateById(rechargeRecord);
    }

  }

  @Override
  public PushToBorrowRepaymentResForm pushToBorrowRepayment(String ssn) throws Exception {
    logger.info("调用借款端进行还款beigin");
    GedRepaymentRecord record = repaymentRecordService.getGedRepaymentRecordByZjStreamNo(ssn);
    if (record == null) {
      throw new BusinessException(Errors.SYSTEM_DATA_ERROR,
          "缺少还款支付成功记录 orderNo=" + record.getSeqNo());
    }


    GedOrder order = this.selectOrderByOrderNo(record.getSeqNo());
    if (order == null) {
      throw new BusinessException(Errors.SYSTEM_DATA_ERROR,
          "还款支付成功后调借款端扣款时查询订单失败 orderNo=" + record.getSeqNo());
    }
    User user = userService.getUserById(order.getUserId());
    if (user == null) {
      throw new BusinessException(Errors.SYSTEM_DATA_ERROR,
          "还款支付成功后调借款端扣款时查询账户失败 userID=" + order.getUserId());
    }

    Account account = accountService.getAccountByUserId(order.getUserId());
    if (account == null) {
      throw new BusinessException(Errors.SYSTEM_DATA_ERROR,
          "还款支付成功后调借款端扣款时查询账户失败 userID=" + order.getUserId());
    }
    PushToBorrowRepaymentReqForm reqForm = new PushToBorrowRepaymentReqForm();
    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    Map<String, String> map = new HashedMap();
    if (record.getType() != null && record.getType() == 2) // 提前结清
      reqForm.setAdvanceFlag("1");
    reqForm.setCustId(user.getGetCustId());// 客户id在冠E通存在的账户id
    reqForm.setContractNo(order.getContractCode());//// 合同号
    reqForm.setPeriodNum(order.getReplyTerm() + "");/// 期数
    reqForm.setDeductCustName(user.getUsername()); // 扣款人Name 扣款人姓名
    // reqForm.setDeductApplyNo(record.getSeqNo() + sf.format(new Date())); // 扣款申请序列号 （你们产生）
    reqForm.setDeductApplyNo("gedapi" + sf.format(new Date())); // 扣款申请序列号 （你们产生）
    reqForm.setMobileNum(account.getCorporationPhone());// 手机号 扣款人
    reqForm.setBankcardNo(account.getCompanyAccount()); // 银行卡号 扣款人
    reqForm.setOrderNo(record.getOrderNo());
    // reqForm.setDeductAmount(accountBalance(order.getOrderCode(),order.getUserId()).setScale(2,
    // BigDecimal.ROUND_HALF_DOWN) + ""); //账户余额
    reqForm
        .setDeductAmount(record.getRepaymentAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN) + ""); // 充值金额
    // 根据字典表查询出银行
    SystemDictionaryItem item = null;
    if (account.getCompanyBankOfDeposit() != null) {
      item = dictionaryService.getDictionaryByParam("BANK_TYPE", account.getCompanyBankOfDeposit());
    }
    reqForm.setBank(item == null ? "" : item.getValue()); // 开户银行（字典类型：BANKS） 扣款人

    repay(reqForm);
    logger.info("---------------------------调用借款端进行还款end---------------------------------------");

    GedRepaymentRecord recorde = new GedRepaymentRecord();
    recorde.setOrderNo(ssn);
    recorde.setDeductApplyNo(reqForm.getDeductApplyNo()); // 扣款申请序列号 （你们产生）

    repaymentRecordService.insertOrUpdate(recorde);

    return null;
  }

  @Override
  public void pushCreateOrder(GedOrder gedOrder) {
    gedOrderMapper.insertSelective(gedOrder);
  }

  @Override
  public void updateOrderByContractNo(String contractNo) {
    gedOrderMapper.updateOrderByContractNo(contractNo);
  }

  @Override
  public RepaymentAllResForm getRepaymentAllInfo(String orderNo) throws Exception {

    GedOrder order = this.selectOrderByOrderNo(orderNo);
    if (order == null || order.getContractCode() == null) {
      throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "获取提前还款信息的订单信息缺失 orderNo=" + orderNo);
    }
    JSONObject res = this.repaymentAllInfo(order.getContractCode());
    RepaymentAllResForm resForm = this.repaymentAllInfoResForm(res);
    resForm.setIsCanRepay(
        order.getRepaymentLockFlag() == null ? "1" : order.getRepaymentLockFlag() + "");
    return resForm;
  }

  // 调用借款系统获取提现还款的信息
  private JSONObject repaymentAllInfo(String contractNo) throws Exception {
    Map<String, Object> map = new HashedMap();
    map.put("contractNo", contractNo);
    logger.info("（1）repaymentAllInfo 调用借款系统 输入的合同编号为：" + contractNo);
    JSONObject jsonObject =
        HttpUtils.doPost(borrowUrl + "/api/advanceRepayDetail", JSONObject.toJSONString(map));
    logger.info("（2）repaymentAllInfo 调用借款系统获取结果为：" + jsonObject);
    return (jsonObject);
  }

  private RepaymentAllResForm repaymentAllInfoResForm(JSONObject json) {
    if (json != null && Constant.RES_CODE_SUCCESS.equals(json.get("code"))) {
      // String msg = (String) json.getJSONObject("repayPlanInfo").getString("data");
      String msg = (String) json.getString("data");
      RepaymentAllResForm data = JSONObject.parseObject(msg, RepaymentAllResForm.class);
      return data;
    }
    throw new BusinessException(Errors.NOTE_UNCOMMIT_ERROR, "错误原因 ：" + json);
  }

  @Override
  public List<OrderGroupInfo> selectJoinOrderList(Long userId) {
    List<OrderGroupInfo> datas = gedOrderMapper.selectJoinOrderList(userId);
    if (datas != null && datas.size() > 0) {
      for (OrderGroupInfo g : datas) {
        if (g.getCompanyCount() != null && g.getCompanyCount() > 1)
          g.setCompanyCount(g.getCompanyCount() - 1);
      }
    }
    return datas;
  }

  @Override
  public JSONObject repay(PushToBorrowRepaymentReqForm paraForm) throws Exception {
    Map<String, Object> map = new HashedMap();
    map.put("custId", paraForm.getCustId()); //// 客户id在冠E通存在的账户id
    map.put("contractNo", paraForm.getContractNo()); //// 合同号
    // map.put("periodNum", paraForm.getPeriodNum()); ///期数
    map.put("deductCustName", paraForm.getDeductCustName()); // 扣款人Name 扣款人姓名 ---企业名称
    map.put("deductApplyNo", paraForm.getDeductApplyNo()); // 扣款申请序列号 （你们产生）20180612123456
    map.put("mobileNum", paraForm.getMobileNum()); // 手机号 扣款人
    map.put("bankcardNo", paraForm.getBankcardNo()); // 银行卡号 扣款人
    map.put("bank", paraForm.getBank()); // 开户银行（字典类型：BANKS） 扣款人
    map.put("deductAmount",
        new BigDecimal(paraForm.getDeductAmount()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
    map.put("accountNo", paraForm.getOrderNo());
    map.put("advanceRepayMoney", paraForm.getAdvanceRepayMoney());
    map.put("advanceFlag", paraForm.getAdvanceFlag());
    if (paraForm.getAdvanceRepayMoney() == null)
      logger
          .info("---------------------------调用借款端进行还款的输入参数--------------------------------------");
    else
      logger.info(
          "---------------------------【提前结清】调用借款端进行还款的输入参数--------------------------------------");
    logger.info(JSONObject.toJSONString(map));

    // JSONObject jsonObject = HttpUtils.doPost(repaymentBorrowUrl, JSONObject.toJSONString(map));
    HttpUtils.doPost(borrowUrl + "/api/accAccount", JSONObject.toJSONString(map));
    // logger.info("调get费用接口返结果:" + jsonObject);
    return null;
  }

  @Override
  public Integer getRepayStatus(Long userId) {
    GedOrder order = this.getNewOrderByUserId(userId);
    /*
     * RBucket rBucket = redissonClient.getBucket(Constant.CONTRACT_NO + ":" +
     * order.getContractCode()); if (rBucket.isExists()) { return (int) rBucket.get(); }else {
     * return 1; }
     */
    return order.getRepaymentLockFlag() == null ? 1 : order.getRepaymentLockFlag();
  }

  @Override
  public void updateFinishRepay(String orderNo) {
    GedOrder order = this.selectOrderByOrderNo(orderNo);
    if (order != null) {
      GedOrder updater = new GedOrder();
      updater.setId(order.getId());
      updater.setRepaymentLockFlag(1);
      logger.info(
          "========================================借款系统还款完成回调=====================================orderNo"
              + orderNo);
      this.gedOrderMapper.updateByPrimaryKeySelective(updater);
    }
  }


  public void saveRechargeRecord(GedOrder order, String username, String order_no, String amt) {

    GedRechargeRecord rechargeRecord = new GedRechargeRecord();
    rechargeRecord.setStatus(1);
    rechargeRecord.setSeqNo(order.getOrderCode());
    // rechargeRecord.setCreateTime(new Date());
    // rechargeRecord.setCustNo();
    rechargeRecord.setMobileNo(username);
    // rechargeRecord.setOrderNo(order_no);
    // rechargeRecord.setRechargeAmount(new BigDecimal(amt));
    rechargeRecord.setRespCode(Constant.RES_CODE_SUCCESS);
    rechargeRecord.setTradeDate(new Date());
    rechargeRecord.setType(RechargeType.REPAYMENT.getKey());// 还款充值

    GedRechargeRecord record =
        rechargeRecordService.getRechargeRecordByOrderNo(order.getOrderCode());
    if (record == null) {
      rechargeRecordService.insert(rechargeRecord);
    } else {
      rechargeRecord.setId(record.getId());
      rechargeRecordService.updateById(rechargeRecord);
    }
  }

}
