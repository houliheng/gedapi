package com.gq.ged.city.dao.model;

import java.util.Date;

public class LoanCityInfo {
    private Long cityid;

    private String pcityid;

    private String pcityids;

    private String cityname;

    private String citysort;

    private String citycode;

    private String citytype;

    private String cityFullName;

    private String isVirtualRegion;

    private String delFlag;

    private String remark;

    private Date createTime;

    private Date modifyTime;

    public Long getCityid() {
        return cityid;
    }

    public void setCityid(Long cityid) {
        this.cityid = cityid;
    }

    public String getPcityid() {
        return pcityid;
    }

    public void setPcityid(String pcityid) {
        this.pcityid = pcityid == null ? null : pcityid.trim();
    }

    public String getPcityids() {
        return pcityids;
    }

    public void setPcityids(String pcityids) {
        this.pcityids = pcityids == null ? null : pcityids.trim();
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname == null ? null : cityname.trim();
    }

    public String getCitysort() {
        return citysort;
    }

    public void setCitysort(String citysort) {
        this.citysort = citysort == null ? null : citysort.trim();
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode == null ? null : citycode.trim();
    }

    public String getCitytype() {
        return citytype;
    }

    public void setCitytype(String citytype) {
        this.citytype = citytype == null ? null : citytype.trim();
    }

    public String getCityFullName() {
        return cityFullName;
    }

    public void setCityFullName(String cityFullName) {
        this.cityFullName = cityFullName == null ? null : cityFullName.trim();
    }

    public String getIsVirtualRegion() {
        return isVirtualRegion;
    }

    public void setIsVirtualRegion(String isVirtualRegion) {
        this.isVirtualRegion = isVirtualRegion == null ? null : isVirtualRegion.trim();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}