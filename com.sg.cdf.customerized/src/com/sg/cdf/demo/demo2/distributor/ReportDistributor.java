package com.sg.cdf.demo.demo2.distributor;

import java.util.List;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;

import com.sg.cdf.core.content.Attachment;
import com.sg.cdf.core.content.ContentBody;
import com.sg.cdf.core.content.ContentProvider;
import com.sg.cdf.core.content.HTMLContentBody;
import com.sg.cdf.core.content.TextContentBody;
import com.sg.cdf.demo.demo1.distributor.DistributorEmail;

public class ReportDistributor extends DistributorEmail {

	/**
	 * 
	 */
	private static final long serialVersionUID = -366139847933467625L;

	@Override
	protected void setupEmail(Email email, ContentProvider cp)
			throws EmailException {
		super.setupEmail(email, cp);

		// 设置邮件标题
		String title = cp.getTitle();
		email.setSubject(title);

		// 设置邮件的文本内容
		ContentBody mainBody = cp.getMainBody();
		if (mainBody != null) {
			if (mainBody instanceof TextContentBody) {
				email.setMsg(((TextContentBody) mainBody).getText());
			}

			// 设置邮件的HTML内容
			if (mainBody instanceof HTMLContentBody) {
				((HtmlEmail) email).setHtmlMsg(((HTMLContentBody) mainBody)
						.getHtml());
			}
		}

		// 设置邮件附件
		Attachment attachment = cp.getAttachment();
		if (attachment != null) {
			List<String> files = attachment.getFiles();
			for (int i = 0; i < files.size(); i++) {
				EmailAttachment emailAttachment = new EmailAttachment();
				emailAttachment.setPath(files.get(i));
				emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
				emailAttachment.setDescription("demo attachment");
				emailAttachment.setName(files.get(i));
				((MultiPartEmail) email).attach(emailAttachment);
			}
		}
	}
	
	@Override
	protected int getFormat() {
		return HTML_FORMAT;
	}
}
