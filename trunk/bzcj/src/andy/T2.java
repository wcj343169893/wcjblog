package andy;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class T2 {
	public static void main(String[] args) {
		try {
			FileInputStream f = new FileInputStream(
					"E:/kid/template/Layout/home_horizontal.json");
			DataInputStream in = new DataInputStream(f);
			 BufferedReader br = new BufferedReader(new InputStreamReader(f));
			while (br.ready())
			 System.out.println(br.readLine());
//				System.out.println(in.readLine());
			/**
			 * DataInputStream.readLine()已经过时 使用 DataInputStream
			 * 类读取文本行的程序可以改为使用BufferedReader类 改用BufferedReader.readLine()方法
			 * 只要将以下形式的代码：DataInputStream d = new DataInputStream(in);
			 * 替换为：BufferedReader d = new BufferedReader(new
			 * InputStreamReader(in));
			 */
			String str="DataInputStream.readLine()已经过时 使用 DataInputStream";
			File file=new File("E:/kid/test.txt");
			FileOutputStream os= new FileOutputStream(file);
			os.write(str.getBytes());
			os.flush();
			os.close();
			in.close();
		} catch (Exception e) {
			System.err.println("File input error!");
		}
	}
	private void writeFile(String path, String str) {
		try {
			File file=new File(path);
			FileOutputStream os= new FileOutputStream(file);
			os.write(str.getBytes());
			os.flush();
			os.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
