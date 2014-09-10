package com.sg.cdf.ws.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface DistributionService {

	@WebMethod
	public void distribute(RequestBuilder factory);

	@WebMethod
	public String run(RequestBuilder factory);

	@WebMethod
	public void simpleApplicationEmailNotice(AppEmailNotice emailNotice);

	@WebMethod
	public byte[] downloadFile(String context, String filename);

}