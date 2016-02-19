package com.bizvision.dpf.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.eclipse.core.internal.jobs.JobManager;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;

import com.bizvision.dpf.persistence.IPersistence;
import com.bizvision.dpf.processor.Exception;
import com.bizvision.dpf.processor.IProcessor;
import com.bizvision.dpf.processor.ProcessException;
import com.bizvision.dpf.processor.ProcessorPerformence;
import com.bizvision.dpf.processor.Result;
import com.bizvision.dpf.processor.Task;

@WebService(endpointInterface = "com.bizvision.dpf.processor.IProcessor")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class Processor implements IProcessor {

	private IProcessorConfig processorConfig;
	private String url;
	private TaskAllocator taskAllocator;
	private IPersistence persistence;

	@Override
	public Result execute(Task task) throws ProcessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessorPerformence getProcessorPerformence()
			throws ProcessException {
		// TODO Auto-generated method stub
		return null;
	}

	public String genarateUrl(String processorBaseUrl, String ipAddress)
			throws ProcessException {
		int port = getAvailablePort(ipAddress);
		url = processorBaseUrl + ":" + port + "/processor";
		return url;
	}

	public String getUrl() {
		return url;
	}

	private int getAvailablePort(String ipAddress) throws ProcessException {

		Socket s = new Socket();
		for (int port = 20000; port < 30000; port++) {
			try {
				s.bind(new InetSocketAddress(ipAddress, port));
				s.close();
				return port;
			} catch (IOException e) {
			}
		}
		throw new ProcessException("PORT NOT AVAILABLE", new Exception());
	}

	public IProcessorConfig getProcessorConfig() {
		return processorConfig;
	}

	public void setConfig(IProcessorConfig processorConfig) {
		this.processorConfig = processorConfig;
	}

	public void setTaskAllocator(TaskAllocator taskAllocator) {
		this.taskAllocator = taskAllocator;

	}

	public TaskAllocator getTaskAllocator() {
		return taskAllocator;
	}

	public void setPersistence(IPersistence persistence) {
		this.persistence = persistence;
	}

	public void online() {
		// TODO Auto-generated method stub

	}

	public void offline() {
		// TODO Auto-generated method stub

	}
}
