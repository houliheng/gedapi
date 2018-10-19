package com.resoft.outinterface.themis;

import javax.jws.WebService;




@WebService(targetNamespace="http://service.themis.com/analysis")
public interface ThemisServerInterface {
	public String getAnalysisResult(String arg0);
}
