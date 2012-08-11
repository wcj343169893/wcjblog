package com.google.choujone.blog.util;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

public final class PMF {
	private static final PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");
	private static PersistenceManager pm;
	private PMF() {
	}

	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}

	public static PersistenceManager getPersistenceManager() {
		if (pm==null || pm.isClosed()) {
			return get().getPersistenceManager();
		}
		return pm;
	}

	public static Query getQuery(Object obj) {
		return getPersistenceManager().newQuery(obj);
	}

	public static void closePm(PersistenceManager pm) {
		if (pm != null && !pm.isClosed()) {
			pm.close();
		}
	}
}