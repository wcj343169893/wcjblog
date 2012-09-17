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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 
 * www.shishicai.cn
 * 前后2统计
 * 功能：根据文件的注数,统计对错及盈利结果
 * 3:最多错35次
 */
public class TxtReader_BT2 {

	// 前面的计数器-对的
	private static int beforCount;

	// 前面的计数器-错的
	private static int beforCount_;

	// 后面的计数器-对的
	private static int afterCount;

	// 后面的计数器-错的
	private static int afterCount_;

	// 前面的计数器-所有对的
	private static int beforCountAll;

	// 前面的计数器-所有错的
	private static int beforCountAll_;
	
	// 后面的计数器-所有对的
	private static int afterCountAll;

	// 后面的计数器-所有错的
	private static int afterCountAll_;

	// 前判断码
	private static List<String> juldsNumsBef;

	// 后判断码
	private static List<String> juldsNumsAft;


	// 设置当期状态为正确的
	private static boolean isHasRight = true;

	// 前2：每一天的判断结果
	private static List<String[]> befAllDayResults = new ArrayList();
	
	// 后2：每一天的判断结果
	private static List<String[]> aftAllDayResults = new ArrayList();
	
	//前三:最多极限数
	private static int profitNumBef_1 = 47;
	private static int profitNumBef_2 = 36;
	
	//后三:最多极限数
	private static int profitNumAft_1 = 47;
	private static int profitNumAft_2 = 36;
	
	/**
	 * 读取数据
	 * 
	 * @param fileName
	 */
	public static void ReadData(String fileName) {
		
		
		try {
			//前判断文件			
			//String befFile ="befInvoteRet.txt";
			String befFile ="aftInvoteRes_62.txt";
			String judgeBefFilePath = "F:/prjDocument/2011/invote/To/"+befFile; 
			//前判断数取得
			juldsNumsBef = settingBeforNums(judgeBefFilePath);
			
			//前判断文件
			//String aftFile ="aftInvoteRet.txt";
			String aftFile ="aftInvoteRes_62.txt";
			String judgeAftFilePath = "F:/prjDocument/2011/invote/To/"+aftFile; 
			//前判断数取得
			juldsNumsAft = settingBeforNums(judgeAftFilePath);
			
			
			// System.out.println(juldsNums.toString());
			
			String filePath = "F:/prjDocument/2011/invote/From/EveryDayFiles/"+fileName + ".txt"; 
			String time = getTdate();
			String errBefPath = "F:/prjDocument/2011/invote/To/errorBef_"+ time +".txt"; 
			
			String errAftPath = "F:/prjDocument/2011/invote/To/errorAft_"+ time +".txt"; 
			
			// 1:先把顺序反过来
						 
			// reverfile(filePath, refilePath);
			 // System.out.println("aaaaaaaaaaaa");
			 
			// 2:统计
			FileReader read = new FileReader(filePath);		
			
			BufferedReader br = new BufferedReader(read);
			String row;
			int i = 1;
			int y = 1;
			// 先认证前2
			System.out.println( " 前2开始认证---------------------------> ");
			System.out.println( " -------------------------------------> ");
			 List<String> errBefors = new ArrayList();
			 errBefors.add("---------------------------"+fileName+"\r\n");
			 List<String> errAfters = new ArrayList();
			 errAfters.add("---------------------------"+fileName+"\r\n");
			 
			 String[] everyDayDate = new String[3];
			 
			while ((row = br.readLine()) != null) {
				if("".equals(row)){
					continue;
				}
				
				
				String[] nums = row.split(" ");
				if(nums == null || nums.length < 2 ){
					 nums = row.split("\t");
				}
			
				String num = nums[1];
				
				
				// 第2次开始处理
				String getBefor = num.substring(0, 2);
				
				// 判断前三的对错
				selectJudge_before(getBefor,juldsNumsBef.toString());
				
				
				if(isHasRight){					
					System.out.println(row + " is--> " + isHasRight);
				} else {
					errBefors.add(getBefor);
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
					System.out.println("前2--> "+everyDayDate[0]+"--> 对：" + beforCount +"  错误："+beforCount_ );
					beforCountAll += beforCount;
					beforCountAll_ += beforCount_;
					beforCount = 0;
					beforCount_ = 0;
				}
			}
			//保存所有错误的结果
			Writefile(errBefors,errBefPath);
			
			//保存每天的盈利结果 //Profit
			String eveFilePath ="F:/prjDocument/2011/invote/To/Profit_Bef2_"+ fileName +".txt"; 
			
			EveryWriteToFile_Bef(befAllDayResults,eveFilePath);
			
			System.out.println("前2认证结束------------------------------------------" + beforCountAll +"  错误："+beforCountAll_ );
			System.out.println(" ----------------------------------------------------" );
			System.out.println( "后2开始认证-----------------------------------------> ");
			System.out.println( " ---------------------------------------------------> ");
			// 先认证后2
			read = new FileReader(filePath);
			br = new BufferedReader(read);
			while ((row = br.readLine()) != null) {
				if("".equals(row)){
					continue;
				}
				
				
				
				String[] nums = row.split(" ");
				if(nums == null || nums.length < 2 ){
					 nums = row.split("\t");
				}
				
				String num = nums[1];				
				
				// 第2次开始处理
				String getafter = num.substring(3, 5);
				
				// 判断后2的对错
				selectJudge_after(getafter,juldsNumsAft.toString());
			
				
				if(isHasRight){					
					System.out.println(row + " is--> " + isHasRight);
				} else {
					errAfters.add(getafter);
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
					afterCountAll += afterCount;
					afterCountAll_ += afterCount_;
					afterCount = 0;
					afterCount_ = 0;
				}
			}
			

			//保存所有错误的结果
			Writefile(errAfters,errAftPath);		
			
			//保存每天的盈利结果 //Profit
			eveFilePath ="F:/prjDocument/2011/invote/To/Profit_Aft2_"+ fileName +".txt"; 
			
			EveryWriteToFile_Aft(befAllDayResults,eveFilePath);
			
			System.out.println("前2-->总的： 对：" + beforCountAll +"  错误："+beforCountAll_);
			
			System.out.println("后2-->总的： 对：" + afterCountAll +"  错误："+afterCountAll_);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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

	
	private static void EveryWriteToFile_Bef(List<String[]> sb,
			String filePath) {
				
		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			String[] temp;
			String writeLine;
			//写入文件计数器
			
			for (int i = 0; i < sb.size(); i++) {
				temp = new String[3];
				temp = sb.get(i);
				//判断当天是否在20091214之前 
				Integer profitNum = Integer.valueOf(temp[2]);
				if(compare_date(temp[0],"20091214")) {
					String prfStr = profitNum.toString();
					if(profitNum > profitNumBef_2){
						prfStr += "--------------";
					}
					writeLine = "日期为： " + temp[0] + "-->对：-->" + temp[1] + "--错-->" + prfStr;
				} else {					
					String prfStr = profitNum.toString();
					if(profitNum > profitNumBef_1){
						prfStr += "--------------";
					}
					writeLine = "日期为： " + temp[0] + "-->对：-->" + temp[1] + "--错-->" + prfStr;
				}
					
					bw.newLine();
					bw.write(writeLine);
					}

			bw.flush();
			fw.close();
		} catch (IOException e) {

		}
		

		
	}
	
	private static void EveryWriteToFile_Aft(List<String[]> sb,
			String filePath) {
				
		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			String[] temp;
			String writeLine;
			//写入文件计数器
			
			for (int i = 0; i < sb.size(); i++) {
				temp = new String[3];
				temp = sb.get(i);
				//判断当天是否在20091214之前 
				Integer profitNum = Integer.valueOf(temp[2]);
				if(compare_date(temp[0],"20091214")) {
					String prfStr = profitNum.toString();
					if(profitNum > profitNumAft_2){
						prfStr += "--------------";
					}
					writeLine = "日期为： " + temp[0] + "-->对：-->" + temp[1] + "--错-->" + prfStr;
				} else {					
					String prfStr = profitNum.toString();
					if(profitNum > profitNumAft_1){
						prfStr += "--------------";
					}
					writeLine = "日期为： " + temp[0] + "-->对：-->" + temp[1] + "--错-->" + prfStr;
				}
					
					bw.newLine();
					bw.write(writeLine);
					}

			bw.flush();
			fw.close();
		} catch (IOException e) {

		}
		

		
	}

