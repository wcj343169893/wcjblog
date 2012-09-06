package com.snssly.sms.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;

import com.snssly.sms.entity.School;

/**
 * 学校信息管理的所有操作
 * 
 * @author Administrator
 * 
 */
public class SchoolDAO extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 查询所有的学校,倒序
	 * 
	 * @return
	 */
	public List<School> findAll() {
		this.sql = "select * from school order by id desc;";
		logger.info(this.sql);
		return super.query(new BeanListHandler<School>(School.class));
	}

	/**
	 * 查询学校详细
	 * 
	 * @return
	 */
	public School getSchoolDetail(Integer id) {
		this.sql = "select * from school where id=?";
		logger.info(this.sql);
		return (School) super.query(new BeanHandler<School>(School.class), id);
	}

	/**
	 * 查询所有的学校,正序
	 * 
	 * @return
	 */
	public List<School> findAllAsc() {
		this.sql = "select * from school order by id;";
		logger.info(this.sql);
		return super.query(new BeanListHandler<School>(School.class));
	}

	/**
	 * 添加或者修改学校
	 * 
	 * @param school
	 */
	public void add(School school) {
		if (school.getId() != null && school.getId() > 0) {
			update(school);
		} else {
			this.sql = "insert into school(name,description,url,createTime) values(?,?,?,now());";
			super.update(new Object[] { school.getName(),
					school.getDescription(), school.getUrl() });
		}
	}

	/**
	 * -- 修改学校信息 BY id
	 * 
	 * @param school
	 */
	public void update(School school) {
		this.sql = "update school set name=?,description=?,url=? where id=?";
		Object[] params = new Object[] { school.getName(),
				school.getDescription(), school.getUrl(),
				new Integer(school.getId()) };
		super.update(params);
	}

	/**
	 * -- 根据ID删除学校
	 * 
	 * @param sid
	 */
	public void delete(Integer sid) {
		this.sql = "delete from school where id=?";
		super.update(new Object[] { sid });
	}

	/**
	 * 查询所有学校，并分页
	 * 
	 * @param page
	 * @param count
	 * @return
	 */
	public List<School> getSchoolList(int page, int count) {
		this.sql = "select * from school order by id desc limit "
				+ (page * count) + "," + count;
		logger.info(this.sql);
		return super.query(new BeanListHandler<School>(School.class));
	}

	/**
	 * 显示数据的总条数
	 * 
	 * @return
	 */
	public Integer getAllCount() {
		this.sql = "select count(*) from school";
		return super.count();
	}
}
