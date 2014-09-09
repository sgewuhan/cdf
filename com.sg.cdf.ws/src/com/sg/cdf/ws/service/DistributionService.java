package com.sg.cdf.ws.service;



public interface DistributionService {
	
	void distribute(RequestBuilder factory);
	
	String run(RequestBuilder factory);
	
	void simpleApplicationEmailNotice(AppEmailNotice emailNotice);
}