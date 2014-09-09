package com.sg.cdf.core.request;


public interface IDistributionJob {

	void launch();

	String getRequestId();

	int getState();

	String execute();
	
}
