package by.uniterra.udi.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import by.uniterra.system.util.DateUtils;
import by.uniterra.udi.util.Log;

public class YearSpecialCalendar extends JPanel implements ActionListener
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 8139179633704002297L;

    HashMap<Integer, MonthSpecialCalendar> mpMonth = new HashMap<Integer, MonthSpecialCalendar>();

    private static int MAX_ITEMS_IN_LINE = 4;
    private static final String ACTION_NEXT_YEAR = "Next Year";
    private static final String ACTION_PREVIOUS_YEAR = "Previous Year";
    private int numYear;

    private JLabel lblYearNumber;

    public YearSpecialCalendar()
    {
        super(new GridBagLayout());

        jbInit();
    }

    private void jbInit()
    {
        numYear = DateUtils.getYearNumber(new Date());

        JPanel panelYearControls = new JPanel(new GridBagLayout());
        // create button
        JButton btnNextYear = new JButton(">>");
        btnNextYear.setActionCommand(ACTION_NEXT_YEAR);
        btnNextYear.addActionListener(this);

        JButton btnPreviousYear = new JButton("<<");
        btnPreviousYear.setActionCommand(ACTION_PREVIOUS_YEAR);
        btnPreviousYear.addActionListener(this);
        
        lblYearNumber = new JLabel(String.valueOf(numYear));
        // add to control panel
        panelYearControls.add(btnPreviousYear, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        panelYearControls.add(lblYearNumber, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
        panelYearControls.add(btnNextYear, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        // add to main panel
        add(panelYearControls, new GridBagConstraints(0, 0, 4, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        // build calendar 4x3
        for (int i = 0; i < 12; i++)
        {
            MonthSpecialCalendar msc = new MonthSpecialCalendar();
            msc.setModel(i, numYear);
            mpMonth.put(i, msc);
            int iYPos = (i / MAX_ITEMS_IN_LINE) + 1;
            int iXPos = i % MAX_ITEMS_IN_LINE;
            add(msc, new GridBagConstraints(iXPos, iYPos, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
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
                setModel(++numYear);
                break;
            case ACTION_PREVIOUS_YEAR:
                setModel(--numYear);
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
        for (Integer iMonthIndex : mpMonth.keySet())
        {
            mpMonth.get(iMonthIndex).setModel(iMonthIndex, numYear);
        }
        // update year number
        lblYearNumber.setText(String.valueOf(numYear));
    }

}
