package com.choujone.commons;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/** -- ������ת��
 * @author Administrator
 *
 */
public class Action {
	private Logger logger = Logger.getLogger(this.getClass());
	
	/** -- ������ת��
	 * @param response
	 * @param request
	 * @param key
	 * @param method
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String execute(HttpServletResponse response,HttpServletRequest request,String key,String method){
		//��ȡ������
		String className = Env.read(Env.read("COMMANDPATH"), key);
		String path = null;
		try {
			Class c = Class.forName(Env.PACKAGE+className);
			Method[] methods = c.getDeclaredMethods();
			Method met = null;
			for (Method me : methods) {
				String name = me.getName();
				if (name.equals(method)) {
					met = me;
					break;
				}
			}
			if (met==null) {
				logger.error("û�п�ִ�з���!");
				return null;
			}			
			//ִ�й��ܴ�����
			path = this.invoke(response, request, met,c.newInstance());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("û���ҵ��ô�������"+className);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}
	
	private String invoke(HttpServletResponse response,HttpServletRequest request,Method method,Object c){
		String path = null;
		try {
			path = (String)method.invoke(c, request,response);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			logger.error("���������쳣!request,response"+e.getMessage());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error("���������쳣!public"+e.getMessage());
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.error("���÷����쳣!"+e.getMessage());
		} catch (Exception e1){
			logger.error("ִ�з�������"+e1.getMessage());
		}
		return path;
	}
}
