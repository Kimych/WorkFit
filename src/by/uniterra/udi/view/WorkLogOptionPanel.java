package by.uniterra.udi.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import by.uniterra.udi.iface.IModelOwner;
import by.uniterra.udi.model.UDIPropSingleton;
import by.uniterra.udi.model.WorkLogInfoHolder;

public class WorkLogOptionPanel extends JPanel implements IModelOwner
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 3687259560748246183L;

    private WorkLogInfoHolder woih;
    JTextField tfCurentTime;
    JTextField tfToPlane;
    JTextField tfToBonus;
    JTextField tfTimeLeft;
    JLabel jlLastUpdateDate;
    JLabel jlNameWorker;

    public WorkLogOptionPanel()
    {
        super(new GridBagLayout());
        jbInit();
    }

    private void jbInit()
    {
        tfCurentTime = new JTextField(3);
        tfToPlane = new JTextField(3);
        tfToBonus = new JTextField(3);
        tfTimeLeft = new JTextField(3);
        jlLastUpdateDate = new JLabel();
        jlNameWorker = new JLabel();

        JLabel jlWorker = new JLabel(UDIPropSingleton.getString(this, "jlWorker.label"));
        JLabel jlLastUpdate = new JLabel(UDIPropSingleton.getString(this, "jlLastUpdate.label"));
        JLabel jlCurrentTime = new JLabel(UDIPropSingleton.getString(this, "jlCurrentTime.label"));
        JLabel jlToPlane = new JLabel(UDIPropSingleton.getString(this, "jlToPlane.label"));
        JLabel jlToBonus = new JLabel(UDIPropSingleton.getString(this, "jlToBonus.label"));
        JLabel jlTimeLeft = new JLabel(UDIPropSingleton.getString(this, "jlTimeLeft.label"));

        add(jlWorker, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
        add(jlLastUpdate, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
        add(jlNameWorker, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
        add(jlLastUpdateDate, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
        add(jlCurrentTime, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
        add(jlToPlane, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
        add(jlToBonus, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
        add(jlTimeLeft, new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));

        add(tfCurentTime, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
        add(tfToPlane, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
        add(tfToBonus, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
        add(tfTimeLeft, new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));

    }

    @Override
    public void setModel(Object workLogInfoHolder)
    {
        this.woih = (WorkLogInfoHolder) workLogInfoHolder;
        tfCurentTime.setText(woih.getCurentTime());
        jlLastUpdateDate.setText(woih.getLastUpdateDate());
        jlNameWorker.setText(woih.getNameWorker());
        tfToPlane.setText(String.valueOf(woih.getToPlane()));
        tfToBonus.setText(String.valueOf(woih.getToBonus()));
    }

    @Override
    public Object getModel()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
