package andy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class invoteExt {
  public static void main(String[] args) {
	  try {
		  
		  
		 // d:  cd \ cd caibian java com.batch.EwjDBToCaibianDB
		//  String cmd="d:\\ewjDbTocaibian.bat";
		 String cmd="d:\\hbqnbcaizi.bat";
		
		 //   String cmd="d:\\test.bat"; 
		 // String cmd=" cmd /c copy d:\\out1.txt d:\\out2.txt ";
		  sendLinuxCMD(cmd);
		  //注意，这里cmd字串是为了调用批处理，如果调用exe，更简单，就直接换成路径+exe文件就可以了，比如String cmd="notepad.exe";
		  
		//Runtime.getRuntime().exec("calc");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
  
//发送linux命令
	public static void sendLinuxCMD(String cmd2) throws Exception {

		InputStream ins = null;	
		
			Process process = Runtime.getRuntime().exec(cmd2);
			ins = process.getInputStream(); // cmd 的信息

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					ins));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line); // 输出
			}

			int exitValue = process.waitFor();
			//注：返回值为0时,才是正确的
			System.out.println("返回值：" + exitValue);
			
			process.getOutputStream().close(); // 不要忘记了一定要关

		
	}
}
