package com.resoft.common.utils;

import java.util.HashMap;

/**
 * The result for ajax request.
 * Status: 0 - fail; 1 - success; 999 - timeout.
 */
public class AjaxView extends HashMap<String, Object>
{
	private static final long serialVersionUID = 1L;

	public AjaxView()
	{
		super();
	}

	public AjaxView setSuccess(boolean success)
	{
		put("status", success ? "1" : "0");
		return this;
	}
	
	public AjaxView setSuccess()
	{
		put("status", "1");
		return this;
	}
	
	public AjaxView setFailed()
	{
		put("status", "0");
		return this;
	}
	
	public AjaxView setTimeout()
	{
		put("status", "999");
		return this;
	}
	
	public AjaxView setData(Object data)
	{
		put("data", data);
		return this;
	}
	
	public AjaxView setStatus(String statusCode)
	{
		put("status", statusCode);
		return this;
	}
	
	public AjaxView setMessage(String message)
	{
		put("message", message);
		return this;
	}
}
