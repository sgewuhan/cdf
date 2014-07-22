package com.sg.cdf.demo.demo1.distributor;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;

import com.sg.cdf.core.content.ContentProvider;

public class DistributorByImageHtmlEmail extends DistributorEmail {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8300784472701644159L;

	@Override
	protected void setupEmail(Email email, ContentProvider contentProvider)throws EmailException {
		// define you base URL to resolve relative resource locations
		try {
			URL url = new URL("http://127.0.0.1:10080/html/examples.html");
			String htmlEmail = loadUrlContent(url, "utf-8");
			// create the email message
			((ImageHtmlEmail) email)
					.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setSubject("Test email with inline image");
			// set the html message
			((ImageHtmlEmail) email).setHtmlMsg(htmlEmail);
			// set the alternative message
			((HtmlEmail) email)
					.setTextMsg("Your email client does not support HTML messages");
			super.setupEmail(email,contentProvider);		// load your HTML email template
		} catch (MalformedURLException e) {
			throw new EmailException(e);
		} catch (IOException e) {
			throw new EmailException(e);
		}
	}
	
    private String loadUrlContent(URL url,String charset) throws IOException {
        InputStream stream = url.openStream();
        StringBuilder html = new StringBuilder();
        try {
            List<String> lines = IOUtils.readLines(stream,charset);
            for (String line : lines) {
                html.append(line).append("\n");
            }
        } finally {
            stream.close();
        }
        return html.toString();
    }
    
	@Override
	protected int getFormat() {
		return IMG_HTML_FORMAT;
	}

}
