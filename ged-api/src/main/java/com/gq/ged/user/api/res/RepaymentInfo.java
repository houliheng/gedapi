package com.gq.ged.user.api.res;

/**
 * Created by Levi on 2018/5/28.
 */
public class RepaymentInfo  implements Comparable<RepaymentInfo>{

    //    period   paydate   payAmount    //    期数  支付日期   支付金额(list列表)
    private String period;
    private String paydate;
    private String payAmount;


    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPaydate() {
        return paydate;
    }

    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    @Override
    public int compareTo(RepaymentInfo o) {
        Integer i = Integer.parseInt(this.period);
        Integer j = Integer.parseInt(o.period);
        return i.compareTo(j);
    }
}
