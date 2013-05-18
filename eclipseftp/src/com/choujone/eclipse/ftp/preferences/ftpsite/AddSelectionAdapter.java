package com.choujone.eclipse.ftp.preferences.ftpsite;


import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;

public class AddSelectionAdapter extends SelectionAdapter {
	private FtpSitePreferencePage ftpSitePreferencePage;
	public AddSelectionAdapter(FtpSitePreferencePage ftpSitePreferencePage){
		this.ftpSitePreferencePage=ftpSitePreferencePage;
	}
	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		super.widgetSelected(e);
		Shell shell=new Shell();
		FtpSiteDialog dialog=new FtpSiteDialog(shell,ftpSitePreferencePage);
		dialog.setSaveAdapter(new FtpSiteDialogSaveSelectionAdapter(dialog));
		dialog.open();
		
	}
}
