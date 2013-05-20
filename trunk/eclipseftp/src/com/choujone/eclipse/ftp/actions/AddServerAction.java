package com.choujone.eclipse.ftp.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.choujone.eclipse.ftp.preferences.ClassftpRootPreferencePage;

public class AddServerAction implements IObjectActionDelegate {
	private Shell shell;

	@Override
	public void run(IAction arg0) {
//		MessageDialog.openInformation(
//				shell,
//				"Eclipseftp",
//				"增加服务器.");
		
	}

	@Override
	public void selectionChanged(IAction arg0, ISelection arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setActivePart(IAction arg0, IWorkbenchPart arg1) {
		shell = arg1.getSite().getShell();
	}

}
