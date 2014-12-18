package by.uniterra.udi.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import by.uniterra.udi.util.Log;

public class MonthSpecialCalendar extends JPanel
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

        jlWorkingDay = new JLabel("work days:");

        monthView = new JXMonthViewExt();
        monthView.setFirstDayOfWeek(Calendar.MONDAY);

        monthView.setDayForeground(Calendar.SUNDAY, Color.MAGENTA);
        monthView.setDayForeground(Calendar.SATURDAY, Color.MAGENTA);
        monthView.setShowingWeekNumber(true);
        // monthView.setTimeZone(TimeZone.getTimeZone("UTC"));
        monthView.addMouseListener(new MouseAdapter()
        {
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
        });

        monthView.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Date date = ((JXMonthView) e.getSource()).getSelection().last();
                if (date instanceof Date)
                {
                    // if select
                    // add in List
                    // change color
                }
                else
                {
                    Log.error(MonthSpecialCalendar.class, "actionPerformed(ActionEvent e)");
                }
            }
        });
        add(monthView, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlWorkingDay, new GridBagConstraints(0, 1, 1, 0, 0, 0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));

    }

    public void setModel(int numMonth, int numYear)
    {
        monthView.setDisplayedMonth(numMonth, numYear);
        numWorkingDay = DateUtils.getNumWorkindDaysInMonth(numYear, numMonth);
    }
    
    public void setZoomable(boolean bSet)
    {
        monthView.setZoomable(bSet);
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
            if(mapFlaggetMonthDay.get(iIndex).getTypeDay().contains(EDayType.WORKING_DAY))
            {
                iDelta++;
            }
            if(mapFlaggetMonthDay.get(iIndex).getTypeDay().contains(EDayType.DAY_OFF))
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
    

}
