package com.sg.cdf.monitor;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * Configures the perspective layout. This class is contributed through the
 * plugin.xml.
 */
public class Perspective implements IPerspectiveFactory {

	private static final String PROGRESS_VIEW_ID = "com.sg.cdf.monitor.MonitorView";

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		
		layout.addPlaceholder(PROGRESS_VIEW_ID, IPageLayout.LEFT, 0.25f, editorArea);
		layout.setFixed(true);
	}
}
