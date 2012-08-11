package com.google.choujone.blog.util;

import java.io.File;

public class Mkdir {
	/**
	 * 
	 * @category 立刻创建文件夹的方法 1个参数 path表示服务器路径
	 */
	public static boolean promptMkdir(String path) {
		File myFilePath = new File(path.toString());
		try {
			if (myFilePath.isDirectory()) {// 此文件夹以存在
				return false;
			} else {// 新建目录成功
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			System.out.println("新建目录操作出错");
			e.printStackTrace();
		}
		return true;
	}
}