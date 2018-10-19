package com.resoft.accounting.checkAccount.service;

import com.resoft.accounting.checkAccount.entity.CheckAccountVerify;
import java.util.List;

/**
 * 查账审核.
 *
 * @author SeppSong
 */
public interface CheckAccountVerifyService {

    /**
     * 查询审核列表
     * @param checkAccountVerify 查账审核条件
     * @return 审核列表
     */
    List<CheckAccountVerify> list(CheckAccountVerify checkAccountVerify);

    /**
     * 查询已审核列表
     * @param checkAccountVerify 查账审核条件
     * @return 审核列表
     */
    List<CheckAccountVerify> listDone(CheckAccountVerify checkAccountVerify);
}
