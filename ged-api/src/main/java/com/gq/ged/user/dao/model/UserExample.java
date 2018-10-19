package com.gq.ged.user.dao.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserExample() {
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

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andGetCustIdIsNull() {
            addCriterion("get_cust_id is null");
            return (Criteria) this;
        }

        public Criteria andGetCustIdIsNotNull() {
            addCriterion("get_cust_id is not null");
            return (Criteria) this;
        }

        public Criteria andGetCustIdEqualTo(String value) {
            addCriterion("get_cust_id =", value, "getCustId");
            return (Criteria) this;
        }

        public Criteria andGetCustIdNotEqualTo(String value) {
            addCriterion("get_cust_id <>", value, "getCustId");
            return (Criteria) this;
        }

        public Criteria andGetCustIdGreaterThan(String value) {
            addCriterion("get_cust_id >", value, "getCustId");
            return (Criteria) this;
        }

        public Criteria andGetCustIdGreaterThanOrEqualTo(String value) {
            addCriterion("get_cust_id >=", value, "getCustId");
            return (Criteria) this;
        }

        public Criteria andGetCustIdLessThan(String value) {
            addCriterion("get_cust_id <", value, "getCustId");
            return (Criteria) this;
        }

        public Criteria andGetCustIdLessThanOrEqualTo(String value) {
            addCriterion("get_cust_id <=", value, "getCustId");
            return (Criteria) this;
        }

        public Criteria andGetCustIdLike(String value) {
            addCriterion("get_cust_id like", value, "getCustId");
            return (Criteria) this;
        }

        public Criteria andGetCustIdNotLike(String value) {
            addCriterion("get_cust_id not like", value, "getCustId");
            return (Criteria) this;
        }

        public Criteria andGetCustIdIn(List<String> values) {
            addCriterion("get_cust_id in", values, "getCustId");
            return (Criteria) this;
        }

        public Criteria andGetCustIdNotIn(List<String> values) {
            addCriterion("get_cust_id not in", values, "getCustId");
            return (Criteria) this;
        }

        public Criteria andGetCustIdBetween(String value1, String value2) {
            addCriterion("get_cust_id between", value1, value2, "getCustId");
            return (Criteria) this;
        }

        public Criteria andGetCustIdNotBetween(String value1, String value2) {
            addCriterion("get_cust_id not between", value1, value2, "getCustId");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
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

        public Criteria andCompanyCardCodeIsNull() {
            addCriterion("company_card_code is null");
            return (Criteria) this;
        }

        public Criteria andCompanyCardCodeIsNotNull() {
            addCriterion("company_card_code is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyCardCodeEqualTo(String value) {
            addCriterion("company_card_code =", value, "companyCardCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCardCodeNotEqualTo(String value) {
            addCriterion("company_card_code <>", value, "companyCardCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCardCodeGreaterThan(String value) {
            addCriterion("company_card_code >", value, "companyCardCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCardCodeGreaterThanOrEqualTo(String value) {
            addCriterion("company_card_code >=", value, "companyCardCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCardCodeLessThan(String value) {
            addCriterion("company_card_code <", value, "companyCardCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCardCodeLessThanOrEqualTo(String value) {
            addCriterion("company_card_code <=", value, "companyCardCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCardCodeLike(String value) {
            addCriterion("company_card_code like", value, "companyCardCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCardCodeNotLike(String value) {
            addCriterion("company_card_code not like", value, "companyCardCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCardCodeIn(List<String> values) {
            addCriterion("company_card_code in", values, "companyCardCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCardCodeNotIn(List<String> values) {
            addCriterion("company_card_code not in", values, "companyCardCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCardCodeBetween(String value1, String value2) {
            addCriterion("company_card_code between", value1, value2, "companyCardCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCardCodeNotBetween(String value1, String value2) {
            addCriterion("company_card_code not between", value1, value2, "companyCardCode");
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

        public Criteria andIdCardNumIsNull() {
            addCriterion("id_card_num is null");
            return (Criteria) this;
        }

        public Criteria andIdCardNumIsNotNull() {
            addCriterion("id_card_num is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardNumEqualTo(String value) {
            addCriterion("id_card_num =", value, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumNotEqualTo(String value) {
            addCriterion("id_card_num <>", value, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumGreaterThan(String value) {
            addCriterion("id_card_num >", value, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumGreaterThanOrEqualTo(String value) {
            addCriterion("id_card_num >=", value, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumLessThan(String value) {
            addCriterion("id_card_num <", value, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumLessThanOrEqualTo(String value) {
            addCriterion("id_card_num <=", value, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumLike(String value) {
            addCriterion("id_card_num like", value, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumNotLike(String value) {
            addCriterion("id_card_num not like", value, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumIn(List<String> values) {
            addCriterion("id_card_num in", values, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumNotIn(List<String> values) {
            addCriterion("id_card_num not in", values, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumBetween(String value1, String value2) {
            addCriterion("id_card_num between", value1, value2, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumNotBetween(String value1, String value2) {
            addCriterion("id_card_num not between", value1, value2, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardFlagIsNull() {
            addCriterion("id_card_flag is null");
            return (Criteria) this;
        }

        public Criteria andIdCardFlagIsNotNull() {
            addCriterion("id_card_flag is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardFlagEqualTo(Integer value) {
            addCriterion("id_card_flag =", value, "idCardFlag");
            return (Criteria) this;
        }

        public Criteria andIdCardFlagNotEqualTo(Integer value) {
            addCriterion("id_card_flag <>", value, "idCardFlag");
            return (Criteria) this;
        }

        public Criteria andIdCardFlagGreaterThan(Integer value) {
            addCriterion("id_card_flag >", value, "idCardFlag");
            return (Criteria) this;
        }

        public Criteria andIdCardFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_card_flag >=", value, "idCardFlag");
            return (Criteria) this;
        }

        public Criteria andIdCardFlagLessThan(Integer value) {
            addCriterion("id_card_flag <", value, "idCardFlag");
            return (Criteria) this;
        }

        public Criteria andIdCardFlagLessThanOrEqualTo(Integer value) {
            addCriterion("id_card_flag <=", value, "idCardFlag");
            return (Criteria) this;
        }

        public Criteria andIdCardFlagIn(List<Integer> values) {
            addCriterion("id_card_flag in", values, "idCardFlag");
            return (Criteria) this;
        }

        public Criteria andIdCardFlagNotIn(List<Integer> values) {
            addCriterion("id_card_flag not in", values, "idCardFlag");
            return (Criteria) this;
        }

        public Criteria andIdCardFlagBetween(Integer value1, Integer value2) {
            addCriterion("id_card_flag between", value1, value2, "idCardFlag");
            return (Criteria) this;
        }

        public Criteria andIdCardFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("id_card_flag not between", value1, value2, "idCardFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagIsNull() {
            addCriterion("active_flag is null");
            return (Criteria) this;
        }

        public Criteria andActiveFlagIsNotNull() {
            addCriterion("active_flag is not null");
            return (Criteria) this;
        }

        public Criteria andActiveFlagEqualTo(Integer value) {
            addCriterion("active_flag =", value, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagNotEqualTo(Integer value) {
            addCriterion("active_flag <>", value, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagGreaterThan(Integer value) {
            addCriterion("active_flag >", value, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("active_flag >=", value, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagLessThan(Integer value) {
            addCriterion("active_flag <", value, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagLessThanOrEqualTo(Integer value) {
            addCriterion("active_flag <=", value, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagIn(List<Integer> values) {
            addCriterion("active_flag in", values, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagNotIn(List<Integer> values) {
            addCriterion("active_flag not in", values, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagBetween(Integer value1, Integer value2) {
            addCriterion("active_flag between", value1, value2, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andActiveFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("active_flag not between", value1, value2, "activeFlag");
            return (Criteria) this;
        }

        public Criteria andCheckAccountFlagIsNull() {
            addCriterion("check_account_flag is null");
            return (Criteria) this;
        }

        public Criteria andCheckAccountFlagIsNotNull() {
            addCriterion("check_account_flag is not null");
            return (Criteria) this;
        }

        public Criteria andCheckAccountFlagEqualTo(Integer value) {
            addCriterion("check_account_flag =", value, "checkAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCheckAccountFlagNotEqualTo(Integer value) {
            addCriterion("check_account_flag <>", value, "checkAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCheckAccountFlagGreaterThan(Integer value) {
            addCriterion("check_account_flag >", value, "checkAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCheckAccountFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_account_flag >=", value, "checkAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCheckAccountFlagLessThan(Integer value) {
            addCriterion("check_account_flag <", value, "checkAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCheckAccountFlagLessThanOrEqualTo(Integer value) {
            addCriterion("check_account_flag <=", value, "checkAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCheckAccountFlagIn(List<Integer> values) {
            addCriterion("check_account_flag in", values, "checkAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCheckAccountFlagNotIn(List<Integer> values) {
            addCriterion("check_account_flag not in", values, "checkAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCheckAccountFlagBetween(Integer value1, Integer value2) {
            addCriterion("check_account_flag between", value1, value2, "checkAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCheckAccountFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("check_account_flag not between", value1, value2, "checkAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountFlagIsNull() {
            addCriterion("company_account_flag is null");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountFlagIsNotNull() {
            addCriterion("company_account_flag is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountFlagEqualTo(Integer value) {
            addCriterion("company_account_flag =", value, "companyAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountFlagNotEqualTo(Integer value) {
            addCriterion("company_account_flag <>", value, "companyAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountFlagGreaterThan(Integer value) {
            addCriterion("company_account_flag >", value, "companyAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("company_account_flag >=", value, "companyAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountFlagLessThan(Integer value) {
            addCriterion("company_account_flag <", value, "companyAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountFlagLessThanOrEqualTo(Integer value) {
            addCriterion("company_account_flag <=", value, "companyAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountFlagIn(List<Integer> values) {
            addCriterion("company_account_flag in", values, "companyAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountFlagNotIn(List<Integer> values) {
            addCriterion("company_account_flag not in", values, "companyAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountFlagBetween(Integer value1, Integer value2) {
            addCriterion("company_account_flag between", value1, value2, "companyAccountFlag");
            return (Criteria) this;
        }

        public Criteria andCompanyAccountFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("company_account_flag not between", value1, value2, "companyAccountFlag");
            return (Criteria) this;
        }

        public Criteria andRecommendCodeIsNull() {
            addCriterion("recommend_code is null");
            return (Criteria) this;
        }

        public Criteria andRecommendCodeIsNotNull() {
            addCriterion("recommend_code is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendCodeEqualTo(String value) {
            addCriterion("recommend_code =", value, "recommendCode");
            return (Criteria) this;
        }

        public Criteria andRecommendCodeNotEqualTo(String value) {
            addCriterion("recommend_code <>", value, "recommendCode");
            return (Criteria) this;
        }

        public Criteria andRecommendCodeGreaterThan(String value) {
            addCriterion("recommend_code >", value, "recommendCode");
            return (Criteria) this;
        }

        public Criteria andRecommendCodeGreaterThanOrEqualTo(String value) {
            addCriterion("recommend_code >=", value, "recommendCode");
            return (Criteria) this;
        }

        public Criteria andRecommendCodeLessThan(String value) {
            addCriterion("recommend_code <", value, "recommendCode");
            return (Criteria) this;
        }

        public Criteria andRecommendCodeLessThanOrEqualTo(String value) {
            addCriterion("recommend_code <=", value, "recommendCode");
            return (Criteria) this;
        }

        public Criteria andRecommendCodeLike(String value) {
            addCriterion("recommend_code like", value, "recommendCode");
            return (Criteria) this;
        }

        public Criteria andRecommendCodeNotLike(String value) {
            addCriterion("recommend_code not like", value, "recommendCode");
            return (Criteria) this;
        }

        public Criteria andRecommendCodeIn(List<String> values) {
            addCriterion("recommend_code in", values, "recommendCode");
            return (Criteria) this;
        }

        public Criteria andRecommendCodeNotIn(List<String> values) {
            addCriterion("recommend_code not in", values, "recommendCode");
            return (Criteria) this;
        }

        public Criteria andRecommendCodeBetween(String value1, String value2) {
            addCriterion("recommend_code between", value1, value2, "recommendCode");
            return (Criteria) this;
        }

        public Criteria andRecommendCodeNotBetween(String value1, String value2) {
            addCriterion("recommend_code not between", value1, value2, "recommendCode");
            return (Criteria) this;
        }

        public Criteria andBindEmailFlagIsNull() {
            addCriterion("bind_email_flag is null");
            return (Criteria) this;
        }

        public Criteria andBindEmailFlagIsNotNull() {
            addCriterion("bind_email_flag is not null");
            return (Criteria) this;
        }

        public Criteria andBindEmailFlagEqualTo(Integer value) {
            addCriterion("bind_email_flag =", value, "bindEmailFlag");
            return (Criteria) this;
        }

        public Criteria andBindEmailFlagNotEqualTo(Integer value) {
            addCriterion("bind_email_flag <>", value, "bindEmailFlag");
            return (Criteria) this;
        }

        public Criteria andBindEmailFlagGreaterThan(Integer value) {
            addCriterion("bind_email_flag >", value, "bindEmailFlag");
            return (Criteria) this;
        }

        public Criteria andBindEmailFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("bind_email_flag >=", value, "bindEmailFlag");
            return (Criteria) this;
        }

        public Criteria andBindEmailFlagLessThan(Integer value) {
            addCriterion("bind_email_flag <", value, "bindEmailFlag");
            return (Criteria) this;
        }

        public Criteria andBindEmailFlagLessThanOrEqualTo(Integer value) {
            addCriterion("bind_email_flag <=", value, "bindEmailFlag");
            return (Criteria) this;
        }

        public Criteria andBindEmailFlagIn(List<Integer> values) {
            addCriterion("bind_email_flag in", values, "bindEmailFlag");
            return (Criteria) this;
        }

        public Criteria andBindEmailFlagNotIn(List<Integer> values) {
            addCriterion("bind_email_flag not in", values, "bindEmailFlag");
            return (Criteria) this;
        }

        public Criteria andBindEmailFlagBetween(Integer value1, Integer value2) {
            addCriterion("bind_email_flag between", value1, value2, "bindEmailFlag");
            return (Criteria) this;
        }

        public Criteria andBindEmailFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("bind_email_flag not between", value1, value2, "bindEmailFlag");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNull() {
            addCriterion("user_type is null");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNotNull() {
            addCriterion("user_type is not null");
            return (Criteria) this;
        }

        public Criteria andUserTypeEqualTo(Integer value) {
            addCriterion("user_type =", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotEqualTo(Integer value) {
            addCriterion("user_type <>", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThan(Integer value) {
            addCriterion("user_type >", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_type >=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThan(Integer value) {
            addCriterion("user_type <", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThanOrEqualTo(Integer value) {
            addCriterion("user_type <=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeIn(List<Integer> values) {
            addCriterion("user_type in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotIn(List<Integer> values) {
            addCriterion("user_type not in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeBetween(Integer value1, Integer value2) {
            addCriterion("user_type between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("user_type not between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserRoleIsNull() {
            addCriterion("user_role is null");
            return (Criteria) this;
        }

        public Criteria andUserRoleIsNotNull() {
            addCriterion("user_role is not null");
            return (Criteria) this;
        }

        public Criteria andUserRoleEqualTo(Integer value) {
            addCriterion("user_role =", value, "userRole");
            return (Criteria) this;
        }

        public Criteria andUserRoleNotEqualTo(Integer value) {
            addCriterion("user_role <>", value, "userRole");
            return (Criteria) this;
        }

        public Criteria andUserRoleGreaterThan(Integer value) {
            addCriterion("user_role >", value, "userRole");
            return (Criteria) this;
        }

        public Criteria andUserRoleGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_role >=", value, "userRole");
            return (Criteria) this;
        }

        public Criteria andUserRoleLessThan(Integer value) {
            addCriterion("user_role <", value, "userRole");
            return (Criteria) this;
        }

        public Criteria andUserRoleLessThanOrEqualTo(Integer value) {
            addCriterion("user_role <=", value, "userRole");
            return (Criteria) this;
        }

        public Criteria andUserRoleIn(List<Integer> values) {
            addCriterion("user_role in", values, "userRole");
            return (Criteria) this;
        }

        public Criteria andUserRoleNotIn(List<Integer> values) {
            addCriterion("user_role not in", values, "userRole");
            return (Criteria) this;
        }

        public Criteria andUserRoleBetween(Integer value1, Integer value2) {
            addCriterion("user_role between", value1, value2, "userRole");
            return (Criteria) this;
        }

        public Criteria andUserRoleNotBetween(Integer value1, Integer value2) {
            addCriterion("user_role not between", value1, value2, "userRole");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAmountIsNull() {
            addCriterion("guarantee_amount is null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAmountIsNotNull() {
            addCriterion("guarantee_amount is not null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAmountEqualTo(BigDecimal value) {
            addCriterion("guarantee_amount =", value, "guaranteeAmount");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAmountNotEqualTo(BigDecimal value) {
            addCriterion("guarantee_amount <>", value, "guaranteeAmount");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAmountGreaterThan(BigDecimal value) {
            addCriterion("guarantee_amount >", value, "guaranteeAmount");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("guarantee_amount >=", value, "guaranteeAmount");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAmountLessThan(BigDecimal value) {
            addCriterion("guarantee_amount <", value, "guaranteeAmount");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("guarantee_amount <=", value, "guaranteeAmount");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAmountIn(List<BigDecimal> values) {
            addCriterion("guarantee_amount in", values, "guaranteeAmount");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAmountNotIn(List<BigDecimal> values) {
            addCriterion("guarantee_amount not in", values, "guaranteeAmount");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("guarantee_amount between", value1, value2, "guaranteeAmount");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("guarantee_amount not between", value1, value2, "guaranteeAmount");
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

        public Criteria andIsEnabledIsNull() {
            addCriterion("is_enabled is null");
            return (Criteria) this;
        }

        public Criteria andIsEnabledIsNotNull() {
            addCriterion("is_enabled is not null");
            return (Criteria) this;
        }

        public Criteria andIsEnabledEqualTo(Byte value) {
            addCriterion("is_enabled =", value, "isEnabled");
            return (Criteria) this;
        }

        public Criteria andIsEnabledNotEqualTo(Byte value) {
            addCriterion("is_enabled <>", value, "isEnabled");
            return (Criteria) this;
        }

        public Criteria andIsEnabledGreaterThan(Byte value) {
            addCriterion("is_enabled >", value, "isEnabled");
            return (Criteria) this;
        }

        public Criteria andIsEnabledGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_enabled >=", value, "isEnabled");
            return (Criteria) this;
        }

        public Criteria andIsEnabledLessThan(Byte value) {
            addCriterion("is_enabled <", value, "isEnabled");
            return (Criteria) this;
        }

        public Criteria andIsEnabledLessThanOrEqualTo(Byte value) {
            addCriterion("is_enabled <=", value, "isEnabled");
            return (Criteria) this;
        }

        public Criteria andIsEnabledIn(List<Byte> values) {
            addCriterion("is_enabled in", values, "isEnabled");
            return (Criteria) this;
        }

        public Criteria andIsEnabledNotIn(List<Byte> values) {
            addCriterion("is_enabled not in", values, "isEnabled");
            return (Criteria) this;
        }

        public Criteria andIsEnabledBetween(Byte value1, Byte value2) {
            addCriterion("is_enabled between", value1, value2, "isEnabled");
            return (Criteria) this;
        }

        public Criteria andIsEnabledNotBetween(Byte value1, Byte value2) {
            addCriterion("is_enabled not between", value1, value2, "isEnabled");
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

        public Criteria andModifyIdIsNull() {
            addCriterion("modify_id is null");
            return (Criteria) this;
        }

        public Criteria andModifyIdIsNotNull() {
            addCriterion("modify_id is not null");
            return (Criteria) this;
        }

        public Criteria andModifyIdEqualTo(Long value) {
            addCriterion("modify_id =", value, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdNotEqualTo(Long value) {
            addCriterion("modify_id <>", value, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdGreaterThan(Long value) {
            addCriterion("modify_id >", value, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("modify_id >=", value, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdLessThan(Long value) {
            addCriterion("modify_id <", value, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdLessThanOrEqualTo(Long value) {
            addCriterion("modify_id <=", value, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdIn(List<Long> values) {
            addCriterion("modify_id in", values, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdNotIn(List<Long> values) {
            addCriterion("modify_id not in", values, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdBetween(Long value1, Long value2) {
            addCriterion("modify_id between", value1, value2, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdNotBetween(Long value1, Long value2) {
            addCriterion("modify_id not between", value1, value2, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
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