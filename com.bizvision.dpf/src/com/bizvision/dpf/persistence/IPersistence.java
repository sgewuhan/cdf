package com.bizvision.dpf.persistence;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IPersistence {

	@WebMethod
	public String listTaskAllocators();

	@WebMethod
	public void updateTaskAllocatorState(
			@WebParam(name = "taskallocatorUrl") String taskallocatorUrl,
			@WebParam(name = "state") int state);
}
