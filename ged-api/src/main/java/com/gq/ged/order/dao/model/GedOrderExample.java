package com.gq.ged.order.dao.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GedOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public GedOrderExample() {
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

        public Criteria andOrderCodeIsNull() {
            addCriterion("order_code is null");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIsNotNull() {
            addCriterion("order_code is not null");
            return (Criteria) this;
        }

        public Criteria andOrderCodeEqualTo(String value) {
            addCriterion("order_code =", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotEqualTo(String value) {
            addCriterion("order_code <>", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeGreaterThan(String value) {
            addCriterion("order_code >", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeGreaterThanOrEqualTo(String value) {
            addCriterion("order_code >=", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLessThan(String value) {
            addCriterion("order_code <", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLessThanOrEqualTo(String value) {
            addCriterion("order_code <=", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLike(String value) {
            addCriterion("order_code like", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotLike(String value) {
            addCriterion("order_code not like", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIn(List<String> values) {
            addCriterion("order_code in", values, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotIn(List<String> values) {
            addCriterion("order_code not in", values, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeBetween(String value1, String value2) {
            addCriterion("order_code between", value1, value2, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotBetween(String value1, String value2) {
            addCriterion("order_code not between", value1, value2, "orderCode");
            return (Criteria) this;
        }

        public Criteria andMasterOrderCodeIsNull() {
            addCriterion("master_order_code is null");
            return (Criteria) this;
        }

        public Criteria andMasterOrderCodeIsNotNull() {
            addCriterion("master_order_code is not null");
            return (Criteria) this;
        }

        public Criteria andMasterOrderCodeEqualTo(String value) {
            addCriterion("master_order_code =", value, "masterOrderCode");
            return (Criteria) this;
        }

        public Criteria andMasterOrderCodeNotEqualTo(String value) {
            addCriterion("master_order_code <>", value, "masterOrderCode");
            return (Criteria) this;
        }

        public Criteria andMasterOrderCodeGreaterThan(String value) {
            addCriterion("master_order_code >", value, "masterOrderCode");
            return (Criteria) this;
        }

        public Criteria andMasterOrderCodeGreaterThanOrEqualTo(String value) {
            addCriterion("master_order_code >=", value, "masterOrderCode");
            return (Criteria) this;
        }

        public Criteria andMasterOrderCodeLessThan(String value) {
            addCriterion("master_order_code <", value, "masterOrderCode");
            return (Criteria) this;
        }

        public Criteria andMasterOrderCodeLessThanOrEqualTo(String value) {
            addCriterion("master_order_code <=", value, "masterOrderCode");
            return (Criteria) this;
        }

        public Criteria andMasterOrderCodeLike(String value) {
            addCriterion("master_order_code like", value, "masterOrderCode");
            return (Criteria) this;
        }

        public Criteria andMasterOrderCodeNotLike(String value) {
            addCriterion("master_order_code not like", value, "masterOrderCode");
            return (Criteria) this;
        }

        public Criteria andMasterOrderCodeIn(List<String> values) {
            addCriterion("master_order_code in", values, "masterOrderCode");
            return (Criteria) this;
        }

        public Criteria andMasterOrderCodeNotIn(List<String> values) {
            addCriterion("master_order_code not in", values, "masterOrderCode");
            return (Criteria) this;
        }

        public Criteria andMasterOrderCodeBetween(String value1, String value2) {
            addCriterion("master_order_code between", value1, value2, "masterOrderCode");
            return (Criteria) this;
        }

        public Criteria andMasterOrderCodeNotBetween(String value1, String value2) {
            addCriterion("master_order_code not between", value1, value2, "masterOrderCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeIsNull() {
            addCriterion("contract_code is null");
            return (Criteria) this;
        }

        public Criteria andContractCodeIsNotNull() {
            addCriterion("contract_code is not null");
            return (Criteria) this;
        }

        public Criteria andContractCodeEqualTo(String value) {
            addCriterion("contract_code =", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeNotEqualTo(String value) {
            addCriterion("contract_code <>", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeGreaterThan(String value) {
            addCriterion("contract_code >", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeGreaterThanOrEqualTo(String value) {
            addCriterion("contract_code >=", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeLessThan(String value) {
            addCriterion("contract_code <", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeLessThanOrEqualTo(String value) {
            addCriterion("contract_code <=", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeLike(String value) {
            addCriterion("contract_code like", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeNotLike(String value) {
            addCriterion("contract_code not like", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeIn(List<String> values) {
            addCriterion("contract_code in", values, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeNotIn(List<String> values) {
            addCriterion("contract_code not in", values, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeBetween(String value1, String value2) {
            addCriterion("contract_code between", value1, value2, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeNotBetween(String value1, String value2) {
            addCriterion("contract_code not between", value1, value2, "contractCode");
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

        public Criteria andCompanyTypeIsNull() {
            addCriterion("company_type is null");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeIsNotNull() {
            addCriterion("company_type is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeEqualTo(String value) {
            addCriterion("company_type =", value, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeNotEqualTo(String value) {
            addCriterion("company_type <>", value, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeGreaterThan(String value) {
            addCriterion("company_type >", value, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeGreaterThanOrEqualTo(String value) {
            addCriterion("company_type >=", value, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeLessThan(String value) {
            addCriterion("company_type <", value, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeLessThanOrEqualTo(String value) {
            addCriterion("company_type <=", value, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeLike(String value) {
            addCriterion("company_type like", value, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeNotLike(String value) {
            addCriterion("company_type not like", value, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeIn(List<String> values) {
            addCriterion("company_type in", values, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeNotIn(List<String> values) {
            addCriterion("company_type not in", values, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeBetween(String value1, String value2) {
            addCriterion("company_type between", value1, value2, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeNotBetween(String value1, String value2) {
            addCriterion("company_type not between", value1, value2, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyCardNumIsNull() {
            addCriterion("company_card_num is null");
            return (Criteria) this;
        }

        public Criteria andCompanyCardNumIsNotNull() {
            addCriterion("company_card_num is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyCardNumEqualTo(String value) {
            addCriterion("company_card_num =", value, "companyCardNum");
            return (Criteria) this;
        }

        public Criteria andCompanyCardNumNotEqualTo(String value) {
            addCriterion("company_card_num <>", value, "companyCardNum");
            return (Criteria) this;
        }

        public Criteria andCompanyCardNumGreaterThan(String value) {
            addCriterion("company_card_num >", value, "companyCardNum");
            return (Criteria) this;
        }

        public Criteria andCompanyCardNumGreaterThanOrEqualTo(String value) {
            addCriterion("company_card_num >=", value, "companyCardNum");
            return (Criteria) this;
        }

        public Criteria andCompanyCardNumLessThan(String value) {
            addCriterion("company_card_num <", value, "companyCardNum");
            return (Criteria) this;
        }

        public Criteria andCompanyCardNumLessThanOrEqualTo(String value) {
            addCriterion("company_card_num <=", value, "companyCardNum");
            return (Criteria) this;
        }

        public Criteria andCompanyCardNumLike(String value) {
            addCriterion("company_card_num like", value, "companyCardNum");
            return (Criteria) this;
        }

        public Criteria andCompanyCardNumNotLike(String value) {
            addCriterion("company_card_num not like", value, "companyCardNum");
            return (Criteria) this;
        }

        public Criteria andCompanyCardNumIn(List<String> values) {
            addCriterion("company_card_num in", values, "companyCardNum");
            return (Criteria) this;
        }

        public Criteria andCompanyCardNumNotIn(List<String> values) {
            addCriterion("company_card_num not in", values, "companyCardNum");
            return (Criteria) this;
        }

        public Criteria andCompanyCardNumBetween(String value1, String value2) {
            addCriterion("company_card_num between", value1, value2, "companyCardNum");
            return (Criteria) this;
        }

        public Criteria andCompanyCardNumNotBetween(String value1, String value2) {
            addCriterion("company_card_num not between", value1, value2, "companyCardNum");
            return (Criteria) this;
        }

        public Criteria andPersonCardTypeIsNull() {
            addCriterion("person_card_type is null");
            return (Criteria) this;
        }

        public Criteria andPersonCardTypeIsNotNull() {
            addCriterion("person_card_type is not null");
            return (Criteria) this;
        }

        public Criteria andPersonCardTypeEqualTo(Integer value) {
            addCriterion("person_card_type =", value, "personCardType");
            return (Criteria) this;
        }

        public Criteria andPersonCardTypeNotEqualTo(Integer value) {
            addCriterion("person_card_type <>", value, "personCardType");
            return (Criteria) this;
        }

        public Criteria andPersonCardTypeGreaterThan(Integer value) {
            addCriterion("person_card_type >", value, "personCardType");
            return (Criteria) this;
        }

        public Criteria andPersonCardTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("person_card_type >=", value, "personCardType");
            return (Criteria) this;
        }

        public Criteria andPersonCardTypeLessThan(Integer value) {
            addCriterion("person_card_type <", value, "personCardType");
            return (Criteria) this;
        }

        public Criteria andPersonCardTypeLessThanOrEqualTo(Integer value) {
            addCriterion("person_card_type <=", value, "personCardType");
            return (Criteria) this;
        }

        public Criteria andPersonCardTypeIn(List<Integer> values) {
            addCriterion("person_card_type in", values, "personCardType");
            return (Criteria) this;
        }

        public Criteria andPersonCardTypeNotIn(List<Integer> values) {
            addCriterion("person_card_type not in", values, "personCardType");
            return (Criteria) this;
        }

        public Criteria andPersonCardTypeBetween(Integer value1, Integer value2) {
            addCriterion("person_card_type between", value1, value2, "personCardType");
            return (Criteria) this;
        }

        public Criteria andPersonCardTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("person_card_type not between", value1, value2, "personCardType");
            return (Criteria) this;
        }

        public Criteria andPersonCardNumIsNull() {
            addCriterion("person_card_num is null");
            return (Criteria) this;
        }

        public Criteria andPersonCardNumIsNotNull() {
            addCriterion("person_card_num is not null");
            return (Criteria) this;
        }

        public Criteria andPersonCardNumEqualTo(String value) {
            addCriterion("person_card_num =", value, "personCardNum");
            return (Criteria) this;
        }

        public Criteria andPersonCardNumNotEqualTo(String value) {
            addCriterion("person_card_num <>", value, "personCardNum");
            return (Criteria) this;
        }

        public Criteria andPersonCardNumGreaterThan(String value) {
            addCriterion("person_card_num >", value, "personCardNum");
            return (Criteria) this;
        }

        public Criteria andPersonCardNumGreaterThanOrEqualTo(String value) {
            addCriterion("person_card_num >=", value, "personCardNum");
            return (Criteria) this;
        }

        public Criteria andPersonCardNumLessThan(String value) {
            addCriterion("person_card_num <", value, "personCardNum");
            return (Criteria) this;
        }

        public Criteria andPersonCardNumLessThanOrEqualTo(String value) {
            addCriterion("person_card_num <=", value, "personCardNum");
            return (Criteria) this;
        }

        public Criteria andPersonCardNumLike(String value) {
            addCriterion("person_card_num like", value, "personCardNum");
            return (Criteria) this;
        }

        public Criteria andPersonCardNumNotLike(String value) {
            addCriterion("person_card_num not like", value, "personCardNum");
            return (Criteria) this;
        }

        public Criteria andPersonCardNumIn(List<String> values) {
            addCriterion("person_card_num in", values, "personCardNum");
            return (Criteria) this;
        }

        public Criteria andPersonCardNumNotIn(List<String> values) {
            addCriterion("person_card_num not in", values, "personCardNum");
            return (Criteria) this;
        }

        public Criteria andPersonCardNumBetween(String value1, String value2) {
            addCriterion("person_card_num between", value1, value2, "personCardNum");
            return (Criteria) this;
        }

        public Criteria andPersonCardNumNotBetween(String value1, String value2) {
            addCriterion("person_card_num not between", value1, value2, "personCardNum");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeIsNull() {
            addCriterion("loan_purpose is null");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeIsNotNull() {
            addCriterion("loan_purpose is not null");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeEqualTo(Integer value) {
            addCriterion("loan_purpose =", value, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeNotEqualTo(Integer value) {
            addCriterion("loan_purpose <>", value, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeGreaterThan(Integer value) {
            addCriterion("loan_purpose >", value, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeGreaterThanOrEqualTo(Integer value) {
            addCriterion("loan_purpose >=", value, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeLessThan(Integer value) {
            addCriterion("loan_purpose <", value, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeLessThanOrEqualTo(Integer value) {
            addCriterion("loan_purpose <=", value, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeIn(List<Integer> values) {
            addCriterion("loan_purpose in", values, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeNotIn(List<Integer> values) {
            addCriterion("loan_purpose not in", values, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeBetween(Integer value1, Integer value2) {
            addCriterion("loan_purpose between", value1, value2, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeNotBetween(Integer value1, Integer value2) {
            addCriterion("loan_purpose not between", value1, value2, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanTypeIsNull() {
            addCriterion("loan_type is null");
            return (Criteria) this;
        }

        public Criteria andLoanTypeIsNotNull() {
            addCriterion("loan_type is not null");
            return (Criteria) this;
        }

        public Criteria andLoanTypeEqualTo(Integer value) {
            addCriterion("loan_type =", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeNotEqualTo(Integer value) {
            addCriterion("loan_type <>", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeGreaterThan(Integer value) {
            addCriterion("loan_type >", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("loan_type >=", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeLessThan(Integer value) {
            addCriterion("loan_type <", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeLessThanOrEqualTo(Integer value) {
            addCriterion("loan_type <=", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeIn(List<Integer> values) {
            addCriterion("loan_type in", values, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeNotIn(List<Integer> values) {
            addCriterion("loan_type not in", values, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeBetween(Integer value1, Integer value2) {
            addCriterion("loan_type between", value1, value2, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("loan_type not between", value1, value2, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanAmountIsNull() {
            addCriterion("loan_amount is null");
            return (Criteria) this;
        }

        public Criteria andLoanAmountIsNotNull() {
            addCriterion("loan_amount is not null");
            return (Criteria) this;
        }

        public Criteria andLoanAmountEqualTo(BigDecimal value) {
            addCriterion("loan_amount =", value, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountNotEqualTo(BigDecimal value) {
            addCriterion("loan_amount <>", value, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountGreaterThan(BigDecimal value) {
            addCriterion("loan_amount >", value, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_amount >=", value, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountLessThan(BigDecimal value) {
            addCriterion("loan_amount <", value, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_amount <=", value, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountIn(List<BigDecimal> values) {
            addCriterion("loan_amount in", values, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountNotIn(List<BigDecimal> values) {
            addCriterion("loan_amount not in", values, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_amount between", value1, value2, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_amount not between", value1, value2, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andOtherLoanAmountIsNull() {
            addCriterion("other_loan_amount is null");
            return (Criteria) this;
        }

        public Criteria andOtherLoanAmountIsNotNull() {
            addCriterion("other_loan_amount is not null");
            return (Criteria) this;
        }

        public Criteria andOtherLoanAmountEqualTo(String value) {
            addCriterion("other_loan_amount =", value, "otherLoanAmount");
            return (Criteria) this;
        }

        public Criteria andOtherLoanAmountNotEqualTo(String value) {
            addCriterion("other_loan_amount <>", value, "otherLoanAmount");
            return (Criteria) this;
        }

        public Criteria andOtherLoanAmountGreaterThan(String value) {
            addCriterion("other_loan_amount >", value, "otherLoanAmount");
            return (Criteria) this;
        }

        public Criteria andOtherLoanAmountGreaterThanOrEqualTo(String value) {
            addCriterion("other_loan_amount >=", value, "otherLoanAmount");
            return (Criteria) this;
        }

        public Criteria andOtherLoanAmountLessThan(String value) {
            addCriterion("other_loan_amount <", value, "otherLoanAmount");
            return (Criteria) this;
        }

        public Criteria andOtherLoanAmountLessThanOrEqualTo(String value) {
            addCriterion("other_loan_amount <=", value, "otherLoanAmount");
            return (Criteria) this;
        }

        public Criteria andOtherLoanAmountLike(String value) {
            addCriterion("other_loan_amount like", value, "otherLoanAmount");
            return (Criteria) this;
        }

        public Criteria andOtherLoanAmountNotLike(String value) {
            addCriterion("other_loan_amount not like", value, "otherLoanAmount");
            return (Criteria) this;
        }

        public Criteria andOtherLoanAmountIn(List<String> values) {
            addCriterion("other_loan_amount in", values, "otherLoanAmount");
            return (Criteria) this;
        }

        public Criteria andOtherLoanAmountNotIn(List<String> values) {
            addCriterion("other_loan_amount not in", values, "otherLoanAmount");
            return (Criteria) this;
        }

        public Criteria andOtherLoanAmountBetween(String value1, String value2) {
            addCriterion("other_loan_amount between", value1, value2, "otherLoanAmount");
            return (Criteria) this;
        }

        public Criteria andOtherLoanAmountNotBetween(String value1, String value2) {
            addCriterion("other_loan_amount not between", value1, value2, "otherLoanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanTermIsNull() {
            addCriterion("loan_term is null");
            return (Criteria) this;
        }

        public Criteria andLoanTermIsNotNull() {
            addCriterion("loan_term is not null");
            return (Criteria) this;
        }

        public Criteria andLoanTermEqualTo(Integer value) {
            addCriterion("loan_term =", value, "loanTerm");
            return (Criteria) this;
        }

        public Criteria andLoanTermNotEqualTo(Integer value) {
            addCriterion("loan_term <>", value, "loanTerm");
            return (Criteria) this;
        }

        public Criteria andLoanTermGreaterThan(Integer value) {
            addCriterion("loan_term >", value, "loanTerm");
            return (Criteria) this;
        }

        public Criteria andLoanTermGreaterThanOrEqualTo(Integer value) {
            addCriterion("loan_term >=", value, "loanTerm");
            return (Criteria) this;
        }

        public Criteria andLoanTermLessThan(Integer value) {
            addCriterion("loan_term <", value, "loanTerm");
            return (Criteria) this;
        }

        public Criteria andLoanTermLessThanOrEqualTo(Integer value) {
            addCriterion("loan_term <=", value, "loanTerm");
            return (Criteria) this;
        }

        public Criteria andLoanTermIn(List<Integer> values) {
            addCriterion("loan_term in", values, "loanTerm");
            return (Criteria) this;
        }

        public Criteria andLoanTermNotIn(List<Integer> values) {
            addCriterion("loan_term not in", values, "loanTerm");
            return (Criteria) this;
        }

        public Criteria andLoanTermBetween(Integer value1, Integer value2) {
            addCriterion("loan_term between", value1, value2, "loanTerm");
            return (Criteria) this;
        }

        public Criteria andLoanTermNotBetween(Integer value1, Integer value2) {
            addCriterion("loan_term not between", value1, value2, "loanTerm");
            return (Criteria) this;
        }

        public Criteria andCreditAmountIsNull() {
            addCriterion("credit_amount is null");
            return (Criteria) this;
        }

        public Criteria andCreditAmountIsNotNull() {
            addCriterion("credit_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCreditAmountEqualTo(BigDecimal value) {
            addCriterion("credit_amount =", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountNotEqualTo(BigDecimal value) {
            addCriterion("credit_amount <>", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountGreaterThan(BigDecimal value) {
            addCriterion("credit_amount >", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_amount >=", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountLessThan(BigDecimal value) {
            addCriterion("credit_amount <", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_amount <=", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountIn(List<BigDecimal> values) {
            addCriterion("credit_amount in", values, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountNotIn(List<BigDecimal> values) {
            addCriterion("credit_amount not in", values, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_amount between", value1, value2, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_amount not between", value1, value2, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andReplyAmountIsNull() {
            addCriterion("reply_amount is null");
            return (Criteria) this;
        }

        public Criteria andReplyAmountIsNotNull() {
            addCriterion("reply_amount is not null");
            return (Criteria) this;
        }

        public Criteria andReplyAmountEqualTo(BigDecimal value) {
            addCriterion("reply_amount =", value, "replyAmount");
            return (Criteria) this;
        }

        public Criteria andReplyAmountNotEqualTo(BigDecimal value) {
            addCriterion("reply_amount <>", value, "replyAmount");
            return (Criteria) this;
        }

        public Criteria andReplyAmountGreaterThan(BigDecimal value) {
            addCriterion("reply_amount >", value, "replyAmount");
            return (Criteria) this;
        }

        public Criteria andReplyAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("reply_amount >=", value, "replyAmount");
            return (Criteria) this;
        }

        public Criteria andReplyAmountLessThan(BigDecimal value) {
            addCriterion("reply_amount <", value, "replyAmount");
            return (Criteria) this;
        }

        public Criteria andReplyAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("reply_amount <=", value, "replyAmount");
            return (Criteria) this;
        }

        public Criteria andReplyAmountIn(List<BigDecimal> values) {
            addCriterion("reply_amount in", values, "replyAmount");
            return (Criteria) this;
        }

        public Criteria andReplyAmountNotIn(List<BigDecimal> values) {
            addCriterion("reply_amount not in", values, "replyAmount");
            return (Criteria) this;
        }

        public Criteria andReplyAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("reply_amount between", value1, value2, "replyAmount");
            return (Criteria) this;
        }

        public Criteria andReplyAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("reply_amount not between", value1, value2, "replyAmount");
            return (Criteria) this;
        }

        public Criteria andReplyTermIsNull() {
            addCriterion("reply_term is null");
            return (Criteria) this;
        }

        public Criteria andReplyTermIsNotNull() {
            addCriterion("reply_term is not null");
            return (Criteria) this;
        }

        public Criteria andReplyTermEqualTo(Integer value) {
            addCriterion("reply_term =", value, "replyTerm");
            return (Criteria) this;
        }

        public Criteria andReplyTermNotEqualTo(Integer value) {
            addCriterion("reply_term <>", value, "replyTerm");
            return (Criteria) this;
        }

        public Criteria andReplyTermGreaterThan(Integer value) {
            addCriterion("reply_term >", value, "replyTerm");
            return (Criteria) this;
        }

        public Criteria andReplyTermGreaterThanOrEqualTo(Integer value) {
            addCriterion("reply_term >=", value, "replyTerm");
            return (Criteria) this;
        }

        public Criteria andReplyTermLessThan(Integer value) {
            addCriterion("reply_term <", value, "replyTerm");
            return (Criteria) this;
        }

        public Criteria andReplyTermLessThanOrEqualTo(Integer value) {
            addCriterion("reply_term <=", value, "replyTerm");
            return (Criteria) this;
        }

        public Criteria andReplyTermIn(List<Integer> values) {
            addCriterion("reply_term in", values, "replyTerm");
            return (Criteria) this;
        }

        public Criteria andReplyTermNotIn(List<Integer> values) {
            addCriterion("reply_term not in", values, "replyTerm");
            return (Criteria) this;
        }

        public Criteria andReplyTermBetween(Integer value1, Integer value2) {
            addCriterion("reply_term between", value1, value2, "replyTerm");
            return (Criteria) this;
        }

        public Criteria andReplyTermNotBetween(Integer value1, Integer value2) {
            addCriterion("reply_term not between", value1, value2, "replyTerm");
            return (Criteria) this;
        }

        public Criteria andWithdrawFlagIsNull() {
            addCriterion("withdraw_flag is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawFlagIsNotNull() {
            addCriterion("withdraw_flag is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawFlagEqualTo(Integer value) {
            addCriterion("withdraw_flag =", value, "withdrawFlag");
            return (Criteria) this;
        }

        public Criteria andWithdrawFlagNotEqualTo(Integer value) {
            addCriterion("withdraw_flag <>", value, "withdrawFlag");
            return (Criteria) this;
        }

        public Criteria andWithdrawFlagGreaterThan(Integer value) {
            addCriterion("withdraw_flag >", value, "withdrawFlag");
            return (Criteria) this;
        }

        public Criteria andWithdrawFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("withdraw_flag >=", value, "withdrawFlag");
            return (Criteria) this;
        }

        public Criteria andWithdrawFlagLessThan(Integer value) {
            addCriterion("withdraw_flag <", value, "withdrawFlag");
            return (Criteria) this;
        }

        public Criteria andWithdrawFlagLessThanOrEqualTo(Integer value) {
            addCriterion("withdraw_flag <=", value, "withdrawFlag");
            return (Criteria) this;
        }

        public Criteria andWithdrawFlagIn(List<Integer> values) {
            addCriterion("withdraw_flag in", values, "withdrawFlag");
            return (Criteria) this;
        }

        public Criteria andWithdrawFlagNotIn(List<Integer> values) {
            addCriterion("withdraw_flag not in", values, "withdrawFlag");
            return (Criteria) this;
        }

        public Criteria andWithdrawFlagBetween(Integer value1, Integer value2) {
            addCriterion("withdraw_flag between", value1, value2, "withdrawFlag");
            return (Criteria) this;
        }

        public Criteria andWithdrawFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("withdraw_flag not between", value1, value2, "withdrawFlag");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountIsNull() {
            addCriterion("withdraw_amount is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountIsNotNull() {
            addCriterion("withdraw_amount is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountEqualTo(BigDecimal value) {
            addCriterion("withdraw_amount =", value, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountNotEqualTo(BigDecimal value) {
            addCriterion("withdraw_amount <>", value, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountGreaterThan(BigDecimal value) {
            addCriterion("withdraw_amount >", value, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("withdraw_amount >=", value, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountLessThan(BigDecimal value) {
            addCriterion("withdraw_amount <", value, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("withdraw_amount <=", value, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountIn(List<BigDecimal> values) {
            addCriterion("withdraw_amount in", values, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountNotIn(List<BigDecimal> values) {
            addCriterion("withdraw_amount not in", values, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdraw_amount between", value1, value2, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdraw_amount not between", value1, value2, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andRateDayIsNull() {
            addCriterion("rate_day is null");
            return (Criteria) this;
        }

        public Criteria andRateDayIsNotNull() {
            addCriterion("rate_day is not null");
            return (Criteria) this;
        }

        public Criteria andRateDayEqualTo(BigDecimal value) {
            addCriterion("rate_day =", value, "rateDay");
            return (Criteria) this;
        }

        public Criteria andRateDayNotEqualTo(BigDecimal value) {
            addCriterion("rate_day <>", value, "rateDay");
            return (Criteria) this;
        }

        public Criteria andRateDayGreaterThan(BigDecimal value) {
            addCriterion("rate_day >", value, "rateDay");
            return (Criteria) this;
        }

        public Criteria andRateDayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("rate_day >=", value, "rateDay");
            return (Criteria) this;
        }

        public Criteria andRateDayLessThan(BigDecimal value) {
            addCriterion("rate_day <", value, "rateDay");
            return (Criteria) this;
        }

        public Criteria andRateDayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("rate_day <=", value, "rateDay");
            return (Criteria) this;
        }

        public Criteria andRateDayIn(List<BigDecimal> values) {
            addCriterion("rate_day in", values, "rateDay");
            return (Criteria) this;
        }

        public Criteria andRateDayNotIn(List<BigDecimal> values) {
            addCriterion("rate_day not in", values, "rateDay");
            return (Criteria) this;
        }

        public Criteria andRateDayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rate_day between", value1, value2, "rateDay");
            return (Criteria) this;
        }

        public Criteria andRateDayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rate_day not between", value1, value2, "rateDay");
            return (Criteria) this;
        }

        public Criteria andProvinceIdIsNull() {
            addCriterion("province_id is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIdIsNotNull() {
            addCriterion("province_id is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceIdEqualTo(Long value) {
            addCriterion("province_id =", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdNotEqualTo(Long value) {
            addCriterion("province_id <>", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdGreaterThan(Long value) {
            addCriterion("province_id >", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdGreaterThanOrEqualTo(Long value) {
            addCriterion("province_id >=", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdLessThan(Long value) {
            addCriterion("province_id <", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdLessThanOrEqualTo(Long value) {
            addCriterion("province_id <=", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdIn(List<Long> values) {
            addCriterion("province_id in", values, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdNotIn(List<Long> values) {
            addCriterion("province_id not in", values, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdBetween(Long value1, Long value2) {
            addCriterion("province_id between", value1, value2, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdNotBetween(Long value1, Long value2) {
            addCriterion("province_id not between", value1, value2, "provinceId");
            return (Criteria) this;
        }

        public Criteria andCityIdIsNull() {
            addCriterion("city_id is null");
            return (Criteria) this;
        }

        public Criteria andCityIdIsNotNull() {
            addCriterion("city_id is not null");
            return (Criteria) this;
        }

        public Criteria andCityIdEqualTo(Long value) {
            addCriterion("city_id =", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotEqualTo(Long value) {
            addCriterion("city_id <>", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdGreaterThan(Long value) {
            addCriterion("city_id >", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdGreaterThanOrEqualTo(Long value) {
            addCriterion("city_id >=", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdLessThan(Long value) {
            addCriterion("city_id <", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdLessThanOrEqualTo(Long value) {
            addCriterion("city_id <=", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdIn(List<Long> values) {
            addCriterion("city_id in", values, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotIn(List<Long> values) {
            addCriterion("city_id not in", values, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdBetween(Long value1, Long value2) {
            addCriterion("city_id between", value1, value2, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotBetween(Long value1, Long value2) {
            addCriterion("city_id not between", value1, value2, "cityId");
            return (Criteria) this;
        }

        public Criteria andAreaIdIsNull() {
            addCriterion("area_id is null");
            return (Criteria) this;
        }

        public Criteria andAreaIdIsNotNull() {
            addCriterion("area_id is not null");
            return (Criteria) this;
        }

        public Criteria andAreaIdEqualTo(Long value) {
            addCriterion("area_id =", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotEqualTo(Long value) {
            addCriterion("area_id <>", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdGreaterThan(Long value) {
            addCriterion("area_id >", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdGreaterThanOrEqualTo(Long value) {
            addCriterion("area_id >=", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdLessThan(Long value) {
            addCriterion("area_id <", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdLessThanOrEqualTo(Long value) {
            addCriterion("area_id <=", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdIn(List<Long> values) {
            addCriterion("area_id in", values, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotIn(List<Long> values) {
            addCriterion("area_id not in", values, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdBetween(Long value1, Long value2) {
            addCriterion("area_id between", value1, value2, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotBetween(Long value1, Long value2) {
            addCriterion("area_id not between", value1, value2, "areaId");
            return (Criteria) this;
        }

        public Criteria andManagementAddrIsNull() {
            addCriterion("management_addr is null");
            return (Criteria) this;
        }

        public Criteria andManagementAddrIsNotNull() {
            addCriterion("management_addr is not null");
            return (Criteria) this;
        }

        public Criteria andManagementAddrEqualTo(String value) {
            addCriterion("management_addr =", value, "managementAddr");
            return (Criteria) this;
        }

        public Criteria andManagementAddrNotEqualTo(String value) {
            addCriterion("management_addr <>", value, "managementAddr");
            return (Criteria) this;
        }

        public Criteria andManagementAddrGreaterThan(String value) {
            addCriterion("management_addr >", value, "managementAddr");
            return (Criteria) this;
        }

        public Criteria andManagementAddrGreaterThanOrEqualTo(String value) {
            addCriterion("management_addr >=", value, "managementAddr");
            return (Criteria) this;
        }

        public Criteria andManagementAddrLessThan(String value) {
            addCriterion("management_addr <", value, "managementAddr");
            return (Criteria) this;
        }

        public Criteria andManagementAddrLessThanOrEqualTo(String value) {
            addCriterion("management_addr <=", value, "managementAddr");
            return (Criteria) this;
        }

        public Criteria andManagementAddrLike(String value) {
            addCriterion("management_addr like", value, "managementAddr");
            return (Criteria) this;
        }

        public Criteria andManagementAddrNotLike(String value) {
            addCriterion("management_addr not like", value, "managementAddr");
            return (Criteria) this;
        }

        public Criteria andManagementAddrIn(List<String> values) {
            addCriterion("management_addr in", values, "managementAddr");
            return (Criteria) this;
        }

        public Criteria andManagementAddrNotIn(List<String> values) {
            addCriterion("management_addr not in", values, "managementAddr");
            return (Criteria) this;
        }

        public Criteria andManagementAddrBetween(String value1, String value2) {
            addCriterion("management_addr between", value1, value2, "managementAddr");
            return (Criteria) this;
        }

        public Criteria andManagementAddrNotBetween(String value1, String value2) {
            addCriterion("management_addr not between", value1, value2, "managementAddr");
            return (Criteria) this;
        }

        public Criteria andContactPhoneIsNull() {
            addCriterion("contact_phone is null");
            return (Criteria) this;
        }

        public Criteria andContactPhoneIsNotNull() {
            addCriterion("contact_phone is not null");
            return (Criteria) this;
        }

        public Criteria andContactPhoneEqualTo(String value) {
            addCriterion("contact_phone =", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotEqualTo(String value) {
            addCriterion("contact_phone <>", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneGreaterThan(String value) {
            addCriterion("contact_phone >", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("contact_phone >=", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneLessThan(String value) {
            addCriterion("contact_phone <", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneLessThanOrEqualTo(String value) {
            addCriterion("contact_phone <=", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneLike(String value) {
            addCriterion("contact_phone like", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotLike(String value) {
            addCriterion("contact_phone not like", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneIn(List<String> values) {
            addCriterion("contact_phone in", values, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotIn(List<String> values) {
            addCriterion("contact_phone not in", values, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneBetween(String value1, String value2) {
            addCriterion("contact_phone between", value1, value2, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotBetween(String value1, String value2) {
            addCriterion("contact_phone not between", value1, value2, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContractSignProvinceIsNull() {
            addCriterion("contract_sign_province is null");
            return (Criteria) this;
        }

        public Criteria andContractSignProvinceIsNotNull() {
            addCriterion("contract_sign_province is not null");
            return (Criteria) this;
        }

        public Criteria andContractSignProvinceEqualTo(String value) {
            addCriterion("contract_sign_province =", value, "contractSignProvince");
            return (Criteria) this;
        }

        public Criteria andContractSignProvinceNotEqualTo(String value) {
            addCriterion("contract_sign_province <>", value, "contractSignProvince");
            return (Criteria) this;
        }

        public Criteria andContractSignProvinceGreaterThan(String value) {
            addCriterion("contract_sign_province >", value, "contractSignProvince");
            return (Criteria) this;
        }

        public Criteria andContractSignProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("contract_sign_province >=", value, "contractSignProvince");
            return (Criteria) this;
        }

        public Criteria andContractSignProvinceLessThan(String value) {
            addCriterion("contract_sign_province <", value, "contractSignProvince");
            return (Criteria) this;
        }

        public Criteria andContractSignProvinceLessThanOrEqualTo(String value) {
            addCriterion("contract_sign_province <=", value, "contractSignProvince");
            return (Criteria) this;
        }

        public Criteria andContractSignProvinceLike(String value) {
            addCriterion("contract_sign_province like", value, "contractSignProvince");
            return (Criteria) this;
        }

        public Criteria andContractSignProvinceNotLike(String value) {
            addCriterion("contract_sign_province not like", value, "contractSignProvince");
            return (Criteria) this;
        }

        public Criteria andContractSignProvinceIn(List<String> values) {
            addCriterion("contract_sign_province in", values, "contractSignProvince");
            return (Criteria) this;
        }

        public Criteria andContractSignProvinceNotIn(List<String> values) {
            addCriterion("contract_sign_province not in", values, "contractSignProvince");
            return (Criteria) this;
        }

        public Criteria andContractSignProvinceBetween(String value1, String value2) {
            addCriterion("contract_sign_province between", value1, value2, "contractSignProvince");
            return (Criteria) this;
        }

        public Criteria andContractSignProvinceNotBetween(String value1, String value2) {
            addCriterion("contract_sign_province not between", value1, value2, "contractSignProvince");
            return (Criteria) this;
        }

        public Criteria andContractSignCityIsNull() {
            addCriterion("contract_sign_city is null");
            return (Criteria) this;
        }

        public Criteria andContractSignCityIsNotNull() {
            addCriterion("contract_sign_city is not null");
            return (Criteria) this;
        }

        public Criteria andContractSignCityEqualTo(String value) {
            addCriterion("contract_sign_city =", value, "contractSignCity");
            return (Criteria) this;
        }

        public Criteria andContractSignCityNotEqualTo(String value) {
            addCriterion("contract_sign_city <>", value, "contractSignCity");
            return (Criteria) this;
        }

        public Criteria andContractSignCityGreaterThan(String value) {
            addCriterion("contract_sign_city >", value, "contractSignCity");
            return (Criteria) this;
        }

        public Criteria andContractSignCityGreaterThanOrEqualTo(String value) {
            addCriterion("contract_sign_city >=", value, "contractSignCity");
            return (Criteria) this;
        }

        public Criteria andContractSignCityLessThan(String value) {
            addCriterion("contract_sign_city <", value, "contractSignCity");
            return (Criteria) this;
        }

        public Criteria andContractSignCityLessThanOrEqualTo(String value) {
            addCriterion("contract_sign_city <=", value, "contractSignCity");
            return (Criteria) this;
        }

        public Criteria andContractSignCityLike(String value) {
            addCriterion("contract_sign_city like", value, "contractSignCity");
            return (Criteria) this;
        }

        public Criteria andContractSignCityNotLike(String value) {
            addCriterion("contract_sign_city not like", value, "contractSignCity");
            return (Criteria) this;
        }

        public Criteria andContractSignCityIn(List<String> values) {
            addCriterion("contract_sign_city in", values, "contractSignCity");
            return (Criteria) this;
        }

        public Criteria andContractSignCityNotIn(List<String> values) {
            addCriterion("contract_sign_city not in", values, "contractSignCity");
            return (Criteria) this;
        }

        public Criteria andContractSignCityBetween(String value1, String value2) {
            addCriterion("contract_sign_city between", value1, value2, "contractSignCity");
            return (Criteria) this;
        }

        public Criteria andContractSignCityNotBetween(String value1, String value2) {
            addCriterion("contract_sign_city not between", value1, value2, "contractSignCity");
            return (Criteria) this;
        }

        public Criteria andContractSignAreaIsNull() {
            addCriterion("contract_sign_area is null");
            return (Criteria) this;
        }

        public Criteria andContractSignAreaIsNotNull() {
            addCriterion("contract_sign_area is not null");
            return (Criteria) this;
        }

        public Criteria andContractSignAreaEqualTo(String value) {
            addCriterion("contract_sign_area =", value, "contractSignArea");
            return (Criteria) this;
        }

        public Criteria andContractSignAreaNotEqualTo(String value) {
            addCriterion("contract_sign_area <>", value, "contractSignArea");
            return (Criteria) this;
        }

        public Criteria andContractSignAreaGreaterThan(String value) {
            addCriterion("contract_sign_area >", value, "contractSignArea");
            return (Criteria) this;
        }

        public Criteria andContractSignAreaGreaterThanOrEqualTo(String value) {
            addCriterion("contract_sign_area >=", value, "contractSignArea");
            return (Criteria) this;
        }

        public Criteria andContractSignAreaLessThan(String value) {
            addCriterion("contract_sign_area <", value, "contractSignArea");
            return (Criteria) this;
        }

        public Criteria andContractSignAreaLessThanOrEqualTo(String value) {
            addCriterion("contract_sign_area <=", value, "contractSignArea");
            return (Criteria) this;
        }

        public Criteria andContractSignAreaLike(String value) {
            addCriterion("contract_sign_area like", value, "contractSignArea");
            return (Criteria) this;
        }

        public Criteria andContractSignAreaNotLike(String value) {
            addCriterion("contract_sign_area not like", value, "contractSignArea");
            return (Criteria) this;
        }

        public Criteria andContractSignAreaIn(List<String> values) {
            addCriterion("contract_sign_area in", values, "contractSignArea");
            return (Criteria) this;
        }

        public Criteria andContractSignAreaNotIn(List<String> values) {
            addCriterion("contract_sign_area not in", values, "contractSignArea");
            return (Criteria) this;
        }

        public Criteria andContractSignAreaBetween(String value1, String value2) {
            addCriterion("contract_sign_area between", value1, value2, "contractSignArea");
            return (Criteria) this;
        }

        public Criteria andContractSignAreaNotBetween(String value1, String value2) {
            addCriterion("contract_sign_area not between", value1, value2, "contractSignArea");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFlagIsNull() {
            addCriterion("guarantee_flag is null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFlagIsNotNull() {
            addCriterion("guarantee_flag is not null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFlagEqualTo(Integer value) {
            addCriterion("guarantee_flag =", value, "guaranteeFlag");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFlagNotEqualTo(Integer value) {
            addCriterion("guarantee_flag <>", value, "guaranteeFlag");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFlagGreaterThan(Integer value) {
            addCriterion("guarantee_flag >", value, "guaranteeFlag");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("guarantee_flag >=", value, "guaranteeFlag");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFlagLessThan(Integer value) {
            addCriterion("guarantee_flag <", value, "guaranteeFlag");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFlagLessThanOrEqualTo(Integer value) {
            addCriterion("guarantee_flag <=", value, "guaranteeFlag");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFlagIn(List<Integer> values) {
            addCriterion("guarantee_flag in", values, "guaranteeFlag");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFlagNotIn(List<Integer> values) {
            addCriterion("guarantee_flag not in", values, "guaranteeFlag");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFlagBetween(Integer value1, Integer value2) {
            addCriterion("guarantee_flag between", value1, value2, "guaranteeFlag");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("guarantee_flag not between", value1, value2, "guaranteeFlag");
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

        public Criteria andReceivableCashDepositIsNull() {
            addCriterion("receivable_cash_deposit is null");
            return (Criteria) this;
        }

        public Criteria andReceivableCashDepositIsNotNull() {
            addCriterion("receivable_cash_deposit is not null");
            return (Criteria) this;
        }

        public Criteria andReceivableCashDepositEqualTo(BigDecimal value) {
            addCriterion("receivable_cash_deposit =", value, "receivableCashDeposit");
            return (Criteria) this;
        }

        public Criteria andReceivableCashDepositNotEqualTo(BigDecimal value) {
            addCriterion("receivable_cash_deposit <>", value, "receivableCashDeposit");
            return (Criteria) this;
        }

        public Criteria andReceivableCashDepositGreaterThan(BigDecimal value) {
            addCriterion("receivable_cash_deposit >", value, "receivableCashDeposit");
            return (Criteria) this;
        }

        public Criteria andReceivableCashDepositGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("receivable_cash_deposit >=", value, "receivableCashDeposit");
            return (Criteria) this;
        }

        public Criteria andReceivableCashDepositLessThan(BigDecimal value) {
            addCriterion("receivable_cash_deposit <", value, "receivableCashDeposit");
            return (Criteria) this;
        }

        public Criteria andReceivableCashDepositLessThanOrEqualTo(BigDecimal value) {
            addCriterion("receivable_cash_deposit <=", value, "receivableCashDeposit");
            return (Criteria) this;
        }

        public Criteria andReceivableCashDepositIn(List<BigDecimal> values) {
            addCriterion("receivable_cash_deposit in", values, "receivableCashDeposit");
            return (Criteria) this;
        }

        public Criteria andReceivableCashDepositNotIn(List<BigDecimal> values) {
            addCriterion("receivable_cash_deposit not in", values, "receivableCashDeposit");
            return (Criteria) this;
        }

        public Criteria andReceivableCashDepositBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receivable_cash_deposit between", value1, value2, "receivableCashDeposit");
            return (Criteria) this;
        }

        public Criteria andReceivableCashDepositNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receivable_cash_deposit not between", value1, value2, "receivableCashDeposit");
            return (Criteria) this;
        }

        public Criteria andReceivableGuaranteeFeeIsNull() {
            addCriterion("receivable_guarantee_fee is null");
            return (Criteria) this;
        }

        public Criteria andReceivableGuaranteeFeeIsNotNull() {
            addCriterion("receivable_guarantee_fee is not null");
            return (Criteria) this;
        }

        public Criteria andReceivableGuaranteeFeeEqualTo(BigDecimal value) {
            addCriterion("receivable_guarantee_fee =", value, "receivableGuaranteeFee");
            return (Criteria) this;
        }

        public Criteria andReceivableGuaranteeFeeNotEqualTo(BigDecimal value) {
            addCriterion("receivable_guarantee_fee <>", value, "receivableGuaranteeFee");
            return (Criteria) this;
        }

        public Criteria andReceivableGuaranteeFeeGreaterThan(BigDecimal value) {
            addCriterion("receivable_guarantee_fee >", value, "receivableGuaranteeFee");
            return (Criteria) this;
        }

        public Criteria andReceivableGuaranteeFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("receivable_guarantee_fee >=", value, "receivableGuaranteeFee");
            return (Criteria) this;
        }

        public Criteria andReceivableGuaranteeFeeLessThan(BigDecimal value) {
            addCriterion("receivable_guarantee_fee <", value, "receivableGuaranteeFee");
            return (Criteria) this;
        }

        public Criteria andReceivableGuaranteeFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("receivable_guarantee_fee <=", value, "receivableGuaranteeFee");
            return (Criteria) this;
        }

        public Criteria andReceivableGuaranteeFeeIn(List<BigDecimal> values) {
            addCriterion("receivable_guarantee_fee in", values, "receivableGuaranteeFee");
            return (Criteria) this;
        }

        public Criteria andReceivableGuaranteeFeeNotIn(List<BigDecimal> values) {
            addCriterion("receivable_guarantee_fee not in", values, "receivableGuaranteeFee");
            return (Criteria) this;
        }

        public Criteria andReceivableGuaranteeFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receivable_guarantee_fee between", value1, value2, "receivableGuaranteeFee");
            return (Criteria) this;
        }

        public Criteria andReceivableGuaranteeFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receivable_guarantee_fee not between", value1, value2, "receivableGuaranteeFee");
            return (Criteria) this;
        }

        public Criteria andCashDepositIsNull() {
            addCriterion("cash_deposit is null");
            return (Criteria) this;
        }

        public Criteria andCashDepositIsNotNull() {
            addCriterion("cash_deposit is not null");
            return (Criteria) this;
        }

        public Criteria andCashDepositEqualTo(BigDecimal value) {
            addCriterion("cash_deposit =", value, "cashDeposit");
            return (Criteria) this;
        }

        public Criteria andCashDepositNotEqualTo(BigDecimal value) {
            addCriterion("cash_deposit <>", value, "cashDeposit");
            return (Criteria) this;
        }

        public Criteria andCashDepositGreaterThan(BigDecimal value) {
            addCriterion("cash_deposit >", value, "cashDeposit");
            return (Criteria) this;
        }

        public Criteria andCashDepositGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cash_deposit >=", value, "cashDeposit");
            return (Criteria) this;
        }

        public Criteria andCashDepositLessThan(BigDecimal value) {
            addCriterion("cash_deposit <", value, "cashDeposit");
            return (Criteria) this;
        }

        public Criteria andCashDepositLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cash_deposit <=", value, "cashDeposit");
            return (Criteria) this;
        }

        public Criteria andCashDepositIn(List<BigDecimal> values) {
            addCriterion("cash_deposit in", values, "cashDeposit");
            return (Criteria) this;
        }

        public Criteria andCashDepositNotIn(List<BigDecimal> values) {
            addCriterion("cash_deposit not in", values, "cashDeposit");
            return (Criteria) this;
        }

        public Criteria andCashDepositBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cash_deposit between", value1, value2, "cashDeposit");
            return (Criteria) this;
        }

        public Criteria andCashDepositNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cash_deposit not between", value1, value2, "cashDeposit");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeIsNull() {
            addCriterion("guarantee_fee is null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeIsNotNull() {
            addCriterion("guarantee_fee is not null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeEqualTo(BigDecimal value) {
            addCriterion("guarantee_fee =", value, "guaranteeFee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeNotEqualTo(BigDecimal value) {
            addCriterion("guarantee_fee <>", value, "guaranteeFee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeGreaterThan(BigDecimal value) {
            addCriterion("guarantee_fee >", value, "guaranteeFee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("guarantee_fee >=", value, "guaranteeFee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeLessThan(BigDecimal value) {
            addCriterion("guarantee_fee <", value, "guaranteeFee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("guarantee_fee <=", value, "guaranteeFee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeIn(List<BigDecimal> values) {
            addCriterion("guarantee_fee in", values, "guaranteeFee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeNotIn(List<BigDecimal> values) {
            addCriterion("guarantee_fee not in", values, "guaranteeFee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("guarantee_fee between", value1, value2, "guaranteeFee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("guarantee_fee not between", value1, value2, "guaranteeFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeWayIsNull() {
            addCriterion("service_fee_way is null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeWayIsNotNull() {
            addCriterion("service_fee_way is not null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeWayEqualTo(Integer value) {
            addCriterion("service_fee_way =", value, "serviceFeeWay");
            return (Criteria) this;
        }

        public Criteria andServiceFeeWayNotEqualTo(Integer value) {
            addCriterion("service_fee_way <>", value, "serviceFeeWay");
            return (Criteria) this;
        }

        public Criteria andServiceFeeWayGreaterThan(Integer value) {
            addCriterion("service_fee_way >", value, "serviceFeeWay");
            return (Criteria) this;
        }

        public Criteria andServiceFeeWayGreaterThanOrEqualTo(Integer value) {
            addCriterion("service_fee_way >=", value, "serviceFeeWay");
            return (Criteria) this;
        }

        public Criteria andServiceFeeWayLessThan(Integer value) {
            addCriterion("service_fee_way <", value, "serviceFeeWay");
            return (Criteria) this;
        }

        public Criteria andServiceFeeWayLessThanOrEqualTo(Integer value) {
            addCriterion("service_fee_way <=", value, "serviceFeeWay");
            return (Criteria) this;
        }

        public Criteria andServiceFeeWayIn(List<Integer> values) {
            addCriterion("service_fee_way in", values, "serviceFeeWay");
            return (Criteria) this;
        }

        public Criteria andServiceFeeWayNotIn(List<Integer> values) {
            addCriterion("service_fee_way not in", values, "serviceFeeWay");
            return (Criteria) this;
        }

        public Criteria andServiceFeeWayBetween(Integer value1, Integer value2) {
            addCriterion("service_fee_way between", value1, value2, "serviceFeeWay");
            return (Criteria) this;
        }

        public Criteria andServiceFeeWayNotBetween(Integer value1, Integer value2) {
            addCriterion("service_fee_way not between", value1, value2, "serviceFeeWay");
            return (Criteria) this;
        }

        public Criteria andServiceFeeIsNull() {
            addCriterion("service_fee is null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeIsNotNull() {
            addCriterion("service_fee is not null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeEqualTo(BigDecimal value) {
            addCriterion("service_fee =", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeNotEqualTo(BigDecimal value) {
            addCriterion("service_fee <>", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeGreaterThan(BigDecimal value) {
            addCriterion("service_fee >", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("service_fee >=", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeLessThan(BigDecimal value) {
            addCriterion("service_fee <", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("service_fee <=", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeIn(List<BigDecimal> values) {
            addCriterion("service_fee in", values, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeNotIn(List<BigDecimal> values) {
            addCriterion("service_fee not in", values, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("service_fee between", value1, value2, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("service_fee not between", value1, value2, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeFlagIsNull() {
            addCriterion("service_fee_flag is null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeFlagIsNotNull() {
            addCriterion("service_fee_flag is not null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeFlagEqualTo(Integer value) {
            addCriterion("service_fee_flag =", value, "serviceFeeFlag");
            return (Criteria) this;
        }

        public Criteria andServiceFeeFlagNotEqualTo(Integer value) {
            addCriterion("service_fee_flag <>", value, "serviceFeeFlag");
            return (Criteria) this;
        }

        public Criteria andServiceFeeFlagGreaterThan(Integer value) {
            addCriterion("service_fee_flag >", value, "serviceFeeFlag");
            return (Criteria) this;
        }

        public Criteria andServiceFeeFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("service_fee_flag >=", value, "serviceFeeFlag");
            return (Criteria) this;
        }

        public Criteria andServiceFeeFlagLessThan(Integer value) {
            addCriterion("service_fee_flag <", value, "serviceFeeFlag");
            return (Criteria) this;
        }

        public Criteria andServiceFeeFlagLessThanOrEqualTo(Integer value) {
            addCriterion("service_fee_flag <=", value, "serviceFeeFlag");
            return (Criteria) this;
        }

        public Criteria andServiceFeeFlagIn(List<Integer> values) {
            addCriterion("service_fee_flag in", values, "serviceFeeFlag");
            return (Criteria) this;
        }

        public Criteria andServiceFeeFlagNotIn(List<Integer> values) {
            addCriterion("service_fee_flag not in", values, "serviceFeeFlag");
            return (Criteria) this;
        }

        public Criteria andServiceFeeFlagBetween(Integer value1, Integer value2) {
            addCriterion("service_fee_flag between", value1, value2, "serviceFeeFlag");
            return (Criteria) this;
        }

        public Criteria andServiceFeeFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("service_fee_flag not between", value1, value2, "serviceFeeFlag");
            return (Criteria) this;
        }

        public Criteria andServiceFeeResultIsNull() {
            addCriterion("service_fee_result is null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeResultIsNotNull() {
            addCriterion("service_fee_result is not null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeResultEqualTo(Integer value) {
            addCriterion("service_fee_result =", value, "serviceFeeResult");
            return (Criteria) this;
        }

        public Criteria andServiceFeeResultNotEqualTo(Integer value) {
            addCriterion("service_fee_result <>", value, "serviceFeeResult");
            return (Criteria) this;
        }

        public Criteria andServiceFeeResultGreaterThan(Integer value) {
            addCriterion("service_fee_result >", value, "serviceFeeResult");
            return (Criteria) this;
        }

        public Criteria andServiceFeeResultGreaterThanOrEqualTo(Integer value) {
            addCriterion("service_fee_result >=", value, "serviceFeeResult");
            return (Criteria) this;
        }

        public Criteria andServiceFeeResultLessThan(Integer value) {
            addCriterion("service_fee_result <", value, "serviceFeeResult");
            return (Criteria) this;
        }

        public Criteria andServiceFeeResultLessThanOrEqualTo(Integer value) {
            addCriterion("service_fee_result <=", value, "serviceFeeResult");
            return (Criteria) this;
        }

        public Criteria andServiceFeeResultIn(List<Integer> values) {
            addCriterion("service_fee_result in", values, "serviceFeeResult");
            return (Criteria) this;
        }

        public Criteria andServiceFeeResultNotIn(List<Integer> values) {
            addCriterion("service_fee_result not in", values, "serviceFeeResult");
            return (Criteria) this;
        }

        public Criteria andServiceFeeResultBetween(Integer value1, Integer value2) {
            addCriterion("service_fee_result between", value1, value2, "serviceFeeResult");
            return (Criteria) this;
        }

        public Criteria andServiceFeeResultNotBetween(Integer value1, Integer value2) {
            addCriterion("service_fee_result not between", value1, value2, "serviceFeeResult");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeFlagIsNull() {
            addCriterion("guarantee_fee_flag is null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeFlagIsNotNull() {
            addCriterion("guarantee_fee_flag is not null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeFlagEqualTo(Integer value) {
            addCriterion("guarantee_fee_flag =", value, "guaranteeFeeFlag");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeFlagNotEqualTo(Integer value) {
            addCriterion("guarantee_fee_flag <>", value, "guaranteeFeeFlag");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeFlagGreaterThan(Integer value) {
            addCriterion("guarantee_fee_flag >", value, "guaranteeFeeFlag");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("guarantee_fee_flag >=", value, "guaranteeFeeFlag");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeFlagLessThan(Integer value) {
            addCriterion("guarantee_fee_flag <", value, "guaranteeFeeFlag");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeFlagLessThanOrEqualTo(Integer value) {
            addCriterion("guarantee_fee_flag <=", value, "guaranteeFeeFlag");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeFlagIn(List<Integer> values) {
            addCriterion("guarantee_fee_flag in", values, "guaranteeFeeFlag");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeFlagNotIn(List<Integer> values) {
            addCriterion("guarantee_fee_flag not in", values, "guaranteeFeeFlag");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeFlagBetween(Integer value1, Integer value2) {
            addCriterion("guarantee_fee_flag between", value1, value2, "guaranteeFeeFlag");
            return (Criteria) this;
        }

        public Criteria andGuaranteeFeeFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("guarantee_fee_flag not between", value1, value2, "guaranteeFeeFlag");
            return (Criteria) this;
        }

        public Criteria andAccountManageFeeIsNull() {
            addCriterion("account_manage_fee is null");
            return (Criteria) this;
        }

        public Criteria andAccountManageFeeIsNotNull() {
            addCriterion("account_manage_fee is not null");
            return (Criteria) this;
        }

        public Criteria andAccountManageFeeEqualTo(BigDecimal value) {
            addCriterion("account_manage_fee =", value, "accountManageFee");
            return (Criteria) this;
        }

        public Criteria andAccountManageFeeNotEqualTo(BigDecimal value) {
            addCriterion("account_manage_fee <>", value, "accountManageFee");
            return (Criteria) this;
        }

        public Criteria andAccountManageFeeGreaterThan(BigDecimal value) {
            addCriterion("account_manage_fee >", value, "accountManageFee");
            return (Criteria) this;
        }

        public Criteria andAccountManageFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("account_manage_fee >=", value, "accountManageFee");
            return (Criteria) this;
        }

        public Criteria andAccountManageFeeLessThan(BigDecimal value) {
            addCriterion("account_manage_fee <", value, "accountManageFee");
            return (Criteria) this;
        }

        public Criteria andAccountManageFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("account_manage_fee <=", value, "accountManageFee");
            return (Criteria) this;
        }

        public Criteria andAccountManageFeeIn(List<BigDecimal> values) {
            addCriterion("account_manage_fee in", values, "accountManageFee");
            return (Criteria) this;
        }

        public Criteria andAccountManageFeeNotIn(List<BigDecimal> values) {
            addCriterion("account_manage_fee not in", values, "accountManageFee");
            return (Criteria) this;
        }

        public Criteria andAccountManageFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account_manage_fee between", value1, value2, "accountManageFee");
            return (Criteria) this;
        }

        public Criteria andAccountManageFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account_manage_fee not between", value1, value2, "accountManageFee");
            return (Criteria) this;
        }

        public Criteria andRepaymentStyleIsNull() {
            addCriterion("repayment_style is null");
            return (Criteria) this;
        }

        public Criteria andRepaymentStyleIsNotNull() {
            addCriterion("repayment_style is not null");
            return (Criteria) this;
        }

        public Criteria andRepaymentStyleEqualTo(String value) {
            addCriterion("repayment_style =", value, "repaymentStyle");
            return (Criteria) this;
        }

        public Criteria andRepaymentStyleNotEqualTo(String value) {
            addCriterion("repayment_style <>", value, "repaymentStyle");
            return (Criteria) this;
        }

        public Criteria andRepaymentStyleGreaterThan(String value) {
            addCriterion("repayment_style >", value, "repaymentStyle");
            return (Criteria) this;
        }

        public Criteria andRepaymentStyleGreaterThanOrEqualTo(String value) {
            addCriterion("repayment_style >=", value, "repaymentStyle");
            return (Criteria) this;
        }

        public Criteria andRepaymentStyleLessThan(String value) {
            addCriterion("repayment_style <", value, "repaymentStyle");
            return (Criteria) this;
        }

        public Criteria andRepaymentStyleLessThanOrEqualTo(String value) {
            addCriterion("repayment_style <=", value, "repaymentStyle");
            return (Criteria) this;
        }

        public Criteria andRepaymentStyleLike(String value) {
            addCriterion("repayment_style like", value, "repaymentStyle");
            return (Criteria) this;
        }

        public Criteria andRepaymentStyleNotLike(String value) {
            addCriterion("repayment_style not like", value, "repaymentStyle");
            return (Criteria) this;
        }

        public Criteria andRepaymentStyleIn(List<String> values) {
            addCriterion("repayment_style in", values, "repaymentStyle");
            return (Criteria) this;
        }

        public Criteria andRepaymentStyleNotIn(List<String> values) {
            addCriterion("repayment_style not in", values, "repaymentStyle");
            return (Criteria) this;
        }

        public Criteria andRepaymentStyleBetween(String value1, String value2) {
            addCriterion("repayment_style between", value1, value2, "repaymentStyle");
            return (Criteria) this;
        }

        public Criteria andRepaymentStyleNotBetween(String value1, String value2) {
            addCriterion("repayment_style not between", value1, value2, "repaymentStyle");
            return (Criteria) this;
        }

        public Criteria andServiceProvinceIdIsNull() {
            addCriterion("service_province_id is null");
            return (Criteria) this;
        }

        public Criteria andServiceProvinceIdIsNotNull() {
            addCriterion("service_province_id is not null");
            return (Criteria) this;
        }

        public Criteria andServiceProvinceIdEqualTo(Integer value) {
            addCriterion("service_province_id =", value, "serviceProvinceId");
            return (Criteria) this;
        }

        public Criteria andServiceProvinceIdNotEqualTo(Integer value) {
            addCriterion("service_province_id <>", value, "serviceProvinceId");
            return (Criteria) this;
        }

        public Criteria andServiceProvinceIdGreaterThan(Integer value) {
            addCriterion("service_province_id >", value, "serviceProvinceId");
            return (Criteria) this;
        }

        public Criteria andServiceProvinceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("service_province_id >=", value, "serviceProvinceId");
            return (Criteria) this;
        }

        public Criteria andServiceProvinceIdLessThan(Integer value) {
            addCriterion("service_province_id <", value, "serviceProvinceId");
            return (Criteria) this;
        }

        public Criteria andServiceProvinceIdLessThanOrEqualTo(Integer value) {
            addCriterion("service_province_id <=", value, "serviceProvinceId");
            return (Criteria) this;
        }

        public Criteria andServiceProvinceIdIn(List<Integer> values) {
            addCriterion("service_province_id in", values, "serviceProvinceId");
            return (Criteria) this;
        }

        public Criteria andServiceProvinceIdNotIn(List<Integer> values) {
            addCriterion("service_province_id not in", values, "serviceProvinceId");
            return (Criteria) this;
        }

        public Criteria andServiceProvinceIdBetween(Integer value1, Integer value2) {
            addCriterion("service_province_id between", value1, value2, "serviceProvinceId");
            return (Criteria) this;
        }

        public Criteria andServiceProvinceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("service_province_id not between", value1, value2, "serviceProvinceId");
            return (Criteria) this;
        }

        public Criteria andSysFlagIsNull() {
            addCriterion("sys_flag is null");
            return (Criteria) this;
        }

        public Criteria andSysFlagIsNotNull() {
            addCriterion("sys_flag is not null");
            return (Criteria) this;
        }

        public Criteria andSysFlagEqualTo(Integer value) {
            addCriterion("sys_flag =", value, "sysFlag");
            return (Criteria) this;
        }

        public Criteria andSysFlagNotEqualTo(Integer value) {
            addCriterion("sys_flag <>", value, "sysFlag");
            return (Criteria) this;
        }

        public Criteria andSysFlagGreaterThan(Integer value) {
            addCriterion("sys_flag >", value, "sysFlag");
            return (Criteria) this;
        }

        public Criteria andSysFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("sys_flag >=", value, "sysFlag");
            return (Criteria) this;
        }

        public Criteria andSysFlagLessThan(Integer value) {
            addCriterion("sys_flag <", value, "sysFlag");
            return (Criteria) this;
        }

        public Criteria andSysFlagLessThanOrEqualTo(Integer value) {
            addCriterion("sys_flag <=", value, "sysFlag");
            return (Criteria) this;
        }

        public Criteria andSysFlagIn(List<Integer> values) {
            addCriterion("sys_flag in", values, "sysFlag");
            return (Criteria) this;
        }

        public Criteria andSysFlagNotIn(List<Integer> values) {
            addCriterion("sys_flag not in", values, "sysFlag");
            return (Criteria) this;
        }

        public Criteria andSysFlagBetween(Integer value1, Integer value2) {
            addCriterion("sys_flag between", value1, value2, "sysFlag");
            return (Criteria) this;
        }

        public Criteria andSysFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("sys_flag not between", value1, value2, "sysFlag");
            return (Criteria) this;
        }

        public Criteria andBranchOfficeIsNull() {
            addCriterion("branch_office is null");
            return (Criteria) this;
        }

        public Criteria andBranchOfficeIsNotNull() {
            addCriterion("branch_office is not null");
            return (Criteria) this;
        }

        public Criteria andBranchOfficeEqualTo(String value) {
            addCriterion("branch_office =", value, "branchOffice");
            return (Criteria) this;
        }

        public Criteria andBranchOfficeNotEqualTo(String value) {
            addCriterion("branch_office <>", value, "branchOffice");
            return (Criteria) this;
        }

        public Criteria andBranchOfficeGreaterThan(String value) {
            addCriterion("branch_office >", value, "branchOffice");
            return (Criteria) this;
        }

        public Criteria andBranchOfficeGreaterThanOrEqualTo(String value) {
            addCriterion("branch_office >=", value, "branchOffice");
            return (Criteria) this;
        }

        public Criteria andBranchOfficeLessThan(String value) {
            addCriterion("branch_office <", value, "branchOffice");
            return (Criteria) this;
        }

        public Criteria andBranchOfficeLessThanOrEqualTo(String value) {
            addCriterion("branch_office <=", value, "branchOffice");
            return (Criteria) this;
        }

        public Criteria andBranchOfficeLike(String value) {
            addCriterion("branch_office like", value, "branchOffice");
            return (Criteria) this;
        }

        public Criteria andBranchOfficeNotLike(String value) {
            addCriterion("branch_office not like", value, "branchOffice");
            return (Criteria) this;
        }

        public Criteria andBranchOfficeIn(List<String> values) {
            addCriterion("branch_office in", values, "branchOffice");
            return (Criteria) this;
        }

        public Criteria andBranchOfficeNotIn(List<String> values) {
            addCriterion("branch_office not in", values, "branchOffice");
            return (Criteria) this;
        }

        public Criteria andBranchOfficeBetween(String value1, String value2) {
            addCriterion("branch_office between", value1, value2, "branchOffice");
            return (Criteria) this;
        }

        public Criteria andBranchOfficeNotBetween(String value1, String value2) {
            addCriterion("branch_office not between", value1, value2, "branchOffice");
            return (Criteria) this;
        }

        public Criteria andRegionOfficeIsNull() {
            addCriterion("region_office is null");
            return (Criteria) this;
        }

        public Criteria andRegionOfficeIsNotNull() {
            addCriterion("region_office is not null");
            return (Criteria) this;
        }

        public Criteria andRegionOfficeEqualTo(String value) {
            addCriterion("region_office =", value, "regionOffice");
            return (Criteria) this;
        }

        public Criteria andRegionOfficeNotEqualTo(String value) {
            addCriterion("region_office <>", value, "regionOffice");
            return (Criteria) this;
        }

        public Criteria andRegionOfficeGreaterThan(String value) {
            addCriterion("region_office >", value, "regionOffice");
            return (Criteria) this;
        }

        public Criteria andRegionOfficeGreaterThanOrEqualTo(String value) {
            addCriterion("region_office >=", value, "regionOffice");
            return (Criteria) this;
        }

        public Criteria andRegionOfficeLessThan(String value) {
            addCriterion("region_office <", value, "regionOffice");
            return (Criteria) this;
        }

        public Criteria andRegionOfficeLessThanOrEqualTo(String value) {
            addCriterion("region_office <=", value, "regionOffice");
            return (Criteria) this;
        }

        public Criteria andRegionOfficeLike(String value) {
            addCriterion("region_office like", value, "regionOffice");
            return (Criteria) this;
        }

        public Criteria andRegionOfficeNotLike(String value) {
            addCriterion("region_office not like", value, "regionOffice");
            return (Criteria) this;
        }

        public Criteria andRegionOfficeIn(List<String> values) {
            addCriterion("region_office in", values, "regionOffice");
            return (Criteria) this;
        }

        public Criteria andRegionOfficeNotIn(List<String> values) {
            addCriterion("region_office not in", values, "regionOffice");
            return (Criteria) this;
        }

        public Criteria andRegionOfficeBetween(String value1, String value2) {
            addCriterion("region_office between", value1, value2, "regionOffice");
            return (Criteria) this;
        }

        public Criteria andRegionOfficeNotBetween(String value1, String value2) {
            addCriterion("region_office not between", value1, value2, "regionOffice");
            return (Criteria) this;
        }

        public Criteria andReadFlagIsNull() {
            addCriterion("read_flag is null");
            return (Criteria) this;
        }

        public Criteria andReadFlagIsNotNull() {
            addCriterion("read_flag is not null");
            return (Criteria) this;
        }

        public Criteria andReadFlagEqualTo(Integer value) {
            addCriterion("read_flag =", value, "readFlag");
            return (Criteria) this;
        }

        public Criteria andReadFlagNotEqualTo(Integer value) {
            addCriterion("read_flag <>", value, "readFlag");
            return (Criteria) this;
        }

        public Criteria andReadFlagGreaterThan(Integer value) {
            addCriterion("read_flag >", value, "readFlag");
            return (Criteria) this;
        }

        public Criteria andReadFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("read_flag >=", value, "readFlag");
            return (Criteria) this;
        }

        public Criteria andReadFlagLessThan(Integer value) {
            addCriterion("read_flag <", value, "readFlag");
            return (Criteria) this;
        }

        public Criteria andReadFlagLessThanOrEqualTo(Integer value) {
            addCriterion("read_flag <=", value, "readFlag");
            return (Criteria) this;
        }

        public Criteria andReadFlagIn(List<Integer> values) {
            addCriterion("read_flag in", values, "readFlag");
            return (Criteria) this;
        }

        public Criteria andReadFlagNotIn(List<Integer> values) {
            addCriterion("read_flag not in", values, "readFlag");
            return (Criteria) this;
        }

        public Criteria andReadFlagBetween(Integer value1, Integer value2) {
            addCriterion("read_flag between", value1, value2, "readFlag");
            return (Criteria) this;
        }

        public Criteria andReadFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("read_flag not between", value1, value2, "readFlag");
            return (Criteria) this;
        }

        public Criteria andRepaymentLockFlagIsNull() {
            addCriterion("repayment_lock_flag is null");
            return (Criteria) this;
        }

        public Criteria andRepaymentLockFlagIsNotNull() {
            addCriterion("repayment_lock_flag is not null");
            return (Criteria) this;
        }

        public Criteria andRepaymentLockFlagEqualTo(Integer value) {
            addCriterion("repayment_lock_flag =", value, "repaymentLockFlag");
            return (Criteria) this;
        }

        public Criteria andRepaymentLockFlagNotEqualTo(Integer value) {
            addCriterion("repayment_lock_flag <>", value, "repaymentLockFlag");
            return (Criteria) this;
        }

        public Criteria andRepaymentLockFlagGreaterThan(Integer value) {
            addCriterion("repayment_lock_flag >", value, "repaymentLockFlag");
            return (Criteria) this;
        }

        public Criteria andRepaymentLockFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("repayment_lock_flag >=", value, "repaymentLockFlag");
            return (Criteria) this;
        }

        public Criteria andRepaymentLockFlagLessThan(Integer value) {
            addCriterion("repayment_lock_flag <", value, "repaymentLockFlag");
            return (Criteria) this;
        }

        public Criteria andRepaymentLockFlagLessThanOrEqualTo(Integer value) {
            addCriterion("repayment_lock_flag <=", value, "repaymentLockFlag");
            return (Criteria) this;
        }

        public Criteria andRepaymentLockFlagIn(List<Integer> values) {
            addCriterion("repayment_lock_flag in", values, "repaymentLockFlag");
            return (Criteria) this;
        }

        public Criteria andRepaymentLockFlagNotIn(List<Integer> values) {
            addCriterion("repayment_lock_flag not in", values, "repaymentLockFlag");
            return (Criteria) this;
        }

        public Criteria andRepaymentLockFlagBetween(Integer value1, Integer value2) {
            addCriterion("repayment_lock_flag between", value1, value2, "repaymentLockFlag");
            return (Criteria) this;
        }

        public Criteria andRepaymentLockFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("repayment_lock_flag not between", value1, value2, "repaymentLockFlag");
            return (Criteria) this;
        }

        public Criteria andStockFlagIsNull() {
            addCriterion("stock_flag is null");
            return (Criteria) this;
        }

        public Criteria andStockFlagIsNotNull() {
            addCriterion("stock_flag is not null");
            return (Criteria) this;
        }

        public Criteria andStockFlagEqualTo(Integer value) {
            addCriterion("stock_flag =", value, "stockFlag");
            return (Criteria) this;
        }

        public Criteria andStockFlagNotEqualTo(Integer value) {
            addCriterion("stock_flag <>", value, "stockFlag");
            return (Criteria) this;
        }

        public Criteria andStockFlagGreaterThan(Integer value) {
            addCriterion("stock_flag >", value, "stockFlag");
            return (Criteria) this;
        }

        public Criteria andStockFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("stock_flag >=", value, "stockFlag");
            return (Criteria) this;
        }

        public Criteria andStockFlagLessThan(Integer value) {
            addCriterion("stock_flag <", value, "stockFlag");
            return (Criteria) this;
        }

        public Criteria andStockFlagLessThanOrEqualTo(Integer value) {
            addCriterion("stock_flag <=", value, "stockFlag");
            return (Criteria) this;
        }

        public Criteria andStockFlagIn(List<Integer> values) {
            addCriterion("stock_flag in", values, "stockFlag");
            return (Criteria) this;
        }

        public Criteria andStockFlagNotIn(List<Integer> values) {
            addCriterion("stock_flag not in", values, "stockFlag");
            return (Criteria) this;
        }

        public Criteria andStockFlagBetween(Integer value1, Integer value2) {
            addCriterion("stock_flag between", value1, value2, "stockFlag");
            return (Criteria) this;
        }

        public Criteria andStockFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("stock_flag not between", value1, value2, "stockFlag");
            return (Criteria) this;
        }

        public Criteria andSignContractFlagIsNull() {
            addCriterion("sign_contract_flag is null");
            return (Criteria) this;
        }

        public Criteria andSignContractFlagIsNotNull() {
            addCriterion("sign_contract_flag is not null");
            return (Criteria) this;
        }

        public Criteria andSignContractFlagEqualTo(Integer value) {
            addCriterion("sign_contract_flag =", value, "signContractFlag");
            return (Criteria) this;
        }

        public Criteria andSignContractFlagNotEqualTo(Integer value) {
            addCriterion("sign_contract_flag <>", value, "signContractFlag");
            return (Criteria) this;
        }

        public Criteria andSignContractFlagGreaterThan(Integer value) {
            addCriterion("sign_contract_flag >", value, "signContractFlag");
            return (Criteria) this;
        }

        public Criteria andSignContractFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("sign_contract_flag >=", value, "signContractFlag");
            return (Criteria) this;
        }

        public Criteria andSignContractFlagLessThan(Integer value) {
            addCriterion("sign_contract_flag <", value, "signContractFlag");
            return (Criteria) this;
        }

        public Criteria andSignContractFlagLessThanOrEqualTo(Integer value) {
            addCriterion("sign_contract_flag <=", value, "signContractFlag");
            return (Criteria) this;
        }

        public Criteria andSignContractFlagIn(List<Integer> values) {
            addCriterion("sign_contract_flag in", values, "signContractFlag");
            return (Criteria) this;
        }

        public Criteria andSignContractFlagNotIn(List<Integer> values) {
            addCriterion("sign_contract_flag not in", values, "signContractFlag");
            return (Criteria) this;
        }

        public Criteria andSignContractFlagBetween(Integer value1, Integer value2) {
            addCriterion("sign_contract_flag between", value1, value2, "signContractFlag");
            return (Criteria) this;
        }

        public Criteria andSignContractFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("sign_contract_flag not between", value1, value2, "signContractFlag");
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

        public Criteria andTerraceServiceFeeIsNull() {
            addCriterion("terrace_service_fee is null");
            return (Criteria) this;
        }

        public Criteria andTerraceServiceFeeIsNotNull() {
            addCriterion("terrace_service_fee is not null");
            return (Criteria) this;
        }

        public Criteria andTerraceServiceFeeEqualTo(BigDecimal value) {
            addCriterion("terrace_service_fee =", value, "terraceServiceFee");
            return (Criteria) this;
        }

        public Criteria andTerraceServiceFeeNotEqualTo(BigDecimal value) {
            addCriterion("terrace_service_fee <>", value, "terraceServiceFee");
            return (Criteria) this;
        }

        public Criteria andTerraceServiceFeeGreaterThan(BigDecimal value) {
            addCriterion("terrace_service_fee >", value, "terraceServiceFee");
            return (Criteria) this;
        }

        public Criteria andTerraceServiceFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("terrace_service_fee >=", value, "terraceServiceFee");
            return (Criteria) this;
        }

        public Criteria andTerraceServiceFeeLessThan(BigDecimal value) {
            addCriterion("terrace_service_fee <", value, "terraceServiceFee");
            return (Criteria) this;
        }

        public Criteria andTerraceServiceFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("terrace_service_fee <=", value, "terraceServiceFee");
            return (Criteria) this;
        }

        public Criteria andTerraceServiceFeeIn(List<BigDecimal> values) {
            addCriterion("terrace_service_fee in", values, "terraceServiceFee");
            return (Criteria) this;
        }

        public Criteria andTerraceServiceFeeNotIn(List<BigDecimal> values) {
            addCriterion("terrace_service_fee not in", values, "terraceServiceFee");
            return (Criteria) this;
        }

        public Criteria andTerraceServiceFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("terrace_service_fee between", value1, value2, "terraceServiceFee");
            return (Criteria) this;
        }

        public Criteria andTerraceServiceFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("terrace_service_fee not between", value1, value2, "terraceServiceFee");
            return (Criteria) this;
        }

        public Criteria andConsultServiceFeeIsNull() {
            addCriterion("consult_service_fee is null");
            return (Criteria) this;
        }

        public Criteria andConsultServiceFeeIsNotNull() {
            addCriterion("consult_service_fee is not null");
            return (Criteria) this;
        }

        public Criteria andConsultServiceFeeEqualTo(BigDecimal value) {
            addCriterion("consult_service_fee =", value, "consultServiceFee");
            return (Criteria) this;
        }

        public Criteria andConsultServiceFeeNotEqualTo(BigDecimal value) {
            addCriterion("consult_service_fee <>", value, "consultServiceFee");
            return (Criteria) this;
        }

        public Criteria andConsultServiceFeeGreaterThan(BigDecimal value) {
            addCriterion("consult_service_fee >", value, "consultServiceFee");
            return (Criteria) this;
        }

        public Criteria andConsultServiceFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("consult_service_fee >=", value, "consultServiceFee");
            return (Criteria) this;
        }

        public Criteria andConsultServiceFeeLessThan(BigDecimal value) {
            addCriterion("consult_service_fee <", value, "consultServiceFee");
            return (Criteria) this;
        }

        public Criteria andConsultServiceFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("consult_service_fee <=", value, "consultServiceFee");
            return (Criteria) this;
        }

        public Criteria andConsultServiceFeeIn(List<BigDecimal> values) {
            addCriterion("consult_service_fee in", values, "consultServiceFee");
            return (Criteria) this;
        }

        public Criteria andConsultServiceFeeNotIn(List<BigDecimal> values) {
            addCriterion("consult_service_fee not in", values, "consultServiceFee");
            return (Criteria) this;
        }

        public Criteria andConsultServiceFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("consult_service_fee between", value1, value2, "consultServiceFee");
            return (Criteria) this;
        }

        public Criteria andConsultServiceFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("consult_service_fee not between", value1, value2, "consultServiceFee");
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