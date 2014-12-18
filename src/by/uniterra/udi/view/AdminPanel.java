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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXMonthView;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.decorator.PatternPredicate;

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
/*    private static final String ACTION_NEXT_MONTH = "Next Month";
    private static final String ACTION_PREVIOUS_MONTH = "Previous Month";*/

    private WorkLogTableModel wltm;
    private MonthSpecialCalendar jxmvCalendar;
    // private JMenu jmMenu;
    // private JMenuBar jmBar;
    private JLabel jlLastUpdateInMonth;
    private JLabel jlUpdatedate;

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
                // show edit view on mouse right click
                if (SwingUtilities.isRightMouseButton(e))
                {
                }
            }  
        };
        jxmvCalendar.setZoomable(true);
        
        /*JButton btnNextYear = new JButton(">>");
        btnNextYear.setActionCommand(ACTION_NEXT_MONTH);
        btnNextYear.addActionListener(this);

        JButton btnPreviousYear = new JButton("<<");
        btnPreviousYear.setActionCommand(ACTION_PREVIOUS_MONTH);
        btnPreviousYear.addActionListener(this);*/
       /* jxmvCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        // old style: set visual property with JXMonthView api
        jxmvCalendar.setDayForeground(Calendar.SUNDAY, Color.MAGENTA);
        jxmvCalendar.setDayForeground(Calendar.SATURDAY, Color.MAGENTA);
        jxmvCalendar.setZoomable(true);*/

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
        table.getColumn(wltm.getColIndex(WorkLogTableModel.COL_TO_BONUS)).setCellRenderer(new DoubleTableCellRenderer(SwingConstants.CENTER, 2));
        table.getColumn(wltm.getColIndex(WorkLogTableModel.COL_REST_HOLIDAY)).setCellRenderer(new DoubleTableCellRenderer(SwingConstants.CENTER, 0));

        for (final Highlighter curHighlighter : getSpecilHighlighters())
        {
            table.addHighlighter(curHighlighter);
        }
        
/*        jxmvCalendar.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
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
        });*/

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
            // convert List<Date> into Date[]
            Date[] arrDate = new Date[lstDate.size()];
            arrDate = lstDate.toArray(arrDate);
            // change color
  /*          ((JXMonthView) jxmvCalendar).setFlaggedDayForeground(Color.RED);
            ((JXMonthView) jxmvCalendar).setFlaggedDates(arrDate);
            jlLastUpdateInMonth.setText(UDIPropSingleton.getString(this, "updateTime.footer") + DATE_FORMAT.format(jxmvCalendar.getFlaggedDates().last()));*/
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        /*try
        {
            switch (arg0.getActionCommand())
            {
            case ACTION_NEXT_MONTH:
                jxmvCalendar.setModel(++numYear);
                break;
            case ACTION_PREVIOUS_MONTH:
                setModel(--numYear);
                break;
            default:
                break;
            }
        }
        catch (Exception e)
        {
            Log.error(YearSpecialCalendar.class, e, "actionPerformed expressions");
        }*/
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
