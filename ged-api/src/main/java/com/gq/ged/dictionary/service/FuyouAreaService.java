package com.gq.ged.dictionary.service;

import com.gq.ged.dictionary.dao.model.FuiouArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wyq_tomorrow on 2018/1/31.
 */
public interface FuyouAreaService {
  /**
   * 获得国标码
   * 
   * @param code
   * @return
   */
  String getBaseCodeByFuyouCode(String code);

  /**
   * 获取所有市
   * 
   * @return
   */
  List<FuiouArea> selectProvinceList();

  /**
   * 根据code查询实体
   * 
   * @param code
   * @return
   */
  FuiouArea selectFuiouAreaByCode(String code);

  /**
   * 获取所有省
   * 
   * @return
   */
  List<FuiouArea> selectCityList(Long provinceId);

  /**
   * 根据code查询FuiouArea
   * @param code
   * @return
   */
   FuiouArea  getFuiouArea( String code);
}
