package com.choujone.eclipse.ftp.preferences.projectresource.dialogs.sourcefolderselection;

import java.util.Iterator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

import com.choujone.eclipse.ftp.preferences.projectresource.VariableChangeLinster;

public class ProjectSourceFolderSelectionDialog extends Dialog {

	private Tree tree;
	private ITreeEntry currentTreeEntry;
	private Object inputData;
	private VariableChangeLinster listener;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public ProjectSourceFolderSelectionDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		final TreeViewer treeViewer = new TreeViewer(container, SWT.BORDER);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				Iterator iter = selection.iterator();
				while (iter.hasNext()) {
					ITreeEntry entry = (ITreeEntry) iter.next();
					currentTreeEntry = entry;
				}
			}
		});
		treeViewer.setLabelProvider(new TreeViewerLabelProvider());
		treeViewer.setContentProvider(new TreeViewerContentProvider());
		tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		treeViewer.setInput(inputData);
		return container;
	}

	/**
	 * Create contents of the button bar
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(332, 375);
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Source Folder Selection");
	}

	@Override
	protected void buttonPressed(int buttonId) {
		super.buttonPressed(buttonId);
		if (buttonId == IDialogConstants.OK_ID && null != listener) {
			listener.update(currentTreeEntry);
		}
	}

	public Object getInputData() {
		return inputData;
	}

	public void setInputData(Object inputData) {
		this.inputData = inputData;
	}

	public void setListener(VariableChangeLinster listener) {
		this.listener = listener;
	}

}
