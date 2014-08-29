package by.uniterra.udi.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import by.uniterra.dai.entity.Worker;
import by.uniterra.udi.iface.IModelOwner;
import by.uniterra.udi.model.UDIPropSingleton;

public class WorkerOptionPanel extends JPanel implements IModelOwner
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = -146808107564587451L;
    
    private JTextField tfFirstName;
    private JTextField tfSecondName;
    private JTextField tfThirdName;
    private JTextField tfAlias;
   
    private Worker worker;
    

    public WorkerOptionPanel()
    {
        super(new GridBagLayout());
        jbInit();
    }
    
    public void jbInit()
    {
        JLabel jlFirstName = new JLabel(UDIPropSingleton.getString(this, "jlFirstName.label"));
        JLabel jlSecondName = new JLabel(UDIPropSingleton.getString(this, "jlSecondName.label"));
        JLabel jlThirdName = new JLabel(UDIPropSingleton.getString(this, "jlThirdName.label"));
        JLabel jlAlias = new JLabel(UDIPropSingleton.getString(this, "jlAlias.label"));
        
        tfFirstName = new JTextField();
        tfSecondName = new JTextField();
        tfThirdName = new JTextField();
        tfAlias = new JTextField();
        
        add(jlFirstName, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(tfFirstName, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        add(jlSecondName, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 5), 0, 0));
        add(tfSecondName, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
        add(jlThirdName, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 5), 0, 0));
        add(tfThirdName, new GridBagConstraints(1, 2, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
        add(jlAlias, new GridBagConstraints(0,3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 5), 0, 0));
        add(tfAlias, new GridBagConstraints(1, 3, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
    }
    @Override
    public void setModel(Object objWorker)
    {
        this.worker = (Worker)objWorker;
        tfFirstName.setText(worker.getFirstName());
        tfSecondName.setText(worker.getSecondName());
        tfThirdName.setText(worker.getThirdName());
        tfAlias.setText(worker.getAlias());
    }

    @Override
    public Object getModel()
    {
        if (worker == null)
        {
            worker = new Worker();
        }
        worker.setFirstName(tfFirstName.getText());
        worker.setSecondName(tfSecondName.getText());
        worker.setThirdName(tfThirdName.getText());
        worker.setAlias(tfAlias.getText());
        return worker;
    }
    
    

}
