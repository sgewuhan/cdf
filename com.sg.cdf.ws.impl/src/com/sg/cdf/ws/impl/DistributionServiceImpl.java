package com.sg.cdf.ws.impl;

import java.util.ArrayList;

import com.sg.cdf.core.CDF;
import com.sg.cdf.core.content.ContentProvider;
import com.sg.cdf.core.distributor.Distributor;
import com.sg.cdf.core.persistence.IDistributionPersistence;
import com.sg.cdf.core.request.ContentDistributionRequest;
import com.sg.cdf.ws.service.DistributionService;
import com.sg.cdf.ws.service.NameSpace;
import com.sg.cdf.ws.service.Parameter;
import com.sg.cdf.ws.service.RequestBuilder;

public class DistributionServiceImpl implements DistributionService {

	@Override
	public void distribute(RequestBuilder factory) {
		try {
			run(factory);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	void run(RequestBuilder builder) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		ContentDistributionRequest req;
		String extId = builder.getExtensionId();
		String name = builder.getName();
		if (extId != null) {
			req = new ContentDistributionRequest(extId, name);
		} else {
			req = new ContentDistributionRequest(name);
		}

		NameSpace space = builder.getPersistence();
		Object instance = getInstance(space.getBundle(), space.getClas());
		if (instance instanceof IDistributionPersistence) {
			req.setPersistence((IDistributionPersistence) instance);
		}

		ArrayList<Parameter> parameters = builder.getParameters();
		if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				req.setParameterValue(parameters.get(i).getName(), parameters.get(i).getValue());
			}
		}

		space = builder.getContentProvider();
		instance = getInstance(space.getBundle(), space.getClas());
		if (instance instanceof ContentProvider) {
			req.registerContentProvider((ContentProvider) instance);
		}

		 ArrayList<NameSpace> ns = builder.getDistributors();
		if (ns != null) {
			for (int i = 0; i < ns.size(); i++) {
				instance = getInstance(ns.get(i).getBundle(), ns.get(i).getClas());
				if (instance instanceof Distributor) {
					req.registeDistributor((Distributor) instance);
				}
			}
		}

		// 添加发布器，注意该发布器必须在扩展中进行注册
		// 创建发布任务,并运行
		req.createDistributionJob(true);
	}

	private Object getInstance(String bundle, String clas)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class<?> bundleLoadedClass = CDF.getBundleLoadedClass(bundle, clas);
		return bundleLoadedClass.newInstance();
	}

}
