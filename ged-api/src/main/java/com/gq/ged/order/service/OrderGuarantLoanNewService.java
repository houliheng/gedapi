package com.gq.ged.order.service;

import java.text.ParseException;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gq.ged.order.controller.req.GuarantConfirmReqForm;
import com.gq.ged.order.controller.req.GuaranteeForm;
import com.gq.ged.order.controller.req.PageReqForm;
import com.gq.ged.order.controller.res.DeductResult;
import com.gq.ged.order.controller.res.GuaranteeConfirmResForm;
import com.gq.ged.order.controller.res.GuaranteeLoanResForm;
import com.gq.ged.order.pc.res.*;

/**
 * Created by wrh on 2018/4/17.
 */
public interface OrderGuarantLoanNewService {
  /**
   * 查询担保借款
   *
   * @param userId
   * @return
   */
  GuaranteeLoanResForm getOrderGuarantLoanInfo(Long userId);

  /**
   * 担保记录
   *
   * @param userId
   * @return
   * @throws Exception
   */
  List<GuaranteeConfirmResForm> selectGuaranteeRecord(Long userId, PageReqForm reqForm) throws Exception;


  /**
   * 担保确认列表
   * @param userId
   * @return
   * @throws Exception
   */
  List<GuaranteeConfirmResForm> selectGuaranteeConfirm(Long userId, PageReqForm reqForm) throws Exception;

  /**
   * 担保确认按钮
   * @param userId
   */
   public void ConfirmGuarantee(Long userId, GuarantConfirmReqForm reqForm) throws  Exception;

    public void  isCanpay(List<DeductResult> list, List<RepaymentItem> datas) throws ParseException;

    /**
   * pc 担保人首页
   * @param userId
   * @return
   */
   public GuarantorIndexResForm index(Long userId) throws Exception;

    /**
     * 推送担保记录
     * @param reqForm
     */
   public void pushGuaranteeRecord(GuaranteeForm reqForm);

    /**
     * pc 端确认担保列表页
     * @param userId
     * @return
     */
   public List<ConfirmGuaranteeListResForm> confirmGuaranteeList(Long userId);

    /**
     * pc端担保纪录页面
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
   public PageInfo<GuaranteeRecordListResForm> guaranteeRecordList(Long userId, Integer pageNum, Integer pageSize);

    /**
     * pc确认担保详情页
     * @param guaranteeId
     * @return
     */
   public ConfirmGuaranteeDetailResForm confirmGuaranteeDetail(Long guaranteeId, Long userId) throws Exception;

    /**
     * pc担保纪录详情页
     * @param guaranteeId
     * @param userId
     * @return
     */
   public GuaranteeRecordDetailResForm guaranteeRecordDetail(Long guaranteeId, Long userId) throws Exception;

    /**
     * 联合授信批量删除担保关系
     * @param masterOrderNo
     */
   public void cascadingDeletion(String masterOrderNo);

    /**
     * 非联合授信删除担保关系
     * @param orderId
     */
    public void detetetionGuar(Long orderId);
}
