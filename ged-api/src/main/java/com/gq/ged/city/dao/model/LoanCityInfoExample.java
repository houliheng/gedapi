package com.gq.ged.city.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoanCityInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LoanCityInfoExample() {
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

        public Criteria andCityidIsNull() {
            addCriterion("cityid is null");
            return (Criteria) this;
        }

        public Criteria andCityidIsNotNull() {
            addCriterion("cityid is not null");
            return (Criteria) this;
        }

        public Criteria andCityidEqualTo(Long value) {
            addCriterion("cityid =", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidNotEqualTo(Long value) {
            addCriterion("cityid <>", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidGreaterThan(Long value) {
            addCriterion("cityid >", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidGreaterThanOrEqualTo(Long value) {
            addCriterion("cityid >=", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidLessThan(Long value) {
            addCriterion("cityid <", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidLessThanOrEqualTo(Long value) {
            addCriterion("cityid <=", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidIn(List<Long> values) {
            addCriterion("cityid in", values, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidNotIn(List<Long> values) {
            addCriterion("cityid not in", values, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidBetween(Long value1, Long value2) {
            addCriterion("cityid between", value1, value2, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidNotBetween(Long value1, Long value2) {
            addCriterion("cityid not between", value1, value2, "cityid");
            return (Criteria) this;
        }

        public Criteria andPcityidIsNull() {
            addCriterion("pcityid is null");
            return (Criteria) this;
        }

        public Criteria andPcityidIsNotNull() {
            addCriterion("pcityid is not null");
            return (Criteria) this;
        }

        public Criteria andPcityidEqualTo(String value) {
            addCriterion("pcityid =", value, "pcityid");
            return (Criteria) this;
        }

        public Criteria andPcityidNotEqualTo(String value) {
            addCriterion("pcityid <>", value, "pcityid");
            return (Criteria) this;
        }

        public Criteria andPcityidGreaterThan(String value) {
            addCriterion("pcityid >", value, "pcityid");
            return (Criteria) this;
        }

        public Criteria andPcityidGreaterThanOrEqualTo(String value) {
            addCriterion("pcityid >=", value, "pcityid");
            return (Criteria) this;
        }

        public Criteria andPcityidLessThan(String value) {
            addCriterion("pcityid <", value, "pcityid");
            return (Criteria) this;
        }

        public Criteria andPcityidLessThanOrEqualTo(String value) {
            addCriterion("pcityid <=", value, "pcityid");
            return (Criteria) this;
        }

        public Criteria andPcityidLike(String value) {
            addCriterion("pcityid like", value, "pcityid");
            return (Criteria) this;
        }

        public Criteria andPcityidNotLike(String value) {
            addCriterion("pcityid not like", value, "pcityid");
            return (Criteria) this;
        }

        public Criteria andPcityidIn(List<String> values) {
            addCriterion("pcityid in", values, "pcityid");
            return (Criteria) this;
        }

        public Criteria andPcityidNotIn(List<String> values) {
            addCriterion("pcityid not in", values, "pcityid");
            return (Criteria) this;
        }

        public Criteria andPcityidBetween(String value1, String value2) {
            addCriterion("pcityid between", value1, value2, "pcityid");
            return (Criteria) this;
        }

        public Criteria andPcityidNotBetween(String value1, String value2) {
            addCriterion("pcityid not between", value1, value2, "pcityid");
            return (Criteria) this;
        }

        public Criteria andPcityidsIsNull() {
            addCriterion("pcityids is null");
            return (Criteria) this;
        }

        public Criteria andPcityidsIsNotNull() {
            addCriterion("pcityids is not null");
            return (Criteria) this;
        }

        public Criteria andPcityidsEqualTo(String value) {
            addCriterion("pcityids =", value, "pcityids");
            return (Criteria) this;
        }

        public Criteria andPcityidsNotEqualTo(String value) {
            addCriterion("pcityids <>", value, "pcityids");
            return (Criteria) this;
        }

        public Criteria andPcityidsGreaterThan(String value) {
            addCriterion("pcityids >", value, "pcityids");
            return (Criteria) this;
        }

        public Criteria andPcityidsGreaterThanOrEqualTo(String value) {
            addCriterion("pcityids >=", value, "pcityids");
            return (Criteria) this;
        }

        public Criteria andPcityidsLessThan(String value) {
            addCriterion("pcityids <", value, "pcityids");
            return (Criteria) this;
        }

        public Criteria andPcityidsLessThanOrEqualTo(String value) {
            addCriterion("pcityids <=", value, "pcityids");
            return (Criteria) this;
        }

        public Criteria andPcityidsLike(String value) {
            addCriterion("pcityids like", value, "pcityids");
            return (Criteria) this;
        }

        public Criteria andPcityidsNotLike(String value) {
            addCriterion("pcityids not like", value, "pcityids");
            return (Criteria) this;
        }

        public Criteria andPcityidsIn(List<String> values) {
            addCriterion("pcityids in", values, "pcityids");
            return (Criteria) this;
        }

        public Criteria andPcityidsNotIn(List<String> values) {
            addCriterion("pcityids not in", values, "pcityids");
            return (Criteria) this;
        }

        public Criteria andPcityidsBetween(String value1, String value2) {
            addCriterion("pcityids between", value1, value2, "pcityids");
            return (Criteria) this;
        }

        public Criteria andPcityidsNotBetween(String value1, String value2) {
            addCriterion("pcityids not between", value1, value2, "pcityids");
            return (Criteria) this;
        }

        public Criteria andCitynameIsNull() {
            addCriterion("cityname is null");
            return (Criteria) this;
        }

        public Criteria andCitynameIsNotNull() {
            addCriterion("cityname is not null");
            return (Criteria) this;
        }

        public Criteria andCitynameEqualTo(String value) {
            addCriterion("cityname =", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameNotEqualTo(String value) {
            addCriterion("cityname <>", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameGreaterThan(String value) {
            addCriterion("cityname >", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameGreaterThanOrEqualTo(String value) {
            addCriterion("cityname >=", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameLessThan(String value) {
            addCriterion("cityname <", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameLessThanOrEqualTo(String value) {
            addCriterion("cityname <=", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameLike(String value) {
            addCriterion("cityname like", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameNotLike(String value) {
            addCriterion("cityname not like", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameIn(List<String> values) {
            addCriterion("cityname in", values, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameNotIn(List<String> values) {
            addCriterion("cityname not in", values, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameBetween(String value1, String value2) {
            addCriterion("cityname between", value1, value2, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameNotBetween(String value1, String value2) {
            addCriterion("cityname not between", value1, value2, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitysortIsNull() {
            addCriterion("citysort is null");
            return (Criteria) this;
        }

        public Criteria andCitysortIsNotNull() {
            addCriterion("citysort is not null");
            return (Criteria) this;
        }

        public Criteria andCitysortEqualTo(String value) {
            addCriterion("citysort =", value, "citysort");
            return (Criteria) this;
        }

        public Criteria andCitysortNotEqualTo(String value) {
            addCriterion("citysort <>", value, "citysort");
            return (Criteria) this;
        }

        public Criteria andCitysortGreaterThan(String value) {
            addCriterion("citysort >", value, "citysort");
            return (Criteria) this;
        }

        public Criteria andCitysortGreaterThanOrEqualTo(String value) {
            addCriterion("citysort >=", value, "citysort");
            return (Criteria) this;
        }

        public Criteria andCitysortLessThan(String value) {
            addCriterion("citysort <", value, "citysort");
            return (Criteria) this;
        }

        public Criteria andCitysortLessThanOrEqualTo(String value) {
            addCriterion("citysort <=", value, "citysort");
            return (Criteria) this;
        }

        public Criteria andCitysortLike(String value) {
            addCriterion("citysort like", value, "citysort");
            return (Criteria) this;
        }

        public Criteria andCitysortNotLike(String value) {
            addCriterion("citysort not like", value, "citysort");
            return (Criteria) this;
        }

        public Criteria andCitysortIn(List<String> values) {
            addCriterion("citysort in", values, "citysort");
            return (Criteria) this;
        }

        public Criteria andCitysortNotIn(List<String> values) {
            addCriterion("citysort not in", values, "citysort");
            return (Criteria) this;
        }

        public Criteria andCitysortBetween(String value1, String value2) {
            addCriterion("citysort between", value1, value2, "citysort");
            return (Criteria) this;
        }

        public Criteria andCitysortNotBetween(String value1, String value2) {
            addCriterion("citysort not between", value1, value2, "citysort");
            return (Criteria) this;
        }

        public Criteria andCitycodeIsNull() {
            addCriterion("citycode is null");
            return (Criteria) this;
        }

        public Criteria andCitycodeIsNotNull() {
            addCriterion("citycode is not null");
            return (Criteria) this;
        }

        public Criteria andCitycodeEqualTo(String value) {
            addCriterion("citycode =", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotEqualTo(String value) {
            addCriterion("citycode <>", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeGreaterThan(String value) {
            addCriterion("citycode >", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeGreaterThanOrEqualTo(String value) {
            addCriterion("citycode >=", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeLessThan(String value) {
            addCriterion("citycode <", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeLessThanOrEqualTo(String value) {
            addCriterion("citycode <=", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeLike(String value) {
            addCriterion("citycode like", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotLike(String value) {
            addCriterion("citycode not like", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeIn(List<String> values) {
            addCriterion("citycode in", values, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotIn(List<String> values) {
            addCriterion("citycode not in", values, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeBetween(String value1, String value2) {
            addCriterion("citycode between", value1, value2, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotBetween(String value1, String value2) {
            addCriterion("citycode not between", value1, value2, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitytypeIsNull() {
            addCriterion("citytype is null");
            return (Criteria) this;
        }

        public Criteria andCitytypeIsNotNull() {
            addCriterion("citytype is not null");
            return (Criteria) this;
        }

        public Criteria andCitytypeEqualTo(String value) {
            addCriterion("citytype =", value, "citytype");
            return (Criteria) this;
        }

        public Criteria andCitytypeNotEqualTo(String value) {
            addCriterion("citytype <>", value, "citytype");
            return (Criteria) this;
        }

        public Criteria andCitytypeGreaterThan(String value) {
            addCriterion("citytype >", value, "citytype");
            return (Criteria) this;
        }

        public Criteria andCitytypeGreaterThanOrEqualTo(String value) {
            addCriterion("citytype >=", value, "citytype");
            return (Criteria) this;
        }

        public Criteria andCitytypeLessThan(String value) {
            addCriterion("citytype <", value, "citytype");
            return (Criteria) this;
        }

        public Criteria andCitytypeLessThanOrEqualTo(String value) {
            addCriterion("citytype <=", value, "citytype");
            return (Criteria) this;
        }

        public Criteria andCitytypeLike(String value) {
            addCriterion("citytype like", value, "citytype");
            return (Criteria) this;
        }

        public Criteria andCitytypeNotLike(String value) {
            addCriterion("citytype not like", value, "citytype");
            return (Criteria) this;
        }

        public Criteria andCitytypeIn(List<String> values) {
            addCriterion("citytype in", values, "citytype");
            return (Criteria) this;
        }

        public Criteria andCitytypeNotIn(List<String> values) {
            addCriterion("citytype not in", values, "citytype");
            return (Criteria) this;
        }

        public Criteria andCitytypeBetween(String value1, String value2) {
            addCriterion("citytype between", value1, value2, "citytype");
            return (Criteria) this;
        }

        public Criteria andCitytypeNotBetween(String value1, String value2) {
            addCriterion("citytype not between", value1, value2, "citytype");
            return (Criteria) this;
        }

        public Criteria andCityFullNameIsNull() {
            addCriterion("city_full_name is null");
            return (Criteria) this;
        }

        public Criteria andCityFullNameIsNotNull() {
            addCriterion("city_full_name is not null");
            return (Criteria) this;
        }

        public Criteria andCityFullNameEqualTo(String value) {
            addCriterion("city_full_name =", value, "cityFullName");
            return (Criteria) this;
        }

        public Criteria andCityFullNameNotEqualTo(String value) {
            addCriterion("city_full_name <>", value, "cityFullName");
            return (Criteria) this;
        }

        public Criteria andCityFullNameGreaterThan(String value) {
            addCriterion("city_full_name >", value, "cityFullName");
            return (Criteria) this;
        }

        public Criteria andCityFullNameGreaterThanOrEqualTo(String value) {
            addCriterion("city_full_name >=", value, "cityFullName");
            return (Criteria) this;
        }

        public Criteria andCityFullNameLessThan(String value) {
            addCriterion("city_full_name <", value, "cityFullName");
            return (Criteria) this;
        }

        public Criteria andCityFullNameLessThanOrEqualTo(String value) {
            addCriterion("city_full_name <=", value, "cityFullName");
            return (Criteria) this;
        }

        public Criteria andCityFullNameLike(String value) {
            addCriterion("city_full_name like", value, "cityFullName");
            return (Criteria) this;
        }

        public Criteria andCityFullNameNotLike(String value) {
            addCriterion("city_full_name not like", value, "cityFullName");
            return (Criteria) this;
        }

        public Criteria andCityFullNameIn(List<String> values) {
            addCriterion("city_full_name in", values, "cityFullName");
            return (Criteria) this;
        }

        public Criteria andCityFullNameNotIn(List<String> values) {
            addCriterion("city_full_name not in", values, "cityFullName");
            return (Criteria) this;
        }

        public Criteria andCityFullNameBetween(String value1, String value2) {
            addCriterion("city_full_name between", value1, value2, "cityFullName");
            return (Criteria) this;
        }

        public Criteria andCityFullNameNotBetween(String value1, String value2) {
            addCriterion("city_full_name not between", value1, value2, "cityFullName");
            return (Criteria) this;
        }

        public Criteria andIsVirtualRegionIsNull() {
            addCriterion("is_virtual_region is null");
            return (Criteria) this;
        }

        public Criteria andIsVirtualRegionIsNotNull() {
            addCriterion("is_virtual_region is not null");
            return (Criteria) this;
        }

        public Criteria andIsVirtualRegionEqualTo(String value) {
            addCriterion("is_virtual_region =", value, "isVirtualRegion");
            return (Criteria) this;
        }

        public Criteria andIsVirtualRegionNotEqualTo(String value) {
            addCriterion("is_virtual_region <>", value, "isVirtualRegion");
            return (Criteria) this;
        }

        public Criteria andIsVirtualRegionGreaterThan(String value) {
            addCriterion("is_virtual_region >", value, "isVirtualRegion");
            return (Criteria) this;
        }

        public Criteria andIsVirtualRegionGreaterThanOrEqualTo(String value) {
            addCriterion("is_virtual_region >=", value, "isVirtualRegion");
            return (Criteria) this;
        }

        public Criteria andIsVirtualRegionLessThan(String value) {
            addCriterion("is_virtual_region <", value, "isVirtualRegion");
            return (Criteria) this;
        }

        public Criteria andIsVirtualRegionLessThanOrEqualTo(String value) {
            addCriterion("is_virtual_region <=", value, "isVirtualRegion");
            return (Criteria) this;
        }

        public Criteria andIsVirtualRegionLike(String value) {
            addCriterion("is_virtual_region like", value, "isVirtualRegion");
            return (Criteria) this;
        }

        public Criteria andIsVirtualRegionNotLike(String value) {
            addCriterion("is_virtual_region not like", value, "isVirtualRegion");
            return (Criteria) this;
        }

        public Criteria andIsVirtualRegionIn(List<String> values) {
            addCriterion("is_virtual_region in", values, "isVirtualRegion");
            return (Criteria) this;
        }

        public Criteria andIsVirtualRegionNotIn(List<String> values) {
            addCriterion("is_virtual_region not in", values, "isVirtualRegion");
            return (Criteria) this;
        }

        public Criteria andIsVirtualRegionBetween(String value1, String value2) {
            addCriterion("is_virtual_region between", value1, value2, "isVirtualRegion");
            return (Criteria) this;
        }

        public Criteria andIsVirtualRegionNotBetween(String value1, String value2) {
            addCriterion("is_virtual_region not between", value1, value2, "isVirtualRegion");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(String value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(String value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(String value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(String value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(String value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(String value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLike(String value) {
            addCriterion("del_flag like", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotLike(String value) {
            addCriterion("del_flag not like", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<String> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<String> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(String value1, String value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(String value1, String value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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