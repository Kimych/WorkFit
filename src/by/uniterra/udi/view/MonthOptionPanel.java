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

import by.uniterra.dai.eao.NameMonthEAO;
import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.eao.YearEAO;
import by.uniterra.dai.entity.Month;
import by.uniterra.dai.entity.NameMonth;
import by.uniterra.dai.entity.Year;
import by.uniterra.udi.iface.IModelOwner;

public class MonthOptionPanel extends JPanel implements IModelOwner
{
    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 3210721310184243056L;
    private JTextField tfNumber;
    private JTextArea tfDeskription;
    private JLabel jlNumber;
    private JLabel jlDesk;
    private JComboBox cbMonth;
    private JComboBox cbYear;
    private Month month;
    private List<NameMonth> nameMonthArrayList;
    private List<Year> yearArrayList;

    public MonthOptionPanel()
    {
        super(new GridBagLayout());
        jbInit();
    }

    public void jbInit()
    {
        jlNumber = new JLabel("Дней");
        jlDesk = new JLabel("Примечание");
        tfNumber = new JTextField(10);
        nameMonthArrayList = new NameMonthEAO(ServiceBaseEAO.getDefaultEM()).loadAll();
        cbMonth = new JComboBox(new DefaultComboBoxModel(nameMonthArrayList.toArray()));
        yearArrayList = new YearEAO(ServiceBaseEAO.getDefaultEM()).loadAll();
        cbYear = new JComboBox(new DefaultComboBoxModel(yearArrayList.toArray()));
        
        
        tfDeskription = new JTextArea();
        tfDeskription.setColumns(30);
        tfDeskription.setRows(5);
        tfDeskription.setLineWrap(true);

        add(cbMonth, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(cbYear, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        add(jlNumber, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(tfNumber, new GridBagConstraints(3, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        add(jlDesk, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 5), 0, 0));
        add(tfDeskription, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));

    }

    /**
     * @see by.uniterra.udi.view.IModelOwner#setModel(java.lang.Object)
     */
    @Override
    public void setModel(Object objMonth)
    {
        this.month = (Month) objMonth;
        tfNumber.setText(String.valueOf(month.getWorkingDaysCount()));
        tfDeskription.setText(month.getDescription());
        cbYear.setSelectedItem(month.getYear());
        cbMonth.setSelectedItem(month.getNameMonth());
    }

    /* (non-Javadoc)
     * @see by.uniterra.udi.view.IModelOwner#getModel()
     */
    @Override
    public Month getModel()
    {
        if (month == null)
        {
            month = new Month();
        }
        month.setWorkingDaysCount(Integer.valueOf(tfNumber.getText()));
        month.setDescription(tfDeskription.getText());
        month.setYear((Year) cbYear.getSelectedItem());
        month.setNameMonth((NameMonth) cbMonth.getSelectedItem());
        return month;
    }

}
