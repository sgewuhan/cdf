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
	public String execute(MultiStatus status) {
		StringBuffer sb = new StringBuffer();
		if (contentProvider != null) {
			/*
			 * 执行内容提取
			 */
			IStatus result = contentProvider.run(this);
			if (result != null) {
				if(status!=null){
					status.add(result);
				}
				sb.append(result.getMessage());
				sb.append("\n");
			}
		}

		String message = super.execute(status);
		sb.append(message);
		return sb.toString();
	}

	@Override
	protected ContentDistributionJob clone() throws CloneNotSupportedException {
		return new ContentDistributionJob(this);
	}

}
