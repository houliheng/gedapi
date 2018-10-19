package com.resoft.outinterface.rest.SVFinancialToThemis.interfaces;

import org.springframework.web.bind.annotation.RequestBody;

public interface SVFinancialToThemisInterface {
	public String sendFinanceReportToThemis(@RequestBody String json);
}
