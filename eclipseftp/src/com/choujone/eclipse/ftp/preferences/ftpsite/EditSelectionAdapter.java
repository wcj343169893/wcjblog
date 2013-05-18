package com.choujone.eclipse.ftp.preferences.ftpsite;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.choujone.eclipse.ftp.model.FtpSite;

public class EditSelectionAdapter extends SelectionAdapter {
	private FtpSitePreferencePage ftpSitePreferencePage;

	public EditSelectionAdapter(FtpSitePreferencePage ftpSitePreferencePage) {
		this.ftpSitePreferencePage = ftpSitePreferencePage;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		try {
			super.widgetSelected(e);
			Table table = ftpSitePreferencePage.tableViewer.getTable();
			int index = table.getSelectionIndex();
			if (index != -1) {
				TableItem item = table.getItem(index);
				FtpSite ftpSite = new FtpSite();
				ftpSite.setName(item.getText(0));
				ftpSite.setHostIP(item.getText(1));
				ftpSite.setHostPort(item.getText(2));
				ftpSite.setLoginName(item.getText(3));
				ftpSite.setLoginPwd(item.getText(4));
				Shell shell = new Shell();
				FtpSiteDialog dialog = new FtpSiteDialog(shell,
						ftpSitePreferencePage);
				dialog.setEditFtpSite(ftpSite);
				dialog.setSaveAdapter(new FtpSiteDialogEditSelectionAdapter(
						dialog));
				dialog.open();
			}
		} catch (Exception ex) {

			MessageDialog.openInformation(null, "Error", ex
					.getLocalizedMessage());
		}

	}

}
