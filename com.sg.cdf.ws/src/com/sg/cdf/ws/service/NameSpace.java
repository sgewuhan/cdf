package com.sg.cdf.ws.service;

import java.io.Serializable;

public class NameSpace implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2038304626481845065L;

	String bundle;
	
	String clas;
	
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
	
	public String getBundle() {
		return bundle;
	}
	
	public void setClas(String clas) {
		this.clas = clas;
	}
	
	public String getClas() {
		return clas;
	}
	
	@Override
	public String toString() {
		return bundle+"@"+clas;
	}
}
