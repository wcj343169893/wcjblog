package com.choujone.eclipse.ftp.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * http://code.google.com/p/slave4j/source/browse/trunk
 * @author Administrator
 *
 */
public class UploadAction implements IObjectActionDelegate {
	private Shell shell;
	private List<Object> address = new ArrayList<Object>();// 选中文件列表

	@Override
	public void run(IAction action) {
		if (address.size()>0) {
			//有选中文件
			
		}
	}

	/* 这个方法，每次选中文件，都会请求两次
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction arg0, ISelection selection) {
		address = new ArrayList<Object>();// 选中文件列表
		if (selection instanceof IStructuredSelection) {
			File directory = null;
			for (Iterator iterator = ((IStructuredSelection) selection)
					.iterator(); iterator.hasNext();) {
				IAdaptable adaptable = (IAdaptable) iterator.next();
				if (adaptable instanceof IResource) {
					// selectedFile = ((IResource) adaptable);
					directory = new File(((IResource) adaptable).getLocation()
							.toOSString());
				} else if ((adaptable instanceof PackageFragment)
						&& (((PackageFragment) adaptable)
								.getPackageFragmentRoot() instanceof JarPackageFragmentRoot)) {

					directory = getJarFile(((PackageFragment) adaptable)
							.getPackageFragmentRoot());
				} else if (adaptable instanceof JarPackageFragmentRoot) {
					directory = getJarFile(adaptable);
				} else {
					// directory = ((IResource) adaptable
					// .getAdapter(IResource.class));
					directory = new File(
							((IResource) adaptable.getAdapter(IResource.class))
									.getLocation().toOSString());
				}
				String path=directory.toString();
				if (!address.contains(path)) {
					address.add(path);
					System.out.println("选中文件：" + path);
				}
			}
		} 
	}

	protected File getJarFile(IAdaptable adaptable) {
		JarPackageFragmentRoot jpfr = (JarPackageFragmentRoot) adaptable;
		File selected = jpfr.getPath().makeAbsolute().toFile();
		if (!(selected.exists())) {
			File projectFile = new File(jpfr.getJavaProject().getProject()
					.getLocation().toOSString());
			selected = new File(projectFile.getParent() + selected.toString());
		}
		return selected;
	}

	@Override
	public void setActivePart(IAction arg0, IWorkbenchPart arg1) {
		shell = arg1.getSite().getShell();
	}

}
