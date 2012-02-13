package com.google.choujone.blog.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Text;
import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.entity.Blog;
import com.google.choujone.blog.entity.Friends;
import com.google.choujone.blog.entity.Reply;
import com.google.choujone.blog.entity.Spider;
import com.google.choujone.blog.entity.Statistics;
import com.google.choujone.blog.entity.User;
import com.google.choujone.blog.util.Config;
import com.google.choujone.blog.util.MyCache;
import com.google.choujone.blog.util.PMF;
import com.google.choujone.blog.util.Tools;

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
		User u = user;
		try {
			if (operation.equals(Operation.add)) {// 增加用户信息只运行一次
				pm.makePersistent(user);
				flag = true;
			} else if (operation.equals(Operation.modify)) {// 修改
				Query query = pm.newQuery(User.class);
				List<User> users = (List<User>) query.execute();
				if (users != null && users.size() > 0) {
					u = users.get(0);
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
					u.setMenu(user.getMenu());
					u.setBlogDescription(user.getBlogDescription());
					u.setBlogKeyword(user.getBlogKeyword());

					u.setDescription(user.getDescription());
					// 2011-10-28 添加顶部和底部代码
					u.setBlogHead(user.getBlogHead());
					u.setBlogFoot(user.getBlogFoot());

					u.setIsUpload(user.getIsUpload());
					// 更新静态设置
					Config.blog_user = u;
					// 更新导航
					Config.menus = Tools.split(u.getMenu(), ";", ",");

				}
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		MyCache.cache.put("userDao_getUserDetail", u);
		closePM();
		return flag;
	}

	/**
	 * 获取统计
	 * 
	 * @return
	 */
	public Statistics getStatistics() {
		Statistics s = null;
		try {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			Query query = pm.newQuery(Statistics.class);
			List<Statistics> statisticsList = (List<Statistics>) query
					.execute();
			if (statisticsList != null && statisticsList.size() > 0) {
				s = statisticsList.get(0);
				Config.blogType_blog_size_map = Tools.map2map(Tools.str2map(s
						.getBlogType_blog_size()));
				Config.blog_reply_size = Tools.map2map(Tools.str2map(s
						.getBlog_reply_size().getValue()));
			} else {
				Query q = pm.newQuery(Blog.class);
				List<Blog> blogs = (List<Blog>) q.execute();
				int blogcount = 0;
				int scancount = 0;
				int replycount = 0;
				int allcount = blogs.size();
				int messagecount = 0;
				Map<Object, Object> bt_blog_size = new HashMap<Object, Object>();
				Map<Long, Integer> blog_reply_size = new HashMap<Long, Integer>();
				for (int i = 0; i < allcount; i++) {
					Blog b = blogs.get(i);
					if (b.getIsVisible().equals(0)) {
						blogcount += 1;
						scancount += b.getCount();
						replycount += b.getReplyCount();
						// 统计每个博客分类的数量
						bt_blog_size.put(b.getTid(), bt_blog_size.get(b
								.getTid()) != null ? Integer
								.parseInt(bt_blog_size.get(b.getTid())
										.toString()) + 1 : 1);
						// 统计每篇博客的回复数量
						blog_reply_size.put(b.getTid(), blog_reply_size
								.get(b.getTid()) != null ? blog_reply_size
								.get(b.getTid()) + 1 : 0);
					}
				}
				// 查询留言
				String filter = " select count(id) from "
						+ Reply.class.getName() + " where bid == -1L";
				q = pm.newQuery(filter);
				Object obj = q.execute();
				messagecount = Integer.parseInt(obj.toString());
				// 新增统计
				s = new Statistics();
				s.setId((new Date()).getTime());
				// 所有博客
				s.setBlog_size(allcount);
				// 能显示的博客
				s.setBlog_visible_size(blogcount);
				// 回复
				s.setReply_size(replycount);
				// 浏览
				s.setScan_count(scancount);
				// 留言
				s.setMessage_count(messagecount);
				// 博客分类size
				s.setBlogType_size(bt_blog_size.keySet().size());
				// 分类博客size
				s.setBlogType_blog_size(Tools.map2str(bt_blog_size));
				Config.blogType_blog_size_map = Tools.map2map(bt_blog_size);
				// 统计每篇博客的回复数量
				s.setBlog_reply_size(new Text(Tools.map2str(Tools
						.map2map2(blog_reply_size))));
				Config.blog_reply_size = blog_reply_size;

				// 友情链接size
				filter = " select count(id) from " + Friends.class.getName();
				q = pm.newQuery(filter);
				obj = q.execute();
				s.setFriends_size(Integer.parseInt(obj.toString()));
				// 采集任务size
				filter = " select count(id) from " + Spider.class.getName();
				q = pm.newQuery(filter);
				obj = q.execute();
				s.setSpider_size(Integer.parseInt(obj.toString()));
				pm.makePersistent(s);
				closePM();
			}
		} catch (Exception e) {
			System.out.println("查找统计失败" + e.getMessage());
		}
		return s;
	}

	public void modifyStatistics(Statistics statistics) {
		try {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			Query query = pm.newQuery(Statistics.class);
			List<Statistics> statisticsList = (List<Statistics>) query
					.execute();
			if (statisticsList != null && statisticsList.size() > 0) {
				Statistics s = statisticsList.get(0);
				s.setBlog_size(statistics.getBlog_size());
				s.setBlog_visible_size(statistics.getBlog_visible_size());
				s.setBlogType_size(statistics.getBlogType_size());
				s.setFriends_size(statistics.getFriends_size());
				s.setReply_size(statistics.getReply_size());
				s.setSpider_size(statistics.getSpider_size());
				s.setMessage_count(statistics.getMessage_count());
				s.setScan_count(statistics.getScan_count());
				s.setBlogType_blog_size(Tools.map2str2(Config.blogType_blog_size_map));
				s.setBlog_reply_size(new Text(Tools.map2str2(Config.blog_reply_size)));
				Config.statistics = s;
			}
		} catch (Exception e) {
			System.out.println("修改统计错误");
		}
	}

	/**
	 * 关闭连接
	 * 
	 */
	public void closePM() {
		PMF.closePm(this.pm);
	}
}
