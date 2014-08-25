package by.uniterra.system.util;

public class WorkLogUtils
{
    private static int  WORK_HOUR_IN_DAY = 8;
    private static double  PERCENT_REMAINS_TO_BONUS = 1.05;

    public static double getTimeRemainsToPlane(int workDayInMonth, double worklog, double bonustime)
    {
        return workDayInMonth * WORK_HOUR_IN_DAY - worklog + bonustime;
    }
    
    public static double getTimeRemainsToBonus(int workDayInMonth, double worklog, double bonustime)
    {
        return workDayInMonth * WORK_HOUR_IN_DAY * PERCENT_REMAINS_TO_BONUS - worklog + bonustime;
    }
    

}
