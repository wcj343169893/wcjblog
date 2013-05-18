package com.choujone.eclipse.ftp.model;

import java.util.List;
import java.util.regex.Pattern;

import com.choujone.eclipse.ftp.projectresource.Project;
import com.choujone.eclipse.ftp.projectresource.ProjectResourceManager;

public class FtpSiteValidate {

	public static boolean isValidateIP(String ip) {
		Pattern patt = Pattern
				.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
		if (patt.matcher(ip).matches()) {
			String[] ipstrs = ip.split("\\.");
			int[] ipnums = new int[ipstrs.length];
			for (int i = 0; i < 4; i++) {
				ipnums[i] = Integer.parseInt(ipstrs[i]);
				if (ipnums[i] <= 255)
					continue;
				else
					return false;
			}
			if (ipnums[3] >= 255) {
				return false;
			}
			return true;
		}
		return false;
	}

	public static boolean isValidatePort(String port) {
		try {
			Integer.parseInt(port);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	public static boolean isUsedInProject(FtpSite ftpSite) {
		ProjectResourceManager manager = new ProjectResourceManager();
		List<Project> projectList = manager.getProjectsConfiguration();
		if (projectList == null) {
			return false;
		} else {
			for (Project project : projectList) {
				if (project.getFtpSite().getName().equals(ftpSite.getName())) {
					return true;
				}
			}
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println(isValidateIP("255.255.255.254"));
	}

}
