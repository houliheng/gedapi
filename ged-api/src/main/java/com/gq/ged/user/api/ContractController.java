package com.gq.ged.user.api;

import com.alibaba.fastjson.JSON;
import com.gq.ged.common.Constant;
import com.gq.ged.common.Errors;
import com.gq.ged.common.enums.UserTypeEnum;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.common.resp.ResponseEntity;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.common.utils.StringUtil;
import com.gq.ged.common.web.BaseController;
import com.gq.ged.user.api.req.*;
import com.gq.ged.user.api.res.ElectronicLetter;
import com.gq.ged.user.api.res.GuarantorLetterResForm;
import com.gq.ged.user.api.res.LoanServiceAgreement;
import com.gq.ged.user.api.res.WithdrawalLetter;
import com.gq.ged.user.dao.model.User;
import com.gq.ged.user.api.res.CompanyInfoProtocolResForm;
import com.gq.ged.user.api.res.LoanGuaranteeProtocolResForm;
import com.gq.ged.user.api.res.PersonalInfoProtocolResForm;
import com.gq.ged.user.service.ContractService;
import com.gq.ged.user.utils.ProtocolUtils;
import com.gqhmt.fs.FileService;
import com.gqhmt.fs.fdfs.FastDfsGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 合同Controller
 *
 * @author yxh by 2018/5/31
 */
