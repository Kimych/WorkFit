package by.uniterra.udi.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import by.uniterra.system.util.DateUtils;
import by.uniterra.system.util.WorkLogUtils;
import by.uniterra.udi.iface.IModelOwner;
import by.uniterra.udi.model.UDIPropSingleton;
import by.uniterra.udi.model.WorkLogInfoHolder;

public class WorkLogOptionPanel extends JPanel implements IModelOwner
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 3687259560748246183L;
    private static final Color COLOR_OK_GREEN = Color.GREEN;
    private static final Color COLOR_WARNING_RED = Color.RED;

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
        tfCurentTime = new JTextField(4);
        tfToPlane = new JTextField(4);
        tfToBonus = new JTextField(4);
        tfTimeLeft = new JTextField(4);
        jlLastUpdateDate = new JLabel();
        jlNameWorker = new JLabel();

        JLabel jlCurrentTime = new JLabel(UDIPropSingleton.getString(this, "jlCurrentTime.label"));
        JLabel jlToPlane = new JLabel(UDIPropSingleton.getString(this, "jlToPlane.label"));
        JLabel jlToBonus = new JLabel(UDIPropSingleton.getString(this, "jlToBonus.label"));
        JLabel jlTimeLeft = new JLabel(UDIPropSingleton.getString(this, "jlTimeLeft.label"));

        add(jlNameWorker, new GridBagConstraints(0, 0, 2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
        add(jlLastUpdateDate, new GridBagConstraints(0, 1, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
        add(jlCurrentTime, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
        add(tfCurentTime, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
        add(jlToPlane, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
        add(tfToPlane, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
        add(jlToBonus, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
        add(tfToBonus, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
        add(jlTimeLeft, new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
        add(tfTimeLeft, new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
    }

    @Override
    public void setModel(Object workLogInfoHolder)
    {
        this.woih = (WorkLogInfoHolder) workLogInfoHolder;
        tfCurentTime.setText(woih.getCurentTime());
        jlLastUpdateDate.setText(DateUtils.toGMT(woih.getLastUpdateDate()));

        jlNameWorker.setText(woih.getNameWorker());
        jlNameWorker.setFont(new Font("Serif", Font.PLAIN, 20));

        tfToPlane.setText(WorkLogUtils.roundToString(woih.getToPlane(), 2, BigDecimal.ROUND_HALF_UP));
        // set color
        if (woih.isBeInPlane())
        {
            tfToPlane.setBackground(COLOR_OK_GREEN);
        }
        else
        {
            tfToPlane.setBackground(COLOR_WARNING_RED);
        }

        tfToBonus.setText(WorkLogUtils.roundToString(woih.getToBonus(), 2, BigDecimal.ROUND_HALF_UP));
        tfTimeLeft.setText(String.valueOf(WorkLogUtils.roundToString(woih.getTimeLeft(), 0, BigDecimal.ROUND_HALF_UP)));
    }

    @Override
    public Object getModel()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
