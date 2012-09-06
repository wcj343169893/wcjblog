package com.snssly.sms.dao;

import java.util.List;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;
import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.User;

public class ParentsDao extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * -- 查询所有学生家长
	 * 
	 * @return
	 */
	public List<User> findAll(Integer id) {
		this.sql = "SELECT u.nikeName,up.relationship,u.mobile "
				+ " FROM `user_parent` AS up "
				+ " LEFT JOIN `user` AS u ON u.id = up.cuid "
				+ " WHERE up.cuid=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class), id);
	}

	/**
	 * -- 根据学生ID查询学生家长
	 * 
	 * @return
	 */
	public List<User> findParentsById(Integer id) {
		this.sql = "SELECT up.id upid,up.cuid cuid,up.puid puid,up.id upid,u.nikeName nikeName,up.relationship,u.mobile "
				+ " FROM `user_parent` AS up "
				+ " LEFT JOIN `user` AS u ON u.id = up.puid "
				+ " WHERE up.cuid=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class), id);
	}
	
	/**
	 * -- 根据学生ID查询学生家长
	 * 
	 * @return
	 */
	public List<User> findStudentByPuid(Integer id) {
		this.sql = "SELECT up.cuid id"
				+ " FROM `user_parent` AS up "
				+ " WHERE up.puid=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class), id);
	}

	/**
	 * -- 根据手机号码查询学生家长
	 * 
	 * @return
	 */
	public List<User> findParentsByMobile(String mobile) {
		this.sql = "SELECT pu.id puid,pu.nikename pnikename,cu.nikeName nikename,pu.rid rid,r.name rname" +
				" FROM `user` pu " +
				" LEFT JOIN `user_parent` up ON up.puid=pu.id" +
				" LEFT JOIN `user` cu  ON up.cuid=cu.id  " +
				" LEFT JOIN role r ON pu.rid=r.id" +
			    " WHERE pu.isVisible=0 AND pu.mobile=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class), mobile);
	}

	/**
	 * -- 查询指定用户
	 * 
	 * @return
	 */
	public User findUserById(Integer id) {
		this.sql = "SELECT up.*,pu.nikename pnikename,pu.mobile mobile,pu.isVisible isVisible "
				+ " FROM user_parent up "
				+ " LEFT JOIN `user` cu ON up.cuid=cu.id "
				+ " LEFT JOIN `user` pu ON up.puid=pu.id " + " WHERE cu.id=?";
		logger.info(this.sql);
		return (User) super.query(new BeanHandler<User>(User.class), id);
	}

	/**
	 * -- 修改添加user_parents
	 * 
	 * @param mod
	 */
	public Integer updateUP(User user) {
		if (user.getUpid() == null || "".equals(user.getUpid()) || user.getUpid() < 1) {
			this.sql = "insert into user_parent(" 
				    + " cuid,puid,relationship)"
					+ " values(?,?,?)";
			super.update(user.getCuid(), user.getPuid(), user.getRelationship());
		} else {
			this.sql = "update user_parent "
					+ "set cuid=?,puid=?,relationship=? where id=?";
			super.update(user.getCuid(), user.getPuid(),
					user.getRelationship(), user.getUpid());
		}
		return super.getLastInsertId();
	}

	/**
	 * -- 修改添加user_parents后，添加Parent
	 * 
	 * @param mod
	 */
	public Integer updateParents(User user) {
		if (user.getId() != null && user.getId() > 0) {
			this.sql = "update user set name=?,nikeName=?,mobile=?,password=? where id=?";
			super.update(user.getName(), user.getNikeName(), user.getMobile(),
					user.getPassword(), user.getId());
		} else {
			this.sql = "insert into user("
					+ " name,nikeName,rid,gid,mobile,registTime,isVisible,password)"
					+ " values(?,?,4,?,?,now(),0,?)";
			super.update(user.getName(), user.getNikeName(), user.getGid(),
					user.getMobile(), user.getPassword());
		}
		return super.getLastInsertId();
	}

	/**
	 * -- 根据ID删除用户
	 * 
	 * @param mid
	 */
	public void delete(Integer id) {
		this.sql = "update  user set isVisible=1 where id=?";
		super.update(new Object[] { id });
	}

	/**
	 * -- 根据ID删除用户
	 * 
	 * @param mid
	 */
	public void deleteUP(Integer id) {
		this.sql = "delete from user_parent where  id=?";
		super.update(new Object[] { id });
	}

	/**
	 * 查询总条数
	 * 
	 * @return
	 */
	public Integer getCount(Integer id) {
		this.sql = "select count(*) from user_parent as up"
				+ " left join user as u on u.id=up.cuid" + " where u.cid=?";
		return super.count(id);
	}

	/**
	 * 根据条件查询
	 * 
	 * @return
	 */
	public Integer getCount(String cond, int cid) {
		this.sql = "select count(*) from user as u where u.isVisible=0 and u.id=?";
		return super.count(cid);
	}

	/**
	 * 查询所有的条数
	 * 
	 * @return
	 */
	public Integer getAllCount(String cond, int cid, Integer sex) {
		this.sql = "SELECT COUNT(m.id) "
			    + " FROM (" 
			    + " SELECT count(*) id "
				+ "	FROM user_parent up "
				+ " RIGHT JOIN `user` cu ON up.cuid=cu.id  "
				+ " LEFT JOIN `user` pu ON up.puid=pu.id "
				+ " WHERE (pu.isVisible=0 || pu.isVisible IS NULL ) and cu.isVisible=0 and cu.rid=3 AND cu.cid=? ";
		if (sex != null && !"".equals(sex) && sex < 2) {
			this.sql += " and cu.sex=" + sex;
		}
		if (cond != null && !"".equals(cond)) {
			this.sql += " and (cu.nikeName like '%" + cond + "%' or pu.nikeName like '%"+cond+"%') ";
		}
		this.sql +=" group by cu.id ) m";
		return super.count(cid);
	}

	/**
	 * 分页查询用户
	 * 
	 * @param page
	 * @param count
	 * @return
	 */
	public List<User> getUserList(int page, int count, String cond, int cid,
			Integer sex,Integer order,String way) {
		this.sql = " SELECT cu.id,cu.nikename nikeName,up.relationship relationship,pu.nikename pnikeName,pu.id,up.id upid,cu.id cuid,up.puid puid,pu.mobile mobile,pu.isVisible isVisible,cu.cid cid,c.gid gradeId "
				+ "	FROM user_parent up "
				+ "   RIGHT JOIN `user` cu ON up.cuid=cu.id  "
				+ "   LEFT JOIN `user` pu ON up.puid=pu.id "
				+ "   left join clazz c on cu.cid=c.id"
				+ "   WHERE (pu.isVisible=0 || pu.isVisible IS NULL ) and cu.isVisible=0 and cu.rid=3 AND cu.cid=?";
		if (sex != null && !"".equals(sex) && sex < 2) {
			this.sql += " and cu.sex=" + sex;
		}
		if (cond != null && !"".equals(cond)) {
			this.sql += " and (cu.nikeName like '%" + cond + "%' or pu.nikeName like '%"+cond+"%')";
		}
		this.sql += " group by cu.id order by "+order+" "+way+" LIMIT " + (page * count) + "," + count;
		return super.query(new BeanListHandler<User>(User.class), cid);
	}

	/**
	 * 分页条件查询用户
	 * 
	 * @param page
	 * @param count
	 * @return
	 */
	public List<User> getUserList(int page, int count, int id) {
		this.sql = "SELECT u.*,r.name as rname,g.name as gname,c.name as cname,gd.name as gdname "
				+ " from user as u "
				+ " left join role r on r.id=u.rid"
				+ " left join groups as g on g.id=u.gid"
				+ " left join clazz as c on c.id=u.cid"
				+ " left join grade as gd on gd.id=u.gradeId"
				+ " WHERE u.isVisible=0 and u.id=?"
				+ " order by u.id desc LIMIT " + (page * count) + "," + count;
		return super.query(new BeanListHandler<User>(User.class), id);
	}
	/**
	 * 根据puid查询user在user_parent中的关系数
	 * 
	 * @return
	 */
	public Integer countParentByPuid(int puid) {
		this.sql = "select count(*) from user_parent where puid=?";
		return super.count(puid);
	}

}