package com.google.choujone.blog.entity;

/**首页菜单
 * choujone'blog<br>
 * 功能描述：
 * 2012-2-3
 */
public class Menu {
	String title;
	String url;
	boolean selected;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
