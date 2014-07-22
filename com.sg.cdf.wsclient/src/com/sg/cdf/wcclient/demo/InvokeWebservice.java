package com.sg.cdf.wcclient.demo;

import java.util.List;


import com.sg.cdf.wsclient.ArrayOfNameSpace;
import com.sg.cdf.wsclient.ArrayOfParameter;
import com.sg.cdf.wsclient.DistributionService;
import com.sg.cdf.wsclient.DistributionServicePortType;
import com.sg.cdf.wsclient.NameSpace;
import com.sg.cdf.wsclient.ObjectFactory;
import com.sg.cdf.wsclient.Parameter;
import com.sg.cdf.wsclient.RequestBuilder;

public class InvokeWebservice {

	public static void main(String[] args) {
		DistributionService service = new DistributionService();
		DistributionServicePortType servicePort = service
				.getDistributionServicePort();

		ObjectFactory of = new ObjectFactory();
	
		RequestBuilder builder = of.createRequestBuilder();

		builder.setName(of.createRequestBuilderName("Web Service �������ݷ��񲢴��ݲ���"));
		//�־û�ע��
		NameSpace nameSpace = of.createNameSpace();
		nameSpace.setBundle(of.createNameSpaceBundle("com.sg.cdf.persistence.mongodb"));
		nameSpace.setClas(of.createNameSpaceClas("com.sg.cdf.persistence.mongodb.MongoDBJsonDistribution"));
		builder.setPersistence(of.createRequestBuilderPersistence(nameSpace));
		
		//���ݲ���
		ArrayOfParameter arrayOfParameter = of.createArrayOfParameter();
		List<Parameter> parameterList = arrayOfParameter.getParameter();
		
		Parameter parameter = of.createParameter();
		parameter.setName(of.createParameterName("templateURL"));
		parameter.setValue(of.createParameterValue("http://localhost:10080/html/sample.rptdesign"));
		parameterList.add(parameter);
		
		parameter = of.createParameter();
		parameter.setName(of.createParameterName("dataURL"));
		parameter.setValue(of.createParameterValue("http://localhost:10080/html/xmlpo.xml"));
		parameterList.add(parameter);

		builder.setParameters(of.createRequestBuilderParameters(arrayOfParameter));
		
		//ע�������ṩ��
		nameSpace = of.createNameSpace();
		nameSpace.setBundle(of.createNameSpaceBundle("com.sg.cdf.customerized"));
		nameSpace.setClas(of.createNameSpaceClas("com.sg.cdf.demo.demo2.contentprovider.ContentProviderDemo"));
		builder.setContentProvider(of.createRequestBuilderContentProvider(nameSpace));
		
		//ע�뷢����
		ArrayOfNameSpace arrayOfNameSpace = of.createArrayOfNameSpace();
		List<NameSpace> nameSpaceList = arrayOfNameSpace.getNameSpace();
		
		nameSpace = of.createNameSpace();
		nameSpace.setBundle(of.createNameSpaceBundle("com.sg.cdf.customerized"));
		nameSpace.setClas(of.createNameSpaceClas("com.sg.cdf.demo.demo2.distributor.DistributorByEmailWithParameter"));
		nameSpaceList.add(nameSpace);
		
		builder.setDistributors(of.createRequestBuilderDistributors(arrayOfNameSpace));
		
		servicePort.distribute(builder);
	}
}
