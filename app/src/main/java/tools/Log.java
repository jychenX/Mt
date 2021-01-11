package tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	private static String tag = "--CJY_QQ--- ";
	private static Log log;

	public static void show(String log){
		System.out.println("--ThreadId：" + Thread.currentThread().getId() + "--" + log);
	}

	public static void showQQ(String log){
		System.out.println(tag + Thread.currentThread().getId() + "--" + log);
	}

	public static void showStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.close();
		try {
			sw.close();
		} catch (IOException e1) {
			//ignore
		}
//		show("异常信息:" + e.getMessage());
		show("异常信息：" + sw.toString());
	}


	private static String getTimeStamp(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		String timaStamp = simpleDateFormat.format(new Date(System.currentTimeMillis()));
		return timaStamp;
	}

	public static void showDebugStackTrace(String tag){
		RuntimeException here = new RuntimeException("System.out " + tag);
		here.fillInStackTrace().printStackTrace();
	}
}
