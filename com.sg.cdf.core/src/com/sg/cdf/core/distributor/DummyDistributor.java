package com.sg.cdf.core.distributor;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.sg.cdf.core.request.IDistributionJob;

public class DummyDistributor extends Distributor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public IStatus run(IDistributionJob job) {
		return Status.OK_STATUS;
	}

}
