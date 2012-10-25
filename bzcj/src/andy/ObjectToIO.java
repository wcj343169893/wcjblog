package andy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ObjectToIO

{

	/**
	 * 
	 * 
	 * 
	 * 对象转Byte数组
	 * 
	 * 
	 * 
	 * @param obj
	 * 
	 * 
	 * 
	 * @return
	 * 
	 * 
	 * 
	 * @throws Exception
	 * 
	 * 
	 */

	public static byte[] objectToBytes(Object obj) throws Exception

	{

		// logger.debug("objectToString called ");

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		ObjectOutputStream sOut = new ObjectOutputStream(out);

		sOut.writeObject(obj);

		sOut.flush();

		byte[] bytes = out.toByteArray();

		// logger.debug(bytes.toString());

		return bytes;

	}

	/**
	 * 
	 * 
	 * 
	 * 字节数组转对象
	 * 
	 * @param content
	 * 
	 * 
	 * 
	 * @return
	 * 
	 * 
	 * 
	 * @throws Exception
	 * 
	 * 
	 */

	public static Object bytesToObject(byte[] bytes) throws Exception

	{

		// logger.debug("bytesToObject called ");

		// byte转object

		ByteArrayInputStream in = new ByteArrayInputStream(bytes);

		ObjectInputStream sIn = new ObjectInputStream(in);

		return sIn.readObject();

	}

	public static void main(String[] args) {
		Map<String, String> map= new HashMap<String, String>();
		map.put("title", "这是标题1");
		map.put("title2", "这是标题2");
		map.put("title3", "这是标题3");
		map.put("title4", "这是标题4");
		try {
			byte[] b =ObjectToIO.objectToBytes(map);
			System.out.println(b);
			String str= new String(b);
			System.out.println(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}