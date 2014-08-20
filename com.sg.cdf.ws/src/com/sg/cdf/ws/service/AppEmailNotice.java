package com.sg.cdf.ws.service;

public class AppEmailNotice {

	private String receiverName;
	
	private String receiverAddress;
	
	private String subject;
	
	private String htmlEmailBody;
	
	private String appName;
	
	private String requestName;
	
	private NameSpace persistence;
	
	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getHtmlEmailBody() {
		return htmlEmailBody;
	}

	public void setHtmlEmailBody(String htmlEmailBody) {
		this.htmlEmailBody = htmlEmailBody;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}

	public NameSpace getPersistence() {
		return persistence;
	}

	public void setPersistence(NameSpace persistence) {
		this.persistence = persistence;
	}

	
}
