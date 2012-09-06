package com.snssly.sms.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

import com.snssly.sms.servlet.DefaultServlet;

/**
 * 发送短信
 * 
 */
public class SendSms {
	private static String URL = Env.read("smsUrl");
	private static String USERNAME = Env.read("smsUserName");
	private static String PASSWORD = Env.read("smsPassword");
	private static String SENDECODE = Env.read("smsSendEcode");
	private static String ENCODE = DefaultServlet.ENCODE;
	private static Logger logger = Logger.getLogger(SendSms.class);

	/**
	 * 请求发送短信接口 <br/>
	 * 注意中文转码问题URLEncoder.encode(USERNAME, encodeURI)
	 * 
	 * @param message
	 *            消息内容
	 * @param mobile
	 *            需要发送的手机号码
	 * @return 获取发送状态
	 */
	public static Integer sendToSomeBody(String message, String mobile) {
		logger.info("开始发送短信：电话号码：" + mobile + "  信息" + message);
		String result = "0";
		try {
			if (mobile != null && !"".equals(mobile.trim()) && message != null
					&& !"".equals(message.trim())) {// 判断手机号码和短信都不能为空
				URL url = new URL(URL);
				String prams = "ECODE=" + SENDECODE + "&USERNAME="
						+ URLEncoder.encode(USERNAME, ENCODE) + "&PASSWORD="
						+ PASSWORD + "&MOBILE=" + mobile + "&CONTENT="
						+ URLEncoder.encode(message, ENCODE);

				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.setDoOutput(true);
				con.setDoInput(true);
				con.setRequestMethod("POST");
				con.getOutputStream().write(prams.getBytes());
				con.getOutputStream().flush();
				con.getOutputStream().close();

				BufferedReader br = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String line;

				while ((line = br.readLine()) != null) {
					result += line;
				}
				br.close();
				logger.info("发送短信成功");
			}
			// message.setStatu(Integer.parseInt(result.trim()));
		} catch (MalformedURLException e) {
			// message.setStatu(-22);
			logger.info("发送短信异常"+e.getMessage());
		} catch (IOException e) {
			// message.setStatu(-23);
			logger.info("发送短信异常"+e.getMessage());
		}
		return Integer.parseInt(result.trim());
	}
}
