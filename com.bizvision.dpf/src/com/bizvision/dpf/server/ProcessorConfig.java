package com.bizvision.dpf.server;

import org.eclipse.core.runtime.IConfigurationElement;

import com.bizvision.dpf.persistence.ProcessorType;
import com.bizvision.dpf.runtime.IProcessorRunable;

public class ProcessorConfig  {

	private IConfigurationElement ce;
	
	private String id;
	
	private String name;
	
	private int maxThreadCount;

	private ProcessorType[] processorTypes;

	public ProcessorConfig(IConfigurationElement ce) {
		this.ce = ce;
		this.id = ce.getAttribute("id");
		this.name = ce.getAttribute("name");
		this.maxThreadCount = Integer.parseInt(ce
				.getAttribute("maxThreadCount"));
		IConfigurationElement[] children = ce.getChildren("processorType");
		processorTypes = new ProcessorType[children.length];
		for (int i = 0; i < children.length; i++) {
			processorTypes[i] = new ProcessorType();
			processorTypes[i].setName(children[i].getAttribute("name"));
			processorTypes[i].setSourceType(children[i]
					.getAttribute("sourceType"));
			processorTypes[i].setTargetType(children[i]
					.getAttribute("targetType"));
		}
	}

	public int getMaxThreadCount() {
		return maxThreadCount;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public IProcessorRunable getProcessorRunable() throws Exception {
		return (IProcessorRunable) ce.createExecutableExtension("runable");
	}

	public ProcessorType[] getProcessorTypes() {
		return processorTypes;
	}

}
