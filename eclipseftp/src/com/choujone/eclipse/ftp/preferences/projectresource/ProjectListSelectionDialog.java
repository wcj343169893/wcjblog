package com.choujone.eclipse.ftp.preferences.projectresource;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class ProjectListSelectionDialog extends ElementListSelectionDialog {
private ListViewer projectListViewer;
	public ProjectListSelectionDialog(ListViewer projectListViewer,Shell parent, ILabelProvider renderer) {
		super(parent, renderer);
		this.setTitle("Select Project");
		this.projectListViewer=projectListViewer;
	}
	


	@Override
	protected void buttonPressed(int buttonId) {
		// TODO Auto-generated method stub
		super.buttonPressed(buttonId);
		if (buttonId == IDialogConstants.OK_ID) {
			Object[] objs = this.getResult();
			for (int i = 0; i < objs.length; i++) {
				IProject project = (IProject) objs[i];
				projectListViewer.add(project);
			}
		}
	}
}
