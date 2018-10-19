package com.gq.ged.account.service.impl;

import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gq.ged.account.controller.req.v2.*;
import com.gq.ged.account.controller.res.AccountResForm;
import com.gq.ged.account.controller.res.v2.*;
import com.gq.ged.account.dao.mapper.RsaKeyMapper;
import com.gq.ged.account.dao.model.*;
import com.gq.ged.account.service.AccountOpenService;
import com.gq.ged.common.enums.*;
import com.gq.ged.common.utils.RSAUtils;
import com.gq.ged.common.utils.copy.BeanCopyTools;
import com.gq.ged.order.controller.res.OrderDetailResForm;
import com.gq.ged.order.controller.res.RepayPlanTotleResForm;
import com.gq.ged.order.controller.res.WithDrawResForm;
import com.gq.ged.order.dao.mapper.GedOrderMapper;
import com.gq.ged.order.dao.mapper.GedRechargeRecordMapper;
import com.gq.ged.order.dao.mapper.GedRepaymentRecordMapper;
import com.gq.ged.order.dao.mapper.UserRechargeRecordMapper;
import com.gq.ged.order.dao.model.*;
import com.gq.ged.order.pc.req.PushToBorrowRepaymentReqForm;
import com.gq.ged.order.pc.req.RechargeCallBackForm;
import com.gq.ged.order.service.*;
import com.gq.ged.user.dao.mapper.UserMapper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.logging.log4j.util.Strings;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gq.ged.account.dao.mapper.AccountCompanyMapper;
import com.gq.ged.account.dao.mapper.AccountMapper;
import com.gq.ged.activemq.service.JmsProvider;
import com.gq.ged.common.Constant;
import com.gq.ged.common.Errors;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.common.http.HttpUtils;
import com.gq.ged.common.utils.date.DateFormatUtils;
import com.gq.ged.dictionary.service.DictionaryService;
import com.gq.ged.dictionary.service.FuyouAreaService;
import com.gq.ged.file.service.SystemFileService;
import com.gq.ged.user.dao.model.User;
import com.gq.ged.user.service.UserService;

/**
 * Created by zhaozb on 2018/3/19.
 */
@Service
public class AccountOpenServiceImpl implements AccountOpenService {

  static Logger logger = LoggerFactory.getLogger(AccountOpenServiceImpl.class);

  @Resource
  AccountMapper accountMapper;

  @Resource
  AccountCompanyMapper companyMapper;
  @Resource
  GedRechargeRecordMapper rechargeRecordMapper;
  @Resource
  UserRechargeRecordMapper userRechargeRecordMapper;
  @Resource
  GedOrderMapper gedOrderMapper;
  @Resource
  RechargeRecordService rechargeRecordService;
  @Resource
  RepaymentRecordService repaymentRecordService;

  @Resource
  RsaKeyMapper rsaKeyMapper;

  @Resource
  UserService userService;
  @Resource
  OrderNewService orderService;
  @Resource
  OrderBorrowerNewService orderBorrowerNewService;
  @Resource
  OrderLogService orderLogService;
  @Resource
  JmsProvider jmsProvider;

  @Resource
  SystemFileService systemFileService;

  @Resource
  FuyouAreaService fuyouAreaService;

  @Resource
  DictionaryService dictionaryService;
  @Resource
  AccountOpenService accountService;
  @Resource
  UserMapper userMapper;
  @Resource
  GedRepaymentRecordMapper gedRepaymentRecordMapper;

  @Value("${gq.ged.call.v2.loansys.account.url}")
  String loanCreateAccountUrl;

  @Value("${gq.ged.call.v2.loansys.accountCompany.url}")
  String loanCreateAccountCompanyUrl;

  @Value("${gq.ged.call.v2.accont.personal.url}")
  String personalUrl;
  @Value("${gq.ged.call.v2.accont.personal.pc.url}")
  String personalPCUrl;
  @Value("${gq.ged.call.v2.accont.personal.page.url}")
  String personalPageUrl;
  @Value("${gq.ged.call.v2.accont.personal.pc.page.url}")
  String personalPCPageUrl;
  @Value("${gq.ged.call.v2.accont.personal.callback.url}")
  String personalCallbackUrl;
  @Value("${gq.ged.call.v2.accont.personal.tradeType}")
  String personalTradeType;
  @Value("${gq.ged.call.v2.accont.personal.pc.tradeType}")
  String personalPCTradeType;
  @Value("${gq.ged.call.v2.accont.enterprise.url}")
  String enterpriseUrl;
  @Value("${gq.ged.call.v2.accont.enterprise.pc.url}")
  String enterprisePCUrl;
  @Value("${gq.ged.call.v2.accont.enterprise.page.url}")
  String enterprisePageUrl;
  @Value("${gq.ged.call.v2.accont.enterprise.pc.page.url}")
  String enterprisePCPageUrl;
  @Value("${gq.ged.call.v2.accont.enterprise.modify.url}")
  String enterpriseModifyUrl;
  @Value("${gq.ged.call.v2.accont.enterprise.modify.pc.url}")
  String enterpriseModifyPCUrl;

  @Value("${gq.ged.call.v2.accont.enterprise.callback.url}")
  String enterpriseCallbackUrl;
  @Value("${gq.ged.call.v2.accont.enterprise.tradeType}")
  String enterpriseTradeType;
  @Value("${gq.ged.call.v2.recharge.url}")
  String rechargeUrl;
  @Value("${gq.ged.call.v2.recharge.page.url}")
  String rechargePageUrl;
  @Value("${gq.ged.call.v2.recharge.callback.url}")
  String rechargeCallbackUrl;
  @Value("${gq.ged.call.v2.recharge.tradeType}")
  String rechargeTradeType;
  @Value("${gq.ged.call.v2.activateUser.url}")
  String activateUserUrl;
  @Value("${gq.ged.call.v2.activateUser.page.url}")
  String activateUserPageUrl;
  @Value("${gq.ged.call.v2.activateUser.callback.url}")
  String activateUserCallbackUrl;
  @Value("${gq.ged.call.v2.activateUser.tradeType}")
  String activateUserTradeType;
  @Value("${gq.ged.call.v2.charges.url}")
  String chargesUrl;
  @Value("${gq.ged.call.v2.charges.page.url}")
  String chargesPageUrl;
  @Value("${gq.ged.call.v2.charges.recharge.callback.url}")
  String chargesRechargeCallbackUrl;
  @Value("${gq.ged.call.v2.charges.callback.url}")
  String chargesCallbackUrl;
  @Value("${gq.ged.call.v2.charges.tradeType}")
  String chargesTradeType;
  @Value("${gq.ged.call.v2.charges.loanPlatform}")
  String chargesLoanPlatform;
  @Value("${gq.ged.call.v2.charges.feeType}")
  String chargesFeeType;
  @Value("${gq.ged.call.v2.charges.custType}")
  String chargesCustType;

  @Value("${gq.ged.call.v2.verifyDeduct.tradeType}")
  String verifyDeductTradeType;
  @Value("${gq.ged.call.v2.verifyDeduct.url}")
  String verifyDeductUrl;
  @Value("${gq.ged.call.v2.verifyDeduct.page.url}")
  String verifyDeductPageUrl;
  @Value("${gq.ged.call.v2.verifyDeduct.callback.url}")
  String verifyDeductCallBackUrl;
  @Value("${serviceFee.amount}")
  Double serviceFeeAmount;


  @Value("${gq.ged.call.v2.withdraw.url}")
  String withDrawUrl;
  @Value("${gq.ged.call.v2.withdraw.page.url}")
  String withDrawPageUrl;
  @Value("${gq.ged.call.v2.withdraw.page.pc.url}")
  String withDrawPCPageUrl;
  @Value("${gq.ged.call.v2.withdraw.callback.url}")
  String withdrawCallBackUrl;
  @Value("${gq.ged.call.v2.withdraw.tradeType}")
  String withdrawTradeType;


  // 银行卡变更 begin
  @Value("${gq.ged.call.v2.bankCardChange.url}")
  String bankCardChangeUrl;
  @Value("${gq.ged.call.v2.bankCardChange.page.url}")
  String bankCardChangePageUrl;
  @Value("${gq.ged.call.v2.bankCardChange.callback.url}")
  String bankCardChangeCallBackUrl;
  @Value("${gq.ged.call.v2.bankCardChange.tradeType}")
  String bankCardChangeTradeType;
  // end

  @Value("${gq.ged.call.capital.account.tradetype.value}")
  String tradeType;
  @Value("${gq.ged.get.mchn}")
  String mchn;

  @Value("${gq.ged.call.v2.estabAccountCallback}")
  String estabAccountCallbackUrl;
  @Value("${gq.ged.call.v2.userActivationCallback}")
  String userActivationCallbackUrl;
  @Value("${gq.ged.call.v2.registerUser}")
  String registerUserUrl;


  @Value("${gq.ged.call.v2.queryAccBalance}")
  String accountBalanceUrl;
  @Value("${gq.ged.call.v2.queryAccBalance.tradeType}")
  String accountBalanceTradeType;
  @Value("${gq.ged.call.v2.repayment.borrow.url}")
  String repaymentBorrowUrl;
  @Value("${gq.ged.gedUpdateOrderStatus.url}")
  String gedUpdateOrderUrl;
  @Value("${gq.ged.firstWithDeposit.url}")
  String firstWithDepositUrl;

  @Resource
  RedissonClient redissonClient;

  @Override
  public AccountCompany queryCompanyAccountBySocialCreditCode(String socialCreditCode) {
    AccountCompanyExample example = new AccountCompanyExample();
    example.createCriteria().andSocialCreditCodeEqualTo(socialCreditCode);
    List<AccountCompany> list = companyMapper.selectByExample(example);
    if (list.size() > 0) {
      return list.get(0);
    } else {
      return null;
    }
  }

  @Override
  public AccountCompany queryCompanyAccountByUserId(Long userId) {
    AccountCompanyExample example = new AccountCompanyExample();
    example.createCriteria().andUserIdEqualTo(userId);
    List<AccountCompany> list = companyMapper.selectByExample(example);
    if (list.size() > 0) {
      return list.get(0);
    } else {
      return null;
    }
  }

  @Override
  public AccountCompany queryCompanyAccountByCustId(String custId) {
    AccountCompanyExample example = new AccountCompanyExample();
    example.createCriteria().andCustIdEqualTo(custId);
    List<AccountCompany> list = companyMapper.selectByExample(example);
    if (list.size() > 0) {
      return list.get(0);
    } else {
      return null;
    }
  }

  @Override
  public List<Account> getAccountInfo(Long userId) {
    AccountExample example = new AccountExample();
    example.createCriteria().andUserIdEqualTo(userId);
    List<Account> list = accountMapper.selectByExample(example);
    return list;
  }

  @Override
  public BigDecimal queryAccountBalance(Long userId) throws Exception {
    User user = userService.getUserById(userId);
    String custId = user.getGetCustId();

    String seqNo = DateFormatUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS");
    // V2.0 --- 查询账户余额
    Map<String, Object> dataMap = new HashMap<String, Object>();
    dataMap.put("custId", custId);// 客户Id
    dataMap.put("custType", "1");// 账户类型
    String data = JSON.toJSONString(dataMap);
    RsaKey rsaKey = getKeys();
    String sysprkey = rsaKey.getPrivateKey();
    String str = mchn + "|" + seqNo + "|" + accountBalanceTradeType + "|" + data + "||";

    String signatureStr = RSAUtils.sign(str.getBytes(), sysprkey);
    // String signature = URLEncoder.encode(signatureStr,"UTF-8");

    logger.info("请求资金系统 查询账户余额URL：" + accountBalanceUrl);
    Map<String, String> map = new HashMap<String, String>();
    map.put("mchn", mchn);
    map.put("seq_no", seqNo);
    map.put("trade_type", accountBalanceTradeType);
    map.put("page_url", "");
    map.put("back_url", "");
    map.put("data", data);
    map.put("signature", signatureStr);
    logger.info("请求资金系统 查询账户余额参数：" + JSON.toJSONString(map));

    // 账户余额
    String loanJson = HttpUtils.doPostForm(accountBalanceUrl, map);
    logger.info("账户余额返回的对象" + loanJson);
    JSONObject jsonObject = JSONObject.parseObject(loanJson);
    if (jsonObject.getString("resp_code").equals("0000")) {
      ObjectMapper mapper = new ObjectMapper();
      AccountBalanceResForm resForm =
          mapper.readValue(jsonObject.getString("data"), AccountBalanceResForm.class);
      logger.info("账户余额：" + resForm.getAmount());
      return resForm.getAmount();
    }
    return new BigDecimal(0);
  }


  @Override
  public TradeFlowResForm queryTradeFlow(Long userId, String tradeFilters) throws Exception {
    TradeFlowResForm tradeFlowResForm = new TradeFlowResForm();
    User user = userService.getUserById(userId);
    String custId = user.getGetCustId();

    String seqNo = DateFormatUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS");
    // V2.0 --- 查询账户余额
    Map<String, Object> dataMap = new HashMap<String, Object>();
    dataMap.put("cust_id", custId);// 客户Id
    dataMap.put("str_trade_time", "20180704");// 交易开始时间
    dataMap.put("end_trade_time", "20180704");// 交易结束时间
    dataMap.put("tradeFilters", tradeFilters);// 业务标识 c:充值 w:提现 b;出借 r:回款 o 其他
    dataMap.put("sortBy", "1");// 倒叙排列
    String data = JSON.toJSONString(dataMap);
    RsaKey rsaKey = getKeys();
    String sysprkey = rsaKey.getPrivateKey();
    String url = "http://10.100.200.119:8881/api/query/queryFundTrade";
    Integer tradeType = 11110001;
    String str = mchn + "|" + seqNo + "|" + tradeType + "|" + data;

    String signatureStr = RSAUtils.sign(str.getBytes(), sysprkey);
    String signature = URLEncoder.encode(signatureStr, "UTF-8");
    StringBuilder sbd = new StringBuilder();
    sbd.append(url).append("?").append("mchn=");
    sbd.append(mchn).append("&seq_no=").append(seqNo).append("&trade_type=").append(tradeType);
    sbd.append("&page_url=").append("").append("&back_url=").append("");
    sbd.append("&data=").append(URLEncoder.encode(data, "UTF-8")).append("&signature=")
        .append(signature);

    logger.info("请求资金系统 查询交易流水参数：" + sbd.toString());
    logger.info("请求资金系统 查询交易流水URL：" + accountBalanceUrl);
    // 账户余额
    String loanJson = HttpUtils.doGet(sbd.toString());
    logger.info("账户余额返回的对象" + loanJson);
    JSONObject jsonObject = JSONObject.parseObject(loanJson);
    if (jsonObject.getString("resp_code").equals("0000")) {
      ObjectMapper mapper = new ObjectMapper();
      tradeFlowResForm = mapper.readValue(jsonObject.getString("data"), TradeFlowResForm.class);
      logger.info("账户流水：" + JSON.toJSONString(tradeFlowResForm));
    }
    return tradeFlowResForm;
  }

  @Override
  public AccountStatus queryAccStatus(Long userId) throws Exception {
    String url = "http://10.100.200.119:8881/api/query/queryAccStatus";
    User user = userService.getUserById(userId);
    String custId = user.getGetCustId();

    String seqNo = DateFormatUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS");
    // V2.0 --- 查询账户余额
    Map<String, Object> dataMap = new HashMap<String, Object>();
    dataMap.put("custId", custId);// 客户Id
    String data = JSON.toJSONString(dataMap);
    RsaKey rsaKey = getKeys();
    String sysprkey = rsaKey.getPrivateKey();
    String tradeType = "11021100";
    String str = mchn + "|" + seqNo + "|" + tradeType + "|" + data;

    String signatureStr = RSAUtils.sign(str.getBytes(), sysprkey);
    String signature = URLEncoder.encode(signatureStr, "UTF-8");
    StringBuilder sbd = new StringBuilder();
    sbd.append(url).append("?").append("mchn=");
    sbd.append(mchn).append("&seq_no=").append(seqNo).append("&trade_type=").append(tradeType);
    sbd.append("&page_url=").append("").append("&back_url=").append("");
    sbd.append("&data=").append(URLEncoder.encode(data, "UTF-8")).append("&signature=")
        .append(signature);
    logger.info("请求资金系统 查询账户信息参数：" + sbd.toString());
    logger.info("请求资金系统 查询账户信息URL：" + accountBalanceUrl);
    // 账户余额
    String accStatusJson = HttpUtils.doGet(sbd.toString());
    logger.info("查询账户信息返回的对象" + accStatusJson);
    JSONObject jsonObject = JSONObject.parseObject(accStatusJson);
    AccountStatus accountStatus = new AccountStatus();
    if (jsonObject.getString("resp_code").equals("0000")) {
      ObjectMapper objectMapper = new ObjectMapper();
      accountStatus = objectMapper.readValue(jsonObject.getString("data"), AccountStatus.class);
    }
    return accountStatus;
  }

  @Override
  public Map<String, Object> enterpriseAccountLoanCallBack(LoanEnterpriseReturnForm reqForm) {
    Map<String, Object> resMap = new HashMap<String, Object>();
    AccountCompany accountCompany = queryCompanyAccountByUserId(reqForm.getUserId());
    /*
     * if(accountCompany == null){ accountCompany =
     * queryCompanyAccountBySocialCreditCode(reqForm.getSocialCreditCode()); }
     */

    int result = 0;
    if (accountCompany != null) {
      accountCompany.setStatus(reqForm.getStatus());
      accountCompany.setAccountVerifyInfo(reqForm.getReturnReason());
      result = companyMapper.updateByPrimaryKeySelective(accountCompany);
    }

    if (result > 0) {
      resMap.put("resp_code", "0000");
      resMap.put("resp_msg", "调用成功");
    } else {
      resMap.put("resp_code", "0001");
      resMap.put("resp_msg", "调用失败");
    }
    return resMap;
  }


