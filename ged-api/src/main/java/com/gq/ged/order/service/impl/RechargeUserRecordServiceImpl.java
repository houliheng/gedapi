package com.gq.ged.order.service.impl;

import com.gq.ged.order.dao.mapper.UserRechargeRecordMapper;
import com.gq.ged.order.dao.model.UserRechargeRecord;
import com.gq.ged.order.dao.model.UserRechargeRecordExample;
import com.gq.ged.order.service.RechargeUserRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wyq_tomorrow on 2018/7/27.
 */
@Service
public class RechargeUserRecordServiceImpl implements RechargeUserRecordService {
  @Resource
  UserRechargeRecordMapper userRechargeRecordMapper;

  @Override
  public UserRechargeRecord getGedUserRechargeRecordOrderNo(String orderNo) {
    UserRechargeRecordExample example = new UserRechargeRecordExample();
    example.setOrderByClause("create_time desc");
    UserRechargeRecordExample.Criteria criteria = example.createCriteria();
    criteria.andOrderNoEqualTo(orderNo);
    List<UserRechargeRecord> list = userRechargeRecordMapper.selectByExample(example);
    if (list == null || list.size() == 0) {
      return null;
    } else {
      return list.get(0);
    }

  }

  @Override
  public void updateUserRechargeRecordById(UserRechargeRecord rechargeRecord) {
    userRechargeRecordMapper.updateByPrimaryKeySelective(rechargeRecord);
  }

  @Override
  public void insertUserRechargeRecorde(UserRechargeRecord rechargeRecord) {
    userRechargeRecordMapper.insertSelective(rechargeRecord);
  }
}
