package by.uniterra.udi.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.JXMonthView;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.decorator.PatternPredicate;

import by.uniterra.dai.eao.DaysOfWorkEAO;
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
    private static final String ACTION_SAVE_TO_MODEL = "Save to model";

    private WorkLogTableModel wltm;
    private JXMonthView jxmvCalendar;
    //private JMenu jmMenu;
    //private JMenuBar jmBar;
    private JLabel jlLastUpdateInMonth;
    private JLabel jlUpdatedate;

    public AdminPanel()
    {
        super(new GridBagLayout());
        jbInit();
        //loadDataInUI();
    }

    private void jbInit()
    {
        Date currentDate = new Date();
        jlUpdatedate = new JLabel(UDIPropSingleton.getString(this, "infoTime.header") + DATE_FORMAT.format(currentDate));
        jlLastUpdateInMonth = new JLabel(UDIPropSingleton.getString(this, "updateTime.footer")+ "--.--.--");
        setLayout(new GridBagLayout());

        jxmvCalendar = new JXMonthView();
        jxmvCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        // old style: set visual property with JXMonthView api
        jxmvCalendar.setDayForeground(Calendar.SUNDAY, Color.MAGENTA);
        jxmvCalendar.setDayForeground(Calendar.SATURDAY, Color.MAGENTA);
        jxmvCalendar.setZoomable(true);

        wltm = new WorkLogTableModel();
        //wltm.setTableData(WorkLogInfoHelper.getLogListUpToDate(currentDate));
        JXTable table = new JXTable(wltm);
        table.setColumnControlVisible(true);
        // table.setHorizontalScrollEnabled(true);
        table.addHighlighter(HighlighterFactory.createSimpleStriping(new Color(234, 234, 234)));
        // swt custom renderers
        TimestampTableCellRenderer txrTimestampRenderer = new TimestampTableCellRenderer(SwingConstants.CENTER, DateUtils.EUROP_FULL_DATETIMEFORMAT, "UTC");
        table.setDefaultRenderer(Timestamp.class, txrTimestampRenderer);
        table.setDefaultRenderer(Date.class, txrTimestampRenderer);
        
        table.getColumn(wltm.getColIndex(WorkLogTableModel.COL_TO_PLANE)).setCellRenderer(new DoubleTableCellRenderer(SwingConstants.CENTER, 2));
        table.getColumn(wltm.getColIndex(WorkLogTableModel.COL_TO_BONUS)).setCellRenderer(new DoubleTableCellRenderer(SwingConstants.CENTER, 2));
        table.getColumn(wltm.getColIndex(WorkLogTableModel.COL_REST_HOLIDAY)).setCellRenderer(new DoubleTableCellRenderer(SwingConstants.CENTER, 0));
        
        for (final Highlighter curHighlighter : getSpecilHighlighters())
        {
            table.addHighlighter(curHighlighter);
        }
        
        jxmvCalendar.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Date date = ((JXMonthView) e.getSource()).getSelection().last();
                if (date instanceof Date)
                {
                    List<WorkLogInfoHolder> lstNewData = WorkLogInfoHelper.getLogListUpToDate(date);
                    jlUpdatedate.setText(UDIPropSingleton.getString(this, "infoTime.header")  + DATE_FORMAT.format(date));
                    wltm.setTableData(lstNewData);
                }
                else
                {
                    Log.error(AdminPanel.class, "actionPerformed(ActionEvent e)");
                }
            }
        });

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
            jxmvCalendar.setFlaggedDayForeground(Color.RED);
            jxmvCalendar.setFlaggedDates(arrDate);
            jlLastUpdateInMonth.setText(UDIPropSingleton.getString(this, "updateTime.footer") + DATE_FORMAT.format(jxmvCalendar.getFlaggedDates().last()));
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        try
        {
            switch (arg0.getActionCommand())
            {
            case ACTION_SAVE_TO_MODEL:
                ;
                break;

            default:
                break;
            }
        }
        catch (Exception e)
        {
            Log.error(AdminPanel.class, "actionPerformed error!");
            e.printStackTrace();
        }
    }
    
    private List<Highlighter> getSpecilHighlighters()
    {
        final List<Highlighter> lstResult = new ArrayList<Highlighter>();
        
        final int iStatusColumnIndex = wltm.getColIndex(WorkLogTableModel.COL_TO_PLANE);
        final int iStatusColumnIndex2 = wltm.getColIndex(WorkLogTableModel.COL_TO_BONUS);
        final int iStatusColumnIndex3 = wltm.getColIndex(WorkLogTableModel.COL_REST_HOLIDAY);
        
        
        final PatternPredicate patternPredicate = new PatternPredicate("-", iStatusColumnIndex,
                iStatusColumnIndex);
        final PatternPredicate patternPredicate2 = new PatternPredicate("-", iStatusColumnIndex2,
                iStatusColumnIndex2);
        final PatternPredicate patternPredicate3 = new PatternPredicate("-", iStatusColumnIndex3,
                iStatusColumnIndex3);
        
        
        final ColorHighlighter colorHighlighter = new ColorHighlighter(patternPredicate, Color.GREEN, Color.BLACK, Color.GREEN, Color.BLACK);
        final ColorHighlighter colorHighlighter2 = new ColorHighlighter(patternPredicate2, Color.GREEN, Color.BLACK, Color.GREEN, Color.BLACK);
        final ColorHighlighter colorHighlighter3 = new ColorHighlighter(patternPredicate3, Color.GREEN, Color.BLACK, Color.GREEN, Color.BLACK);
        

        lstResult.add(colorHighlighter);
        lstResult.add(colorHighlighter2);
        lstResult.add(colorHighlighter3);
        
        return lstResult;
    }
}
