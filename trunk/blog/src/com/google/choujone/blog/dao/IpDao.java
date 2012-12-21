package com.google.choujone.blog.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.common.Pages;
import com.google.choujone.blog.entity.Ad;
import com.google.choujone.blog.entity.Ip;
import com.google.choujone.blog.util.MyCache;
import com.google.choujone.blog.util.PMF;
import com.google.choujone.blog.util.Tools;

public class IpDao {
	PersistenceManager pm;
	String key = "blog_ip_all";// 缓存key

	public boolean operationIp(Operation operation, Ip ip) {
		boolean flag = false;
		pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
		if (operation.equals(Operation.add)) {// 新增
			try {
				Date dt = new Date(System.currentTimeMillis());
				ip.setId(dt.getTime());
				ip.setSdTime(Tools.changeTime(new Date()));
				pm.makePersistent(ip);
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
		}
		MyCache.clear(key);
		return flag;
	}

	/**
	 * 删除ip
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteIp(Long id) {
		boolean flag = false;
		pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
		Query query = pm.newQuery(Ip.class, " id == " + id);
		List<Ip> ips = (List<Ip>) query.execute();
		if (ips.size() > 0) {
			pm.deletePersistent(ips.get(0));
			flag = true;
			MyCache.clear(key);
		}
		return flag;
	}

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	public List<Ip> getIpList() {
		List<Ip> ips = MyCache.get(key);
		if (ips == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			try {
				Query query = pm.newQuery(Ip.class);
				query.setOrdering("sdTime desc");
				ips = (List<Ip>) query.execute();
				MyCache.put(key, ips);
			} catch (Exception e) {
			}
		}
		return ips;
	}

	/**
	 * 关闭链接（不能在显示数据前关闭链接，不然报错）
	 */
	public void closePM() {
		PMF.closePm(this.pm);
	}
}
