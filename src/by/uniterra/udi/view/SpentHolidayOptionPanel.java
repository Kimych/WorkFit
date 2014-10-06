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
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.entity.Month;
import by.uniterra.dai.entity.SpentHoliday;
import by.uniterra.dai.entity.Worker;
import by.uniterra.system.model.SystemModel;
import by.uniterra.udi.iface.IModelOwner;
import by.uniterra.udi.model.UDIPropSingleton;

public class SpentHolidayOptionPanel extends JPanel implements IModelOwner
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 3367779691566169692L;

    private JTextField tfDaysCount;
    private JComboBox cbDate;
    private JComboBox cbWorker;
    private JTextArea taDesc;

    private List<Month> monthArrayList;
    private List<Worker> workerArrayList;

    private SpentHoliday spentHoliday;

    public SpentHolidayOptionPanel()
    {
        super(new GridBagLayout());
        jbInit();
    }

    private void jbInit()
    {
        tfDaysCount = new JTextField();
        taDesc = new JTextArea();

        JLabel jlDaysCount = new JLabel(UDIPropSingleton.getString(this, "DaysCount.label"));
        JLabel jlDate = new JLabel(UDIPropSingleton.getString(this, "Date.label"));
        JLabel jlWorker = new JLabel(UDIPropSingleton.getString(this, "Worker.label"));
        JLabel jlDesk = new JLabel(UDIPropSingleton.getString(this, "Desc.label"));

        monthArrayList = new MonthEAO(SystemModel.getDefaultEM()).loadAll();
        cbDate = new JComboBox(new DefaultComboBoxModel(monthArrayList.toArray()));

        workerArrayList = new WorkerEAO(SystemModel.getDefaultEM()).loadAll();
        cbWorker = new JComboBox(new DefaultComboBoxModel(workerArrayList.toArray()));

        add(jlDaysCount, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(tfDaysCount, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlDate, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(cbDate, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlWorker, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(cbWorker, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(jlDesk, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(taDesc, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));

    }

    @Override
    public void setModel(Object objSpentHoliday)
    {
        this.spentHoliday = (SpentHoliday) objSpentHoliday;
        if (spentHoliday.getWorker() == null)
        {
            cbDate.setSelectedIndex(0);
            cbWorker.setSelectedIndex(0);
        }
        else
        {
            cbDate.setSelectedItem(spentHoliday.getMonth());
            cbWorker.setSelectedItem(spentHoliday.getWorker());
        }
        tfDaysCount.setText(String.valueOf(spentHoliday.getCountDays()));
        taDesc.setText(spentHoliday.getDescription());

    }

    @Override
    public Object getModel()
    {
        if (spentHoliday == null)
        {
            spentHoliday = new SpentHoliday();
        }
        spentHoliday.setCountDays(Integer.valueOf(tfDaysCount.getText()));
        spentHoliday.setMonth((Month) cbDate.getSelectedItem());
        spentHoliday.setWorker((Worker) cbWorker.getSelectedItem());
        spentHoliday.setDescription(taDesc.getText());

        return spentHoliday;
    }

}
