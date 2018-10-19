package com.gq.ged.dictionary.service;

import com.gq.ged.dictionary.controller.res.BankInfo;
import com.gq.ged.dictionary.controller.res.LoanListResForm;
import com.gq.ged.dictionary.dao.model.SystemDictionaryItem;
import io.swagger.models.auth.In;

import java.math.BigDecimal;
import java.util.Dictionary;
import java.util.List;

/**
 * Created by wyq_tomorrow on 2018/1/28.
 */
public interface DictionaryService {
  /**
   * 获取首页金额
   * 
   * @return
   */
  String getPortalAmount();

  /**
   * 获取借钱list
   * 
   * @return
   */
  List<LoanListResForm> getLoanList();

  /**
   * 
   * @param dicId
   * @param key
   * @return
   */
  SystemDictionaryItem getDictionaryByParam(String dicId, String key);

  /**
   * 获取字典值
   * 
   * @param dicId
   * @param key
   * @return
   */
  String getDictionaryValue(String dicId, Integer key);

  /**
   * 获取银行信息
   * @return
   */
  List<BankInfo> getBanks(String itemCode);

  /**
   * 根据银行编码查询银行信息
   * @param code
   * @return
   */
  SystemDictionaryItem getBankInfo(String code);

}
