package com.resoft.accounting.bankAccountStatement.entity;

import java.io.Serializable;

public class AccountNameForm implements Serializable {

    private static final long serialVersionUID = -5337417703867176927L;
    private String accountName;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
