package com.sg.cdf.persistence.mongodb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleReference;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sg.cdf.core.persistence.IDistributionPersistence;
import com.sg.cdf.core.request.DistributionJob;
import com.sg.cdf.core.request.IDistributionJob;

public class MongoDBByteDistribution implements IDistributionPersistence {
	

	private static final String COL_NAME = "cdf_distribution";
	/**
	 * 
	 */
	private static final long serialVersionUID = -6903098167412274351L;

	@Override
	public List<IDistributionJob> getDistributions() {
		List<IDistributionJob> result = new ArrayList<IDistributionJob>();
		DBCollection col = Activator.getDb().getCollection(COL_NAME);
		DBCursor cursor = col.find();
		while (cursor.hasNext()) {
			try {
				final DBObject data = cursor.next();
				byte[] javaObjectByte = (byte[]) data.get("job");
				if (javaObjectByte == null) {
					continue;
				}
				InputStream inputStream = new ByteArrayInputStream(
						javaObjectByte);
				ObjectInputStream in = new ObjectInputStream(inputStream) {
					@Override
					protected Class<?> resolveClass(ObjectStreamClass desc)
							throws IOException, ClassNotFoundException {
						try {
							Class<?> clas = super.resolveClass(desc);
							return clas;
						} catch (Exception e) {
						}
						String className = desc.getName();
						return resolveClassInRegistery(data, className);
					}
				};

				Object javaObject = in.readObject();
				result.add((DistributionJob) javaObject);
				in.close();
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
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

	@Override
	public void save(IDistributionJob distributionJob) {
		try {
			final DBObject data = new BasicDBObject();
			String id = distributionJob.getRequestId();
			data.put("id", id);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(outputStream) {
				@Override
				protected void writeClassDescriptor(ObjectStreamClass desc)
						throws IOException {
					Class<?> forClass = desc.forClass();
					ClassLoader cl = forClass.getClassLoader();
					if (cl instanceof BundleReference) {
						Bundle bundle = ((BundleReference) cl).getBundle();
						registerClass(data, bundle.getSymbolicName(),
								forClass.getName());
					}
					super.writeClassDescriptor(desc);
				}
			};
			out.writeObject(distributionJob);
			data.put("job", outputStream.toByteArray());
			out.close();
			outputStream.close();
			DBCollection col = Activator.getDb().getCollection(
					COL_NAME);
			col.update(new BasicDBObject().append("id", id), data, true, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	protected void registerClass(DBObject data, String bundleName,
			String className) {
		Object classMap = data.get("classMap");
		if (classMap == null) {
			classMap = new ArrayList<String[]>();
		}
		((List<String[]>) classMap).add(new String[] { bundleName, className });
		data.put("classMap", classMap);
	}

	private Class<?> resolveClassInRegistery(DBObject data, String className)
			throws ClassNotFoundException {
		List<?> classMap = (List<?>) data.get("classMap");
		if (classMap != null) {
			for (int i = 0; i < classMap.size(); i++) {
				List<?> classEntry = (List<?>) classMap.get(i);
				if (classEntry.get(1).equals(className)) {
					Bundle bundle = Platform.getBundle((String) classEntry
							.get(0));
					Assert.isNotNull(bundle, "Can not find bundle, name:"
							+ bundle.getSymbolicName());
					return bundle.loadClass(className);
				}
			}
		}
		throw new ClassNotFoundException();
	}

}
