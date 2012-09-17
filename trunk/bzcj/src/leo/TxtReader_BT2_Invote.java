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
 * 前后2统计
 * 功能：调试出错误最大的号码
 * 3:最多错35次
 */
public class TxtReader_BT2_Invote {

	// 取出前2判断的数,是不重复的
	private static List<String> befGetNums = new ArrayList();

	// 取出判断的数
	private static List<String[]> befResults = new ArrayList();

	// 取出后2判断的数,是不重复的
	private static List<String> aftGetNums = new ArrayList();

	// 取出判断的数
	private static List<String[]> aftResults = new ArrayList();

	/**
	 * 读取数据
	 * 
	 * @param fileName
	 */
	public static void ReadData(String fileName) {

		//错误文件路径
		String errBefPath = "F:/prjDocument/2011/invote/errorBef.txt";
		String errAftPath = "F:/prjDocument/2011/invote/errorAft.txt";
		
		//调查结果文件路径
		String errBefResPath = "F:/prjDocument/2011/invote/errBefRes.txt";
		String errAftResPath = "F:/prjDocument/2011/invote/errAftRes.txt";
		try {

			//前2的调查
			List<String> befNums = getInvoteNum(errBefPath);
			//查出出现的次数及
			select_Before(befNums);
			//将结果保存到文件中

			Writefile(befResults, errBefResPath);
			System.out.println(" 前2统计结束---------------------------> ");

			//后2的调查
			List<String> aftNums = getInvoteNum(errAftPath);
			//查出出现的次数及
			select_After(aftNums);
			//将结果保存到文件中

			Writefile(aftResults, errAftResPath);
			System.out.println(" 前2统计结束---------------------------> ");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 在txt文件后追加内容
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
				writeLine = "号码：-->" + temp[0] + "--出现-->" + temp[1];
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
		String tempNum = "";//临时交换数
		boolean isGet = false;
		for (int j = 0; j < befNums.size(); j++) {
			everyNum = befNums.get(j);

			if (!isGet) {
				befGetNums.add(everyNum);
				isGet = true;
				continue;
			}
			//取出不重复的数
			if (befGetNums != null && !befGetNums.contains(everyNum)) {
				befGetNums.add(everyNum);
			}

		}

		String tmp[] = new String[2];
		//再进来判断
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
		String tempNum = "";//临时交换数
		boolean isGet = false;
		for (int j = 0; j < befNums.size(); j++) {
			everyNum = befNums.get(j);

			if (!isGet) {
				aftGetNums.add(everyNum);
				isGet = true;
				continue;
			}
			//取出不重复的数
			if (aftGetNums != null && !aftGetNums.contains(everyNum)) {
				aftGetNums.add(everyNum);
			}

		}

		String tmp[] = new String[2];
		//再进来判断
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

	//取得需要调查的数字
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
