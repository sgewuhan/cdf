package com.sg.cdf.monitor.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.sg.cdf.core.distributor.Distributor;
import com.sg.cdf.core.persistence.IDistributionPersistence;
import com.sg.cdf.core.request.DistributionJob;
import com.sg.cdf.core.request.IDistributionJob;
import com.sg.cdf.core.request.RestartDistributionRequest;

public class MonitorView extends ViewPart {

	private static final String TIMEFORMAT = "%1$tY/%1$tm/%1$td %1$tH:%1$tM:%1$tS";

	public boolean hideFinishedJob = false;

	private TableViewer viewer;
	private Text statusText;

	private IDistributionPersistence persistence;

	private TableViewer metaDataTableViewer;

	private TreeViewer distributorsTreeViewer;

	public void showData(String name, IDistributionPersistence persistence) {
		setPartName(name);
		this.persistence = persistence;
		viewer.setInput(persistence.getDistributions());
	}

	@Override
	public void createPartControl(Composite parent) {
		SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);

		createViewer(sashForm);

		createFolder(sashForm);

		sashForm.setWeights(new int[] { 70, 30 });

	}

	private void createFolder(Composite parent) {
		CTabFolder folder = new CTabFolder(parent, SWT.BOTTOM | SWT.FLAT);

		createStatusTabItem(folder);

		createRequestMetaItem(folder);

		createDistributorItem(folder);

		folder.setSelection(0);
	}

	private void createDistributorItem(CTabFolder folder) {
		CTabItem item = new CTabItem(folder, SWT.NONE);
		item.setText("Distributors");
		
		distributorsTreeViewer = new TreeViewer(folder,SWT.NONE); 
		distributorsTreeViewer.setAutoExpandLevel(TreeViewer.ALL_LEVELS);
		distributorsTreeViewer.setContentProvider(new ITreeContentProvider() {
			
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
			
			@Override
			public void dispose() {
			}
			
			@Override
			public boolean hasChildren(Object element) {
				return element instanceof Distributor;
			}
			
			@Override
			public Object getParent(Object element) {
				return null;
			}
			
			@Override
			public Object[] getElements(Object inputElement) {
				return (Object[])inputElement;
			}
			
			@Override
			public Object[] getChildren(Object parentElement) {
				if(parentElement instanceof Distributor){
					String[] parameters = getParameters(parentElement.getClass());
					Object[] result = new Object[parameters.length];
					for (int i = 0; i < parameters.length; i++) {
						Object value = getParameterValue(parameters[i],parentElement);
						result[i] = new Object[]{parameters[i],value};
					}
					return result;
				}
				return null;
			}
		});
		
		distributorsTreeViewer.setLabelProvider(new LabelProvider(){
			@Override
			public String getText(Object element) {
				if(element instanceof Distributor){
					return element.getClass().getSimpleName();
				}else if(element instanceof Object[]){
					return ((Object[])element)[0].toString()+": "+((Object[])element)[1];
				}
				return "";
			}
		});
		
		item.setControl(distributorsTreeViewer.getControl());
	}
	
	private String[] getParameters(Class<?> clas){
		Field[] fields = clas.getDeclaredFields();
		if(fields==null){
			return new String[0];
		}
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fieldNames[i] = fields[i].getName();
		}
		return fieldNames;
	}
	
	private Object getParameterValue(String parameter,Object obj){
		try {
			Field field = obj.getClass().getDeclaredField(parameter);
			return field.get(obj);
		} catch (NoSuchFieldException e) {
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
		return null;
	}

	private void createRequestMetaItem(CTabFolder folder) {
		CTabItem item = new CTabItem(folder, SWT.NONE);
		item.setText("Request");
		metaDataTableViewer = new TableViewer(folder, SWT.NONE);
		metaDataTableViewer.getTable().setHeaderVisible(true);
		TableViewerColumn col = new TableViewerColumn(metaDataTableViewer, SWT.LEFT);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				@SuppressWarnings("rawtypes")
				Entry entry = ((Entry) element);
				return entry.getKey().toString();
			}
		});

		col.getColumn().setWidth(160);
		col.getColumn().setText("Request Properties");

		col = new TableViewerColumn(metaDataTableViewer, SWT.LEFT);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				@SuppressWarnings("rawtypes")
				Entry entry = ((Entry) element);
				Object value = entry.getValue();
				return value == null ? "" : value.toString();
			}
		});
		col.getColumn().setWidth(240);
		col.getColumn().setText("");

		metaDataTableViewer.setContentProvider(ArrayContentProvider.getInstance());
		item.setControl(metaDataTableViewer.getControl());
	}

	private void createStatusTabItem(CTabFolder folder) {
		CTabItem item = new CTabItem(folder, SWT.NONE);
		item.setText("Result");
		statusText = new Text(folder, SWT.MULTI | SWT.BORDER);
		statusText.setEditable(false);
		item.setControl(statusText);
	}

	private void createViewer(SashForm sashForm) {
		viewer = new TableViewer(sashForm, SWT.MULTI | SWT.H_SCROLL);
		viewer.getTable().setHeaderVisible(true);

		viewer.setComparator(new ViewerComparator() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public int compare(Viewer testViewer, Object e1, Object e2) {
				return ((Comparable) e1).compareTo(e2);
			}
		});

		/*
		 * id列
		 */
		ColumnLabelProvider labelProvider = new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((DistributionJob) element).getRequestId();
			}
		};
		createColumn("Id", labelProvider, 120);

		/*
		 * 状态列
		 */
		labelProvider = new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return getStateText(((DistributionJob) element));
			}

			@Override
			public Image getImage(Object element) {
				return getStateImage(((DistributionJob) element));
			}
		};
		createColumn("State", labelProvider, 100);

		/*
		 * 名称列
		 */
		labelProvider = new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((DistributionJob) element).getName();
			}
		};
		createColumn("Job", labelProvider, 240);

		/*
		 * 类型列
		 */
		labelProvider = new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((DistributionJob) element).getRequestTypeName();
			}
		};
		createColumn("Type", labelProvider, 120);

		/*
		 * 类型列
		 */
		labelProvider = new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((DistributionJob) element).getRequestName();
			}
		};
		createColumn("Name", labelProvider, 120);

		/*
		 * 运行时间
		 */
		labelProvider = new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Date date = ((DistributionJob) element).getRunningAt();
				return date == null ? "" : String.format(TIMEFORMAT, date);
			}
		};
		createColumn("Running", labelProvider, 160);

		// /*
		// * 睡眠时间
		// */
		// labelProvider = new ColumnLabelProvider() {
		// @Override
		// public String getText(Object element) {
		// Date date = ((DistributionJob) element).getSleepingAt();
		// return date == null ? "" : String.format(TIMEFORMAT, date);
		// }
		// };
		// createColumn("Sleeping",labelProvider,160);

		// /*
		// * 唤醒时间
		// */
		// labelProvider = new ColumnLabelProvider() {
		// @Override
		// public String getText(Object element) {
		// Date date = ((DistributionJob) element).getAwakeAt();
		// return date == null ? "" : String.format(TIMEFORMAT, date);
		// }
		// };
		// createColumn("Awake",labelProvider,160);

		/*
		 * 完成时间
		 */
		labelProvider = new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Date date = ((DistributionJob) element).getDoneAt();
				return date == null ? "" : String.format(TIMEFORMAT, date);
			}
		};
		createColumn("Done", labelProvider, 160);

		/*
		 * 结论
		 */
		labelProvider = new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return (((DistributionJob) element)).getResult();
			}
		};
		createColumn("Result", labelProvider, 180);

		// /*
		// * 原数据
		// */
		// labelProvider = new ColumnLabelProvider() {
		// @Override
		// public String getText(Object element) {
		// Map<String, String> meta = (((DistributionJob) element)).getMeta();
		// return meta==null?"":meta.toString();
		// }
		// };
		// createColumn("Detail",labelProvider,240);
		
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection ss = (IStructuredSelection) event
						.getSelection();
				if (ss == null || ss.isEmpty()) {
					statusText.setText("");
					metaDataTableViewer.setInput(null);
					distributorsTreeViewer.setInput(null);
				} else {
					DistributionJob ji = (DistributionJob) ss.getFirstElement();
					String resultDetail = ji.getResultDetail();
					if (resultDetail != null) {
						statusText.setText(resultDetail);
					} else {
						statusText.setText("");
					}
					Map<String, String> meta = ji.getMeta();
					if (meta == null) {
						metaDataTableViewer.setInput(null);
					} else {
						metaDataTableViewer.setInput(meta.entrySet());
					}
					List<Distributor> dis = ji.getDistributors();
					if(dis==null){
						distributorsTreeViewer.setInput(null);
					}else{
						distributorsTreeViewer.setInput(dis.toArray());
					}
				}
			}
		});
		getSite().setSelectionProvider(viewer);
	}

	private void createColumn(String name, ColumnLabelProvider labelProvider,
			int width) {
		TableViewerColumn col = new TableViewerColumn(viewer, SWT.LEFT);
		col.setLabelProvider(labelProvider);
		col.getColumn().setText(name);
		col.getColumn().setWidth(width);
		new TextColumnSorter(viewer, col, labelProvider);
	}

	protected Image getStateImage(DistributionJob job) {
		switch (job.getState()) {
		case Job.RUNNING:
			return PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_ELCL_SYNCED);
		case Job.WAITING:
			return PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_OBJS_INFO_TSK);
		}
		return PlatformUI.getWorkbench().getSharedImages()
				.getImage(ISharedImages.IMG_ELCL_SYNCED_DISABLED);
	}

	private String getStateText(DistributionJob job) {
		switch (job.getState()) {
		case Job.RUNNING:
			return "Running";
		case Job.WAITING:
			return "Waiting";
		case Job.SLEEPING:
			return "Sleeping";
		case Job.NONE:
			if (job.isFinished()) {
				return "Done";
			} else {
				return "Disactivate";
			}
		}
		return "Unknown";
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public void switchHideFinishedJob() {
		hideFinishedJob = !hideFinishedJob;
		if (hideFinishedJob) {
			viewer.setFilters(new ViewerFilter[] { new ViewerFilter() {
				@Override
				public boolean select(Viewer viewer, Object parentElement,
						Object element) {
					return Job.NONE != ((DistributionJob) element).getState();
				}
			} });
		} else {
			viewer.resetFilters();
		}
	}

	public void refresh() {
		if (persistence == null) {
			return;
		}
		viewer.setInput(persistence.getDistributions());
	}

	public void removeFinishedJob() {
		if (persistence == null) {
			return;
		}
		List<IDistributionJob> dis = persistence.getDistributions();
		List<IDistributionJob> jobs = new ArrayList<IDistributionJob>();
		if (dis != null && dis.size() > 0) {
			for (IDistributionJob job : dis) {
				if (Job.NONE == job.getState()) {
					jobs.add(job);
				}
			}
		}
		persistence.remove(jobs);
		refresh();
	}

	public void removeSelectedJob() {
		if (persistence == null) {
			return;
		}
		IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();
		Iterator<?> iterator = selection.iterator();
		List<IDistributionJob> jobs = new ArrayList<IDistributionJob>();
		while (iterator.hasNext()) {
			IDistributionJob job = (IDistributionJob) iterator.next();
			jobs.add(job);
		}
		persistence.remove(jobs);
		refresh();
	}

	public void restartSelectedJob() {
		if (persistence == null) {
			return;
		}
		IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();
		Iterator<?> iterator = selection.iterator();
		while (iterator.hasNext()) {
			DistributionJob job = (DistributionJob) iterator.next();
			RestartDistributionRequest r = new RestartDistributionRequest(
					job);
			DistributionJob distributionJob = r.createDistributionJob();
			distributionJob.launch();
		}
		refresh();
	}

}
