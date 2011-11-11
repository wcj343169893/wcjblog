package com.google.choujone.blog.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.common.Pages;
import com.google.choujone.blog.entity.Blog;
import com.google.choujone.blog.entity.Reply;
import com.google.choujone.blog.util.MyCache;
import com.google.choujone.blog.util.PMF;
import com.google.choujone.blog.util.Tools;

/**
 * 博客操作类
 * 
 * 缓存key命名：列表=类名_方法名_类型_时间_分页信息__ 单条查询=类名_id_编号 或者 类名_tid_编号
 */
public class BlogDao {
	PersistenceManager pm;
	String key = "";// 缓存key
	String page_key = "";// 分页

	/**
	 * 增加，删除，修改
	 * 
	 * @param operation
	 * @param blog
	 * @return
	 */
	public boolean operationBlog(Operation operation, Blog blog) {
		boolean flag = false;
		Blog b = null;
		pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
		if (operation.equals(Operation.add)) {// 新增
			try {
				Date dt = new Date(System.currentTimeMillis());
				blog.setId(dt.getTime());
				pm.makePersistent(blog);
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
			b=blog;
		} else if (operation.equals(Operation.delete)) {// 隐藏
			try {
				// Query query = pm.newQuery(Blog.class, " id == " +
				// blog.getId());
				// List<Blog> blogs = (List<Blog>) query.execute();
				// if (blogs.size() > 0) {
				// pm.deletePersistent(blogs.get(0));
				// flag = true;
				// }
				// pm.deletePersistent(pm.getObjectById(Blog.class,
				// blog.getId()));
				b = pm.getObjectById(Blog.class, blog.getId());
				b.setIsVisible(blog.getIsVisible());
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (operation.equals(Operation.modify)) {// 修改
			try {
				b = pm.getObjectById(Blog.class, blog.getId());
				b.setContent(blog.getContent());
				b.setTag(blog.getTag());
				b.setTitle(blog.getTitle());
				b.setTid(blog.getTid());
				b.setMoTime(Tools.changeTime(new Date()));
				b.setIsVisible(blog.getIsVisible());
				// pm.flush();
				flag = true;
			} catch (Exception e) {
			}
		} else if (operation.equals(Operation.readTimes)) {// 增加阅读次数
			try {
				b = pm.getObjectById(Blog.class, blog.getId());
				b.setCount(b.getCount() + 1);
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (operation.equals(Operation.replyTimes)) {// 增加回复次数
			try {
				b = pm.getObjectById(Blog.class, blog.getId());
				b.setReplyCount(b.getReplyCount() + 1);
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 更新缓存中的内容
		key = "blogDao_getBlogsByPage_null_null_1_10";
		MyCache.updateList(key, b);
		MyCache.cache.put("blogDao_id_" + b.getId(), b);
		closePM();
		return flag;
	}

	/**
	 * 根据id获取博客信息(调用完后，需要关闭链接closePM();)
	 * 
	 * @param id
	 * @return
	 */
	public Blog getBlogById(Long id) {
		key = "blogDao_id_" + id;
		Blog blog = (Blog) MyCache.cache.get(key);
		if (blog == null) {
			try {
				pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
				blog = pm.getObjectById(Blog.class, id);
				MyCache.cache.put(key, blog);
			} catch (Exception e) {
			}
		}
		return blog;
	}

	/**
	 * 根据id删除博客已经评论(真删除)
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteBlog(Long id) {
		Reply reply = new Reply();
		reply.setBid(id);
		boolean flag = false;
		pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
		Query query = pm.newQuery(Blog.class, " id == " + id);
		List<Blog> blogs = (List<Blog>) query.execute();
		if (blogs.size() > 0) {
			pm.deletePersistent(blogs.get(0));
			flag = true;
		}
		if (flag) {
			ReplyDao rd = new ReplyDao();
			rd.operationReply(Operation.delete, reply);
		}
		return flag;
	}

	/**
	 * 查询所有博客
	 */
	public List<Blog> getBlogList() {
		key = "blogDao_getBlogList";
		List<Blog> blogs = MyCache.get(key);
		if (blogs == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			try {
				Query query = pm.newQuery(Blog.class);
				query.setOrdering("sdTime desc");
				blogs = (List<Blog>) query.execute();
				MyCache.put(key, blogs);
			} catch (Exception e) {
			}
		}
		return blogs;
	}

	/**
	 * 查询最新博客
	 * 
	 * @param count
	 *            条数
	 * @return
	 */
	public List<Blog> getBlogList_hot(int count) {
		key = "blogDao_getBlogList_hot_" + count;
		List<Blog> blogs = MyCache.get(key);
		if (blogs == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			try {
				Query query = pm.newQuery(Blog.class, " isVisible==0");
				query.setRange(0, count);
				query.setOrdering("replyCount desc , count desc");
				blogs = (List<Blog>) query.execute();
				MyCache.put(key, blogs);
			} catch (Exception e) {
			}
		}
		return blogs;
	}

	/**
	 * 前台分页查询博客
	 * 
	 * @param pages
	 * @return
	 */
	public List<Blog> getBlogListByPage(Pages pages, Long tid) {
		key = "blogDao_getBlogListByPage_" + tid + "_null_" + pages.getPageNo()
				+ "_" + pages.getPageSize();
		List<Blog> blogs = MyCache.get(key);
		page_key = key + "_pages";
		Pages page = (Pages) MyCache.cache.get(page_key) != null ? (Pages) MyCache.cache
				.get(page_key)
				: pages;
				
		if (blogs == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			try {
				String filter = "select count(id) from " + Blog.class.getName()
						+ " where isVisible==0 ";
				if (tid != null && tid > 0) {
					filter += "&& tid == " + tid;
				}
				// 查询总条数
				Query q = pm.newQuery(filter);
				Object obj = q.execute();
				pages.setRecTotal(Integer.parseInt(obj.toString()));
				filter = " isVisible==0 ";
				if (tid != null && tid > 0) {
					filter += "&& tid == " + tid;
				}
				Query query = pm.newQuery(Blog.class, filter);
				query.setOrdering("sdTime desc");
				query.setRange(pages.getFirstRec(), pages.getPageNo()
						* pages.getPageSize());
				blogs = (List<Blog>) query.execute();
				MyCache.put(key, blogs);
				MyCache.cache.put(page_key, pages);
			} catch (Exception e) {
			}
		}
		pages.setRecTotal(page.getRecTotal());
		return blogs;
	}

	/**
	 * 根据日期查询博客
	 * 
	 * @param pages
	 * @param time
	 * @return
	 */
	public List<Blog> getBlogListByPage(Pages pages, String time) {
		key = "blogDao_getBlogsByPage_null_" + time + "_" + pages.getPageNo()
				+ "_" + pages.getPageSize();
		List<Blog> blogs = MyCache.get(key);

		if (blogs == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			try {
				// String filter = "select count(id) from " +
				// Blog.class.getName()
				// + " where isVisible==0 ";
				String filter = " isVisible==0 && name >= " + time + "%";
				Query query = pm.newQuery(Blog.class);
				blogs = (List<Blog>) query.execute();
				MyCache.put(key, blogs);
				// if (time != null && !"".equals(time.trim())) {
				// filter += " && sdTime == date";
				// }
				// // 查询总条数
				// Query q = pm.newQuery();
				// Object obj = q.execute();
				// pages.setRecTotal(Integer.parseInt(obj.toString()));
				// filter = " isVisible==0 ";
				// Extent transactionExtent = pm.getExtent(Blog.class, true);
				// query.setRange(pages.getFirstRec(), pages.getPageNo()
				// * pages.getPageSize());
				// blogs = (List<Blog>) query.execute();
				// Extent movieExtent = pm.getExtent(Blog.class, true);
				// Query query = pm.newQuery(movieExtent, filter);
				// query.declareImports("import java.util.String");
				// query.declareParameters("String time");
				// query.setFilter(filter);
				// HashMap parameters = new HashMap();
				// parameters.put("time", time);
				// Collection result = (Collection)
				// query.executeWithMap(parameters);
				// Iterator iter = result.iterator();
				// while (iter.hasNext()) {
				// Blog blog = (Blog) iter.next();
				// System.out.println(blog.getTitle());
				// System.out.println(blog.getSdTime());
				// blogs.add(blog);
				// }
				// query.setOrdering("sdTime desc");
				// if (time != null) {
				// blogs = (List<Blog>) query.execute(time);
				// } else {
				// blogs = (List<Blog>) query.execute();
				// }

			} catch (Exception e) {
			}
		}
		return blogs;
	}

	/**
	 * 后台分页查询博客
	 * 
	 * @param pages
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Blog> getBlogsByPage(Pages pages) {
		key = "blogDao_getBlogsByPage_null_null_" + pages.getPageNo() + "_"
				+ pages.getPageSize();
		List<Blog> blogs = MyCache.get(key);
		page_key = key + "_pages";
		Pages page = (Pages) MyCache.cache.get(page_key) != null ? (Pages) MyCache.cache
				.get(page_key)
				: pages;
		if (blogs == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			try {
				// 查询总条数
				Query q = pm.newQuery("select count(id) from "
						+ Blog.class.getName());
				Object obj = q.execute();
				pages.setRecTotal(Integer.parseInt(obj.toString()));

				Query query = pm.newQuery(Blog.class);
				query.setOrdering("sdTime desc");
				query.setRange(pages.getFirstRec(), pages.getPageNo()
						* pages.getPageSize());
				blogs = (List<Blog>) query.execute();
				MyCache.put(key, blogs);
				MyCache.cache.put(page_key, pages);
			} catch (Exception e) {
			}
		}
		pages.setRecTotal(page.getRecTotal());
		return blogs;
	}

	/**
	 * 查询上一条
	 * 
	 * @param id
	 * @return
	 */
	public Blog getPreBlog(Long id) {
		Blog blog = (Blog) MyCache.cache.get("blogDao_pre_id_" + id);
		if (blog == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			try {
				String filter = "id > " + id + " && isVisible==0";
				Query query = pm.newQuery(Blog.class, filter);
				// query.declareParameters("int id");
				query.setRange(0, 1);
				query.setOrdering("id asc");
				List<Blog> blogs = (List<Blog>) query.execute();
				if (blogs != null && blogs.size() > 0) {
					blog = blogs.get(0);
				}
				MyCache.cache.put("blogDao_pre_id_" + id, blog);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("查询上一条出错");
			}
		}
		return blog;
	}

	/**
	 * 查询下一条
	 * 
	 * @param id
	 * @return
	 */
	public Blog getNextBlog(Long id) {
		Blog blog = (Blog) MyCache.cache.get("blogDao_next_id_" + id);
		if (blog == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			try {
				// Extent extent = pm.getExtent(Blog.class, true);
				// String filter = " id < bid && isVisible==0";
				// Query query = pm.newQuery(extent, filter);
				// query.declareParameters("int bid");
				String filter = "id < " + id + " && isVisible==0";
				Query query = pm.newQuery(Blog.class, filter);
				// query.declareParameters("int id");
				query.setOrdering("id desc");
				query.setRange(0, 1);
				List<Blog> blogs = (List<Blog>) query.execute(id);
				if (blogs != null && blogs.size() > 0) {
					blog = blogs.get(0);
				}
				MyCache.cache.put("blogDao_next_id_" + id, blog);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("查询下一条出错");
			}
		}
		return blog;
	}

	/**
	 * 查询网站记录
	 * 
	 * @return
	 */
	public Map<String, Integer> getCount() {
		Map<String, Integer> counts = (Map<String, Integer>) MyCache.cache
				.get("blogDao_getCount");
		if (counts == null) {
			counts = new HashMap<String, Integer>();
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			try {
				Query q = pm.newQuery(Blog.class, " isVisible==0 ");
				List<Blog> blogs = (List<Blog>) q.execute();
				int blogcount = blogs.size();
				int scancount = 0;
				int replycount = 0;
				for (int i = 0; i < blogcount; i++) {
					Blog b = blogs.get(i);
					scancount += b.getCount();
					replycount += b.getReplyCount();
				}
				// 查询文章总数
				counts.put("blogcount", blogcount);
				// 查询浏览总数
				counts.put("scancount", scancount);
				// 查询评论
				counts.put("replycount", replycount);

				// 查询留言
				String filter = " select count(id) from "
						+ Reply.class.getName() + " where bid == -1L";
				q = pm.newQuery(filter);
				Object obj = q.execute();
				counts.put("messagecount", Integer.parseInt(obj.toString()));
				MyCache.cache.put("blogDao_getCount", counts);
			} catch (Exception e) {
				e.printStackTrace();
			}
			closePM();
		}
		return counts;
	}

	/**
	 * 根据博客类型查询博客数量
	 * 
	 * @param tid
	 *            博客类型编号
	 * @return
	 */
	public Integer getCount(Long tid) {
		key = "blogDao_getCount_" + tid;
		Integer count = (Integer) MyCache.cache.get(key);
		if (count == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			Query q = null;
			String filter = " select count(id) from " + Blog.class.getName();
			if (tid != null && tid > 0) {
				filter += " where isVisible==0  && tid == " + tid;
			}
			q = pm.newQuery(filter);
			Object obj = q.execute();
			count = Integer.parseInt(obj.toString());
			MyCache.cache.put(key, count);
		}
		return count;
	}

	/**
	 * 得到所有的tag，以及它包含的文章数目
	 * 
	 * @return
	 */
	public Map<String, Integer> getTags() {
		key = "blogDao_getTags";
		Map<String, Integer> tagsMap = (Map<String, Integer>) MyCache.cache
				.get(key);
		if (tagsMap == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			tagsMap = new HashMap<String, Integer>();
			Query q = null;
			String filter = "select tag from " + Blog.class.getName()
					+ " where isVisible==0";
			q = pm.newQuery(filter);
			List<String> tags = (List<String>) q.execute();
			if (tags != null) {
				for (int i = 0; i < tags.size(); i++) {
					String[] tag = tags.get(i).split(" ");
					for (int j = 0; j < tag.length; j++) {
						Integer size = 1;
						if (tagsMap.containsKey(tag[j])) {
							size = tagsMap.get(tag[j]) + 1;
							tagsMap.remove(tag[j]);
						}
						tagsMap.put(tag[j], size);
					}
				}
			}
			// 对map排序 排了 但是无用
			List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(
					tagsMap.entrySet());
			// 排序前
			// for (int i = 0; i < infoIds.size(); i++) {
			// String id = infoIds.get(i).toString();
			// System.out.println(id);
			// }
			// 排序
			Collections.sort(infoIds,
					new Comparator<Map.Entry<String, Integer>>() {
						public int compare(Map.Entry<String, Integer> o1,
								Map.Entry<String, Integer> o2) {
							return (o2.getValue() - o1.getValue());
						}
					});
			// System.out.println("排序后");
			// 排序后
			tagsMap.clear();
			for (int i = 0; i < infoIds.size(); i++) {
				// String id = infoIds.get(i).toString();
				tagsMap.put(infoIds.get(i).getKey(), infoIds.get(i).getValue());
				// System.out.println(id);
			}
			MyCache.cache.put(key, tagsMap);
		}
		return tagsMap;
	}

	/**
	 * 关闭链接（不能在显示数据前关闭链接，不然报错）
	 */
	public void closePM() {
		PMF.closePm(this.pm);
	}
}
