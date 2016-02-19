package com.bizvision.dpf.persistence;

public class ProcessorPersistable {

	private String url;

	private int state;

	private int maxThreadCount;

	private ProcessorType[] processorTypes;

	private String id;

	private String name;

	public void setUrl(String url) {
		this.url = url;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setMaxThreadCount(int maxThreadCount) {
		this.maxThreadCount = maxThreadCount;
	}

	public void setProcessorTypes(ProcessorType[] processorTypes) {
		this.processorTypes = processorTypes;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public int getState() {
		return state;
	}

	public int getMaxThreadCount() {
		return maxThreadCount;
	}

	public ProcessorType[] getProcessorTypes() {
		return processorTypes;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	
}
