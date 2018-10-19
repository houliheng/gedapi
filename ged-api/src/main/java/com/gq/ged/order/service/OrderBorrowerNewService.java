package com.gq.ged.order.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.gq.ged.order.controller.res.DeductResult;
import com.gq.ged.order.pc.req.ContractRepayPlanDetail;
import com.gq.ged.order.pc.res.*;
import com.gq.ged.user.dao.model.User;

/**
 * Created by Levi on 2018/4/18.
 */
public interface OrderBorrowerNewService {

    /**
     * 借款人首页
     * @param user
     * @return
     */
    public BorrowerIndexResForm index(User user) throws Exception;

    /**
     * 根据userid 获取借款记录
     * @param userId
     * @return
     */
    public List<LoanRecordResForm> getLoanRecordsByUserId(Long userId);

    /**
     * 根据userid 获取借款记录 分页
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<LoanRecordResForm> getLoanRecordsPageByUserId(Long userId, Integer pageNum, Integer pageSize);

    /**
     * 根据订单编号 查询订单详情
     * @param orderNo
     * @return
     */
    public OrderDetailResForm orderDetail(String orderNo) throws Exception;

    /**
     * 我的还款列表
     * @param userId
     * @return
     */
    public List<ContractRepayPlanDetail> myRepayments(Long userId) throws Exception;

    /**
     * 缴费详情页面
     * @return
     */
    public ServiceFeeDetailResForm serviceFeeDetail(String orderNo);

    /**
     * 提现详情页
     * @param orderNo
     * @return
     */
    public WithdrawDetailResForm withdrawDetail(String orderNo) throws Exception;

    /**
     * 判断是否开户
     * @param user
     * @return
     */
    public Integer isOpenAccount(User user);

    /**
     * 根据 合同编号 获取待还计划详情
     * @param contractNos
     * @return
     */
    public JSONObject planDetail(List<String> contractNos) throws Exception;

    /**
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public  Map<String , Object> planDetailByUserId(Long userId) throws Exception;

    /**
     * 合同还款详情
     * @param contractNos
     * @return
     * @throws Exception
     */
    public JSONObject borrowConditon(List<String> contractNos) throws Exception;

    /**
     * 根据合同编号合同还款详情
     * @param userId
     * @return
     * @throws Exception
     */
    public JSONObject borrowConditonByUserId(Long userId) throws Exception;

    /**
     * 根据userId  获取
     * @return
     */
    public List<ContractRepayPlanDetail> contractRepayPlanDetails(JSONObject object);

    public void repayCopyItem(DeductResult deduct, RepaymentItem repaymentItem);

    /**
     * 计算还款日
     * @param calendar
     * @return
     */
    public int getRepaymentDay(Calendar calendar);

}
