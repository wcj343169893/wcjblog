package andy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class Keakon {
	public static int count = 1;
	public static List<String> indexs = new ArrayList<String>();// 索引
	public static int maxThread = 500;

	public static void main(String[] args) {
		Keakon keakon = new Keakon();
		keakon.start("http://www.keakon.net/");
		// String content = keakon.getpage("http://www.keakon.net/");
		// System.out.println(content);
		// Map<String, String> urls = keakon.getDetailList(content);
		// for (String url : urls.keySet()) {
		// System.out.println(url + "--->" + urls.get(url));
		// }
		// System.out.println(urls.size());
	}

	public void start(String url) {
		Spider spider = new Spider(url);
		spider.start();
	}

	class Spider extends Thread {
		int i = 0;
		String url = "";
		List<String> links = new ArrayList<String>();

		public Spider() {
		}

		public Spider(String url) {
			this.url = url;
		}

		@Override
		public void run() {
			String content = getpage(this.url);
			this.links = getDetailList(content);
			System.out.println(i + "-->" + this.url);
			if (content == null || this.links == null || this.links.size() == 0) {
				this.stop();
			}
			for (String s : this.links) {
				if (Keakon.count < Keakon.maxThread) {
					Keakon.count++;
					Spider spider = new Spider(s);
					spider.i = Keakon.count;
					spider.start();
				}
			}
		}

		public List<String> getDetailList(String content) {
			List<String> links = new ArrayList<String>();
			if (content != null) {
				// var
				// flashUrl='http://mat1.qq.com/kid/flash/07/02/07/mdhz0207.swf';
				// String pattern =
				// "<a[^>]+href=\"([\\s\\S]*?)\"[^>]+title=\"([^'\"\\s]+)\"[^>]*>";
				// String pattern =
				// "<a[^>]+href=['\"](.*?)['\"]\\s*title=['\"]?([^'\"\\s]+)[^>]*>";
				// String pattern = "<a[^>]+href=\"(.*?)\"\\s*>(.*?)</a>";
				String pattern = "<a\\s+([^>h]|h(?!ref\\s))*(?<=[\\s+]?href[\\s+]?=[\\s+]?('|\")?)([^\"|'>]+?(?=\"|'))(.+?)?((?<=>)(.+?)?(?=</a>))";
				String pre = "http://www.keakon.net";
				Pattern p = Pattern.compile(pattern);
				Matcher m = p.matcher(content);
				while (m.find()) {
					String link = m.group(3);
					if (link != null && link.indexOf("http") == -1
							&& link.indexOf("javascript") == -1
							&& link.indexOf("category") == -1
							&& link.indexOf("about") == -1
							&& link.indexOf("tag") == -1
							&& link.indexOf("#") == -1
							&& link.indexOf("respond") == -1
							&& link.indexOf("login") == -1
							&& link.indexOf("feed") == -1
							&& link.indexOf("wap") == -1 && link.length() > 5) {
						String dlink = pre + link;
						if (!links.contains(dlink)
								&& !Keakon.indexs.contains(dlink)) {
							links.add(dlink);
						}
					}
				}
			}
			return links;
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

	/**
	 * 获取详细页地址
	 * 
	 * @param url
	 * @return
	 */
	public Map<String, String> getDetailList(String content) {
		Map<String, String> map = new HashMap<String, String>();
		if (content != null) {
			// var
			// flashUrl='http://mat1.qq.com/kid/flash/07/02/07/mdhz0207.swf';
			// String pattern =
			// "<a[^>]+href=\"([\\s\\S]*?)\"[^>]+title=\"([^'\"\\s]+)\"[^>]*>";
			// String pattern =
			// "<a[^>]+href=['\"](.*?)['\"]\\s*title=['\"]?([^'\"\\s]+)[^>]*>";
			// String pattern =
			// "<a[^>]+href=\"(.*?)\"\\s*title=['\"](.*?)['\"]>";
			// String pattern = "<a[^>]+href=\"(.*?)\"\\s*>(.*?)</a>";
			// String pattern
			// ="(?<=[\\s+]?href[\\s+]?=[\\s+]?('|\")?)[^(\"|')>]+?(?=\"|')";
			String pattern = "<a\\s+([^>h]|h(?!ref\\s))*(?<=[\\s+]?href[\\s+]?=[\\s+]?('|\")?)([^\"|'>]+?(?=\"|'))(.+?)?((?<=>)(.+?)?(?=</a>))";
			String pre = "http://www.keakon.net";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(content);
			while (m.find()) {
				String dlink = m.group(3);
				// System.out.println(dlink);
				// System.out.println(m.group(1));
				// System.out.println(m.group(2));
				// System.out.println(m.group(3));
				// System.out.println(m.group(5));
				// String title = m.group(2);
				if (dlink != null && dlink.indexOf("http") == -1) {
					dlink = pre + dlink;
					if (!map.containsKey(dlink)) {
						map.put(dlink, dlink);
					}
				}
			}
		}
		return map;
	}

	/**
	 * 获取内容
	 * 
	 * @param url
	 * @return
	 */
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
