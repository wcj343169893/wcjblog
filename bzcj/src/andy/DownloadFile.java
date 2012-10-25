package andy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadFile {
	public static void main(String[] args) {
		DownloadFile df = new DownloadFile();
		df.downFile("http://www.mofing.com/app/download/1/29.apk", "appname", "E:/wwwroot/market/wj_lan_app/2012/10/");
	}

	// 建立缓冲区
	private int FILESIZE = 4 * 1024;

	public boolean downFile(String pUrl, String pStoreName, String floder) {
		// String lastName =
		// pUrl.substring(pUrl.lastIndexOf("/"+1,pUrl.length()));//pUrl.split("/");
		String[] names = pUrl.split("/");
		String name = names[names.length - 1];
		name = name.substring(name.lastIndexOf("."), name.lastIndexOf(".") + 4);
		OutputStream outputStream = null;
		InputStream inputStream = null;
		HttpURLConnection urlConnection;
		boolean bSuccess = false;
		URL url;
		File dir = new File(floder);
		if (!dir.exists()) {
			dir.mkdir();
		}
		// floder="E:/kid/swf/";
		// 创建建文件
		File file = new File(floder + pStoreName + name);
		if ((file != null) && !file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				outputStream = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				url = new URL(pUrl);
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setDoInput(true);
				urlConnection.connect();

				inputStream = urlConnection.getInputStream();
				int len = urlConnection.getContentLength();
				// System.out.println("file size is:" + len);
				byte[] buffer = new byte[FILESIZE];
				int byteRead = -1;

				while ((byteRead = (inputStream.read(buffer))) != -1) {
					outputStream.write(buffer, 0, byteRead);
				}
				outputStream.flush();

				inputStream.close();
				outputStream.close();
				bSuccess = true;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				bSuccess = false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				bSuccess = false;
			}
		}

		return bSuccess;
	}
}
