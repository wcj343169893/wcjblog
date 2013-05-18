package com.choujone.eclipse.ftp.preferences.projectresource.dialogs.sourcefolderselection;

import java.util.ArrayList;
import java.util.List;

public class ProjectFolderEntry implements ITreeEntry {
	private String name;
	private String path;
	private List<ITreeEntry> children;
	private boolean isRootNode=false;

	public List<ITreeEntry> getChildren() {
		return children;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public String getPath() {
		// TODO Auto-generated method stub
		return path;
	}

	public void setChildren(List<ITreeEntry> children) {
		this.children = children;

	}

	public void setName(String name) {
		this.name = name;

	}

	public void setPath(String path) {
		this.path = path;

	}

	public void addChild(ITreeEntry child) {
		if (null == this.children) {
			children = new ArrayList<ITreeEntry>();
		}
		children.add(child);

	}
	
	public String toString(){
		return "[name: "+name+" paht: "+path+"]";
	}

	public boolean isRootNode() {
		return isRootNode;
	}

	public void setIsRootNode(boolean isRootNode) {
		this.isRootNode=isRootNode;
		
	}

}
