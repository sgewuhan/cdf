package com.sg.cdf.ws.service;

import java.io.Serializable;

public class Parameter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4946834825215250479L;

	String name;
	
	String value;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return name+":"+value;
	}
}
