package com.gq.ged.order.service.impl;

import com.gq.ged.account.dao.model.Account;
import com.gq.ged.account.dao.model.AccountCompany;
import com.gq.ged.account.service.AccountService;
import com.gq.ged.common.Errors;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.order.controller.res.JoinCompanyInfo;
import com.gq.ged.order.dao.model.GedOrder;
import com.gq.ged.order.pc.res.LoanRecordResForm;
import com.gq.ged.order.service.CompanyService;
import com.gq.ged.order.service.OrderBorrowerService;
import com.gq.ged.order.service.OrderService;
import com.gq.ged.user.dao.model.User;
import com.gq.ged.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Levi on 2018/5/30.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

  @Resource
  OrderService orderService;
  @Resource
  AccountService accountService;
  @Resource
  UserService userService;
  @Resource
  OrderBorrowerService orderBorrowerService;


  @Override
  public List<JoinCompanyInfo> getJoinCompanyInfos(Long userId, String masterCode) {
    List<JoinCompanyInfo> datas = new ArrayList<>();
    GedOrder gedOrder = new GedOrder();
    gedOrder.setMasterOrderCode(masterCode);
    List<GedOrder> orders = orderService.getOrderingByCondition(gedOrder, null);
    if (orders != null && orders.size() > 0) {
      for (GedOrder order : orders) {

        User user = userService.getUserById(order.getUserId());
        if (order.getUserId() == null) {
          throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR, "联合授信企业查询用户信息缺失");
        } else {
          if (userId != order.getUserId()) {
            if (user == null) {
              throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR, "联合授信企业查询用户信息失败");
            }
            AccountCompany account = accountService.queryCompanyAccountInfoById(order.getUserId());
            if (account == null) {
              throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR, "联合授信企业查询用账户息失败");
            }
            JoinCompanyInfo joinCompanyInfo = new JoinCompanyInfo();
            // 企业名称
            joinCompanyInfo.setCompanyName(account.getCompanyName());
            // 对公银行卡号
            joinCompanyInfo.setCompanyAccount(account.getCompanyAccount());
            // 开户行名称
            joinCompanyInfo.setCompanyBank(account.getCompanyBankBranchName());
            joinCompanyInfo.setIsOpenAccount(
                user.getCompanyAccountFlag() == null ? "0" : user.getCompanyAccountFlag() + "");
            joinCompanyInfo.setCompanyAccountName(account.getCompanyName());
            datas.add(joinCompanyInfo);
          }

        }

      }
      return datas;
    }
    return null;
  }

  @Override
    public List<LoanRecordResForm> getJoinCompanyOrderInfos(Long userId, String masterCode) {

        List<LoanRecordResForm> datas = new ArrayList<>();
        GedOrder gedOrder = new GedOrder();
        gedOrder.setMasterOrderCode(masterCode);
        List<GedOrder> orders = orderService.getOrderingByCondition(gedOrder, null);
        if (orders!= null && orders.size() > 0){
            for (GedOrder order:orders) {

                User user = userService.getUserById(order.getUserId());
                if (order.getUserId() == null){
                    throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR , "联合授信企业订单查询用户信息缺失");
                }else {
                    if (userId != order.getUserId()){
                        LoanRecordResForm dats = orderBorrowerService.getJoinLoanRecordsPageByUserId(userId,order.getMasterOrderCode());
                        if (dats != null){
                            AccountCompany account = accountService.queryCompanyAccountInfoById(order.getUserId());
                            if (account == null)
                                throw new BusinessException(Errors.ORDER_NOT_EXISTS_ERROR , "联合授信企业订单查询账户信息缺失");
                            //dats.setCompanyName(account.getCompanyName());
                            dats.setContractNo(order.getContractCode());
                            datas.add(dats);
                        }

                    }

                }

            }
                return datas;
        }
        return null;
    }

}
