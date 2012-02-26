package com.google.choujone.blog.util;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {
	private static Calendar calendar = Calendar.getInstance();

	public static void main(String[] args) {
		int year = 2010;
		int month = 12;
		String days[];
		days = new String[42];
		for (int i = 0; i < 42; i++) {
			days[i] = "";
		}
		Calendar thisMonth = Calendar.getInstance();
		thisMonth.set(Calendar.YEAR, year);// 设置查询的年份
		thisMonth.set(Calendar.MONTH, month - 1);// 设置查询的月份

		thisMonth.set(Calendar.DAY_OF_MONTH, 1);// 设置每月第一天是几号(必须设置)
		int firstIndex = thisMonth.get(Calendar.DAY_OF_WEEK) - 1;// 得到这个月的第一天是星期几
		int maxIndex = thisMonth.getActualMaximum(Calendar.DAY_OF_MONTH);// 得到这个月有多少天
		for (int i = 0; i < maxIndex; i++) {
			days[firstIndex + i] = String.valueOf(i + 1);
		}
		System.out.println("日\t一\t二\t三\t四\t五\t六");
		for (int j = 0; j < 6; j++) {
			for (int i = j * 7; i < (j + 1) * 7; i++) {
				System.out.print(days[i]);
				System.out.print("\t");
			}
			System.out.println();
		}
	}

	public static String[] getCalendar(int year, int month) {
		String days[];
		days = new String[42];
		for (int i = 0; i < 42; i++) {
			days[i] = "";
		}
		Calendar thisMonth = Calendar.getInstance();
		thisMonth.set(Calendar.YEAR, year);// 设置查询的年份
		thisMonth.set(Calendar.MONTH, month - 1);// 设置查询的月份

		thisMonth.set(Calendar.DAY_OF_MONTH, 1);// 设置每月第一天是几号(必须设置)
		int firstIndex = thisMonth.get(Calendar.DAY_OF_WEEK) - 1;// 得到这个月的第一天是星期几
		int maxIndex = thisMonth.getActualMaximum(Calendar.DAY_OF_MONTH);// 得到这个月有多少天
		for (int i = 0; i < maxIndex; i++) {
			days[firstIndex + i] = String.valueOf(i + 1);
		}
		return days;
	}

	public static Date getDate() {
		return calendar.getTime();
	}
}
