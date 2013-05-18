package com.choujone.eclipse.ftp.preferences.ftpsite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.choujone.eclipse.ftp.model.FtpSite;
import com.choujone.eclipse.ftp.model.FtpSiteValidate;
//import org.eclipse.swt.widgets.Dialog;

public class FtpSiteDialog extends Dialog {
	private static Log logger = LogFactory.getLog(FtpSiteDialog.class);

	private Text txtLoginPwd;
	private Text txtLoginName;
	private Text txtHostIp;
	private Text txtHostPort;
	private Text txtName;
	private Label hint;
	private Button saveButton;
	protected Object result;
	protected Composite shell;
	private FtpSitePreferencePage ftpSitePreferencePage;
	private SelectionAdapter saveAdapter;
	private FtpSite editFtpSite;

	private class ModifyListenerAdapter implements ModifyListener {
		public void modifyText(ModifyEvent e) {
			if (txtLoginPwd.getText().trim().length() > 0
					&& txtLoginName.getText().trim().length() > 0
					&& txtHostIp.getText().trim().length() > 0
					&& txtHostPort.getText().trim().length() > 0
					&& txtName.getText().trim().length() > 0) {
				if (FtpSiteValidate.isValidateIP(txtHostIp.getText().trim())
						&& FtpSiteValidate.isValidatePort(txtHostPort.getText()
								.trim())) {
					saveButton.setEnabled(true);
					return;
				}
			}
			saveButton.setEnabled(false);
		}
	}

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 * @param style
	 * @wbp.parser.constructor
	 */
	public FtpSiteDialog(Shell parent, int style) {
		super(parent);

	}

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 */
	public FtpSiteDialog(Shell parent) {
		this(parent, SWT.NONE);
	}

	public FtpSiteDialog(Shell parent,
			FtpSitePreferencePage ftpSitePreferencePage) {
		this(parent, SWT.NONE);
		this.ftpSitePreferencePage = ftpSitePreferencePage;

	}

