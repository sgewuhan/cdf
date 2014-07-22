package com.sg.cdf.core.request;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;

import com.sg.cdf.core.content.ContentProvider;

public class ContentDistributionJob extends DistributionJob {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1776489124349046710L;
	private ContentProvider contentProvider;

	ContentDistributionJob(ContentDistributionJob job,
			CommonDistributionRequest request) {
		super(job, request);
		setContentProvider(job.getContentProvider());
	}

	ContentDistributionJob(String name, ContentDistributionRequest request) {
		super(name, request);
		setContentProvider(request.getContentProvider());
	}

	ContentDistributionJob(ContentDistributionJob job) {
		super(job);
		setContentProvider(job.getContentProvider());
	}

	public ContentProvider getContentProvider() {
		return contentProvider;
	}

	public void setContentProvider(ContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}
	
	@Override
	protected void execute(MultiStatus status) {
		if (contentProvider == null) {
			return;
		}

		/*
		 * 执行内容提取
		 */
		IStatus result = contentProvider.run(this);
		if (result != null) {
			status.add(result);
		}

		super.execute(status);
	}

	@Override
	protected ContentDistributionJob clone() throws CloneNotSupportedException {
		return new ContentDistributionJob(this);
	}

}
