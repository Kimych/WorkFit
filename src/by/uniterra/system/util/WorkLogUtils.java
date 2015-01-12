package by.uniterra.system.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import by.uniterra.dai.eao.CalendarSpecialDayEAO;
import by.uniterra.dai.entity.CalendarSpecialDay;
import by.uniterra.system.model.SystemModel;
import by.uniterra.udi.util.EDayType;
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
     * @param actualWorkeDays
     *            - the number of working days in a current day
     * @param worklog
     *            - time from last log in a month
     * @param bonustime
     *            - - the sum of bonus time from log
     * @return amount of time to be processed before the closing current time
     *         period
     *
     * @author Sergio Alecky
     * @date 02 окт. 2014 г.
     */
    public static double getTimeRemainsToPlaneToDay(int actualWorkeDays, double worklog, double bonustime, double spentholiday)
    {
        return actualWorkeDays * WORK_HOUR_IN_DAY - (worklog + bonustime + spentholiday * WORK_HOUR_IN_DAY);
    }

    /**
     * 
     * @param startDate
     * @param endDate
     * @return actual worked days taking into account the special days
     *
     * @author Sergio Alecky
     * @date 22 дек. 2014 г.
     */
    public static int getAсtualWorkedDays(Date startDate, Date endDate)
    {
        int iWrkDays = DateUtils.getWorkingDaysBetweenTwoDates(startDate, endDate);
        int iDelta = 0;
        int iStartDay = DateUtils.getDayOfYearUTC(startDate);
        int iEndDate = DateUtils.getDayOfYearUTC(endDate);
        // get list CSD
        CalendarSpecialDayEAO eaoCalSpecDay = new CalendarSpecialDayEAO(SystemModel.getDefaultEM());
        List<CalendarSpecialDay> lstCSD = eaoCalSpecDay.getSpecialDayByYear(DateUtils.getYearNumber(endDate));
        for (CalendarSpecialDay calendarSpecialDay : lstCSD)
        {
            int iYearDayNumber = calendarSpecialDay.getYearDayNumber();
            if (iYearDayNumber >= iStartDay && iYearDayNumber <= iEndDate)
            {
                List<EDayType> lstDayType = calendarSpecialDay.getTypeDay();
                if (lstDayType.contains(EDayType.WORKING_DAY))
                {
                    iDelta++;
                }
                if (lstDayType.contains(EDayType.DAY_OFF))
                {
                    iDelta--;
                }
            }
        }

        return iWrkDays+iDelta;
    }

    public static double getTimeRemainsToBonusInMonth(int workDayInMonth, double worklog, double bonustime)
    {
        // bonus means +1 day to amount of working days in current month
        return (workDayInMonth + 1) * WORK_HOUR_IN_DAY - (worklog + bonustime);
    }

    public static double getTimeRemainsToBonusToDay(int actualWorkeDays, double worklog, double bonustime, double spentholiday)
    {
        // bonus means +1 day to amount of working days in current month
        return (actualWorkeDays + 1) * WORK_HOUR_IN_DAY - (worklog + bonustime + spentholiday * WORK_HOUR_IN_DAY);
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

}
