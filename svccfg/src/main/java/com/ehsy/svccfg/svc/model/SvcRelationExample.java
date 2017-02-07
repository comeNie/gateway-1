package com.ehsy.svccfg.svc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SvcRelationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SvcRelationExample() {
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
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSvcCodeMasterIsNull() {
            addCriterion("SVC_CODE_MASTER is null");
            return (Criteria) this;
        }

        public Criteria andSvcCodeMasterIsNotNull() {
            addCriterion("SVC_CODE_MASTER is not null");
            return (Criteria) this;
        }

        public Criteria andSvcCodeMasterEqualTo(String value) {
            addCriterion("SVC_CODE_MASTER =", value, "svcCodeMaster");
            return (Criteria) this;
        }

        public Criteria andSvcCodeMasterNotEqualTo(String value) {
            addCriterion("SVC_CODE_MASTER <>", value, "svcCodeMaster");
            return (Criteria) this;
        }

        public Criteria andSvcCodeMasterGreaterThan(String value) {
            addCriterion("SVC_CODE_MASTER >", value, "svcCodeMaster");
            return (Criteria) this;
        }

        public Criteria andSvcCodeMasterGreaterThanOrEqualTo(String value) {
            addCriterion("SVC_CODE_MASTER >=", value, "svcCodeMaster");
            return (Criteria) this;
        }

        public Criteria andSvcCodeMasterLessThan(String value) {
            addCriterion("SVC_CODE_MASTER <", value, "svcCodeMaster");
            return (Criteria) this;
        }

        public Criteria andSvcCodeMasterLessThanOrEqualTo(String value) {
            addCriterion("SVC_CODE_MASTER <=", value, "svcCodeMaster");
            return (Criteria) this;
        }

        public Criteria andSvcCodeMasterLike(String value) {
            addCriterion("SVC_CODE_MASTER like", value, "svcCodeMaster");
            return (Criteria) this;
        }

        public Criteria andSvcCodeMasterNotLike(String value) {
            addCriterion("SVC_CODE_MASTER not like", value, "svcCodeMaster");
            return (Criteria) this;
        }

        public Criteria andSvcCodeMasterIn(List<String> values) {
            addCriterion("SVC_CODE_MASTER in", values, "svcCodeMaster");
            return (Criteria) this;
        }

        public Criteria andSvcCodeMasterNotIn(List<String> values) {
            addCriterion("SVC_CODE_MASTER not in", values, "svcCodeMaster");
            return (Criteria) this;
        }

        public Criteria andSvcCodeMasterBetween(String value1, String value2) {
            addCriterion("SVC_CODE_MASTER between", value1, value2, "svcCodeMaster");
            return (Criteria) this;
        }

        public Criteria andSvcCodeMasterNotBetween(String value1, String value2) {
            addCriterion("SVC_CODE_MASTER not between", value1, value2, "svcCodeMaster");
            return (Criteria) this;
        }

        public Criteria andSvcCodeBranchIsNull() {
            addCriterion("SVC_CODE_BRANCH is null");
            return (Criteria) this;
        }

        public Criteria andSvcCodeBranchIsNotNull() {
            addCriterion("SVC_CODE_BRANCH is not null");
            return (Criteria) this;
        }

        public Criteria andSvcCodeBranchEqualTo(String value) {
            addCriterion("SVC_CODE_BRANCH =", value, "svcCodeBranch");
            return (Criteria) this;
        }

        public Criteria andSvcCodeBranchNotEqualTo(String value) {
            addCriterion("SVC_CODE_BRANCH <>", value, "svcCodeBranch");
            return (Criteria) this;
        }

        public Criteria andSvcCodeBranchGreaterThan(String value) {
            addCriterion("SVC_CODE_BRANCH >", value, "svcCodeBranch");
            return (Criteria) this;
        }

        public Criteria andSvcCodeBranchGreaterThanOrEqualTo(String value) {
            addCriterion("SVC_CODE_BRANCH >=", value, "svcCodeBranch");
            return (Criteria) this;
        }

        public Criteria andSvcCodeBranchLessThan(String value) {
            addCriterion("SVC_CODE_BRANCH <", value, "svcCodeBranch");
            return (Criteria) this;
        }

        public Criteria andSvcCodeBranchLessThanOrEqualTo(String value) {
            addCriterion("SVC_CODE_BRANCH <=", value, "svcCodeBranch");
            return (Criteria) this;
        }

        public Criteria andSvcCodeBranchLike(String value) {
            addCriterion("SVC_CODE_BRANCH like", value, "svcCodeBranch");
            return (Criteria) this;
        }

        public Criteria andSvcCodeBranchNotLike(String value) {
            addCriterion("SVC_CODE_BRANCH not like", value, "svcCodeBranch");
            return (Criteria) this;
        }

        public Criteria andSvcCodeBranchIn(List<String> values) {
            addCriterion("SVC_CODE_BRANCH in", values, "svcCodeBranch");
            return (Criteria) this;
        }

        public Criteria andSvcCodeBranchNotIn(List<String> values) {
            addCriterion("SVC_CODE_BRANCH not in", values, "svcCodeBranch");
            return (Criteria) this;
        }

        public Criteria andSvcCodeBranchBetween(String value1, String value2) {
            addCriterion("SVC_CODE_BRANCH between", value1, value2, "svcCodeBranch");
            return (Criteria) this;
        }

        public Criteria andSvcCodeBranchNotBetween(String value1, String value2) {
            addCriterion("SVC_CODE_BRANCH not between", value1, value2, "svcCodeBranch");
            return (Criteria) this;
        }

        public Criteria andDegradeIsNull() {
            addCriterion("DEGRADE is null");
            return (Criteria) this;
        }

        public Criteria andDegradeIsNotNull() {
            addCriterion("DEGRADE is not null");
            return (Criteria) this;
        }

        public Criteria andDegradeEqualTo(String value) {
            addCriterion("DEGRADE =", value, "degrade");
            return (Criteria) this;
        }

        public Criteria andDegradeNotEqualTo(String value) {
            addCriterion("DEGRADE <>", value, "degrade");
            return (Criteria) this;
        }

        public Criteria andDegradeGreaterThan(String value) {
            addCriterion("DEGRADE >", value, "degrade");
            return (Criteria) this;
        }

        public Criteria andDegradeGreaterThanOrEqualTo(String value) {
            addCriterion("DEGRADE >=", value, "degrade");
            return (Criteria) this;
        }

        public Criteria andDegradeLessThan(String value) {
            addCriterion("DEGRADE <", value, "degrade");
            return (Criteria) this;
        }

        public Criteria andDegradeLessThanOrEqualTo(String value) {
            addCriterion("DEGRADE <=", value, "degrade");
            return (Criteria) this;
        }

        public Criteria andDegradeLike(String value) {
            addCriterion("DEGRADE like", value, "degrade");
            return (Criteria) this;
        }

        public Criteria andDegradeNotLike(String value) {
            addCriterion("DEGRADE not like", value, "degrade");
            return (Criteria) this;
        }

        public Criteria andDegradeIn(List<String> values) {
            addCriterion("DEGRADE in", values, "degrade");
            return (Criteria) this;
        }

        public Criteria andDegradeNotIn(List<String> values) {
            addCriterion("DEGRADE not in", values, "degrade");
            return (Criteria) this;
        }

        public Criteria andDegradeBetween(String value1, String value2) {
            addCriterion("DEGRADE between", value1, value2, "degrade");
            return (Criteria) this;
        }

        public Criteria andDegradeNotBetween(String value1, String value2) {
            addCriterion("DEGRADE not between", value1, value2, "degrade");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("TYPE =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("TYPE <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("TYPE >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("TYPE >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("TYPE <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("TYPE <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("TYPE like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("TYPE not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("TYPE in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("TYPE not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("TYPE between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("TYPE not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
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