package com.bizvision.dpf.persistence;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.mongodb.DB;

@WebService(endpointInterface = "com.bizvision.dpf.persistence.IPersistence")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class MongoDBPersistence implements IPersistence {

	private DB db;

	public MongoDBPersistence(DB db) {
		this.db = db;
	}

	@Override
	public void doSaveTaskAllocator(String url) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getAllTaskAllocator() {
		// TODO Auto-generated method stub
		return null;
	}

}
