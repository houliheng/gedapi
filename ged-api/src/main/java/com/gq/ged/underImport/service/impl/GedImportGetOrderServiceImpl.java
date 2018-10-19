package com.gq.ged.underImport.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gq.ged.account.dao.model.Account;
import com.gq.ged.account.service.AccountService;
import com.gq.ged.account.service.SignService;
import com.gq.ged.activemq.service.JmsProvider;
import com.gq.ged.common.Constant;
import com.gq.ged.dictionary.dao.model.GedFuiouBankCode;
import com.gq.ged.dictionary.service.GedFuiouBankCodeService;
import com.gq.ged.order.dao.model.GedOrder;
import com.gq.ged.order.dao.model.OrderLog;
import com.gq.ged.order.service.OrderLogService;
import com.gq.ged.order.service.OrderService;
import com.gq.ged.order.service.impl.OrderServiceImpl;
import com.gq.ged.underImport.dao.mapper.GedImportGetOrderMapper;
import com.gq.ged.underImport.dao.model.GedImportGetOrder;
import com.gq.ged.underImport.dao.model.GedImportGetOrderExample;
import com.gq.ged.underImport.service.GedImportGetOrderService;
import com.gq.ged.user.dao.model.User;
import com.gq.ged.user.dao.model.UserLogin;
import com.gq.ged.user.service.UserService;
import jodd.util.BCrypt;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Levi on 2018/7/18.
 */
@Service
public class GedImportGetOrderServiceImpl implements GedImportGetOrderService {

  @Resource
  GedImportGetOrderMapper gedImportGetOrderMapper;
  @Resource
  JmsProvider jmsProvider;

  @Resource
  UserService userService;

  @Resource
  AccountService accountService;

  @Resource
  OrderService orderService;

  @Resource
  OrderLogService orderLogService;

  @Resource
  SignService signService;

   @Resource
  GedFuiouBankCodeService gedFuiouBankCodeService;

  Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

  @Override
  public void savePushInfos(String jsonRes) {
    logger.info("***********************************************借款系统推送过来的线下信息：" + jsonRes);
    Map<String, String> data = new HashedMap();
    data.put("jsonRes", jsonRes);
    jmsProvider.sendMessage(Constant.MQ_GED_UNDER_INFOS, jsonRes);
  }

  @Override
  public void saveList(String jsonRes) {
    if (jsonRes != null && !jsonRes.trim().equals("")) {
      List<GedImportGetOrder> datas = JSONObject.parseArray(jsonRes, GedImportGetOrder.class);
      if (datas != null && datas.size() >= 1) {
        gedImportGetOrderMapper.batchInsertOfflineOrder(datas);
      }
    }
  }

  @Override
  public Integer save(GedImportGetOrder gedImportGetOrder) {
    return gedImportGetOrderMapper.insertSelective(gedImportGetOrder);
  }

  @Override
  public Integer updateById(GedImportGetOrder gedImportGetOrder) {
    return gedImportGetOrderMapper.updateByPrimaryKeySelective(gedImportGetOrder);
  }

