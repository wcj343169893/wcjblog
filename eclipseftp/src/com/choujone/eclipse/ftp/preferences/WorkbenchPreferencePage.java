package com.choujone.eclipse.ftp.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class WorkbenchPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {
	private Text _greeting;

	public WorkbenchPreferencePage() {
		super();
	}

	public WorkbenchPreferencePage(String title) {
		super(title);
	}

	public WorkbenchPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	protected Control createContents(Composite parent) {
		Label label = new Label(parent, SWT.CENTER);
		label.setText("Greeting");
		_greeting = new Text(parent, SWT.SINGLE | SWT.BORDER);
		return parent;
	}

	@Override
	public void init(IWorkbench arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * Performs special processing when this page's Restore Defaults button has
	 * 
	 * been pressed.
	 * 
	 * Sets the contents of the color field to the default value in the
	 * preference
	 * 
	 * store.
	 */

	protected void performDefaults() {
		// colorEditor.loadDefault();
	}

	@Override
	protected void performApply() {
		// TODO Auto-generated method stub
		super.performApply();
	}

	/**
	 * 
	 * Method declared on IPreferencePage. Save the
	 * 
	 * color preference to the preference store.
	 */

	public boolean performOk() {
		// colorEditor.store();
		return super.performOk();
	}

}
