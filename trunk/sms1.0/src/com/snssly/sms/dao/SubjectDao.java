package com.snssly.sms.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.Subjects;

public class SubjectDao extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * -- 查询所有科目
	 * 
	 * @return
	 */
	public List<Subjects> findAll() {
		this.sql = "select * from subjects s order by id desc";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Subjects>(Subjects.class));
	}

	/**
	 * -- 添加科目
	 * 
	 * @return
	 * 
	 * 
	 */
	public void add(Subjects subjects) {
		this.sql = "insert into subjects(name,uid)values(?,?)";
		super.update(subjects.getName(), 1);
	}

	/**
	 * -- 修改科目
	 * 
	 * @param g
	 */
	public void update(Subjects s) {
		super.sql = "update subjects set name=?,uid=1 where id=?";
		super.update(s.getName(), s.getId());
	}

	/**
	 * -- 删除科目
	 * 
	 * @param r
	 */
	public void delete(Integer s) {
		super.sql = "delete from subjects where id=?";
		super.update(s);
	}

	/**
	 * 查询所有科目，并分页
	 * 
	 * @param page
	 * @param count
	 * @return
	 */
	public List<Subjects> getSubjectsList(int page, int count) {
		this.sql = "select * from subjects order by id desc limit "
				+ (page * count) + "," + count;
		return super.query(new BeanListHandler<Subjects>(Subjects.class));
	}

	/**
	 * 显示数据的总条数
	 * 
	 * @return
	 */
	public Integer getAllCount() {
		this.sql = "select count(*) from subjects";
		return super.count();
	}

	/**
	 * 查询所有科目，根据用户的班级(各年级的科目不同)
	 * 
	 * @param uid
	 * @return
	 */
	public List<Subjects> list(Integer uid) {
		this.sql = "SELECT s.id,s.name FROM subjects s LEFT JOIN grade_subjects gs ON gs.sid = s.id  LEFT JOIN clazz c ON c.gid=gs.gid LEFT JOIN `user` u ON u.cid = c.id WHERE u.id = ?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Subjects>(Subjects.class), uid);

	}

	/**
	 * 查询科目，根据eid （分页显示所有的考试记录）
	 * 
	 * @param eid
	 * @return
	 */
	public List<Subjects> findByEid(Integer eid) {
		this.sql = "SELECT	s.id,s.name,es.id esid,ecs.id ecsid FROM subjects s LEFT JOIN exam_subject es ON s.id=es.subjects LEFT JOIN exam_clazz_send ecs ON es.id=ecs.esid WHERE es.eid=? GROUP BY s.id ORDER BY es.id";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Subjects>(Subjects.class), eid);
	}

	public Integer getAllCount(Integer eid) {
		this.sql = "select count(*) FROM subjects s LEFT JOIN exam_subject es ON s.id=es.subjects LEFT JOIN exam_clazz_send ecs ON es.id=ecs.esid WHERE es.eid=? ORDER BY es.id";
		return super.count(eid);
	}

	/**
	 * 根据班级得到科目
	 * 
	 * @param cid
	 * @return
	 */
	public List<Subjects> getByCid(Integer cid) {
		this.sql = "SELECT s.* FROM subjects s LEFT JOIN grade_subjects gs ON gs.sid=s.id WHERE gs.gid=(SELECT gid FROM clazz WHERE id=?)";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Subjects>(Subjects.class), cid);
	}
	/**
	 * 根据年级得到科目
	 * @param gid
	 * @return
	 */
	public List<Subjects> getByGid(Integer gid) {
		this.sql = "SELECT s.id id,s.`name` `name` FROM subjects s LEFT JOIN grade_subjects gs ON gs.sid=s.id WHERE gs.gid=? ";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Subjects>(Subjects.class), gid);
	}
	/**
	 * 根据id得到科目
	 * @param id
	 * @return
	 */
	public Subjects findSubjectById(Integer id){
		this.sql = "SELECT * FROM subjects WHERE id=?";
		logger.info(this.sql);
		return (Subjects)super.query(new BeanHandler<Subjects>(Subjects.class),id);
	}
}
