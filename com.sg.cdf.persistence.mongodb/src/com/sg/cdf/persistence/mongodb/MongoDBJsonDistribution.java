package com.sg.cdf.persistence.mongodb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.sg.cdf.core.persistence.DistributionJsonPersistence;
import com.sg.cdf.core.request.ContentDistributionJob;
import com.sg.cdf.core.request.IDistributionJob;

public class MongoDBJsonDistribution extends
		DistributionJsonPersistence<ContentDistributionJob> {

	private static final String COL_NAME = "cdf_distribution1";
	/**
	 * 
	 */
	private static final long serialVersionUID = 6384367452221655302L;

	public MongoDBJsonDistribution() {
		super();
	}

	@Override
	public void save(IDistributionJob distributionJob, String json) {
		String id = distributionJob.getRequestId();

		DBObject data;
		try {
			data = (DBObject) JSON.parse(json);
		} catch (Exception e) {
			data = new BasicDBObject();
		}
		data.put("id", id);

		DBCollection col = Activator.getDb().getCollection(COL_NAME);
		col.update(new BasicDBObject().append("id", id), data, true, false);
	}

	@Override
	public List<IDistributionJob> getDistributions() {
		List<IDistributionJob> result = new ArrayList<IDistributionJob>();
		DBCollection col = Activator.getDb().getCollection(COL_NAME);
		DBCursor cursor = col.find();
		while (cursor.hasNext()) {
			DBObject data = cursor.next();
			String content = JSON.serialize(data);
			IDistributionJob job = fromJson(content);
			if (job != null) {
				result.add(job);
			}
		}
		return result;
	}

	@Override
	public void remove(List<IDistributionJob> jobs) {
		Set<String> ids = new HashSet<String>();
		for (IDistributionJob job : jobs) {
			ids.add(job.getRequestId());
		}
		DBCollection col = Activator.getDb().getCollection(COL_NAME);
		col.remove(new BasicDBObject().append("id",
				new BasicDBObject().append("$in", ids)));
	}

}