	/**
	 * Create contents of the dialog
	 */
	protected Control createDialogArea(Composite parent) {
		shell = (Composite) super.createDialogArea(parent);

		// shell = new Shell(getParent(), SWT.DIALOG_TRIM |
		// SWT.APPLICATION_MODAL);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shell.setLayout(gridLayout);
		shell.setSize(500, 186);
		shell.getShell().setText("FtpSite");
		// shell.setText("FtpSite");

		ModifyListener modifyListener = new ModifyListenerAdapter();

		final Label nameLabel = new Label(shell, SWT.NONE);
		nameLabel.setText("Name");

		txtName = new Text(shell, SWT.BORDER);
		if (editFtpSite != null) {
			txtName.setText(editFtpSite.getName());
			txtName.setEditable(false);
		}
		final GridData gd_txtName = new GridData(SWT.FILL, SWT.FILL, true,
				false);
		gd_txtName.widthHint = 263;
		txtName.setLayoutData(gd_txtName);
		txtName.addModifyListener(modifyListener);
		final Label hostIpLabel = new Label(shell, SWT.NONE);
		hostIpLabel.setText("Host IP");

		txtHostIp = new Text(shell, SWT.BORDER);
		if (editFtpSite != null) {
			txtHostIp.setText(editFtpSite.getHostIP());
		}
		final GridData gd_txtHostIp = new GridData(SWT.FILL, SWT.CENTER, true,
				false);
		txtHostIp.setLayoutData(gd_txtHostIp);
		txtHostIp.addModifyListener(modifyListener);

		final Label hostPort = new Label(shell, SWT.NONE);
		hostPort.setText("Host Port");

		txtHostPort = new Text(shell, SWT.BORDER);
		if (editFtpSite != null) {
			txtHostPort.setText(editFtpSite.getHostPort());
		}
		final GridData gd_txtHostPort = new GridData(SWT.FILL, SWT.CENTER,
				true, false);
		txtHostPort.setLayoutData(gd_txtHostPort);
		txtHostPort.addModifyListener(modifyListener);

		final Label loginNameLabel = new Label(shell, SWT.NONE);
		loginNameLabel.setText("Login Name");

		txtLoginName = new Text(shell, SWT.BORDER);
		if (editFtpSite != null) {
			txtLoginName.setText(editFtpSite.getLoginName());
		}
		final GridData gd_txtLoginName = new GridData(SWT.FILL, SWT.CENTER,
				true, false);
		txtLoginName.setLayoutData(gd_txtLoginName);
		txtLoginName.addModifyListener(modifyListener);

		final Label loginPasswordLabel = new Label(shell, SWT.NONE);
		final GridData gd_loginPasswordLabel = new GridData();
		loginPasswordLabel.setLayoutData(gd_loginPasswordLabel);
		loginPasswordLabel.setText("Login Password");

		txtLoginPwd = new Text(shell, SWT.BORDER);
		if (editFtpSite != null) {
			txtLoginPwd.setText(editFtpSite.getLoginPwd());
		}
		final GridData gd_txtLoginPwd = new GridData(SWT.FILL, SWT.CENTER,
				true, false);
		txtLoginPwd.setLayoutData(gd_txtLoginPwd);
		txtLoginPwd.addModifyListener(modifyListener);

		hint = new Label(shell, SWT.NONE);
		hint.setText("               ");
		if (editFtpSite != null) {

			// txtName.setText(editFtpSite.getName());
			// txtName.setEditable(false);
			// txtHostIp.setText(editFtpSite.getHostIP());
			// txtHostPort.setText(editFtpSite.getHostPort());
			// txtLoginName.setText(editFtpSite.getLoginName());
			// txtLoginPwd.setText(editFtpSite.getLoginPwd());
		}

		// final Composite composite = new Composite(shell, SWT.NONE);
		// final GridLayout gridLayout_1 = new GridLayout();
		// gridLayout_1.numColumns = 2;
		// composite.setLayout(gridLayout_1);
		// composite.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true,
		// false));
		//
		// saveButton = new Button(composite, SWT.NONE);
		// saveButton.addSelectionListener(saveAdapter);
		// saveButton.setText("Save");
		// saveButton.setEnabled(false);
		//
		// final Button cancelButton = new Button(composite, SWT.NONE);
		// cancelButton.setText("Cancel");
		// final FtpSiteDialog ftpsiteDialog = this;
		// cancelButton.addSelectionListener(new SelectionAdapter() {
		// public void widgetSelected(SelectionEvent e) {
		// ftpsiteDialog.close();
		// }
		// });

		return shell;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		((GridLayout) parent.getLayout()).numColumns++;
		saveButton = new Button(parent, SWT.PUSH);
		saveButton.addSelectionListener(saveAdapter);
		saveButton.setText("Save");
		saveButton.setEnabled(false);
		setButtonLayoutData(saveButton);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	public int getStyle() {
		// TODO Auto-generated method stub
		return super.getShellStyle() | SWT.RESIZE | SWT.MAX;
	}

	public void addFtpSite() throws Exception {
		FtpSite ftpSite = new FtpSite();
		ftpSite.setName(this.txtName.getText().trim());
		ftpSite.setHostIP(this.txtHostIp.getText().trim());
		ftpSite.setHostPort(this.txtHostPort.getText().trim());
		ftpSite.setLoginName(this.txtLoginName.getText().trim());
		ftpSite.setLoginPwd(this.txtLoginPwd.getText().trim());

		logger.debug("===============add ftp==================");
		logger.debug("ftp Name = " + this.txtName.getText());
		logger.debug("ftp HostIp = " + this.txtHostIp.getText());
		logger.debug("ftp LoginName = " + this.txtLoginName.getText());
		logger.debug("ftp LoginPwd = " + this.txtLoginPwd.getText());
		this.ftpSitePreferencePage.addFtpSite(ftpSite);
	}

	public void editFtpSite() throws Exception {
		FtpSite ftpSite = new FtpSite();
		ftpSite.setName(this.txtName.getText().trim());
		ftpSite.setHostIP(this.txtHostIp.getText().trim());
		ftpSite.setHostPort(this.txtHostPort.getText().trim());
		ftpSite.setLoginName(this.txtLoginName.getText().trim());
		ftpSite.setLoginPwd(this.txtLoginPwd.getText().trim());
		this.ftpSitePreferencePage.editFtpSite(ftpSite);
	}

	public void setEditFtpSite(FtpSite ftpSite) {
		this.editFtpSite = ftpSite;
	}

	public void setSaveAdapter(SelectionAdapter saveAdapter) {
		this.saveAdapter = saveAdapter;
	}

}
