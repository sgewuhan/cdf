package com.bizvision.dpf;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.ws.Endpoint;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.bizvision.dpf.persistence.IPersistence;
import com.bizvision.dpf.processor.ProcessException;
import com.bizvision.dpf.server.IProcessorConfig;
import com.bizvision.dpf.server.Processor;
import com.bizvision.dpf.server.ProcessorConfig;
import com.bizvision.dpf.server.TaskAllocator;

/**
 * The activator class controls the plug-in life cycle
 */
public class DPFActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.bizvision.dpf"; //$NON-NLS-1$

	// The shared instance
	private static DPFActivator plugin;

	private List<IProcessorConfig> processorConfigs;

	private Endpoint taskAllocatorService;

	private List<Endpoint> processorServices;

	private String processorBaseUrl;

	private String taskallocatorUrl;

	private String processorIPAddress;

	private IPersistence persistence;

	private TaskAllocator taskAllocator;

	/**
	 * The constructor
	 */
	public DPFActivator() {
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

		loadProperties();

		persistence = createPersistenceClient();

		createTaskAllocatorService();

		registerProcessors();

		createProcessorServices();
	}

	private void loadProperties() {
		InputStream is = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(System.getProperty("user.dir") //$NON-NLS-1$
					+ "/configuration/dpf.properties"); //$NON-NLS-1$
			is = new BufferedInputStream(fis);
			Properties props = new Properties();
			props.load(is);

			processorBaseUrl = props.getProperty("dpf.processorBaseUrl");
			processorIPAddress = props.getProperty("dpf.processorIPAddress");
			taskallocatorUrl = props.getProperty("dpf.taskAllocatorUrl");

		} catch (Exception e) {
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
				}
		}
	}

	private void createProcessorServices() {
		processorServices = new ArrayList<Endpoint>();
		for (IProcessorConfig processorConfig : processorConfigs) {
			try {
				Endpoint endpoint = createProcessorService(processorConfig);
				processorServices.add(endpoint);
			} catch (ProcessException e) {
				e.printStackTrace();
			}
		}
	}

	private Endpoint createProcessorService(IProcessorConfig processorConfig)
			throws ProcessException {
		Processor processor = new Processor();
		processor.setConfig(processorConfig);
		processor.setTaskAllocator(taskAllocator);
		String url = processor
				.genarateUrl(processorBaseUrl, processorIPAddress);
		processor.setPersistence(persistence);

		Endpoint endpoint = Endpoint.create(processor);
		endpoint.publish(url);

		processor.online();

		return endpoint;
	}

	private void createTaskAllocatorService() {
		taskAllocator = new TaskAllocator();
		taskAllocator.setUrl(taskallocatorUrl);
		taskAllocator.setPersistence(persistence);

		taskAllocatorService = Endpoint.create(taskAllocator);
		taskAllocatorService.publish(taskallocatorUrl);
		taskAllocator.online();
	}

	private IPersistence createPersistenceClient() {
		return null;
	}

	private void registerProcessors() {
		IExtensionRegistry eReg = Platform.getExtensionRegistry();
		IExtensionPoint ePnt = eReg.getExtensionPoint(DPFActivator.PLUGIN_ID,
				"processor");
		if (ePnt == null) {
			return;
		}
		processorConfigs = new ArrayList<IProcessorConfig>();
		IExtension[] exts = ePnt.getExtensions();
		for (int i = 0; i < exts.length; i++) {
			IConfigurationElement[] confs = exts[i].getConfigurationElements();
			for (int j = 0; j < confs.length; j++) {
				if ("processor".equals(confs[j].getName())) {
					ProcessorConfig processorConfig = new ProcessorConfig(
							confs[j]);
					processorConfigs.add(processorConfig);
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

		for (Endpoint processorService : processorServices) {
			((Processor) processorService.getImplementor()).offline();
			processorService.stop();
		}

		taskAllocator.offline();
		taskAllocatorService.stop();

		plugin = null;

		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static DPFActivator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 *
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
