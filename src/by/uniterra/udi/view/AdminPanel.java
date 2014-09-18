package by.uniterra.udi.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXMonthView;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.system.model.SystemModel;
import by.uniterra.system.util.DateUtils;
import by.uniterra.udi.iface.IModelOwner;
import by.uniterra.udi.model.WorkLogInfoHelper;
import by.uniterra.udi.model.WorkLogTableModel;

public class AdminPanel extends JPanel implements IModelOwner, ActionListener
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = -6399838252872491307L;

    static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public AdminPanel()
    {
        super(new GridBagLayout());
        jbInit();
    }

    private void jbInit()
    {
        Date currentDate = new Date();
        JLabel jlUpdatedate = new JLabel("Данные предоставлены по сотоянию на" + ": " + DATE_FORMAT.format(currentDate));
        JLabel jlLastUpdateInMonth = new JLabel("Последнее обновление лога: --.--.-- ");
        setLayout(new GridBagLayout());

        JXMonthView jxmvCalendar = new JXMonthView();
        // change the color in the days that came logs
        Date curentDate = new Date();
        List<Date> lstDate = new ArrayList<Date>();
        DaysOfWorkEAO eaoDoW = new DaysOfWorkEAO(SystemModel.getDefaultEM());
        List<DaysOfWork> lstDaysOfWork = eaoDoW.loadAll();
        for (DaysOfWork dof : lstDaysOfWork)
        {
            if ((DateUtils.getMonthNumber(dof.getTimestamp()) == DateUtils.getMonthNumber(curentDate))
                    && (DateUtils.getYearNumber(dof.getTimestamp()) == DateUtils.getYearNumber(curentDate)))
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
            jlLastUpdateInMonth.setText("Последнее обновление лога: " + DATE_FORMAT.format(jxmvCalendar.getFlaggedDates().last()));
        }
        jxmvCalendar.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

                /*
                 * jlDate.setText(((JXMonthView)
                 * e.getSource()).getSelection().toString());
                 * WorkLogInfoHelper.getLogListUpToDate
                 * ((Date)(DATE_FORMAT.parse(((JXMonthView)
                 * e.getSource()).getSelection().last().toString())));
                 */
                Date date = ((JXMonthView) e.getSource()).getSelection().last();
                if (date instanceof Date)
                {
                    jlUpdatedate.setText("Данные предоставлены по сотоянию на" + ": " + DATE_FORMAT.format(date));
                }
                else
                {

                    System.err.println("Что-то пошло не так....");
                }

            }
        });

        WorkLogTableModel wltm = new WorkLogTableModel();
        wltm.setTableData(WorkLogInfoHelper.getLogListUpToDate(currentDate));
        JXTable table = new JXTable(wltm);

        table.setColumnControlVisible(true);
        // table.setHorizontalScrollEnabled(true);
        table.addHighlighter(HighlighterFactory.createSimpleStriping(new Color(234, 234, 234)));
        // table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(jlUpdatedate, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        add(new JScrollPane(table), new GridBagConstraints(0, 1, 1, 1, 12, 100, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
                0));
        add(jxmvCalendar, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        setVisible(true);
        add(jlLastUpdateInMonth, new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void setModel(Object mData)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Object getModel()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
