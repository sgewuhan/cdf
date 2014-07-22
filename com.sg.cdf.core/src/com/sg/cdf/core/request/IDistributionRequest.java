package com.sg.cdf.core.request;


public interface IDistributionRequest {

	String[] getParameters();

	void setParameterValue(String parameter, Object value);

	void setMetaData(String string, String data);

	IDistributionJob createDistributionJob();

}
