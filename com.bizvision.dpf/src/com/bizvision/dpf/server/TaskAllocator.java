package com.bizvision.dpf.server;

import java.net.URL;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.bizvision.dpf.persistence.IPersistence;
import com.bizvision.dpf.persistence.ProcessorType;
import com.bizvision.dpf.processor.IProcessor;
import com.bizvision.dpf.processor.ProcessorService;
import com.bizvision.dpf.processor.Result;
import com.bizvision.dpf.processor.Task;
import com.bizvision.dpf.runtime.IProcessExceptionCode;
import com.bizvision.dpf.runtime.ITaskAllocator;

@WebService(endpointInterface = "com.bizvision.dpf.runtime.ITaskAllocator")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class TaskAllocator implements ITaskAllocator {

	public static final int STATE_ONLINE = 1;

	public static final int STATE_OFFLINE = 0;

	private IPersistence persistence;

	private String url;

	@Override
	public ProcessorType[] getRegisterProcessorTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessorType[] getProcessorTypes(String sourceType,
			String targetType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result allocateByProcesserType(ProcessorType processerType, Task task)
			throws Exception {
		if (processerType == null) {
			throw new Exception(IProcessExceptionCode.NO_PROCESSERTYPE);
		}
		return allocateByProcesserTypes(new ProcessorType[] { processerType },
				task);
	}

	@Override
	public Result allocateByProcesserTypes(ProcessorType[] processerTypes,
			Task task) throws Exception {
		if (processerTypes == null || processerTypes.length == 0) {
			throw new Exception(IProcessExceptionCode.NO_PROCESSERTYPE);
		}
		IProcessor[] processors = getProcessors(processerTypes);
		if (processors.length == 0) {
			throw new Exception(IProcessExceptionCode.NO_REG_PROCESSOR);
		}
		IProcessor processor = lookup(processors);
		if (processor == null) {
			throw new Exception(IProcessExceptionCode.NO_PROCESSOR);
		}
		return processor.execute(task);
	}

	private IProcessor lookup(IProcessor[] processors) throws Exception {
		// TODO Auto-generated method stub
		return null;

	}

	private IProcessor[] getProcessors(ProcessorType[] processerTypes)
			throws Exception {
		IProcessor[] result = new IProcessor[0];
		for (ProcessorType processerType : processerTypes) {
			IProcessor[] processors = getProcesserService(processerType);
			result = arrayAppend(result, processors);
		}
		return result;
	}

	private IProcessor[] arrayAppend(IProcessor[] array1, IProcessor[] array2) {
		IProcessor[] result = new IProcessor[array1.length + array2.length];
		System.arraycopy(array1, 0, result, 0, array1.length);
		System.arraycopy(array2, 0, result, array1.length, array2.length);
		return result;
	}

	private IProcessor[] getProcesserService(ProcessorType processerType) {
		IProcessor[] result = new IProcessor[0];
		URL[] urls = getProcesserServiceURL(processerType);
		for (URL url : urls) {
			try {
				ProcessorService ps = new ProcessorService(url);
				IProcessor processor = ps.getPort(IProcessor.class);
				result = arrayAppend(result, new IProcessor[] { processor });
			} catch (Exception e) {
				// TODO
			}
		}
		return result;
	}

	private URL[] getProcesserServiceURL(ProcessorType processerType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result allocate(String sourceType, String targetType, Task task)
			throws Exception {
		ProcessorType[] processorTypes = getProcessorTypes(sourceType,
				targetType);
		return allocateByProcesserTypes(processorTypes, task);
	}

	@Override
	public String ping(String value) {
		return "ping " + value;
	}

	public void setPersistence(IPersistence persistence) {
		this.persistence = persistence;
	}

	public void online() {
		persistence.updateTaskAllocatorState(url, STATE_ONLINE);
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void offline() {
		persistence.updateTaskAllocatorState(url, STATE_OFFLINE);
	}

}
