package by.uniterra.udi.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


import by.uniterra.udi.util.Log;

public class YearSpecialCalendar extends JPanel implements ActionListener
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 8139179633704002297L;

    private static final String ACTION_NEXT_YEAR = "Next Year";
    private static final String ACTION_PREVIOUS_YEAR = "Previous Year";
    
    
    public YearSpecialCalendar(int numYear)
    {
        super(new GridBagLayout());
        
        jbInit(numYear);
    }

    private void jbInit(int numYear)
    {
        //create button
        JButton btnNextYear = new JButton("Next Year");
        btnNextYear.setActionCommand(ACTION_NEXT_YEAR);
        btnNextYear.addActionListener(this);
        
        JButton  btnPreviousYear = new JButton("Previous Year");
        btnPreviousYear.setActionCommand(ACTION_PREVIOUS_YEAR);
        btnPreviousYear.addActionListener(this);
        
        add(btnPreviousYear, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(btnNextYear, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        //build calendar 4x3 
        for (int i = 0; i < 12; i++)
        {
            JPanel msc = new MonthSpecialCalendar(i , numYear);
            if (i < 4)
            {
                add(msc, new GridBagConstraints(i, 1, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
            }
            else
            {
                if (i < 8)
                {
                    add(msc, new GridBagConstraints(i-4, 2, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));

                }
                else{
                    add(msc, new GridBagConstraints(i-8, 3, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
                }
            }

        }
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        try
        {
            switch (arg0.getActionCommand())
            {
            case ACTION_NEXT_YEAR:
                removeAll();
                new YearSpecialCalendar(2020);
                repaint();
                break;
            case ACTION_PREVIOUS_YEAR:
                jbInit(2013);
                repaint();
                break;
            
            default:
                break;
            }
        }
        catch (Exception e)
        {
            Log.error(YearSpecialCalendar.class, e, "actionPerformed expressions");
        }
        
    }

}
