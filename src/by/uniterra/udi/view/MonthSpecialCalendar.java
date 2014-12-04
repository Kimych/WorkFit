package by.uniterra.udi.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXMonthView;

import by.uniterra.dai.entity.CalendarSpecialDay;
import by.uniterra.system.util.DateUtils;
import by.uniterra.udi.model.UDIPropSingleton;
import by.uniterra.udi.util.Log;

public class MonthSpecialCalendar extends JPanel implements ActionListener
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 6315005613998935370L;
    HashMap<Date, Integer> mapDayType = new HashMap<Date, Integer>();

    JXMonthView monthView;

    public enum EDayType
    {
        WORKING_DAY, // 0
        DAY_OFF, // 1
        BIRTHDAY; // 2

        public static EDayType fromInteger(int x)
        {
            switch (x)
            {
            case 0:
                return WORKING_DAY;
            case 1:
                return DAY_OFF;
            case 2:
                return BIRTHDAY;
            }
            return null;
        }

        public static  String toString(EDayType sValue)
        {
            String strResult = "просто день";
            switch (sValue)
            {
            case WORKING_DAY:
                strResult = UDIPropSingleton.getString(MonthSpecialCalendar.class, "WrkDay.label");
                break;
            case DAY_OFF:
                strResult = UDIPropSingleton.getString(MonthSpecialCalendar.class, "DayOff.label");
                break;
            case BIRTHDAY:
                strResult = UDIPropSingleton.getString(MonthSpecialCalendar.class, "Birthday.lqbel");
                break;
            }
            return strResult;
        }
    };

    private List<CalendarSpecialDay> lstFlaggetDays;
    private List<CalendarSpecialDay> lstToDelete;
    private List<CalendarSpecialDay> lstToChange;

    public MonthSpecialCalendar()
    {
        super(new GridBagLayout());
        jbInit();
    }

    private void jbInit()
    {
        lstFlaggetDays = new ArrayList<CalendarSpecialDay>();

        JLabel jlWorkingDay = new JLabel("work days:");

        monthView = new JXMonthView();
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
                    // get date from JXMonthView
                    // use NQ_FIND_BY_DATE
                    // create CalendarSpecialDayOptionPanel()
                    // save changes in db
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
        lstFlaggetDays.clear();
        Date monthStart = DateUtils.getMonthStartDate(monthView);// 00:00:00
        Date monthFinish = DateUtils.upToEndDayDate(monthView.getLastDisplayedDay());
        for (CalendarSpecialDay calSpecialDay : lstSpecialDayInYear)
        {
            Date currentDay = calSpecialDay.getDateDay();
            if (currentDay.getTime() >= monthStart.getTime() && currentDay.getTime() <= monthFinish.getTime())
            {
                lstFlaggetDays.add(calSpecialDay);
            }
        }

        List<Date> lstDate = new ArrayList<Date>();
        for (CalendarSpecialDay csd : lstFlaggetDays)
        {
            lstDate.add(csd.getDateDay());
        }
        if (!lstDate.isEmpty())
        {
            Date[] arrDate = new Date[lstDate.size()];
            arrDate = lstDate.toArray(arrDate);
            monthView.setFlaggedDayForeground(Color.RED);
            monthView.setFlaggedDates(arrDate);
        }
    }

    public List<String> getDayType(Date currentDate)
    {

        List<String> lstStrResult = new ArrayList<String>();
        for (CalendarSpecialDay csd : lstFlaggetDays)
        {
            if (DateUtils.isSameDay(currentDate, csd.getDateDay()))
            {
                lstStrResult.add(EDayType.toString(EDayType.fromInteger(csd.getTypeDay())));
            }
        }
        return lstStrResult;

    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        // TODO Auto-generated method stub

    }

}
