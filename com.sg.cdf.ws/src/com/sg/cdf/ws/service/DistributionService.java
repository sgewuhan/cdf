package com.sg.cdf.ws.service;


public interface DistributionService {

	public void distribute(RequestBuilder factory);

	public String run(RequestBuilder factory);

	public void simpleApplicationEmailNotice(AppEmailNotice emailNotice);

	public byte[] downloadFile(String context, String filename);

}