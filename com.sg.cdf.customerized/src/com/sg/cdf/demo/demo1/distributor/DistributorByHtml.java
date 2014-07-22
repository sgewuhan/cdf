package com.sg.cdf.demo.demo1.distributor;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.sg.cdf.core.distributor.Distributor;
import com.sg.cdf.core.request.IDistributionJob;

public class DistributorByHtml extends Distributor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7913305479342588287L;

	public DistributorByHtml() {
	}

	@Override
	public IStatus run(IDistributionJob job) {
		return new Status(Status.OK, "com.sg.cdf.core.persistence", "HTML Published");

	}


}
