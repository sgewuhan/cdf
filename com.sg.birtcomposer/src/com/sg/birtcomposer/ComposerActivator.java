package com.sg.birtcomposer;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class ComposerActivator implements BundleActivator {

	private IReportEngine engine;

	private static ComposerActivator bundle;

	@Override
	public void start(BundleContext context) throws Exception {
		bundle = this;
	}

	public IReportEngine createEngine() {
		IReportEngineFactory factory = (IReportEngineFactory) Platform
				.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
		EngineConfig config = new EngineConfig();
		IReportEngine engine = factory.createReportEngine(config);
		return engine;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (engine != null) {
			engine.destroy();
		}
	}

	public static IReportEngine getEngine() {
		if (bundle.engine == null) {
			bundle.engine = bundle.createEngine();
		}
		return bundle.engine;
	}

}
