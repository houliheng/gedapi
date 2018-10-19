package com.gq.ged.order.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderVerifyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderVerifyExample() {
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

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(Long value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(Long value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(Long value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(Long value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Long value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<Long> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<Long> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(Long value1, Long value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(Long value1, Long value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andCompanyInfoIsNull() {
            addCriterion("company_info is null");
            return (Criteria) this;
        }

        public Criteria andCompanyInfoIsNotNull() {
            addCriterion("company_info is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyInfoEqualTo(String value) {
            addCriterion("company_info =", value, "companyInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyInfoNotEqualTo(String value) {
            addCriterion("company_info <>", value, "companyInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyInfoGreaterThan(String value) {
            addCriterion("company_info >", value, "companyInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyInfoGreaterThanOrEqualTo(String value) {
            addCriterion("company_info >=", value, "companyInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyInfoLessThan(String value) {
            addCriterion("company_info <", value, "companyInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyInfoLessThanOrEqualTo(String value) {
            addCriterion("company_info <=", value, "companyInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyInfoLike(String value) {
            addCriterion("company_info like", value, "companyInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyInfoNotLike(String value) {
            addCriterion("company_info not like", value, "companyInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyInfoIn(List<String> values) {
            addCriterion("company_info in", values, "companyInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyInfoNotIn(List<String> values) {
            addCriterion("company_info not in", values, "companyInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyInfoBetween(String value1, String value2) {
            addCriterion("company_info between", value1, value2, "companyInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyInfoNotBetween(String value1, String value2) {
            addCriterion("company_info not between", value1, value2, "companyInfo");
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

        public Criteria andLoanPurposeEqualTo(String value) {
            addCriterion("loan_purpose =", value, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeNotEqualTo(String value) {
            addCriterion("loan_purpose <>", value, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeGreaterThan(String value) {
            addCriterion("loan_purpose >", value, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeGreaterThanOrEqualTo(String value) {
            addCriterion("loan_purpose >=", value, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeLessThan(String value) {
            addCriterion("loan_purpose <", value, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeLessThanOrEqualTo(String value) {
            addCriterion("loan_purpose <=", value, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeLike(String value) {
            addCriterion("loan_purpose like", value, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeNotLike(String value) {
            addCriterion("loan_purpose not like", value, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeIn(List<String> values) {
            addCriterion("loan_purpose in", values, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeNotIn(List<String> values) {
            addCriterion("loan_purpose not in", values, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeBetween(String value1, String value2) {
            addCriterion("loan_purpose between", value1, value2, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andLoanPurposeNotBetween(String value1, String value2) {
            addCriterion("loan_purpose not between", value1, value2, "loanPurpose");
            return (Criteria) this;
        }

        public Criteria andCompanyProductInfoIsNull() {
            addCriterion("company_product_info is null");
            return (Criteria) this;
        }

        public Criteria andCompanyProductInfoIsNotNull() {
            addCriterion("company_product_info is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyProductInfoEqualTo(String value) {
            addCriterion("company_product_info =", value, "companyProductInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyProductInfoNotEqualTo(String value) {
            addCriterion("company_product_info <>", value, "companyProductInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyProductInfoGreaterThan(String value) {
            addCriterion("company_product_info >", value, "companyProductInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyProductInfoGreaterThanOrEqualTo(String value) {
            addCriterion("company_product_info >=", value, "companyProductInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyProductInfoLessThan(String value) {
            addCriterion("company_product_info <", value, "companyProductInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyProductInfoLessThanOrEqualTo(String value) {
            addCriterion("company_product_info <=", value, "companyProductInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyProductInfoLike(String value) {
            addCriterion("company_product_info like", value, "companyProductInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyProductInfoNotLike(String value) {
            addCriterion("company_product_info not like", value, "companyProductInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyProductInfoIn(List<String> values) {
            addCriterion("company_product_info in", values, "companyProductInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyProductInfoNotIn(List<String> values) {
            addCriterion("company_product_info not in", values, "companyProductInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyProductInfoBetween(String value1, String value2) {
            addCriterion("company_product_info between", value1, value2, "companyProductInfo");
            return (Criteria) this;
        }

        public Criteria andCompanyProductInfoNotBetween(String value1, String value2) {
            addCriterion("company_product_info not between", value1, value2, "companyProductInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerSanctionIsNull() {
            addCriterion("borrower_sanction is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerSanctionIsNotNull() {
            addCriterion("borrower_sanction is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerSanctionEqualTo(String value) {
            addCriterion("borrower_sanction =", value, "borrowerSanction");
            return (Criteria) this;
        }

        public Criteria andBorrowerSanctionNotEqualTo(String value) {
            addCriterion("borrower_sanction <>", value, "borrowerSanction");
            return (Criteria) this;
        }

        public Criteria andBorrowerSanctionGreaterThan(String value) {
            addCriterion("borrower_sanction >", value, "borrowerSanction");
            return (Criteria) this;
        }

        public Criteria andBorrowerSanctionGreaterThanOrEqualTo(String value) {
            addCriterion("borrower_sanction >=", value, "borrowerSanction");
            return (Criteria) this;
        }

        public Criteria andBorrowerSanctionLessThan(String value) {
            addCriterion("borrower_sanction <", value, "borrowerSanction");
            return (Criteria) this;
        }

        public Criteria andBorrowerSanctionLessThanOrEqualTo(String value) {
            addCriterion("borrower_sanction <=", value, "borrowerSanction");
            return (Criteria) this;
        }

        public Criteria andBorrowerSanctionLike(String value) {
            addCriterion("borrower_sanction like", value, "borrowerSanction");
            return (Criteria) this;
        }

        public Criteria andBorrowerSanctionNotLike(String value) {
            addCriterion("borrower_sanction not like", value, "borrowerSanction");
            return (Criteria) this;
        }

        public Criteria andBorrowerSanctionIn(List<String> values) {
            addCriterion("borrower_sanction in", values, "borrowerSanction");
            return (Criteria) this;
        }

        public Criteria andBorrowerSanctionNotIn(List<String> values) {
            addCriterion("borrower_sanction not in", values, "borrowerSanction");
            return (Criteria) this;
        }

        public Criteria andBorrowerSanctionBetween(String value1, String value2) {
            addCriterion("borrower_sanction between", value1, value2, "borrowerSanction");
            return (Criteria) this;
        }

        public Criteria andBorrowerSanctionNotBetween(String value1, String value2) {
            addCriterion("borrower_sanction not between", value1, value2, "borrowerSanction");
            return (Criteria) this;
        }

        public Criteria andBorrowerLitigationIsNull() {
            addCriterion("borrower_litigation is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerLitigationIsNotNull() {
            addCriterion("borrower_litigation is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerLitigationEqualTo(String value) {
            addCriterion("borrower_litigation =", value, "borrowerLitigation");
            return (Criteria) this;
        }

        public Criteria andBorrowerLitigationNotEqualTo(String value) {
            addCriterion("borrower_litigation <>", value, "borrowerLitigation");
            return (Criteria) this;
        }

        public Criteria andBorrowerLitigationGreaterThan(String value) {
            addCriterion("borrower_litigation >", value, "borrowerLitigation");
            return (Criteria) this;
        }

        public Criteria andBorrowerLitigationGreaterThanOrEqualTo(String value) {
            addCriterion("borrower_litigation >=", value, "borrowerLitigation");
            return (Criteria) this;
        }

        public Criteria andBorrowerLitigationLessThan(String value) {
            addCriterion("borrower_litigation <", value, "borrowerLitigation");
            return (Criteria) this;
        }

        public Criteria andBorrowerLitigationLessThanOrEqualTo(String value) {
            addCriterion("borrower_litigation <=", value, "borrowerLitigation");
            return (Criteria) this;
        }

        public Criteria andBorrowerLitigationLike(String value) {
            addCriterion("borrower_litigation like", value, "borrowerLitigation");
            return (Criteria) this;
        }

        public Criteria andBorrowerLitigationNotLike(String value) {
            addCriterion("borrower_litigation not like", value, "borrowerLitigation");
            return (Criteria) this;
        }

        public Criteria andBorrowerLitigationIn(List<String> values) {
            addCriterion("borrower_litigation in", values, "borrowerLitigation");
            return (Criteria) this;
        }

        public Criteria andBorrowerLitigationNotIn(List<String> values) {
            addCriterion("borrower_litigation not in", values, "borrowerLitigation");
            return (Criteria) this;
        }

        public Criteria andBorrowerLitigationBetween(String value1, String value2) {
            addCriterion("borrower_litigation between", value1, value2, "borrowerLitigation");
            return (Criteria) this;
        }

        public Criteria andBorrowerLitigationNotBetween(String value1, String value2) {
            addCriterion("borrower_litigation not between", value1, value2, "borrowerLitigation");
            return (Criteria) this;
        }

        public Criteria andBorrowerActInfoIsNull() {
            addCriterion("borrower_act_info is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerActInfoIsNotNull() {
            addCriterion("borrower_act_info is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerActInfoEqualTo(String value) {
            addCriterion("borrower_act_info =", value, "borrowerActInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerActInfoNotEqualTo(String value) {
            addCriterion("borrower_act_info <>", value, "borrowerActInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerActInfoGreaterThan(String value) {
            addCriterion("borrower_act_info >", value, "borrowerActInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerActInfoGreaterThanOrEqualTo(String value) {
            addCriterion("borrower_act_info >=", value, "borrowerActInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerActInfoLessThan(String value) {
            addCriterion("borrower_act_info <", value, "borrowerActInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerActInfoLessThanOrEqualTo(String value) {
            addCriterion("borrower_act_info <=", value, "borrowerActInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerActInfoLike(String value) {
            addCriterion("borrower_act_info like", value, "borrowerActInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerActInfoNotLike(String value) {
            addCriterion("borrower_act_info not like", value, "borrowerActInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerActInfoIn(List<String> values) {
            addCriterion("borrower_act_info in", values, "borrowerActInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerActInfoNotIn(List<String> values) {
            addCriterion("borrower_act_info not in", values, "borrowerActInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerActInfoBetween(String value1, String value2) {
            addCriterion("borrower_act_info between", value1, value2, "borrowerActInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerActInfoNotBetween(String value1, String value2) {
            addCriterion("borrower_act_info not between", value1, value2, "borrowerActInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerDebtInfoIsNull() {
            addCriterion("borrower_debt_info is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerDebtInfoIsNotNull() {
            addCriterion("borrower_debt_info is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerDebtInfoEqualTo(String value) {
            addCriterion("borrower_debt_info =", value, "borrowerDebtInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerDebtInfoNotEqualTo(String value) {
            addCriterion("borrower_debt_info <>", value, "borrowerDebtInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerDebtInfoGreaterThan(String value) {
            addCriterion("borrower_debt_info >", value, "borrowerDebtInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerDebtInfoGreaterThanOrEqualTo(String value) {
            addCriterion("borrower_debt_info >=", value, "borrowerDebtInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerDebtInfoLessThan(String value) {
            addCriterion("borrower_debt_info <", value, "borrowerDebtInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerDebtInfoLessThanOrEqualTo(String value) {
            addCriterion("borrower_debt_info <=", value, "borrowerDebtInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerDebtInfoLike(String value) {
            addCriterion("borrower_debt_info like", value, "borrowerDebtInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerDebtInfoNotLike(String value) {
            addCriterion("borrower_debt_info not like", value, "borrowerDebtInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerDebtInfoIn(List<String> values) {
            addCriterion("borrower_debt_info in", values, "borrowerDebtInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerDebtInfoNotIn(List<String> values) {
            addCriterion("borrower_debt_info not in", values, "borrowerDebtInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerDebtInfoBetween(String value1, String value2) {
            addCriterion("borrower_debt_info between", value1, value2, "borrowerDebtInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowerDebtInfoNotBetween(String value1, String value2) {
            addCriterion("borrower_debt_info not between", value1, value2, "borrowerDebtInfo");
            return (Criteria) this;
        }

        public Criteria andBankLoanInfoIsNull() {
            addCriterion("bank_loan_info is null");
            return (Criteria) this;
        }

        public Criteria andBankLoanInfoIsNotNull() {
            addCriterion("bank_loan_info is not null");
            return (Criteria) this;
        }

        public Criteria andBankLoanInfoEqualTo(String value) {
            addCriterion("bank_loan_info =", value, "bankLoanInfo");
            return (Criteria) this;
        }

        public Criteria andBankLoanInfoNotEqualTo(String value) {
            addCriterion("bank_loan_info <>", value, "bankLoanInfo");
            return (Criteria) this;
        }

        public Criteria andBankLoanInfoGreaterThan(String value) {
            addCriterion("bank_loan_info >", value, "bankLoanInfo");
            return (Criteria) this;
        }

        public Criteria andBankLoanInfoGreaterThanOrEqualTo(String value) {
            addCriterion("bank_loan_info >=", value, "bankLoanInfo");
            return (Criteria) this;
        }

        public Criteria andBankLoanInfoLessThan(String value) {
            addCriterion("bank_loan_info <", value, "bankLoanInfo");
            return (Criteria) this;
        }

        public Criteria andBankLoanInfoLessThanOrEqualTo(String value) {
            addCriterion("bank_loan_info <=", value, "bankLoanInfo");
            return (Criteria) this;
        }

        public Criteria andBankLoanInfoLike(String value) {
            addCriterion("bank_loan_info like", value, "bankLoanInfo");
            return (Criteria) this;
        }

        public Criteria andBankLoanInfoNotLike(String value) {
            addCriterion("bank_loan_info not like", value, "bankLoanInfo");
            return (Criteria) this;
        }

        public Criteria andBankLoanInfoIn(List<String> values) {
            addCriterion("bank_loan_info in", values, "bankLoanInfo");
            return (Criteria) this;
        }

        public Criteria andBankLoanInfoNotIn(List<String> values) {
            addCriterion("bank_loan_info not in", values, "bankLoanInfo");
            return (Criteria) this;
        }

        public Criteria andBankLoanInfoBetween(String value1, String value2) {
            addCriterion("bank_loan_info between", value1, value2, "bankLoanInfo");
            return (Criteria) this;
        }

        public Criteria andBankLoanInfoNotBetween(String value1, String value2) {
            addCriterion("bank_loan_info not between", value1, value2, "bankLoanInfo");
            return (Criteria) this;
        }

        public Criteria andOverdueNumberIsNull() {
            addCriterion("overdue_number is null");
            return (Criteria) this;
        }

        public Criteria andOverdueNumberIsNotNull() {
            addCriterion("overdue_number is not null");
            return (Criteria) this;
        }

        public Criteria andOverdueNumberEqualTo(Integer value) {
            addCriterion("overdue_number =", value, "overdueNumber");
            return (Criteria) this;
        }

        public Criteria andOverdueNumberNotEqualTo(Integer value) {
            addCriterion("overdue_number <>", value, "overdueNumber");
            return (Criteria) this;
        }

        public Criteria andOverdueNumberGreaterThan(Integer value) {
            addCriterion("overdue_number >", value, "overdueNumber");
            return (Criteria) this;
        }

        public Criteria andOverdueNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("overdue_number >=", value, "overdueNumber");
            return (Criteria) this;
        }

        public Criteria andOverdueNumberLessThan(Integer value) {
            addCriterion("overdue_number <", value, "overdueNumber");
            return (Criteria) this;
        }

        public Criteria andOverdueNumberLessThanOrEqualTo(Integer value) {
            addCriterion("overdue_number <=", value, "overdueNumber");
            return (Criteria) this;
        }

        public Criteria andOverdueNumberIn(List<Integer> values) {
            addCriterion("overdue_number in", values, "overdueNumber");
            return (Criteria) this;
        }

        public Criteria andOverdueNumberNotIn(List<Integer> values) {
            addCriterion("overdue_number not in", values, "overdueNumber");
            return (Criteria) this;
        }

        public Criteria andOverdueNumberBetween(Integer value1, Integer value2) {
            addCriterion("overdue_number between", value1, value2, "overdueNumber");
            return (Criteria) this;
        }

        public Criteria andOverdueNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("overdue_number not between", value1, value2, "overdueNumber");
            return (Criteria) this;
        }

        public Criteria andAssetsInfoIsNull() {
            addCriterion("assets_info is null");
            return (Criteria) this;
        }

        public Criteria andAssetsInfoIsNotNull() {
            addCriterion("assets_info is not null");
            return (Criteria) this;
        }

        public Criteria andAssetsInfoEqualTo(String value) {
            addCriterion("assets_info =", value, "assetsInfo");
            return (Criteria) this;
        }

        public Criteria andAssetsInfoNotEqualTo(String value) {
            addCriterion("assets_info <>", value, "assetsInfo");
            return (Criteria) this;
        }

        public Criteria andAssetsInfoGreaterThan(String value) {
            addCriterion("assets_info >", value, "assetsInfo");
            return (Criteria) this;
        }

        public Criteria andAssetsInfoGreaterThanOrEqualTo(String value) {
            addCriterion("assets_info >=", value, "assetsInfo");
            return (Criteria) this;
        }

        public Criteria andAssetsInfoLessThan(String value) {
            addCriterion("assets_info <", value, "assetsInfo");
            return (Criteria) this;
        }

        public Criteria andAssetsInfoLessThanOrEqualTo(String value) {
            addCriterion("assets_info <=", value, "assetsInfo");
            return (Criteria) this;
        }

        public Criteria andAssetsInfoLike(String value) {
            addCriterion("assets_info like", value, "assetsInfo");
            return (Criteria) this;
        }

        public Criteria andAssetsInfoNotLike(String value) {
            addCriterion("assets_info not like", value, "assetsInfo");
            return (Criteria) this;
        }

        public Criteria andAssetsInfoIn(List<String> values) {
            addCriterion("assets_info in", values, "assetsInfo");
            return (Criteria) this;
        }

        public Criteria andAssetsInfoNotIn(List<String> values) {
            addCriterion("assets_info not in", values, "assetsInfo");
            return (Criteria) this;
        }

        public Criteria andAssetsInfoBetween(String value1, String value2) {
            addCriterion("assets_info between", value1, value2, "assetsInfo");
            return (Criteria) this;
        }

        public Criteria andAssetsInfoNotBetween(String value1, String value2) {
            addCriterion("assets_info not between", value1, value2, "assetsInfo");
            return (Criteria) this;
        }

        public Criteria andRepayChanelIsNull() {
            addCriterion("repay_chanel is null");
            return (Criteria) this;
        }

        public Criteria andRepayChanelIsNotNull() {
            addCriterion("repay_chanel is not null");
            return (Criteria) this;
        }

        public Criteria andRepayChanelEqualTo(String value) {
            addCriterion("repay_chanel =", value, "repayChanel");
            return (Criteria) this;
        }

        public Criteria andRepayChanelNotEqualTo(String value) {
            addCriterion("repay_chanel <>", value, "repayChanel");
            return (Criteria) this;
        }

        public Criteria andRepayChanelGreaterThan(String value) {
            addCriterion("repay_chanel >", value, "repayChanel");
            return (Criteria) this;
        }

        public Criteria andRepayChanelGreaterThanOrEqualTo(String value) {
            addCriterion("repay_chanel >=", value, "repayChanel");
            return (Criteria) this;
        }

        public Criteria andRepayChanelLessThan(String value) {
            addCriterion("repay_chanel <", value, "repayChanel");
            return (Criteria) this;
        }

        public Criteria andRepayChanelLessThanOrEqualTo(String value) {
            addCriterion("repay_chanel <=", value, "repayChanel");
            return (Criteria) this;
        }

        public Criteria andRepayChanelLike(String value) {
            addCriterion("repay_chanel like", value, "repayChanel");
            return (Criteria) this;
        }

        public Criteria andRepayChanelNotLike(String value) {
            addCriterion("repay_chanel not like", value, "repayChanel");
            return (Criteria) this;
        }

        public Criteria andRepayChanelIn(List<String> values) {
            addCriterion("repay_chanel in", values, "repayChanel");
            return (Criteria) this;
        }

        public Criteria andRepayChanelNotIn(List<String> values) {
            addCriterion("repay_chanel not in", values, "repayChanel");
            return (Criteria) this;
        }

        public Criteria andRepayChanelBetween(String value1, String value2) {
            addCriterion("repay_chanel between", value1, value2, "repayChanel");
            return (Criteria) this;
        }

        public Criteria andRepayChanelNotBetween(String value1, String value2) {
            addCriterion("repay_chanel not between", value1, value2, "repayChanel");
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