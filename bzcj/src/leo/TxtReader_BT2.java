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
 * ǰ��2ͳ��
 * ���ܣ������ļ���ע��,ͳ�ƶԴ�ӯ�����
 * 3:����35��
 */
public class TxtReader_BT2 {

	// ǰ��ļ�����-�Ե�
	private static int beforCount;

	// ǰ��ļ�����-���
	private static int beforCount_;

	// ����ļ�����-�Ե�
	private static int afterCount;

	// ����ļ�����-���
	private static int afterCount_;

	// ǰ��ļ�����-���жԵ�
	private static int beforCountAll;

	// ǰ��ļ�����-���д��
	private static int beforCountAll_;
	
	// ����ļ�����-���жԵ�
	private static int afterCountAll;

	// ����ļ�����-���д��
	private static int afterCountAll_;

	// ǰ�ж���
	private static List<String> juldsNumsBef;

	// ���ж���
	private static List<String> juldsNumsAft;


	// ���õ���״̬Ϊ��ȷ��
	private static boolean isHasRight = true;

	// ǰ2��ÿһ����жϽ��
	private static List<String[]> befAllDayResults = new ArrayList();
	
	// ��2��ÿһ����жϽ��
	private static List<String[]> aftAllDayResults = new ArrayList();
	
	//ǰ��:��༫����
	private static int profitNumBef_1 = 47;
	private static int profitNumBef_2 = 36;
	
	//����:��༫����
	private static int profitNumAft_1 = 47;
	private static int profitNumAft_2 = 36;
	
	/**
	 * ��ȡ����
	 * 
	 * @param fileName
	 */
	public static void ReadData(String fileName) {
		
		
		try {
			//ǰ�ж��ļ�			
			//String befFile ="befInvoteRet.txt";
			String befFile ="aftInvoteRes_62.txt";
			String judgeBefFilePath = "F:/prjDocument/2011/invote/To/"+befFile; 
			//ǰ�ж���ȡ��
			juldsNumsBef = settingBeforNums(judgeBefFilePath);
			
			//ǰ�ж��ļ�
			//String aftFile ="aftInvoteRet.txt";
			String aftFile ="aftInvoteRes_62.txt";
			String judgeAftFilePath = "F:/prjDocument/2011/invote/To/"+aftFile; 
			//ǰ�ж���ȡ��
			juldsNumsAft = settingBeforNums(judgeAftFilePath);
			
			
			// System.out.println(juldsNums.toString());
			
			String filePath = "F:/prjDocument/2011/invote/From/EveryDayFiles/"+fileName + ".txt"; 
			String time = getTdate();
			String errBefPath = "F:/prjDocument/2011/invote/To/errorBef_"+ time +".txt"; 
			
			String errAftPath = "F:/prjDocument/2011/invote/To/errorAft_"+ time +".txt"; 
			
			// 1:�Ȱ�˳�򷴹���
						 
			// reverfile(filePath, refilePath);
			 // System.out.println("aaaaaaaaaaaa");
			 
			// 2:ͳ��
			FileReader read = new FileReader(filePath);		
			
			BufferedReader br = new BufferedReader(read);
			String row;
			int i = 1;
			int y = 1;
			// ����֤ǰ2
			System.out.println( " ǰ2��ʼ��֤---------------------------> ");
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
				
				
				// ��2�ο�ʼ����
				String getBefor = num.substring(0, 2);
				
				// �ж�ǰ���ĶԴ�
				selectJudge_before(getBefor,juldsNumsBef.toString());
				
				
				if(isHasRight){					
					System.out.println(row + " is--> " + isHasRight);
				} else {
					errBefors.add(getBefor);
					System.out.println(row + " is--> " + isHasRight +"---");
				}
				
				
				//����: 20091214-001 С��: 20091213-01 
				String[] everyDayFind ;
				String[] pattens = row.split("-"); 
				//�жϵ����Ƿ���20091214֮ǰ 
				if(compare_date(pattens[0],"20091214")) {
					everyDayFind = row.split("-01");
				} else {					
					everyDayFind = row.split("-001");
				}
				
				//�ֳ�ÿ���
				if(everyDayFind != null && !"".equals(everyDayFind) && everyDayFind.length > 1){
					
					everyDayDate = new String[3];
					everyDayDate[0] = everyDayFind[0];
					everyDayDate[1] = String.valueOf(beforCount);
					everyDayDate[2] = String.valueOf(beforCount_);
					befAllDayResults.add(everyDayDate);
					System.out.println("ǰ2--> "+everyDayDate[0]+"--> �ԣ�" + beforCount +"  ����"+beforCount_ );
					beforCountAll += beforCount;
					beforCountAll_ += beforCount_;
					beforCount = 0;
					beforCount_ = 0;
				}
			}
			//�������д���Ľ��
			Writefile(errBefors,errBefPath);
			
			//����ÿ���ӯ����� //Profit
			String eveFilePath ="F:/prjDocument/2011/invote/To/Profit_Bef2_"+ fileName +".txt"; 
			
			EveryWriteToFile_Bef(befAllDayResults,eveFilePath);
			
			System.out.println("ǰ2��֤����------------------------------------------" + beforCountAll +"  ����"+beforCountAll_ );
			System.out.println(" ----------------------------------------------------" );
			System.out.println( "��2��ʼ��֤-----------------------------------------> ");
			System.out.println( " ---------------------------------------------------> ");
			// ����֤��2
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
				
				// ��2�ο�ʼ����
				String getafter = num.substring(3, 5);
				
				// �жϺ�2�ĶԴ�
				selectJudge_after(getafter,juldsNumsAft.toString());
			
				
				if(isHasRight){					
					System.out.println(row + " is--> " + isHasRight);
				} else {
					errAfters.add(getafter);
					System.out.println(row + " is--> " + isHasRight +"---");
				}
				
				
								
				//����: 20091214-001 С��: 20091213-01 
				String[] everyDayFind ;
				String[] pattens = row.split("-"); 
				//�жϵ����Ƿ���20091214֮ǰ 
				if(compare_date(pattens[0],"20091214")) {
					everyDayFind = row.split("-01");
				} else {					
					everyDayFind = row.split("-001");
				}
				
				//�ֳ�ÿ���
				if(everyDayFind != null && !"".equals(everyDayFind) && everyDayFind.length > 1){
					everyDayDate = new String[3];
					everyDayDate[0] = everyDayFind[0];
					everyDayDate[1] = String.valueOf(afterCount);
					everyDayDate[2] = String.valueOf(afterCount_);
					aftAllDayResults.add(everyDayDate);
					System.out.println("��2--> "+everyDayDate[0]+"--> �ԣ�" + afterCount +"  ����"+afterCount_ );
					afterCountAll += afterCount;
					afterCountAll_ += afterCount_;
					afterCount = 0;
					afterCount_ = 0;
				}
			}
			

