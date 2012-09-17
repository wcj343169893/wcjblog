package leo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 
 * 
 * ������ʱ��ͳ��
 * ���ܣ�������ʱ��ͳ��
 * 
 */
public class TxtReader_Nouth_Invote_2 {

	// ȡ��ǰ2�жϵ���,�ǲ��ظ���
	private static List<String> befGetNums = new ArrayList();
	
	//	 ȡ��ԭ������
	private static List<String> getNums = new ArrayList();


	// ȡ���жϵ���
	private static List<String[]> befResults = new ArrayList();

	// ȡ����2�жϵ���,�ǲ��ظ���
	private static List<String> aftGetNums = new ArrayList();

	// ȡ���жϵ���
	private static List<String[]> aftResults = new ArrayList();
	
	//�ظ�����
	private static List<String[]> recounts = new ArrayList();

	static int setCount;
	/**
	 * ��ȡ����
	 * 
	 * @param fileName
	 */
	public static void ReadData(String fileName,String selectNum) {

		
		String reSultPath = "F:/prjDocument/2011/invote/To/"+fileName + "_Resut.txt"; 
		
		String reSultPath_re = "F:/prjDocument/2011/invote/To/"+fileName + "_Resut_re.txt"; 
		
		//1:�Ȱ�˳�򷴹���
					 
		 try {
			//reverfile(filePath, refilePath);
				//ǰ2�ĵ���
				List<String> befNums = getInvoteNum(reSultPath);
				
				//������ֵĴ�����
				select_Before(befNums,selectNum);
				//��������浽�ļ���
				System.out.println(" ----------------------��ʼ���ɽ���ļ�---------------------------> ");
				Writefile(befResults, reSultPath);
				System.out.println("---------------------- ǰ2ͳ�ƽ���---------------------------> ");

				//�ظ����ļ�����				
				Writefile(recounts, reSultPath_re);
				
				//�ҳ�������û���ֹ�����
				
			
		}  catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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

	// ��txt�ļ���׷������
	private static void Writefile(List<String[]> sb, String filePath)
			throws FileNotFoundException, IOException {
		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			String[] temp;
			String writeLine;
			for (int i = 0; i < sb.size(); i++) {
				temp = new String[2];
				temp = sb.get(i);
				writeLine = "���룺-->" + temp[0] + "--����---->" + temp[1];
				//���������ظ��Ľ��
				if(Integer.valueOf(temp[1])> 1){
					recounts.add(temp);
				}
				bw.newLine();
				bw.write(writeLine);

			}

			bw.flush();
			fw.close();
		} catch (IOException e) {

		}

	}

	
	private static void select_Before(List<String> befNums,String selectNum) {

		String everyNum = "";
		String tempNum = "";//��ʱ������
		int reCount = 0;
		getNums.clear();
		befGetNums.clear();
		befResults.clear();
		recounts.clear();
		
		for (int j = 0; j < befNums.size(); j++) {
			String row = befNums.get(j);
			//everyNum = befNums.get(j);

			if("".equals(row)){
				continue;
			}
			
			
			String[] nums = row.split("��");
			if(nums == null || nums.length < 2 ){
				 nums = row.split("\t");
			}
			/*if(j == 1082){
				System.out.println("---------------"+j+"---------------");
			}*/
			everyNum = nums[1];
			//System.out.println("---------------"+j+"---------------");
			befGetNums.add(everyNum);
			

		}

		String tmp[] = new String[2];
		//�ٽ����ж�
	
		boolean isFind = false;
		String[] numbs = selectNum.split(" ");
		for (int j = 0; j < befGetNums.size(); j++) {
			everyNum = befGetNums.get(j);
			tmp = new String[2];
			int count = 0;
			for (int i = 0; i < numbs.length; i++) {
				tempNum = numbs[i];
				if (everyNum.contains(tempNum)) {
					count++;
					if(count == setCount){
						System.out.println("---------------"+(j+1)+"---------------"+everyNum);
						isFind = true;
						break;
					}
				}
			}
			
		}
		if(isFind){
			System.out.println("----------�Ѿ����ֹ���!---------------");
		} else {
			System.out.println("----------û�г��ֹ�!---------------");
		}

		//System.out.println(befResults.toString());

	}

	private static void select_After(List<String> befNums) {

		String everyNum = "";
		String tempNum = "";//��ʱ������
		boolean isGet = false;
		for (int j = 0; j < befNums.size(); j++) {
			everyNum = befNums.get(j);

			if (!isGet) {
				aftGetNums.add(everyNum);
				isGet = true;
				continue;
			}
			//ȡ�����ظ�����
			if (aftGetNums != null && !aftGetNums.contains(everyNum)) {
				aftGetNums.add(everyNum);
			}

		}

		String tmp[] = new String[2];
		//�ٽ����ж�
		for (int j = 0; j < aftGetNums.size(); j++) {
			everyNum = aftGetNums.get(j);
			tmp = new String[2];
			int count = 0;
			for (int i = 0; i < befNums.size(); i++) {
				tempNum = befNums.get(i);
				if (everyNum.equals(tempNum)) {
					count++;
				}
			}
			tmp[0] = everyNum;// url
			tmp[1] = String.valueOf(count);// title	
			aftResults.add(tmp);
		}

	}

	//ȡ����Ҫ���������
	private static List<String> getInvoteNum(String filePath) {
		List<String> nums = new ArrayList();
		FileReader read;
		try {
			read = new FileReader(filePath);
			String row;
			BufferedReader br = new BufferedReader(read);
			while ((row = br.readLine()) != null) {
				if (!"".equals(row)) {

					nums.add(row);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nums;

	}

	public static void main(String[] args) {
		//String fileName= "20110822-20110816";
		//String fileName= "all_resource_A";
		String fileName= "2008-2011";
		String selectNum = "02 04 09 12 18 25 31";
		setCount = selectNum.split(" ").length;//2��Ͷ�Ӧ2��7�Ͷ�Ӧ7
		System.out.println("setCount = "+setCount);
		ReadData(fileName,selectNum);
	}
}
