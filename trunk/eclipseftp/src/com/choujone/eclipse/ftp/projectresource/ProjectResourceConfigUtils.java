/**
 * 
 */
package com.choujone.eclipse.ftp.projectresource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.XStream;

/**
 * @author hxs_handle
 * 
 */
public class ProjectResourceConfigUtils {
	// for test
	private static String PROJECT_RESOURCE_MAPPING_CONFIGURATION = "c:/project_resource_mapping.xml";
	// private static String
	// PROJECT_RESOURCE_MAPPING_CONFIGURATION=""+Activator.getDefault().getStateLocation().makeAbsolute().toFile().getAbsoluteFile()+"/project_resource_mapping.xml";
	private static Log logger = LogFactory
			.getLog(ProjectResourceConfigUtils.class);

	public static ProjectConfiguration getConfig() throws Exception {
		File file = new File(PROJECT_RESOURCE_MAPPING_CONFIGURATION);
		XStream xstream = getXStream();
		ProjectConfiguration projectConfiguration = null;
		if (!file.exists()) {
			projectConfiguration = new ProjectConfiguration();
			save(projectConfiguration);
		} else {

			FileInputStream fis = new FileInputStream(file);
			projectConfiguration = (ProjectConfiguration) xstream.fromXML(fis);
			fis.close();
		}
		return projectConfiguration;
	}

	public static void save(ProjectConfiguration projectConfiguration) {
		if (null == projectConfiguration) {
			logger.error("Project configuration is null");
			return;
		}
		XStream xstream = getXStream();
		String xml = xstream.toXML(projectConfiguration);
		saveFile(xml, PROJECT_RESOURCE_MAPPING_CONFIGURATION);
	}

	public static void save(Project project, String filePath) {
		XStream xstream = getXStream();
		String xml = xstream.toXML(project);
		saveFile(xml, filePath);
	}

	private static void saveFile(String xml, String filePath) {
		try {
			File file = new File(filePath);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(xml.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	private static XStream getXStream() {
		XStream xstream = new XStream();
		xstream.alias("configuration", ProjectConfiguration.class);
		xstream.alias("project", Project.class);
		xstream.alias("path", ProjectResourceMapping.class);
		return xstream;
	}

	public static Project getProjectConfig(String filePath) {
		Project projectConfiguration = null;
		try {
			File file = new File(filePath);
			XStream xstream = getXStream();
			FileInputStream fis = new FileInputStream(file);
			projectConfiguration = (Project) xstream.fromXML(fis);
			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return projectConfiguration;
	}

}
