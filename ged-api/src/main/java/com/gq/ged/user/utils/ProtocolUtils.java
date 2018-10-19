package com.gq.ged.user.utils;

import com.gq.ged.account.dao.model.Account;
import com.gq.ged.common.Errors;
import com.gq.ged.common.enums.CertificateTypeEnum;
import com.gq.ged.common.enums.UserTypeEnum;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.common.utils.NumberToCN;
import com.gq.ged.common.utils.StringUtil;
import com.gq.ged.order.dao.model.GedOrder;
import com.gq.ged.user.api.req.LoanGuaranteeProtocolReqForm;
import com.gq.ged.user.api.res.CompanyInfoProtocolResForm;
import com.gq.ged.user.api.res.GuarantorLetterResForm;
import com.gq.ged.user.api.res.LoanGuaranteeProtocolResForm;
import com.gq.ged.user.api.res.PersonalInfoProtocolResForm;
import com.gq.ged.user.dao.model.User;
import java.time.LocalDate;
import org.apache.commons.lang.StringUtils;

/**
 * 协议组装工具类.
 *
 * @author SeppSong
 */
public class ProtocolUtils {

    public static PersonalInfoProtocolResForm transPersonalInfoProtocol(User user, Account account) {
        PersonalInfoProtocolResForm resForm = new PersonalInfoProtocolResForm();
        resForm.setName(user.getUsername());
        resForm.setIdentityNo(account.getCorporationCardNum());
        resForm.setRegisterId(account.getCorporationPhone());
        resForm.setAuthorizer(account.getCorporationName());
        resForm.setCertificateType(CertificateTypeEnum.IDENTITY_CARD.getValue());
        resForm.setCertificateNo(account.getCorporationCardNum());
        resForm.setDate(LocalDate.now().toString());
        return resForm;
    }

    public static CompanyInfoProtocolResForm transCompanyInfoProtocol(User user) {
        CompanyInfoProtocolResForm resForm = new CompanyInfoProtocolResForm();
        resForm.setName(user.getLegalName());
        resForm.setIdentityNo(user.getLegalCardNumber());
        resForm.setCompany(user.getCompanyName());
        resForm.setSocialCreditCode(user.getSocialCreditCode());
        resForm.setRegisterId(user.getSocialCreditCode());
        resForm.setAuthorizer(user.getCompanyName());
        resForm.setLegalPerson(user.getLegalName());
        resForm.setCertificateType(CertificateTypeEnum.valueOf(user.getLegalCardType()));
        resForm.setCertificateNo(user.getLegalCardNumber());
        resForm.setDate(LocalDate.now().toString());
        return resForm;
    }

    public static LoanGuaranteeProtocolResForm transLoanGuaranteeProtocol(User user, Account account) {
        LoanGuaranteeProtocolResForm resForm = new LoanGuaranteeProtocolResForm();
        if (UserTypeEnum.COMPANY.equals(UserTypeEnum.valueOf(user.getUserType()))) {
            resForm.setName(user.getLegalName());
        } else {
            resForm.setLegalPerson(user.getCompanyName());
        }
        resForm.setDate(LocalDate.now().toString());
        return resForm;
    }

    public static GuarantorLetterResForm guarantorLetterCommon(GedOrder gedOrder) {
        GuarantorLetterResForm resForm = new GuarantorLetterResForm();
        resForm.setAddress(gedOrder.getManagementAddr());
        resForm.setDate(LocalDate.now().toString());
        resForm.setContractNo(gedOrder.getContractCode());
        resForm.setLoanAmount(gedOrder.getLoanAmount().toString());
        resForm.setLoanAmountUpperCase(NumberToCN.number2CNMontrayUnit(gedOrder.getLoanAmount()));
        return resForm;
    }

    public static void validateReqForm(LoanGuaranteeProtocolReqForm reqForm) {
        if (StringUtils.isBlank(reqForm.getOrderNo())) {
            throw new BusinessException(Errors.SYSTEM_REQUEST_PARAM_ERROR, "订单编号缺失");
        }
        if (StringUtils.isEmpty(reqForm.getOtherPlatformLoanAmount())) {
            throw new BusinessException(Errors.SYSTEM_REQUEST_PARAM_ERROR, "其他借款平台借款金额缺失");
        }
    }
}
