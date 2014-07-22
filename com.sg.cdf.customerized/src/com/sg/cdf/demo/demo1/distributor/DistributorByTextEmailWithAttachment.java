package com.sg.cdf.demo.demo1.distributor;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import com.sg.cdf.core.content.ContentProvider;

public class DistributorByTextEmailWithAttachment extends
		DistributorByTextEmail {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7170321231008747958L;

	@Override
	protected void setupEmail(Email email,ContentProvider cp) throws EmailException {
		super.setupEmail(email,cp);
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath("h:/±à¼­1.txt");
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("²âÊÔ¸½¼þ");
		attachment.setName("±à¼­");
		((MultiPartEmail) email).attach(attachment);
	}
	
	@Override
	protected int getFormat() {
		return MULTIPART_FORMAT;
	}

}
