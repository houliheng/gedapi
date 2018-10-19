package com.gq.ged.order.service;

import com.gq.ged.order.controller.req.PaymentPlanReqForm;

/**
 * Created by wyq_tomorrow on 2018/3/7.
 */
public interface OrderPlanService {
    /**
     * 生成还款计划
     *
     * @param reqForm
     */
    void generatorOrderPlan(PaymentPlanReqForm reqForm);

}
