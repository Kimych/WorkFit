package by.uniterra.udi.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import by.uniterra.udi.iface.IModelOwner;
import by.uniterra.udi.util.Log;

public class YearSpecialCalendar extends JPanel implements ActionListener
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 8139179633704002297L;

    HashMap<Integer, MonthSpecialCalendar> mpMonth = new HashMap<Integer, MonthSpecialCalendar>();

    MonthSpecialCalendar msc;
    MonthSpecialCalendar msc1;
    MonthSpecialCalendar msc2;
    MonthSpecialCalendar msc3;
  

    private static final String ACTION_NEXT_YEAR = "Next Year";
    private static final String ACTION_PREVIOUS_YEAR = "Previous Year";

    public YearSpecialCalendar()
    {
        super(new GridBagLayout());

        jbInit();
    }

    private void jbInit()
    {
        // create button
        JButton btnNextYear = new JButton("Next Year");
        btnNextYear.setActionCommand(ACTION_NEXT_YEAR);
        btnNextYear.addActionListener(this);

        JButton btnPreviousYear = new JButton("Previous Year");
        btnPreviousYear.setActionCommand(ACTION_PREVIOUS_YEAR);
        btnPreviousYear.addActionListener(this);

        add(btnPreviousYear, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(btnNextYear, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        // build calendar 4x3
        msc = new MonthSpecialCalendar();
        msc.setModel(0, 2014);
        mpMonth.put(0, msc);
        
        msc1 = new MonthSpecialCalendar();
        msc1.setModel(1, 2014);
        mpMonth.put(1, msc1);
        
        msc2 = new MonthSpecialCalendar();
        msc2.setModel(2, 2014);
        mpMonth.put(2, msc2);
        
        msc3 = new MonthSpecialCalendar();
        msc3.setModel(3, 2014);
        mpMonth.put(3, msc3);
        
   /*     for (int i = 0; i < 12; i++)
        {
            msc = new MonthSpecialCalendar();

            // test
            msc.setModel(i, 2014);

            mpMonth.put(i, msc);
            

            if (i < 4)
            {
                add(mpMonth.get(i), new GridBagConstraints(i, 1, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5),
                        0, 0));
            }
            else
            {
                if (i < 8)
                {
                    add(mpMonth.get(i), new GridBagConstraints(i - 4, 2, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0,
                            0, 5), 0, 0));

                }
                else
                {
                    add(mpMonth.get(i), new GridBagConstraints(i - 8, 3, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0,
                            0, 5), 0, 0));
                }
            }
            
        }*/
        add(mpMonth.get(0), new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5),
                0, 0));
        add(mpMonth.get(1), new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5),
                0, 0));
        add(mpMonth.get(2), new GridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5),
                0, 0));
        add(mpMonth.get(3), new GridBagConstraints(3, 1, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5),
                0, 0));

    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        try
        {
            switch (arg0.getActionCommand())
            {
            case ACTION_NEXT_YEAR:
                setModel(2015);
                break;
            case ACTION_PREVIOUS_YEAR:
                setModel(2013);
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

    public void setModel(int numYear)
    {
        this.mpMonth.clear();
        if (mpMonth.isEmpty())
        {
            msc.setModel(0, numYear);
            mpMonth.put(0, msc);
            
            msc1.setModel(1, numYear);
            mpMonth.put(1, msc1);
            
            msc2.setModel(2, numYear);
            mpMonth.put(2, msc2);
            
            msc3.setModel(3, numYear);
            mpMonth.put(3, msc3);
        }
    }

}
