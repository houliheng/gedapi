package com.resoft.outinterface.SV.server;

import javax.jws.WebService;

@WebService
public interface SVServeInterface {
	public String SVInvestInfoResponse(String svr) throws Exception;

	public String SVInformationMatchResponse(String message) throws Exception;
}