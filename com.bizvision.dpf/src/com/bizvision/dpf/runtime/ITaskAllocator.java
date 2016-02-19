package com.bizvision.dpf.runtime;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.bizvision.dpf.server.ProcessorType;
import com.bizvision.dpf.server.Result;
import com.bizvision.dpf.server.Task;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ITaskAllocator {
	@WebMethod
	public ProcessorType[] getRegisterProcessorTypes();

	@WebMethod
	public ProcessorType[] getProcessorTypes(
			@WebParam(name = "sourceType") String sourceType,
			@WebParam(name = "targetType") String targetType);

	@WebMethod
	public Result allocateByProcesserType(
			@WebParam(name = "processerType") ProcessorType processerType,
			@WebParam(name = "task") Task task) throws Exception;

	@WebMethod
	public Result allocateByProcesserTypes(
			@WebParam(name = "processerType") ProcessorType[] processerType,
			@WebParam(name = "task") Task task) throws Exception;

	@WebMethod
	public Result allocate(@WebParam(name = "sourceType") String sourceType,
			@WebParam(name = "targetType") String targetType,
			@WebParam(name = "task") Task task) throws Exception;

	@WebMethod
	public String ping(@WebParam(name = "value") String value);
}
