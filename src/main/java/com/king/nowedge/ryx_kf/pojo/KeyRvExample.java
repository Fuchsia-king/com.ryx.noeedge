package com.king.nowedge.ryx_kf.pojo;

import java.util.ArrayList;
import java.util.List;

public class KeyRvExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public KeyRvExample() {
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

        public Criteria andKey1IsNull() {
            addCriterion("key1 is null");
            return (Criteria) this;
        }

        public Criteria andKey1IsNotNull() {
            addCriterion("key1 is not null");
            return (Criteria) this;
        }

        public Criteria andKey1EqualTo(String value) {
            addCriterion("key1 =", value, "key1");
            return (Criteria) this;
        }

        public Criteria andKey1NotEqualTo(String value) {
            addCriterion("key1 <>", value, "key1");
            return (Criteria) this;
        }

        public Criteria andKey1GreaterThan(String value) {
            addCriterion("key1 >", value, "key1");
            return (Criteria) this;
        }

        public Criteria andKey1GreaterThanOrEqualTo(String value) {
            addCriterion("key1 >=", value, "key1");
            return (Criteria) this;
        }

        public Criteria andKey1LessThan(String value) {
            addCriterion("key1 <", value, "key1");
            return (Criteria) this;
        }

        public Criteria andKey1LessThanOrEqualTo(String value) {
            addCriterion("key1 <=", value, "key1");
            return (Criteria) this;
        }

        public Criteria andKey1Like(String value) {
            addCriterion("key1 like", value, "key1");
            return (Criteria) this;
        }

        public Criteria andKey1NotLike(String value) {
            addCriterion("key1 not like", value, "key1");
            return (Criteria) this;
        }

        public Criteria andKey1In(List<String> values) {
            addCriterion("key1 in", values, "key1");
            return (Criteria) this;
        }

        public Criteria andKey1NotIn(List<String> values) {
            addCriterion("key1 not in", values, "key1");
            return (Criteria) this;
        }

        public Criteria andKey1Between(String value1, String value2) {
            addCriterion("key1 between", value1, value2, "key1");
            return (Criteria) this;
        }

        public Criteria andKey1NotBetween(String value1, String value2) {
            addCriterion("key1 not between", value1, value2, "key1");
            return (Criteria) this;
        }

        public Criteria andRkeyIsNull() {
            addCriterion("rkey is null");
            return (Criteria) this;
        }

        public Criteria andRkeyIsNotNull() {
            addCriterion("rkey is not null");
            return (Criteria) this;
        }

        public Criteria andRkeyEqualTo(String value) {
            addCriterion("rkey =", value, "rkey");
            return (Criteria) this;
        }

        public Criteria andRkeyNotEqualTo(String value) {
            addCriterion("rkey <>", value, "rkey");
            return (Criteria) this;
        }

        public Criteria andRkeyGreaterThan(String value) {
            addCriterion("rkey >", value, "rkey");
            return (Criteria) this;
        }

        public Criteria andRkeyGreaterThanOrEqualTo(String value) {
            addCriterion("rkey >=", value, "rkey");
            return (Criteria) this;
        }

        public Criteria andRkeyLessThan(String value) {
            addCriterion("rkey <", value, "rkey");
            return (Criteria) this;
        }

        public Criteria andRkeyLessThanOrEqualTo(String value) {
            addCriterion("rkey <=", value, "rkey");
            return (Criteria) this;
        }

        public Criteria andRkeyLike(String value) {
            addCriterion("rkey like", value, "rkey");
            return (Criteria) this;
        }

        public Criteria andRkeyNotLike(String value) {
            addCriterion("rkey not like", value, "rkey");
            return (Criteria) this;
        }

        public Criteria andRkeyIn(List<String> values) {
            addCriterion("rkey in", values, "rkey");
            return (Criteria) this;
        }

        public Criteria andRkeyNotIn(List<String> values) {
            addCriterion("rkey not in", values, "rkey");
            return (Criteria) this;
        }

        public Criteria andRkeyBetween(String value1, String value2) {
            addCriterion("rkey between", value1, value2, "rkey");
            return (Criteria) this;
        }

