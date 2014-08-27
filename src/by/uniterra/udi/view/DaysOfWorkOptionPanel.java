package by.uniterra.udi.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.dai.entity.Month;
import by.uniterra.dai.entity.Worker;
import by.uniterra.udi.iface.IModelOwner;

public class DaysOfWorkOptionPanel extends JPanel implements IModelOwner
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 3401564759734700646L;
    
    private JComboBox cbMonth;
    private JComboBox cbYear;
    private JComboBox cbWorker;
    private JTextField tfActualDays;
    private JTextField tfLogTime;
    private JTextField tfBonusTime;
    private JTextArea  taBonusDesc;
    private JTextField tfRefreshTime;
    
    private List<Worker> workerArrayList;
    private List<Month> monthArrayList;
    
    
    private DaysOfWork daysOfWork;

    public DaysOfWorkOptionPanel()
    {
        super(new GridBagLayout());
        jbUnit();
    }

    private void jbUnit()
    {

        tfActualDays = new JTextField();
        tfLogTime = new JTextField();
        taBonusDesc = new JTextArea();
        tfRefreshTime =new JTextField();
        tfBonusTime = new JTextField();

        JLabel jlMonth = new JLabel("Month");
        //JLabel jlYear = new JLabel("Year");
        JLabel jlActualDays = new JLabel("Aktual days");
        JLabel jlWorker = new JLabel("Worker");
        JLabel jlRefresh = new JLabel("Refresh");
        JLabel jlLogTime = new JLabel("Time from log");
        JLabel jlBonusTime = new JLabel("Bonus time");
        JLabel jlBonusDesc = new JLabel("Description");
        
        workerArrayList = new WorkerEAO(ServiceBaseEAO.getDefaultEM()).loadAll();
        monthArrayList  = new MonthEAO(ServiceBaseEAO.getDefaultEM()).loadAll();
        
        cbWorker = new JComboBox(new DefaultComboBoxModel(workerArrayList.toArray()));
        cbMonth = new JComboBox(new DefaultComboBoxModel(monthArrayList.toArray()));
        
        
        taBonusDesc = new JTextArea();
        taBonusDesc.setColumns(30);
        taBonusDesc.setRows(5);
        taBonusDesc.setLineWrap(true);
        
        add(jlMonth, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(cbMonth, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlActualDays, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(tfActualDays, new GridBagConstraints(3, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlWorker, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(cbWorker, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlLogTime, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(tfLogTime, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlRefresh, new GridBagConstraints(2, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(tfRefreshTime, new GridBagConstraints(3, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlBonusTime, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(tfBonusTime, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlBonusDesc, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(taBonusDesc, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
    }

    @Override
    public void setModel(Object objDaysOfWork)
    {
        this.daysOfWork = (DaysOfWork) objDaysOfWork;
        cbMonth.setSelectedItem(daysOfWork.getMonth());
        tfActualDays.setText(String.valueOf(daysOfWork.getAktualWorkedDays()));
        cbWorker.setSelectedItem(daysOfWork.getWorker());
        tfLogTime.setText(String.valueOf(daysOfWork.getWorklog()));
        
        tfRefreshTime.setText("00.00.00");
        
        tfBonusTime.setText(String.valueOf(daysOfWork.getBonusTime()));
        taBonusDesc.setText(daysOfWork.getBonusTimeDescription());
    }

    @Override
    public Object getModel()
    {
        if(daysOfWork == null)
        {
            daysOfWork = new DaysOfWork();
        }
        daysOfWork.setWorklog(Double.valueOf(tfLogTime.getText()));
        
        daysOfWork.setTimestamp(null);
        
        daysOfWork.setBonusTime(Double.valueOf(tfBonusTime.getText()));
        daysOfWork.setWorker((Worker) cbWorker.getSelectedItem());
        daysOfWork.setAktualWorkedDays(Integer.valueOf(tfActualDays.getText()));
        daysOfWork.setBonusTimeDescription(taBonusDesc.getText());
        // ??
        //daysOfWork.setTimestamp((Date)tfRefreshTime.getText());
        
        daysOfWork.setMonth((Month) cbMonth.getSelectedItem());
        daysOfWork.setWorker((Worker) cbWorker.getSelectedItem());
        
        return daysOfWork;
    }

}
