package com.sg.cdf.demo.demo1.distributor;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;

import com.sg.cdf.core.content.ContentProvider;

public class DistributorByTextEmail extends DistributorEmail {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4306464434850092610L;

	@Override
	protected void setupEmail(Email email, ContentProvider contentProvider)
			throws EmailException {
		email.setSubject("TestMail≤‚ ‘” º˛");
		email.setMsg("This is a test mail ... :-)");
		super.setupEmail(email, contentProvider);
	}

}
