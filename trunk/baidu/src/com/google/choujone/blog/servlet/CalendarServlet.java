package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.choujone.blog.util.CalendarUtil;

@SuppressWarnings("serial")
public class CalendarServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		int tYear = Integer.parseInt(sdf.format(date));
		String year = req.getParameter("year") != null
				&& !"".equals(req.getParameter("year").trim()) ? req
				.getParameter("year") : sdf.format(date);
		sdf = new SimpleDateFormat("MM");
		String month = req.getParameter("month") != null
				&& !"".equals(req.getParameter("month").trim()) ? req
				.getParameter("month") : sdf.format(date);
		int tMonth = Integer.parseInt(sdf.format(date));
		sdf = new SimpleDateFormat("dd");
		int tDay = Integer.parseInt(sdf.format(date));
		int y = Integer.parseInt(year);
		int m = Integer.parseInt(month);
		boolean isTyear = tYear == y;// 判断是否是本年
		boolean isTMonth = tMonth == m;// 判断是否是本月
		int preYear = 0;
		int preMonth = 0;
		int nextYear = 0;
		int nextMonth = 0;
		if (m == 1) {
			preYear = y - 1;
			nextYear = y;
			preMonth = 12;
			nextMonth = 2;
		} else if (m == 12) {
			preYear = y;
			nextYear = y + 1;
			preMonth = 11;
			nextMonth = 1;
		} else {
			preYear = y;
			nextYear = y;
			preMonth = m - 1;
			nextMonth = m + 1;
		}
		String[] days = CalendarUtil.getCalendar(y, m);
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Cache-Control", "no-cache");
		sdf = new SimpleDateFormat("yyyy-MM");
		String responseText = "<div align='center'>";
		responseText += "<a href=\"javascript:void(0)\" onclick=\"checkCal('"
				+ preYear + "','" + preMonth + "')\" title=\"" + preYear + "年"
				+ preMonth + "月\">&lt; &lt; </a> &nbsp;&nbsp;";
		responseText += year + "年" + month + "月";
		responseText += " &nbsp;&nbsp;<a href=\"javascript:void(0)\" onclick=\"checkCal('"
				+ nextYear
				+ "','"
				+ nextMonth
				+ "')\" title=\""
				+ nextYear
				+ "年" + nextMonth + "月\">&gt; &gt; </a>";
		responseText += "<div>";
		responseText += "<table class='tableCalendar' align='center' style=\"background: url('/images/calendar/month"
				+ m + ".gif') no-repeat center;\">";
		responseText += "<tr><td class=\"week-sun\">日</td><td class=\"week\">一</td><td class=\"week\">二</td><td class=\"week\">三</td><td class=\"week\">四</td><td class=\"week\">五</td><td class=\"week-sat\">六</td></tr>";
		for (int j = 0; j < 6; j++) {
			responseText += "<tr>";
			for (int i = j * 7; i < (j + 1) * 7; i++) {
				if (i % 7 == 0) {
					responseText += "<td class=\"week-sun";
				} else if (i % 7 == 6) {
					responseText += "<td class=\"week-sat";
				} else {
					responseText += "<td class=\"week";
				}
				if (!"".equals(days[i].trim()) && isTMonth && isTyear && tDay==Integer.parseInt(days[i].trim())) {
					responseText += " tdays";
				}
				responseText += "\">";
				responseText += days[i];
				responseText += "</td>";
			}
			responseText += "</tr>";
		}
		responseText += "</table>";
		// System.out.println(responseText);
		PrintWriter out = resp.getWriter();
		out.println(responseText);
		out.close();
	}
}