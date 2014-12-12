package by.uniterra.udi.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jdesktop.swingx.JXMonthView;

public class JXMonthViewExt extends JXMonthView
{
    private static final long serialVersionUID = 5892386471244018862L;

    /**
     * 
     * @return day of year of first day in current month 
     *
     * @author Anton Nedbailo
     * @date Dec 11, 2014
     */
    public int getFirstDisplayedDayOfYear()
    {
        return getDayOfYear(getFirstDisplayedDay());
    }

    /**
     * 
     * @return day of year of last day in current month 
     *
     * @author Anton Nedbailo
     * @date Dec 11, 2014
     */
    public int getLastDisplayedDayOfYear()
    {
        return getDayOfYear(getLastDisplayedDay());
    }

    /**
     * Get day of year number by given Date
     * 
     * @param dDate - Date to calculate the number 
     * 
     * @return day number in year
     *
     * @author Anton Nedbailo
     * @date Dec 11, 2014
     */
    private int getDayOfYear(Date dDate)
    {
        Calendar calMonthCal = getCalendar();
        calMonthCal.setTime(dDate);
        return calMonthCal.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Returns true if the Date at the location is week end
     * 
     * @param x - the x position of the location in pixel
     * @param y - the y position of the location in pixel
     * 
     * @return true if date at given location is week end
     *
     * @author Anton Nedbailo
     * @date Dec 11, 2014
     */
    public boolean isWeekEndAtLocation(int x, int y)
    {
        // get copy of internal calendar
        Calendar calMonthCal = getCalendar();
        // set time at given location
        calMonthCal.setTime(getDayAtLocation(x, y));
        // return true if given day is week end
        return calMonthCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calMonthCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    /**
     * Returns day of year at given location
     * 
     * @param x - the x position of the location in pixel
     * @param y - the y position of the location in pixel
     * 
     * @return day of year at given location
     *
     * @author Anton Nedbailo
     * @date Dec 11, 2014
     */
    public int getDayOfYearAtLocation(int x, int y)
    {
        return getDayOfYear(getDayAtLocation(x, y));
    }

    public void setFlaggedDates(List<Integer> arrayList)
    {
        List<Date> lstSelectedDates = new ArrayList<Date>(arrayList.size());
        for (Integer iCurDayOfYear : arrayList)
        {
            Calendar cal = getCalendar();
            cal.set(Calendar.DAY_OF_YEAR, iCurDayOfYear);
            lstSelectedDates.add(cal.getTime());
        }
        setFlaggedDates(lstSelectedDates.stream().toArray(Date[]::new));
    }

}