	public static String getTdate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
	private  static List<String> settingBeforNums(String filePath) {
		List<String> juldsNums = new ArrayList();
		FileReader read;
		try {
			read = new FileReader(filePath);
			BufferedReader br = new BufferedReader(read);
			String row;
			
			while ((row = br.readLine()) != null) {
				if(!"".equals(row)){
					juldsNums.add(row);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		return juldsNums;

		
	}
	
	



	// 判断前三对错
	private static void selectJudge_before(String selectNum, String judgeNum) {
		boolean isRight = false;

		if (judgeNum.contains(selectNum)) {
			isRight = true;// 有一个就就表达正确了

		}

		if (isRight) {
			beforCount++;
			isHasRight = true;
		} else {
			beforCount_++;
			isHasRight = false;
		}

	}

	// 判断后三对错
	private static void selectJudge_after(String selectNum, String judgeNum) {

		boolean isRight = false;

		if (judgeNum.contains(selectNum)) {
			isRight = true;// 有一个就就表达正确了

		}

		if (isRight) {
			afterCount++;
			isHasRight = true;
		} else {
			afterCount_++;
			isHasRight = false;
		}

	}


	// 在txt文件后追加内容
	private static void Writefile(List<String> sb, String filePath)
			throws FileNotFoundException, IOException {
		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0 ; i < sb.size(); i++) {
				
				bw.newLine();
				bw.write(sb.get(i));

			}

			bw.flush();
			fw.close();
		} catch (IOException e) {

		}

	}

	/**
	 * 读取绝对路径的文件内容
	 * 
	 * @param filename
	 *            String
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
	 * 
	 * @param strBuffer
	 *            写入得字符串
	 * @param filename
	 *            文件名（包括路径）
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
		String fileName = "all_resource_A";
		ReadData(fileName);
	}
}
