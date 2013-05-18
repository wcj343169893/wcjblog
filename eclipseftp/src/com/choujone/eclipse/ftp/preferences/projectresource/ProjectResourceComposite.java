package com.choujone.eclipse.ftp.preferences.projectresource;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.choujone.eclipse.ftp.projectresource.ProjectResourceManager;

public class ProjectResourceComposite extends Composite {
	private ListViewer projectListViewer;
	private ProjectResourceDetailComposite projectResourceDetailComposite;

	class ListLabelProvider extends LabelProvider {
		public String getText(Object element) {
			IProject project = (IProject) element;
			return project.getName();
		}

		public Image getImage(Object element) {
			return null;
		}
	}

	class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof java.util.List) {
				return ((java.util.List) inputElement).toArray();
			} else {
				return new Object[0];
			}
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	private List list;
	private ProjectResourceManager projectResourceManager = new ProjectResourceManager();
	private Button removeButton ;
	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public ProjectResourceComposite(Composite parent, int style) {
		super(parent, style);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		setLayout(gridLayout);

		final Group projectGroup = new Group(this, SWT.NONE);
		projectGroup.setText("Project");
		final GridData gd_projectGroup = new GridData(SWT.FILL, SWT.FILL, true,
				true);
		projectGroup.setLayoutData(gd_projectGroup);
		projectGroup.setLayout(new GridLayout());

		final Composite composite = new Composite(projectGroup, SWT.NONE);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		composite.setLayout(gridLayout_1);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		projectListViewer = new ListViewer(composite, SWT.BORDER);
		projectListViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(final SelectionChangedEvent event) {
						IStructuredSelection selection = (IStructuredSelection) event
								.getSelection();
						Iterator iter = selection.iterator();
						while (iter.hasNext()) {
							IProject selectedProject = (IProject) iter.next();
							projectResourceDetailComposite.showProjectDetail(selectedProject);
						}
						removeButton.setEnabled(true); 
					}
				});
		projectListViewer.setLabelProvider(new ListLabelProvider());
		projectListViewer.setContentProvider(new ContentProvider());
		list = projectListViewer.getList();
		final GridData gd_list = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_list.minimumWidth = 100;
		list.setLayoutData(gd_list);
		// set configed project
		projectListViewer.setInput(projectResourceManager.getConfigedProjects());
		final Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new GridLayout());
		composite_1.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, true));

		final Button addButton = new Button(composite_1, SWT.NONE);
		addButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				ProjectListSelectionDialog projectListDialog = new ProjectListSelectionDialog(
						projectListViewer, new Shell(), new ProjectListLabelProvider());
				projectListDialog.setElements(getUnConfigedProject().toArray());
				projectListDialog.open();
			}
		});
		addButton.setText("Add");

		removeButton = new Button(composite_1, SWT.NONE);
		removeButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		removeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) projectListViewer
						.getSelection();
				IProject project = (IProject) selection.getFirstElement();
				if (null != project) {
					
					MessageBox box=new MessageBox(new Shell(),SWT.ICON_QUESTION|SWT.YES|SWT.NO);
					box.setText("Warning");
					box.setMessage("Do you want to delete now?");
					int rc=box.open();
					if(rc==SWT.NO){
						return;
					}
					
					projectListViewer.remove(project);
					projectResourceManager.removeProject(project.getName());
					((java.util.List) projectListViewer.getInput()).remove(project);
					projectListViewer.refresh();
					projectResourceDetailComposite.removeProject(project.getName());
					removeButton.setEnabled(false); 
				}
			}
		});
		removeButton.setText("Remove");
		removeButton.setEnabled(false);

		final Group detailsGroup = new Group(this, SWT.NONE);
		detailsGroup.setText("Details");
		final GridData gd_detailsGroup = new GridData(SWT.FILL, SWT.FILL, true,
				true);
		detailsGroup.setLayoutData(gd_detailsGroup);
		detailsGroup.setLayout(new GridLayout());

		projectResourceDetailComposite = new ProjectResourceDetailComposite(
				detailsGroup, SWT.NONE, projectResourceManager);
		projectResourceDetailComposite.setLayoutData(new GridData(SWT.FILL,
				SWT.FILL, true, true));
		//
	}

	public void save() {
		projectResourceDetailComposite.save();

	}

	private java.util.List<IProject> getUnConfigedProject() {
		java.util.List<IProject> allProjects = projectResourceManager
				.getWorkspaseProjects();
		java.util.List<IProject> configedProjects = projectResourceManager
				.getConfigedProjects();
		java.util.List<IProject> unConfigedProjects = new ArrayList<IProject>();
		boolean isConfiged = false;
		for (IProject ap : allProjects) {
			isConfiged = false;
			for (IProject cp : configedProjects) {
				if (ap.getName().equals(cp.getName())) {
					isConfiged = true;
					break;
				}
			}
			if (!isConfiged) {
				unConfigedProjects.add(ap);
			}
		}
		return unConfigedProjects;
	}

}
