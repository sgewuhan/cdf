package com.sg.cdf.demo.app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sg.cdf.core.request.ContentDistributionRequest;
import com.sg.cdf.core.request.IDistributionJob;
import com.sg.cdf.core.request.IDistributionRequest;

/**
 * 提供HTTP服务用于启动一个发布任务
 * 
 * @author zhonghua
 *
 */
public class HttpDistribution extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7616088805976355544L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doDistribution(req);
		resp.getWriter().write("scheduled");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doDistribution(req);
		resp.getWriter().write("scheduled");
	}

	protected void doDistribution(HttpServletRequest httpServletRequest) {
		String name = httpServletRequest.getParameter("name");
		String extId = httpServletRequest.getParameter("id");
		IDistributionRequest request = new ContentDistributionRequest(extId, name);
		String[] parameters = request.getParameters();

		for (int i = 0; i < parameters.length; i++) {
			request.setParameterValue(parameters[i],
					httpServletRequest.getParameter(parameters[i]));
		}
		
		request.setMetaData("uri", httpServletRequest.getRequestURI());
		request.setMetaData("ip", httpServletRequest.getRemoteAddr());
		request.setMetaData("server", httpServletRequest.getServerName());
		
		IDistributionJob dj = request.createDistributionJob();
		dj.launch();
	}
}