  @Override
  public void dealUnderInfos() {
    // 1 获取未处理的underinfos
    GedImportGetOrder con = new GedImportGetOrder();
    con.setHandleStatus(0);
    List<GedImportGetOrder> datas = this.getListByCondition(con);
    logger.info("准备存入数据的冠E贷数据为"+JSONObject.toJSONString(datas)+"=======");
    if (datas == null || datas.size() < 1)
      return;

    try {
      for (GedImportGetOrder g : datas) {
        // 1保存登录信息 按照统一社会信用代码查询 无结果 则插入
        // 2保存用户信息 1 插入则插入
        // 3保存账号信息 2 插入则插入
        // 4保存订单信息 信息插入
        // 5保存订单日志信息 信息插入 只保存139状态

        User user = null;

        // 1
        List<User> users = userService.getBySocialCreditCode(g.getCompanyCardNo());
        if (users == null || users.size() == 0) {
          // 根据社会统一信用代码没有查询到用户 id,mobile,social_credit_code,password,is_enabled
          UserLogin userLogin = new UserLogin();
          userLogin.setIsEnabled((byte) 1);
          userLogin.setMobile(g.getContractPhone());
          String socialCreditCode = g.getCompanyCardNo();
          String password =
              socialCreditCode.substring(socialCreditCode.length() - 6, socialCreditCode.length());
          userLogin.setPassword(BCrypt.hashpw(password, "$2a$10$LnBOX/Y75eUMFtUyN/THse"));
          userLogin.setSocialCreditCode(g.getCompanyCardNo());
          userService.insertLoginUser(userLogin);

          // 插入用户信息
          user = new User();
          user.setId(userLogin.getId());
          user.setUsername(g.getUserName());
          user.setMobile(g.getContractPhone());
          user.setCompanyName(g.getCompanyName());
          user.setCompanyCardCode(g.getCompanyCardNo());
          user.setSocialCreditCode(g.getCompanyCardNo());
          user.setCompanyCardType("1");
          user.setIdCardNum(g.getIdNumber());
          user.setCheckAccountFlag(4);
          user.setUserType(g.getUserType());
          user.setCompanyAccountFlag(1);
          user.setGetCustId(g.getCustId());
          userService.inserUser(user);


          // 插入账户信息
          Account account = accountService.selectAccountByCode(g.getCompanyCardNo());
          if (account == null) {
            updateAccountByNotExists(account, g, user);
          } else if (account.getUserId() == null) {
            updateAccountByExistsAndUserNull(account, g, user);
          }
        } else {
          user = users.get(0);
          Account account = accountService.selectAccountByCode(g.getCompanyCardNo());
          // 没有根据userid查到账户信息则尼玛臭嗨插入账户
          if (account != null) {
            if (account.getUserId() != null) {
              if (account.getUserId().longValue() == user.getId().longValue()) {
                //
              } else {
                // 插入
                account.setUserId(user.getId());
                accountService.insertAccount(account);
                user.setCheckAccountFlag(4);
                user.setGetCustId(g.getCustId());
                userService.updateUser(user);
                //同步借款系统
                signService.callLoanPlatformCreateAccount(account,g.getCompanyCardNo());
              }
            } else {
              updateAccountByExistsAndUserNull(account, g, user);
            }
          } else {
            updateAccountByNotExists(account, g, user);
          }
        }
          logger.info("保存订单信息用户"+user.getId());
        GedOrder gedOrderCon=orderService.selectByContract(g.getContractNo());
        if(gedOrderCon!=null){

        }else {
          // 插入订单信息
          GedOrder order = new GedOrder();
          order.setUserId(user.getId());
          order.setContractCode(g.getContractNo());
          order.setCompanyName(g.getCompanyName());
          order.setCompanyCardNum(g.getCompanyCardNo());
          order.setPersonCardNum(g.getIdNumber());
          order.setLoanType(g.getLoanType());
          order.setLoanAmount(g.getLoanAmount());
          order.setLoanTerm(g.getLoanTerm());
          order.setCreditAmount(g.getCreditAmount());
          order.setReplyAmount(g.getLoanAmount());
          order.setReplyTerm(g.getLoanTerm());
          order.setRateDay(g.getMonthRate());
          order.setRepaymentStyle(g.getRepaymentType());
          order.setStatus(g.getStatus());
          order.setOrderCode(g.getOrderCode());
          order.setStockFlag(1);
          order.setWithdrawFlag(4);
          order.setWithdrawAmount(g.getCreditAmount());
          // order.setLoanPurpose(g.getLoanPurpose() == null ? Integer.parseInt(g.getLoanPurpose()) :
          // null);
          order.setLoanPurpose(99);
          order.setCashDeposit(g.getCashDeposit());
          orderService.insertOrder(order);
          // 插入订单日志信息
          // ="INSERT INTO ged_order_log(order_id,source_status,target_status,create_id,create_time)
          // VALUES('"&A1&"','139','139','"&B1&"','"&M1&"');"
          OrderLog orderLog = new OrderLog();
          orderLog.setOrderId(order.getId());
          orderLog.setSourceStatus(99);
          orderLog.setTargetStatus(139);
          orderLog.setCreateId(user.getId());
          orderLog.setCreateTime(new Date());
          orderLogService.createOrderlog(orderLog);
          // 更新导入信息 回写处理标记
          updateUtill(g, 1);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void updateAccountByExists() {

  }

  private void updateAccountByExistsAndUserNull(Account account, GedImportGetOrder g, User user) {
    account.setUserId(user.getId());
    account.setSocialCreditCode(user.getSocialCreditCode());
    accountService.updateAccount(account);
    user.setCheckAccountFlag(4);
    user.setGetCustId(g.getCustId());
    userService.updateUser(user);
  }

  private void updateAccountByNotExists(Account account, GedImportGetOrder g, User user) throws Exception {
    Account accountNew=new Account();
    accountNew.setUserId(user.getId());
    accountNew.setCustId(g.getCustId());
    accountNew.setSocialCreditCode(g.getCompanyCardNo());
    accountNew.setCityCode(g.getCityCode());
    accountNew.setStatus(4);
    accountNew.setCompanyBankBranchName(g.getBankBranchName());
    accountNew.setCompanyAccount(g.getAccountCode());
    accountNew.setCompanyName(user.getCompanyName());
    accountNew.setCorporationName(user.getUsername());
    accountNew.setCorporationCardNum(user.getIdCardNum());
    accountNew.setCorporationPhone(user.getMobile());
    accountNew.setCompanyBankOfDeposit(g.getBankCode());
    if(StringUtils.isNotBlank(g.getBankCode())) {
      GedFuiouBankCode gedFuiouBankCode =
              gedFuiouBankCodeService.getBankInfo(g.getBankCode());
      if(gedFuiouBankCode!=null){
        accountNew.setCompanyBankOfDepositValue(gedFuiouBankCode.getBankName());
      }
    }
    accountService.insertAccount(accountNew);
    user.setCheckAccountFlag(4);
    user.setGetCustId(g.getCustId());
    userService.updateUser(user);
    //同时推送借款系统
    signService.callLoanPlatformCreateAccount(accountNew,g.getCompanyCardNo());

  }

  private void updateUtill(GedImportGetOrder g, int status) {
    g.setHandleStatus(status);
    this.updateById(g);
  }

  @Override
  public List<GedImportGetOrder> getListByCondition(GedImportGetOrder gedImportGetOrder) {
    GedImportGetOrderExample example = new GedImportGetOrderExample();
    GedImportGetOrderExample.Criteria criteria = example.createCriteria();
    if (gedImportGetOrder != null) {
      if (gedImportGetOrder.getHandleStatus() != null)
        criteria.andHandleStatusEqualTo(gedImportGetOrder.getHandleStatus());
    }
    return gedImportGetOrderMapper.selectByExample(example);
  }
}
