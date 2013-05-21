package com.choujone.eclipse.ftp.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class AddServerAction implements IObjectActionDelegate {
//	private Shell shell;

	@Override
	public void run(IAction arg0) {
		//打开首选项的ftp站点菜单
		PreferenceDialog createPreferenceDialogOn = PreferencesUtil
				.createPreferenceDialogOn(
						new Shell(),
						"com.choujone.eclipse.ftp.preferences.ftpsite.FtpSitePreferencePage",
						null, null);
		createPreferenceDialogOn.open();
	}

	@Override
	public void selectionChanged(IAction arg0, ISelection arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setActivePart(IAction arg0, IWorkbenchPart arg1) {
//		shell = arg1.getSite().getShell();
	}

}
