package cn.edu.zstu.tools;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateTools {
	public static String getFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(date)+".jpg";
	}
	
	
}
