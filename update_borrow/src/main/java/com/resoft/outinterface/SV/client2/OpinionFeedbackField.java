package com.resoft.outinterface.SV.client2;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpinionFeedbackField {//意⻅见反馈信息
    @JsonProperty(value = "C_Grade")
     private String cGrade  ; //评分
    @JsonProperty(value = "C_Evaluate")
    private String cEvaluate  ;//评价
    @JsonProperty(value = "C_detailedDescription")
    private String cdetailedDescription  ; //详细描述
    @JsonProperty(value = "work_id")
    private String workId;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }
    public String getcGrade() {
        return cGrade;
    }

    public void setcGrade(String cGrade) {
        this.cGrade = cGrade;
    }

    public String getcEvaluate() {
        return cEvaluate;
    }

    public void setcEvaluate(String cEvaluate) {
        this.cEvaluate = cEvaluate;
    }

    public String getCdetailedDescription() {
        return cdetailedDescription;
    }

    public void setCdetailedDescription(String cdetailedDescription) {
        this.cdetailedDescription = cdetailedDescription;
    }
}
