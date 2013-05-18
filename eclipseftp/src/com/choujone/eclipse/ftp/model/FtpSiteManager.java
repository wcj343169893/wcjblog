package com.choujone.eclipse.ftp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FtpSiteManager {
	// private static FtpSiteManager isntance = new FtpSiteManager();
	private static Log logger = LogFactory.getLog(FtpSiteManager.class);
	private ClassftpConfiguration classftpConfiguration;

	public static FtpSiteManager getInstance() {
		return new FtpSiteManager();
	}

	private FtpSiteManager() {
		try {
			classftpConfiguration = ConfigurationUtils.getConfig();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	public void save() {
		ConfigurationUtils.save(classftpConfiguration);
	}

	public void addOrUpdateFtpSite(FtpSite ftpSite) {
		if (null == ftpSite) {
			logger.warn("add or update ftpsite but the site is null");
			return;
		}
		List<FtpSite> ftpSites = classftpConfiguration.getFtpSites();
		if (null == ftpSites) {
			ftpSites = new ArrayList<FtpSite>();
			ftpSites.add(ftpSite);
			classftpConfiguration.setFtpSites(ftpSites);
		} else {
			Iterator iter = ftpSites.iterator();
			while (iter.hasNext()) {
				FtpSite site = (FtpSite) iter.next();
				if (site.getName().equals(ftpSite.getName())) {
					iter.remove();
					// ftpSites.remove(site);
				}
			}
			ftpSites.add(ftpSite);
			classftpConfiguration.setFtpSites(ftpSites);
		}
	}

	public boolean addFtpSite(FtpSite ftpSite) {
		if (null == ftpSite) {
			logger.warn("add ftpsite but the site is null");
			return false;
		}
		List<FtpSite> ftpSites = classftpConfiguration.getFtpSites();
		if (null == ftpSites) {
			ftpSites = new ArrayList<FtpSite>();
			ftpSites.add(ftpSite);
			classftpConfiguration.setFtpSites(ftpSites);
			return true;
		} else {
			Iterator iter = ftpSites.iterator();
			while (iter.hasNext()) {
				FtpSite site = (FtpSite) iter.next();
				if (site.getName().equals(ftpSite.getName())) {
					return false;
					// ftpSites.remove(site);
				}
			}
			ftpSites.add(ftpSite);
			classftpConfiguration.setFtpSites(ftpSites);
			return true;
		}
	}

	public boolean updateFtpSite(FtpSite ftpSite) {
		if (null == ftpSite) {
			logger.warn("update ftpsite but the site is null");
			return false;
		}
		List<FtpSite> ftpSites = classftpConfiguration.getFtpSites();
		if (null == ftpSites) {
			ftpSites = new ArrayList<FtpSite>();
			ftpSites.add(ftpSite);
			classftpConfiguration.setFtpSites(ftpSites);
			return true;
		} else {
			for (int i = 0; i < ftpSites.size(); i++) {
				FtpSite site = ftpSites.get(i);
				if (site.getName().equals(ftpSite.getName())) {
					ftpSites.set(i, ftpSite);
					return true;
				}
			}
			return false;
		}
	}

	public boolean removeFtpSite(FtpSite ftpSite) {
		if (null == ftpSite) {
			logger.warn("remove ftpsite but the site is null");
			return false;
		}
        if (FtpSiteValidate.isUsedInProject(ftpSite)) {
        	return false;
        }
		List<FtpSite> ftpSites = classftpConfiguration.getFtpSites();
		if (null == ftpSites) {
			return false;
		} else {
			Iterator iter = ftpSites.iterator();
			while (iter.hasNext()) {
				FtpSite site = (FtpSite) iter.next();
				if (site.getName().equals(ftpSite.getName())) {
					ftpSites.remove(site);
					return true;
				}
			}
			return false;
		}
	}

	public List<FtpSite> getFtpSite() {
		return this.classftpConfiguration.getFtpSites();
	}

	public FtpSite getFtpSite(String ftpName) {
		List<FtpSite> sites = this.classftpConfiguration.getFtpSites();
		FtpSite ftpSite = null;
		for (FtpSite fs : sites) {
			if (fs.getName().equals(ftpName)) {
				ftpSite = fs;
			}
		}
		return ftpSite;
	}

}
