package com.gq.ged.common.utils;


/**
 * 认证token信息工具类
 * Created by Cougar on 17-02-27.
 */
public class TokenInfo {

    private byte flag = TokenUtil.GUEST_FLAG;
    private String uid;
    private String did;
    private int crtTime;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TokenInfo{");
        sb.append("flag=").append(flag);
        sb.append(", uid=").append(uid);
        sb.append(", did=").append(did);
        sb.append(", crtTime=").append(crtTime);
        sb.append('}');
        return sb.toString();
    }

    public byte getFlag() {
        return flag;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uin) {
        this.uid = uin;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public int getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(int crtTime) {
        this.crtTime = crtTime;
    }


}
