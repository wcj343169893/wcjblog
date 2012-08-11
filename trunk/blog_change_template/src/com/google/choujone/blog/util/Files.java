package com.google.choujone.blog.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Files {
	/**
	 * @category 复制本地文件到服务器的方法
	 * @param inputFiledata
	 *            文件输入流
	 * @param serverpath
	 *            表示存到服务器的全路径
	 * @return boolean
	 */
	public static boolean promptFile(InputStream inputFiledata,
			String serverpath) {

		try {
			// 文件输出流
			FileOutputStream fos = new FileOutputStream(new File(serverpath));
			int len = 0;
			byte[] bt = new byte[1024 * 1024];
			while ((len = inputFiledata.read(bt)) != -1) {
				fos.write(bt, 0, len);// 写
			}
			inputFiledata.close();
			fos.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}
}