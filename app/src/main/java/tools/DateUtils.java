package tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	private static DateUtils dateUtils;
	private int MIN_CLICK_TIME = 0;
	private long lastClickTime;

	public static synchronized DateUtils getInstance(){
		if(dateUtils == null){
			dateUtils = new DateUtils();
		}
		return dateUtils;
	}

	/**
	 * 时间戳转成"yyyy-MM-dd-HH:mm:ss"
	 * @return String
	 */
	public static String longToDate(long time){
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date date = new Date(time);
		res = simpleDateFormat.format(date);
		return res;
	}
}
