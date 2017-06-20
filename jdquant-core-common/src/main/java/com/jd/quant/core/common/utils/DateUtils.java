package com.jd.quant.core.common.utils;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    //SimpleDateFormat非线程安全,多线程环境下会有问题，请注意。
    //private static final SimpleDateFormat DATE_PATTERN = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    //private static final SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
    //private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //private static final SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
    //private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static SimpleDateFormat dateFormat(String match) {
        return new SimpleDateFormat(match);
    }

    private static final ZoneId zone = ZoneId.systemDefault();

    public static Date addDay(String date, int day) {
        Date d = transString2Date(date);
        return addDay(d, day);
    }

    public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return gc;
    }

    public static Date parseDay(String date) {
        return transString2Date(date);
    }

    public static Date parseDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return Date.from(dateTime.atZone(zone).toInstant());
    }

    public static LocalDateTime parseLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), zone);
    }

    /**
     * Date转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate parseLocalDate(Date date) {
        return parseLocalDateTime(date).toLocalDate();
    }

    /**
     * LocalDate转Date
     *
     * @param localDate
     * @return
     */
    public static Date parseDate(LocalDate localDate) {
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.of(00, 00));
        return parseDate(localDateTime);
    }

    /**
     * method: DateUtil  formatDate
     *
     * @param date
     * @param format
     * @return String
     */
    public static String formatDate(Date date, String format) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat myFormatter = new SimpleDateFormat(format);
        return myFormatter.format(date);
    }

    /**
     * 用于VM页面上格式时间
     * method: DateUtil  format
     *
     * @param date
     * @param format
     * @return String
     * 创建日期： 2012-10-8
     * Copyright(C) 2012, by YJH.
     */
    public static String format(Date date, String format) {
        if (date != null) {
            SimpleDateFormat myFormatter = new SimpleDateFormat(format);
            return myFormatter.format(date);
        }
        return null;
    }

    /**
     * 根据日期获取当前计算机时间
     *
     * @param dateFormatString
     * @return
     */
    public static Long getLongDate(String dateFormatString) {
        Date resultDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (dateFormatString != null && !"".equals(dateFormatString)) {
                resultDate = dateFormat.parse(dateFormatString);
            }
            return resultDate.getTime();
        } catch (ParseException e) {
            LOGGER.error("exception: ", e);
        }
        return null;
    }

    public static String getCurrentMonthAsString() {
        Calendar c = Calendar.getInstance();
        return new SimpleDateFormat("yyyy-MM").format(c.getTime());
    }

    public static String getCurrentDayAsString() {
        Calendar c = Calendar.getInstance();
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    public static Date getCurrentDay() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    public static Date addDay(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(6, day);
        return c.getTime();
    }

    public static boolean isWeekday(Date date, int weekday) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int current = c.get(7);
        return current == weekday;
    }

    public static boolean isSunday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int current = c.get(Calendar.DAY_OF_WEEK);
        return current == 1;
    }

    public static boolean isSaturday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int current = c.get(Calendar.DAY_OF_WEEK);
        return current == 7;
    }

    public static String transDate2String(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    /**
     * 格式：返回的是20151231这种格式
     *
     * @param date
     * @return
     */
    public static String transDay22String(Date date) {
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }

    public static Date transString2Date(String s) {
        try {
            if (s.length() == 7)
                return new SimpleDateFormat("yyyy-MM").parse(s);
            if (s.length() == 8)
                return new SimpleDateFormat("yyyyMMdd").parse(s);
            if (s.length() == 10)
                return new SimpleDateFormat("yyyy-MM-dd").parse(s);
            if (s.length() == 19)
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
        } catch (Exception e) {
            LOGGER.error("日期转换错误: ", e);
        }
        return null;
    }

    public static Date addMonth(Date date, int months) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(2, months);
        return c.getTime();
    }

    public static int getBetweenMonths(Date start, Date end) {
        Calendar c = Calendar.getInstance();
        c.setTime(end);
        int year1 = c.get(1);
        int month1 = c.get(2);
        c.setTime(start);
        int year2 = c.get(1);
        int month2 = c.get(2);
        int result;
        if (year1 == year2)
            result = month1 - month2;
        else
            result = (12 * (year1 - year2) + month1) - month2;
        return result;
    }

    public static int getBetweenDays(Date start, Date end) {
        return (int) ((toDayBegin(end).getTime() - toDayBegin(start).getTime()) / 0x5265c00L);
    }

    public static String toLocalDateString(Date date) {
        if (date == null) {
            return null;
        } else {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), zone);
            return localDateTime.toLocalDate().toString();
        }
    }

    public static Date getWeekendDay(Calendar calendar) {
        int day = calendar.get(7);
        Date calDate;
        if (day == 1)
            calDate = org.apache.commons.lang3.time.DateUtils.addDays(calendar.getTime(), -2);
        else if (day == 7)
            calDate = org.apache.commons.lang3.time.DateUtils.addDays(calendar.getTime(), -1);
        else
            calDate = calendar.getTime();
        return calDate;
    }

    public static Date toDayBegin(Date date) {
        return new DateTime(date.getTime()).withTimeAtStartOfDay().toDate();
    }

    public static Date toMarketBegin(Date date) {
        return new DateTime(date.getTime()).withTimeAtStartOfDay().withHourOfDay(9).withMinuteOfHour(30).toDate();
    }

    public static Date beforeMarketBegin(Date date) {
        return new DateTime(date.getTime()).withTimeAtStartOfDay().withHourOfDay(9).withMinuteOfHour(30).toDate();
    }

    public static Date toMarketEnd(Date date) {
        return new DateTime(date.getTime()).withTimeAtStartOfDay().withHourOfDay(15).withMinuteOfHour(0).toDate();
    }

    public static Date addMinutes(Date date, int minutes) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, minutes);
        return c.getTime();
    }

    public static Date addSeconds(Date date, int seconds) {
        return new DateTime(date).plusSeconds(seconds).toDate();
    }

    public static Date toDayEnd(Date date) {
        return new DateTime(date.getTime()).withTime(23, 59, 59, 0).toDate();
    }

    public static Date toMinuteBegin(Date date) {
        return new DateTime(date.getTime()).withSecondOfMinute(0).withMillisOfSecond(0).toDate();
    }

    public static boolean isSameDay(Date dateA, Date dateB) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(dateA);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(dateB);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }

    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 格式化化LocalDateTime
     *
     * @param localDateTime
     * @param formatStyle
     * @return
     */
    public static String formaterLocalDateTime(LocalDateTime localDateTime, String formatStyle) {
        return DateTimeFormatter.ofPattern(formatStyle).format(localDateTime);
    }

    public static boolean isFirstDayOfMonth(Date date) {
        return date != null && new DateTime(date).getDayOfMonth() == 1;
    }
}