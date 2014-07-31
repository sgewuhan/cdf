package com.sg.cdf.ws.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.sg.cdf.ws.impl.DistributionServiceImpl;
import com.sg.cdf.ws.service.DistributionService;

public class Activator implements BundleActivator {

//	private static final String ORG_APACHE_CXF_WS_ADDRESS = "org.apache.cxf.ws.address";
//	private static final String SERVICE_EXPORTED_CONFIGS = "service.exported.configs";
//	private static final String SERVICE_EXPORTED_INTENTS = "service.exported.intents";
//	private static final String SERVICE_EXPORTED_INTERFACES = "service.exported.interfaces";
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
			Iterator<String> iter = conf.stringPropertyNames().iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				props.put(key, conf.getProperty(key));
			}

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
