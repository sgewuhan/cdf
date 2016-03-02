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

	public String cc;

	public String ccName;

	public String fromName;

	public String fromEmail;

	@Override
	protected void setupEmail(Email email, ContentProvider cp)
			throws EmailException {
		super.setupEmail(email, cp);

		email.addTo(to, toName);

		if (cc != null) {
			try {
				String[] ccs = cc.split(";");
				String[] ccnames = ccName.split(";");
				for (int i = 0; i < ccnames.length; i++) {
					email.addCc(ccs[i], ccnames[i]);
				}
			} catch (Exception e) {
			}
		}
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

	@Override
	protected String getFromEmail() {
		if (fromEmail != null && !fromEmail.isEmpty())
			return fromEmail;
		return super.getFromEmail();
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

}
