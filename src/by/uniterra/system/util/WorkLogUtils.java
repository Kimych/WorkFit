package by.uniterra.system.util;

import java.math.BigDecimal;

public class WorkLogUtils
{
    private static int WORK_HOUR_IN_DAY = 8;
    private static double PERCENT_REMAINS_TO_BONUS = 1.05;

    public static double getTimeRemainsToPlane(int workDayInMonth, double worklog, double bonustime)
    {
        return workDayInMonth * WORK_HOUR_IN_DAY - worklog + bonustime;
    }

    public static double getTimeRemainsToBonus(int workDayInMonth, double worklog, double bonustime)
    {
        return workDayInMonth * WORK_HOUR_IN_DAY * PERCENT_REMAINS_TO_BONUS - worklog + bonustime;
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
        catch (NumberFormatException ex)
        {
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
