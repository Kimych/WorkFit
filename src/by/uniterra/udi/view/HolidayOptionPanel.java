package by.uniterra.udi.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.eao.YearEAO;
import by.uniterra.dai.entity.Holiday;
import by.uniterra.dai.entity.Worker;
import by.uniterra.dai.entity.Year;
import by.uniterra.system.model.SystemModel;
import by.uniterra.udi.iface.IModelOwner;
import by.uniterra.udi.model.UDIPropSingleton;

public class HolidayOptionPanel extends JPanel implements IModelOwner
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = -6282330382069748329L;
    
    private JComboBox<?> cbName;
    private JComboBox<?> cbYear;
    private Holiday holiday;
    private JTextField tfDays;
    private List<Worker> workerArrayList;
    private List<Year> yearArrayList;
    
    

    public HolidayOptionPanel()
    {
        super(new GridBagLayout());
        jbInit();
    }

    
    private void jbInit()
    {
        JLabel jlName = new JLabel(UDIPropSingleton.getString(this, "jlName.label"));
        JLabel jlYear = new JLabel(UDIPropSingleton.getString(this, "jlYear.label"));
        JLabel jlDays = new JLabel(UDIPropSingleton.getString(this, "jlDays.label"));
        tfDays = new JTextField(2);
        
        workerArrayList = new WorkerEAO(SystemModel.getDefaultEM()).loadAll();
        cbName = new JComboBox(new DefaultComboBoxModel(workerArrayList.toArray()));
        
        yearArrayList = new YearEAO(SystemModel.getDefaultEM()).loadAll();
        cbYear = new JComboBox(new DefaultComboBoxModel(yearArrayList.toArray()));
        
        add(jlName, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(cbName, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlYear, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 5), 0, 0));
        add(cbYear, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 5), 0, 0));
        add(jlDays, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 5), 0, 0));
        add(tfDays, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 5), 0, 0));
    }


    @Override
    public void setModel(Object objHoliday)
    {
        this.holiday = (Holiday) objHoliday;
        tfDays.setText(String.valueOf(holiday.getCountDays()));
        cbName.setSelectedItem(holiday.getWorker());
        cbYear.setSelectedItem(holiday.getYear());

    }

    @Override
    public Object getModel()
    {
        holiday.setWorker((Worker) cbName.getSelectedItem());
        holiday.setYear((Year) cbYear.getSelectedItem());
        holiday.setCountDays(Integer.valueOf(tfDays.getText()));
        return holiday;
    }

}
