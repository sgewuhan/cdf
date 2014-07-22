package com.sg.cdf.monitor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class CDFMonitor extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.sg.cdf.core.monitor"; //$NON-NLS-1$

	// The shared instance
	private static CDFMonitor plugin;

	private static Map<String, IConfigurationElement> monitors = new HashMap<String, IConfigurationElement>();

	public static Map<String, IConfigurationElement> getMonitorsConfig() {
		return monitors;
	}

	/**
	 * The constructor
	 */
	public CDFMonitor() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		loadConfig();
	}

	private void loadConfig() {
		IExtensionRegistry eReg = Platform.getExtensionRegistry();
		IExtensionPoint ePnt = eReg.getExtensionPoint(CDFMonitor.PLUGIN_ID,
				"cdfmonitor");
		if (ePnt != null) {
			IExtension[] exts = ePnt.getExtensions();
			for (int i = 0; i < exts.length; i++) {
				IConfigurationElement[] confs = exts[i]
						.getConfigurationElements();
				for (int j = 0; j < confs.length; j++) {
					if ("monitor".equals(confs[j].getName())) {
						String id = confs[j].getAttribute("id");
						monitors.put(id, confs[j]);
					}
				}
			}
		}
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
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static CDFMonitor getDefault() {
		return plugin;
	}

}
