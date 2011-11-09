package com.google.choujone.blog.dao;

import java.util.Date;
import java.util.List;

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
 * choujone'blog<br>
 * 功能描述：回复 2010-11-20
 */
public class ReplyDao {
	PersistenceManager pm;

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
		List<Reply> replyList = null;
		try {
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery("select count(id) from "
					+ Reply.class.getName() + " where bid == " + bid);
			Object obj = q.execute();
			pages.setRecTotal(Integer.parseInt(obj.toString()));

			Query query = pm.newQuery(Reply.class, " bid == " + bid);
			query.setRange(pages.getFirstRec(), pages.getPageNo()
					* pages.getPageSize());
			if (bid < 0) {
				query.setOrdering(" sdTime desc ");
			}
			replyList = (List<Reply>) query.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		List<Reply> replyList = null;
		if (MyCache.cache.get("replyDao_getReplyList") == null) {
			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery("select count(id) from "
						+ Reply.class.getName() + " where bid == " + bid);
				Object obj = q.execute();
				pages.setRecTotal(Integer.parseInt(obj.toString()));

				Query query = pm.newQuery(Reply.class, " bid == " + bid);
				query.setOrdering(" sdTime desc");
				query.setRange(pages.getFirstRec(), pages.getPageNo()
						* pages.getPageSize());
				replyList = (List<Reply>) query.execute();
				MyCache.cache.put("replyDao_getReplyList", replyList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			replyList = (List<Reply>) MyCache.cache
					.get("replyDao_getReplyList");
		}
		return replyList;
	}

	/**
	 * 分页查询
	 * 
	 * @param pages
	 * @return
	 */
	public List<Reply> getReplyList(Pages pages) {
		List<Reply> replyList = null;
		try {
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery("select count(id) from "
					+ Reply.class.getName());
			Object obj = q.execute();
			pages.setRecTotal(Integer.parseInt(obj.toString()));

			Query query = pm.newQuery(Reply.class);
			query.setOrdering(" sdTime desc");
			query.setRange(pages.getFirstRec(), pages.getPageNo()
					* pages.getPageSize());
			replyList = (List<Reply>) query.execute();
		} catch (Exception e) {
		}
		return replyList;
	}

	/**
	 * 查询不等于-1的留言
	 * 
	 * @param count
	 * @return
	 */
	public List<Reply> getReplyList(int count) {
		List<Reply> replyList = (List<Reply>) MyCache.cache
				.get("replyDao_getReplyList");
		if (replyList == null) {
			try {
				pm = PMF.get().getPersistenceManager();
				Query query = pm.newQuery(Reply.class, " bid != -1L ");
				query.setRange(0, count);
				query.setOrdering(" bid desc");
				replyList = (List<Reply>) query.execute();
				MyCache.cache.put("replyDao_getReplyList", replyList);
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
		this.pm.close();
	}

}
