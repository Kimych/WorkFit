package by.uniterra.system.util;

public class WorkLogUtils
{

    public static double getTimeRemainsToPlane(int workDayInMonth, double worklog, double bonustime)
    {
        return workDayInMonth * 8 - worklog + bonustime;
    }

}
