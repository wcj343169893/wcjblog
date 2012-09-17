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
 *  
 * 5个胆码
 * 最大多少期不开
 */
public class TxtReader_invote_1 {

	// 前面的计数器-对的
	private static int beforCount;
	
	// 前面的计数器-错的
	private static int beforCount_;
	
	// 后面的计数器-对的
	private static int afterCount;
	
	// 后面的计数器-错的
	private static int afterCount_;
	
	// 前面的取得要用数
	private static String beforNum ="1234567";
	
	// 后面的取得要用数
	private static String afterNum ="1234567";
	
	// 数的类型1
	private static String OTSF = "1234";
	
	//  数的类型2
	private static String TFFS = "5689";
	

	
	//设置当期状态为正确的
	private static boolean isHasRight = true;
	
	//计数最大连错数
	private static int maxErrNum =0;
	
	//计数最小连错数
	private static int minErrNum =3;
	
	private static int maxErrNumTep =0;
	
	private static List<Integer> minList = new ArrayList();
	
	// 前2：每一天的判断结果
	private static List<String[]> befAllDayResults = new ArrayList();
	
	// 后2：每一天的判断结果
	private static List<String[]> aftAllDayResults = new ArrayList();
	
	// 前面的计数器-所有对的
	private static int beforCountAll;

	// 前面的计数器-所有错的
	private static int beforCountAll_;
	
	// 后面的计数器-所有对的
	private static int afterCountAll;

	// 后面的计数器-所有错的
	private static int afterCountAll_;
	
