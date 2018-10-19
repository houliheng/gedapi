package com.gq.ged.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gq.ged.common.Errors;
import com.gq.ged.common.enums.RepayMentFlag;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.order.controller.req.ContractForm;
import com.gq.ged.order.controller.req.PaymentPlanReqForm;
import com.gq.ged.order.controller.req.StagesForm;
import com.gq.ged.order.controller.res.RepayPlanTotleResForm;
import com.gq.ged.order.dao.mapper.GedOrderRepaymentPlanMapper;
import com.gq.ged.order.dao.model.GedOrder;
import com.gq.ged.order.dao.model.GedOrderExample;
import com.gq.ged.order.dao.model.GedOrderRepaymentPlan;
import com.gq.ged.order.dao.model.GedOrderRepaymentPlanExample;
import com.gq.ged.order.pc.req.RechargeForm;
import com.gq.ged.order.pc.req.RechargeReqForm;
import com.gq.ged.order.pc.res.RepayPlanResForm;
import com.gq.ged.order.pc.res.RepaymentPlan;
import com.gq.ged.order.service.OrderPlanService;
import com.gq.ged.order.service.OrderService;
import com.gq.ged.user.dao.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyq_tomorrow on 2018/3/7.
 */
@Service
public class OrderPlanServiceImpl implements OrderPlanService {
  private Logger logger = LoggerFactory.getLogger(OrderPlanServiceImpl.class);

  @Resource
  GedOrderRepaymentPlanMapper planMapper;
  @Resource
  OrderService orderService;

  @Override
  public void generatorOrderPlan(PaymentPlanReqForm reqForm) {
    // 1先删除原还款计划
    GedOrderRepaymentPlanExample example = new GedOrderRepaymentPlanExample();
    example.createCriteria().andOrderNoEqualTo(reqForm.getOrderCode());
    planMapper.deleteByExample(example);
    // 2在插入新的还款计划
    Date date = new Date();
    List<ContractForm> list = reqForm.getList();
    list.forEach(n -> {
      List<StagesForm> stagesFormList = n.getList();
      stagesFormList.forEach(x -> {
        GedOrderRepaymentPlan plan = new GedOrderRepaymentPlan();
        plan.setContractNo(n.getContractNo());
        plan.setOrderNo(reqForm.getOrderCode());
        plan.setCreditType(Integer.parseInt(reqForm.getCreditType()));
        plan.setOrderTerm(x.getTerm());
        plan.setOverdueAmount(x.getOverdueAmount());
        plan.setRepaymentFlag(x.getRepaymentFlag());
        plan.setRepaymentTime(x.getRepaymentTime());
        plan.setOverdueInterest(x.getOverdueInterest());
        plan.setOverduePenalty(x.getOverduePenalty());
        plan.setCreateTime(date);
        planMapper.insertSelective(plan);
      });
    });
  }

}
