package by.uniterra.udi.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXMonthView;

import by.uniterra.system.util.DateUtils;
import by.uniterra.udi.model.UDIPropSingleton;
import by.uniterra.udi.model.WorkLogInfoHelper;
import by.uniterra.udi.model.WorkLogInfoHolder;
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
        monthView.setTodayBackground(Color.GREEN);
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
                    //if select
                    
                    // add in List
                    
                    
                    //change color
                    
                }
                else
                {
                    Log.error(MonthSpecialCalendar.class, "actionPerformed(ActionEvent e)");
                }
            }
        });
        /*monthView.setPreferredColumnCount(4);
        monthView.setPreferredRowCount(3);*/
        add(monthView, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlWorkingDay, new GridBagConstraints(0, 1, 1, 0, 0, 0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        
    }

    public  void setModel(int numMonth, int numYear)
    
    {
        
       monthView.setFirstDisplayedDay(DateUtils.getMonthStartDate(numMonth, numYear));
       
        
    }


    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        // TODO Auto-generated method stub
        
    }



}
