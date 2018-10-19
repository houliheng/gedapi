package com.resoft.multds.postloan.pushdatatopl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.creditViewBook.entity.creditAssets.CreditAssets;
import com.resoft.credit.mortgageOtherInfo.entity.MortgageOtherInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 资产信息DAO接口
 * @author zhaohuakui
 * @version 2016-06-23
 */
@MyBatisDao("postloanCreditAssetDao")
public interface PlCreditAssetDao extends CrudDao<MortgageOtherInfo> {

	public void insertCreditAssetToPl(@Param("creditAssetsList")List<CreditAssets> creditAssetsList);
}