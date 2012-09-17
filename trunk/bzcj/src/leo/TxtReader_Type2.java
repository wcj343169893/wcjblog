package leo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 
 * 每期都换的胆时
 */
public class TxtReader_Type2 {

	// 前面的计数器-对的
	private static int beforCount;
	
	// 前面的计数器-错的
	private static int beforCount_;
	
	// 后面的计数器-对的
	private static int afterCount;
	
	// 后面的计数器-错的
	private static int afterCount_;
	
	// 前面的取得要用数
	private static String beforNum;
	
	// 后面的取得要用数
	private static String afterNum;
	
	// 数的类型1
	private static String OTSF = "1368";
	
	//  数的类型2
	private static String TFFS = "2457";
	
	//  数的类型3
	private static String ONE = "0";
	
//  数的类型3
	private static String NIGHT = "9";
	
	//设置当期状态为正确的
	private static boolean isHasRight = true;
	/**
	 * 读取数据
	 * @param fileName 
	 */
	public static void ReadData(String fileName) {
		try {
			String filePath = "F:/prjDocument/2011/invote/"+fileName + ".txt"; 
			
			String refilePath = "F:/prjDocument/2011/invote_ob/"+fileName + "_ob.txt"; 
			
			//1:先把顺序反过来
						 
			 reverfile(filePath, refilePath);
			 // System.out.println("aaaaaaaaaaaa");
			 
			//2:统计
			FileReader read = new FileReader(refilePath);
			
			
			BufferedReader br = new BufferedReader(read);
			String row;
			int i = 1;
			int y = 1;
			//先认证前三
			System.out.println( "前三开始认证-----------------------------------------> ");
			System.out.println( " ---------------------------------------------------> ");
			
			while ((row = br.readLine()) != null && !"".equals(row)) {
				String[] nums = row.split(" ");
				String num = nums[1];
				//第1次先取得,不作任何处理
				
				//前三的先取得,或者第1次有2个0或9状态时，第2次重取
				if(i == 1 || "".equals(beforNum) ){
					String	beforNum2 = num.substring(0, 3);
					
						//前三的先取得
					beforNum =selectType(beforNum2);					
						i++; 
					System.out.println(row + " is--> " );
					continue;
				}			
				
				//第2次开始处理
				String getBefor = num.substring(0, 3);
				
				//判断前三的对错
				selectJudge_before(getBefor,beforNum);
				
				
				//重新设置
				//String	beforNum2 = num.substring(0, 3);
				
				//前三的先取得
				beforNum =selectType(getBefor);					
				
				if(isHasRight){					
					System.out.println(row + " is--> " + isHasRight);
				} else {
					System.out.println(row + " is--> " + isHasRight +"---");
				}
			}
			System.out.println("前三--> 对：" + beforCount +"  错误："+beforCount_);
			System.out.println(" ----------------------------------------------------" );
			System.out.println( "后三开始认证-----------------------------------------> ");
			System.out.println( " ---------------------------------------------------> ");
			
			//先认证后三
			read = new FileReader(refilePath);
			br = new BufferedReader(read);
			while ((row = br.readLine()) != null) {
				String[] nums = row.split(" ");
				String num = nums[1];
			
								
				//第1次先取得,不作任何处理,或者第1次有2个0或9状态时，第2次重取
				if(y == 1 || "".equals(afterNum)){					
					String	afterNum3 = num.substring(2, 5);
					
					//后三的先取得
					afterNum =selectType(afterNum3);
						y++; 
					System.out.println(row + " is--> " );
					continue;
				}
				
				//第2次开始处理
				String getafter = num.substring(2, 5);
				
				//判断后三的对错
				selectJudge_after(getafter,afterNum);
				
				//重新设置
				//String	beforNum2 = num.substring(2, 5);
			
				//后三的先取得
				afterNum =selectType(getafter);				
				if(isHasRight){					
					System.out.println(row + " is--> " + isHasRight);
				} else {
					System.out.println(row + " is--> " + isHasRight +"---");
				}
			}
			System.out.println("前三--> 对：" + beforCount +"  错误："+beforCount_);
			System.out.println("后三--> 对：" + afterCount +"  错误："+afterCount_);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String selectType(String selectNum) {
		int type1 = 0;
		int type2 = 0;
		//包含有0或9状态有2个时
		int onestatus = 0 ;
		int nighttatus = 0 ;
		for(int j=0 ;j< selectNum.length();j++){
			String sigNum = String.valueOf(selectNum.charAt(j));
			
				//0状态
				if(ONE.contains(sigNum)){
					onestatus ++;
					continue;
				}
				
				//9状态
				if(NIGHT.contains(sigNum)){
					nighttatus ++;
					continue;
				}
				
				if(OTSF.contains(sigNum)){
					type1 ++;
				} else {
					type2  ++;
				}						
			
		}
		//有几种状态需要判断		
		String returnNum = "";
		
		//2个0以上或者1个0+1368状态		
		if(type1 > type2 || onestatus > 1 || (onestatus+type1) > 1){
			returnNum = OTSF;
		} else {
			returnNum = TFFS;
		}
		return returnNum;
	}
	
	//判断前三对错
	private static void selectJudge_before(String selectNum,String judgeNum) {
		boolean isRight = false;
		
		for(int j=0 ;j< selectNum.length();j++){
			String sigNum = String.valueOf(selectNum.charAt(j));
			if(judgeNum.contains(sigNum)){
				isRight = true;//有一个就就表达正确了
				break;
			} 					
			
		}
		
		if(isRight){
			beforCount ++ ;
			isHasRight = true; 
		} else {
			beforCount_ ++;
			isHasRight = false; 
		}
		
	}
	
	
	//判断后三对错
	private static void selectJudge_after(String selectNum,String judgeNum) {

		boolean isRight = false;
		
		for(int j=0 ;j< selectNum.length();j++){
			String sigNum = String.valueOf(selectNum.charAt(j));
			if(judgeNum.contains(sigNum)){
				isRight = true;//有一个就就表达正确了
				break;
			} 					
			
		}
		
		if(isRight){
			afterCount ++ ;
			isHasRight = true; 
		} else {
			afterCount_ ++;
			isHasRight = false; 
		}
		
	}


	private static void reverfile(String filePath, String refilePath)
			throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));//要确定根目录下有test.txt文件且有内容
		  //StringBuffer sb = new StringBuffer();
		  List<String> sb = new ArrayList();
		  String lineContent = null;
		  while ((lineContent = br.readLine()) != null) {
		  /* StringTokenizer st = new StringTokenizer(lineContent, " ");

		   for (int t = 0; st.hasMoreElements(); t++) {
		    String word = (String) st.nextElement();
		    sb.add(word);

		   }*/
			  sb.add(lineContent + "\r\n");
		  }
		
