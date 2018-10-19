package com.resoft.outinterface.rest.ged.entity.request;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.binding.soap.Soap11;
import org.junit.Test;

import com.resoft.common.utils.JsonTransferUtils;

/**
* @author guoshaohua
* @version 2018年4月19日 下午4:36:27
* 
*/
public class ContractNoListRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<String> contractNo;
	
	public ContractNoListRequest(){
		
	}
	public List<String> getContractNo() {
		return contractNo;
	}
	public void setContractNo(List<String> contractNo) {
		this.contractNo = contractNo;
	}
	
}

