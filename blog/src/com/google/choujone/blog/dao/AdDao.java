package com.google.choujone.blog.dao;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.common.Pages;
import com.google.choujone.blog.entity.Ad;
import com.google.choujone.blog.util.MyCache;
import com.google.choujone.blog.util.PMF;

/**
 * 广告操作类
 * 
 * 
 */
public class AdDao {
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
	public boolean operationAd(Operation operation, Ad ad) {
		boolean flag = false;
		Ad b = null;
		pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
		if (operation.equals(Operation.add)) {// 新增
			try {
				Date dt = new Date(System.currentTimeMillis());
				ad.setId(dt.getTime());
				pm.makePersistent(ad);
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
			b = ad;
		} else if (operation.equals(Operation.delete)) {// 隐藏
			try {
				b = pm.getObjectById(Ad.class, ad.getId());
				b.setIsVisible(ad.getIsVisible());
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (operation.equals(Operation.modify)) {// 修改
			try {
				b = pm.getObjectById(Ad.class, ad.getId());
				b.setIsVisible(ad.getIsVisible());
				b.setBeginTime(ad.getBeginTime());
				b.setCode(ad.getCode());
				b.setDescription(ad.getDescription());
				b.setEndTime(ad.getEndTime());
				b.setName(ad.getName());
				b.setSdTime(ad.getSdTime());
				flag = true;
			} catch (Exception e) {
			}
		} else if (operation.equals(Operation.readTimes)) {// 增加阅读次数
			try {
				b = pm.getObjectById(Ad.class, ad.getId());
				b.setCount(b.getCount() + 1);
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 更新缓存中的内容
		// key = "blogDao_getBlogsByPage_null_null_1_10";
		// MyCache.updateList(key, b);
		// MyCache.cache.put("blogDao_id_" + b.getId(), b);
		closePM();
		return flag;
	}

	/**
	 * 根据编号查询广告
	 * 
	 * @param id
	 * @return
	 */
	public Ad getAdById(Long id) {
		key = "ad_id_" + id;
		Ad ad = (Ad) MyCache.cache.get(key);
		if (ad == null) {
			try {
				pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
				ad = pm.getObjectById(Ad.class, id);
				MyCache.cache.put(key, ad);
			} catch (Exception e) {
			}
		}
		return ad;
	}
	public List<Ad> getAdListByPage(Pages pages, Long pid) {
		key = "adDao_getAdListByPage_" + pid + "_null_" + pages.getPageNo()
				+ "_" + pages.getPageSize();
		List<Ad> ads = MyCache.get(key);
		page_key = key + "_pages";
		Pages page = (Pages) MyCache.cache.get(page_key) != null ? (Pages) MyCache.cache
				.get(page_key)
				: pages;

		if (ads == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			try {
				String filter = "select count(id) from " + Ad.class.getName()
						+ " where isVisible==0 ";
				if (pid != null && pid > 0) {
					filter += "&& pid == " + pid;
				}
				// 查询总条数
				Query q = pm.newQuery(filter);
				Object obj = q.execute();
				pages.setRecTotal(Integer.parseInt(obj.toString()));
				filter = " isVisible==0 ";
				if (pid != null && pid > 0) {
					filter += "&& pid == " + pid;
				}
				Query query = pm.newQuery(Ad.class, filter);
				query.setOrdering("sdTime desc");
				query.setRange(pages.getFirstRec(), pages.getPageNo()
						* pages.getPageSize());
				ads = (List<Ad>) query.execute();
				MyCache.put(key, ads);
				MyCache.cache.put(page_key, pages);
			} catch (Exception e) {
			}
		}
		pages.setRecTotal(page.getRecTotal());
		return ads;
	}
	public boolean deleteAd(Long id) {
		boolean flag = false;
		pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
		Query query = pm.newQuery(Ad.class, " id == " + id);
		List<Ad> ads = (List<Ad>) query.execute();
		if (ads.size() > 0) {
			pm.deletePersistent(ads.get(0));
			flag = true;
		}
		return flag;
	}

	/**
	 * 关闭链接（不能在显示数据前关闭链接，不然报错）
	 */
	public void closePM() {
		PMF.closePm(this.pm);
	}
}
