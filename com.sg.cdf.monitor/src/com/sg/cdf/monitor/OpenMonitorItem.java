package com.sg.cdf.monitor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import com.sg.cdf.core.persistence.IDistributionPersistence;
import com.sg.cdf.monitor.ui.MonitorView;


public class OpenMonitorItem extends Action implements
		ActionFactory.IWorkbenchAction {

	private static final String VIEW_ID = "com.sg.cdf.monitor.MonitorView";
	private IConfigurationElement conf;
	private String name;

	public OpenMonitorItem(String id, String name, IConfigurationElement conf) {
		this.conf = conf;
		this.name = name;
		setText(name);
		setId(id);
	}

	@Override
	public void run() {
		try {

			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();

			String id = getId();
			IViewReference reference = page.findViewReference(VIEW_ID, id);
			if (reference == null) {
				MonitorView viewPart = (MonitorView) page.showView(VIEW_ID, id,
						IWorkbenchPage.VIEW_ACTIVATE);
				try {
					final IDistributionPersistence persistence = (IDistributionPersistence) conf
							.createExecutableExtension("persistence");
					viewPart.showData(name, persistence);
				} catch (CoreException e) {
				}

			} else {
				page.showView(VIEW_ID, id, IWorkbenchPage.VIEW_ACTIVATE);
			}
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dispose() {

	}

}
