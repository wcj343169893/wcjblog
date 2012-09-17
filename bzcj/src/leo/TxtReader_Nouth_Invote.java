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
 * 次数与时间统计
 * 功能：次数与时间统计
 * 
 */
public class TxtReader_Nouth_Invote {

	// 取出前2判断的数,是不重复的
	private static List<String> befGetNums = new ArrayList();
	
	//	 取出原来的数
	private static List<String> getNums = new ArrayList();


	// 取出判断的数
	private static List<String[]> befResults = new ArrayList();

	// 取出后2判断的数,是不重复的
	private static List<String> aftGetNums = new ArrayList();

	// 取出判断的数
	private static List<String[]> aftResults = new ArrayList();
	
	//重复的数
	private static List<String[]> recounts = new ArrayList();

	/**
	 * 读取数据
	 * 
	 * @param fileName
	 */
	public static void ReadData(String fileName) {

		String filePath = "F:/prjDocument/2011/invote/From/"+fileName + ".txt"; 
		
		String refilePath = "F:/prjDocument/2011/invote/To/"+fileName + "_ob.txt"; 
		
		String reSultPath = "F:/prjDocument/2011/invote/To/"+fileName + "_Resut.txt"; 
		
		String reSultPath_re = "F:/prjDocument/2011/invote/To/"+fileName + "_Resut_re.txt"; 
		
		//1:先把顺序反过来
					 
		 try {
			//reverfile(filePath, refilePath);
				//前2的调查
				List<String> befNums = getInvoteNum(filePath);
				
				//查出出现的次数及
				select_Before(befNums);
				//将结果保存到文件中
				System.out.println(" ----------------------开始生成结果文件---------------------------> ");
				Writefile(befResults, reSultPath);
				System.out.println("---------------------- 前2统计结束---------------------------> ");

				//重复数文件保存				
				Writefile(recounts, reSultPath_re);
				
				//找出个从来没出现过的数
				
			
		}  catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}
	

	private static void reverfile(String filePath, String refilePath)
			throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));//要确定根目录下有test.txt文件且有内容
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
				writeLine = "号码：-->" + temp[0] + "--出现---->" + temp[1];
				//用来保存重复的结果
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

	
	private static void select_Before(List<String> befNums) {

		String everyNum = "";
		String tempNum = "";//临时交换数
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
			
			
			String[] nums = row.split("：");
			if(nums == null || nums.length < 2 ){
				 nums = row.split("\t");
			}
		
			everyNum = nums[1];
			
			
			getNums.add(everyNum);
			
			//取出不重复的数
			if (befGetNums != null && !befGetNums.contains(everyNum)) {
				befGetNums.add(everyNum);
			} else {
				reCount++;
				
			}

		}

		String tmp[] = new String[2];
		//再进来判断
		for (int j = 0; j < befGetNums.size(); j++) {
			everyNum = befGetNums.get(j);
			tmp = new String[2];
			int count = 0;
			for (int i = 0; i < getNums.size(); i++) {
				tempNum = getNums.get(i);
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
		ReadData(fileName);
	}
}
