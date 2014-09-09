package com.sg.cdf.core;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class CDF implements BundleActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.sg.cdf.core"; //$NON-NLS-1$

	public static String EMAIL_AUTHUSERNAME = null;

	public static String EMAIL_HOSTNAME = null;

	public static int EMAIL_SMTPPORT = 0;

	public static boolean EMAIL_SSLONCONNECT = false;

	public static String EMAIL_AUTHUSER = null;

	public static String EMAIL_AUTHPASS = null;

	// The shared instance
	private static CDF plugin;

	// private static Map<String, Class<?>> dClassMap = new HashMap<String,
	// Class<?>>();

	private static Map<String, DistributionConfig> distributions = new HashMap<String, DistributionConfig>();

	private Properties properties;

	/**
	 * The constructor
	 */
	public CDF() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		plugin = this;
		loadConfig();
		readEmailSetting();
	}

	private void loadConfig() {
		IExtensionRegistry eReg = Platform.getExtensionRegistry();
		IExtensionPoint ePnt = eReg.getExtensionPoint(CDF.PLUGIN_ID,
				"distribution");
		if (ePnt == null)
			return;
		IExtension[] exts = ePnt.getExtensions();
		for (int i = 0; i < exts.length; i++) {
			IConfigurationElement[] confs = exts[i].getConfigurationElements();
			for (int j = 0; j < confs.length; j++) {
				if ("distribution".equals(confs[j].getName())) {
					distributions.put(confs[j].getAttribute("id"),
							new DistributionConfig(confs[j]));
				} else if ("distributor".equals(confs[j].getName())) {
					// CDF.registeJsonSerializeable(
					// confs[j].getAttribute("class"), confs[j]
					// .getContributor().getName());
				}
			}
		}

	}

	public static DistributionConfig getDistributions(String id) {
		return distributions.get(id);
	}

	public static Properties readProperties() throws Exception {
		InputStream is = null;
		FileInputStream fis = null;
		fis = new FileInputStream(System.getProperty("user.dir") //$NON-NLS-1$
				+ "/configuration/cdf.properties"); //$NON-NLS-1$
		is = new BufferedInputStream(fis);
		Properties properties = new Properties();
		properties.load(is);
		fis.close();
		return properties;
	}

	private void readEmailSetting() throws Exception {
		properties = readProperties();
		EMAIL_HOSTNAME = properties.getProperty("mail.smtp.host");
		EMAIL_SSLONCONNECT = "true".equalsIgnoreCase(properties
				.getProperty("mail.smtp.useSSL"));
		EMAIL_SMTPPORT = Integer.parseInt(properties
				.getProperty("mail.smtp.port"));
		EMAIL_AUTHUSER = properties.getProperty("sender.address");
		EMAIL_AUTHUSERNAME = properties.getProperty("sender.name",
				"CDF Messenger");
		EMAIL_AUTHPASS = properties.getProperty("sender.password");
	}

	public Properties getProperties() {
		return properties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static CDF getDefault() {
		return plugin;
	}

	public static Class<?> getBundleLoadedClass(String bundleName,
			String className) throws ClassNotFoundException {
		Assert.isLegal(className != null && className.length() > 0
				&& bundleName != null && bundleName.length() > 0,
				"Bundle name and class Name can not be null or empty.");
		Bundle bundle = Platform.getBundle(bundleName);
		Assert.isNotNull(bundle, "Can not find bundle, name:" + bundleName);
		return bundle.loadClass(className);
	}

}
