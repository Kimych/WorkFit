package by.uniterra.udi.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXLabel;

import by.uniterra.udi.util.Cryptor;
import by.uniterra.udi.util.SelectAllFocusAdapter;
import by.uniterra.udi.model.RequestFocusListener;
import by.uniterra.udi.model.UDIPropSingleton;

public class LoginPanel extends JPanel
{
    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 7067901994049701935L;

    
    private JTextField tfUserName;
    private JPasswordField ptfPwd;
    private JCheckBox cbSave;
    boolean flag = false;


    public LoginPanel(String strDefaultUser)
    {
        super(new GridBagLayout());
        jbUnit();
        if (!strDefaultUser.isEmpty())
        {
            // set default user
            tfUserName.setText(strDefaultUser);
            //set focus on password
            ptfPwd.addAncestorListener( new RequestFocusListener(true) );
        }
    }

    private void jbUnit()
    {
        tfUserName = new JTextField();
        cbSave = new JCheckBox(UDIPropSingleton.getString(this, "save.label") ,false);
        tfUserName.addFocusListener(new SelectAllFocusAdapter(tfUserName));
        tfUserName.addAncestorListener(new RequestFocusListener(true));
        ptfPwd = new JPasswordField();
        ptfPwd.addFocusListener(new SelectAllFocusAdapter(ptfPwd));

        //Add the components to the JPanel        
        add(new JXLabel(UDIPropSingleton.getString(this, "user.label")), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        add(tfUserName, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        add(new JXLabel(UDIPropSingleton.getString(this, "password.label")), new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        add(ptfPwd,new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        add(cbSave,new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        
        cbSave.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                flag = true;
            }
        });
    }
    
    /**
     * Compare valid password with user input
     * @return true if password equals, false otherwise
     *
     * @author Anton Nedbailo
     * @date Sep 1, 2013
     */
    public boolean isCorrectPwdEntered(char[] arrValidPassword)
    {
        boolean bResult = false;
        //Retrieve entered password
        char[] arrEnteredPwd = ptfPwd.getPassword();
        bResult = Arrays.equals(arrValidPassword, arrEnteredPwd);
        // it's recommended for added security to zero out the returned char array containing the password:
        Arrays.fill(arrEnteredPwd, '0');
        return bResult;
    }

    /**
     * Get entered user name
     * @return String object with entered user name
     *
     * @author Anton Nedbailo
     * @date Sep 1, 2013
     */
    public String getUserName()
    {
        return tfUserName.getText();
    }
    
    public String getPassword()
    {
        return Cryptor.getSecureString(ptfPwd.getText());
    }
    
    public boolean getStatmentFlag()
    {
        return flag;
    }
    


}
