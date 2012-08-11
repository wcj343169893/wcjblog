package com.google.choujone.blog.dao;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.common.Pages;
import com.google.choujone.blog.entity.AdPlace;
import com.google.choujone.blog.util.MyCache;
import com.google.choujone.blog.util.PMF;

/**
 * 广告操作类
 * 
 * 
 */
public class AdPlaceDao {
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
	public boolean operationAd(Operation operation, AdPlace adPlace) {
		boolean flag = false;
		AdPlace b = null;
		pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
		if (operation.equals(Operation.add)) {// 新增
			try {
				Date dt = new Date(System.currentTimeMillis());
				adPlace.setId(dt.getTime());
				pm.makePersistent(adPlace);
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
			b = adPlace;
		} else if (operation.equals(Operation.delete)) {// 隐藏
			try {
				b = pm.getObjectById(AdPlace.class, adPlace.getId());
				b.setIsVisible(adPlace.getIsVisible());
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (operation.equals(Operation.modify)) {// 修改
			try {
				b = pm.getObjectById(AdPlace.class, adPlace.getId());
				b.setIsVisible(adPlace.getIsVisible());
				b.setCode(adPlace.getCode());
				b.setDescription(adPlace.getDescription());
				b.setName(adPlace.getName());
				b.setSdTime(adPlace.getSdTime());
				flag = true;
			} catch (Exception e) {
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
	public AdPlace getAdById(Long id) {
		key = "adPlace_id_" + id;
		AdPlace adPlace = (AdPlace) MyCache.cache.get(key);
		if (adPlace == null) {
			try {
				pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
				adPlace = pm.getObjectById(AdPlace.class, id);
				MyCache.cache.put(key, adPlace);
			} catch (Exception e) {
			}
		}
		return adPlace;
	}

	public List<AdPlace> getAdPlaceListByPage(Pages pages) {
		key = "adDao_getAdListByPage_null_null_" + pages.getPageNo()
				+ "_" + pages.getPageSize();
		List<AdPlace> ads = MyCache.get(key);
		page_key = key + "_pages";
		Pages page = (Pages) MyCache.cache.get(page_key) != null ? (Pages) MyCache.cache
				.get(page_key)
				: pages;

		if (ads == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			try {
				String filter = "select count(id) from " + AdPlace.class.getName()
						+ " where isVisible==0 ";
				// 查询总条数
				Query q = pm.newQuery(filter);
				Object obj = q.execute();
				pages.setRecTotal(Integer.parseInt(obj.toString()));
				filter = " isVisible==0 ";
				Query query = pm.newQuery(AdPlace.class, filter);
				query.setOrdering("sdTime desc");
				query.setRange(pages.getFirstRec(), pages.getPageNo()
						* pages.getPageSize());
				ads = (List<AdPlace>) query.execute();
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
		Query query = pm.newQuery(AdPlace.class, " id == " + id);
		List<AdPlace> ads = (List<AdPlace>) query.execute();
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
