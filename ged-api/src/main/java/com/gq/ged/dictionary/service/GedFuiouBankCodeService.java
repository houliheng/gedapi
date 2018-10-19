package com.gq.ged.dictionary.service;

import com.gq.ged.dictionary.dao.model.GedFuiouBankCode;

/**
 * Created by wrh on 2018/8/2.
 */
public interface GedFuiouBankCodeService {

  /**
   * 根据银行编码查询银行信息
   * @param code
   * @return
   */
  GedFuiouBankCode getBankInfo(String code);

}
