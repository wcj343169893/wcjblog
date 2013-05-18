package com.choujone.eclipse.ftp.projectresource;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.choujone.eclipse.ftp.model.FtpSite;

public class ProjectResourceManager {
	private static Log logger = LogFactory.getLog(ProjectResourceManager.class);

	private ProjectConfiguration projectConfiguration;
	private List<IProject> configedProjects;
	private List<Project> projectConfigurations;

	public ProjectResourceManager() {
		try {
			projectConfiguration = ProjectResourceConfigUtils.getConfig();
			configedProjects = new ArrayList<IProject>();
			projectConfigurations = projectConfiguration.getProjects();
			if (null != projectConfigurations) {
				String projectName = null;
				for (Project projectResourceMapping : projectConfigurations) {
					projectName = projectResourceMapping.getName();
					IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject(
							projectName);
					configedProjects.add(p);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}

	public List<IProject> getConfigedProjects() {
		return configedProjects;
	}

	public void addOrUpdateProjectResourceMapping(Project newProject) {
		if (null == newProject) {
			logger.error("projectResourceMapping is null");
			return;
		}
		List<Project> configedProjects = this.projectConfiguration.getProjects();
		if (null == configedProjects) {
			List<Project> projects = new ArrayList<Project>();
			projects.add(newProject);
			projectConfiguration.setProjects(projects);
		} else {
			Iterator<Project> iter = configedProjects.iterator();
			while (iter.hasNext()) {
				Project p = iter.next();
				if (p.getName().equals(newProject.getName())) {
					iter.remove();
					break;
				}
			}
			configedProjects.add(newProject);
			projectConfiguration.setProjects(configedProjects);
		}

	}

	public void save() {
		ProjectResourceConfigUtils.save(projectConfiguration);
	}

	public Project getProjectConfiguration(IProject project) {
		for (Project p : projectConfigurations) {
			if (project.getName().equals(p.getName())) {
				return p;
			}
		}
		return null;
	}

	public List<Project> getProjectsConfiguration() {
		return this.projectConfigurations;
	}

	public java.util.List<IProject> getWorkspaseProjects() {
		// Activator.getDefault()
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		java.util.List<IProject> lstProject = Arrays.asList(projects);
		return lstProject;
	}

	public IProject getWorkspaceProjects(String projectName) {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
	}

	public List<File> getAllProjectFolder(IProject project) {
		List<File> resource = new ArrayList<File>();
		File file = project.getLocation().toFile();
		getAllDirectory(file, resource);
		return resource;
	}

	private void getAllDirectory(File file, List<File> list) {
		if (file.isDirectory()) {
			File[] fs = file.listFiles();
			if (fs != null) {
				for (int i = 0; i < fs.length; i++) {
					if (fs[i].isDirectory()) {
						list.add(fs[i]);
						getAllDirectory(fs[i], list);
					}
				}
			}
		}
	}

	public void removeProject(String projectName) {
		if (null == projectName) {
			return;
		}
		projectConfigurations = this.projectConfiguration.getProjects();
		Iterator<Project> iter = projectConfigurations.iterator();
		Project projcet;
		while (iter.hasNext()) {
			projcet = iter.next();
			if (projcet.getName().equals(projectName)) {
				iter.remove();
			}
		}
	}

	public void updateFtp(FtpSite ftpSite) {
		List<Project> list = projectConfiguration.getProjects();
		if (null != list && !list.isEmpty()) {
			for (Project project : list) {
				if (project.getFtpSite().getName().equals(ftpSite.getName())) {
					project.setFtpSite(ftpSite);
				}
			}
		}

	}

	public void exprotProjectResourceMapping(IProject exportProject,
			String filePath) {
		Project projectConfig = getProjectConfiguration(exportProject);
		ProjectResourceConfigUtils.save(projectConfig, filePath);
	}

	public Project getProjectConfiguration(String filePath) {
		// TODO Auto-generated method stub
		return ProjectResourceConfigUtils.getProjectConfig(filePath);
	}

}
