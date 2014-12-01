package by.uniterra.system.util;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import by.uniterra.udi.util.Log;

public class WorkLogUtils
{
    private static int WORK_HOUR_IN_DAY = 8;
    private static double PERCENT_REMAINS_TO_BONUS = 1.05;

    /**
     * 
     * @param workDayInMonth
     *            - the number of working days in a current month
     * @param worklog
     *            - time from last log in a month
     * @param bonustime
     *            - the sum of bonus time from log
     * @return amount of time to be processed before the closing months
     *
     * @author Sergio Alecky
     * @date 02 окт. 2014 г.
     */
    public static double getTimeRemainsToPlaneInMonth(int workDayInMonth, double worklog, double bonustime)
    {
        return workDayInMonth * WORK_HOUR_IN_DAY - (worklog + bonustime);
    }
    
    
    /**
     * 
     * @param actualWorkeDays - the number of working days in a current day
     * @param worklog -  time from last log in a month
     * @param bonustime - - the sum of bonus time from log
     * @return amount of time to be processed before the closing current time period
     *
     * @author Sergio Alecky
     * @date 02 окт. 2014 г.
     */
    public static double getTimeRemainsToPlaneToDay(int actualWorkeDays, double worklog, double bonustime, double spentholiday)
    {
        return actualWorkeDays * WORK_HOUR_IN_DAY - (worklog + bonustime + spentholiday*WORK_HOUR_IN_DAY);
    }

    public static double getTimeRemainsToBonusInMonth(int workDayInMonth, double worklog, double bonustime)
    {
        return workDayInMonth * WORK_HOUR_IN_DAY * PERCENT_REMAINS_TO_BONUS - (worklog + bonustime);
    }
    
    public static double getTimeRemainsToBonusToDay(int actualWorkeDays, double worklog, double bonustime, double spentholiday)
    {
        return actualWorkeDays * WORK_HOUR_IN_DAY * PERCENT_REMAINS_TO_BONUS - (worklog + bonustime + spentholiday*WORK_HOUR_IN_DAY);
    }

    /**
     * <P>
     * Round the given value to the specified number of decimal places.
     * </P>
     * The value is rounded using the java.math.BigDecimal.ROUND_HALF_UP method.
     * 
     * @param x
     *            - the value to round.
     * @param scale
     *            - scale the number of digits to the right of the decimal
     *            point.
     * @param roundingMethod
     *            - the rounding method as defined in java.math.BigDecimal.
     * @return - the rounded value.
     */
    public static double round(double x, int scale, int roundingMethod)
    {
        try
        {
            return (new BigDecimal(Double.toString(x)).setScale(scale, roundingMethod)).doubleValue();
        }
        catch (NumberFormatException e)
        {
            Log.error(WorkLogUtils.class, e, "round error");
            if (Double.isInfinite(x))
            {
                return x;
            }
            else
            {
                return Double.NaN;
            }
        }
    }

    /**
     * <P>
     * Round the given value to the specified number of decimal places and
     * convert it to string.
     * </P>
     * The value is rounded using the java.math.BigDecimal.ROUND_HALF_UP method.
     * 
     * @param x
     *            - the value to round.
     * @param scale
     *            - scale the number of digits to the right of the decimal
     *            point.
     * @param roundingMethod
     *            - the rounding method as defined in java.math.BigDecimal.
     * 
     * @return - the rounded value in String format
     */
    public static String roundToString(double x, int scale, int roundingMethod)
    {
        // math result
        double dResult = round(x, scale, roundingMethod);
        // convert to string
        return scale >= 0 ? String.format("%." + scale + "f", dResult) : String.valueOf((int) dResult);
    }

    public static boolean beInPlaneAtTime(int actualWorkingDays, int workingDaysInMonth, double toPlane)
    {
        if (WORK_HOUR_IN_DAY * (workingDaysInMonth - actualWorkingDays) >= toPlane)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static int getWorkingDaysBetweenTwoDates(Date startDate, Date endDate)
    {
        Calendar startCal;
        Calendar endCal;
        startCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        startCal.setTime(startDate);
        endCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        endCal.setTime(endDate);
        int workDays = 0;

        // Return 0 if start and end are the same
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis())
        {
            return workDays;
        }

        if (startCal.getTimeInMillis() > endCal.getTimeInMillis())
        {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }
        
       /* Log.debug(WorkLogUtils.class, DateUtils.toUTC(startCal.getTimeInMillis()) + " is a start time, " 
                + DateUtils.toUTC(endCal.getTimeInMillis()) + " is an end time.");*/
        
        while (startCal.getTimeInMillis() < endCal.getTimeInMillis())
        {
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
            {
                ++workDays;
                //Log.debug(WorkLogUtils.class, DateUtils.toUTC(startCal.getTimeInMillis()) + " added to working days (" + workDays + ")");
            }
            startCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        

        return workDays;
    }

    public static Date getDateCurentMonthStart(Date dateFromLog)
    {
        /*ZoneId tz = Clock.systemDefaultZone().getZone();
        // tha same zone should be used twice to annihilate time shifting
        Date dateItNow = Date.from(LocalDateTime.now(tz).withDayOfMonth(1).atZone(tz).toInstant());
        dateItNow.setHours(0);
        dateItNow.setMinutes(0);
        dateItNow.setSeconds(1);*/
        
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(dateFromLog);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, 0 );
        calendar.set(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);

        

       // Date firstDayOfMonth = calendar.getTime();

        return calendar.getTime();
    }

}
