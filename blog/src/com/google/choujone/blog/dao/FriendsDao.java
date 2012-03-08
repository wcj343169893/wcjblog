package com.google.choujone.blog.dao;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.common.Pages;
import com.google.choujone.blog.entity.Friends;
import com.google.choujone.blog.util.MyCache;
import com.google.choujone.blog.util.PMF;

/**
 * 友情链接操作类
 */
public class FriendsDao {
	PersistenceManager pm;
	String key = "";// 缓存key
	String page_key = "";//

	/**
	 * 增加，删除，修改
	 * 
	 * @param operation
	 * @param friends
	 * @return
	 */
	public boolean operationFriends(Operation operation, Friends friends) {
		boolean flag = false;
		pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
		if (operation.equals(Operation.add)) {// 新增
			try {
				Date dt = new Date(System.currentTimeMillis());
				friends.setId(dt.getTime());
				pm.makePersistent(friends);
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
		} else if (operation.equals(Operation.delete)) {// 删除
			try {
				Query query = pm.newQuery(Friends.class, " id == "
						+ friends.getId());
				List<Friends> friendsList = (List<Friends>) query.execute();
				if (friendsList.size() > 0) {
					pm.deletePersistent(friendsList.get(0));
					flag = true;
				}
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (operation.equals(Operation.modify)) {// 修改
			try {
				Friends f = pm.getObjectById(Friends.class, friends.getId());
				f.setName(friends.getName());
				f.setUrl(friends.getUrl());
				f.setDescription(friends.getDescription());
				f.setIstop(friends.getIstop());
				f.setTid(friends.getTid());
				friends=f;
				flag = true;
			} catch (Exception e) {
			}
		}// 更新缓存中的内容
		key = "friendsDao_getFriendsByPage_1";
		MyCache.updateList(key, friends);
		MyCache.cache.put("blogDao_id_" + friends.getId(), friends);
		closePM();
		return flag;
	}

	public Friends getFriendsById(Long id) {
		pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
		return (Friends) pm.getObjectById(Friends.class, id);
	}
	public List<Friends> getFriends(){
		List<Friends> friends = MyCache.get(key);
		
		return friends;
	}
	/**
	 * 后台分页查询朋友链接
	 * 
	 * @param pages
	 * @return
	 */
	@SuppressWarnings( { "unchecked" })
	public List<Friends> getFriendsByPage(Pages pages) {
		String key = "friendsDao_getFriendsByPage_" + pages.getPageNo()+"_"+pages.getPageSize();
		List<Friends> friends = MyCache.get(key);
		page_key = key + "_pages";
//		Pages page = (Pages) MyCache.cache.get(page_key) != null ? (Pages) MyCache.cache
//				.get(page_key)
//				: pages;
		pages.setRecTotal(getCount());
		if (friends == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			try {
				// 查询总条数
//				Query q = pm.newQuery("select count(id) from "
//						+ Friends.class.getName());
//				Object obj = q.execute();
//				pages.setRecTotal(getCount());
				Query query = pm.newQuery(Friends.class);
				query.setOrdering("sdTime desc");
				query.setRange(pages.getFirstRec(), pages.getPageNo()
						* pages.getPageSize());
				friends = (List<Friends>) query.execute();
				MyCache.put(key, friends);
//				MyCache.cache.put(page_key, pages);
			} catch (Exception e) {
			}
		}
		return friends;
	}

	public Integer getCount() {
		Integer count = 0;
		String key = "friendsDao_frind_count";
		count = (Integer) MyCache.cache_count.get(key);
		if (count==null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			Query q = pm.newQuery("select count(id) from "
					+ Friends.class.getName());
			Object obj = q.execute();
			count = Integer.parseInt(obj.toString());
			MyCache.cache_count.put(key, count);
		}
		return count;
	}

	/**
	 * 关闭链接（不能在显示数据前关闭链接，不然报错）
	 */
	public void closePM() {
		PMF.closePm(this.pm);
	}
}
