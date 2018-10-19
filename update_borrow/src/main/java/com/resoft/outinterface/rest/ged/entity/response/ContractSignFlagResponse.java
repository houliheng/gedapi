package com.resoft.outinterface.rest.ged.entity.response;

import java.io.Serializable;

/**
 * 更新合同签署信息response.
 *
 * @author SeppSong
 */
public class ContractSignFlagResponse implements Serializable {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
