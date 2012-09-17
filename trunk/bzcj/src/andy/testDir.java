package andy;

import java.io.File;
import java.io.IOException;


public class testDir {
 public static void main(String[] args) {
	 
	// String filePath ="F:/gw/updatesoft/报纸采集/河北青年报/河北青年报(2011-06-14)/NS_show_epaper_B4/《好人会有好报吗?》.txt";
	 String filePath ="F:/gw/updatesoft/报纸采集/河北青年报/河北青年报(2011-06-14)/NS_show_epaper_B4/《好人会有好?报吗?》.txt";
			 
	// filePath = filePath.replace("?", "");
	
	 String newFileNm = "";
		
		try {
			 newFileNm = filePath.replace("?", "").replace("“", "\"");
			 
		} catch (Exception e) {
			newFileNm = filePath;
		}
		
	 File f = new File(newFileNm);		
		if(!f.exists()){			
			//创建文件
			
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
 
 }
 
 
 
 
}