			//�������д���Ľ��
			Writefile(errAfters,errAftPath);		
			
			//����ÿ���ӯ����� //Profit
			eveFilePath ="F:/prjDocument/2011/invote/To/Profit_Aft2_"+ fileName +".txt"; 
			
			EveryWriteToFile_Aft(befAllDayResults,eveFilePath);
			
			System.out.println("ǰ2-->�ܵģ� �ԣ�" + beforCountAll +"  ����"+beforCountAll_);
			
			System.out.println("��2-->�ܵģ� �ԣ�" + afterCountAll +"  ����"+afterCountAll_);
			
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
	               // System.out.println("dt1 ��dt2ǰ");
	            	isBefor = true;
	            } else if (dt1.getTime() < dt2.getTime()) {
	               // System.out.println("dt1��dt2��");
	            	isBefor = false;
	            }

	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }*/
	       Integer dt1 = Integer.valueOf(DATE1);
	       Integer dt2 = Integer.valueOf(DATE2);
	       if (dt1 < dt2) {
               // System.out.println("dt1 ��dt2ǰ");
            	isBefor = true;
            } else {
               // System.out.println("dt1��dt2��");
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
			//д���ļ�������
			
			for (int i = 0; i < sb.size(); i++) {
				temp = new String[3];
				temp = sb.get(i);
				//�жϵ����Ƿ���20091214֮ǰ 
				Integer profitNum = Integer.valueOf(temp[2]);
				if(compare_date(temp[0],"20091214")) {
					String prfStr = profitNum.toString();
					if(profitNum > profitNumBef_2){
						prfStr += "--------------";
					}
					writeLine = "����Ϊ�� " + temp[0] + "-->�ԣ�-->" + temp[1] + "--��-->" + prfStr;
				} else {					
					String prfStr = profitNum.toString();
					if(profitNum > profitNumBef_1){
						prfStr += "--------------";
					}
					writeLine = "����Ϊ�� " + temp[0] + "-->�ԣ�-->" + temp[1] + "--��-->" + prfStr;
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
			//д���ļ�������
			
			for (int i = 0; i < sb.size(); i++) {
				temp = new String[3];
				temp = sb.get(i);
				//�жϵ����Ƿ���20091214֮ǰ 
				Integer profitNum = Integer.valueOf(temp[2]);
				if(compare_date(temp[0],"20091214")) {
					String prfStr = profitNum.toString();
					if(profitNum > profitNumAft_2){
						prfStr += "--------------";
					}
					writeLine = "����Ϊ�� " + temp[0] + "-->�ԣ�-->" + temp[1] + "--��-->" + prfStr;
				} else {					
					String prfStr = profitNum.toString();
					if(profitNum > profitNumAft_1){
						prfStr += "--------------";
					}
					writeLine = "����Ϊ�� " + temp[0] + "-->�ԣ�-->" + temp[1] + "--��-->" + prfStr;
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
		String fileName = "all_resource_A";
		ReadData(fileName);
	}
}
