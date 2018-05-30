package cn.yang.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SystemLogHelper {

	/**
	 * @param ex
	 * @return 异常的详细错误信息, {@link Exception#printStackTrace()}
	 */
	public static String getDetailMessage(Exception ex) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(byteArrayOutputStream);
		ex.printStackTrace(printStream);
		return byteArrayOutputStream.toString();
	}

}
