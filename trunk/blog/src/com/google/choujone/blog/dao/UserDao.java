package com.google.choujone.blog.dao;

import java.util.Date;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.entity.User;
import com.google.choujone.blog.util.MyCache;
import com.google.choujone.blog.util.PMF;

/**
 * choujone'blog<br>
 * 功能描述：用户 2010-11-21
 */
public class UserDao {
	public PersistenceManager pm;
	private User user;

	public User getUserDetail() {
		user = (User) MyCache.cache.get("userDao_getUserDetail");
		if (user == null) {
			try {
				pm = PMF.get().getPersistenceManager();
				Query query = pm.newQuery(User.class);
				List<User> users = (List<User>) query.execute();
				if (users != null && users.size() > 0) {
					user = users.get(0);
				} else {
					Create();
				}
				// 把用户信息存入缓存中
				MyCache.cache.put("userDao_getUserDetail", user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	/**
	 * 根据名字查询
	 * 
	 * @param name
	 * @return
	 */
	public User getUserByName(String name) {
		try {
			pm = PMF.get().getPersistenceManager();
			Extent<User> extent = pm.getExtent(User.class, true);
			String filter = " name == loginname";
			Query query = pm.newQuery(extent, filter);
			query.declareParameters("String loginname");
			query.setRange(0, 1);
			List<User> users = (List<User>) query.execute(name);
			if (users != null && users.size() > 0) {
				user = users.get(0);
			} else {
				pm.makePersistent(Create());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	// 默认创建一个用户
	private User Create() {
		user = new User();
		Date dt = new Date(System.currentTimeMillis());
		user.setId(dt.getTime());
		user.setName("choujone");
		user.setPassword("123456");
		user.setpTitle("文朝军的博客");
		user.setCtitle("这是我写的第一个小博客");// 子标题
		user.setEmail("wcj343169893@163.com");
		user.setDescription("我目前是一个Java程序员");

		return user;
	}

	/**
	 * 增加，修改
	 * 
	 * @param operation
	 * @param user
	 * @return
	 */
	public boolean operationUser(Operation operation, User user) {
		boolean flag = false;
		pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
		try {
			if (operation.equals(Operation.add)) {// 增加用户信息只运行一次
				pm.makePersistent(user);
				flag = true;
			} else if (operation.equals(Operation.modify)) {// 修改
				Query query = pm.newQuery(User.class);
				List<User> users = (List<User>) query.execute();
				if (users != null && users.size() > 0) {
					User u = users.get(0);
					u.setName(user.getName());
					u.setPassword(user.getPassword());
					u.setBrithday(user.getBrithday());
					u.setpTitle(user.getpTitle());
					u.setCtitle(user.getCtitle());
					u.setEmail(user.getEmail());
					u.setNotice(user.getNotice());
					u.setUrl(user.getUrl());
					u.setStyle(user.getStyle());

					u.setIsWeather(user.getIsWeather());
					u.setIsCalendars(user.getIsCalendars());
					u.setIsHotBlog(user.getIsHotBlog());
					u.setIsNewReply(user.getIsNewReply());
					u.setIsLeaveMessage(user.getIsLeaveMessage());
					u.setIsStatistics(user.getIsStatistics());
					u.setIsFriends(user.getIsFriends());
					u.setIsInfo(user.getIsInfo());
					u.setIsTags(user.getIsTags());
					u.setIsType(user.getIsType());

					u.setPreMessage(user.getPreMessage());

					u.setBlogDescription(user.getBlogDescription());
					u.setBlogKeyword(user.getBlogKeyword());

					u.setDescription(user.getDescription());
					// 2011-10-28 添加顶部和底部代码
					u.setBlogHead(user.getBlogHead());
					u.setBlogFoot(user.getBlogFoot());
				}
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		MyCache.cache.put("userDao_getUserDetail", user);
		closePM();
		return flag;
	}

	/**
	 * 关闭连接
	 * 
	 */
	public void closePM() {
		PMF.closePm(this.pm);
	}
}
