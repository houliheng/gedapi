package com.resoft.outinterface.utils;

public interface DispatchInterface { 
	public <T>T getDispatchedInterface(int interfaceName,Class<T> objClass,String applyNo,String xml)throws Exception;
}
