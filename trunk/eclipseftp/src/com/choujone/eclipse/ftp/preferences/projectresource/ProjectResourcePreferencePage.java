package com.choujone.eclipse.ftp.preferences.projectresource;

import org.eclipse.jface.preference.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>,
 * we can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class ProjectResourcePreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private ProjectResourceComposite cop;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	@Override
	protected Control createContents(Composite parent) {
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		cop = new ProjectResourceComposite(parent, SWT.NONE);
		return cop;
	}


	@Override
	protected void performApply() {
		// TODO Auto-generated method stub
		
		super.performApply();
	}

	@Override
	public boolean performOk() {
		// TODO Auto-generated method stub
		cop.save();
		return super.performOk();
		
	}

}