	/**
	 * 读取数据
	 * @param fileName 
	 */
	public static void ReadData(String fileName) {
		try {
			String filePath = "F:/prjDocument/2011/invote/From/"+fileName + ".txt"; 
			
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
			 String[] everyDayDate = new String[3];
			//先认证前三
			System.out.println( " 前三开始认证---------------------------> ");
			System.out.println( " -------------------------------------> ");
			while ((row = br.readLine()) != null ) {
				if("".equals(row)){
					continue;
				}
				String[] nums = row.split(" ");
				if(nums.length < 2){
					nums = row.split("\t");
				}
				String num = nums[1];
				//第1次先取得,不作任何处理
				
				//前三的先取得,或者第1次有2个0或9状态时，第2次重取
				/*if(i == 1 || "".equals(beforNum) ){
					String	beforNum2 = num.substring(0, 3);
					
						//前三的先取得
					beforNum =selectType(beforNum2);					
						i++; 
					System.out.println(row + " is--> " );
					continue;
				}		*/	
				
				//第2次开始处理
				String getBefor = num.substring(0, 1);
				
				//判断前三的对错
				selectJudge_before(getBefor,beforNum);
				
				
				//重新设置
			//	beforNum =selectType(getBefor);		
				
				
				if(isHasRight){	
					//最大连挂次数
					if(maxErrNum != 0  && maxErrNum > maxErrNumTep){						
						maxErrNumTep = maxErrNum;
					}
					/*
					if(maxErrNum != 0 ){						
						minList.add(maxErrNum);
					}*/
					
					maxErrNum = 0;
					
					System.out.println(row + " is--> " + isHasRight);
				} else {
					maxErrNum++;
					System.out.println(row + " is--> " + isHasRight +"---");
				}
				
				
				//大于: 20091214-001 小于: 20091213-01 
				String[] everyDayFind ;
				String[] pattens = row.split("-"); 
				//判断当天是否在20091214之前 
				if(compare_date(pattens[0],"20091214")) {
					everyDayFind = row.split("-01");
				} else {					
					everyDayFind = row.split("-001");
				}
				
				//分出每天的
				if(everyDayFind != null && !"".equals(everyDayFind) && everyDayFind.length > 1){
					
					everyDayDate = new String[3];
					everyDayDate[0] = everyDayFind[0];
					everyDayDate[1] = String.valueOf(beforCount);
					everyDayDate[2] = String.valueOf(beforCount_);
					befAllDayResults.add(everyDayDate);
					System.out.println("前3--> "+everyDayDate[0]+"--> 对：" + beforCount +"  错误："+beforCount_ );
					System.out.println("前3--> "+everyDayDate[0]+ "前三-->最大连错是：" + maxErrNumTep +"次");
					beforCountAll += beforCount;
					beforCountAll_ += beforCount_;
					beforCount = 0;
					beforCount_ = 0;
				}
			}
			System.out.println("前三--> 对：" + beforCount +"  错误："+beforCount_ );
			System.out.println(" ----------------------------------------------------" );
			System.out.println("前三-->最大连错是：" + maxErrNumTep +"次");
			
			System.out.println( "后三开始认证-----------------------------------------> ");
			System.out.println( " ---------------------------------------------------> ");
			
			//先认证后三
			read = new FileReader(refilePath);
			br = new BufferedReader(read);
			while ((row = br.readLine()) != null) {
				if("".equals(row.toString())){
					continue;
				}
				String[] nums = row.split(" ");
				if(nums.length < 2){
					nums = row.split("\t");
				}
				String num = nums[1];
			
								
				//第1次先取得,不作任何处理,或者第1次有2个0或9状态时，第2次重取
			/*	if(y == 1 || "".equals(afterNum)){					
					String	afterNum3 = num.substring(2, 5);
					
					//后三的先取得
					afterNum =selectType(afterNum3);
						y++; 
					System.out.println(row + " is--> " );
					continue;
				}*/
				
				/**if(maxErrNum > 2){
					System.out.println("proberm is--> " );
				}*/
				//第2次开始处理
				String getafter = num.substring(2, 5);
				
				//判断后三的对错
				selectJudge_after(getafter,afterNum);
				
				afterNum =selectType(getafter);
			
				
			
				if(isHasRight){	
					
					//最大连挂次数
					if(maxErrNum != 0  && maxErrNum > maxErrNumTep){						
						maxErrNumTep = maxErrNum;
					}
					
					/*if(maxErrNum != 0 ){						
						minList.add(maxErrNum);
					}*/
					
					maxErrNum = 0;
					System.out.println(row + " is--> " + isHasRight);
				} else {
					maxErrNum++;
					
					System.out.println(row + " is--> " + isHasRight +"---");
				}
				
				//大于: 20091214-001 小于: 20091213-01 
				String[] everyDayFind ;
				String[] pattens = row.split("-"); 
				
				//判断当天是否在20091214之前 
				if(compare_date(pattens[0],"20091214")) {
					everyDayFind = row.split("-01");
				} else {					
					everyDayFind = row.split("-001");
				}
				
				//分出每天的
				if(everyDayFind != null && !"".equals(everyDayFind) && everyDayFind.length > 1){
					everyDayDate = new String[3];
					everyDayDate[0] = everyDayFind[0];
					everyDayDate[1] = String.valueOf(afterCount);
					everyDayDate[2] = String.valueOf(afterCount_);
					aftAllDayResults.add(everyDayDate);
					System.out.println("后3--> "+everyDayDate[0]+"--> 对：" + afterCount +"  错误："+afterCount_ );
					System.out.println("后3--> "+everyDayDate[0]+ "后三-->最大连错是：" + maxErrNumTep +"次");
					
					afterCountAll += afterCount;
					afterCountAll_ += afterCount_;
					afterCount = 0;
					afterCount_ = 0;
				}
			}
			
			//取得最小连错数
			/*for(int j=0;j<minList.size();j++ ){
				Integer tempNum = minList.get(j);
				if(tempNum > 1 && tempNum< minErrNum){
					minErrNum = tempNum;
				}
			}*/
			
			System.out.println("后三--> 对：" + afterCount +"  错误："+afterCount_);
			
			System.out.println("后三-->最大连错是：" + maxErrNumTep +"次");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static boolean compare_date(String DATE1, String DATE2) {
	       
	       boolean isBefor = false;
	      
	       Integer dt1 = Integer.valueOf(DATE1);
	       Integer dt2 = Integer.valueOf(DATE2);
	       if (dt1 < dt2) {
            // System.out.println("dt1 在dt2前");
         	isBefor = true;
         } else {
            // System.out.println("dt1在dt2后");
         	isBefor = false;
         }
	       
			return isBefor;
	  }


	//判断是否是豹子
	private static boolean isTheSameNum(String getBefor) {
		boolean isSam = false;
		if("000".equals(getBefor) || "111".equals(getBefor) || "222".equals(getBefor) || 
				"333".equals(getBefor) || "444".equals(getBefor) || "555".equals(getBefor) || 
				"666".equals(getBefor) || "777".equals(getBefor) || "888".equals(getBefor) || 
				"999".equals(getBefor) ){
			isSam = true;
		}
		return isSam;
	}

	private static String selectType(String selectNum) {
		int type1 = 0;
		int type2 = 0;
		
		for(int j=0 ;j< selectNum.length();j++){
			String sigNum = String.valueOf(selectNum.charAt(j));				
				
				if(OTSF.contains(sigNum) || ("2".contains(sigNum))){
					type1 ++;
				} else {
					type2  ++;
				}						
			
		}
		//有几种状态需要判断		
		String returnNum = "";
		
		//2个0以上或者1个0+1368状态		
		if(type1 > type2){
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
		//计数必须包含2个才算对
		int i = 0;
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
		String fileName= "20110822-20110816";
		//String fileName= "all_resource_A";
		ReadData(fileName);
	}
}
