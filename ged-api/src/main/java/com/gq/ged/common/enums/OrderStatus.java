package com.gq.ged.common.enums;

/**
 * Created by wyq_tomorrow on 2017/8/23.
 */
public enum OrderStatus {
    ZLWSZ(100, "资料完善中", "资料完善中", "S0"), SHZ(110, "审核中", "审核中", "S10"),
    SHTG(120, "审核通过", "审核通过", "S30"), CBZ(130, "筹标中", "审核中",
            "S20"), FKCG(139,"待提现","放款成功","S40"),YCTX(140, "提现中", "提现处理中", "S40"), YTX(150,
            "已提现", "已提现", "S41"), HKING(160, "正在还款",
                "还款中", "S50"), YYQ(170, "已逾期", "已逾期", "S94"), YHQ(180, "已 还清", "完成", "S90"), YQYH(190, "逾期已还", "完成",
            "S90"), SHWTG(200, "审核未通过", "审核未通过", "S91"), LB(210, "流标",
            "流标", "S92"),YE(0,"首页","首页","S90"),FQSQ(220,"放弃申请","放弃申请","S90");
    private int code;
    private String feStatusDesc;
    private String backStatusDesc;
    private String feStatus;

    OrderStatus(int code, String backStatusDesc, String feStatusDesc, String feStatus) {
        this.code = code;
        this.feStatusDesc = feStatusDesc;
        this.backStatusDesc = backStatusDesc;
        this.feStatus = feStatus;

    }

    public static OrderStatus resove(int code) {
        switch (code) {
            case 100:
                return ZLWSZ;
            case 110:
                return SHZ;
            case 120:
                return SHTG;
            case 130:
                return CBZ;
            case 139:
                return FKCG;
            case 140:
                return YCTX;
            case 150:
                return YTX;
            case 160:
                return HKING;
            case 170:
                return YYQ;
            case 180:
                return YHQ;
            case 190:
                return YQYH;
            case 200:
                return SHWTG;
            case 210:
                return LB;
            case 220:
                return FQSQ;
            case 0:
                return YE;
            default:
                return null;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getFeStatusDesc() {
        return feStatusDesc;
    }

    public void setFeStatusDesc(String feStatusDesc) {
        this.feStatusDesc = feStatusDesc;
    }

    public String getBackStatusDesc() {
        return backStatusDesc;
    }

    public void setBackStatusDesc(String backStatusDesc) {
        this.backStatusDesc = backStatusDesc;
    }

    public String getFeStatus() {
        return feStatus;
    }

    public void setFeStatus(String feStatus) {
        this.feStatus = feStatus;
    }
}
