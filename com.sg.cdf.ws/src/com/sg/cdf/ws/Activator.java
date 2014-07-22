package com.sg.cdf.ws;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.sg.cdf.ws.service.DistributionService;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	@SuppressWarnings("rawtypes")
	private ServiceTracker tracker;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
        tracker = new ServiceTracker(Activator.context, DistributionService.class.getName(), null);
        tracker.open();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		tracker.close();
		Activator.context = null;
	}

}
