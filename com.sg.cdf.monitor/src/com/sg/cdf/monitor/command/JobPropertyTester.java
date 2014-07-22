package com.sg.cdf.monitor.command;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.jobs.Job;

import com.sg.cdf.core.request.DistributionJob;

public class JobPropertyTester extends PropertyTester {

	public JobPropertyTester() {
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		if (receiver instanceof DistributionJob) {
			DistributionJob job = (DistributionJob) receiver;
			int state = job.getState();
			boolean value = "true".equals(expectedValue);

			if ("canremove".equalsIgnoreCase(property)) {
				return value == (state == Job.NONE||state == Job.RUNNING);
			} else {
				if ("cansleep".equalsIgnoreCase(property)) {
					return value == (state == Job.RUNNING);
				} else if ("canawake".equalsIgnoreCase(property)) {
					return value == (state == Job.SLEEPING);
				} else if ("canstop".equalsIgnoreCase(property)) {
					return value == (state == Job.RUNNING);
				} else if ("canrestart".equalsIgnoreCase(property)) {
					return value == (state == Job.NONE);
				}
			}
			return true;
		}
		return false;
	}

}
