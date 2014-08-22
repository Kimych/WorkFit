package by.uniterra.system.util;

public class WorkLogUtils
{

    public double getTimeRemainsToPlane(int workDayInMonth, double worklog, double bonustime)
    {
        double toBonusresult = 0;
        toBonusresult = workDayInMonth * 8 - worklog + bonustime;
        return toBonusresult;
    }

}
