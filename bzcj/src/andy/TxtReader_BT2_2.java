package andy;

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
import java.util.List;

/**
 * @author 
 * 
 * 前后2统计
 * 功能：根据胆码,统计对错
 * 3:最多错35次
 */
public class TxtReader_BT2_2 {

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

	// 前后判断码
	private static List<String> juldsNums;

	// 后面的取得要用数
	private static String afterNum;

	// 数的类型1
	private static String OTSF = "01368";

	// 数的类型2
	private static String TFFS = "24579";

	// 数的类型3
	private static String ONE = "0";

	// 数的类型3
	private static String NIGHT = "9";

	// 设置当期状态为正确的
	private static boolean isHasRight = true;

	/**
	 * 读取数据
	 * 
	 * @param fileName
	 */
	public static void ReadData(String fileName) {
		
		
		try {
			
			
			juldsNums = settingBeforNums3();
			
			// System.out.println(juldsNums.toString());
			
			String filePath = "F:/prjDocument/2011/invote/"+fileName + ".txt"; 
			
			String errBefPath = "F:/prjDocument/2011/invote/errorBef.txt"; 
			
			String errAftPath = "F:/prjDocument/2011/invote/errorAft.txt";
			
			// 1:先把顺序反过来
						 
			// reverfile(filePath, refilePath);
			 // System.out.println("aaaaaaaaaaaa");
			 
			// 2:统计
			FileReader read = new FileReader(filePath);		
			
			BufferedReader br = new BufferedReader(read);
			String row;
			int i = 1;
			int y = 1;
			// 先认证前三
			System.out.println( " 前2开始认证---------------------------> ");
			System.out.println( " -------------------------------------> ");
			 List<String> errBefors = new ArrayList();
			 errBefors.add("---------------------------"+fileName+"\r\n");
			 List<String> errAfters = new ArrayList();
			 errAfters.add("---------------------------"+fileName+"\r\n");
			while ((row = br.readLine()) != null && !"".equals(row)) {
				String[] nums = row.split(" ");
				String num = nums[1];
				// 第1次先取得,不作任何处理
				
				// 前三的先取得,或者第1次有2个0或9状态时，第2次重取
				/*
				 * if(i == 1 || "".equals(beforNum) ){ String beforNum2 =
				 * num.substring(0, 2);
				 * 
				 * //前三的先取得 beforNum =selectType2(beforNum2); i++;
				 * System.out.println(row + " is--> " ); continue; }
				 */		
				
				// 第2次开始处理
				String getBefor = num.substring(0, 2);
				
				// 判断前三的对错
				selectJudge_before(getBefor,juldsNums.toString());
				
				
				// 重新设置
				// String beforNum2 = num.substring(0, 3);
				
				// 前三的先取得
				// 1：错时不变
				/*
				 * if(isHasRight){
				 * 
				 * beforNum =selectType2(getBefor); }
				 */
				
				
				if(isHasRight){					
					System.out.println(row + " is--> " + isHasRight);
				} else {
					errBefors.add(getBefor);
					System.out.println(row + " is--> " + isHasRight +"---");
				}
			}
			
			Writefile(errBefors,errBefPath);
			
			System.out.println("前2--> 对：" + beforCount +"  错误："+beforCount_ );
			System.out.println(" ----------------------------------------------------" );
			System.out.println( "后2开始认证-----------------------------------------> ");
			System.out.println( " ---------------------------------------------------> ");
			// 先认证后三
			read = new FileReader(filePath);
			br = new BufferedReader(read);
			while ((row = br.readLine()) != null) {
				String[] nums = row.split(" ");
				String num = nums[1];
			
								
				// 第1次先取得,不作任何处理,或者第1次有2个0或9状态时，第2次重取
				/*
				 * if(y == 1 || "".equals(afterNum)){ String afterNum3 =
				 * num.substring(3, 5);
				 * 
				 * //后三的先取得 afterNum =selectType2(afterNum3); y++;
				 * System.out.println(row + " is--> " ); continue; }
				 */
				
				// 第2次开始处理
				String getafter = num.substring(3, 5);
				
				// 判断后三的对错
				selectJudge_after(getafter,juldsNums.toString());
				
				// 重新设置
				// String beforNum2 = num.substring(2, 5);
			
				// 后三的先取得
				// 1：错时不变
				/*
				 * if(isHasRight){ afterNum =selectType2(getafter); }
				 */
				
				if(isHasRight){					
					System.out.println(row + " is--> " + isHasRight);
				} else {
					errAfters.add(getafter);
					System.out.println(row + " is--> " + isHasRight +"---");
				}
			}
			
			Writefile(errAfters,errAftPath);
			
			System.out.println("前2--> 对：" + beforCount +"  错误："+beforCount_);
			
			System.out.println("后2--> 对：" + afterCount +"  错误："+afterCount_);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private  static List<String> settingBeforNums() {
		List<String> juldsNums = new ArrayList();
		juldsNums.clear();
		//67
		juldsNums.add("00");
		juldsNums.add("01");
		juldsNums.add("03");
		juldsNums.add("04");
		juldsNums.add("05");
		juldsNums.add("06");
		juldsNums.add("07");
		juldsNums.add("09");
		juldsNums.add("10");
		juldsNums.add("11");
		juldsNums.add("12");
		juldsNums.add("13");
		juldsNums.add("16");
		juldsNums.add("17");
		juldsNums.add("18");
		juldsNums.add("19");
		juldsNums.add("22");
		juldsNums.add("23");
		juldsNums.add("24");
		juldsNums.add("25");
		juldsNums.add("30");
		juldsNums.add("31");
		juldsNums.add("32");
		juldsNums.add("33");
		juldsNums.add("35");
		juldsNums.add("36");
		juldsNums.add("37");
		juldsNums.add("38");
		juldsNums.add("40");
		juldsNums.add("43");
		juldsNums.add("44");
		juldsNums.add("45");
		juldsNums.add("46");
		juldsNums.add("47");
		juldsNums.add("50");
		juldsNums.add("51");
		juldsNums.add("53");
		juldsNums.add("55");
		juldsNums.add("56");
		juldsNums.add("57");
		juldsNums.add("60");
		juldsNums.add("61");
		juldsNums.add("62");
		juldsNums.add("64");
		juldsNums.add("67");
		juldsNums.add("68");
		juldsNums.add("71");
		juldsNums.add("72");
		juldsNums.add("73");
		juldsNums.add("74");
		juldsNums.add("75");
		juldsNums.add("76");
		juldsNums.add("80");
		juldsNums.add("81");
		juldsNums.add("83");
		juldsNums.add("84");
		juldsNums.add("85");
		juldsNums.add("86");
		juldsNums.add("87");
		juldsNums.add("89");
		juldsNums.add("90");
		juldsNums.add("93");
		juldsNums.add("94");
		juldsNums.add("95");
		juldsNums.add("96");
		juldsNums.add("98");
		juldsNums.add("99");
		return juldsNums;

		
	}
	
	
	private static List<String> settingBeforNums2() {
			List<String> juldsNums = new ArrayList();
			juldsNums.clear();
			//66
		juldsNums.add("11");juldsNums.add("12");juldsNums.add("13");juldsNums.add("14");juldsNums.add("15");juldsNums.add("16");juldsNums.add("17");
		juldsNums.add("20");juldsNums.add("21");juldsNums.add("22");juldsNums.add("23");juldsNums.add("24");juldsNums.add("25");juldsNums.add("26");juldsNums.add("27");
		juldsNums.add("30");juldsNums.add("31");juldsNums.add("32");juldsNums.add("33");juldsNums.add("34");juldsNums.add("35");juldsNums.add("36");juldsNums.add("38");juldsNums.add("39");
		juldsNums.add("40");juldsNums.add("41");juldsNums.add("42");juldsNums.add("43");juldsNums.add("44");juldsNums.add("45");juldsNums.add("47");juldsNums.add("48");juldsNums.add("49");
		juldsNums.add("50");juldsNums.add("51");juldsNums.add("52");juldsNums.add("53");juldsNums.add("54");juldsNums.add("56");juldsNums.add("57");juldsNums.add("58");juldsNums.add("59");
		juldsNums.add("60");juldsNums.add("61");juldsNums.add("62");juldsNums.add("63");juldsNums.add("65");juldsNums.add("66");juldsNums.add("67");juldsNums.add("68");juldsNums.add("69");
		juldsNums.add("71");juldsNums.add("72");juldsNums.add("74");juldsNums.add("75");juldsNums.add("76");
		juldsNums.add("80");juldsNums.add("83");juldsNums.add("84");juldsNums.add("85");juldsNums.add("86");
		juldsNums.add("90");juldsNums.add("93");juldsNums.add("94");juldsNums.add("95");juldsNums.add("96");
		return juldsNums;
	

	}
	
	private static List<String> settingBeforNums3() {
		List<String> juldsNums = new ArrayList();
		juldsNums.clear();
		//75 --最大错   26  -->1950   94 -->1974
	juldsNums.add("20");juldsNums.add("21");juldsNums.add("22");juldsNums.add("23");juldsNums.add("24");juldsNums.add("25");juldsNums.add("26");juldsNums.add("27");juldsNums.add("28");juldsNums.add("29");
	juldsNums.add("40");juldsNums.add("41");juldsNums.add("42");juldsNums.add("43");juldsNums.add("44");juldsNums.add("45");juldsNums.add("46");juldsNums.add("47");juldsNums.add("48");juldsNums.add("49");
	juldsNums.add("50");juldsNums.add("51");juldsNums.add("52");juldsNums.add("53");juldsNums.add("54");juldsNums.add("55");juldsNums.add("56");juldsNums.add("57");juldsNums.add("58");juldsNums.add("59");
	juldsNums.add("70");juldsNums.add("71");juldsNums.add("72");juldsNums.add("73");juldsNums.add("74");juldsNums.add("75");juldsNums.add("76");juldsNums.add("77");juldsNums.add("78");juldsNums.add("79");
	juldsNums.add("90");juldsNums.add("91");juldsNums.add("92");juldsNums.add("93");juldsNums.add("94");juldsNums.add("95");juldsNums.add("96");juldsNums.add("97");juldsNums.add("98");juldsNums.add("99");
	juldsNums.add("02");juldsNums.add("04");juldsNums.add("05");juldsNums.add("07");juldsNums.add("09");
	juldsNums.add("12");juldsNums.add("14");juldsNums.add("15");juldsNums.add("17");juldsNums.add("19");
	juldsNums.add("32");juldsNums.add("34");juldsNums.add("35");juldsNums.add("37");juldsNums.add("39");
	juldsNums.add("62");juldsNums.add("64");juldsNums.add("65");juldsNums.add("67");juldsNums.add("69");
	juldsNums.add("82");juldsNums.add("84");juldsNums.add("85");juldsNums.add("87");juldsNums.add("89");
	return juldsNums;


}

	// 判断是否是豹子
	private static boolean isTheSameNum(String getBefor) {
		boolean isSam = false;
		if ("000".equals(getBefor) || "111".equals(getBefor)
				|| "222".equals(getBefor) || "333".equals(getBefor)
				|| "444".equals(getBefor) || "555".equals(getBefor)
				|| "666".equals(getBefor) || "777".equals(getBefor)
				|| "888".equals(getBefor) || "999".equals(getBefor)) {
			isSam = true;
		}
		return isSam;
	}

	private static String selectType2(String selectNum) {
		int type1 = 0;
		int type2 = 0;
		// 包含有0或9状态有2个时
		int onestatus = 0;
		int nighttatus = 0;

		String sigNum = String.valueOf(selectNum.charAt(0));

		if (OTSF.contains(sigNum)) {
			type1++;
		} else {
			type2++;
		}

		// 有几种状态需要判断
		String returnNum = "";

		// 2个0以上或者1个0+1368状态
		if (type1 > type2) {
			returnNum = OTSF;
		} else {
			returnNum = TFFS;
		}
		return returnNum;
	}

	private static String selectType(String selectNum) {
		int type1 = 0;
		int type2 = 0;
		// 包含有0或9状态有2个时
		int onestatus = 0;
		int nighttatus = 0;
		for (int j = 0; j < selectNum.length(); j++) {
			String sigNum = String.valueOf(selectNum.charAt(j));

			// 0状态
			if (ONE.contains(sigNum)) {
				onestatus++;
				continue;
			}

			// 9状态
			if (NIGHT.contains(sigNum)) {
				nighttatus++;
				continue;
			}

			if (OTSF.contains(sigNum)) {
				type1++;
			} else {
				type2++;
			}

		}
		// 有几种状态需要判断
		String returnNum = "";

		// 2个0以上或者1个0+1368状态
		if (type1 > type2 || onestatus > 1 || (onestatus + type1) > 1) {
			returnNum = OTSF;
		} else {
			returnNum = TFFS;
		}
		return returnNum;
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

	private static void reverfile(String filePath, String refilePath)
			throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));// 要确定根目录下有test.txt文件且有内容
		// StringBuffer sb = new StringBuffer();
		List<String> sb = new ArrayList();
		String lineContent = null;
		while ((lineContent = br.readLine()) != null) {
			/*
			 * StringTokenizer st = new StringTokenizer(lineContent, " ");
			 * 
			 * for (int t = 0; st.hasMoreElements(); t++) { String word =
			 * (String) st.nextElement(); sb.add(word);
			 *  }
			 */
			sb.add(lineContent + "\r\n");
		}

		PrintWriter pw = new PrintWriter(refilePath);
		for (int i = sb.size() - 1; i >= 0; i--) {
			pw.write(sb.get(i));
		}

		br.close();
		pw.close();
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
		String fileName = "20110816";
		ReadData(fileName);
	}
}
