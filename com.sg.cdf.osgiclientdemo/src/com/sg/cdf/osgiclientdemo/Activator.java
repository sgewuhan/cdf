package com.sg.cdf.osgiclientdemo;

import java.util.ArrayList;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import com.sg.cdf.ws.service.DistributionService;
import com.sg.cdf.ws.service.NameSpace;
import com.sg.cdf.ws.service.Parameter;
import com.sg.cdf.ws.service.RequestBuilder;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	@SuppressWarnings("rawtypes")
	private ServiceTracker tracker;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		tracker = new ServiceTracker(bundleContext, DistributionService.class.getName(), null) {
            @Override
            public Object addingService(ServiceReference reference) {
                Object service = super.addingService(reference);
                if (service instanceof DistributionService) {
                    useService2((DistributionService) service);
                }
                return service;
            }
        };
        tracker.open();
	}
	
	protected void useService2(DistributionService service) {
		RequestBuilder factory  = new RequestBuilderImpl();
		factory.setName("执行文档转换");
		
		NameSpace nameSpace = new NameSpace();
		nameSpace.setBundle("com.sg.cdf.persistence.mongodb");
		nameSpace.setClas("com.sg.cdf.persistence.mongodb.MongoDBJsonDistribution");
		factory.setPersistence(nameSpace);

		//传递参数
		ArrayList<Parameter> parameterList = new ArrayList<Parameter>();
		
		Parameter parameter = new Parameter();
		parameter.setName("sourcePath");
		parameter.setValue("h:/BizVision最终用户协议.docx");
		parameterList.add(parameter);
		
		parameter = new Parameter();
		parameter.setName("targetPath");
		parameter.setValue("h:/abc.svg");
		parameterList.add(parameter);
		
		parameter = new Parameter();
		parameter.setName("targetMediaType");
		parameter.setValue("svg");
		parameterList.add(parameter);

		factory.setParameters(parameterList);
		
		//注入内容提供者
		nameSpace = new NameSpace();
		nameSpace.setBundle("com.sg.cdf.contentconverter");
		nameSpace.setClas("com.sg.cdf.contentconverter.contentprovider.ConvertContentProvider");
		factory.setContentProvider(nameSpace);
		
		//注入发布器
		ArrayList<NameSpace> nameSpaceList = new ArrayList<NameSpace>();
		nameSpace = new NameSpace();
		nameSpace.setBundle("com.sg.cdf.core");
		nameSpace.setClas("com.sg.cdf.core.distributor.DummyDistributor");
		nameSpaceList.add(nameSpace);
		
		factory.setDistributors(nameSpaceList);
		
		service.distribute(factory);
	}

	protected void useService1(DistributionService service) {
		RequestBuilder factory  = new RequestBuilderImpl();
		factory.setName("Web Service 调用内容服务并传递参数");
		
		NameSpace nameSpace = new NameSpace();
		nameSpace.setBundle("com.sg.cdf.persistence.mongodb");
		nameSpace.setClas("com.sg.cdf.persistence.mongodb.MongoDBJsonDistribution");
		factory.setPersistence(nameSpace);

		//传递参数
		ArrayList<Parameter> parameterList = new ArrayList<Parameter>();
		
		Parameter parameter = new Parameter();
		parameter.setName("templateURL");
		parameter.setValue("http://localhost:10080/html/sample.rptdesign");
		parameterList.add(parameter);
		
		parameter = new Parameter();
		parameter.setName("dataURL");
		parameter.setValue("http://localhost:10080/html/xmlpo.xml");
		parameterList.add(parameter);

		factory.setParameters(parameterList);
		
		//注入内容提供者
		nameSpace = new NameSpace();
		nameSpace.setBundle("com.sg.cdf.customerized");
		nameSpace.setClas("com.sg.cdf.demo.demo2.contentprovider.ContentProviderDemo");
		factory.setContentProvider(nameSpace);
		
		//注入发布器
		ArrayList<NameSpace> nameSpaceList = new ArrayList<NameSpace>();
		nameSpace = new NameSpace();
		nameSpace.setBundle("com.sg.cdf.customerized");
		nameSpace.setClas("com.sg.cdf.demo.demo2.distributor.DistributorByEmailWithParameter");
		nameSpaceList.add(nameSpace);
		
		factory.setDistributors(nameSpaceList);
		
		service.distribute(factory);
	}


	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		tracker.close();
		Activator.context = null;
	}

}
