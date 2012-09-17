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
 * ���ܣ��жϸ�λ����ÿ�����ֳ��ֺ�����ĸ����ֵĻ������
 * 
 * 
 */
public class TxtReader_postiotn_3 {

	// ǰ��ļ�����-�Ե�
	private static int beforCount;
	
	// ǰ��ļ�����-���
	private static int beforCount_;
	
	// ����ļ�����-�Ե�
	private static int afterCount;
	
	// ����ļ�����-���
	private static int afterCount_;
	
	// ����ǰ�γ��ֵ���
	private static String beforApperNum;
	
	// �����ȡ��Ҫ����
	private static String afterNum;
	

	
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
	
	//����Ϊ0ʱ��
	static HashMap<String,Integer> hm_0= new HashMap<String,Integer>();
	
	//����Ϊ1ʱ��
	static HashMap<String,Integer> hm_1= new HashMap<String,Integer>();
	
	//����Ϊ2ʱ��
	static HashMap<String,Integer> hm_2= new HashMap<String,Integer>();
	
	//����Ϊ3ʱ��
	static HashMap<String,Integer> hm_3= new HashMap<String,Integer>();
	
	//����Ϊ4ʱ��
	static HashMap<String,Integer> hm_4= new HashMap<String,Integer>();
	
	//����Ϊ5ʱ��
	static HashMap<String,Integer> hm_5= new HashMap<String,Integer>();
	
	//����Ϊ6ʱ��
	static HashMap<String,Integer> hm_6= new HashMap<String,Integer>();
	
	//����Ϊ7ʱ��
	static HashMap<String,Integer> hm_7= new HashMap<String,Integer>();
	
	//����Ϊ8ʱ��
	static HashMap<String,Integer> hm_8= new HashMap<String,Integer>();
	
	//����Ϊ9ʱ��
	static HashMap<String,Integer> hm_9= new HashMap<String,Integer>();
	
