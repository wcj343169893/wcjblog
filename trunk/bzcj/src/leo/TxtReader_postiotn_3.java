package leo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 
 * 
 * 功能：判断个位数上每个数字出现后接着哪个数字的机率最大
 * 
 * 
 */
public class TxtReader_postiotn_3 {

	// 前面的计数器-对的
	private static int beforCount;
	
	// 前面的计数器-错的
	private static int beforCount_;
	
	// 后面的计数器-对的
	private static int afterCount;
	
	// 后面的计数器-错的
	private static int afterCount_;
	
	// 保存前次出现的数
	private static String beforApperNum;
	
	// 后面的取得要用数
	private static String afterNum;
	

	
	//计数最大连错数
	private static int maxErrNum =0;
	
	
	private static int maxErrNumTep =0;
	
	
	//设置当期状态为正确的
	private static boolean isHasRight = true;
	
	// 前2：每一天的判断结果
	private static List<String[]> befAllDayResults = new ArrayList();
	
	// 后2：每一天的判断结果
	private static List<String[]> aftAllDayResults = new ArrayList();
	
	// 后面的计数器-所有对的
	private static int afterCountAll;

	// 后面的计数器-所有错的
	private static int afterCountAll_;
	
	//出现为0时的
	static HashMap<String,Integer> hm_0= new HashMap<String,Integer>();
	
	//出现为1时的
	static HashMap<String,Integer> hm_1= new HashMap<String,Integer>();
	
	//出现为2时的
	static HashMap<String,Integer> hm_2= new HashMap<String,Integer>();
	
	//出现为3时的
	static HashMap<String,Integer> hm_3= new HashMap<String,Integer>();
	
	//出现为4时的
	static HashMap<String,Integer> hm_4= new HashMap<String,Integer>();
	
	//出现为5时的
	static HashMap<String,Integer> hm_5= new HashMap<String,Integer>();
	
	//出现为6时的
	static HashMap<String,Integer> hm_6= new HashMap<String,Integer>();
	
	//出现为7时的
	static HashMap<String,Integer> hm_7= new HashMap<String,Integer>();
	
	//出现为8时的
	static HashMap<String,Integer> hm_8= new HashMap<String,Integer>();
	
	//出现为9时的
	static HashMap<String,Integer> hm_9= new HashMap<String,Integer>();
	
