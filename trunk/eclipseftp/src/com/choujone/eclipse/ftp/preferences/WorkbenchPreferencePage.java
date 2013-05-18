package com.choujone.eclipse.ftp.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class WorkbenchPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {
	private Table serverTable;
	private Label ftpExplain;
	private TableColumn tblName;
	private TableColumn tblHost;
	private TableColumn tblPort;
	private TableColumn tblUserName;
	private TableColumn tblPassword;
	private Button addServerBtn;
	private Button removeServerBtn;

	public WorkbenchPreferencePage() {
		super();
	}

	/**
	 * @wbp.parser.constructor
	 */
	public WorkbenchPreferencePage(String title) {
		super(title);
	}

	public WorkbenchPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	protected Control createContents(Composite parent) {
		ftpExplain = new Label(parent, SWT.NONE);
		ftpExplain.setText("FTP上传代码插件,支持多服务器同时发布。");
		Label lblGreetings = new Label(parent, SWT.CENTER);
		lblGreetings.setText("服务器：");
		serverTable = new Table(parent, SWT.BORDER);
		serverTable.setLinesVisible(true);
		serverTable.setHeaderVisible(true);

		tblName = new TableColumn(serverTable, SWT.NONE);
		tblName.setText("服务器名称");
		tblName.setWidth(100);
		tblHost = new TableColumn(serverTable, SWT.NONE);
		tblHost.setText("地址");
		tblHost.setWidth(100);
		tblUserName = new TableColumn(serverTable, SWT.NONE);
		tblUserName.setText("登录名");
		tblUserName.setWidth(100);

		addServerBtn = new Button(parent, SWT.FLAT | SWT.CENTER);
		removeServerBtn = new Button(parent, SWT.FLAT | SWT.CENTER);
		addServerBtn.setText("增加");
		addServerBtn.setBounds(60, 235, 70, 25);
		
		removeServerBtn.setText("移除");
		removeServerBtn.setBounds(160, 235, 70, 25);
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
