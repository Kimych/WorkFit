package by.uniterra.udi.view;

import java.util.Calendar;
import java.util.Date;

import org.jdesktop.swingx.JXMonthView;

public class JXMonthViewExt extends JXMonthView
{
    private static final long serialVersionUID = 5892386471244018862L;

    public int getFirstDisplayedDayOfYear()
    {
        return getDayOfYear(getFirstDisplayedDay());
    }

    public int getLastDisplayedDayOfYear()
    {
        return getDayOfYear(getLastDisplayedDay());
    }

    private int getDayOfYear(Date lastDisplayedDay)
    {
        Calendar calMonthCal = getCalendar();
        calMonthCal.setTime(lastDisplayedDay);
        return calMonthCal.get(Calendar.DAY_OF_YEAR);
    }

}
