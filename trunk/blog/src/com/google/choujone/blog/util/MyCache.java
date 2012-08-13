package com.google.choujone.blog.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;
import com.google.choujone.blog.entity.User;
import java.lang.reflect.*;

/**
 * choujone'blog<br>
 * 功能描述： 2011-11-9
 * http://code.google.com/intl/zh-CN/appengine/docs/java/memcache
 * /usingjcache.html
 */
public class MyCache {
	public static Cache cache;
	public static Cache cache_count;
	private static CacheFactory cacheFactory;
	private static Map props = new HashMap();
	private static Map<Long, Integer> blogReplyCount = new HashMap<Long, Integer>();// 博客回复数量
	static {
		try {
			props.put(GCacheFactory.EXPIRATION_DELTA, 7200);// 3600秒后过期
			props.put(MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT, true);// 如果不存在具有该键的值，则添加该值；如果存在具有该键的值，则不执行任何操作

			cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(props);

			// 专门存放统计数量的缓存
			props.put(GCacheFactory.EXPIRATION_DELTA, 14400);// 14400秒后过期(4小时过期)
			cache_count = cacheFactory.createCache(props);
			System.out.println("启动缓存");
		} catch (CacheException e) {
			System.out.println("初始化缓存失败!");
		}
	}

	/**
	 * 获取缓存
	 * 
	 * @return
	 */
	private static Cache getCache() {
		if (cache == null) {
			try {
				CacheFactory factory = CacheManager.getInstance()
						.getCacheFactory();
				cache = factory.createCache(Collections.emptyMap());
			} catch (CacheException e) {
				e.printStackTrace();
			}
		}
		return cache;
	}

	/**
	 * 专门存放list数组
	 * 
	 * @param <T>
	 * @param list
	 */
	public static <T> void put(String key, List<T> list) {
		Map<Integer, T> tmap = new HashMap<Integer, T>();
		if (list != null) {
			// 转换list为数组
			Integer count = 1;
			for (T t : list) {
				tmap.put(count, t);
				count++;
			}
			// 把数组放入缓存中
			cache.put(key, tmap);
		}
	}

	/**
	 * 清除缓存中的数据
	 * 
	 * @param key
	 * @return
	 */
	public static boolean clear(String key) {
		boolean flag = false;
		cache.put(key, null);
		return flag;
	}

	/**
	 * 取值
	 * 
	 * @param <T>
	 * @param key
	 * @return
	 */
	public static <T> List<T> get(String key) {
		Map<Integer, T> tmap = new HashMap<Integer, T>();
		List<T> list = null;
		tmap = (Map<Integer, T>) cache.get(key);
		if (tmap != null && tmap.size() > 0) {
			list = new ArrayList<T>();
			for (Integer c : tmap.keySet()) {
				list.add(tmap.get(c));
			}
		}
		return list;
	}

	/**
	 * 更新列表
	 * 
	 * @param <T>
	 * @param key
	 * @param entity
	 */
	public static <T> void updateList(String key, T entity) {
		if (entity == null || key == null) {
			return;
		}
		List<T> list = get(key);
		List<T> newList = new ArrayList<T>();
		boolean isExists = false;
		if (list != null && list.size() > 0) {
			Long entiti_id = getId(entity);// 需要更新的内容
			for (T t : list) {
				Long id = getId(t);
				if (id.equals(entiti_id)) {
					newList.add(entity);
					isExists = true;
				} else {
					newList.add(t);
				}
			}
			if (!isExists) {
				newList.add(entity);
			}
		}
		put(key, newList);// 放到缓存当中
	}

	/**
	 * 清理缓存
	 * 
	 * @return
	 */
	public static boolean refreshCache() {
		boolean flag = false;
		try {
//			cacheFactory = CacheManager.getInstance().getCacheFactory();
//			cache = cacheFactory.createCache(props);
			
			props.put(GCacheFactory.EXPIRATION_DELTA, 7200);// 7200秒后过期

			cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(props);

			// 专门存放统计数量的缓存
			props.put(GCacheFactory.EXPIRATION_DELTA, 14400);// 14400秒后过期(4小时过期)
			cache_count = cacheFactory.createCache(props);
			flag = true;
		} catch (CacheException e) {
			System.out.println("清理缓存失败!");
			flag = false;
		}
		return flag;
	}

	public static Map<Long, Integer> getBlogReplyCount() {
		return blogReplyCount;
	}

	public static void setBlogReplyCount(Map<Long, Integer> blogReplyCount) {
		MyCache.blogReplyCount = blogReplyCount;
	}

	/**
	 * 获取编号
	 * 
	 * @param <T>
	 * @param t
	 * @return
	 */
	private static <T> Long getId(T t) {
		Long id = -1L;
		Class c = t.getClass();
		try {
			Method m = c.getDeclaredMethod("getId", new Class[] {});
			id = (Long) m.invoke(t, new Class[] {});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static void main(String[] args) {
		User user = new User();
		user.setId(155854L);
		user.setName("我是来打酱油的");
		System.out.println(getId(user));
	}
}