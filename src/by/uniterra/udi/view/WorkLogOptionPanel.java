package by.uniterra.udi.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.eao.MonthEAO;
import by.uniterra.dai.eao.ServiceBaseEAO;
import by.uniterra.dai.eao.WorkerEAO;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.udi.iface.IModelOwner;

public class WorkLogOptionPanel extends JPanel implements IModelOwner
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 3687259560748246183L;

    private DaysOfWork dof;
    JTextField tfCurentTime = new JTextField(3);
    JTextField tfToPlane = new JTextField(3);
    JTextField tfToBonus = new JTextField(3);
    JTextField tfTimeLeft = new JTextField(3);
    JLabel jlLastUpdateDate = new JLabel();
    JLabel jlNameWorker = new JLabel();

    public WorkLogOptionPanel()
    {
        super(new GridBagLayout());
        jbInit();
    }

    private void jbInit()
    {
        JLabel jlWorker = new JLabel("Сотрудник:");
        JLabel jlLastUpdate = new JLabel("Обновлялось");
        JLabel jlCurrentTime = new JLabel("Текущее");
        JLabel jlToPlane = new JLabel("До плана");
        JLabel jlToBonus = new JLabel("До бонуса");
        JLabel jlTimeLeft = new JLabel("Осталось отработать");

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
    public void setModel(Object lstDaysOfWork)
    {
        this.dof = (DaysOfWork) lstDaysOfWork;
        tfCurentTime.setText(String.valueOf(dof.getDaysOfWorkId()));
        jlLastUpdateDate.setText(String.valueOf(dof.getTimestamp()));
        jlNameWorker.setText(String.valueOf(dof.getWorker()));
        // add tfToPlane value
        // add tfToPlane value
        // add...
    }

    @Override
    public Object getModel()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
