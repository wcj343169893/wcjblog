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
 * ��2-7��
 * 0136847
 * 2457936
 * 
 * ÿ�ڶ����ĵ�ʱ
 */
public class TxtReader_Type2_7 {

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
	
	// �����ȡ��Ҫ����
	private static String afterNum;
	
	// ��������1
	private static String OTSF = "0136859";
	
	//  ��������2
	
	private static String TFFS = "2457918";
	
	//�������������
	private static int maxErrNum =0;
	
	
	private static int maxErrNumTep =0;
	
	
	//���õ���״̬Ϊ��ȷ��
	private static boolean isHasRight = true;
	
	// ǰ2��ÿһ����жϽ��
	private static List<String[]> befAllDayResults = new ArrayList();
	
	// ��2��ÿһ����жϽ��
	private static List<String[]> aftAllDayResults = new ArrayList();
	
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
			System.out.println( "ǰ����ʼ��֤-----------------------------------------> ");
			System.out.println( " ---------------------------------------------------> ");
			
			/*while ((row = br.readLine()) != null && !"".equals(row)) {
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
				//��1����ȡ��,�����κδ���
				
				//ǰ������ȡ��,���ߵ�1����2��0��9״̬ʱ����2����ȡ
				if(i == 1 || "".equals(beforNum) ){
					String	beforNum2 = num.substring(0, 2);
					
						//ǰ������ȡ��
					beforNum =selectType(beforNum2);					
						i++; 
					System.out.println(row + " is--> " );
					continue;
				}			
				
				//��2�ο�ʼ����
				String getBefor = num.substring(0, 3);
				
				//�ж�ǰ���ĶԴ�
				selectJudge_before(getBefor,beforNum);
				
				
				//��������
				//String	beforNum2 = num.substring(0, 3);
				
				//ǰ������ȡ��
				beforNum =selectType(getBefor);					
				
				if(isHasRight){					
					System.out.println(row + " is--> " + isHasRight);
				} else {
					System.out.println(row + " is--> " + isHasRight +"---");
				}
			}*/
			System.out.println("ǰ��--> �ԣ�" + beforCount +"  ����"+beforCount_);
			System.out.println(" ----------------------------------------------------" );
			System.out.println( "������ʼ��֤-----------------------------------------> ");
			System.out.println( " ---------------------------------------------------> ");
			
			//����֤����
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
			
								
				//��1����ȡ��,�����κδ���,���ߵ�1����2��0��9״̬ʱ����2����ȡ
				if(y == 1 || "".equals(afterNum)){					
					String	afterNum3 = num.substring(3, 5);
					
					//��������ȡ��
					afterNum =selectType(afterNum3);
						y++; 
					System.out.println(row + " is--> " );
					continue;
				}
				
				//��2�ο�ʼ����
				String getafter = num.substring(3, 5);
				
				//�жϺ����ĶԴ�
				selectJudge_after(getafter,afterNum);
				
				//��������
				//String	beforNum2 = num.substring(2, 5);
			
				//��������ȡ��
				afterNum =selectType(getafter);				
				if(isHasRight){	
					//������Ҵ���
					if(maxErrNum != 0  && maxErrNum > maxErrNumTep){						
						maxErrNumTep = maxErrNum;
					}
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
					System.out.println("��2--> "+everyDayDate[0]+"--> �ԣ�" + afterCount +"  ����"+afterCount_ );
					System.out.println("��2-->��������ǣ�" + maxErrNumTep +"��");
					if(maxErrNumTep == 4){
						System.out.println("��2-->��������ǣ�" + maxErrNumTep +"��");
					}
					afterCountAll += afterCount;
					afterCountAll_ += afterCount_;
					afterCount = 0;
					afterCount_ = 0;
				}
			}
			System.out.println("ǰ��--> �ԣ�" + beforCount +"  ����"+beforCount_);
			System.out.println("����--> �ԣ�" + afterCount +"  ����"+afterCount_);
		
			System.out.println("����-->��������ǣ�" + maxErrNumTep +"��");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String selectType(String selectNum) {
		
		String returnNum = "";
			String sigNum = String.valueOf(selectNum.charAt(0));
			
				//0״̬
				if(OTSF.contains(sigNum)){
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
	
	public static void main(String[] args) {
		String fileName= "20110822-20110816";
		ReadData(fileName);
	}
}
