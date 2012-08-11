package com.google.choujone.blog.util;

import java.util.Map;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;
import javax.servlet.ServletException;

public class CacheSingleton {
	private static final CacheSingleton instance = new CacheSingleton();
	private Cache cache;

	private CacheSingleton() {
	}

	public static CacheSingleton getInstance() {
		return instance;
	}

	public void init(Map props) throws ServletException {
		try {
			CacheFactory factory = CacheManager.getInstance().getCacheFactory();
			cache = factory.createCache(props);
		} catch (CacheException e) {
			throw new ServletException("cache error: " + e.getMessage(), e);
		}
	}

	public Cache getCache() {
		return cache;
	}

	public void clear() {
		if (cache != null) {
			cache.clear();
		}
	}
}
