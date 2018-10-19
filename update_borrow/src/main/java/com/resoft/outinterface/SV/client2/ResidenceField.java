package com.resoft.outinterface.SV.client2;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResidenceField {
    @JsonProperty(value = "P_residenceAddress")
    private String presidenceAddress;//详细地址
    @JsonProperty(value = "P_residenceDateilAddress")
    private String presidenceDateilAddress;//实际居住地详细地址
    @JsonProperty(value = "work_id")
    private String workId;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }
    public String getPresidenceAddress() {
        return presidenceAddress;
    }

    public void setPresidenceAddress(String presidenceAddress) {
        this.presidenceAddress = presidenceAddress;
    }

    public String getPresidenceDateilAddress() {
        return presidenceDateilAddress;
    }

    public void setPresidenceDateilAddress(String presidenceDateilAddress) {
        this.presidenceDateilAddress = presidenceDateilAddress;
    }
}
