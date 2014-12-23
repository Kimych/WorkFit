package by.uniterra.udi.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXMonthView;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.decorator.PatternPredicate;

import by.uniterra.dai.eao.CalendarSpecialDayEAO;
import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.entity.CalendarSpecialDay;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.system.model.SystemModel;
import by.uniterra.system.util.DateUtils;
import by.uniterra.udi.model.UDIPropSingleton;
import by.uniterra.udi.model.WorkLogInfoHelper;
import by.uniterra.udi.model.WorkLogInfoHolder;
import by.uniterra.udi.model.WorkLogTableModel;
import by.uniterra.udi.util.Log;

import com.sun.jmx.snmp.Timestamp;

public class AdminPanel extends JPanel implements ActionListener
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = -6399838252872491307L;

    static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private WorkLogTableModel wltm;
    private MonthSpecialCalendar jxmvCalendar;
    private JLabel jlLastUpdateInMonth;
    private JLabel jlUpdatedate;

    private HashMap<Integer, List<CalendarSpecialDay>> mapFlaggetMonthDay = new HashMap<Integer, List<CalendarSpecialDay>>();
    private CalendarSpecialDayEAO eaoCalSpecDay = new CalendarSpecialDayEAO(SystemModel.getDefaultEM());
    
    public AdminPanel()
    {
        super(new GridBagLayout());
        jbInit();
        // loadDataInUI();
    }

    private void jbInit()
    {
        Date currentDate = new Date();
        jlUpdatedate = new JLabel(UDIPropSingleton.getString(this, "infoTime.header") + DATE_FORMAT.format(currentDate));
        jlLastUpdateInMonth = new JLabel(UDIPropSingleton.getString(this, "updateTime.footer") + "--.--.--");
        setLayout(new GridBagLayout());

        jxmvCalendar = new MonthSpecialCalendar()
        {
            /** TODO document <code>serialVersionUID</code> */
            private static final long serialVersionUID = 1029511220030271022L;

            public void mouseReleased(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    if(!((JXMonthView) e.getSource()).getSelection().isEmpty())
                    {
                        Date date = ((JXMonthView) e.getSource()).getSelection().last();
                        
                        if (date instanceof Date)
                        {
                            List<WorkLogInfoHolder> lstNewData = WorkLogInfoHelper.getLogListUpToDate(date);
                            jlUpdatedate.setText(UDIPropSingleton.getString(this, "infoTime.header") + DATE_FORMAT.format(date));
                            wltm.setTableData(lstNewData);
                        }
                        else
                        {
                            Log.error(AdminPanel.class, "actionPerformed(ActionEvent e)");
                        }
                    }
                }
             }
        };
        jxmvCalendar.setZoomable(true);
        jxmvCalendar.getMonthView().addPropertyChangeListener(new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent e)
            {
                if ("firstDisplayedDay".equals(e.getPropertyName()))
                {
                    setNumWorkingDays();
                }
            }
        });

        wltm = new WorkLogTableModel();
        // wltm.setTableData(WorkLogInfoHelper.getLogListUpToDate(currentDate));
        JXTable table = new JXTable(wltm);
        table.setColumnControlVisible(true);
        // table.setHorizontalScrollEnabled(true);
        table.addHighlighter(HighlighterFactory.createSimpleStriping(new Color(234, 234, 234)));
        // swt custom renderers
        TimestampTableCellRenderer txrTimestampRenderer = new TimestampTableCellRenderer(SwingConstants.CENTER, DateUtils.EUROP_FULL_DATETIMEFORMAT, "UTC");
        table.setDefaultRenderer(Timestamp.class, txrTimestampRenderer);
        table.setDefaultRenderer(Date.class, txrTimestampRenderer);

        table.getColumn(wltm.getColIndex(WorkLogTableModel.COL_TO_PLAN)).setCellRenderer(new DoubleTableCellRenderer(SwingConstants.CENTER, 2));
        table.getColumn(wltm.getColIndex(WorkLogTableModel.COL_TO_PLAN)).setMaxWidth(100);
        
        table.getColumn(wltm.getColIndex(WorkLogTableModel.COL_SPENT_HOL_IN_MONT)).setCellRenderer(new DoubleTableCellRenderer(SwingConstants.CENTER, 0));
        
        table.getColumn(wltm.getColIndex(WorkLogTableModel.COL_TO_BONUS)).setCellRenderer(new DoubleTableCellRenderer(SwingConstants.CENTER, 2));
        table.getColumn(wltm.getColIndex(WorkLogTableModel.COL_TO_BONUS)).setMaxWidth(100);
        
        table.getColumn(wltm.getColIndex(WorkLogTableModel.COL_UPDATE_TIME)).setMinWidth(130);
        table.getColumn(wltm.getColIndex(WorkLogTableModel.COL_NAME)).setMinWidth(50);
        
        table.getColumn(wltm.getColIndex(WorkLogTableModel.COL_REST_HOLIDAY)).setCellRenderer(new DoubleTableCellRenderer(SwingConstants.CENTER, 0));

        for (final Highlighter curHighlighter : getSpecilHighlighters())
        {
            table.addHighlighter(curHighlighter);
        }

        add(jlUpdatedate, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        add(new JScrollPane(table), new GridBagConstraints(0, 1, 1, 1, 12, 100, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
                0));
        add(jxmvCalendar, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        setVisible(true);
        add(jlLastUpdateInMonth, new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        setVisible(true);

    }

    public void loadDataInUI()
    {
        setNumWorkingDays();
        // change the color in the days that came logs
        List<Date> lstDate = new ArrayList<Date>();
        DaysOfWorkEAO eaoDoW = new DaysOfWorkEAO(SystemModel.getDefaultEM());
        List<DaysOfWork> lstDaysOfWork = eaoDoW.loadAll();
        wltm.setTableData(WorkLogInfoHelper.getLogListUpToDate(new Date()));
        for (DaysOfWork dof : lstDaysOfWork)
        {
            {
                lstDate.add(dof.getTimestamp());
            }
        }
        if (!lstDate.isEmpty())
        {
            Date[] arrDate = new Date[lstDate.size()];
            arrDate = lstDate.toArray(arrDate);
            jxmvCalendar.setFlaggedDates(arrDate);
            jlLastUpdateInMonth.setText(UDIPropSingleton.getString(this, "updateTime.footer") + DATE_FORMAT.format(jxmvCalendar.getFlaggedDates().last()));
        }
    }
    
    
    public void setNumWorkingDays()
    {
        int yearNumber = jxmvCalendar.getMonthView().getYearNumber();
        if(!mapFlaggetMonthDay.containsKey(yearNumber))
        {
            mapFlaggetMonthDay.put(yearNumber, eaoCalSpecDay.getSpecialDayByYear(yearNumber));
        }
        jxmvCalendar.selectCSDforCurrentMonth(mapFlaggetMonthDay.get(yearNumber), false);
        jxmvCalendar.setNumWrkDays();
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        
    }

    private List<Highlighter> getSpecilHighlighters()
    {
        final List<Highlighter> lstResult = new ArrayList<Highlighter>();
        final int iStatusColumnIndexToPlane = wltm.getColIndex(WorkLogTableModel.COL_TO_PLAN);
        //final int iStatusColumnIndexToBonus = wltm.getColIndex(WorkLogTableModel.COL_TO_BONUS);
        final int iStatusColumnIndexRestHol = wltm.getColIndex(WorkLogTableModel.COL_REST_HOLIDAY);
        final PatternPredicate patternPredicateToPlane = new PatternPredicate("^[^-]*$", iStatusColumnIndexToPlane, iStatusColumnIndexToPlane);
       // final PatternPredicate patternPredicateToBonus = new PatternPredicate("-", iStatusColumnIndexToBonus, iStatusColumnIndexToBonus);
        final PatternPredicate patternPredicateToRestHol = new PatternPredicate("-", iStatusColumnIndexRestHol, iStatusColumnIndexRestHol);
        final ColorHighlighter colorHighlighterToPlane = new ColorHighlighter(patternPredicateToPlane, Color.PINK, Color.BLACK, Color.PINK, Color.BLACK);
        //final ColorHighlighter colorHighlighterToBonus = new ColorHighlighter(patternPredicateToBonus, Color.PINK, Color.BLACK, Color.PINK, Color.BLACK);
        final ColorHighlighter colorHighlighterToRestHol = new ColorHighlighter(patternPredicateToRestHol, Color.PINK, Color.BLACK, Color.PINK, Color.BLACK);
        lstResult.add(colorHighlighterToPlane);
        //lstResult.add(colorHighlighterToBonus);
        lstResult.add(colorHighlighterToRestHol);

        return lstResult;
    }
}