  public AccountCompany getAccountCompayInfo(Long userId) {
    AccountCompanyExample example = new AccountCompanyExample();
    example.createCriteria().andUserIdEqualTo(userId);
    List<AccountCompany> list = companyMapper.selectByExample(example);
    if (list.size() > 0) {
      return list.get(0);
    } else {
      return null;
    }
  }


  /**
   * 调用冠E通获取客户ID @return；
   * 
   * @throws Exception
   */
  private JSONObject callGetPlatformCreateCusId(String mobile, String busiType) throws Exception {
    Map<String, String> map = new HashedMap(8);
    map.put("mobilePhone", mobile);// 注册手机号
    map.put("userName", "");// 用户名
    map.put("password", "");// 密码
    map.put("recommender", "");// 推荐码
    map.put("channel", "");// 渠道
    map.put("clientType", "10");// 客户端类型 10为冠e贷
    map.put("blackBox", "");// 同盾黑盒(此参数限移动客户端传，冠e贷不需要用同盾)
    map.put("busiType", busiType);// 0-个人,1-企业
    logger.info("调用冠e通 注册接口 请求url：" + registerUserUrl);
    logger.info("调用冠e通 注册接口 请求参数：" + JSON.toJSONString(map));
    String jsonObject = HttpUtils.doPostForm(registerUserUrl, map);
    logger.info("调用冠e通 注册接口 返回数据：" + jsonObject);
    return JSONObject.parseObject(jsonObject);
  }


  private void callPersonalAccountV2(String mobile, String custId) throws Exception {
    String seqNo = DateFormatUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS");
    // V2.0 --- 开户
    Map<String, String> data = new HashMap<String, String>(3);
    data.put("cust_id", custId);
    data.put("mobile", "13381279513");
    data.put("remark", "");

    Map reqMap = new HashMap();
    reqMap.put("mchn", mchn);
    reqMap.put("seq_no", seqNo);
    reqMap.put("trade_type", "11021002");
    reqMap.put("page_url", "http://www.999kan.com/dianying/index.html?usd");
    reqMap.put("back_url", "http://10.100.161.215:9090//v2/api/account/personAccountCallBack");
    reqMap.put("signature", "");
    reqMap.put("data", data);
    HttpUtils.doPost(personalUrl, JSON.toJSONString(reqMap));
  }

  private JSONObject callEnterpriseAccountV2(String mobile, String custId) throws Exception {
    // V2.0 --- 开户
    Map<String, String> map = new HashMap<String, String>();
    map.put("cust_id", custId);
    map.put("mobile", mobile);
    map.put("remark", "");
    String jsonObject = HttpUtils.doPostForm(enterpriseUrl, map);
    logger.info(jsonObject.toString());
    return JSONObject.parseObject(jsonObject);
  }

  /**
   * 调用借款系统同步银行卡状态
   *
   * @param reqForm
   * @param userId
   * @param status
   * @return
   * @throws Exception
   */
  private JSONObject callLoanPlatformCreatePersonAccount(PersonalAccount reqForm, Long userId,
      int status) throws Exception {
    Map<String, Object> map = new HashedMap();
    map.put("comIdNum", "0");// 统一社会信用代码
    map.put("userId", userId);
    map.put("companyBankBranchName", "0");// 支行名称
    map.put("legalPerName", reqForm.getCustName());
    map.put("legalPerPhone", reqForm.getMobile());
    map.put("custId", reqForm.getCustId());
    map.put("status", status);
    map.put("companyName", "0");
    map.put("companyAccount", reqForm.getBankcardNo());
    map.put("provinceName", "0");
    map.put("companyBankOfDeposit", reqForm.getBankCode());// 银行编码
    map.put("cityName", "0");
    map.put("legalPerNum", reqForm.getIdCardNo());

    logger.info("个人开户>>>推送借款系统请求地址：" + loanCreateAccountUrl);
    logger.info("个人开户>>>推送借款系统请求参数：" + JSONObject.toJSONString(map));
    JSONObject jsonObject = HttpUtils.doPost(loanCreateAccountUrl, JSONObject.toJSONString(map));
    logger.info("个人开户>>>推送借款系统返回结果：" + jsonObject);
    return jsonObject;
  }


  /*
   * 调用借款系统同步银行卡状态
   * 
   * @return
   * 
   * @throws Exception
   */
  /*
   * private JSONObject callLoanPlatformCreateEnterpriseAccount(EnterpriseAccount reqForm, Long
   * userId, int status) throws Exception { Map<String, Object> map = new HashedMap();
   * map.put("userId", userId); map.put("companyBankOfDeposit", reqForm.getBankCode());
   * map.put("companyAccount", reqForm.getBankcardNo()); map.put("companyBankBranchName", "未记录");
   * map.put("provinceName", null); map.put("cityName", ""); map.put("comIdNum",
   * reqForm.getUnifiedCode()); map.put("legalPerNum", reqForm.getIdCardNo());
   * map.put("legalPerName", reqForm.getCustName()); map.put("companyName",
   * reqForm.getEnterpriseName()); map.put("legalPerPhone", reqForm.getMobile()); map.put("status",
   * status); JSONObject jsonObject = HttpUtils.doPost(loanCreateAccountCompanyUrl,
   * JSONObject.toJSONString(map)); return jsonObject; }
   */



  @Override
  public PageResForm toPersonalPageAPP(User user) throws Exception {

    /*
     * UserResForm resForm = userService.loginUser(user.getMobile(), "gq12345",
     * ChanelStyle.MOBILE.getValue()); logger.info("token：" + resForm.getToken());
     */
    PageResForm pageResForm = new PageResForm();
    User user1 = getCustId(user, "0");// 个人开户业务类型传0
    // 更新用户信息
    // userService.updateUserCustId(custId, user.getId());
    String seqNo = getSeqNo();
    // V2.0 --- 开户
    Map<String, String> dataMap = new HashMap<String, String>(3);
    dataMap.put("custId", user1.getGetCustId());
    dataMap.put("mobile", user1.getMobile());
    dataMap.put("isChange", "0");// 是否修改手机号 0修改 1不修改 默认0

    if (user.getUserRole() != null && user.getUserRole() > 0) {
      dataMap.put("userRole", "2");// 担保人
    } else {
      dataMap.put("userRole", "1");// 借款人
    }
    String data = JSON.toJSONString(dataMap);
    RsaKey rsaKey = getKeys();
    String sysprkey = rsaKey.getPrivateKey();
    logger.info("personOpenAccount 个人开户 私钥：" + sysprkey);
    // personalPageUrl = personalPageUrl +"?token="+resForm.getToken();
    logger.info("personalPageUrl：" + personalPageUrl);

    String str = mchn + "|" + seqNo + "|" + personalTradeType + "|" + data + "|" + personalPageUrl
        + "|" + personalCallbackUrl;
    logger.info("personOpenAccount 个人开户APP str :" + str);
    String signatureStr = RSAUtils.sign(str.getBytes(), sysprkey);
    String signature = URLEncoder.encode(signatureStr, "UTF-8");
    logger.info("personOpenAccount 个人开户APP 签名:" + signature);

    StringBuilder sbd = new StringBuilder();
    sbd.append(personalUrl).append("?").append("mchn=");
    sbd.append(mchn).append("&seq_no=").append(seqNo).append("&trade_type=")
        .append(personalTradeType);
    sbd.append("&page_url=").append(personalPageUrl).append("&back_url=")
        .append(personalCallbackUrl);
    sbd.append("&data=").append(URLEncoder.encode(data, "UTF-8")).append("&signature=")
        .append(signature);
    logger.info("个人开户APP 返回给前端请求的url：" + sbd.toString());
    pageResForm.setRedirectUrl(sbd.toString());
    return pageResForm;
  }

  @Override
  public PageResForm toPersonalPagePC(User user) throws Exception {
    PageResForm pageResForm = new PageResForm();
    User user1 = getCustId(user, "0");// 个人开户业务类型传0
    // 更新用户信息
    // userService.updateUserCustId(custId, user.getId());
    String seqNo = getSeqNo();
    // V2.0 --- 开户
    Map<String, String> dataMap = new HashMap<String, String>(3);
    dataMap.put("custId", user1.getGetCustId());
    dataMap.put("mobile", user.getMobile());
    dataMap.put("isChange", "0");// 是否修改手机号 0修改 1不修改 默认0
    // 用户类型 0个人1企业
    // 用户角色0借款人1自有担保人2自有担保企业3合作企业
    if (user.getUserRole() != null && user.getUserRole() > 0) {
      dataMap.put("userRole", "2");// 担保人
    } else {
      dataMap.put("userRole", "1");// 借款人
    }
    String data = JSON.toJSONString(dataMap);
    RsaKey rsaKey = getKeys();
    String sysprkey = rsaKey.getPrivateKey();

    String str = mchn + "|" + seqNo + "|" + personalPCTradeType + "|" + data + "|"
        + personalPCPageUrl + "|" + personalCallbackUrl;
    String signatureStr = RSAUtils.sign(str.getBytes(), sysprkey);
    String signature = URLEncoder.encode(signatureStr, "UTF-8");
    logger.info("personOpenAccount 个人开户PC端 签名:" + signature);
    StringBuilder sbd = new StringBuilder();
    sbd.append(personalPCUrl).append("?").append("mchn=");
    sbd.append(mchn).append("&seq_no=").append(seqNo).append("&trade_type=")
        .append(personalPCTradeType);
    sbd.append("&page_url=").append(personalPCPageUrl).append("&back_url=")
        .append(personalCallbackUrl);
    sbd.append("&data=").append(URLEncoder.encode(data, "UTF-8")).append("&signature=")
        .append(signature);
    pageResForm.setRedirectUrl(sbd.toString());
    logger.info("个人开户返回给前端请求的url：" + sbd.toString());

    return pageResForm;
  }


  @Override
  public PageResForm toEnterprisePageAPP(User user) throws Exception {
    PageResForm pageResForm = new PageResForm();
    User user1 = getCustId(user, "1");// 企业开户业务类型传1

    // 更新用户信息
    // userService.updateUserCustId(custId, user.getId());
    String seqNo = getSeqNo();
    // V2.0 --- 开户
    Map<String, String> dataMap = new HashMap<String, String>(3);
    dataMap.put("custId", user1.getGetCustId());
    dataMap.put("mobile", user1.getMobile());
    dataMap.put("isChange", "0");// 是否修改手机号 0修改 1不修改 默认0
    dataMap.put("isEvidence", "1");// 是否需要三证 0 不需要 1需要 默认0

    if (user.getUserRole() != null && user.getUserRole() > 0) {
      dataMap.put("userRole", "2");// 担保人
    } else {
      dataMap.put("userRole", "1");// 借款人
    }
    String data = JSON.toJSONString(dataMap);
    RsaKey rsaKey = getKeys();
    String sysprkey = rsaKey.getPrivateKey();

    String str = mchn + "|" + seqNo + "|" + enterpriseTradeType + "|" + data + "|"
        + enterprisePageUrl + "|" + enterpriseCallbackUrl;
    String signatureStr = RSAUtils.sign(str.getBytes(), sysprkey);
    String signature = URLEncoder.encode(signatureStr, "UTF-8");
    logger.info("企业开户生成的签名：" + signature);

    StringBuilder sbd = new StringBuilder();
    sbd.append(enterpriseUrl).append("?").append("mchn=");
    sbd.append(mchn).append("&seq_no=").append(seqNo).append("&trade_type=")
        .append(enterpriseTradeType);
    sbd.append("&page_url=").append(enterprisePageUrl).append("&back_url=")
        .append(enterpriseCallbackUrl);
    sbd.append("&data=").append(URLEncoder.encode(data, "UTF-8")).append("&signature=")
        .append(signature);
    logger.info("企业开户返回给前端请求的url：" + sbd.toString());

    pageResForm.setRedirectUrl(sbd.toString());
    return pageResForm;
  }

  @Override
  public PageResForm toEnterprisePagePC(User user) throws Exception {
    PageResForm pageResForm = new PageResForm();
    User user1 = getCustId(user, "1");// 企业开户业务类型传1

    String seqNo = getSeqNo();
    // V2.0 --- 开户
    Map<String, String> dataMap = new HashMap<String, String>(3);
    dataMap.put("custId", user1.getGetCustId());
    dataMap.put("mobile", user1.getMobile());
    dataMap.put("isChange", "0");// 是否修改手机号 0修改 1不修改 默认0
    dataMap.put("isEvidence", "1");// 是否需要三证 0 不需要 1需要 默认0
    if (user.getUserRole() != null && user.getUserRole() > 0) {
      dataMap.put("userRole", "2");// 担保人
    } else {
      dataMap.put("userRole", "1");// 借款人
    }
    String data = JSON.toJSONString(dataMap);
    RsaKey rsaKey = getKeys();
    String sysprkey = rsaKey.getPrivateKey();

    String str = mchn + "|" + seqNo + "|" + enterpriseTradeType + "|" + data + "|"
        + enterprisePCPageUrl + "|" + enterpriseCallbackUrl;
    String signatureStr = RSAUtils.sign(str.getBytes(), sysprkey);
    String signature = URLEncoder.encode(signatureStr, "UTF-8");
    logger.info("企业开户  PC端生成的签名：" + signature);

    StringBuilder sbd = new StringBuilder();
    sbd.append(enterprisePCUrl).append("?").append("mchn=");
    sbd.append(mchn).append("&seq_no=").append(seqNo).append("&trade_type=")
        .append(enterpriseTradeType);
    sbd.append("&page_url=").append(enterprisePCPageUrl).append("&back_url=")
        .append(enterpriseCallbackUrl);
    sbd.append("&data=").append(URLEncoder.encode(data, "UTF-8")).append("&signature=")
        .append(signature);
    logger.info("企业开户 PC端返回给前端请求的url：" + sbd.toString());
    pageResForm.setRedirectUrl(sbd.toString());
    return pageResForm;
  }

    @Override
    public PageResForm toModifyEnterprisePC(User user) throws Exception {
        PageResForm pageResForm = new PageResForm();
        User user1 = getCustId(user, "1");// 企业开户业务类型传1

        String seqNo = getSeqNo();
        // V2.0 --- 开户
        Map<String, String> dataMap = new HashMap<String, String>(3);
        dataMap.put("custId", user1.getGetCustId());
        dataMap.put("mobile", user1.getMobile());
        dataMap.put("isChange", "0");// 是否修改手机号 0修改 1不修改 默认0
        dataMap.put("isEvidence", "1");// 是否需要三证 0 不需要 1需要 默认0
        if (user.getUserRole() != null && user.getUserRole() > 0) {
            dataMap.put("userRole", "2");// 担保人
        } else {
            dataMap.put("userRole", "1");// 借款人
        }
        String data = JSON.toJSONString(dataMap);
        RsaKey rsaKey = getKeys();
        String sysprkey = rsaKey.getPrivateKey();

        String str = mchn + "|" + seqNo + "|" + enterpriseTradeType + "|" + data + "|"
                + enterprisePCPageUrl + "|" + enterpriseCallbackUrl;
        String signatureStr = RSAUtils.sign(str.getBytes(), sysprkey);
        String signature = URLEncoder.encode(signatureStr, "UTF-8");

        StringBuilder sbd = new StringBuilder();
        sbd.append(enterpriseModifyPCUrl).append("?").append("mchn=");
        sbd.append(mchn).append("&seq_no=").append(seqNo).append("&trade_type=")
                .append(enterpriseTradeType);
        sbd.append("&page_url=").append(enterprisePCPageUrl).append("&back_url=")
                .append(enterpriseCallbackUrl);
        sbd.append("&data=").append(URLEncoder.encode(data, "UTF-8")).append("&signature=")
                .append(signature);
        logger.info("企业开户修改上次资料 PC端返回给前端请求的url：" + sbd.toString());
        pageResForm.setRedirectUrl(sbd.toString());
        return pageResForm;
    }

    @Override
    public PageResForm toModifyEnterpriseAPP(User user) throws Exception {
        PageResForm pageResForm = new PageResForm();
        User user1 = getCustId(user, "1");// 企业开户业务类型传1

        String seqNo = getSeqNo();
        // V2.0 --- 开户
        Map<String, String> dataMap = new HashMap<String, String>(3);
        dataMap.put("custId", user1.getGetCustId());
        dataMap.put("mobile", user1.getMobile());
        dataMap.put("isChange", "0");// 是否修改手机号 0修改 1不修改 默认0
        dataMap.put("isEvidence", "1");// 是否需要三证 0 不需要 1需要 默认0
        if (user.getUserRole() != null && user.getUserRole() > 0) {
            dataMap.put("userRole", "2");// 担保人
        } else {
            dataMap.put("userRole", "1");// 借款人
        }
        String data = JSON.toJSONString(dataMap);
        RsaKey rsaKey = getKeys();
        String sysprkey = rsaKey.getPrivateKey();

        String str = mchn + "|" + seqNo + "|" + enterpriseTradeType + "|" + data + "|"
                + enterprisePCPageUrl + "|" + enterpriseCallbackUrl;
        String signatureStr = RSAUtils.sign(str.getBytes(), sysprkey);
        String signature = URLEncoder.encode(signatureStr, "UTF-8");

        StringBuilder sbd = new StringBuilder();
        sbd.append(enterpriseModifyUrl).append("?").append("mchn=");
        sbd.append(mchn).append("&seq_no=").append(seqNo).append("&trade_type=")
                .append(enterpriseTradeType);
        sbd.append("&page_url=").append(enterprisePCPageUrl).append("&back_url=")
                .append(enterpriseCallbackUrl);
        sbd.append("&data=").append(URLEncoder.encode(data, "UTF-8")).append("&signature=")
                .append(signature);
        logger.info("企业开户修改上次资料 APP端返回给前端请求的url：" + sbd.toString());
        pageResForm.setRedirectUrl(sbd.toString());
        return pageResForm;
    }


