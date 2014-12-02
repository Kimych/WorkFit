package by.uniterra.udi.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXMonthView;

import by.uniterra.dai.entity.CalendarSpecialDay;
import by.uniterra.system.util.DateUtils;
import by.uniterra.udi.util.Log;

public class MonthSpecialCalendar extends JPanel implements ActionListener
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 6315005613998935370L;

    JXMonthView monthView;

    public MonthSpecialCalendar()
    {
        super(new GridBagLayout());
        jbInit();
    }

    private void jbInit()
    {
        JLabel jlWorkingDay = new JLabel("work days:");

        monthView = new JXMonthView();
        monthView.setFirstDayOfWeek(Calendar.MONDAY);

        monthView.setDayForeground(Calendar.SUNDAY, Color.MAGENTA);
        monthView.setDayForeground(Calendar.SATURDAY, Color.MAGENTA);
        monthView.setShowingWeekNumber(true);

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

    public List<Date> setListSpecialdays(List<CalendarSpecialDay> lstSpecialDayInYear)
    {
        List<Date> lstFlaggetDate = new ArrayList<Date>();
        Date monthStart = DateUtils.getMonthStartDate(monthView);//00:00:00
        Date monthFinish =DateUtils.getMonthLastDate(monthView);//23:59:59
        for (CalendarSpecialDay calSpecialDay : lstSpecialDayInYear)
        {
            Date currentDay = calSpecialDay.getDateDay();
            if (currentDay.getTime() >= monthStart.getTime() && currentDay.getTime() <= monthFinish.getTime())
            {
                lstFlaggetDate.add(currentDay);
            }
        }
        return  lstFlaggetDate;
    }

    
    public void setSpecialDays(List<Date> lstFlaggetDate)
    {
        Date[] arrDateHoliday = new Date[lstFlaggetDate.size()];
        arrDateHoliday = lstFlaggetDate.toArray(arrDateHoliday);
        monthView.setFlaggedDayForeground(Color.RED);
        monthView.setFlaggedDates(arrDateHoliday);
    }
    

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        // TODO Auto-generated method stub

    }

}