	static String positonResPath_1 = null;
	/**
	 * ��ȡ����
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
			
			//�������·��
			positonResPath_1 = "F:/prjDocument/2011/invote/To/"+fileName + "_p3.txt";
			
			//1:�Ȱ�˳�򷴹���						 
			 reverfile(filePath, refilePath);
			 // System.out.println("aaaaaaaaaaaa");
			 
			//2:ͳ��
			FileReader read = new FileReader(refilePath);
			
			
			BufferedReader br = new BufferedReader(read);
			String row;
			
			int y = 1;
			 String[] everyDayDate = new String[3];
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
					beforApperNum = num.substring(2, 3);
										
						y++; 
					System.out.println(row + " is--> " );
					continue;
				}
				
				//��2�ο�ʼ����
				String getafter = num.substring(2, 3);
				
				//�жϺ����ĶԴ�
				selectJudge_after(getafter,afterNum);
				
				beforApperNum = getafter;
				
						
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
					
					afterCountAll += afterCount;
					afterCountAll_ += afterCount_;
					afterCount = 0;
					afterCount_ = 0;
					//��ӡÿ�������е���
					printlnEverDays(true);
					System.out.println("��ӡ������");
				}
			}
			System.out.println("����--> �ԣ�" + afterCount +"  ����"+afterCount_);
		
			System.out.println("����-->��������ǣ�" + maxErrNumTep +"��");
			
			//��ӡ�����е�����
			printlnEverDays(false);
			System.out.println("��ӡ������");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printlnEverDays(boolean isNow) {
		//��ӡ��ÿ��0�����					
		printlnEveryDay(hm_0.keySet(),hm_0, 0 ,isNow);
		//��ӡ��ÿ��1�����
		printlnEveryDay(hm_1.keySet(),hm_1,1 ,isNow);
		//��ӡ��ÿ��2�����
		printlnEveryDay(hm_2.keySet(),hm_2,2 ,isNow);
		//��ӡ��ÿ��3�����
		printlnEveryDay(hm_3.keySet(),hm_3,3 ,isNow);
		//��ӡ��ÿ��4�����
		printlnEveryDay(hm_4.keySet(),hm_4,4 ,isNow);
		//��ӡ��ÿ��5�����
		printlnEveryDay(hm_5.keySet(),hm_5,5 ,isNow);
		//��ӡ��ÿ��6�����
		printlnEveryDay(hm_6.keySet(),hm_6,6 ,isNow);
		//��ӡ��ÿ��4�����
		printlnEveryDay(hm_7.keySet(),hm_7,7 ,isNow);
		//��ӡ��ÿ��4�����
		printlnEveryDay(hm_8.keySet(),hm_8,8 ,isNow);
		//��ӡ��ÿ��4�����
		printlnEveryDay(hm_9.keySet(),hm_9,9 ,isNow);
	}

	private static void printlnEveryDay(Set keys,HashMap<String,Integer> hm,Integer whichNum,boolean isNow) {
		if(isNow){			
			System.out.println("����Ϊ--"+whichNum+"--�������ʼͳ��");	
			printElemets(keys,hm, whichNum, isNow);
			System.out.println("����Ϊ--"+whichNum+"--������Ѿ�����");
		} else {
			System.out.println("���н������Ϊ--"+whichNum+"--�������ʼͳ��");	
			printElemets(keys,hm, whichNum, isNow);
			System.out.println("���н������Ϊ--"+whichNum+"--������Ѿ�����");
		}
	}
	
	static void printElemets(Collection c,HashMap hm,Integer whichNum ,boolean isNow){
		Iterator it = c.iterator();
			try {
				FileWriter fw = new FileWriter(positonResPath_1, true);
				BufferedWriter bw = new BufferedWriter(fw);
			
				String writeLine;
				
				if(isNow){			
					writeLine = "����Ϊ--"+whichNum+"--�������ʼͳ��";	
					
				} else {
					writeLine = "���н������Ϊ--"+whichNum+"--�������ʼͳ��";	
					bw.newLine();
					bw.write(writeLine);
				}
				
				
				
				while(it.hasNext()){
					Object key1= it.next();
					System.out.println(key1 + "=��" + hm.get(key1));
				//д���ļ�������				
				
					//���ִ���
					if(!isNow){
						writeLine = "���룺-->" + key1 + "--����-->" + hm.get(key1);
						bw.newLine();
						bw.write(writeLine);
					}
						//ȡ����Ҫ������
						/*if(Integer.valueOf(temp[1]) > AppearNum){					
							count ++;
							//�������
							if(type == 1){						
								befGetNums.add(temp[0]);
							} else {
								aftGetNums.add(temp[0]);
							}
						}*/

				}
				
				if(isNow){			
					writeLine = "����Ϊ--"+whichNum+"--������Ѿ�����";	
					
				} else {
					writeLine = "���н������Ϊ--"+whichNum+"--������Ѿ�����";	
					bw.newLine();
					bw.write(writeLine);
				}

				
				
				bw.flush();
				fw.close();
			} catch (IOException e) {

			}
		
	}
	
	// ��txt�ļ���׷������
	private static Integer Writefile(List<String[]> sb, String filePath)
			throws FileNotFoundException, IOException {
		Integer count = 0;
		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			String[] temp;
			String writeLine;
			//д���ļ�������
			
			for (int i = 0; i < sb.size(); i++) {
				temp = new String[2];
				temp = sb.get(i);
				//���ִ���
					writeLine = "���룺-->" + temp[0] + "--����-->" + temp[1];
					bw.newLine();
					bw.write(writeLine);
					//ȡ����Ҫ������
					/*if(Integer.valueOf(temp[1]) > AppearNum){					
						count ++;
						//�������
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


	//Ϊÿ��������
	private static HashMap<String,Integer> setHashMap() {
		HashMap<String,Integer> hm_Mpa = new HashMap<String,Integer>();
		//��ʼ������Ϊ0ʱ��
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

	
	
	
	
	//�жϺ����Դ�
	private static void selectJudge_after(String getafter,String judgeNum) {

		Integer befApNum = Integer.valueOf(beforApperNum);
		Integer getEveNum;
		switch (befApNum){
			case 0:
				//ȡ�����ڵ���
				 getEveNum = hm_0.get(getafter);
				hm_0.put(getafter, getEveNum+1);			
			break;
			case 1:
				//ȡ�����ڵ���
				 getEveNum = hm_1.get(getafter);
				hm_1.put(getafter, getEveNum+1);			
			break;
			case 2:
				//ȡ�����ڵ���
				 getEveNum = hm_2.get(getafter);
				hm_2.put(getafter, getEveNum+1);			
			break;
			case 3:
				//ȡ�����ڵ���
				 getEveNum = hm_3.get(getafter);
				hm_3.put(getafter, getEveNum+1);			
			break;
			case 4:
				//ȡ�����ڵ���
				 getEveNum = hm_4.get(getafter);
				hm_4.put(getafter, getEveNum+1);			
			break;
			case 5:
				//ȡ�����ڵ���
				 getEveNum = hm_5.get(getafter);
				hm_5.put(getafter, getEveNum+1);			
			break;
			case 6:
				//ȡ�����ڵ���
				 getEveNum = hm_6.get(getafter);
				hm_6.put(getafter, getEveNum+1);			
			break;
			case 7:
				//ȡ�����ڵ���
				 getEveNum = hm_7.get(getafter);
				hm_7.put(getafter, getEveNum+1);			
			break;
			case 8:
				//ȡ�����ڵ���
				 getEveNum = hm_8.get(getafter);
				hm_8.put(getafter, getEveNum+1);			
			break;
			case 9:
				//ȡ�����ڵ���
				 getEveNum = hm_9.get(getafter);
				hm_9.put(getafter, getEveNum+1);			
			break;
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
		//��ʼ������Ϊ0ʱ��
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
