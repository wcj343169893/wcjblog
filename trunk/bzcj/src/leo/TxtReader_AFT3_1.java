package leo;

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

import sun.util.logging.resources.logging;

/**
 * @author 
 * 
 * ǰ��2ͳ��
 * 
 * ���ܣ�ȡ��һ��ʱ�ڵ����,ͳ�Ƴ��ִ���,�����������ע��
 */
public class TxtReader_AFT3_1 {

	// ǰ��ļ�����-�Ե�
	private static int beforCount;
	
	// ǰ��ļ�����-���
	private static int beforCount_;
	
	// ����ļ�����-�Ե�
	private static int afterCount;
	
	// ����ļ�����-���
	private static int afterCount_;
	//���õ���״̬Ϊ��ȷ��
	private static boolean isHasRight = true;
	
	//�������l����
	private static int maxErrNum =0;
	
	
	private static int maxErrNumTep =0;
	
	// ȡ���ж���Դ����
	private static List<String> JudgeSourch = new ArrayList();
	
	private final static int AppearNum = 1450;

	// ����ļ�����-���жԵ�
	private static int afterCountAll;

	// ����ļ�����-���д��
	private static int afterCountAll_;
	
	private static String afterJudgeNum = "";
	/**
	 * ��ȡ���
	 * 
	 * @param fileName
	 */
	public static void ReadData(String fileName) {
		
		//��Ҫ����ʱ��ε��ļ�·��	
		String invotePath = "F:/prjDocument/2011/invote/From/"+fileName + ".txt";
		
		//ȡ����Ҫ������Դ�ķ����ļ�
		String resourcePath = "F:/prjDocument/2011/invote/From/aft3.txt";
				
		String beforeResPath = "F:/prjDocument/2011/invote/To/beforeRes_100.txt";
		
		String aftResPath = "F:/prjDocument/2011/invote/To/afterRes_100.txt";
		
		String time = getTdate();
		
		//���������Ͷע�ļ�
		String befInvoteResPath = "F:/prjDocument/2011/invote/To/befInvoteRes_"+ time +".txt";
		
		String aftInvoteResPath = "F:/prjDocument/2011/invote/To/aftInvoteRes_"+ time +".txt";
		
		
		try {
		//	JudgeSourch = getInvoteNum(resourcePath);
			JudgeSourch = getInvoteNum(invotePath);
			
			//2:ͳ��
			
			
			 String[] everyDayDate = new String[3];
			String row;
			
			System.out.println("----------------------------------------------------" );
		
			System.out.println( " ---------------------------------------------------> ");
			//����֤����
		
			for (int j = 0; j < JudgeSourch.size(); j++) {
				row = JudgeSourch.get(j);
			
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
				
				//��2�ο�ʼ����
				String getafter = num.substring(2, 5);
				
				//�жϺ���ĶԴ�
				selectJudge_after(num,getafter);
				
				System.out.println("setting  num is ----->" + afterJudgeNum);
				
				if(isHasRight){	

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
					everyDayFind = row.split("-120");
				}
				
				//�ֳ�ÿ���
				if(everyDayFind != null && !"".equals(everyDayFind) && everyDayFind.length > 1){
					everyDayDate = new String[3];
					everyDayDate[0] = everyDayFind[0];
					
					//aftAllDayResults.add(everyDayDate);
					//System.out.println("��3--> "+everyDayDate[0]+"-->�ԣ�" + afterCount +" ����"+afterCount_ );
					System.out.println(everyDayDate[0] + "��   -->���l���ǣ�最大错数-->" + maxErrNumTep +"��");
					
					/*if(maxErrNumTep == 5){
						System.out.println("��3-->���l���ǣ�" + maxErrNumTep +"��");
					}*/
					
					if(compare_date(pattens[0],"20091214")) {
						everyDayFind = row.split("-01");
						
						if(afterCount_<20){
							System.out.println("��3--> "+"  ����"+afterCount_ );
						}
					} else {					
						everyDayFind = row.split("-001");
						if(afterCount_<32){
							System.out.println("��3--> "+"  ����"+afterCount_ );
						}
					}
					
					afterCountAll += afterCount;
					afterCountAll_ += afterCount_;
					afterCount = 0;
					afterCount_ = 0;
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//�ж�ǰ��Դ�
	private static void selectJudge_before(String selectNum) {
		boolean isRight = false;
		
		for(int j=0 ;j< JudgeSourch.size();j++){
			String sigNum = JudgeSourch.get(j);
			if(sigNum.equals(selectNum)){
				isRight = true;//��һ��;ͱ����ȷ��
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
	
	//�жϺ���Դ�
	private static void selectJudge_after(String num,String selectNum) {

		boolean isRight = false;		
		
		/*for(int j=0 ;j< JudgeSourch.size();j++){
			String sigNum = JudgeSourch.get(j);
			if(sigNum.equals(selectNum)){
				isRight = true;//��һ��;ͱ����ȷ��
				break;
			} 					
			
		}*/
		//System.out.println("  num is ----->" + num);
		for (int i = 0; i < selectNum.length(); i++) {
			String temp = String.valueOf(selectNum.charAt(i));
			if(afterJudgeNum.contains(temp)){
				isRight = true;//��һ��;ͱȷ��
				break;
			} 
		}
		
		if(isHasRight){
			settingJudgeNum(num);
		}	
		
		
		if(isRight){
			afterCount ++ ;
			isHasRight = true; 
		} else {
			afterCount_ ++;
			isHasRight = false; 
		}
		
	}

	private static void settingJudgeNum(String num) {
		//首先判断单数多,还是双数多
		String num1 = String.valueOf(num.charAt(0));
		String num2 = String.valueOf(num.charAt(1));
		String num3 = String.valueOf(num.charAt(2));
		String num4 = String.valueOf(num.charAt(3));
		String num5 = String.valueOf(num.charAt(4));
		//计数双数
		int isShanSou = 0 ;
		//计数大数
		int isDaSou = 0 ;
		//大数
		StringBuffer DaSou = new StringBuffer();
		//小数
		StringBuffer xiaoSou = new StringBuffer();
		//判断双数		
		if("56789".contains(num1)){
			isDaSou++;
			DaSou.append(num1);
		} else {
			xiaoSou.append(num1);
		} 
		
		if("56789".contains(num2)){
			isDaSou++;
			DaSou.append(num2);
		} else {
			xiaoSou.append(num2);
		} 
		
		if("56789".contains(num3)){
			isDaSou++;
			DaSou.append(num3);
		} else {
			xiaoSou.append(num3);
		} 
		
		if("56789".contains(num4)){
			isDaSou++;
			DaSou.append(num4);
		} else {
			xiaoSou.append(num4);
		} 
		
		if("56789".contains(num5)){
			isDaSou++;
			DaSou.append(num5);
		} else {
			xiaoSou.append(num5);
		} 
		
		//双数
		StringBuffer shanSou = new StringBuffer();
		//单数
		StringBuffer singleSou = new StringBuffer();
		//判断双数		
		if("02468".contains(num1)){
			isShanSou++;
			shanSou.append(num1);
		} else {
			singleSou.append(num1);
		} 
		
		if("02468".contains(num2)){
			isShanSou++;
			shanSou.append(num2);
		} else {
			singleSou.append(num2);
		} 
		
		if("02468".contains(num3)){
			isShanSou++;
			shanSou.append(num3);
		} else {
			singleSou.append(num3);
		} 
		
		if("02468".contains(num4)){
			isShanSou++;
			shanSou.append(num4);
		} else {
			singleSou.append(num4);
		} 
		
		if("02468".contains(num5)){
			isShanSou++;
			shanSou.append(num5);
		} else {
			singleSou.append(num5);
		} 
		//双数大于2个时,取单数
		//System.out.println("num is ----->" + num);
		if(isShanSou > 2) {
			//要取单数
			String single1 = "";
			String single2 = "";
			if(singleSou.toString().length() == 2){
				
				 single1 = String.valueOf(singleSou.toString().charAt(0));
				 single2 = String.valueOf(singleSou.toString().charAt(1));
			} else if(singleSou.toString().length() == 1){
				single1 = String.valueOf(singleSou.toString().charAt(0));
			} 
			
			//相等时,复杂处理
			if(singleSou.toString().length() == 2) {
				if( single1.equals(single2)){
					//直接替换掉1个
					if(isDaSou > 2){
						afterJudgeNum = "13579".replaceAll(single1, "").substring(0, 3);					
					} else {
						afterJudgeNum = "13579".replaceAll(single1, "").substring(1, 4);
					}
					
				} else {
					//直接替换掉
					afterJudgeNum = "13579".replaceAll(single1, "").replaceAll(single2, "");
				}
			}
			
			if(singleSou.toString().length() == 1) {
				
				if(isDaSou > 2){
					afterJudgeNum = "13579".replaceAll(single1, "").substring(0, 3);					
				} else {
					afterJudgeNum = "13579".replaceAll(single1, "").substring(1, 4);
				}				
			}
			
			if(singleSou.toString().length() == 0) {
				
				if(isDaSou > 2){
					afterJudgeNum = "13579".substring(0, 3);					
				} else {
					afterJudgeNum = "13579".substring(2, 5);
				}				
			}
			
		} else {
			

			//要取双数
			String single1 = "";
			String single2 = "";
			if(shanSou.toString().length() == 2){
				
				 single1 = String.valueOf(shanSou.toString().charAt(0));
				 single2 = String.valueOf(shanSou.toString().charAt(1));
			} else if(singleSou.toString().length() == 1){
				single1 = String.valueOf(shanSou.toString().charAt(0));
			} 
			
			//相等时,复杂处理
			if(shanSou.toString().length() == 2) {
				if( single1.equals(single2)){
					//直接替换掉1个
					if(isDaSou > 2){
						afterJudgeNum = "02468".replaceAll(single1, "").substring(0, 3);					
					} else {
						afterJudgeNum = "02468".replaceAll(single1, "").substring(1, 4);
					}
					
				} else {
					//直接替换掉
					afterJudgeNum = "02468".replaceAll(single1, "").replaceAll(single2, "");
				}
			}
			
			if(shanSou.toString().length() == 1) {
				
				if(isDaSou > 2){
					afterJudgeNum = "02468".replaceAll(single1, "").substring(0, 3);					
				} else {
					afterJudgeNum = "02468".replaceAll(single1, "").substring(1, 4);
				}				
			}
			
			if(shanSou.toString().length() == 0) {
				
				if(isDaSou > 2){
					afterJudgeNum = "02468".substring(0, 3);					
				} else {
					afterJudgeNum = "02468".substring(2, 5);
				}				
			}
			
		
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
							//befGetNums.add(temp[0]);
						} else {
						//	aftGetNums.add(temp[0]);
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
		//�ٽ�4�ж�
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
			//befResults.add(tmp);
		}

		//System.out.println(befResults.toString());

	}

	private static void select_After(List<String> allNums) {

		String everyNum = "";
		String tempNum = "";//��ʱ������
		String tmp[] = new String[2];
		//�ٽ�4�ж�
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
			//aftResults.add(tmp);
		}

		//System.out.println(befResults.toString());

	}

	//ȡ����Ҫ��������
	private static List<String> getInvoteNum(String filePath) {
		List<String> nums = new ArrayList();
		FileReader read;
		try {
			read = new FileReader(filePath);
			String row;
			BufferedReader br = new BufferedReader(read);
			while ((row = br.readLine()) != null) {
				if (!"".equals(row)) {
					//String[] getNums = row.split(" ");
					//for(int i =0; i<getNums.length; i++){
						
					//	nums.add(getNums[i]);
					//}
					nums.add(row);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//反过来取一次 从小到大
		List<String> numsReserve = new ArrayList();
		for (int i = nums.size() -1 ; i > 0; i--) {
			numsReserve.add(nums.get(i));
		}
		return numsReserve;

	}

	//ȡ����Ҫ����ʱ����ļ�������
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
	
	public static void main(String[] args) {
		String fileName = "20110822-20110816";
		//String fileName = "all_resource_A";
		ReadData(fileName);
	}
}
