package andy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 
 * 
 * ǰ��2ͳ��
 * 
 * ���ܣ�ȡ��һ��ʱ�ڵ�����,ͳ�Ƴ��ִ���,��������������ע��
 */
public class TxtReader_BT2_Invote2 {


	// ȡ���жϵ���
	private static List<String[]> befResults = new ArrayList();

	// ȡ���жϵ���
	private static List<String[]> aftResults = new ArrayList();
	
	// ȡ���ж���Դ����
	private static List<String> JudgeSourch = new ArrayList();
	
	private final static int AppearNum = 1450;
	
	// ����������
	private static List<String> befGetNums = new ArrayList();
	
	// ����������
	private static List<String> aftGetNums = new ArrayList();

	/**
	 * ��ȡ����
	 * 
	 * @param fileName
	 */
	public static void ReadData(String fileName) {
		
		//��Ҫ�����ʱ��ε��ļ�·��	
		String invotePath = "F:/prjDocument/2011/invote/From/"+fileName + ".txt";
		
		//ȡ����Ҫ�������Դ�ķ����ļ�
		String resourcePath = "F:/prjDocument/2011/invote/From/Betting_100.txt";
				
		String beforeResPath = "F:/prjDocument/2011/invote/To/beforeRes_100.txt";
		
		String aftResPath = "F:/prjDocument/2011/invote/To/afterRes_100.txt";
		
		String time = getTdate();
		
		//�����������Ͷע�ļ�
		String befInvoteResPath = "F:/prjDocument/2011/invote/To/befInvoteRes_"+ time +".txt";
		
		String aftInvoteResPath = "F:/prjDocument/2011/invote/To/aftInvoteRes_"+ time +".txt";
		
		
		try {
			JudgeSourch = getInvoteNum(resourcePath);
			
			//ȡ������ʱ��ε���
			List<String> allNums = getInvoteTimeNum(invotePath);
			
			//ǰ2�ĵ���			
			//������ֵĴ�����
			select_Before(allNums);
			
			//д���ļ�
			Integer count = Writefile(befResults, beforeResPath ,1);
			
			//�ѽ�����������Ϊ�����뱣��Ϊ�ļ�
			WriteToSourshFile(befGetNums, befInvoteResPath);
			
			System.out.println(" ǰ2ͳ�ƽ���--------------------------->��Ҫ�Ĺ���"+count+"�� ");
			
			//��2�ĵ���
			
			//������ֵĴ�����
			select_After(allNums);
			//��������浽�ļ���

			count = Writefile(aftResults, aftResPath,2);
			
			//�ѽ�����������Ϊ�����뱣��Ϊ�ļ�
			WriteToSourshFile(aftGetNums, aftInvoteResPath);
			
			System.out.println(" ��2ͳ�ƽ���--------------------------->��Ҫ�Ĺ���"+count+"�� ");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getTdate() {
		return new SimpleDateFormat("yyyy-MM-dd_HHMMSS").format(new Date());
	}
	
	// ��txt�ļ���׷������
	private static void WriteToSourshFile(List<String> sb, String filePath)
			throws FileNotFoundException, IOException {
		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			//String temp;
			String writeLine;
			for (int i = 0; i < sb.size(); i++) {			
				writeLine = sb.get(i);
				//writeLine = "���룺-->" + temp[0] + "--����-->" + temp[1];
				bw.newLine();
				bw.write(writeLine);

			}

			bw.flush();
			fw.close();
		} catch (IOException e) {

		}

	}

	// ��txt�ļ���׷������
	private static Integer Writefile(List<String[]> sb, String filePath,Integer type)
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
					if(Integer.valueOf(temp[1]) > AppearNum){					
						count ++;
						//�������
						if(type == 1){						
							befGetNums.add(temp[0]);
						} else {
							aftGetNums.add(temp[0]);
						}
					}

			}

			bw.flush();
			fw.close();
		} catch (IOException e) {

		}
		return count;

	}

	private static void select_Before(List<String> allNums) {

		String everyNum = "";
		String tempNum = "";//��ʱ������
		String tmp[] = new String[2];
		//�ٽ����ж�
		for (int j = 0; j < JudgeSourch.size(); j++) {
			everyNum = JudgeSourch.get(j);
			tmp = new String[2];
			int count = 0;
			for (int i = 0; i < allNums.size(); i++) {
				tempNum = allNums.get(i);
				String	beforNum = tempNum.substring(0, 2);
				if (everyNum.equals(beforNum)) {
					count++;
				}
			}
			tmp[0] = everyNum;// url
			tmp[1] = String.valueOf(count);// title	
			befResults.add(tmp);
		}

		//System.out.println(befResults.toString());

	}

	private static void select_After(List<String> allNums) {

		String everyNum = "";
		String tempNum = "";//��ʱ������
		String tmp[] = new String[2];
		//�ٽ����ж�
		for (int j = 0; j < JudgeSourch.size(); j++) {
			everyNum = JudgeSourch.get(j);
			tmp = new String[2];
			int count = 0;
			for (int i = 0; i < allNums.size(); i++) {
				tempNum = allNums.get(i);
				String	beforNum = tempNum.substring(3, 5);
				if (everyNum.equals(beforNum)) {
					count++;
				}
			}
			tmp[0] = everyNum;// url
			tmp[1] = String.valueOf(count);// title	
			aftResults.add(tmp);
		}

		//System.out.println(befResults.toString());

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
					String[] getNums = row.split(" ");
					for(int i =0; i<getNums.length; i++){
						
						nums.add(getNums[i]);
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nums;

	}

	//ȡ����Ҫ�����ʱ����ļ�������
	private static List<String> getInvoteTimeNum(String filePath) {
		List<String> nums = new ArrayList();
		FileReader read;
		try {
			read = new FileReader(filePath);
			String row;
			BufferedReader br = new BufferedReader(read);
			while ((row = br.readLine()) != null) {
				if (!"".equals(row)) {
					String[] souchNums = row.split(" ");
					if(souchNums == null || souchNums.length < 2 ){
						souchNums = row.split("\t");
					}
					String num = souchNums[1];
					nums.add(num);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nums;

	}

	
	public static void main(String[] args) {
		//String fileName = "20110822-20110816";
		String fileName = "all_resource_A";
		ReadData(fileName);
	}
}
