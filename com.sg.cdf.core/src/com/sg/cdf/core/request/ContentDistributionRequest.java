package com.sg.cdf.core.request;

import java.lang.reflect.InvocationTargetException;

import com.sg.cdf.core.CDF;
import com.sg.cdf.core.DistributionConfig;
import com.sg.cdf.core.Processor;
import com.sg.cdf.core.content.ContentProvider;

public class ContentDistributionRequest extends CommonDistributionRequest {

	private ContentProvider contentProvider;
	
	
	public ContentDistributionRequest() {
		super();
	}

	public ContentDistributionRequest(String distExtId, String name) {
		super(distExtId, name);
	}

	public ContentDistributionRequest(String name) {
		super(name);
	}

	@Override
	protected void init(String distExtId) {
		DistributionConfig conf = CDF.getDistributions(distExtId);
		setContentProvider(conf.getContentProvider());
		super.init(distExtId);
	}
	
	final public ContentProvider getContentProvider() {
		return contentProvider;
	}

	final public void setContentProvider(ContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}

	/**
	 * 为请求注册一个内容提供者，注册后将自动为内容提供者传递参数
	 * @param contentProvider
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	final public void registerContentProvider(ContentProvider contentProvider){
		String[] parameters = getParameters();
		for (String parameter : parameters) {
			try {
				((Processor)contentProvider).setParameterValue(parameter,
						getParameterValue(parameter));
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
			}
		}
		setContentProvider(contentProvider);
	}
	
	@Override
	public void setParameterValue(String parameter, Object value) {
		super.setParameterValue(parameter, value);
		try {
			Processor processor = (Processor)getContentProvider();
			if(processor!=null){
				processor.setParameterValue(parameter, value);
			}
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
		}

	}
	
	@Override
	public IDistributionJob createDistributionJob(boolean runImediately) {
		String name = getName();
		IDistributionJob job = new ContentDistributionJob(name, this);
		if (runImediately) {
			job.launch();
		}
		return job;
	}
}
