package com.choujone.commons;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.log4j.Logger;

public class DBHelper{
	protected String sql="";
	private Logger logger = Logger.getLogger(this.getClass());
	public DBHelper(){
		
	}

	
	/** -- ��ѯ
	 * @param <T>
	 * @param rsh -- ��ʽ�������
	 * @return
	 */
	protected <T> T query(ResultSetHandler<T> rsh){
		return this.query(rsh,(Object[])null);
	}
	
	/** -- ��ѯ
	 * @param <T>
	 * @param rsh -- ��ʽ�������
	 * @param p -- ����
	 * @return
	 */
	protected <T> T query(ResultSetHandler<T> rsh,Object... p){
		QueryRunner q = new QueryRunner(Env.createDataSource());
		try {
			return q.query(sql, rsh, p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("��ѯ����:"+e.getMessage());
		}
		return null;
	}
	
	/** -- ִ�и���,���,ɾ��
	 * @param p -- ����
	 * @return
	 */
	protected boolean update(Object... p){
		QueryRunner q = new QueryRunner(Env.createDataSource());
		try {
			int a = q.update(this.sql, p);
			if (a>0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("���³���:"+e.getMessage());
		}
		return false;
	}
	
	/** -- ִ�и���,���,ɾ��
	 * @return
	 */
	protected boolean update(){
		return this.update((Object[])null);
	}
	
	/** -- ��ѯ����select count(*) from table_name;
	 * @param p -- ����
	 * @return
	 */
	protected Integer count(Object...p){
		return this.query(new ResultSetHandler<Integer>(){

			public Integer handle(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				if (rs.next()) {
					return rs.getInt(1);
				}
				return null;
			}}, p);
	}
}
