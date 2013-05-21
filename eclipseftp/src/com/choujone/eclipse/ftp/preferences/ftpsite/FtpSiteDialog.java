package com.choujone.eclipse.ftp.preferences.ftpsite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.choujone.eclipse.ftp.l10n.Language;
import com.choujone.eclipse.ftp.model.FtpSite;
import com.choujone.eclipse.ftp.model.FtpSiteValidate;
import com.choujone.eclipse.ftp.util.StringUtil;

//import org.eclipse.swt.widgets.Dialog;

public class FtpSiteDialog extends Dialog {
	private static Log logger = LogFactory.getLog(FtpSiteDialog.class);

	private Text txtLoginPwd;
	private Text txtLoginName;
	private Text txtHostIp;
	private Text txtHostPort;
	private Text txtName;
	private Text localRoot;
	private Text webRoot;
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
		shell.setSize(500, 246);
		shell.getShell().setText("FtpSite");
		// shell.setText("FtpSite");

		ModifyListener modifyListener = new ModifyListenerAdapter();
		final Label nameLabel = new Label(shell, SWT.NONE);
		nameLabel.setText(Language.names("plugin_site_name"));

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
		hostIpLabel.setText(Language.names("plugin_site_ip_address"));

		txtHostIp = new Text(shell, SWT.BORDER);
		if (editFtpSite != null) {
			txtHostIp.setText(editFtpSite.getHostIP());
		}
		txtHostIp
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		txtHostIp.addModifyListener(modifyListener);

		final Label hostPort = new Label(shell, SWT.NONE);
		hostPort.setText(Language.names("plugin_site_port"));

		txtHostPort = new Text(shell, SWT.BORDER);
		txtHostPort.setText("21");
		if (editFtpSite != null) {
			txtHostPort.setText(editFtpSite.getHostPort());
		}
		txtHostPort.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		txtHostPort.addModifyListener(modifyListener);

		final Label loginNameLabel = new Label(shell, SWT.NONE);
		loginNameLabel.setText(Language.names("plugin_site_login_name"));

		txtLoginName = new Text(shell, SWT.BORDER);
		if (editFtpSite != null) {
			txtLoginName.setText(editFtpSite.getLoginName());
		}
		txtLoginName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		txtLoginName.addModifyListener(modifyListener);

		final Label loginPasswordLabel = new Label(shell, SWT.NONE);
		loginPasswordLabel.setText(Language.names("plugin_site_login_password"));

		txtLoginPwd = new Text(shell, SWT.BORDER);
		if (editFtpSite != null) {
			txtLoginPwd.setText(editFtpSite.getLoginPwd());
		}
		txtLoginPwd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		txtLoginPwd.addModifyListener(modifyListener);

		final Label localRootLabel = new Label(shell, SWT.NONE);
		localRootLabel.setText(Language.names("plugin_site_local_address"));

		localRoot = new Text(shell, SWT.BORDER);
		GridData gd_localRoot = new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1);
		gd_localRoot.widthHint = 268;
		localRoot.setLayoutData(gd_localRoot);
		localRoot.setText(System.getProperty("user.dir")+"\\");
		if (editFtpSite != null) {
			localRoot.setText(editFtpSite.getLocalRoot());
		}
		// localRoot.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
		// true, false));
		Button localPathBtn = new Button(parent, SWT.NONE);
		localPathBtn.setText(Language.names("plugin_site_browse_local_address"));
		// localPathBtn.addTouchListener(selectFloder);
		localPathBtn.addSelectionListener(new SelectionListener() {
			/**
			 * 文件夹（目录）选择对话框
			 */
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// 新建文件夹（目录）对话框
				DirectoryDialog folderdlg = new DirectoryDialog(shell
						.getShell());
				// 设置文件对话框的标题
				folderdlg.setText(Language.names("plugin_site_folder_selection"));
				// 设置初始路径
				folderdlg.setFilterPath(localRoot.getText());
				// 设置对话框提示文本信息
				folderdlg.setMessage(Language.names("plugin_site_folder_selection_notice"));
				// 打开文件对话框，返回选中文件夹目录
				String selecteddir = folderdlg.open();
				if (selecteddir == null) {
					return;
				} else {
					localRoot.setText(selecteddir.replaceAll("\\\\", "/")+"/");
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		// localPathBtn.setLocation(300, 150);

		localRoot.addModifyListener(modifyListener);
		final Label webRootLabel = new Label(shell, SWT.NONE);
		webRootLabel.setText(Language.names("plugin_site_server_address"));

		webRoot = new Text(shell, SWT.BORDER);
		webRoot.setText("/");
		if (editFtpSite != null) {
			webRoot.setText(editFtpSite.getWebRoot());
		}
		webRoot.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		webRoot.addModifyListener(modifyListener);

		hint = new Label(shell, SWT.NONE);
		hint.setText("               ");
		new Label(shell, SWT.NONE);
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
		saveButton.setText(Language.names("plugin_site_btn_save"));
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
		ftpSite.setLocalRoot(this.localRoot.getText().trim().replaceAll("\\\\", "/"));
		ftpSite.setWebRoot(this.webRoot.getText().trim().replaceAll("\\\\", "/"));//必须以/结尾
		if(!StringUtil.endsWithSlash(ftpSite.getWebRoot())){
			ftpSite.setWebRoot(ftpSite.getWebRoot()+"/");
		}
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
		ftpSite.setLocalRoot(this.localRoot.getText().trim().replaceAll("\\\\", "/"));
		ftpSite.setWebRoot(this.webRoot.getText().trim().replaceAll("\\\\", "/"));
		if(!StringUtil.endsWithSlash(ftpSite.getWebRoot())){
			ftpSite.setWebRoot(ftpSite.getWebRoot()+"/");
		}
		this.ftpSitePreferencePage.editFtpSite(ftpSite);
	}

	public void setEditFtpSite(FtpSite ftpSite) {
		this.editFtpSite = ftpSite;
	}

	public void setSaveAdapter(SelectionAdapter saveAdapter) {
		this.saveAdapter = saveAdapter;
	}

}
