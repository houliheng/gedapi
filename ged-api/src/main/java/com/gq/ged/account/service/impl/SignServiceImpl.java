package com.gq.ged.account.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gq.ged.account.controller.req.SignSuccessReqForm;
import com.gq.ged.account.controller.res.SignResForm;
import com.gq.ged.account.dao.model.Account;
import com.gq.ged.account.dao.model.RsaKey;
import com.gq.ged.account.service.AccountService;
import com.gq.ged.account.service.RsaKeyService;
import com.gq.ged.account.service.SignService;
import com.gq.ged.common.Constant;
import com.gq.ged.common.http.HttpUtils;
import com.gq.ged.common.utils.RSAEncryptUtils;
import com.gq.ged.dictionary.dao.model.FuiouArea;
import com.gq.ged.dictionary.dao.model.SystemDictionaryItem;
import com.gq.ged.dictionary.service.DictionaryService;
import com.gq.ged.dictionary.service.FuyouAreaService;
import com.gq.ged.user.dao.model.User;
import com.gq.ged.user.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Levi on 2017/8/25.
 */
@Service
public class SignServiceImpl implements SignService {
  static org.slf4j.Logger logger = LoggerFactory.getLogger(SignServiceImpl.class);

  @Resource
  UserService userService;

  @Resource
  AccountService accountService;

  @Resource
  RsaKeyService rsaKeyService;

  @Value("${ged.sign.info}")
  String signIpInfo;

  @Value("${ged.sign.trade.type}")
  String signTradetype;

  @Value("${ged.sign.success.h5}")
  String signSuccessH5;

  @Value("${gq.ged.get.mchn}")
  String signMchn;

  @Resource
  DictionaryService dictionaryService;

  /*
   * @Value("${gq.ged.call.loansys.account.url}") String loanCreateAccountUrl;
   */

  @Value("${gq.borrow.url}")
  String borrowUrl;

  @Resource
  FuyouAreaService fuyouAreaService;



  @PostConstruct
  public void initSignKey() throws IOException {
    RsaKey rsaKey = rsaKeyService.getAllKeys(2);
    RSAEncryptUtils.DEFAULT_PUBLIC_KEY = new String(rsaKey.getPublicKey());
    RSAEncryptUtils.DEFAULT_PRIVATE_KEY = new String(rsaKey.getPrivateKey());
  }


  @Override
  public SignResForm sign(Long userId) throws Exception {
    // 获取user信息
    User user = userService.getUserById(userId);
    Account account = accountService.getAccountByUserId(userId);
    SignResForm resForm = new SignResForm();
    Map<String, String> data = new HashedMap();
    // mchn; //商户号
    data.put("mchn", signMchn);
    // trade_type; //交易类型
    data.put("trade_type", signTradetype);
    // cust_id;
    data.put("cust_id", "");
    // 现金贷客户id
    data.put("user_id", user.getId() + "");
    // cert_no; //身份证号
    data.put("cert_no", account.getCorporationCardNum() + "");
    // cert_name; //客户姓名
    // data.put("cert_name", user.getUsername() + "");
    data.put("cert_name", "");
    // cert_mobile; //看客户手机号
    data.put("cert_mobile", account.getCorporationPhone() + "");
    // page_notify_url; //签约完成回调地址
    data.put("page_notify_url", signSuccessH5);
    // page_notify_url_fuiou; //富有回调地址
    data.put("page_notify_url_fuiou", "");
    String res = RSAEncryptUtils.encryptByPublicKey(JSONObject.toJSONString(data));
    logger.info(java.net.URLEncoder.encode((res), "utf-8"));
    logger.info(JSONObject.toJSONString(data));
    resForm.setSuccessMark(true);
    String url =
        signIpInfo + "?mchn=" + signMchn + "&data=" + java.net.URLEncoder.encode((res), "utf-8")
            + "&seq_no=" + System.currentTimeMillis() + "&trade_type=" + signTradetype;
    logger.info(url);
    resForm.setSignUrl(url);
    return resForm;
  }

  @Override
  public Integer signSuccess(SignSuccessReqForm reqForm) throws Exception {
    // 转码
    logger.info("result===" + JSONObject.toJSONString(reqForm));
    String data = java.net.URLDecoder.decode((reqForm.getData()), "utf-8");
    String data1 = java.net.URLDecoder.decode((data), "utf-8");
    // 解密
    String datajJosn1 = RSAEncryptUtils.decryptByPrivateKey(data1);
    logger.info("result解密===" + JSONObject.toJSONString(datajJosn1));
    JSONObject jb = JSON.parseObject(datajJosn1);
    // 通过身份证号码获取user
    Account account = accountService.getAccountByUserId(Long.parseLong(jb.getString("user_id")));
    if (account.getStatus() == 4) {
      return 0;
    }
    if (Constant.RES_CODE_SUCCESS.equals(reqForm.getCode().trim())) {
      // 成功
      logger.info("签约成功");
      account.setStatus(4);
      User user = userService.getUserById(account.getUserId());
      accountService.updateAccountStatus(account);
      userService.updateCheckAccountFlag(user.getId(), null, 4);
      callLoanPlatformCreateAccount(account, user.getSocialCreditCode());
      return 0;
    } else {
      // 失败
      logger.info("签约失败");
      account.setStatus(5);
      accountService.updateAccountStatus(account);
      userService.updateCheckAccountFlag(account.getUserId(), null, 5);
      return 1;
    }


  }


  public JSONObject callLoanPlatformCreateAccount(Account account, String cardCode)
      throws Exception {
    User user = userService.getUserById(account.getUserId());
    Map<String, Object> map = new HashedMap();
    SystemDictionaryItem dictionaryItem =
        dictionaryService.getDictionaryByParam("BANK_TYPE", account.getCompanyBankOfDeposit());
    map.put("custId", user.getGetCustId());
    map.put("companyBankOfDeposit", account.getCompanyBankOfDeposit());
    map.put("companyAccount", account.getCompanyAccount());
    map.put("companyBankBranchName", account.getCompanyBankBranchName());
    if (account.getCityCode() != null) {
      FuiouArea area2 = fuyouAreaService.selectFuiouAreaByCode(account.getCityCode());
      if (area2 != null) {
        map.put("cityName", area2.getAreaFullCode());
      }
      FuiouArea area1 = fuyouAreaService.selectFuiouAreaByCode(area2.getParentId().toString());
      if (area1 != null) {
        map.put("provinceName", area1.getAreaFullCode());
      }

    } else {
      map.put("provinceName", "");
      map.put("cityName", "");
    }

    map.put("comIdNum", cardCode);
    map.put("legalPerNum", account.getCorporationCardNum());
    map.put("legalPerName", account.getCorporationName());
    map.put("companyName", account.getCompanyName());
    if (user.getMobile() == null) {
      map.put("legalPerPhone", account.getCorporationPhone());
    } else {
      map.put("legalPerPhone", user.getMobile());
    }
    map.put("status", 4);
    // 新增加
    map.put("userId", user.getId());
    JSONObject jsonObject = HttpUtils.doPost(borrowUrl + "/api/gedAccount",
        JSONObject.toJSONString(map, SerializerFeature.WriteMapNullValue));
    // JSONObject jsonObject = HttpUtils.doPost(loanCreateAccountUrl, JSONObject.toJSONString(map));
    return jsonObject;
  }
}
