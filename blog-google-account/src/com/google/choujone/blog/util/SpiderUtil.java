package com.google.choujone.blog.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.choujone.blog.common.WebPage;
import com.google.choujone.blog.entity.Spider;

/**
 * choujone'blog<br>
 * 功能描述：采集模块，最好弄个定时采集 2011-12-7
 */
public class SpiderUtil {
	public String charset = "utf-8";// 采集内容的编码
	public String web_host = "";// 网站的网址
	public String listUrl = "";
	public String web_list_begin = "";
	public String web_list_end = "";
	public List<String> contentUrls = new ArrayList<String>();
	public String web_content_title = "";// 标题正则表达式
	public String web_content_begin = "";// 内容开始位置
	public String web_content_end = "";// 内容结束位置

	public String[] clear_title_reg;// 标题清理标签
	public String[] clear_content_reg;// 内容清理标签

	public static String getUrl() {
		String line = "";
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL("http://www.williamlong.info/archives/2904.html");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream()));
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			reader.close();
			System.out.println(sb.toString());
		} catch (MalformedURLException e) {
			// ...
		} catch (IOException e) {
			// ...
		}
		return sb.toString();
	}

	/**
	 * 根据链接获取内容
	 * 
	 * @param link
	 * @return
	 */
	public String getContent(String link) {
		String line = "";
		StringBuffer sb = new StringBuffer();
		// TODO 需要设置编码，不然gbk的就乱码了
		try {
			URL url = new URL(link);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), charset));
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			reader.close();
			// System.out.println(sb.toString());
			// System.out.println("获取内容成功");
		} catch (MalformedURLException e) {
			// ...
		} catch (IOException e) {
			// ...
		}
		return sb.toString();
		// return Tools.escape(line);
	}

	/**
	 * 根据首尾字符串，截取内容
	 * 
	 * @param content
	 * @param begin
	 * @param end
	 * @return
	 */
	public String split(String content, String begin, String end) {
		int begin_index = getIndex(content, begin);
		int end_index = getIndex(content, end);
		if (begin_index != -1 && end_index != -1 && begin_index < end_index) {
			return content.substring(begin_index, end_index);
		}
		return "";
	}

	/**
	 * 查找位置
	 * 
	 * @param content
	 * @param find
	 * @return
	 */
	public int getIndex(String content, String find) {
		if (Tools.isNotNull(content) && Tools.isNotNull(find)) {
			return content.indexOf(find);
		}
		return -1;
	}

	/**
	 * 得到内容页网址列表
	 * 
	 * @param content
	 * @return
	 */
	public List<String> getLinks(String content) {
		List<String> links = new ArrayList<String>();
		Pattern p = Pattern
				.compile("(<a\\s+([^>h]|h(?!ref\\s))*href[\\s+]?=[\\s+]?('|\"))([^(\\s+|'|\")]*)([^>]*>)(.*?)</a>");
		if (Tools.isNotNull(content)) {
			Matcher m = p.matcher(content);
			while (m.find()) {
				// TODO 判断链接是否为绝对地址
				String url = m.group(4);
				if (getIndex(url, web_host) == -1) {
					url = web_host + url;
				}
				links.add(url);
			}
		}
		return links;
	}

	/**
	 * 得到内容页网址列表
	 * 
	 * @return
	 */
	public List<String> getLinks() {
		return getLinks(split(getContent(listUrl), web_list_begin, web_list_end));
	}

	/**
	 * 得到内容页网址列表
	 * 
	 * @param listUrl
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<String> getLinks(String listUrl, String begin, String end) {
		return getLinks(split(getContent(listUrl), begin, end));
	}

	/**
	 * 根据正则获取内容 对于较短的内容有效
	 * 
	 * @param input
	 * @param regex
	 * @return
	 */
	public String getContentByReg(String input, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		if (m.find()) {
			// System.out.println("m.group:"+m.group());
			return m.group(1);
		}
		return "";
	}

	/**
	 * 根据前后标识内容 获取内容
	 * 
	 * @param input
	 *            内容
	 * @param begin
	 *            开始标识
	 * @param end
	 *            结束标识
	 * @return
	 */
	public String getContentByReg(String input, String begin, String end) {
		return split(input, begin, end);
	}

	/**
	 * 清理所有标签
	 * 
	 * @param content
	 * @return
	 */
	public String clear(String content) {
		return content.replaceAll("<[^>]*>", "");
	}

	/**
	 * 删除页面标签，保留args标签
	 * 
	 * @param content
	 * @param args
	 *            需要保留的标签
	 * @return
	 */
	public String clear(String content, Object... args) {
		StringBuffer reg = new StringBuffer("<[^>]*>");
		if (args != null && args.length > 0) {
			reg = new StringBuffer("<(?!");
			for (int i = 0; i < args.length; i++) {
				reg.append(args[i]);
				if (i < args.length - 1) {
					reg.append("|");
				}
			}
			reg.append(")[^>]*>");
		}
		return content.replaceAll(reg.toString(), "");
	}

	/**
	 * 批量删除内容
	 * 
	 * @param content
	 * @param args
	 * @return
	 */
	public String replace(String content, String... args) {
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				content = content.replaceAll(args[i], "");
			}
		}
		return content;
	}

	/**
	 * 根据列表地址，获取所有内容
	 * 
	 * @param url
	 * @return
	 */
	public List<WebPage> getWebPages(String url) {
		this.setListUrl(url);
		return getWebPages();
	}

	public List<WebPage> getWebPages() {
		List<WebPage> webPages = new ArrayList<WebPage>();
		List<String> web_urls = this.getContentUrls();
		for (String string : web_urls) {
			webPages.add(getWebPage(string));
		}
		return webPages;
	}

	/**
	 * 获取页面内容
	 * 
	 * @param url
	 * @return
	 */
	public WebPage getWebPage(String url) {
		WebPage webPage = new WebPage();
		try {
			webPage.setUrl(url);
			// 获取页面内容
			String content = getContent(url);
			// 获取标题
			String title = getContentByReg(content, web_content_title);
			// TODO 清理标题中的标签
			title = clear(title, getClear_title_reg());
			webPage.setTitle(title);
			// 获取内容
			content = getContentByReg(content, web_content_begin,
					web_content_end);
			// TODO 清理内容中的标签
			content = clear(content, getClear_content_reg());
			webPage.setContent(content);
			webPage.setDate(new Date());
		} catch (Exception e) {
			System.out.println("获取网页内容错误" + e.getMessage());
		}
		return webPage;
	}

	public List<WebPage> run(Spider spider) {
		// 设置编码
		this.setCharset(spider.getCharset());
		// 设置域名
		this.setWeb_host(spider.getWeb_host());
		// 设置列表页地址
		this.setListUrl(spider.getWeb_list_url());
		// 设置list列表区域开始和结束
		this.setWeb_list_begin(spider.getWeb_list_begin());
		this.setWeb_list_end(spider.getWeb_list_end());
		// 得到列表之后，设置内容页的标题
		this.setWeb_content_title(spider.getWeb_content_title());
		// 设置标签清理 为空代表清理所有
		this.setClear_title_reg(null);
		// 设置内容开始结束位置
		this.setWeb_content_begin(spider.getWeb_content_begin());
		this.setWeb_content_end(spider.getWeb_content_end());
		// 清理内容标签
		this.setClear_content_reg(spider.getClear_content_reg().split(","));
		return getWebPages();
	}

	public static void main(String[] args) {
		// Pattern p = Pattern
		// .compile("(<a\\s+([^>h]|h(?!ref\\s))*href[\\s+]?=[\\s+]?('|\"))([^(\\s+|'|\")]*)([^>]*>)(.*?)</a>");
		// String str =
		// "<a href='/html/gndy/dyzz/20120207/36399.html'>尼古拉斯·凯奇2011最新《寻求正义》DVD中</a><br> ·<a href='/html/gndy/dyzz/20120206/36386.html'>2011年度BBC记录大片《冰冻星球》BD中英双</a><br> <a href='/html/gndy/dyzz/20120205/36373.html'>1024分辨率《穿孔/刺穿》BD中英双字</a><br> ·<a href='/html/gndy/dyzz/20120205/36372.html'>1024分辨率《勇气/英勇无畏》BD中英双字</a><br><a href='/html/gndy/dyzz/20120205/36368.html'>1024分辨率《三个火枪手》BD中字</a><br> ·<a href='/html/gndy/dyzz/20120204/36350.html'>1024分辨率《超世纪战神/魔王》BD中英双字</a><br>·<a href='/html/gndy/dyzz/20120204/36349.html'>1024分辨率《保卫战队之出动喇！朋友！》BD</a><br> ·<a href='/html/gndy/dyzz/20120204/36348.html'>1024分辨率《寻堡奇遇3加长版》BD中英双字</a><br>";
		// Matcher m = p.matcher(str);
		// while (m.find()) {
		// // System.out.println(m.group());
		// System.out.println(m.group(4));// 获取href中的值
		// System.out.println(m.group(6));// 获取标签内容
		// }
		// 测试采集列表地址
		// SpiderUtil spider = new SpiderUtil();
		// spider.setCharset("gbk");
		// spider.setWeb_host("http://www.xiaodiao.com");
		// List<String> lists = spider.getLinks(
		// "http://www.xiaodiao.com/html/gndy/dyzz/index.html",
		// "<div class=\"co_content8\">",
		// "align=\"center\" bgcolor=\"#F4FAE2\"");
		//
		// for (String string : lists) {
		// System.out.println(string);
		// }

		// 清理标签，除了特定的以外
		// String str = spider
		// .getContentByReg(
		// spider
		// .getContent("http://www.xiaodiao.com/html/gndy/dyzz/20120210/36434.html"),
		// "<h1>(.*)</h1>");
		// System.out.println("得到的内容:" + str);
		// System.out.println("清理后的内容:" + spider.clear(str));
		// String test =
		// "234<strong>324</strong>324<em>32<a href=\"#\">4te</a>st1</em>2<img src=\"test.jpg\" />3";
		// System.out.println(test);
		// System.out
		// .println(test.replaceAll("<(?!strong|/strong|img)[^>]*>", ""));

		// System.out.println("spider.clear:" + spider.clear(test, "img"));

		// 获取内容
		// String content = spider
		// .getContentByReg(
		// spider
		// .getContent("http://www.xiaodiao.com/html/gndy/dyzz/20120210/36434.html"),
		// "<div id=\"Zoom\">", "安装软件后,点击即可下载,谢谢大家支持，欢迎每天来");
		// System.out.println("页面内容"
		// + spider.replace(spider.clear(content, "img", "p", "/p",
		// "span", "/span"), "&nbsp;", "\n", "\t"));
		// System.out.println("页面内容2"+spider.getContentByReg(spider
		// .getContent("http://www.xiaodiao.com/html/gndy/dyzz/20120210/36434.html"),
		// "<div[^>]*>(.*)</div>"));
		// 所有连起来测试一下
		SpiderUtil spider = new SpiderUtil();
		spider.setCharset("gbk");
		spider.setWeb_host("http://www.xiaodiao.com");
		// 设置list地址
		spider.setListUrl("http://www.xiaodiao.com/html/gndy/dyzz/index.html");
		// 设置list列表区域开始和结束
		spider.setWeb_list_begin("<div class=\"co_content8\">");
		spider.setWeb_list_end("align=\"center\" bgcolor=\"#F4FAE2\"");
		// 得到列表之后，设置内容页的标题
		spider.setWeb_content_title("<h1>(.*)</h1>");
		// 设置标签清理 为空代表清理所有
		spider.setClear_title_reg(null);
		// 设置内容开始结束位置
		spider.setWeb_content_begin("<div id=\"Zoom\">");
		spider.setWeb_content_end("安装软件后,点击即可下载,谢谢大家支持，欢迎每天来");
		// 清理内容标签
		spider.setClear_content_reg(new String[] { "img", "p", "/p", "span","br",
				"/span" });
		// 得到内容页面
		List<WebPage> webPages = spider.getWebPages();
		for (WebPage webPage : webPages) {
//			System.out.println("url:" + webPage.getUrl() + " \t title:"
//					+ webPage.getTitle() + "\t content:"
//					+ webPage.getContent().substring(0, 100));
			System.out.println(webPage.getContent());
		}
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getWeb_host() {
		return web_host;
	}

	public void setWeb_host(String webHost) {
		web_host = webHost;
	}

	public String getListUrl() {
		return listUrl;
	}

	public void setListUrl(String listUrl) {
		this.listUrl = listUrl;
	}

	public String getWeb_list_begin() {
		return web_list_begin;
	}

	public void setWeb_list_begin(String webListBegin) {
		web_list_begin = webListBegin;
	}

	public String getWeb_list_end() {
		return web_list_end;
	}

	public void setWeb_list_end(String webListEnd) {
		web_list_end = webListEnd;
	}

	/**
	 * 获取内容链接
	 * 
	 * @return
	 */
	public List<String> getContentUrls() {
		// TODO 获取内容链接
		return getLinks();
	}

	public void setContentUrls(List<String> contentUrls) {
		this.contentUrls = contentUrls;
	}

	public String getWeb_content_title() {
		return web_content_title;
	}

	public void setWeb_content_title(String webContentTitle) {
		web_content_title = webContentTitle;
	}

	public String getWeb_content_begin() {
		return web_content_begin;
	}

	public void setWeb_content_begin(String webContentBegin) {
		web_content_begin = webContentBegin;
	}

	public String getWeb_content_end() {
		return web_content_end;
	}

	public void setWeb_content_end(String webContentEnd) {
		web_content_end = webContentEnd;
	}

	public String[] getClear_title_reg() {
		return clear_title_reg;
	}

	public void setClear_title_reg(String[] clearTitleReg) {
		clear_title_reg = clearTitleReg;
	}

	public String[] getClear_content_reg() {
		return clear_content_reg;
	}

	public void setClear_content_reg(String[] clearContentReg) {
		clear_content_reg = clearContentReg;
	}

}
