package com.sg.cdf.demo.demo1.distributor;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.sg.cdf.core.content.ContentProvider;

public class DistributorByHtmlEmail extends DistributorEmail {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1030118116907782540L;

	@Override
	protected void setupEmail(Email email, ContentProvider contentProvider)throws EmailException {
		super.setupEmail(email,contentProvider);

		email.setSubject("Test email with inline image");
		// embed the image and get the content id
		// URL url = new URL("http://127.0.0.1:10080/html/examples.html");
		// String cid = ((HtmlEmail) email).embed(url, "Apache logo");
		
		// set the html message
		((HtmlEmail) email).setHtmlMsg("<html><body>DEMO <b>HTML</b> MESSAGE</body></html>");

		// set the alternative message
		((HtmlEmail) email)
				.setTextMsg("Your email client does not support HTML messages");
	}

	@Override
	protected int getFormat() {
		return HTML_FORMAT;
	}
}
