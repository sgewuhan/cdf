package com.sg.cdf.ws.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.sg.cdf.ws.impl.DistributionServiceImpl;
import com.sg.cdf.ws.service.DistributionService;

public class Activator implements BundleActivator {

	private static final String ORG_APACHE_CXF_WS_ADDRESS = "org.apache.cxf.ws.address";
	private static final String SERVICE_EXPORTED_CONFIGS = "service.exported.configs";
	private static final String SERVICE_EXPORTED_INTENTS = "service.exported.intents";
	private static final String SERVICE_EXPORTED_INTERFACES = "service.exported.interfaces";
	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	private ServiceRegistration<?> registration;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		// 设置服务的属性
		Activator.context = bundleContext;
		InputStream is = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(System.getProperty("user.dir") //$NON-NLS-1$
					+ "/configuration/cdf.properties"); //$NON-NLS-1$
			is = new BufferedInputStream(fis);
			Properties conf = new Properties();
			conf.load(is);

			Dictionary<String, String> props = new Hashtable<String, String>();
			props.put(SERVICE_EXPORTED_INTERFACES,
					conf.getProperty(SERVICE_EXPORTED_INTERFACES, "*"));
			props.put(SERVICE_EXPORTED_INTENTS,
					conf.getProperty(SERVICE_EXPORTED_INTERFACES, "SOAP"));
			props.put(SERVICE_EXPORTED_CONFIGS, conf.getProperty(
					SERVICE_EXPORTED_CONFIGS, "org.apache.cxf.ws"));
			props.put(ORG_APACHE_CXF_WS_ADDRESS,conf.getProperty(
					SERVICE_EXPORTED_CONFIGS, 
					"http://localhost:9000/distribution"));
			// 注册服务
			registration = Activator.context.registerService(
					DistributionService.class.getName(),
					new DistributionServiceImpl(), props);
		} catch (Exception e) {
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		registration.unregister();
		Activator.context = null;
	}

}
