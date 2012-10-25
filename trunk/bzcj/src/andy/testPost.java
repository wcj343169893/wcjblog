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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class testPost {

	/*
	 * public static void main(String[] args) throws
	 * UnsupportedEncodingException { long bc = System.currentTimeMillis(); for
	 * (int i = 0; i < 100; i++) { long b = System.currentTimeMillis(); String
	 * str = "<xml><test>aa 1_ 1 </test><name>姓名</name></xml>" + i 100; str =
	 * URLEncoder.encode(str, "utf-8"); testPost.sendRequest(str); long c =
	 * System.currentTimeMillis(); System.out.println("第------->>" + i + "此" +
	 * "耗时:" + (c - b)); } long cc = System.currentTimeMillis();
	 * System.out.println("耗时:" + (bc - cc));
	 * 
	 * }
	 */

	public static void main(String[] args) throws UnsupportedEncodingException {
		long bc = System.currentTimeMillis();
		StringBuffer sendString = new StringBuffer();
		Map<String, String> postdata = new HashMap<String, String>();
		postdata.put("mofing", "1");
		postdata.put("username", "dhy806470396");
		postdata.put("password", "806470396");
//		postdata.put("uid", "20881");
//		postdata.put("star", "4.6");

//		postdata.put("content", URLEncoder.encode("无所谓好或不好，&uid=51&人生一场虚空大梦，韶华白首，不过转瞬。唯有天道恒在，往复循环，不"+bc));
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
		testPost.sendRequest(sendString.toString());
		System.out.println("------------发送结束------------");
		long cc = System.currentTimeMillis();
		System.out.println("耗时:" + (cc - bc));

	}

	public static String sendRequest(String strXml) {
		StringBuffer buffer = null;
		HttpURLConnection c = null;
		try {
			System.out.println("go!");
			// URL url = new
			// URL("http://localhost:8080/ebillion/getPhpServlet");
			// URL url = new
			// URL("http://www.10000-e.com/mreader/getPhpServlet");
			// URL url = new URL(
			// "http://api.10000-e.com/shop/v1/channelProduct/download/2/768715/18559/1617113211181715191349762116906.apk");
//			URL url = new URL(
//					"http://www.mofing.com/api/application/comment/11/chinese.json?"+strXml);
			URL url = new URL(
					"http://www.mofing.com/users/login.json?"+strXml);
			c = (HttpURLConnection) url.openConnection();
			// c.addRequestProperty("Cookie",
			// "CAKEPHP=80b7eeed3f32beecdcd616f6cd17d531; expires=Wed, 17-Oct-2012 07:49:27 GMT; path=/");
			c.setRequestMethod("POST");
			c.setRequestProperty("content-type", "text/html");
			c.setRequestProperty("Accept-Charset", "utf-8");
			 c.setRequestProperty("Content-Length", String.valueOf(strXml.length()));   
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
				// System.out.println(c.getContentType());
			}
			// Map<String, List<String>> h = c.getHeaderFields();
			// for (String s : h.keySet()) {
			// for (String s2 :h.get(s)) {
			// System.out.println(s2);
			// }
			// }
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