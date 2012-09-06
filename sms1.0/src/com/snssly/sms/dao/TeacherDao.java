package com.snssly.sms.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.User;

public class TeacherDao extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * -- 查询所有用户
	 * 
	 * @return
	 */
	public List<User> findAll(Integer gradeId,Integer cid) {
		this.sql =  "SELECT u.snumber snumber,u.nikename nikename,u.sex sex,g.id gradeId,gr.id gid,g.name gname,c.id cid,c.name cname,u.brithday birthday,u.sid sid,u.birthplace birthplace " +
				    " FROM user u "+
					" LEFT JOIN grade g  ON u.gradeid=g.id"+
					" LEFT JOIN clazz c ON u.cid=c.id " +
					" left join groups gr on u.gid=gr.id" +
					" where u.gradeId=? and cid=?"+
				    " order by id desc;";

		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class),gradeId,cid);
	}

	/**
	 * -- 查询指定用户
	 * 
	 * @return
	 */
	public User findUserById(Integer id) {
		this.sql = "select u.id,u.nikeName,u.sex,u.gid,u.cid,c.gid gradeId,u.sid,u.brithday,u.birthplace,u.mobile" +
				   " from user u" +
				   " left join clazz c on c.id=u.cid" +
				   " left join grade gd on c.gid=gd.id" +
				   " where u.id=?;";
		logger.info(this.sql);
		return (User) super.query(new BeanHandler<User>(User.class), id);
	}

	/**
	 * -- 修改添加用户用户
	 * 
	 * @param mod
	 */
	public Integer update(User user) {

		if (user.getId() != null && user.getId() > 0) {
			this.sql = "update user " +
					   "set name=?,nikeName=?,cid=?,gradeId=?,gid=?,sex=?,brithday=?,sid=?,birthplace=?,mobile=?,password=? where id=?";
			super.update(user.getName(),user.getNikeName(),user.getCid(),user.getGradeId(),user.getGid(),user.getSex(),user.getBrithday(),user.getSid(),user.getBirthplace(),user.getMobile(),user.getPassword(),user.getId());
			return user.getId();
		} else {
			this.sql = "insert into user(" +
					   " name,nikeName,cid,gradeId,rid,gid,sex,brithday,sid,birthplace,registTime,lastLoginTime,mobile,password)" +
					   " values( ?,?,?,?,2,?,?,?,?,?,now(),now(),?,?)";
			super.update(user.getName(),user.getNikeName(),user.getCid(),user.getGradeId(),user.getGid(),user.getSex(),user.getBrithday(),user.getSid(),user.getBirthplace(),user.getMobile(),user.getPassword());
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
	public Integer getCount(String cond,int id) {
		this.sql = "select count(*) from user as u where u.isVisible=0 and id=?";
		return super.count(id);
	}

	/**
	 * 查询所有的条数
	 * 
	 * @return
	 */
	public Integer getAllCount(String cond,Integer sex,Integer gid,Integer gd,Integer c) {
		this.sql = "select count(*) from user where rid=2 and isVisible=0 ";
		if (sex !=null && !"".equals(sex) && sex <2) {
			this.sql +=" and sex="+sex;
		}
		if (cond != null && !"".equals(cond)) {
			this.sql +=" and nikeName like '%"+cond+"%' ";
		}
		if (gid != null && !"".equals(gid) && gid>0) {
			this.sql += " and gid="+gid;
		}
		if (gd != null && !"".equals(gd) && gd>0) {
			this.sql += " and gradeId="+gd;
		}
		if (c != null && !"".equals(c) && c>0) {
			this.sql += " and cid="+c;
		}
		return super.count();
	}

	/**分页查询用户
	 * @param page
	 * @param count
	 * @return
	 */
	public List<User> getUserList(int page, int count,String cond,Integer sex,Integer gid,Integer gd,Integer c,String order,String sort) {
		this.sql = "SELECT u.*,r.name as rname,g.name as gname,c.name as cname,gd.name as gdname " 
			    + " from user as u " 
			    + " left join role r on r.id=u.rid"
			    + " left join groups as g on g.id=u.gid" 
			    + " left join clazz as c on c.id=u.cid"
			    + " left join grade as gd on gd.id=u.gradeId"
				+ " WHERE u.isVisible=0 " 
				+ " and u.rid=2" ;
		if (sex !=null && !"".equals(sex) && sex <2) {
			this.sql +=" and u.sex="+sex;
		}
		if (cond!=null && !"".equals(cond)) {
			this.sql +=" and u.nikeName like '%"+cond+"%'";
		}
		if (gid != null && !"".equals(gid) && gid>0) {
			this.sql += " and u.gid="+gid;
		}
		if (gd != null && !"".equals(gd) && gd>0) {
			this.sql += " and u.gradeId="+gd;
		}
		if (c != null && !"".equals(c) && c>0) {
			this.sql += " and u.cid="+c;
		}
		this.sql += " order by u."+order+" "+sort+" LIMIT "
				+ (page * count) + "," + count;
		return super.query(new BeanListHandler<User>(User.class));
	}
	/**分页条件查询用户
	 * @param page
	 * @param count
	 * @return
	 */
	public List<User> getUserList(int page, int count,int id,String cond) {
		this.sql = "SELECT u.*,r.name as rname,g.name as gname,c.name as cname,gd.name as gdname " 
			    + " from user as u " 
			    + " left join role r on r.id=u.rid"
			    + " left join groups as g on g.id=u.gid" 
			    + " left join clazz as c on c.id=u.cid"
			    + " left join grade as gd on gd.id=u.gradeId"
				+ " WHERE u.isVisible=0 and id=?"
				+ " order by u.id desc LIMIT "
				+ (page * count) + "," + count;
		return super.query(new BeanListHandler<User>(User.class),id);
	}
	
	/**
	 * 根据班级ID显示学生生日
	 */
	public List<User> getBirthday(int month){
		this.sql="SELECT u.id,u.nikeName,u.brithday,g.name gname,gd.name gdname,c.name cname" +
				 " FROM `user` u" +
				 " left join groups g on g.id=u.gid" +
				 " left join clazz c on c.id=u.cid" +
				 " left join grade gd on gd.id=c.gid" +
				 " WHERE u.isVisible=0 AND u.rid=2 and month(brithday)=?";
		
		return super.query(new BeanListHandler<User>(User.class),month);
		
	}
	/**
	 * -- 查询手机号码是否被使用
	 * 
	 * @return
	 */
	public List<User> findTeacherByMobile(String mobile) {
		this.sql = "SELECT id"
				+ " FROM user "
				+ " WHERE isVisible=0 AND mobile=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class), mobile);
	}
}