@Api(value = "合同协议服务", description = "合同协议服务")
@RestController
@RequestMapping(value = "/service/contract", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContractController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ContractController.class);
    @Resource
    private ContractService contractService;
    @Resource
    private FileService fileService;

    @ApiOperation(value = "个人信息采集协议", notes = "个人信息采集协议")
    @RequestMapping(value = "/getPersonalInfoProtocol", method = RequestMethod.POST)
    public ResponseEntity getPersonalInfoProtocol(HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        if (UserTypeEnum.PERSONAL.equals(UserTypeEnum.valueOf(user.getUserType()))) {
            PersonalInfoProtocolResForm resForm = contractService.getPersonalInfoCollectionProtocol(user.getId());
            return ResponseEntityUtil.success(resForm);
        } else {
            return ResponseEntityUtil.fail(Errors.SYSTEM_NO_ACCESS, "用户类型不符");
        }
    }

    @ApiOperation(value = "企业信息采集协议", notes = "企业信息采集协议")
    @RequestMapping(value = "/getCompanyInfoProtocol", method = RequestMethod.POST)
    public ResponseEntity getCompanyInfoProtocol(HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        if (UserTypeEnum.COMPANY.equals(UserTypeEnum.valueOf(user.getUserType()))) {
            CompanyInfoProtocolResForm resForm = contractService.getCompanyInfoCollectionProtocol(user.getId());
            return ResponseEntityUtil.success(resForm);
        } else {
            return ResponseEntityUtil.fail(Errors.SYSTEM_NO_ACCESS, "用户类型不符");
        }
    }

    @ApiOperation(value = "网络借贷平台借款情况披露及承诺书", notes = "网络借贷平台借款情况披露及承诺书")
    @RequestMapping(value = "/getLoanGuaranteeProtocol", method = RequestMethod.POST)
    public ResponseEntity getLoanGuaranteeProtocol(@RequestBody LoanGuaranteeProtocolReqForm reqForm, HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        ProtocolUtils.validateReqForm(reqForm);
        LoanGuaranteeProtocolResForm resForm = contractService.getLoanGuaranteeProtocol(user.getId(), reqForm);
        return ResponseEntityUtil.success(resForm);
    }

    @ApiOperation(value = "借款服务协议", notes = "借款服务协议")
    @RequestMapping(value = "/loanServiceAgreement", method = RequestMethod.GET)
    public ResponseEntity<?> loanServiceAgreement(HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        LoanServiceAgreement loanServiceAgreementInfo = contractService.getLoanServiceAgreementInfo(user.getId());
        return ResponseEntityUtil.success(loanServiceAgreementInfo);
    }

    @ApiOperation(value = "电子签名数字证书用户申请确认函", notes = "电子签名数字证书用户申请确认函")
    @RequestMapping(value = "/electronicLetter", method = RequestMethod.GET)
    public ResponseEntity<?> electronicLetter(HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        ElectronicLetter electronicLetterInfo = contractService.getElectronicLetterInfo(user.getId());
        return ResponseEntityUtil.success(electronicLetterInfo);
    }

    @ApiOperation(value = "授权提现涵", notes = "授权提现涵")
    @RequestMapping(value = "/withdrawalLetter", method = RequestMethod.GET)
    public ResponseEntity<?> withdrawalLetter(HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        WithdrawalLetter withdrawalLetterInfo = contractService.getWithdrawalLetterInfo(user.getId());
        return ResponseEntityUtil.success(withdrawalLetterInfo);
    }



    /**
     * 企业立即签署
     *
     * @param signatoryName
     * @param signatoryIdentity
     * @param file
     * @param mobile
     * @param smCode
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "企业立即签署合同", notes = "企业立即签署合同")
    @RequestMapping(value = "/companyImmediatelySigned", method = RequestMethod.POST)
    public ResponseEntity<?> companyImmediatelySigned(
            @RequestParam(value = "otherLoanAmt") String otherLoanAmt,
            @RequestParam(value = "signatoryName") String signatoryName,
            @RequestParam(value = "signatoryIdentity") String signatoryIdentity,
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "mobile") String mobile,
            @RequestParam(value = "smCode") String smCode,
            HttpServletRequest request) throws Exception {
        logger.info("===========企业立即签署合同======================》");

        String fileId = null;
        try {
            fileId = fileService.uploadWithGroup(file.getBytes(),
                    FastDfsGroup.PRI_FILE, "jpg");
        } catch (Exception e) {
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "企业电子签名委托书上传失败");
        }
        ProcurationSignatureReqForm procurationSignatureReqForm = new ProcurationSignatureReqForm();
        procurationSignatureReqForm.setFileId(fileId);
        procurationSignatureReqForm.setMobile(mobile);
        procurationSignatureReqForm.setSignatoryIdentity(signatoryIdentity);
        procurationSignatureReqForm.setSignatoryName(signatoryName);
        procurationSignatureReqForm.setOtherLoanAmt(otherLoanAmt);
        procurationSignatureReqForm.setSmCode(smCode);
        logger.info("签署代表参数信息：" + JSON.toJSONString(procurationSignatureReqForm));
        User user = this.getUserInfo(request);
        Map<String, Object> objectMap = contractService.companyImmediatelySigned(user.getId(), procurationSignatureReqForm);
        if (Constant.RES_CODE_SUCCESS.equals(objectMap.get("resCode"))) {
            return ResponseEntityUtil.success(objectMap);
        } else if ("0001".equals(objectMap.get("resCode"))) {
            return ResponseEntityUtil.fail(Errors.REQ_ERROR, objectMap);
        }
        return null;
    }


    /**
     * 企业签署表单验证
     */
    @ApiOperation(value = "企业立即签署表单验证接口", notes = "企业立即签署表单验证接口")
    @RequestMapping(value = "/companyFormValidate", method = RequestMethod.POST)
    public Map<String,Object> companyFormValidate( @RequestParam(value = "otherLoanAmt") String otherLoanAmt,
                                                   @RequestParam(value = "signatoryName") String signatoryName,
                                                   @RequestParam(value = "signatoryIdentity") String signatoryIdentity,
                                                   @RequestParam(value = "file") MultipartFile file,
                                                   @RequestParam(value = "mobile") String mobile,
                                                   @RequestParam(value = "smCode") String smCode){


        return  contractService.companyFormValidate(otherLoanAmt,signatoryName,
                signatoryIdentity,mobile,smCode,file );
    }


    @ApiOperation(value = "获取担保函", notes = "获取担保函")
    @RequestMapping(value = "/getGuaranteeLetter", method = RequestMethod.POST)
    public ResponseEntity getGuaranteeLetter(@RequestBody GuarantorLetterReqForm reqForm, HttpServletRequest request) throws Exception {
        String orderNo = reqForm.getOrderNo();
        if (StringUtils.isBlank(orderNo)) {
            throw new BusinessException(Errors.SYSTEM_DATA_ERROR, "订单编号缺失");
        }
        User user = this.getUserInfo(request);
        GuarantorLetterResForm resForm = contractService.getGuaranteeLetter(orderNo, user.getId());
        return ResponseEntityUtil.success(resForm);
    }

    /**
     * 个人签署表单验证
     */
    @ApiOperation(value = "个人立即签署表单验证接口", notes = "个人立即签署表单验证接口")
    @RequestMapping(value = "/personFormValidate", method = RequestMethod.POST)
    public Map<String,Object> personFormValidate(@RequestBody PersonReqForm personReqForm){
        return  contractService.personFormValidate(personReqForm) ;
    }

    /**
     * 个人立即签署
     *
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "个人立即签署合同", notes = "个人立即签署合同")
    @RequestMapping(value = "/personalImmediatelySigned", method = RequestMethod.POST)
    public ResponseEntity<?> personalImmediatelySigned(@RequestBody PersonReqForm personReqForm,
                                                       HttpServletRequest request) throws Exception {
        logger.info("===========个人立即签署合同======================》");

        ProcurationSignatureReqForm procurationSignatureReqForm = new ProcurationSignatureReqForm();
        procurationSignatureReqForm.setSmCode(personReqForm.getSmCode());
        procurationSignatureReqForm.setMobile(personReqForm.getMobile());
        procurationSignatureReqForm.setOtherLoanAmt(personReqForm.getOtherLoanAmt());
        logger.info("签署代表参数信息：" + JSON.toJSONString(procurationSignatureReqForm));
        User user = this.getUserInfo(request);
        Map<String, Object> objectMap = contractService.personalImmediatelySigned(user.getId(), procurationSignatureReqForm);
        if (Constant.RES_CODE_SUCCESS.equals(objectMap.get("resCode"))) {
            return ResponseEntityUtil.success(objectMap);
        } else if ("0001".equals(objectMap.get("resCode"))) {
            return ResponseEntityUtil.fail(Errors.REQ_ERROR, objectMap);
        }
        return null;
    }
}