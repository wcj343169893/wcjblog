package com.choujone.commons;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class Env {
	private static Logger logger = Logger.getLogger(Env.class);
	
	/**
	 *  -- �����ļ�
	 */
	public static String CONFIG=null;
	
	public static String PACKAGE="com.choujone.";
	
	/** -- ��ȡ�����ļ�.��̬����
	 * @param baseName -- �����ļ�����
	 * @param key -- ����Ҫ��ȡ�����ݵļ�
	 * @return -- ���ظ��ݼ���ȡ����ֵ
	 */
	public static String read(String baseName,String key){
		if(baseName==null||baseName.equals(""))
			return null;
		ResourceBundle bundle=ResourceBundle.getBundle(baseName);
		String value=null;
		try {
			value=bundle.getString(key);
		} catch (Exception e) {
			value=null;
			logger.error("���ܴ������ļ�["+baseName+"]�л��ֵ["+key+"]"); 
		}
		return value;
	}
	
	/** -- ��ȡ�����ļ�.��̬������CONFIG��
	 * @param key -- ����Ҫ��ȡ�����ݵļ�
	 * @return-- ���ظ��ݼ���ȡ����ֵ
	 */
	public static String read(String key){
		if (CONFIG!=null) {
			return Env.read(CONFIG, key);
		}
		return null;
	}
	
	/** -- ��---����
	 * @param className
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object forName(String className){
		try {
			Class<Object> c =  (Class<Object>) Class.forName(className);
			return c.newInstance();
		} catch (ClassNotFoundException e) {
			logger.error("Cannot find class:"+className);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			logger.error("Cannot Instantiation class:"+className);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error("Illegal access in class:"+className);
		}
		return null;
	}
	
	/** -- �������������(8λ)
	 * @return
	 */
	public static String activate(){
		char[] temp="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		StringBuffer str =new StringBuffer();
		Random rand=new Random(new Date().getTime());
		for (int i = 0; i < 8; i++) {
			int a=rand.nextInt(36);
			str.append(temp[a]);
			rand=new Random(new Date().getTime()+i);
		}
		return str.toString();
	}
	
	/** -- ����JNDI��ʽ������ݿ�����,Ĭ�����ݿ�
	 * @return DataSource
	 */
	public static DataSource createDataSource(){
		String jndi = Env.read(Env.read("DATABASE")+".JNDI");
		return Env.createDataSource(jndi);
	}
	
	/** -- ����JNDI��ʽ������ݿ�����
	 * @return DataSource
	 */
	public static DataSource createDataSource(String jndi){
		DataSource ds = null;
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup(jndi);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			logger.error("NamingException: "+jndi);
		}
		return ds;
	}
	
	/** -- ����MD5����
	 * @param key
	 * @return
	 */
	public static String encode(String key){
		String sr=null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update((key+"A*+C").getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			sr=buf.toString();
			sr=sr.substring(8, 16)+sr.substring(24, 32);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Cannot get MD5 encode: "+key);
		}
		return sr;
	}
	public static void main(String[] args) {
		System.out.println(Env.encode("000000"));
	}
	public static String format(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String s =null;
		try {
			s = sdf.format(date);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Cannot format date to: "+format);
		}
		return s;
	}
	
	public static Date format(String source,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date s =null;
		try {
			s = sdf.parse(source);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Cannot format date to: "+format);
		}
		return s;
	}
	
	public static String format(Date date){
		return Env.format(date, "yyyy-MM-dd HH:mm:ss.SSS");
	}
	
	public static Date format(String source){
		return Env.format(source, "yyyy-MM-dd HH:mm:ss.SSS");
	}
	
	/** -- ��ʽ������Ϊ:yyyy-mm-dd hh:MM
	 * @param format -- ����Ҫ���ʽ{yyyy-mm-dd hh:MM:ss.0}
	 * @return
	 */
	public static String formatDate(String format){
		String key = format;
		if(key!=null&&!key.equals("")&&key.matches("^\\d{4}\\-\\d{2}\\-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?$")){
			key = key.substring(2, 16);
		}
		return key;
	}
}
