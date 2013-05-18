/**
 * 
 */
package com.choujone.eclipse.ftp.preferences.ftpsite;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * @author Administrator
 * 
 */
public class FtpSiteDialogSaveSelectionAdapter extends SelectionAdapter {
	private FtpSiteDialog ftpSiteDialog;

	public FtpSiteDialogSaveSelectionAdapter(FtpSiteDialog ftpSiteDialog) {
		this.ftpSiteDialog = ftpSiteDialog;

	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		super.widgetSelected(e);
		try {
			ftpSiteDialog.addFtpSite();
			ftpSiteDialog.close();
		} catch (Exception ex) {

		}
	}

}
