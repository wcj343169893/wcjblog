package leo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class zz_test {

	static void test() {
		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		boolean b = false;
		// 正则表达式表示首字母是a，中间是任意字符，结尾以b结束
		// 不匹配的结果
		p = Pattern.compile("a*b");
		m = p.matcher("baaaaab");
		b = m.matches();
		System.out.println("匹配结果：" + b); // 输出：true
		// 匹配的结果
		p = Pattern.compile("a*b");
		m = p.matcher("aaaaab");
		b = m.matches();
		System.out.println("匹配结果：" + b); // 输出：false
	}

	static void test1() {
		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		boolean b = false;
		// 正则表达式表示第一位是1，第二位为3或5，结尾为9位数字的一串数字
		p = Pattern.compile("^[1][3,5]+\\d{9}");
		m = p.matcher("13812345678");
		b = m.matches();
		System.out.println("手机号码正确：" + b); // 输出：true
		//
		p = Pattern.compile("[1][3,5]+\\d{9}");
		m = p.matcher("03812345678");// 错误 首位为0
		// m = p.matcher("13812345-7a");//错误 字符串中有字母或者字符
		b = m.matches();
		System.out.println("手机号码错误：" + b); // 输出：false
	}

	static void test2() {
		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		boolean b = false;
		// 正则表达式表示15位或者18位数字的一串数字
		p = Pattern.compile("\\d{15}|\\d{18}");
		m = p.matcher("120101198506020080");
		b = m.matches();
		System.out.println("身份证号码正确：" + b); // 输出：true
		//
		p = Pattern.compile("\\d{15}|\\d{18}");
		m = p.matcher("020101198506020080");// 错误 首位为0
		b = m.matches();
		System.out.println("身份证号码错误：" + b); // 输出：false
	}

	static void test3() {
		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		boolean b = false;
		// 正则表达式表示邮箱号码
		p = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		m = p.matcher("user+chenl@test.com");
		b = m.matches();
		System.out.println("email号码正确：" + b); // 输出：true
		//
		p = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		m = p.matcher("user.test.com");// 错误 @应为.
		b = m.matches();
		System.out.println("email号码错误：" + b); // 输出：false
	}

	static void test4() {
		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		boolean b = false;
		// 正则表达式表示邮箱号码
		p = Pattern.compile("\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}");
		m = p.matcher("192.168.1.1");
		b = m.matches();
		System.out.println("IP正确：" + b); // 输出：true
		//
		p = Pattern.compile("\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}");
		m = p.matcher("192.168.1.1234");// 错误 应该为3位不应该是4位
		b = m.matches();
		System.out.println("IP错误：" + b); // 输出：false
	}

	static void test5() {
		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		boolean b = false;
		// 正则表达式表示汉字的一串字符串
		p = Pattern.compile("^[\u4e00-\u9fa5]+$");
		m = p.matcher("貂禅");
		b = m.matches();
		System.out.println("中文名正确：" + b); // 输出：true
		//
		p = Pattern.compile("^[\u4e00-\u9fa5]+$");
		m = p.matcher("nick");// 错误 只能是中文
		b = m.matches();
		System.out.println("中文名错误：" + b); // 输出：false
	}

	static void test6() {
		/**
		 * start()返回匹配到的子字符串在字符串中的索引位置. end()返回匹配到的子字符串的最后一个字符在字符串中的索引位置.
		 * group()返回匹配到的子字符串
		 */
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher("aaa2223bb11222");
		while (m.find()) {
			System.out.println(m.start());// 第一个循环返回3，第二个循环返回

			System.out.println(m.end());// 返回7,第二个循环返回14
			System.out.println(m.group());// 返回2233，第二个返回11222
		}
	}

	static void test7() {
		/*
		 * 本例groupCount将返回三组a11bbb、11、bbb
		 */
		Pattern p = Pattern.compile("\\w(\\d\\d)(\\w+)");
		Matcher m = p.matcher("aa11bbb");
		if (m.find()) {
			int count = m.groupCount(); // 返回匹配组的数目，而不是匹配字符串的数目
			for (int i = 0; i <= count; i++)
				System.out.println("group " + i + " :" + m.group(i));
		}
	}

	static void test8() {
		Pattern p=Pattern.compile("\\d+"); //将所含数字去掉
		String str[] = p.split("aa11bbb33cc55gg");
		for (int i = 0; i < str.length; i++) {
		System.out.println(str[i]);
		}
		}
		/*
		* 返回结果如下
		* aa
		* bbb
		* cc
		* gg
		*/
	/*
	 * 返回结果如下 group 0 :a11bbb group 1 :11 group 2 :bbb
	 */
	
	static void test9() {
		Pattern p = Pattern.compile("55");
		Matcher m = p.matcher("aa11bbb33cc55gg55yy");
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
		m.appendReplacement(sb, "@@");//用@@替换所有的55
		}
		System.out.println(sb.toString());//打印aa11bbb33cc@@gg@@
		m.appendTail(sb);//将最后一次替换后的字符串加上
		System.out.println(sb.toString());//打印aa11bbb33cc@@gg@@yy
		}
	
	
	static void test10() {
		
		String regexForFontTag ="<\\s*font\\s+([^>]*)\\s*>";
		String regexForFontAttri="([a-z]+)\\s*=\\s*\"([^\"]+)\"";
		
		
		String content = "<font size=50 color=red/>";
		//String allPattern = "[\\s\\S]*?NewsTilte[^>]*?>\\s*([\\s\\S]+?)\\s*</div>[\\s\\S]*?CurtTitle[^>]*?>\\s*([\\s\\S]*?)\\s*</div>[\\s\\S]+?NewsContent[^>]*>\\s*((?=(?:(?!<script[^>]*src=)[\\s\\S])*src=\\s*(['\"])?((?<=['\"])[^>]+?(?=\\4)|(?!\\s*['\"])[^\\s>]+))[\\s\\S]+?|[\\s\\S]+?)\\s*</div>[\\s\\S]*?NewsAuthor[^>]*?>([^<]+)";
		String allPattern = "[\\s\\S]*?NewsTilte[^>]*?>\\s*([\\s\\S]+?)\\s*</div>[\\s\\S]*?CurtTitle[^>]*?>\\s*([\\s\\S]*?)\\s*</div>[\\s\\S]+?NewsContent[^>]*>((?=(?:(?!<script[^>]*src=)[\\s\\S])*src=\\s*(['\"])?((?<=['\"])[^>]+?(?=\4)|(?!\\s*['\"])[^\\s>]+))[\\s\\S]+?|[\\s\\S]+?)\\s*</div>[\\s\\S]*?NewsAuthor[^>]*?>([^<]+)";
		Pattern p1 = Pattern.compile(regexForFontTag);
		Matcher m1 = p1.matcher(content);
		if(m1.find()){
			System.out.println(m1.group(1));
		}
		
		Pattern p = Pattern.compile("55");
		
		Matcher m = p.matcher("aa11bbb33cc55gg55yy");
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
		m.appendReplacement(sb, "@@");//用@@替换所有的55
		}
		System.out.println(sb.toString());//打印aa11bbb33cc@@gg@@
		m.appendTail(sb);//将最后一次替换后的字符串加上
		System.out.println(sb.toString());//打印aa11bbb33cc@@gg@@yy
		}
	
	public static void main(String argus[]) {
		test10();
	}

}
