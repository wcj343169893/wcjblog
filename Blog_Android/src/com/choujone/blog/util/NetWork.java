package com.choujone.blog.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * 网络应用
 * 
 * @author choujone
 */
public class NetWork {

	/**
	 * @param title 标题
	 * @param tid	分类编号
	 * @param content	内容	
	 * @param isVisible	是否发布
	 */
	public static boolean postData(String title,String tid,String content,String isVisible) {
		boolean flag=false;
		//http://www.choujone.com/blog4Android?title=aadddddda&content=bbcdssfsddddddddddb&tag=test&tid=1343875969020&isVisible=0
		String destUrl = "http://www.choujone.com/blog4Android";
//		String destUrl = "http://WWW.10000-e.com/temp/blog.php";
		// instantiate HttpPost object from the url address
		HttpEntityEnclosingRequestBase httpRequest = new HttpPost(destUrl);
		// the post name and value must be used as NameValuePair
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("title", title));
		params.add(new BasicNameValuePair("tid", tid));
		params.add(new BasicNameValuePair("isVisible", isVisible));
		params.add(new BasicNameValuePair("tag", ""));
		params.add(new BasicNameValuePair("content", content));
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			// execute the post and get the response from servers
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// get the result
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				// tvResult.setText(strResult);
				Log.d("netWork", strResult);
				flag=true;
			} else {
				// tvResult.setText("Error Response"+httpResponse.getStatusLine().toString());
				Log.d("netWork", httpResponse.getStatusLine().toString());
			}
		} catch (Exception e) {
			System.out.println("error occurs");
		}
		return flag;
	}
}
