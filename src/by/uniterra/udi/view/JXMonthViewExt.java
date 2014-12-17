package by.uniterra.udi.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jdesktop.swingx.JXMonthView;
import org.jdesktop.swingx.calendar.CalendarUtils;

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
     * @param dDate
     *            - Date to calculate the number
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
     * @param x
     *            - the x position of the location in pixel
     * @param y
     *            - the y position of the location in pixel
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
     * @param x
     *            - the x position of the location in pixel
     * @param y
     *            - the y position of the location in pixel
     * 
     * @return day of year at given location
     *
     * @author Anton Nedbailo
     * @date Dec 11, 2014
     */
    public int getDayOfYearAtLocation(int x, int y)
    {
        return getDayAtLocation(x, y) != null ?  getDayOfYear(getDayAtLocation(x, y)): 0;
    }

    /**
     * Replace all flags with the given days of current year.
     * 
     * @param lstDaysOfYear
     *            - lstDaysOfYear list of days of year to be flagged
     *
     * @author Anton Nedbailo
     * @date Dec 12, 2014
     */
    public void setFlaggedDates(List<Integer> lstDaysOfYear)
    {
        List<Date> lstSelectedDates = new ArrayList<Date>(lstDaysOfYear.size());
        for (Integer iCurDayOfYear : lstDaysOfYear)
        {
            Calendar cal = getCalendar();
            cal.set(Calendar.DAY_OF_YEAR, iCurDayOfYear);
            lstSelectedDates.add(cal.getTime());
        }
        setFlaggedDates(lstSelectedDates.stream().toArray(Date[]::new));
    }

    /**
     * Set month and year number to be displayed
     * 
     * @param numMonth
     *            - month number
     * @param numYear
     *            - year nember
     *
     * @author Anton Nedbailo
     * @date Dec 12, 2014
     */
    public void setDisplayedMonth(int numMonth, int numYear)
    {
        Calendar calMonthCal = getCalendar();
        // set new month and year numbers
        calMonthCal.set(Calendar.MONTH, numMonth);
        calMonthCal.set(Calendar.YEAR, numYear);
        // round to start of current month
        CalendarUtils.startOfMonth(calMonthCal);
        // set new first displayed day
        setFirstDisplayedDay(calMonthCal.getTime());
    }
    
    public int getNumWorkindDay()
    {
        
        return 0;
    }
    

}
