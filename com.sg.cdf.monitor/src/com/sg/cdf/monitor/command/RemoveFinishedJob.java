package com.sg.cdf.monitor.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

import com.sg.cdf.monitor.ui.MonitorView;

public class RemoveFinishedJob extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPart part = HandlerUtil.getActivePart(event);
		if(part instanceof MonitorView){
			MonitorView view = (MonitorView) part;
			view.removeFinishedJob();
		}
		return null;
	}

}
