package com.gq.ged.account.controller.res.v2;

import java.io.Serializable;

public class PageResForm implements Serializable{
    private String redirectUrl;

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
