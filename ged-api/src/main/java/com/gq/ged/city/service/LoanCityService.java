package com.gq.ged.city.service;

import com.gq.ged.city.dao.model.LoanCityInfo;

/**
 * Created by wyq_tomorrow on 2018/1/26.
 */
public interface LoanCityService {
  /**
   * 查询城市实体
   * 
   * @param cityid
   * @return
   */
  LoanCityInfo getCityEntityById(Long cityid);
}
