package com.snssly.sms.commons;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileManager {
	/**
	 * 下载文件
	 * 
	 * @param request
	 * @param response
	 * @param file
	 * @throws UnsupportedEncodingException
	 */
	public static void download(HttpServletRequest request,
			HttpServletResponse response, File file)
			throws UnsupportedEncodingException {
		String name = file.getName();
		// 设置为下载application/x-download
		response.setContentType("application/x-download");
		// 即将下载的文件在服务器上的绝对路径
		// 下载文件时显示的文件保存名称
		String filenamedisplay = name;
		// 中文编码转换
		filenamedisplay = URLEncoder.encode(filenamedisplay, "UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ filenamedisplay);
		try {
			java.io.OutputStream os = response.getOutputStream();
			java.io.FileInputStream fis = new java.io.FileInputStream(file);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = fis.read(b)) > 0) {
				os.write(b, 0, i);
			}
			fis.close();
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