		  PrintWriter pw = new PrintWriter(refilePath);
		  for(int i=sb.size()-1; i >= 0 ; i--){
			  pw.write(sb.get(i));
		  }
		 
		  br.close();
		  pw.close();
	}

	/**  
	 * 读取绝对路径的文件内容  
	 * @param filename String  
	 * @return String  
	 */
	public static String read(String filename) {
		String strBuffer = new String();
		try {
			File file = new File(filename);
			if (file.isFile()) {
				Long lens = new Long(file.length());
				byte[] buffer = new byte[lens.intValue()];
				FileInputStream in = new FileInputStream(file);

				in.read(buffer);
				strBuffer = new String(buffer);
				in.close();
			} else
				System.out.print(filename
						+ ": is not a file ,please confirm you file name");
		} catch (IOException e) {

			e.printStackTrace();

		}
		return strBuffer;
	}
	
	public static byte[] readbuf(String filename) {
		byte[] buffer = null;
		try {
			File file = new File(filename);
			if (file.isFile()) {

				Long lens = new Long(file.length());
				buffer = new byte[lens.intValue()];
				FileInputStream in = new FileInputStream(file);

				in.read(buffer);

				in.close();
			} else
				System.out.print(filename
						+ ": is not a file ,please confirm you file name");
		} catch (IOException e) {

			e.printStackTrace();

		}
		return buffer;
	}
	
	/**  
	 * 写文件  
	 * @param strBuffer 写入得字符串  
	 * @param filename  文件名（包括路径）  
	 */
	public static void write(byte[] Buffer, String filename) {
		try {

			filename = filename.replaceAll("//", "/");

			File file = new File(filename);
			if (file.isFile()) {
				System.out.println(filename + ": has existed..delete..");
			}

			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			out.write(Buffer);
			out.close();
			file.exists();
			// log.info(filename+": end writing ... ");   
		} catch (IOException e) {
			e.printStackTrace();

		}

	}
	
	public static void main(String[] args) {
		String fileName= "20110816";
		ReadData(fileName);
	}
}
