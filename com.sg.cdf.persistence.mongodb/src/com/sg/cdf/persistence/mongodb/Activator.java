package com.sg.cdf.persistence.mongodb;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.MongoClientOptions.Builder;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	private static DB db;
	private static MongoClient mongo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		createDBFromConfig();
	}

	private static void createDBFromConfig() {
		InputStream is = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(System.getProperty("user.dir") //$NON-NLS-1$
					+ "/configuration/cdf.properties"); //$NON-NLS-1$
			is = new BufferedInputStream(fis);
			Properties props = new Properties();
			props.load(is);

			String dbname = props.getProperty("db.name"); //$NON-NLS-1$
			mongo = createMongoClient(props);
			db = mongo.getDB(dbname);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

	public static DB getDb() {
		if(db == null){
			createDBFromConfig();
		}
		return db;
	}
	
	
	public static MongoClient getMongo() {
		return mongo;
	}

	public static MongoClient createMongoClient(Properties props)
			throws UnknownHostException {
		String host = props.getProperty("db.host"); //$NON-NLS-1$
		String _port = props.getProperty("db.port");
		int port = _port == null ? 10001 : Integer.parseInt(_port); //$NON-NLS-1$
		ArrayList<ServerAddress> serverList = null;
		String replicaSet = props.getProperty("db.replicaSet"); //$NON-NLS-1$
		if (replicaSet != null && replicaSet.length() > 0) {
			serverList = new ArrayList<ServerAddress>();
			String[] arr = replicaSet.split(" ");
			for (int i = 0; i < arr.length; i++) {
				String[] ari = arr[i].split(":");
				ServerAddress address = new ServerAddress(ari[0],
						Integer.parseInt(ari[1]));
				serverList.add(address);
			}
		}

		Builder builder = MongoClientOptions.builder();
		builder.autoConnectRetry("true".equalsIgnoreCase(props //$NON-NLS-1$
				.getProperty("db.options.autoConnectRetry"))); //$NON-NLS-1$
		builder.connectionsPerHost(Integer.parseInt(props
				.getProperty("db.options.connectionsPerHost"))); //$NON-NLS-1$
		builder.maxWaitTime(Integer.parseInt(props
				.getProperty("db.options.maxWaitTime"))); //$NON-NLS-1$
		builder.socketTimeout(Integer.parseInt(props
				.getProperty("db.options.socketTimeout"))); //$NON-NLS-1$
		builder.connectTimeout(Integer.parseInt(props
				.getProperty("db.options.connectTimeout"))); //$NON-NLS-1$
		builder.threadsAllowedToBlockForConnectionMultiplier(Integer.parseInt(props
				.getProperty("db.options.threadsAllowedToBlockForConnectionMultiplier"))); //$NON-NLS-1$
		ServerAddress address = new ServerAddress(host, port);
		if (serverList != null) {
			return new MongoClient(serverList);
		} else {
			return new MongoClient(address, builder.build());
		}
	}

}
