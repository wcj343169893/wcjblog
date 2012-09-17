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
		Pattern p = null; // ������ʽ
		Matcher m = null; // �������ַ���
		boolean b = false;
		// ������ʽ��ʾ����ĸ��a���м��������ַ�����β��b����
		// ��ƥ��Ľ��
		p = Pattern.compile("a*b");
		m = p.matcher("baaaaab");
		b = m.matches();
		System.out.println("ƥ������" + b); // �����true
		// ƥ��Ľ��
		p = Pattern.compile("a*b");
		m = p.matcher("aaaaab");
		b = m.matches();
		System.out.println("ƥ������" + b); // �����false
	}

	static void test1() {
		Pattern p = null; // ������ʽ
		Matcher m = null; // �������ַ���
		boolean b = false;
		// ������ʽ��ʾ��һλ��1���ڶ�λΪ3��5����βΪ9λ���ֵ�һ������
		p = Pattern.compile("^[1][3,5]+\\d{9}");
		m = p.matcher("13812345678");
		b = m.matches();
		System.out.println("�ֻ�������ȷ��" + b); // �����true
		//
		p = Pattern.compile("[1][3,5]+\\d{9}");
		m = p.matcher("03812345678");// ���� ��λΪ0
		// m = p.matcher("13812345-7a");//���� �ַ���������ĸ�����ַ�
		b = m.matches();
		System.out.println("�ֻ��������" + b); // �����false
	}

	static void test2() {
		Pattern p = null; // ������ʽ
		Matcher m = null; // �������ַ���
		boolean b = false;
		// ������ʽ��ʾ15λ����18λ���ֵ�һ������
		p = Pattern.compile("\\d{15}|\\d{18}");
		m = p.matcher("120101198506020080");
		b = m.matches();
		System.out.println("���֤������ȷ��" + b); // �����true
		//
		p = Pattern.compile("\\d{15}|\\d{18}");
		m = p.matcher("020101198506020080");// ���� ��λΪ0
		b = m.matches();
		System.out.println("���֤�������" + b); // �����false
	}

	static void test3() {
		Pattern p = null; // ������ʽ
		Matcher m = null; // �������ַ���
		boolean b = false;
		// ������ʽ��ʾ�������
		p = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		m = p.matcher("user+chenl@test.com");
		b = m.matches();
		System.out.println("email������ȷ��" + b); // �����true
		//
		p = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		m = p.matcher("user.test.com");// ���� @ӦΪ.
		b = m.matches();
		System.out.println("email�������" + b); // �����false
	}

	static void test4() {
		Pattern p = null; // ������ʽ
		Matcher m = null; // �������ַ���
		boolean b = false;
		// ������ʽ��ʾ�������
		p = Pattern.compile("\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}");
		m = p.matcher("192.168.1.1");
		b = m.matches();
		System.out.println("IP��ȷ��" + b); // �����true
		//
		p = Pattern.compile("\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}");
		m = p.matcher("192.168.1.1234");// ���� Ӧ��Ϊ3λ��Ӧ����4λ
		b = m.matches();
		System.out.println("IP����" + b); // �����false
	}

	static void test5() {
		Pattern p = null; // ������ʽ
		Matcher m = null; // �������ַ���
		boolean b = false;
		// ������ʽ��ʾ���ֵ�һ���ַ���
		p = Pattern.compile("^[\u4e00-\u9fa5]+$");
		m = p.matcher("����");
		b = m.matches();
		System.out.println("��������ȷ��" + b); // �����true
		//
		p = Pattern.compile("^[\u4e00-\u9fa5]+$");
		m = p.matcher("nick");// ���� ֻ��������
		b = m.matches();
		System.out.println("����������" + b); // �����false
	}

	static void test6() {
		/**
		 * start()����ƥ�䵽�����ַ������ַ����е�����λ��. end()����ƥ�䵽�����ַ��������һ���ַ����ַ����е�����λ��.
		 * group()����ƥ�䵽�����ַ���
		 */
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher("aaa2223bb11222");
		while (m.find()) {
			System.out.println(m.start());// ��һ��ѭ������3���ڶ���ѭ������

			System.out.println(m.end());// ����7,�ڶ���ѭ������14
			System.out.println(m.group());// ����2233���ڶ�������11222
		}
	}

	static void test7() {
		/*
		 * ����groupCount����������a11bbb��11��bbb
		 */
		Pattern p = Pattern.compile("\\w(\\d\\d)(\\w+)");
		Matcher m = p.matcher("aa11bbb");
		if (m.find()) {
			int count = m.groupCount(); // ����ƥ�������Ŀ��������ƥ���ַ�������Ŀ
			for (int i = 0; i <= count; i++)
				System.out.println("group " + i + " :" + m.group(i));
		}
	}

	static void test8() {
		Pattern p=Pattern.compile("\\d+"); //����������ȥ��
		String str[] = p.split("aa11bbb33cc55gg");
		for (int i = 0; i < str.length; i++) {
		System.out.println(str[i]);
		}
		}
		/*
		* ���ؽ������
		* aa
		* bbb
		* cc
		* gg
		*/
	/*
	 * ���ؽ������ group 0 :a11bbb group 1 :11 group 2 :bbb
	 */
	
	static void test9() {
		Pattern p = Pattern.compile("55");
		Matcher m = p.matcher("aa11bbb33cc55gg55yy");
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
		m.appendReplacement(sb, "@@");//��@@�滻���е�55
		}
		System.out.println(sb.toString());//��ӡaa11bbb33cc@@gg@@
		m.appendTail(sb);//�����һ���滻����ַ�������
		System.out.println(sb.toString());//��ӡaa11bbb33cc@@gg@@yy
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
		m.appendReplacement(sb, "@@");//��@@�滻���е�55
		}
		System.out.println(sb.toString());//��ӡaa11bbb33cc@@gg@@
		m.appendTail(sb);//�����һ���滻����ַ�������
		System.out.println(sb.toString());//��ӡaa11bbb33cc@@gg@@yy
		}
	
	public static void main(String argus[]) {
		test10();
	}

}
