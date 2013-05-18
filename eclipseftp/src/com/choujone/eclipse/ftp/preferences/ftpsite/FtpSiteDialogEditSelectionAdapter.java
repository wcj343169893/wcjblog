package com.choujone.eclipse.ftp.preferences.ftpsite;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class FtpSiteDialogEditSelectionAdapter extends SelectionAdapter {
	private FtpSiteDialog ftpSiteDialog;

	public FtpSiteDialogEditSelectionAdapter(FtpSiteDialog ftpSiteDialog) {
		this.ftpSiteDialog = ftpSiteDialog;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		super.widgetSelected(e);
		try {
			ftpSiteDialog.editFtpSite();
			ftpSiteDialog.close();
		} catch (Exception ex) {

		}
	}

}
