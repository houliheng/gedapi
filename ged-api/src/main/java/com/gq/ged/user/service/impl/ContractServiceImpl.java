package com.gq.ged.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gq.ged.account.dao.mapper.AccountCompanyMapper;
import com.gq.ged.account.dao.model.Account;
import com.gq.ged.account.dao.model.AccountCompany;
import com.gq.ged.account.service.AccountOpenService;
import com.gq.ged.account.service.AccountService;
import com.gq.ged.city.dao.model.LoanCityInfo;
import com.gq.ged.city.service.LoanCityService;
import com.gq.ged.common.Constant;
import com.gq.ged.common.Errors;
import com.gq.ged.common.enums.JointCreditFlag;
import com.gq.ged.common.enums.MsgType;
import com.gq.ged.common.enums.ResCodeEnum;
import com.gq.ged.common.enums.UserTypeEnum;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.common.http.HttpUtils;
import com.gq.ged.common.utils.NumberToCN;
import com.gq.ged.common.utils.ValidateIdentityUtil;
import com.gq.ged.dictionary.dao.mapper.FuiouAreaMapper;
import com.gq.ged.dictionary.dao.model.FuiouArea;
import com.gq.ged.dictionary.dao.model.SystemDictionaryItem;
import com.gq.ged.dictionary.service.DictionaryService;
import com.gq.ged.dictionary.service.FuyouAreaService;
import com.gq.ged.message.api.res.MsgRedisEntity;
import com.gq.ged.order.controller.res.DeductResult;
import com.gq.ged.order.dao.mapper.GedOrderMapper;
import com.gq.ged.order.dao.model.GedOrder;
import com.gq.ged.order.service.OrderService;
import com.gq.ged.user.api.req.*;
import com.gq.ged.user.api.res.*;
import com.gq.ged.user.dao.mapper.UserMapper;
import com.gq.ged.user.dao.model.User;
import com.gq.ged.user.api.res.CompanyInfoProtocolResForm;
import com.gq.ged.user.api.res.LoanGuaranteeProtocolResForm;
import com.gq.ged.user.api.res.PersonalInfoProtocolResForm;
import com.gq.ged.user.service.ContractService;
import com.gq.ged.user.service.UserService;
import com.gq.ged.user.utils.ProtocolUtils;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 合同业务层实现类.
 *
 * @author yxh by 2018/5/31
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
public class ContractServiceImpl implements ContractService {
    private static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);
    @Resource
    private UserService userService;
    @Resource
    private OrderService orderService;
    @Resource
    private AccountService accountService;
    @Resource
    private AccountOpenService accountOpenService;
    @Resource
    private LoanCityService loanCityService;
    @Resource
    private DictionaryService dictionaryService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private GedOrderMapper orderMapper;
    @Resource
    private FuyouAreaService fuyouAreaService;


    @Value("${gq.ged.getcontract.url}")
    String contractServiceUrl;
    @Value("${gq.borrow.url}")
    String borrowUrl;
    @Value("${gq.jkfwVersion}")
    String jkfwVersion;
    @Value("${gq.qysqVersion}")
    String qysqVersion;
    @Value("${gq.grsqVersion}")
    String grsqVersion;
    @Value("${gq.dbVersion}")
    String dbVersion;
    @Value("${gq.txVersion}")
    String txVersion;
    @Value("${gq.signVersion}")
    String signVersion;
    @Value("${gq.netVersion}")
    String netVersion;


    /**
     * 授权提现涵
     *
     * @param userId
     * @return
     */
    @Override
    public WithdrawalLetter getWithdrawalLetterInfo(Long userId) throws Exception {
        logger.info("用户请求的userId===>" + userId);
        WithdrawalLetter withdrawalLetter = new WithdrawalLetter();
        User user = userService.getUserById(userId);
        if (user != null) {
            withdrawalLetter.setWtName(user.getCompanyName());
            if (user.getUserType() != null && user.getUserType() == 0) {
                withdrawalLetter.setWtCertNo(user.getIdCardNum());
                withdrawalLetter.setGedCustName(user.getMobile());
                withdrawalLetter.setWtName(user.getUsername());//委托人姓名
                withdrawalLetter.setJkCustName(user.getUsername());//借款人
            } else if (user.getUserType() != null && user.getUserType() == 1) {
                withdrawalLetter.setWtCertNo(user.getSocialCreditCode());
                withdrawalLetter.setGedCustName(user.getSocialCreditCode());
                withdrawalLetter.setJkCustName(user.getCompanyName());//借款人
                withdrawalLetter.setWtName(user.getCompanyName());//委托人姓名
            }
        }
        withdrawalLetter.setSignDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        logger.info("授权提现涵返回的数据===》" + JSON.toJSONString(withdrawalLetter));
        return withdrawalLetter;
    }

    /**
     * 借款服务协议
     *
     * @param userId
     * @return
     */
    @Override
    public LoanServiceAgreement getLoanServiceAgreementInfo(Long userId) throws Exception {
        logger.info("用户请求的userId===>" + userId);
        LoanServiceAgreement resForm = new LoanServiceAgreement();
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "获取用户信息失败");
        }
        GedOrder order = this.getOrderCode(userId);
        if (order == null) {
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "获取订单信息失败");
        }
        if (user.getUserType() != null && UserTypeEnum.PERSONAL.equals(UserTypeEnum.valueOf(user.getUserType()))) {//个人
            Account account = accountService.getAccountByUserId(userId);//查询账户信息
            if (account == null) {
                throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "获取个人账户信息失败");
            }
            resForm.setAccount(account.getCorporationName());//户名
            resForm.setBankName(account.getCompanyBankOfDepositValue());//开户行
            resForm.setBankNo(account.getCompanyAccount());//开户账号
        } else if (user.getUserType() != null && UserTypeEnum.COMPANY.equals(UserTypeEnum.valueOf(user.getUserType()))) {
            AccountCompany accountCompany = accountOpenService.queryCompanyAccountByUserId(userId);
            if (accountCompany == null) {
                throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "获取企业账户信息失败");
            }
            resForm.setAccount(accountCompany.getCompanyName());//户名
            resForm.setBankName(accountCompany.getCompanyBankOfDepositValue());//开户行
            resForm.setBankNo(accountCompany.getCompanyAccount());//开户账号
        }
        LoanCityInfo province = null;
        LoanCityInfo city = null;
        LoanCityInfo area = null;
        if (order.getContractSignProvince() != null) {
            province = loanCityService.getCityEntityById(Long.parseLong(order.getContractSignProvince()));
        }
        if (order.getContractSignCity() != null) {
            city = loanCityService.getCityEntityById(Long.parseLong(order.getContractSignCity()));
        }
        if (order.getContractSignArea() != null) {
            area = loanCityService.getCityEntityById(Long.parseLong(order.getContractSignArea()));
        }
        if (province != null) {
            resForm.setProvince(province.getCityname());
        }
        if (city != null) {
            resForm.setCity(city.getCityname());
        }
        if (area != null) {
            resForm.setArea(area.getCityname());
        }
        //获取账户管理费分期支付表
        List<RepaymentInfo> repayList = getRepaymentInfos(order);
        Collections.sort(repayList);
        resForm.setPayType(2 + "");//支付类型
        resForm.setPeriodCount(repayList.size() + "");//共几期
        resForm.setList(repayList);//账户管理费分期支付表
        resForm.setContractNo(order.getContractCode());//合同编号
        resForm.setLoanAmt(order.getLoanAmount().toString());//借款金额
        if (order.getLoanPurpose() != null) {
            SystemDictionaryItem item =
                    dictionaryService.getDictionaryByParam("LOAN_PURPOST", order.getLoanPurpose() + "");
            resForm.setLoanReason(item.getValue());//借款用途
        }
        if (order.getServiceFee() != null) {
            resForm.setServiceFee(order.getServiceFee().toString());//平台服务费
            resForm.setServiceFeeUpper(NumberToCN.number2CNMontrayUnit(order.getServiceFee()));//平台服务费转大写
            BigDecimal terraceServiceFee = order.getServiceFee().multiply(new BigDecimal(0.4, new MathContext(2))).setScale(2, BigDecimal.ROUND_DOWN);
            BigDecimal consultServiceFee = order.getServiceFee().multiply(new BigDecimal(0.6, new MathContext(2))).setScale(2, BigDecimal.ROUND_DOWN);
            //保存平台服务费和咨询服务费
            GedOrder recode = new GedOrder();
            recode.setTerraceServiceFee(terraceServiceFee);
            recode.setConsultServiceFee(consultServiceFee);
            recode.setOrderCode(order.getOrderCode());
            orderMapper.updateByOrderCodeSelective(recode);

            resForm.setServiceFee(terraceServiceFee.toString());//平台服务费
            resForm.setServiceFeeUpper(NumberToCN.number2CNMontrayUnit(terraceServiceFee));//平台服务费转大写
            resForm.setConsultServiceFee(consultServiceFee.toString());//咨询服务费
            resForm.setConsultServiceFeeUpper(NumberToCN.number2CNMontrayUnit(consultServiceFee));//咨询服务费转大写
        }
        if (order.getAccountManageFee() != null) {
            resForm.setManageFee(order.getAccountManageFee().toString());//账户管理费
            resForm.setAccountManageFeeUpper(NumberToCN.number2CNMontrayUnit(order.getAccountManageFee()));//账户管理费转大写
        }
        FuiouArea fuiouArea = fuyouAreaService.getFuiouArea(order.getManagementAddr());
        if (fuiouArea != null) {
            resForm.setCustAddress(fuiouArea.getAreaValue());//联系地址
        }
        resForm.setJkCustNameDb(user.getLegalName());//法定代表人
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());
        resForm.setSignDate(format);//签约时间
        resForm.setCustPhone(user.getMobile());//手机号码
        resForm.setCertNo(user.getLegalCardNumber());//身份证号
        if (user.getUserType() != null && UserTypeEnum.PERSONAL.equals(UserTypeEnum.valueOf(user.getUserType()))) {//个人
            resForm.setGedCustName(user.getMobile());//冠e贷注册ID==手机号码
            resForm.setJkCustName(user.getUsername());//借款人
        } else if (user.getUserType() != null && UserTypeEnum.COMPANY.equals(UserTypeEnum.valueOf(user.getUserType()))) {
            resForm.setGedCustName(user.getSocialCreditCode());//冠e贷注册ID==统一社会信用代码
            resForm.setJkCustName(user.getCompanyName());//借款人
        }
        logger.info("借款服务协议返回的数据===》" + JSON.toJSONString(resForm));
        return resForm;
    }

    /**
     * 获取账户管理费分期支付表
     *
     * @param order
     * @return
     * @throws Exception
     */
    private List<RepaymentInfo> getRepaymentInfos(GedOrder order) throws Exception {
        ContractNoReqForm contractNoReqForm = new ContractNoReqForm();
        contractNoReqForm.setContractNo(order.getContractCode());
        JSONObject jsonObject = HttpUtils.doPost(borrowUrl + "/api/gedActivitiRepayPlan", JSON.toJSONString(contractNoReqForm));
        if (jsonObject == null) {
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "获取借款系统返回的账户管理费分期支付表失败");
        }
        List<RepaymentInfo> repayList = new ArrayList<RepaymentInfo>();
        JSONArray jsonArray = (JSONArray) jsonObject.get("list");
        String js = JSON.toJSONString(jsonArray);
        List<AccountManagerResForm> list = JSON.parseArray(js, AccountManagerResForm.class);
        if (list == null||list.size()==0) {
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "获取借款系统返回的账户管理费分期支付表失败");
        }
        RepaymentInfo reinfo = null;
        for (AccountManagerResForm re : list) {
            reinfo = new RepaymentInfo();
            reinfo.setPeriod(re.getPeriodNum());
            reinfo.setPaydate(re.getDeductDate());
            reinfo.setPayAmount(re.getManagementFee());
            repayList.add(reinfo);
        }
        if (repayList == null || repayList.size() == 0) {
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "获取借款系统返回的账户管理费分期支付表失败");
        }
        return repayList;
    }

    /**
     * 电子签名数字证书
     *
     * @param userId
     * @return
     */
    @Override
    public ElectronicLetter getElectronicLetterInfo(Long userId) throws Exception {
        logger.info("用户请求的userId===>" + userId);
        ElectronicLetter electronicLetter = new ElectronicLetter();
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "获取用户信息失败");
        }
        electronicLetter.setCustName(user.getUsername());
        if (user.getUserType() != null && UserTypeEnum.PERSONAL.equals(UserTypeEnum.valueOf(user.getUserType()))) {
            electronicLetter.setCertNo(user.getIdCardNum());
            electronicLetter.setJkCustName(user.getUsername());
        } else if (user.getUserType() != null && UserTypeEnum.COMPANY.equals(UserTypeEnum.valueOf(user.getUserType()))) {
            electronicLetter.setCertNo(user.getSocialCreditCode());
            electronicLetter.setJkCustName(user.getCompanyName());
        }
        electronicLetter.setSignDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        logger.info("电子签名数字证书返回的数据===》" + JSON.toJSONString(electronicLetter));
        return electronicLetter;
    }

    /**
     * 个人信息采集
     *
     * @param userId 用户主键
     * @return
     */
    @Override
    public PersonalInfoProtocolResForm getPersonalInfoCollectionProtocol(Long userId) throws Exception {
        User user = this.getUserById(userId);
        Account account = accountService.getAccountByUserId(userId);
        return ProtocolUtils.transPersonalInfoProtocol(user, account);
    }

    /**
     * 企业信息采集
     *
     * @param userId 用户主键
     * @return
     */
    @Override
    public CompanyInfoProtocolResForm getCompanyInfoCollectionProtocol(Long userId) throws Exception {
        User user = this.getUserById(userId);
        return ProtocolUtils.transCompanyInfoProtocol(user);
    }

    public User getUserById(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * 网络借贷平台借款情况披露及承诺书
     *
     * @param userId  用户主键
     * @param reqForm 请求参数
     * @return
     */
    @Override
    public LoanGuaranteeProtocolResForm getLoanGuaranteeProtocol(Long userId, LoanGuaranteeProtocolReqForm reqForm) throws Exception {
        LoanGuaranteeProtocolResForm resForm = getLoanGuaranteeProtocol(userId);
        GedOrder order = orderService.selectOrderByOrderNo(reqForm.getOrderNo());
        order.setOtherLoanAmount(reqForm.getOtherPlatformLoanAmount());
        orderMapper.updateOtherLoanAmount(order);
        resForm.setLoanAmount(order.getLoanAmount().toString());
        resForm.setOtherPlatformLoanAmount(reqForm.getOtherPlatformLoanAmount());
        return resForm;
    }

    @Override
    public LoanGuaranteeProtocolResForm getLoanGuaranteeProtocol(Long userId) throws Exception {
        User user = this.getUserById(userId);
        Account account = accountService.getAccountByUserId(userId);
        return ProtocolUtils.transLoanGuaranteeProtocol(user, account);
    }

    /**
     * 担保函
     *
     * @param orderNo 订单编号
     * @param userId  用户主键
     * @return
     */
    @Override
    public GuarantorLetterResForm getGuaranteeLetter(String orderNo, Long userId) throws Exception {
        GedOrder gedOrder = orderService.selectOrderByOrderNo(orderNo);
        if (gedOrder == null) {
            throw new BusinessException(Errors.SYSTEM_DATA_NOT_FOUND, "担保函获取订单失败");
        }
        GuarantorLetterResForm resForm = ProtocolUtils.guarantorLetterCommon(gedOrder);
        User user = this.getUserById(userId);
        if (user == null) {
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "获取用户信息失败");
        }
        if (UserTypeEnum.PERSONAL.equals(UserTypeEnum.valueOf(user.getUserType()))) {
            Account userAccount = accountService.getAccountByUserId(userId);
            if (userAccount == null) {
                throw new BusinessException(Errors.SYSTEM_DATA_NOT_FOUND, "担保函获取账户信息失败");
            }
            resForm.setGuarantor(userAccount.getCorporationName());
        } else {
            resForm.setLegalPerson(user.getLegalName());
            resForm.setGuarantor(user.getCompanyName());
        }
        Long borrowerUserId = gedOrder.getUserId();
        User borrower = this.getUserById(borrowerUserId);
        if (UserTypeEnum.PERSONAL.equals(UserTypeEnum.valueOf(borrower.getUserType()))) {
            Account borrowerAccount = accountService.getAccountByUserId(borrowerUserId);
            if (borrowerAccount == null) {
                throw new BusinessException(Errors.SYSTEM_DATA_NOT_FOUND, "担保函获取账户信息失败");
            }
            resForm.setBorrower(borrowerAccount.getCorporationName());
            resForm.setIdentityNo(borrowerAccount.getCorporationCardNum());
        } else {
            resForm.setBorrower(borrower.getCompanyName());
            resForm.setSocialCreditCode(borrower.getSocialCreditCode());
        }
        return resForm;
    }


    /**
     * 企业立即签署
     *
     * @param userId
     * @param procurationSignatureReqForm
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> companyImmediatelySigned(Long userId, ProcurationSignatureReqForm procurationSignatureReqForm) throws Exception {
        Map<String, Object> resForm = new HashMap<String, Object>();
        GedOrder order = this.getOrderCode(userId);
        User user = userService.getUserById(userId);
        if (order.getSignContractFlag() == 1) {
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "该合同已经签署过了，请勿重复签署！");
        }
        if (user == null) {
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "获取用户信息失败");
        }
        if (UserTypeEnum.PERSONAL.equals(UserTypeEnum.valueOf(user.getUserType()))) {
            throw new BusinessException(Errors.SYSTEM_REQUEST_PARAM_ERROR, "用户类型不匹配，该接口设计企业签署接口，请用企业用户签署");
        }

        if (order == null) {
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "获取订单信息失败");
        }
        if (order.getContractCode() == null) {
            throw new BusinessException(Errors.NOT_CONTRACT_CODE, "合同编号不存在");
        }
        //获取服务协议请求参数
        LoanServiceAgreement loanServiceAgreementInfo = this.getLoanServiceAgreementInfo(userId);
        ElectronicLetter electronicLetterInfo = this.getElectronicLetterInfo(userId);
        WithdrawalLetter withdrawalLetterInfo = this.getWithdrawalLetterInfo(userId);
        CompanyInfoProtocolResForm companyInfoCollectionProtocol = this.getCompanyInfoCollectionProtocol(userId);
        LoanGuaranteeProtocolResForm loanGuaranteeProtocol = this.getLoanGuaranteeProtocol(userId);
        /**
         * 企业签署必填字段验证
         */
        logger.info("企业签署必填字段验证。。。");
        this.checkFormParamCompany(loanServiceAgreementInfo,electronicLetterInfo,withdrawalLetterInfo,companyInfoCollectionProtocol,loanGuaranteeProtocol,order);

        if (loanServiceAgreementInfo == null || electronicLetterInfo == null || withdrawalLetterInfo == null
                || companyInfoCollectionProtocol == null || loanGuaranteeProtocol == null || user == null) {
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "获取合同服务协议内的参数失败");
        }
        CompanyImmediateSignReqFrom immediateSignReqFrom = new CompanyImmediateSignReqFrom(
                loanServiceAgreementInfo.getContractNo(),
                loanServiceAgreementInfo.getJkCustName(),
                loanServiceAgreementInfo.getGedCustName(),
                loanServiceAgreementInfo.getJkCustNameDb(),
                loanServiceAgreementInfo.getCertNo(),
                loanServiceAgreementInfo.getCustAddress(),
                loanServiceAgreementInfo.getCustPhone(),
                loanServiceAgreementInfo.getCustEmail(),
                loanServiceAgreementInfo.getLoanAmt(),
                loanServiceAgreementInfo.getLoanReason(),
                loanServiceAgreementInfo.getServiceFee(),
                loanServiceAgreementInfo.getServiceFeeUpper(),
                loanServiceAgreementInfo.getManageFee(),
                loanServiceAgreementInfo.getAccountManageFeeUpper(),
                loanServiceAgreementInfo.getPayType(),
                loanServiceAgreementInfo.getPeriodCount(),
                loanServiceAgreementInfo.getList(),
                loanServiceAgreementInfo.getAccount(),
                loanServiceAgreementInfo.getBankName(),
                loanServiceAgreementInfo.getBankNo(),
                loanServiceAgreementInfo.getSignDate(),
                withdrawalLetterInfo.getWtName(),
                withdrawalLetterInfo.getWtCertNo(),
                withdrawalLetterInfo.getGedCustName(),
                withdrawalLetterInfo.getJkCustName(),
                withdrawalLetterInfo.getSignDate(),
                electronicLetterInfo.getCustName(),
                electronicLetterInfo.getCertNo(),
                electronicLetterInfo.getJkCustName(),
                electronicLetterInfo.getSignDate(),
                companyInfoCollectionProtocol.getName(),
                companyInfoCollectionProtocol.getIdentityNo(),
                companyInfoCollectionProtocol.getCompany(),
                companyInfoCollectionProtocol.getSocialCreditCode(),
                companyInfoCollectionProtocol.getRegisterId(),
                companyInfoCollectionProtocol.getAuthorizer(),
                companyInfoCollectionProtocol.getLegalPerson(),
                companyInfoCollectionProtocol.getCertificateType(),
                companyInfoCollectionProtocol.getCertificateNo(),
                companyInfoCollectionProtocol.getDate(),
                loanGuaranteeProtocol.getName(),
                loanGuaranteeProtocol.getLegalPerson(),
                loanGuaranteeProtocol.getDate(),
                "201", "202", "203", "204", "205", "206",
                jkfwVersion, qysqVersion, grsqVersion, dbVersion, txVersion, signVersion, "2"
        );
        immediateSignReqFrom.setServiceFee(loanServiceAgreementInfo.getServiceFee());//平台服务费
        immediateSignReqFrom.setServiceFeeUpper(loanServiceAgreementInfo.getServiceFeeUpper());//平台服务费转大写
        immediateSignReqFrom.setConsultServiceFee(loanServiceAgreementInfo.getConsultServiceFee());//咨询服务费
        immediateSignReqFrom.setConsultServiceFeeUpper(loanServiceAgreementInfo.getConsultServiceFeeUpper());//咨询服务费转大写
        LoanCityInfo province = null;
        LoanCityInfo city = null;
        LoanCityInfo area = null;
        if (order.getContractSignProvince() != null) {
            province = loanCityService.getCityEntityById(Long.parseLong(order.getContractSignProvince()));
        }
        if (order.getContractSignCity() != null) {
            city = loanCityService.getCityEntityById(Long.parseLong(order.getContractSignCity()));
        }
        if (order.getContractSignArea() != null) {
            area = loanCityService.getCityEntityById(Long.parseLong(order.getContractSignArea()));
        }
        if (province != null) {
            immediateSignReqFrom.setProvince(province.getCityname());
        }
        if (city != null) {
            immediateSignReqFrom.setCity(city.getCityname());
        }
        if (area != null) {
            immediateSignReqFrom.setArea(area.getCityname());
        }

        immediateSignReqFrom.setNetVersion(netVersion);
        immediateSignReqFrom.setNetContractType("207");
        GedOrder gedOrder = this.getOrderCode(userId);
        if (gedOrder == null) {
            throw new BusinessException(Errors.SYSTEM_DATA_NOT_FOUND, "获取订单信息失败");
        }

        immediateSignReqFrom.setGetLoanAmt(gedOrder.getLoanAmount().toString());
        //签约代表人为空
        if (procurationSignatureReqForm.getSignatoryName() == null || procurationSignatureReqForm.getSignatoryName().length() == 0) {
            throw new BusinessException(Errors.SIGNERNAME_ISNULL_ERROR);
        }
        //手机号码为空
        if (procurationSignatureReqForm.getMobile() == null) {
            throw new BusinessException(Errors.MOBILE_IS_NULL_ERROR);
        }
        //手机号码格式不对
        if (procurationSignatureReqForm.getMobile() != null && !ValidateIdentityUtil.isMobileNO(procurationSignatureReqForm.getMobile())) {
            throw new BusinessException(Errors.MOBILE_IS_ERROR);
        }
        //手机短信验证码验证
        //validateSmCode(procurationSignatureReqForm);
        immediateSignReqFrom.setSignatoryName(procurationSignatureReqForm.getSignatoryName());//签约代表人姓名
        immediateSignReqFrom.setSignatoryIdentity(procurationSignatureReqForm.getSignatoryIdentity());//签约代表人身份证号码

        immediateSignReqFrom.setMobile(procurationSignatureReqForm.getMobile());//手机号码
        immediateSignReqFrom.setUserTypeFlag("1");//用户类型0个人 1企业
        immediateSignReqFrom.setFileId(procurationSignatureReqForm.getFileId());//企业电子签名委托书
        immediateSignReqFrom.setLoanSumAmt(procurationSignatureReqForm.getOtherLoanAmt());//其他网络借贷平台借款总额
        //将对象转换成json格式参数
        String contractData = JSON.toJSONString(immediateSignReqFrom);
        logger.info("立即签署请求参数json   开始==>" + contractData);
        logger.info("立即签署请求参数json    结束==>");
        logger.info("调用合同生成接口开始======时间=======>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        JSONObject jsonObject = HttpUtils.doPost(contractServiceUrl + "/api/contract/loanRelatedCompanyContract", contractData);
        logger.info("调用合同生成接口结束======时间=======>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if (jsonObject == null) {
            resForm.put("resCode", "0001");
            resForm.put("Message", "企业立即签署失败,生成合同时出现错误");
            logger.info(JSON.toJSONString(resForm));
            return resForm;
        }
        //调用成功
        if (Constant.RES_CODE_SUCCESS.equals(jsonObject.getString("code"))) {
            try {
                //修改订单Order表
                orderService.updateOrderByContractNo(loanServiceAgreementInfo.getContractNo());
                //调用借款系统同步更新
                ContractNoReqForm contractNoReqForm = new ContractNoReqForm();
                contractNoReqForm.setContractNo(loanServiceAgreementInfo.getContractNo());
                String contractNo = JSON.toJSONString(contractNoReqForm).toString();
                logger.info("contractNo:", contractNo);
                HttpUtils.doPost(borrowUrl + "/api/updateContractSignFlag", contractNo);
                logger.info("签署状态同步成功");
                resForm.put("resCode", "0000");
                resForm.put("orderCode", gedOrder.getOrderCode());
                resForm.put("Message", "个人立即签署成功");
                logger.info(JSON.toJSONString(resForm));
                return resForm;
            } catch (Exception e) {
                resForm.put("resCode", "0001");
                resForm.put("Message", "企业立即签署异常，原因：" + e.getMessage());
                logger.info(JSON.toJSONString(resForm));
                return resForm;
            }
        } else {
            resForm.put("resCode", "0001");
            resForm.put("Message", "企业立即签署失败,生成合同时出现错误");
            logger.info(JSON.toJSONString(resForm));
            return resForm;
        }
    }

    /**
     * 企业立即签署  ======= 验证必填参数
     * @param loanServiceAgreementInfo
     * @param electronicLetterInfo
     * @param withdrawalLetterInfo
     * @param companyInfoCollectionProtocol
     * @param loanGuaranteeProtocol
     * @param order
     */
    private void checkFormParamCompany(LoanServiceAgreement loanServiceAgreementInfo, ElectronicLetter electronicLetterInfo, WithdrawalLetter withdrawalLetterInfo, CompanyInfoProtocolResForm companyInfoCollectionProtocol, LoanGuaranteeProtocolResForm loanGuaranteeProtocol,GedOrder order) {
        /**
         * 借款服务协议必填字段验证
         */
        if(StringUtils.isBlank(loanServiceAgreementInfo.getContractNo())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，合同号缺失！");
        }
        if(StringUtils.isBlank(loanServiceAgreementInfo.getJkCustName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，借款人姓名缺失！");
        }
        if(StringUtils.isBlank(loanServiceAgreementInfo.getGedCustName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，冠易贷注册ID缺失！");
        }
        if(StringUtils.isBlank(loanServiceAgreementInfo.getCertNo())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，借款人身份证号码缺失！");
        }
        if(StringUtils.isBlank(loanServiceAgreementInfo.getLoanAmt())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，借款金额缺失！");
        }
        if(StringUtils.isBlank(loanServiceAgreementInfo.getLoanReason())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，借款用途缺失！");
        }
        if(StringUtils.isBlank(loanServiceAgreementInfo.getPeriodCount())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，支付管理费期数缺失！");
        }
        if(loanServiceAgreementInfo.getList()==null){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，账户管理费分期支付列表缺失！");
        }
        if(StringUtils.isBlank(loanServiceAgreementInfo.getAccount())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，账户缺失！");
        }
        if(StringUtils.isBlank(loanServiceAgreementInfo.getBankName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，开户银行缺失！");
        }
        if(StringUtils.isBlank(loanServiceAgreementInfo.getBankNo())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，开户银行账号缺失！");
        }

        /**
         * 企业信息采集  必填字段验证
         */
        if(StringUtils.isBlank(companyInfoCollectionProtocol.getName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"企业信息采集，法人名称缺失！");
        }
        if(StringUtils.isBlank(companyInfoCollectionProtocol.getIdentityNo())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"企业信息采集，身份证号码缺失！");
        }
        if(StringUtils.isBlank(companyInfoCollectionProtocol.getCompany())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"企业信息采集，公司名称缺失！");
        }
        if(StringUtils.isBlank(companyInfoCollectionProtocol.getSocialCreditCode())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"企业信息采集，统一社会信用代码缺失！");
        }
        if(StringUtils.isBlank(companyInfoCollectionProtocol.getRegisterId())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"企业信息采集，注册ID缺失！");
        }
        if(StringUtils.isBlank(companyInfoCollectionProtocol.getAuthorizer())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"企业信息采集，授权人缺失！");
        }
        if(StringUtils.isBlank(companyInfoCollectionProtocol.getLegalPerson())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"企业信息采集，法定代表人缺失！");
        }
        if(StringUtils.isBlank(companyInfoCollectionProtocol.getCertificateType())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"企业信息采集，证件类型缺失！");
        }
        if(StringUtils.isBlank(companyInfoCollectionProtocol.getCertificateNo())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"企业信息采集，证件号码缺失！");
        }
        /**
         * 授权提现涵必填字段
         */
        if(StringUtils.isBlank(withdrawalLetterInfo.getWtName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"授权提现涵,委托人姓名缺失！");
        }
        if(StringUtils.isBlank(withdrawalLetterInfo.getWtCertNo())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"授权提现涵,身份证号码缺失！");
        }
        if(StringUtils.isBlank(withdrawalLetterInfo.getGedCustName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"授权提现涵,冠易贷注册ID缺失！");
        }
        if(StringUtils.isBlank(withdrawalLetterInfo.getJkCustName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"授权提现涵,借款人姓名缺失！");
        }
        /**
         * 电子签名数字证书  必填字段验证
         */
        if(StringUtils.isBlank(electronicLetterInfo.getCustName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"电子签名数字证书，用户名称缺失！");
        }
        if(StringUtils.isBlank(electronicLetterInfo.getCertNo())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"电子签名数字证书，身份证号码缺失！");
        }
        if(StringUtils.isBlank(electronicLetterInfo.getJkCustName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"电子签名数字证书，借款人名称缺失！");
        }


        /**
         * 网络借贷平台借款情况披露及承诺书.  必填字段验证
         */
        if(StringUtils.isBlank(loanGuaranteeProtocol.getName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"网络借贷平台借款情况披露及承诺书,自然人姓名缺失！");
        }
        /**
         * 订单表 必填字段
         */
        if(StringUtils.isBlank(order.getLoanAmount().toString())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"本人拟在冠e通平台借款金额字段缺失！");
        }


    }

    /**
     * 个人签署  必填字段验证
     * @param loanServiceAgreementInfo
     * @param electronicLetterInfo
     * @param withdrawalLetterInfo
     * @param personalInfoCollectionProtocol
     * @param loanGuaranteeProtocol
     * @param order
     */
    private void checkFormParamPerson(LoanServiceAgreement loanServiceAgreementInfo, ElectronicLetter electronicLetterInfo, WithdrawalLetter withdrawalLetterInfo, PersonalInfoProtocolResForm personalInfoCollectionProtocol, LoanGuaranteeProtocolResForm loanGuaranteeProtocol, GedOrder order) {
        /**
         * 借款服务协议必填字段验证
         */
        if(StringUtils.isBlank(loanServiceAgreementInfo.getContractNo())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，合同号缺失！");
        }
        if(StringUtils.isBlank(loanServiceAgreementInfo.getJkCustName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，借款人姓名缺失！");
        }
        if(StringUtils.isBlank(loanServiceAgreementInfo.getGedCustName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，冠易贷注册ID缺失！");
        }
        if(StringUtils.isBlank(loanServiceAgreementInfo.getCertNo())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，借款人身份证号码缺失！");
        }
        if(StringUtils.isBlank(loanServiceAgreementInfo.getLoanAmt())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，借款金额缺失！");
        }
        if(StringUtils.isBlank(loanServiceAgreementInfo.getLoanReason())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，借款用途缺失！");
        }
        if(StringUtils.isBlank(loanServiceAgreementInfo.getPeriodCount())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，支付管理费期数缺失！");
        }
        if(loanServiceAgreementInfo.getList()==null){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，账户管理费分期支付列表缺失！");
        }
        if(StringUtils.isBlank(loanServiceAgreementInfo.getAccount())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，账户缺失！");
        }
        if(StringUtils.isBlank(loanServiceAgreementInfo.getBankName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，开户银行缺失！");
        }
        if(StringUtils.isBlank(loanServiceAgreementInfo.getBankNo())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"借款服务协议，开户银行账号缺失！");
        }

        /**
         * 个人信息采集  必填字段验证
         */
        if(StringUtils.isBlank(personalInfoCollectionProtocol.getName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"个人信息采集，法人名称缺失！");
        }
        if(StringUtils.isBlank(personalInfoCollectionProtocol.getIdentityNo())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"个人信息采集，身份证号码缺失！");
        }

        if(StringUtils.isBlank(personalInfoCollectionProtocol.getRegisterId())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"个人信息采集，注册ID缺失！");
        }
        if(StringUtils.isBlank(personalInfoCollectionProtocol.getAuthorizer())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"个人信息采集，授权人缺失！");
        }

        if(StringUtils.isBlank(personalInfoCollectionProtocol.getCertificateType())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"个人信息采集，证件类型缺失！");
        }
        if(StringUtils.isBlank(personalInfoCollectionProtocol.getCertificateNo())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"个人信息采集，证件号码缺失！");
        }
        /**
         * 授权提现涵必填字段
         */
        if(StringUtils.isBlank(withdrawalLetterInfo.getWtName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"授权提现涵,委托人姓名缺失！");
        }
        if(StringUtils.isBlank(withdrawalLetterInfo.getWtCertNo())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"授权提现涵,身份证号码缺失！");
        }
        if(StringUtils.isBlank(withdrawalLetterInfo.getGedCustName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"授权提现涵,冠易贷注册ID缺失！");
        }
        if(StringUtils.isBlank(withdrawalLetterInfo.getJkCustName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"授权提现涵,借款人姓名缺失！");
        }
        /**
         * 电子签名数字证书  必填字段验证
         */
        if(StringUtils.isBlank(electronicLetterInfo.getCustName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"电子签名数字证书，用户名称缺失！");
        }
        if(StringUtils.isBlank(electronicLetterInfo.getCertNo())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"电子签名数字证书，身份证号码缺失！");
        }
        if(StringUtils.isBlank(electronicLetterInfo.getJkCustName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"电子签名数字证书，借款人名称缺失！");
        }


        /**
         * 网络借贷平台借款情况披露及承诺书.  必填字段验证
         */
        if(StringUtils.isBlank(loanGuaranteeProtocol.getName())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"网络借贷平台借款情况披露及承诺书,自然人姓名缺失！");
        }
        /**
         * 订单表 必填字段
         */
        if(StringUtils.isBlank(order.getLoanAmount().toString())){
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR,"本人拟在冠e通平台借款金额字段缺失！");
        }
    }

    private GedOrder getOrderCode(Long userId) {
        List<GedOrder> gedOrders = orderService.selectGedOrder(userId);
        GedOrder gedOrder = null;
        if (gedOrders != null && gedOrders.size() != 0) {
            gedOrder = gedOrders.get(0);
        }
        return gedOrder;
    }

    /**
     * 个人立即签约
     *
     * @param userId
     * @param procurationSignatureReqForm
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> personalImmediatelySigned(Long userId, ProcurationSignatureReqForm procurationSignatureReqForm) throws Exception {
        Map<String, Object> resForm = new HashMap<String, Object>();
        GedOrder order = this.getOrderCode(userId);
        User user = userService.getUserById(userId);
        if (order.getSignContractFlag() == 1) {
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "该合同已经签署过了，请勿重复签署！");
        }
        if (user == null) {
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "获取用户信息失败");
        }
        if (UserTypeEnum.COMPANY.equals(UserTypeEnum.valueOf(user.getUserType()))) {
            throw new BusinessException(Errors.SYSTEM_REQUEST_PARAM_ERROR, "用户类型不匹配，该接口是个人签署接口，请用个人用户签署");
        }
        if (order == null) {
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "获取订单信息失败");
        }
        if (order.getContractCode() == null) {
            throw new BusinessException(Errors.NOT_CONTRACT_CODE, "合同编号不存在");
        }
        //获取协议参数
        LoanServiceAgreement loanServiceAgreementInfo = this.getLoanServiceAgreementInfo(userId);
        ElectronicLetter electronicLetterInfo = this.getElectronicLetterInfo(userId);
        WithdrawalLetter withdrawalLetterInfo = this.getWithdrawalLetterInfo(userId);
        PersonalInfoProtocolResForm personalInfoCollectionProtocol = this.getPersonalInfoCollectionProtocol(userId);
        LoanGuaranteeProtocolResForm loanGuaranteeProtocol = this.getLoanGuaranteeProtocol(userId);
        /**
         * 个人签署必填字段验证
         */
        logger.info("个人签署必填字段验证。。。");
        this.checkFormParamPerson(loanServiceAgreementInfo,electronicLetterInfo,withdrawalLetterInfo,personalInfoCollectionProtocol,
                loanGuaranteeProtocol,order);
        if (loanServiceAgreementInfo == null || electronicLetterInfo == null || withdrawalLetterInfo == null
                || personalInfoCollectionProtocol == null || loanGuaranteeProtocol == null || user == null) {
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "获取合同服务协议内的参数失败");
        }
        PersonalImmediateSignReqFrom immediateSignReqFrom = new PersonalImmediateSignReqFrom(
                loanServiceAgreementInfo.getContractNo(),
                loanServiceAgreementInfo.getJkCustName(),
                loanServiceAgreementInfo.getGedCustName(),
                loanServiceAgreementInfo.getJkCustNameDb(),
                loanServiceAgreementInfo.getCertNo(),
                loanServiceAgreementInfo.getCustAddress(),
                loanServiceAgreementInfo.getCustPhone(),
                loanServiceAgreementInfo.getCustEmail(),
                loanServiceAgreementInfo.getLoanAmt(),
                loanServiceAgreementInfo.getLoanReason(),
                loanServiceAgreementInfo.getServiceFee(),
                loanServiceAgreementInfo.getServiceFeeUpper(),
                loanServiceAgreementInfo.getManageFee(),
                loanServiceAgreementInfo.getAccountManageFeeUpper(),
                loanServiceAgreementInfo.getPayType(),
                loanServiceAgreementInfo.getPeriodCount(),
                loanServiceAgreementInfo.getList(),
                loanServiceAgreementInfo.getAccount(),
                loanServiceAgreementInfo.getBankName(),
                loanServiceAgreementInfo.getBankNo(),
                loanServiceAgreementInfo.getSignDate(),
                withdrawalLetterInfo.getWtName(),
                withdrawalLetterInfo.getWtCertNo(),
                withdrawalLetterInfo.getGedCustName(),
                withdrawalLetterInfo.getJkCustName(),
                withdrawalLetterInfo.getSignDate(),
                electronicLetterInfo.getCustName(),
                electronicLetterInfo.getCertNo(),
                electronicLetterInfo.getJkCustName(),
                electronicLetterInfo.getSignDate(),
                personalInfoCollectionProtocol.getName(),
                personalInfoCollectionProtocol.getIdentityNo(),
                personalInfoCollectionProtocol.getRegisterId(),
                personalInfoCollectionProtocol.getAuthorizer(),
                personalInfoCollectionProtocol.getCertificateType(),
                personalInfoCollectionProtocol.getCertificateNo(),
                personalInfoCollectionProtocol.getDate(),
                loanGuaranteeProtocol.getName(),
                loanGuaranteeProtocol.getLegalPerson(),
                loanGuaranteeProtocol.getDate(),
                "201", "202", "203", "204", "205", "206",
                jkfwVersion, qysqVersion, grsqVersion, dbVersion, txVersion, signVersion, "2"
        );
        immediateSignReqFrom.setServiceFee(loanServiceAgreementInfo.getServiceFee());//平台服务费
        immediateSignReqFrom.setServiceFeeUpper(loanServiceAgreementInfo.getServiceFeeUpper());//平台服务费转大写
        immediateSignReqFrom.setConsultServiceFee(loanServiceAgreementInfo.getConsultServiceFee());//咨询服务费
        immediateSignReqFrom.setConsultServiceFeeUpper(loanServiceAgreementInfo.getConsultServiceFeeUpper());//咨询服务费转大写
        LoanCityInfo province = null;
        LoanCityInfo city = null;
        LoanCityInfo area = null;
        if (order.getContractSignProvince() != null) {
            province = loanCityService.getCityEntityById(Long.parseLong(order.getContractSignProvince()));
        }
        if (order.getContractSignCity() != null) {
            city = loanCityService.getCityEntityById(Long.parseLong(order.getContractSignCity()));
        }
        if (order.getContractSignArea() != null) {
            area = loanCityService.getCityEntityById(Long.parseLong(order.getContractSignArea()));
        }
        if (province != null) {
            immediateSignReqFrom.setProvince(province.getCityname());
        }
        if (city != null) {
            immediateSignReqFrom.setCity(city.getCityname());
        }
        if (area != null) {
            immediateSignReqFrom.setArea(area.getCityname());
        }
        immediateSignReqFrom.setNetVersion(netVersion);
        immediateSignReqFrom.setNetContractType("207");
        immediateSignReqFrom.setLoanSumAmt(procurationSignatureReqForm.getOtherLoanAmt());//其他平台借款总额
        //验证手机短信验证码
        //validateSmCode(procurationSignatureReqForm);
        immediateSignReqFrom.setUserTypeFlag("0");//用户类型标识0个人1企业

        GedOrder gedOrder = this.getOrderCode(userId);
        if (gedOrder == null) {
            throw new BusinessException(Errors.SYSTEM_DATA_NOT_FOUND, "获取订单信息失败");
        }
        immediateSignReqFrom.setGetLoanAmt(gedOrder.getLoanAmount().toString());//借款总额
        //转换成json字符串参数
        String contractData = JSON.toJSONString(immediateSignReqFrom);
        logger.info("立即签署请求参数json==>" + contractData);
        logger.info("立即签署请求参数json==>结束");
        logger.info("调用合同生成接口开始======时间=======>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        JSONObject jsonObject = HttpUtils.doPost(contractServiceUrl + "/api/contract/loanRelatedPersonContract", contractData);
        logger.info("调用合同生成接口结束======时间=======>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //获取返回状态码
        if (jsonObject == null) {
            resForm.put("resCode", "0001");
            resForm.put("Message", "个人立即签署失败,生成合同时出现错误");
            logger.info(JSON.toJSONString(resForm));
            return resForm;
        }
        //调用成功
        if (Constant.RES_CODE_SUCCESS.equals(jsonObject.getString("code"))) {
            try {
                //修改订单Order表
                orderService.updateOrderByContractNo(loanServiceAgreementInfo.getContractNo());
                //调用借款系统同步更新
                ContractNoReqForm contractNoReqForm = new ContractNoReqForm();
                contractNoReqForm.setContractNo(loanServiceAgreementInfo.getContractNo());
                String contractNo = JSON.toJSONString(contractNoReqForm).toString();
                logger.info("contractNo:", contractNo);
                HttpUtils.doPost(borrowUrl + "/api/updateContractSignFlag", contractNo);
                logger.info("签署状态同步成功");
                resForm.put("resCode", "0000");
                resForm.put("Message", "个人立即签署成功");
                resForm.put("orderCode", gedOrder.getOrderCode());
                logger.info(JSON.toJSONString(resForm));
                return resForm;
            } catch (Exception e) {
                resForm.put("resCode", "0001");
                resForm.put("Message", "个人立即签署异常，原因：" + e.getMessage());
                logger.info(JSON.toJSONString(resForm));
                return resForm;
            }
        } else {
            resForm.put("resCode", "0001");
            resForm.put("Message", "个人立即签署失败,生成合同时出现错误");
            logger.info(JSON.toJSONString(resForm));
            return resForm;
        }
    }


    @Override
    public Map<String, Object> personFormValidate(PersonReqForm personReqForm) {
        Map<String, Object> map = new HashMap<String, Object>();
        logger.info("otherLoanAmt:" + personReqForm.getOtherLoanAmt());
        logger.info("mobile:" + personReqForm.getMobile());
        logger.info("smCode:" + personReqForm.getSmCode());
        if (StringUtils.isBlank(personReqForm.getOtherLoanAmt())) {
            map.put("msg", "其他网络借贷平台借款总额缺失");
            map.put("code", "0001");
            return map;
            //throw new BusinessException(Errors.SYSTEM_REQUEST_PARAM_ERROR, "其他网络借贷平台借款总额缺失");
        }
        if (StringUtils.isBlank(personReqForm.getMobile())) {
            map.put("msg", "手机号码缺失");
            map.put("code", "0001");
            return map;
            // throw new BusinessException(Errors.SYSTEM_REQUEST_PARAM_ERROR, "手机号码缺失");
        }
        if (StringUtils.isBlank(personReqForm.getSmCode())) {
            map.put("msg", "短信验证码缺失");
            map.put("code", "0001");
            return map;
            // throw new BusinessException(Errors.SYSTEM_REQUEST_PARAM_ERROR, "短信验证码缺失");
        }
        if (StringUtils.isNotBlank(personReqForm.getSmCode())) {
            ProcurationSignatureReqForm procurationSignatureReqForm = new ProcurationSignatureReqForm();
            procurationSignatureReqForm.setMobile(personReqForm.getMobile());
            procurationSignatureReqForm.setSmCode(personReqForm.getSmCode());
            try {
                this.validateSmCode(procurationSignatureReqForm);
            } catch (Exception e) {
                map.put("msg", e.getMessage());
                map.put("code", "0001");
                return map;
            }
        }
        map.put("msg", "表单验证通过！");
        map.put("code", "0");
        return map;
    }

    @Override
    public Map<String, Object> companyFormValidate(String otherLoanAmt, String signatoryName, String signatoryIdentity, String mobile, String smCode, MultipartFile file) {
        Map<String, Object> map = new HashMap<String, Object>();
        logger.info("otherLoanAmt:" + otherLoanAmt);
        logger.info("，signatoryName:" + signatoryName);
        logger.info("，signatoryIdentity:" + signatoryIdentity);
        logger.info("file:" + file);
        logger.info("mobile:" + mobile);
        logger.info("smCode:" + smCode);
        if (StringUtils.isBlank(otherLoanAmt)) {
            map.put("msg", "其他网络借贷平台借款总额缺失");
            map.put("code", "0001");
            return map;
            //throw new BusinessException(Errors.SYSTEM_REQUEST_PARAM_ERROR, "其他网络借贷平台借款总额缺失");
        }
        if (StringUtils.isBlank(signatoryName)) {
            map.put("msg", "签约代表人姓名缺失");
            map.put("code", "0001");
            return map;
            //throw new BusinessException(Errors.SYSTEM_REQUEST_PARAM_ERROR, "签约代表人姓名缺失");
        }
        if (StringUtils.isBlank(signatoryIdentity)) {
            map.put("msg", "签约代表人身份证号码缺失");
            map.put("code", "0001");
            return map;
            //throw new BusinessException(Errors.SYSTEM_REQUEST_PARAM_ERROR, "签约代表人身份证号码缺失");
        }
        if (StringUtils.isBlank(mobile)) {
            map.put("msg", "手机号码缺失");
            map.put("code", "0001");
            return map;
            //throw new BusinessException(Errors.SYSTEM_REQUEST_PARAM_ERROR, "手机号码缺失");
        }
        if (StringUtils.isBlank(smCode)) {
            map.put("msg", "短信验证码缺失");
            map.put("code", "0001");
            return map;
            //throw new BusinessException(Errors.SYSTEM_REQUEST_PARAM_ERROR, "短信验证码缺失");
        }
        if (StringUtils.isNotBlank(smCode)) {
            ProcurationSignatureReqForm procurationSignatureReqForm = new ProcurationSignatureReqForm();
            procurationSignatureReqForm.setMobile(mobile);
            procurationSignatureReqForm.setSmCode(smCode);
            try {
                this.validateSmCode(procurationSignatureReqForm);
            } catch (Exception e) {
                map.put("msg", e.getMessage());
                map.put("code", "0001");
                return map;
            }
        }
        if (file.isEmpty()) {
            map.put("msg", "企业电子签名委托书缺失");
            map.put("code", "0001");
            //throw new BusinessException(Errors.SYSTEM_REQUEST_PARAM_ERROR, "企业电子签名委托书缺失");
            return map;
        }
        map.put("msg", "表单验证通过！");
        map.put("code", "0");
        return map;
    }

    //验证手机短信验证码
    private void validateSmCode(ProcurationSignatureReqForm procurationSignatureReqForm) {
        logger.info("进入短信验证码验证方法。。。");
        //验证手机短信验证码
        if (procurationSignatureReqForm.getSmCode() == null) {
            new BusinessException(Errors.VALIDECODE_ISNULL_ERROR);
        }
        RBucket rBucket = redissonClient.getBucket(
                Constant.VALID_MSG + ":" + MsgType.BIND_MOBILE.toString() + ":" + procurationSignatureReqForm.getMobile());
        if (!rBucket.isExists()) {
            throw new BusinessException(Errors.VALIDECODE_EXPIRE_ERROR);
        }
        MsgRedisEntity mr = (MsgRedisEntity) rBucket.get();
        String rcode = mr.getParam()[0];
        logger.info("redis中保存的验证码：" + rcode);
        if (!rcode.equals(procurationSignatureReqForm.getSmCode())) {
            throw new BusinessException(Errors.VALIDATE_INPUT_ERROR);
        }
    }
}
