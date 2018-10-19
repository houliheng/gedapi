package com.gq.ged.account.service;

import com.gq.ged.account.dao.model.RsaKey;

/**
 * Created by wyq_tomorrow on 2018/3/7.
 */
public interface RsaKeyService {
  /**
   * 获取公钥
   * 
   * @param type
   * @return
   */
  String getPublicKey(Integer type);

  /**
   * 获取私钥
   * 
   * @param type
   * @return
   */
  String getPrivateKey(Integer type);

  /**
   *
   * @param type
   * @return
   */
  RsaKey getAllKeys(Integer type);
}