	static String positonResPath_1 = null;
	/**
	 * 读取数据
	 * @param fileName 
	 */
	public static void ReadData(String fileName) {
		try {
			hm_0 = setHashMap();
			hm_1 = setHashMap();
			hm_2 = setHashMap();
			hm_3 = setHashMap();
			hm_4 = setHashMap();
			hm_5 = setHashMap();
			hm_6 = setHashMap();
			hm_7 = setHashMap();
			hm_8 = setHashMap();
			hm_9 = setHashMap();
			
			String filePath = "F:/prjDocument/2011/invote/From/"+fileName + ".txt"; 
			
			String refilePath = "F:/prjDocument/2011/invote_ob/"+fileName + "_ob.txt"; 
			
			//结果保存路径
			positonResPath_1 = "F:/prjDocument/2011/invote/To/"+fileName + "_p3.txt";
			
			//1:先把顺序反过来						 
			 reverfile(filePath, refilePath);
			 // System.out.println("aaaaaaaaaaaa");
			 
			//2:统计
			FileReader read = new FileReader(refilePath);
			
			
			BufferedReader br = new BufferedReader(read);
			String row;
			
			int y = 1;
			 String[] everyDayDate = new String[3];
			System.out.println( "后三开始认证-----------------------------------------> ");
			System.out.println( " ---------------------------------------------------> ");
			
			//先认证后三
			read = new FileReader(refilePath);
			br = new BufferedReader(read);
			while ((row = br.readLine()) != null) {
				
				if("".equals(row)){
					continue;
				}
				
				String[] nums = row.split(" ");
				if("".equals(nums)){
					continue;
				}
				if(nums.length ==1){
					nums = row.split("\t");
				}
				String num = nums[1];
			
								
				//第1次先取得,不作任何处理,或者第1次有2个0或9状态时，第2次重取
				if(y == 1 || "".equals(afterNum)){					
					beforApperNum = num.substring(2, 3);
										
						y++; 
					System.out.println(row + " is--> " );
					continue;
				}
				
				//第2次开始处理
				String getafter = num.substring(2, 3);
				
				//判断后三的对错
				selectJudge_after(getafter,afterNum);
				
				beforApperNum = getafter;
				
						
				if(isHasRight){	
					//最大连挂次数
					if(maxErrNum != 0  && maxErrNum > maxErrNumTep){						
						maxErrNumTep = maxErrNum;
					}
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
					System.out.println("后2--> "+everyDayDate[0]+"--> 对：" + afterCount +"  错误："+afterCount_ );
					System.out.println("后2-->最大连错是：" + maxErrNumTep +"次");
					
					afterCountAll += afterCount;
					afterCountAll_ += afterCount_;
					afterCount = 0;
					afterCount_ = 0;
					//打印每天所所有的数
					printlnEverDays(true);
					System.out.println("打印结束！");
				}
			}
			System.out.println("后三--> 对：" + afterCount +"  错误："+afterCount_);
		
			System.out.println("后三-->最大连错是：" + maxErrNumTep +"次");
			
			//打印所所有的期数
			printlnEverDays(false);
			System.out.println("打印结束！");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printlnEverDays(boolean isNow) {
		//打印出每天0的情况					
		printlnEveryDay(hm_0.keySet(),hm_0, 0 ,isNow);
		//打印出每天1的情况
		printlnEveryDay(hm_1.keySet(),hm_1,1 ,isNow);
		//打印出每天2的情况
		printlnEveryDay(hm_2.keySet(),hm_2,2 ,isNow);
		//打印出每天3的情况
		printlnEveryDay(hm_3.keySet(),hm_3,3 ,isNow);
		//打印出每天4的情况
		printlnEveryDay(hm_4.keySet(),hm_4,4 ,isNow);
		//打印出每天5的情况
		printlnEveryDay(hm_5.keySet(),hm_5,5 ,isNow);
		//打印出每天6的情况
		printlnEveryDay(hm_6.keySet(),hm_6,6 ,isNow);
		//打印出每天4的情况
		printlnEveryDay(hm_7.keySet(),hm_7,7 ,isNow);
		//打印出每天4的情况
		printlnEveryDay(hm_8.keySet(),hm_8,8 ,isNow);
		//打印出每天4的情况
		printlnEveryDay(hm_9.keySet(),hm_9,9 ,isNow);
	}

	private static void printlnEveryDay(Set keys,HashMap<String,Integer> hm,Integer whichNum,boolean isNow) {
		if(isNow){			
			System.out.println("当天为--"+whichNum+"--的情况开始统计");	
			printElemets(keys,hm, whichNum, isNow);
			System.out.println("当天为--"+whichNum+"--的情况已经结束");
		} else {
			System.out.println("所有结果期数为--"+whichNum+"--的情况开始统计");	
			printElemets(keys,hm, whichNum, isNow);
			System.out.println("所有结果期数为--"+whichNum+"--的情况已经结束");
		}
	}
	
	static void printElemets(Collection c,HashMap hm,Integer whichNum ,boolean isNow){
		Iterator it = c.iterator();
			try {
				FileWriter fw = new FileWriter(positonResPath_1, true);
				BufferedWriter bw = new BufferedWriter(fw);
			
				String writeLine;
				
				if(isNow){			
					writeLine = "当天为--"+whichNum+"--的情况开始统计";	
					
				} else {
					writeLine = "所有结果期数为--"+whichNum+"--的情况开始统计";	
					bw.newLine();
					bw.write(writeLine);
				}
				
				
				
				while(it.hasNext()){
					Object key1= it.next();
					System.out.println(key1 + "=：" + hm.get(key1));
				//写入文件计数器				
				
					//出现大于
					if(!isNow){
						writeLine = "号码：-->" + key1 + "--出现-->" + hm.get(key1);
						bw.newLine();
						bw.write(writeLine);
					}
						//取出需要的条数
						/*if(Integer.valueOf(temp[1]) > AppearNum){					
							count ++;
							//保存号码
							if(type == 1){						
								befGetNums.add(temp[0]);
							} else {
								aftGetNums.add(temp[0]);
							}
						}*/

				}
				
				if(isNow){			
					writeLine = "当天为--"+whichNum+"--的情况已经结束";	
					
				} else {
					writeLine = "所有结果期数为--"+whichNum+"--的情况已经结束";	
					bw.newLine();
					bw.write(writeLine);
				}

				
				
				bw.flush();
				fw.close();
			} catch (IOException e) {

			}
		
	}
	
	// 在txt文件后追加内容
	private static Integer Writefile(List<String[]> sb, String filePath)
			throws FileNotFoundException, IOException {
		Integer count = 0;
		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			String[] temp;
			String writeLine;
			//写入文件计数器
			
			for (int i = 0; i < sb.size(); i++) {
				temp = new String[2];
				temp = sb.get(i);
				//出现大于
					writeLine = "号码：-->" + temp[0] + "--出现-->" + temp[1];
					bw.newLine();
					bw.write(writeLine);
					//取出需要的条数
					/*if(Integer.valueOf(temp[1]) > AppearNum){					
						count ++;
						//保存号码
						if(type == 1){						
							befGetNums.add(temp[0]);
						} else {
							aftGetNums.add(temp[0]);
						}
					}*/

			}

			bw.flush();
			fw.close();
		} catch (IOException e) {

		}
		return count;

	}


	//为每个数设置
	private static HashMap<String,Integer> setHashMap() {
		HashMap<String,Integer> hm_Mpa = new HashMap<String,Integer>();
		//初始化出现为0时的
		hm_Mpa.put("0", 0);
		hm_Mpa.put("1", 0);
		hm_Mpa.put("2", 0);
		hm_Mpa.put("3", 0);
		hm_Mpa.put("4", 0);
		hm_Mpa.put("5", 0);
		hm_Mpa.put("6", 0);
		hm_Mpa.put("7", 0);
		hm_Mpa.put("8", 0);
		hm_Mpa.put("9", 0);
		return hm_Mpa;
	}

	
	
	
	
	//判断后三对错
	private static void selectJudge_after(String getafter,String judgeNum) {

		Integer befApNum = Integer.valueOf(beforApperNum);
		Integer getEveNum;
		switch (befApNum){
			case 0:
				//取出现在的数
				 getEveNum = hm_0.get(getafter);
				hm_0.put(getafter, getEveNum+1);			
			break;
			case 1:
				//取出现在的数
				 getEveNum = hm_1.get(getafter);
				hm_1.put(getafter, getEveNum+1);			
			break;
			case 2:
				//取出现在的数
				 getEveNum = hm_2.get(getafter);
				hm_2.put(getafter, getEveNum+1);			
			break;
			case 3:
				//取出现在的数
				 getEveNum = hm_3.get(getafter);
				hm_3.put(getafter, getEveNum+1);			
			break;
			case 4:
				//取出现在的数
				 getEveNum = hm_4.get(getafter);
				hm_4.put(getafter, getEveNum+1);			
			break;
			case 5:
				//取出现在的数
				 getEveNum = hm_5.get(getafter);
				hm_5.put(getafter, getEveNum+1);			
			break;
			case 6:
				//取出现在的数
				 getEveNum = hm_6.get(getafter);
				hm_6.put(getafter, getEveNum+1);			
			break;
			case 7:
				//取出现在的数
				 getEveNum = hm_7.get(getafter);
				hm_7.put(getafter, getEveNum+1);			
			break;
			case 8:
				//取出现在的数
				 getEveNum = hm_8.get(getafter);
				hm_8.put(getafter, getEveNum+1);			
			break;
			case 9:
				//取出现在的数
				 getEveNum = hm_9.get(getafter);
				hm_9.put(getafter, getEveNum+1);			
			break;
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

	public static boolean compare_date(String DATE1, String DATE2) {
	       
	       boolean isBefor = false;
	       /* DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        try {
	            Date dt1 = df.parse(DATE1);
	            Date dt2 = df.parse(DATE2);
	            if (dt1.getTime() > dt2.getTime()) {
	               // System.out.println("dt1 在dt2前");
	            	isBefor = true;
	            } else if (dt1.getTime() < dt2.getTime()) {
	               // System.out.println("dt1在dt2后");
	            	isBefor = false;
	            }

	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }*/
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
	
	public static void main(String[] args) {
		
		//String fileName= "20110822-20110816";
		String fileName= "all_resource_A";
		ReadData(fileName);
		
		/*Hashtable numbers = new Hashtable();
	     numbers.put("one", new Integer(1));
	     numbers.put("two", new Integer(2));
	     numbers.put("three", new Integer(3));
	     numbers.put("three", new Integer(4));
	   
	     Integer number = (Integer) numbers.get("three");
	     if(number != null){
	    	 System.out.println(number);
	     }*/
		
		/*HashMap<String,Integer> hm_Mpa = new HashMap<String,Integer>();
		//初始化出现为0时的
		hm_Mpa.put("0", 1);
		hm_Mpa.put("1", 1);
		hm_Mpa.put("2", 1);
		hm_Mpa.put("2", 5);
		Integer number = (Integer) hm_Mpa.get("2");
	     if(number != null){
	    	 System.out.println(number);
	     }*/
	}
}
