package com.snssly.sms.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.User;

public class StudentDao extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * -- 查询所有用户
	 * 
	 * @return
	 */
	public List<User> findAll(Integer cid) {
		this.sql = "SELECT u.snumber snumber,u.nikename nikename,u.sex sex,gd.id gradeId,g.id gid,g.name gname,c.id cid,c.name cname,u.brithday birthday,u.sid sid,u.birthplace birthplace,gd.name gdname,r.id rid"
				+ " FROM user u "
				+ " LEFT JOIN grade gd  ON u.gradeid=gd.id"
				+ " LEFT JOIN clazz c ON u.cid=c.id "
				+ " left join groups g on u.gid=g.id"
				+ " left join role r on u.rid=r.id"
				+ " where cid=?"
				+ " order by id desc;";

		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class), cid);
	}

	/**
	 * -- 查询指定用户
	 * 
	 * @return
	 */
	public User findUserById(Integer id) {
		this.sql = "select u.*,gd.name gdname,u.nikeName nikeName,c.name cname"
				+ " from user u " + " left join clazz c on c.id=u.cid"
				+ " left join grade gd on gd.id=c.gid" + " where u.id=?;";
		logger.info(this.sql);
		return (User) super.query(new BeanHandler<User>(User.class), id);
	}

	/**
	 * -- 修改添加用户
	 * 
	 * @param mod
	 */
	public Integer update(User user) {
		if (user.getId() != null && user.getId() > 0) {
			this.sql = "update user "
					+ "set nikeName=?,cid=?,gradeId=?,snumber=?,gid=?,sex=?,brithday=?,sid=?,birthplace=?,mobile=?,password=? where id=?";
			super.update(user.getNikeName(), user.getCid(), user.getGradeId(),
					user.getSnumber(), user.getGid(), user.getSex(), user
							.getBrithday(), user.getSid(),
					user.getBirthplace(), user.getMobile(),user.getPassword(), user.getId());
			return user.getId();
		} else {
			this.sql = "insert into user("
					+ "name,nikeName,cid,gradeId,rid,snumber,gid,sex,brithday,sid,birthplace,registTime,isVisible,lastLoginTime,password,mobile)"
					+ " values(?,?,?,?,3,?,?,?,?,?,?,now(),0,now(),?,?)";
			super.update(user.getName(), user.getNikeName(), user.getCid(),
					user.getGradeId(), user.getSnumber(), user.getGid(), user
							.getSex(), user.getBrithday(), user.getSid(), user
							.getBirthplace(), user.getPassword(), user
							.getMobile());
			return super.getLastInsertId();
		}
	}

	/**
	 * -- 根据ID删除用户
	 * 
	 * @param mid
	 */
	public void delete(Integer id) {
		this.sql = "update user set isVisible=1 where id=?";
		super.update(new Object[] { id });
	}

	/**
	 * 查询总条数
	 * 
	 * @return
	 */
	public Integer getCount(Integer id) {
		this.sql = "select count(*) from user as u where u.isVisible=0 and u.id=?";
		return super.count(id);
	}

	/**
	 * 根据条件查询
	 * 
	 * @return
	 */
	public Integer getCountById(String cond, int id) {
		this.sql = "select count(*) from user as u where u.isVisible=0 and id=?";
		return super.count(id);
	}

	/**
	 * 查询所有的条数
	 * 
	 * @return
	 */
	public Integer getAllCount(String cond, int cid, Integer sex, Integer gid) {
		this.sql = "select count(*) from user u " 
				+ " left join clazz as c on c.id=u.cid"
				+ " left join grade as gd on gd.id=c.gid" 
				+ " where u.isVisible=0 and u.rid=3 and u.cid=?";
		if (sex != null && !"".equals(sex) && sex < 2) {
			this.sql += " and u.sex=" + sex;
		}
		if (cond != null && !"".equals(cond)) {
			this.sql += " and u.nikeName like '%" + cond + "%'";
		}
		if (gid != null && !"".equals(gid) && gid > 0) {
			this.sql += " and u.gid=" + gid;
		}
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
			Integer sex, Integer gid,String order,String way) {
		this.sql = "SELECT u.*,r.name as rname,g.name as gname,c.name as cname,gd.name as gdname "
				+ " from user as u "
				+ " left join role r on r.id=u.rid"
				+ " left join groups as g on g.id=u.gid"
				+ " left join clazz as c on c.id=u.cid"
				+ " left join grade as gd on gd.id=c.gid"
				+ " WHERE  u.isVisible=0 and u.rid=3 and u.cid =?";
		if (sex != null && !"".equals(sex) && sex < 2) {
			this.sql += " and u.sex=" + sex;
		}
		if (cond != null && !"".equals(cond)) {
			this.sql += " and u.nikeName like '%" + cond + "%'";
		}
		if (gid != null && !"".equals(gid) && gid > 0) {
			this.sql += " and u.gid=" + gid;
		}
		this.sql += " order by u."+order+" "+way+" LIMIT " + (page * count) + "," + count;
		return super.query(new BeanListHandler<User>(User.class), cid);
	}

	/**
	 * 根据班级ID显示学生生日
	 */
	public List<User> getBirthday(int cid, int month) {
		this.sql = "SELECT id,nikeName,brithday"
				+ " FROM `user` "
				+ " WHERE isVisible=0 AND rid=3 AND cid=? and MONTH(brithday)=?";
		return super.query(new BeanListHandler<User>(User.class), cid, month);

	}
	/**
	 * -- 查询手机号码是否被使用
	 * 
	 * @return
	 */
	public List<User> findStudentByMobile(String mobile) {
		this.sql = "SELECT id"
				+ " FROM user "
				+ " WHERE isVisible=0 AND mobile=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class), mobile);
	}
}