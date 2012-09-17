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
 * ÿ�ڶ����ĵ�ʱ
 */
public class TxtReader_Type2 {

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
	private static String OTSF = "1368";
	
	//  ��������2
	private static String TFFS = "2457";
	
	//  ��������3
	private static String ONE = "0";
	
//  ��������3
	private static String NIGHT = "9";
	
	//���õ���״̬Ϊ��ȷ��
	private static boolean isHasRight = true;
	/**
	 * ��ȡ����
	 * @param fileName 
	 */
	public static void ReadData(String fileName) {
		try {
			String filePath = "F:/prjDocument/2011/invote/"+fileName + ".txt"; 
			
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
			//����֤ǰ��
			System.out.println( "ǰ����ʼ��֤-----------------------------------------> ");
			System.out.println( " ---------------------------------------------------> ");
			
			while ((row = br.readLine()) != null && !"".equals(row)) {
				String[] nums = row.split(" ");
				String num = nums[1];
				//��1����ȡ��,�����κδ���
				
				//ǰ������ȡ��,���ߵ�1����2��0��9״̬ʱ����2����ȡ
				if(i == 1 || "".equals(beforNum) ){
					String	beforNum2 = num.substring(0, 3);
					
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
			}
			System.out.println("ǰ��--> �ԣ�" + beforCount +"  ����"+beforCount_);
			System.out.println(" ----------------------------------------------------" );
			System.out.println( "������ʼ��֤-----------------------------------------> ");
			System.out.println( " ---------------------------------------------------> ");
			
			//����֤����
			read = new FileReader(refilePath);
			br = new BufferedReader(read);
			while ((row = br.readLine()) != null) {
				String[] nums = row.split(" ");
				String num = nums[1];
			
								
				//��1����ȡ��,�����κδ���,���ߵ�1����2��0��9״̬ʱ����2����ȡ
				if(y == 1 || "".equals(afterNum)){					
					String	afterNum3 = num.substring(2, 5);
					
					//��������ȡ��
					afterNum =selectType(afterNum3);
						y++; 
					System.out.println(row + " is--> " );
					continue;
				}
				
				//��2�ο�ʼ����
				String getafter = num.substring(2, 5);
				
				//�жϺ����ĶԴ�
				selectJudge_after(getafter,afterNum);
				
				//��������
				//String	beforNum2 = num.substring(2, 5);
			
				//��������ȡ��
				afterNum =selectType(getafter);				
				if(isHasRight){					
					System.out.println(row + " is--> " + isHasRight);
				} else {
					System.out.println(row + " is--> " + isHasRight +"---");
				}
			}
			System.out.println("ǰ��--> �ԣ�" + beforCount +"  ����"+beforCount_);
			System.out.println("����--> �ԣ�" + afterCount +"  ����"+afterCount_);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String selectType(String selectNum) {
		int type1 = 0;
		int type2 = 0;
		//������0��9״̬��2��ʱ
		int onestatus = 0 ;
		int nighttatus = 0 ;
		for(int j=0 ;j< selectNum.length();j++){
			String sigNum = String.valueOf(selectNum.charAt(j));
			
				//0״̬
				if(ONE.contains(sigNum)){
					onestatus ++;
					continue;
				}
				
				//9״̬
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
		//�м���״̬��Ҫ�ж�		
		String returnNum = "";
		
		//2��0���ϻ���1��0+1368״̬		
		if(type1 > type2 || onestatus > 1 || (onestatus+type1) > 1){
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
	
	public static void main(String[] args) {
		String fileName= "20110816";
		ReadData(fileName);
	}
}
