package com.gq.ged.order.service.impl;

import com.gq.ged.order.dao.mapper.GedRechargeRecordMapper;
import com.gq.ged.order.dao.mapper.GedRepaymentRecordMapper;
import com.gq.ged.order.dao.model.GedRepaymentRecord;
import com.gq.ged.order.dao.model.GedRepaymentRecordExample;
import com.gq.ged.order.service.RechargeRepaymentRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wyq_tomorrow on 2018/7/27.
 */
@Service
public class RechargeRepaymentRecordServiceImpl implements RechargeRepaymentRecordService {

  @Resource
  GedRepaymentRecordMapper gedRepaymentRecordMapper;

  @Override
  public GedRepaymentRecord getGedRepaymentRecordByZjStreamNo(String streamNo) {
    GedRepaymentRecordExample example = new GedRepaymentRecordExample();
    example.setOrderByClause("create_time desc");
    GedRepaymentRecordExample.Criteria criteria = example.createCriteria();
    criteria.andOrderNoEqualTo(streamNo);
    List<GedRepaymentRecord> list = gedRepaymentRecordMapper.selectByExample(example);
    if (list == null || list.size() == 0) {
      return null;
    } else {
      return list.get(0);
    }
  }

  public void insertOrUpdate(GedRepaymentRecord recorde) {
    GedRepaymentRecordExample example = new GedRepaymentRecordExample();
    example.setOrderByClause("create_time desc");
    GedRepaymentRecordExample.Criteria criteria = example.createCriteria();
    criteria.andOrderNoEqualTo(recorde.getOrderNo());
    List<GedRepaymentRecord> list = gedRepaymentRecordMapper.selectByExample(example);
    if (list == null || list.size() == 0) {
      gedRepaymentRecordMapper.insertSelective(recorde);
      return;
    }
    recorde.setId(list.get(0).getId());
    gedRepaymentRecordMapper.updateByPrimaryKeySelective(recorde);
  }
}
