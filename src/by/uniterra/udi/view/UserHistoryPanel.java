package by.uniterra.udi.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTable;





import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.dai.entity.Worker;
import by.uniterra.system.model.SystemModel;
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
            JXTable table = new JXTable(doftm);
            add(new JScrollPane(table), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        }
        
    }
    
    
}
