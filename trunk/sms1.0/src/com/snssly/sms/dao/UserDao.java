package com.snssly.sms.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.User;

public class UserDao extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * -- 查询所有用户
	 * 
	 * @return
	 */
	public List<User> findAll() {
		this.sql = "select u.*,r.name as rname,g.name as gname,c.name as cname,gd.name as gdname"
				+ " from user u"
				+ " left join role as r on r.id=u.rid "
				+ " left join groups as g on g.id=u.gid"
				+ " left join clazz as c on c.id=u.cid"
				+ " left join grade as gd on gd.id=c.gid"
				+ " order by id desc;";

		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class));
	}

	public List<User> findByIds(String ids) {
		this.sql = "select * from user where id in (" + ids + ")";
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class));
	}

	/**
	 * 根据(查询教师)
	 * 
	 * @param groupId
	 *            分组编号
	 * @param gradeId
	 *            年级编号
	 * @return
	 */
	public List<User> findByGidGid(Integer groupId, Integer gradeId) {
		this.sql = "select u.*,r.name as rname,g.name as gname,c.name as cname,gd.name as gdname"
				+ " from user u"
				+ " left join role as r on r.id=u.rid "
				+ " left join groups as g on g.id=u.gid"
				+ " left join clazz as c on c.id=u.cid"
				+ " left join grade as gd on gd.id=c.gid "
				+ "where g.id=? and gd.id=?" + " order by id desc;";
		return super.query(new BeanListHandler<User>(User.class), groupId,
				gradeId);
	}

	/**
	 * 根据分组和班级查询（查询学生）
	 * 
	 * @param groupId
	 *            分组编号
	 * @param clazzId
	 *            班级编号
	 * @return
	 */
	public List<User> findByGidCid(Integer groupId, Integer clazzId) {
		this.sql = "select u.*,r.name as rname,g.name as gname,c.name as cname"
				+ " from user u" + " left join role as r on r.id=u.rid "
				+ " left join groups as g on g.id=u.gid"
				+ " left join clazz as c on c.id=u.cid"
				+ " where g.id=? and c.id=?" + " order by id desc;";
		return super.query(new BeanListHandler<User>(User.class), groupId,
				clazzId);
	}

	/**
	 * 根据班级编号和角色编号查询用户
	 * 
	 * @param rid
	 *            角色编号
	 * @param cid
	 *            班级编号
	 * @return
	 */
	public List<User> findByRidCid(Integer rid, Integer cid) {
		this.sql = "select u.*,r.name as rname,g.name as gname,c.name as cname,gd.name as gdname"
				+ " from user u"
				+ " left join role as r on r.id=u.rid "
				+ " left join groups as g on g.id=u.gid"
				+ " left join clazz as c on c.id=u.cid"
				+ " left join grade as gd on gd.id=c.gid "
				+ "where u.rid=? and c.id=? order by id desc;";
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class), rid, cid);
	}

	/**
	 * 根据家长编号查询教师
	 * 
	 * @param puid
	 * @return
	 */
	public List<User> findTeacherByPuid(Integer puid) {
		this.sql = "SELECT * FROM user WHERE cid IN (SELECT u.cid cids FROM user u LEFT JOIN user_parent up ON up.cuid=u.id WHERE up.puid=?) AND rid=2";
		return super.query(new BeanListHandler<User>(User.class), puid);
	}

	/**
	 * 根据角色编号查询用户
	 * 
	 * @param rid
	 * @return
	 */
	public List<User> findByRid(Integer rid) {
		this.sql = "select * from user where rid=? and isVisible=0";
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class), rid);
	}

	/**
	 * -- 查询指定用户
	 * 
	 * @return
	 */
	public User findUserById(Integer id) {
		this.sql = "select * from user where id=?;";
		logger.info(this.sql);
		return (User) super.query(new BeanHandler<User>(User.class), id);
	}

	/**
	 * -- 查询指定用户
	 * 
	 * @return
	 */
	public User getUserById(Integer id) {
		this.sql = "SELECT u.id,u.name,u.cid,gd.id gradeId,u.rid,u.snumber,u.gid,u.sex,u.brithday,u.sid,u.birthplace,u.isVisible,u.nikeName,u.mobile,c.name cname,gd.name gdname,r.name rname,gr.name gname,u.memberEndTime memberEndTime "
				+ "FROM `user` u "
				+ "LEFT JOIN clazz c ON c.id=u.cid "
				+ "LEFT JOIN grade gd ON c.gid=gd.id "
				+ "LEFT JOIN role r ON r.id=u.rid "
				+ "LEFT JOIN groups gr ON gr.id=u.gid " + "WHERE u.id=?;";
		logger.info(this.sql);
		return (User) super.query(new BeanHandler<User>(User.class), id);
	}

	/**
	 * -- 查询指定用户详细信息
	 * 
	 * @return
	 */
	public User findUserDetailById(Integer id) {
		this.sql = "SELECT u.*,r.name AS rname,g.name AS gname,c.name AS cname,gd.name AS gdname "
				+ "FROM `user` AS u "
				+ "LEFT JOIN role r ON r.id=u.rid "
				+ "LEFT JOIN groups AS g ON g.id=u.gid "
				+ "LEFT JOIN clazz AS c ON c.id=u.cid "
				+ "LEFT JOIN grade AS gd ON gd.id=c.gid " + "WHERE u.id = ?";
		logger.info(this.sql);
		return (User) super.query(new BeanHandler<User>(User.class), id);
	}

	// 根据puid，查询用户详细信息
	public User findUserDetailByPuid(Integer id) {
		this.sql = "SELECT pu.nikename pnikeName,cu.nikeName nikeName,pu.rid rid,gr.name gname,cu.snumber snumber,up.relationship relationship, pu.mobile mobile,pu.brithday brithday,pu.birthplace birthplace,pu.registTime registTime,pu.lastLoginTime lastLoginTime,g.name gdname,c.name cname,c.id cid"
				+ " FROM user_parent up"
				+ " RIGHT JOIN `user` cu ON up.cuid=cu.id  "
				+ " LEFT JOIN `user` pu ON up.puid=pu.id "
				+ " LEFT JOIN clazz c ON cu.cid=c.id"
				+ " LEFT JOIN grade g ON c.gid=g.id"
				+ " LEFT JOIN groups gr ON pu.gid=gr.id"
				+ " WHERE cu.isVisible=0 AND cu.rid=3 AND pu.id=?";
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
			this.sql = "update user "
					+ "set name=?,nikeName=?,cid=?,gradeId=?,rid=?,snumber=?,gid=?,sex=?,brithday=?,sid=?,birthplace=?,isVisible=?,mobile=?,password=?,memberEndTime=? where id=?";
			super.update(user.getName(), user.getNikeName(), user.getCid(),
					user.getGradeId(), user.getRid(), user.getSnumber(), user
							.getGid(), user.getSex(), user.getBrithday(), user
							.getSid(), user.getBirthplace(), user
							.getIsVisible(), user.getMobile(), user
							.getPassword(), user.getMemberEndTime(), user
							.getId());
			return user.getId();
		} else {
			this.sql = "insert into user("
					+ " name,nikeName,cid,gradeId,rid,snumber,gid,sex,brithday,sid,birthplace,registTime,isVisible,lastLoginTime,password,mobile,memberEndTime)"
					+ " values( ?,?,?,?,?,?,?,?,?,?,?,now(),?,now(),?,?,?)";
			super.update(user.getName(), user.getNikeName(), user.getCid(),
					user.getGradeId(), user.getRid(), user.getSnumber(), user
							.getGid(), user.getSex(), user.getBrithday(), user
							.getSid(), user.getBirthplace(), user
							.getIsVisible(), user.getPassword(), user
							.getMobile(), user.getMemberEndTime());
			return super.getLastInsertId();
		}
	}

	/**
	 * -- 修改添加用户用户
	 * 
	 * @param mod
	 */
	public void updateSelf(User user) {
		this.sql = "update user "
				+ "set name=?,nikeName=?,snumber=?,brithday=?,sex=?,sid=?,birthplace=?,mobile=?,password=? where id=?";
		super.update(user.getName(), user.getNikeName(), user.getSnumber(),
				user.getBrithday(), user.getSex(), user.getSid(), user
						.getBirthplace(), user.getMobile(), user.getPassword(),
				user.getId());
	}

	/**
	 * -- 更新会员时间
	 * 
	 * @param mod
	 */
	public void updateEndTime(User user) {
		this.sql = "update user" + " set memberEndTime=? where id=?";
		super.update(user.getMemberEndTime(), user.getId());
	}

	/**
	 * -- 根据ID删除用户
	 * 
	 * @param mid
	 */
	public void delete(Integer id) {
		this.sql = "delete from user where id=?";
		super.update(new Object[] { id });
	}

	/**
	 * -- 删除用户,同时删除user_parent中的关系
	 * 
	 * @param mid
	 */
	public void deleteUP(Integer id) {
		this.sql = "delete from user_parent where (cuid=? or puid=?)";
		super.update(new Object[] { id, id });
	}

	/**
	 * -- 删除子用户
	 * 
	 * @param root
	 */
	public void deleteByRoot(Integer root) {
		this.sql = "delete from user where root=?";
		super.update(new Object[] { root });
	}

	/**
	 * 查询总条数
	 * 
	 * @return
	 */
	public Integer getCount() {
		this.sql = "select count(*) from user as u where u.isVisible=0";
		return super.count();
	}

	/**
	 * 根据条件查询
	 * 
	 * @return
	 */

	public Integer getCount(String cond, Integer sex, Integer rid, Integer gid,
			Integer iv, Integer gd, Integer c) {

		this.sql = "select count(*) from user u"
				+ " left join clazz as c on c.id=u.cid"
				+ " left join grade as gd on gd.id=c.gid " + "  where 1=1";
		if (sex != null && !"".equals(sex) && sex < 2) {
			this.sql += " and sex=" + sex;
		}
		if (cond != null && !"".equals(cond)) {
			this.sql += " and (u.name like '%" + cond
					+ "%' or u.nikeName like '%" + cond + "%')";
		}
		if (rid != null && !"".equals(rid) && rid > 0) {
			this.sql += " and u.rid=" + rid;
		}
		if (gid != null && !"".equals(gid) && gid > 0) {
			this.sql += " and u.gid=" + gid;
		}
		if (iv != null && !"".equals(iv) && iv < 2) {
			this.sql += " and u.isVisible=" + iv;
		}
		if (gd != null && !"".equals(gd) && gd > 0) {
			this.sql += " and gd.id=" + gd;
		}
		if (c != null && !"".equals(c) && c > 0) {
			this.sql += " and c.id=" + c;
		}
		return super.count();
	}

	/**
	 * 查询所有的条数
	 * 
	 * @return
	 */
	public Integer getAllCount() {
		this.sql = "select count(*) from user";
		return super.count();
	}

	/**
	 * 分页查询用户
	 * 
	 * @param page
	 * @param count
	 * @return
	 */
	public List<User> getUserList(int page, int count) {
		this.sql = "SELECT u.*,r.name as rname,g.name as gname,c.name as cname,gd.name as gdname "
				+ " from user as u "
				+ " left join role r on r.id=u.rid"
				+ " left join groups as g on g.id=u.gid"
				+ " left join clazz as c on c.id=u.cid"
				+ " left join grade as gd on gd.id=c.gid"
				+ " order by u.id desc LIMIT " + (page * count) + "," + count;
		return super.query(new BeanListHandler<User>(User.class));
	}

	/**
	 * 分页条件查询用户
	 * 
	 * @param page
	 * @param count
	 * @return
	 */

	public List<User> getUserList(int page, int count, String cond,
			Integer sex, Integer rid, Integer gid, Integer iv, Integer gd,
			Integer c, String order, String way) {

		this.sql = "SELECT u.*,r.name as rname,g.name as gname,c.name as cname,gd.name as gdname "
				+ " from user as u "
				+ " left join role r on r.id=u.rid"
				+ " left join clazz as c on c.id=u.cid"
				+ " left join groups as g on g.id=u.gid"
				+ " left join grade as gd on gd.id=c.gid" + " WHERE 1=1 ";
		if (cond != null && !"".equals(cond)) {
			this.sql += " and ( u.name like '%" + cond
					+ "%' or u.nikeName like '%" + cond + "%')";
		}
		if (sex != null && !"".equals(sex) && sex < 2) {
			this.sql += " and u.sex=" + sex;
		}
		if (rid != null && !"".equals(rid) && rid > 0) {
			this.sql += " and u.rid=" + rid;
		}
		if (gid != null && !"".equals(gid) && gid > 0) {
			this.sql += " and u.gid=" + gid;
		}
		if (iv != null && !"".equals(iv) && iv < 2) {
			this.sql += " and u.isVisible=" + iv;
		}
		if (gd != null && !"".equals(gd) && gd > 0) {
			this.sql += " and gd.id=" + gd;
		}
		if (c != null && !"".equals(c) && c > 0) {
			this.sql += " and c.id=" + c;
		}
		this.sql += " order by u." + order + " " + way + " LIMIT "
				+ (page * count) + "," + count;
		return super.query(new BeanListHandler<User>(User.class));
	}

	/**
	 * 根据 年级id cid，得到用户，角色为学生 成绩分析，角色为老师
	 * 
	 * @param cid
	 * @return
	 */
	public List<User> getUser(Integer cid) {
		this.sql = "SELECT u.id,u.nikeName nikeName,u.snumber FROM `user` u WHERE u.rid=3 AND u.cid=? AND isVisible=0 ORDER BY u.id";
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class), cid);
	}

	/**
	 * 根据家长id puid，得到对应学生的id和昵称nikeName
	 * 
	 * @param cid
	 * @return
	 */
	public List<User> getUserByPuid(Integer puid) {
		this.sql = "SELECT u.id,u.nikeName FROM `user` u LEFT JOIN user_parent up ON up.cuid=u.id WHERE up.puid=? ";
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class), puid);
	}

	/**
	 * 根据子女编号查询父母信息
	 * 
	 * @param uid
	 * @return
	 */
	public List<User> getParentByUid(Integer uid) {
		this.sql = "SELECT u.* FROM `user` u LEFT JOIN user_parent up ON u.id=up.puid WHERE up.cuid=? ";
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class), uid);
	}

	/**
	 * 根据uid，得到user表的值
	 * 
	 * @param uid
	 * @return
	 */
	public User getNikeNameByuid(Integer uid) {
		this.sql = "SELECT * FROM `user` WHERE id=?";
		logger.info(this.sql);
		return super.query(new BeanHandler<User>(User.class), uid);
	}

	/**
	 * 根据用户名，查询用户所在班级学生当月生日
	 * 
	 * @param adm
	 * @return
	 */
	public List<User> getStudentByBrithday(Integer rid, Integer cid) {
		this.sql = "SELECT id,nikeName,brithday "
				+ "FROM `user` "
				+ "WHERE MONTH(brithday)=MONTH(NOW()) AND rid=? AND cid=? order by brithday desc ";
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class), rid, cid);
	}

	/**
	 * 根据用户名，查询用户所在班级老师当月生日
	 * 
	 * @param adm
	 * @return
	 */
	public List<User> getTeacherByBrithday(Integer rid, Integer cid) {
		this.sql = "SELECT id,nikeName,brithday "
				+ "FROM `user` "
				+ "WHERE MONTH(brithday)=MONTH(NOW()) AND rid=? AND cid=? order by brithday desc";
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class), rid, cid);
	}

	/**
	 * 根据用户名，查询教师当月生日
	 * 
	 * @param adm
	 * @return
	 */
	public List<User> getTeacherByBrithday(Integer rid) {
		this.sql = "SELECT u.id,u.nikeName,u.brithday,c.name cname,gd.name gdname,g.name gname "
				+ " FROM `user` u"
				+ " left join groups g on u.gid=g.id"
				+ " left join clazz c on u.cid=c.id"
				+ " left join grade gd on c.gid=gd.id"
				+ " WHERE MONTH(brithday)=MONTH(NOW()) AND u.rid=? order by u.brithday desc";
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class), rid);
	}

	/**
	 * 根据用户名，查询教师当月生日
	 * 
	 * @param adm
	 * @return
	 */
	public List<User> getLeaderByBrithday(Integer rid) {
		this.sql = "SELECT u.id,u.nikeName,u.brithday,g.name gname "
				+ " FROM `user` u"
				+ " left join groups g on u.gid=g.id"
				+ " WHERE MONTH(brithday)=MONTH(NOW()) AND u.rid=? order by u.brithday desc";
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class), rid);
	}

	/**
	 * -- 查询手机号码是否被使用
	 * 
	 * @return
	 */
	public List<User> findUsersByMobile(String mobile) {
		this.sql = "SELECT id" + " FROM user "
				+ " WHERE isVisible=0 AND mobile=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class), mobile);
	}

	public List<User> findUsersByMobile(String mobile, Integer cid,
			Integer gid, Integer rid) {
		this.sql = "SELECT u.* FROM user u LEFT JOIN clazz c ON c.id=u.cid LEFT JOIN grade g ON c.gid=g.id"
				+ " WHERE u.isVisible=0 ";
		// 判断，如果输入的是中文，则查询姓名，如果输入的是电话，则查询手机号码
		if (mobile.matches("\\d+")) {// 数字
			this.sql += " AND u.mobile like '" + mobile + "%' ";
		} else {// 中文
			this.sql += " and u.nikeName like '%" + mobile + "%'";
		}
		if (cid != null && cid > 0) {
			this.sql += " and c.id=" + cid;
		}
		if (gid != null && gid > 0) {
			this.sql += " and g.id=" + gid;
		}
		if (rid != null && rid > 0) {
			this.sql += " and u.rid=" + rid;
		}
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class));
	}

}
