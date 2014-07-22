package com.sg.cdf.core.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sg.cdf.core.content.ContentProvider;
import com.sg.cdf.core.distributor.Distributor;
import com.sg.cdf.core.request.ContentDistributionJob;
import com.sg.cdf.core.request.IDistributionJob;

public abstract class DistributionJsonPersistence<T extends IDistributionJob> implements
		IDistributionPersistence {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3552931018731412458L;
	
	private Class<T> targetClass;

	@Override
	final public void save(IDistributionJob distributionJob) {
		save(distributionJob,toJson(distributionJob));
	}
	
	protected abstract void save(IDistributionJob distributionJob, String json) ;


	final protected String toJson(Object object) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		registerAdapter(gsonBuilder);
		Gson gs = gsonBuilder.create();
		String json = gs.toJson(object);
		return json;
	}

	final protected IDistributionJob fromJson(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		registerAdapter(gsonBuilder);
		Gson gs = gsonBuilder.create();
		if(targetClass==null){
			return gs.fromJson(json, ContentDistributionJob.class);
		}else{
			return gs.fromJson(json, targetClass);
		}
	}

	public void registerAdapter(GsonBuilder gsonBuilder) {
		gsonBuilder.registerTypeAdapter(IDistributionJob.class,
				new JsonClassAdapter<IDistributionJob>());
		gsonBuilder.registerTypeAdapter(Distributor.class,
				new JsonClassAdapter<Distributor>());
		gsonBuilder.registerTypeAdapter(IDistributionPersistence.class,
				new JsonClassAdapter<IDistributionPersistence>());
		gsonBuilder.registerTypeAdapter(ContentProvider.class,
				new JsonClassAdapter<ContentProvider>());
	}
	

}
