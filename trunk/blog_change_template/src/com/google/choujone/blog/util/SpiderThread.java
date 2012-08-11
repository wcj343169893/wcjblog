package com.google.choujone.blog.util;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.google.choujone.blog.common.WebPage;
import com.google.choujone.blog.entity.Spider;

public class SpiderThread extends Thread {
	Spider spider;//采集配置
	
	@Override
	public void run() {
		Timer timer=new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("开始运行任务");
				SpiderUtil util=new SpiderUtil();
				List<WebPage> webpages=util.run(spider);
				for (WebPage webPage : webpages) {
					System.out.println(webPage.getTitle());
				}
			}
		}, Tools.changeTime(spider.getSpider_start()), 1000);
	}

	public Spider getSpider() {
		return spider;
	}

	public void setSpider(Spider spider) {
		this.spider = spider;
	}

}
