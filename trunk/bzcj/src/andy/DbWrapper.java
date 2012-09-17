package andy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class DbWrapper {
	private static DataSource ds = null;

	// 使用数据源连接数据库
	public static Connection openConnection() throws Exception {

		if (ds == null) {

			ApplicationContext context = new FileSystemXmlApplicationContext(
					"Bean.xml");

			ds = (DataSource) context.getBean("dataSource");

		}

		return ds.getConnection();
	}

	// 使用传统方式连接数据库
	public static Connection openConnection(String driver, String url,
			String username, String password) throws Exception {

		Class.forName(driver).newInstance();
		return DriverManager.getConnection(url, username, password);

	}

	// 关闭连接
	public static void closeConnection(Connection conn) throws Exception {

		if (conn != null) {
			conn.close();
		}

	}

	// 执行插入、更新操作
	public static int executeUpdate(String sql) throws Exception {

		int count = 0;
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = openConnection();
			stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
		} catch (Exception e) {
			throw e;
		}

		finally {
			closeConnection(conn);
		}
		return count;

	}

	// 执行查询操作
	public static List executeQuery(String sql) throws Exception {

		List list = new ArrayList();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = openConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {

				Map map = new HashMap();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					map.put(rsmd.getColumnName(i), rs.getObject(i));
				}

				list.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			closeConnection(conn);
		}

		return list;

	}

}