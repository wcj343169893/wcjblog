package com.google.choujone.blog.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;

/**
 * choujone'blog<br>
 * 功能描述： 2011-11-9
 * http://code.google.com/intl/zh-CN/appengine/docs/java/memcache
 * /usingjcache.html
 */
public class MyCache {
	public static Cache cache;
	private static CacheFactory cacheFactory;
	private static Map props = new HashMap();
	static {
		try {
			props.put(GCacheFactory.EXPIRATION_DELTA, 3600);// 3600秒后过期
			props.put(MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT, true);// 如果不存在具有该键的值，则添加该值；如果存在具有该键的值，则不执行任何操作

			cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(props);
		} catch (CacheException e) {
			System.out.println("初始化缓存失败!");
		}
	}

	/**
	 * 清理缓存
	 * 
	 * @return
	 */
	public static boolean refreshCache() {
		boolean flag = false;
		try {
			cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(props);
			flag = true;
		} catch (CacheException e) {
			System.out.println("清理缓存失败!");
			flag = false;
		}
		return flag;
	}

}