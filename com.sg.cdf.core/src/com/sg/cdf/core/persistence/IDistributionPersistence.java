package com.sg.cdf.core.persistence;

import java.io.Serializable;
import java.util.List;

import com.sg.cdf.core.request.IDistributionJob;


public interface IDistributionPersistence extends Serializable {

	List<IDistributionJob> getDistributions();

	void remove(List<IDistributionJob> jobs);

	void save(IDistributionJob distributionJob);


}
