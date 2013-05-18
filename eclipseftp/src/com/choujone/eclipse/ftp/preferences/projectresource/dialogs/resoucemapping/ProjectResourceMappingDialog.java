package com.choujone.eclipse.ftp.preferences.projectresource.dialogs.resoucemapping;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.choujone.eclipse.ftp.preferences.projectresource.VariableChangeLinster;
import com.choujone.eclipse.ftp.preferences.projectresource.dialogs.sourcefolderselection.ITreeEntry;
import com.choujone.eclipse.ftp.preferences.projectresource.dialogs.sourcefolderselection.ProjectSourceFolderDataFactory;
import com.choujone.eclipse.ftp.preferences.projectresource.dialogs.sourcefolderselection.ProjectSourceFolderSelectionDialog;
import com.choujone.eclipse.ftp.projectresource.ProjectResourceMapping;

public class ProjectResourceMappingDialog extends Dialog implements Observer {

	private Text txtRemote;
	private Text txtLocal;
	public IProject currentProject;
	private VariableChangeLinster vaiableChangelistener;
	private VariableChangeLinster listener;
	private List<ProjectResourceMapping> currentConfigedResource;
	private boolean isEdit = false;
	private ProjectResourceMapping editResource = null;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public ProjectResourceMappingDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		vaiableChangelistener = new VariableChangeLinster(this);
		Composite container = (Composite) super.createDialogArea(parent);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		container.setLayout(gridLayout);

		final Label localPathLabel = new Label(container, SWT.NONE);
		localPathLabel.setText("Local Path");

		txtLocal = new Text(container, SWT.BORDER);
		final GridData gd_txtLocal = new GridData(SWT.FILL, SWT.CENTER, true, false);
		txtLocal.setLayoutData(gd_txtLocal);

		final Button selectButton = new Button(container, SWT.NONE);
		selectButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				ProjectSourceFolderSelectionDialog dialog = new ProjectSourceFolderSelectionDialog(
						new Shell());
				dialog.setInputData(ProjectSourceFolderDataFactory
						.getData(currentProject));
				dialog.setListener(vaiableChangelistener);
				dialog.open();
			}
		});
		selectButton.setText("select...");

		final Label remotePathLabel = new Label(container, SWT.NONE);
		remotePathLabel.setText("Remote Path");

		txtRemote = new Text(container, SWT.BORDER);
		final GridData gd_txtRemote = new GridData(SWT.FILL, SWT.CENTER, true,
				false);
		txtRemote.setLayoutData(gd_txtRemote);
		new Label(container, SWT.NONE);

		if (isEdit && editResource != null) {
			txtRemote.setText(editResource.getRemotePath());
			txtLocal.setText(editResource.getLocalPath());
		}
		if(isEdit){
			txtLocal.setEnabled(false);
			selectButton.setEnabled(false);
		}

		//
		return container;
	}

	/**
	 * Create contents of the button bar
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		final Button button = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(427, 150);
	}

	public IProject getCurrentProject() {
		return currentProject;
	}

	public void setCurrentProject(IProject currentProject) {
		this.currentProject = currentProject;
	}

	public void update(Observable arg0, Object arg1) {
		ITreeEntry entry = (ITreeEntry) arg1;
		this.txtLocal.setText(entry.getPath());
	}

	public void setListener(VariableChangeLinster listener) {
		this.listener = listener;
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			// TODO Auto-generated method stub
			String strRemotePath = txtRemote.getText();
			String strLocalPaht = txtLocal.getText();
			if (strRemotePath == null || strRemotePath.trim().length() < 1) {
				MessageDialog.openInformation(this.getShell(), "Error",
						"The remote path is requried.");
				return;
			}
			if (strLocalPaht == null || strLocalPaht.trim().length() < 1) {
				MessageDialog.openInformation(this.getShell(), "Error",
						"The local path is requried.");
				return;
			}
			if (!isEdit && isResourceAlreadyConfiged()) {
				MessageDialog.openInformation(this.getShell(), "Error",
						"The resource is already configed.");
				return;
			}
			ProjectResourceMapping mapping = new ProjectResourceMapping();
			mapping.setLocalPath(txtLocal.getText());
			mapping.setRemotePath(txtRemote.getText());
			if (buttonId == IDialogConstants.OK_ID && null != listener) {
				listener.update(mapping);
			}
		}
		super.buttonPressed(buttonId);

	}

	private boolean isResourceAlreadyConfiged() {
		boolean flag = false;
		if (currentConfigedResource != null && !currentConfigedResource.isEmpty()) {
			for (ProjectResourceMapping m : currentConfigedResource) {
				if (m.getLocalPath().equals(txtLocal.getText().trim())) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	public void setCurrentResource(Object input) {
		if (input instanceof List) {
			currentConfigedResource = (List) input;
		}
	}

	public void setEndit(boolean isEndit) {
		this.isEdit = isEndit;
	}

	public void setEditResource(ProjectResourceMapping editResource) {
		this.editResource = editResource;
	}

}
