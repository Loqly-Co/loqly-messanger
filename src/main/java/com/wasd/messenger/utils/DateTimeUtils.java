package com.wasd.messenger.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static final BigDecimal DAYS_IN_YEAR = BigDecimal.valueOf(365);

	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static LocalDate parseDate(String date) {
		date = date.trim().replace(" ", "").replace("/", "-").replace(".", "-");
		return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
	}
}
