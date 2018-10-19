package com.gq.ged.city.service.impl;

import com.gq.ged.city.dao.mapper.LoanCityInfoMapper;
import com.gq.ged.city.dao.model.LoanCityInfo;
import com.gq.ged.city.dao.model.LoanCityInfoExample;
import com.gq.ged.city.service.LoanCityService;
import com.gq.ged.common.Errors;
import com.gq.ged.common.exception.business.BusinessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wyq_tomorrow on 2018/1/26.
 */
@Service
public class LoanCityServiceImpl implements LoanCityService {

  @Resource
  LoanCityInfoMapper loanCityInfoMapper;

  @Override
  public LoanCityInfo getCityEntityById(Long cityid) {
    LoanCityInfo info = loanCityInfoMapper.selectByPrimaryKey(cityid);
    return info;
  }
}
