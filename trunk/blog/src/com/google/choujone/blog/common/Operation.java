package com.google.choujone.blog.common;

/**
 * 操作类
 * 
 */
public enum Operation {
	/**
	 * 新增
	 */
	add,
	/**
	 * 加载修改项
	 */
	preModify,
	/**
	 * 修改
	 */
	modify,
	/**
	 * 删除
	 */
	delete,
	/**
	 * 阅读次数
	 */
	readTimes,
	/**
	 * 查询
	 */
	lists,
	/**
	 * 回复次数
	 */
	replyTimes,
	/**
	 * 清理缓存
	 * */
	clearCache,
	/**
	 * 测试列表
	 */
	testList,
	/**
	 *测试内容
	 */
	testContent,
	/**
	 * 开始
	 */
	start, getUser,
	ttop,dtop,all
}
