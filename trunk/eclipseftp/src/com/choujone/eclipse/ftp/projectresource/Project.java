package com.choujone.eclipse.ftp.projectresource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.choujone.eclipse.ftp.model.FtpSite;


public class Project {
	private String name;
	List<ProjectResourceMapping> mappingPath = null;
	private FtpSite ftpSite;

	public FtpSite getFtpSite() {
		return ftpSite;
	}

	public void setFtpSite(FtpSite ftpSite) {
		this.ftpSite = ftpSite;
	}

	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}

	public List<ProjectResourceMapping> getMappingPath() {
		return mappingPath;
	}

	public void setMappingPath(List<ProjectResourceMapping> mappingPath) {
		this.mappingPath = mappingPath;
	}

	public void addResourceMapping(
			ProjectResourceMapping projectResourceMappingPath) {
		if (null == mappingPath) {
			mappingPath = new ArrayList<ProjectResourceMapping>();
			mappingPath.add(projectResourceMappingPath);
		} else {
			Iterator<ProjectResourceMapping> iter = mappingPath.iterator();
			while (iter.hasNext()) {
				ProjectResourceMapping prmp = iter.next();
				if (prmp.getLocalPath().equals(
						projectResourceMappingPath.getLocalPath())) {
					iter.remove();
				}
			}
			mappingPath.add(projectResourceMappingPath);
		}

	}

}
