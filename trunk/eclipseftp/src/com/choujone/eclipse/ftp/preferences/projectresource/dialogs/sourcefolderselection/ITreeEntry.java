package com.choujone.eclipse.ftp.preferences.projectresource.dialogs.sourcefolderselection;

import java.util.List;

public interface ITreeEntry {
	public String getName();

	public void setName(String name);

	public String getPath();

	public void setPath(String path);

	public void setChildren(List<ITreeEntry> children);

	public List<ITreeEntry> getChildren();

	public void addChild(ITreeEntry child);

	public boolean isRootNode();

	public void setIsRootNode(boolean isRootNode);

}
