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
 * ǰ��2ͳ��
 * ���ܣ����ݵ���,ͳ�ƶԴ�
 * 3:����35��
 */
public class TxtReader_BT2_2 {

	// ǰ��ļ�����-�Ե�
	private static int beforCount;

	// ǰ��ļ�����-���
	private static int beforCount_;

	// ����ļ�����-�Ե�
	private static int afterCount;

	// ����ļ�����-���
	private static int afterCount_;

	// ǰ���ȡ��Ҫ����
	private static String beforNum;

	// ǰ���ж���
	private static List<String> juldsNums;

	// �����ȡ��Ҫ����
	private static String afterNum;

	// ��������1
	private static String OTSF = "01368";

	// ��������2
	private static String TFFS = "24579";

	// ��������3
	private static String ONE = "0";

	// ��������3
	private static String NIGHT = "9";

	// ���õ���״̬Ϊ��ȷ��
	private static boolean isHasRight = true;

	/**
	 * ��ȡ����
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
			
			// 1:�Ȱ�˳�򷴹���
						 
			// reverfile(filePath, refilePath);
			 // System.out.println("aaaaaaaaaaaa");
			 
			// 2:ͳ��
			FileReader read = new FileReader(filePath);		
			
			BufferedReader br = new BufferedReader(read);
			String row;
			int i = 1;
			int y = 1;
			// ����֤ǰ��
			System.out.println( " ǰ2��ʼ��֤---------------------------> ");
			System.out.println( " -------------------------------------> ");
			 List<String> errBefors = new ArrayList();
			 errBefors.add("---------------------------"+fileName+"\r\n");
			 List<String> errAfters = new ArrayList();
			 errAfters.add("---------------------------"+fileName+"\r\n");
			while ((row = br.readLine()) != null && !"".equals(row)) {
				String[] nums = row.split(" ");
				String num = nums[1];
				// ��1����ȡ��,�����κδ���
				
				// ǰ������ȡ��,���ߵ�1����2��0��9״̬ʱ����2����ȡ
				/*
				 * if(i == 1 || "".equals(beforNum) ){ String beforNum2 =
				 * num.substring(0, 2);
				 * 
				 * //ǰ������ȡ�� beforNum =selectType2(beforNum2); i++;
				 * System.out.println(row + " is--> " ); continue; }
				 */		
				
				// ��2�ο�ʼ����
				String getBefor = num.substring(0, 2);
				
				// �ж�ǰ���ĶԴ�
				selectJudge_before(getBefor,juldsNums.toString());
				
				
				// ��������
				// String beforNum2 = num.substring(0, 3);
				
				// ǰ������ȡ��
				// 1����ʱ����
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
			
			System.out.println("ǰ2--> �ԣ�" + beforCount +"  ����"+beforCount_ );
			System.out.println(" ----------------------------------------------------" );
			System.out.println( "��2��ʼ��֤-----------------------------------------> ");
			System.out.println( " ---------------------------------------------------> ");
			// ����֤����
			read = new FileReader(filePath);
			br = new BufferedReader(read);
			while ((row = br.readLine()) != null) {
				String[] nums = row.split(" ");
				String num = nums[1];
			
								
				// ��1����ȡ��,�����κδ���,���ߵ�1����2��0��9״̬ʱ����2����ȡ
				/*
				 * if(y == 1 || "".equals(afterNum)){ String afterNum3 =
				 * num.substring(3, 5);
				 * 
				 * //��������ȡ�� afterNum =selectType2(afterNum3); y++;
				 * System.out.println(row + " is--> " ); continue; }
				 */
				
				// ��2�ο�ʼ����
				String getafter = num.substring(3, 5);
				
				// �жϺ����ĶԴ�
				selectJudge_after(getafter,juldsNums.toString());
				
				// ��������
				// String beforNum2 = num.substring(2, 5);
			
				// ��������ȡ��
				// 1����ʱ����
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
			
			System.out.println("ǰ2--> �ԣ�" + beforCount +"  ����"+beforCount_);
			
			System.out.println("��2--> �ԣ�" + afterCount +"  ����"+afterCount_);
			
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
		//75 --����   26  -->1950   94 -->1974
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

	// �ж��Ƿ��Ǳ���
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
		// ������0��9״̬��2��ʱ
		int onestatus = 0;
		int nighttatus = 0;

		String sigNum = String.valueOf(selectNum.charAt(0));

		if (OTSF.contains(sigNum)) {
			type1++;
		} else {
			type2++;
		}

		// �м���״̬��Ҫ�ж�
		String returnNum = "";

		// 2��0���ϻ���1��0+1368״̬
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
		// ������0��9״̬��2��ʱ
		int onestatus = 0;
		int nighttatus = 0;
		for (int j = 0; j < selectNum.length(); j++) {
			String sigNum = String.valueOf(selectNum.charAt(j));

			// 0״̬
			if (ONE.contains(sigNum)) {
				onestatus++;
				continue;
			}

			// 9״̬
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
		// �м���״̬��Ҫ�ж�
		String returnNum = "";

		// 2��0���ϻ���1��0+1368״̬
		if (type1 > type2 || onestatus > 1 || (onestatus + type1) > 1) {
			returnNum = OTSF;
		} else {
			returnNum = TFFS;
		}
		return returnNum;
	}

	// �ж�ǰ���Դ�
	private static void selectJudge_before(String selectNum, String judgeNum) {
		boolean isRight = false;

		if (judgeNum.contains(selectNum)) {
			isRight = true;// ��һ���;ͱ����ȷ��

		}

		if (isRight) {
			beforCount++;
			isHasRight = true;
		} else {
			beforCount_++;
			isHasRight = false;
		}

	}

	// �жϺ����Դ�
	private static void selectJudge_after(String selectNum, String judgeNum) {

		boolean isRight = false;

		if (judgeNum.contains(selectNum)) {
			isRight = true;// ��һ���;ͱ����ȷ��

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
		BufferedReader br = new BufferedReader(new FileReader(filePath));// Ҫȷ����Ŀ¼����test.txt�ļ���������
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

	// ��txt�ļ���׷������
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
	 * ��ȡ����·�����ļ�����
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
	 * д�ļ�
	 * 
	 * @param strBuffer
	 *            д����ַ���
	 * @param filename
	 *            �ļ���������·����
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
