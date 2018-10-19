package com.gq.ged.account.service;

import com.alibaba.fastjson.JSONObject;
import com.gq.ged.account.controller.req.SignSuccessReqForm;
import com.gq.ged.account.controller.res.SignResForm;
import com.gq.ged.account.dao.model.Account;

import java.io.UnsupportedEncodingException;


/**
 * Created by Levi on 2017/8/25.
 */
public interface SignService {

  SignResForm sign(Long userId) throws Exception;

  /**
   * 修改签约成功状态
   * 
   * @param reqForm
   * @return
   * @throws UnsupportedEncodingException
   */
  Integer signSuccess(SignSuccessReqForm reqForm) throws Exception;

  /**
   * 请求借款系统推送账户信息
   * 
   * @param account
   * @param cardCode
   * @return
   */
  JSONObject callLoanPlatformCreateAccount(Account account, String cardCode) throws Exception;

}
