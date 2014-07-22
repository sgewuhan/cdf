package com.sg.cdf.ws.service;

import java.util.ArrayList;


public interface RequestBuilder {

	void setName(String name);
	
	String getName();

	void setExtensionId(String extensionId);

	String getExtensionId();
	
	void setPersistence(NameSpace nameSpace);

	NameSpace getPersistence();
	
	void setContentProvider(NameSpace nameSpace);
	
	NameSpace getContentProvider();
	
	void setDistributors(ArrayList<NameSpace> nameSpaces);

	ArrayList<NameSpace> getDistributors();
	
	void setParameters(ArrayList<Parameter> parameters);

	ArrayList<Parameter> getParameters();
}
