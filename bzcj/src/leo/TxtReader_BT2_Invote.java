package leo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 
 * 
 * ǰ��2ͳ��
 * ���ܣ����Գ��������ĺ���
 * 3:����35��
 */
public class TxtReader_BT2_Invote {

	// ȡ��ǰ2�жϵ���,�ǲ��ظ���
	private static List<String> befGetNums = new ArrayList();

	// ȡ���жϵ���
	private static List<String[]> befResults = new ArrayList();

	// ȡ����2�жϵ���,�ǲ��ظ���
	private static List<String> aftGetNums = new ArrayList();

	// ȡ���жϵ���
	private static List<String[]> aftResults = new ArrayList();

	/**
	 * ��ȡ����
	 * 
	 * @param fileName
	 */
	public static void ReadData(String fileName) {

		//�����ļ�·��
		String errBefPath = "F:/prjDocument/2011/invote/errorBef.txt";
		String errAftPath = "F:/prjDocument/2011/invote/errorAft.txt";
		
		//�������ļ�·��
		String errBefResPath = "F:/prjDocument/2011/invote/errBefRes.txt";
		String errAftResPath = "F:/prjDocument/2011/invote/errAftRes.txt";
		try {

			//ǰ2�ĵ���
			List<String> befNums = getInvoteNum(errBefPath);
			//������ֵĴ�����
			select_Before(befNums);
			//��������浽�ļ���

			Writefile(befResults, errBefResPath);
			System.out.println(" ǰ2ͳ�ƽ���---------------------------> ");

			//��2�ĵ���
			List<String> aftNums = getInvoteNum(errAftPath);
			//������ֵĴ�����
			select_After(aftNums);
			//��������浽�ļ���

			Writefile(aftResults, errAftResPath);
			System.out.println(" ǰ2ͳ�ƽ���---------------------------> ");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				writeLine = "���룺-->" + temp[0] + "--����-->" + temp[1];
				bw.newLine();
				bw.write(writeLine);

			}

			bw.flush();
			fw.close();
		} catch (IOException e) {

		}

	}

	private static void select_Before(List<String> befNums) {

		String everyNum = "";
		String tempNum = "";//��ʱ������
		boolean isGet = false;
		for (int j = 0; j < befNums.size(); j++) {
			everyNum = befNums.get(j);

			if (!isGet) {
				befGetNums.add(everyNum);
				isGet = true;
				continue;
			}
			//ȡ�����ظ�����
			if (befGetNums != null && !befGetNums.contains(everyNum)) {
				befGetNums.add(everyNum);
			}

		}

		String tmp[] = new String[2];
		//�ٽ����ж�
		for (int j = 0; j < befGetNums.size(); j++) {
			everyNum = befGetNums.get(j);
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
			befResults.add(tmp);
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
				if (!"".equals(row) && row.length() == 2) {

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
		//String fileName = "20110816";
		ReadData("");
	}
}
