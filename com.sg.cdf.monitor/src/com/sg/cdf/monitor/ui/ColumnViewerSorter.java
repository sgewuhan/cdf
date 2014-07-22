package com.sg.cdf.monitor.ui;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.TableColumn;

class ColumnViewerSorter extends ViewerComparator {
	
	public static final int ASC = 1; // 排序的方式,顺序

	public static final int NONE = 0;

	public static final int DESC = -1; // 排序的方式,倒序

	private int direction = 0; // 排序的方式

	protected Item column;

	private ColumnViewer viewer;

	/**
	 * 创建一个新的对象
	 * 
	 * @param viewer
	 * @param column
	 */
	/**
	 * 构造方法
	 * 
	 * @param viewer
	 * @param column
	 *            排序的列
	 */
	public ColumnViewerSorter(ColumnViewer viewer, Item column) {
		this.column = column;
		this.viewer = viewer;
		((TableColumn) column).addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (ColumnViewerSorter.this.viewer.getComparator() != null) {
					if (ColumnViewerSorter.this.viewer.getComparator() == ColumnViewerSorter.this) {
						int tdirection = ColumnViewerSorter.this.direction;

						if (tdirection == ASC) {
							setSorter(ColumnViewerSorter.this, DESC);
						} else if (tdirection == DESC) {
							setSorter(ColumnViewerSorter.this, NONE);
						}
					} else {
						setSorter(ColumnViewerSorter.this, ASC);
					}
				} else {
					setSorter(ColumnViewerSorter.this, ASC);
				}
			}
		});

	}

	/**
	 * 设置排序的方向
	 * 
	 * @param sorter
	 * @param direction
	 */
	/**
	 * 设置排序方式
	 * 
	 * @param sorter
	 * @param direction
	 */
	public void setSorter(ColumnViewerSorter sorter, int direction) {
		if (direction == NONE) {
			((TableColumn) column).getParent().setSortColumn(null);
			((TableColumn) column).getParent().setSortDirection(SWT.NONE);
			viewer.setComparator(null);
		} else {
			((TableColumn) column).getParent().setSortColumn(
					((TableColumn) column));
			sorter.direction = direction;

			if (direction == ASC) {
				((TableColumn) column).getParent().setSortDirection(SWT.UP);
			} else {
				((TableColumn) column).getParent().setSortDirection(SWT.DOWN);
			}

			if (viewer.getComparator() == sorter) {
				viewer.refresh();
			} else {
				viewer.setComparator(sorter);
			}
		}
	}

	/**
	 * 覆盖父类的排序方法
	 */
	/**
	 * 按照排序的方式比较传入的两个对象
	 */
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		return direction * doCompare(viewer, e1, e2);
	}

	/**
	 * 排序方法，由子类实现
	 * 
	 * @param viewer
	 * @param e1
	 * @param e2
	 * @return
	 */
	/**
	 * 对传入的两个对象进行比较的具体实现
	 * 
	 * @param viewer
	 * @param e1
	 * @param e2
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected int doCompare(Viewer viewer, Object e1, Object e2) {
		if (e1 instanceof Comparable && e2 instanceof Comparable) {
			return ((Comparable) e1).compareTo(e2);
		}
		return 0;
	}
}
