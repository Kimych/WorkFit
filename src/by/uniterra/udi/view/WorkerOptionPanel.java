package by.uniterra.udi.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import by.uniterra.dai.entity.Worker;
import by.uniterra.udi.iface.IModelOwner;

public class WorkerOptionPanel extends JPanel implements IModelOwner
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = -146808107564587451L;
    
    private JTextField tfFirstName;
    private JTextField tfSecondName;
    private JTextField tfThirdName;
    private JLabel jlFirstName;
    private JLabel jlSecondName;
    private JLabel jlThirdName;
    
    private Worker worker;
    

    public WorkerOptionPanel()
    {
        super(new GridBagLayout());
        jbInit();
    }
    
    public void jbInit()
    {
        jlFirstName = new JLabel("Имя");
        jlSecondName = new JLabel("Фамилия");
        jlThirdName = new JLabel("Отчество");
        tfFirstName = new JTextField();
        tfSecondName = new JTextField();
        tfThirdName = new JTextField();
        
        add(jlFirstName, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
        add(tfFirstName, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        add(jlSecondName, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 5), 0, 0));
        add(tfSecondName, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
        add(jlThirdName, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 5), 0, 0));
        add(tfThirdName, new GridBagConstraints(1, 2, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
    }
    @Override
    public void setModel(Object objWorker)
    {
        this.worker = (Worker)objWorker;
        tfFirstName.setText(worker.getFirstName());
        tfSecondName.setText(worker.getSecondName());
        tfThirdName.setText(worker.getSecondName());
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
        return worker;
    }
    
    

}
