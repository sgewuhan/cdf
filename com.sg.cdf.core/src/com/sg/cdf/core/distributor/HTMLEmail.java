package com.sg.cdf.core.distributor;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.sg.cdf.core.content.ContentProvider;
import com.sg.cdf.core.distributor.EMailDistributor;

public class HTMLEmail extends EMailDistributor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String title;

	public String message;

	public String to;

	public String toName;

	public String fromName;

	@Override
	protected void setupEmail(Email email, ContentProvider cp)
			throws EmailException {
		super.setupEmail(email, cp);

		email.addTo(to, toName);

		// 设置邮件标题
		email.setSubject(title);

		// 设置邮件的HTML内容
		((HtmlEmail) email).setHtmlMsg(message);

	}

	@Override
	protected int getFormat() {
		return HTML_FORMAT;
	}

	@Override
	protected String getFromName() {
		if (fromName != null && !fromName.isEmpty())
			return fromName;
		return super.getFromName();
	}

}
