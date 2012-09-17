package andy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class Kid {
	// static Logger logger = Logger.getLogger(dptoolsForWindows.class);
	static String fileDirPath = "E:/kid/pic/";
	static Map<String, String> detailLink = new HashMap<String, String>();
	static int count = 0;
	String floder = "";

	public static void main(String[] args) {
		try {
			Kid kid = new Kid();
			kid.zuoye_grade2_makeMpk();
			//kid.zuoye_grade2();
			// kid.makeMpk();
			// kid.copyFolder("E:/kid/template", "E:/kid/template2");
			// kid.makeMpk();
			// 成语：http://kid.qq.com/c/chengyulist_3.htm
			// 数学：http://kid.qq.com/c/shuxue1_1.htm
			// kid.getFzwb("http://kid.qq.com/c/shuxue1_", 1, 9);
			// kid.download("拔山举鼎",
			// "http://mat1.qq.com/kid/flash/09/03/bashanjuding_6.swf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void zuoye_grade2_makeMpk() {
		this.floder = "一年级英语";
		makeMpk();
	}

	public void zuoye_grade2() {
		String url = "/c/yuwen3";///c/shuxue2  /c/eng2  12
		try {
			this.floder = "一年级英语";
			getFzwb("http://kid.qq.com/c/eng1_", 1, 7);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 制作mpk
	 */
	public void makeMpk() {
		// 读取swf列表
		String swfFloder = "E:/kid/swf/" + this.floder + "/";
		File file = new File(swfFloder);
		File[] files = file.listFiles();
		for (File file2 : files) {
			makeMpk(file2);
			// break;
		}
	}

	public void makeMpk(File file) {
		String name = file.getName();
		String pinyin = "";
		String jsonUrl = "";
		String floderName = "";
		String floderPath = "";
		if (name.indexOf("swf") == -1) {
			return;
		}
		// file:///data/local/data/mpk/拔苗助长.mpk/Media/wang.html?swf=swf/bamao
		// 去除空格，冒号，分号
		name = name.replace(" ", "");
		name = name.replace("：", "");
		name = name.replace(";", "");
		name = name.substring(0, name.indexOf("."));
		floderName = name + ".mpk";
		floderPath = "E:/kid/mpk/" + this.floder + "/"+floderName;
		pinyin = CnToSpell.getFullSpell(name);
		// System.out.println(pinyin);
		jsonUrl = "file:///data/local/data/mpk/" + name
				+ ".mpk/Media/wang.html?swf=swf/" + pinyin;
		// 模板地址E:\kid\template
		//TODO: 如果存在  就返回
		
		copyFolder("E:/kid/template", floderPath);
		// 修改jsonE:\kid\template\Layout/home_horizontal.json
		// 替换[URL] 为jsonUrl
		modify(floderPath + "/Layout/home_horizontal.json", "[URL]", jsonUrl);
		// 复制swf 到 E:\kid\template\Media\swf
		copyFile(file, floderPath + "/Media/swf/" + pinyin + ".swf");
		System.out.println(name + "成功");
	}

	/**
	 * 复制文件
	 * 
	 * @param oldFile
	 * @param newPath
	 */
	private void copyFile(File oldFile, String newPath) {
		if (oldFile.isFile()) {
			try {
				FileInputStream input = new FileInputStream(oldFile);
				FileOutputStream output = new FileOutputStream(newPath);
				byte[] b = new byte[1024 * 5];
				int len;
				while ((len = input.read(b)) != -1) {
					output.write(b, 0, len);
				}
				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void modify(String path, String oldChar, String newChar) {
		String source = readFile(path);
		source = source.replace(oldChar, newChar);
		writeFile(path, source);
	}

	/**
	 * 写入文件
	 * 
	 * @param path
	 * @param str
	 */
	private void writeFile(String path, String str) {
		try {
			File file = new File(path);
			FileOutputStream os = new FileOutputStream(file);
			// System.out.println(str);
			os.write(str.getBytes());
			os.flush();
			os.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 读取文件
	 * 
	 * @param path
	 * @return
	 */
	private String readFile(String path) {
		StringBuffer sb = new StringBuffer();
		try {
			FileInputStream f = new FileInputStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(f));
			while (br.ready()) {
				sb.append(br.readLine() + "\n");
			}
			br.close();
			f.close();
		} catch (Exception e) {
			System.err.println("File input error!");
		}
		return sb.toString();
	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	private static void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();
		}
	}

	public void getFzwb(String rq, int start, int end) throws Exception {
		String url = rq;
		for (int i = start; i <= end; i++) {
			Random random = new Random();
			String d = random.nextInt() + "";
			d = d.substring(d.length() - 3);

			url = rq + i + ".htm?" + d;
			String content = getpage(url);
			Map<String, String> detailMap = getDetailList(content);
			System.out.println(url);
			for (String key : detailMap.keySet()) {
				// 获取详细内容中的链接地址
				String detail = getpage(detailMap.get(key));
				String pattern = "flashUrl='(.*?)'";
				Pattern p = Pattern.compile(pattern);
				Matcher m = p.matcher(detail);
				while (m.find()) {
					String link = m.group(1);
					System.out.println(link);
					if (!detailLink.containsValue(link)) {
						detailLink.put(key, link);
						download(key,link);
					}
				}
			}
			//for (String k : detailLink.keySet()) {
			//			download(key,link);
				// System.out.println(++count);
			//}
		}
		// System.out.println(content);
	}

	/**
	 * 下载swf文件
	 * 
	 * @param url
	 */
	public void download(String title, String url) {
		DownloadThread dl = new DownloadThread();
		dl.setSwf(url);
		dl.setTitle(title);
		dl.setFloder("E:/kid/swf/" + this.floder + "/");
		dl.start();
	}

	class DownloadThread extends Thread {
		String swf = "";
		String title = "";
		String floder = "E:/kid/swf/";

		@Override
		public void run() {
			DownloadFile df = new DownloadFile();
			df.downFile(this.getSwf(), this.getTitle(), this.floder);
			System.out.println((++count) + this.getTitle() + "下载成功");
		}

		public String getSwf() {
			return swf;
		}

		public void setSwf(String swf) {
			this.swf = swf;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getFloder() {
			return floder;
		}

		public void setFloder(String floder) {
			this.floder = floder;
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
			String pattern = "<a[^>]+href=\"(.*?)\"\\s*title=['\"](.*?)['\"]>";
			String pre = "http://kid.qq.com";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(content);
			while (m.find()) {
				String dlink = pre + m.group(1);
				String title = m.group(2);
				// System.out.println(dlink);
				// System.out.println(m.group(2));
				if (!map.containsKey(title)) {
					map.put(title, dlink);
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
