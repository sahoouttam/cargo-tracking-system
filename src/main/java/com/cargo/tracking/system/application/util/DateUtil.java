package com.cargo.tracking.system.application.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public DateUtil() {
    }

    public static Date toDate(String date) {
        return toDate(date, "00:00.00.000");
    }

    public static Date toDate(String date, String time) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date + " " + time);
        } catch (ParseException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static String getDateFromDateTime(String dateTime) {
        return dateTime.substring(0, dateTime.indexOf(" "));
    }

    public static String getTimeFromDateTime(String dateTime) {
        return dateTime.substring(dateTime.indexOf(" ") + 1);
    }

    public static long computeDuration(Date endDate) {
        Date today = trim(new Date());
        long diff = endDate.getTime() - today.getTime();
        return diff / (24 * 60 * 60 * 1000);
    }

    public static Date trim(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        return calendar.getTime();
    }
}
