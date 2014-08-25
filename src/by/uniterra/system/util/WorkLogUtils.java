package by.uniterra.system.util;

public class WorkLogUtils
{

    public static double getTimeRemainsToPlane(int workDayInMonth, double worklog, double bonustime)
    {
        return workDayInMonth * 8 - worklog + bonustime;
    }
    
    public static double getTimeRemainsToBonus(int workDayInMonth, double worklog, double bonustime)
    {
        return workDayInMonth * 8 * 1.05 - worklog + bonustime;
    }
    

}
