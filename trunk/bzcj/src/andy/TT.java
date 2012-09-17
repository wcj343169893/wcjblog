package andy;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class TT {
	public static void main(String[] args) {
		// String name="勇士大冒险.swf?CourseCode=2SXRJ01&ContentID=040104";
		// name=name.substring(name.lastIndexOf("."),name.indexOf(".")+4);
		// System.out.println(name);
		TT tt = new TT();
		// http://api.liqwei.com/mail/?address=343169898@qq.com&subject=%E8%AE%A9%E4%BD%A0%E7%AC%91%E5%96%B7%E7%9A%84%E9%82%AE%E4%BB%B6

		for (int i = 12349898; i < 12359898; i++) {
			tt.a("http://api.liqwei.com/mail/?address=" + 12349898
					+ "@qq.com&subject=hello%20gay");
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void a(String url) {
		MailThread mt = new MailThread();
		mt.setUrl(url);
		mt.start();
	}

	class MailThread extends Thread {
		String url = "";

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		@Override
		public void run() {
			String msg = getpage(this.getUrl());
			System.out.println(this.getId()+msg);
		}

		public String getpage(String url) {
			HttpClient client = new HttpClient();
			GetMethod method = new GetMethod(url);
			method.getParams().setContentCharset("GB2312");
			String responseStr = "";
			try {
				client.executeMethod(method);
				responseStr = method.getResponseBodyAsString();
				method.releaseConnection();

			} catch (IOException e) {
				e.printStackTrace();
			}
			return responseStr;
		}
	}
}