  @Override
  public Map<String, Object> personAccountCallBack(CapitalResForm resForm) throws Exception {
    synchronized (this) {
      Map<String, Object> resMap = new HashMap<String, Object>();
      // data是JSON字符串
      logger.info("个人开户>>>>>资金系统回调返回的数据：" + JSON.toJSONString(resForm));
      String data = URLDecoder.decode(resForm.getData(), "UTF-8");
      ObjectMapper mapper = new ObjectMapper();
      PersonalAccount accountData = mapper.readValue(data, PersonalAccount.class);

      User user = userService.getUserByCustId(accountData.getCustId());
      Integer accountFlag = 4;// 个人已开户
      Account account = new Account();
      Account myAccount = getAccountByUserId(user.getId());

      if (myAccount != null) {
        resMap.put("resp_code", "0000");// 个人开户成功
        resMap.put("resp_msg", "个人开户成功");// 个人开户成功
        resMap.put("status", accountFlag);// 个人开户成功状态
        resMap.put("verifyInfo", null);// 个人开户没有审核信息
        return resMap;
      } else {
        if (!resForm.getResp_code().equals("0000")) {
          String msg = resForm.getResp_msg();
          resMap.put("resp_code", "0001");// 个人开户失败
          resMap.put("resp_msg", msg);// 个人开户失败
          resMap.put("status", "0");// 开户状态
          resMap.put("verifyInfo", null);// 个人开户没有审核信息
          return resMap;
        } else {
          Date date = new Date();
          account.setStatus(accountFlag);// 已开户
          account.setAccountCode(resForm.getSeq_no());// 账户编号(当前时间+随机数)
          account.setUserId(user.getId());// 客户ID
          account.setCustId(accountData.getCustId());//custId
          account.setCorporationPhone(accountData.getMobile());// 客户银行预留手机号
          account.setCorporationCardNum(accountData.getIdCardNo());// 身份证号
          account.setCorporationName(accountData.getCustName());// 客户真实姓名
          account.setCompanyAccount(accountData.getBankcardNo());// 银行卡号
          account.setCompanyBankOfDeposit(accountData.getBankCode());// 开户行编码
          account.setCompanyBankOfDepositValue(accountData.getBankName());// 开户行名称
          account.setCreateId(user.getId());
          account.setCreateTime(date);
          logger.info("个人开户>>>插入的数据：" + JSON.toJSONString(account));
          accountMapper.insertSelective(account);// 插入个人开户表中
          userService.updatePersonalAccountFlag(user.getId(), accountData, accountFlag,date);// 开户并签约
          resMap.put("resp_code", "0000");// 开户成功
          resMap.put("status", accountFlag);// 开户状态
          resMap.put("verifyInfo", null);// 个人开户没有审核信息
          resMap.put("resp_msg", "个人开户成功");// 个人开户成功

          try {
            // 同步借款系统
            callLoanPlatformCreatePersonAccount(accountData, user.getId(), accountFlag);
          } catch (Exception e) {
            logger.error("catch 个人开户信息同步借款系统 异常：" + e.getMessage(), e);
          }

          try {
            // 把个人开户信息推送给冠e通
            estabPersonalAccountCallback(resForm, data, user);
          } catch (Exception e) {
            logger.error("catch 个人开户信息推送给冠e通 异常：" + e.getMessage(), e);
          }
          return resMap;
        }

      }
    }
  }


  private void userActivationCallback(CapitalResForm resForm, String dataStr, User user)
      throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    CommonAccount commonAccount = mapper.readValue(dataStr, CommonAccount.class);
    String data = JSON.toJSONString(commonAccount);

