package com.google.choujone.blog.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				flag = true;
			} catch (Exception e) {
			}
		}
		closePM();
		return flag;
	}

	public Friends getFriendsById(Long id) {
		pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
		return (Friends) pm.getObjectById(Friends.class, id);
	}

	/**
	 * 后台分页查询朋友链接
	 * 
	 * @param pages
	 * @return
	 */
	@SuppressWarnings( { "unchecked" })
	public List<Friends> getFriendsByPage(Pages pages) {
		String key = "friendsDao_getFriendsByPage";
		List<Friends> friends = MyCache.get(key);
		if (friends == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			try {
				// 查询总条数
				Query q = pm.newQuery("select count(id) from "
						+ Friends.class.getName());
				Object obj = q.execute();
				pages.setRecTotal(Integer.parseInt(obj.toString()));
				Query query = pm.newQuery(Friends.class);
				query.setOrdering("sdTime desc");
				query.setRange(pages.getFirstRec(), pages.getPageNo()
						* pages.getPageSize());
				friends = (List<Friends>) query.execute();
				MyCache.put(key, friends);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return friends;
	}

	/**
	 * 关闭链接（不能在显示数据前关闭链接，不然报错）
	 */
	public void closePM() {
		PMF.closePm(this.pm);
	}
}
