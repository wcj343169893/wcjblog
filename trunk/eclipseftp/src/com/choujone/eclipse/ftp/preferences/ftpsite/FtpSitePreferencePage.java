package com.choujone.eclipse.ftp.preferences.ftpsite;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.choujone.eclipse.ftp.model.FtpSite;
import com.choujone.eclipse.ftp.model.FtpSiteManager;
import com.choujone.eclipse.ftp.projectresource.ProjectResourceManager;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class FtpSitePreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {
	public FtpSitePreferencePage() {
	}
	private Table table;
	TableViewer tableViewer;
	private FtpSiteManager fsm;

	@Override
	protected Control createContents(Composite parent) {
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		Composite topComp = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		topComp.setLayout(gridLayout);

		final Group ftpSitesGroup = new Group(topComp, SWT.NONE);
		ftpSitesGroup
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		ftpSitesGroup.setText("Ftp 站点");
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.makeColumnsEqualWidth = true;
		ftpSitesGroup.setLayout(gridLayout_1);

		tableViewer = new TableViewer(ftpSitesGroup, SWT.FULL_SELECTION
				| SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);

		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setContentProvider(new ContentProvider());
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final TableColumn newColumnTableColumn = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn.setWidth(100);
		newColumnTableColumn.setText("站点名称");

		final TableColumn newColumnTableColumn_1 = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_1.setWidth(100);
		newColumnTableColumn_1.setText("IP地址");

		final TableColumn newColumnTableColumn_2 = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_2.setWidth(100);
		newColumnTableColumn_2.setText("端口");

		final TableColumn newColumnTableColumn_3 = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_3.setWidth(100);
		newColumnTableColumn_3.setText("登录名");

		final TableColumn newColumnTableColumn_4 = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_4.setWidth(100);
		newColumnTableColumn_4.setText("登录密码");
		
		final TableColumn newColumnTableColumn_5 = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_5.setWidth(100);
		newColumnTableColumn_5.setText("本地路径");
		
		final TableColumn newColumnTableColumn_6 = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_6.setWidth(100);
		newColumnTableColumn_6.setText("服务器路径");

		fsm = FtpSiteManager.getInstance();
		tableViewer.setInput(fsm.getFtpSite());

		final Composite composite = new Composite(topComp, SWT.NONE);
		composite.setLayout(new GridLayout());
		final GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, false,
				false);
		gd_composite.widthHint = 73;
		gd_composite.heightHint = 50;
		composite.setLayoutData(gd_composite);

		final Button btnAddFtp = new Button(composite, SWT.NONE);
		btnAddFtp
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		btnAddFtp.addSelectionListener(new AddSelectionAdapter(this));
		btnAddFtp.setSelection(true);
		btnAddFtp.setText("Add");

		final Button btnEditFtp = new Button(composite, SWT.NONE);
		btnEditFtp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false));
		btnEditFtp.addSelectionListener(new EditSelectionAdapter(this));
		btnEditFtp.setText("Edit");
		btnEditFtp.setEnabled(false);

		final Button btnRemoveFtp = new Button(composite, SWT.NONE);
		btnRemoveFtp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false));
		btnRemoveFtp.addSelectionListener(new RemoveSelectionAdapter(this));
		btnRemoveFtp.setText("Remove");
		btnRemoveFtp.setEnabled(false);

		tableViewer
				.addPostSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						btnEditFtp.setEnabled(true);
						btnRemoveFtp.setEnabled(true);
					}
				});
		return topComp;
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

		// System.out.println("aaaaaaaaaaaaa
		// "+Activator.getDefault().getStateLocation().makeAbsolute().toFile().getAbsoluteFile());

	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public void addFtpSite(FtpSite ftpSite) throws Exception {
		// TODO add new FtpSite
		if (!this.fsm.addFtpSite(ftpSite)) {
			// 报错信息 新增配置的名称已存在或为空
			MessageDialog.openInformation(null, "Error",
					"The host name has existed or is null!");
			throw new Exception("The host name has existed or is null");
		}//这里没刷新成功，也许是tableview绑定问题
		tableViewer.setInput(fsm.getFtpSite());
		this.tableViewer.refresh();
	}

	public void editFtpSite(FtpSite ftpSite) throws Exception {
		if (!this.fsm.updateFtpSite(ftpSite)) {
			MessageDialog.openInformation(null, "Error",
					"can not find the host name!");
			throw new Exception("can not find the host name!");
		}
		this.tableViewer.refresh();
	}

	public void removeFtpSite(FtpSite ftpSite) throws Exception {
		if (!this.fsm.removeFtpSite(ftpSite)) {
			MessageDialog.openInformation(null, "Error",
					"the config could be used in projects now!");
			throw new Exception("the config could be used in projects now!");
		}
		this.tableViewer.refresh();
	}

	public boolean performOk() {
		List<FtpSite> ftpSiteList = this.fsm.getFtpSite();
		if (null != ftpSiteList) {
			ProjectResourceManager manager = new ProjectResourceManager();
			for (FtpSite ftpsite : ftpSiteList) {
				manager.updateFtp(ftpsite);
			}
			this.fsm.save();
			manager.save();
			return true;
		}
		return false;
	}

}