package com.choujone.eclipse.ftp.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.XStream;

public class ConfigurationUtils {
	public static final String CONFIG_FILE_PATH=System.getProperty("user.home")+"/eclipse_ftp_sites.xml";

	private static Log logger=LogFactory.getLog(ConfigurationUtils.class);
	
	
	public static void save(ClassftpConfiguration classftpConfiguration) {
		if (null==classftpConfiguration){
			logger.error("classftp configuration is null");
			return;
		}
		XStream xstream = getXStream();
		String xml = xstream.toXML(classftpConfiguration);
		try {
			File file=new File(CONFIG_FILE_PATH);
			FileOutputStream fos=new FileOutputStream(file);
			fos.write(xml.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	public static ClassftpConfiguration getConfig() throws Exception {
		File file = new File(CONFIG_FILE_PATH);
		XStream xstream = getXStream();
		ClassftpConfiguration classftpConfiguration = null;
		if (!file.exists()) {
			classftpConfiguration = new ClassftpConfiguration();
			save(classftpConfiguration);

		} else {
			FileInputStream fis = new FileInputStream(file);
			classftpConfiguration = (ClassftpConfiguration) xstream
					.fromXML(fis);
			fis.close();
		}
		return classftpConfiguration;
	}
	
	private static XStream getXStream() {
		XStream xstream=new XStream();
		xstream.alias("configuration", ClassftpConfiguration.class);
		xstream.alias("site", FtpSite.class);
		xstream.alias("path", DirectoryMapping.class);
		return xstream;
	}
	

}