        public Criteria andRkeyNotBetween(String value1, String value2) {
            addCriterion("rkey not between", value1, value2, "rkey");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("sort not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andDisplayIsNull() {
            addCriterion("display is null");
            return (Criteria) this;
        }

        public Criteria andDisplayIsNotNull() {
            addCriterion("display is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayEqualTo(Integer value) {
            addCriterion("display =", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayNotEqualTo(Integer value) {
            addCriterion("display <>", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayGreaterThan(Integer value) {
            addCriterion("display >", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayGreaterThanOrEqualTo(Integer value) {
            addCriterion("display >=", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayLessThan(Integer value) {
            addCriterion("display <", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayLessThanOrEqualTo(Integer value) {
            addCriterion("display <=", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayIn(List<Integer> values) {
            addCriterion("display in", values, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayNotIn(List<Integer> values) {
            addCriterion("display not in", values, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayBetween(Integer value1, Integer value2) {
            addCriterion("display between", value1, value2, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayNotBetween(Integer value1, Integer value2) {
            addCriterion("display not between", value1, value2, "display");
            return (Criteria) this;
        }

        public Criteria andIdeletedIsNull() {
            addCriterion("ideleted is null");
            return (Criteria) this;
        }

        public Criteria andIdeletedIsNotNull() {
            addCriterion("ideleted is not null");
            return (Criteria) this;
        }

        public Criteria andIdeletedEqualTo(Integer value) {
            addCriterion("ideleted =", value, "ideleted");
            return (Criteria) this;
        }

        public Criteria andIdeletedNotEqualTo(Integer value) {
            addCriterion("ideleted <>", value, "ideleted");
            return (Criteria) this;
        }

        public Criteria andIdeletedGreaterThan(Integer value) {
            addCriterion("ideleted >", value, "ideleted");
            return (Criteria) this;
        }

        public Criteria andIdeletedGreaterThanOrEqualTo(Integer value) {
            addCriterion("ideleted >=", value, "ideleted");
            return (Criteria) this;
        }

        public Criteria andIdeletedLessThan(Integer value) {
            addCriterion("ideleted <", value, "ideleted");
            return (Criteria) this;
        }

        public Criteria andIdeletedLessThanOrEqualTo(Integer value) {
            addCriterion("ideleted <=", value, "ideleted");
            return (Criteria) this;
        }

        public Criteria andIdeletedIn(List<Integer> values) {
            addCriterion("ideleted in", values, "ideleted");
            return (Criteria) this;
        }

        public Criteria andIdeletedNotIn(List<Integer> values) {
            addCriterion("ideleted not in", values, "ideleted");
            return (Criteria) this;
        }

        public Criteria andIdeletedBetween(Integer value1, Integer value2) {
            addCriterion("ideleted between", value1, value2, "ideleted");
            return (Criteria) this;
        }

        public Criteria andIdeletedNotBetween(Integer value1, Integer value2) {
            addCriterion("ideleted not between", value1, value2, "ideleted");
            return (Criteria) this;
        }

        public Criteria andRkey1IsNull() {
            addCriterion("rkey1 is null");
            return (Criteria) this;
        }

        public Criteria andRkey1IsNotNull() {
            addCriterion("rkey1 is not null");
            return (Criteria) this;
        }

        public Criteria andRkey1EqualTo(String value) {
            addCriterion("rkey1 =", value, "rkey1");
            return (Criteria) this;
        }

        public Criteria andRkey1NotEqualTo(String value) {
            addCriterion("rkey1 <>", value, "rkey1");
            return (Criteria) this;
        }

        public Criteria andRkey1GreaterThan(String value) {
            addCriterion("rkey1 >", value, "rkey1");
            return (Criteria) this;
        }

        public Criteria andRkey1GreaterThanOrEqualTo(String value) {
            addCriterion("rkey1 >=", value, "rkey1");
            return (Criteria) this;
        }

        public Criteria andRkey1LessThan(String value) {
            addCriterion("rkey1 <", value, "rkey1");
            return (Criteria) this;
        }

        public Criteria andRkey1LessThanOrEqualTo(String value) {
            addCriterion("rkey1 <=", value, "rkey1");
            return (Criteria) this;
        }

        public Criteria andRkey1Like(String value) {
            addCriterion("rkey1 like", value, "rkey1");
            return (Criteria) this;
        }

        public Criteria andRkey1NotLike(String value) {
            addCriterion("rkey1 not like", value, "rkey1");
            return (Criteria) this;
        }

        public Criteria andRkey1In(List<String> values) {
            addCriterion("rkey1 in", values, "rkey1");
            return (Criteria) this;
        }

        public Criteria andRkey1NotIn(List<String> values) {
            addCriterion("rkey1 not in", values, "rkey1");
            return (Criteria) this;
        }

        public Criteria andRkey1Between(String value1, String value2) {
            addCriterion("rkey1 between", value1, value2, "rkey1");
            return (Criteria) this;
        }

        public Criteria andRkey1NotBetween(String value1, String value2) {
            addCriterion("rkey1 not between", value1, value2, "rkey1");
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