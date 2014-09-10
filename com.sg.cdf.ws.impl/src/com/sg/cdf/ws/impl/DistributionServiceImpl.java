package com.sg.cdf.ws.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.sg.cdf.core.CDF;
import com.sg.cdf.core.content.ContentProvider;
import com.sg.cdf.core.distributor.Distributor;
import com.sg.cdf.core.distributor.HTMLEmail;
import com.sg.cdf.core.persistence.IDistributionPersistence;
import com.sg.cdf.core.request.ContentDistributionRequest;
import com.sg.cdf.ws.service.AppEmailNotice;
import com.sg.cdf.ws.service.DistributionService;
import com.sg.cdf.ws.service.NameSpace;
import com.sg.cdf.ws.service.Parameter;
import com.sg.cdf.ws.service.RequestBuilder;

public class DistributionServiceImpl implements DistributionService {

	@Override
	public void distribute(RequestBuilder factory) {
		try {
			runDistribute(factory);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String run(RequestBuilder factory) {
		ContentDistributionRequest req;
		try {
			req = createRequest(factory);
			return req.execute();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	void runDistribute(RequestBuilder builder) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		ContentDistributionRequest req = createRequest(builder);

		// 添加发布器，注意该发布器必须在扩展中进行注册
		// 创建发布任务,并运行
		req.createDistributionJob(true);
	}

	private ContentDistributionRequest createRequest(RequestBuilder builder)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
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
				req.setParameterValue(parameters.get(i).getName(), parameters
						.get(i).getValue());
			}
		}

		space = builder.getContentProvider();
		if (space != null) {
			instance = getInstance(space.getBundle(), space.getClas());
			if (instance instanceof ContentProvider) {
				req.registerContentProvider((ContentProvider) instance);
			}
		}

		ArrayList<NameSpace> ns = builder.getDistributors();
		if (ns != null) {
			for (int i = 0; i < ns.size(); i++) {
				instance = getInstance(ns.get(i).getBundle(), ns.get(i)
						.getClas());
				if (instance instanceof Distributor) {
					req.registeDistributor((Distributor) instance);
				}
			}
		}
		return req;
	}

	private Object getInstance(String bundle, String clas)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class<?> bundleLoadedClass = CDF.getBundleLoadedClass(bundle, clas);
		return bundleLoadedClass.newInstance();
	}

	@Override
	public void simpleApplicationEmailNotice(AppEmailNotice appEmailNotice) {
		String appName = appEmailNotice.getAppName();
		String name = appEmailNotice.getRequestName();
		String receiverAddress = appEmailNotice.getReceiverAddress();
		String reveiverName = appEmailNotice.getReceiverName();
		String title = appEmailNotice.getSubject();
		String body = appEmailNotice.getHtmlEmailBody();

		ContentDistributionRequest req = new ContentDistributionRequest(appName
				+ "-" + name);

		NameSpace nameSpace = appEmailNotice.getPersistence();
		if (nameSpace == null) {
			nameSpace = new NameSpace();
			nameSpace.setBundle("com.sg.cdf.persistence.mongodb");
			nameSpace
					.setClas("com.sg.cdf.persistence.mongodb.MongoDBJsonDistribution");
		}

		try {
			Object instance = getInstance(nameSpace.getBundle(),
					nameSpace.getClas());
			if (instance instanceof IDistributionPersistence) {
				req.setPersistence((IDistributionPersistence) instance);
			}

			req.setParameterValue("title", title);
			req.setParameterValue("message", body);
			req.setParameterValue("fromName", appName);
			req.setParameterValue("toName", reveiverName);
			req.setParameterValue("to", receiverAddress);

			req.registeDistributor(new HTMLEmail());

			req.createDistributionJob(true);
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}

	}

	@Override
	public byte[] downloadFile(String context, String filename) {
		File file = new File(CDF.FILE_SERVER_PATH
				+ File.separator + context + File.separator + filename);
		if(file.isFile()){
			try {
				return getBytes(file);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}else{
			return null;
		}
	}
	
	private byte[] getBytes(File file)
			throws IOException {
		
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();
		
		/*
		 * You cannot create an array using a long type. It needs to be an int
		 * type. Before converting to an int type, check to ensure that file is
		 * not loarger than Integer.MAX_VALUE;
		 */
		if (length > Integer.MAX_VALUE) {
			is.close();
			return null;
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while ((offset < bytes.length)
				&& ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)) {

			offset += numRead;

		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			is.close();
			throw new IOException("Could not completely read file ");
		}

		is.close();
		return bytes;
	}

}
