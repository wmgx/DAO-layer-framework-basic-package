package top.wmgx.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class TimeUtils {
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getNow() {
		return LocalDateTime.now().toString();
	}
	
	public static Date toDate(LocalDateTime t) {
		return  Date.from(t.atZone(ZoneId.systemDefault()).toInstant());
	}
	public static Date toDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
	}
	public static LocalDate toLocalDate(Date date) {
		return date.toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDate();
	}
}
