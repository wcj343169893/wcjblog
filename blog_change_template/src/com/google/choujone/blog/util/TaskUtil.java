package com.google.choujone.blog.util;

import java.net.URL;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class TaskUtil {
	PersistenceManager pm;

	public void start() {
		pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
		Queue queue = QueueFactory.getDefaultQueue();
		Transaction tx = (Transaction) pm.currentTransaction();
		try {
//			tx.commit();

			pm.makePersistent("");

			queue.add(DatastoreServiceFactory.getDatastoreService()
					.getCurrentTransaction(), TaskOptions.Builder.withUrl(""));


			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
	}
}
