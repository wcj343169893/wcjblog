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
 * 前后2统计
 * 
 * 功能：取出一段时期的数据,统计出现次数,并根据条件生成注码
 */
public class TxtReader_BT2_Invote2 {


	// 取出判断的数
	private static List<String[]> befResults = new ArrayList();

	// 取出判断的数
	private static List<String[]> aftResults = new ArrayList();
	
	// 取出判断资源的数
	private static List<String> JudgeSourch = new ArrayList();
	
	private final static int AppearNum = 1450;
	
	// 调查结果的数
	private static List<String> befGetNums = new ArrayList();
	
	// 调查结果的数
	private static List<String> aftGetNums = new ArrayList();

	/**
	 * 读取数据
	 * 
	 * @param fileName
	 */
	public static void ReadData(String fileName) {
		
		//需要调查的时间段的文件路径	
		String invotePath = "F:/prjDocument/2011/invote/From/"+fileName + ".txt";
		
		//取出需要调查的资源的方法文件
		String resourcePath = "F:/prjDocument/2011/invote/From/Betting_100.txt";
				
		String beforeResPath = "F:/prjDocument/2011/invote/To/beforeRes_100.txt";
		
		String aftResPath = "F:/prjDocument/2011/invote/To/afterRes_100.txt";
		
		String time = getTdate();
		
		//保存调查结果的投注文件
		String befInvoteResPath = "F:/prjDocument/2011/invote/To/befInvoteRes_"+ time +".txt";
		
		String aftInvoteResPath = "F:/prjDocument/2011/invote/To/aftInvoteRes_"+ time +".txt";
		
		
		try {
			JudgeSourch = getInvoteNum(resourcePath);
			
			//取出所有时间段的数
			List<String> allNums = getInvoteTimeNum(invotePath);
			
			//前2的调查			
			//查出出现的次数及
			select_Before(allNums);
			
			//写入文件
			Integer count = Writefile(befResults, beforeResPath ,1);
			
			//把结果调查的数作为资料码保存为文件
			WriteToSourshFile(befGetNums, befInvoteResPath);
			
			System.out.println(" 前2统计结束--------------------------->需要的共："+count+"条 ");
			
			//后2的调查
			
			//查出出现的次数及
			select_After(allNums);
			//将结果保存到文件中

			count = Writefile(aftResults, aftResPath,2);
			
			//把结果调查的数作为资料码保存为文件
			WriteToSourshFile(aftGetNums, aftInvoteResPath);
			
			System.out.println(" 后2统计结束--------------------------->需要的共："+count+"条 ");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getTdate() {
		return new SimpleDateFormat("yyyy-MM-dd_HHMMSS").format(new Date());
	}
	
	// 在txt文件后追加内容
	private static void WriteToSourshFile(List<String> sb, String filePath)
			throws FileNotFoundException, IOException {
		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			//String temp;
			String writeLine;
			for (int i = 0; i < sb.size(); i++) {			
				writeLine = sb.get(i);
				//writeLine = "号码：-->" + temp[0] + "--出现-->" + temp[1];
				bw.newLine();
				bw.write(writeLine);

			}

			bw.flush();
			fw.close();
		} catch (IOException e) {

		}

	}

	// 在txt文件后追加内容
	private static Integer Writefile(List<String[]> sb, String filePath,Integer type)
			throws FileNotFoundException, IOException {
		Integer count = 0;
		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			String[] temp;
			String writeLine;
			//写入文件计数器
			
			for (int i = 0; i < sb.size(); i++) {
				temp = new String[2];
				temp = sb.get(i);
				//出现大于
					writeLine = "号码：-->" + temp[0] + "--出现-->" + temp[1];
					bw.newLine();
					bw.write(writeLine);
					//取出需要的条数
					if(Integer.valueOf(temp[1]) > AppearNum){					
						count ++;
						//保存号码
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
		String tempNum = "";//临时交换数
		String tmp[] = new String[2];
		//再进来判断
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
		String tempNum = "";//临时交换数
		String tmp[] = new String[2];
		//再进来判断
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

	//取得需要调查的数字
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

	//取得需要调查的时间段文件的数字
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
