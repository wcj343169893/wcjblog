package com.google.choujone.blog.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Blob;
import com.google.choujone.blog.entity.BlogType;

public class Tools {
	/**
	 * @功能 转换字符串值为int型值
	 * @参数 value为要转换的字符串
	 * @返回值 int型值
	 */
	public static String changeHTML(String value) {
		/*
		 * value=value.replace("'", "''"); value=value.replace("&","&amp;");
		 * value=value.replace(" ","&nbsp;"); value=value.replace("<","&lt;");
		 * value=value.replace(">","&gt;"); value=value.replace("\r\n","<br>");
		 */
		return value;
	}

	public static String FilterHTML(String value) {
		value = value.replace("'", "''");
		value = value.replace("&", "&amp;");
		value = value.replace(" ", "&nbsp;");
		value = value.replace("<", "&lt;");
		value = value.replace(">", "&gt;");
		value = value.replace("\r\n", "<br>");
		return value;
	}

	/**
	 * @功能 转换字符串值为int型值
	 * @参数 value为要转换的字符串
	 * @返回值 int型值
	 */
	public static int strToint(String value) {
		int i = -1;
		if (value == null || value.equals(""))
			return i;
		try {
			i = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			i = -1;
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * string转化为long
	 * 
	 * @param value
	 * @return
	 */
	public static Long strTolong(String value) {
		Long i = -1L;
		if (value == null || value.equals(""))
			return i;
		try {
			i = Long.valueOf(value.trim());
		} catch (NumberFormatException e) {
			i = -1L;
		}
		return i;
	}

	/**
	 * @功能 解决通过提交表单产生的中文乱码
	 * @参数 value为要转换的字符串
	 * @返回值 String型值
	 */
	public static String toChinese(String value) {
		return value;
		/*
		 * if (value == null) return ""; try { value = new
		 * String(value.getBytes("ISO-8859-1"), "UTF-8"); return value; } catch
		 * (Exception e) { return ""; }
		 */
	}

	/**
	 * @功能 将Date型日期转换成指定格式的字符串形式，如“yyyy年MM月dd日 HH:mm:ss”
	 * @参数 date为要被转换的Date型日期
	 * @返回值 String型值
	 */
	public static String changeTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, 8);
		return format.format(calendar.getTime());
	}

	public static void blob2Img(Blob blob, HttpServletResponse resp) {
		resp.setContentType("image/jpeg");
		try {
			resp.getOutputStream().write(blob.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 把map数组转换成下拉列表
	 * 
	 * @param blogTypeList
	 *            文章类型所有列表
	 * @param id
	 *            默认需要选中的编号
	 * @return
	 */
	public static String blogTypeList2Str(List<BlogType> blogTypeList, Long id) {
		StringBuffer sb = new StringBuffer();
		for (BlogType blogType : blogTypeList) {
			if (blogType.getParentId() == null || blogType.getParentId() == 0) {
				String selected = "";
				if (id != null && blogType.getId().equals(id)) {
					selected = "selected";
				}
				sb.append("<option value=\"" + blogType.getId() + "\" "
						+ selected + ">" + blogType.getName() + "</option>");
				for (BlogType bt_c : blogTypeList) {
					// System.out.println(bt_c.getParentId()+"  "+
					// blogType.getId());
					if (bt_c.getParentId() != null
							&& bt_c.getParentId().equals(blogType.getId())) {
						selected = "";
						if (id != null && bt_c.getId().equals(id)) {
							selected = "selected";
						}
						sb.append("<option value=\"" + bt_c.getId() + "\" "
								+ selected + "> ├" + bt_c.getName()
								+ "</option>");
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * @param blogTypeList
	 * @return
	 */
	public static String blogTypeList2Str(List<BlogType> blogTypeList) {
		StringBuffer sb = new StringBuffer();
		for (BlogType blogType : blogTypeList) {
			// <li title="<%=bt.getInfo() %>"><a
			// href="/?tid=<%=bt.getId() %>"><%=bt.getName()%>(<%=bd.getCount(bt.getId())
			// %>)</a></li>
			if (blogType.getParentId() == null || blogType.getParentId() == 0) {
				sb.append("<li title=\"" + blogType.getInfo()
						+ "\"><a href=/?tid=" + blogType.getId() + ">"
						+ blogType.getName() + "</a>");
				sb.append("<ul>");
				for (BlogType bt_c : blogTypeList) {
					// System.out.println(bt_c.getParentId()+"  "+
					// blogType.getId());
					if (bt_c.getParentId() != null
							&& bt_c.getParentId().equals(blogType.getId())) {
						sb.append("<li title=\"" + bt_c.getInfo()
								+ "\"><a href=/?tid=" + bt_c.getId() + ">"
								+ bt_c.getName() + "</a></li>");
					}
				}
				sb.append("</ul>");
				sb.append("</li>");
			}
		}
		return sb.toString();
	}
}