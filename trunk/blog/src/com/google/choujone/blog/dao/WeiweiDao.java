package com.google.choujone.blog.dao;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.entity.Ip;
import com.google.choujone.blog.entity.Weiwei;
import com.google.choujone.blog.util.MyCache;
import com.google.choujone.blog.util.PMF;
import com.google.choujone.blog.util.SpiderUtil;
import com.google.choujone.blog.util.Tools;

public class WeiweiDao {
	PersistenceManager pm;
	String key = "weiwei_id_all";// 缓存key

	/**
	 * 新增编号
	 * */
	public boolean operationId(Operation operation, Weiwei ww) {
		boolean flag = false;
		if (ww.getNo() < 1) {
			return false;
		}
		pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
		if (operation.equals(Operation.add)) {// 新增
			try {
				Date dt = new Date(System.currentTimeMillis());
				ww.setId(dt.getTime());
				ww.setSdTime(Tools.changeTime(new Date()));
				pm.makePersistent(ww);
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
		}
		MyCache.clear(key);
		return flag;
	}

	/**
	 * 自动签到
	 * */
	public void registration() {
		List<Weiwei> ids = MyCache.get(key);
		if (ids == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			try {
				Query query = pm.newQuery(Weiwei.class);
				query.setOrdering("sdTime desc");
				ids = (List<Weiwei>) query.execute();
				MyCache.put(key, ids);
			} catch (Exception e) {
			}
		}
		if(ids != null){
			SpiderUtil spiderUtil = new SpiderUtil();
			for(Weiwei w :ids){
				String link="http://www.uwewe.com/get/signPC.aspx?weweid="+w.getNo();
				spiderUtil.getContent(link);
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					//e.printStackTrace();
				}
			}
		}
	}
}
