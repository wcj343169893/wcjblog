package com.google.choujone.blog.dao;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.common.Pages;
import com.google.choujone.blog.entity.AdPlace;
import com.google.choujone.blog.entity.Spider;
import com.google.choujone.blog.util.MyCache;
import com.google.choujone.blog.util.PMF;
import com.google.choujone.blog.util.Tools;

public class SpiderDao {
	PersistenceManager pm;
	String key = "";// 缓存key
	String page_key = "";// 分页

	/**
	 * 增加，删除，修改
	 * 
	 * @param operation
	 * @param blog
	 * @return
	 */
	public boolean operationSpider(Operation operation, Spider spider) {
		boolean flag = false;
		Spider b = null;
		pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
		Date dt = new Date(System.currentTimeMillis());
		if (operation.equals(Operation.add)) {// 新增
			try {
				spider.setId(dt.getTime());
				spider.setSdTime(Tools.changeTime(dt));// 创建时间
				spider.setEdTime(Tools.changeTime(dt));// 修改时间
				pm.makePersistent(spider);
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
			b = spider;
		} else if (operation.equals(Operation.delete)) {// 隐藏
			try {
				b = pm.getObjectById(Spider.class, spider.getId());
				b.setStart(0);
				b.setIsVisible(1);
				b.setEdTime(Tools.changeTime(dt));// 修改时间
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
		} else if (operation.equals(Operation.modify)) {// 修改
			try {
				b = pm.getObjectById(Spider.class, spider.getId());
				b.setEdTime(Tools.changeTime(dt));// 修改时间
				b.setName(spider.getName());
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
		}
		key="adDao_getSpiderByPages_1";
		MyCache.clear(key);
		closePM();
		return flag;
	}

	/**
	 * 分页查询
	 * 
	 * @param pages
	 * @return
	 */
	public List<Spider> getSpiderByPages(Pages pages) {
		key = "adDao_getSpiderByPages_" + pages.getPageNo();
		List<Spider> spiders = MyCache.get(key);
		page_key = key + "_pages";
		Pages page = (Pages) MyCache.cache.get(page_key) != null ? (Pages) MyCache.cache
				.get(page_key)
				: pages;

		if (spiders == null || page == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			try {
				String filter = "select count(id) from "
						+ Spider.class.getName();
				// 查询总条数
				Query q = pm.newQuery(filter);
				Object obj = q.execute();
				pages.setRecTotal(Integer.parseInt(obj.toString()));
				Query query = pm.newQuery(Spider.class);
				query.setOrdering("sdTime desc");
				query.setRange(pages.getFirstRec(), pages.getPageNo()
						* pages.getPageSize());
				spiders = (List<Spider>) query.execute();
				MyCache.put(key, spiders);
				MyCache.cache.put(page_key, pages);
			} catch (Exception e) {
			}
		}
		pages.setRecTotal(page.getRecTotal());
		return spiders;
	}

	/**
	 * 关闭链接（不能在显示数据前关闭链接，不然报错）
	 */
	public void closePM() {
		PMF.closePm(this.pm);
	}
}
