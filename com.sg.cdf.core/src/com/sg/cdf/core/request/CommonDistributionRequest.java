package com.sg.cdf.core.request;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sg.cdf.core.CDF;
import com.sg.cdf.core.DistributionConfig;
import com.sg.cdf.core.Processor;
import com.sg.cdf.core.distributor.Distributor;
import com.sg.cdf.core.persistence.IDistributionPersistence;

/**
 * @author Administrator
 *
 */
public class CommonDistributionRequest implements IDistributionRequest {

	private String id;

	private String name;

	private String typeName;

	private List<Distributor> distributors;

	private Map<String, String> meta;

	private String[] parameters;

	private IDistributionPersistence persistence;

	private Map<String, Object> parameterValueMap;

	CommonDistributionRequest(String name) {
		this();
		setName(name);
	}

	CommonDistributionRequest() {
		long millis = System.currentTimeMillis();
		setId(Long.toHexString(millis));
	}

	/**
	 * 根据扩展点实例化
	 * 
	 * @param distExtId
	 */
	CommonDistributionRequest(String distExtId, String name) {
		this(name);
		init(distExtId);
	}

	/**
	 * 从扩展点获取请求
	 * 
	 * @param distExtId
	 */
	protected void init(String distExtId) {
		DistributionConfig conf = CDF.getDistributions(distExtId);
		setTypeName(conf.getName());
		setPersistence(conf.getPersistence());
		setDistributors(conf.getDistributors());
		setParameters(conf.getParameters());
	}

	final public String getId() {
		return id;
	}

	final public void setId(String id) {
		this.id = id;
	}

	final public String getName() {
		return name;
	}

	final public void setName(String name) {
		this.name = name;
	}

	public IDistributionJob createDistributionJob() {
		return createDistributionJob(false);
	}

	public IDistributionJob createDistributionJob(boolean runImediately) {
		String name = getName();
		IDistributionJob job = new DistributionJob(name, this);
		if (runImediately) {
			job.launch();
		}
		return job;
	}

	public String execute(){
		IDistributionJob job = createDistributionJob(false);
		String message = job.run();
		return message;
	}
	
	final public List<Distributor> getDistributors() {
		return distributors;
	}

	final public void setDistributors(List<Distributor> distributors) {
		this.distributors = distributors;
	}

	/**
	 * 为请求注册一个发布器，注册后将自动为该发布器传递来自本请求的参数
	 * @param distributor
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	final public void registeDistributor(Distributor distributor) {
		if (this.distributors == null) {
			distributors = new ArrayList<Distributor>();
		}
		String[] parameters = getParameters();
		for (String parameter : parameters) {
			try {
				((Processor)distributor).setParameterValue(parameter,
						getParameterValue(parameter));
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
			}
		}
		distributors.add(distributor);
	}
	
	protected Object getParameterValue(String parameter) {
		if (parameterValueMap == null) {
			return null;
		}
		return parameterValueMap.get(parameter);
	}

	final public String getTypeName() {
		return typeName;
	}

	final public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setParameterValue(String parameter, Object value) {
		if (parameter == null) {
			return;
		}

		// 扩充参数表
		if (parameters == null) {
			parameters = new String[0];
		}

		String[] target = new String[parameters.length + 1];
		System.arraycopy(parameters, 0, target, 0, parameters.length);
		System.arraycopy(new String[] { parameter }, 0, target,
				parameters.length, 1);
		parameters = target;

		if (parameterValueMap == null) {
			parameterValueMap = new HashMap<String, Object>();
		}
		parameterValueMap.put(parameter, value);

		List<Distributor> distributors = getDistributors();
		if (distributors != null && distributors.size() > 0) {
			for (int i = 0; i < distributors.size(); i++) {
				try {
					((Processor)distributors.get(i)).setParameterValue(parameter, value);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}

	final public String[] getParameters() {
		return parameters;
	}

	final public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}

	final public IDistributionPersistence getPersistence() {
		return persistence;
	}

	final public void setPersistence(IDistributionPersistence persistence) {
		this.persistence = persistence;
	}

	final public void setMeta(Map<String, String> meta) {
		this.meta = meta;
	}

	final public Map<String, String> getMeta() {
		return meta;
	}

	final public void setMetaData(String key, String value) {
		if (meta == null) {
			meta = new HashMap<String, String>();
		}
		meta.put(key, value);
	}

	final public String getMetaData(String key) {
		if (meta == null) {
			return null;
		}
		return meta.get(key);
	}

}
