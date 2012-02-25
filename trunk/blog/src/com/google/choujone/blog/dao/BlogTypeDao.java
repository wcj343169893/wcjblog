package com.google.choujone.blog.dao;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.entity.BlogType;
import com.google.choujone.blog.util.MyCache;
import com.google.choujone.blog.util.PMF;

public class BlogTypeDao {
	PersistenceManager pm;
	String key = "";

	public boolean operationBlogType(Operation operation, BlogType blogType) {
		boolean flag = false;
		pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
		if (operation.equals(Operation.add)) {// 新增
			try {
				Date dt = new Date(System.currentTimeMillis());
				blogType.setId(dt.getTime());
				if (blogType.getParentId() < 0) {
					blogType.setParentId(null);
				}
				pm.makePersistent(blogType);
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
		} else if (operation.equals(Operation.delete)) {// 删除
			try {
				Query query = pm.newQuery(BlogType.class, " id == "
						+ blogType.getId());
				List<BlogType> blogTypes = (List<BlogType>) query.execute();
				if (blogTypes.size() > 0) {
					pm.deletePersistent(blogTypes.get(0));
					flag = true;
				}
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (operation.equals(Operation.modify)) {// 修改
			try {
				BlogType bt = pm
						.getObjectById(BlogType.class, blogType.getId());
				bt.setInfo(blogType.getInfo());
				bt.setName(blogType.getName());
				if (blogType.getParentId() >= 0L) {
					bt.setParentId(blogType.getParentId());
				}
				// pm.flush();
				flag = true;
			} catch (Exception e) {
			}
		}
		closePM();
		return flag;
	}

	/**
	 * 获取所有的类型
	 * 
	 * @return
	 */
	public List<BlogType> getBlogTypeList() {
		key = "blogTypeDao_getBlogTypeList";
		List<BlogType> blogTypeList = MyCache.get(key);
		if (blogTypeList == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			try {
				Query query = pm.newQuery(BlogType.class);
				blogTypeList = (List<BlogType>) query.execute();
				MyCache.put(key, blogTypeList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return blogTypeList;
	}

	/**
	 * 根据分类父级编号查询
	 * 
	 * @param parentId
	 * @return
	 */
	public List<BlogType> getBlogTypeList(Long parentId) {
		key = "blogTypeDao_getBlogTypeList_" + parentId;
		List<BlogType> blogTypeList = MyCache.get(key);
		if (blogTypeList == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			try {
				String filter = "";
				Query query = null;
				if (parentId != null) {
					filter = " parentId== " + parentId;
					query = pm.newQuery(BlogType.class, filter);
				} else {
					query = pm.newQuery(BlogType.class);
				}
				blogTypeList = (List<BlogType>) query.execute();
				MyCache.put(key, blogTypeList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return blogTypeList;
	}

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	public BlogType getBlogTypeById(Long id) {
		key = "blogTypeDao_id_" + id;
		BlogType bt = (BlogType) MyCache.cache.get(key);
		if (bt == null) {
			List<BlogType> blogTypeList = MyCache
					.get("blogTypeDao_getBlogTypeList");
			if (blogTypeList == null) {
				try {
					pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
					Query query = pm.newQuery(BlogType.class, " id == " + id);
					List<BlogType> blogTypes = (List<BlogType>) query.execute();
					if (blogTypes.size() > 0) {
						bt = blogTypes.get(0);
					}
				} catch (Exception e) {
				}
				MyCache.cache.put(key, bt);
			}else{
				for (BlogType blogType : blogTypeList) {
					if (blogType.getId().equals(id)) {
						bt=blogType;
					}
				}
			}
		}
		return bt;
	}

	/**
	 * 关闭链接（不能在显示数据前关闭链接，不然报错）
	 */
	public void closePM() {
		PMF.closePm(this.pm);
	}
}
