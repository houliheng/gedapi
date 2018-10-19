package com.resoft.Accoutinterface.utils;

public interface AccDispatchInterface {
	public <T>T getDispatchedInterface(int interfaceName,Class<T> objClass,String applyNo,String xml)throws Exception;
}
