package andy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TestYP {
	public static void main(String[] args) throws UnsupportedEncodingException {
		long bc = System.currentTimeMillis();
		StringBuffer sendString = new StringBuffer();
		Map<String, String> postdata = new HashMap<String, String>();
		postdata.put("app", "api");
		postdata.put("mod", "Statuses");
		postdata.put("act", "setBc");
		postdata.put("oauth_token", "af14167afe134a55bde768b871752eef");
		postdata.put("oauth_token_secret", "5039bb79bd0fd62775cda5bf76bc2cf2");
		postdata.put("title", "脉动");
		postdata.put("ac_id", "8");
		for (String key : postdata.keySet()) {
			sendString.append(key);
			sendString.append("=");
			sendString.append(postdata.get(key));
			sendString.append("&");
		}
		if (sendString.length() > 0) {
			sendString.deleteCharAt(sendString.length() - 1);
		}
		System.out.println("------------开始发送------------");
		TestYP.sendRequest(sendString.toString());
		System.out.println("------------发送结束------------");
		long cc = System.currentTimeMillis();
		System.out.println("耗时:" + (cc - bc));

	}

	public static String sendRequest(String strXml) {
		StringBuffer buffer = null;
		HttpURLConnection c = null;
		try {
			System.out.println("go!");
			URL url = new URL(
					"http://yp.mofing.com/index.php?"
							+ strXml);
			c = (HttpURLConnection) url.openConnection();
			// c.addRequestProperty("Cookie",
			// "CAKEPHP=80b7eeed3f32beecdcd616f6cd17d531; expires=Wed, 17-Oct-2012 07:49:27 GMT; path=/");
			c.setRequestMethod("POST");
			c.setRequestProperty("content-type", "text/html");
			c.setRequestProperty("Accept-Charset", "utf-8");
			c.setRequestProperty("Content-Length",
					String.valueOf(strXml.length()));
			// c.setRequestProperty("CAKEPHP",
			// "1e36a5a2c7cbf1edfd89933bc227cc71");
			// c.setRequestProperty("User-Agent", "NetFox");
			// c.setRequestProperty("RANGE", "bytes=20330000-20339000");
			c.setDoOutput(true);
			c.setDoInput(true);
			// c.setConnectTimeout(30000);//设置连接主机超时（单位：毫秒）
			// c.setReadTimeout(30000);//设置从主机读取数据超时（单位：毫秒）
			c.connect();
			System.out.println("-------begin--------");
			System.out.println(strXml);
			PrintWriter out = new PrintWriter(c.getOutputStream());// 发送数据
			out.print(strXml);
			out.flush();
			out.close();

			// c.geth
			String header;
			for (int i = 0; true; i++) {
				header = c.getHeaderField(i);
				if (header == null)
					break;
				System.out.println("header:" + header);
			}
			int res = 0;
			res = c.getResponseCode();
			System.out.println("res=" + res);

			if (res == 200) {

				InputStream u = c.getInputStream();// 获取servlet返回值，接收
				BufferedReader in = new BufferedReader(new InputStreamReader(u));
				buffer = new StringBuffer();
				String line = "";
				String returnStr = "";

				while ((line = in.readLine()) != null) {

					byte[] byteStr = line.getBytes();
					returnStr = new String(byteStr, "utf-8");
					buffer.append(returnStr);
				}
				System.out.println("---------->>" + buffer.toString());
			} else if (res == 206) {
				System.out.println("------断点续传开始-------");
				InputStream u = c.getInputStream();// 获取servlet返回值，接收
				BufferedReader in = new BufferedReader(new InputStreamReader(u));
				buffer = new StringBuffer();
				String line = "";
				String returnStr = "";
				String fileName = "768715.txt";
				File file = new File(fileName);
				FileOutputStream fos = new FileOutputStream(file);
				while ((line = in.readLine()) != null) {
					byte[] byteStr = line.getBytes();
					fos.write(byteStr);
					returnStr = new String(byteStr, "utf-8");
					buffer.append(returnStr);
				}
				fos.flush();
				fos.close();
			} else {
				System.out.println("------接收出错了-------");
			}
			c.disconnect();
			System.out.println("-------end--------");

			// System.out.println();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("异常！");
			System.out.println(e.toString());
		}
		return buffer.toString();
	}
}
