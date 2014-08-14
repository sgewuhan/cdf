package com.sg.cdf.osgiclientdemo;

import java.util.ArrayList;

import com.sg.cdf.ws.service.NameSpace;
import com.sg.cdf.ws.service.Parameter;
import com.sg.cdf.ws.service.RequestBuilder;

public class RequestBuilderImpl implements RequestBuilder {

	private String name;
	private String extId;
	private NameSpace pNameSpace;
	private NameSpace cNameSpace;
	private ArrayList<NameSpace> distributors;
	private ArrayList<Parameter> parameters;

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setExtensionId(String extId) {
		this.extId = extId;
	}

	@Override
	public String getExtensionId() {
		return extId;
	}

	@Override
	public void setPersistence(NameSpace ns) {
		this.pNameSpace = ns;
		
	}

	@Override
	public NameSpace getPersistence() {
		return pNameSpace;
	}

	@Override
	public void setContentProvider(NameSpace ns) {
		this.cNameSpace = ns;
		
	}

	@Override
	public NameSpace getContentProvider() {
		return cNameSpace;
	}

	@Override
	public void setDistributors(ArrayList<NameSpace> nameSpaces) {
		this.distributors = nameSpaces;
		
	}

	@Override
	public ArrayList<NameSpace> getDistributors() {
		return distributors;
	}

	@Override
	public void setParameters(ArrayList<Parameter> parameters) {
		this.parameters = parameters;
	}

	@Override
	public ArrayList<Parameter> getParameters() {
		return parameters;
	}



	
}