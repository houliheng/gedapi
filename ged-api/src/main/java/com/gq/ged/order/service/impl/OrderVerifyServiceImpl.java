package com.gq.ged.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gq.ged.common.Constant;
import com.gq.ged.common.http.HttpUtils;
import com.gq.ged.order.controller.req.CreditInfoToRequest;
import com.gq.ged.order.controller.req.GqgetAssetCarInfo;
import com.gq.ged.order.controller.req.GqgetAssetHouseInfo;
import com.gq.ged.order.controller.req.GuanETInfo;
import com.gq.ged.order.controller.res.OrderVerifyResForm;
import com.gq.ged.order.dao.mapper.OrderVerifyMapper;
import com.gq.ged.order.dao.model.GedOrder;
import com.gq.ged.order.dao.model.OrderVerify;
import com.gq.ged.order.dao.model.OrderVerifyExample;
import com.gq.ged.order.service.OrderService;
import com.gq.ged.order.service.OrderVerifyService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wrh on 2018/4/25.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class OrderVerifyServiceImpl implements OrderVerifyService {
  private Logger logger = LoggerFactory.getLogger(OrderVerifyServiceImpl.class);

  @Resource
  OrderVerifyMapper orderVerifyMapper;
  @Resource
  OrderService orderService;

  @Value("${gq.borrow.url}")
  String verifyUrl;

  @Override
  public void insertOrderVerify(CreditInfoToRequest reqForm) {
    logger.info("信审信息传的参数="+ JSONObject.toJSONString(reqForm));
    String applyIdChlid = reqForm.getOrderSubNo();
    if (StringUtils.isBlank(applyIdChlid)) {// 非联合授信
      GedOrder gedOrder = orderService.selectOrderByOrderNo(reqForm.getOrderNo());
      if (gedOrder != null) {
        Long gedId = gedOrder.getId();
        //删除信审信息
        deleteVerfity(gedId);
        // 保存授信信息
        createOrderVerify(gedId, reqForm);
      }
    } else {// 联合授信
      GedOrder ge = orderService.selectOrderByOrderNo(applyIdChlid);
      if (ge != null) {
        Long gedId = ge.getId();
        //删除信审信息
        deleteVerfity(gedId);
        // 保存授信信息
        createOrderVerify(gedId, reqForm);
      }
    }
  }

  public void createOrderVerify(Long gedId, CreditInfoToRequest reqForm) {
    OrderVerify orderVerify = new OrderVerify();
    orderVerify.setOrderId(gedId);
    GuanETInfo guanETInfo=reqForm.getGuanETInfo();
    List<GqgetAssetCarInfo> cardList= reqForm.getGetAssetCardList();//车辆信息
    List<GqgetAssetHouseInfo> houseList= reqForm.getGqgetHouseList();//房屋资产信息
    orderVerify.setCompanyInfo(guanETInfo.getIntrOfCompany());// 企业信息
    orderVerify.setLoanPurpose(guanETInfo.getMixLoanUsage());// 借款用途
    orderVerify.setCompanyProductInfo(guanETInfo.getIntrOfComProduction());// 产品信息
    orderVerify.setBorrowerSanction(guanETInfo.getBorrowAndMatePunish());//借款人行政处罚
    orderVerify.setBorrowerLitigation(guanETInfo.getBorrowInvolveInfo());//借款人涉诉情况
    orderVerify.setBorrowerActInfo(guanETInfo.getBorrowCrimaAdminPunish());//借款人行事处罚
    orderVerify.setBorrowerDebtInfo(guanETInfo.getBorrowNewLoanBlance());//借款人在其他平台共债情况
    orderVerify.setBankLoanInfo(BankLoanInfo(guanETInfo));//银行贷款情况
      if(StringUtils.isNotBlank(guanETInfo.getPlatformOverdueTime())) {
          orderVerify.setOverdueNumber(Integer.parseInt(guanETInfo.getPlatformOverdueTime()));//逾期次数
      }
    orderVerify.setAssetsInfo(AssetsInfo(cardList,houseList,guanETInfo));//资产信息
     orderVerify.setRepayChanel(sourceOfDepaymentInfo(guanETInfo));//还款来源
    orderVerify.setCreateTime(new Date());
    orderVerifyMapper.insertSelective(orderVerify);
  }

  public  void  deleteVerfity(Long gedId){
    OrderVerifyExample example=new OrderVerifyExample();
    example.createCriteria().andOrderIdEqualTo(gedId);
    orderVerifyMapper.deleteByExample(example);
  }

  public String AssetsInfo(List<GqgetAssetCarInfo> cardList,List<GqgetAssetHouseInfo> houseList,GuanETInfo guanETInfo){
    Map<String,Object> param=new HashMap<String,Object>();
    List<Object> list=new ArrayList<Object>();
    List<Object> list1=new ArrayList<Object>();
    List<Object> list2=new ArrayList<Object>();
    if(cardList!=null && cardList.size()>0){
      for(GqgetAssetCarInfo carInfo:cardList){
        list.add(carInfo);
      }
    }
    if(houseList!=null && houseList.size()>0){
      for(GqgetAssetHouseInfo houseInfo:houseList){
        list1.add(houseInfo);
      }
    }
    if(guanETInfo!=null){
      if(StringUtils.isNotBlank(guanETInfo.getOther())) {
        list2.add(guanETInfo.getOther());
      }
    }
    param.put("car",list);
    param.put("house",list1);
    param.put("other",list2);
    return JSONObject.toJSONString(param);
  }

  //还款来源
  public  String sourceOfDepaymentInfo(GuanETInfo guanETInfo){
    StringBuffer stringBuffer=new StringBuffer();
    int count=1;
    if(StringUtils.isNotBlank(guanETInfo.getSourceOfDepayment1())){
      stringBuffer.append(count+":"+guanETInfo.getSourceOfDepayment1()).append(";");
    }
    if(StringUtils.isNotBlank(guanETInfo.getSourceOfDepayment2())){
      count++;
      stringBuffer.append(count+":"+guanETInfo.getSourceOfDepayment2()).append(";");
    }
    if(StringUtils.isNotBlank(guanETInfo.getSourceOfDepayment3())){
      count++;
      stringBuffer.append(count+":"+guanETInfo.getSourceOfDepayment3()).append(";");
    }
    if(StringUtils.isNotBlank(guanETInfo.getSourceOfDepayment4())){
      count++;
      stringBuffer.append(count+":"+guanETInfo.getSourceOfDepayment4());
    }
    return stringBuffer.toString();
  }

 //银行贷款情况
  public String BankLoanInfo(GuanETInfo guanETInfo){
    StringBuffer stringBuffer=new StringBuffer();
    stringBuffer.append("是否贷款:"+guanETInfo.getIsLoan()).append(",")
            .append("共几笔贷款记录:"+guanETInfo.getLoanRecode()).append(",")
            .append("是否有逾期:"+guanETInfo.getIsOverdue()).append(",")
            .append("征信记录等级:"+guanETInfo.getSourceOfCreRegist());
    return stringBuffer.toString();
  }

   //查询信审信息
  @Override
  public OrderVerifyResForm selectOrderVerify(String applyNo, String orderSubNo) throws Exception{
    OrderVerifyResForm resForm=new OrderVerifyResForm();
    Map<String,String> param=new HashMap<String,String>();
    if(StringUtils.isBlank(applyNo)){
      param.put("applyNo",orderSubNo);
      param.put("applySubNo","");
    }else{
      param.put("applyNo",applyNo);
      param.put("applySubNo",orderSubNo);
    }
    JSONObject json= HttpUtils.doPost(verifyUrl+"/api/gedCreditInfo",JSONObject.toJSONString(param));
    logger.info("信审信息返回的值=="+JSONObject.toJSONString(json));
    if (Constant.RES_CODE_SUCCESS.equals(json.get("code"))) {
      GuanETInfo guanETInfo=(GuanETInfo)json.getObject("guanETInfo",GuanETInfo.class);
      if(guanETInfo!=null){
        resForm.setCompanyInfo(guanETInfo.getIntrOfCompany());// 企业信息
        resForm.setLoanPurpose(guanETInfo.getMixLoanUsage());// 借款用途
        resForm.setCompanyProductInfo(guanETInfo.getIntrOfComProduction());// 产品信息
        resForm.setBorrowerSanction(guanETInfo.getBorrowAndMatePunish());//借款人行政处罚
        resForm.setBorrowerLitigation(guanETInfo.getBorrowInvolveInfo());//借款人涉诉情况
        resForm.setBorrowerActInfo(guanETInfo.getBorrowCrimaAdminPunish());//借款人行事处罚
        resForm.setBorrowerDebtInfo(guanETInfo.getBorrowNewLoanBlance());//借款人在其他平台共债情况
        resForm.setBankLoanInfo(BankLoanInfo(guanETInfo));//银行贷款情况
        if(StringUtils.isNotBlank(guanETInfo.getPlatformOverdueTime())) {
            resForm.setOverdueNumber(Integer.parseInt(guanETInfo.getPlatformOverdueTime()));//逾期次数
        }
        resForm.setRepayChanel(sourceOfDepaymentInfo(guanETInfo));//还款来源
      }
      JSONArray assetCardList = json.getJSONArray("getAssetCardList");
      JSONArray gqhouseList = json.getJSONArray("gqgetHouseList");
      List<GqgetAssetCarInfo> cardList = JSONObject.parseArray(assetCardList.toJSONString(), GqgetAssetCarInfo.class);
      List<GqgetAssetHouseInfo> houseList = JSONObject.parseArray(gqhouseList.toJSONString(), GqgetAssetHouseInfo.class);
        resForm.setAssetsInfo(AssetsInfo(cardList,houseList,guanETInfo));//资产信息
    }
    return resForm;
  }
}
