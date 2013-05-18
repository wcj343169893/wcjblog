package com.choujone.eclipse.ftp.preferences.projectresource.dialogs.sourcefolderselection;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TreeViewerContentProvider implements ITreeContentProvider {

	public Object[] getChildren(Object parentElement) {
		ITreeEntry entry = (ITreeEntry) parentElement;
		List list = entry.getChildren();
		if (list == null || list.isEmpty()) {
			return new Object[0];
		} else {
			return list.toArray();
		}
	}

	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasChildren(Object element) {
		ITreeEntry entry = (ITreeEntry) element;
		List list = entry.getChildren();
		if (null == list || list.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof List) {
			List list = (List) inputElement;
			return list.toArray();
		} else {
			return new Object[0];
		}
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

}
