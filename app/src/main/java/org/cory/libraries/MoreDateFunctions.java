package org.cory.libraries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Library of functions related to calculation and formatting of dates.
 * @author Cory Ma
 */
public final class MoreDateFunctions
{
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd", Locale.US);
    private final static SimpleDateFormat slashDateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);
    private final static SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.US);
    private final static SimpleDateFormat fmTimestampFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.US);
    private final static SimpleDateFormat fileTimestampFormat = new SimpleDateFormat("yyMMdd_kkmm", Locale.US);


    /**
     * Takes the difference of two Dates and displays the result in days.
     * @param subtrFromDate - Date to subtract from
     * @param subtrWithDate - Date to subtract with
     * @return double
     */
    public static double timeDiffInDays(Date subtrFromDate, Date subtrWithDate)
    {
        return (timeDiffInHours(subtrFromDate, subtrWithDate) / 24);
    }

    /**
     * Takes the difference of two Dates and displays the result in hours.
     * @param subtrFromDate - Date to subtract from
     * @param subtrWithDate - Date to subtract with
     * @return double
     */
    public static double timeDiffInHours(Date subtrFromDate, Date subtrWithDate)
    {
        return (timeDiffInMinutes(subtrFromDate, subtrWithDate) / 60);
    }

    /**
     * Takes the difference of two Dates and displays the result in minutes.
     * @param subtrFromDate - Date to subtract from
     * @param subtrWithDate - Date to subtract with
     * @return double
     */
    public static double timeDiffInMinutes(Date subtrFromDate, Date subtrWithDate)
    {
        return (timeDiffInSeconds(subtrFromDate, subtrWithDate) / 60);
    }

    /**
     * Takes the difference of two Dates and displays the result in seconds.
     * @param subtrFromDate - Date to subtract from
     * @param subtrWithDate - Date to subtract with
     * @return double
     */
    public static double timeDiffInSeconds(Date subtrFromDate, Date subtrWithDate)
    {
        return ((double)(subtrFromDate.getTime() - subtrWithDate.getTime()) / 1000);
    }

    /**
     * Rounds the time down to the nearest quarter hour.
     * @param roundDate - Time to round down
     * @return Date
     */
    public static Date roundDownToQuarterHour(Date roundDate)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(roundDate);

        int roundMinute = calendar.get(Calendar.MINUTE);

        calendar.set(Calendar.MINUTE, roundMinute - (roundMinute % 15));
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    /**
     * Rounds the time to the nearest hour.
     * @param roundDate - Time to round
     * @return Date
     */
    public static Date roundToHour(Date roundDate)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(roundDate);

        if (calendar.get(Calendar.MINUTE) >= 30)
            calendar.add(Calendar.HOUR, 1);

        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    /**
     * Formats a Date as a human readable time stamp
     * @param formatDate - Date to format
     * @return String
     */
    public static String formatDateAsTimestamp(Date formatDate)
    {
        return timestampFormat.format(formatDate);
    }

    /**
     * Formats a Date as a human readable MM/DD/YY
     * @param formatDate - Date to format
     * @return String
     */
    public static String formatDateAsSlashMMDDYY(Date formatDate)
    {
        return slashDateFormat.format(formatDate);
    }

    /**
     * Formats current date as YYMMDD
     * @return String
     */
    public static String getTodayYYMMDD()
    {
        return (dateFormat.format(new Date()));
    }

    /**
     * Formats current date as MM/DD/YY
     * @return String
     */
    public static String getTodaySlashMMDDYY()
    {
        return (slashDateFormat.format(new Date()));
    }

    /**
     * Formats current date as a timestamp
     * @return String
     */
    public static String getNowTimestamp()
    {
        return (timestampFormat.format(new Date()));
    }

    /**
     * Formats current date as a file friendly timestamp
     * @return String
     */
    public static String getNowFileTimestamp()
    {
        return (fileTimestampFormat.format(new Date()));
    }

    /**
     * Formats current date as a filemaker timestamp
     * @return String
     */
    public static String getNowFMTimestamp()
    {
        return (fmTimestampFormat.format(new Date()));
    }

    /**
     * Parses Date from YYMMDD String
     * @param dateString - YYMMDD formatted date String
     * @return Date
     */
    public static Date getDateFromYYMMDD(String dateString) throws ParseException
    {
        return dateFormat.parse(dateString);
    }
}
