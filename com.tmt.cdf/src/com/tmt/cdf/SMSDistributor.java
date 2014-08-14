package com.tmt.cdf;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.sg.cdf.core.CDF;
import com.sg.cdf.core.distributor.Distributor;
import com.sg.cdf.core.request.IDistributionJob;

public class SMSDistributor extends Distributor {

	public String senderId;

	public String mobileNumber;

	public String textMessage;

	public String sendTime;

	public String mobileCount;

	@Override
	public IStatus run(IDistributionJob job) {
		String sql = "INSERT INTO MT_SMS(SEND_USER_ID,SCH_MARK,IS_HANDLE,MOBILE_NUMBER,CONTENT,SEND_TIME,SMS_Status,Mobile_Count) "
				+ "values ('"
				+ senderId
				+ "','now','0','"
				+ mobileNumber
				+ "','"
				+ textMessage + "','" + sendTime + "',0," + mobileCount + ")";

		try {
//			SQLUtil.SQL_EXECUTE("SMS", new String[] { sql });
			System.out.println(sql);
		} catch (Exception e) {
			return new Status(Status.ERROR, CDF.PLUGIN_ID, e.getMessage());
		}

		return new Status(Status.OK, CDF.PLUGIN_ID, "SMS Distributed.");
	}

}
