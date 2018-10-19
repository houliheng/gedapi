package com.gq.ged.order.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GedOrderTagsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public GedOrderTagsExample() {
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

        public Criteria andRefIdIsNull() {
            addCriterion("ref_id is null");
            return (Criteria) this;
        }

        public Criteria andRefIdIsNotNull() {
            addCriterion("ref_id is not null");
            return (Criteria) this;
        }

        public Criteria andRefIdEqualTo(Long value) {
            addCriterion("ref_id =", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdNotEqualTo(Long value) {
            addCriterion("ref_id <>", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdGreaterThan(Long value) {
            addCriterion("ref_id >", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ref_id >=", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdLessThan(Long value) {
            addCriterion("ref_id <", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdLessThanOrEqualTo(Long value) {
            addCriterion("ref_id <=", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdIn(List<Long> values) {
            addCriterion("ref_id in", values, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdNotIn(List<Long> values) {
            addCriterion("ref_id not in", values, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdBetween(Long value1, Long value2) {
            addCriterion("ref_id between", value1, value2, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdNotBetween(Long value1, Long value2) {
            addCriterion("ref_id not between", value1, value2, "refId");
            return (Criteria) this;
        }

        public Criteria andRefTypeIsNull() {
            addCriterion("ref_type is null");
            return (Criteria) this;
        }

        public Criteria andRefTypeIsNotNull() {
            addCriterion("ref_type is not null");
            return (Criteria) this;
        }

        public Criteria andRefTypeEqualTo(Integer value) {
            addCriterion("ref_type =", value, "refType");
            return (Criteria) this;
        }

        public Criteria andRefTypeNotEqualTo(Integer value) {
            addCriterion("ref_type <>", value, "refType");
            return (Criteria) this;
        }

        public Criteria andRefTypeGreaterThan(Integer value) {
            addCriterion("ref_type >", value, "refType");
            return (Criteria) this;
        }

        public Criteria andRefTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ref_type >=", value, "refType");
            return (Criteria) this;
        }

        public Criteria andRefTypeLessThan(Integer value) {
            addCriterion("ref_type <", value, "refType");
            return (Criteria) this;
        }

        public Criteria andRefTypeLessThanOrEqualTo(Integer value) {
            addCriterion("ref_type <=", value, "refType");
            return (Criteria) this;
        }

        public Criteria andRefTypeIn(List<Integer> values) {
            addCriterion("ref_type in", values, "refType");
            return (Criteria) this;
        }

        public Criteria andRefTypeNotIn(List<Integer> values) {
            addCriterion("ref_type not in", values, "refType");
            return (Criteria) this;
        }

        public Criteria andRefTypeBetween(Integer value1, Integer value2) {
            addCriterion("ref_type between", value1, value2, "refType");
            return (Criteria) this;
        }

        public Criteria andRefTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("ref_type not between", value1, value2, "refType");
            return (Criteria) this;
        }

        public Criteria andTagKeyIsNull() {
            addCriterion("tag_key is null");
            return (Criteria) this;
        }

        public Criteria andTagKeyIsNotNull() {
            addCriterion("tag_key is not null");
            return (Criteria) this;
        }

        public Criteria andTagKeyEqualTo(Integer value) {
            addCriterion("tag_key =", value, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyNotEqualTo(Integer value) {
            addCriterion("tag_key <>", value, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyGreaterThan(Integer value) {
            addCriterion("tag_key >", value, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyGreaterThanOrEqualTo(Integer value) {
            addCriterion("tag_key >=", value, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyLessThan(Integer value) {
            addCriterion("tag_key <", value, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyLessThanOrEqualTo(Integer value) {
            addCriterion("tag_key <=", value, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyIn(List<Integer> values) {
            addCriterion("tag_key in", values, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyNotIn(List<Integer> values) {
            addCriterion("tag_key not in", values, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyBetween(Integer value1, Integer value2) {
            addCriterion("tag_key between", value1, value2, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyNotBetween(Integer value1, Integer value2) {
            addCriterion("tag_key not between", value1, value2, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagValueIsNull() {
            addCriterion("tag_value is null");
            return (Criteria) this;
        }

        public Criteria andTagValueIsNotNull() {
            addCriterion("tag_value is not null");
            return (Criteria) this;
        }

        public Criteria andTagValueEqualTo(String value) {
            addCriterion("tag_value =", value, "tagValue");
            return (Criteria) this;
        }

        public Criteria andTagValueNotEqualTo(String value) {
            addCriterion("tag_value <>", value, "tagValue");
            return (Criteria) this;
        }

        public Criteria andTagValueGreaterThan(String value) {
            addCriterion("tag_value >", value, "tagValue");
            return (Criteria) this;
        }

        public Criteria andTagValueGreaterThanOrEqualTo(String value) {
            addCriterion("tag_value >=", value, "tagValue");
            return (Criteria) this;
        }

        public Criteria andTagValueLessThan(String value) {
            addCriterion("tag_value <", value, "tagValue");
            return (Criteria) this;
        }

        public Criteria andTagValueLessThanOrEqualTo(String value) {
            addCriterion("tag_value <=", value, "tagValue");
            return (Criteria) this;
        }

        public Criteria andTagValueLike(String value) {
            addCriterion("tag_value like", value, "tagValue");
            return (Criteria) this;
        }

        public Criteria andTagValueNotLike(String value) {
            addCriterion("tag_value not like", value, "tagValue");
            return (Criteria) this;
        }

        public Criteria andTagValueIn(List<String> values) {
            addCriterion("tag_value in", values, "tagValue");
            return (Criteria) this;
        }

        public Criteria andTagValueNotIn(List<String> values) {
            addCriterion("tag_value not in", values, "tagValue");
            return (Criteria) this;
        }

        public Criteria andTagValueBetween(String value1, String value2) {
            addCriterion("tag_value between", value1, value2, "tagValue");
            return (Criteria) this;
        }

        public Criteria andTagValueNotBetween(String value1, String value2) {
            addCriterion("tag_value not between", value1, value2, "tagValue");
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