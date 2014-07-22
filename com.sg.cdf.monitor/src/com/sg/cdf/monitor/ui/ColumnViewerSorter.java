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
	
	public static final int ASC = 1; // ����ķ�ʽ,˳��

	public static final int NONE = 0;

	public static final int DESC = -1; // ����ķ�ʽ,����

	private int direction = 0; // ����ķ�ʽ

	protected Item column;

	private ColumnViewer viewer;

	/**
	 * ����һ���µĶ���
	 * 
	 * @param viewer
	 * @param column
	 */
	/**
	 * ���췽��
	 * 
	 * @param viewer
	 * @param column
	 *            �������
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
	 * ��������ķ���
	 * 
	 * @param sorter
	 * @param direction
	 */
	/**
	 * ��������ʽ
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
	 * ���Ǹ�������򷽷�
	 */
	/**
	 * ��������ķ�ʽ�Ƚϴ������������
	 */
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		return direction * doCompare(viewer, e1, e2);
	}

	/**
	 * ���򷽷���������ʵ��
	 * 
	 * @param viewer
	 * @param e1
	 * @param e2
	 * @return
	 */
	/**
	 * �Դ��������������бȽϵľ���ʵ��
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
