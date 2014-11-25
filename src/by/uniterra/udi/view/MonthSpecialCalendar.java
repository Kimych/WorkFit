package by.uniterra.udi.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXMonthView;

import by.uniterra.system.util.DateUtils;

public class MonthSpecialCalendar extends JPanel
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 6315005613998935370L;

    

    
    public MonthSpecialCalendar(int month, int year)
    {
        super(new GridBagLayout());
        jbInit(month, year);
    }


    private void jbInit(int month, int year)
    {
        JLabel jlWorkingDay = new JLabel("Wrk  Day:");
        
        JXMonthView monthView = new JXMonthView(DateUtils.getMonthStartDate(month, 2014));
        monthView.setFirstDayOfWeek(Calendar.MONDAY);
        monthView.setTodayBackground(Color.GREEN);
        /*monthView.setPreferredColumnCount(4);
        monthView.setPreferredRowCount(3);*/
        add(monthView, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlWorkingDay, new GridBagConstraints(0, 1, 1, 0, 0, 0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        
    }
}
