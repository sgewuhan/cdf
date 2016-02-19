package com.bizvision.dpf.persistence;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.mongodb.DB;

@WebService(endpointInterface = "com.bizvision.dpf.persistence.IPersistence")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class Persistence implements IPersistence {

	private DB db;

	public Persistence(DB db) {
		this.db = db;
	}

	@Override
	public String listTaskAllocators() {
		// TODO Auto-generated method stub
		return db+"listTaskAllocators";
	}

	@Override
	public void updateTaskAllocatorState(String url, int state) {
		// TODO Auto-generated method stub
		System.out.println(url);
	}

	@Override
	public void updateProcessor(ProcessorPersistable persistable) {
		// TODO Auto-generated method stub
		System.out.println(persistable.getId());
	}

	
}
