package eclipseftp.popup.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import eclipseftp.popup.util.ContinueFTP2;
import eclipseftp.popup.util.FileUploadUtil;
import eclipseftp.popup.util.ContinueFTP2.UploadStatus;
import eclipseftp.popup.util.FtpManager;

public class TestFtp {
	static String host = "192.168.0.109";
	static int port = 21;
	static String username = "ftpuser";
	static String password = "DF7X52KT";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		FileUploadUtil fuu=new FileUploadUtil();
		List<String> address = new ArrayList<String>();
		address.add("D:/appengine-java-sdk-1.7.2/demos/helloxmpp");
		address.add("D:/appengine-java-sdk-1.7.2/demos/jdoexamples/war/addressbook.jsp");
//		address.add("D:/appengine-java-sdk-1.7.2/demos/jdoexamples/war/guestbook.jsp");
//		address.add("D:/appengine-java-sdk-1.7.2/demos/mandelbrot/build.xml");
		fuu.uploadFiles(address);
		// TODO Auto-generated method stub
		// ContinueFTP2 ftp = new ContinueFTP2();
		// try {
		// ftp.connect(host, port, username, password);
		// // myFtp.ftpClient.makeDirectory(new
		// // String(" 电视剧".getBytes("GBK"),"iso-8859-1"));
		// // myFtp.ftpClient.changeWorkingDirectory(new
		// // String(" 电视剧".getBytes("GBK"),"iso-8859-1"));
		// // myFtp.ftpClient.makeDirectory(new
		// // String(" 走西口".getBytes("GBK"),"iso-8859-1"));
		// // System.out.println(myFtp.upload("http://www.5a520.cn /yw.flv",
		// // "/yw.flv",5));
		// //
		// System.out.println(myFtp.upload("http://www.5a520.cn /走西口24.mp4","/央视走西口/新浪网/走西口 24.mp4"));
		// // System.out.println(myFtp.download("/ 央视走西口/新浪网/走西口24.mp4",
		// // "E:\\走西口242.mp4"));
		// UploadStatus us =ftp.upload("D:/TDDOWNLOAD/私人空间/新建文本文档.txt",
		// "/google/app/新建文本文档.txt");
		// System.out.println(us.toString());
		// // ftp.upload("D:/appengine-java-sdk-1.7.7.1.zip",
		// "/google/app/appengine-java-sdk-1.7.7.1.zip");
		//
		// // ftp.download(file1, file2);
		// // System.out.println(myFtp.upload("c:\\a.iso", "/a.iso"));
		// ftp.disconnect();
		// } catch (IOException e) {
		// System.out.println("连接FTP出错：" + e.getMessage());
		// }
		// testUpLoadFromDisk();
		// testUpLoadFromString();
		// testDownFile();
	}

	/**
	 * 将本地文件上传到FTP服务器上
	 * 
	 */
	public static void testUpLoadFromDisk() {
		try {
			FileInputStream in = new FileInputStream(new File(
					"D:/appengine-java-sdk-1.7.7.1.zip"));
			boolean flag = FtpManager.uploadFile(host, port, username,
					password, "t", "appengine-java-sdk-1.7.7.1.zip", in);
			System.out.println(flag);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 在FTP服务器上生成一个文件，并将一个字符串写入到该文件中
	 * 
	 */
	public static void testUpLoadFromString() {
		try {
			String str = "这是要写入的字符串！";
			InputStream input = new ByteArrayInputStream(str.getBytes("utf-8"));
			boolean flag = FtpManager.uploadFile(host, port, username,
					password, "test", "test.txt", input);
			System.out.println(flag);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将FTP服务器上文件下载到本地
	 * 
	 */
	public static void testDownFile() {
		try {
			boolean flag = FtpManager.downFile(host, port, username, password,
					"test", "test.txt", "D:/");
			System.out.println(flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
