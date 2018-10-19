package com.gq.ged.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gq.ged.account.controller.res.AccountResForm;
import com.gq.ged.account.dao.model.Account;
import com.gq.ged.account.dao.model.AccountCompany;
import com.gq.ged.account.service.AccountOpenService;
import com.gq.ged.account.service.AccountService;
import com.gq.ged.common.Errors;
import com.gq.ged.common.enums.OrderStatus;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.dictionary.service.DictionaryService;
import com.gq.ged.order.controller.res.OrderDetailResForm;
import com.gq.ged.order.dao.mapper.GedOrderMapper;
import com.gq.ged.order.dao.model.GedOrder;
import com.gq.ged.order.dao.model.GedOrderExample;
import com.gq.ged.order.dao.model.OrderLog;
import com.gq.ged.order.service.OrderDetailService;
import com.gq.ged.order.service.OrderLogService;
import com.gq.ged.order.service.OrderService;
import com.gq.ged.user.dao.model.User;
import com.gq.ged.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

/**
 * Created by wyq_tomorrow on 2018/1/29.
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
  Logger logger = LoggerFactory.getLogger(this.getClass());

  @Resource
  GedOrderMapper gedOrderMapper;

  @Resource
  AccountService accountService;
  @Resource
  AccountOpenService accountOpenService;

  @Resource
  OrderLogService orderLogService;
  @Resource
  DictionaryService dictionaryService;
  @Resource
  OrderService orderService;
  @Resource
  UserService userService;

  @Override
  public OrderDetailResForm getOrderDetailInfo(String orderCode, Long userId) throws Exception{
    GedOrderExample example = new GedOrderExample();
    example.createCriteria().andOrderCodeEqualTo(orderCode);
    User user=userService.getUserById(userId);
    List<GedOrder> list = gedOrderMapper.selectByExample(example);
    if (list.size() == 0) {
      throw new BusinessException(Errors.SYSTEM_DATA_NOT_FOUND);
    }
    GedOrder gedOrder = list.get(0);
    // 查询账户信息

    OrderDetailResForm resultForm = new OrderDetailResForm();


    AccountResForm resForm = new AccountResForm();
    resForm.setStatus(0);
    resultForm.setAccountResForm(resForm);
    //个人角色，开个人户
    if(user.getCheckAccountFlag() != null && user.getCheckAccountFlag() == 4){
      Account account = accountOpenService.getAccountByUserId(userId);
      resForm.setBankOfDeposit(account.getCompanyBankOfDeposit());
      resForm.setCardNum(account.getCompanyAccount());
      // resForm.setCompanyName(account.getCompanyName());
      resForm.setCorporationName(account.getCorporationName());
      resForm.setStatus(account.getStatus());
      //resForm.setCompanyStatus(user.getCompanyAccountFlag());
      resultForm.setAccountResForm(resForm);
    }else if(user.getCompanyAccountFlag() != null && user.getCompanyAccountFlag() == 7){
      AccountCompany accountCompany = accountOpenService.getAccountCompanyByUserId(userId);
      resForm.setBankOfDeposit(accountCompany.getCompanyBankOfDeposit());
      resForm.setCardNum(accountCompany.getCompanyAccount());
      // resForm.setCompanyName(account.getCompanyName());
      resForm.setCorporationName(accountCompany.getLegalName());
      resForm.setStatus(accountCompany.getStatus());
      //resForm.setCompanyStatus(user.getCompanyAccountFlag());
      resultForm.setAccountResForm(resForm);
    }else{
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
      if(user.getUserRole()==0)//借款人才有借款合同
      {
          resultForm.setContractUrl(orderService.getContractUrlByContractNo(gedOrder.getContractCode()));
      }
    }
    if (gedOrder.getRateDay() != null) {
      resultForm.setRateDay(gedOrder.getRateDay() + "%");
    }
    resultForm.setServiceFee(gedOrder.getLoanAmount().intValue() * 0.001 + "/月");
    if (gedOrder.getLoanType() != null) {
      resultForm
          .setLoanType(dictionaryService.getDictionaryValue("LOAN_TYPE", gedOrder.getLoanType()));
    }
    if(StringUtils.isBlank(gedOrder.getContractCode())){
      resultForm.setSignContractFlag(2);
      resultForm.setStatus(OrderStatus.resove(gedOrder.getStatus()).getFeStatusDesc());
    }else {
      if(gedOrder.getStockFlag()==1){//存量数据
        resultForm.setSignContractFlag(2);
        resultForm.setStatus(OrderStatus.resove(gedOrder.getStatus()).getFeStatusDesc());
      }else  if(gedOrder.getSignContractFlag()==0){
          resultForm.setStatus("待签署合同");
        resultForm.setSignContractFlag(gedOrder.getSignContractFlag());
        }else{
          resultForm.setStatus(OrderStatus.resove(gedOrder.getStatus()).getFeStatusDesc());
        resultForm.setSignContractFlag(gedOrder.getSignContractFlag());
        }
    }
    logger.info(JSONObject.toJSONString(resultForm));
    return resultForm;
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
}
