package andy;

public class fzwb {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		System.setProperty("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime",
				"true");
		System.setProperty("org.apache.commons.logging"
				+ ".simplelog.log.org.apache.commons.httpclient", "error");

		String rq = "2011-07/28";
		//String rq = "";

		//�ֶ���������
		if (args.length > 0) {
			if (!"".equals(args[0]))
				rq = args[0];
		}
		//�ֶ���������		
		dptoolsForWindows.getFzwb(rq);
		//dptools.getFzwb(rq);
	}

}
