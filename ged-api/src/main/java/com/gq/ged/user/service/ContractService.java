package com.gq.ged.user.service;

import com.gq.ged.user.api.req.LoanGuaranteeProtocolReqForm;
import com.gq.ged.user.api.req.PersonReqForm;
import com.gq.ged.user.api.req.ProcurationSignatureReqForm;
import com.gq.ged.user.api.res.*;
import com.gq.ged.user.api.res.CompanyInfoProtocolResForm;
import com.gq.ged.user.api.res.LoanGuaranteeProtocolResForm;
import com.gq.ged.user.api.res.PersonalInfoProtocolResForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by yxh on 2018/5/30.
 */
public interface ContractService {

    /**
     * 获取授权提现函信息
     * @return
     */
    WithdrawalLetter getWithdrawalLetterInfo(Long id) throws Exception;


    /**
     * 获取 借款服务协议变量 信息
     * @return
     */
    LoanServiceAgreement getLoanServiceAgreementInfo(Long id) throws Exception;


    /**
     *
     * 获取  电子签名数字证书用户申请确认函s 信息
     * @return
     */
    ElectronicLetter getElectronicLetterInfo(Long id) throws Exception;
    /**
     * 获取个人信息采集协议数据
     * @param userId 用户主键
     * @return 协议数据
     */
    PersonalInfoProtocolResForm getPersonalInfoCollectionProtocol(Long userId) throws Exception;

    /**
     * 获取企业信息采集协议数据
     * @param userId 用户主键
     * @return 协议数据
     */
    CompanyInfoProtocolResForm getCompanyInfoCollectionProtocol(Long userId) throws Exception;

    /**
     * 获取网络借贷平台借款情况披露及承诺书
     * @param userId 用户主键
     * @return 协议数据
     */
    LoanGuaranteeProtocolResForm getLoanGuaranteeProtocol(Long userId) throws Exception;

    /**
     * 获取网络借贷平台借款情况披露及承诺书
     * @param userId 用户主键
     * @param reqForm 请求参数
     * @return 协议数据
     */
    LoanGuaranteeProtocolResForm getLoanGuaranteeProtocol(Long userId, LoanGuaranteeProtocolReqForm reqForm) throws Exception;

    /**
     * 公司立即签署
     * @param id
     * @param procurationSignatureReqForm
     * @return
     * @throws Exception
     */
    Map<String,Object> companyImmediatelySigned(Long id,ProcurationSignatureReqForm procurationSignatureReqForm) throws Exception;

    /**
     * 个人立即签署
     * @param id
     * @param procurationSignatureReqForm
     * @return
     * @throws Exception
     */
    Map<String,Object> personalImmediatelySigned(Long id, ProcurationSignatureReqForm procurationSignatureReqForm) throws Exception;

    /**
     * 获取担保函
     * @param orderNo 订单编号
     * @param userId 用户主键
     * @return 协议数据
     */
    GuarantorLetterResForm getGuaranteeLetter(String orderNo, Long userId) throws Exception;


    Map<String,Object> personFormValidate(PersonReqForm personReqForm);

    Map<String,Object> companyFormValidate(String otherLoanAmt, String signatoryName, String signatoryIdentity, String mobile, String smCode,MultipartFile file);
}
