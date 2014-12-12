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
import by.uniterra.udi.util.EDayType;
import by.uniterra.udi.util.Log;

public class MonthSpecialCalendar extends JPanel implements ActionListener
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 6315005613998935370L;
    static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    JXMonthViewExt monthView;

    HashMap<Integer, CalendarSpecialDay> mapFlaggetMonthDay = new HashMap<Integer, CalendarSpecialDay>();
    
    public MonthSpecialCalendar()
    {
        super(new GridBagLayout());
        jbInit();
    }

    private void jbInit()
    {

        JLabel jlWorkingDay = new JLabel("work days:");

        monthView = new JXMonthViewExt();
        monthView.setFirstDayOfWeek(Calendar.MONDAY);

        monthView.setDayForeground(Calendar.SUNDAY, Color.MAGENTA);
        monthView.setDayForeground(Calendar.SATURDAY, Color.MAGENTA);
        monthView.setShowingWeekNumber(true);
        //monthView.setTimeZone(TimeZone.getTimeZone("UTC"));
        monthView.addMouseListener(new MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    JPopupMenu pmTooltip = new JPopupMenu();
                    List<String> lstType = getDayType(monthView.getDayAtLocation(e.getX(), e.getY()));
                    for (String strDayType : lstType)
                    {
                        pmTooltip.add(strDayType);
                    }
                    pmTooltip.show((JXMonthView) e.getSource(), e.getX(), e.getY());
                }
                // show edit view on mouse right click
                if (SwingUtilities.isRightMouseButton(e))
                {
                    Date dateFromCal = monthView.getDayAtLocation(e.getX(), e.getY());
                    CalendarSpecialDayOptionPanel csd = new CalendarSpecialDayOptionPanel();
                    
                    csd.setModel(getCalendarSpecialDay(dateFromCal), monthView.isWeekEndAtLocation(e.getX(), e.getY()));
                    
                    JOptionPane.showMessageDialog(monthView, csd, DATE_FORMAT.format(dateFromCal), JOptionPane.PLAIN_MESSAGE);
                    //lstToChange = csd.getModel();
                    csd.getModel();
                   
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
        monthView.setFirstDisplayedDay(DateUtils.getMonthStartDate(numMonth, numYear));
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
                mapFlaggetMonthDay.put(currentDayNum, calSpecialDay);
            }
        }
       
        if (!mapFlaggetMonthDay.isEmpty())
        {
            monthView.setFlaggedDayForeground(Color.RED);
            monthView.setFlaggedDates(new ArrayList<Integer>(mapFlaggetMonthDay.keySet()));
        }
    }

    public List<String> getDayType(Date currentDate)
    {

        List<String> lstStrResult = new ArrayList<String>();
        List<EDayType> lstDayTypes = new ArrayList<EDayType>();
        int dayYearNum = DateUtils.getDayNumberInYear(currentDate);
        
        if(mapFlaggetMonthDay.containsKey(dayYearNum))
        {
            CalendarSpecialDay csd = mapFlaggetMonthDay.get(dayYearNum);
            lstDayTypes = csd.getTypeDay();
            for (EDayType eDayType : lstDayTypes)
            {
                lstStrResult.add(eDayType.toString());
            }
        }
        return lstStrResult;    

    }
     
    public CalendarSpecialDay getCalendarSpecialDay(Date date)
    {
        CalendarSpecialDay objResult = new CalendarSpecialDay();
        int dayYearNum = DateUtils.getDayNumberInYear(date);
        if(mapFlaggetMonthDay.containsKey(dayYearNum))
        {
            objResult = mapFlaggetMonthDay.get(dayYearNum);
        }
        return objResult;
    }

  /*  public List<EDayType> getListEnumDayType(Date date)
    {
        List<EDayType> lstREsult = new ArrayList<EDayType>();
        int dayYearNum = DateUtils.getDayNumberInYear(date);
        for (CalendarSpecialDay csd : lstFlaggetDays)
        {
            if(dayYearNum == csd.getYearDayNumber())
            {
                lstREsult = csd.getTypeDay();
                break;
            }
        }
        return lstREsult;
    }*/
    
    
    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        // TODO Auto-generated method stub

    }

}