    Map<String, String> reqMap = new HashMap<String, String>();
    reqMap.put("mchn", resForm.getMchn());
    reqMap.put("seq_no", resForm.getSeq_no());
    reqMap.put("signature", resForm.getSignature());
    reqMap.put("trade_type", resForm.getTrade_type());
    reqMap.put("data", data);
    reqMap.put("resp_code", resForm.getResp_code());
    reqMap.put("resp_msg", resForm.getResp_msg());
    logger.info("用户激活推送冠e通请求url：" + userActivationCallbackUrl);
    logger.info("用户激活推送冠e通请求参数：" + JSON.toJSONString(reqMap));
    String result = HttpUtils.doPostForm(userActivationCallbackUrl, reqMap);
    logger.info("用户激活推送冠e通返回结果：" + result);
  }

  /**
   * 冠e通实名开户(个人开户)
   * 
   * @param resForm
   * @param data
   * @param user
   * @throws Exception
   */
  private String estabPersonalAccountCallback(CapitalResForm resForm, String data, User user)
      throws Exception {
    Map<String, String> reqMap = new HashMap<String, String>();
    reqMap.put("mchn", resForm.getMchn());
    reqMap.put("seq_no", resForm.getSeq_no());
    reqMap.put("signature", resForm.getSignature());
    reqMap.put("trade_type", resForm.getTrade_type());
    reqMap.put("data", data);
    reqMap.put("resp_code", resForm.getResp_code());
    reqMap.put("resp_msg", resForm.getResp_msg());
    logger.info("冠易贷2.0>>>个人开户推送冠e通请求url：" + estabAccountCallbackUrl);
    logger.info("冠易贷2.0>>>个人开户推送冠e通请求参数：" + JSON.toJSONString(reqMap));
    String result = HttpUtils.doPostForm(estabAccountCallbackUrl, reqMap);
    logger.info("冠易贷2.0>>>个人开户推送冠e通返回结果：" + result);
    return result;
  }

  /**
   * 冠e通实名开户(企业开户)
   * 
   * @param resForm
   * @throws Exception
   */
  @Override
  public String pushEnterpriseAccountToGqget(CapitalResForm resForm) throws Exception {
    String dataStr = URLDecoder.decode(resForm.getData(), "UTF-8");
    ObjectMapper mapper = new ObjectMapper();
    EnterpriseAccount account = mapper.readValue(dataStr, EnterpriseAccount.class);

    Map<String, Object> map = new HashedMap();
    map.put("custId", account.getCustId());
    map.put("mobile", account.getMobile());
    map.put("idCardNo", account.getIdCardNo());
    map.put("custName", account.getCustName());
    map.put("bankName", account.getBankName());
    map.put("bankCode", account.getBankCode());
    map.put("bankcardNo", account.getBankcardNo());
    map.put("loanCompanyDesc", account.getEnterpriseName());
    // map.put("remark",account.getRemark());

    String data = JSON.toJSONString(map);
    Map<String, String> reqMap = new HashMap<String, String>();
    reqMap.put("mchn", resForm.getMchn());
    reqMap.put("seq_no", resForm.getSeq_no());
    reqMap.put("signature", resForm.getSignature());
    reqMap.put("trade_type", resForm.getTrade_type());
    reqMap.put("data", data);
    reqMap.put("resp_code", resForm.getResp_code());
    reqMap.put("resp_msg", resForm.getResp_msg());
    // logger.info("冠易贷2.0>>>企业开户推送冠e通请求url："+estabAccountCallbackUrl);
    // logger.info("冠易贷2.0>>>企业开户推送冠e通请求参数："+ JSON.toJSONString(reqMap));
    String result = HttpUtils.doPostForm(estabAccountCallbackUrl, reqMap);
    logger.info("冠易贷2.0>>>企业开户推送冠e通返回结果：" + result);
    return result;
  }

  @Transactional
  @Override
  public Map<String, Object> enterpriseAccountCallBack(CapitalResForm resForm) throws Exception {
    logger.info("企业开户>>>>>资金系统回调返回的数据：" + JSON.toJSONString(resForm));
    String data = resForm.getData();
    ObjectMapper mapper = new ObjectMapper();
    String dataDecode = URLDecoder.decode(data, "UTF-8");
    logger.info("企业开户>>>>> data Decode :" + dataDecode);
    EnterpriseAccount enterpriseData = mapper.readValue(dataDecode, EnterpriseAccount.class);
    User user = userService.getUserByCustId(enterpriseData.getCustId());

    Map<String, Object> resMap = new HashMap<String, Object>();
    Integer status = 0;
    if (enterpriseData.getAuditStatus() != null) {
      switch (enterpriseData.getAuditStatus()) {
        case 0:
          status = 6;// 懒猫待审核
          break;
        case 1:
          status = 7;// 懒猫审核通过（已开户）
          break;
        case 2:
          status = 8;// 懒猫审核退回
          break;
        case 3:
          status = 9;// 懒猫审核拒绝
          break;
        case 10:
          status = 10;// 借款系统退回
          break;
      }
    }
    logger.info("status:" + status);
    AccountCompany accountCompany = new AccountCompany();
    accountCompany.setUserId(user.getId());
    accountCompany.setCustId(enterpriseData.getCustId());// custId
    accountCompany.setCompanyName(enterpriseData.getEnterpriseName());// 企业名称
    accountCompany.setCompanyAccount(enterpriseData.getBankcardNo());// 对公银行卡号
    accountCompany.setCompanyBankOfDeposit(enterpriseData.getBankCode());// 开户行编码
    accountCompany.setCompanyBankOfDepositValue(enterpriseData.getBankName());// 开户行名称
    accountCompany.setLegalCardNumber(enterpriseData.getIdCardNo());// 法人证件号
    accountCompany.setLegalMobile(enterpriseData.getMobile());// 法人客户手机号
    accountCompany.setLegalName(enterpriseData.getCustName());// 法人姓名
    accountCompany.setSocialCreditCode(enterpriseData.getUnifiedCode());// 统一社会信用代码
    accountCompany.setCreateId(user.getId());// 创建人Id
    accountCompany.setCreateTime(new Date());// 创建时间
    accountCompany.setAccountsPermitsUrl(enterpriseData.getOpenAccount());// 开户许可证
    accountCompany.setStatus(status);// 企业开户(审核)状态
    accountCompany.setAccountVerifyInfo(resForm.getResp_msg());// 企业开户审核结果
    accountCompany.setBusinessLicenceUrl(enterpriseData.getLicenseImg());
    accountCompany.setIdCardBackUrl(enterpriseData.getCardBackImg());
    accountCompany.setIdCardFaceUrl(enterpriseData.getCardFaceImg());
    accountCompany.setIdCardHoldUrl(enterpriseData.getCardHandImg());
    accountCompany.setBusinessLicence(enterpriseData.getBusinessLicense());
    accountCompany.setTaxCode(enterpriseData.getTaxNo());
    accountCompany.setOrganizationCode(enterpriseData.getOrgNo());
    accountCompany.setCompanyContact(enterpriseData.getContact());
    accountCompany.setContactMobile(enterpriseData.getContactPhone());

    Date date = new Date();
    RLock lock = redissonClient.getLock(enterpriseData.getCustId());
    Boolean  isLock = lock.tryLock(3, 10, TimeUnit.SECONDS);
    try{
      if (isLock) {
        Integer result = 0;
        if (redissonClient.getBucket(enterpriseData.getCustId()).isExists()) {
          AccountCompany company = (AccountCompany) redissonClient.getBucket(enterpriseData.getCustId()).get();
          Integer accountStatus = company.getStatus();
          logger.info("accountStatus:" + accountStatus);
          if(accountStatus == status){
            logger.info("企业开户信息已存在，企业开户状态：" + accountStatus);
            resMap.put("resp_code", "0000");// 开户成功
            resMap.put("verifyInfo", company.getAccountVerifyInfo());// 企业开户审核信息
            resMap.put("status", accountStatus);
            resMap.put("resp_msg", "企业开户回调成功");// 开户成功
            return resMap;
          }else{
            BeanCopyTools.copyProperties(accountCompany, company);
            result = companyMapper.updateByPrimaryKey(company);// 更新企业开户表信息(更新审核状态和审核信息)
            redissonClient.getBucket(enterpriseData.getCustId()).set(company, 15, TimeUnit.DAYS);
          }

        } else {
          logger.info(">>>>>>新增企业开户表信息>>>>>>");
          result = companyMapper.insertSelective(accountCompany);// 插入企业开户表中
          redissonClient.getBucket(enterpriseData.getCustId()).set(accountCompany, 15,
                  TimeUnit.DAYS);
        }
        if (result > 0) {
          resMap.put("resp_code", "0000");// 开户成功
          resMap.put("verifyInfo", accountCompany.getAccountVerifyInfo());// 企业开户审核信息
          resMap.put("status", status);
          resMap.put("resp_msg", "企业开户回调成功");// 开户成功
          userService.updateEnterpriseAccountFlag(user.getId(), enterpriseData, status, date);
          // 把企业开户信息推送到借款系统
          jmsProvider.sendMessage(Constant.MQ_GED_ENTERPRISE_ACCOUNT_TO_LOAN, accountCompany);
          // 把企业开户信息推送到冠e通
          jmsProvider.sendMessage(Constant.MQ_GED_ENTERPRISE_ACCOUNT_TO_GET, resForm);
          return resMap;
        } else {
          resMap.put("resp_code", "0001");// 开户失败
          resMap.put("status", status);// 开户状态
          resMap.put("verifyInfo", accountCompany.getAccountVerifyInfo());// 企业开户审核信息
          resMap.put("resp_msg", "企业开户回调失败");// 开户失败
          return resMap;
        }
      }

    }catch (Exception e){
        logger.error(e.getMessage(),e);
        throw e;
    }finally {
        if(isLock){
            lock.unlock();
        }
    }

    return resMap;
  }


  @Override
  public JSONObject pushEnterpriseAccounToLoan(AccountCompany accountCompany) {
    JSONObject jsonObject = new JSONObject();
    String accoutJSON =
        JSONObject.toJSONString(accountCompany, SerializerFeature.WriteMapNullValue);
    // logger.info("企业开户成功后 推送 >>>>>>借款系统 请求数据：" + accoutJSON);
    try {
      jsonObject = HttpUtils.doPost(loanCreateAccountCompanyUrl, accoutJSON);
      logger.info("企业开户推送 >>>>>>借款系统返回结果：" + JSON.toJSONString(jsonObject));
    } catch (Exception e) {
      logger.error("企业开户信息推送 >>>>>>借款系统，错误：", e.getMessage(), e);
    }
    return jsonObject;
  }

  @Override
  public RepayPageResForm repaymentRecharge(RepaymentReqForm reqForm, User user) throws Exception {
    // REPEYMENT_PERIOD
    // redissonClient.getBucket(Constant.REPEYMENT_PERIOD+reqForm.getAmount()).set(reqForm.getPeriod());
    RepayPageResForm repayPageResForm = new RepayPageResForm();
    User dataUser = userService.getUserById(user.getId());
    String custId = dataUser.getGetCustId();
    String seqNo = getSeqNo();
    // V2.0 --- 网银充值
    Map<String, Object> dataMap = new HashMap<String, Object>();
    dataMap.put("custId", custId);// 客户Id
    dataMap.put("mobile", user.getMobile());// 客户手机号
    dataMap.put("amount", reqForm.getAmount());// 充值金额
    dataMap.put("custType", "1");// 账户类型
    dataMap.put("chargeType", 1);// 充值方式 1 网银 2 快捷
    dataMap.put("remark", reqForm.getOrderNo() + "_" + reqForm.getType());
    String data = JSON.toJSONString(dataMap);

    RsaKey rsaKey = getKeys();
    String sysprkey = rsaKey.getPrivateKey();

    String str = mchn + "|" + seqNo + "|" + rechargeTradeType + "|" + data + "|" + rechargePageUrl
        + "|" + rechargeCallbackUrl;
    String signatureStr = RSAUtils.sign(str.getBytes(), sysprkey);
    String signature = URLEncoder.encode(signatureStr, "UTF-8");
    logger.info("网银充值生成的 签名:" + signature);
    StringBuilder sbd = new StringBuilder();
    sbd.append(rechargeUrl).append("?").append("mchn=");
    sbd.append(mchn).append("&seq_no=").append(seqNo).append("&trade_type=")
        .append(rechargeTradeType);
    sbd.append("&page_url=").append(rechargePageUrl).append("&back_url=")
        .append(rechargeCallbackUrl);
    sbd.append("&data=").append(URLEncoder.encode(data, "UTF-8")).append("&signature=")
        .append(signature);
    logger.info("网银充值返回给前端请求的url：" + sbd.toString());
    repayPageResForm.setRedirectUrl(sbd.toString());
    return repayPageResForm;
  }



  @Override
  public Map<String, Object> repaymentRechargeCallBack(CapitalResForm resForm) throws Exception {
    synchronized (this) {
      Map<String, Object> resMap = new HashMap<String, Object>();
      logger.info("还款充值>>>>>资金系统回调返回的数据：" + JSON.toJSONString(resForm));
      String data = resForm.getData();
      ObjectMapper mapper = new ObjectMapper();
      OnlineBankingRecharge rechargeData =
          mapper.readValue(URLDecoder.decode(data, "UTF-8"), OnlineBankingRecharge.class);
      User user = userService.getUserByCustId(rechargeData.getCustId());
      // 根据订单号查询订单
      String[] remarks = rechargeData.getRemark().split("_");

      String orderNo = remarks[0];
      Integer type = Integer.parseInt(remarks[1]);// 还款类型
      logger.info("还款充值>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单编号：" + orderNo);
      logger.info("还款类型>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + type);

      GedOrder gedOrder = orderService.getGedOrderByOrderNo(orderNo);
      // 用订单号查询充值记录改为用流水号查询查询充值记录
      GedRepaymentRecord repaymentRecord = new GedRepaymentRecord();
      if (StringUtils.isNotBlank(resForm.getSeq_no())) {
        repaymentRecord = repaymentRecordService.getRapaymentRecordBySeqNo(resForm.getSeq_no());
      }

      GedRepaymentRecord record = new GedRepaymentRecord();
      if (repaymentRecord != null) {
        logger.info("repaymentRecord != null 已调用过");
        resMap.put("amount", repaymentRecord.getRepaymentAmount());
        resMap.put("status", repaymentRecord.getStatus());
        resMap.put("res_code", "0000");
        resMap.put("res_msg", "充值(还款)成功");
        if (redissonClient.getBucket(rechargeData.getRemark() + "_deductUrl").isExists()) {
          resMap.put("deducturl",
              (String) redissonClient.getBucket(rechargeData.getRemark() + "_deductUrl").get());
        } else {
          resMap.put("deducturl", "0");// 不需缴费
        }

        return resMap;
      } else {
        if (!resForm.getResp_code().equals("0000")) {
          String msg = resForm.getResp_msg();
          // 新增或更新还款充值记录表
          resMap.put("amount", rechargeData.getAmount());
          resMap.put("status", "0");
          resMap.put("res_code", "0001");
          resMap.put("res_msg", "充值(还款)失败");
          resMap.put("deducturl", "0");
          return resMap;
        } else {
          String tradeTimeStr =
              rechargeData.getTradeDate() == null ? null : rechargeData.getTradeDate();
          Date trateTime = null;
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          if (StringUtils.isNotBlank(tradeTimeStr)) {
            trateTime = format.parse(tradeTimeStr);
          }
          record.setRepaymentAmount(rechargeData.getAmount());
          record.setOrderNo(orderNo);// 订单号
          record.setSeqNo(resForm.getSeq_no());// 流水号
          record.setCreateTime(new Date());
          record.setModifyTime(new Date());
          record.setCustId(user.getGetCustId());
          record.setContractNo(gedOrder.getContractCode());
          record.setStatus(1);// 充值成功
          record.setType(type);// 还款记录类型
          // record.setPeriodNum(peroid);
          record.setDeductCustName(user.getUsername());
          record.setDeductApplyNo(resForm.getSeq_no());
          record.setMobileNum(user.getMobile());
          // record.setBank(bankId);银行id(2.0版本没有)
          BigDecimal accountBalance = queryAccountBalance(user.getId());
          record.setAccountBalance(accountBalance);
          record.setCreateId(user.getId());

          // 插入充值记录
          int result = gedRepaymentRecordMapper.insertSelective(record);
          // 更新订单表状态为还款进行中(锁住)
          orderService.updateRepayStatusAfterRecharge(gedOrder);
          if (result > 0) {
            resMap.put("amount", record.getRepaymentAmount());
            resMap.put("status", record.getStatus());
            resMap.put("res_code", "0000");
            resMap.put("res_msg", "充值(还款)成功");
            // 把还款充值成功以后推送借款系统
            Map<String, Object> reqMap = new HashMap<String, Object>();
            reqMap.put("orderNo", rechargeData.getRemark());
            reqMap.put("seqNo", resForm.getSeq_no());
            if (redissonClient.getBucket(rechargeData.getCustId()).isExists()) {
              if (((String) redissonClient.getBucket(rechargeData.getCustId()).get())
                  .equals(resForm.getSeq_no())) {
                logger.info("redis 判断 已经调用过");
                return resMap;
              }
            }
            // jmsProvider.sendMessage(Constant.MQ_GED_REPAYMENT_RECHARGE_BORROW, record);
            pushToBorrowRepayment(record, user);
            return resMap;
          } else {
            resMap.put("amount", rechargeData.getAmount());
            resMap.put("status", "0");
            resMap.put("res_code", "0001");
            resMap.put("res_msg", "充值(还款)失败");
            resMap.put("deducturl", "0");
            return resMap;
          }

        }

      }
    }

  }

  private JSONObject pushToBorrowRepayment(GedRepaymentRecord record, User user) throws Exception {
    logger.info("-------------------调用借款端进行还款   beigin--------------------------------");

    GedOrder order = this.selectOrderByOrderNo(record.getOrderNo());
    if (order == null)
      throw new BusinessException(Errors.SYSTEM_DATA_ERROR,
          "V2.0>>>>>>>>>>>还款支付成功后调借款端扣款时查询订单失败 orderNo=" + record.getSeqNo());
    Account account = null;
    String mobile = "";
    String bankName = "";
    String bankCardNo = "";
    if (user.getCheckAccountFlag() == 4) {
      account = accountService.getAccountByUserId(order.getUserId());
      mobile = account.getCorporationPhone();
      bankName = account.getCompanyBankOfDeposit();
      bankCardNo = account.getCompanyAccount();
    }
    AccountCompany accountCompany = null;
    if (user.getCompanyAccountFlag() == 7) {
      accountCompany = accountService.getAccountCompanyByUserId(order.getUserId());
      mobile = accountCompany.getLegalMobile();
      bankName = accountCompany.getCompanyBankOfDeposit();
      bankCardNo = accountCompany.getCompanyAccount();
    }

    PushToBorrowRepaymentReqForm reqForm = new PushToBorrowRepaymentReqForm();
    SimpleDateFormat sf = new SimpleDateFormat("HHmmssSSS");

    Map<String, String> map = new HashedMap();
    reqForm.setCustId(user.getGetCustId());// 客户id在冠E通存在的账户id
    reqForm.setContractNo(order.getContractCode());//// 合同号
    reqForm.setPeriodNum(order.getReplyTerm() + "");/// 期数
    reqForm.setDeductCustName(user.getUsername()); // 扣款人Name 扣款人姓名
    reqForm.setDeductApplyNo(record.getSeqNo()); // 资金返回的流水号
    reqForm.setMobileNum(mobile);// 手机号 扣款人
    reqForm.setBankcardNo(bankCardNo); // 银行卡号 扣款人
    reqForm.setBank(bankName);// 银行名称？
    reqForm.setOrderNo(record.getOrderNo());
    reqForm
        .setDeductAmount(record.getRepaymentAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN) + ""); // 充值金额
    // type=1 正常环境 type=2提前还款
    if (record.getType() != null && record.getType() == 1) {
      reqForm.setAdvanceFlag("1");
      reqForm.setAdvanceRepayMoney(null);// 提前还款的违约金
    }
    JSONObject json = repayMent(reqForm);
    logger
        .info("---------------------------调用借款端进行还款end---------------------------------------返回结果："
            + json);
    return json;
  }

  public JSONObject repayMent(PushToBorrowRepaymentReqForm paraForm) throws Exception {
    Map<String, Object> map = new HashedMap();
    map.put("custId", paraForm.getCustId()); //// 客户id在冠E通存在的账户id
    map.put("contractNo", paraForm.getContractNo()); //// 合同号
    // map.put("periodNum", paraForm.getPeriodNum()); ///期数
    map.put("deductCustName", paraForm.getDeductCustName()); // 扣款人Name 扣款人姓名 ---企业名称
    map.put("deductApplyNo", paraForm.getDeductApplyNo()); // 扣款申请序列号 （你们产生）
    map.put("mobileNum", paraForm.getMobileNum()); // 手机号 扣款人
    map.put("bankcardNo", paraForm.getBankcardNo()); // 银行卡号 扣款人
    map.put("bank", paraForm.getBank()); // 开户银行（字典类型：BANKS） 扣款人
    map.put("deductAmount",
        new BigDecimal(paraForm.getDeductAmount()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
    map.put("accountNo", paraForm.getOrderNo());

    // 提前还款的参数
    map.put("advanceRepayMoney", paraForm.getAdvanceRepayMoney());
    map.put("advanceFlag", paraForm.getAdvanceFlag());
    logger.info("--------------------------- 调用借款端进行还款的请求參數--------------------------------------");
    logger.info(JSONObject.toJSONString(map));

    JSONObject jsonObject = HttpUtils.doPost(repaymentBorrowUrl, JSONObject.toJSONString(map));
    redissonClient.getBucket(paraForm.getCustId()).set(paraForm.getDeductApplyNo());// 把用过的流水号存到redis，防止重复推送
    logger.info("调用借款系统还款接口返结果:" + jsonObject);
    return jsonObject;
  }

  /*
   * private JSONObject pushRepayMentToLoan(GedRepaymentRecord record) throws Exception {
   * Map<String, Object> map = new HashedMap(); map.put("custId", record.getCustId()); ////
   * 客户id在冠E通存在的账户id map.put("contractNo", paraForm.getContractNo()); //// 合同号 //
   * map.put("periodNum", paraForm.getPeriodNum()); ///期数 map.put("deductCustName",
   * paraForm.getDeductCustName()); // 扣款人Name 扣款人姓名 ---企业名称 map.put("deductApplyNo",
   * paraForm.getDeductApplyNo()); // 扣款申请序列号 （你们产生） map.put("mobileNum", paraForm.getMobileNum());
   * // 手机号 扣款人 map.put("bankcardNo", paraForm.getBankcardNo()); // 银行卡号 扣款人 map.put("bank",
   * paraForm.getBank()); // 开户银行（字典类型：BANKS） 扣款人 map.put("deductAmount", new
   * BigDecimal(paraForm.getDeductAmount()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
   * map.put("accountNo", paraForm.getOrderNo());
   * logger.info("--------------------------- 调用借款端进行还款的请求參數--------------------------------------"
   * ); logger.info(JSONObject.toJSONString(map));
   * 
   * JSONObject jsonObject = HttpUtils.doPost(repaymentBorrowUrl, JSONObject.toJSONString(map));
   * redissonClient.getBucket(paraForm.getCustId()).set(paraForm.getDeductApplyNo());//
   * 把用过的流水号存到redis，防止重复推送 logger.info("调用借款系统还款接口返结果:" + jsonObject); return jsonObject; }
   */


  private Integer insertOrUpdate(GedRepaymentRecord record) {
    Integer result = 0;
    GedRepaymentRecordExample example = new GedRepaymentRecordExample();
    example.setOrderByClause("create_time desc");
    GedRepaymentRecordExample.Criteria criteria = example.createCriteria();
    criteria.andOrderNoEqualTo(record.getOrderNo());
    List<GedRepaymentRecord> list = gedRepaymentRecordMapper.selectByExample(example);
    if (list == null || list.size() == 0) {
      result = gedRepaymentRecordMapper.insertSelective(record);
      return result;
    }
    record.setId(list.get(0).getId());
    result = gedRepaymentRecordMapper.updateByPrimaryKeySelective(record);
    return result;
  }

  public JSONObject rechargeCallBack(RechargeCallBackForm paraForm) throws Exception {
    Map<String, String> map = new HashedMap();

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

    String jsonObject = HttpUtils.doPostForm(rechargeCallbackUrl, map);
    // logger.info("是否充值成功接口返回结果:"+jsonObject);
    return JSONObject.parseObject(jsonObject);
  }

  private GedRepaymentRecord getGedRepaymentRecordByOrderNo(String orderNo) {
    GedRepaymentRecordExample example = new GedRepaymentRecordExample();
    example.setOrderByClause("create_time desc");
    GedRepaymentRecordExample.Criteria criteria = example.createCriteria();
    criteria.andSeqNoEqualTo(orderNo);
    List<GedRepaymentRecord> list = gedRepaymentRecordMapper.selectByExample(example);
    if (list == null || list.size() == 0)
      return null;
    else
      return list.get(0);

  }



  @Override
  public PageResForm activateUser(User user) throws Exception {
    PageResForm pageResForm = new PageResForm();
    String custId = user.getGetCustId();
    // 更新用户信息
    // userService.updateUserCustId(custId, user.getId());
    String seqNo = getSeqNo();
    // V2.0 --- 用户激活（个人）
    Map<String, String> dataMap = new HashMap<String, String>(3);
    dataMap.put("custId", user.getGetCustId());// 客户Id
    // dataMap.put("remark", "");
    String data = JSON.toJSONString(dataMap);

    RsaKey rsaKey = getKeys();
    String sysprkey = rsaKey.getPrivateKey();
    String str = mchn + "|" + seqNo + "|" + activateUserTradeType + "|" + data + "|"
        + activateUserPageUrl + "|" + activateUserCallbackUrl;
    String signatureStr = RSAUtils.sign(str.getBytes(), sysprkey);
    String signature = URLEncoder.encode(signatureStr, "UTF-8");
    logger.info("用户激活接口生成的签名：" + signature);
    StringBuilder sbd = new StringBuilder();
    sbd.append(activateUserUrl).append("?").append("mchn=");
    sbd.append(mchn).append("&seq_no=").append(seqNo).append("&trade_type=")
        .append(activateUserTradeType);
    sbd.append("&page_url=").append(activateUserPageUrl).append("&back_url=")
        .append(activateUserCallbackUrl);
    sbd.append("&data=").append(URLEncoder.encode(data, "UTF-8")).append("&signature=")
        .append(signature);
    logger.info("用户激活返回给前端请求的url：" + sbd.toString());

    pageResForm.setRedirectUrl(sbd.toString());
    return pageResForm;
  }

  private RsaKey getKeys() {
    RsaKeyExample example = new RsaKeyExample();
    example.createCriteria().andTypeEqualTo(2);// 资金公私钥
    List<RsaKey> rsaKeys = rsaKeyMapper.selectByExample(example);
    RsaKey rsaKey = null;
    if (rsaKeys != null && rsaKeys.size() > 0) {
      rsaKey = rsaKeys.get(0);
    }
    return rsaKey;
  }

  @Override
  public Map<String, Object> activateUserCallBack(CapitalResForm resForm) throws Exception {
    // 更新用户信息
    // userService.updateUserCustId(custId, user.getId());
    logger.info("用户激活(个人)  >>>>>资金系统回调返回的数据：" + JSON.toJSONString(resForm));
    String data = resForm.getData();
    ObjectMapper mapper = new ObjectMapper();
    ActivateUser activateUser =
        mapper.readValue(URLDecoder.decode(data, "UTF-8"), ActivateUser.class);
    User user = userService.getUserByCustId(activateUser.getCustId());
    Account account = new Account();
    Map<String, Object> resMap = new HashMap<String, Object>();
    if (user != null && user.getStatus() != 2) { // 不是老用户
      resMap.put("res_code", "0000");
      resMap.put("res_msg", "用户激活回调成功");
      return resMap;
    } else {
      if (!resForm.getResp_code().equals("0000")) {
        String msg = resForm.getResp_msg();
        resMap.put("res_code", "0001");
        return resMap;
        // throw new BusinessException(Errors.USER_REGISTER_ERROR, msg);
      } else {
        account.setStatus(1);// 已开户
        account.setAccountCode(resForm.getSeq_no());// 账户编号(当前时间+随机数)
        account.setUserId(user.getId());// 客户ID
        account.setCustId(activateUser.getCustId());
        account.setCorporationPhone(activateUser.getMobile());// 客户银行预留手机号
        account.setCorporationCardNum(activateUser.getIdCardNo());// 身份证号
        account.setCorporationName(activateUser.getCustName());// 客户真实姓名
        account.setCompanyAccount(activateUser.getBankcardNo());// 银行卡号
        account.setCompanyBankOfDeposit(activateUser.getBankName());// 开户行名称
        account.setCompanyBankOfDepositValue(activateUser.getBankCode());// 开户行编码
        account.setCreateId(user.getId());
        account.setCreateTime(new Date());
        int result = accountMapper.insertSelective(account);// 插入个人开户表中
        if (result > 0) {
          // 更新用户激活信息
          userService.updateActiveUserFlag(user.getId(), activateUser, 1);
          // 需要同步借款系统吗??
          // 把用户激活信息推送给冠e通
          userActivationCallback(resForm, data, user);
          resMap.put("res_code", "0000");
          resMap.put("res_msg", "用户激活成功");
        } else {
          resMap.put("res_code", "0001");
          resMap.put("res_msg", "用户激活失败");
        }

        return resMap;
      }
    }


  }



  @Override
  public RechargeFeePageResForm toChargePage(ChargeReqForm chargeReqForm, User user)
      throws Exception {
    RechargeFeePageResForm pageResForm = new RechargeFeePageResForm();
    RechargeFeeForm rechargeFeeForm = new RechargeFeeForm();
    /*
     * rechargeFeeForm.setAmount(amount); rechargeFeeForm.setCustId(custId);
     */
    rechargeFeeForm.setOrderNo(chargeReqForm.getOrderNo());
    GedRechargeRecord rechargeRecord =
        rechargeRecordService.getRechargeRecordByOrderNoAndAmount(rechargeFeeForm);
    // 已经充值过(查询条件里查询的是充值支付成功的记录)
    if (rechargeRecord != null) {
      throw new BusinessException(Errors.ALREADY_RECHARGED);// 已充值过
    }

    User dataUser = userService.getUserById(user.getId());
    String custId = dataUser.getGetCustId();

    String seqNo = getSeqNo();
    GedOrder gedOrder = orderService.getGedOrderByOrderNo(chargeReqForm.getOrderNo());
    BigDecimal amount = new BigDecimal(0);
    if (gedOrder != null)
      amount = gedOrder.getServiceFee();

    // V2.0 --- 网银充值
    Map<String, Object> dataMap = new HashMap<String, Object>();
    dataMap.put("custId", custId);// 客户Id
    dataMap.put("mobile", user.getMobile());// 客户手机号
    dataMap.put("amount", amount);// 充值金额
    dataMap.put("chargeType", 1);// 充值方式 1 网银 2 快捷
    dataMap.put("custType", "1");// 账户类型
    dataMap.put("remark", chargeReqForm.getOrderNo());
    String data = JSON.toJSONString(dataMap);

    RsaKey rsaKey = getKeys();
    String sysprkey = rsaKey.getPrivateKey();
    String str = mchn + "|" + seqNo + "|" + rechargeTradeType + "|" + data + "|" + chargesPageUrl
        + "|" + chargesRechargeCallbackUrl;

    String signatureStr = RSAUtils.sign(str.getBytes(), sysprkey);
    String signature = URLEncoder.encode(signatureStr, "UTF-8");
    logger.info("网银充值缴费接口生成的签名：" + signature);
    StringBuilder sbd = new StringBuilder();
    sbd.append(rechargeUrl).append("?").append("mchn=");
    sbd.append(mchn).append("&seq_no=").append(seqNo).append("&trade_type=")
        .append(rechargeTradeType);
    /*
     * if(user.getUserRole() > 0){ chargesPageUrl =
     * "http://10.100.161.215:9090/v2/pc/page/guarantorPayCallBack"; }
     */

    logger.info("网银充值传给资金的pageUrl：" + chargesPageUrl);
    sbd.append("&page_url=").append(chargesPageUrl).append("&back_url=")
        .append(chargesRechargeCallbackUrl);
    sbd.append("&data=").append(URLEncoder.encode(data, "UTF-8")).append("&signature=")
        .append(signature);
    logger.info("网银充值（缴费用）接口返回给前端请求的(充值页面)url：" + sbd.toString());
    pageResForm.setRedirectUrl(sbd.toString());
    return pageResForm;
  }

  @Override
  public PageResForm toChargePageGuarantor(User user) throws Exception {
    PageResForm pageResForm = new PageResForm();
    User dataUser = userService.getUserById(user.getId());
    String custId = dataUser.getGetCustId();
    String seqNo = getSeqNo();
    GedOrder gedOrder = orderService.getNewOrderByUserId(dataUser.getId());
    // V2.0 --- 网银充值
    Map<String, Object> dataMap = new HashMap<String, Object>();
    dataMap.put("custId", custId);// 客户Id
    dataMap.put("mobile", user.getMobile());// 客户手机号
    dataMap.put("amount", new BigDecimal(0.00));// 充值金额(不需页面输入的话，传默认值0.00)
    dataMap.put("chargeType", 1);// 充值方式 1 网银 2 快捷
    dataMap.put("custType", "1");// 账户类型
    dataMap.put("remark", "");// 订单号，暂时不传
    String data = JSON.toJSONString(dataMap);

    RsaKey rsaKey = getKeys();
    String sysprkey = rsaKey.getPrivateKey();
    String str = mchn + "|" + seqNo + "|" + rechargeTradeType + "|" + data + "|" + chargesPageUrl
        + "|" + chargesRechargeCallbackUrl;

    String signatureStr = RSAUtils.sign(str.getBytes(), sysprkey);
    String signature = URLEncoder.encode(signatureStr, "UTF-8");
    logger.info("网银充值缴费接口生成的签名：" + signature);
    StringBuilder sbd = new StringBuilder();
    sbd.append(rechargeUrl).append("?").append("mchn=");
    sbd.append(mchn).append("&seq_no=").append(seqNo).append("&trade_type=")
        .append(rechargeTradeType);

    logger.info("网银充值传给资金的pageUrl：" + chargesPageUrl);
    sbd.append("&page_url=").append(chargesPageUrl).append("&back_url=")
        .append(chargesRechargeCallbackUrl);
    sbd.append("&data=").append(URLEncoder.encode(data, "UTF-8")).append("&signature=")
        .append(signature);
    logger.info("网银充值（缴费用）接口返回给前端请求的(充值页面)url：" + sbd.toString());

    pageResForm.setRedirectUrl(sbd.toString());

    return pageResForm;
  }


  @Override
  public Map<String, Object> chargesCallBack(CapitalResForm resForm) throws Exception {
    Map<String, Object> resMap = new HashMap<String, Object>();

    logger.info("费用收取 >>>>>资金系统回调返回的数据：" + JSON.toJSONString(resForm));
    String data = resForm.getData();
    ObjectMapper mapper = new ObjectMapper();
    Charges chargesData = mapper.readValue(URLDecoder.decode(data, "UTF-8"), Charges.class);
    User user = userService.getUserByCustId(chargesData.getCustId());
    GedOrder gedOrder = orderService.getNewOrderByUserId(user.getId());
    GedRechargeRecord gedRechargeRecord =
        rechargeRecordService.getRechargeRecordByCustId(chargesData.getCustId());



    if (!resForm.getResp_code().equals("0000")) {
      String msg = resForm.getResp_msg();
      throw new BusinessException(Errors.ONLINE_BANKING_RECHARGE_ERROR, msg);
    } else {
      String tradeTimeStr = chargesData.getTradeDate() == null ? null : chargesData.getTradeDate();
      Date trateTime = null;
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      if (StringUtils.isNotBlank(tradeTimeStr)) {
        trateTime = format.parse(tradeTimeStr);
      }

      if (gedOrder == null) {
        throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR);
      }

      // 更新订单表服务费标识为已缴费
      GedOrder serviceFeeOrder = new GedOrder();
      serviceFeeOrder.setServiceFeeFlag(1);// 已缴费
      serviceFeeOrder.setModifyTime(new Date());
      serviceFeeOrder.setOrderCode(gedOrder.getOrderCode());
      gedOrderMapper.updateByOrderCodeSelective(serviceFeeOrder);

      gedRechargeRecord.setSeqNo(resForm.getSeq_no());
      // userRechargeRecord.setMobileNo(chargesData.getMobile() == null ? null :
      // chargesData.getMobile());
      gedRechargeRecord.setRechargeAmount(chargesData.getAmount());
      gedRechargeRecord.setRespCode(resForm.getResp_code());
      gedRechargeRecord.setCustNo(chargesData.getCustId());
      gedRechargeRecord.setType(4);// 1服务费充值2还款充值 3提现 4缴费
      gedRechargeRecord.setCreateTime(new Date());
      gedRechargeRecord.setTradeDate(trateTime);
      gedRechargeRecord.setStatus(3);// 0未充值1充值成功2充值失败3缴费成功5充值中
      logger.info("缴费回调接口插入的数据：" + JSON.toJSONString(gedRechargeRecord));
      rechargeRecordMapper.updateByPrimaryKeySelective(gedRechargeRecord);// 插入充值记录表中
    }

    // 同步借款系统
    // callLoanCreatePersonAccount(chargesData, user.getId(), 2);
    logger.info("缴费结果回调 返回结果：" + JSON.toJSONString(resMap));
    return resMap;
  }

  @Override
  public Map<String, Object> queryAccountInfo(Long userId) throws Exception {
    Map<String, Object> resMap = new HashMap<String, Object>();

    AccountExample example = new AccountExample();
    example.createCriteria().andUserIdEqualTo(userId);
    List<Account> personList = accountMapper.selectByExample(example);

    AccountCompanyExample companyExample = new AccountCompanyExample();
    companyExample.createCriteria().andUserIdEqualTo(userId);
    List<AccountCompany> enterpriseList = companyMapper.selectByExample(companyExample);


    if (personList != null && personList.size() > 0) {
      Account account = personList.get(0);
      AccountVo accountVo = new AccountVo();
      BeanCopyTools.copyProperties(account, accountVo);
      accountVo.setAccountFlag(1);// 个人开户
      resMap.put("data", accountVo);
      return resMap;
    } else if (enterpriseList != null && enterpriseList.size() > 0) {
      AccountCompany accountCompany = enterpriseList.get(0);
      AccountCompanyVo accountCompanyVo = new AccountCompanyVo();
      BeanCopyTools.copyProperties(accountCompany, accountCompanyVo);
      accountCompanyVo.setAccountFlag(2);// 企业开户
      resMap.put("data", accountCompanyVo);
      return resMap;
    } else {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("accountFlag", 0);// 未开户
      resMap.put("data", map);
      return resMap;
    }

  }

  @Override
  public AccountFlagNewResForm queryUserAccountFlag(User user) {
    AccountExample example1 = new AccountExample();
    example1.createCriteria().andUserIdEqualTo(user.getId());
    List<Account> list1 = accountMapper.selectByExample(example1);
    Account account = getAccountByUserId(user.getId());
    AccountCompany accountCompany = getAccountCompanyByUserId(user.getId());
    AccountFlagNewResForm resForm = new AccountFlagNewResForm();
    resForm.setAccountFlag(0);// 未开户
    if (account != null) {
      if (account.getStatus() != null) {
        resForm.setAccountFlag(account.getStatus());
        resForm.setAccount(account);
      }
    }

    if (accountCompany != null) {
      if (accountCompany.getStatus() != null) {
        resForm.setAccountFlag(accountCompany.getStatus());// 企业开户状态
        resForm.setAccountCompany(accountCompany);
      }
    }
    return resForm;
  }

  @Override
  public Map<String, Object> paychargeCallBack(CapitalResForm resForm) throws Exception {
    Map<String, Object> resMap = new HashMap<String, Object>();
    // 加锁
    synchronized (this) {
      logger.info("网银充值(缴费业务)>>>>>资金系统回调返回的数据：" + JSON.toJSONString(resForm));
      String data = resForm.getData();
      ObjectMapper mapper = new ObjectMapper();
      OnlineBankingRecharge rechargeData =
          mapper.readValue(URLDecoder.decode(data, "UTF-8"), OnlineBankingRecharge.class);
      GedRechargeRecord gedRechargeRecord = new GedRechargeRecord();
      // 用订单号查询充值记录改为用流水号查询查询充值记录
      if (StringUtils.isNotBlank(resForm.getSeq_no())) {
        gedRechargeRecord = rechargeRecordService.getRechargeRecordBySeqNo(resForm.getSeq_no());
      }
      if (redissonClient.getBucket(rechargeData.getCustId() + resForm.getSeq_no()).isExists()) {
        resMap.put("res_code", "0000");
        resMap.put("status",
            gedRechargeRecord.getStatus() == null ? 0 : gedRechargeRecord.getStatus());
        resMap.put("amount", gedRechargeRecord.getRechargeAmount() == null ? 0
            : gedRechargeRecord.getRechargeAmount());
        resMap.put("orderNo", rechargeData.getRemark());// 订单号
        resMap.put("res_msg", "充值成功");
        return resMap;
      }

      if (gedRechargeRecord != null) {
        resMap.put("res_code", "0000");
        resMap.put("status", gedRechargeRecord.getStatus());
        resMap.put("amount", gedRechargeRecord.getRechargeAmount());
        resMap.put("orderNo", rechargeData.getRemark());// 订单号
        resMap.put("res_msg", "充值成功");
        return resMap;
      } else {
        if (!resForm.getResp_code().equals("0000")) {
          String msg = resForm.getResp_msg();
          resMap.put("res_code", "0001");
          resMap.put("status", "2");
          resMap.put("amount", rechargeData.getAmount() == null ? 0 : rechargeData.getAmount());
          resMap.put("orderNo", rechargeData.getRemark());
          resMap.put("res_msg", msg);
          return resMap;
        } else {
          String tradeTimeStr =
              rechargeData.getTradeDate() == null ? null : rechargeData.getTradeDate();
          Date trateTime = null;
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          if (StringUtils.isNotBlank(tradeTimeStr)) {
            trateTime = format.parse(tradeTimeStr);
          }

          GedRechargeRecord rechargeRecord = new GedRechargeRecord();
          rechargeRecord.setSeqNo(resForm.getSeq_no());// 第三方流水号
          rechargeRecord.setOrderNo(rechargeData.getRemark());// 对应订单号
          rechargeRecord.setMobileNo(rechargeData.getMobile());
          rechargeRecord.setRechargeAmount(rechargeData.getAmount());
          rechargeRecord.setRespCode(resForm.getResp_code());
          rechargeRecord.setCustNo(rechargeData.getCustId());
          rechargeRecord.setType(1);// 1服务费充值2还款充值 3提现
          rechargeRecord.setCreateTime(new Date());
          rechargeRecord.setTradeDate(trateTime);
          rechargeRecord.setStatus(1);// 0未充值1充值成功2充值失败3缴费成功5充值中
          Integer result = rechargeRecordMapper.insertSelective(rechargeRecord);// 插入充值记录表
          /*
           * 调用缴费接口---直连 改为页面 RechargeReqForm rechargeReqForm = new RechargeReqForm();
           * charge(user,rechargeReqForm);
           */
          if (result > 0) {
            redissonClient.getBucket(rechargeData.getCustId() + resForm.getSeq_no())
                .set(resForm.getSeq_no());// 用过的流水号放到redis中
            resMap.put("res_code", "0000");
            resMap.put("status", "1");
            resMap.put("amount", rechargeRecord.getRechargeAmount());
            resMap.put("orderNo", rechargeData.getRemark());// 订单号
            resMap.put("res_msg", "充值成功");
            return resMap;
          } else {
            resMap.put("res_code", "0001");
            resMap.put("status", "2");
            resMap.put("amount", rechargeData.getAmount());
            resMap.put("orderNo", rechargeData.getRemark());// 订单号
            resMap.put("res_msg", "冠e贷充值失败");
            return resMap;
          }
        }
      }
    }
  }


  private User getCustId(User user, String busiType) throws Exception {

    String custId = "";
    synchronized (user) {
      User userLocal = userService.getUserById(user.getId());
      if (userLocal != null && StringUtils.isNotBlank(userLocal.getGetCustId())) {
        custId = userLocal.getGetCustId();
      } else {
        String mobile = userLocal.getMobile() == null ? "" : userLocal.getMobile();
        JSONObject getCustJsonObj = callGetPlatformCreateCusId(mobile, busiType);
        if (!getCustJsonObj.getBoolean("isSuccess")) {
          String msg = getCustJsonObj.getString("msg");
          userLocal = null;
          throw new BusinessException(Errors.USER_CREATE_ACCOUNT_ERROR, msg);
        } else {
          String gedJsonStr = getCustJsonObj.getString("data");
          custId = JSONObject.parseObject(gedJsonStr).getString("custId");
          Map<String, Object> reqMap = new HashedMap();
          reqMap.put("userId", user.getId());
          reqMap.put("custId", custId);
          // jmsProvider.sendMessage(Constant.MQ_GED_USER_UPDATE_CUSTID,
          // JSONObject.toJSONString(reqMap));
          User user1 = new User();
          user1.setId(userLocal.getId());
          user1.setGetCustId(custId);
          logger.info("开户更新用户表的信息：" + JSON.toJSONString(user1));
          userMapper.updateByPrimaryKeySelective(user1);
        }
        userLocal.setGetCustId(custId);
        userLocal.setMobile(userLocal.getMobile());
      }

      return userLocal;
    }
  }

  @Override
  public OrderDetailResForm getOrderDetailInfo(String orderCode, Long userId) {
    GedOrderExample example = new GedOrderExample();
    example.createCriteria().andOrderCodeEqualTo(orderCode);
    List<GedOrder> list = gedOrderMapper.selectByExample(example);
    if (list.size() == 0) {
      throw new BusinessException(Errors.SYSTEM_DATA_NOT_FOUND);
    }
    GedOrder gedOrder = list.get(0);
    // 查询账户信息

    OrderDetailResForm resultForm = new OrderDetailResForm();
    // List<Account> listAccount = accountService.getAccountInfo(userId);
    AccountExample accountExample = new AccountExample();
    accountExample.createCriteria().andUserIdEqualTo(userId);
    List<Account> personList = accountMapper.selectByExample(accountExample);

    AccountCompanyExample companyExample = new AccountCompanyExample();
    companyExample.createCriteria().andUserIdEqualTo(userId);
    List<AccountCompany> enterpriseList = companyMapper.selectByExample(companyExample);

    AccountResForm resForm = new AccountResForm();
    // 个人开户
    if (personList != null && personList.size() > 0) {
      Account account = personList.get(0);

      resForm.setBankOfDeposit(account.getCompanyBankOfDeposit());
      resForm.setCardNum(account.getCompanyAccount());
      resForm.setCorporationName(account.getCorporationName());
      resForm.setStatus(account.getStatus());
      resultForm.setAccountResForm(resForm);
      // 企业开户
    } else if (enterpriseList != null && enterpriseList.size() > 0) {
      AccountCompany accountCompany = enterpriseList.get(0);
      resForm.setBankOfDeposit(accountCompany.getCompanyBankOfDeposit());
      resForm.setCardNum(accountCompany.getCompanyAccount());
      // resForm.setCompanyName(account.getCompanyName());
      resForm.setCorporationName(accountCompany.getCompanyName());
      resForm.setStatus(accountCompany.getStatus());
      resultForm.setAccountResForm(resForm);
    } else {
      resForm.setStatus(0);
      resultForm.setAccountResForm(resForm);
    }
    resultForm.setLoanAmount(gedOrder.getLoanAmount());
    resultForm.setLoanTerm(gedOrder.getLoanTerm() + "个月(期)");
    if (gedOrder.getLoanPurpose() != null) {
      resultForm.setLoanPurpose(
          dictionaryService.getDictionaryValue("LOAN_PURPOST", gedOrder.getLoanPurpose()));
    }
    // 查询还款日
    OrderLog orderLog =
        orderLogService.getOrderLogByOrderId(gedOrder.getId(), OrderStatus.FKCG.getCode());
    if (orderLog != null) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(orderLog.getCreateTime());
      Calendar calendarEnd = Calendar.getInstance();
      calendarEnd.setTime(orderLog.getCreateTime());
      calendarEnd.add(Calendar.MONTH, gedOrder.getLoanTerm());
      resultForm.setStartTime(DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd"));
      resultForm.setEndTime(DateUtils.formatDate(calendarEnd.getTime(), "yyyy-MM-dd"));
      resultForm.setPaymentDay("每月" + getRepaymentDay(calendar) + "日");
    }
    if (gedOrder.getRateDay() != null) {
      resultForm.setRateDay(gedOrder.getRateDay() + "%");
    }
    resultForm.setServiceFee(gedOrder.getLoanAmount().intValue() * 0.001 + "/月");
    if (gedOrder.getLoanType() != null) {
      resultForm
          .setLoanType(dictionaryService.getDictionaryValue("LOAN_TYPE", gedOrder.getLoanType()));
    }
    resultForm.setStatus(OrderStatus.resove(gedOrder.getStatus()).getFeStatusDesc());
    logger.info(JSONObject.toJSONString(resultForm));
    return resultForm;
  }


  @Override
  public int personAccountSuccess(CapitalResForm reqForm) {
    if (reqForm.getResp_code().equals("0000")) {
      logger.info("开户成功");
      return 1;
    } else {
      logger.info("开户失败");
      return 0;
    }
  }

  @Override
  public Account getAccountByUserId(long userId) {
    AccountExample accountExample = new AccountExample();
    accountExample.createCriteria().andUserIdEqualTo(userId);
    List<Account> accountList = accountMapper.selectByExample(accountExample);
    Account account = null;
    if (accountList != null && accountList.size() > 0) {
      account = accountList.get(0);
    }
    return account;
  }

  @Override
  public AccountCompany getAccountCompanyByUserId(long userId) {
    AccountCompanyExample companyExample = new AccountCompanyExample();
    companyExample.createCriteria().andUserIdEqualTo(userId);
    List<AccountCompany> companyList = companyMapper.selectByExample(companyExample);
    AccountCompany accountCompany = null;
    if (companyList != null && companyList.size() > 0) {
      accountCompany = companyList.get(0);
    }
    return accountCompany;
  }


  // 查询订单表
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


  public int getRepaymentDay(Calendar calendar) {
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    if (day > 1) {
      return day - 1;
    } else {
      calendar.add(Calendar.MONTH, -1);
      return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
  }

  @Override
  public WithDrawResForm withDraw(User userInfo, String orderNo) throws Exception {
    WithDrawResForm resForm = new WithDrawResForm();
    BigDecimal accountBalan = queryAccountBalance(userInfo.getId());
    BigDecimal withDraw = new BigDecimal(0.0);
    resForm.setAccountBalance(accountBalan);// 账户余额
    GedOrder gedOrder = selectOrderByOrderNo(orderNo);
    if (gedOrder == null) {
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR);
    }

    AccountExample example = new AccountExample();
    example.createCriteria().andUserIdEqualTo(userInfo.getId());
    List<Account> personList = accountMapper.selectByExample(example);

    AccountCompanyExample companyExample = new AccountCompanyExample();
    companyExample.createCriteria().andUserIdEqualTo(userInfo.getId());
    List<AccountCompany> enterpriseList = companyMapper.selectByExample(companyExample);

    if (personList != null && personList.size() > 0) {
      Account account = personList.get(0);
      resForm.setCorporationName(account.getCorporationName());// 法人姓名
      resForm.setCorporationAccount(account.getCompanyAccount());// 个人账户
      resForm.setCorporationBankOfDeposit(account.getCompanyBankOfDeposit());// 个人开户行
    } else if (enterpriseList != null && enterpriseList.size() > 0) {
      AccountCompany accountCompany = enterpriseList.get(0);
      resForm.setCorporationName(accountCompany.getCompanyName());// 公司名称
      resForm.setCorporationAccount(accountCompany.getCompanyAccount());// 个人对公账户
      resForm.setCorporationBankOfDeposit(accountCompany.getCompanyBankOfDeposit());// 开户行编码
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
    Integer serviceFeeFlag =
        gedOrder.getServiceFeeFlag() == null ? 0 : gedOrder.getServiceFeeFlag();// 服务费标识
    Integer guaranteeFeeFlag =
        gedOrder.getGuaranteeFeeFlag() == null ? 0 : gedOrder.getGuaranteeFeeFlag();// 担保费标识
    if (serviceFeeFlag == 0 || guaranteeFeeFlag == 0) {// 服务费 保障金未缴费 提现60%
      if (withDrawFlag == WithDrawFlag.fIRST_WITHDRAWING.getKey()
          || withDrawFlag == WithDrawFlag.FIRST_WITHDRAWFINISH.getKey()) {// 提现中 但是没还有回调回来或者回调回来 //
        // 提现40%
        withDraw = gedOrder.getCreditAmount().multiply(new BigDecimal(0.4)).setScale(2,
            BigDecimal.ROUND_HALF_DOWN);
      } else if (gedOrder.getWithdrawAmount() != null) {
        withDraw = gedOrder.getCreditAmount().subtract(gedOrder.getWithdrawAmount()).setScale(2,
            BigDecimal.ROUND_HALF_DOWN);
      } else
        withDraw = gedOrder.getCreditAmount().multiply(new BigDecimal(0.6)).setScale(2,
            BigDecimal.ROUND_HALF_DOWN);
    } else if (serviceFeeFlag == 1 && guaranteeFeeFlag == 1) {// 服务费和担保费都已缴费
      if (gedOrder.getWithdrawAmount() != null) { // 交了服务费 回调回来 提现剩下的40%
        withDraw = gedOrder.getCreditAmount().subtract(gedOrder.getWithdrawAmount()).setScale(2,
            BigDecimal.ROUND_HALF_DOWN);
      } else {
        if (withDrawFlag == WithDrawFlag.fIRST_WITHDRAWING.getKey()) {// 交了服务费 但是没还有回调回来 2
          withDraw = gedOrder.getCreditAmount().multiply(new BigDecimal(0.4)).setScale(2,
              BigDecimal.ROUND_HALF_DOWN);
        } else if (withDrawFlag == WithDrawFlag.FIRST_WITHDRAWFINISH.getKey()) {
          withDraw = gedOrder.getCreditAmount().multiply(new BigDecimal(0.4)).setScale(2,
              BigDecimal.ROUND_HALF_DOWN);
        } else {
          withDraw = gedOrder.getCreditAmount();// 已交服务费 全额提现
        }
      }
    }
    return withDraw;
  }


  // 根据订单编号返回订单信息
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

  @Override
  /*
   * public BigDecimal accountBalance(String orderNo, Long userId) throws Exception { Date
   * currentDate = new Date(); User user = userService.getUserById(userId); String dataTime =
   * DateFormatUtils.formatDate(currentDate, "yyyyMMddHHmmssSSS");
   * logger.info("账户余额交易流水号===========================" + dataTime); Map<String, String> param = new
   * HashMap<String, String>(); param.put("cust_no", user.getGetCustId());// 测试6666 //
   * param.put("cust_no", "6666"); param.put("busi_type", "1"); param.put("mchn", mchn);//
   * 系统代码88721657SUKQ param.put("seq_no", dataTime);// 交易流水号 param.put("trade_type",
   * accountBalanceTradeType);// 交易类型11110003 // 账户余额 String loanJson =
   * HttpUtils.doPostForm(accountBalanceUrl, param); logger.info("账户余额返回的对象" + loanJson); JSONObject
   * jsonObject = JSONObject.parseObject(loanJson); if
   * (jsonObject.getString("resp_code").equals("0000")) { logger.info("账户余额：" +
   * jsonObject.getBigDecimal("amount")); return jsonObject.getBigDecimal("amount"); } return new
   * BigDecimal(0); }
   */


  /**
   * 银行卡变更
   * 
   * @param user
   * @return
   * @throws Exception
   */
  public PageResForm bankCardChange(User user) throws Exception {
    PageResForm pageResForm = new PageResForm();
    logger.info("============银行卡变更接口==========》");
    String custId = user.getGetCustId();// 获取客户id
    String seqNo = getSeqNo();// 生成唯一序列号
    Map<String, Object> data = new HashMap<String, Object>(2);// 数据域参数
    data.put("custId", "622610");// 客户Id
    data.put("remark", "");// 备用
    String jsonData = JSON.toJSONString(data);
    logger.info("jsonData:" + jsonData);
    // 获取公钥和私钥
    RsaKey rsaKey = getKeys();
    String sysprkey = rsaKey.getPrivateKey();
    // 生成签名
    String signature = "";
    String str = mchn + "|" + seqNo + "|" + bankCardChangeTradeType + "|" + jsonData + "|"
        + bankCardChangePageUrl + "|" + bankCardChangeCallBackUrl;
    signature = RSAUtils.sign(str.getBytes(), sysprkey);
    logger.info("数字签名=======>" + signature);
    StringBuilder ret = new StringBuilder();
    ret.append(bankCardChangeUrl).append("?mchn=").append(mchn).append("&seq_no=").append(seqNo)
        .append("&trade_type=").append(bankCardChangeTradeType).append("&page_url=")
        .append(bankCardChangePageUrl).append("&back_url=").append(bankCardChangeCallBackUrl)
        .append("&data=").append(URLEncoder.encode(JSON.toJSONString(data), "UTF-8"))
        .append("&signature=").append(signature);
    logger.info("请求完整路径======>" + ret.toString());
    pageResForm.setRedirectUrl(ret.toString());
    return pageResForm;
  }

  /**
   * 银行卡变更回调接口
   * 
   * @param resForm
   */
  @Override
  public Map<String, Object> bankCardChangePageCallBack(CapitalResForm resForm) throws IOException {
    logger.info("银行卡变更(个人)  >>>>>资金系统回调返回的数据：" + JSON.toJSONString(resForm));
    Map<String, Object> resMap = new HashMap();

    String data = resForm.getData();
    ObjectMapper mapper = new ObjectMapper();
    BankCardChanger bankCardChanger =
        mapper.readValue(URLDecoder.decode(data, "UTF-8"), BankCardChanger.class);
    User user = userService.getUserByCustId(bankCardChanger.getCustId());

    Account account = new Account();
    Account oldAccount = getAccountByUserId(user.getId());
    if (oldAccount != null) {
      resMap.put("resp_code", "0000");
      resMap.put("resp_msg", "银行卡变更接口回调成功");
    }
    if (!resForm.getResp_code().equals("0000")) {
      String msg = resForm.getResp_msg();
      // throw new BusinessException(Errors.USER_REGISTER_ERROR, msg);
      resMap.put("resp_code", "0001");
      resMap.put("resp_msg", msg);
    } else {
      account.setStatus(1);// 已开户
      account.setAccountCode(resForm.getSeq_no());// 账户编号(当前时间+随机数)
      account.setUserId(user.getId());// 客户ID
      account.setCorporationPhone(bankCardChanger.getMobile());// 客户银行预留手机号
      account.setCorporationCardNum(bankCardChanger.getIdCardNo());// 身份证号
      account.setCorporationName(bankCardChanger.getCustName());// 客户真实姓名
      account.setCompanyAccount(bankCardChanger.getBankcardNo());// 银行卡号
      account.setCompanyBankOfDeposit(bankCardChanger.getBankName());// 开户行名称
      account.setCompanyBankOfDepositValue(bankCardChanger.getBankCode());// 开户行编码
      account.setCreateId(user.getId());
      account.setCreateTime(new Date());
      AccountExample example = new AccountExample();
      example.createCriteria().andUserIdEqualTo(user.getId());
      int result = accountMapper.updateByExample(account, example);// 插入个人开户表中
      if (result > 0) {
        resMap.put("resp_code", "0000");
        resMap.put("resp_msg", "银行卡变更接口回调成功");
        resMap.put("bankCardNo", bankCardChanger.getBankcardNo());
        resMap.put("mobile", user.getMobile());
      } else {
        resMap.put("resp_code", "0001");
        resMap.put("resp_msg", "银行卡变更接口回调失败");
        resMap.put("bankCardNo", bankCardChanger.getBankcardNo());
        resMap.put("mobile", user.getMobile());
      }

    }

    // 需要同步借款系统吗??
    return resMap;
  }


  /**
   * 网页提现接口PC
   * 
   * @param orderNo
   * @param user
   * @throws Exception
   */
  @Override
  public WithDrawPageResForm toWithdrawPage(String orderNo, User user) throws Exception {
    WithDrawPageResForm pageResForm = new WithDrawPageResForm();
    user = userService.getUserById(user.getId());
    logger.info("============账户网页提现接口调用==========》");
    logger.info("请求参数orderNo=======》" + orderNo);
    String seqNo = getSeqNo();// 流水号，不可重复
    Map<String, Object> data = new HashMap<String, Object>(3);// 数据域参数
    data.put("custId", user.getGetCustId());// 客户Id
    data.put("mobile", user.getMobile());// 客户手机号
    GedOrder gedOrder = selectOrderByOrderNo(orderNo);// 根据订单号获取订单信息
    if (gedOrder == null) { // 订单不存在
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR);
    }

    BigDecimal withDrawAmount = this.withDrawAmount(orderNo);// 计算提现金额
    BigDecimal balance = queryAccountBalance(user.getId());
    // 如果账户余额小于可提现余额
    logger.info("提现金额：" + withDrawAmount);
    logger.info("账户余额：" + balance);
    // 如果提现余额大于账户余额
    logger.info("比较的值：" + (withDrawAmount.subtract(balance).doubleValue() > 0));


    // 计算60%的放款金额
    BigDecimal firstAmount = gedOrder.getCreditAmount().multiply(new BigDecimal(0.6)).setScale(2,
        BigDecimal.ROUND_HALF_DOWN);
    if (firstAmount.subtract(withDrawAmount).doubleValue() != 0) {
      // 未交服务费，或者未交保证金和担保费
      if (gedOrder.getServiceFeeFlag() == 0 || gedOrder.getGuaranteeFeeFlag() == 0) {
        throw new BusinessException(Errors.WITHDRAW_UNPAID_GUARANTEE_DEPOSIT_FEE);
      }
    }

    if (withDrawAmount.subtract(balance).doubleValue() > 0
        || balance.subtract(new BigDecimal(0)).doubleValue() == 0) {
      throw new BusinessException(Errors.WITHDRAW_LACK_BALANCE);
    }

    /*
     * //未交保证金和担保费 if (gedOrder.getServiceFeeFlag() == 1 && gedOrder.getGuaranteeFeeFlag() == 0) {
     * //已交服务费并且未交保证金和担保费 throw new BusinessException(Errors.WITHDRAW_UNPAID_GUARANTEE_DEPOSIT_FEE);
     * }
     */

    Integer serviceFeeFlag = gedOrder.getServiceFeeFlag();// 服务费标识 0未交费 1已缴费
    logger.info("服务费标识=" + serviceFeeFlag);
    /**
     * WithdrawFlag >>> 提款次数判断，如果第二次提现中就提现金额置为0
     */
    if (gedOrder.getWithdrawFlag() != null) {
      if (gedOrder.getWithdrawFlag() == WithDrawFlag.SECOND_WITHDRAWING.getKey()) {// 如果是二次提现处理中 置为0
        withDrawAmount = new BigDecimal(0.0);
      }
    }
    logger.info("放款额度=" + gedOrder.getCreditAmount() + "提现金额=" + withDrawAmount + "提现标志="
        + gedOrder.getWithdrawFlag() + "订单编号=" + orderNo);



    /**
     * 返回提款金额等信息
     */
    data.put("amount", withDrawAmount);// 提现金额
    data.put("commission", 0);// 提现手续费
    data.put("custType", "1");// 账户类型
    data.put("remark", orderNo);// 订单号
    String jsonData = JSON.toJSONString(data);
    logger.info("返回参数信息Data=======》" + jsonData);
    // 获取公钥和私钥
    RsaKey rsaKey = getKeys();
    String sysprkey = rsaKey.getPrivateKey();
    String str = mchn + "|" + seqNo + "|" + withdrawTradeType + "|" + jsonData + "|"
        + withDrawPCPageUrl + "|" + withdrawCallBackUrl;
    String signatureStr = RSAUtils.sign(str.getBytes(), sysprkey);
    String signature = URLEncoder.encode(signatureStr, "UTF-8");

    logger.info("方法签名=======>" + signature);
    /**
     * 生成url前端请求地址
     */
    StringBuilder ret = new StringBuilder();
    ret.append(withDrawUrl).append("?mchn=").append(mchn).append("&seq_no=").append(seqNo)
        .append("&trade_type=").append(withdrawTradeType).append("&page_url=")
        .append(withDrawPCPageUrl).append("&back_url=").append(withdrawCallBackUrl).append("&data=")
        .append(URLEncoder.encode(jsonData, "UTF-8")).append("&signature=").append(signature);
    logger.info("返回值=====>" + ret.toString());
    pageResForm.setRedirectUrl(ret.toString());

    return pageResForm;
  }

  /**
   * 网页提现接口APP
   * 
   * @param orderNo
   * @param user
   * @throws Exception
   */
  @Override
  public WithDrawPageResForm toWithdrawPageAPP(String orderNo, User user) throws Exception {
    WithDrawPageResForm pageResForm = new WithDrawPageResForm();
    user = userService.getUserById(user.getId());
    logger.info("============账户网页提现接口调用==========》");
    logger.info("请求参数orderNo=======》" + orderNo);
    String seqNo = getSeqNo();// 流水号，不可重复
    Map<String, Object> data = new HashMap<String, Object>(3);// 数据域参数
    data.put("custId", user.getGetCustId());// 客户Id
    data.put("mobile", user.getMobile());// 客户手机号
    GedOrder gedOrder = selectOrderByOrderNo(orderNo);// 根据订单号获取订单信息
    if (gedOrder == null) {// 订单不存在
      throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR);
    }
    BigDecimal withDrawAmount = this.withDrawAmount(orderNo);// 计算提现金额
    BigDecimal balance = queryAccountBalance(user.getId());

    // 计算60%的放款金额
    BigDecimal firstAmount = gedOrder.getCreditAmount().multiply(new BigDecimal(0.6)).setScale(2,
        BigDecimal.ROUND_HALF_DOWN);
    if (firstAmount.subtract(withDrawAmount).doubleValue() != 0) {
      // 未交服务费，或者未交保证金和担保费
      if (gedOrder.getServiceFeeFlag() == 0 || gedOrder.getGuaranteeFeeFlag() == 0) {
        throw new BusinessException(Errors.WITHDRAW_UNPAID_GUARANTEE_DEPOSIT_FEE);
      }
    }

    if (withDrawAmount.subtract(balance).doubleValue() > 0
        || balance.subtract(new BigDecimal(0)).doubleValue() == 0) {
      throw new BusinessException(Errors.WITHDRAW_LACK_BALANCE);
    }


    /**
     * WithdrawFlag >>> 提款次数判断，如果第二次提现中就提现金额置为0
     */
    if (gedOrder.getWithdrawFlag() != null) {
      if (gedOrder.getWithdrawFlag() == WithDrawFlag.SECOND_WITHDRAWING.getKey()) {// 如果是二次提现处理中 置为0
        withDrawAmount = new BigDecimal(0.0);
      }
    }
    logger.info("放款额度=" + gedOrder.getCreditAmount() + "提现金额=" + withDrawAmount + "提现标志="
        + gedOrder.getWithdrawFlag() + "订单编号=" + orderNo);
    /**
     * 返回提款金额等信息
     */
    data.put("amount", withDrawAmount);// 提现金额
    data.put("commission", 0);// 提现手续费
    data.put("custType", "1");// 账户类型
    data.put("remark", orderNo);// 订单号
    String jsonData = JSON.toJSONString(data);
    logger.info("返回参数信息Data=======》" + jsonData);
    // 获取公钥和私钥
    RsaKey rsaKey = getKeys();
    String sysprkey = rsaKey.getPrivateKey();
    String str = mchn + "|" + seqNo + "|" + withdrawTradeType + "|" + jsonData + "|"
        + withDrawPageUrl + "|" + withdrawCallBackUrl;
    String signatureStr = RSAUtils.sign(str.getBytes(), sysprkey);
    String signature = URLEncoder.encode(signatureStr, "UTF-8");
    logger.info("方法签名=======>" + signature);
    /**
     * 生成url前端请求地址
     */
    StringBuilder ret = new StringBuilder();
    ret.append(withDrawUrl).append("?mchn=").append(mchn).append("&seq_no=").append(seqNo)
        .append("&trade_type=").append(withdrawTradeType).append("&page_url=")
        .append(withDrawPageUrl).append("&back_url=").append(withdrawCallBackUrl).append("&data=")
        .append(URLEncoder.encode(jsonData, "UTF-8")).append("&signature=").append(signature);
    logger.info("返回值=====>" + ret.toString());
    pageResForm.setRedirectUrl(ret.toString());
    logger.info("冠易贷2.0APP 提现 返回前端 结果:" + JSON.toJSONString(pageResForm));
    return pageResForm;
  }



  /**
   * 网页提现接口回调方法
   *
   * @param resForm
   * @return
   * @throws Exception
   */
  @Override
  public Map<String, Object> withdrawPageCallBack(CapitalResForm resForm) throws Exception {
    synchronized (this) {
      logger.info("=======资金系统提现回调业务处理方法========= 回调数据：" + JSON.toJSONString(resForm));
      Map<String, Object> resMap = new HashMap<String, Object>();
      logger.info("============账户网页提现接口回调==========》");
      String data = URLDecoder.decode(resForm.getData(), "utf-8");
      logger.info("获取数据域：" + data);
      ObjectMapper mapper = new ObjectMapper();
      OnlineBankingRecharge withDrawData = mapper.readValue(data, OnlineBankingRecharge.class);// json反序例化对象
      GedRechargeRecord gedRechargeRecord =
          rechargeRecordService.getWithDrawRecordByOrderNoAndSeqNO(withDrawData.getRemark(),
              withDrawData.getAmount(), resForm.getResp_code());
      GedOrder oldOrder = orderService.getGedOrderByOrderNo(withDrawData.getRemark());// 查询订单
      String bankCode = "";
      if (gedRechargeRecord != null) {
        User user = userService.getUserByCustId(withDrawData.getCustId());

        if (user.getCheckAccountFlag() == 4) {
          Account account = getAccountByUserId(user.getId());
          bankCode = account.getCompanyAccount();
        } else {
          AccountCompany accountCompany = getAccountCompanyByUserId(user.getId());
          bankCode = accountCompany.getCompanyAccount();
        }
        logger.info("redis 已调用过");
        resMap.put("resp_code", "0000");
        resMap.put("resp_msg", "回调成功");
        resMap.put("status", gedRechargeRecord.getStatus());
        resMap.put("serviceFeeFlag", oldOrder.getServiceFeeFlag());
        resMap.put("amount", withDrawData.getAmount());
        resMap.put("bankCardNo", bankCode);
        return resMap;
      }


      String key = withDrawData.getRemark() + "_" + withDrawData.getAmount();
      redissonClient.getBucket(key).set(resForm.getResp_code());
      if (redissonClient.getBucket(key).isExists()) {
        if (redissonClient.getBucket(key).toString().equals(resForm.getResp_code())) {
          logger.info("redis 已调用过");
          resMap.put("resp_code", "0000");
          resMap.put("resp_msg", "回调成功");
          resMap.put("status", oldOrder.getWithdrawFlag());
          resMap.put("serviceFeeFlag",
              oldOrder.getServiceFeeFlag() == null ? 0 : oldOrder.getServiceFeeFlag());
          resMap.put("amount", withDrawData.getAmount());
          resMap.put("bankCardNo", bankCode);
          return resMap;
        }

      }

      GedRechargeRecord rechargeRecord = new GedRechargeRecord();// 提现记录对象
      /*
       * if(oldOrder == null ){ String busiNo = this.getBusiNo(withDrawData.getCustId(),
       * withDrawData.getMobile());//查询合同号 logger.info("合同编号:" + busiNo + "===" + "回调回来的交易流水号" +
       * resForm.getSeq_no()); oldOrder = selectByContract(busiNo);//查询订单 } //订单不存在 if(oldOrder ==
       * null){ throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR); }
       * logger.info("当前提现的订单信息:" + JSON.toJSONString(oldOrder));
       */

      /**
       * 保存提现记录日志
       */
      // 0未提现1一次提现中2一次提现完成3二次提现中4二次提现完成5提现失败
      logger.info("===========保存提现日志记录==============");
      String tradeTimeStr =
          withDrawData.getTradeDate() == null ? null : withDrawData.getTradeDate();
      Date trateTime = null;
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      if (StringUtils.isNotBlank(tradeTimeStr)) {
        trateTime = format.parse(tradeTimeStr);
      }

      rechargeRecord.setOrderNo(withDrawData.getRemark());// 订单号
      rechargeRecord.setMobileNo(withDrawData.getMobile());
      rechargeRecord.setRechargeAmount(withDrawData.getAmount());
      rechargeRecord.setRespCode(resForm.getResp_code());
      rechargeRecord.setCustNo(withDrawData.getCustId());
      rechargeRecord.setType(3); // 1服务费充值2还款充值3网页提现
      rechargeRecord.setTradeDate(trateTime);
      rechargeRecord.setCreateTime(new Date());
      // rechargeRecord.setStatus(1);
      rechargeRecord.setSeqNo(resForm.getSeq_no());// 序列号

      // AccountStatus accountStatus = queryAccStatus(user.getId());
      // 0001 提现受理中 0000 提现成功（已入账） 9999提现失败
      // 0未提现1一次提现中2一次提现完成3二次提现中4二次提现完成5提现失败
      Integer withDrawFlag = withDrawFlag(oldOrder, resForm, withDrawData);//

      Integer orderStatus = 0;// 订单状态
      Integer withDrawStauts = 0;// 提现状态
      Integer loanStauts = 0;// 推送给借款系统的提现状态
      BigDecimal reaTradeAmount = withDrawData.getAmount();// 回调回来的提现金额
      BigDecimal creditAmount = oldOrder.getCreditAmount();// 放款额度
      BigDecimal withDrawAmount = oldOrder.getWithdrawAmount();// 已提现金额

      GedOrder gedOrder = new GedOrder();
      if ("0001".equals(resForm.getResp_code())) {

        // 一次提现处理中
        if (withDrawFlag == 1) {
          orderStatus = OrderStatus.YCTX.getCode();// 提现处理中
          withDrawStauts = WithDrawFlag.fIRST_WITHDRAWING.getKey();
          loanStauts = FirstWithDeposit.FIRST_WITHCASH.getCode();// 首次提现
          rechargeRecord.setStatus(withDrawStauts);// 一次提现处理中
          gedOrder.setStatus(orderStatus); // 设置订单状态
          gedOrder.setWithdrawFlag(withDrawStauts);// 第一次提现中
          gedOrder.setWithdrawAmount(reaTradeAmount);// 提现金额
          // 二次提现处理中
        } else {
          orderStatus = OrderStatus.HKING.getCode();// 还款中
          withDrawStauts = WithDrawFlag.SECOND_WITHDRAWING.getKey();
          loanStauts = FirstWithDeposit.ALREADY_PRESENTED.getCode();// 已提现
          rechargeRecord.setStatus(withDrawStauts);// 二次提现处理中
          gedOrder.setStatus(orderStatus);// 订单状态
          gedOrder.setWithdrawFlag(withDrawStauts);// 提现状态设置为第二次提现中(4)
          gedOrder.setWithdrawAmount(creditAmount);// 提现金额(这里直接设置为放款金额。等于第一次、第二次提现金额的和)
        }

        // 如果响应码等于0000，表示提现成功
      } else if ("0000".equals(resForm.getResp_code())) {
        if (withDrawFlag == 1) {
          orderStatus = OrderStatus.YCTX.getCode();// 提现成功(已提现)
          withDrawStauts = WithDrawFlag.FIRST_WITHDRAWFINISH.getKey();
          loanStauts = FirstWithDeposit.FIRST_WITHCASH.getCode();// 已提现
          rechargeRecord.setStatus(withDrawStauts);// 一次提现成功
          gedOrder.setStatus(orderStatus);// 订单状态
          gedOrder.setWithdrawFlag(withDrawStauts);// 一次提现成功
          gedOrder.setWithdrawAmount(withDrawData.getAmount());// 提现金额(提现中已设置了金额，这里不需要再设置)
        } else {
          orderStatus = OrderStatus.HKING.getCode();// 提现成功(已提现)
          withDrawStauts = WithDrawFlag.SECOND_WITHDEAWFINISH.getKey();
          loanStauts = FirstWithDeposit.ALREADY_PRESENTED.getCode();// 已提现
          rechargeRecord.setStatus(withDrawStauts);// 二次提现成功
          gedOrder.setStatus(orderStatus);// 订单状态
          gedOrder.setWithdrawFlag(withDrawStauts);// 提现状态设置为第二次提现中(4)
          gedOrder.setWithdrawAmount(creditAmount);// 提现金额
        }

      } else {
        orderStatus = OrderStatus.YCTX.getCode();// 待提现（订单状态为这个合适吗？）
        withDrawStauts = WithDrawFlag.FAIL.getKey();// 提现失败 提现失败要不要判断第一次和第二次？
        loanStauts = FirstWithDeposit.FAIL.getCode();// 提现失败
        rechargeRecord.setStatus(withDrawStauts);// 提现失败
        gedOrder.setStatus(orderStatus);// 订单状态
        gedOrder.setWithdrawFlag(withDrawStauts);// 提现失败
        gedOrder
            .setWithdrawAmount(withDrawAmount == null ? new BigDecimal(0)
                : withDrawAmount.subtract(
                    withDrawData.getAmount() == null ? new BigDecimal(0) : withDrawData.getAmount())
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN));// 已提现金额(提现失败，退单。这里已提现余额减去资金回调的金额，对应用户银行卡余额做加法)

      }

      logger.info("订单状态：" + orderStatus);
      logger.info("提现状态：" + withDrawStauts);
      logger.info("推送借款系统提现状态：" + loanStauts);

      Integer result = 0;
      if (gedRechargeRecord != null) {

        rechargeRecord.setId(gedRechargeRecord.getId());
        gedOrder.setId(oldOrder.getId());
        if (!resForm.getResp_code().equals(gedRechargeRecord.getRespCode())) {
          result = rechargeRecordMapper.updateByPrimaryKeySelective(rechargeRecord);// 更新
          gedOrderMapper.updateByPrimaryKeySelective(gedOrder);// 更新订单表信息
        } else {
          result = 1;
        }
      } else {
        gedOrder.setId(oldOrder.getId());
        gedOrderMapper.updateByPrimaryKeySelective(gedOrder);// 更新订单表信息
        result = rechargeRecordMapper.insertSelective(rechargeRecord);// 新增
      }

      logger.info("入库提现流水：" + JSON.toJSONString(rechargeRecord));
      logger.info("更新订单信息：" + JSON.toJSONString(gedOrder));
      if (result > 0) {
        resMap.put("resp_code", "0000");
        resMap.put("resp_msg", "回调成功");
        resMap.put("status", withDrawStauts);
        resMap.put("serviceFeeFlag",
            oldOrder.getServiceFeeFlag() == null ? 0 : oldOrder.getServiceFeeFlag());
        resMap.put("amount", withDrawData.getAmount());
        resMap.put("bankCardNo", bankCode);
      } else {
        resMap.put("resp_code", "0001");
        resMap.put("resp_msg", "回调失败");
        resMap.put("status", withDrawStauts == 0 ? 5 : withDrawStauts);
        resMap.put("serviceFeeFlag",
            oldOrder.getServiceFeeFlag() == null ? 0 : oldOrder.getServiceFeeFlag());
        resMap.put("amount", withDrawData.getAmount());
        resMap.put("bankCardNo", bankCode);
      }


      // 将订单状态传递给借款系统，并同步数据
      Map<String, String> loanParam = new HashMap<String, String>();
      loanParam.put("orderNo", oldOrder.getOrderCode());
      loanParam.put("status", String.valueOf(orderStatus));
      JSONObject json = HttpUtils.doPost(gedUpdateOrderUrl, JSONObject.toJSONString(loanParam));
      JSONObject loanJson = HttpUtils.doPost(gedUpdateOrderUrl, JSONObject.toJSONString(loanParam));
      logger.info("推送借款系统更新状态返回的参数值===============" + JSON.toJSONString(json));

      Map<String, String> withCashParam = new HashMap<String, String>();
      withCashParam.put("loanStatus", String.valueOf(loanStauts));// 状态码
      withCashParam.put("contractNo", oldOrder.getContractCode());// 合同号
      withCashParam.put("applyNo", oldOrder.getOrderCode());// 订单编号
      withCashParam.put("withdrawAmount", withDrawData.getAmount() + "");// 提现金额
      withCashParam.put("seqNo", resForm.getSeq_no());// 交易流水号
      withCashParam.put("mchn", resForm.getMchn());// 商户号
      withCashParam.put("flag", "");// 不知道什么字段
      JSONObject loanJs =
          HttpUtils.doPost(firstWithDepositUrl, JSONObject.toJSONString(withCashParam));
      logger.info("推送借款系统提现反馈返回的参数值===============" + loanJs.get("code") + "");

      return resMap;
    }
  }


  public Integer withDrawFlag(GedOrder gedOrder, CapitalResForm resForm,
      OnlineBankingRecharge withDrawData) {
    Integer status = OrderStatus.YCTX.getCode();
    Integer loanStauts = FirstWithDeposit.FIRST_WITHCASH.getCode();

    // 如果响应码等于0000，则获取提现信息，并修改提现状态信息
    BigDecimal reaTradeAmount = withDrawData.getAmount();// 回调回来的提现金额
    BigDecimal creditAmount = gedOrder.getCreditAmount();// 放款额度
    BigDecimal withDrawAmount = gedOrder.getWithdrawAmount();// 已提现金额
    logger.info("回调回来的放款额度===》" + reaTradeAmount + "===" + "放款额度=====》" + creditAmount + "==="
        + "数据库存放的已提现金额=====》" + withDrawAmount);

    /**
     * 回调显示提现金额等于放款金额，则说明是全额提现，更新ged_order表数据
     */
    if (reaTradeAmount.equals(creditAmount)) { // 第一次交服务费全额提现
      logger.info("===========直接全部提现    相当于第二次提现 =============");
      return 2;// 相当于第二次提现
    } else {
      /**
       * 如果数据库中的已提现金额字段为null,说明这个账户之前是没有提现过的，那么这次是第一次提现 更新ged_order表数据
       */
      if (withDrawAmount == null) { // 第一次提现
        logger.info("==========是一次提现啊============");
        return 1;// 第一次提现
        /**
         * 否则那这就是第二次提现了，更新ged_order表数据
         */
      } else { // 二次提现处理成功
        logger.info("==========二次提现=============");
        return 2;
      }
    }
  }

  // 查询合同号
  private String getBusiNo(String custId, String mobile) {
    return accountMapper.selectBusiNoByCustIdAndMobile(custId, mobile);
  }

  public OrderLog selectOrderLog(GedOrder gedOrder) {
    return orderLogService.getOrderLogByOrderId(gedOrder.getId(), gedOrder.getStatus());
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

  @Override
  public Integer selectAccStatus(User user) {
    if (user == null) {
      throw new BusinessException(Errors.USER_MOBILE_NOT_EXISTS_ERROR);
    }

    if (user.getCheckAccountFlag() != null && user.getCheckAccountFlag() != 0) {
      return user.getCheckAccountFlag();
    } else if (user.getCompanyAccountFlag() != null && user.getCompanyAccountFlag() != 0) {
      return user.getCompanyAccountFlag();
    } else {
      return 0;
    }
  }

  @Override
  public AccountStatusResForm queryAccountStatus(User user) {
    AccountStatusResForm resForm = new AccountStatusResForm();
    // resForm.setStatus(0);//个人、企业均未开户
    if (Strings.isNotBlank(user.getGetCustId())) {
      resForm.setStatus(-1);// 开户处理中
    }
    Account account = getAccountByUserId(user.getId());
    if (account != null) {
      // resForm.setStatus(-1);//处理中
      if (account.getStatus() == 1 || account.getStatus() == 4)
        resForm.setStatus(1);// 个人开户成功
      else
        resForm.setStatus(0);// 个人开户失败
      return resForm;
    }
    AccountCompany accountCompany = getAccountCompanyByUserId(user.getId());
    if (accountCompany != null) {
      // resForm.setStatus(-1);//处理中
      resForm.setStatus(accountCompany.getStatus());// 企业开户也可能返回0,返回0时，提示开户失败
      resForm.setVerifyInfo(accountCompany.getAccountVerifyInfo());
      return resForm;
    }

    return resForm;
  }

  @Override
  public Map<String, Object> rechargeGuarantorCallBack(CapitalResForm resForm) throws Exception {
    synchronized (this) {
      Map<String, Object> resMap = new HashMap<String, Object>();

      // 更新用户信息
      // userService.updateUserCustId(custId, user.getId());
      logger.info("网银充值(缴费业务)>>>>>资金系统回调返回的数据：" + JSON.toJSONString(resForm));
      String data = resForm.getData();
      ObjectMapper mapper = new ObjectMapper();
      OnlineBankingRecharge rechargeData =
          mapper.readValue(URLDecoder.decode(data, "UTF-8"), OnlineBankingRecharge.class);
      User user = userService.getUserByCustId(rechargeData.getCustId());
      GedOrder gedOrder = orderService.getNewOrderByUserId(user.getId());
      GedRechargeRecord rechargeRecord = new GedRechargeRecord();

      GedRechargeRecord gedRechargeRecord =
          rechargeRecordService.getRechargeRecordByOrderNo(resForm.getSeq_no());
      if (!resForm.getResp_code().equals("0000")) {
        String msg = resForm.getResp_msg();
        throw new BusinessException(Errors.ONLINE_BANKING_RECHARGE_ERROR, msg);
      } else {
        String tradeTimeStr =
            rechargeData.getTradeDate() == null ? null : rechargeData.getTradeDate();
        Date trateTime = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isNotBlank(tradeTimeStr)) {
          trateTime = format.parse(tradeTimeStr);
        }

        rechargeRecord.setSeqNo(resForm.getSeq_no());// 第三方流水号
        rechargeRecord.setOrderNo(gedOrder.getOrderCode());// 对应订单号
        rechargeRecord.setMobileNo(rechargeData.getMobile());
        rechargeRecord.setRechargeAmount(rechargeData.getAmount());
        rechargeRecord.setRespCode(resForm.getResp_code());
        rechargeRecord.setCustNo(rechargeData.getCustId());
        rechargeRecord.setType(1);// 1服务费充值2还款充值 3提现
        rechargeRecord.setCreateTime(new Date());
        rechargeRecord.setTradeDate(trateTime);
        rechargeRecord.setStatus(1);// 0未充值1充值成功2充值失败3缴费成功5充值中
        rechargeRecordMapper.insertSelective(rechargeRecord);// 插入企业开户表中
      }

      return resMap;
    }
  }

  @Override
  public Map<String, Object> charge(RechargeReqForm rechargeReqForm) throws Exception {
    Map<String, Object> map = new HashMap<String, Object>();
    logger.info("缴费(直连)接口请求参数，rechargeReqForm ：" + JSON.toJSONString(rechargeReqForm));
    GedOrder gedOrder = orderService.getGedOrderByOrderNo(rechargeReqForm.getOrderNo());
    // 调用缴费接口
    String seqNo = getSeqNo();
    // 平台分期服务费
    Map<String, Object> serviceFeeMap = new HashMap<String, Object>();
    serviceFeeMap.put("feeType", 11085001);// 费用类型--平台分期服务费
    serviceFeeMap.put("feeAmount", rechargeReqForm.getServiceFee());// 费用金额
    // map1.put("memo", "");// 备注（可以不传）

    // 账户管理服务费
    Map<String, Object> managementFeeMap = new HashMap<String, Object>();
    managementFeeMap.put("feeType", 11085002);// 费用类型--账户管理服务费
    managementFeeMap.put("feeAmount", rechargeReqForm.getManagementFee());// 费用金额

    JSONArray jsonArray = new JSONArray();
    jsonArray.add(serviceFeeMap);
    jsonArray.add(managementFeeMap);

    Map<String, Object> dataMap = new HashMap<String, Object>();
    dataMap.put("contractNo", gedOrder.getContractCode());// 标的合同号
    // dataMap.put("debtNo", null);// 债转编号（可以不传）
    // dataMap.put("productName", null);// 标的产品名称（可以不传）
    dataMap.put("custId", rechargeReqForm.getCustId());// 客户Id
    dataMap.put("custType", "1");// 1借款客户
    dataMap.put("platform", chargesLoanPlatform);// 北京：10040001
    dataMap.put("amount", rechargeReqForm.getServiceFee().add(rechargeReqForm.getManagementFee())
        .setScale(2, BigDecimal.ROUND_HALF_DOWN));// 收费总金额
    dataMap.put("feeList", jsonArray);// 费用列表
    // dataMap.put("remark", "");// 备注（可以不传）
    String data = JSON.toJSONString(dataMap);

    RsaKey rsaKey = getKeys();
    String sysprkey = rsaKey.getPrivateKey();
    String seqNoCharges = DateFormatUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS");
    String str = mchn + "|" + seqNoCharges + "|" + chargesTradeType + "|" + data + "|" + "|"
        + chargesCallbackUrl;
    String signature = RSAUtils.sign(str.getBytes(), sysprkey);
    Map<String, String> reqMap = new HashMap<String, String>();
    reqMap.put("mchn", mchn);
    reqMap.put("seq_no", seqNoCharges);
    reqMap.put("trade_type", chargesTradeType);
    reqMap.put("back_url", chargesCallbackUrl);
    reqMap.put("page_url", "");
    reqMap.put("data", data);
    reqMap.put("signature", URLEncoder.encode(signature, "UTF-8"));
    logger.info("调用资金系统异步缴费接口的请求URL：" + chargesUrl);
    logger.info("调用资金系统异步缴费接口的请求参数：" + reqMap);
    String result = HttpUtils.doPostForm(chargesUrl, reqMap);
    logger.info("调用资金系统缴费接口的返回值：" + result);
    if (result.contains("0000")) {
      map.put("resp_code", "0000");
      map.put("resp_msg", "缴费成功");
    } else {
      map.put("resp_code", "0001");
      map.put("resp_msg", "缴费失败");
    }
    return map;
  }

  @Override
  public VerifyDeductPageResForm toVerifyDeductPage(ChargeReqForm chargeReqForm, User user)
      throws Exception {
    VerifyDeductPageResForm pageResForm = new VerifyDeductPageResForm();
    String seqNo = getSeqNo();
    GedOrder gedOrder = orderService.getGedOrderByOrderNo(chargeReqForm.getOrderNo());
    BigDecimal amount = new BigDecimal(0);
    if (gedOrder != null)
      amount = gedOrder.getServiceFee();
    // 已缴费
    if (gedOrder.getServiceFeeFlag() != null && gedOrder.getServiceFeeFlag() > 0) {
      throw new BusinessException(Errors.ALREADY_DECUCTED);
    }

    User dataUser = userService.getUserById(user.getId());
    // GedOrder gedOrder = orderService.getNewOrderByUserId(dataUser.getId());
    String custId = dataUser.getGetCustId();
    // V2.0 --- 网银充值
    Map<String, Object> dataMap = new HashMap<String, Object>();
    dataMap.put("custId", custId);// 客户Id
    dataMap.put("custType", "1");// 账户类型
    dataMap.put("feeType", "1");// 收取费用类型(1借款客户)
    dataMap.put("amount", amount);// 收费金额
    dataMap.put("loanPlatform", "10040001");// 借款平台（北京：10040001）
    dataMap.put("contractNo", gedOrder.getContractCode());// 合同号
    // dataMap.put("bidId", null);
    // dataMap.put("productName", "");
    dataMap.put("remark", chargeReqForm.getOrderNo());// 订单号
    String data = JSON.toJSONString(dataMap);

    RsaKey rsaKey = getKeys();
    String sysprkey = rsaKey.getPrivateKey();
    String str = mchn + "|" + seqNo + "|" + verifyDeductTradeType + "|" + data + "|"
        + verifyDeductPageUrl + "|" + verifyDeductCallBackUrl;
    String signatureStr = RSAUtils.sign(str.getBytes(), sysprkey);
    String signature = URLEncoder.encode(signatureStr, "UTF-8");
    StringBuilder sbd = new StringBuilder();
    sbd.append(verifyDeductUrl).append("?").append("mchn=");
    sbd.append(mchn).append("&seq_no=").append(seqNo).append("&trade_type=")
        .append(verifyDeductTradeType);
    sbd.append("&page_url=").append(verifyDeductPageUrl).append("&back_url=")
        .append(verifyDeductCallBackUrl);
    sbd.append("&data=").append(URLEncoder.encode(data, "UTF-8")).append("&signature=")
        .append(signature);
    logger.info("缴费接口（页面）>>>>>>  返回给前端请求的(充值页面)url：" + sbd.toString());

    pageResForm.setRedirectUrl(sbd.toString());
    return pageResForm;
  }

  @Override
  public Map<String, Object> verifyDeductCallBack(CapitalResForm resForm) throws Exception {
    Map<String, Object> resMap = new HashMap<String, Object>();
    // 加锁
    synchronized (this) {
      logger.info("资金系统缴费回调（页面）>>>>>资金系统返回的数据：" + JSON.toJSONString(resForm));
      logger.info("资金系统缴费回调（页面）>>>>>资金系统返回的状态码：：" + resForm.getResp_code());
      String data = resForm.getData();
      ObjectMapper mapper = new ObjectMapper();
      JSONObject jsonObject = JSONObject.parseObject(URLDecoder.decode(data, "UTF-8"));
      GedOrder gedOrder = null;
      if (StringUtils.isNotBlank(jsonObject.getString("remark"))) {
        gedOrder = orderService.getGedOrderByOrderNo(jsonObject.getString("remark"));
      }

      if(!"0000".equals(resForm.getResp_code())){
        resMap.put("res_code", resForm.getResp_code());
        resMap.put("status", "5");
        resMap.put("amount", gedOrder.getServiceFee());
        resMap.put("res_msg", "缴费失败");
        return resMap;
      }

      if (gedOrder.getServiceFeeFlag() > 0) {
        resMap.put("res_code", "0000");
        resMap.put("status", "3");
        resMap.put("amount", gedOrder.getServiceFee());
        resMap.put("res_msg", "缴费成功");
        return resMap;
      }

      Charges chargesData = mapper.readValue(jsonObject.toJSONString(), Charges.class);
      // 根据订单号查询缴费记录
      GedRechargeRecord rechargeRecord = new GedRechargeRecord();
      if (StringUtils.isNotBlank(resForm.getSeq_no())) {
        rechargeRecord = rechargeRecordService.getRechargeRecordBySeqNo(resForm.getSeq_no());
      }

      String tradeTimeStr = chargesData.getTradeDate() == null ? null : chargesData.getTradeDate();
      Date trateTime = null;
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      if (StringUtils.isNotBlank(tradeTimeStr)) {
        trateTime = format.parse(tradeTimeStr);
      }
      if (rechargeRecord != null && rechargeRecord.getStatus() != 3) { // 缴费成功
        rechargeRecord.setSeqNo(resForm.getSeq_no());// 第三方流水号
        rechargeRecord.setOrderNo(chargesData.getRemark());// 对应订单号
        // gedRechargeRecord.setMobileNo(chargesData.getMobile()); 问一下资金系统，手机号和交易时间能否返回？
        rechargeRecord.setRechargeAmount(chargesData.getAmount());
        rechargeRecord.setRespCode(resForm.getResp_code());
        rechargeRecord.setCustNo(chargesData.getCustId());
        rechargeRecord.setType(1);// 1服务费充值2还款充值 3提现
        rechargeRecord.setModifyTime(new Date());
        rechargeRecord.setTradeDate(trateTime);
        rechargeRecord.setStatus(3);// 0未充值1充值成功2充值失败3缴费成功6缴费失败
        Integer result = rechargeRecordMapper.updateByPrimaryKeySelective(rechargeRecord);// 插入缴费充值记录表
        // 更新订单表服务费标识为已缴费
        GedOrder serviceFeeOrder = new GedOrder();
        serviceFeeOrder.setServiceFeeFlag(1);// 已缴费
        serviceFeeOrder.setModifyTime(new Date());
        serviceFeeOrder.setOrderCode(chargesData.getRemark());// 订单号
        gedOrderMapper.updateByOrderCodeSelective(serviceFeeOrder);
        if (result > 0) {
          resMap.put("res_code", "0000");
          resMap.put("status", "3");
          resMap.put("amount", rechargeRecord.getRechargeAmount());
          resMap.put("res_msg", "缴费成功");
          return resMap;
        } else {
          resMap.put("res_code", "0001");
          resMap.put("status", "6");
          resMap.put("amount", chargesData.getAmount());
          resMap.put("res_msg", "缴费失败");
          return resMap;
        }
      } else if (rechargeRecord != null && rechargeRecord.getStatus() == 3) {
        resMap.put("res_code", "0000");
        resMap.put("status", rechargeRecord.getStatus());
        resMap.put("amount", rechargeRecord.getRechargeAmount());
        resMap.put("res_msg", "缴费成功");
        return resMap;
      } else {
        GedRechargeRecord gedRechargeRecord = new GedRechargeRecord();
        gedRechargeRecord.setSeqNo(resForm.getSeq_no());// 第三方流水号
        gedRechargeRecord.setOrderNo(chargesData.getRemark());// 对应订单号
        // gedRechargeRecord.setMobileNo(chargesData.getMobile()); 问一下资金系统，手机号和交易时间能否返回？
        gedRechargeRecord.setRechargeAmount(chargesData.getAmount());
        gedRechargeRecord.setRespCode(resForm.getResp_code());
        gedRechargeRecord.setCustNo(chargesData.getCustId());
        gedRechargeRecord.setType(1);// 1服务费充值2还款充值 3提现
        gedRechargeRecord.setCreateTime(new Date());
        gedRechargeRecord.setTradeDate(trateTime);
        gedRechargeRecord.setStatus(3);// 0未充值1充值成功2充值失败3缴费成功6缴费失败
        Integer result = rechargeRecordMapper.insertSelective(gedRechargeRecord);// 插入缴费充值记录表
        // 更新订单表服务费标识为已缴费
        GedOrder serviceFeeOrder = new GedOrder();
        serviceFeeOrder.setServiceFeeFlag(1);// 已缴费
        serviceFeeOrder.setModifyTime(new Date());
        serviceFeeOrder.setOrderCode(chargesData.getRemark());// 订单号
        gedOrderMapper.updateByOrderCodeSelective(serviceFeeOrder);
        if (result > 0) {
          resMap.put("res_code", "0000");
          resMap.put("status", "3");
          resMap.put("amount", gedRechargeRecord.getRechargeAmount());
          resMap.put("res_msg", "缴费成功");
          return resMap;
        } else {
          resMap.put("res_code", "0001");
          resMap.put("status", "6");
          resMap.put("amount", chargesData.getAmount());
          resMap.put("res_msg", "缴费失败");
          return resMap;
        }

      }
    }
  }

  @Override
  public RechargeFeeResForm queryRechargeFeeStatus(RechargePayReqForm reqForm) {
    GedRechargeRecord rechargeRecord =
        rechargeRecordService.getRechargeRecordByOrderNo(reqForm.getOrderNo());
    RechargeFeeResForm rechargeFeeResForm = new RechargeFeeResForm();
    if (rechargeRecord != null) {
      rechargeFeeResForm.setAmount(rechargeRecord.getRechargeAmount());
      rechargeFeeResForm.setStatus(rechargeRecord.getStatus());
    } else {
      rechargeFeeResForm.setStatus(-1);// 充值进行中
      rechargeFeeResForm.setAmount(null);
    }

    logger.info(">>>>>>>>> 查询充值缴费结果 >>>>>>>>> 返回信息：" + JSON.toJSONString(rechargeFeeResForm));
    return rechargeFeeResForm;
  }

  /*
   * @Override public WithDrawForm queryWithDrawStatus(User user,RechargePayReqForm reqForm) {
   * GedRechargeRecord rechargeRecord =
   * rechargeRecordService.getWithDrawRecordByOrderNo(reqForm.getOrderNo()); WithDrawForm
   * withDrawForm = new WithDrawForm(); if(rechargeRecord != null){
   * withDrawForm.setAmount(rechargeRecord.getRechargeAmount());
   * withDrawForm.setStatus(rechargeRecord.getStatus()); }else{ withDrawForm.setStatus(-1);//提现进行中
   * withDrawForm.setAmount(null); }
   * 
   * Account account = getAccountByUserId(user.getId()); if(account != null){
   * withDrawForm.setBankCardNo(account.getCompanyAccount()); } AccountCompany accountCompany =
   * getAccountCompanyByUserId(user.getId()); if(accountCompany != null){
   * withDrawForm.setBankCardNo(accountCompany.getCompanyAccount()); }
   * 
   * logger.info(">>>>>>>>> 查询提现结果 >>>>>>>>> 返回信息：" + JSON.toJSONString(withDrawForm)); return
   * withDrawForm; }
   */

  @Override
  public ActivateUserResForm queryActivateUserStatus(String custId) {
    User user = userService.getUserByCustId(custId);
    ActivateUserResForm activateUserResForm = new ActivateUserResForm();
    if (user != null) {
      activateUserResForm.setStatus(user.getStatus() == 2 ? -1 : user.getStatus());
      activateUserResForm.setMobile(user.getMobile());
      activateUserResForm.setUserName(user.getUsername());
    } else {
      return null;
    }

    return activateUserResForm;
  }

  @Override
  public UserStatusResForm queryUserStatus(Long id) {
    User user = userService.getUserById(id);
    UserStatusResForm userStatusResForm = new UserStatusResForm();
    userStatusResForm.setStatus(user.getStatus());

    GedOrder gedOrder = orderService.getNewOrderByUserId(user.getId());
    if (gedOrder == null)
      userStatusResForm.setSysFlag(0);// 0借款系统
    else
      userStatusResForm.setSysFlag(gedOrder.getSysFlag());

    logger.info(">>>>>>>>> 查询新老用户状态>>>>>> 返回信息：" + JSON.toJSONString(userStatusResForm));
    return userStatusResForm;
  }

  @Override
  public RepaymentResForm queryRepaymentStatus(User user, RechargePayReqForm reqForm) {
    GedRepaymentRecord repaymentRecord =
        repaymentRecordService.getRechargeRecordByOrderNo(reqForm.getOrderNo());
    RepaymentResForm repaymentResForm = new RepaymentResForm();
    if (repaymentRecord == null) {
      repaymentResForm.setStatus(-1);
    } else {
      repaymentResForm.setStatus(repaymentRecord.getStatus());
      repaymentResForm.setAmount(repaymentRecord.getRepaymentAmount());
      repaymentResForm.setOrderNo(repaymentRecord.getSeqNo());
    }

    logger.info("查询还款充值结果 >>>>>>>>>返回信息为：" + JSON.toJSONString(user));
    return repaymentResForm;
  }


  public String getSeqNo() {
    int a[] = new int[10];
    StringBuilder seq = new StringBuilder();;
    seq.append(DateFormatUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
    String randomStr = "";
    for (int i = 0; i < 10; i++) {
      int random = (int) (Math.random() * 10);
      if (randomStr.indexOf(random + "") != -1) {
        i = i - 1;
      } else {
        randomStr += random;
      }
    }
    seq.append(randomStr);
    return seq.toString();
  }

}
