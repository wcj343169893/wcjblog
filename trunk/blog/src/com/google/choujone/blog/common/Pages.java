package com.google.choujone.blog.common;

import java.io.Serializable;

@SuppressWarnings("unchecked")
public class Pages implements Serializable {
	private static final long serialVersionUID = 1L;
	private String orderBy = "";
	private String sort = "asc";
	private int pageNo = 1; // 实际页号
	private int pageSize = 10; // 每页记录数
	private int recTotal = 0; // 总记录数
	private int pageTotal = 0;// 总页数

	public Pages() {
	}


	public Pages(int pageSize) {
		this.pageSize = pageSize;
	}

	public Pages(int pageNo, int pageSize) {
		this.setPageNo(pageNo);
		this.setPageSize(pageSize);
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return (0 == pageSize) ? 10 : pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRecTotal() {
		return recTotal;
	}

	public void setRecTotal(int recTotal) {
		this.recTotal = recTotal;
	}

	public int getPageTotal() {
		int ret = (this.getRecTotal() - 1) / this.getPageSize() + 1;
		ret = (ret < 1) ? 1 : ret;
		return ret;
	}

	/**
	 * @return
	 */
	public int getFirstRec() {
		int ret = (this.getPageNo() - 1) * this.getPageSize();// + 1;
		ret = (ret < 1) ? 0 : ret;
		return ret;
	}
	
	/**
	 * @return 分页页码a标签
	 */
	public String getPageNos(Long tid){
		StringBuffer sb = new StringBuffer();
		//当前页码
		for (int i = 1; i <= this.getPageTotal(); i++) {
			String link="";
			if (this.getPageNo()==i) {
				link=""+i;
			}else{
				link="<a href='index.jsp?tid="+tid+"&p="+i+"'>"+i+"</a>";
			}
			sb.append(link);
		}
		return sb.toString();
	}
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}
