package com.sg.cdf.monitor.ui;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Item;

import com.sg.cdf.core.request.DistributionJob;

class TextColumnSorter extends ColumnViewerSorter {

	private ColumnLabelProvider labelProvider;

	public TextColumnSorter(ColumnViewer viewer, Item column) {
		super(viewer, column);
	}

	public TextColumnSorter(TableViewer viewer, TableViewerColumn col,
			ColumnLabelProvider labelProvider) {
		super(viewer, col.getColumn());
		this.labelProvider = labelProvider;

	}

	@Override
	protected int doCompare(Viewer viewer, Object e1, Object e2) {
		return labelProvider.getText(((DistributionJob) e1)).compareTo(
				labelProvider.getText(((DistributionJob) e2)));
	}
}
