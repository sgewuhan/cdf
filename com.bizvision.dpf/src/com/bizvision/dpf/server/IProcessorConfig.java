package com.bizvision.dpf.server;

import com.bizvision.dpf.persistence.ProcessorType;

public interface IProcessorConfig {

	public int getMaxThreadCount();

	public String getId();

	public String getName();

	public ProcessorType[] getProcessorTypes();

}
