package com.tmt.cdf;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.sg.cdf.core.CDF;
import com.sg.cdf.core.distributor.Distributor;
import com.sg.cdf.core.request.IDistributionJob;

public class WEBDistributor extends Distributor {

	public String message;


	@Override
	public IStatus run(IDistributionJob job) {
		System.out.println(message);
		return new Status(Status.OK, CDF.PLUGIN_ID, "SMS Distributed.");
	}

}
