package com.sg.cdf.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import com.sg.cdf.core.content.ContentProvider;
import com.sg.cdf.core.distributor.Distributor;
import com.sg.cdf.core.persistence.IDistributionPersistence;

public class DistributionConfig {

	private IConfigurationElement ce;

	public DistributionConfig(IConfigurationElement ce) {
		this.ce = ce;
//		registerClass();
	}

//	private void registerClass() {
//		CDF.registeJsonSerializeable(ce,"persistence");
//		CDF.registeJsonSerializeable(ce,"contentProvider");
//		IConfigurationElement[] children = ce.getChildren("distributor");
//		for (int k = 0; k < children.length; k++) {
//			CDF.registeJsonSerializeable(children[k],"class");
//		}
//	}

	public String getName() {
		return ce.getAttribute("name");
	}

	public String getDescription() {
		return ce.getAttribute("description");
	}

	public IDistributionPersistence getPersistence() {
		try {
			return (IDistributionPersistence) ce
					.createExecutableExtension("persistence");
		} catch (CoreException e) {
		}
		return null;
	}

	public List<Distributor> getDistributors() {
		IConfigurationElement[] children = ce.getChildren("distributor");
		ArrayList<Distributor> distributors = new ArrayList<Distributor>();
		for (int k = 0; k < children.length; k++) {
			try {
				Distributor idis = (Distributor) children[k]
						.createExecutableExtension("class");
				distributors.add(idis);
			} catch (CoreException e) {
			}
		}
		return distributors;
	}

	public String[] getParameters() {
		IConfigurationElement[] children = ce.getChildren("parameter");
		String[] parameters = new String[children.length];
		for (int k = 0; k < children.length; k++) {
			String parameterName = children[k].getAttribute("name");
			parameters[k] = parameterName;
		}
		return parameters;
	}

	public ContentProvider getContentProvider() {
		try {
			return (ContentProvider) ce
					.createExecutableExtension("contentProvider");
		} catch (CoreException e) {
		}
		return null;
	}

}
