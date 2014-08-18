package by.uniterra.udi.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

import by.uniterra.dai.entity.Year;
import by.uniterra.udi.iface.IModelOwner;
import by.uniterra.udi.model.UDIPropSingleton;

public class YearOptionPanel extends JPanel implements IModelOwner
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = -5254096192556277102L;

    private JTextField tfNumber;
    private JTextArea tfDeskription;
    private JLabel jlNumber;
    private JLabel jlDesk;

    private Year year;

    public YearOptionPanel()
    {
        super(new GridBagLayout());
        jbInit();
    }

    public void jbInit()
    {
        jlNumber = new JLabel(UDIPropSingleton.getString(this, "jlNumber.label"));
        jlDesk = new JLabel(UDIPropSingleton.getString(this, "jlDesk.label"));
        tfNumber = new JTextField(10);
        tfDeskription = new JTextArea();
        tfDeskription.setColumns(30);
        tfDeskription.setRows(5);
        tfDeskription.setLineWrap(true);

        add(jlNumber, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(tfNumber, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        add(jlDesk, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 5), 0, 0));
        add(tfDeskription, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
    }

    @Override
    public void setModel(Object objYear)
    {
        this.year = (Year) objYear;
        tfNumber.setText(String.valueOf(year.getNumber()));
        tfDeskription.setText(year.getDeskription());
    }

    @Override
    public Object getModel()
    {
        if (year == null)
        {
            year = new Year();
        }
        year.setNumber(Integer.valueOf(tfNumber.getText()));
        year.setDeskription(tfDeskription.getText());
        return year;
    }

}