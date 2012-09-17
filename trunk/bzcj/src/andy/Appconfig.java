package andy;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Appconfig {
	public static final String AppcfgUrl = "/cfg.properties";
	
	public static String getValue(String key) throws Exception {		
		return getKeyValue(key);
	}
	
	
	static String getKeyValue(String key) {

		Properties props = System.getProperties();
		//得到配置资源文件
    	InputStream input = Appconfig.class.getResourceAsStream(AppcfgUrl);
    	
		try {
			//加载
			props.load(input);
			//关闭文件�?
			input.close();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获得key配置信息
		String temp = props.getProperty(key).trim();
		
		return temp;
	}
}
