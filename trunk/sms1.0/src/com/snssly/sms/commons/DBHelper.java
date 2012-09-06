package com.snssly.sms.commons;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.log4j.Logger;

public class DBHelper {
	protected String sql = "";
	private Logger logger = Logger.getLogger(this.getClass());

	public DBHelper() {

	}

	/**
	 * -- 查询
	 * 
	 * @param <T>
	 * @param rsh
	 *            -- 格式化结果集
	 * @return
	 */
	protected <T> T query(ResultSetHandler<T> rsh) {
		return this.query(rsh, (Object[]) null);
	}

	/**
	 * -- 查询
	 * 
	 * @param <T>
	 * @param rsh
	 *            -- 格式化结果集
	 * @param p
	 *            -- 参数
	 * @return
	 */
	protected <T> T query(ResultSetHandler<T> rsh, Object... p) {
		QueryRunner q = new QueryRunner(Env.createDataSource());
		try {
			return q.query(sql, rsh, p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("查询出错:" + e.getMessage());
		}
		return null;
	}

	/**
	 * -- 执行更新,添加,删除
	 * 
	 * @param p
	 *            -- 参数
	 * @return
	 */
	protected boolean update(Object... p) {
		QueryRunner q = new QueryRunner(Env.createDataSource());
		try {
			int a = q.update(this.sql, p);
			if (a > 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("更新出错:" + e.getMessage());
		}
		return false;
	}

	/**
	 * -- 执行更新,添加,删除
	 * 
	 * @return
	 */
	protected boolean update() {
		return this.update((Object[]) null);
	}

	/**
	 * -- 查询数量select count(*) from table_name;
	 * 
	 * @param p
	 *            -- 参数
	 * @return
	 */
	protected Integer count(Object... p) {
		return this.query(new ResultSetHandler<Integer>() {

			public Integer handle(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				if (rs.next()) {
					return rs.getInt(1);
				}
				return 0;
			}
		}, p);
	}

	/**获取新增数据的编号
	 * @return
	 */
	protected Integer getLastInsertId() {
		this.sql = "SELECT LAST_INSERT_ID()";
		return this.query(new ResultSetHandler<Integer>() {
			public Integer handle(ResultSet rs) throws SQLException {
				if (rs.next()) {
					return rs.getInt(1);
				}
				return null;
			}
		});
	}
}
