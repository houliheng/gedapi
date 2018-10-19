package com.resoft.outinterface.hr.service;

import javax.jws.WebService;

@WebService(targetNamespace="http://resoft.controller.application.com/")
public interface HRService{
		public <T>String PersonAdd(String xml,Class<T> objClass);
		public <T>String PersonEdit(String xml,Class<T> objClass);
		public <T>String PersonDelete(String xml,Class<T> objClass);
		public <T>String PersonLogic(String xml,Class<T> objClass);
}