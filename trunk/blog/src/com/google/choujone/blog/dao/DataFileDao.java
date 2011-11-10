package com.google.choujone.blog.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.choujone.blog.common.Pages;
import com.google.choujone.blog.entity.DataFile;
import com.google.choujone.blog.util.MyCache;
import com.google.choujone.blog.util.PMF;

public class DataFileDao {
	PersistenceManager pm;
	String key = "";

	public void add(DataFile df) {
		pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(df);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			pm.close();
		}
	}

	public void delete(long id) {
		pm = PMF.get().getPersistenceManager();
		try {
			DataFile df = pm.getObjectById(DataFile.class, id);
			pm.deletePersistent(df);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			pm.close();
		}
	}

	public DataFile get(long id, boolean loadContent) {
		key = "dataFile_id_" + id;
		DataFile df = (DataFile) MyCache.cache.get(key);
		if (df == null) {
			pm = PMF.get().getPersistenceManager();
			try {
				df = pm.getObjectById(DataFile.class, id);
				if (df != null && loadContent)
					df.getFileData();

			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
			MyCache.cache.put(key, df);
		}
		return df;
	}

	/**
	 * 分页查询
	 * 
	 * @param pages
	 * @return
	 */
	public List<DataFile> getDataFileListByPage(Pages pages) {
		key = "dataFile_getDataFileListByPage_" + pages.getPageNo()
				+ pages.getPageSize() + pages.getPageTotal();
		List<DataFile> dataFiles = (List<DataFile>) MyCache.cache.get(key);
		if (dataFiles == null) {
			pm = PMF.get().getPersistenceManager();// 获取操作数据库对象
			try {
				String filter = "select count(id) from "
						+ DataFile.class.getName() + " ";
				// 查询总条数
				Query q = pm.newQuery(filter);
				Object obj = q.execute();
				pages.setRecTotal(Integer.parseInt(obj.toString()));

				Query query = pm.newQuery(DataFile.class);
				query.setOrdering("postDate desc");
				query.setRange(pages.getFirstRec(), pages.getPageNo()
						* pages.getPageSize());
				dataFiles = (List<DataFile>) query.execute();
				MyCache.cache.put(key, dataFiles);
			} catch (Exception e) {
			}
		}
		return dataFiles;
	}

	/**
	 * 关闭链接（不能在显示数据前关闭链接，不然报错）
	 */
	public void closePM() {
		this.pm.close();
	}
}
