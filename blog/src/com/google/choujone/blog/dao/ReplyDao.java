package com.google.choujone.blog.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Text;
import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.common.Pages;
import com.google.choujone.blog.entity.Reply;
import com.google.choujone.blog.entity.Statistics;
import com.google.choujone.blog.util.Config;
import com.google.choujone.blog.util.MyCache;
import com.google.choujone.blog.util.PMF;
import com.google.choujone.blog.util.Tools;

/**
 * choujone'blog<br>
 * 功能描述：回复 2010-11-20
 */
public class ReplyDao {
	PersistenceManager pm;
	String key = "";// 缓存key
	String page_key = "";//

	/**
	 * 发布,回复
	 * 
	 * @param operation
	 * @param reply
	 * @return
	 */
	public boolean operationReply(Operation operation, Reply reply) {
		boolean flag = false;
		pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
		if (operation.equals(Operation.add)) {// 新增
			try {
				Date dt = new Date(System.currentTimeMillis());
				reply.setId(dt.getTime());
				pm.makePersistent(reply);
				flag = true;
				Config.blog_reply_size
						.put(reply.getBid(), Config.blog_reply_size.get(reply
								.getBid()) != null ? Config.blog_reply_size
								.get(reply.getBid()) + 1 : 1);
//				Config.statistics.setBlog_reply_size(new Text(Tools
//						.map2str2(Config.blog_reply_size)));
				Config.statistics.setReply_size(Config.statistics
						.getReply_size() + 1);
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
			}
		} else if (operation.equals(Operation.delete)) {// 删除
			try {
				Query query = pm.newQuery(Reply.class, " bid == "
						+ reply.getBid());
				List<Reply> replys = (List<Reply>) query.execute();
				if (replys.size() > 0) {
					for (int i = 0; i < replys.size(); i++) {
						pm.deletePersistent(replys.get(i));
					}
					flag = true;
				}
				Config.blog_reply_size
						.put(
								reply.getBid(),
								Config.blog_reply_size.get(reply.getBid()) != null
										&& Config.blog_reply_size.get(reply
												.getBid()) > 0 ? Config.blog_reply_size
										.get(reply.getBid()) - 1
										: 0);
//				Config.statistics.setBlog_reply_size(new Text(Tools
//						.map2str2(Config.blog_reply_size)));
				Config.statistics.setReply_size(Config.statistics
						.getReply_size() - 1);
				// pm.deletePersistent(pm.getObjectById(Blog.class,
				// blog.getId()));
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (operation.equals(Operation.modify)) {// 我回复
			try {
				Reply r = (Reply) pm.getObjectById(Reply.class, reply.getId());
				r.setReplyMessage(reply.getReplyMessage());
				r.setReplyTime(Tools.changeTime(new Date()));
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
			}
		}
		UserDao ud = new UserDao();
		ud.modifyStatistics(Config.statistics);
		closePM();
		return flag;
	}

	/**
	 * 根据id编号数组删除回复
	 * 
	 * @param ids
	 * @return
	 */
	public boolean deleteReply(String ids) {
		boolean flag = false;
		pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
		if (ids != null) {
			String[] id_str = ids.split(",");
			for (int i = 0; i < id_str.length; i++) {
				Long id = Long.valueOf(id_str[i]);
				if (id > 0) {
					Query query = pm.newQuery(Reply.class, " id == " + id);
					List<Reply> replys = (List<Reply>) query.execute();
					if (replys.size() > 0) {
						pm.deletePersistent(replys.get(0));
						flag = true;
					}
				}
			}
		}
		closePM();
		return flag;
	}

	/**
	 * 根据文章id查询回复
	 * 
	 * @param bid
	 * @return
	 */
	public List<Reply> getReplyListByBid(Long bid, Pages pages) {
		if (bid != null) {
			pages
					.setRecTotal(Config.blog_reply_size.get(bid) != null ? Config.blog_reply_size
							.get(bid)
							: 0);

		}
		key = "replyDao_bid_" + bid + "_" + pages.getPageNo() + "_"
				+ pages.getRecTotal();
		List<Reply> replyList = MyCache.get(key);
		// page_key = key + "_pages";
		// Pages page = (Pages) MyCache.cache.get(page_key) != null ? (Pages)
		// MyCache.cache
		// .get(page_key)
		// : pages;
		if (replyList == null) {
			try {
				pm = PMF.get().getPersistenceManager();
				// Query q = pm.newQuery("select count(id) from "
				// + Reply.class.getName() + " where bid == " + bid);
				// Object obj = q.execute();
				// pages.setRecTotal(Integer.parseInt(obj.toString()));
				if (pages.getRecTotal() > 0) {
					Query query = pm.newQuery(Reply.class, " bid == " + bid);
					query.setRange(pages.getFirstRec(), pages.getPageNo()
							* pages.getPageSize());
					if (bid < 0) {
						query.setOrdering(" sdTime desc ");
					}
					replyList = (List<Reply>) query.execute();
					MyCache.put(key, replyList);
					// MyCache.cache.put(page_key, pages);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// pages.setRecTotal(page.getRecTotal());
		return replyList;
	}

	/**
	 * 查询最新评论
	 * 
	 * @param bid
	 * @param pages
	 * @return
	 */
	public List<Reply> getReplyList(Long bid, Pages pages) {
		pages.setRecTotal(Config.statistics.getReply_size());
		key = "replyDao_getReplyList_" + bid + "_" + pages.getPageNo() + "_"
				+ pages.getRecTotal();
		List<Reply> replyList = MyCache.get(key);
		// page_key = key + "_pages";
		// Pages page = (Pages) MyCache.cache.get(page_key) != null ? (Pages)
		// MyCache.cache
		// .get(page_key)
		// : pages;
		if (replyList == null) {
			replyList = new ArrayList<Reply>();
			try {
				pm = PMF.get().getPersistenceManager();
				// Query q = pm.newQuery("select count(id) from "
				// + Reply.class.getName() + " where bid == " + bid);
				// Object obj = q.execute();

				Query query = pm.newQuery(Reply.class, " bid == " + bid);
				query.setOrdering(" sdTime desc");
				query.setRange(pages.getFirstRec(), pages.getPageNo()
						* pages.getPageSize());
				replyList = (List<Reply>) query.execute();
				MyCache.put(key, replyList);
				// MyCache.cache.put(page_key, pages);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// pages.setRecTotal(page.getRecTotal());
		return replyList;
	}

	/**
	 * 分页查询
	 * 
	 * @param pages
	 * @return
	 */
	public List<Reply> getReplyList(Pages pages) {
		pages.setRecTotal(Config.statistics.getReply_size());
		key = "replyDao_getReplyList_all_" + pages.getPageNo() + "_"
				+ pages.getRecTotal();
		List<Reply> replyList = MyCache.get(key);
		// page_key = key + "_pages";
		// Pages page = (Pages) MyCache.cache.get(page_key) != null ? (Pages)
		// MyCache.cache
		// .get(page_key)
		// : pages;
		if (replyList == null) {
			try {
				pm = PMF.get().getPersistenceManager();
				// Query q = pm.newQuery("select count(id) from "
				// + Reply.class.getName());
				// Object obj = q.execute();
				// pages.setRecTotal(Integer.parseInt(obj.toString()));
				// pages.setRecTotal(Config.statistics.getReply_size());
				Query query = pm.newQuery(Reply.class);
				query.setOrdering(" sdTime desc");
				query.setRange(pages.getFirstRec(), pages.getPageNo()
						* pages.getPageSize());
				replyList = (List<Reply>) query.execute();
				MyCache.put(key, replyList);
				// MyCache.cache.put(page_key, pages);
			} catch (Exception e) {
			}
		}
		// pages.setRecTotal(page.getRecTotal());
		return replyList;
	}

	/**
	 * 查询不等于-1的留言
	 * 
	 * @param count
	 * @return
	 */
	public List<Reply> getReplyList(int count) {
		key = "replyDao_getReplyList_" + count;
		List<Reply> replyList = MyCache.get(key);
		if (replyList == null) {
			try {
				pm = PMF.get().getPersistenceManager();
				Query query = pm.newQuery(Reply.class);
				query.setRange(0, count);
				query.setOrdering(" sdTime desc");
				// query.setFilter(" bid!=-1L");
				// query.setOrdering(" bid desc");
				replyList = (List<Reply>) query.execute();
				MyCache.put(key, replyList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return replyList;
	}

	/**
	 * 关闭链接（不能在显示数据前关闭链接，不然报错）
	 */
	public void closePM() {
		PMF.closePm(this.pm);
	}

}
