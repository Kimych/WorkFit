package by.uniterra.udi.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.JXTable;





import org.jdesktop.swingx.decorator.HighlighterFactory;

import com.sun.jmx.snmp.Timestamp;

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.dai.entity.Worker;
import by.uniterra.system.model.SystemModel;
import by.uniterra.system.util.DateUtils;
import by.uniterra.udi.model.DaysOfWorkTableModel;


public class UserHistoryPanel extends JPanel
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 7019769463093193762L;
    
    public UserHistoryPanel(Worker worker)
    {
        super(new GridBagLayout());
        jbInit(worker);
    }

    private void jbInit(Worker worker)
    {
        
        DaysOfWorkEAO dofEAO = new DaysOfWorkEAO(SystemModel.getDefaultEM());
        List<DaysOfWork> lstDofW = dofEAO.finAllByWorker(worker);
        if(lstDofW.size() != 0)
        {
            DaysOfWorkTableModel doftm = new DaysOfWorkTableModel(true);
            doftm.setTableData(lstDofW);
            JXTable tTable = new JXTable(doftm);
            // All there is to it! Users can now select which columns to view
            tTable.setColumnControlVisible(true);
            tTable.setHorizontalScrollEnabled(true);
            tTable.addHighlighter(HighlighterFactory.createSimpleStriping(new Color(234, 234, 234)));
            tTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            // set renderers for special types
            TimestampTableCellRenderer txrTimestampRenderer = new TimestampTableCellRenderer(SwingConstants.CENTER, DateUtils.EUROP_FULL_DATETIMEFORMAT, "UTC");
            tTable.setDefaultRenderer(Timestamp.class, txrTimestampRenderer);
            tTable.setDefaultRenderer(Date.class, txrTimestampRenderer);
            tTable.setSortOrder(doftm.getSortColumnIndex(), doftm.getSortOrder());
            add(new JScrollPane(tTable), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        }
        
    }
    
    
}
