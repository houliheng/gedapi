package com.gq.ged.dictionary.service.impl;

import com.gq.ged.dictionary.dao.mapper.FuiouAreaMapper;
import com.gq.ged.dictionary.dao.model.FuiouArea;
import com.gq.ged.dictionary.dao.model.FuiouAreaExample;
import com.gq.ged.dictionary.service.FuyouAreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wyq_tomorrow on 2018/1/31.
 */
@Service
public class FuyouAreaServiceImpl implements FuyouAreaService {

  @Resource
  FuiouAreaMapper mapper;

  @Override
  public String getBaseCodeByFuyouCode(String code) {
    FuiouAreaExample example = new FuiouAreaExample();
    example.createCriteria().andAreaCodeEqualTo(code);
    List<FuiouArea> list = mapper.selectByExample(example);
    if (list.size() == 0) {
      return "";
    }
    return list.get(0).getAreaFullCode();
  }

  @Override
  public List<FuiouArea> selectProvinceList() {
    FuiouAreaExample example = new FuiouAreaExample();
    example.createCriteria().andAreaTypeEqualTo("1");
    List<FuiouArea> list = mapper.selectByExample(example);
    return list;
  }

  @Override
  public FuiouArea selectFuiouAreaByCode(String code) {
    FuiouAreaExample example = new FuiouAreaExample();
    example.createCriteria().andAreaCodeEqualTo(code);
    List<FuiouArea> list = mapper.selectByExample(example);
    if(list.size()>0){
      return list.get(0);
    }
    return null;
  }

  @Override
  public List<FuiouArea> selectCityList(Long provinceId) {
    FuiouAreaExample example = new FuiouAreaExample();
    example.createCriteria().andAreaTypeEqualTo("2").andParentIdEqualTo(provinceId);
    List<FuiouArea> list = mapper.selectByExample(example);
    return list;
  }

  @Override
  public FuiouArea getFuiouArea(String code) {
    FuiouArea fuiouArea = null;
    List<FuiouArea> list = mapper.getFuiouArea(code);
    if(list!=null&&list.size()!=0)
    {
      fuiouArea=list.get(0);
    }
    return fuiouArea;
  }
}
