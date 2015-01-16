package by.uniterra.udi.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import by.uniterra.dai.eao.CalendarSpecialDayEAO;
import by.uniterra.dai.eao.YearEAO;
import by.uniterra.dai.entity.CalendarSpecialDay;
import by.uniterra.dai.entity.Year;
import by.uniterra.system.model.SystemModel;
import by.uniterra.udi.util.Log;

public class YearSpecialCalendar extends JPanel implements ActionListener
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 8139179633704002297L;

    private static final int MAX_ITEMS_IN_LINE = 4;
    private static final String ACTION_NEXT_YEAR = "Next Year";
    private static final String ACTION_PREVIOUS_YEAR = "Previous Year";

    private int numYear;
    public static Year objYear;
    private HashMap<Integer, MonthSpecialCalendar> mpMonthPanels = new HashMap<Integer, MonthSpecialCalendar>();
    HashMap<Integer, CalendarSpecialDay> mapFlaggetDay = new HashMap<Integer, CalendarSpecialDay>();

    private List<CalendarSpecialDay> lstYearCalSpecDay = new ArrayList<CalendarSpecialDay>();

    private CalendarSpecialDayEAO eaoCalSpecDay = new CalendarSpecialDayEAO(SystemModel.getDefaultEM());
    private JLabel lblYearNumber;

    public YearSpecialCalendar()
    {
        super(new GridBagLayout());
        jbInit();
    }

    private void jbInit()
    {
        JPanel panelYearControls = new JPanel(new GridBagLayout());
        // create button
        JButton btnNextYear = new JButton(">>");
        btnNextYear.setActionCommand(ACTION_NEXT_YEAR);
        btnNextYear.addActionListener(this);

        JButton btnPreviousYear = new JButton("<<");
        btnPreviousYear.setActionCommand(ACTION_PREVIOUS_YEAR);
        btnPreviousYear.addActionListener(this);

        lblYearNumber = new JLabel();
        // add to control panel
        panelYearControls.add(btnPreviousYear, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0,
                0), 0, 0));
        panelYearControls.add(lblYearNumber, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 5, 0, 5), 0, 0));
        panelYearControls.add(btnNextYear, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0),
                0, 0));
        // add to main panel
        add(panelYearControls, new GridBagConstraints(0, 0, 4, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        // build calendar 4x3
        for (int i = 0; i < 12; i++)
        {
            MonthSpecialCalendar msc = new MonthSpecialCalendar();
            mpMonthPanels.put(i, msc);
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
                saveChanges();
                setModel(++numYear);
                break;
            case ACTION_PREVIOUS_YEAR:
                saveChanges();
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
        this.numYear = numYear;
        YearSpecialCalendar.objYear = new YearEAO(SystemModel.getDefaultEM()).getYearByYearNum(numYear);

        // get list year special day in numYear
        lstYearCalSpecDay = eaoCalSpecDay.getSpecialDayByYear(numYear);
        for (Integer iMonthIndex : mpMonthPanels.keySet())
        {
            MonthSpecialCalendar msc = mpMonthPanels.get(iMonthIndex);
            msc.setModel(iMonthIndex, numYear);
            msc.selectCSDforCurrentMonth(lstYearCalSpecDay, true);
        }
        // update year number
        lblYearNumber.setText(String.valueOf(numYear));
    }

    public boolean saveChanges()
    {
        CalendarSpecialDayEAO csdEAO = new CalendarSpecialDayEAO(SystemModel.getDefaultEM());
        boolean bResult = false;
        // get changes
        mapFlaggetDay.clear();
        for (Integer iMonthIndex : mpMonthPanels.keySet())
        {
            MonthSpecialCalendar msc = mpMonthPanels.get(iMonthIndex);
            mapFlaggetDay.putAll(msc.getMapFlaggetMonthDay());
        }
        // create map
        HashMap<Integer, CalendarSpecialDay> mapYearCalSpecDay = new HashMap<Integer, CalendarSpecialDay>();
        for (CalendarSpecialDay csd : lstYearCalSpecDay)
        {
            mapYearCalSpecDay.put(csd.getYearDayNumber(), csd);
        }
        for (Iterator<Entry<Integer, CalendarSpecialDay>> iterator = mapYearCalSpecDay.entrySet().iterator(); iterator.hasNext();)
        {
            // get current entry
            Entry<Integer, CalendarSpecialDay> enOrigEntry = iterator.next();
            // get key
            Integer iYearIndex = enOrigEntry.getKey();
            if (mapFlaggetDay.containsKey(iYearIndex))
            {
                if (!enOrigEntry.getValue().equals(mapFlaggetDay.get(iYearIndex)))
                {
                    // save changes (and remove this records from maps)
                    csdEAO.save(mapFlaggetDay.get(iYearIndex));
                }
                // remove duplication to have at finish only "deleted" elements
                iterator.remove();
                // remove from map to have at finish only "new" elements
                mapFlaggetDay.remove(iYearIndex);
            }
        }
        // save new
        for (Integer iIndex : mapFlaggetDay.keySet())
        {
            csdEAO.save(mapFlaggetDay.get(iIndex));
        }
        // remove from db
        for (Integer iIndex : mapYearCalSpecDay.keySet())
        {
            csdEAO.delete(mapYearCalSpecDay.get(iIndex));
        }
        return bResult;
    }

}
