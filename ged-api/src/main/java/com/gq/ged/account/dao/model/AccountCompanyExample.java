package com.gq.ged.account.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountCompanyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AccountCompanyExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andCustIdIsNull() {
            addCriterion("cust_id is null");
            return (Criteria) this;
        }

        public Criteria andCustIdIsNotNull() {
            addCriterion("cust_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustIdEqualTo(String value) {
            addCriterion("cust_id =", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotEqualTo(String value) {
            addCriterion("cust_id <>", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdGreaterThan(String value) {
            addCriterion("cust_id >", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdGreaterThanOrEqualTo(String value) {
            addCriterion("cust_id >=", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdLessThan(String value) {
            addCriterion("cust_id <", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdLessThanOrEqualTo(String value) {
            addCriterion("cust_id <=", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdLike(String value) {
            addCriterion("cust_id like", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotLike(String value) {
            addCriterion("cust_id not like", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdIn(List<String> values) {
            addCriterion("cust_id in", values, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotIn(List<String> values) {
            addCriterion("cust_id not in", values, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdBetween(String value1, String value2) {
            addCriterion("cust_id between", value1, value2, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotBetween(String value1, String value2) {
            addCriterion("cust_id not between", value1, value2, "custId");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIsNull() {
            addCriterion("company_name is null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIsNotNull() {
            addCriterion("company_name is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameEqualTo(String value) {
            addCriterion("company_name =", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotEqualTo(String value) {
            addCriterion("company_name <>", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThan(String value) {
            addCriterion("company_name >", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThanOrEqualTo(String value) {
            addCriterion("company_name >=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThan(String value) {
            addCriterion("company_name <", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThanOrEqualTo(String value) {
            addCriterion("company_name <=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLike(String value) {
            addCriterion("company_name like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotLike(String value) {
            addCriterion("company_name not like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIn(List<String> values) {
            addCriterion("company_name in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotIn(List<String> values) {
            addCriterion("company_name not in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameBetween(String value1, String value2) {
            addCriterion("company_name between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotBetween(String value1, String value2) {
            addCriterion("company_name not between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyCardTypeIsNull() {
            addCriterion("company_card_type is null");
            return (Criteria) this;
        }

        public Criteria andCompanyCardTypeIsNotNull() {
            addCriterion("company_card_type is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyCardTypeEqualTo(String value) {
            addCriterion("company_card_type =", value, "companyCardType");
            return (Criteria) this;
        }

        public Criteria andCompanyCardTypeNotEqualTo(String value) {
            addCriterion("company_card_type <>", value, "companyCardType");
            return (Criteria) this;
        }

        public Criteria andCompanyCardTypeGreaterThan(String value) {
            addCriterion("company_card_type >", value, "companyCardType");
            return (Criteria) this;
        }

        public Criteria andCompanyCardTypeGreaterThanOrEqualTo(String value) {
            addCriterion("company_card_type >=", value, "companyCardType");
            return (Criteria) this;
        }

        public Criteria andCompanyCardTypeLessThan(String value) {
            addCriterion("company_card_type <", value, "companyCardType");
            return (Criteria) this;
        }

        public Criteria andCompanyCardTypeLessThanOrEqualTo(String value) {
            addCriterion("company_card_type <=", value, "companyCardType");
            return (Criteria) this;
        }

        public Criteria andCompanyCardTypeLike(String value) {
            addCriterion("company_card_type like", value, "companyCardType");
            return (Criteria) this;
        }

        public Criteria andCompanyCardTypeNotLike(String value) {
            addCriterion("company_card_type not like", value, "companyCardType");
            return (Criteria) this;
        }

        public Criteria andCompanyCardTypeIn(List<String> values) {
            addCriterion("company_card_type in", values, "companyCardType");
            return (Criteria) this;
        }

        public Criteria andCompanyCardTypeNotIn(List<String> values) {
            addCriterion("company_card_type not in", values, "companyCardType");
            return (Criteria) this;
        }

        public Criteria andCompanyCardTypeBetween(String value1, String value2) {
            addCriterion("company_card_type between", value1, value2, "companyCardType");
            return (Criteria) this;
        }

        public Criteria andCompanyCardTypeNotBetween(String value1, String value2) {
            addCriterion("company_card_type not between", value1, value2, "companyCardType");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeIsNull() {
            addCriterion("social_credit_code is null");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeIsNotNull() {
            addCriterion("social_credit_code is not null");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeEqualTo(String value) {
            addCriterion("social_credit_code =", value, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeNotEqualTo(String value) {
            addCriterion("social_credit_code <>", value, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeGreaterThan(String value) {
            addCriterion("social_credit_code >", value, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeGreaterThanOrEqualTo(String value) {
            addCriterion("social_credit_code >=", value, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeLessThan(String value) {
            addCriterion("social_credit_code <", value, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeLessThanOrEqualTo(String value) {
            addCriterion("social_credit_code <=", value, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeLike(String value) {
            addCriterion("social_credit_code like", value, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeNotLike(String value) {
            addCriterion("social_credit_code not like", value, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeIn(List<String> values) {
            addCriterion("social_credit_code in", values, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeNotIn(List<String> values) {
            addCriterion("social_credit_code not in", values, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeBetween(String value1, String value2) {
            addCriterion("social_credit_code between", value1, value2, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeNotBetween(String value1, String value2) {
            addCriterion("social_credit_code not between", value1, value2, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceIsNull() {
            addCriterion("business_licence is null");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceIsNotNull() {
            addCriterion("business_licence is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceEqualTo(String value) {
            addCriterion("business_licence =", value, "businessLicence");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceNotEqualTo(String value) {
            addCriterion("business_licence <>", value, "businessLicence");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceGreaterThan(String value) {
            addCriterion("business_licence >", value, "businessLicence");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceGreaterThanOrEqualTo(String value) {
            addCriterion("business_licence >=", value, "businessLicence");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceLessThan(String value) {
            addCriterion("business_licence <", value, "businessLicence");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceLessThanOrEqualTo(String value) {
            addCriterion("business_licence <=", value, "businessLicence");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceLike(String value) {
            addCriterion("business_licence like", value, "businessLicence");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceNotLike(String value) {
            addCriterion("business_licence not like", value, "businessLicence");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceIn(List<String> values) {
            addCriterion("business_licence in", values, "businessLicence");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceNotIn(List<String> values) {
            addCriterion("business_licence not in", values, "businessLicence");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceBetween(String value1, String value2) {
            addCriterion("business_licence between", value1, value2, "businessLicence");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceNotBetween(String value1, String value2) {
            addCriterion("business_licence not between", value1, value2, "businessLicence");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeIsNull() {
            addCriterion("organization_code is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeIsNotNull() {
            addCriterion("organization_code is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeEqualTo(String value) {
            addCriterion("organization_code =", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeNotEqualTo(String value) {
            addCriterion("organization_code <>", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeGreaterThan(String value) {
            addCriterion("organization_code >", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeGreaterThanOrEqualTo(String value) {
            addCriterion("organization_code >=", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeLessThan(String value) {
            addCriterion("organization_code <", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeLessThanOrEqualTo(String value) {
            addCriterion("organization_code <=", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeLike(String value) {
            addCriterion("organization_code like", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeNotLike(String value) {
            addCriterion("organization_code not like", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeIn(List<String> values) {
            addCriterion("organization_code in", values, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeNotIn(List<String> values) {
            addCriterion("organization_code not in", values, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeBetween(String value1, String value2) {
            addCriterion("organization_code between", value1, value2, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeNotBetween(String value1, String value2) {
            addCriterion("organization_code not between", value1, value2, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andTaxCodeIsNull() {
            addCriterion("tax_code is null");
            return (Criteria) this;
        }

        public Criteria andTaxCodeIsNotNull() {
            addCriterion("tax_code is not null");
            return (Criteria) this;
        }

        public Criteria andTaxCodeEqualTo(String value) {
            addCriterion("tax_code =", value, "taxCode");
            return (Criteria) this;
        }

        public Criteria andTaxCodeNotEqualTo(String value) {
            addCriterion("tax_code <>", value, "taxCode");
            return (Criteria) this;
        }

        public Criteria andTaxCodeGreaterThan(String value) {
            addCriterion("tax_code >", value, "taxCode");
            return (Criteria) this;
        }

        public Criteria andTaxCodeGreaterThanOrEqualTo(String value) {
            addCriterion("tax_code >=", value, "taxCode");
            return (Criteria) this;
        }

        public Criteria andTaxCodeLessThan(String value) {
            addCriterion("tax_code <", value, "taxCode");
            return (Criteria) this;
        }

        public Criteria andTaxCodeLessThanOrEqualTo(String value) {
            addCriterion("tax_code <=", value, "taxCode");
            return (Criteria) this;
        }

        public Criteria andTaxCodeLike(String value) {
            addCriterion("tax_code like", value, "taxCode");
            return (Criteria) this;
        }

        public Criteria andTaxCodeNotLike(String value) {
            addCriterion("tax_code not like", value, "taxCode");
            return (Criteria) this;
        }

        public Criteria andTaxCodeIn(List<String> values) {
            addCriterion("tax_code in", values, "taxCode");
            return (Criteria) this;
        }

        public Criteria andTaxCodeNotIn(List<String> values) {
            addCriterion("tax_code not in", values, "taxCode");
            return (Criteria) this;
        }

        public Criteria andTaxCodeBetween(String value1, String value2) {
            addCriterion("tax_code between", value1, value2, "taxCode");
            return (Criteria) this;
        }

        public Criteria andTaxCodeNotBetween(String value1, String value2) {
            addCriterion("tax_code not between", value1, value2, "taxCode");
            return (Criteria) this;
        }

        public Criteria andLegalNameIsNull() {
            addCriterion("legal_name is null");
            return (Criteria) this;
        }

        public Criteria andLegalNameIsNotNull() {
            addCriterion("legal_name is not null");
            return (Criteria) this;
        }

        public Criteria andLegalNameEqualTo(String value) {
            addCriterion("legal_name =", value, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameNotEqualTo(String value) {
            addCriterion("legal_name <>", value, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameGreaterThan(String value) {
            addCriterion("legal_name >", value, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameGreaterThanOrEqualTo(String value) {
            addCriterion("legal_name >=", value, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameLessThan(String value) {
            addCriterion("legal_name <", value, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameLessThanOrEqualTo(String value) {
            addCriterion("legal_name <=", value, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameLike(String value) {
            addCriterion("legal_name like", value, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameNotLike(String value) {
            addCriterion("legal_name not like", value, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameIn(List<String> values) {
            addCriterion("legal_name in", values, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameNotIn(List<String> values) {
            addCriterion("legal_name not in", values, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameBetween(String value1, String value2) {
            addCriterion("legal_name between", value1, value2, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameNotBetween(String value1, String value2) {
            addCriterion("legal_name not between", value1, value2, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalMobileIsNull() {
            addCriterion("legal_mobile is null");
            return (Criteria) this;
        }

        public Criteria andLegalMobileIsNotNull() {
            addCriterion("legal_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andLegalMobileEqualTo(String value) {
            addCriterion("legal_mobile =", value, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileNotEqualTo(String value) {
            addCriterion("legal_mobile <>", value, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileGreaterThan(String value) {
            addCriterion("legal_mobile >", value, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileGreaterThanOrEqualTo(String value) {
            addCriterion("legal_mobile >=", value, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileLessThan(String value) {
            addCriterion("legal_mobile <", value, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileLessThanOrEqualTo(String value) {
            addCriterion("legal_mobile <=", value, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileLike(String value) {
            addCriterion("legal_mobile like", value, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileNotLike(String value) {
            addCriterion("legal_mobile not like", value, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileIn(List<String> values) {
            addCriterion("legal_mobile in", values, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileNotIn(List<String> values) {
            addCriterion("legal_mobile not in", values, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileBetween(String value1, String value2) {
            addCriterion("legal_mobile between", value1, value2, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileNotBetween(String value1, String value2) {
            addCriterion("legal_mobile not between", value1, value2, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalCardTypeIsNull() {
            addCriterion("legal_card_type is null");
            return (Criteria) this;
        }

        public Criteria andLegalCardTypeIsNotNull() {
            addCriterion("legal_card_type is not null");
            return (Criteria) this;
        }

        public Criteria andLegalCardTypeEqualTo(Integer value) {
            addCriterion("legal_card_type =", value, "legalCardType");
            return (Criteria) this;
        }

        public Criteria andLegalCardTypeNotEqualTo(Integer value) {
            addCriterion("legal_card_type <>", value, "legalCardType");
            return (Criteria) this;
        }

        public Criteria andLegalCardTypeGreaterThan(Integer value) {
            addCriterion("legal_card_type >", value, "legalCardType");
            return (Criteria) this;
        }

        public Criteria andLegalCardTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("legal_card_type >=", value, "legalCardType");
            return (Criteria) this;
        }

        public Criteria andLegalCardTypeLessThan(Integer value) {
            addCriterion("legal_card_type <", value, "legalCardType");
            return (Criteria) this;
        }

        public Criteria andLegalCardTypeLessThanOrEqualTo(Integer value) {
            addCriterion("legal_card_type <=", value, "legalCardType");
            return (Criteria) this;
        }

        public Criteria andLegalCardTypeIn(List<Integer> values) {
            addCriterion("legal_card_type in", values, "legalCardType");
            return (Criteria) this;
        }

        public Criteria andLegalCardTypeNotIn(List<Integer> values) {
            addCriterion("legal_card_type not in", values, "legalCardType");
            return (Criteria) this;
        }

        public Criteria andLegalCardTypeBetween(Integer value1, Integer value2) {
            addCriterion("legal_card_type between", value1, value2, "legalCardType");
            return (Criteria) this;
        }

        public Criteria andLegalCardTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("legal_card_type not between", value1, value2, "legalCardType");
            return (Criteria) this;
        }

        public Criteria andLegalCardNumberIsNull() {
            addCriterion("legal_card_number is null");
            return (Criteria) this;
        }

        public Criteria andLegalCardNumberIsNotNull() {
            addCriterion("legal_card_number is not null");
            return (Criteria) this;
        }

        public Criteria andLegalCardNumberEqualTo(String value) {
            addCriterion("legal_card_number =", value, "legalCardNumber");
            return (Criteria) this;
        }

        public Criteria andLegalCardNumberNotEqualTo(String value) {
            addCriterion("legal_card_number <>", value, "legalCardNumber");
            return (Criteria) this;
        }

        public Criteria andLegalCardNumberGreaterThan(String value) {
            addCriterion("legal_card_number >", value, "legalCardNumber");
            return (Criteria) this;
        }

        public Criteria andLegalCardNumberGreaterThanOrEqualTo(String value) {
            addCriterion("legal_card_number >=", value, "legalCardNumber");
            return (Criteria) this;
        }

        public Criteria andLegalCardNumberLessThan(String value) {
            addCriterion("legal_card_number <", value, "legalCardNumber");
            return (Criteria) this;
        }

        public Criteria andLegalCardNumberLessThanOrEqualTo(String value) {
            addCriterion("legal_card_number <=", value, "legalCardNumber");
            return (Criteria) this;
        }

        public Criteria andLegalCardNumberLike(String value) {
            addCriterion("legal_card_number like", value, "legalCardNumber");
            return (Criteria) this;
        }

        public Criteria andLegalCardNumberNotLike(String value) {
            addCriterion("legal_card_number not like", value, "legalCardNumber");
            return (Criteria) this;
        }

        public Criteria andLegalCardNumberIn(List<String> values) {
            addCriterion("legal_card_number in", values, "legalCardNumber");
            return (Criteria) this;
        }

        public Criteria andLegalCardNumberNotIn(List<String> values) {
            addCriterion("legal_card_number not in", values, "legalCardNumber");
            return (Criteria) this;
        }

        public Criteria andLegalCardNumberBetween(String value1, String value2) {
            addCriterion("legal_card_number between", value1, value2, "legalCardNumber");
            return (Criteria) this;
        }

        public Criteria andLegalCardNumberNotBetween(String value1, String value2) {
            addCriterion("legal_card_number not between", value1, value2, "legalCardNumber");
            return (Criteria) this;
        }

        public Criteria andCompanyContactIsNull() {
            addCriterion("company_contact is null");
            return (Criteria) this;
        }

        public Criteria andCompanyContactIsNotNull() {
            addCriterion("company_contact is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyContactEqualTo(String value) {
            addCriterion("company_contact =", value, "companyContact");
            return (Criteria) this;
        }

        public Criteria andCompanyContactNotEqualTo(String value) {
            addCriterion("company_contact <>", value, "companyContact");
            return (Criteria) this;
        }

        public Criteria andCompanyContactGreaterThan(String value) {
            addCriterion("company_contact >", value, "companyContact");
            return (Criteria) this;
        }

        public Criteria andCompanyContactGreaterThanOrEqualTo(String value) {
            addCriterion("company_contact >=", value, "companyContact");
            return (Criteria) this;
        }

        public Criteria andCompanyContactLessThan(String value) {
            addCriterion("company_contact <", value, "companyContact");
            return (Criteria) this;
        }

        public Criteria andCompanyContactLessThanOrEqualTo(String value) {
            addCriterion("company_contact <=", value, "companyContact");
            return (Criteria) this;
        }

        public Criteria andCompanyContactLike(String value) {
            addCriterion("company_contact like", value, "companyContact");
            return (Criteria) this;
        }

        public Criteria andCompanyContactNotLike(String value) {
            addCriterion("company_contact not like", value, "companyContact");
            return (Criteria) this;
        }

        public Criteria andCompanyContactIn(List<String> values) {
            addCriterion("company_contact in", values, "companyContact");
            return (Criteria) this;
        }

        public Criteria andCompanyContactNotIn(List<String> values) {
            addCriterion("company_contact not in", values, "companyContact");
            return (Criteria) this;
        }

        public Criteria andCompanyContactBetween(String value1, String value2) {
            addCriterion("company_contact between", value1, value2, "companyContact");
            return (Criteria) this;
        }

        public Criteria andCompanyContactNotBetween(String value1, String value2) {
            addCriterion("company_contact not between", value1, value2, "companyContact");
            return (Criteria) this;
        }

        public Criteria andContactMobileIsNull() {
            addCriterion("contact_mobile is null");
            return (Criteria) this;
        }

        public Criteria andContactMobileIsNotNull() {
            addCriterion("contact_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andContactMobileEqualTo(String value) {
            addCriterion("contact_mobile =", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileNotEqualTo(String value) {
            addCriterion("contact_mobile <>", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileGreaterThan(String value) {
            addCriterion("contact_mobile >", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileGreaterThanOrEqualTo(String value) {
            addCriterion("contact_mobile >=", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileLessThan(String value) {
            addCriterion("contact_mobile <", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileLessThanOrEqualTo(String value) {
            addCriterion("contact_mobile <=", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileLike(String value) {
            addCriterion("contact_mobile like", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileNotLike(String value) {
            addCriterion("contact_mobile not like", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileIn(List<String> values) {
            addCriterion("contact_mobile in", values, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileNotIn(List<String> values) {
            addCriterion("contact_mobile not in", values, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileBetween(String value1, String value2) {
            addCriterion("contact_mobile between", value1, value2, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileNotBetween(String value1, String value2) {
            addCriterion("contact_mobile not between", value1, value2, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositIsNull() {
            addCriterion("company_bank_of_deposit is null");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositIsNotNull() {
            addCriterion("company_bank_of_deposit is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositEqualTo(String value) {
            addCriterion("company_bank_of_deposit =", value, "companyBankOfDeposit");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositNotEqualTo(String value) {
            addCriterion("company_bank_of_deposit <>", value, "companyBankOfDeposit");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositGreaterThan(String value) {
            addCriterion("company_bank_of_deposit >", value, "companyBankOfDeposit");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositGreaterThanOrEqualTo(String value) {
            addCriterion("company_bank_of_deposit >=", value, "companyBankOfDeposit");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositLessThan(String value) {
            addCriterion("company_bank_of_deposit <", value, "companyBankOfDeposit");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositLessThanOrEqualTo(String value) {
            addCriterion("company_bank_of_deposit <=", value, "companyBankOfDeposit");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositLike(String value) {
            addCriterion("company_bank_of_deposit like", value, "companyBankOfDeposit");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositNotLike(String value) {
            addCriterion("company_bank_of_deposit not like", value, "companyBankOfDeposit");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositIn(List<String> values) {
            addCriterion("company_bank_of_deposit in", values, "companyBankOfDeposit");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositNotIn(List<String> values) {
            addCriterion("company_bank_of_deposit not in", values, "companyBankOfDeposit");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositBetween(String value1, String value2) {
            addCriterion("company_bank_of_deposit between", value1, value2, "companyBankOfDeposit");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositNotBetween(String value1, String value2) {
            addCriterion("company_bank_of_deposit not between", value1, value2, "companyBankOfDeposit");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositValueIsNull() {
            addCriterion("company_bank_of_deposit_value is null");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositValueIsNotNull() {
            addCriterion("company_bank_of_deposit_value is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositValueEqualTo(String value) {
            addCriterion("company_bank_of_deposit_value =", value, "companyBankOfDepositValue");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositValueNotEqualTo(String value) {
            addCriterion("company_bank_of_deposit_value <>", value, "companyBankOfDepositValue");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositValueGreaterThan(String value) {
            addCriterion("company_bank_of_deposit_value >", value, "companyBankOfDepositValue");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositValueGreaterThanOrEqualTo(String value) {
            addCriterion("company_bank_of_deposit_value >=", value, "companyBankOfDepositValue");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositValueLessThan(String value) {
            addCriterion("company_bank_of_deposit_value <", value, "companyBankOfDepositValue");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositValueLessThanOrEqualTo(String value) {
            addCriterion("company_bank_of_deposit_value <=", value, "companyBankOfDepositValue");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositValueLike(String value) {
            addCriterion("company_bank_of_deposit_value like", value, "companyBankOfDepositValue");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositValueNotLike(String value) {
            addCriterion("company_bank_of_deposit_value not like", value, "companyBankOfDepositValue");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositValueIn(List<String> values) {
            addCriterion("company_bank_of_deposit_value in", values, "companyBankOfDepositValue");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositValueNotIn(List<String> values) {
            addCriterion("company_bank_of_deposit_value not in", values, "companyBankOfDepositValue");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositValueBetween(String value1, String value2) {
            addCriterion("company_bank_of_deposit_value between", value1, value2, "companyBankOfDepositValue");
            return (Criteria) this;
        }

        public Criteria andCompanyBankOfDepositValueNotBetween(String value1, String value2) {
            addCriterion("company_bank_of_deposit_value not between", value1, value2, "companyBankOfDepositValue");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountIsNull() {
            addCriterion("company_account is null");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountIsNotNull() {
            addCriterion("company_account is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountEqualTo(String value) {
            addCriterion("company_account =", value, "companyAccount");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountNotEqualTo(String value) {
            addCriterion("company_account <>", value, "companyAccount");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountGreaterThan(String value) {
            addCriterion("company_account >", value, "companyAccount");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountGreaterThanOrEqualTo(String value) {
            addCriterion("company_account >=", value, "companyAccount");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountLessThan(String value) {
            addCriterion("company_account <", value, "companyAccount");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountLessThanOrEqualTo(String value) {
            addCriterion("company_account <=", value, "companyAccount");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountLike(String value) {
            addCriterion("company_account like", value, "companyAccount");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountNotLike(String value) {
            addCriterion("company_account not like", value, "companyAccount");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountIn(List<String> values) {
            addCriterion("company_account in", values, "companyAccount");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountNotIn(List<String> values) {
            addCriterion("company_account not in", values, "companyAccount");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountBetween(String value1, String value2) {
            addCriterion("company_account between", value1, value2, "companyAccount");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountNotBetween(String value1, String value2) {
            addCriterion("company_account not between", value1, value2, "companyAccount");
            return (Criteria) this;
        }

        public Criteria andCompanyBankBranchNameIsNull() {
            addCriterion("company_bank_branch_name is null");
            return (Criteria) this;
        }

        public Criteria andCompanyBankBranchNameIsNotNull() {
            addCriterion("company_bank_branch_name is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyBankBranchNameEqualTo(String value) {
            addCriterion("company_bank_branch_name =", value, "companyBankBranchName");
            return (Criteria) this;
        }

        public Criteria andCompanyBankBranchNameNotEqualTo(String value) {
            addCriterion("company_bank_branch_name <>", value, "companyBankBranchName");
            return (Criteria) this;
        }

        public Criteria andCompanyBankBranchNameGreaterThan(String value) {
            addCriterion("company_bank_branch_name >", value, "companyBankBranchName");
            return (Criteria) this;
        }

        public Criteria andCompanyBankBranchNameGreaterThanOrEqualTo(String value) {
            addCriterion("company_bank_branch_name >=", value, "companyBankBranchName");
            return (Criteria) this;
        }

        public Criteria andCompanyBankBranchNameLessThan(String value) {
            addCriterion("company_bank_branch_name <", value, "companyBankBranchName");
            return (Criteria) this;
        }

        public Criteria andCompanyBankBranchNameLessThanOrEqualTo(String value) {
            addCriterion("company_bank_branch_name <=", value, "companyBankBranchName");
            return (Criteria) this;
        }

        public Criteria andCompanyBankBranchNameLike(String value) {
            addCriterion("company_bank_branch_name like", value, "companyBankBranchName");
            return (Criteria) this;
        }

        public Criteria andCompanyBankBranchNameNotLike(String value) {
            addCriterion("company_bank_branch_name not like", value, "companyBankBranchName");
            return (Criteria) this;
        }

        public Criteria andCompanyBankBranchNameIn(List<String> values) {
            addCriterion("company_bank_branch_name in", values, "companyBankBranchName");
            return (Criteria) this;
        }

        public Criteria andCompanyBankBranchNameNotIn(List<String> values) {
            addCriterion("company_bank_branch_name not in", values, "companyBankBranchName");
            return (Criteria) this;
        }

        public Criteria andCompanyBankBranchNameBetween(String value1, String value2) {
            addCriterion("company_bank_branch_name between", value1, value2, "companyBankBranchName");
            return (Criteria) this;
        }

        public Criteria andCompanyBankBranchNameNotBetween(String value1, String value2) {
            addCriterion("company_bank_branch_name not between", value1, value2, "companyBankBranchName");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeIsNull() {
            addCriterion("province_code is null");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeIsNotNull() {
            addCriterion("province_code is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeEqualTo(String value) {
            addCriterion("province_code =", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeNotEqualTo(String value) {
            addCriterion("province_code <>", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeGreaterThan(String value) {
            addCriterion("province_code >", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeGreaterThanOrEqualTo(String value) {
            addCriterion("province_code >=", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeLessThan(String value) {
            addCriterion("province_code <", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeLessThanOrEqualTo(String value) {
            addCriterion("province_code <=", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeLike(String value) {
            addCriterion("province_code like", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeNotLike(String value) {
            addCriterion("province_code not like", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeIn(List<String> values) {
            addCriterion("province_code in", values, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeNotIn(List<String> values) {
            addCriterion("province_code not in", values, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeBetween(String value1, String value2) {
            addCriterion("province_code between", value1, value2, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeNotBetween(String value1, String value2) {
            addCriterion("province_code not between", value1, value2, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeIsNull() {
            addCriterion("city_code is null");
            return (Criteria) this;
        }

        public Criteria andCityCodeIsNotNull() {
            addCriterion("city_code is not null");
            return (Criteria) this;
        }

        public Criteria andCityCodeEqualTo(String value) {
            addCriterion("city_code =", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotEqualTo(String value) {
            addCriterion("city_code <>", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeGreaterThan(String value) {
            addCriterion("city_code >", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeGreaterThanOrEqualTo(String value) {
            addCriterion("city_code >=", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLessThan(String value) {
            addCriterion("city_code <", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLessThanOrEqualTo(String value) {
            addCriterion("city_code <=", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLike(String value) {
            addCriterion("city_code like", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotLike(String value) {
            addCriterion("city_code not like", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeIn(List<String> values) {
            addCriterion("city_code in", values, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotIn(List<String> values) {
            addCriterion("city_code not in", values, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeBetween(String value1, String value2) {
            addCriterion("city_code between", value1, value2, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotBetween(String value1, String value2) {
            addCriterion("city_code not between", value1, value2, "cityCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeIsNull() {
            addCriterion("area_code is null");
            return (Criteria) this;
        }

        public Criteria andAreaCodeIsNotNull() {
            addCriterion("area_code is not null");
            return (Criteria) this;
        }

        public Criteria andAreaCodeEqualTo(String value) {
            addCriterion("area_code =", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotEqualTo(String value) {
            addCriterion("area_code <>", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeGreaterThan(String value) {
            addCriterion("area_code >", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeGreaterThanOrEqualTo(String value) {
            addCriterion("area_code >=", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeLessThan(String value) {
            addCriterion("area_code <", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeLessThanOrEqualTo(String value) {
            addCriterion("area_code <=", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeLike(String value) {
            addCriterion("area_code like", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotLike(String value) {
            addCriterion("area_code not like", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeIn(List<String> values) {
            addCriterion("area_code in", values, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotIn(List<String> values) {
            addCriterion("area_code not in", values, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeBetween(String value1, String value2) {
            addCriterion("area_code between", value1, value2, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotBetween(String value1, String value2) {
            addCriterion("area_code not between", value1, value2, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andIdCardFaceUrlIsNull() {
            addCriterion("id_card_face_url is null");
            return (Criteria) this;
        }

        public Criteria andIdCardFaceUrlIsNotNull() {
            addCriterion("id_card_face_url is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardFaceUrlEqualTo(String value) {
            addCriterion("id_card_face_url =", value, "idCardFaceUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFaceUrlNotEqualTo(String value) {
            addCriterion("id_card_face_url <>", value, "idCardFaceUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFaceUrlGreaterThan(String value) {
            addCriterion("id_card_face_url >", value, "idCardFaceUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFaceUrlGreaterThanOrEqualTo(String value) {
            addCriterion("id_card_face_url >=", value, "idCardFaceUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFaceUrlLessThan(String value) {
            addCriterion("id_card_face_url <", value, "idCardFaceUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFaceUrlLessThanOrEqualTo(String value) {
            addCriterion("id_card_face_url <=", value, "idCardFaceUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFaceUrlLike(String value) {
            addCriterion("id_card_face_url like", value, "idCardFaceUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFaceUrlNotLike(String value) {
            addCriterion("id_card_face_url not like", value, "idCardFaceUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFaceUrlIn(List<String> values) {
            addCriterion("id_card_face_url in", values, "idCardFaceUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFaceUrlNotIn(List<String> values) {
            addCriterion("id_card_face_url not in", values, "idCardFaceUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFaceUrlBetween(String value1, String value2) {
            addCriterion("id_card_face_url between", value1, value2, "idCardFaceUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFaceUrlNotBetween(String value1, String value2) {
            addCriterion("id_card_face_url not between", value1, value2, "idCardFaceUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlIsNull() {
            addCriterion("id_card_back_url is null");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlIsNotNull() {
            addCriterion("id_card_back_url is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlEqualTo(String value) {
            addCriterion("id_card_back_url =", value, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlNotEqualTo(String value) {
            addCriterion("id_card_back_url <>", value, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlGreaterThan(String value) {
            addCriterion("id_card_back_url >", value, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlGreaterThanOrEqualTo(String value) {
            addCriterion("id_card_back_url >=", value, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlLessThan(String value) {
            addCriterion("id_card_back_url <", value, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlLessThanOrEqualTo(String value) {
            addCriterion("id_card_back_url <=", value, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlLike(String value) {
            addCriterion("id_card_back_url like", value, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlNotLike(String value) {
            addCriterion("id_card_back_url not like", value, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlIn(List<String> values) {
            addCriterion("id_card_back_url in", values, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlNotIn(List<String> values) {
            addCriterion("id_card_back_url not in", values, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlBetween(String value1, String value2) {
            addCriterion("id_card_back_url between", value1, value2, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlNotBetween(String value1, String value2) {
            addCriterion("id_card_back_url not between", value1, value2, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardHoldUrlIsNull() {
            addCriterion("id_card_hold_url is null");
            return (Criteria) this;
        }

        public Criteria andIdCardHoldUrlIsNotNull() {
            addCriterion("id_card_hold_url is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardHoldUrlEqualTo(String value) {
            addCriterion("id_card_hold_url =", value, "idCardHoldUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardHoldUrlNotEqualTo(String value) {
            addCriterion("id_card_hold_url <>", value, "idCardHoldUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardHoldUrlGreaterThan(String value) {
            addCriterion("id_card_hold_url >", value, "idCardHoldUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardHoldUrlGreaterThanOrEqualTo(String value) {
            addCriterion("id_card_hold_url >=", value, "idCardHoldUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardHoldUrlLessThan(String value) {
            addCriterion("id_card_hold_url <", value, "idCardHoldUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardHoldUrlLessThanOrEqualTo(String value) {
            addCriterion("id_card_hold_url <=", value, "idCardHoldUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardHoldUrlLike(String value) {
            addCriterion("id_card_hold_url like", value, "idCardHoldUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardHoldUrlNotLike(String value) {
            addCriterion("id_card_hold_url not like", value, "idCardHoldUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardHoldUrlIn(List<String> values) {
            addCriterion("id_card_hold_url in", values, "idCardHoldUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardHoldUrlNotIn(List<String> values) {
            addCriterion("id_card_hold_url not in", values, "idCardHoldUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardHoldUrlBetween(String value1, String value2) {
            addCriterion("id_card_hold_url between", value1, value2, "idCardHoldUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardHoldUrlNotBetween(String value1, String value2) {
            addCriterion("id_card_hold_url not between", value1, value2, "idCardHoldUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceUrlIsNull() {
            addCriterion("business_licence_url is null");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceUrlIsNotNull() {
            addCriterion("business_licence_url is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceUrlEqualTo(String value) {
            addCriterion("business_licence_url =", value, "businessLicenceUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceUrlNotEqualTo(String value) {
            addCriterion("business_licence_url <>", value, "businessLicenceUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceUrlGreaterThan(String value) {
            addCriterion("business_licence_url >", value, "businessLicenceUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceUrlGreaterThanOrEqualTo(String value) {
            addCriterion("business_licence_url >=", value, "businessLicenceUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceUrlLessThan(String value) {
            addCriterion("business_licence_url <", value, "businessLicenceUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceUrlLessThanOrEqualTo(String value) {
            addCriterion("business_licence_url <=", value, "businessLicenceUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceUrlLike(String value) {
            addCriterion("business_licence_url like", value, "businessLicenceUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceUrlNotLike(String value) {
            addCriterion("business_licence_url not like", value, "businessLicenceUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceUrlIn(List<String> values) {
            addCriterion("business_licence_url in", values, "businessLicenceUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceUrlNotIn(List<String> values) {
            addCriterion("business_licence_url not in", values, "businessLicenceUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceUrlBetween(String value1, String value2) {
            addCriterion("business_licence_url between", value1, value2, "businessLicenceUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenceUrlNotBetween(String value1, String value2) {
            addCriterion("business_licence_url not between", value1, value2, "businessLicenceUrl");
            return (Criteria) this;
        }

        public Criteria andAccountsPermitsUrlIsNull() {
            addCriterion("accounts_permits_url is null");
            return (Criteria) this;
        }

        public Criteria andAccountsPermitsUrlIsNotNull() {
            addCriterion("accounts_permits_url is not null");
            return (Criteria) this;
        }

        public Criteria andAccountsPermitsUrlEqualTo(String value) {
            addCriterion("accounts_permits_url =", value, "accountsPermitsUrl");
            return (Criteria) this;
        }

        public Criteria andAccountsPermitsUrlNotEqualTo(String value) {
            addCriterion("accounts_permits_url <>", value, "accountsPermitsUrl");
            return (Criteria) this;
        }

        public Criteria andAccountsPermitsUrlGreaterThan(String value) {
            addCriterion("accounts_permits_url >", value, "accountsPermitsUrl");
            return (Criteria) this;
        }

        public Criteria andAccountsPermitsUrlGreaterThanOrEqualTo(String value) {
            addCriterion("accounts_permits_url >=", value, "accountsPermitsUrl");
            return (Criteria) this;
        }

        public Criteria andAccountsPermitsUrlLessThan(String value) {
            addCriterion("accounts_permits_url <", value, "accountsPermitsUrl");
            return (Criteria) this;
        }

        public Criteria andAccountsPermitsUrlLessThanOrEqualTo(String value) {
            addCriterion("accounts_permits_url <=", value, "accountsPermitsUrl");
            return (Criteria) this;
        }

        public Criteria andAccountsPermitsUrlLike(String value) {
            addCriterion("accounts_permits_url like", value, "accountsPermitsUrl");
            return (Criteria) this;
        }

        public Criteria andAccountsPermitsUrlNotLike(String value) {
            addCriterion("accounts_permits_url not like", value, "accountsPermitsUrl");
            return (Criteria) this;
        }

        public Criteria andAccountsPermitsUrlIn(List<String> values) {
            addCriterion("accounts_permits_url in", values, "accountsPermitsUrl");
            return (Criteria) this;
        }

        public Criteria andAccountsPermitsUrlNotIn(List<String> values) {
            addCriterion("accounts_permits_url not in", values, "accountsPermitsUrl");
            return (Criteria) this;
        }

        public Criteria andAccountsPermitsUrlBetween(String value1, String value2) {
            addCriterion("accounts_permits_url between", value1, value2, "accountsPermitsUrl");
            return (Criteria) this;
        }

        public Criteria andAccountsPermitsUrlNotBetween(String value1, String value2) {
            addCriterion("accounts_permits_url not between", value1, value2, "accountsPermitsUrl");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreateIdIsNull() {
            addCriterion("create_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateIdIsNotNull() {
            addCriterion("create_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateIdEqualTo(Long value) {
            addCriterion("create_id =", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotEqualTo(Long value) {
            addCriterion("create_id <>", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdGreaterThan(Long value) {
            addCriterion("create_id >", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdGreaterThanOrEqualTo(Long value) {
            addCriterion("create_id >=", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdLessThan(Long value) {
            addCriterion("create_id <", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdLessThanOrEqualTo(Long value) {
            addCriterion("create_id <=", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdIn(List<Long> values) {
            addCriterion("create_id in", values, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotIn(List<Long> values) {
            addCriterion("create_id not in", values, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdBetween(Long value1, Long value2) {
            addCriterion("create_id between", value1, value2, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotBetween(Long value1, Long value2) {
            addCriterion("create_id not between", value1, value2, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andAccountVerifyInfoIsNull() {
            addCriterion("account_verify_info is null");
            return (Criteria) this;
        }

        public Criteria andAccountVerifyInfoIsNotNull() {
            addCriterion("account_verify_info is not null");
            return (Criteria) this;
        }

        public Criteria andAccountVerifyInfoEqualTo(String value) {
            addCriterion("account_verify_info =", value, "accountVerifyInfo");
            return (Criteria) this;
        }

        public Criteria andAccountVerifyInfoNotEqualTo(String value) {
            addCriterion("account_verify_info <>", value, "accountVerifyInfo");
            return (Criteria) this;
        }

        public Criteria andAccountVerifyInfoGreaterThan(String value) {
            addCriterion("account_verify_info >", value, "accountVerifyInfo");
            return (Criteria) this;
        }

        public Criteria andAccountVerifyInfoGreaterThanOrEqualTo(String value) {
            addCriterion("account_verify_info >=", value, "accountVerifyInfo");
            return (Criteria) this;
        }

        public Criteria andAccountVerifyInfoLessThan(String value) {
            addCriterion("account_verify_info <", value, "accountVerifyInfo");
            return (Criteria) this;
        }

        public Criteria andAccountVerifyInfoLessThanOrEqualTo(String value) {
            addCriterion("account_verify_info <=", value, "accountVerifyInfo");
            return (Criteria) this;
        }

        public Criteria andAccountVerifyInfoLike(String value) {
            addCriterion("account_verify_info like", value, "accountVerifyInfo");
            return (Criteria) this;
        }

        public Criteria andAccountVerifyInfoNotLike(String value) {
            addCriterion("account_verify_info not like", value, "accountVerifyInfo");
            return (Criteria) this;
        }

        public Criteria andAccountVerifyInfoIn(List<String> values) {
            addCriterion("account_verify_info in", values, "accountVerifyInfo");
            return (Criteria) this;
        }

        public Criteria andAccountVerifyInfoNotIn(List<String> values) {
            addCriterion("account_verify_info not in", values, "accountVerifyInfo");
            return (Criteria) this;
        }

        public Criteria andAccountVerifyInfoBetween(String value1, String value2) {
            addCriterion("account_verify_info between", value1, value2, "accountVerifyInfo");
            return (Criteria) this;
        }

        public Criteria andAccountVerifyInfoNotBetween(String value1, String value2) {
            addCriterion("account_verify_info not between", value1, value2, "accountVerifyInfo");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}