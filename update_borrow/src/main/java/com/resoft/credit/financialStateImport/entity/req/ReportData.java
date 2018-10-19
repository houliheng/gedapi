package com.resoft.credit.financialStateImport.entity.req;


import java.io.Serializable;
import java.util.List;

public class ReportData implements Serializable {

    private  List<FinanceBs>  financeBs ;
   private List<FinancePl> financePl;

    public List<FinanceBs> getFinanceBs() {
        return financeBs;
    }

    public void setFinanceBs(List<FinanceBs> financeBs) {
        this.financeBs = financeBs;
    }

    public List<FinancePl> getFinancePl() {
        return financePl;
    }

    public void setFinancePl(List<FinancePl> financePl) {
        this.financePl = financePl;
    }
}
