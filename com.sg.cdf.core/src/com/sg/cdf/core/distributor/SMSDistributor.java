package com.sg.cdf.core.distributor;

import org.eclipse.core.runtime.IStatus;

import com.sg.cdf.core.request.IDistributionJob;

public class SMSDistributor extends Distributor{

	@Override
	public IStatus run(IDistributionJob job) {
//		try {
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
//			String url="jdbc:sqlserver://172.16.9.173:1433;DatabaseName=BSMetone";
//			String user="xcwl";
//			String pwd="xcwl2012";
//			java.sql.Connection con=DriverManager.getConnection(url,user,pwd);
//			Statement stmt = con.createStatement();
//			String sql="INSERT INTO MT_SMS(SEND_USER_ID,SCH_MARK,IS_HANDLE,MOBILE_NUMBER,CONTENT,SEND_TIME,SMS_Status,Mobile_Count) " +
//					"values ('1996','now','0','"+mobile+"','" + textMessage + "','" + dateString
//					+ "',0,"+s+")";
//			if (stmt.executeUpdate(sql) == 0) {
//				message = "短信发送未成功！";
//			} else {
//				message = "短信发送成功！";
//			}
//			//System.out.println("sql :"+sql);
//			stmt.close();
//			con.close();
//		} catch (Exception e) {
//			message = "短信发送未成功！";
//			e.printStackTrace();
//		}
//		return message;
		return null;
	}

}
