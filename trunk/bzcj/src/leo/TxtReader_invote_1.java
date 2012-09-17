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
 * 5������
 * �������ڲ���
 */
public class TxtReader_invote_1 {

	// ǰ��ļ�����-�Ե�
	private static int beforCount;
	
	// ǰ��ļ�����-���
	private static int beforCount_;
	
	// ����ļ�����-�Ե�
	private static int afterCount;
	
	// ����ļ�����-���
	private static int afterCount_;
	
	// ǰ���ȡ��Ҫ����
	private static String beforNum ="1234567";
	
	// �����ȡ��Ҫ����
	private static String afterNum ="1234567";
	
	// ��������1
	private static String OTSF = "1234";
	
	//  ��������2
	private static String TFFS = "5689";
	

	
	//���õ���״̬Ϊ��ȷ��
	private static boolean isHasRight = true;
	
	//�������������
	private static int maxErrNum =0;
	
	//������С������
	private static int minErrNum =3;
	
	private static int maxErrNumTep =0;
	
	private static List<Integer> minList = new ArrayList();
	
	// ǰ2��ÿһ����жϽ��
	private static List<String[]> befAllDayResults = new ArrayList();
	
	// ��2��ÿһ����жϽ��
	private static List<String[]> aftAllDayResults = new ArrayList();
	
	// ǰ��ļ�����-���жԵ�
	private static int beforCountAll;

	// ǰ��ļ�����-���д��
	private static int beforCountAll_;
	
	// ����ļ�����-���жԵ�
	private static int afterCountAll;

	// ����ļ�����-���д��
	private static int afterCountAll_;
	
	/**
	 * ��ȡ����
	 * @param fileName 
	 */
	public static void ReadData(String fileName) {
		try {
			String filePath = "F:/prjDocument/2011/invote/From/"+fileName + ".txt"; 
			
			String refilePath = "F:/prjDocument/2011/invote_ob/"+fileName + "_ob.txt"; 
			
			//1:�Ȱ�˳�򷴹���						 
			 reverfile(filePath, refilePath);
			 // System.out.println("aaaaaaaaaaaa");
			 
			//2:ͳ��
			FileReader read = new FileReader(refilePath);
			
			
			BufferedReader br = new BufferedReader(read);
			String row;
			int i = 1;
			int y = 1;
			 String[] everyDayDate = new String[3];
			//����֤ǰ��
			System.out.println( " ǰ����ʼ��֤---------------------------> ");
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
				//��1����ȡ��,�����κδ���
				
				//ǰ������ȡ��,���ߵ�1����2��0��9״̬ʱ����2����ȡ
				/*if(i == 1 || "".equals(beforNum) ){
					String	beforNum2 = num.substring(0, 3);
					
						//ǰ������ȡ��
					beforNum =selectType(beforNum2);					
						i++; 
					System.out.println(row + " is--> " );
					continue;
				}		*/	
				
				//��2�ο�ʼ����
				String getBefor = num.substring(0, 1);
				
				//�ж�ǰ���ĶԴ�
				selectJudge_before(getBefor,beforNum);
				
				
				//��������
			//	beforNum =selectType(getBefor);		
				
				
				if(isHasRight){	
					//������Ҵ���
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
					System.out.println("ǰ3--> "+everyDayDate[0]+"--> �ԣ�" + beforCount +"  ����"+beforCount_ );
					System.out.println("ǰ3--> "+everyDayDate[0]+ "ǰ��-->��������ǣ�" + maxErrNumTep +"��");
					beforCountAll += beforCount;
					beforCountAll_ += beforCount_;
					beforCount = 0;
					beforCount_ = 0;
				}
			}
			System.out.println("ǰ��--> �ԣ�" + beforCount +"  ����"+beforCount_ );
			System.out.println(" ----------------------------------------------------" );
			System.out.println("ǰ��-->��������ǣ�" + maxErrNumTep +"��");
			
			System.out.println( "������ʼ��֤-----------------------------------------> ");
			System.out.println( " ---------------------------------------------------> ");
			
			//����֤����
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
			
								
				//��1����ȡ��,�����κδ���,���ߵ�1����2��0��9״̬ʱ����2����ȡ
			/*	if(y == 1 || "".equals(afterNum)){					
					String	afterNum3 = num.substring(2, 5);
					
					//��������ȡ��
					afterNum =selectType(afterNum3);
						y++; 
					System.out.println(row + " is--> " );
					continue;
				}*/
				
				/**if(maxErrNum > 2){
					System.out.println("proberm is--> " );
				}*/
				//��2�ο�ʼ����
				String getafter = num.substring(2, 5);
				
				//�жϺ����ĶԴ�
				selectJudge_after(getafter,afterNum);
				
				afterNum =selectType(getafter);
			
				
			
				if(isHasRight){	
					
					//������Ҵ���
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
					System.out.println("��3--> "+everyDayDate[0]+"--> �ԣ�" + afterCount +"  ����"+afterCount_ );
					System.out.println("��3--> "+everyDayDate[0]+ "����-->��������ǣ�" + maxErrNumTep +"��");
					
					afterCountAll += afterCount;
					afterCountAll_ += afterCount_;
					afterCount = 0;
					afterCount_ = 0;
				}
			}
			
			//ȡ����С������
			/*for(int j=0;j<minList.size();j++ ){
				Integer tempNum = minList.get(j);
				if(tempNum > 1 && tempNum< minErrNum){
					minErrNum = tempNum;
				}
			}*/
			
			System.out.println("����--> �ԣ�" + afterCount +"  ����"+afterCount_);
			
			System.out.println("����-->��������ǣ�" + maxErrNumTep +"��");
			
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
            // System.out.println("dt1 ��dt2ǰ");
         	isBefor = true;
         } else {
            // System.out.println("dt1��dt2��");
         	isBefor = false;
         }
	       
			return isBefor;
	  }


	//�ж��Ƿ��Ǳ���
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
		//�м���״̬��Ҫ�ж�		
		String returnNum = "";
		
		//2��0���ϻ���1��0+1368״̬		
		if(type1 > type2){
			returnNum = OTSF;
		} else {
			returnNum = TFFS;
		}
		return returnNum;
	}
	
	//�ж�ǰ���Դ�
	private static void selectJudge_before(String selectNum,String judgeNum) {
		boolean isRight = false;
		
		for(int j=0 ;j< selectNum.length();j++){
			String sigNum = String.valueOf(selectNum.charAt(j));
			if(judgeNum.contains(sigNum)){
				isRight = true;//��һ���;ͱ����ȷ��
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
	
	
	//�жϺ����Դ�
	private static void selectJudge_after(String selectNum,String judgeNum) {

		boolean isRight = false;
		//�����������2�������
		int i = 0;
		for(int j=0 ;j< selectNum.length();j++){
			String sigNum = String.valueOf(selectNum.charAt(j));
			if(judgeNum.contains(sigNum)){				
								
					isRight = true;//��һ���;ͱ����ȷ��
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
		BufferedReader br = new BufferedReader(new FileReader(filePath));//Ҫȷ����Ŀ¼����test.txt�ļ���������
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
	 * ��ȡ����·�����ļ�����  
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
	 * д�ļ�  
	 * @param strBuffer д����ַ���  
	 * @param filename  �ļ���������·����  
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
