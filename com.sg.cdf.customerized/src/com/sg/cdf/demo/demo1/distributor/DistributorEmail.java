package com.sg.cdf.demo.demo1.distributor;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;

import com.sg.cdf.core.content.ContentProvider;
import com.sg.cdf.core.distributor.EMailDistributor;

public class DistributorEmail extends EMailDistributor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4731283508437738413L;

	@Override
	protected void setupEmail(Email email, ContentProvider contentProvider)throws EmailException {
		super.setupEmail(email, contentProvider);
		email.setFrom("zhonghua@yaozheng.com.cn","pm2 ≤‚ ‘∑˛ŒÒ’ ∫≈");
		email.addTo("zhonghua@yaozheng.com.cn","≤‚ ‘’ﬂ1");
		email.addCc("zh@yaozheng.com.cn", "≤‚ ‘’ﬂ2");
	}


}
