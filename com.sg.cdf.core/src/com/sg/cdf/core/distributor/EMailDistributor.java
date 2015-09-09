package com.sg.cdf.core.distributor;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.sg.cdf.core.CDF;
import com.sg.cdf.core.content.ContentProvider;
import com.sg.cdf.core.request.ContentDistributionJob;
import com.sg.cdf.core.request.IDistributionJob;

public abstract class EMailDistributor extends Distributor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8969294871122243900L;

	protected static final int TEXT_FORMAT = 10;

	protected static final int MULTIPART_FORMAT = 21;

	protected static final int HTML_FORMAT = 30;

	protected static final int IMG_HTML_FORMAT = 40;

	public EMailDistributor() {
		super();
	}

	@Override
	public IStatus run(IDistributionJob job) {
		Email email;
		try {
			switch (getFormat()) {
			case HTML_FORMAT:
				email = new HtmlEmail();
				break;
			case IMG_HTML_FORMAT:
				email = new ImageHtmlEmail();
				break;
			case MULTIPART_FORMAT:
				email = new MultiPartEmail();
				break;
			default:
				email = new SimpleEmail();
			}

			if (job instanceof ContentDistributionJob) {
				ContentProvider contentProvider = ((ContentDistributionJob) job)
						.getContentProvider();
				setupEmail(email, contentProvider);
			} else {
				setupEmail(email, null);
			}
			 email.send();
		} catch (EmailException e) {
			return new Status(Status.ERROR, CDF.PLUGIN_ID,
					"Email Sending Error.", e);
		}
		return new Status(Status.OK, CDF.PLUGIN_ID, "Email Sended.");
	}

	protected void setupEmail(Email email,
			ContentProvider contentProvider) throws EmailException{
		email.setHostName(CDF.EMAIL_HOSTNAME);
		email.setSmtpPort(CDF.EMAIL_SMTPPORT);
		email.setSSLOnConnect(CDF.EMAIL_SSLONCONNECT);
		email.setAuthentication(CDF.EMAIL_AUTHUSER, CDF.EMAIL_AUTHPASS);
		String fromName = getFromName();
		String fromEmail = getFromEmail();
		email.setFrom(fromEmail,fromName);
	}
	
	protected String getFromEmail(){
		return CDF.EMAIL_AUTHUSER;
	}

	protected String getFromName() {
		return  CDF.EMAIL_AUTHUSERNAME;
	}

	protected int getFormat() {
		return TEXT_FORMAT;
	}

}
