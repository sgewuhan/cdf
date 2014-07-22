package com.sg.cdf.monitor;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	// Actions - important to allocate these only in makeActions, and then use
	// them
	// in the fill methods. This ensures that the actions aren't recreated
	// when fillActionBars is called with FILL_PROXY.
	// private IWorkbenchAction exitAction;
	// private IAction aboutAction;
	
	private IWorkbenchAction[] actions;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(final IWorkbenchWindow window) {
		Map<String, IConfigurationElement> monitors = CDFMonitor.getMonitorsConfig();
		Set<String> keySet = monitors.keySet();
		actions = new IWorkbenchAction[keySet.size()];
		Iterator<String> iterator = keySet.iterator();
		for (int i = 0; i < actions.length; i++) {
			String id = iterator.next();
			IConfigurationElement conf = monitors.get(id);
			String name = conf.getAttribute("name");
			actions[i] = new OpenMonitorItem(id,name,conf);
			register(actions[i]);
		}
	}
	
	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
	    coolBar.add(new ToolBarContributionItem(toolbar, "main"));
	    for (int i = 0; i < actions.length; i++) {
	    	toolbar.add(actions[i]);
		}
	    
	    toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
	    coolBar.add(new ToolBarContributionItem(toolbar, "view"));
		super.fillCoolBar(coolBar);
	}


}
