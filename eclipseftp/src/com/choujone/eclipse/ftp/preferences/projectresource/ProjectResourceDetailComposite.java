package com.choujone.eclipse.ftp.preferences.projectresource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.choujone.eclipse.ftp.model.FtpSite;
import com.choujone.eclipse.ftp.model.FtpSiteManager;
import com.choujone.eclipse.ftp.preferences.projectresource.dialogs.resoucemapping.ProjectResourceMappingDialog;
import com.choujone.eclipse.ftp.projectresource.Project;
import com.choujone.eclipse.ftp.projectresource.ProjectResourceManager;
import com.choujone.eclipse.ftp.projectresource.ProjectResourceMapping;

public class ProjectResourceDetailComposite extends Composite implements
		Observer {

	class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			if (null != inputElement) {
				return ((List) inputElement).toArray();
			} else {
				return new Object[0];
			}
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	class TableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof ProjectResourceMapping) {
				ProjectResourceMapping projectResource = (ProjectResourceMapping) element;
				if (0 == columnIndex) {
					return projectResource.getLocalPath();
				} else {
					return projectResource.getRemotePath();
				}
			}
			return "";
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
	}

	private Table table;
	private Combo combo;
	private TableViewer tableViewer;
	private IProject currentProject;
	private VariableChangeLinster variableChangeListener;
	private ProjectResourceManager projectResourceManager;
	private Map<String, Project> projects;
	private Button removeButton;
	private Button addButton;
	private Button editButton;
	private Button inputButton;
	private Button exportButton;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public ProjectResourceDetailComposite(Composite parent, int style,
			ProjectResourceManager prm) {
		super(parent, style);
		this.projectResourceManager = prm;
		List<Project> lstProject = projectResourceManager
				.getProjectsConfiguration();
		projects = new HashMap<String, Project>();
		showConfiguration();
		for (Project p : lstProject) {
			projects.put(p.getName(), p);
		}
		variableChangeListener = new VariableChangeLinster(this);
		setLayout(new GridLayout());

		final Composite composite = new Composite(this, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		final Label ftpSiteLabel = new Label(composite, SWT.NONE);
		ftpSiteLabel.setText("Ftp Site");

		final ComboViewer comboViewer = new ComboViewer(composite, SWT.BORDER);
		comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				Project project = getProjectConfig();
				project.setFtpSite(getCurrentFtpSite());
			}
		});
		combo = comboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite_1.setLayout(new GridLayout());

		final Group resourceMappingGroup = new Group(composite_1, SWT.NONE);
		resourceMappingGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));
		resourceMappingGroup.setText("Resource Mapping");
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		resourceMappingGroup.setLayout(gridLayout_1);

		tableViewer = new TableViewer(resourceMappingGroup, SWT.FULL_SELECTION
				| SWT.BORDER);
		tableViewer.setContentProvider(new ContentProvider());
		tableViewer.setLabelProvider(new TableLabelProvider());
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final TableColumn newColumnTableColumn = new TableColumn(table, SWT.NONE);
		newColumnTableColumn.setWidth(100);
		newColumnTableColumn.setText("Local Directory");

		final TableColumn newColumnTableColumn_1 = new TableColumn(table, SWT.NONE);
		newColumnTableColumn_1.setWidth(100);
		newColumnTableColumn_1.setText("Remote Directory");

		final Composite composite_2 = new Composite(resourceMappingGroup, SWT.NONE);
		composite_2.setLayout(new GridLayout());
		composite_2.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true));

		addButton = new Button(composite_2, SWT.NONE);
		addButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				ProjectResourceMappingDialog dialog = new ProjectResourceMappingDialog(
						getShell());
				dialog.setCurrentProject(currentProject);
				dialog.setCurrentResource(tableViewer.getInput());
				dialog.setListener(variableChangeListener);
				dialog.open();
			}
		});
		addButton.setText("Add");

		editButton = new Button(composite_2, SWT.NONE);
		editButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				ProjectResourceMappingDialog dialog = new ProjectResourceMappingDialog(
						getShell());
				dialog.setCurrentProject(currentProject);
				dialog.setEndit(true);
				dialog.setEditResource(getCurrentResourceMapping());
				dialog.setCurrentResource(tableViewer.getInput());
				dialog.setListener(variableChangeListener);
				dialog.open();
			}
		});
		final GridData gd_editButton = new GridData(SWT.FILL, SWT.CENTER, true,
				false);
		editButton.setLayoutData(gd_editButton);
		editButton.setText("Edit");
		removeButton = new Button(composite_2, SWT.NONE);
		removeButton
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		removeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) tableViewer
						.getSelection();
				ProjectResourceMapping mapping = (ProjectResourceMapping) selection
						.getFirstElement();
				if (null != mapping) {
					MessageBox box=new MessageBox(new Shell(),SWT.ICON_QUESTION|SWT.YES|SWT.NO);
					box.setText("Warning");
					box.setMessage("Do you want to delete now?");
					int rc=box.open();
					if(rc==SWT.NO){
						return;
					}
					int index = table.getSelectionIndex();
					table.setSelection(index + 1);
					tableViewer.remove(mapping);
					((List) tableViewer.getInput()).remove(mapping);
					tableViewer.refresh();
				}
			}
		});
		removeButton.setText("Remove");

		inputButton = new Button(composite_2, SWT.NONE);
		inputButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				FileDialog dialog=new FileDialog(getShell(),SWT.OPEN);
				dialog.setFilterExtensions(new String[]{"*.projres"});
				String filePath=dialog.open();
				if (null!=filePath) {
					Project project = projectResourceManager
							.getProjectConfiguration(filePath);
					updateResourceMapping(project);
				}
			}
		});
		final GridData gd_inputButton = new GridData(SWT.FILL, SWT.CENTER, true, false);
		inputButton.setLayoutData(gd_inputButton);
		inputButton.setText("Input...");

		exportButton = new Button(composite_2, SWT.NONE);
		exportButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				FileDialog dialog=new FileDialog(getShell(),SWT.SAVE);
				dialog.setFilterExtensions(new String[]{"*.projres"});
				String filePath=dialog.open();
				if(null!=filePath){
					if(filePath.indexOf(".projres")==-1){
						filePath+=".projres";
					}
					projectResourceManager.exprotProjectResourceMapping(currentProject, filePath);
					
					System.out.println("save file "+filePath);
				}else{
					System.out.println("cancel save");
				}
				
			}
		});
		final GridData gd_exportButton = new GridData(SWT.FILL, SWT.CENTER, true, false);
		exportButton.setLayoutData(gd_exportButton);
		exportButton.setText("Export...");

		checkButtonEnable();
	}

	private void checkButtonEnable() {
		if (currentProject == null) {
			addButton.setEnabled(false);
			removeButton.setEnabled(false);
			combo.setEnabled(false);
			editButton.setEnabled(false);
			inputButton.setEnabled(false);
			exportButton.setEnabled(false);
		} else {
			addButton.setEnabled(true);
			removeButton.setEnabled(true);
			combo.setEnabled(true);
			editButton.setEnabled(true);
			inputButton.setEnabled(true);
			exportButton.setEnabled(true);
		}
	}

	@Override
	protected void checkSubclass() {
	}

	public void showProjectDetail(IProject selectedProject) {
		Project project = projects.get(selectedProject.getName());
		showFtpSite(project);
		showResourceMapping(project);
		this.currentProject = selectedProject;
		checkButtonEnable();
	}

	private void showResourceMapping(Project project) {
		if (null != project) {
			List<ProjectResourceMapping> projectResource = project.getMappingPath();
			tableViewer.setInput(projectResource);
		} else {
			tableViewer.getTable().removeAll();
		}

	}

	private void showFtpSite(Project project) {
		List<FtpSite> ftpSites = FtpSiteManager.getInstance().getFtpSite();
		int ftpSitesCount = 0;
		int configedftpIndex = -1;
		String ftpSiteName = null;
		if (null != project) {
			ftpSiteName = project.getFtpSite().getName();
		}
		if (null != ftpSites) {
			ftpSitesCount = ftpSites.size();
			String[] strFtpSites = new String[ftpSitesCount];
			for (int i = 0; i < ftpSitesCount; i++) {
				strFtpSites[i] = ftpSites.get(i).getName();
				if (null != ftpSiteName
						&& ftpSiteName.equals(ftpSites.get(i).getName())) {
					configedftpIndex = i;
				}
			}
			combo.setItems(strFtpSites);
			if (-1 != configedftpIndex) {
				combo.select(configedftpIndex);
			} else {
				combo.deselectAll();
			}
		}
	}

	public void update(Observable arg0, Object arg1) {
		ProjectResourceMapping mapping = (ProjectResourceMapping) arg1;
		ProjectResourceMapping currentResouce = getCurrentResourceMapping();
		List<ProjectResourceMapping> mappingPath;
		Project project = getProjectConfig();
		mappingPath = project.getMappingPath();
		if (null ==currentResouce || !mapping.getLocalPath().equals(currentResouce.getLocalPath())) {
			mappingPath.add(mapping);
		} else {
			for (ProjectResourceMapping m : mappingPath) {
				if (m.getLocalPath().equals(mapping.getLocalPath())) {
					m.setRemotePath(mapping.getRemotePath());
					break;
				}
			}
		}
		tableViewer.setInput(mappingPath);
		tableViewer.refresh();
	}
	
	private void updateResourceMapping(Project project){
		tableViewer.setInput(project.getMappingPath());
		tableViewer.refresh();
		projects.put(project.getName(), project);
		//projectResourceManager.addOrUpdateProjectResourceMapping(project);
	}

	private Project getProjectConfig() {
		Project project = projects.get(this.currentProject.getName());
		if (project == null) {
			project = new Project();
			project.setName(this.currentProject.getName());
			FtpSite ftpSite = getCurrentFtpSite();
			if (null != ftpSite) {
				project.setFtpSite(ftpSite);
			}
			project.setMappingPath(new ArrayList<ProjectResourceMapping>());
			projects.put(project.getName(), project);
		}
		return project;
	}

	private ProjectResourceMapping getCurrentResourceMapping() {
		ProjectResourceMapping resouceMapping = null;
		int selecteionIndex = tableViewer.getTable().getSelectionIndex();
		if (-1 != selecteionIndex) {
			TableItem item = tableViewer.getTable().getItem(selecteionIndex);
			resouceMapping = new ProjectResourceMapping();
			resouceMapping.setLocalPath(item.getText(0));
			resouceMapping.setRemotePath(item.getText(1));
		}
		return resouceMapping;
	}

	public FtpSite getCurrentFtpSite() {
		FtpSite ftpSite = null;
		int selectionIndex = combo.getSelectionIndex();
		if (-1 != selectionIndex) {
			String ftpName = (String) combo.getItem(selectionIndex);
			ftpSite = FtpSiteManager.getInstance().getFtpSite(ftpName);
		}
		return ftpSite;
	}

	private void showConfiguration() {
		List<Project> list = projectResourceManager.getProjectsConfiguration();
		for (Project project : list) {
			List<ProjectResourceMapping> prm = project.getMappingPath();
			if (null != prm) {
				for (ProjectResourceMapping m : prm) {
				}
			}
		}
	}

	public void save() {
		Iterator<String> iter = projects.keySet().iterator();
		String key;
		Project project;
		while (iter.hasNext()) {
			key = iter.next();
			project = projects.get(key);
			projectResourceManager.addOrUpdateProjectResourceMapping(project);
		}
		projectResourceManager.save();
	}

	public void removeProject(String projectName) {
		if (null == projectName) {
			return;
		}
		tableViewer.setInput(null);
		combo.removeAll();
		tableViewer.refresh();
		combo.redraw();
		projects.remove(projectName);
		currentProject = null;
		checkButtonEnable();

	}
}
