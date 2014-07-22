package com.sg.cdf.core.request;


public class RestartDistributionRequest extends CommonDistributionRequest{

	private DistributionJob oldJob;

	public RestartDistributionRequest(DistributionJob job){
		super();
		oldJob = job;
		setName("Cloned Distribution Job");
	}
	
	@Override
	public DistributionJob createDistributionJob() {
		DistributionJob job = null;
		try {
			job = oldJob.clone();
			job.setRequestId(getId());
			job.setRequestName(getName());
			job.setRequestTypeName(getTypeName());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return job;
	}
}
