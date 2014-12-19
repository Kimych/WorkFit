package by.uniterra.udi.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TimeZone;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXMonthView;

import by.uniterra.dai.entity.CalendarSpecialDay;
import by.uniterra.system.util.DateUtils;
import by.uniterra.udi.model.UDIPropSingleton;
import by.uniterra.udi.util.EDayType;

public class MonthSpecialCalendar extends JPanel implements MouseListener
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 6315005613998935370L;

    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private JXMonthViewExt monthView;
    private JLabel jlWorkingDay;
    public int numWorkingDay = 0;

    private HashMap<Integer, CalendarSpecialDay> mapFlaggetMonthDay = new HashMap<Integer, CalendarSpecialDay>();

    public HashMap<Integer, CalendarSpecialDay> getMapFlaggetMonthDay()
    {
        return this.mapFlaggetMonthDay;
    }

    public MonthSpecialCalendar()
    {
        super(new GridBagLayout());
        jbInit();
    }

    private void jbInit()
    {
        jlWorkingDay = new JLabel("");
        monthView = new JXMonthViewExt();
        monthView.setFirstDayOfWeek(Calendar.MONDAY);
        monthView.setDayForeground(Calendar.SUNDAY, Color.MAGENTA);
        monthView.setDayForeground(Calendar.SATURDAY, Color.MAGENTA);
        monthView.setShowingWeekNumber(true);
        monthView.setTimeZone(TimeZone.getTimeZone("UTC"));
        monthView.addMouseListener(this);
        add(monthView, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlWorkingDay, new GridBagConstraints(0, 1, 1, 0, 0, 0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
    }

    public void setModel(int numMonth, int numYear)
    {
        monthView.setDisplayedMonth(numMonth, numYear);
        numWorkingDay = getNumWorkingDays();
    }

    public void setZoomable(boolean bSet)
    {
        monthView.setZoomable(bSet);
    }

    public void setFlaggedDates(Date[] arrDate)
    {
        monthView.setFlaggedDates(arrDate);
    }

    public SortedSet<Date> getFlaggedDates()
    {
        return monthView.getFlaggedDates();
    }

    public void selectCSDforCurrentMonth(List<CalendarSpecialDay> lstSpecialDayInYear)
    {
        mapFlaggetMonthDay.clear();
        int monthStartNum = monthView.getFirstDisplayedDayOfYear();
        int monthFinishNum = monthView.getLastDisplayedDayOfYear();
        for (CalendarSpecialDay calSpecialDay : lstSpecialDayInYear)
        {
            int currentDayNum = calSpecialDay.getYearDayNumber();
            if (currentDayNum >= monthStartNum && currentDayNum <= monthFinishNum)
            {
                mapFlaggetMonthDay.put(currentDayNum, new CalendarSpecialDay(calSpecialDay));
            }
        }
        repaintFlaggedDays();
    }

    public void repaintFlaggedDays()
    {
        int iDelta = 0;
        for (Integer iIndex : mapFlaggetMonthDay.keySet())
        {
            if (mapFlaggetMonthDay.get(iIndex).getTypeDay().contains(EDayType.WORKING_DAY))
            {
                iDelta++;
            }
            if (mapFlaggetMonthDay.get(iIndex).getTypeDay().contains(EDayType.DAY_OFF))
            {
                iDelta--;
            }
        }
        monthView.setFlaggedDayForeground(Color.RED);
        monthView.setFlaggedDates(new ArrayList<Integer>(mapFlaggetMonthDay.keySet()));
        jlWorkingDay.setText(UDIPropSingleton.getString(this, "Wrk.label") + (numWorkingDay + iDelta));
    }

    public List<String> getDayTooltip(int dayYearNum)
    {
        List<String> lstStrResult = new ArrayList<String>();
        if (mapFlaggetMonthDay.containsKey(dayYearNum))
        {
            for (EDayType eDayType : mapFlaggetMonthDay.get(dayYearNum).getTypeDay())
            {
                lstStrResult.add(eDayType.toString());
            }
            if (!mapFlaggetMonthDay.get(dayYearNum).getDescrition().isEmpty())
            {
                lstStrResult.add("* " + mapFlaggetMonthDay.get(dayYearNum).getDescrition());
            }
        }
        return lstStrResult;
    }

    public CalendarSpecialDay getCalendarSpecialDay(int dayYearNum)
    {
        if (!mapFlaggetMonthDay.containsKey(dayYearNum))
        {
            // create new
            CalendarSpecialDay objResult = new CalendarSpecialDay();
            objResult.setYear(YearSpecialCalendar.objYear);
            objResult.setYearDayNumber(dayYearNum);
            // add to map
            mapFlaggetMonthDay.put(dayYearNum, objResult);
        }
        return mapFlaggetMonthDay.get(dayYearNum);
    }

    @Override
    public void mouseClicked(MouseEvent arg0)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent arg0)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent arg0)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (SwingUtilities.isLeftMouseButton(e))
        {
            JPopupMenu pmTooltip = new JPopupMenu();
            int dayNumber = monthView.getDayOfYearAtLocation(e.getX(), e.getY());
            if (dayNumber != 0)
            {
                List<String> lstType = getDayTooltip(dayNumber);
                if (!lstType.isEmpty())
                {
                    for (String strDayType : lstType)
                    {
                        pmTooltip.add(strDayType);
                    }
                    pmTooltip.show((JXMonthView) e.getSource(), e.getX(), e.getY());
                }
            }
        }
        // show edit view on mouse right click
        if (SwingUtilities.isRightMouseButton(e))
        {
            int dateFromCal = monthView.getDayOfYearAtLocation(e.getX(), e.getY());
            if (dateFromCal != 0)
            {
                CalendarSpecialDayOptionPanel csdPanel = new CalendarSpecialDayOptionPanel();

                csdPanel.setModel(getCalendarSpecialDay(dateFromCal), monthView.isWeekEndAtLocation(e.getX(), e.getY()));

                JOptionPane.showMessageDialog(monthView, csdPanel, DATE_FORMAT.format(monthView.getDayAtLocation(e.getX(), e.getY())),
                        JOptionPane.PLAIN_MESSAGE);

                CalendarSpecialDay objCurrentCSD = csdPanel.getModel();
                // for new empty object
                if (objCurrentCSD.getDescrition().isEmpty() && objCurrentCSD.getTypeDay().isEmpty())
                {
                    mapFlaggetMonthDay.remove(objCurrentCSD.getYearDayNumber());
                }
                else
                {
                    mapFlaggetMonthDay.put(objCurrentCSD.getYearDayNumber(), objCurrentCSD);
                }
                repaintFlaggedDays();
            }
        }
    }
    
    /**
     * 
     * @return  number of working days in current month
     *
     * @author Sergio Alecky
     * @date 19 дек. 2014 г.
     */
    public int getNumWorkingDays()
    {
        Date startDate = monthView.getFirstDisplayedDay();
        Date endDate = monthView.getLastDisplayedDay();
        Calendar startCal;
        Calendar endCal;
        startCal = Calendar.getInstance(monthView.getTimeZone());
        startCal.setTime(startDate);
        endCal = Calendar.getInstance(monthView.getTimeZone());
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
}
