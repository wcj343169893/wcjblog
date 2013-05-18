package com.choujone.eclipse.ftp.preferences.projectresource.dialogs.sourcefolderselection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

public class ProjectSourceFolderDataFactory {

	public static List<ITreeEntry> getData(IProject project) {
		List<ITreeEntry> data = new ArrayList<ITreeEntry>();
		File root = project.getLocation().toFile();
		System.out.println("root path ==>"+root.getPath());
		ITreeEntry entry = getAllDirectory(root, null, root.getPath());
		data.add(entry);
		return data;
	}

	private static ITreeEntry getAllDirectory(File file, ITreeEntry entry,
			String rootPath) {
		if (file.isDirectory()) {
			if (null == entry) {
				entry = new ProjectFolderEntry();
				String name = file.getName();
				entry.setName(file.getName());
				entry.setPath(File.separator);
				entry.setIsRootNode(true);
			}
			File[] fs = file.listFiles();
			String path=null;
			if (fs != null) {
				for (int i = 0; i < fs.length; i++) {
					if (fs[i].isDirectory()) {
						ITreeEntry te = new ProjectFolderEntry();
						te.setName(fs[i].getName());
						path=fs[i].getPath().substring(rootPath.length());
						te.setPath(path);
						entry.addChild(getAllDirectory(fs[i], te, rootPath));
					}
				}
			}
		}
		return entry;
	}

}
