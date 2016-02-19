package com.bizvision.dpf.persistence;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IPersistence {

	@WebMethod
	public void doSaveTaskAllocator(String url);
	
	public String[] getAllTaskAllocator();
}
