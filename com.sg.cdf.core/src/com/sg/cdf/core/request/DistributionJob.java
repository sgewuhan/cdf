package com.sg.cdf.core.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

import com.sg.cdf.core.CDF;
import com.sg.cdf.core.distributor.Distributor;
import com.sg.cdf.core.persistence.IDistributionPersistence;

public class DistributionJob implements IDistributionJob,
		Comparable<DistributionJob>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3734317469977076588L;

	private String name;

	private Date runningAt;

	private Date sleepingAt;

	private Date awakeAt;

	private Date doneAt;

	private Date scheduleAt;

	private Date aboutToRunAt;

	private List<Distributor> distributors;

	private String requestId;

	private String requestName;

	private String requestTypeName;

	private IDistributionPersistence persistence;

	private int state;

	private String resultDetail;

	private String result;

	private transient Job job;

	private Map<String, String> meta;

	private int serverity;

	DistributionJob(String name, CommonDistributionRequest request) {

		setRequestId(request.getId());

		setRequestName(request.getName());

		setRequestTypeName(request.getTypeName());

		setMeta(request.getMeta());

		setPersistence(request.getPersistence());

		setName(name);

		addDistributorList(request.getDistributors());

	}

	DistributionJob(DistributionJob job, CommonDistributionRequest request) {

		setRequestId(request.getId());

		setRequestName(request.getName());

		// 从job中取出
		setRequestTypeName(job.getRequestTypeName());

		setMeta(job.getMeta());

		setPersistence(job.getPersistence());

		setName(job.getName());

		addDistributorList(job.getDistributors());

	}

	DistributionJob(DistributionJob job) {

		setRequestId(job.getRequestId());

		setRequestName(job.getRequestName());

		setRequestTypeName(job.getRequestTypeName());

		setMeta(job.getMeta());

		setPersistence(job.getPersistence());

		setName(job.getName());

		addDistributorList(job.getDistributors());

	}

	final public void launch() {
		job = new Job(getName()) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				MultiStatus status = new MultiStatus(CDF.PLUGIN_ID, Status.OK,
						null, null);
				execute(status);
				return status;
			}

		};
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void aboutToRun(final IJobChangeEvent event) {
				setAboutToRunAt(new Date());
				statusChanged(job);
			}

			@Override
			public void awake(final IJobChangeEvent event) {
				setAwakeAt(new Date());
				statusChanged(job);
			}

			@Override
			public void done(final IJobChangeEvent event) {
				setDoneAt(new Date());
				statusChanged(job);
			}

			@Override
			public void running(final IJobChangeEvent event) {
				setRunningAt(new Date());
				statusChanged(job);
			}

			@Override
			public void scheduled(final IJobChangeEvent event) {
				setScheduleAt(new Date());
				statusChanged(job);
			}

			@Override
			public void sleeping(final IJobChangeEvent event) {
				setSleepingAt(new Date());
				statusChanged(job);
			}
		});
		job.schedule();
	}

	public String execute() {
		return execute(null);
	}
	
	public String execute(MultiStatus status) {
		if (distributors == null || distributors.isEmpty()) {
			if (status != null) {
				status.add(new Status(Status.WARNING, CDF.PLUGIN_ID,
						"Distribution job must has one distributor at least."));
			}
			return "Distribution job must has one distributor at least.";
		}

		StringBuffer sb = new StringBuffer();
		/*
		 * 执行发布
		 */
		for (int i = 0; i < distributors.size(); i++) {
			Distributor distributor = distributors.get(i);
			IStatus result = distributor.run(this);
			if (result != null) {
				if (status != null) {
					status.add(result);
				}
				sb.append(result.getMessage());
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Distributor> getDistributors() {
		return distributors;
	}

	public void setDistributors(final List<Distributor> distributors) {
		this.distributors = distributors;
	}

	public boolean addDistributor(final Distributor dist) {
		if (distributors == null) {
			distributors = new ArrayList<Distributor>();
		}
		return distributors.add(dist);
	}

	public boolean addDistributorList(final List<Distributor> dists) {
		if (distributors == null) {
			distributors = new ArrayList<Distributor>();
		}
		return distributors.addAll(dists);
	}

	public void clearDistributors() {
		if (distributors == null) {
			return;
		}
		distributors.clear();
	}

	public boolean removeDistributor(final Distributor dist) {
		if (distributors == null) {
			return false;
		}
		return distributors.remove(dist);
	}

	public int compareTo(final DistributionJob job2) {
		return requestId.compareTo(job2.requestId);
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void wakeUp() throws Exception {
		if (job == null) {
			throw new Exception("Job need launch first!");
		}
		job.wakeUp();
	}

	public void schedule() throws Exception {
		if (job == null) {
			throw new Exception("Job need launch first!");
		}
		job.schedule();
	}

	public void sleep() throws Exception {
		if (job == null) {
			throw new Exception("Job need launch first!");
		}
		job.sleep();
	}

	public void cancel() throws Exception {
		if (job == null) {
			throw new Exception("Job need launch first!");
		}
		job.cancel();
	}

	public Date getScheduleAt() {
		return scheduleAt;
	}

	public void setScheduleAt(final Date scheduleAt) {
		this.scheduleAt = scheduleAt;
	}

	public Date getRunningAt() {
		return runningAt;
	}

	public void setRunningAt(final Date runningAt) {
		this.runningAt = runningAt;
	}

	public Date getSleepingAt() {
		return sleepingAt;
	}

	public void setSleepingAt(final Date sleepingAt) {
		this.sleepingAt = sleepingAt;
	}

	public Date getAwakeAt() {
		return awakeAt;
	}

	public void setAwakeAt(final Date awakeAt) {
		this.awakeAt = awakeAt;
	}

	public Date getDoneAt() {
		return doneAt;
	}

	public void setDoneAt(final Date doneAt) {
		this.doneAt = doneAt;
	}

	public Date getAboutToRunAt() {
		return aboutToRunAt;
	}

	public void setAboutToRunAt(final Date aboutToRunAt) {
		this.aboutToRunAt = aboutToRunAt;
	}

	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(final String requestName) {
		this.requestName = requestName;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(final String requestId) {
		this.requestId = requestId;
	}

	public void setRequestTypeName(final String requestTypeName) {
		this.requestTypeName = requestTypeName;
	}

	public String getRequestTypeName() {
		return requestTypeName;
	}

	public IDistributionPersistence getPersistence() {
		return persistence;
	}

	public void setPersistence(IDistributionPersistence persistence) {
		this.persistence = persistence;
	}

	public static DistributionJob getInstance(String name,
			CommonDistributionRequest request) {
		return new DistributionJob(name, request);
	}

	protected void statusChanged(Job job) {
		readJobStatus(job);
		// 当状态发生改变的时候需要持久化
		if (persistence == null) {
			return;
		}
		persistence.save(this);
	}

	public void setResultDetail(String detail) {
		this.resultDetail = detail;
	}

	public String getResultDetail() {
		return resultDetail;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	private void readJobStatus(Job job) {
		setState(job.getState());

		IStatus status = job.getResult();
		if (status == null) {
			setResultDetail(null);
			setResult(null);
			return;
		}

		int code = status.getSeverity();
		setServerity(code);
		String message = status.getMessage();
		message = message.length() > 0 ? " :" + message : message;
		switch (code) {
		case IStatus.CANCEL:
			setResult("Canceled ");
		case IStatus.OK:
			setResult("OK ");
		case IStatus.ERROR:
			setResult("Error " + message);
		case IStatus.WARNING:
			setResult("Warning " + message);
		case IStatus.INFO:
			setResult("Information " + message);
		default:
			break;
		}

		IStatus[] children = status.getChildren();
		if (children != null && children.length > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < children.length; i++) {
				int serity = children[i].getSeverity();
				switch (serity) {
				case IStatus.CANCEL:
					sb.append("Canceled: ");
					break;
				case IStatus.OK:
					sb.append("OK: ");
					break;
				case IStatus.ERROR:
					sb.append("Error: ");
					break;
				case IStatus.WARNING:
					sb.append("Warning: ");
					break;
				case IStatus.INFO:
					sb.append("Infomation: ");
					break;
				default:
					break;
				}

				sb.append(children[i].getMessage());
				Throwable exception = children[i].getException();
				if (exception != null) {
					sb.append(" ");
					sb.append(exception.getMessage());
				}
				sb.append("\n");
			}
			setResultDetail(sb.toString());
		}
	}

	public void setServerity(int code) {
		this.serverity = code;
	}

	public int getServerity() {
		return serverity;
	}

	public void setMeta(Map<String, String> meta) {
		this.meta = meta;
	}

	public Map<String, String> getMeta() {
		return meta;
	}

	public boolean isFinished() {
		return getDoneAt() != null;
	}

	@Override
	protected DistributionJob clone() throws CloneNotSupportedException {
		return new DistributionJob(this);
	}
}
