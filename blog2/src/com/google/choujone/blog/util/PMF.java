package com.google.choujone.blog.util;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
	private static final PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	private PMF() {
	}

	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}

	public static void closePm(PersistenceManager pm) {
		if (pm != null && !pm.isClosed()) {
			pm.close();
		}
	}
}