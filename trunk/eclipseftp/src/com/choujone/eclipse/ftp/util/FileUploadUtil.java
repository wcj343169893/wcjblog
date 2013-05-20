package com.choujone.eclipse.ftp.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.choujone.eclipse.ftp.model.FtpSite;
import com.choujone.eclipse.ftp.util.ContinueFTP2.UploadStatus;

public class FileUploadUtil {
	private String host = "192.168.0.109";
	private int port = 21;
	private String username = "ftpuser";
	private String password = "DF7X52KT";
	private String localRoot = "D:/appengine-java-sdk-1.7.2/demos/";// 本地根目录,/结尾
	private String webRoot = "/";// 服务器根目录,/结尾

	public FileUploadUtil() {
	}

	public FileUploadUtil(FtpSite ftpSite) {
		this.host = ftpSite.getHostIP();
		this.port = Integer.parseInt(ftpSite.getHostPort());
		this.username = ftpSite.getLoginName();
		this.password = ftpSite.getLoginPwd();
		this.localRoot = ftpSite.getLocalRoot();
		this.webRoot = ftpSite.getWebRoot();
	}

	public FileUploadUtil(String host, int port, String username,
			String password, String localRoot, String webRoot) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.localRoot = localRoot;
		this.webRoot = webRoot;
	}

	/**
	 * 上传多个文件
	 * */
	public String uploadFiles(List<String> address) {
		String result = "";
		// 连接服务器
		ContinueFTP2 ftp = new ContinueFTP2();
		try {
			ftp.connect(host, port, username, password);
			System.out.println(host+"连接服务器成功");
			for (String localAddress : address) {
				upload(localAddress, ftp);
			}
			ftp.disconnect();
			System.out.println("断开连接");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	/**
	 * 递归上传文件夹下的内容
	 * */
	private void upload(String localAddress, ContinueFTP2 ftp)
			throws IOException {
		File file = new File(localAddress);
		// 过滤掉不是文件和隐藏文件
		if (file.exists() && !file.isHidden()) {
			// 递归遍历文件夹
			if (file.isDirectory()) {
				String[] files = file.list();
				for (String f : files) {
					String newlocalAddress = "";
					if (!localAddress.endsWith("/")) {
						newlocalAddress = localAddress + "/" + f;
					} else {
						newlocalAddress = localAddress + f;
					}
					upload(newlocalAddress, ftp);
				}
			}
			// 上传文件
			if (file.isFile()) {
				String filePath = localAddress.substring(localRoot.length());
				ftp.locate(webRoot);
				UploadStatus us = ftp.upload(localAddress, webRoot + filePath);
				System.out.println("本地地址:" + localAddress);
				System.out.println("服务器:" + filePath);
				System.out.println("上传状态:" + us.toString());
			}
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocalRoot() {
		return localRoot;
	}

	public void setLocalRoot(String localRoot) {
		this.localRoot = localRoot;
	}

	public String getWebRoot() {
		return webRoot;
	}

	public void setWebRoot(String webRoot) {
		this.webRoot = webRoot;
	}

}
