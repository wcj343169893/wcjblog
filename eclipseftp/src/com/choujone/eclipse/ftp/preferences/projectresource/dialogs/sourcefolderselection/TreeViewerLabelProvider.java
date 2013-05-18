package com.choujone.eclipse.ftp.preferences.projectresource.dialogs.sourcefolderselection;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class TreeViewerLabelProvider implements ILabelProvider {

	public Image getImage(Object element) {
		ITreeEntry entry=(ITreeEntry)element;
		if(entry.isRootNode()){
			return new Image(Display.getCurrent(),getClass().getResourceAsStream("projects.gif"));	
		}else{
			return new Image(Display.getCurrent(),getClass().getResourceAsStream("fldr_obj.gif"));
		}
		
		
	}

	public String getText(Object element) {
		ITreeEntry entry=(ITreeEntry)element;
		return entry.getName();
	}

	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

}
