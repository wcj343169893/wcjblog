package andy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbBean {

	// 判断是否采集过该日期报纸
	public boolean ifGetThisDay(String date) throws Exception {
		Connection con = null;
		Statement smt = null;
		ResultSet rst = null;
		String sql = null;

		int total = 0;

		try {
			con = DbWrapper.openConnection();

			sql = "SELECT count(*) as total FROM `cjzt` WHERE `cjrq` = '"
					+ date + "' and cjlx='fzwb' and bmcjzt=1";

			smt = con.createStatement();

			rst = smt.executeQuery(sql);

			while (rst.next()) {
				total = rst.getInt("total");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rst != null)
				rst.close();
			DbWrapper.closeConnection(con);
		}

		if (total > 0) {
			return true;
		} else {

			return false;
		}

	}

	// 判断是否采集过该日期版面索引
	public boolean ifGetThisDayIndex(String date) throws Exception {
		Connection con = null;
		Statement smt = null;
		ResultSet rst = null;
		String sql = null;

		int total = 0;

		try {
			con = DbWrapper.openConnection();

			sql = "SELECT count(*) as total FROM `cjzt` WHERE `cjrq` = '"
					+ date + "' and cjlx='fzwb' and indexcjzt=1";

			smt = con.createStatement();

			rst = smt.executeQuery(sql);

			while (rst.next()) {
				total = rst.getInt("total");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rst != null)
				rst.close();
			DbWrapper.closeConnection(con);
		}

		if (total > 0) {
			return true;
		} else {

			return false;
		}

	}

	// 执行添加操作
	public void execInsertSql(String sql) throws Exception {
		Connection con = null;
		Statement smt = null;
		ResultSet rst = null;

		try {
			con = DbWrapper.openConnection();
			smt = con.createStatement();
			smt.execute(sql);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rst != null)
				rst.close();
			DbWrapper.closeConnection(con);
		}
	}

	// 查找待采集的版面
	public List<String[]> getWaitingBanMianList(String bmdate) throws Exception {

		Connection con = null;
		Statement smt = null;
		ResultSet rst = null;
		String sql = null;

		List<String[]> temp = new ArrayList<String[]>();

		try {
			con = DbWrapper.openConnection();

			sql = "select * from banmian where lx='fzwb' and bmdate='" + bmdate
					+ "' and getdone=0";

			smt = con.createStatement();

			rst = smt.executeQuery(sql);

			while (rst.next()) {
				String[] arrtemp = new String[6];
				arrtemp[0] = rst.getString("id");// id
				arrtemp[1] = rst.getString("content");// content
				arrtemp[2] = rst.getString("url");// url
				arrtemp[3] = rst.getString("lx");// lx
				arrtemp[4] = rst.getString("bmdate");// bmdate
				arrtemp[5] = rst.getString("pdf_url");//pdf_url
				temp.add(arrtemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rst != null)
				rst.close();
			DbWrapper.closeConnection(con);
		}
		return temp;
	}

	// 查找待采集的版面
	public List<String[]> getBanMianPdfList(String bmdate) throws Exception {

		Connection con = null;
		Statement smt = null;
		ResultSet rst = null;
		String sql = null;

		List<String[]> temp = new ArrayList<String[]>();

		try {
			con = DbWrapper.openConnection();

			sql = "select * from banmian where lx='fzwb' and bmdate='" + bmdate
					+ "' and getdone=1";

			smt = con.createStatement();

			rst = smt.executeQuery(sql);

			while (rst.next()) {
				String[] arrtemp = new String[2];				
				arrtemp[0] = rst.getString("content");// content				
				arrtemp[1] = rst.getString("pdf_url");//pdf_url
				temp.add(arrtemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rst != null)
				rst.close();
			DbWrapper.closeConnection(con);
		}
		return temp;
	}
	// 查找待采集的新闻
	public List<String[]> getWaitingNewsList(String bmdate) throws Exception {

		Connection con = null;
		Statement smt = null;
		ResultSet rst = null;
		String sql = null;

		List<String[]> temp = new ArrayList<String[]>();

		try {
			con = DbWrapper.openConnection();

			sql = "select * from newslist where bmid in (select id from banmian where bmdate='"
					+ bmdate + "') and getdone=0";

			smt = con.createStatement();

			rst = smt.executeQuery(sql);

			while (rst.next()) {
				String[] arrtemp = new String[3];
				arrtemp[0] = rst.getString("id");// id
				arrtemp[1] = rst.getString("content");// content
				arrtemp[2] = rst.getString("url");// url

				temp.add(arrtemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rst != null)
				rst.close();
			DbWrapper.closeConnection(con);
		}
		return temp;
	}

	// 查找某天的版面
	public List<String[]> getBanMianListByDate(String bmdate) throws Exception {

		Connection con = null;
		Statement smt = null;
		ResultSet rst = null;
		String sql = null;

		List<String[]> temp = new ArrayList<String[]>();

		try {
			con = DbWrapper.openConnection();

			sql = "select * from banmian where lx='fzwb' and bmdate='" + bmdate
					+ "' and createdone=0";

			smt = con.createStatement();

			rst = smt.executeQuery(sql);

			while (rst.next()) {
				String[] arrtemp = new String[3];
				arrtemp[0] = rst.getString("id");// id
				arrtemp[1] = rst.getString("content");// content
				arrtemp[2] = rst.getString("url");// url

				temp.add(arrtemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rst != null)
				rst.close();
			DbWrapper.closeConnection(con);
		}
		return temp;
	}

	// 查找某天的内容
	public List<String[]> getNewsByDate(String date) throws Exception {

		Connection con = null;
		Statement smt = null;
		ResultSet rst = null;
		String sql = null;

		List<String[]> temp = new ArrayList<String[]>();

		try {
			con = DbWrapper.openConnection();

			sql = "select d.id,b.content bm,n.content bt,d.content,d.imgurl from detaillist d inner join newslist n on n.id=d.newsid inner join banmian b on b.id=n.bmid and d.createdone=0 and b.lx='fzwb' and b.bmdate='"+date+"'";

			smt = con.createStatement();

			rst = smt.executeQuery(sql);

			while (rst.next()) {
				String[] arrtemp = new String[5];
				arrtemp[0] = rst.getString("id");// id
				arrtemp[1] = rst.getString("bm");// bm
				arrtemp[2] = rst.getString("bt");// bt
				arrtemp[3] = rst.getString("content");// content
				arrtemp[4] = rst.getString("imgurl");// imgurl

				temp.add(arrtemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rst != null)
				rst.close();
			DbWrapper.closeConnection(con);
		}
		return temp;
	}

	
	
	
	
}
