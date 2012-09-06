package com.snssly.sms.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.Grade;

/**
 * --年级操作的所有dao
 * 
 * @author Administrator
 * 
 */
public class GradeDao extends DBHelper {

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * -- 查询所有年级
	 * 
	 * @return
	 */
	public List<Grade> findAll() {
		this.sql = "select * from grade as g";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Grade>(Grade.class));
	}

	/**
	 * --添加新的年级
	 * 
	 * @param grade
	 */
	public void add(Grade grade) {
		this.sql = "INSERT INTO `grade`(`name`,`schoolid`,`createTime`) VALUES ( ?,?,now())";
		super.update(new Object[] { grade.getName(), grade.getSchoolid() });
	}

	/**
	 * 修改年级
	 * 
	 * @param grade
	 */
	public void update(Grade grade) {
		this.sql = "UPDATE `grade` SET `name`=?,`schoolid`=? WHERE `id`=?";
		super.update(new Object[] { grade.getName(), grade.getSchoolid(),
				grade.getId() });
	}

	// 删除年级
	public void delete(Integer gid) {
		this.sql = "DELETE FROM `grade` WHERE `id`=?";
		super.update(new Object[] { gid });
	}

	// 查询年级，根据学校
	public List<Grade> findGradeBySchool(Integer id) {
		this.sql = "SELECT * FROM grade WHERE schoolid = ?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Grade>(Grade.class), id);
	}

	// 查询数据库中grade表的所有数据条数
	public Integer getAllCount() {
		this.sql = "select count(*) from grade";
		return super.count();
	}

	public List<Grade> getGradeList(Integer page, Integer count) {
		this.sql = "select * from grade order by id desc limit "
				+ (page * count) + "," + count;
		logger.info(this.sql);
		return super.query(new BeanListHandler<Grade>(Grade.class));
	}

	// 根据ID查询年级
	public Grade findGradeById(Integer id) {
		this.sql = "select * from grade where id =?";
		logger.info(this.sql);
		return (Grade) super.query(new BeanListHandler<Grade>(Grade.class), id);
	}

//	/**
//	 * 领导成绩分析的年级  用到学校id
//	 * 
//	 * @param uid
//	 * @return
//	 */
//	public List<Grade> getGradeBySid(Integer uid) {
//		this.sql = "SELECT id,`name` FROM grade WHERE schoolid=(SELECT g.schoolid "
//				+ "FROM grade g LEFT JOIN clazz c ON c.gid=g.id LEFT JOIN `user` u ON u.cid=c.id "
//				+ "WHERE u.id=? AND (u.rid=5 OR u.rid=1) AND u.isVisible=0)";
//		logger.info(this.sql);
//		return super.query(new BeanListHandler<Grade>(Grade.class), uid);
//	}

	/**
	 * 根据班级id，查询年级
	 * 
	 * @param cid
	 * @return
	 */
	public Grade findGradeByCid(Integer cid) {
		this.sql = "SELECT g.id,g.name FROM grade g LEFT JOIN clazz c ON c.gid=g.id WHERE c.id=?";
		logger.info(this.sql);
		return (Grade) super
				.query(new BeanHandler<Grade>(Grade.class), cid);
	}
	/**
	 * 根据用户id，查询年级
	 * @param uid
	 * @return
	 */
	public Grade findGradeByUid(Integer uid) {
		this.sql = "SELECT g.id,g.name FROM grade g LEFT JOIN clazz c ON c.gid=g.id LEFT JOIN `user` u ON u.cid=c.id WHERE u.id=?";
		logger.info(this.sql);
		return (Grade) super
				.query(new BeanHandler<Grade>(Grade.class), uid);
	}
}
