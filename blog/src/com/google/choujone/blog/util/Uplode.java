package com.google.choujone.blog.util;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * choujone'blog<br>
 * 功能描述：上传文件 调用方法 String imagepath =
 * Uplode.promptUplode(uf.getUpfile().getFileData(),
 * uf.getUpfile().getFileName(), "文件名随便打",
 * request.getSession().getServletContext().getRealPath("\\"));
 * FormBean里头的支持文件上传字段 org.apache.struts.upload.FormFile; 2010-11-25
 */
public class Uplode {
	/**
	 * @category 上传文件的方法
	 * @param inputFiledata
	 *            输入流
	 * @param filename
	 *            文件名
	 * @param ismkdir
	 *            子模块文件夹名字 用于在项目中创建文件夹名字
	 * @param serverpath
	 *            服务器路径
	 * @return filePath
	 */
	public static String promptUplode(InputStream inputFiledata,
			String filename, String ismkdir, String serverpath) {
		// 在项目里创建保存上传文件的文件夹和文件
		createMonthlyFolder(serverpath, ismkdir);

		// 制作文件名
		String[] str = filename.split("\\.");// 只要后缀
		int random = (int) (Math.random() * 10000);// 随机数防止文件名重复
		String mytime = getDateTime("yyyyMMddHHmmss") + "_" + random + "."
				+ str[str.length - 1];

		// 在服务器的文件夹内 创建你上传的文件 如图片
		Files.promptFile(inputFiledata, serverpath + ismkdir + "/" + ismkdir
				+ getDateTime("yyyyMMdd") + "" + "/" + mytime);

		return ismkdir + "/" + ismkdir + getDateTime("yyyyMMdd") + "" + "/"
				+ mytime;
	}

	/**
	 * 创建当天文件夹和 该类存放文件夹
	 * 
	 * @param serverpath
	 * @param ismkdir
	 */
	private static final void createMonthlyFolder(String serverpath,
			String ismkdir) {
		// 获取当前时间
		String modernTime = getDateTime("yyyyMMdd");
		// 创建子模块的文件夹
		Mkdir.promptMkdir(serverpath + ismkdir);
		// 创建当月文件夹images(2009-10) ="images("+modernTime+")名字
		String path = serverpath + ismkdir + "/" + ismkdir + modernTime + "";
		Mkdir.promptMkdir(path);
	}

	/**
	 * 获取当前时间 如 2009-05样式
	 * 
	 * @return
	 */
	private static final String getDateTime(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		return sdf.format(new Date());
	}
}
