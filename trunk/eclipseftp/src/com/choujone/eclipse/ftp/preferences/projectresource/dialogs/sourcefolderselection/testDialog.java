package com.choujone.eclipse.ftp.preferences.projectresource.dialogs.sourcefolderselection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class testDialog {

	protected Shell shell;

	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			testDialog window = new testDialog();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window
	 */
	public void open() {
		final Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(500, 375);
		shell.setText("SWT Application");

		final Button showDialogButton = new Button(shell, SWT.NONE);
		showDialogButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				ProjectSourceFolderSelectionDialog dialog=new ProjectSourceFolderSelectionDialog(new Shell());
				dialog.open();
			}
		});
		showDialogButton.setText("Show Dialog");
		showDialogButton.setBounds(96, 108, 44, 23);
		//
	}

}
