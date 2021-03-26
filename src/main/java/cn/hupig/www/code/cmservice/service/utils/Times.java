package cn.hupig.www.code.cmservice.service.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class Times {

	/**
	 * 获取当前时间的时间戳/UTC时间
	 * 使用Instant.now().atZone(ZoneId.systemDefault())
	 * 可以获取当前时区的时间
	 * @return Instant you need
	 */
	public static Instant getInstant() {
		return Instant.now();
	}

	/**
	 * 获取若干小时之后的时间戳
	 * @param Integer need hour.
	 * @return Instant you need
	 */
	public static Instant getHourInstant(Integer hour) {
		return Instant.now().plusMillis(TimeUnit.HOURS.toMillis(hour));
	}

	/**
	 * 判断当前时间是否在时间段之内
	 * @param start 开始时间， end 结束时间
	 * @return 当前时间是否在这个时间之内
	 */
	public static boolean timeSlot (Instant start,Instant end) {
		Instant time = getInstant();
		Long endNumber = ChronoUnit.SECONDS.between(time , end);
		Long startNumber = ChronoUnit.SECONDS.between(start , time);
		return endNumber > 0 && startNumber > 0;
	}
	
}
